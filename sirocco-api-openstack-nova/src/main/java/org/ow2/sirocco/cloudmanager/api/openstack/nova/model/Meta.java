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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.model;

import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Metadata hack. Result of the metadata get operation returns {"meta": {"foo": "bar"}}
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("meta")
public class Meta implements Serializable {
    private Map<String, String> data;

    public Meta() {
        this.data = new HashMap<String, String>();
    }

    public Meta(Map<String, String> data) {
        this.data = data;
    }

    /**
     * @return the metadata
     */
    @JsonValue
    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
