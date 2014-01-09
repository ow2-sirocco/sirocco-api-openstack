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
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;

/**
 * TODO : JSON compare
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class SecurityGroupRuleForCreateTest {

    @Test
    public void testCreate() throws Exception {
        SecurityGroupRuleForCreate create = new SecurityGroupRuleForCreate();
        create.setIpProtocol("tcp");
        create.setToPort(8080);
        create.setFromPort(8080);
        create.setGroupId(UUID.randomUUID().toString());

        JacksonConfigurator configurator = new JacksonConfigurator();
        String out = configurator.getContext(SecurityGroupRuleForCreate.class).writeValueAsString(create);
        System.out.println(out);
        assertNotNull(out);
    }
}
