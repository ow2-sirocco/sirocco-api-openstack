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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertTrue;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class ProtocolToVersionTest {

    @Test
    public void testNullProtocol() throws Exception {
        assertTrue(ProtocolToVersion.V4 == new ProtocolToVersion().apply(null));
    }

    @Test
    public void testString6() {
        assertTrue(ProtocolToVersion.V6 == new ProtocolToVersion().apply("6"));
    }

    @Test
    public void testString4() {
        assertTrue(ProtocolToVersion.V4 == new ProtocolToVersion().apply("4"));
    }

    @Test
    public void testString5() {
        assertTrue(ProtocolToVersion.V4 == new ProtocolToVersion().apply("5"));
    }

    @Test
    public void testIPV4String() {
        assertTrue(ProtocolToVersion.V4 == new ProtocolToVersion().apply("IPv4"));
    }

    @Test
    public void testIPV6String() {
        assertTrue(ProtocolToVersion.V6 == new ProtocolToVersion().apply("IPv6"));
    }

    @Test
    public void testRandomWith6() {
        assertTrue(ProtocolToVersion.V6 == new ProtocolToVersion().apply("ksksk6"));
    }

    @Test
    public void testRandomWith4() {
        assertTrue(ProtocolToVersion.V4 == new ProtocolToVersion().apply("gsg4ks"));
    }

    @Test
    public void testRandomWith4And6() {
        assertTrue(ProtocolToVersion.V4 == new ProtocolToVersion().apply("kjdkjd4dkdkk6"));
    }
}
