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

/**
 * Create a server with the openstack API
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
        String name = UUID.randomUUID().toString();
        MachineImage image = createImage("image-testCreateSingleServer");
        MachineConfiguration machineConfiguration = createMachineConfiguration("config-testCreateSingleServer", 1, 512, null);

        ServerCreated server = nova.getApi().getServerApiForZone(getZone()).create(name, image.getUuid(), machineConfiguration.getUuid());

        assertNotNull(server);
        checkCreate(server);
        // validates that the server we have back is registered in sirocco
        assertNotNull(machineManager.getMachineByUuid(server.getId()));
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
        Machine machine = createMachine("single", "imagesingle", 1, 512, null);
        assertNotNull(machine);

        Server server = nova.getApi().getServerApiForZone(getZone()).get(machine.getUuid());
        assertNotNull(server);
        assertEquals(machine.getUuid(), server.getUuid());
        assertTrue(checkServer(server));
    }

    @Test
    public void testList() throws CloudProviderException {
        int size = 1;
        // TODO : Create N servers
        Machine machine = createMachine("list", "imageslist", 1, 512, null);

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
        int size = 1;
        // TODO : Create N servers
        Machine machine = createMachine("details", "imagesdetails", 1, 512, null);

        PagedIterable<? extends Server> result = nova.getApi().getServerApiForZone(getZone()).listInDetail();
        assertEquals(size, result.concat().size());
        assertTrue(result.concat().allMatch(new Predicate<Server>() {
            @Override
            public boolean apply(Server input) {
                return checkServer(input);
            }
        }));
    }

}
