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

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@JsonRootName("subnet")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subnet implements Serializable{
	
	private String name;

    @JsonProperty("enable_dhcp")
	private boolean enableDHCP;

    @JsonProperty("network_id")
	private String networkId;

    @JsonProperty("tenant_id")
	private String tenantId;

    @JsonProperty("dns_nameservers")
	private List<String> dnsNames;

    @JsonProperty("allocation_pools")
	private List<Pool> list;

    @JsonProperty("host_routes")
	private List<String> hostRoutes;

    @JsonProperty("ip_version")
	private IpVersion ipversion;

    @JsonProperty("gateway_ip")
	private String gw;

    private String cidr;

    private String id;
	
	public static enum IpVersion implements Serializable {
		IPV4(4), IPV6(6);
		private int code;

		IpVersion(int code) {
			this.code = code;
		}

		@JsonValue
		public int code() {
			return code;
		}

		@JsonCreator
		public static IpVersion valueOf(int value) {
			for (IpVersion ipVersion : IpVersion.values()) {
				if (ipVersion.code() == value) {
					return ipVersion;
				}
			}
			return IPV4;
		}

		@Override
		public String toString() {
			return String.valueOf(code);
		}
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
	 * @return the enableDHCP
	 */
	public boolean isEnableDHCP() {
		return enableDHCP;
	}

	/**
	 * @param enableDHCP the enableDHCP to set
	 */
	public void setEnableDHCP(boolean enableDHCP) {
		this.enableDHCP = enableDHCP;
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

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the dnsNames
	 */
	public List<String> getDnsNames() {
		return dnsNames;
	}

	/**
	 * @param dnsNames the dnsNames to set
	 */
	public void setDnsNames(List<String> dnsNames) {
		this.dnsNames = dnsNames;
	}

	/**
	 * @return the list
	 */
	public List<Pool> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Pool> list) {
		this.list = list;
	}

	/**
	 * @return the hostRoutes
	 */
	public List<String> getHostRoutes() {
		return hostRoutes;
	}

	/**
	 * @param hostRoutes the hostRoutes to set
	 */
	public void setHostRoutes(List<String> hostRoutes) {
		this.hostRoutes = hostRoutes;
	}

	/**
	 * @return the ipversion
	 */
	public IpVersion getIpversion() {
		return ipversion;
	}

	/**
	 * @param ipversion the ipversion to set
	 */
	public void setIpversion(IpVersion ipversion) {
		this.ipversion = ipversion;
	}

	/**
	 * @return the gw
	 */
	public String getGw() {
		return gw;
	}

	/**
	 * @param gw the gw to set
	 */
	public void setGw(String gw) {
		this.gw = gw;
	}

	/**
	 * @return the cidr
	 */
	public String getCidr() {
		return cidr;
	}

	/**
	 * @param cidr the cidr to set
	 */
	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

    @Override
    public String toString() {
        return "Subnet{" +
                "name='" + name + '\'' +
                ", enableDHCP=" + enableDHCP +
                ", networkId='" + networkId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", dnsNames=" + dnsNames +
                ", list=" + list +
                ", hostRoutes=" + hostRoutes +
                ", ipversion=" + ipversion +
                ", gw='" + gw + '\'' +
                ", cidr='" + cidr + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
