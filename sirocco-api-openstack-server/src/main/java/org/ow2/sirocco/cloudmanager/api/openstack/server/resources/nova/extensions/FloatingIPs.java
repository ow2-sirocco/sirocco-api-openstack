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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions;

import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Link;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.ExtensionProvider;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.floatingips.model.Pool;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Extension;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions.AddressToFloatingIP;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.AddressCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.deleted;
import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.notFound;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.badRequest;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
@org.ow2.sirocco.cloudmanager.api.openstack.api.annotations.Extension(of = "compute", name = "os-floating-ips", documentation = "http://api.openstack.org/api-ref-compute-ext.html#ext-os-floating-ips")
public class FloatingIPs extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.floatingips.FloatingIPs, ExtensionProvider {

    private static Logger LOG = LoggerFactory.getLogger(FloatingIPs.class);

    @Inject
    private INetworkManager networkManager;

    @Override
    public Response list() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Getting floating IP list");
        }

        org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.floatingips.model.FloatingIPs result = new org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.floatingips.model.FloatingIPs();
        try {
            result.setIps(Lists.transform(networkManager.getAddresses().getItems(), new AddressToFloatingIP()));
            return ok(result);
        } catch (InvalidRequestException ire) {
            return badRequest("Bad request", ire.getMessage());
        } catch (CloudProviderException e) {
            return computeFault("Server error", e.getMessage());
        }
    }

    @Override
    public Response get(String id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Get floating IP " + id);
        }
        try {
            return ok(new AddressToFloatingIP().apply(networkManager.getAddressByUuid(id)));
        } catch (ResourceNotFoundException e) {
            return notFound();
        }
    }

    @Override
    public Response create(Pool pool) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Create floating IP with pool" + pool);
        }

        try {
            Job job = networkManager.createAddress(new AddressCreate());
            return ok(new AddressToFloatingIP().apply(networkManager.getAddressByUuid(job.getTargetResource().getUuid())));
        } catch (InvalidRequestException ire) {
            return badRequest("Bad request while creating floating IP", ire.getMessage());
        } catch (CloudProviderException e) {
            if (LOG.isDebugEnabled()) {
                LOG.warn("Create floating IP error", e);
            }
            return computeFault("Floating IP create error", e.getMessage());
        }
    }

    @Override
    public Response delete(String id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Delete floating IP " + id);
        }

        try {
            networkManager.deleteAddress(id);
            return deleted();
        } catch (ResourceNotFoundException rnfe) {
            return notFound();
        } catch (CloudProviderException e) {
            return computeFault("Server error", e.getMessage());
        }
    }

    @Override
    public Extension getExtensionMetadata() {
        return new org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Extension("os-floating-ips", "Floating IPs support.", new ArrayList<Link>(), "FloatingIps", "http://docs.openstack.org/compute/ext/floating_ips/api/v1.1", new Date());
    }
}
