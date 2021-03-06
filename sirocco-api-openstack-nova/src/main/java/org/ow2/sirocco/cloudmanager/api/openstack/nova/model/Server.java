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
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.Resource;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroups;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Server resource
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@XmlRootElement(name = "server")
@JsonRootName(value = "server")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Server extends Resource {

    public String accessIPv4;

    public String accessIPv6;

    public Addresses addresses;

    public Date created;

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

    public Date updated;

    @JsonProperty("user_id")
    @XmlElement(name = "user_id")
    public String userId;

    public String adminPass;

    @JsonProperty("security_groups")
    public SecurityGroups securityGroups;

    @JsonProperty("fault")
    public AsynchronousFault fault;

    @JsonProperty("OS-EXT-STS:task_state")
    public String taskState;

    @JsonProperty("OS-EXT-STS:power_state")
    public String powerState;

    @JsonProperty("OS-EXT-STS:vm_state")
    public String vmState;

    @Override
    public String toString() {
        return "Server{" +
                "accessIPv4='" + accessIPv4 + '\'' +
                ", accessIPv6='" + accessIPv6 + '\'' +
                ", addresses=" + addresses +
                ", created=" + created +
                ", flavor=" + flavor +
                ", hostId='" + hostId + '\'' +
                ", image=" + image +
                ", metadata=" + metadata +
                ", progress=" + progress +
                ", status='" + status + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", updated=" + updated +
                ", userId='" + userId + '\'' +
                ", adminPass='" + adminPass + '\'' +
                ", securityGroups=" + securityGroups +
                ", fault=" + fault +
                ", taskState='" + taskState + '\'' +
                ", powerState='" + powerState + '\'' +
                ", vmState='" + vmState + '\'' +
                '}';
    }
}
