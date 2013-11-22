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

import com.google.common.collect.Lists;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Link;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.builders.FaultBuilder;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Server;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerForUpdate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.functions.MachineToServer;
import org.ow2.sirocco.cloudmanager.api.openstack.server.functions.ServerCreateToMachineCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.functions.ServerForUpdateToMachineUpdate;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.computeFault;
import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.deleted;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class Servers extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Servers {

    private static final Logger LOGGER = LoggerFactory.getLogger(Servers.class);

    @Inject
    private IMachineManager machineManager;

    public Servers() {
        super();
    }

    @Override
    public Response list() {
        JaxRsRequestInfos infos = getJaxRsRequestInfos();
        try {
            org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Servers result = new org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Servers();
            // TODO : Filter machine from input
            List<Machine> machines = machineManager.getMachines().getItems();

            if (machines == null || machines.size() == 0) {
                // TODO : Check openstack API for empty response.
                return Response.ok(result).build();
            } else {
                result.setServers(Lists.transform(machines, new MachineToServer(false)));
                return Response.ok(result).build();
            }

        } catch (CloudProviderException e) {
            final String error = "Error while getting servers";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response create(ServerForCreate server) {
        LOGGER.info("SERVER NAME " + server.getName());
        // Get the server parameters from the input request

        try {
            Job job = machineManager.createMachine(new ServerCreateToMachineCreate().apply(server));

            Server response = new Server();
            response.id = "" + job.getId();
            response.adminPass = "TODO";

            String href = "http://TODO";

            response.links.add(new Link(href, "self"));
            response.links.add(new Link(href, "bookmark"));
            return Response.accepted(server).header("Location", href).build();
        } catch (CloudProviderException e) {
            final String error = "Error while getting servers";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response details() {
        try {
            org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Servers result = new org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Servers();
            // TODO : Filter machine from input
            List<Machine> machines = machineManager.getMachines().getItems();

            if (machines == null || machines.size() == 0) {
                // TODO : Check openstack API for empty response.
                return Response.ok(result).build();
            } else {
                result.setServers(Lists.transform(machines, new MachineToServer(true)));
                return Response.ok(result).build();
            }

        } catch (CloudProviderException e) {
            final String error = "Error while getting servers";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response details(String id) {
        try {
            Machine machine = machineManager.getMachineById(id);
            if (machine == null) {
                return resourceNotFoundException("server", id, new ResourceNotFoundException("Server not found"));
            } else {
                return Response.ok(new MachineToServer(true).apply(machine)).build();
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
            return computeFault("Server Error", 500, e.getMessage());
        }
    }

    @Override
    public Response update(String id, ServerForUpdate update) {
        try {
            Job job = machineManager.updateMachine(new ServerForUpdateToMachineUpdate().apply(update));
            // In the better case we can get the updated data right now
            // in the worst case, we must wait for the job to complte...
            // TODO : Check job progress
            LOGGER.warn("TODO : Check machine update progress before return");
            return Response.ok(new MachineToServer(true).apply(machineManager.getMachineById(id))).build();
        } catch (ResourceNotFoundException rnf) {
            return resourceNotFoundException("server", id, rnf);
        } catch (CloudProviderException e) {
            final String error = "Error while updating server information";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(error, e);
            } else {
                LOGGER.error(error);
            }
            return computeFault("Server Error", 500, e.getMessage());
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
            return computeFault("Server Error", 500, e.getMessage());
        }
        return deleted();
    }
}
