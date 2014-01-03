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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions;

import com.google.common.base.Function;
import org.codehaus.jackson.JsonNode;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerAction;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Action;
import org.ow2.sirocco.cloudmanager.core.api.IMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class CreateImageAction extends AbstractAction implements Action {

    private static Logger LOG = LoggerFactory.getLogger(CreateImageAction.class);

    @Inject
    IMachineImageManager manager;

    public static final String ACTION = "createImage";

    @Override
    public String getName() {
        return ACTION;
    }

    @Override
    public Response invoke(String serverId, JsonNode payload) {

        try {
            ServerAction.CreateImage create = getBean(payload, ServerAction.CreateImage.class);
            Job job = manager.createMachineImage(new Function<ServerAction.CreateImage, MachineImage>() {
                @Override
                public MachineImage apply(ServerAction.CreateImage input) {
                    MachineImage image = new MachineImage();
                    image.setName(input.getName());
                    image.setProperties(input.getMetadata());
                    return image;
                }
            }.apply(create));

            // TODO : Create the HTTP header location which has to be returned
            // http://docs.openstack.org/api/openstack-compute/2/content/Create_Image-d1e4655.html
            
            return accepted(URI.create("http://TODO"));

        } catch (CloudProviderException e) {
            LOG.warn("Sirocco error", e);
            return computeFault(500, "Error", e.getMessage());
        } catch (IOException e) {
            LOG.warn("Parsing error", e);
            return computeFault(500, "Error", e.getMessage());
        }
    }
}
