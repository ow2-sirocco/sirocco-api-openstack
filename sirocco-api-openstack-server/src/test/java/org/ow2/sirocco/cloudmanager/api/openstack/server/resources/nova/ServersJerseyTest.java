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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.easymock.EasyMock;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.filter.KeystoneFilter;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Server;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerForCreate;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.QueryParams;
import org.ow2.sirocco.cloudmanager.core.api.QueryResult;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServersJerseyTest extends JerseyTest {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ServersJerseyTest.class);

    // TODO : Do not use static stuff...
    static IMachineManager service = EasyMock.createMock(IMachineManager.class);

    @Override
    protected Application configure() {
        System.out.println("Configure app");

        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(JacksonConfigurator.class);
        classes.add(JacksonJsonProvider.class);
        //classes.add(KeystoneFilter.class);
        //classes.add(JacksonFeature.class);
        classes.add(Servers.class);

        LoggingFilter filter = new LoggingFilter(Logger.getLogger(ServersJerseyTest.class.getName()), true);

        //classes.add(LoggingFilter.class);


        ResourceConfig app = new ResourceConfig(classes);
        Servers servers = new Servers();

        // inject mock resource
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(service).to(IMachineManager.class);
            }
        };
        app.registerInstances(binder, filter);
        return app;
    }

    public void tearDown() throws Exception {
        super.tearDown();
        System.out.println("Reset mocks...");
        super.tearDown();
        EasyMock.reset(this.service);
    }

    @Test
    public void testGetServers() throws Exception {

        Machine item;
        List<Machine> list = new ArrayList<Machine>();
        for (int i = 0; i < 3; i++) {
            item = new Machine();
            item.setId(i);
            item.setName("foobar-" + i);
            item.setState(Machine.State.STARTED);
            list.add(item);
        }

        QueryResult<Machine> result = new QueryResult<Machine>(list.size(), list);
        result.setItems(list);

        QueryParams query = EasyMock.createMock(QueryParams.class);
        EasyMock.expect(this.service.getMachines((QueryParams) null)).andReturn(result).once();
        EasyMock.replay(this.service);

        Response response = this.target().path("/v2/1234567/servers").request(MediaType.APPLICATION_JSON_TYPE).get();
        EasyMock.verify(this.service);
        LOGGER.info("Response status %s", response.getStatus());
        assertEquals(200, response.getStatus());
        System.out.println(response.readEntity(String.class));

        // TODO : Check JSON
    }


    @Test
    public void testGetServer() throws Exception {

        Machine item = new Machine();
        item.setId(123);
        item.setName("foobar");
        item.setState(Machine.State.STARTED);

        EasyMock.expect(this.service.getMachineById("123")).andReturn(item).once();
        EasyMock.replay(this.service);

        Response response = this.target().path("/v2/1234567/servers/123").request(MediaType.APPLICATION_JSON_TYPE).get();
        System.out.println("OUT : " + response.readEntity(String.class));

        EasyMock.verify(this.service);

        // TODO : Check JSON
    }

    @Test
    public void testCreateServer() {

        ServerForCreate server = new ServerForCreate();
        server.setName("CreateMe");
        server.setAdminPass("foobar");
        server.getSecurityGroups().add(new ServerForCreate.SecurityGroup("mysecgroup"));

        //Server server = new Server();

        //Response response = this.target().path("/v2/1234567/servers").request(MediaType.APPLICATION_JSON_TYPE).put(Entity.entity(server, MediaType.APPLICATION_JSON_TYPE));

        WebTarget target = this.target();
        target.register(JacksonConfigurator.class);
        target.register(JacksonJsonProvider.class);
        target.register(org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Servers.class);
        target.register(LoggingFilter.class);

        Response response = target.path("/v2/1234567/servers").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(server, MediaType.APPLICATION_JSON_TYPE));

        System.out.println(response.getStatus());
        //assertTrue(response.getStatus() == Response.Status.CREATED.getStatusCode());

    }
}
