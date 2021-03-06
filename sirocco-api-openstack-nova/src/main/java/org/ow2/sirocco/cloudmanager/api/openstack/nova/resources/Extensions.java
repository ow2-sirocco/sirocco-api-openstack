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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Lists all available extensions and gets details for a specified extension.
 * Extensions introduce features and vendor-specific functionality in the API without requiring a version change.
 *
 * Documentation is at http://api.openstack.org/api-ref-compute.html#compute_extensions
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.Nova.EXTENSIONS_PATH)
public interface Extensions {

    /**
     * Lists all available extensions.
     *
     * @return HTTP 200 || 203 with extensions as JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response get();

    /**
     * Gets details about the specified extension.
     *
     * @param alias
     * @return HTTP 200 || 203 with extension as JSON
     */
    @GET
    @Path("/{alias}")
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("alias") String alias);

}
