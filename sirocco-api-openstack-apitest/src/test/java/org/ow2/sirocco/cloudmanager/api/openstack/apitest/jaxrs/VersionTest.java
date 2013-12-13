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
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JAXRSBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class VersionTest extends JAXRSBasedTest {

    @Deployment
    public static Archive deploy() {
        return Archives.openstackAPIAndMockProvider("sirocco");
    }

    /**
     * Get the version from client side
     *
     * @param url
     * @throws URISyntaxException
     */
    @Test
    @RunAsClient()
    public void testGet(@ArquillianResource URL url) throws URISyntaxException {
        assertNotNull(url);
        System.out.println("Getting version from base URL : " + url);

        System.out.println("openstack/v2");
        Response response = target(path(url, "openstack/v2")).request(MediaType.APPLICATION_JSON).get();
        System.out.println(response.readEntity(String.class));
        assertEquals(200, response.getStatus());
    }
}
