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
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Flavor;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.Visibility;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class MachineConfigurationToFlavorTest {

    @Test
    public void testVisibilityNotSet() {
        MachineConfiguration config = new MachineConfiguration();
        Flavor f = new MachineConfigurationToFlavor(true).apply(config);
        assertFalse(f.isPublic);
    }

    @Test
    public void testPublic() {
        MachineConfiguration config = new MachineConfiguration();
        config.setVisibility(Visibility.PUBLIC);
        Flavor f = new MachineConfigurationToFlavor(true).apply(config);
        assertTrue(f.isPublic);
    }

    @Test
    public void testPrivate() {
        MachineConfiguration config = new MachineConfiguration();
        config.setVisibility(Visibility.PRIVATE);
        Flavor f = new MachineConfigurationToFlavor(true).apply(config);
        assertFalse(f.isPublic);
    }
}
