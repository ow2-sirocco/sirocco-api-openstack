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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder.functions;

import com.google.common.base.Function;
import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.VolumeForCreate;
import org.ow2.sirocco.cloudmanager.core.api.IVolumeManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.VolumeCreate;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class VolumeForCreateToVolumeCreate implements Function<VolumeForCreate, VolumeCreate> {

    private final IVolumeManager volumeManager;
    private final String providerAccountId;

    public VolumeForCreateToVolumeCreate(IVolumeManager volumeManager, String providerAccountId) {
        this.volumeManager = volumeManager;
        this.providerAccountId = providerAccountId;
    }

    @Override
    public VolumeCreate apply(org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.VolumeForCreate input) {
        VolumeCreate create = new VolumeCreate();
        create.setDescription(input.getDescription());
        create.setName(input.getName());

        if (input.getType() != null) {
            try {
                create.setVolumeTemplate(volumeManager.getVolumeTemplateByUuid(input.getType()));
            } catch (CloudProviderException e) {
                // handle
                e.printStackTrace();
            }
        }

        create.setLocation(input.getAvailabilityZone());

        if (input.getMetadata() != null && input.getMetadata().getMetadata() != null) {
            create.setProperties(input.getMetadata().getMetadata());
        }

        if (providerAccountId != null) {
            create.setProviderAccountId(providerAccountId);
        }

        return create;
    }
}
