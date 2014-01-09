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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jclouds.openstack.nova.v2_0.domain.Ingress;
import org.jclouds.openstack.nova.v2_0.domain.IpProtocol;
import org.jclouds.openstack.nova.v2_0.domain.SecurityGroupRule;
import org.jclouds.openstack.nova.v2_0.extensions.SecurityGroupApi;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroup;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroupCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
@Ignore
public class SecurityGroupRulesTest extends JcloudsBasedTest {

    private static Logger LOG = LoggerFactory.getLogger(SecurityGroupsTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    private SecurityGroupApi api() {
        return nova.getApi().getSecurityGroupExtensionForZone(getZone()).get();
    }

    @Test
    public void testCidr() throws Exception {
        SecurityGroupCreate create = new SecurityGroupCreate();
        create.setName("testCidrGroup");
        Job job = networkManager.createSecurityGroup(create);
        waitForJobCompletion(job);

        SecurityGroup group = networkManager.getSecurityGroupByUuid(job.getTargetResource().getUuid());
        assertNotNull("Can not create group for test", group);
        assertEquals("Invalid init state", 0, group.getRules().size());

        Ingress ingress = Ingress.builder().ipProtocol(IpProtocol.TCP).fromPort(22).toPort(22).build();
        String cidr = "0.0.0.0/0";
        SecurityGroupRule rule = api().createRuleAllowingCidrBlock(group.getUuid(), ingress, cidr);

        // wait for the rule to be added...
        // TODO : Poll group

        group = networkManager.getSecurityGroupByUuid(group.getUuid());

        assertNotNull(group);
        assertNotNull(group.getRules());
        assertEquals(1, group.getRules().size());

        org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroupRule securityGroupRule = group.getRules().get(0);
        assertTrue("from port differs", ingress.getFromPort() == securityGroupRule.getFromPort());
        assertTrue("to port differs", ingress.getToPort() == securityGroupRule.getToPort());
        assertTrue("IP protocol differs", ingress.getIpProtocol().value().equalsIgnoreCase(securityGroupRule.getIpProtocol()));
        assertEquals("CIDR differs", cidr, securityGroupRule.getSourceIpRange());

        assertEquals(create.getName(), securityGroupRule.getParentGroup());
    }

    @Test
    public void testCreateRuleAllowingSecurityGroupId() throws Exception {
        SecurityGroupCreate create = new SecurityGroupCreate();
        create.setName("testCreateRuleAllowingSecurityGroupId");
        Job job = networkManager.createSecurityGroup(create);
        waitForJobCompletion(job);

        String targetId = job.getTargetResource().getUuid();

        create = new SecurityGroupCreate();
        create.setName("testCreateRuleAllowingSecurityGroupIdSource");
        job = networkManager.createSecurityGroup(create);
        waitForJobCompletion(job);

        String sourceId = job.getTargetResource().getUuid();

        SecurityGroup targetGroup = networkManager.getSecurityGroupByUuid(targetId);
        SecurityGroup sourceGroup = networkManager.getSecurityGroupByUuid(sourceId);

        Ingress ingress = Ingress.builder().ipProtocol(IpProtocol.TCP).fromPort(22).toPort(22).build();
        SecurityGroupRule rule = api().createRuleAllowingSecurityGroupId(targetId, ingress, sourceId);

        // wait for the rule to be added...
        // TODO : Poll group

        SecurityGroup group = networkManager.getSecurityGroupByUuid(targetId);

        assertNotNull(group);
        assertNotNull(group.getRules());
        assertEquals(1, group.getRules().size());

        org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroupRule securityGroupRule = group.getRules().get(0);
        assertTrue("from port differs", ingress.getFromPort() == securityGroupRule.getFromPort());
        assertTrue("to port differs", ingress.getToPort() == securityGroupRule.getToPort());
        assertTrue("IP protocol differs", ingress.getIpProtocol().value().equalsIgnoreCase(securityGroupRule.getIpProtocol()));
        assertNotNull(securityGroupRule.getSourceGroup());
        assertEquals(sourceId, securityGroupRule.getSourceGroup().getUuid());
    }
}
