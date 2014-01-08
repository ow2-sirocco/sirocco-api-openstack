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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions;

import org.ow2.sirocco.cloudmanager.api.openstack.api.annotations.Extension;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygrouprules.model.SecurityGroupDefaultRule;
import org.ow2.sirocco.cloudmanager.core.api.IJobManager;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroup;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroupCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroupRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.notImplemented;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.badRequest;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
@Extension(of = "compute", name = "os-security-group-rules", documentation = "http://api.openstack.org/api-ref-compute.html#os-security-group-rules")
public class SecurityGroupRules extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygrouprules.SecurityGroupRules {

    private static Logger LOG = LoggerFactory.getLogger(SecurityGroupRules.class);

    @Inject
    private INetworkManager networkManager;

    @Inject
    private IJobManager jobManager;

    @Override
    public Response list() {
        LOG.warn("SecurityGroupRules.list() is not implemented");
        return notImplemented("SecurityGroupRules", "list");
    }

    @Override
    public Response create(SecurityGroupDefaultRule rule) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Create security group default rule");
        }
        if (rule == null || rule.getRule() == null) {
            return badRequest("null input", "Can not create a security group default rule from empty request");
        }
        
        // FIXME: is it just a about creating a security group and adding the rule?
        // FIXME : Do it in another thread!
        try {
            SecurityGroupCreate create = new SecurityGroupCreate();
            create.setName("default");
            create.setDescription("Default security group for tenant " + getTenantId());
            Job job = this.networkManager.createSecurityGroup(create);
            SecurityGroup group = (SecurityGroup) job.getTargetResource();
            // wait for the job to complete to add the rule
            String jobUuid = job.getUuid();
            int counter = 20;
            while (true) {
                job = this.jobManager.getJobByUuid(jobUuid);
                if (job.getState() != Job.Status.RUNNING) {
                    break;
                }
                Thread.sleep(1000);
                if (counter-- == 0) {
                    return computeFault("create security group error", "timeout");
                }
            }
            SecurityGroupRule securityGroupRule = this.networkManager.addRuleToSecurityGroupUsingIpRange(group.getUuid(), rule.getRule().getIpRange().getCidr(), rule.getRule().getIpProtocol(), rule.getRule().getFromPort(), rule.getRule().getToPort());
            rule.getRule().setId(securityGroupRule.getUuid());
            return ok(rule);
        } catch (Exception e) {
            return computeFault("create security group error", e.getMessage());
        }
    }

    @Override
    public Response get(String id) {
        LOG.warn("SecurityGroupRules.get(id) is not implemented");
        return notImplemented("SecurityGroupRules", "list");
    }
}
