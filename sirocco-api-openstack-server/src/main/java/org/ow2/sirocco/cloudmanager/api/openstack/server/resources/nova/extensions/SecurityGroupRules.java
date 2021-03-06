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
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Link;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.ExtensionProvider;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygrouprules.model.SecurityGroupRuleForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions.SecurityGroupRuleToSecurityGroupRule;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroupRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.deleted;
import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.notImplemented;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.badRequest;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
@Extension(of = "compute", name = "os-security-group-rules", documentation = "http://api.openstack.org/api-ref-compute.html#os-security-group-rules")
public class SecurityGroupRules extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygrouprules.SecurityGroupRules, ExtensionProvider {

    private static Logger LOG = LoggerFactory.getLogger(SecurityGroupRules.class);

    @Inject
    private INetworkManager networkManager;

    @Override
    public Response list() {
        LOG.warn("SecurityGroupRules.list() is not implemented");
        return notImplemented("SecurityGroupRules", "list");
    }

    @Override
    public Response create(SecurityGroupRuleForCreate rule) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Add a rule to security group : " + rule);
        }
        if (rule == null) {
            return badRequest("null input", "Can not add a rule from empty request");
        }

        try {
            SecurityGroupRule securityGroupRule = null;
            if (rule.getParentGroupId() != null && rule.getGroupId() == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Adding rule to security group using IP range");
                }
                securityGroupRule = this.networkManager.addRuleToSecurityGroupUsingIpRange(rule.getParentGroupId(), rule.getCidr(), rule.getIpProtocol(), rule.getFromPort(), rule.getToPort());
            } else if (rule.getParentGroupId() != null && rule.getGroupId() != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Adding rule to security group using source group");
                }
                securityGroupRule = this.networkManager.addRuleToSecurityGroupUsingSourceGroup(rule.getParentGroupId(), rule.getGroupId(), rule.getIpProtocol(), rule.getFromPort(), rule.getToPort());
            } else {
                return badRequest("Bad request", "Can not add rule from CIDR nor source group, check parameters");
            }
            return ok(new SecurityGroupRuleToSecurityGroupRule().apply(securityGroupRule));
        } catch (Exception e) {
            return computeFault("create security group error", e.getMessage());
        }
    }

    @Override
    public Response get(String id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Get security group rule " + id);
        }

        try {
            return ok(new SecurityGroupRuleToSecurityGroupRule().apply(networkManager.getSecurityGroupRuleByUuid(id)));
        } catch (ResourceNotFoundException e) {
            return resourceNotFoundException("secgrouprule", id, e);
        }
    }

    @Override
    public Response delete(String id) {
        try {
            this.networkManager.deleteRuleFromSecurityGroup(id);
            return deleted();
        } catch (CloudProviderException e) {
            final String error = "Error while deleting security group rule " + id;
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault(error, e.getMessage());
        }
    }

    @Override
    public org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Extension getExtensionMetadata() {
        return new org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Extension("os-security-group-default-rules", "Default rules for security group support.", new ArrayList<Link>(), "SecurityGroupDefaultRules", "http://docs.openstack.org/compute/ext/securitygroupdefaultrules/api/v1.1", new Date());
    }
}
