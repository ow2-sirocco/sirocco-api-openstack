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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Server Addresses resource. Lists addresses for a specified server or a specified server and network.
 * Documentation available at http://api.openstack.org/api-ref-compute.html#compute_server-addresses
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.SERVER_ADDRESSES_PATH)
public interface ServerAddresses {

    /**
     * Lists networks and addresses for a specified tenant and server.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Lists addresses for a specified tenant, server, and network.
     *
     * @return
     */
    @GET
    @Path("/{network_label}")
    @Produces(MediaType.APPLICATION_JSON)
    Response list(@PathParam("network_label") String network);


}
