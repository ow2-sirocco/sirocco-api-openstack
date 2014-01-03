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

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.AsynchronousFault;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Server resource
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@XmlRootElement(name = "server")
@JsonRootName(value = "server")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Server extends Identifiable {

    public String name;
    public List<Link> links = new ArrayList<Link>();
    public String accessIPv4;
    public String accessIPv6;

    public Addresses addresses;

    public String created;

    public Flavor flavor;

    @JsonProperty("hostId")
    @XmlElement(name = "hostId")
    public String hostId;

    public Image image;

    public Metadata metadata;

    public Integer progress;
    public String status;

    @JsonProperty("tenant_id")
    @XmlElement(name = "tenant_id")
    public String tenantId;

    // TODO : Date with format
    public String updated;

    @JsonProperty("user_id")
    @XmlElement(name = "user_id")
    public String userId;

    public String adminPass;

    @JsonProperty("fault")
    public AsynchronousFault fault;

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", links=" + links +
                ", accessIPv4='" + accessIPv4 + '\'' +
                ", accessIPv6='" + accessIPv6 + '\'' +
                ", addresses=" + addresses +
                ", created='" + created + '\'' +
                ", flavor=" + flavor +
                ", hostId='" + hostId + '\'' +
                ", image=" + image +
                ", metadata=" + metadata +
                ", progress=" + progress +
                ", status='" + status + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", updated='" + updated + '\'' +
                ", userId='" + userId + '\'' +
                ", adminPass='" + adminPass + '\'' +
                '}';
    }
}
