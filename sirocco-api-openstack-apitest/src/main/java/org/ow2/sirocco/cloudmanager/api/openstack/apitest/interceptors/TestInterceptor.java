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

package org.ow2.sirocco.cloudmanager.api.openstack.apitest.interceptors;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.core.api.IdentityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Interceptor
@ResourceInterceptorBinding
public class TestInterceptor implements Serializable {

    @Inject
    protected IdentityContext context;

    private static Logger LOG = LoggerFactory.getLogger(TestInterceptor.class);

    @AroundInvoke
    public Object log(final InvocationContext ctx) throws Exception {
        LOG.debug(">>> Calling : " + ctx.getMethod());

        if (context != null) {
            context.setTenantName("trial");
            context.setUserName("guest");
        } else {
            LOG.warn("Context is null, tests will fail...");
        }

        return ctx.proceed();
    }
}
