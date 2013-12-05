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

package org.ow2.sirocco.cloudmanager.api.openstack.server.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.ServerActions;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.actions.RebootAction;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Test the ServerActions with the help of Arquillian
 * TODO : To be completed
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class ServerActionsTest { //extends JerseyTest {

    /**
     * Injected by arquillian
     */
    @Inject org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.ServerActions actions;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class).addClasses(ServerActions.class, RebootAction.class, EmptyMachineManager.class).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    /*
    @Deployment
    public static WebArchive createWebDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(ServerActions.class, RebootAction.class, EmptyMachineManager.class, EmptyActionsApplication.class).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    */

    @Test
    public void testNotInject() {
        assertNotNull(actions);
    }

    @Test
    public void testInvokeNullPayload() {
        Response response = actions.action(null);
        assertEquals(500, response.getStatus());
    }

    @Test
    public void testInvokeReboot() {
        InputStream stream = ServerActionsTest.class.getResourceAsStream("/org/ow2/sirocco/cloudmanager/api/openstack/server/json/nova/actions/reboot.json");
        Response response = actions.action(stream);
        // empty machine manager will return null job
        assertEquals(Response.accepted().build().getStatus(), response.getStatus());
    }

    @Test
    public void testInvokeNotAvailableAction() {
        InputStream stream = ServerActionsTest.class.getResourceAsStream("/org/ow2/sirocco/cloudmanager/api/openstack/server/json/nova/actions/createImage.json");
        Response response = actions.action(stream);
        assertEquals(500, response.getStatus());
    }

    /**
     * Works with a WebArchive deployment.
     *
     * @param baseUrl
     */
    @Test
    @RunAsClient
    @Ignore
    public void testClient(@ArquillianResource URL baseUrl) {
        System.out.println(baseUrl);
    }

}
