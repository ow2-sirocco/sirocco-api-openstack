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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest;

import org.junit.After;
import org.ow2.sirocco.cloudmanager.core.api.*;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceConflictException;
import org.ow2.sirocco.cloudmanager.model.cimi.*;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.jclouds.Constants.PROPERTY_RELAX_HOSTNAME;
import static org.jclouds.Constants.PROPERTY_TRUST_ALL_CERTS;

/**
 * Base class for all the open stack tests. Gets the configuration values from the environment.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class AbstractOpenStackTest {

    private static Logger LOG = LoggerFactory.getLogger(AbstractOpenStackTest.class);

    @Inject
    protected IdentityContext identityContext;

    @Inject
    protected DbManagerBean dbManagerBean;

    @Inject
    protected IMachineManager machineManager;

    @Inject
    protected IUserManager userManager;

    @Inject
    protected ITenantManager tenantManager;

    @Inject
    protected ICloudProviderManager providerManager;

    @Inject
    protected IJobManager jobManager;

    @Inject
    protected IMachineImageManager machineImageManager;

    @Inject
    protected INetworkManager networkManager;

    protected String identity;
    protected String password;
    protected String endpoint;
    protected String apiVersion;
    protected String provider;
    protected String tenantName = "trial";

    @After
    public void tearDown() {
        LOG.info("Teardown");
        this.cleanUp();
    }

    /**
     * Retrieve properties from the environment
     *
     * @return
     */
    protected Properties setupProperties() {
        LOG.info("Setup properties");

        Properties overrides = new Properties();
        overrides.setProperty(PROPERTY_TRUST_ALL_CERTS, "true");
        overrides.setProperty(PROPERTY_RELAX_HOSTNAME, "true");
        identity = setIfTestSystemPropertyPresent(overrides,  provider + ".identity", getDefaultIdentity());
        password = setIfTestSystemPropertyPresent(overrides,  provider + ".password", getDefaultPassword());
        endpoint = setIfTestSystemPropertyPresent(overrides,  provider + ".endpoint", getDefaultEndpoint());
        apiVersion = setIfTestSystemPropertyPresent(overrides,  provider + ".api-version", deDefaultAPIVersion());
        return overrides;
    }

    /**
     * CLean up the database
     */
    public void cleanUp() {
        if (dbManagerBean != null)
            dbManagerBean.cleanup();
    }

    protected String deDefaultAPIVersion() {
        return "1.1";
    }

    protected String getDefaultEndpoint() {
        return "http://localhost:5000/v2.0/";
    }

    protected String getDefaultIdentity() {
        return tenantName + ":sirocco";
    }

    public String getDefaultPassword() {
        return "sirocco";
    }

    protected String getZone() {
        return "France";
    }

    /**
     * Create an image with the sirocco API
     *
     * @param name
     * @return
     * @throws CloudProviderException
     */
    protected MachineImage createImage(String name) throws CloudProviderException {
        LOG.info("Create image " + name);

        MachineImage image = new MachineImage();
        image.setName(name);
        Job job = machineImageManager.createMachineImage(image);

        LOG.info("Image created " + name  + " : " + job.getTargetResource().getUuid());
        return machineImageManager.getMachineImageByUuid(job.getTargetResource().getUuid());
    }

    /**
     * Create a tenant with the sirocco API
     *
     * @param name
     * @return
     */
    protected Tenant getOrCreateTenant(String name) {
        LOG.info("Create tenant " + name);

        Tenant tenant = new Tenant();
        tenant.setName(name);

        try {
            return this.tenantManager.createTenant(tenant);
        } catch (ResourceConflictException e) {
            LOG.warn("Tenant already exists exception " + name);
            try {
                return this.tenantManager.getTenantByName(name);
            } catch (CloudProviderException e1) {
                return null;
            }
        } catch (CloudProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a user and add it to the given tenant. It will create the tenant if it does not exist.
     *
     * @param login
     * @param password
     * @param tenant
     */
    protected User createUser(String login, String password, String tenant) throws CloudProviderException {
        LOG.info("Create user " + login);

        User user = new User();
        user.setUsername(login);
        user.setPassword(password);
        user = this.userManager.createUser(user);

        Tenant t = getOrCreateTenant(tenant);
        if (t == null) {
            LOG.warn("Tenant has not been retrieved nor created : " + tenant);
        } else {
            this.tenantManager.addUserToTenant(t.getUuid(), user.getUuid());
        }
        return user;
    }

    /**
     * Create a base Sirocco environment
     *
     * @throws Exception
     */
    protected void createEnv() throws Exception {
        LOG.info("Creating initial env");

        if (machineImageManager == null) {
            // return, we are probably on the client side so things are not injected...
            LOG.warn("Beans are null, probably running on client side");
            return;
        }

        Tenant tenant = getOrCreateTenant(tenantName);
        User user = createUser("guest", "guest", tenantName);

        // identity context is used by the machine manager when creating machine
        // so it needs to be set here...
        if (this.identityContext != null) {
            this.identityContext.setUserName("guest");
            this.identityContext.setTenantName(tenantName);
        } else {
            LOG.warn("Identity context is null");
        }

        LOG.info("Create cloud provider profile");
        CloudProviderProfile providerProfile = new CloudProviderProfile();
        providerProfile.setType("mock");
        providerProfile.setDescription("Mock");
        providerProfile.setConnectorClass("org.ow2.sirocco.cloudmanager.connector.mock.MockCloudProviderConnector");
        this.providerManager.createCloudProviderProfile(providerProfile);

        LOG.info("Create cloud provider mock");
        CloudProvider provider = new CloudProvider();
        provider.setEndpoint("");
        provider.setCloudProviderType("mock");
        provider.setDescription("mock");
        provider = this.providerManager.createCloudProvider(provider);

        LOG.info("Create Cloud provider location");
        CloudProviderLocation location = new CloudProviderLocation();
        location.setIso3166_1("FR");
        location.setCountryName("France");
        this.providerManager.addLocationToCloudProvider(provider.getUuid(), location);

        LOG.info("Create Cloud provider account");
        CloudProviderAccount account = new CloudProviderAccount();
        account.setLogin(user.getUsername());
        account.setPassword(user.getPassword());
        ICloudProviderManager.CreateCloudProviderAccountOptions options = new ICloudProviderManager.CreateCloudProviderAccountOptions().importMachineConfigs(true)
                .importMachineImages(true).importNetworks(true);
        account = this.providerManager.createCloudProviderAccount(provider.getUuid(), account, options);

        LOG.info("Add cloud provider account to tenant");
        this.providerManager.addCloudProviderAccountToTenant(tenant.getUuid(), account.getUuid());
    }

    /**
     * Injected with Maven, check the pom.xml for more details
     *
     * @param overrides
     * @param key
     * @return
     */
    protected String setIfTestSystemPropertyPresent(Properties overrides, String key, String def) {
        if (System.getProperties().containsKey("test." + key)) {
            String val = System.getProperty("test." + key);
            overrides.setProperty(key, val);
            return val;
        }
        return def;
    }

    /**
     * Create a machine configuration
     *
     *
     * @param name
     * @param cpu
     * @param memory
     * @param properties
     * @return
     * @throws CloudProviderException
     */
    protected MachineConfiguration createMachineConfiguration(String name, int cpu, int memory, Map<String, String> properties) throws CloudProviderException {
        LOG.info("Create machine configuration " + name);
        MachineConfiguration machineConfig = new MachineConfiguration();
        machineConfig.setName(name);
        machineConfig.setCpu(cpu);
        machineConfig.setMemory(memory);
        Map<String, String> props = new HashMap<>();
        if (properties != null) {
            props.putAll(properties);
        }

        machineConfig.setProperties(props);
        DiskTemplate disk = new DiskTemplate();
        disk.setCapacity(1024);
        machineConfig.setDisks(Collections.singletonList(disk));
        machineConfig = machineManager.createMachineConfiguration(machineConfig);
        LOG.info("Machine configuration created : " + name + " : " + machineConfig.getUuid());
        return machineConfig;
    }

    /**
     * Create a machine.
     *
     *
     * @param name
     * @param image
     * @param cpu
     * @param memory
     * @param waitStart
     * @return
     * @throws CloudProviderException
     */
    protected Machine createMachine(String name, String image, int cpu, int memory, Map<String, String> props, boolean waitStart) throws CloudProviderException {
        LOG.info("Create machine " + name + " - image : " + image + " - cpu : " + cpu + " - memory : " + memory);
        MachineTemplate template = new MachineTemplate();
        template.setName("template1");

        MachineConfiguration config = createMachineConfiguration("config-" + name, cpu, memory, props);
        template.setMachineConfig(config);

        MachineImage img = createImage(image);
        template.setMachineImage(img);

        MachineCreate machine = new MachineCreate();
        machine.setDescription("Machine description " + name);
        machine.setName(name);
        machine.setLocation("FR");
        machine.setMachineTemplate(template);
        Job job = machineManager.createMachine(machine);

        // wait for job to complete if requested
        if (waitStart) {
            try {
                waitForJobCompletion(job);
            } catch (Exception e) {
                throw new CloudProviderException(e);
            }
        } else {
            LOG.warn("Do not wait the machine to be started, can cause some tests to fail if not well handled...");
        }

        LOG.info("Machine created " + job.getTargetResource().getUuid());
        return machineManager.getMachineByUuid(job.getTargetResource().getUuid());
    }

    protected void stopMachine(String uuid, boolean waitStop) throws CloudProviderException {
        Job job = machineManager.stopMachine(uuid);
        if (waitStop) {
            try {
                waitForJobCompletion(job);
            } catch (Exception e) {
                throw new CloudProviderException(e);
            }
        } else {
            LOG.warn("Do not wait the machine to be stopped, can cause some tests to fail if not well handled...");
        }
    }

    /**
     *
     * @param job
     * @return
     * @throws Exception
     */
    protected Job.Status waitForJobCompletion(Job job) throws Exception {
        int counter = timeout(60);
        String jobUuid = job.getUuid();
        while (true) {
            LOG.info("Waiting for the job to complete...");
            job = this.jobManager.getJobByUuid(jobUuid);
            if (job.getState() != Job.Status.RUNNING) {
                break;
            }
            Thread.sleep(1000);
            if (counter-- == 0) {
                throw new Exception("Machine operation time out");
            }
        }
        return job.getState();
    }

    /**
     * Wait for machine state, or raise exception after timeout...
     *
     * @param machine
     * @param state
     * @param timeout in seconds
     */
    protected void waitMachineState(Machine machine, Machine.State state, int timeout) throws Exception {
        int counter = timeout(timeout > 0 ? timeout : 30);
        while (true) {
            LOG.info("Waiting for machine state to be " + state);
            machine = this.machineManager.getMachineByUuid(machine.getUuid());
            if (machine.getState() == state) {
                LOG.info("Valid machine state");
                break;
            }
            Thread.sleep(1000);
            if (counter-- == 0) {
                throw new Exception("Machine state time out");
            }
        }
    }

    protected void waitSecurityGroupState(SecurityGroup group, SecurityGroup.State state, int timeout) throws Exception {
        int counter = timeout(timeout > 0 ? timeout : 30);
        while (true) {
            LOG.info("Waiting for machine state to be " + state);
            group = this.networkManager.getSecurityGroupByUuid(group.getUuid());
            if (group.getState() == state) {
                LOG.info("Valid group state");
                break;
            }
            Thread.sleep(1000);
            if (counter-- == 0) {
                throw new Exception("Group state time out");
            }
        }
    }

    /**
     * Get the timeout from the environment or return default if not found
     *
     * @param d default value
     * @return
     */
    protected int timeout(int d) {
        String val = System.getProperty("test.timeout");
        if (val != null) {
            try {
                return Integer.parseInt(val);
            } catch (NumberFormatException e) {
                return d > 0 ? d : 60;
            }
        }
        return d > 0 ? d : 60;
    }

    protected Machine getUpdatedMachine(Machine machine) throws CloudProviderException {
        return machineManager.getMachineByUuid(machine.getUuid());
    }
}
