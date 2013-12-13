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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Module;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.NovaAsyncApi;
import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.openstack.v2_0.domain.Resource;
import org.jclouds.openstack.v2_0.predicates.LinkPredicates;
import org.jclouds.rest.RestContext;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class JcloudsBasedTest extends AbstractOpenStackTest {

    private static Logger LOG = LoggerFactory.getLogger(JcloudsBasedTest.class);

    protected ComputeService compute;
    protected RestContext<NovaApi, NovaAsyncApi> nova;
    //protected Set<String> zones;

    @Before
    public void init() throws Exception {
        LOG.info(">>> Init test environment");
        setupProperties();
        createEnv();

        Iterable<Module> modules = ImmutableSet.<Module> of(new SLF4JLoggingModule());
        String provider = "openstack-nova";

        LOG.info("Create Compute context");
        ComputeServiceContext context = ContextBuilder.newBuilder(provider)
                .endpoint(endpoint)
                .credentials(identity, credential)
                .modules(modules)
                .buildView(ComputeServiceContext.class);

        compute = context.getComputeService();
        nova = context.unwrap();
        //zones = jclouds.getApi().getConfiguredZones();
    }

    /**
     * Checks the default values of any openstack resource
     *
     * @param resource
     */
    protected boolean checkResource(Resource resource) {
        assertNotNull(resource.getId());
        assertNotNull(resource.getName());
        assertNotNull(resource.getLinks());
        assertTrue(Iterables.any(resource.getLinks(), LinkPredicates.relationEquals(Link.Relation.SELF)));
        return true;
    }
}
