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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.jclouds;

import com.google.common.base.Predicate;
import junit.framework.TestCase;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jclouds.collect.PagedIterable;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.v2_0.domain.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class ImagesTest extends JcloudsBasedTest {
    private static Logger LOG = LoggerFactory.getLogger(ImagesTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    @Test
    public void testGetSingle() throws CloudProviderException {
        MachineImage image = createImage("testGetSingle");
        Image i = nova.getApi().getImageApiForZone(getZone()).get(image.getUuid());
        assertNotNull(i);
        assertEquals(image.getName(), i.getName());
        assertEquals(image.getUuid(), i.getId());
    }

    @Test
    public void testList() throws CloudProviderException {
        MachineImage image1 = createImage("testList1");
        MachineImage image2 = createImage("testList2");

        PagedIterable<? extends Resource> result = nova.getApi().getImageApiForZone(getZone()).list();
        TestCase.assertEquals(2, result.concat().size());
        assertTrue(result.concat().allMatch(new Predicate<Resource>() {
            @Override
            public boolean apply(Resource input) {
                return checkResource(input);
            }
        }));
    }

    @Test
    public void testListDetails() throws CloudProviderException {
        LOG.info("Test get list details");

        MachineImage image1 = createImage("testListDetails1");
        MachineImage image2 = createImage("testListDetails2");

        PagedIterable<? extends Image> result = nova.getApi().getImageApiForZone(getZone()).listInDetail();
        assertEquals(2, result.concat().size());

        assertTrue(result.concat().allMatch(new Predicate<Image>() {
            @Override
            public boolean apply(Image input) {
                return checkImage(input);
            }
        }));
    }

    @Test
    public void testDelete() throws CloudProviderException {
        MachineImage image1 = createImage("testDelete");
        // TODO : Wait for effective image creation before delete...

        nova.getApi().getImageApiForZone(getZone()).delete(image1.getUuid());

        // TODO : Wait for image delete before check
        assertTrue(machineImageManager.getMachineImages().size() == 0);
        // NOTE : image can be here but in another state...

    }

    private boolean checkImage(Image input) {
        checkResource(input);
        // TODO : Better test
        return true;
    }

}
