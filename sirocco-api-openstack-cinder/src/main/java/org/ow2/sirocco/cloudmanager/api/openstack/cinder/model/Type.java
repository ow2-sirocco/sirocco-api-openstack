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

package org.ow2.sirocco.cloudmanager.api.openstack.cinder.model;

import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("volume_types")
public class Type {

    private String id;

    private String name;

    // TODO : Check the extra_specs type
    private ExtraSpecs extraSpecs;


    public ExtraSpecs getExtraSpecs() {
        return extraSpecs;
    }

    public void setExtraSpecs(ExtraSpecs extraSpecs) {
        this.extraSpecs = extraSpecs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @JsonRootName("extra_specs")
    public static class ExtraSpecs {

        private Map<String, String> specs;

        public ExtraSpecs() {
            this.specs = new HashMap<String, String>();
        }

        public ExtraSpecs(Map<String, String> specs) {
            this.specs = specs;
        }

        @JsonValue
        public Map<String, String> getSpecs() {
            return specs;
        }

    }
}
