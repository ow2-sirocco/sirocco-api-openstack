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

package org.ow2.sirocco.cloudmanager.api.openstack.commons.resource;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.faults.NotImplementedFault;

import javax.ws.rs.core.Response;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ResponseHelper {

    /**
     * TODO : Check Openstack API
     *
     * @return
     */
    public static Response notFound() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * @return
     */
    public static Response updated() {
        return Response.ok().build();
    }

    /**
     * HTTP 204
     *
     * @return
     */
    public static Response deleted() {
        return Response.noContent().build();
    }

    /**
     *
     * @param fault
     * @return
     */
    public static Response fault(Fault fault) {
        return Response.status(fault.code).entity(fault).build();
    }

    /**
     *
     * @param resource
     * @param operation
     * @return
     */
    public static final Response notImplemented(String resource, String operation) {
        return fault(new NotImplementedFault("Not implemented", operation + " is not implemented for " + resource));
    }

}
