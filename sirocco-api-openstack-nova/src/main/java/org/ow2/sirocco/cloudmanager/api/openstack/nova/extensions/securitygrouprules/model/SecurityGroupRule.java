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
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.Rule;

import java.io.Serializable;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("security_group_rule")
public class SecurityGroupRule implements Serializable {

    private String id;

    @JsonProperty("parent_group_id")
    private String parentGroupId;

    @JsonProperty("group")
    private Rule.Group group = new Rule.Group();

    @JsonProperty("from_port")
    private Integer fromPort;

    @JsonProperty("to_port")
    private Integer toPort;

    @JsonProperty("ip_protocol")
    private String ipProtocol;

    @JsonProperty("ip_range")
    private Rule.IpRange ipRange = new Rule.IpRange();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public Rule.Group getGroup() {
        return group;
    }

    public void setGroup(Rule.Group group) {
        this.group = group;
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

    public Rule.IpRange getIpRange() {
        return ipRange;
    }

    public void setIpRange(Rule.IpRange ipRange) {
        this.ipRange = ipRange;
    }
}
