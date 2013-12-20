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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.jaxrs;

import com.google.common.collect.Lists;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JAXRSBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Servers;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class ServersTest extends JAXRSBasedTest {

    static String CREATE_SERVER_PATTERN = "{\n" +
            "    \"server\": {\n" +
            "        \"flavorRef\": \"FLAVOR\",\n" +
            "        \"imageRef\": \"IMAGE\",\n" +
            "        \"metadata\": {\n" +
            "            \"foo\": \"bar\"\n" +
            "        },\n" +
            "        \"name\": \"NAME\",\n" +
            "        \"personality\": [\n" +
            "            {\n" +
            "                \"contents\": \"Sirocco!\",\n" +
            "                \"path\": \"/etc/banner.txt\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

    private static Logger LOG = LoggerFactory.getLogger(ServersTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIAndMockProvider("sirocco");
    }

    /* LIST */

    @Test
    public void testOneMachineList() {
        Machine machine = null;
        try {
            machine = createMachine("mytestmachine", "image", 1, 1024, null);
        } catch (CloudProviderException e) {
            e.printStackTrace();
            // this should abort this test class...
            fail(e.getMessage());
        }

        // OpenStack API call

        Response response = target(BASE_URL + tenantName + "/servers").request(MediaType.APPLICATION_JSON_TYPE).get();
        Servers servers = readResponse(response, Servers.class);
        assertEquals(1, servers.getServers().size());
        Assert.assertEquals(machine.getName(), servers.getServers().get(0).name);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDetailsOneMachineList() {
        Machine machine = null;
        try {
            machine = createMachine("mytestmachinedetails", "image", 1, 1024, null);
        } catch (CloudProviderException e) {
            e.printStackTrace();
            // this should abort this test class...
            fail(e.getMessage());
        }

        // OpenStack API call

        Response response = target(BASE_URL + tenantName + "/servers/detail").request(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);
        assertEquals(1, servers.getServers().size());
        Assert.assertEquals(machine.getName(), servers.getServers().get(0).name);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        // TODO : details
    }

    @Test
    public void testNMachinesList() {
        int size = 10;
        List<Machine> machines = Lists.newArrayList();
        for(int i = 0; i < size; i++) {
            try {
                machines.add(createMachine("mytestmachine-" + i, "image", 1, 1024, null));
            } catch (CloudProviderException e) {
                e.printStackTrace();
                // this should abort this test class...
                fail(e.getMessage());
            }
        }

        Response response = target(BASE_URL + tenantName + "/servers").request(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);
        assertEquals(machines.size(), servers.getServers().size());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testSingleMachineDetails() throws CloudProviderException {
        Machine machine = createMachine("mymachine", "myimage", 1, 512, null);
        Response response = target(BASE_URL + tenantName + "/servers/" + machine.getUuid()).request(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        // FIXME : Does not work for single server but works for list...
        //Server server = readResponse(response, Server.class);
        String server = readResponse(response, String.class);

        assertNotNull(server);
        LOG.info("" + server);
    }

    @Test
      public void testGetWithNameFilterOne() throws CloudProviderException {
        Machine machine1 = createMachine("mymachine1", "myimage", 1, 512, null);
        Machine machine2 = createMachine("mymachine2", "myimage", 1, 512, null);
        Machine machine3 = createMachine("mymachine3", "myimage", 1, 512, null);

        Response response = target(BASE_URL + tenantName + "/servers").queryParam("name", machine1.getName()).request(MediaType.APPLICATION_JSON_TYPE).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);
        assertEquals(1, servers.getServers().size());
        assertEquals(machine1.getName(), servers.getServers().get(0).name);
    }

    @Test
    public void testGetWithNameFilterNone() throws CloudProviderException {
        Machine machine1 = createMachine("mymachine1", "myimage", 1, 512, null);
        Machine machine2 = createMachine("mymachine2", "myimage", 1, 512, null);
        Machine machine3 = createMachine("mymachine3", "myimage", 1, 512, null);

        Response response = target(BASE_URL + tenantName + "/servers").queryParam("name", "foo").request(MediaType.APPLICATION_JSON_TYPE).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);
        assertEquals(3, servers.getServers().size());
    }

    @Test
    public void testGetWithImageFilterOne() throws CloudProviderException {
        Machine machine1 = createMachine("mymachine1", "myimage1", 1, 512, null);
        Machine machine2 = createMachine("mymachine2", "myimage2", 1, 512, null);
        Machine machine3 = createMachine("mymachine3", "myimage3", 1, 512, null);

        Response response = target(BASE_URL + tenantName + "/servers").queryParam("image", machine1.getImage().getName()).request(MediaType.APPLICATION_JSON_TYPE).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);

        assertEquals(1, servers.getServers().size());
        assertEquals(machine1.getName(), servers.getServers().get(0).name);
    }

    @Test
    public void testGetWithImageFilterNone() throws CloudProviderException {
        Machine machine1 = createMachine("mymachine1", "myimage1", 1, 512, null);
        Machine machine2 = createMachine("mymachine2", "myimage2", 1, 512, null);
        Machine machine3 = createMachine("mymachine3", "myimage3", 1, 512, null);

        Response response = target(BASE_URL + tenantName + "/servers").queryParam("image", "foo").request(MediaType.APPLICATION_JSON_TYPE).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);
        assertNotNull(servers);
        assertNotNull(servers.getServers());
        assertEquals(0, servers.getServers().size());
    }

    @Test
    public void testGetWithFlavorFilterOne() throws CloudProviderException {
        Machine machine1 = createMachine("mymachine1", "myimage1", 1, 512, null);
        Machine machine2 = createMachine("mymachine2", "myimage2", 1, 512, null);
        Machine machine3 = createMachine("mymachine3", "myimage3", 1, 512, null);

        Response response = target(BASE_URL + tenantName + "/servers").queryParam("flavor", machine1.getConfig().getName()).request(MediaType.APPLICATION_JSON_TYPE).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);
        assertEquals(1, servers.getServers().size());
        assertEquals(machine1.getName(), servers.getServers().get(0).name);
    }

    @Test
    public void testGetWithFlavorFilterNone() throws CloudProviderException {
        Machine machine1 = createMachine("mymachine1", "myimage1", 1, 512, null);
        Machine machine2 = createMachine("mymachine2", "myimage2", 1, 512, null);
        Machine machine3 = createMachine("mymachine3", "myimage3", 1, 512, null);

        Response response = target(BASE_URL + tenantName + "/servers").queryParam("flavor", "foo").request(MediaType.APPLICATION_JSON_TYPE).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);
        assertNotNull(servers);
        assertNotNull(servers.getServers());
        assertEquals(0, servers.getServers().size());
    }

    @Test
    public void testGetWithStatusFilterOne() throws CloudProviderException {
        Machine machine1 = createMachine("mymachine1", "myimage1", 1, 512, null);
        Machine machine2 = createMachine("mymachine2", "myimage2", 1, 512, null);
        Machine machine3 = createMachine("mymachine3", "myimage3", 1, 512, null);

        Response response = target(BASE_URL + tenantName + "/servers").queryParam("status", machine1.getState().toString()).request(MediaType.APPLICATION_JSON_TYPE).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);
        assertTrue(servers.getServers().size() >= 1);
    }


    @Test
    public void testGetWithStatusFilterNone() throws CloudProviderException {
        Machine machine1 = createMachine("mymachine1", "myimage1", 1, 512, null);
        Machine machine2 = createMachine("mymachine2", "myimage2", 1, 512, null);
        Machine machine3 = createMachine("mymachine3", "myimage3", 1, 512, null);

        Response response = target(BASE_URL + tenantName + "/servers").queryParam("status", Machine.State.ERROR.toString()).request(MediaType.APPLICATION_JSON_TYPE).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);
        assertNotNull(servers);
        assertNotNull(servers.getServers());
        assertEquals(0, servers.getServers().size());
    }

    @Test
    public void testGetWithHostFilterOne() throws CloudProviderException {
        Machine machine1 = createMachine("mymachine1", "myimage1", 1, 512, null);
        Machine machine2 = createMachine("mymachine2", "myimage2", 1, 512, null);
        Machine machine3 = createMachine("mymachine3", "myimage3", 1, 512, null);
        String hostname = "TODO";

        Response response = target(BASE_URL + tenantName + "/servers").queryParam("host", hostname).request(MediaType.APPLICATION_JSON_TYPE).get();

        fail("Need to define host mapping");
    }

    @Test
    public void testGetWithStatusHostFilterNone() throws CloudProviderException {
        Machine machine1 = createMachine("mymachine1", "myimage1", 1, 512, null);
        Machine machine2 = createMachine("mymachine2", "myimage2", 1, 512, null);
        Machine machine3 = createMachine("mymachine3", "myimage3", 1, 512, null);

        Response response = target(BASE_URL + tenantName + "/servers").queryParam("host", "foo").request(MediaType.APPLICATION_JSON_TYPE).get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Servers servers = readResponse(response, Servers.class);
        assertNotNull(servers);
        assertNotNull(servers.getServers());
        assertEquals(0, servers.getServers().size());
    }

    @Ignore
    @Test
    @RunAsClient
    @InSequence(1000)
    public void testSingleElement(@ArquillianResource URL url) throws Exception {

        // the server will extracct the tenant from the incoming request using an interceptor. This will create an identitycontext
        Response response = target(path(url, "/openstack/v2/" + tenantName + "/servers")).request(MediaType.APPLICATION_JSON_TYPE).get();
        LOG.info(response.readEntity(String.class));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    /* CREATE */

    @Test
    public void testCreateServer() throws CloudProviderException {

        String name = "createServerFromString";
        String image = "testCreateServerImage";
        String flavor = "testCreateServerFlavor";

        // create the MachineConfiguration (flavor)
        MachineConfiguration config = createMachineConfiguration(flavor, 1, 512, null);
        MachineImage img = createImage(image);

        List<Machine> before = this.machineManager.getMachines().getItems();

        Response response = target(BASE_URL + tenantName + "/servers").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(CREATE_SERVER_PATTERN.replaceAll("FLAVOR", config.getUuid()).replaceAll("IMAGE", img.getUuid()).replaceAll("NAME", name)));
        assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
        LOG.info(response.readEntity(String.class));

        List<Machine> after = this.machineManager.getMachines().getItems();

        assertTrue(after.size() > before.size());
    }

    @Test
    public void testCreateServerEmptyPayload() {
        Response response = target(BASE_URL + tenantName + "/servers").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json("{}"));
        assertNotEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
    }

    /* DELETE */

    @Test
    public void testDeleteUnknownServer() {
        Response response = target(BASE_URL + tenantName + "/servers/" + UUID.randomUUID().toString()).request().delete();
        assertEquals("Bad unknown delete return code", Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteServer() throws CloudProviderException {
        Machine machine = createMachine("foobar", "baz", 1, 512, null);
        LOG.info("Machine ID " + machine.getUuid());

        Response response = target(BASE_URL + tenantName + "/servers/" + machine.getUuid()).request().delete();
        assertEquals("Bad unknown delete return code", Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // machine should be in DELETING state
        assertTrue(machineManager.getMachineByUuid(machine.getUuid()) == null || machineManager.getMachineByUuid(machine.getUuid()).getState().equals(Machine.State.DELETING));
    }
}
