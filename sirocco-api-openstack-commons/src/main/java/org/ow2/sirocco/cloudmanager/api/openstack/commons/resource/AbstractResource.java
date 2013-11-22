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

package org.ow2.sirocco.cloudmanager.api.openstack.commons.resource;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

/**
 * Code from org.ow2.sirocco.cimi.server.resource.RestResourceAbstract. To be moved in commons.
 * <p/>
 * TODO : Get the project name from the URL (cf tenant_id path parameter)
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
public class AbstractResource {

    @Context
    private UriInfo uriInfo;

    @Context
    protected HttpHeaders headers;

    @Context
    private Request request;

    private JaxRsRequestInfos infos;

    /**
     *
     */
    public AbstractResource() {
        this.infos = new JaxRsRequestInfos();
    }

    /**
     * @return the uriInfo
     */
    public UriInfo getUriInfo() {
        return this.uriInfo;
    }

    /**
     * @return the headers
     */
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    /**
     * @return the request
     */
    public Request getRequest() {
        return this.request;
    }

    /**
     * @return the request information.
     * This is used to introspect incoming request values.
     */
    public JaxRsRequestInfos getJaxRsRequestInfos() {
        return this.infos;
    }

    public class JaxRsRequestInfos {

        /**
         * @return the uriInfo
         */
        public UriInfo getUriInfo() {
            return AbstractResource.this.uriInfo;
        }

        /**
         * @return the headers
         */
        public HttpHeaders getHeaders() {
            return AbstractResource.this.headers;
        }

        /**
         * @return the request
         */
        public Request getRequest() {
            return AbstractResource.this.request;
        }
    }
}
