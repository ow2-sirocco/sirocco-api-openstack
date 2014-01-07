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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.jclouds;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jclouds.collect.PagedIterable;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.v2_0.domain.Resource;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.model.Access;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test the server resource operations (cf http://api.openstack.org/api-ref-compute.html#compute_servers)
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class ServersTest extends JcloudsBasedTest {

    private static Logger LOG = LoggerFactory.getLogger(ServersTest.class);

    @Inject
    protected Access access;

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    @Test
    public void testCreateSingleServer() throws CloudProviderException, IOException {
        LOG.info("Creating server");

        String name = UUID.randomUUID().toString();
        MachineImage image = createImage("image-testCreateSingleServer");
        MachineConfiguration machineConfiguration = createMachineConfiguration("config-testCreateSingleServer", 1, 512, null);

        ServerCreated server = nova.getApi().getServerApiForZone(getZone()).create(name, image.getUuid(), machineConfiguration.getUuid());

        assertNotNull(server);
        checkCreate(server);
        // validates that the server we have back is registered in sirocco
        assertNotNull(machineManager.getMachineByUuid(server.getId()));
    }

    /**
     * Create a server from the Sirocco API then delete it from the jclouds client
     */
    @Test
    public void testDeleteServer() throws CloudProviderException {
        LOG.info("Delete server test");

        Machine machine = createMachine("testDeleteJclouds", "testDeleteJcloudsImage", 1, 512, null, false);
        nova.getApi().getServerApiForZone(getZone()).delete(machine.getUuid());

        // wait for the server to be in deleted mode
        try {
            waitMachineState(machineManager.getMachineByUuid(machine.getUuid()), Machine.State.DELETED, timeout(30));
        } catch (Exception e) {
            fail("Can not get the machine in deleted state after timeout..., state is " + machineManager.getMachineByUuid(machine.getUuid()).getState());
        }
        assertTrue((machineManager.getMachineByUuid(machine.getUuid()) == null) || (machineManager.getMachineByUuid(machine.getUuid()).getState().equals(Machine.State.DELETED)));
    }

    @Ignore
    protected boolean checkServer(Server server) {
        checkResource(server);
        assertFalse(server.getAddresses().isEmpty());
        return true;
    }

    @Ignore
    protected void checkCreate(ServerCreated server) {
        checkResource(server);
    }

    @Test
    public void testSingleElement() throws CloudProviderException {
        LOG.info("Test get server from ID");

        // wait for machine to be started to get the created field
        Machine machine = createMachine("single", "imagesingle", 1, 512, null, true);
        assertNotNull(machine);

        Server server = nova.getApi().getServerApiForZone(getZone()).get(machine.getUuid());
        assertNotNull(server);
        assertEquals(machine.getUuid(), server.getUuid());
        assertTrue(checkServer(server));
    }

    @Test
    public void testList() throws CloudProviderException {
        LOG.info("Test get servers list");

        int size = 1;
        Machine machine = createMachine("list", "imageslist", 1, 512, null, true);

        PagedIterable<? extends Resource> result = nova.getApi().getServerApiForZone(getZone()).list();
        assertEquals(size, result.concat().size());
        assertTrue(result.concat().allMatch(new Predicate<Resource>() {
            @Override
            public boolean apply(Resource input) {
                return checkResource(input);
            }
        }));
    }

    @Test
    public void testListDetails() throws CloudProviderException {
        LOG.info("Test get list details");
        int size = 1;
        Machine machine = createMachine("details", "imagesdetails", 1, 512, null, true);

        PagedIterable<? extends Server> result = nova.getApi().getServerApiForZone(getZone()).listInDetail();
        assertEquals(size, result.concat().size());
        assertTrue(result.concat().allMatch(new Predicate<Server>() {
            @Override
            public boolean apply(Server input) {
                return checkServer(input);
            }
        }));
    }

    @Test
    public void testUpdateName() throws Exception {
        String newName = "testUpdatedName";
        Machine machine = createMachine("testUpdateName", "imagesdetails", 1, 512, null, true);
        nova.getApi().getServerApiForZone(getZone()).rename(machine.getUuid(), newName);

        // wait some time since the operation is async
        int counter = timeout(60);
        while (true) {
            LOG.info("Waiting for machine name to be updated...");
            machine = getUpdatedMachine(machine);
            if (machine.getName().equals(newName)) {
                LOG.info("Valid machine name");
                break;
            }
            Thread.sleep(1000);
            if (counter-- == 0) {
                throw new Exception("Machine name update time out");
            }
        }
        assertEquals(newName, getUpdatedMachine(machine));
    }

    /**
     * Defined in Openstack documentation but not in jclouds nor sirocco api
     */
    @Test
    public void testUpdateIPV4() {
        fail("Not supported by JCLOUDS");
    }

    /**
     * Defined in Openstack documentation but not in jclouds nor sirocco api
     */
    @Test
    public void testUpdateIPV6() {
        fail("Not supported by JCLOUDS");
    }
}
