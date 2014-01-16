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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.jclouds.nova;

import com.google.common.collect.ImmutableMap;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class ServerMetadataTest extends JcloudsBasedTest {
    private static Logger LOG = LoggerFactory.getLogger(ServerMetadataTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    @Test
    public void testGetMetadatas() throws CloudProviderException {
        Map<String, String> props = ImmutableMap.of("foo", "bar");
        Machine machine = createMachine("testgetmetas", "testimage", 1, 512, props, true);

        Map<String, String> metadata = serverApi().getMetadata(machine.getUuid());
        assertNotNull(metadata);
        assertEquals(1, metadata.size());
        assertNotNull(metadata.get("foo"));
        assertEquals("bar", metadata.get("bar"));
    }

    @Test
    public void testGetMetadata() throws CloudProviderException {
        Map<String, String> props = ImmutableMap.of("foo", "bar");
        Machine machine = createMachine("testgetmeta", "testimage", 1, 512, props, true);

        String metadata = serverApi().getMetadata(machine.getUuid(), "foo");
        assertNotNull(metadata);
        assertEquals("bar", metadata);
    }

    @Test
    public void testUpdateMetadataEntry() throws CloudProviderException {
        Map<String, String> props = ImmutableMap.of("foo", "bar");
        Machine machine = createMachine("testgetmeta", "testimage", 1, 512, props, true);

        String metadata = serverApi().updateMetadata(machine.getUuid(), "foo", "baz");
        assertNotNull(metadata);
        assertEquals("baz", machineManager.getMachineByUuid(machine.getUuid()).getProperties().get("foo"));
    }

    @Test
    public void testDeleteMetadataEntry() throws CloudProviderException {
        Map<String, String> props = ImmutableMap.of("foo", "bar");
        Machine machine = createMachine("testgetmeta", "testimage", 1, 512, props, true);

        serverApi().deleteMetadata(machine.getUuid(), "foo");
        assertNull(machineManager.getMachineByUuid(machine.getUuid()).getProperties().get("foo"));
    }


    @Test
    public void testSetMetadata() throws CloudProviderException {
        Map<String, String> props = ImmutableMap.of("foo", "bar");
        Machine machine = createMachine("testsetmeta", "testimage", 1, 512, props, true);

        Map<String, String> set = ImmutableMap.of("bar", "baz");
        Map<String, String> metadata = serverApi().setMetadata(machine.getUuid(), set);

        assertNotNull(metadata);
        System.out.println(metadata);
    }

}
