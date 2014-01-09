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

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Add rule to a security group bean.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("security_group_rule")
public class SecurityGroupRuleForCreate {

    /**
     * The ID of the security group to add the rule from (and not to!)...
     */
    @JsonProperty("group_id")
    private String groupId;

    /**
     * Parent group ID is used to define the target ie where to add the rule. This parameter name is conflicting with groupId...
     */
    @JsonProperty("parent_group_id")
    private String parentGroupId;

    @JsonProperty("from_port")
    private Integer fromPort;

    @JsonProperty("to_port")
    private Integer toPort;

    @JsonProperty("ip_protocol")
    private String ipProtocol;

    private String cidr;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public Integer getFromPort() {
        return fromPort;
    }

    public void setFromPort(Integer fromPort) {
        this.fromPort = fromPort;
    }

    public Integer getToPort() {
        return toPort;
    }

    public void setToPort(Integer toPort) {
        this.toPort = toPort;
    }

    public String getIpProtocol() {
        return ipProtocol;
    }

    public void setIpProtocol(String ipProtocol) {
        this.ipProtocol = ipProtocol;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    @Override
    public String toString() {
        return "SecurityGroupRuleForCreate{" +
                "groupId='" + groupId + '\'' +
                ", parentGroupId='" + parentGroupId + '\'' +
                ", fromPort=" + fromPort +
                ", toPort=" + toPort +
                ", ipProtocol='" + ipProtocol + '\'' +
                ", cidr='" + cidr + '\'' +
                '}';
    }
}
