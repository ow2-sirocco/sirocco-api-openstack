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

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class Tenants implements Iterable<Tenant>, Serializable {

	@JsonProperty("tenants")
	private List<Tenant> list;

	@JsonProperty("tenants_links")
	private List<Link> links;

	/**
	 * @return the list
	 */
	public List<Tenant> getList() {
		return list;
	}

    public void setList(List<Tenant> list) {
        this.list = list;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    /**
	 * @return the links
	 */
	public List<Link> getLinks() {
		return links;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tenants [list=" + list + ", links=" + links + "]";
	}

	@Override
	public Iterator<Tenant> iterator() {
		return list.iterator();
	}
	
}
