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
@JsonRootName("router")
public class RouterForCreate {

    @JsonProperty("name")
	String name;

    private List<String> routers;

    @JsonProperty("admin_state_up")
	String admin_state_up;

    @JsonProperty("status")
	String status;

    @JsonProperty("external_gateway_info")
	String externalGatewayInfo;

    @JsonProperty("tenant_id")
	String tenantId;

    @JsonProperty("id")
	String id;

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getRouters() {
		return routers;
	}
	public void setRouters(List<String> routers) {
		this.routers = routers;
	}
	public String getAdmin_state_up() {
		return admin_state_up;
	}
	public void setAdmin_state_up(String admin_state_up) {
		this.admin_state_up = admin_state_up;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExternalGatewayInfo() {
		return externalGatewayInfo;
	}
	public void setExternalGatewayInfo(String externalGatewayInfo) {
		this.externalGatewayInfo = externalGatewayInfo;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

    @Override
    public String toString() {
        return "RouterForCreate{" +
                "name='" + name + '\'' +
                ", routers=" + routers +
                ", admin_state_up='" + admin_state_up + '\'' +
                ", status='" + status + '\'' +
                ", externalGatewayInfo='" + externalGatewayInfo + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
