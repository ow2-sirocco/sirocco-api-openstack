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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Required object on server creation
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("server")
public class ServerForCreate implements Serializable {

    public static final class SecurityGroup implements Serializable {

        private String name;

        public SecurityGroup() {
        }

        public SecurityGroup(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * Network for create.
     * From the nova command line client (--nic option):
     *
     * <pre>
     * Create a NIC on the server. Specify option multiple
     * times to create multiple NICs. net-id: attach NIC to
     * network with this UUID (required if no port-id), v4
     * -fixed-ip: IPv4 fixed address for NIC (optional),
     * port-id: attach NIC to port with this UUID (required
     * if no net-id)
     * </pre>
     */
    public static final class Network implements Serializable {

        private String uuid;

        @JsonProperty("fixed_ip")
        private String fixedIp;

        private String port;

        public Network() {
        }

        public Network(String uuid, String fixedIp, String port) {
            this.uuid = uuid;
            this.fixedIp = fixedIp;
            this.port = port;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getFixedIp() {
            return fixedIp;
        }

        public void setFixedIp(String fixedIp) {
            this.fixedIp = fixedIp;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }
    }

    private String name;

    private String adminPass;

    private String imageRef;

    private String flavorRef;

    private String accessIPv4;

    private String accessIPv6;

    @JsonProperty("min_count")
    private Integer min;

    @JsonProperty("max_count")
    private Integer max;

    private String diskConfig;

    @JsonProperty("key_name")
    private String keyName;

    //private List<PersonalityFile> personality = new ArrayList<PersonalityFile>();

    private Map<String, String> metadata = new HashMap<String, String>();

    @JsonProperty("security_groups")
    private List<SecurityGroup> securityGroups = new ArrayList<SecurityGroup>();

    @JsonProperty("networks")
    private List<Network> networks = new ArrayList<Network>();

    @JsonProperty("user_data")
    private String userData;

    @JsonProperty("availability_zone")
    private String availabilityZone;

    @JsonProperty("config_drive")
    private boolean configDrive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public String getFlavorRef() {
        return flavorRef;
    }

    public void setFlavorRef(String flavorRef) {
        this.flavorRef = flavorRef;
    }

    public String getAccessIPv4() {
        return accessIPv4;
    }

    public void setAccessIPv4(String accessIPv4) {
        this.accessIPv4 = accessIPv4;
    }

    public String getAccessIPv6() {
        return accessIPv6;
    }

    public void setAccessIPv6(String accessIPv6) {
        this.accessIPv6 = accessIPv6;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getDiskConfig() {
        return diskConfig;
    }

    public void setDiskConfig(String diskConfig) {
        this.diskConfig = diskConfig;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public List<SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(List<SecurityGroup> securityGroups) {
        this.securityGroups = securityGroups;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    public boolean isConfigDrive() {
        return configDrive;
    }

    public void setConfigDrive(boolean configDrive) {
        this.configDrive = configDrive;
    }

    @Override
    public String toString() {
        return "ServerForCreate{" +
                "name='" + name + '\'' +
                ", adminPass='" + adminPass + '\'' +
                ", imageRef='" + imageRef + '\'' +
                ", flavorRef='" + flavorRef + '\'' +
                ", accessIPv4='" + accessIPv4 + '\'' +
                ", accessIPv6='" + accessIPv6 + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", diskConfig='" + diskConfig + '\'' +
                ", keyName='" + keyName + '\'' +
                ", metadata=" + metadata +
                ", securityGroups=" + securityGroups +
                ", networks=" + networks +
                ", userData='" + userData + '\'' +
                ", availabilityZone='" + availabilityZone + '\'' +
                ", configDrive=" + configDrive +
                '}';
    }
}
