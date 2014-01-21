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

package org.ow2.sirocco.cloudmanager.api.openstack.cinder.model;

import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class VolumeTest {

    /**
     * Check that we can serialize the right way since volume override name attribute with a new annotation.
     */
    @Test
    public void testSerialize() throws IOException {
        Volume volume = new Volume();
        volume.setName("foo");
        volume.setDescription("bar");

        JacksonConfigurator configurator = new JacksonConfigurator();
        String out = configurator.getContext(Volume.class).writeValueAsString(volume);

        System.out.println(out);

        assertNotNull(out);
        assertTrue(out.contains("display_name"));
        assertTrue(out.contains("display_description"));
    }
}
