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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers;


import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.faults.Faults;

import javax.ws.rs.core.Response;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ResponseHelper {

    public static Response fault(Fault fault) {
        return Response.status(fault.code).entity(fault).build();
    }

    public static Response computeFault(String message, String details) {
        return fault(Faults.computeFault(500, message, details));
    }

    public static Response computeFault(int code, String message, String details) {
        return fault(Faults.computeFault(code, message, details));
    }

    public static Response serviceUnavailable(String message, String details) {
        return fault(Faults.serviceUnavailable(message, details));
    }

    public static Response unauthorized(String message, String details) {
        return fault(Faults.unauthorized(message, details));
    }

    public static Response forbidden(String message, String details) {
        return fault(Faults.forbidden(message, details));
    }

    public static Response badRequest(String message, String details) {
        return fault(Faults.badRequest(message, details));
    }

    public static Response badMethod(String message, String details) {
        return fault(Faults.badMethod(message, details));
    }

    public static Response overLimit(String message, String details) {
        return fault(Faults.overLimit(message, details));
    }

    public static Response itemNotFound(String message, String details) {
        return fault(Faults.itemNotFound(message, details));
    }

    public static Response badMediaType(String message, String details) {
        return fault(Faults.badMediaType(message, details));
    }

    public static Response serverCapacityUnavailable(String message, String details) {
        return fault(Faults.serverCapacityUnavailable(message, details));
    }

}
