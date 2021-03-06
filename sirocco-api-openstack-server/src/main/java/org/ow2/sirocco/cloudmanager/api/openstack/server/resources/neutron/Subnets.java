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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.SubnetForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.SubnetForUpdate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron.functions.NetworkToNetwork;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron.functions.SubnetToSubnet;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.notImplemented;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.badRequest;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * Subnets are not supported since there is no direct mathing in sirocco. See issue #38
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class Subnets extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.neutron.resources.Subnets {

    private static Logger LOG = LoggerFactory.getLogger(Subnets.class);

    @Inject
    private INetworkManager networkManager;

    @Override
    public Response list() {
    	 org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Subnets subnets = new org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Subnets();
         try {
        	 subnets.setList(Lists.transform(networkManager.getSubnets().getItems(), new SubnetToSubnet()));
             return ok(subnets);
         } catch (InvalidRequestException ire) {
             return badRequest("subnet", "get");
         } catch (CloudProviderException e) {
             final String error = "Error while getting subnet details";
             if (LOG.isDebugEnabled()) {
                 LOG.debug(error, e);
             } else {
                 LOG.error(error);
             }
             return computeFault("Subnet Error", e.getMessage());
         }
    }

    @Override
    public Response create(SubnetForCreate subnet) {
        return notImplemented(Subnets.class.getName(), "create");
    }

    @Override
    public Response get(String id) {
    	try {
            return ok(new SubnetToSubnet().apply(networkManager.getSubnetByUuid(id)));
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("volume", id, rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting subnet details";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Subnet Error", e.getMessage());
        }
    }

    @Override
    public Response update(String id, SubnetForUpdate subnetForUpdate) {
        return notImplemented(Subnets.class.getName(), "update");
    }

    @Override
    public Response delete(String id) {
        return notImplemented(Subnets.class.getName(), "delete");
    }
}
