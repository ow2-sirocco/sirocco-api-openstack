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

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.Metadata;
import org.ow2.sirocco.cloudmanager.model.cimi.Volume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class VolumeToVolume implements Function<Volume, org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.Volume> {

    private static Logger LOG = LoggerFactory.getLogger(VolumeToVolume.class);

    private final boolean details;

    private static Map<Volume.State, String> MAPPING;

    static {
        MAPPING = Maps.newHashMap();
        MAPPING.put(Volume.State.CREATING, "SAVING");
        MAPPING.put(Volume.State.AVAILABLE, "ACTIVE");
        MAPPING.put(Volume.State.DELETING, "DELETED");
        MAPPING.put(Volume.State.DELETED, "DELETED");
        MAPPING.put(Volume.State.ERROR, "ERROR");
    }

    public VolumeToVolume(boolean details) {
        this.details = details;
    }

    @Override
    public org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.Volume apply(org.ow2.sirocco.cloudmanager.model.cimi.Volume input) {

        org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.Volume result = new org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.Volume();
        result.setId(input.getUuid());
        result.setName(input.getName());

        if (details) {
            result.setCreatedAt(input.getCreated());
            result.setVolumeType(input.getType());
            //result.setAvailabilityZone();
            result.setDescription(input.getDescription());
            //result.setHost();

            if (input.getProperties() != null) {
                result.setMetadata(new Metadata(input.getProperties()));
            }

            if (input.getCapacity() != null) {
                result.setSize(input.getCapacity() / 1000 / 1000);
            }

            //result.setSnapshotId();
            //result.setSourceVolume();

            if (input.getState() != null) {
                result.setStatus(MAPPING.get(input.getState()));
            }

            //result.setTenantId();

            result.setVolumeType(input.getType());
        }
        return result;
    }
}
