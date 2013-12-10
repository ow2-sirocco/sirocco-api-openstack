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

package org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.resources;

import org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.model.Access;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.model.Tenant;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.model.authentication.UsernamePassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A fake token resource which is used by tests.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path("/tokens")
@Produces(MediaType.APPLICATION_JSON)
public class Tokens {

    private static Logger LOG = LoggerFactory.getLogger(Tokens.class);

    private Access access;

    public Tokens(Access access) {
        this.access = access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    /**
     * Authenticate user. Returns the user and endpoints informations.
     *
     * @return
     */
    @POST
    public Response auth(UsernamePassword usernamePassword) {
        LOG.debug("USER " + usernamePassword.getPasswordCredentials().getUsername());
        LOG.debug("PASS " + usernamePassword.getPasswordCredentials().getPassword());
        LOG.debug("TENANT " + usernamePassword.getTenantName());
        LOG.debug("TENANT ID " + usernamePassword.getTenantId());

        if (usernamePassword.getPasswordCredentials() == null) {
            LOG.warn("Credentials are null");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (usernamePassword.getPasswordCredentials().getUsername() == null || usernamePassword.getPasswordCredentials().getPassword() == null) {
            LOG.warn("Username or password are null");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String tenant = usernamePassword.getTenantName();
        if (tenant != null) {
            access.getToken().setTenant(new Tenant(tenant));
        }

        return Response.ok(access).build();
    }
}
