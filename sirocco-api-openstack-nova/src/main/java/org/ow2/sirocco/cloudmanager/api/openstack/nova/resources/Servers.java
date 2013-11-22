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

import org.ow2.sirocco.cloudmanager.api.openstack.nova.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerForCreate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Servers resources. Documentation is available at <a href="http://api.openstack.org/api-ref-compute.html#compute_servers">#compute_servers</a>
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(value = Constants.SERVERS_PATH)
public interface Servers {

    /**
     * Lists IDs, names, and links for all servers.
     *
     * @return
     */
    @GET
    Response list();

    /**
     * Creates a new server
     *
     * @return HTTP 202 if created.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(ServerForCreate server);

    /**
     * Lists details for all servers.
     *
     * @return
     */
    @GET
    @Path("/detail")
    Response details();

    /**
     * Get details for a specified server
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{server_id}")
    Response details(@PathParam("server_id") String id);

    /**
     * Updates the editable attributes of the specified server.
     *
     * @param id
     * @return the updated server with HTTP code 200.
     */
    @PUT
    @Path("/{server_id}")
    Response update(@PathParam("server_id") String id);

    /**
     * Deletes a specified server.
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/{server_id}")
    Response delete(@PathParam("server_id")String id);


}
