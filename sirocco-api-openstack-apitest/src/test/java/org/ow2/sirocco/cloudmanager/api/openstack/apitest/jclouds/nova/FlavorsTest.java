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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.jclouds.nova;

import com.google.common.base.Predicate;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jclouds.collect.PagedIterable;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.v2_0.domain.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.JcloudsBasedTest;
import org.ow2.sirocco.cloudmanager.api.openstack.apitest.utils.Archives;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(Arquillian.class)
public class FlavorsTest extends JcloudsBasedTest {

    private static Logger LOG = LoggerFactory.getLogger(FlavorsTest.class);

    @Deployment
    public static WebArchive deploy() {
        return Archives.openstackAPIWithKeystoneMock("sirocco");
    }

    @Test
    public void testGetSingleFlavor() throws CloudProviderException {
        MachineConfiguration config = createMachineConfiguration("testGetSingleFlavor", 1024, 1024, null);
        Flavor flavor = nova.getApi().getFlavorApiForZone(getZone()).get(config.getUuid());
        assertNotNull(flavor);
        assertEquals(config.getName(), flavor.getName());
        assertEquals(config.getUuid(), flavor.getId());
    }

    @Test
    public void testList() throws CloudProviderException {
        MachineConfiguration config1 = createMachineConfiguration("testFlavorList1", 1024, 1024, null);
        MachineConfiguration config2 = createMachineConfiguration("testFlavorList2", 1024, 1024, null);

        PagedIterable<? extends Resource> result = nova.getApi().getFlavorApiForZone(getZone()).list();
        assertEquals(2, result.concat().size());
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

        MachineConfiguration config1 = createMachineConfiguration("testListDetails1", 1024, 1024, null);
        MachineConfiguration config2 = createMachineConfiguration("testListDetails2", 1024, 1024, null);

        PagedIterable<? extends Flavor> result = nova.getApi().getFlavorApiForZone(getZone()).listInDetail();
        assertEquals(2, result.concat().size());

        assertTrue(result.concat().allMatch(new Predicate<Flavor>() {
            @Override
            public boolean apply(Flavor input) {
                return checkFlavor(input);
            }
        }));
    }

    protected boolean checkFlavor(Flavor flavor) {
        checkResource(flavor);
        return true;
    }

}
