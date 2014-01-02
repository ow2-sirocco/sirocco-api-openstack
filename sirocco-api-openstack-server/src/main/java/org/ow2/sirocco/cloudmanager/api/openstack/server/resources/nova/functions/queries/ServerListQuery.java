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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.queries;

import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.Constants;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;

import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServerListQuery extends AbstractQuery {

    /**
     * Key is the Openstack param name, Value is the Sirocco one
     */
    public static Map<String, String> mapping = Maps.newHashMap();

    /**
     * Key is the Openstack status value, value is the sirocco state equivalent
     */
    public static Map<org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status, Machine.State> STATUS = Maps.newHashMap();

    static {
        mapping.put(Constants.REQUEST_CHANGESSINCE, null);
        mapping.put(Constants.REQUEST_FLAVOR, "config.uuid");
        // FIXME issue #27 : Define how to map host in machine query
        mapping.put(Constants.REQUEST_HOST, null);
        mapping.put(Constants.REQUEST_IMAGE, "image.uuid");
        mapping.put(Constants.REQUEST_LIMIT, null);
        mapping.put(Constants.REQUEST_MARKER, null);
        mapping.put(Constants.REQUEST_NAME, "name");
        mapping.put(Constants.REQUEST_STATUS, "state");

        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.ACTIVE, Machine.State.STARTED);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.BUILD, Machine.State.CREATING);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.DELETED, Machine.State.DELETED);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.ERROR, Machine.State.ERROR);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.HARD_REBOOT, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.PASSWORD, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.REBOOT, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.REBUILD, Machine.State.CREATING);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.RESIZE, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.REVERT_RESIZE, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.SUSPENDED, Machine.State.SUSPENDED);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.UNKNOWN, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.VERIFY_RESIZE, null);
    }

    @Override
    protected String getSiroccoParamName(String openstackParamName) {
        return mapping.get(openstackParamName);
    }

    @Override
    protected String getSiroccoParamValue(String openstackParamName, String openstackParamValue) {
        if (openstackParamName != null && openstackParamName.equalsIgnoreCase("status")) {

            try {
                org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status input = org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.valueOf(openstackParamValue.toUpperCase());
                return STATUS.get(input) != null ? STATUS.get(input).toString() : openstackParamValue;
            } catch (IllegalArgumentException e) {
                return openstackParamValue;
            }

            // return the mapped value if found, else the input one...
        }
        return openstackParamValue;
    }
}
