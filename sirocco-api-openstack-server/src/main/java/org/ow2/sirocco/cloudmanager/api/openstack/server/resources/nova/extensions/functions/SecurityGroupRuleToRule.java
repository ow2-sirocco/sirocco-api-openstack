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
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.Rule;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroupRule;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class SecurityGroupRuleToRule implements Function<SecurityGroupRule, Rule> {

    @Override
    public Rule apply(SecurityGroupRule input) {
        Rule rule = new Rule();
        if (input.getSourceIpRange() != null) {
            Rule.IpRange range = new Rule.IpRange();
            range.setCidr(input.getSourceIpRange());
            rule.setIpRange(range);
        }
        rule.setId(input.getUuid());
        rule.setToPort(input.getToPort());
        rule.setFromPort(input.getFromPort());
        rule.setName(input.getUuid());
        rule.setIpProtocol(input.getIpProtocol());
        if (input.getParentGroup() != null) {
            rule.setParentGroupId(input.getParentGroup().getId());
        }
        if (input.getSourceGroup() != null) {
            Rule.Group group = new Rule.Group();
            if (input.getSourceGroup().getTenant() != null) {
                group.setTenantId(input.getSourceGroup().getTenant().getUuid());
            }
            group.setName(input.getSourceGroup().getName());
            rule.setGroup(group);
        }
        return rule;
    }
}
