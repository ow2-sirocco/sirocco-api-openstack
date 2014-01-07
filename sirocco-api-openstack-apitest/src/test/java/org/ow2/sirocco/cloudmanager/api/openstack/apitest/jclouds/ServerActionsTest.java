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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.jclouds;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jclouds.openstack.nova.v2_0.domain.RebootType;
import org.jclouds.openstack.nova.v2_0.options.RebuildServerOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Test the server actions (cf http://api.openstack.org/api-ref-compute.html#compute_server-actions)
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class ServerActionsTest extends JcloudsBasedTest {

    private static Logger LOG = LoggerFactory.getLogger(ServerActionsTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    // ACTIONS

    @Test(expected = RuntimeException.class)
    public void testChangePasswordAction() throws CloudProviderException {
        Machine machine = createMachine("testChangePasswordAction", "testChangePasswordAction", 1, 512, null, true);
        // not implemented, will return HTTP 500
        nova.getApi().getServerApiForZone(getZone()).changeAdminPass(machine.getUuid(), "foobar");
        fail("Not implemented on the server, you should have an exception!");
    }

    @Test(expected = RuntimeException.class)
    public void testConfirmResizeAction() throws CloudProviderException {
        Machine machine = createMachine("testConfirmResizeAction", "testConfirmResizeAction", 1, 512, null, true);
        // not implemented, will return HTTP 500
        nova.getApi().getServerApiForZone(getZone()).confirmResize(machine.getUuid());
        fail("Not implemented on the server, you should have an exception!");
    }

    @Test
    public void testCreateImageAction() throws CloudProviderException {
        Machine machine = createMachine("testCreateImageAction", "testCreateImageAction", 1, 512, null, true);
        String image = nova.getApi().getServerApiForZone(getZone()).createImageFromServer("image", machine.getUuid());

        LOG.info("Machine image created : " + image);

        assertNotNull("Can not get image from the API call", image);
    }

    @Test
    public void testRebootActionHard() throws CloudProviderException {
        LOG.info("Testing hard reboot");

        Machine machine = createMachine("testRebootActionHard", "testRebootActionHard", 1, 512, null, true);
        try {
            waitMachineState(machine, Machine.State.STARTED, 60);
        } catch (Exception e) {
            fail("Machine can not be started...");
        }

        Date d1 = new Date();
        nova.getApi().getServerApiForZone(getZone()).reboot(machine.getUuid(), RebootType.HARD);
        Date d2 = machineManager.getMachineByUuid(machine.getUuid()).getUpdated();

        // wait for the machine to be in started mode
        try {
            waitMachineState(machine, Machine.State.STARTED, 60);
        } catch (Exception e) {
            fail("Machine is not in started mode after timeout, current state is " + getUpdatedMachine(machine).getState());
        }
        //assertTrue("Machine has not been rebooted", d2 != null && d2.compareTo(d1) > 0);
    }

    @Test
    public void testRebootActionSoft() throws CloudProviderException {
        LOG.info("Testing soft reboot");

        Machine machine = createMachine("testRebootActionSoft", "testRebootActionSoft", 1, 512, null, true);
        Date d1 = new Date();
        nova.getApi().getServerApiForZone(getZone()).reboot(machine.getUuid(), RebootType.SOFT);
        Date d2 = machineManager.getMachineByUuid(machine.getUuid()).getUpdated();

        // wait for the machine to be in started mode
        try {
            waitMachineState(machine, Machine.State.STARTED, 60);
        } catch (Exception e) {
            fail("Machine is not in started mode after timeout, current state is " + getUpdatedMachine(machine).getState());
        }
        //assertTrue("Machine has not been rebooted", d2 != null && d2.compareTo(d1) > 0);
    }

    @Test(expected = RuntimeException.class)
    public void testRebuildAction() throws CloudProviderException {
        Machine machine = createMachine("testRebuildAction", "testRebuildAction", 1, 512, null, true);
        nova.getApi().getServerApiForZone(getZone()).rebuild(machine.getUuid(), RebuildServerOptions.Builder.withImage(machine.getImage().getUuid()));
        fail("Not implemented on the server");
    }

    @Test(expected = RuntimeException.class)
    public void testResize() throws CloudProviderException {
        Machine machine = createMachine("testResize", "testResize", 1, 512, null, false);
        // TODO : Another flavor (config) then check that the server uses the new one
        nova.getApi().getServerApiForZone(getZone()).resize(machine.getUuid(), machine.getConfig().getUuid());
    }

    @Test(expected = RuntimeException.class)
    public void testRevertResize() throws CloudProviderException {
        Machine machine = createMachine("testRevertResize", "testRevertResize", 1, 512, null, false);
        nova.getApi().getServerApiForZone(getZone()).revertResize(machine.getUuid());
        // TODO : Check it...
        fail("Not implemented on the server");
    }
}
