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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Address;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Addresses;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.NetworkAddresses;
import org.ow2.sirocco.cloudmanager.api.openstack.server.functions.NetworkInterfacesToAddresses;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineNetworkInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.builders.FaultBuilder.itemNotFound;
import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.*;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class ServerAddresses extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.ServerAddresses {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerAddresses.class);

    @Inject
    private IMachineManager machineManager;

    @Override
    public Response list() {
        String server = getPathParamValue(Constants.Nova.SERVER_ID_PATH_PARAMETER);
        try {
            List<MachineNetworkInterface> list = machineManager.getMachineNetworkInterfaces(server).getItems();
            return ok(new NetworkInterfacesToAddresses().apply(list));
        } catch (InvalidRequestException ire) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Error while getting network interfaces for server " + server, ire);
            }
            return badRequest("server addresses", "list");
        } catch (CloudProviderException e) {
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response list(String network) {
        String server = getPathParamValue(Constants.Nova.SERVER_ID_PATH_PARAMETER);
        try {
            List<MachineNetworkInterface> list = machineManager.getMachineNetworkInterfaces(server).getItems();
            Addresses addresses = new NetworkInterfacesToAddresses().apply(list);

            List<Address> networkAddresses = addresses.getAddresses().get(network);
            if (networkAddresses != null && networkAddresses.size() > 0) {
                NetworkAddresses result = new NetworkAddresses();
                result.setId(network);
                result.getIp().addAll(networkAddresses);
                return ok(result);
            } else {
                return fault(itemNotFound("Not found", 404, "No address for network " + network));
            }

        } catch (InvalidRequestException ire) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Error while getting network interfaces for server " + server, ire);
            }
            return badRequest("server addresses", "list");
        } catch (CloudProviderException e) {
            return computeFault("Server Error", 500, e.getMessage());
        }
    }
}
