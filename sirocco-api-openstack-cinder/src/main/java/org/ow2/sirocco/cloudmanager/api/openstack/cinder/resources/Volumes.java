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

package org.ow2.sirocco.cloudmanager.api.openstack.cinder.resources;

import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.VolumeForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.VolumeForUpdate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Volumes resources
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path("/v2/​{tenant_id}​/volumes")
public interface Volumes {

    /**
     * Creates a volume
     *
     * @param volume
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(VolumeForCreate volume);

    /**
     * Lists summary information for all Cinder volumes that are accessible to the tenant who submits the request.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Lists detailed information for all Cinder volumes that are accessible to the tenant who submits the request.
     *
     * @return
     */
    @GET
    @Path("/detail")
    @Produces(MediaType.APPLICATION_JSON)
    Response details();

    /**
     * Shows information about a specified volume.
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{volume_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("volume_id") String id);

    /**
     * Updates a volume
     *
     * @param id
     * @param volume
     * @return
     */
    @PUT
    @Path("/{volume_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response update(@PathParam("volume_id") String id, VolumeForUpdate volume);

    /**
     * Deletes a specified volume.
     *
     * @param id
     * @return 202 if deleted
     */
    @DELETE
    @Path("/{volume_id}")
    Response delete(@PathParam("volume_id") String id);

}
