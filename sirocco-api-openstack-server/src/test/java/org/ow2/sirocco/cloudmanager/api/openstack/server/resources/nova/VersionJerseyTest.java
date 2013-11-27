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

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class VersionJerseyTest extends JerseyTest {

    @Override
    protected Application configure() {
        System.out.println("Configure app for " + getClass());

        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(JacksonConfigurator.class);
        classes.add(JacksonJsonProvider.class);
        //classes.add(KeystoneFilter.class);
        //classes.add(JacksonFeature.class);
        classes.add(Versions.class);

        ResourceConfig app = new ResourceConfig(classes);
        return app;
    }

    @Test
    public void testGet() {
        Response response = this.target().path("/v2").request(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(200, response.getStatus());
        System.out.println(response.readEntity(String.class));
    }
}
