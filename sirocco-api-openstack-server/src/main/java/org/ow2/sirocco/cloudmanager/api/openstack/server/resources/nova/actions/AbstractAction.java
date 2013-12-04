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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.builders.FaultBuilder;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Action;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.computeFault;

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
        return ResponseHelper.fault(FaultBuilder.itemNotFound(type + " " + id + " not found", 0, message));
    }

    public Response serverFault(CloudProviderException e) {
        LOG.debug("Server side error", e);
        return computeFault("Server Error", 500, e.getMessage());
    }
}
