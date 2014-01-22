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
import org.ow2.sirocco.cloudmanager.api.openstack.api.annotations.Extension;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Link;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.ExtensionProvider;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs.model.KeypairForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions.CredentialsToKeyPair;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions.KeypairForCreateToCredentialsCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.GetCredentialsByName;
import org.ow2.sirocco.cloudmanager.core.api.ICredentialsManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.deleted;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.badRequest;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
@Extension(of = "compute", name = "os-keypairs", documentation = "http://api.openstack.org/api-ref-compute.html#os-keypairs")
public class Keypairs extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs.Keypairs, ExtensionProvider {

    private static Logger LOG = LoggerFactory.getLogger(org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.Keypairs.class);

    @Inject
    private ICredentialsManager credentialsManager;

    @Override
    public Response list() {
        LOG.debug("Getting keypairs list");

        try {
            org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs.model.Keypairs result = new org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs.model.Keypairs();
            result.setKeypairs(Lists.transform(credentialsManager.getCredentials(), new CredentialsToKeyPair()));
            return ok(result);
        } catch (CloudProviderException e) {
            final String error = "Error while getting credentials";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    @Override
    public Response create(KeypairForCreate keypair) {

        // Note : just support when public key is given
        // return error is not available

        if (keypair.getPublicKey() != null) {
            try {
                return ok(new CredentialsToKeyPair().apply(credentialsManager.createCredentials(new KeypairForCreateToCredentialsCreate().apply(keypair))));
            } catch (CloudProviderException e) {
                final String error = "Can not register keypair";
                if (LOG.isDebugEnabled()) {
                    LOG.debug(error, e);
                } else {
                    LOG.error(error);
                }
                return computeFault(500, error, e.getMessage());
            }
        } else {
            return badRequest("Keypair create error", "public key is mandatory");
        }
    }

    @Override
    public Response delete(String name) {
        try {
            Credentials c = new GetCredentialsByName(credentialsManager).apply(name);
            if (c == null) {
                return resourceNotFoundException("keypair", name, null);
            }
            credentialsManager.deleteCredentials(c.getUuid());
            return deleted();
        } catch (InvalidRequestException ire) {
            return badRequest("keypair", "delete");
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("keypair", name, rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while deleting keypair";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    @Override
    public Response get(String name) {
        Credentials c = new GetCredentialsByName(credentialsManager).apply(name);
        if (c == null) {
            return resourceNotFoundException("keypair", name, null);
        } else {
            return (ok(new CredentialsToKeyPair().apply(c)));
        }
    }

    @Override
    public org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Extension getExtensionMetadata() {
        return new org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Extension("os-keypairs", "Keypair support.", new ArrayList<Link>(), "Keypairs", "http://docs.openstack.org/compute/ext/keypairs/api/v1.1", new Date());
    }
}
