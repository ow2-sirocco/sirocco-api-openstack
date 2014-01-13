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
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.QueryParams;
import org.ow2.sirocco.cloudmanager.core.api.QueryResult;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Get the security group UUID from its name.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class SecurityGroupNameToUUID implements Function<String, String> {

    private static Logger LOG = LoggerFactory.getLogger(org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.SecurityGroupNameToUUID.class);

    protected INetworkManager networkManager;

    public SecurityGroupNameToUUID(INetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public String apply(String name) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Trying to find security group UUID from name " + name);
        }
        try {
            QueryResult<SecurityGroup> result = networkManager.getSecurityGroups(new QueryParams.Builder().attribute("name='" + name + "'").build());
            if (result.getItems() != null && result.getItems().size() > 0) {
                return result.getItems().get(0).getUuid();
            }
        } catch (CloudProviderException e) {
            LOG.warn("Can not find security group ID", e);
            return null;
        }
        return null;
    }
}
