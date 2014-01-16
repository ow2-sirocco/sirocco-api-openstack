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

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Image;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Metadata;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;

import java.util.Map;

/**
 * Transforms a Sirocco Image to an Openstack one
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class MachineImageToImage implements Function<MachineImage, Image> {

    private boolean details;

    private static Map<MachineImage.State, String> mapping;
    static {
        mapping = Maps.newHashMap();
        mapping.put(MachineImage.State.AVAILABLE, "ACTIVE");
        mapping.put(MachineImage.State.CREATING, "SAVING");
        mapping.put(MachineImage.State.DELETED, "DELETED");
        mapping.put(MachineImage.State.DELETING, "DELETED");
        mapping.put(MachineImage.State.ERROR, "ERROR");
        mapping.put(MachineImage.State.UNKNOWN, "UNKNOWN");
    }

    public MachineImageToImage(boolean details) {
        this.details = details;
    }

    @Override
    public Image apply(org.ow2.sirocco.cloudmanager.model.cimi.MachineImage input) {
        Image image = new Image();
        image.setId(input.getUuid());
        image.setName(input.getName());

        if (details) {
            image.setCreated(input.getCreated());
            if (input.getProperties() != null) {
                image.setMetadata(new MapToMetadata().apply(input.getProperties()));
            }

            if (input.getCapacity() != null) {
                image.setSize(Long.valueOf((input.getCapacity() * 1000)));
            }

            image.setMinDisk(0);
            image.setMinRam(0);

            image.setProgress(0);
            if (input.getState() == MachineImage.State.AVAILABLE) {
                image.setProgress(100);
            }

            if (input.getProperties() != null) {
                Metadata meta = new Metadata();
                meta.putAll(input.getProperties());
                image.setMetadata(meta);
            }
            image.setStatus(mapping.get(input.getState()));
            image.setUpdated(input.getUpdated());
        }
        return image;
    }
}
