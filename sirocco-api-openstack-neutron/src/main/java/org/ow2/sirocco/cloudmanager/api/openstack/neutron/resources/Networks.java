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
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.NetworkForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.NetworkForUpdate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Networks resource. Details at http://api.openstack.org/api-ref-networking.html#networks
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.BASE_PATH + "/networks")
public interface Networks {

    /**
     * Get the network list
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Create a network
     *
     * @param network
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response create(NetworkForCreate network);

    /**
     * Create multiple networks in a single call.
     * FIXME : Path is the same as for create. This cause conflicts on path with distinct types.
     * @param networks
     * @return
     */
    @Path("/todo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response createMultiple(Networks networks);

    /**
     * Get details of a network
     *
     * @param id
     * @return
     */
    @Path("/{network_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("network_id") String id);

    /**
     * Update a network
     * @param id
     * @return
     */
    @Path("/{network_id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response update(@PathParam("network_id") String id, NetworkForUpdate networkForUpdate);

    /**
     * Delete a network
     *
     * @param id
     * @return HTTP 204 if OK.
     */
    @Path("/{network_id}")
    @DELETE
    Response delete(@PathParam("network_id") String id);


}
