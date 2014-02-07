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
package org.ow2.sirocco.cloudmanager.api.openstack.server;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.filter.UriConnegFilter;

public class MediaTypeFilter extends UriConnegFilter {
	private static final Map<String, MediaType> mappedMediaTypes = new HashMap<String, MediaType>(
			2);

	static {
		mappedMediaTypes.put("json", MediaType.APPLICATION_JSON_TYPE);
		mappedMediaTypes.put("xml", MediaType.APPLICATION_XML_TYPE);
	}

	public MediaTypeFilter() {
		super(mappedMediaTypes, null);
	}
}