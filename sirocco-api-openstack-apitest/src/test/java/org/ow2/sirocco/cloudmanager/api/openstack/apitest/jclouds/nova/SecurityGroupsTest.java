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
import org.jclouds.openstack.nova.v2_0.domain.SecurityGroup;
import org.jclouds.openstack.nova.v2_0.extensions.SecurityGroupApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroupCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class SecurityGroupsTest extends JcloudsBasedTest {

    private static Logger LOG = LoggerFactory.getLogger(SecurityGroupsTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    private SecurityGroupApi api() {
        return nova.getApi().getSecurityGroupExtensionForZone(getZone()).get();
    }

    @Test
    public void testCreate() {
        String name = "testCreateGroup";
        String description = "Sirocco sec group";
        SecurityGroup group = api().createWithDescription(name, description);
        assertNotNull(group);
        assertEquals(name, group.getName());
        assertEquals(description, group.getDescription());
    }

    @Test
    public void testSingle() throws Exception {
        String name = "testSingleGroup";
        String desc = "My description";

        SecurityGroupCreate create = new SecurityGroupCreate();
        create.setName(name);
        create.setDescription(desc);
        Job job = networkManager.createSecurityGroup(create);
        waitForJobCompletion(job);
        org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroup sg = networkManager.getSecurityGroupByUuid(job.getTargetResource().getUuid());

        SecurityGroup group = api().get("" + sg.getUuid());
        assertNotNull(group);
        assertEquals(name, group.getName());
        assertEquals(desc, group.getDescription());
    }

    @Test
    public void testList() throws Exception {
        String name = "testSingleGroup";
        String desc = "My description";

        SecurityGroupCreate create1 = new SecurityGroupCreate();
        create1.setName(name + "A");
        create1.setDescription(desc);
        Job job = networkManager.createSecurityGroup(create1);
        waitForJobCompletion(job);

        SecurityGroupCreate create2 = new SecurityGroupCreate();
        create2.setName(name + "A2");
        create2.setDescription(desc);
        job = networkManager.createSecurityGroup(create2);
        waitForJobCompletion(job);

        FluentIterable<? extends SecurityGroup> groups = api().list();
        assertEquals("Can not retrieve the right number of groups", 2, groups.size());
    }

    @Test
    public void testDelete() throws Exception {
        String name = "testDelete";
        String desc = "My description";

        SecurityGroupCreate create1 = new SecurityGroupCreate();
        create1.setName(name);
        create1.setDescription(desc);
        Job job = networkManager.createSecurityGroup(create1);
        waitForJobCompletion(job);
        org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroup sg = networkManager.getSecurityGroupByUuid(job.getTargetResource().getUuid());

        assertEquals("Can not create the security group to delete", 1, networkManager.getSecurityGroups().getItems().size());
        api().delete("" + sg.getUuid());

        // wait for the security group to be in delete state...
        waitSecurityGroupState(sg, org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroup.State.DELETED, 20);

        sg = networkManager.getSecurityGroupByUuid(sg.getUuid());
        assertTrue("Security group has not been deleted", sg == null || sg.getState() == org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroup.State.DELETED);
    }
}
