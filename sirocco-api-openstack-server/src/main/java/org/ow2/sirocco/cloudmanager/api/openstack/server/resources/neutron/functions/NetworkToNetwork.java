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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron.functions;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.model.cimi.Network;
import org.ow2.sirocco.cloudmanager.model.cimi.Subnet;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class NetworkToNetwork implements Function<Network, org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Network>{

    private final boolean details;

    public NetworkToNetwork(boolean details) {
        this.details = details;
    }

    @Override
    public org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Network apply(org.ow2.sirocco.cloudmanager.model.cimi.Network input) {
        org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Network result = new org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Network();

        result.setId(input.getUuid());
        result.setName(input.getName());

        if (input.getState() != null) {
            result.setStatus(input.getState().name());
        }

        if (input.getTenant() != null) {
            result.setTenantId(input.getTenant().getUuid());
        }

        if (details) {
            if (input.getSubnets() != null && input.getSubnets().size() > 0) {
                result.setSubnets(Lists.newArrayList(Iterables.transform(input.getSubnets(), new Function<Subnet, String>() {
                    @Override
                    public String apply(Subnet input) {
                        return input.getUuid();
                    }
                })));
            }
        }

        return result;
    }
}
