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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.Tenant;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertNotNull;

/**
 * Validates that all the methods in the AbstractOpenStackTest works.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class ValidateAbstractOpenStackTest extends AbstractOpenStackTest {

    private static Logger LOG = LoggerFactory.getLogger(ValidateAbstractOpenStackTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIAndMockProvider("ValidateAbstractOpenStackTest");
    }

    @Before
    public void init() {
        if (this.identityContext == null) {
            fail("Identity context should not be null!!!");
        }
        this.identityContext.setUserName("guest");
        this.identityContext.setTenantName(tenantName);
    }

    @Test
    public void testTenant() {
        Tenant tenant = getOrCreateTenant("yolo");
        assertNotNull(tenant);
    }

    @Test
    public void testCreateImage() throws CloudProviderException {
        getOrCreateTenant(tenantName);
        createImage("imageXXX");
    }

    @Test
    public void testUser() throws CloudProviderException {
        User user = createUser("foo", "bar", "tenantX");
        assertNotNull(user);
    }

    @Test
    public void testCreateEnv() throws Exception {
        createEnv();
    }

    @Test
    public void testMachineConfig() throws CloudProviderException {
        getOrCreateTenant(tenantName);
        MachineConfiguration config = createMachineConfiguration("configA", 1, 512, null);
        assertNotNull(config);
    }

    @Test
    public void testCreateMachine() throws CloudProviderException {
        getOrCreateTenant(tenantName);
        Machine machine = createMachine("machineB", "imageA", 2, 1024, null, false);
        assertNotNull(machine);
    }
}
