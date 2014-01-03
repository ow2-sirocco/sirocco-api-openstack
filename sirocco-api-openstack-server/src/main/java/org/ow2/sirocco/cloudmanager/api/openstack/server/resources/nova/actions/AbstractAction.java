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

import org.codehaus.jackson.JsonNode;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Action;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.itemNotFound;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class AbstractAction implements Action {

    private static Logger LOG = LoggerFactory.getLogger(AbstractAction.class);

    public Response resourceNotFoundException(String type, String id, Exception e) {
        LOG.debug("Resource has not been found", e);
        String message = "Resource not found";
        if (e != null) {
            message = e.getLocalizedMessage();
        }
        return itemNotFound(type + " " + id + " not found", message);
    }

    public Response serverFault(CloudProviderException e) {
        LOG.debug("Server side error", e);
        return computeFault(500, "Server Error", e.getMessage());
    }

    public Response notImplemented() {
        return computeFault(500, "Action not available", getName() + " is not implemented");
    }

    /**
     * Resource has been created. Some openstack operations need to set the location URI of the resource.
     *
     * @param uri of the created resource, or null
     * @return
     */
    public Response accepted(URI uri) {
        return Response.accepted().location(uri).build();
    }

    public <T> T getBean(JsonNode node, Class<T> clazz) throws IOException {
        JacksonConfigurator configurator = new JacksonConfigurator();
        return configurator.getContext(clazz).readValue(node, clazz);
    }
}
