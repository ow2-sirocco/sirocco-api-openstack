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

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Images endpoints for Nova.
 *
 * Documentation is at http://api.openstack.org/api-ref-compute.html#compute_images.
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(value = Constants.Nova.IMAGES_PATH)
public interface Images {

    /**
     * Lists IDs, names, and links for available images.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Lists all details for available images.
     *
     * @return
     */
    @GET
    @Path("/detail")
    @Produces(MediaType.APPLICATION_JSON)
    Response details();

    /**
     * Gets details for a specified image.
     *
     * @param imageId
     * @return
     */
    @GET
    @Path("/{image_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response details(@PathParam("image_id") String imageId);

    /**
     * Deletes a specified image.
     *
     * @param imageid
     * @return
     */
    @DELETE
    @Path("/{image_id}")
    Response delete(@PathParam("image_id") String imageid);

}
