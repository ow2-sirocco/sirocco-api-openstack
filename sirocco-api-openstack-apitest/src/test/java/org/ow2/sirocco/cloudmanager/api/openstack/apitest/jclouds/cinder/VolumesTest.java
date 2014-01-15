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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.jclouds.cinder;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.cinder.v1.options.CreateVolumeOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.jclouds.nova.FlavorsTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.VolumeConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.VolumeCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.VolumeTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class VolumesTest extends JcloudsBasedTest {

    private static Logger LOG = LoggerFactory.getLogger(FlavorsTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    VolumeApi api() {
        Iterable<Module> modules = ImmutableSet.<Module> of(new SLF4JLoggingModule());
        CinderApi cinder = ContextBuilder
                .newBuilder("openstack-cinder")
                .endpoint(BASE_URL)
                .credentials(identity, password)
                .modules(modules)
                .buildApi(CinderApi.class);
        return cinder.getVolumeApiForZone(getZone());
    }

    @Test
    public void testList() throws Exception {
        org.ow2.sirocco.cloudmanager.model.cimi.Volume create = createVolume("testlist0");
        org.ow2.sirocco.cloudmanager.model.cimi.Volume create2 = createVolume("testlist1");

        FluentIterable<? extends Volume> list = api().listInDetail();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    public void testGet() throws Exception {
        org.ow2.sirocco.cloudmanager.model.cimi.Volume create = createVolume("testGet");

        Volume v = api().get(create.getUuid());
        LOG.info(v.toString());
        assertNotNull(v);
        assertEquals(create.getName(), v.getName());
        assertEquals(v.getId(), create.getUuid());
    }

    @Test
    public void testCreate() throws CloudProviderException {
        String name = "volumetestcreate";
        Volume created = api().create(1, CreateVolumeOptions.Builder.name(name));
        assertNotNull(volumeManager.getVolumeByUuid(created.getId()));
    }

    @Test
    public void testDelete() throws Exception {
        org.ow2.sirocco.cloudmanager.model.cimi.Volume create = createVolume("delete");
        api().delete(create.getUuid());
        org.ow2.sirocco.cloudmanager.model.cimi.Volume v = null;
        try {
            v = volumeManager.getVolumeByUuid(create.getUuid());
        } catch (ResourceNotFoundException e) {
            return;
        }

        if (v == null) {
            return;
        }

        assertTrue("Volume is not in DELETED or DELETING state " + v.getState(), (v.getState() == org.ow2.sirocco.cloudmanager.model.cimi.Volume.State.DELETED) || (v.getState() == org.ow2.sirocco.cloudmanager.model.cimi.Volume.State.DELETING));
    }

    protected org.ow2.sirocco.cloudmanager.model.cimi.Volume createVolume(String name) throws Exception {
        VolumeCreate create = new VolumeCreate();
        create.setName(name);

        VolumeConfiguration config = new VolumeConfiguration();
        config.setName(name);
        config.setCapacity(1000);
        config.setType("None");
        VolumeTemplate template = new VolumeTemplate();
        template.setName("template-" + name);
        template.setVolumeConfig(config);

        create.setVolumeTemplate(template);
        Job job = volumeManager.createVolume(create);
        //waitForJobCompletion(job);
        return volumeManager.getVolumeByUuid(job.getTargetResource().getUuid());
    }
}
