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

package org.ow2.sirocco.cloudmanager.api.openstack.server.functions;

import com.google.common.base.Function;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Link;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Image;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;

/**
 * Transforms a Sirocco Image to an Openstack one
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class MachineImageToImage implements Function<MachineImage, Image> {

    private boolean details;

    public MachineImageToImage(boolean details) {
        this.details = details;
    }

    @Override
    public Image apply(org.ow2.sirocco.cloudmanager.model.cimi.MachineImage input) {
        Image image = new Image();
        image.setId("" + input.getId());
        image.setName(input.getName());

        // TODO
        image.getLinks().add(new Link("http://TODO", "bookmark"));

        // TODO
        if (details) {
            //image.setCreated(input.getCreated());
            if (input.getProperties() != null) {
                image.setMetadata(new MapToMetadata().apply(input.getProperties()));
            }
            //image.setMinDisk();
            //image.setMinRam();
            //image.setProgress();
            //image.setSize();
            // TODO : Mapping between sirocco and openstack
            image.setStatus(input.getState().toString());
            //image.setUpdated();
        }
        return image;
    }
}
