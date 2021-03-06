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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.AbstractResource;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.ResourceInterceptorBinding;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.Action;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.badRequest;
import static org.ow2.sirocco.cloudmanager.api.openstack.nova.helpers.ResponseHelper.computeFault;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@ResourceInterceptorBinding
@RequestScoped
public class ServerActions extends AbstractResource implements org.ow2.sirocco.cloudmanager.api.openstack.nova.resources.ServerActions {

    @Inject @Any
    private Instance<Action> actions;

    @Override
    public Response action(InputStream stream) {
        if (stream == null) {
            return badRequest("ServerAction", "undefined : empty payload");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null; // src can be a File, URL, InputStream etc
        try {
            rootNode = mapper.readValue(stream, JsonNode.class);
        } catch (IOException e) {
            return computeFault(500, "Error while reading JSON", e.getMessage());
        }

        String actionName = Iterators.get(rootNode.getFieldNames(), 0);
        Action action = getAction(actionName);
        if (action == null) {
            return computeFault(500, "Can not retrieve action", "Action not found for " + actionName);
        } else {
            return action.invoke(getServerId(), rootNode);
        }
    }

    /**
     *
     * @param actionName
     * @return
     */
    protected Action getAction(final String actionName) {
        if (actionName == null || actions == null) {
            return null;
        }
        return Iterators.tryFind(actions.iterator(), new Predicate<Action>() {
            @Override
            public boolean apply(Action action) {
                return actionName.equals(action.getName());
            }
        }).orNull();
    }

}
