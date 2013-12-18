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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron.functions;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.NetworkForCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.NetworkCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.NetworkTemplate;

import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class NetworkForCreateToNetworkCreate implements Function<NetworkForCreate, NetworkCreate> {

    private String providerAccountId;

    public NetworkForCreateToNetworkCreate(String providerAccountId) {
        this.providerAccountId = providerAccountId;
    }

    @Override
    public NetworkCreate apply(org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.NetworkForCreate input) {
        NetworkCreate result = new NetworkCreate();
        result.setName(input.getName());

        NetworkTemplate template = null;
        result.setNetworkTemplate(template);

        result.setDescription("Network description");

        String location = "TODO";
        result.setLocation(location);

        Map<String, String> properties = Maps.newHashMap();
        result.setProperties(properties);

        result.setProviderAccountId(providerAccountId);
        return result;
    }
}
