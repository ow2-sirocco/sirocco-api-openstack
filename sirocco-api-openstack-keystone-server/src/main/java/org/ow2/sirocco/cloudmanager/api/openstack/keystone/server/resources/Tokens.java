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
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.model.Token;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.model.authentication.UsernamePassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * A fake token resource which is used by tests.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Tokens implements org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.api.resources.Tokens {

    private static Logger LOG = LoggerFactory.getLogger(Tokens.class);

    @Inject
    protected Access access;

    public Response auth(UsernamePassword usernamePassword) {

        System.out.println("<<<<<< ACCESS " + access);

        LOG.info("Keystone token resource : Getting auth request from openstack client");

        System.out.println(usernamePassword);
        System.out.println("TenantID : " + usernamePassword.getTenantId());
        System.out.println("TenantName : " + usernamePassword.getTenantName());

        if (usernamePassword.getPasswordCredentials() == null) {
            LOG.warn("Credentials are null");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOG.info("USER " + usernamePassword.getPasswordCredentials().getUsername());
        LOG.info("PASS " + usernamePassword.getPasswordCredentials().getPassword());
        LOG.info("TENANT " + usernamePassword.getTenantName());
        LOG.info("TENANT ID " + usernamePassword.getTenantId());

        if (usernamePassword.getPasswordCredentials().getUsername() == null || usernamePassword.getPasswordCredentials().getPassword() == null) {
            LOG.warn("Null Username or Password");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Token token = new Token();
        token.setId(UUID.randomUUID().toString());
        token.setIssued_at(Calendar.getInstance());
        Calendar expires = Calendar.getInstance();
        expires.add(Calendar.DAY_OF_MONTH, 1);
        token.setExpires(expires);

        if (access == null) {
            access = new Access();
        }
        access.setToken(token);

        String tenant = usernamePassword.getTenantName();
        if (tenant != null) {
            Tenant t = new Tenant(tenant, "Sirocco test tenant", Boolean.TRUE);
            t.setId("00998873");
            token.setTenant(t);
        } else {

        }

        // User
        Access.User user = new Access.User();
        user.setId("123");
        user.setName("User name");
        user.setUsername(usernamePassword.getPasswordCredentials().getUsername());
        access.setUser(user);

        // Roles
        List<Access.Service> services = new ArrayList<>();
        Access.Service s = new Access.Service();
        s.setName("compute");
        s.setType("compute");
        List<Access.Service.Endpoint> endpoints = new ArrayList<>();
        Access.Service.Endpoint e = new Access.Service.Endpoint();
        e.setAdminURL("http://localhost:8181/sirocco/openstack/v2/");
        e.setInternalURL("http://internal");
        // FIXME : It is not clear if we have to send back the public URL with the tenant or not
        // FIXME : Jclouds seems to require it...
        e.setPublicURL("http://localhost:8181/sirocco/openstack/v2/" + tenant);
        e.setRegion("France");
        e.setTenantName(tenant);

        endpoints.add(e);
        s.setEndpoints(endpoints);
        services.add(s);
        access.setServiceCatalog(services);

        return Response.ok(access).build();
    }
}
