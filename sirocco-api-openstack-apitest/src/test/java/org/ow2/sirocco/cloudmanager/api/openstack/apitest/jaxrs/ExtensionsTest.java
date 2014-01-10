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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.jaxrs;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JAXRSBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Extensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static junit.framework.Assert.*;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class ExtensionsTest extends JAXRSBasedTest {

    private static Logger LOG = LoggerFactory.getLogger(ExtensionsTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIAndMockProvider("sirocco");
    }

    @Test
    public void testGetExtensions() {
        Response response = target(BASE_URL + tenantName + "/extensions").request(MediaType.APPLICATION_JSON_TYPE).get();
        Extensions extensions = readResponse(response, Extensions.class);

        assertNotNull(extensions);
        assertNotNull(extensions.getExtensions() != null);
        assertTrue(extensions.getExtensions().size() > 0);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        LOG.info("Extensions " + extensions);
    }
}
