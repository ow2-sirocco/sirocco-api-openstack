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

package org.ow2.sirocco.cloudmanager.api.openstack.server.functions;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Address;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Addresses;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.NetworkInterfacesToAddresses;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineNetworkInterface;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineNetworkInterfaceAddress;
import org.ow2.sirocco.cloudmanager.model.cimi.Network;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class NetworkInterfacesToAddressesTest {

    @Test
    public void testOneFullFilled() {
        List<MachineNetworkInterface> interfaces = Lists.newArrayList();

        MachineNetworkInterface itf1 = new MachineNetworkInterface();
        itf1.setMacAddress("70:cd:60:ff:fe:ce:e9:30");
        itf1.setNetworkType(Network.Type.PRIVATE);
        List<MachineNetworkInterfaceAddress> addrs = Lists.newArrayList();
        MachineNetworkInterfaceAddress m = new MachineNetworkInterfaceAddress();
        org.ow2.sirocco.cloudmanager.model.cimi.Address a = new org.ow2.sirocco.cloudmanager.model.cimi.Address();
        a.setIp("192.168.0.1");
        a.setProtocol("6");
        m.setAddress(a);
        addrs.add(m);
        itf1.setAddresses(addrs);

        interfaces.add(itf1);

        Addresses addresses = new NetworkInterfacesToAddresses().apply(interfaces);
        assertNotNull(addresses);
        assertEquals(1, addresses.getAddresses().size());
        assertNotNull(addresses.getAddresses().get(itf1.getNetworkType().toString().toLowerCase()));
        assertEquals(1, addresses.getAddresses().get(itf1.getNetworkType().toString().toLowerCase()).size());

        Address aa = addresses.getAddresses().get(itf1.getNetworkType().toString().toLowerCase()).get(0);

        assertEquals(a.getIp(), aa.getAddr());
        assertEquals(itf1.getMacAddress(), aa.getMacAddr());
        assertEquals(a.getProtocol(), "" + aa.getVersion());
    }

    @Test
    public void testNull() {
        Addresses addresses = new NetworkInterfacesToAddresses().apply(null);
        assertNotNull(addresses);
        assertNotNull(addresses.getAddresses());
        assertEquals(0, addresses.getAddresses().size());
    }


}
