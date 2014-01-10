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
package org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.floatingips;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Floating IP extension support. Actions are also available in Server Actions:
 *
 * - {@link org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions.extensions.serveradmin.AddFloatingIPAddressAction}
 * - {@link org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions.extensions.serveradmin.RemoveFloatingIPAddressAction}
 *
 * Documentation is available at <a href="http://api.openstack.org/api-ref-compute-ext.html#ext-os-floating-ips">http://api.openstack.org/api-ref-compute-ext.html#ext-os-floating-ips</a>
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.Nova.TENANT_PATH_TEMPLATE + "/os-floating-ips")
public interface FloatingIPs {

    /**
     * Lists floating IP addresses associated with the tenant or account
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Shows information for a specified floating IP address.
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("id") String id);

    /**
     * Allocates a new floating IP address to a tenant or account.
     * TODO need <code>{"pool": "nova"}</code> as input (map)
     *
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response create();

    /**
     * Deallocates the floating IP address associated with floating_IP_address_ID.
     *
     * @return
     */
    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") String id);


}
