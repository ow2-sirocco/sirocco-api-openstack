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
package org.ow2.sirocco.cloudmanager.api.openstack.nova.resources;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Performs actions for a specified server, including change administrator password, reboot, rebuild, resize, and create image from server.
 * Documentation is available at http://api.openstack.org/api-ref-compute.html#compute_server-actions
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.Nova.SERVERS_ACTION_PATH)
public interface ServerActions {

    /**
     * Generic server action. The payload will define the action to invoke.
     * Up to the implementation to read the input stream and dispatch to an action handler.
     *
     * @return HTTP 202 with JSON payload depending on the action type.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response action(InputStream stream);

}
