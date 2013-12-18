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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("network")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Network implements Serializable{
	
	private String status;

	private List<String> subnets;

    private String name;

    @JsonProperty("provider:physical_network")
	private String providerPhyNet;

    @JsonProperty("admin_state_up")
	private boolean adminStateUp;

    @JsonProperty("tenant_id")
	private String tenantId;

    @JsonProperty("provider:network_type")
	private String netType;

    @JsonProperty("router:external")
    private String routerExternal;

    private String id;

    private String shared;

    @JsonProperty("provider:segmentation_id")
	private String providerSegID;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getSubnets() {
        return subnets;
    }

    public void setSubnets(List<String> subnets) {
        this.subnets = subnets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderPhyNet() {
        return providerPhyNet;
    }

    public void setProviderPhyNet(String providerPhyNet) {
        this.providerPhyNet = providerPhyNet;
    }

    public boolean isAdminStateUp() {
        return adminStateUp;
    }

    public void setAdminStateUp(boolean adminStateUp) {
        this.adminStateUp = adminStateUp;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getRouterExternal() {
        return routerExternal;
    }

    public void setRouterExternal(String routerExternal) {
        this.routerExternal = routerExternal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getProviderSegID() {
        return providerSegID;
    }

    public void setProviderSegID(String providerSegID) {
        this.providerSegID = providerSegID;
    }

    @Override
    public String toString() {
        return "Network{" +
                "status='" + status + '\'' +
                ", subnets=" + subnets +
                ", name='" + name + '\'' +
                ", providerPhyNet='" + providerPhyNet + '\'' +
                ", adminStateUp=" + adminStateUp +
                ", tenantId='" + tenantId + '\'' +
                ", netType='" + netType + '\'' +
                ", routerExternal='" + routerExternal + '\'' +
                ", id='" + id + '\'' +
                ", shared='" + shared + '\'' +
                ", providerSegID='" + providerSegID + '\'' +
                '}';
    }
}
