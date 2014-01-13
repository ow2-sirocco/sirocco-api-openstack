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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.core.api.IVolumeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.notFound;

/**
 * Sirocco does not support volume type. cf issue #4
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class Types extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.cinder.resources.Types {

    private static Logger LOG = LoggerFactory.getLogger(Types.class);

    @Inject
    private IVolumeManager volumeManager;

    @Override
    public Response list() {
        LOG.debug("Get types list");
        return ok(new Types());
    }

    @Override
    public Response get(String id) {
        LOG.debug("Get volume type information");
        return notFound();
    }
}
