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
 * Populate the resource with generated link using uri info and resource ID
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ResourceIDLink implements Function<Resource, Resource> {

    private UriInfo info;
    private String resourceName;

    public ResourceIDLink(UriInfo info, String resourceName) {
        this.info = info;
        this.resourceName = resourceName;
    }

    @Override
    public Resource apply(Resource resource) {
        resource.links.add(LinkHelper.getLink(info.getAbsolutePath().toString(), Constants.Link.SELF, null, getPattern(), resourceName, resource.id));
        resource.links.add(LinkHelper.getLink(info.getAbsolutePath().toString(), Constants.Link.BOOKMARK, null, getPattern(), resourceName, resource.id));
        return resource;
    }

    protected String getPattern() {
        return "%s/%s";
    }

    /**
     *
     * @param info
     * @param resource
     * @param resourceName servers, images, flavors, ...
     * @return
     */
    public static Resource populate(UriInfo info, Resource resource, String resourceName) {
        return new ResourceIDLink(info, resourceName).apply(resource);
    }

}
