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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Address;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Addresses;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineNetworkInterface;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineNetworkInterfaceAddress;

import java.util.List;
import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class NetworkInterfacesToAddresses implements Function<List<MachineNetworkInterface>, Addresses> {

    public NetworkInterfacesToAddresses() {
    }

    @Override
    public Addresses apply(List<MachineNetworkInterface> interfaces) {
        Addresses addresses = new Addresses();
        if (interfaces == null || interfaces.size() == 0) {
            return addresses;
        }

        // get all the network types from interfaces
        Map<String, List<Address>> map = Maps.newHashMap();

        for(MachineNetworkInterface machineNetworkInterface : interfaces) {

            String type = "undefined";
            if (machineNetworkInterface.getNetworkType() != null) {
                type =  machineNetworkInterface.getNetworkType().toString().toLowerCase();
            }
            List<Address> list = Lists.newArrayList();

            for (MachineNetworkInterfaceAddress machineItfAddress : machineNetworkInterface.getAddresses()) {
                Address address = new Address();
                address.setMacAddr(machineNetworkInterface.getMacAddress());
                address.setVersion(new ProtocolToVersion().apply(machineItfAddress.getAddress().getProtocol()));
                address.setAddr(machineItfAddress.getAddress().getIp());
                list.add(address);
            }

            // ensure that we can have multiple networks with the same network type...
            if (map.get(type) == null) {
                map.put(type, list);
            } else {
                List<Address> old = map.get(type);
                old.addAll(list);
                map.put(type, old);
            }
        }

        for(String key : map.keySet()) {
            addresses.add(key, map.get(key));
        }

        return addresses;
    }
}
