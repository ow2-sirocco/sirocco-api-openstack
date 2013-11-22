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

package org.ow2.sirocco.cloudmanager.api.openstack.server.functions;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerForCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineTemplate;

import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServerCreateToMachineCreate implements Function<ServerForCreate, MachineCreate> {

    @Override
    public MachineCreate apply(ServerForCreate server) {
        MachineCreate machine = new MachineCreate();
        machine.setName(server.getName());


        MachineTemplate template = new MachineTemplate();

//        template.setCredential(credentials);
//        template.setEventLogTemplate(eventLogTemplate);
//        template.setInitialState(state);
//        template.setMachineConfig(config);
//        template.setMachineImage(image);
//        template.setNetworkInterfaces(interfaces);
//        template.setSystemCredentialName(credentialName);
//        template.setUserData(userData);
//        template.setUser(user);
//        template.setVolumes(volumes);
//        template.setVolumeTemplates(volumeTemplates);
//        template.setCreated(created);
//        template.setDeleted(deleted);
//        template.setDescription(desccription);
//        template.setId(id);
//        template.setIsEmbeddedInSystemTemplate(Boolean.TRUE);
//        template.setName(name);
//        template.setName(props);
//        template.setProviderAssignedId(id);
//        template.setUpdated(updated);

        machine.setMachineTemplate(template);

        Map<String,String> props = Maps.newHashMap();
        // TODO
        machine.setProperties(props);

        return machine;
    }
}
