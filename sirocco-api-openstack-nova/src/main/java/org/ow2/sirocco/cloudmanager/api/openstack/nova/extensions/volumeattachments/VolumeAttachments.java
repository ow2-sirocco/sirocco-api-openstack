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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.volumeattachments;

import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.volumeattachments.model.VolumeAttachmentForCreate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path("/v2/​{tenant_id}​/servers/​{server_id}/os-volume_attachments")
public interface VolumeAttachments {

    /**
     * Attach a volume to a server
     *
     * @param volumeAttachmentForCreate
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(VolumeAttachmentForCreate volumeAttachmentForCreate);

    /**
     * Get all the attached volumes for the given server
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Delete a volume attachment
     *
     * @param id
     * @return
     */
    @Path("/{attachment_id}")
    @DELETE
    Response delete(@PathParam("attachment_id") String id);

    /**
     * Get a volume attachment
     * @param id
     * @return
     */
    @Path("/{attachment_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("attachment_id") String id);

}
