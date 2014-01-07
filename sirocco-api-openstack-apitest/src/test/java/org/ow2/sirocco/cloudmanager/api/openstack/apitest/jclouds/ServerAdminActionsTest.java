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

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test the server admin actions which are part of the compute API extensions
 * cf http://api.openstack.org/api-ref-compute.html#action
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class ServerAdminActionsTest extends JcloudsBasedTest {

    private static Logger LOG = LoggerFactory.getLogger(ServerAdminActionsTest.class);

    /**
     * Test os-stop
     * @throws org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException
     */
    @Test
    public void testStopServer() throws CloudProviderException {
        LOG.info("Stop server test (os-stop)");

        // create and wait the machine to start
        Machine machine = createMachine("testStopJclouds", "testStopJcloudsImage", 1, 512, null, true);
        assertEquals(Machine.State.STARTED, machine.getState());

        // call stop...
        nova.getApi().getServerApiForZone(getZone()).stop(machine.getUuid());

        // need to wait some time to see if the server is stopped. The REST API returns directly and does not wait job completion
        try {
            waitMachineState(machine, Machine.State.STOPPED, 60);
        } catch (Exception e) {
            fail("Machine has not been stopped after 60 seconds, State is " + machineManager.getMachineByUuid(machine.getUuid()).getState());
        }
        assertEquals(machineManager.getMachineByUuid(machine.getUuid()).getState(), Machine.State.STOPPED);
    }

    @Test
    public void testStartServer() throws CloudProviderException {
        LOG.info("Start server test (os-start)");

        Machine machine = createMachine("testStartJclouds", "testStartJcloudsImage", 1, 512, null, true);
        // stop from the core API
        stopMachine(machine.getUuid(), true);
        try {
            waitMachineState(machineManager.getMachineByUuid(machine.getUuid()), Machine.State.STOPPED, 60);
        } catch (Exception e) {
            fail("Machine can not be stopped from the core API...");
        }

        machine = machineManager.getMachineByUuid(machine.getUuid());
        assertEquals("Machine is not stopped, can not test the start action", Machine.State.STOPPED, machine.getState());

        nova.getApi().getServerApiForZone(getZone()).start(machine.getUuid());
        // false, we do not wait for the job to complete with the REST API call. State will not be equals in some cases,
        // so we have to poll...

        // wait for the server to start...
        try {
            waitMachineState(machineManager.getMachineByUuid(machine.getUuid()), Machine.State.STARTED, 30);
        } catch (Exception e) {
            fail("The machine has not been started even after the wait period. Current state is : " + machineManager.getMachineByUuid(machine.getUuid()).getState());
        }
        assertEquals(machineManager.getMachineByUuid(machine.getUuid()).getState(), Machine.State.STARTED);
    }
}
