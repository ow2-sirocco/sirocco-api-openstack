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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.NetworkForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.NetworkForUpdate;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.notImplemented;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class Networks extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.neutron.resources.Networks {

    private static Logger LOG = LoggerFactory.getLogger(Networks.class);

    @Inject
    private INetworkManager networkManager;

    @Override
    public Response list() {
        return notImplemented(Networks.class.getName(), "list");
    }

    @Override
    public Response create(NetworkForCreate network) {
        return notImplemented(Networks.class.getName(), "create");
    }

    @Override
    public Response createMultiple(org.ow2.sirocco.cloudmanager.api.openstack.neutron.resources.Networks networks) {
        return notImplemented(Networks.class.getName(), "createMultiple");
    }

    @Override
    public Response get(String id) {
        return notImplemented(Networks.class.getName(), "get");
    }

    @Override
    public Response update(String id, NetworkForUpdate networkForUpdate) {
        return notImplemented(Networks.class.getName(), "update");
    }

    @Override
    public Response delete(String id) {
        return notImplemented(Networks.class.getName(), "delete");
    }
}
