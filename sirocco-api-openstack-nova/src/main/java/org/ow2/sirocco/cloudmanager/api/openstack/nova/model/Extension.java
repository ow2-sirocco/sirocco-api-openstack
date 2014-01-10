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

import org.codehaus.jackson.map.annotate.JsonRootName;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Link;

import java.util.Date;
import java.util.List;

/**
 * Extension
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("extension")
public class Extension {

    public String alias;
    public String description;
    public List<Link> links;
    public String name;
    public String namespace;
    public Date updated;

    public Extension() {
    }

    public Extension(String alias, String description, List<Link> links, String name, String namespace, Date updated) {
        this.alias = alias;
        this.description = description;
        this.links = links;
        this.name = name;
        this.namespace = namespace;
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Extension{" +
                "alias='" + alias + '\'' +
                ", description='" + description + '\'' +
                ", links=" + links +
                ", name='" + name + '\'' +
                ", namespace='" + namespace + '\'' +
                ", updated=" + updated +
                '}';
    }
}
