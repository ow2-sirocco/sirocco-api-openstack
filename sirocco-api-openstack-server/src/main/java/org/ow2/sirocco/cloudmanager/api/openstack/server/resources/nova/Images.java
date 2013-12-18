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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.LinkHelper;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Image;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.MachineImageToImage;
import org.ow2.sirocco.cloudmanager.core.api.IMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.computeFault;
import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.deleted;

/**
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class Images extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Images {

    private static final Logger LOGGER = LoggerFactory.getLogger(Servers.class);

    @Inject
    private IMachineImageManager machineImageManager;

    @Override
    public Response list() {
        return getImages(false);
    }

    @Override
    public Response details() {
        return getImages(true);
    }

    protected Response getImages(boolean details) {
        try {
            org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Images result = new org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Images();
            List<MachineImage> images = machineImageManager.getMachineImages();

            if (images == null || images.size() == 0) {
                // TODO : Check openstack API for empty response.
                return ok(result);
            } else {
                List<Image> list = Lists.transform(images, new MachineImageToImage(details));
                list = Lists.transform(list, new Function<Image, Image>() {
                    @Override
                    public Image apply(org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Image input) {
                        input.getLinks().add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, "%s", input.getId()));
                        return input;
                    }
                });
                result.setImages(list);
                return ok(result);
            }

        } catch (CloudProviderException e) {
            final String error = "Error while getting images";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response details(String imageId) {
        try {
            MachineImage image = machineImageManager.getMachineImageByUuid(imageId);
            if (image == null) {
                return resourceNotFoundException("image", imageId, new ResourceNotFoundException("Image not found"));
            } else {
                Image result = new MachineImageToImage(true).apply(image);
                result.getLinks().add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, null));
                return ok(result);
            }
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("image", imageId, new ResourceNotFoundException("Image not found"));
        } catch (CloudProviderException e) {
            final String error = "Error while getting image details";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response delete(String imageid) {
        try {
            // TODO : manage error codes, check operation doc for more details
            machineImageManager.deleteMachineImage(imageid);
        } catch (ResourceNotFoundException rnf) {
            return resourceNotFoundException("image", imageid, rnf);
        } catch (CloudProviderException e) {
            final String error = "Error while deleting image";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
        return deleted();
    }
}
