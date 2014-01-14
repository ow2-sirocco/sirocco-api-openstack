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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.links;

import com.google.common.base.Function;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.LinkHelper;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.Resource;

import javax.ws.rs.core.UriInfo;

/**
 * Add links to resource. This is used when the request target is the resource so the absolute path is already set with resource ID.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class DirectResourceLink implements Function<Resource, Resource> {

    private UriInfo info;

    public DirectResourceLink(UriInfo info) {
        this.info = info;
    }

    @Override
    public Resource apply(Resource resource) {
        resource.links.add(LinkHelper.getLink(info.getAbsolutePath().toString(), Constants.Link.SELF, null, null));
        resource.links.add(LinkHelper.getLink(info.getAbsolutePath().toString(), Constants.Link.BOOKMARK, null, null));
        return resource;
    }

    protected String getPattern() {
        return "%s";
    }

    /**
     *
     * @param info
     * @param resource
     * @return
     */
    public static Resource populate(UriInfo info, Resource resource) {
        return new DirectResourceLink(info).apply(resource);
    }
}
