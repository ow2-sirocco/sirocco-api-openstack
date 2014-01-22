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

package org.ow2.sirocco.cloudmanager.api.openstack.keystone.server;

import com.google.common.collect.Lists;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.model.Access;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.model.Tenant;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.model.Token;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Calendar;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class ApplicationTest {

    @Test
    @Ignore
    public void testPostToken() throws IOException, InterruptedException {

        Application application = new Application(getAccess());
        URI uri = UriBuilder.fromUri("http://localhost/").port(5000).build();
        HttpServer server = HttpServer.create(new InetSocketAddress(uri.getPort()), 0);
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(application, HttpHandler.class);
        server.createContext(uri.getPath(), handler);
        server.start();


        Client client = ClientBuilder.newClient();
        assertEquals(404, client.target("http://localhost:5000/tokens/123").request().get().getStatus());

        String payload = "{\n" +
                "\"auth\":{\n" +
                "\"passwordCredentials\":{\n" +
                "\"username\":\"sirocco\",\n" +
                "\"password\":\"mypass\"\n" +
                "},\n" +
                "\"tenantName\":\"sirocco-tenant\"\n" +
                "}\n" +
                "}";
        Response response = client.target("http://localhost:5000/keystone/tokens").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(payload));


        System.out.println(response.readEntity(String.class));

        server.stop(0);

        assertEquals(200, response.getStatus());

    }

    protected Access getAccess() {
        String tenant = "sirocco-tenant";
        Access.User user = new Access.User();
        user.setName("Sirocco Test");
        user.setUsername("sirocco");

        List<Access.User.Role> roles = Lists.newArrayList();
        Access.User.Role role = new Access.User.Role();
        role.setId("123");
        role.setName("sirocco-test");
        roles.add(role);
        user.setRoles(roles);

        Token token = new Token();
        token.setId("ab48a9efdfedb23ty3494");
        token.setTenant(new Tenant(tenant));
        token.setIssued_at(Calendar.getInstance());

        Calendar expires = Calendar.getInstance();
        expires.add(Calendar.DAY_OF_MONTH, 1);
        token.setExpires(expires);

        Access access = new Access();
        access.setToken(token);
        access.setUser(user);
        List<Access.Service> catalog = Lists.newArrayList();

        Access.Service computeEndpoint = new Access.Service();
        computeEndpoint.setName("Sirocco Compute");
        computeEndpoint.setType("compute");
        Access.Service.Endpoint compute = new Access.Service.Endpoint();
        compute.setAdminURL("http://locahost:30303/admin");
        compute.setInternalURL("http://locahost:30303/internal");
        compute.setPublicURL("http://locahost:30303/internal");
        compute.setRegion("region");

        computeEndpoint.setEndpoints(Lists.newArrayList(compute));
        catalog.add(computeEndpoint);
        access.setServiceCatalog(catalog);
        return access;
    }

}
