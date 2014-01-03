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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova;

import com.google.common.collect.ImmutableMap;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Metadata;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.MapToMetadata;
import org.ow2.sirocco.cloudmanager.api.openstack.server.utils.MapHelper;
import org.ow2.sirocco.cloudmanager.core.api.IMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.deleted;
import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.notFound;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class ImageMetadata extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.ImageMetadata {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageMetadata.class);

    @Inject
    private IMachineImageManager manager;

    @Override
    public Response get() {
        try {
            Map<String, String> meta = manager.getMachineImageByUuid(getImageId()).getProperties();
            if (meta != null) {
                return ok(new MapToMetadata().apply(meta));
            } else {
                return ok(new Metadata());
            }
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("image", getImageId(), rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting image metadata";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    @Override
    public Response update(Metadata metadata) {
        try {
            Map<String, String> meta = manager.getMachineImageByUuid(getImageId()).getProperties();
            Map<String, String> input = metadata.getMetadata();

            // According to the openstack documentation
            // update operation will update the properties which are already set and do not modify other
            // It will not add any metadata which is not already in, we are just in update mode, node add
            // to add, check the #set method.
            Map<String, String> updated =  MapHelper.updateMap(meta, input);
            manager.updateMachineImageAttributes(getImageId(), ImmutableMap.<String, Object>of("properties", updated));

            // FIXME : Need to query the backend to get new values
            return ok(new MapToMetadata().apply(updated));

        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("image", getImageId(), rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting image metadata";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    @Override
    public Response set(Metadata metadata) {
        try {
            Map<String, String> meta = manager.getMachineImageByUuid(getImageId()).getProperties();
            Map<String, String> input = metadata.getMetadata();

            // According to the openstack documentation
            // set operation will replace properties that match incoming request and removes items not specified in the request.
            Map<String, String> updated = MapHelper.replaceMap(meta, input);
            manager.updateMachineImageAttributes(getImageId(), ImmutableMap.<String, Object>of("properties", updated));

            // FIXME : Need to query the backend to get new values
            return ok(new MapToMetadata().apply(updated));

        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("image", getImageId(), rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting image metadata";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    @Override
    public Response get(String key) {
        try {
            Map<String, String> meta = manager.getMachineImageByUuid(getImageId()).getProperties();
            if (meta != null && meta.get(key) != null) {
                return ok(new MapToMetadata().apply(ImmutableMap.of(key, meta.get(key))));
            } else {
                return notFound();
            }
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("image", getImageId(), rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting image metadata";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }    }

    @Override
    public Response set(String key, String value) {
        // get all the values and replace the given one with the given value
        try {
            Map<String, String> meta = manager.getMachineImageByUuid(getImageId()).getProperties();
            if (meta.get(key) != null) {
                meta.put(key, value);
                manager.updateMachineImageAttributes(getImageId(), ImmutableMap.<String, Object>of("properties", meta));
            }
            // FIXME : What if the input key/value is not available? Add it or not.
            // The openstack documentation does not specify what to do.
            // FIXME : Need to query the backend to get new values
            // FIXME : What is the return code in this case?
            return ok(new MapToMetadata().apply(meta));

        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("image", getImageId(), rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting image metadata";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    @Override
    public Response delete(String key) {
        // get all the values and replace the given one with the given value
        try {
            Map<String, String> meta = manager.getMachineImageByUuid(getImageId()).getProperties();
            if (meta.get(key) != null) {
                meta.remove(key);
                manager.updateMachineImageAttributes(getImageId(), ImmutableMap.<String, Object>of("properties", meta));
                return deleted();
            } else {
                // FIXME : What is the return code, not defined in the openstack documentation?
                return notFound();
            }
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("image", getImageId(), rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting image metadata";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    /**
     * Get the current image ID from the URL
     *
     * @return
     */
    protected String getImageId() {
        return getPathParamValue(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.IMAGE_ID_PATH_PARAMETER);
    }

}
