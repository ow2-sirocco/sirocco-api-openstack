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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonRootName("access")
public class Access implements Serializable {

	public static final class Service {
		
		@JsonIgnoreProperties(ignoreUnknown=true)
		public static final class Endpoint {
			
			private String region;
			
			private String publicURL;
			
			private String internalURL;
			
			private String adminURL;

            private String tenantName;

            public String getTenantName() {
                return tenantName;
            }

            public void setTenantName(String tenantName) {
                this.tenantName = tenantName;
            }

            /**
			 * @return the region
			 */
			public String getRegion() {
				return region;
			}

			/**
			 * @return the publicURL
			 */
			public String getPublicURL() {
				return publicURL;
			}

			/**
			 * @return the internalURL
			 */
			public String getInternalURL() {
				return internalURL;
			}

			/**
			 * @return the adminURL
			 */
			public String getAdminURL() {
				return adminURL;
			}

            public void setRegion(String region) {
                this.region = region;
            }

            public void setPublicURL(String publicURL) {
                this.publicURL = publicURL;
            }

            public void setInternalURL(String internalURL) {
                this.internalURL = internalURL;
            }

            public void setAdminURL(String adminURL) {
                this.adminURL = adminURL;
            }

            /* (non-Javadoc)
                         * @see java.lang.Object#toString()
                         */
			@Override
			public String toString() {
				return "Endpoint [region=" + region + ", publicURL="
						+ publicURL + ", internalURL=" + internalURL
						+ ", adminURL=" + adminURL + "]";
			}
			
		}
		
		private String type;
		
		private String name;
		
		private List<Endpoint> endpoints;
		
		@JsonProperty("endpoints_links")
		private List<Link> endpointsLinks;

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the endpoints
		 */
		public List<Endpoint> getEndpoints() {
			return endpoints;
		}

		/**
		 * @return the endpointsLinks
		 */
		public List<Link> getEndpointsLinks() {
			return endpointsLinks;
		}

        public void setType(String type) {
            this.type = type;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setEndpoints(List<Endpoint> endpoints) {
            this.endpoints = endpoints;
        }

        public void setEndpointsLinks(List<Link> endpointsLinks) {
            this.endpointsLinks = endpointsLinks;
        }

        /* (non-Javadoc)
                 * @see java.lang.Object#toString()
                 */
		@Override
		public String toString() {
			return "Service [type=" + type + ", name=" + name + ", endpoints="
					+ endpoints + ", endpointsLinks=" + endpointsLinks + "]";
		}

	}

	@JsonIgnoreProperties(ignoreUnknown=true)
	public static final class User {

		@JsonIgnoreProperties(ignoreUnknown=true)
		public static final class Role {

			private String id;

			private String name;

			/**
			 * @return the id
			 */
			public String getId() {
				return id;
			}

			/**
			 * @return the name
			 */
			public String getName() {
				return name;
			}

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            /* (non-Javadoc)
                         * @see java.lang.Object#toString()
                         */
			@Override
			public String toString() {
				return "Role [id=" + id + ", name=" + name + "]";
			}

		}

		private String id;

		private String name;

		private String username;

		private List<Role> roles;

		@JsonProperty("roles_links")
		private List<Link> rolesLinks;

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @return the roles
		 */
		public List<Role> getRoles() {
			return roles;
		}

		/**
		 * @return the rolesLinks
		 */
		public List<Link> getRolesLinks() {
			return rolesLinks;
		}

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setRoles(List<Role> roles) {
            this.roles = roles;
        }

        public void setRolesLinks(List<Link> rolesLinks) {
            this.rolesLinks = rolesLinks;
        }

        /* (non-Javadoc)
                 * @see java.lang.Object#toString()
                 */
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + ", username="
					+ username + ", roles=" + roles + ", rolesLinks="
					+ rolesLinks + "]";
		}

	}

	private Token token;
	
	private List<Service> serviceCatalog;
	
	private User user;
	
	private Map<String, Object> metadata;

	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @return the serviceCatalog
	 */
	public List<Service> getServiceCatalog() {
		return serviceCatalog;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, Object> getMetadata() {
		return metadata;
	}

    public void setToken(Token token) {
        this.token = token;
    }

    public void setServiceCatalog(List<Service> serviceCatalog) {
        this.serviceCatalog = serviceCatalog;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		return "Access [token=" + token + ", serviceCatalog=" + serviceCatalog
				+ ", user=" + user + ", metadata=" + metadata + "]";
	}
	
}
