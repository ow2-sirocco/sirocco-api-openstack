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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygrouprules.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.Rule;

import static org.junit.Assert.assertNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class SecurityGroupDefaultRuleTest {

    @Test
    public void testCreate() throws Exception {
        SecurityGroupDefaultRule create = new SecurityGroupDefaultRule();
        Rule rule = new Rule();
        rule.setName("foo");
        rule.setFromPort(22);
        rule.setToPort(22);
        rule.setIpProtocol("TCP");
        create.setRule(rule);

        JacksonConfigurator configurator = new JacksonConfigurator();
        String out = configurator.getContext(SecurityGroupDefaultRule.class).writeValueAsString(create);
        System.out.println(out);
        assertNotNull(out);
    }

}
