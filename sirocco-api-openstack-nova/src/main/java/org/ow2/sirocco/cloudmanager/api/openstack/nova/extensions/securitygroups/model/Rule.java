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

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Security group rule
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Rule {

    private String id;

    private String name;

    @JsonProperty("parent_group_id")
    private Integer parentGroupId;

    @JsonProperty("from_port")
    private Integer fromPort;

    @JsonProperty("to_port")
    private Integer toPort;

    @JsonProperty("ip_protocol")
    private String ipProtocol;

    @JsonProperty("ip_range")
    private IpRange ipRange = new IpRange();

    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(Integer parentGroupId) {
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

    public IpRange getIpRange() {
        return ipRange;
    }

    public void setIpRange(IpRange ipRange) {
        this.ipRange = ipRange;
    }

    /**
     * IP Range CIDR as
     *
     * <pre>
     * {
     *    "ip_range" : {
     *      "cidr" : "10.2.6.0/24"
     *    }
     * }
     * </pre>
     */
    public static final class IpRange implements Serializable {

        private String cidr;

        public String getCidr() {
            return cidr;
        }

        public void setCidr(String cidr) {
            this.cidr = cidr;
        }

        @Override
        public String toString() {
            return "IpRange{" +
                    "cidr='" + cidr + '\'' +
                    '}';
        }
    }

    /**
     * Group as
     *
     * <pre>
     * {
     *    "group": {
     *      "tenant_id": "admin",
     *      "name": "11111"
     *    }
     * }
     * </pre>
     */
    public static final class Group implements Serializable {

        private String name;

        @JsonProperty("tenant_id")
        private String tenantId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "name='" + name + '\'' +
                    ", tenantId='" + tenantId + '\'' +
                    '}';
        }
    }

}
