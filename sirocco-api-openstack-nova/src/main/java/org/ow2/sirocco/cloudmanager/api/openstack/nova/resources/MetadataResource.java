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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.resources;

import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Metadata;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Base interface for metadata resources (image, server, ...)
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface MetadataResource {

    /**
     * Lists metadata for the specified resource.
     *
     * @return HTTP 200 or 203 if OK with metadata as JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response get();

    /**
     * Updates metadata items by key for the specified resource.
     * Replaces items that match the specified keys and does not modify items not specified in the request.
     *
     * @return HTTP 200 with the updated metadata as JSON.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response update(Metadata metadata);

    /**
     * Replaces items that match the specified keys and removes items not specified in the request.
     *
     * @return HTTP 200 if OK with updated metadata as JSON.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response set(Metadata metadata);

    /**
     * Gets a metadata item by key for the specified resource.
     *
     * @param key
     * @return HTTP 200 or 203 with metadata element as JSON.
     */
    @GET
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("key") String key);

    /**
     * Sets a metadata item by key for the specified resource.
     *
     * @param metadata
     * @return HTTP 200 if OK with metdata as JSON.
     */
    @PUT
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response setValue(Metadata metadata);

    /**
     * Deletes a metadata item by key for the specified resource.
     *
     * @param key
     * @return HTTP 204 if deleted without any body.
     */
    @DELETE
    @Path("/{key}")
    Response delete(@PathParam("key") String key);

}
