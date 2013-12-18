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

import java.util.HashMap;
import java.util.Map;

/**
 * Creates query parameters from the input HTTP request.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class FlavorListQuery extends AbstractQuery {

    /*
    changes-since (Optional) 	query 	xsd:dateTime
    A time/date stamp for when the flavor last changed.

    minDisk (Optional) 	query 	xsd:int
    Integer value for the minimum disk space in GB so you can filter results.

    minRam (Optional) 	query 	xsd:int
    Integer value for the minimum RAM so you can filter results.

    marker (Optional) 	query 	csapi:UUID
    UUID of the flavor at which you want to set a marker.

    limit (Optional) 	query 	xsd:int
    Integer value for the limit of values to return.
     */

    static Map<String, String> QUERYMAPPING = new HashMap<String, String>();

    static {
        QUERYMAPPING.put("changes-since", null);
        QUERYMAPPING.put("minDisk", null);
        QUERYMAPPING.put("minRam", null);
        QUERYMAPPING.put("marker", null);
        QUERYMAPPING.put("limit", null);
    }

    @Override
    protected String getSiroccoParamName(String openstackParamName) {
        return QUERYMAPPING.get(openstackParamName);
    }
}
