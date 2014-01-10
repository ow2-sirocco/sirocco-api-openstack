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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions.extensions.serveradmin;

import org.codehaus.jackson.JsonNode;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerAction;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Action;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions.AbstractAction;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.badRequest;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * Add a floating IP to a server.
 *
 * <pre>
 *     {
 *          "addFloatingIp": {
 *              "address": "10.10.10.1"
 *          }
 *      }
 * </pre>
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class AddFloatingIPAddressAction extends AbstractAction implements Action {

    private static Logger LOG = LoggerFactory.getLogger(AddFloatingIPAddressAction.class);

    public static final String ACTION = "addFloatingIp";

    @Inject
    private INetworkManager networkManager;

    @Override
    public String getName() {
        return ACTION;
    }

    @Override
    public Response invoke(String serverId, JsonNode payload) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Add floating IP to server ", serverId);
        }

        ServerAction.AssociateFloatingIp associateFloatingIp = null;
        try {
            associateFloatingIp = getBean(payload, ServerAction.AssociateFloatingIp.class);
            if (associateFloatingIp.getAddress() == null) {
                return badRequest("Bad request", "Address is required");
            }
            networkManager.addAddressToMachine(serverId, associateFloatingIp.getAddress());
        } catch (IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.error("Parse payload error", e);
            }
            return badRequest("Server : Can not read input payload", ACTION);
        } catch (ResourceNotFoundException e) {
            return resourceNotFoundException(ACTION, associateFloatingIp.getAddress(), e);
        } catch (CloudProviderException e) {
            if (LOG.isDebugEnabled()) {
                LOG.error("Core exception", e);
            }
            return computeFault(ACTION, e.getMessage());
        }
        return accepted();
    }
}
