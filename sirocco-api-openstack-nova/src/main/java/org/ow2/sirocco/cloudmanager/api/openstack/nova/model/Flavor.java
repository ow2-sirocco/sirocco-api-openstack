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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Link;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Flavor resource
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@XmlRootElement(name = "flavor")
@JsonRootName(value = "flavor")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Flavor extends Identifiable {
    public String name;

    public List<Link> links = new ArrayList<Link>();

    public Integer vcpus;

    public Integer ram;

    public String disk;

    @JsonProperty("OS-FLV-EXT-DATA:ephemeral")
    public Integer ephemeral;

    public String swap;

    @JsonProperty("rxtx_factor")
    public Float rxtxFactor;

    @JsonProperty("OS-FLV-DISABLED:disabled")
    public Boolean disabled;

    @JsonProperty("rxtx_quota")
    public Integer rxtxQuota;

    @JsonProperty("rxtx_cap")
    public Integer rxtxCap;

    @JsonProperty("os-flavor-access:is_public")
    public Boolean isPublic;

}
