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

import com.google.common.collect.Lists;
import org.ow2.sirocco.cloudmanager.api.openstack.api.annotations.Extension;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroupForCreate;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.functions.SecurityGroupToSecurityGroup;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.QueryParams;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.core.api.exception.InvalidRequestException;
import org.ow2.sirocco.cloudmanager.core.api.exception.ResourceNotFoundException;
import org.ow2.sirocco.cloudmanager.model.cimi.CloudResource;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.SecurityGroupCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResponseHelper.deleted;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.badRequest;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
@Extension(of = "compute", name = "os-security-groups", documentation = "http://api.openstack.org/api-ref-compute.html#os-security-groups")
public class SecurityGroups extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.SecurityGroups {

    private static Logger LOG = LoggerFactory.getLogger(SecurityGroups.class);

    @Inject
    protected INetworkManager networkManager;

    @Override
    public Response list() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Get list of security groups for tenant " + getTenantId());
        }

        org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroups groups = new org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroups();
        try {
            groups.setGroups(Lists.transform(networkManager.getSecurityGroups().getItems(), new SecurityGroupToSecurityGroup()));
            return ok(groups);
        } catch (InvalidRequestException ire) {
            return badRequest("securitygroups", "get");
        } catch (CloudProviderException e) {
            final String error = "Error while getting security groups details";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Security Groups Error", e.getMessage());
        }
    }

    @Override
    public Response create(SecurityGroupForCreate securityGroupForCreate) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Create a new security group " + securityGroupForCreate);
        }

        if (securityGroupForCreate == null || securityGroupForCreate.getName() == null) {
            return badRequest("Invalid arguments", "Can not create from null request parameters");
        }

        SecurityGroupCreate securityGroupCreate = new SecurityGroupCreate();
        securityGroupCreate.setName(securityGroupForCreate.getName());
        securityGroupCreate.setDescription(securityGroupForCreate.getDescription());
        try {
            Job job = this.networkManager.createSecurityGroup(securityGroupCreate);
            CloudResource resource = job.getTargetResource();
            return ok(new SecurityGroupToSecurityGroup().apply(this.networkManager.getSecurityGroupByUuid(resource.getUuid())));
        } catch (InvalidRequestException ire) {
            final String warning = "Problem while creating security group";
            LOG.warn(warning, ire);
            return badRequest(warning, ire.getMessage());
        } catch (CloudProviderException e) {
            final String error = "Can not create security group";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault(500, error, e.getMessage());
        }
    }

    @Override
    public Response listForServer(String serverId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Get list of security groups for tenant " + getTenantId() + " and server " + serverId);
        }

        org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroups groups = new org.ow2.sirocco.cloudmanager.api.openstack.nova.extensions.securitygroups.model.SecurityGroups();
        // TODO : Query machine UUID
        QueryParams query = null;
        LOG.warn("TODO : query for current tenant in security groups list");
        try {
            groups.setGroups(Lists.transform(networkManager.getSecurityGroups().getItems(), new SecurityGroupToSecurityGroup()));
            return ok(groups);
        } catch (InvalidRequestException ire) {
            return badRequest("securitygroups", "get");
        } catch (CloudProviderException e) {
            final String error = "Error while getting security groups details";
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault("Security Groups Error", e.getMessage());
        }
    }

    @Override
    public Response get(String id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Get security group ID " + id);
        }

        try {
            return ok(new SecurityGroupToSecurityGroup().apply(networkManager.getSecurityGroupByUuid(id)));
        } catch (ResourceNotFoundException e) {
            return resourceNotFoundException("security_group", id, e);
        }
    }

    @Override
    public Response delete(String id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Delete security group ID " + id);
        }

        try {
            this.networkManager.deleteSecurityGroup(id);
            return deleted();
        } catch (CloudProviderException e) {
            final String error = "Error while deleting security group " + id;
            if (LOG.isDebugEnabled()) {
                LOG.debug(error, e);
            } else {
                LOG.error(error);
            }
            return computeFault(500, "Server Error", e.getMessage());
        }
    }
}
