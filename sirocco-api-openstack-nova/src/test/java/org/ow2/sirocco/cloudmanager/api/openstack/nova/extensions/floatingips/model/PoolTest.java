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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.floatingips.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class PoolTest {

    @Test
    public void testPool() throws Exception {
        Pool pool = new Pool();
        pool.setName("test");

        JacksonConfigurator configurator = new JacksonConfigurator();
        String out = configurator.getContext(Pool.class).writeValueAsString(pool);
        System.out.println(out);
        assertNotNull(out);
        assertTrue(out.contains(pool.getName()));
    }

    @Test
    public void testNull() throws Exception {
        Pool pool = new Pool();

        JacksonConfigurator configurator = new JacksonConfigurator();
        String out = configurator.getContext(Pool.class).writeValueAsString(pool);
        System.out.println(out);
        assertTrue(out.contains("null"));
    }
}
