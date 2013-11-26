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
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;

import javax.ws.rs.core.Response;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.notImplemented;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class ServerAddresses extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.ServerAddresses {

    @Override
    public Response list() {
        return notImplemented("ServerAddresses", "list");
    }

    @Override
    public Response list(String network) {
        return notImplemented("ServerAddresses", "list.network");
    }
}
