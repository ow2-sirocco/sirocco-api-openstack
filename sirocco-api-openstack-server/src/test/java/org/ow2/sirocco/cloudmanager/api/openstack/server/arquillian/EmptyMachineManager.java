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

package org.ow2.sirocco.cloudmanager.api.openstack.server.arquillian;

import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.QueryParams;
import org.ow2.sirocco.cloudmanager.core.api.QueryResult;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceConflictException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.*;

import java.util.List;
import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class EmptyMachineManager implements IMachineManager {


    @Override
    public Job startMachine(String s, Map<String, String> stringStringMap) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job stopMachine(String s, boolean b, Map<String, String> stringStringMap) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job suspendMachine(String s, Map<String, String> stringStringMap) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job restartMachine(String s, boolean b, Map<String, String> stringStringMap) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job pauseMachine(String s, Map<String, String> stringStringMap) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job startMachine(String s) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job stopMachine(String s, boolean b) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job stopMachine(String s) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job suspendMachine(String s) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job restartMachine(String s, boolean b) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job pauseMachine(String s) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job captureMachine(String s, MachineImage machineImage) throws CloudProviderException {
        return null;
    }

    @Override
    public Job deleteMachine(String s) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Machine getMachineById(int i) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Machine getMachineByUuid(String s) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Machine getMachineAttributes(String s, List<String> strings) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job updateMachine(Machine machine) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public void updateMachineState(int i, Machine.State state) throws CloudProviderException {
    }

    @Override
    public void updateMachineVolumeState(int i, MachineVolume.State state) throws CloudProviderException {
    }

    @Override
    public Job updateMachineAttributes(String s, Map<String, Object> stringObjectMap) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public Job createMachine(MachineCreate machineCreate) throws ResourceConflictException, InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public void syncMachine(int i, Machine machine, int i2) throws CloudProviderException {
    }

    @Override
    public void syncVolumeAttachment(int i, MachineVolume machineVolume, int i2) {
    }

    @Override
    public QueryResult<Machine> getMachines(int i, int i2, List<String> strings, List<String> strings2) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public QueryResult<Machine> getMachines(QueryParams... queryParamses) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public MachineConfiguration getMachineConfigurationById(int i) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public MachineConfiguration getMachineConfigurationByUuid(String s) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public MachineConfiguration getMachineConfigurationAttributes(String s, List<String> strings) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public void updateMachineConfiguration(MachineConfiguration machineConfiguration) throws ResourceNotFoundException, InvalidRequestException, CloudProviderException {
    }

    @Override
    public void updateMachineConfigurationAttributes(String s, MachineConfiguration machineConfiguration, List<String> strings) throws ResourceNotFoundException, InvalidRequestException, CloudProviderException {
    }

    @Override
    public void deleteMachineConfiguration(String s) throws ResourceNotFoundException, CloudProviderException {
    }

    @Override
    public MachineConfiguration createMachineConfiguration(MachineConfiguration machineConfiguration) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public QueryResult<MachineConfiguration> getMachineConfigurations(QueryParams... queryParamses) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public QueryResult<MachineConfiguration> getMachineConfigurations(int i, int i2, List<String> strings, List<String> strings2) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public MachineTemplate getMachineTemplateById(int i) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public MachineTemplate getMachineTemplateByUuid(String s) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public MachineTemplate getMachineTemplateAttributes(String s, List<String> strings) throws ResourceNotFoundException, CloudProviderException {
        return null;
    }

    @Override
    public void updateMachineTemplate(MachineTemplate machineTemplate) throws ResourceNotFoundException, InvalidRequestException, CloudProviderException {
    }

    @Override
    public void updateMachineTemplateAttributes(String s, Map<String, Object> stringObjectMap) throws ResourceNotFoundException, InvalidRequestException, CloudProviderException {
    }

    @Override
    public void deleteMachineTemplate(String s) throws ResourceNotFoundException, CloudProviderException {
    }

    @Override
    public MachineTemplate createMachineTemplate(MachineTemplate machineTemplate) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public QueryResult<MachineTemplate> getMachineTemplates(QueryParams... queryParamses) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public QueryResult<MachineTemplate> getMachineTemplates(int i, int i2, List<String> strings, List<String> strings2) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public QueryResult<MachineVolume> getMachineVolumes(String s, QueryParams... queryParamses) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public Job addVolumeToMachine(String s, MachineVolume machineVolume) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public MachineVolume getVolumeFromMachine(String s, String s2) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job removeVolumeFromMachine(String s, String s2) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job updateVolumeOnMachine(String s, MachineVolume machineVolume) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job updateVolumeAttributesInMachine(String s, String s2, Map<String, Object> stringObjectMap) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public QueryResult<MachineDisk> getMachineDisks(String s, QueryParams... queryParamses) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public Job addDiskToMachine(String s, MachineDisk machineDisk) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public MachineDisk getDiskFromMachine(String s, String s2) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job removeDiskFromMachine(String s, String s2) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job updateDiskInMachine(String s, MachineDisk machineDisk) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job updateDiskAttributesInMachine(String s, String s2, Map<String, Object> stringObjectMap) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public QueryResult<MachineNetworkInterface> getMachineNetworkInterfaces(String s, QueryParams... queryParamses) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public Job addNetworkInterfaceToMachine(String s, MachineNetworkInterface machineNetworkInterface) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job removeNetworkInterfaceFromMachine(String s, String s2) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public MachineNetworkInterface getNetworkInterfaceFromMachine(String s, String s2) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job updateNetworkInterfaceInMachine(String s, MachineNetworkInterface machineNetworkInterface) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job updateNetworkInterfaceAttributesInMachine(String s, String s2, Map<String, Object> stringObjectMap) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public QueryResult<MachineNetworkInterfaceAddress> getMachineNetworkInterfaceAddresses(String s, String s2, QueryParams... queryParamses) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public Job addAddressToMachineNetworkInterface(String s, String s2, MachineNetworkInterfaceAddress address) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job removeAddressFromMachineNetworkInterface(String s, String s2, String s3) throws ResourceNotFoundException, CloudProviderException, InvalidRequestException {
        return null;
    }

    @Override
    public Job updateMachineNetworkInterfaceAddress(String s, String s2, MachineNetworkInterfaceAddress address) throws InvalidRequestException, CloudProviderException {
        return null;
    }

    @Override
    public void persistMachineInSystem(Machine machine) throws CloudProviderException {
    }

    @Override
    public void deleteMachineInSystem(Machine machine) throws CloudProviderException {
    }

    @Override
    public void updateMachineInSystem(Machine machine) throws CloudProviderException {
    }
}
