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

package org.ow2.sirocco.cloudmanager.api.openstack.filters;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.model.Access;
import org.ow2.sirocco.cloudmanager.api.openstack.keystone.model.authentication.UsernamePassword;
import org.ow2.sirocco.cloudmanager.core.api.IConfigManager;
import org.ow2.sirocco.cloudmanager.core.api.IdentityContext;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class KeystoneDelegateFilter implements ContainerRequestFilter {

    private static final String ADMIN_TENANT_NAME = "openstack.tenant";

    private static final String ADMIN_USER = "openstack.user";

    private static final String ADMIN_PASSWORD = "openstack.password";

    private static final String KEYSTONE_URI = "openstack.keystone";

    /**
     * This header has been defined by keystone and injected by the client in the input request.
     */
    public static String X_AUTH_TOKEN_HEADER = "X-Auth-Token";

    /**
     * Openstack Project (tenant)
     */
    public static String X_AUTH_PROJECT_ID_HEADER = "X-Auth-Project-Id";

    private static Logger LOG = LoggerFactory.getLogger(KeystoneDelegateFilter.class);

    @Inject
    private IConfigManager configManager;

    @Inject
    IdentityContext identityContext;

    private LoadingCache<OpenstackAdmin, Client> cache;

    public KeystoneDelegateFilter() {
        cache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .build(new CacheLoader<OpenstackAdmin, Client>() {
                    @Override
                    public Client load(OpenstackAdmin admin) {
                        Client client = javax.ws.rs.client.ClientBuilder.newClient();
                        client.register(JacksonJsonProvider.class).register(JacksonConfigurator.class);
                        return client;
                    }
                });
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String token = requestContext.getHeaderString(X_AUTH_TOKEN_HEADER);
        if (token == null) {
            LOG.warn("Can not get the token from the request header, name : " + X_AUTH_TOKEN_HEADER);
            requestContext.abortWith(Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new BadRequestFault("Invalid request", X_AUTH_TOKEN_HEADER + " header is missing"))
                    .build());
        } else {
            OpenstackAdmin admin = null;
            try {
                admin = new OpenstackAdmin(configManager.getConfigParameter(ADMIN_TENANT_NAME), configManager.getConfigParameter(KEYSTONE_URI), configManager.getConfigParameter(ADMIN_USER), configManager.getConfigParameter(ADMIN_PASSWORD));
            } catch (InvalidRequestException ire) {
                LOG.error("Unable to retrieve valid information from sirocco", ire);
                requestContext.abortWith(Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity(new BadRequestFault("Bad request", "Unable to retrieve valid information from sirocco"))
                        .build());
            }

            Client client = null;
            try {
                client = cache.get(admin);
            } catch (ExecutionException e) {
                LOG.error("Unable to load keystone client from cache", e);
                requestContext.abortWith(Response
                        .status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity(new ServiceUnavailableFault("Client Error", "Unable to retrieve valid client from sirocco"))
                        .build());
            }

            try {
                String adminToken = getToken(admin);
                WebTarget target = client.target(admin.keystone).path("/tokens/" + token);
                Invocation.Builder invocation = target.request(MediaType.APPLICATION_JSON_TYPE).header(X_AUTH_TOKEN_HEADER, adminToken);
                Access response = invocation.get(Access.class);
                if (response != null && response.getToken() != null && response.getUser() != null) {
                    identityContext.setUserName(response.getUser().getName());
                    if (response.getToken().getTenant() != null) {
                        identityContext.setTenantId(response.getToken().getTenant().getId());
                        identityContext.setTenantName(response.getToken().getTenant().getName());
                    }
                } else {
                    requestContext.abortWith(Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new UnauthorizedFault("Server error", "Can not get the keystone response"))
                            .build());
                }

            } catch (Exception e) {
                LOG.error("Keystone request error", e);
                requestContext.abortWith(Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity(new UnauthorizedFault("Not authorized", "Unable to retrieve valid information from keystone"))
                        .build());
            }
        }
    }

    /**
     * Get the token from the admin.
     * TODO : Cache it. Use the expire date
     *
     * @param admin
     * @return
     * @throws Exception
     */
    protected String getToken(OpenstackAdmin admin) throws Exception {
        // $ curl -d '{"auth":{"passwordCredentials":{"username": "joeuser", "password": "secrete"}}}' -H "Content-type: application/json" http://localhost:35357/v2.0/tokens
        UsernamePassword password = new UsernamePassword();
        UsernamePassword.PasswordCredentials credentials = new UsernamePassword.PasswordCredentials();
        credentials.setPassword(admin.password);
        credentials.setUsername(admin.user);
        password.setPasswordCredentials(credentials);
        password.setTenantId(admin.tenant);

        Client client = cache.get(admin);
        WebTarget target = client.target(admin.keystone).path("/tokens");
        Access access = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(password), Access.class);

        if (access == null || access.getToken() == null || access.getToken().getId() == null) {
            throw new Exception("Can not get admin token");
        }
        return access.getToken().getId();
    }

    @JsonRootName("unauthorized")
    public class UnauthorizedFault extends org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault {

        public UnauthorizedFault(String message, String details) {
            super(401, message, details);
        }
    }

    @JsonRootName("badRequest")
    public class BadRequestFault extends org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault {

        public BadRequestFault(String message, String details) {
            super(400, message, details);
        }
    }

    @JsonRootName("serviceUnavailable")
    public class ServiceUnavailableFault extends org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Fault {

        public ServiceUnavailableFault(String message, String details) {
            super(503, message, details);
        }
    }

    public class OpenstackAdmin {
        String tenant;
        String keystone;
        String user;
        String password;

        public OpenstackAdmin(String tenant, String keystone, String user, String password) {
            this.tenant = tenant;
            this.keystone = keystone;
            this.user = user;
            this.password = password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OpenstackAdmin that = (OpenstackAdmin) o;

            if (keystone != null ? !keystone.equals(that.keystone) : that.keystone != null) return false;
            if (password != null ? !password.equals(that.password) : that.password != null) return false;
            if (tenant != null ? !tenant.equals(that.tenant) : that.tenant != null) return false;
            if (user != null ? !user.equals(that.user) : that.user != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = tenant != null ? tenant.hashCode() : 0;
            result = 31 * result + (keystone != null ? keystone.hashCode() : 0);
            result = 31 * result + (user != null ? user.hashCode() : 0);
            result = 31 * result + (password != null ? password.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "OpenstackAdmin{" +
                    "tenant='" + tenant + '\'' +
                    ", keystone='" + keystone + '\'' +
                    ", user='" + user + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
