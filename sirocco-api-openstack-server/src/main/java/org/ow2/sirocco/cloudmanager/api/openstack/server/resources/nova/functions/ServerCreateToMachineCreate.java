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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.ServerForCreate;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServerCreateToMachineCreate implements Function<ServerForCreate, MachineCreate> {

    private static Logger LOG = LoggerFactory.getLogger(ServerCreateToMachineCreate.class);

    private final IMachineManager machineManager;

    public ServerCreateToMachineCreate(IMachineManager machineManager) {
        this.machineManager = machineManager;
    }

    @Override
    public MachineCreate apply(ServerForCreate server) {
        MachineCreate machine = new MachineCreate();
        machine.setName(server.getName());
        machine.setDescription("Machine created with the Openstack/Sirocco API");

        // TODO : API user needs to put the image and flavor IDs or URLs in the payload.
        // On the translation side, we need to be able to retrieve properties
        String image = server.getImageRef();
        String flavor = server.getFlavorRef();

        MachineImage machineImage = new MachineImage();
        machineImage.setUuid(image);

        MachineTemplate template = new MachineTemplate();
        template.setMachineImage(machineImage);

        // TODO : handle URLs
        MachineConfiguration machineConfig = null;
        try {
            machineConfig = machineManager.getMachineConfigurationByUuid(flavor);
        } catch (CloudProviderException e) {
            e.printStackTrace();
            // FIXME : We assume that the backend will be able to load all that is required...
            machineConfig = new MachineConfiguration();
            machineConfig.setUuid(flavor);
        }
        template.setMachineConfig(machineConfig);


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
