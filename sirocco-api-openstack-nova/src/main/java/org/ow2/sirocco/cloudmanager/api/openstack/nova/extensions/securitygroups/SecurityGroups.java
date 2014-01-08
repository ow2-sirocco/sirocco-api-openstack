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
package org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroupForCreate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The security groups API (cf http://api.openstack.org/api-ref-compute.html#os-security-groups)
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.Nova.TENANT_PATH_TEMPLATE + "/os-security-groups")
public interface SecurityGroups {

    /**
     * Lists security groups.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Creates a security group.
     *
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response create(SecurityGroupForCreate securityGroupForCreate);

    /**
     * Lists security groups for a specified server.
     *
     * @param serverId
     * @return
     */
    @GET
    @Path("/servers/​{server_id}​/os-security-groups")
    @Produces(MediaType.APPLICATION_JSON)
    Response listForServer(@PathParam("server_id") String serverId);

    /**
     * Shows information for a specified security group.
     *
     * @param id
     * @return
     */

    @GET
    @Path("/{security_group_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("security_group_id") String id);

    /**
     * Deletes a specified security group.
     *
     * @param id
     * @return
     */

    @DELETE
    @Path("/{security_group_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response delete(@PathParam("security_group_id") String id);
}
