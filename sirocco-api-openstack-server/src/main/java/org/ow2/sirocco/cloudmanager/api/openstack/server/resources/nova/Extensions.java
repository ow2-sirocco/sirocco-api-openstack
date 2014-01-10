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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.ExtensionProvider;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

/**
 * TODO : Get extensions from the runtime (annotations)
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class Extensions extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Extensions {

    private static final Logger LOGGER = LoggerFactory.getLogger(Extensions.class);

    @Inject
    @Any
    private Instance<ExtensionProvider> extensionProviders;

    @Override
    public Response get() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Getting extensions");
        }
        org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Extensions result = new org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Extensions();
        if (extensionProviders == null) {
            return ok(result);
        }

        result.setExtensions(Lists.newArrayList(Iterators.transform(extensionProviders.iterator(), new Function<ExtensionProvider, Extension>() {
            @Override
            public Extension apply(ExtensionProvider input) {
                return input.getExtensionMetadata();
            }
        })));
        return ok(result);
    }

    @Override
    public Response get(final String alias) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Getting extension " + alias);
        }

        return ok(Iterators.tryFind(extensionProviders.iterator(), new Predicate<ExtensionProvider>() {
            @Override
            public boolean apply(ExtensionProvider input) {
                return input != null && input.getExtensionMetadata() != null && input.getExtensionMetadata().alias.equals(alias);
            }
        }).orNull());
    }
}
