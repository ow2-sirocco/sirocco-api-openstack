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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder.functions;

import org.junit.Test;
import org.ow2.sirocco.cloudmanager.model.cimi.Volume;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class VolumeToVolumeTest {

    @Test
    public void test() {
        Volume volume = new Volume();
        volume.setState(Volume.State.ERROR);

        org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.Volume out = new VolumeToVolume(true).apply(volume);
        System.out.println(out.getStatus());
    }
}
