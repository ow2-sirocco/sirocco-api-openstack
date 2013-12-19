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

package org.ow2.sirocco.cloudmanager.api.openstack.neutron.resources;

import org.ow2.sirocco.cloudmanager.api.openstack.neutron.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.PortForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.PortForUpdate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Ports resource. Documentation available at http://api.openstack.org/api-ref-networking.html#ports
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.BASE_PATH + "/ports")
public interface Ports {

    /**
     * Get the ports list
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Create a port
     *
     * @param port
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response create(PortForCreate port);

    /**
     * Get details of a port
     *
     * @param id
     * @return
     */
    @Path("/{port_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("port_id") String id);

    /**
     * Update a port
     *
     * @param id
     * @return
     */
    @Path("/{port_id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response update(@PathParam("port_id") String id, PortForUpdate portForUpdate);

    /**
     * Delete a port
     *
     * @param id
     * @return HTTP 204 if OK.
     */
    @Path("/{port_id}")
    @DELETE
    Response delete(@PathParam("port_id") String id);
}
