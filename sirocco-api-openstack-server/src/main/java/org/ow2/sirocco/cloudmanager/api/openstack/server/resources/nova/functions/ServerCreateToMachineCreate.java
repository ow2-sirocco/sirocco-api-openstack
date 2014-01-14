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
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerForCreate;
import org.ow2.sirocco.cloudmanager.core.api.IMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServerCreateToMachineCreate implements Function<ServerForCreate, MachineCreate> {

    private static Logger LOG = LoggerFactory.getLogger(ServerCreateToMachineCreate.class);

    private final IMachineManager machineManager;

    private final IMachineImageManager machineImageManager;

    private final INetworkManager networkManager;

    public ServerCreateToMachineCreate(IMachineManager machineManager, INetworkManager networkManager, IMachineImageManager machineImageManager) {
        this.machineManager = machineManager;
        this.networkManager = networkManager;
        this.machineImageManager = machineImageManager;
    }

    @Override
    public MachineCreate apply(ServerForCreate server) {
        MachineCreate machine = new MachineCreate();
        machine.setName(server.getName());
        machine.setDescription("Machine created with the Openstack/Sirocco API");

        String image = server.getImageRef();
        String flavor = server.getFlavorRef();

        MachineTemplate template = new MachineTemplate();

        if (image != null) {
            if (image.startsWith("http") && image.contains("/")) {
                image = image.substring(image.lastIndexOf('/') + 1);
            }
            try {
                template.setMachineImage(machineImageManager.getMachineImageByUuid(image));
            } catch (CloudProviderException e) {
                LOG.error("Can not find machine image");
            }
        } else {
            LOG.warn("Can not get imageRef from input create server bean");
        }

        // TODO : handle URLs in resources
        if (flavor != null) {
            if (flavor.startsWith("http") && flavor.contains("/")) {
                flavor = flavor.substring(flavor.lastIndexOf('/') + 1);
            }

            MachineConfiguration machineConfig = null;
            try {
                template.setMachineConfig(machineManager.getMachineConfigurationByUuid(flavor));
            } catch (CloudProviderException e) {
                LOG.error("Can not find machine configuration");
            }
        } else {
            // ?
            LOG.warn("Can not get flavorRef from the input create server bean");
        }

        // openstack use the security group names where sirocco uses the UUIDs
        List groups = Lists.newArrayList(Collections2.filter(Lists.transform(server.getSecurityGroups(), new Function<ServerForCreate.SecurityGroup, String>() {
            @Override
            public String apply(ServerForCreate.SecurityGroup input) {
                return new SecurityGroupNameToUUID(networkManager).apply(input.getName());
            }
        }), new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input != null;
            }
        }));
        template.setSecurityGroupUuids(groups);

        // keypair

        // network


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

        if (server.getMetadata() != null && server.getMetadata().size() > 0) {
            machine.setProperties(server.getMetadata());
        }

        return machine;
    }
}
