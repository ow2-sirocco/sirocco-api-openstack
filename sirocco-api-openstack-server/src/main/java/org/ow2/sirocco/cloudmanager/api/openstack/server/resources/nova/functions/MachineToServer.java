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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions;

import com.google.common.base.Function;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Server;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;

/**
 * Transform a Sirocco Machine to an Openstack Server
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class MachineToServer implements Function<Machine, Server> {

    boolean details;

    /**
     *
     */
    public MachineToServer() {
        this(false);
    }

    /**
     *
     * @param details
     */
    public MachineToServer(boolean details) {
        this.details = details;
    }

    @Override
    public Server apply(org.ow2.sirocco.cloudmanager.model.cimi.Machine machine) {
        Server server = new Server();
        server.id = machine.getUuid();
        server.name = machine.getName();

        if (details) {
            // TODO

            server.userId = "TODO";

            if (machine.getTenant() != null) {
                server.tenantId = machine.getTenant().getUuid();
            }

            if (machine.getUpdated() != null) {
                server.updated = machine.getUpdated();
            }

            if (machine.getCreated() != null) {
                server.created = machine.getCreated();
            }

            //server.hostId = Strings.emptyToNull("TODO");
            server.accessIPv4 = "";
            server.accessIPv6 = "";

            // TODO : Mapping
            if (machine.getState() != null) {
                server.status = machine.getState().toString();
            }

            // TODO
            server.image = null;

            if (machine.getConfig() != null) {
                server.flavor = new MachineConfigurationToFlavor(true).apply(machine.getConfig());
            }

            if (machine.getNetworkInterfaces() != null) {
                server.addresses = new NetworkInterfacesToAddresses().apply(machine.getNetworkInterfaces());
            }

            if (machine.getProperties() != null) {
                server.metadata = new MapToMetadata().apply(machine.getProperties());
            }
        }
        return server;
    }
}
