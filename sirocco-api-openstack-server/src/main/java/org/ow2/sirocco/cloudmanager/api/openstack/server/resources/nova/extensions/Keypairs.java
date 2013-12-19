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

import org.ow2.sirocco.cloudmanager.api.openstack.api.annotations.Extension;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs.model.KeypairForCreate;
import org.ow2.sirocco.cloudmanager.core.api.ICredentialsManager;
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
@Extension(of = "compute", name = "os-keypairs", documentation = "http://api.openstack.org/api-ref-compute.html#os-keypairs")
public class Keypairs extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs.Keypairs {

    private static Logger LOG = LoggerFactory.getLogger(org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.Keypairs.class);

    @Inject
    private ICredentialsManager credentialsManager;

    @Override
    public Response list() {
        return notImplemented(Keypairs.class.getName(), "list");
    }

    @Override
    public Response create(KeypairForCreate keypair) {
        return notImplemented(Keypairs.class.getName(), "create");
    }

    @Override
    public Response delete(String name) {
        return notImplemented(Keypairs.class.getName(), "delete");
    }

    @Override
    public Response get(String name) {
        return notImplemented(Keypairs.class.getName(), "get");
    }
}
