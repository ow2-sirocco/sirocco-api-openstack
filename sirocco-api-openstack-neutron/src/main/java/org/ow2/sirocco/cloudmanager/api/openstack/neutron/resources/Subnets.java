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

import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.SubnetForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.SubnetForUpdate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Subnets resource. Documentation is available at http://api.openstack.org/api-ref-networking.html#subnets
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path("/v2.0/subnets")
public interface Subnets {

    /**
     * Get the subnets list
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Create a subnet
     *
     * @param subnet
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response create(SubnetForCreate subnet);

    /**
     * Get details of a subnet
     *
     * @param id
     * @return
     */
    @Path("/{subnet_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("subnet_id") String id);

    /**
     * Update a subnet
     *
     * @param id
     * @return
     */
    @Path("/{subnet_id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response update(@PathParam("subnet_id") String id, SubnetForUpdate subnetForUpdate);

    /**
     * Delete a subnet
     *
     * @param id
     * @return HTTP 204 if OK.
     */
    @Path("/{subnet_id}")
    @DELETE
    Response delete(@PathParam("subnet_id") String id);

}
