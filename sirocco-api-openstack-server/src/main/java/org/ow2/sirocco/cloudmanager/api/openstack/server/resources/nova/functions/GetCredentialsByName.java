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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions;

import com.google.common.base.Function;
import org.ow2.sirocco.cloudmanager.core.api.ICredentialsManager;
import org.ow2.sirocco.cloudmanager.core.api.QueryParams;
import org.ow2.sirocco.cloudmanager.core.api.QueryResult;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class GetCredentialsByName implements Function<String, Credentials> {

    private static Logger LOG = LoggerFactory.getLogger(GetCredentialsByName.class);

    private ICredentialsManager credentialsManager;

    public GetCredentialsByName(ICredentialsManager credentialsManager) {
        this.credentialsManager = credentialsManager;
    }

    @Override
    public Credentials apply(final String name) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Trying to find credential from name " + name);
        }

        try {
            QueryResult<Credentials> query = credentialsManager.getCredentials(new QueryParams.Builder().filter("name='" + name + "'").build());
            if (query.getItems() != null && query.getItems().size() > 0) {
                return query.getItems().get(0);
            }
        } catch (CloudProviderException e) {
            LOG.warn("Error while getting credentials, returning null", e);
        }
        return null;
    }
}
