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
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs.model.KeypairForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions.CredentialsToKeyPair;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions.KeypairForCreateToCredentialsCreate;
import org.ow2.sirocco.cloudmanager.core.api.ICredentialsManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.deleted;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.badRequest;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

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
        KeyPairGenerator generator = null;

        // generate public key only if the input does not have one
        if (keypair.getPublicKey() == null) {
            try {
                generator = KeyPairGenerator.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                final String error = "Can not generate keypair";
                if (LOG.isDebugEnabled()) {
                    LOG.debug(error, e);
                } else {
                    LOG.error(error);
                }
                return computeFault(500, error, e.getMessage());
            }
        }

        try {
            return ok(new CredentialsToKeyPair().apply(credentialsManager.createCredentials(new KeypairForCreateToCredentialsCreate(generator).apply(keypair))));
        } catch (CloudProviderException e) {
            final String error = "Can not register keypair";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault(500, error, e.getMessage());
        }
    }

    @Override
    public Response delete(String name) {
        // As defined in https://github.com/ow2-sirocco/sirocco-api-openstack/issues/17, we use the UUID as name
        try {
            credentialsManager.deleteCredentials(name);
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
        // As defined in https://github.com/ow2-sirocco/sirocco-api-openstack/issues/17, we use the UUID as name
        try {
            return ok(new CredentialsToKeyPair().apply(credentialsManager.getCredentialsByUuid(name)));
        } catch (InvalidRequestException ire) {
            return badRequest("keypair", "get");
        } catch (ResourceNotFoundException rnfe) {
            return resourceNotFoundException("keypair", name, rnfe);
        } catch (CloudProviderException e) {
            final String error = "Error while retrieving keypair";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }
}
