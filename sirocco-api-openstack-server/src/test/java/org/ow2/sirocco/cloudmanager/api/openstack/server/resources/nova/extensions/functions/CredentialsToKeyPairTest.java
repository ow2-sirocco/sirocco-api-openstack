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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions;

import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.keypairs.model.Keypair;
import org.ow2.sirocco.cloudmanager.model.cimi.Credentials;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class CredentialsToKeyPairTest {

    @Test(expected = NullPointerException.class)
    public void testNullApply() throws Exception {
        CredentialsToKeyPair f = new CredentialsToKeyPair();
        f.apply(null);
    }

    /**
     * Validates that we use UUID as keypair name. Will fail on change!
     */
    @Test
    public void testApply() {
        CredentialsToKeyPair f = new CredentialsToKeyPair();
        Credentials input = new Credentials();
        input.setName("A");
        input.setPublicKey("123");
        input.setUuid(UUID.randomUUID().toString());
        Keypair out = f.apply(input);

        assertEquals(input.getPublicKey(), out.getPublicKey());
        assertEquals(input.getName(), out.getName());
    }
}
