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

import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.ProtocolToVersion;
import org.ow2.sirocco.cloudmanager.model.cimi.Subnet;

public class SubnetToSubnet implements Function<Subnet, org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Subnet>{


    public SubnetToSubnet() {
    }

    @Override
    public org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Subnet apply(org.ow2.sirocco.cloudmanager.model.cimi.Subnet input) {
        org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Subnet result = new org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Subnet();

        result.setId(input.getUuid());
        result.setName(input.getName());
        result.setCidr(input.getCidr());
        result.setEnableDHCP(input.isEnableDhcp());
        //TODO use IpVersion enum everywhere
        result.setIpversion(new ProtocolToVersion().apply(input.getProtocol())==4?org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Subnet.IpVersion.IPV4:org.ow2.sirocco.cloudmanager.api.openstack.neutron.model.Subnet.IpVersion.IPV6);

        if (input.getTenant() != null) {
            result.setTenantId(input.getTenant().getUuid());
        }

        return result;
    }
}
