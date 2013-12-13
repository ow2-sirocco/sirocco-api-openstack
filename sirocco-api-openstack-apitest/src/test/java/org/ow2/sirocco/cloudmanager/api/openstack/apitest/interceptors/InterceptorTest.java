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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.interceptors;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.rest.*;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the interceptor and injection of resources
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class InterceptorTest {

    @Deployment
    public static WebArchive deploy() {
                File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml")
                        // for CDI in jersey service (not supported by default)
                .resolve("org.glassfish.jersey.containers.glassfish:jersey-gf-cdi").withoutTransitivity()
                .as(File.class);
        return ShrinkWrap.create(WebArchive.class).addAsLibraries(libs).addClasses(Application.class, SiroccoResource.class, IdentityInterceptor.class, ResourceInterceptorBinding.class, TestBean.class)
                        .setWebXML("identityinterceptortest-web.xml")
                        .addAsWebInfResource("identityinterceptortest-beans.xml", "beans.xml");

    }

    @Test
    @RunAsClient
    public void testCall(@ArquillianResource URL baseURL) throws IOException, URISyntaxException {
        WebTarget target = ClientBuilder.newBuilder().newClient().target(new URL(baseURL, "identity/sirocco/hello").toURI());
        Response out = target.request().get();
        String payload = out.readEntity(String.class);
        assertEquals(200, out.getStatus());
        assertTrue(payload.startsWith("Hello"));
    }

}
