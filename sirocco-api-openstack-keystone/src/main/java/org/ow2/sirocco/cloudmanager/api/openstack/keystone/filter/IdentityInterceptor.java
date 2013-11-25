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

package org.ow2.sirocco.cloudmanager.api.openstack.keystone.filter;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.RequestHelper;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.core.api.IdentityContext;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * Interceptor to fill tenant and user information from incoming HTTP request
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Interceptor
@ResourceInterceptorBinding
public class IdentityInterceptor implements Serializable {

    @Inject
    IdentityContext identityContext;

    @AroundInvoke
    public Object retrieveUserNameAndTenantId(final InvocationContext ctx) throws Exception {
        if (ctx.getTarget() instanceof AbstractResource) {
            AbstractResource resourceBase = (AbstractResource) ctx.getTarget();

            String tenant = RequestHelper.getTenant(resourceBase.getJaxRsRequestInfo());
            if (tenant != null) {
                this.identityContext.setTenantId(tenant);
            }
            //values = headers.getRequestHeader("Authorization");
            //if (values != null && !values.isEmpty()) {
                //tring userPassword[] = RequestHelper.decode(values.get(0));
                //this.identityContext.setUserName(userPassword[0]);
            //}
        }
        return ctx.proceed();
    }
}
