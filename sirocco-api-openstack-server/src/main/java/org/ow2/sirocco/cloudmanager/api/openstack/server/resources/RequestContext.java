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
package org.ow2.sirocco.cloudmanager.api.openstack.server.resources;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;

/**
 * Get all the HTTP request information in this container.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class RequestContext {

    public static RequestContext build(AbstractResource.JaxRsRequestInfos infos, String resourceId, Object input) {
        return new RequestContext();
    }

    /**
     * Resource involved in the request
     */
    private String resourceId;

    /**
     * Input request information
     */
    private AbstractResource.JaxRsRequestInfos requestInfos;

    /**
     * The input object (for example when updating a resource, etc...)
     */
    private Object input;

    public RequestContext() {
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public AbstractResource.JaxRsRequestInfos getRequestInfos() {
        return requestInfos;
    }

    public void setRequestInfos(AbstractResource.JaxRsRequestInfos requestInfos) {
        this.requestInfos = requestInfos;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {
        this.input = input;
    }
}
