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

package org.ow2.sirocco.cloudmanager.api.openstack.nova.model.faults;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.faults.ItemNotFoundFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Faults {

    public static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault computeFault(int code, String message, String details) {
        return new ComputeFault(code, message, details);
    }

    public static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault serviceUnavailable(String message, String details) {
        return new ServiceUnavailableFault(message, details);
    }

    public static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault unauthorized(String message, String details) {
        return new UnauthorizedFault(message, details);
    }

    public static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault forbidden(String message, String details) {
        return new ForbiddenFault(message, details);
    }

    public static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault badRequest(String message, String details) {
        return new BadRequestFault(message, details);
    }

    public static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault badMethod(String message, String details) {
        return new BadMethodFault(message, details);
    }

    public static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault overLimit(String message, String details) {
        return new OverLimitFault(message, details);
    }

    public static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault itemNotFound(String message, String details) {
        return new ItemNotFoundFault(message, details);
    }

    public static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault badMediaType(String message, String details) {
        return new BadMediaTypeFault(message, details);
    }

    public static org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault serverCapacityUnavailable(String message, String details) {
        return new ServerCapacityUnavailableFault(message, details);
    }

}
