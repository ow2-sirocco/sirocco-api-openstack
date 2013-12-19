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

import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.SnapshotForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.SnapshotForUpdate;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Snapshots resources
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.Nova.TENANT_PATH_TEMPLATE + "​/snapshots")
public interface Snapshots {

    /**
     * Creates a snapshot, which is a point-in-time copy of a volume. You can create a new volume from the snapshot.
     *
     * @param snapshot
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(SnapshotForCreate snapshot);

    /**
     * Lists summary information for all Cinder snapshots that are accessible to the tenant who submits the request.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Lists detailed information for all Cinder snapshots that are accessible to the tenant who submits the request.
     *
     * @return
     */
    @GET
    @Path("/detail")
    @Produces(MediaType.APPLICATION_JSON)
    Response details();

    /**
     * Shows information for a specified snapshot.
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{snapshot_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("snapshot_id") String id);

    /**
     * Updates a specified snapshot.
     *
     * @param id
     * @param volume
     * @return
     */
    @PUT
    @Path("/{snapshot_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response update(@PathParam("snapshot_id") String id, SnapshotForUpdate volume);

    /**
     * Deletes a specified snapshot.
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/{snapshot_id}")
    Response delete(@PathParam("snapshot_id") String id);
}
