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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions;

import com.google.common.base.Function;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs.model.KeypairForCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.CredentialsCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.CredentialsTemplate;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class KeypairForCreateToCredentialsCreate implements Function<KeypairForCreate, CredentialsCreate> {

    private final KeyPairGenerator generator;

    public KeypairForCreateToCredentialsCreate(KeyPairGenerator generator) {
        this.generator = generator;
    }

    @Override
    public CredentialsCreate apply(KeypairForCreate input) {
        CredentialsCreate result = new CredentialsCreate();
        // TODO
        // having just a name is enough for the openstack user. Openstack will generate the public key is no one is provided.
        // Not for sirocco...
        result.setName(input.getName());

        // template is required by openstack in all the cases
        CredentialsTemplate template = new CredentialsTemplate();

        String publicKey = input.getPublicKey();
        if (publicKey == null && generator != null) {
            KeyPair pair = generator.generateKeyPair();
            publicKey = new String(pair.getPublic().getEncoded());
        }

        template.setPublicKey(publicKey);
        result.setCredentialTemplate(template);
        return result;
    }
}
