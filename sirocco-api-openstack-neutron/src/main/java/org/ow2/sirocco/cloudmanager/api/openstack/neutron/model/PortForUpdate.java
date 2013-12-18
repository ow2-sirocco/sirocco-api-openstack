/**
 * SIROCCO
 * Copyright (C) 2013 France Telecom
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

package org.ow2.sirocco.cloudmanager.api.openstack.neutron.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.List;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("port")
public class PortForUpdate {

    private String status;

    private String name;

    @JsonProperty("admin_state_up")
    private String adminStateUp;

    @JsonProperty("tenant_id")
    private String tenantId;

    @JsonProperty("mac_address")
    private String macAddress;

    @JsonProperty("fixed_ips")
    private List<Port.Ip> list;

    @JsonProperty("security_groups")
    private String securityGroups;

    @JsonProperty("network_id")
    private String networkId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminStateUp() {
        return adminStateUp;
    }

    public void setAdminStateUp(String adminStateUp) {
        this.adminStateUp = adminStateUp;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public List<Port.Ip> getList() {
        return list;
    }

    public void setList(List<Port.Ip> list) {
        this.list = list;
    }

    public String getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(String securityGroups) {
        this.securityGroups = securityGroups;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    @Override
    public String toString() {
        return "PortForUpdate{" +
                "status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", adminStateUp='" + adminStateUp + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", list=" + list +
                ", securityGroups='" + securityGroups + '\'' +
                ", networkId='" + networkId + '\'' +
                '}';
    }
}
