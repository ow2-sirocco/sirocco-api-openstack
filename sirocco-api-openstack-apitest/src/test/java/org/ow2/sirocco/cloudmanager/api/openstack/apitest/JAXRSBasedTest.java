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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest;

import org.junit.Before;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class JAXRSBasedTest extends AbstractOpenStackTest {

    public static String BASE_URL = "http://localhost:8181/sirocco/openstack/v2/";

    private static Logger LOG = LoggerFactory.getLogger(JAXRSBasedTest.class);

    @Before
    public void init() throws Exception {
        LOG.info("Init test environment");
        setupProperties();
        createEnv();
    }

    /**
     *
     * @return
     */
    @Ignore
    protected WebTarget target(String path) {
        Client client = javax.ws.rs.client.ClientBuilder.newClient();
        return client.target(path);
    }

    /**
     *
     * @param url
     * @param resource
     * @return
     * @throws URISyntaxException
     */
    @Ignore
    protected String path(URL url, String resource) throws URISyntaxException {
        String result = url.toURI().toString();
        if (result.endsWith("/")) {
            result += resource;
        } else {
            result += "/" + resource;
        }
        return result;
    }

}
