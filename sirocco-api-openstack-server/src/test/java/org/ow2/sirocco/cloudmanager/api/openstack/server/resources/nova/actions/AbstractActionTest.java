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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerAction;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class AbstractActionTest {

    @Test
    public void testGetRebootBean() throws IOException {
        InputStream stream = AbstractActionTest.class.getResourceAsStream("/org/ow2/sirocco/cloudmanager/api/openstack/server/json/nova/actions/reboot.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(stream, JsonNode.class);
        AbstractAction action = new AbstractAction() {
            @Override
            public String getName() {
                return "foo";
            }

            @Override
            public Response invoke(String serverId, JsonNode payload) {
                return null;
            }
        };

        ServerAction.Reboot reboot = action.getBean(rootNode, ServerAction.Reboot.class);
        assertNotNull(reboot);
        assertEquals("HARD", reboot.getType());
    }

    @Test
    public void testGetCreateImageBean() throws IOException {
        InputStream stream = AbstractActionTest.class.getResourceAsStream("/org/ow2/sirocco/cloudmanager/api/openstack/server/json/nova/actions/createImage.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(stream, JsonNode.class);
        AbstractAction action = new AbstractAction() {
            @Override
            public String getName() {
                return "foo";
            }

            @Override
            public Response invoke(String serverId, JsonNode payload) {
                return null;
            }
        };

        ServerAction.CreateImage createImage = action.getBean(rootNode, ServerAction.CreateImage.class);
        assertNotNull(createImage);
        assertEquals("new-image", createImage.getName());
        assertNotNull(createImage.getMetadata());
        assertEquals(2, createImage.getMetadata().size());
        assertNotNull(createImage.getMetadata().get("ImageType"));
        assertEquals("Gold", createImage.getMetadata().get("ImageType"));
        assertNotNull(createImage.getMetadata().get("ImageVersion"));
        assertEquals("2.0", createImage.getMetadata().get("ImageVersion"));
    }
}
