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
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions.AbstractAction;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class StartServerAction extends AbstractAction {

    private static Logger LOG = LoggerFactory.getLogger(StartServerAction.class);

    @Inject
    private IMachineManager machineManager;

    public static final String ACTION = "os-start";

    @Override
    public String getName() {
        return ACTION;
    }

    @Override
    public Response invoke(String serverId, JsonNode payload) {
        LOG.info("Starting server " + serverId);
        try {
            machineManager.startMachine(serverId);
            return accepted();
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("server", serverId, rnfe);
        } catch (CloudProviderException e) {
            return serverFault(e);
        }
    }
}
