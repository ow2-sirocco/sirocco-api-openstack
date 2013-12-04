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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions;

import org.codehaus.jackson.JsonNode;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerAction;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Action;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.badRequest;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class RebootAction extends AbstractAction implements Action {

    private static Logger LOG = LoggerFactory.getLogger(RebootAction.class);

    @Inject
    private IMachineManager machineManager;

    public static final String ACTION = "reboot";

    @Override
    public String getName() {
        return ACTION;
    }

    @Override
    public Response invoke(String serverId, JsonNode payload) {
        try {
            ServerAction.Reboot reboot = getBean(payload, ServerAction.Reboot.class);
            machineManager.restartMachine(serverId, reboot.getType() != null && reboot.getType().equalsIgnoreCase("hard") ? true : false);
            return accepted(null);
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("server", serverId, rnfe);
        } catch (CloudProviderException e) {
            return serverFault(e);
        } catch (IOException e) {
            LOG.warn("Parse error", e);
            return badRequest("Server", ACTION);
        }
    }
}
