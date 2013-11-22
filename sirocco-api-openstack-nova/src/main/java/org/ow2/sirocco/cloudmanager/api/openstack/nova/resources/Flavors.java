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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Lists available flavors and gets details for a specified flavor.
 * A flavor is a hardware configuration for a server. Each flavor is a unique combination of disk space and memory capacity.
 *
 * Documentation at <a href="http://api.openstack.org/api-ref-compute.html#compute_flavors">#compute_flavors</a>
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.Nova.FLAVORS_PATH)
public interface Flavors {

    /**
     * Lists IDs, names, and links for available flavors.
     *
     * @return
     */
    @GET
    @Path("/")
    Response list();

    /**
     * Lists all details for available flavors.
     *
     * @return
     */
    @GET
    @Path("/detail")
    Response details();

    /**
     * Gets details for a specified flavor.
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{flavor_id}")
    Response details(@PathParam("flavor_id") String id);
}
