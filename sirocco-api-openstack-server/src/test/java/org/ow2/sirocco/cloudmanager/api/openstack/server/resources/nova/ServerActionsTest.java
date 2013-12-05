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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova;

import org.codehaus.jackson.JsonNode;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Action;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.ServerActions;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions.AbstractActionTest;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.*;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServerActionsTest {

    @Test
    public void testInvokeNull() {
        ServerActions actions = new org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.ServerActions();
        Response response = actions.action(null);
        assertEquals(500, response.getStatus());
    }

    @Test
    public void testInvokeStreamAndNullHandlers() {
        ServerActions actions = new org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.ServerActions();
        InputStream stream = AbstractActionTest.class.getResourceAsStream("/org/ow2/sirocco/cloudmanager/api/openstack/server/json/nova/actions/reboot.json");
        Response response = actions.action(stream);
        assertEquals(500, response.getStatus());
    }


    @Test
    public void testInvokeReboot() {
        org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.ServerActions actions = new org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.ServerActions();

        final AtomicBoolean called = new AtomicBoolean(false);
        Action action = new Action() {
            @Override
            public String getName() {
                return "reboot";
            }

            @Override
            public Response invoke(String serverId, JsonNode payload) {
                called.set(true);
                return Response.accepted().build();
            }
        };

        InputStream stream = AbstractActionTest.class.getResourceAsStream("/org/ow2/sirocco/cloudmanager/api/openstack/server/json/nova/actions/reboot.json");

        org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.ServerActions partialMock = createMockBuilder(org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.ServerActions.class)
                .withConstructor().addMockedMethod("getAction", String.class).createMock();
        expect(partialMock.getAction(action.getName())).andReturn(action).anyTimes();
        replay(partialMock);

        Response response = partialMock.action(stream);
        assertEquals(Response.accepted().build().getStatus(), response.getStatus());
        assertTrue(called.get());
    }




}
