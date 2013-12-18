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

/**
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("port")
public class PortForCreate {

	@JsonProperty("admin_state_up")
	private boolean adminStateUp;

    @JsonProperty("device_id")
	private String deviceId;

    private String name;

    @JsonProperty("network_id")
	private String networkId;

    @JsonProperty("mac_address")
    private String macAddress;

    @JsonProperty("device_owner")
    private String deviceOwner;

    @JsonProperty("tenant_id")
    private String tenantId;
	
	/**
	 * @return the adminStateUp
	 */
	public boolean isAdminStateUp() {
		return adminStateUp;
	}
	
	/**
	 * @param adminStateUp the adminStateUp to set
	 */
	public void setAdminStateUp(boolean adminStateUp) {
		this.adminStateUp = adminStateUp;
	}
	
	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}
	
	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the networkId
	 */
	public String getNetworkId() {
		return networkId;
	}
	
	/**
	 * @param networkId the networkId to set
	 */
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDeviceOwner() {
        return deviceOwner;
    }

    public void setDeviceOwner(String deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "PortForCreate{" +
                "adminStateUp=" + adminStateUp +
                ", deviceId='" + deviceId + '\'' +
                ", name='" + name + '\'' +
                ", networkId='" + networkId + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", deviceOwner='" + deviceOwner + '\'' +
                ", tenantId='" + tenantId + '\'' +
                '}';
    }
}
