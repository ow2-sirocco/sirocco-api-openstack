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

import org.glassfish.jersey.process.internal.RequestScoped;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.RequestHelper;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Metadata;
import org.ow2.sirocco.cloudmanager.api.openstack.server.functions.MapToMetadata;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.computeFault;
import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.notImplemented;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class ServerMetadata extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.ServerMetadata {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMetadata.class);

    @Inject
    private IMachineManager machineManager;

    @Override
    public Response get() {
        try {
            Map<String, String> meta = machineManager.getMachineById(getServerId()).getProperties();
            if (meta != null) {
                return ok(new MapToMetadata().apply(meta));
            } else {
                return ok(new Metadata());
            }
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("server", getServerId(), rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while getting server metadata";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response update(Metadata metadata) {
        return notImplemented("metadata", "update");
    }

    @Override
    public Response set(Metadata metadata) {
        return notImplemented("metadata", "set");
    }

    @Override
    public Response get(@PathParam("key") String key) {
        return notImplemented("metadata", "getkey");
    }

    @Override
    public Response set(@PathParam("key") String key, String value) {
        return notImplemented("metadata", "setkey");
    }

    @Override
    public Response delete(@PathParam("key") String key) {
        return notImplemented("metadata", "deletekey");
    }

    /**
     * Get the current server ID
     *
     * @return
     */
    protected String getServerId() {
       return RequestHelper.getPathParameter(getJaxRsRequestInfo(),
               org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.SERVER_ID_PATH_PARAMETER);
    }
}
