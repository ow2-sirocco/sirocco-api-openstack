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

import java.io.Serializable;

/**
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("network")
public class NetworkForCreate implements Serializable{
	
	private String name;

    @JsonProperty("admin_state_up")
	private boolean adminStateUp;

    @JsonProperty("provider:network_type")
    private String providerNetworkType;

    @JsonProperty("provider:physical_network")
    private String providerPhysicalNetwork;

    @JsonProperty("provider:segmentation_id")
    private Integer providerSegmentationId;

    @JsonProperty("tenant_id")
    private String tenantId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdminStateUp() {
        return adminStateUp;
    }

    public void setAdminStateUp(boolean adminStateUp) {
        this.adminStateUp = adminStateUp;
    }

    public String getProviderNetworkType() {
        return providerNetworkType;
    }

    public void setProviderNetworkType(String providerNetworkType) {
        this.providerNetworkType = providerNetworkType;
    }

    public String getProviderPhysicalNetwork() {
        return providerPhysicalNetwork;
    }

    public void setProviderPhysicalNetwork(String providerPhysicalNetwork) {
        this.providerPhysicalNetwork = providerPhysicalNetwork;
    }

    public Integer getProviderSegmentationId() {
        return providerSegmentationId;
    }

    public void setProviderSegmentationId(Integer providerSegmentationId) {
        this.providerSegmentationId = providerSegmentationId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "NetworkForCreate{" +
                "name='" + name + '\'' +
                ", adminStateUp=" + adminStateUp +
                ", providerNetworkType='" + providerNetworkType + '\'' +
                ", providerPhysicalNetwork='" + providerPhysicalNetwork + '\'' +
                ", providerSegmentationId=" + providerSegmentationId +
                ", tenantId='" + tenantId + '\'' +
                '}';
    }
}
