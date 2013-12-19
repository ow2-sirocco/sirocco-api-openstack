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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions;

import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.api.annotations.Extension;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.volumeattachments.model.VolumeAttachmentForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions.MachineVolumeToVolumeAttachment;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.IVolumeManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.CloudResource;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineVolume;
import org.ow2.sirocco.cloudmanager.model.cimi.Volume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.*;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
@Extension(of = "compute", name = "os-volume_attachments", documentation = "http://api.openstack.org/api-ref-compute.html#os-volume_attachments")
public class VolumeAttachments extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.volumeattachments.VolumeAttachments {

    private static Logger LOG = LoggerFactory.getLogger(VolumeAttachments.class);

    @Inject
    private IVolumeManager volumeManager;

    @Inject
    private IMachineManager machineManager;

    @Override
    public Response create(VolumeAttachmentForCreate volumeAttachmentForCreate) {
        try {
            Volume volume = volumeManager.getVolumeByUuid(volumeAttachmentForCreate.getVolumeId());
            MachineVolume mv = new MachineVolume();
            mv.setVolume(volume);
            mv.setInitialLocation(volumeAttachmentForCreate.getDevice());
            Job job = machineManager.addVolumeToMachine(getServerId(), mv);

            // machine volume is persisted on the backend, get the ID directly from the job
            List<CloudResource> r = job.getAffectedResources();
            if (r != null && r.get(0) != null && r.get(0) instanceof MachineVolume) {
                return created(new MachineVolumeToVolumeAttachment(getServerId()).apply((MachineVolume) r.get(0)));
            } else {
                // send back the local machine volume instead of nothing
                return created(new MachineVolumeToVolumeAttachment(getServerId()).apply(mv));
            }
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("volume_attachment", "", rnfe);
        } catch (InvalidRequestException ire) {
            return badRequest("volume_attachment", "create");
        } catch (CloudProviderException e) {
            final String error = "Error while creating volume attachment";
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
        try {
            org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.volumeattachments.model.VolumeAttachments result = new org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.volumeattachments.model.VolumeAttachments();
            result.setAttachments(Lists.transform(machineManager.getMachineVolumes(getServerId()).getItems(), new MachineVolumeToVolumeAttachment(getServerId())));
            return ok(result);
        } catch (InvalidRequestException ire) {
            return badRequest("volume_attachment", "list");
        } catch (CloudProviderException e) {
            final String error = "Error while getting volume attachment";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response delete(String id) {
        try {
            machineManager.removeVolumeFromMachine(getServerId(), id);
            return deleted();
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("volume", id, rnfe);
        } catch (InvalidRequestException ire) {
            return badRequest("volume-attachment", "delete");
        } catch (CloudProviderException e) {
            final String error = "Error while deleting volume attachment " + id;
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response get(String id) {
        try {
            return ok(new MachineVolumeToVolumeAttachment(getServerId()).apply(machineManager.getVolumeFromMachine(getServerId(), id)));
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("volume_attachment", id, rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting volume attachment " + id;
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }
}
