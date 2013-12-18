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

package org.ow2.sirocco.cloudmanager.api.openstack.server.functions;

import org.easymock.EasyMock;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.JaxRsRequestInfo;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.queries.ServerListQuery;
import org.ow2.sirocco.cloudmanager.core.api.QueryParams;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import static junit.framework.Assert.*;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServerListQueryTest {

    @Test
    public void testNoInputParams() {
        ServerListQuery query = new ServerListQuery();
        AbstractResource resource = EasyMock.createMock(AbstractResource.class);
        JaxRsRequestInfo input = new JaxRsRequestInfo(resource);
        QueryParams params = query.apply(input);
        assertNull(params);
    }

    @Test
    public void testOneValidParam() {
        ServerListQuery query = new ServerListQuery();

        MultivaluedMap<String,String> map = new MultivaluedHashMap<String, String>();
        map.add(Constants.REQUEST_NAME, "azerty");

        AbstractResource resource = EasyMock.createMock(AbstractResource.class);

        UriInfo info = EasyMock.createMock(UriInfo.class);
        EasyMock.expect(info.getQueryParameters()).andReturn(map).anyTimes();

        JaxRsRequestInfo input = new JaxRsRequestInfo(resource);

        EasyMock.expect(resource.getJaxRsRequestInfo()).andReturn(input).anyTimes();
        EasyMock.expect(input.getUriInfo()).andReturn(info).anyTimes();

        EasyMock.replay(resource);
        EasyMock.replay(info);
        //EasyMock.replay(resource);

        QueryParams params = query.apply(input);
        assertNotNull(params);
        assertTrue(params.getFilters().size() > 0);
        assertEquals(1, params.getFilters().size());

        assertEquals("name='azerty'", params.getFilters().get(0));
    }

    @Test
    public void testInvalidParams() {
        ServerListQuery query = new ServerListQuery();

        MultivaluedMap<String,String> map = new MultivaluedHashMap<String, String>();
        map.add("foo", "bar");
        map.add("bar", "baz");

        AbstractResource resource = EasyMock.createMock(AbstractResource.class);

        UriInfo info = EasyMock.createMock(UriInfo.class);
        EasyMock.expect(info.getQueryParameters()).andReturn(map).anyTimes();

        JaxRsRequestInfo input = new JaxRsRequestInfo(resource);

        EasyMock.expect(resource.getJaxRsRequestInfo()).andReturn(input).anyTimes();
        EasyMock.expect(input.getUriInfo()).andReturn(info).anyTimes();

        EasyMock.replay(resource);
        EasyMock.replay(info);
        //EasyMock.replay(resource);

        QueryParams params = query.apply(input);
        assertNotNull(params);
        assertEquals(0, params.getFilters().size());
    }

    @Test
    public void testValidAndInvalidParams() {
        ServerListQuery query = new ServerListQuery();

        MultivaluedMap<String,String> map = new MultivaluedHashMap<String, String>();
        map.add("foo", "bar");
        map.add("bar", "baz");

        map.add(Constants.REQUEST_NAME, "azerty");
        map.add(Constants.REQUEST_IMAGE, "123456789");

        AbstractResource resource = EasyMock.createMock(AbstractResource.class);

        UriInfo info = EasyMock.createMock(UriInfo.class);
        EasyMock.expect(info.getQueryParameters()).andReturn(map).anyTimes();

        JaxRsRequestInfo input = new JaxRsRequestInfo(resource);

        EasyMock.expect(resource.getJaxRsRequestInfo()).andReturn(input).anyTimes();
        EasyMock.expect(input.getUriInfo()).andReturn(info).anyTimes();

        EasyMock.replay(resource);
        EasyMock.replay(info);
        //EasyMock.replay(resource);

        QueryParams params = query.apply(input);
        assertNotNull(params);
        assertEquals(2, params.getFilters().size());

        assertTrue(params.getFilters().contains("name='azerty'"));
        assertTrue(params.getFilters().contains("image.uuid='123456789'"));

    }



}
