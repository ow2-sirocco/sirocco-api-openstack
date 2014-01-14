/**
 * SIROCCO
 * Copyright (C) 2014 France Telecom
 * Contact: sirocco@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */
package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.LinkHelper;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Flavor;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.MachineConfigurationToFlavor;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.queries.FlavorListQuery;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class Flavors extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Flavors {

    private static final Logger LOGGER = LoggerFactory.getLogger(Servers.class);

    @Inject
    private IMachineManager machineManager;

    @Override
    public Response list() {
        return getFlavors(false);
    }

    @Override
    public Response details() {
        return getFlavors(true);
    }

    @Override
    public Response details(String id) {
        try {
            MachineConfiguration config = machineManager.getMachineConfigurationByUuid(id);
            if (config == null) {
                return resourceNotFoundException("flavor", id, new ResourceNotFoundException("Flavor not found"));
            } else {
                Flavor result = new MachineConfigurationToFlavor(true).apply(config);
                result.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, null));
                result.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.BOOKMARK, null, null));
                return ok(result);
            }
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("flavor", id, new ResourceNotFoundException("Flavor not found"));
        } catch (CloudProviderException e) {
            final String error = "Error while getting flavor";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    protected Response getFlavors(boolean details) {
        org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Flavors result = new org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Flavors();
        try {
            List<MachineConfiguration> configs = machineManager.getMachineConfigurations(new FlavorListQuery().apply(getJaxRsRequestInfo())).getItems();
            if (configs == null || configs.size() == 0) {
                return ok(result);
            } else {
                List<Flavor> flavors = Lists.transform(configs, new MachineConfigurationToFlavor(details));
                flavors = Lists.transform(flavors, new Function<Flavor, Flavor>() {
                    @Override
                    public Flavor apply(org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Flavor input) {
                        input.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, "%s", input.id));
                        input.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.BOOKMARK, null, "%s", input.id));
                        return input;
                    }
                });
                result.setFlavors(flavors);
                return ok(result);
            }

        } catch (CloudProviderException e) {
            final String error = "Error while getting flavors";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }

    }
}
