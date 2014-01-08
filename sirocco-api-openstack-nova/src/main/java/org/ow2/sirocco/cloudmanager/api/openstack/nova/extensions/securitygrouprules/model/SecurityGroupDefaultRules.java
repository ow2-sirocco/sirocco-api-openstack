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

import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("security_group_default_rules")
public class SecurityGroupDefaultRules {
    private List<Rule> rules;

    public SecurityGroupDefaultRules() {
        rules = new ArrayList<>();
    }

    @JsonValue
    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
