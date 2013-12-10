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

package org.ow2.sirocco.cloudmanager.api.openstack.keystone.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.api.KeystoneServer;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.server.model.Access;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class KeystoneServerImpl implements KeystoneServer {

    private static Logger LOG = LoggerFactory.getLogger(KeystoneServerImpl.class);
    private final int port;

    private HttpServer server;

    private Application application;

    public KeystoneServerImpl(int port, Access access) throws IOException {
        application = new Application(access);
        this.port = port;
        URI uri = UriBuilder.fromUri("http://localhost/").port(port).build();
        server = HttpServer.create(new InetSocketAddress(uri.getPort()), 0);
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(application, HttpHandler.class);
        server.createContext(uri.getPath(), handler);
    }

    @Override
    public javax.ws.rs.core.Application getApplication() {
        return application;
    }

    @Override
    public void start() {
        LOG.info("Starting keystone server on port " + port);
        server.start();
    }

    @Override
    public void stop() {
        LOG.info("Starting keystone server");
        server.stop(0);
    }
}
