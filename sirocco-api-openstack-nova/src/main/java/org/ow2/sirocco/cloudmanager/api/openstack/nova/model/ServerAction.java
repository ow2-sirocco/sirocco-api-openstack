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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@JsonRootName("serveraction")
public abstract class ServerAction {

    @JsonRootName("changePassword")
    public static final class ChangePassword extends ServerAction {

        private String adminPass;

        public ChangePassword() {
            super();
            // TODO Auto-generated constructor stub
        }

        public ChangePassword(String adminPass) {
            this.adminPass = adminPass;
        }

        /**
         * @return the adminPass
         */
        public String getAdminPass() {
            return adminPass;
        }

        /**
         * @param adminPass the adminPass to set
         */
        public void setAdminPass(String adminPass) {
            this.adminPass = adminPass;
        }

    }


    @JsonRootName("reboot")
    public static final class Reboot extends ServerAction {

        private String type;

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

    }

    @JsonRootName("rebuild")
    public static final class Rebuild extends ServerAction {

        private String imageRef;

        private String name;

        private String adminPass;

        private String accessIPv4;

        private String accessIPv6;

        private Map<String, String> metadata = new HashMap<String, String>();

       // private List<PersonalityFile> personality = new ArrayList<PersonalityFile>();

        @JsonProperty("OS-DCF:diskConfig")
        private String diskConfig;

        /**
         * @return the imageRef
         */
        public String getImageRef() {
            return imageRef;
        }

        /**
         * @param imageRef the imageRef to set
         */
        public void setImageRef(String imageRef) {
            this.imageRef = imageRef;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the adminPass
         */
        public String getAdminPass() {
            return adminPass;
        }

        /**
         * @param adminPass the adminPass to set
         */
        public void setAdminPass(String adminPass) {
            this.adminPass = adminPass;
        }

        /**
         * @return the accessIPv4
         */
        public String getAccessIPv4() {
            return accessIPv4;
        }

        /**
         * @param accessIPv4 the accessIPv4 to set
         */
        public void setAccessIPv4(String accessIPv4) {
            this.accessIPv4 = accessIPv4;
        }

        /**
         * @return the accessIPv6
         */
        public String getAccessIPv6() {
            return accessIPv6;
        }

        /**
         * @param accessIPv6 the accessIPv6 to set
         */
        public void setAccessIPv6(String accessIPv6) {
            this.accessIPv6 = accessIPv6;
        }

        /**
         * @return the metadata
         */
        public Map<String, String> getMetadata() {
            return metadata;
        }

        /**
         * @param metadata the metadata to set
         */
        public void setMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
        }

        /**
         * @return the personality
         */
        /*public List<PersonalityFile> getPersonality() {
            return personality;
        }
*/
        /**
         * @param personality the personality to set
         */
//        public void setPersonality(List<PersonalityFile> personality) {
//            this.personality = personality;
//        }

        /**
         * @return the diskConfig
         */
        public String getDiskConfig() {
            return diskConfig;
        }

        /**
         * @param diskConfig the diskConfig to set
         */
        public void setDiskConfig(String diskConfig) {
            this.diskConfig = diskConfig;
        }

    }

    @JsonRootName("resize")
    public static final class Resize extends ServerAction {

        private String flavorRef;

        @JsonProperty("OS-DCF:diskConfig")
        private String diskConfig;

        /**
         * @return the flavorRef
         */
        public String getFlavorRef() {
            return flavorRef;
        }

        /**
         * @param flavorRef the flavorRef to set
         */
        public void setFlavorRef(String flavorRef) {
            this.flavorRef = flavorRef;
        }

        /**
         * @return the diskConfig
         */
        public String getDiskConfig() {
            return diskConfig;
        }

        /**
         * @param diskConfig the diskConfig to set
         */
        public void setDiskConfig(String diskConfig) {
            this.diskConfig = diskConfig;
        }

    }

    @JsonRootName("confirmResize")
    public static final class ConfirmResize extends ServerAction {

    }

    @JsonRootName("revertResize")
    public static final class RevertResize extends ServerAction {

    }

    @JsonRootName("createImage")
    public static final class CreateImage extends ServerAction {

        private String name;

        private Map<String, String> metadata;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the metadata
         */
        public Map<String, String> getMetadata() {
            return metadata;
        }

        /**
         * @param metadata the metadata to set
         */
        public void setMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
        }

    }

    @JsonRootName("rescue")
    public static final class Rescue extends ServerAction {

        private String adminPass;

        public Rescue() {

        }

        public Rescue(String adminPass) {
            this.adminPass = adminPass;
        }

        /**
         * @return the adminPass
         */
        public String getAdminPass() {
            return adminPass;
        }

        /**
         * @param adminPass the adminPass to set
         */
        public void setAdminPass(String adminPass) {
            this.adminPass = adminPass;
        }

    }

    public static final class RescueResponse extends ServerAction {

        private String adminPass;

        /**
         * @return the adminPass
         */
        public String getAdminPass() {
            return adminPass;
        }

    }

    @JsonRootName("os-stop")
    public static final class Stop extends ServerAction {

    }

    @JsonRootName("forceDelete")
    public static final class ForceDelete extends ServerAction {

    }

    @JsonRootName("restore")
    public static final class Restore extends ServerAction {

    }

    @JsonRootName("addFloatingIp")
    public static final class AssociateFloatingIp extends ServerAction {

        private String address;

        public AssociateFloatingIp() {
            super();
        }

        public AssociateFloatingIp(String address) {
            super();
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }

    @JsonRootName("removeFloatingIp")
    public static final class DisassociateFloatingIp extends ServerAction {

        private String address;

        public DisassociateFloatingIp() {
            super();
        }

        public DisassociateFloatingIp(String address) {
            super();
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }

}
