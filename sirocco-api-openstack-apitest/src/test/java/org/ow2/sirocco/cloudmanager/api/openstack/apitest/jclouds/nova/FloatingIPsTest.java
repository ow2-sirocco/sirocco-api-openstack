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

import com.google.common.collect.FluentIterable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class FloatingIPsTest extends JcloudsBasedTest {

    private static Logger LOG = LoggerFactory.getLogger(FloatingIPsTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    private FloatingIPApi api() {
        return nova.getApi().getFloatingIPExtensionForZone(getZone()).get();
    }

    @Test
    public void testList() {
        fail();
        FluentIterable<? extends FloatingIP> list = api().list();
    }

    @Test
    public void testGetOne() {
        fail();
        String id = "";
        FloatingIP floatingIP = api().get(id);
        assertNotNull(floatingIP);
    }

    @Test
    public void testDelete() {
        fail();
        String id = "";
        api().delete(id);
    }

    @Test
    public void testAssociate() {
        fail("Not implemented");
    }

    @Test
    public void testAddToServer() {
        fail();
        String server = "";
        String address = "";
        api().addToServer(address, server);
    }

}
