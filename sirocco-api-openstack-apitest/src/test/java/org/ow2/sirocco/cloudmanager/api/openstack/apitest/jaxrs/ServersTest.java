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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JAXRSBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
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

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class ServersTest extends JAXRSBasedTest {

    static String CREATE_SERVER_PATTERN = "{\n" +
            "    \"server\": {\n" +
            "        \"flavorRef\": \"$FLAVOR$\",\n" +
            "        \"imageRef\": \"$IMAGE$\",\n" +
            "        \"metadata\": {\n" +
            "            \"foo\": \"bar\"\n" +
            "        },\n" +
            "        \"name\": \"$NAME$\",\n" +
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
        System.out.println(response.readEntity(String.class));
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
        System.out.println(response.readEntity(String.class));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
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
        System.out.println(response.readEntity(String.class));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testSingleMachineDetails() throws CloudProviderException {
        Machine machine = createMachine("mymachine", "myimage", 1, 512, null);
        Response response = target(BASE_URL + tenantName + "/servers/" + machine.getUuid()).request(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String out = response.readEntity(String.class);
        LOG.info(out);
    }

    @Ignore
    @Test
    @RunAsClient
    @InSequence(1000)
    public void testSingleElement(@ArquillianResource URL url) throws Exception {

        // the server will extracct the tenant from the incoming request using an interceptor. This will create an identitycontext
        Response response = target(path(url, "/openstack/v2/" + tenantName + "/servers")).request(MediaType.APPLICATION_JSON_TYPE).get();
        System.out.println(response.readEntity(String.class));
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

        Response response = target(BASE_URL + tenantName + "/servers").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(CREATE_SERVER_PATTERN.replaceAll("$FLAVOR$", config.getUuid()).replaceAll("$IMAGE$", img.getUuid()).replaceAll("$NAME$", name)));
        assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
        System.out.println(response.readEntity(String.class));

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
        LOG.info("---");
        Machine machine = createMachine("foobar", "baz", 1, 512, null);
        LOG.info("Machine ID " + machine.getUuid());

        Response response = target(BASE_URL + tenantName + "/servers/" + machine.getUuid()).request().delete();
        assertEquals("Bad unknown delete return code", Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // wait for the delete job to complete...
        // TODO

        assertEquals(0, machineManager.getMachines().getItems().size());
    }

}
