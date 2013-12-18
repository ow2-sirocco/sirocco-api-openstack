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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.Volume;
import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.VolumeForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.VolumeForUpdate;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.LinkHelper;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder.functions.VolumeForCreateToVolumeCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder.functions.VolumeToVolume;
import org.ow2.sirocco.cloudmanager.core.api.IVolumeManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.computeFault;
import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.deleted;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class Volumes extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.cinder.resources.Volumes {

    private static Logger LOG = LoggerFactory.getLogger(Volumes.class);

    @Inject
    private IVolumeManager volumeManager;

    @Override
    public Response create(VolumeForCreate volumeCreate) {

        String provider = "TODO";
        try {
            Job job = volumeManager.createVolume(new VolumeForCreateToVolumeCreate(volumeManager, provider).apply(volumeCreate));
            org.ow2.sirocco.cloudmanager.model.cimi.Volume volume = (org.ow2.sirocco.cloudmanager.model.cimi.Volume) job.getTargetResource();
            Volume result = new VolumeToVolume(false).apply(volume);

            Volume out = new Function<Volume, Volume>() {
                @Override
                public Volume apply(Volume input) {
                    input.getLinks().add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, "%s", input.getId()));
                    input.getLinks().add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, "%s", input.getId()));
                    return input;
                }
            }.apply(result);
            return Response.accepted(out).build();
        } catch (CloudProviderException e) {
            final String error = "Error while creating volume";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response list() {
        return getVolumes(false);
    }

    @Override
    public Response details() {
        return getVolumes(true);
    }

    @Override
    public Response get(String id) {

        try {
            org.ow2.sirocco.cloudmanager.model.cimi.Volume volume = volumeManager.getVolumeByUuid(id);
            Volume v = new VolumeToVolume(true).apply(volume);
            v.setTenantId(getPathParamValue(Constants.Nova.TENANT_ID_PATH_PARAMETER));
            v.getLinks().add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, null));
            v.getLinks().add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.BOOKMARK, null, null));
            return ok(v);

        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("volume", id, rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting volume details";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Volume Error", 500, e.getMessage());
        }
    }

    @Override
    public Response update(String id, VolumeForUpdate volume) {
        // NOTE : Not implemented on the backend
        org.ow2.sirocco.cloudmanager.model.cimi.Volume update = new org.ow2.sirocco.cloudmanager.model.cimi.Volume();
        update.setUuid(id);
        update.setName(volume.getName());
        update.setDescription(volume.getDescription());
        try {
            Job job = volumeManager.updateVolume(update);
            Volume out = new VolumeToVolume(true).apply((org.ow2.sirocco.cloudmanager.model.cimi.Volume)job.getTargetResource());
            out.getLinks().add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, "%s", id));
            out.getLinks().add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.BOOKMARK, null, "%s", id));
            return ok(out);

        } catch (CloudProviderException e) {
            final String error = "Error while updating volume details";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Volume Error", 500, e.getMessage());        }
    }

    @Override
    public Response delete(String id) {

        try {
            volumeManager.deleteVolume(id);
            return deleted();
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("volume", id, rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting volume details";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Volume Error", 500, e.getMessage());
        }
    }

    /**
     * @param details
     * @return
     */
    protected Response getVolumes(final boolean details) {

        try {
            List<Volume> volumes = Lists.transform(volumeManager.getVolumes().getItems(), new VolumeToVolume(details));
            org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.Volumes result = new org.ow2.sirocco.cloudmanager.api.openstack.cinder.model.Volumes();

            volumes = Lists.transform(volumes, new Function<Volume, Volume>() {
                @Override
                public Volume apply(Volume input) {
                    // Add details if requested
                    if (details) {
                        input.setTenantId(getPathParamValue(Constants.Nova.TENANT_ID_PATH_PARAMETER));
                    }

                    input.getLinks().add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, "%s", input.getId()));
                    input.getLinks().add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.BOOKMARK, null, "%s", input.getId()));
                    return input;
                }
            });

            result.setVolumes(volumes);
            return ok(result);
        } catch (CloudProviderException e) {
            final String error = "Error while getting volumes";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }
}
