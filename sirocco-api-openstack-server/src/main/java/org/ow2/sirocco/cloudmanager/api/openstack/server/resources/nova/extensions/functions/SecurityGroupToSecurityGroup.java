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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroup;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class SecurityGroupToSecurityGroup implements Function<SecurityGroup, org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroup> {

    @Override
    public org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroup apply(SecurityGroup input) {
        org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroup result = new org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroup();
        result.setName(input.getName());
        if (input.getTenant() != null) {
            result.setTenantId(input.getTenant().getUuid());
        }
        result.setDescription(input.getDescription());
        result.setId(input.getId());
        if (input.getRules() != null) {
            result.setRules(Lists.transform(input.getRules(), new SecurityGroupRuleToRule()));
        }
        return result;
    }
}
