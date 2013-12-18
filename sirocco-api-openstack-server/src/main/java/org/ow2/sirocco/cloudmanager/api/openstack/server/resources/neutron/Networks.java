/**
 * SIROCCO
 * Copyright (C) 2013 France Telecom
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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron;

import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.NetworkForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.NetworkForUpdate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron.functions.NetworkForCreateToNetworkCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron.functions.NetworkToNetwork;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.ITenantManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.*;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class Networks extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.neutron.resources.Networks {

    private static Logger LOG = LoggerFactory.getLogger(Networks.class);

    @Inject
    private INetworkManager networkManager;

    @Inject
    private ITenantManager tenantManager;

    @Override
    public Response list() {
        org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Networks networks = new org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Networks();
        try {
            networks.setList(Lists.transform(networkManager.getNetworks().getItems(), new NetworkToNetwork(true)));
            return ok(networks);
        } catch (InvalidRequestException ire) {
            return badRequest("network", "get");
        } catch (CloudProviderException e) {
            final String error = "Error while getting networks details";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Network Error", 500, e.getMessage());
        }
    }

    @Override
    public Response create(NetworkForCreate network) {
        String provider = "TODO";

        try {
            Job job = networkManager.createNetwork(new NetworkForCreateToNetworkCreate(provider).apply(network));
            Network n = (Network) job.getTargetResource();
            return ok(new NetworkToNetwork(true).apply(n));
        } catch (InvalidRequestException ire) {
            return badRequest("network", "create");
        } catch (CloudProviderException e) {
            final String error = "Error while creating network";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Network Error", 500, e.getMessage());
        }
    }

    @Override
    public Response createMultiple(org.ow2.sirocco.cloudmanager.api.openstack.neutron.resources.Networks networks) {
        return notImplemented(Networks.class.getName(), "createMultiple");
    }

    @Override
    public Response get(String id) {
        try {
            return ok(new NetworkToNetwork(true).apply(networkManager.getNetworkByUuid(id)));
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("volume", id, rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting network details";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Network Error", 500, e.getMessage());
        }
    }

    @Override
    public Response update(String id, NetworkForUpdate networkForUpdate) {
        // NOTE : Not supported on the backend
        try {
            Network network = networkManager.getNetworkByUuid(id);

            network.setName(networkForUpdate.getName());
            network.setTenant(tenantManager.getTenantByName(getPathParamValue(Constants.Nova.TENANT_PATH_TEMPLATE)));

            LOG.warn("TODO : shared network mapping");
            LOG.warn("TODO : adminStateUp network mapping");

            networkManager.updateNetwork(network);
            return ok(new NetworkToNetwork(true).apply(networkManager.getNetworkByUuid(id)));
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("network", id, rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while updating network details";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Network Error", 500, e.getMessage());
        }
    }

    @Override
    public Response delete(String id) {
        try {
            networkManager.deleteNetwork(id);
            return deleted();
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("network", id, rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while deleting network";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Network Error", 500, e.getMessage());
        }
    }
}
