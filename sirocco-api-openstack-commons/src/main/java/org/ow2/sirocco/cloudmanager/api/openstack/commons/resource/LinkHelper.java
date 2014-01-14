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
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Link;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class LinkHelper {

    /**
     * Create a Link from the path with resource ID as suffix
     *
     * @param rel
     * @param type may be null
     * @param uriPattern
     * @param args
     * @return
     */
    public static Link getLink(String baseURI, String rel, String type, String uriPattern, Object... args) {
        if (uriPattern != null) {
            if (baseURI.endsWith("/")) {
                baseURI = baseURI.substring(0, baseURI.length() - 1);
            }
            return new Link(baseURI + "/" + String.format(uriPattern, args), rel, type);
        } else {
            return new Link(baseURI, rel, type);
        }
    }

    /**
     *
     * @param baseURI
     * @param resource
     * @param uuid
     * @return
     */
    public static String href(String baseURI, String resource, String uuid) {
        String result = baseURI;
        if (result.endsWith("/")) {
            result = result.substring(0, result.length() - 1);
        }
        result = result + "/";
        result = result + resource;
        result = result + "/";
        result = result + uuid;

        return result;
    }

    /**
     *
     * @param baseURI
     * @param type
     * @param uriPattern
     * @param args
     * @return
     */
    public static Link self(String baseURI, String type, String uriPattern, Object... args) {
        return getLink(baseURI, Constants.Link.SELF, type, uriPattern, args);
    }

    /**
     *
     * @param baseURI
     * @param type
     * @param uriPattern
     * @param args
     * @return
     */
    public static Link bookmark(String baseURI, String type, String uriPattern, Object... args) {
        return getLink(baseURI, Constants.Link.BOOKMARK, type, uriPattern, args);
    }
}
