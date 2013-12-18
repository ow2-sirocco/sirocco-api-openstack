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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions;

import com.google.common.base.Function;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.domain.Link;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.model.Flavor;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;

/**
 * Transforms a sirocco machine configuration to an openstack flavor
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class MachineConfigurationToFlavor implements Function<MachineConfiguration, Flavor> {

    private final boolean details;

    public MachineConfigurationToFlavor(boolean details) {
        this.details = details;
    }

    @Override
    public Flavor apply(MachineConfiguration input) {
        Flavor result = new Flavor();
        result.id = "" + input.getId();
        result.name = input.getName();

        result.links.add(new Link("http://TODO/self/" + input.getId(), "self"));
        result.links.add(new Link("http://TODO/bookmark/" + input.getId(), "bookmark"));

        if (details) {
            // TODO
        }

        return result;
    }
}
