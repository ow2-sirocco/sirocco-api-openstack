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

package org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.builders;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.FaultWrapper;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class FaultBuilder {

    public static final String COMPUTE_FAULT = "computeFault";
    public static final String SERVICE_UNAVAILABLE = "serviceUnavailable";
    public static final String UNAUTHORIZED = "unauthorized";
    public static final String FORBIDDEN = "forbidden";
    private static final String BAD_REQUEST = "badRequest";
    private static final String BAD_METHOD = "badMethod";
    private static final String OVER_LIMIT = "overLimit";
    private static final String ITEM_NOT_FOUND = "itemNotFound";
    private static final String BAD_MEDIA_TYPE = "badMediaType";
    private static final String SERVER_CAPACITY_UNAVAILABLE = "serverCapacityUnavailable";


    public static Fault fault(String message, int code, String details) {
        Fault fault = new Fault();
        fault.code = code;
        fault.details = details;
        fault.message = message;
        return fault;
    }

    public static FaultWrapper computeFault(String message, int code, String details) {
        return wrapper(message, code, details, COMPUTE_FAULT);
    }

    public static FaultWrapper serviceUnavailable(String message, int code, String details) {
        return wrapper(message, 503, details, SERVICE_UNAVAILABLE);
    }

    public static FaultWrapper unauthorized(String message, int code, String details) {
        return wrapper(message, 401, details, UNAUTHORIZED);
    }

    public static FaultWrapper forbidden(String message, int code, String details) {
        return wrapper(message, 403, details, FORBIDDEN);
    }

    public static FaultWrapper badRequest(String message, int code, String details) {
        return wrapper(message, 400, details, BAD_REQUEST);
    }

    public static FaultWrapper badMethod(String message, int code, String details) {
        return wrapper(message, 405, details, BAD_METHOD);
    }

    public static FaultWrapper overLimit(String message, int code, String details) {
        return wrapper(message, 413, details, OVER_LIMIT);
    }

    public static FaultWrapper itemNotFound(String message, int code, String details) {
        return wrapper(message, 404, details, ITEM_NOT_FOUND);
    }

    public static FaultWrapper badMediaType(String message, int code, String details) {
        return wrapper(message, 415, details, BAD_MEDIA_TYPE);
    }

    public static FaultWrapper serverCapacityUnavailable(String message, int code, String details) {
        return wrapper(message, 503, details, SERVER_CAPACITY_UNAVAILABLE);
    }

    public static FaultWrapper wrapper(String message, int code, String details, String name) {
        FaultWrapper wrapper = new FaultWrapper();
        wrapper.setFault(fault(message, code, details));
        wrapper.setName(name);
        return wrapper;
    }

}