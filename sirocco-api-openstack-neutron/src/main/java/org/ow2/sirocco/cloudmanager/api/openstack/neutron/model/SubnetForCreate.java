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
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.io.Serializable;
import java.util.List;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("subnet")
public class SubnetForCreate implements Serializable {

    private String name;

    @JsonProperty("network_id")
    private String networkId;

    @JsonProperty("ip_version")
    private int ipVersion;

    private String cidr;

    @JsonProperty("allocation_pools")
    private List<Pool> list;

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the id
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @param id the id to set
     */
    public void setNetworkId(String id) {
        this.networkId = id;
    }


    /**
     * @return the ipVersion
     */
    public int getIpVersion() {
        return ipVersion;
    }

    /**
     * @param ipVersion the ipVersion to set
     */
    public void setIpVersion(int ipVersion) {
        this.ipVersion = ipVersion;
    }

    /**
     * @return the cidr
     */
    public String getCidr() {
        return cidr;
    }

    /**
     * @param cidr the cidr to set
     */
    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    /**
     * @return the list
     */
    public List<Pool> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<Pool> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SubnetForCreate{" +
                "name='" + name + '\'' +
                ", networkId='" + networkId + '\'' +
                ", ipVersion=" + ipVersion +
                ", cidr='" + cidr + '\'' +
                ", list=" + list +
                '}';
    }
}
