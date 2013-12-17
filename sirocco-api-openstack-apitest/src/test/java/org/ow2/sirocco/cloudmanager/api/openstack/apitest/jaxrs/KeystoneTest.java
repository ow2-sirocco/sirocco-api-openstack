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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.jaxrs;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JAXRSBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.model.Access;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class KeystoneTest extends JAXRSBasedTest {

    @Inject
    protected Access access;

    @Deployment
    public static WebArchive deployment() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    @Test
    public void testAuthOnTokenResource() {
        System.out.println("WWWW ACCESS  : " + access);
        if (access == null) {
            access = new Access();
        }

        Access.User user = new Access.User();
        user.setName("Christophe Hamerling");
        user.setUsername("chamerling");
        access.setUser(user);

        String payload = "{\n" +
                "\"auth\":{\n" +
                "\"passwordCredentials\":{\n" +
                "\"username\":\"sirocco\",\n" +
                "\"password\":\"mypass\"\n" +
                "},\n" +
                "\"tenantName\":\"sirocco-tenant\"\n" +
                "}\n" +
                "}";

        Response response = target(JcloudsBasedTest.BASE_URL + "tokens").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(payload));
        String out = response.readEntity(String.class);
        System.out.println(">> : " + out);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

}
