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
@JsonRootName("subnet")
public class SubnetForUpdate {

    private String name;

    @JsonProperty("network_id")
    private String networkId;

    @JsonProperty("tenant_id")
    private String tenantId;

    @JsonProperty("allocation_pools")
    private List<Pool> list;

    @JsonProperty("gateway_ip")
    private String gw;

    @JsonProperty("ip_version")
    private Subnet.IpVersion ipversion;

    private String cidr;

    private String id;

    @JsonProperty("enable_dhcp")
    private boolean enableDHCP;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<Pool> getList() {
        return list;
    }

    public void setList(List<Pool> list) {
        this.list = list;
    }

    public String getGw() {
        return gw;
    }

    public void setGw(String gw) {
        this.gw = gw;
    }

    public Subnet.IpVersion getIpversion() {
        return ipversion;
    }

    public void setIpversion(Subnet.IpVersion ipversion) {
        this.ipversion = ipversion;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnableDHCP() {
        return enableDHCP;
    }

    public void setEnableDHCP(boolean enableDHCP) {
        this.enableDHCP = enableDHCP;
    }

    @Override
    public String toString() {
        return "SubnetForUpdate{" +
                "name='" + name + '\'' +
                ", networkId='" + networkId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", list=" + list +
                ", gw='" + gw + '\'' +
                ", ipversion=" + ipversion +
                ", cidr='" + cidr + '\'' +
                ", id='" + id + '\'' +
                ", enableDHCP=" + enableDHCP +
                '}';
    }
}
