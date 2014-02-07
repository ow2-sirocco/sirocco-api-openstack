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
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
@JsonRootName("ports")
public class Ports implements Iterable<Port>, Serializable {
	
	private List<Port> list;
	
	/**
	 * @return the list
	 */
	@JsonValue
	public List<Port> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Port> list) {
		this.list = list;
	}

	public Iterator<Port> iterator() {
		return list.iterator();
	}

    @Override
    public String toString() {
        return "Ports{" +
                "list=" + list +
                '}';
    }
}
