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

import com.google.common.collect.Maps;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.easymock.EasyMock;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;
import org.ow2.sirocco.cloudmanager.core.api.IMachineImageManager;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ImageMetadataJerseyTest extends JerseyTest {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ImageMetadataJerseyTest.class);

    // TODO : Do not use static stuff...
    static IMachineImageManager service = EasyMock.createMock(IMachineImageManager.class);

    @Override
    protected Application configure() {
        System.out.println("Configure app");

        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(JacksonConfigurator.class);
        classes.add(JacksonJsonProvider.class);
        classes.add(ImageMetadata.class);

        LoggingFilter filter = new LoggingFilter(Logger.getLogger(ImageMetadataJerseyTest.class.getName()), true);
        classes.add(LoggingFilter.class);

        ResourceConfig app = new ResourceConfig(classes);

        // inject mock resource
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(service).to(IMachineImageManager.class);
            }
        };
        app.registerInstances(binder, filter);
        return app;
    }

    public void tearDown() throws Exception {
        super.tearDown();
        System.out.println("Reset mocks...");
        super.tearDown();
        EasyMock.reset(this.service);
    }

    @Test
    public void testGetList() throws Exception {

        Map<String, String> meta = Maps.newHashMap();
        meta.put("foo", "bar");
        meta.put("bar", "baz");

        MachineImage image = new MachineImage();
        image.setUuid("123");
        image.setName("foobar");
        image.setProperties(meta);

        EasyMock.expect(this.service.getMachineImageByUuid(image.getUuid())).andReturn(image).anyTimes();
        EasyMock.replay(this.service);

        Response response = this.target().path("/v2/tenant_123/images/" + image.getUuid() + "/metadata").request(MediaType.APPLICATION_JSON_TYPE).get();
        EasyMock.verify(this.service);
        LOGGER.info("Response status %s", response.getStatus());
        assertEquals(200, response.getStatus());
        System.out.println(response.readEntity(String.class));

        // TODO : Check JSON
        // Should be :
        /*
        {
          "metadata" : {
            "foo" : "bar",
            "bar" : "baz"
          }
        }
        */
    }
}
