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

import com.google.common.collect.Lists;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.easymock.EasyMock;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.QueryResult;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineNetworkInterface;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineNetworkInterfaceAddress;
import org.ow2.sirocco.cloudmanager.model.cimi.Network;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServerAddressJerseyTest extends JerseyTest {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ServerAddressJerseyTest.class);

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
        classes.add(ServerAddresses.class);

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
    public void testGetAddresses() throws Exception {

        String id = "123456789";

        List<MachineNetworkInterface> interfaces = Lists.newArrayList();

        MachineNetworkInterface itf1 = new MachineNetworkInterface();
        itf1.setMacAddress("70:cd:60:ff:fe:ce:e9:30");
        itf1.setNetworkType(Network.Type.PRIVATE);
        List<MachineNetworkInterfaceAddress> addrs = Lists.newArrayList();

        MachineNetworkInterfaceAddress m = new MachineNetworkInterfaceAddress();
        org.ow2.sirocco.cloudmanager.model.cimi.Address a = new org.ow2.sirocco.cloudmanager.model.cimi.Address();
        a.setIp("192.168.0.1");
        a.setProtocol("4");
        m.setAddress(a);
        addrs.add(m);

        MachineNetworkInterfaceAddress mm = new MachineNetworkInterfaceAddress();
        org.ow2.sirocco.cloudmanager.model.cimi.Address aa = new org.ow2.sirocco.cloudmanager.model.cimi.Address();
        aa.setIp("192.168.0.2");
        aa.setProtocol("4");
        mm.setAddress(aa);
        addrs.add(mm);

        itf1.setAddresses(addrs);
        interfaces.add(itf1);

        QueryResult<MachineNetworkInterface> result = new QueryResult<MachineNetworkInterface>(interfaces.size(), interfaces);
        result.setItems(interfaces);

        EasyMock.expect(this.service.getMachineNetworkInterfaces(id)).andReturn(result).once();
        EasyMock.replay(this.service);

        Response response = this.target().path("/v2/1234567/servers/" + id + "/ips").request(MediaType.APPLICATION_JSON_TYPE).get();
        EasyMock.verify(this.service);
        LOGGER.info("Response status %s", response.getStatus());
        assertEquals(200, response.getStatus());
        System.out.println(response.readEntity(String.class));

    }

    @Test
    public void testGetAddressesByNetwork() throws CloudProviderException {
        String id = "123456789";

        List<MachineNetworkInterface> interfaces = Lists.newArrayList();

        MachineNetworkInterface itf1 = new MachineNetworkInterface();
        itf1.setMacAddress("70:cd:60:ff:fe:ce:e9:30");
        itf1.setNetworkType(Network.Type.PRIVATE);
        List<MachineNetworkInterfaceAddress> addrs = Lists.newArrayList();

        MachineNetworkInterfaceAddress m = new MachineNetworkInterfaceAddress();
        org.ow2.sirocco.cloudmanager.model.cimi.Address a = new org.ow2.sirocco.cloudmanager.model.cimi.Address();
        a.setIp("192.168.0.1");
        a.setProtocol("4");
        m.setAddress(a);
        addrs.add(m);

        MachineNetworkInterfaceAddress mm = new MachineNetworkInterfaceAddress();
        org.ow2.sirocco.cloudmanager.model.cimi.Address aa = new org.ow2.sirocco.cloudmanager.model.cimi.Address();
        aa.setIp("192.168.0.2");
        aa.setProtocol("4");
        mm.setAddress(aa);
        addrs.add(mm);

        itf1.setAddresses(addrs);
        interfaces.add(itf1);

        QueryResult<MachineNetworkInterface> result = new QueryResult<MachineNetworkInterface>(interfaces.size(), interfaces);
        result.setItems(interfaces);

        EasyMock.expect(this.service.getMachineNetworkInterfaces(id)).andReturn(result).anyTimes();
        EasyMock.replay(this.service);

        Response response = this.target().path("/v2/1234567/servers/" + id + "/ips/" + itf1.getNetworkType().toString().toLowerCase()).request(MediaType.APPLICATION_JSON_TYPE).get();
        LOGGER.info("Response status %s", response.getStatus());
        assertEquals(200, response.getStatus());
        System.out.println(response.readEntity(String.class));

        response = this.target().path("/v2/1234567/servers/" + id + "/ips/foobar").request(MediaType.APPLICATION_JSON_TYPE).get();
        LOGGER.info("Response status %s", response.getStatus());
        assertEquals(404, response.getStatus());
        System.out.println(response.readEntity(String.class));

        EasyMock.verify(this.service);
    }
}
