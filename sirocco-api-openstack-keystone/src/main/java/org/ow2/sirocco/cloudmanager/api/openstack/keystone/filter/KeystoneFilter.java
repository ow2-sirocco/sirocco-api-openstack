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

package org.ow2.sirocco.cloudmanager.api.openstack.keystone.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Keystone filter is used to authenticate users based on the incoming request credentials.
 * cf https://jersey.java.net/documentation/latest/filters-and-interceptors.html
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class KeystoneFilter implements ContainerRequestFilter {


    private static Logger LOG = LoggerFactory.getLogger(KeystoneFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        LOG.debug("Filtering request");

        // TODO
        // Contact keystone or a local cache and check that the incoming token is valid and has not expired.
        requestContext.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("User cannot access the resource.")
                .build());
    }
}
