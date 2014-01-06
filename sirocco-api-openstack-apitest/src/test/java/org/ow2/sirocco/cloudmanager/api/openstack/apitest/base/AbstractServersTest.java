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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.base;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.AbstractOpenStackTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;

import static junit.framework.TestCase.fail;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class AbstractServersTest extends AbstractOpenStackTest {

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIAndMockProvider("sirocco");
    }

    @Test
    public void testOneMachineList() {
        Machine machine = null;
        try {
            machine = createMachine("mytestmachine", "image", 1, 1024, null, false);
        } catch (CloudProviderException e) {
            e.printStackTrace();
            // this should abort this test class...
            fail(e.getMessage());
        }

        String out = callGetAllServers();
    }

    protected abstract String callGetAllServers();

}
