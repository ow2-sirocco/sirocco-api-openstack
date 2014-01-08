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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Test JSON serialization
 * TODO JSON comparator
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class SecurityGroupTest {

    @Test
    public void testSingleGroup() throws IOException {
        SecurityGroup securityGroup = new SecurityGroup();
        securityGroup.setName("group1");
        securityGroup.setDescription("My security group");
        securityGroup.setId(12345);
        securityGroup.setTenantId("admin");

        Rule rule = new Rule();
        rule.setFromPort(22);
        rule.setId(1);
        rule.setIpProtocol("TCP");
        rule.setName("SSH");
        Rule.IpRange range = new Rule.IpRange();
        range.setCidr("10.10.10.0/24");
        rule.setIpRange(range);
        rule.setToPort(22);
        securityGroup.getRules().add(rule);

        JacksonConfigurator configurator = new JacksonConfigurator();
        String out = configurator.getContext(SecurityGroup.class).writeValueAsString(securityGroup);
        System.out.println(out);
        assertNotNull(out);
    }

    @Test
    public void testSecurityGroups() throws IOException {

        SecurityGroup securityGroup = new SecurityGroup();
        securityGroup.setName("group1");
        securityGroup.setDescription("My security group");
        securityGroup.setId(12345);
        securityGroup.setTenantId("admin");

        Rule rule = new Rule();
        rule.setFromPort(22);
        rule.setId(1);
        rule.setIpProtocol("TCP");
        rule.setName("SSH");
        Rule.IpRange range = new Rule.IpRange();
        range.setCidr("10.10.10.0/24");
        rule.setIpRange(range);
        rule.setToPort(22);
        securityGroup.getRules().add(rule);

        SecurityGroups groups = new SecurityGroups();
        groups.getGroups().add(securityGroup);

        JacksonConfigurator configurator = new JacksonConfigurator();
        String out = configurator.getContext(SecurityGroups.class).writeValueAsString(groups);
        System.out.println(out);
        assertNotNull(out);
    }
}
