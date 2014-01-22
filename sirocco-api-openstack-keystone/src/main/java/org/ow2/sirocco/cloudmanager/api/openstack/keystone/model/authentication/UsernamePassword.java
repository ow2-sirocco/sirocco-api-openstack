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

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.model.Authentication;

import java.io.Serializable;

@JsonRootName("auth")
public class UsernamePassword extends Authentication {

    @JsonProperty("passwordCredentials")
    private PasswordCredentials passwordCredentials;

    public static final class PasswordCredentials implements Serializable {
		
		private String username;
		
		private String password;

		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

        @Override
        public String toString() {
            return "PasswordCredentials{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
	
	/**
	 * @return the passwordCredentials
	 */
	public PasswordCredentials getPasswordCredentials() {
		return passwordCredentials;
	}

	/**
	 * @param passwordCredentials the passwordCredentials to set
	 */
	public void setPasswordCredentials(PasswordCredentials passwordCredentials) {
		this.passwordCredentials = passwordCredentials;
	}

    @Override
    public String toString() {
        return "UsernamePassword{" +
                "passwordCredentials=" + passwordCredentials +
                '}';
    }
}
