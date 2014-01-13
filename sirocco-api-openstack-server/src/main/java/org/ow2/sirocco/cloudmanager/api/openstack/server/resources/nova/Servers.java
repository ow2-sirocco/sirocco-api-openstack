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
package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.LinkHelper;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Server;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerForUpdate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.MachineToServer;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.ServerCreateToMachineCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.queries.ServerListQuery;
import org.ow2.sirocco.cloudmanager.core.api.IMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.QueryParams;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.deleted;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.badRequest;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class Servers extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Servers {

    private static final Logger LOGGER = LoggerFactory.getLogger(Servers.class);

    @Inject
    private IMachineManager machineManager;

    @Inject
    private IMachineImageManager machineImageManager;

    @Inject
    private INetworkManager networkManager;

    public Servers() {
        super();
    }

    @Override
    public Response list() {
        return getServers(false);
    }

    protected Response getServers(final boolean details) {
        try {
            org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Servers result = new org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Servers();
            QueryParams query = new ServerListQuery().apply(getJaxRsRequestInfo());
            List<Machine> machines = null;
            if (query == null) {
                machines = machineManager.getMachines().getItems();
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Query server with filters : " + query.getFilters());
                }
                machines = machineManager.getMachines(query).getItems();
            }

            if (machines == null || machines.size() == 0) {
                // TODO : Check openstack API for empty response.
                return ok(result);
            } else {
                List<Server> servers = Lists.transform(machines, new MachineToServer(details));
                // generate links
                // TODO : Get other links from generator
                servers = Lists.transform(servers, new Function<Server, Server>() {
                    @Override
                    public Server apply(org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Server input) {
                        // Add details if requested
                        if (details) {
                            input.tenantId = getPathParamValue(Constants.Nova.TENANT_ID_PATH_PARAMETER);
                        }
                        input.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, "%s", input.id));
                        input.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.BOOKMARK, null, "%s", input.id));
                        return input;
                    }
                });
                result.setServers(servers);
                return ok(result);
            }

        } catch (CloudProviderException e) {
            final String error = "Error while getting servers";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    @Override
    public Response create(ServerForCreate server) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("Creating server " + server);
        }

        try {
            Job job = machineManager.createMachine(new ServerCreateToMachineCreate(machineManager, networkManager, machineImageManager).apply(server));
            Machine machine = (Machine) job.getTargetResource();
            Server result = new MachineToServer(false).apply(machine);

            Server out = new Function<Server, Server>() {
                @Override
                public Server apply(org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Server input) {
                    input.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, "%s", input.id));
                    input.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.BOOKMARK, null, "%s", input.id));
                    return input;
                }
            }.apply(result);
            return Response.accepted(out).header("Location", out.links.get(0).href).build();
        } catch (CloudProviderException e) {
            final String error = "Error while getting servers";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    @Override
    public Response details() {
        return getServers(true);
    }

    @Override
    public Response details(String id) {
        try {
            Machine machine = machineManager.getMachineByUuid(id);
            if (machine == null) {
                return resourceNotFoundException("server", id, new ResourceNotFoundException("Server not found"));
            } else {
                Server s = new MachineToServer(true).apply(machine);
                s.tenantId = getPathParamValue(Constants.Nova.TENANT_ID_PATH_PARAMETER);
                s.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, null));
                s.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.BOOKMARK, null, null));
                return ok(s);
            }
        } catch (ResourceNotFoundException rnf) {
            return resourceNotFoundException("server", id, rnf);
        } catch (CloudProviderException e) {
            final String error = "Error while getting server details";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    @Override
    public Response update(String id, ServerForUpdate update) {
        try {
            if (update != null && update.getName() != null) {
                //Job job = machineManager.updateMachine(new ServerForUpdateToMachineUpdate().apply(update));

                machineManager.updateMachineAttributes(id, ImmutableMap.<String, Object>of("name", update.getName()));
                Server s = new MachineToServer(true).apply(machineManager.getMachineByUuid(id));
                s.links.add(LinkHelper.getLink(getUriInfo().getAbsolutePath().toString(), Constants.Link.SELF, null, null));
                return ok(s);
            } else {
                return badRequest("server", "update");
            }
        } catch (ResourceNotFoundException rnf) {
            return resourceNotFoundException("server", id, rnf);
        } catch (CloudProviderException e) {
            final String error = "Error while updating server information";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }

    @Override
    public Response delete(String id) {
        try {
            // TODO : manage error codes, check operation doc for more details
            machineManager.deleteMachine(id);
        } catch (ResourceNotFoundException rnf) {
            return resourceNotFoundException("server", id, rnf);
        } catch (CloudProviderException e) {
            final String error = "Error while updating server information";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
        return deleted();
    }
}
