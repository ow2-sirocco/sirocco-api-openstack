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

import org.junit.Assert;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class VolumeAttachmentsTest {

    @Test
    public void testArray() throws IOException {
        VolumeAttachment attachment = new VolumeAttachment();
        attachment.setVolumeId(UUID.randomUUID().toString());
        attachment.setDevice("/dev/vdb");
        attachment.setServerId(UUID.randomUUID().toString());
        attachment.setId(UUID.randomUUID().toString());

        VolumeAttachments attachments = new VolumeAttachments();
        attachments.getList().add(attachment);

        JacksonConfigurator configurator = new JacksonConfigurator();
        String out = configurator.getContext(VolumeAttachments.class).writeValueAsString(attachments);

        System.out.println(out);

        Assert.assertTrue(out.contains("\"attachments\""));
        // TODO : JSON Test
    }
}
