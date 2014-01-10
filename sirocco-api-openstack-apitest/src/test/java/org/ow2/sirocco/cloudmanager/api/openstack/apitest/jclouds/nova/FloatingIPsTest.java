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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.jclouds.nova;

import com.google.common.collect.FluentIterable;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class FloatingIPsTest extends JcloudsBasedTest {

    private static Logger LOG = LoggerFactory.getLogger(FloatingIPsTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    private FloatingIPApi api() {
        return nova.getApi().getFloatingIPExtensionForZone(getZone()).get();
    }

    @Test
    public void testList() throws CloudProviderException {
        LOG.info("List floating IP");
        Job job = createAddress("testList0");
        Assert.assertNotNull(job.getId());
        Assert.assertNotNull(job.getUuid());
        CloudResource resource = job.getTargetResource();
        Address address = networkManager.getAddressByUuid(resource.getUuid());

        assertEquals(Job.Status.SUCCESS, job.getState());
        FluentIterable<? extends FloatingIP> list = api().list();
        assertEquals("Bad list size", 1, list.size());
        assertEquals(address.getUuid(), list.get(0).getId());
        assertEquals(address.getIp(), list.get(0).getIp());

        LOG.info(list.get(0).toString());
    }

    @Test
    public void testGetOne() throws CloudProviderException {
        LOG.info("Get floating IP");

        Job job = createAddress("testGetOne");
        Address address = networkManager.getAddressByUuid(job.getTargetResource().getUuid());

        FloatingIP floatingIP = api().get(address.getUuid());
        assertNotNull("Can not get floating IP", floatingIP);
        assertEquals(address.getIp(), floatingIP.getIp());
        assertEquals(address.getUuid(), floatingIP.getId());

        LOG.info(floatingIP.toString());
    }

    @Test
    public void testDelete() throws CloudProviderException {
        Job job = createAddress("testDelete");
        CloudResource resource = job.getTargetResource();

        api().delete(resource.getUuid());

        Address a = networkManager.getAddressByUuid(resource.getUuid());
        assertTrue("Address has not be deleted", (a == null || (a != null && (a.getState() == Address.State.DELETED))));
        assertEquals("Address is still in the address list", 0, networkManager.getAddresses().getItems().size());
    }

    @Test
    public void testAddToServer() throws CloudProviderException {
        Machine machine = createMachine("testAddToServer", "imagetestAddToServer", 1, 512, null, true);
        Job job = createAddress("testAddToServerAddress");
        Address address = networkManager.getAddressByUuid(job.getTargetResource().getUuid());

        api().addToServer(address.getIp(), machine.getUuid());

        // TODO : Check that the address is available in the machine...

        machine = machineManager.getMachineByUuid(machine.getUuid());

        // check that the address is linked to the machine
        address = this.networkManager.getAddressByUuid(address.getUuid());
        assertNotNull("Address is not linked to resource", address.getResource());
        Machine attachedMachine = (Machine) address.getResource();
        assertEquals(machine.getUuid(), attachedMachine.getUuid());
    }

    @Test
    public void testDeleteFromServer() throws CloudProviderException {
        Machine machine = createMachine("testDeleteFromServer", "imagetestDeleteFromServer", 1, 512, null, true);
        Job job = createAddress("testDeleteFromServerAddress");
        Address address = networkManager.getAddressByUuid(job.getTargetResource().getUuid());
        networkManager.addAddressToMachine(machine.getUuid(), address.getIp());
        api().removeFromServer(address.getIp(), machine.getUuid());

        address = this.networkManager.getAddressByUuid(address.getUuid());
        assertNull("Address is not unlinked from server", address.getResource());
    }

    private Job createAddress(String name) throws CloudProviderException {
        AddressCreate create = new AddressCreate();
        create.setName(name);
        return this.networkManager.createAddress(create);
    }

}
