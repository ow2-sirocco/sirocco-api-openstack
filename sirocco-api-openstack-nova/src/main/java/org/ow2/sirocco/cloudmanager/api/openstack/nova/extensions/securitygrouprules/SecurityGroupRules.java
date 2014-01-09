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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygrouprules;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygrouprules.model.SecurityGroupRuleForCreate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Security group default rules. cf http://api.openstack.org/api-ref-compute.html#os-security-group-default-rules
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.Nova.TENANT_PATH_TEMPLATE + "/os-security-group-rules")
public interface SecurityGroupRules {

    /**
     * Lists security group rules.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response list();

    /**
     * Add a rule to a security group
     *
     * @return the created SecurityGroupRule
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response create(SecurityGroupRuleForCreate rule);

    /**
     * Shows information for a specified security group rule.
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{security_group_rule_id}​")
    @Produces(MediaType.APPLICATION_JSON)
    Response get(@PathParam("security_group_rule_id") String id);

    /**
     * Delete a rule
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/{security_group_rule_id}​")
    Response delete(@PathParam("security_group_rule_id") String id);
}
