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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions;

import com.google.common.base.Function;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.volumeattachments.model.VolumeAttachment;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineVolume;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class MachineVolumeToVolumeAttachment implements Function<MachineVolume, VolumeAttachment> {

    private final String serverId;

    public MachineVolumeToVolumeAttachment(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public VolumeAttachment apply(MachineVolume input) {
        VolumeAttachment result = new VolumeAttachment();
        result.setDevice(input.getInitialLocation());
        result.setId(input.getUuid());
        result.setServerId(serverId);
        if (input.getVolume() != null) {
            result.setVolumeId(input.getVolume().getUuid());
        }
        return result;
    }
}
