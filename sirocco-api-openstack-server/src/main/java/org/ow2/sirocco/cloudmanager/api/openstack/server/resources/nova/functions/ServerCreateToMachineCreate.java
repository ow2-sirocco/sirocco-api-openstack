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
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerForCreate;
import org.ow2.sirocco.cloudmanager.core.api.ICredentialsManager;
import org.ow2.sirocco.cloudmanager.core.api.IMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServerCreateToMachineCreate implements Function<ServerForCreate, MachineCreate> {

    private static Logger LOG = LoggerFactory.getLogger(ServerCreateToMachineCreate.class);

    private final IMachineManager machineManager;

    private final IMachineImageManager machineImageManager;

    private final INetworkManager networkManager;

    private final ICredentialsManager credentialsManager;

    public ServerCreateToMachineCreate(IMachineManager machineManager, INetworkManager networkManager, IMachineImageManager machineImageManager, ICredentialsManager credentialsManager) {
        this.machineManager = machineManager;
        this.networkManager = networkManager;
        this.machineImageManager = machineImageManager;
        this.credentialsManager = credentialsManager;
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

        if (server.getKeyName() != null) {
            Credentials credentials = new GetCredentialsByName(credentialsManager).apply(server.getKeyName());
            if (credentials != null) {
                template.setCredential(credentials);
            } else {
                // should throw an exception
                LOG.warn("No credentials found for key name " + server.getKeyName());
            }
        }

        if (server.getNetworks() != null) {
            // Keep only the networks with UUID and the ones which are available in sirocco.
            // Other operations are not supported in sirocco.
            Iterator<ServerForCreate.Network> keep = Iterators.filter(server.getNetworks().iterator(), new Predicate<ServerForCreate.Network>() {
                @Override
                public boolean apply(ServerForCreate.Network input) {
                    if (input.getUuid() != null) {
                        try {
                            networkManager.getNetworkByUuid(input.getUuid());
                            return true;
                        } catch (ResourceNotFoundException e) {
                            // TODO : Throw exception since the user specified a network which is not available
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            });

            Iterator<MachineTemplateNetworkInterface> out = Iterators.transform(keep, new Function<ServerForCreate.Network, MachineTemplateNetworkInterface>() {
                @Override
                public MachineTemplateNetworkInterface apply(ServerForCreate.Network network) {
                    MachineTemplateNetworkInterface result = new MachineTemplateNetworkInterface();
                    if (network.getUuid() != null) {
                        try {
                            result.setNetwork(networkManager.getNetworkByUuid(network.getUuid()));
                        } catch (ResourceNotFoundException e) {
                        }
                    }
                    return result;
                }
            });
            template.setNetworkInterfaces(Lists.newArrayList(out));
        }

        if (server.getUserData() != null) {
            template.setUserData(server.getUserData());
        }

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

    private class NetworkToMachineTemplateNetworkInterface implements Function<ServerForCreate.Network, MachineTemplateNetworkInterface> {
            @Override
            public MachineTemplateNetworkInterface apply(ServerForCreate.Network input) {
                return null;
            }
    }
}
