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

package org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.io.Serializable;

@JsonRootName("endpoint")
public class Endpoint implements Serializable {

	private String id;
	
	@JsonProperty("service_id")
	private String serviceId;
	
	private String region;
	
	@JsonProperty("publicurl")
	private String publicURL;
	
	@JsonProperty("internalurl")
	private String internalURL;
	
	@JsonProperty("adminurl")
	private String adminURL;

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

	/**
	 * @return the serviceId
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the publicURL
	 */
	public String getPublicURL() {
		return publicURL;
	}

	/**
	 * @param publicURL the publicURL to set
	 */
	public void setPublicURL(String publicURL) {
		this.publicURL = publicURL;
	}

	/**
	 * @return the internalURL
	 */
	public String getInternalURL() {
		return internalURL;
	}

	/**
	 * @param internalURL the internalURL to set
	 */
	public void setInternalURL(String internalURL) {
		this.internalURL = internalURL;
	}

	/**
	 * @return the adminURL
	 */
	public String getAdminURL() {
		return adminURL;
	}

	/**
	 * @param adminURL the adminURL to set
	 */
	public void setAdminURL(String adminURL) {
		this.adminURL = adminURL;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Endpoint [id=" + id + ", serviceId=" + serviceId + ", region="
				+ region + ", publicURL=" + publicURL + ", internalURL="
				+ internalURL + ", adminURL=" + adminURL + "]";
	}
	
}
