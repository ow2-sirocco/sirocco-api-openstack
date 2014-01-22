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

package org.ow2.sirocco.cloudmanager.api.openstack.keystone.model.authentication;

import org.codehaus.jackson.map.annotate.JsonRootName;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.model.Authentication;

@JsonRootName("auth")
public class AccessKey extends Authentication {
	
	public static final class ApiAccessKeyCredentials {
		
		private String accessKey;
		
		private String secretKey;

		/**
		 * @return the accessKey
		 */
		public String getAccessKey() {
			return accessKey;
		}

		/**
		 * @param accessKey the accessKey to set
		 */
		public void setAccessKey(String accessKey) {
			this.accessKey = accessKey;
		}

		/**
		 * @return the secretKey
		 */
		public String getSecretKey() {
			return secretKey;
		}

		/**
		 * @param secretKey the secretKey to set
		 */
		public void setSecretKey(String secretKey) {
			this.secretKey = secretKey;
		}
		
	}
	
	private ApiAccessKeyCredentials apiAccessKeyCredentials = new ApiAccessKeyCredentials();
	
	public AccessKey() {
		
	}
	
	public AccessKey(String accessKey, String secretKey) {
		apiAccessKeyCredentials.setAccessKey(accessKey);
		apiAccessKeyCredentials.setSecretKey(secretKey);
	}

	/**
	 * @return the apiAccessKeyCredentials
	 */
	public ApiAccessKeyCredentials getApiAccessKeyCredentials() {
		return apiAccessKeyCredentials;
	}

	/**
	 * @param apiAccessKeyCredentials the apiAccessKeyCredentials to set
	 */
	public void setApiAccessKeyCredentials(
			ApiAccessKeyCredentials apiAccessKeyCredentials) {
		this.apiAccessKeyCredentials = apiAccessKeyCredentials;
	}
	
}
