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

import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;

import java.util.List;
import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class RequestHelper {

    public static final String getTenant(JaxRsRequestInfo requestInfos) {
        if (requestInfos != null && requestInfos.getHeaders() != null
                && requestInfos.getHeaders().getRequestHeader(Constants.Nova.TENANT_ID_PATH_PARAMETER) != null
                && requestInfos.getHeaders().getRequestHeader(Constants.Nova.TENANT_ID_PATH_PARAMETER).size() > 0) {
            return requestInfos.getHeaders().getRequestHeader(Constants.Nova.TENANT_ID_PATH_PARAMETER).get(0);
        }
        return null;
    }

    public static Map<String, List<String>> getPathParameters(JaxRsRequestInfo requestInfos) {
        if (requestInfos != null && requestInfos.getUriInfo() != null && requestInfos.getUriInfo().getPathParameters() != null) {
            return requestInfos.getUriInfo().getPathParameters();
        } else {
            return Maps.newHashMap();
        }
    }

    public static String getPathParamater(JaxRsRequestInfo requestInfos, String name) {
        Map<String, List<String>> params = getPathParameters(requestInfos);
        if (params.get(name) != null && params.get(name).size() > 0) {
            return params.get(name).get(0);
        }
        return null;
    }

    public static Map<String, List<String>> getQueryParameters(JaxRsRequestInfo requestInfos) {
        if (requestInfos != null && requestInfos.getUriInfo() != null && requestInfos.getUriInfo().getQueryParameters() != null) {
            return requestInfos.getUriInfo().getQueryParameters();
        } else {
            return Maps.newHashMap();
        }
    }

    public static String getQueryParamater(JaxRsRequestInfo requestInfos, String name) {
        Map<String, List<String>> params = getQueryParameters(requestInfos);
        if (params.get(name) != null && params.get(name).size() > 0) {
            return params.get(name).get(0);
        }
        return null;
    }
}
