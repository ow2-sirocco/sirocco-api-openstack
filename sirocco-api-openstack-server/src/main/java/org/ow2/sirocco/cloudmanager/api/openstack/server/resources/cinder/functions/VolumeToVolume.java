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
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.Metadata;
import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.VolumeAttachments;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder.Status;
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

    public static Map<Volume.State, Status> MAPPING;

    static {
        MAPPING = Maps.newHashMap();
        MAPPING.put(Volume.State.CREATING, Status.CREATING);
        MAPPING.put(Volume.State.AVAILABLE, Status.AVAILABLE);
        MAPPING.put(Volume.State.DELETING, Status.DELETING);
        MAPPING.put(Volume.State.DELETED, Status.DELETING);
        MAPPING.put(Volume.State.ERROR, Status.ERROR);
    }

    public static final String NONE = "None";

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
            Status status = input.getState() == null ? Status.UNRECOGNIZED : MAPPING.get(input.getState());
            if (status == null) {
                status = Status.UNRECOGNIZED;
            }

            if (input.getAttachments() != null && input.getAttachments().size() > 0) {
                status = Status.IN_USE;
            }

            result.setStatus(status.value());

            if (input.getTenant() != null && input.getTenant().getUuid() != null) {
                result.setTenantId(input.getTenant().getUuid());
            }

            result.setVolumeType(NONE);
            // zone is required
            result.setAvailabilityZone("");

            if (input.getAttachments() != null) {
                VolumeAttachments attachments = new VolumeAttachments();
                attachments.setList(Lists.newArrayList(Iterables.transform(input.getAttachments(), new MachineVolumeToVolumeAttachment())));
                result.setAttachments(attachments);
            }
        }
        return result;
    }
}
