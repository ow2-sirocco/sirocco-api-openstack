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

package org.ow2.sirocco.cloudmanager.api.openstack.keystone.model;


import java.util.Calendar;

public final class Token {

	private String id;

	private Calendar issued_at;

	private Calendar expires;

	private Tenant tenant;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the issued_at
	 */
	public Calendar getIssued_at() {
	  return issued_at;
	}

	/**
	 * @return the expires
	 */
	public Calendar getExpires() {
		return expires;
	}

	/**
	 * @return the tenant
	 */
	public Tenant getTenant() {
		return tenant;
	}

    public void setId(String id) {
        this.id = id;
    }

    public void setIssued_at(Calendar issued_at) {
        this.issued_at = issued_at;
    }

    public void setExpires(Calendar expires) {
        this.expires = expires;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		return "Token [id=" + id + ", Issued_at=" + issued_at + ", expires=" + expires + ", tenant="
          + tenant + "]";
	}

}