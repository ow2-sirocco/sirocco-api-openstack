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

import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.faults.ItemNotFoundFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.*;

/**
 * Code from org.ow2.sirocco.cimi.server.resource.RestResourceAbstract. To be moved in commons.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
public class AbstractResource {

    private static Logger LOG = LoggerFactory.getLogger(AbstractResource.class);

    @Context
    private UriInfo uriInfo;

    @Context
    protected HttpHeaders headers;

    @Context
    private Request request;

    private JaxRsRequestInfo info;

    /**
     *
     */
    public AbstractResource() {
        this.info = new JaxRsRequestInfo(this);
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
    public JaxRsRequestInfo getJaxRsRequestInfo() {
        return this.info;
    }

    /**
     *
     * @param type
     * @param id
     * @param e
     * @return
     */
    public Response resourceNotFoundException(String type, String id, Exception e) {
        LOG.debug("Resource has not been found", e);
        String message = "Resource not found";
        if (e != null) {
            message = e.getLocalizedMessage();
        }
        return ResponseHelper.fault(new ItemNotFoundFault(type + " " + id + " not found", message));
    }

    /**
     *
     * @param entity
     * @return
     */
    public Response ok(Object entity) {
        return Response.ok(entity).build();
    }

    /**
     * Openstack create operation returns HTTP Accepted 202
     *
     * @param entity
     * @return
     */
    public Response created(Object entity) {
        return Response.accepted(entity).build();
    }


    /**
     * Get the path param value
     *
     * @param paramName
     * @return the param value or null if not found
     */
    public String getPathParamValue(String paramName) {
        if (paramName == null) {
            return null;
        }
        return RequestHelper.getPathParameter(getJaxRsRequestInfo(),paramName);
    }

    /**
     * Get the query param value for the current request
     *
     * @param paramName
     * @return the query value or null if not found
     */
    public String getQueryParamValue(String paramName) {
        if (paramName == null) {
            return null;
        }

        return RequestHelper.getQueryParamater(getJaxRsRequestInfo(), paramName);
    }

    /**
     * Get tenant ID from the URI if available
     *
     * @return
     */
    public String getTenantId() {
        return getPathParamValue(Constants.Nova.TENANT_ID_PATH_PARAMETER);
    }

    /**
     * Get server ID from the URI if available
     *
     * @return
     */
    public String getServerId() {
        return getPathParamValue(Constants.Nova.SERVER_ID_PATH_PARAMETER);
    }


}
