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

package org.ow2.sirocco.cloudmanager.api.openstack.server;

import org.ow2.sirocco.cloudmanager.api.openstack.commons.provider.JacksonConfigurator;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder.Snapshots;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder.Types;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder.Volumes;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron.Networks;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron.Ports;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.neutron.Subnets;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.*;
import org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.extensions.*;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class OpenStackApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        classes.add(JacksonConfigurator.class);
        //classes.add(JacksonJsonProvider.class);

        // NOVA
        classes.add(Extensions.class);
        classes.add(Flavors.class);
        classes.add(ImageMetadata.class);
        classes.add(Images.class);
        classes.add(ServerActions.class);
        classes.add(ServerAddresses.class);
        classes.add(ServerMetadata.class);
        classes.add(Servers.class);
        classes.add(Versions.class);

        // CINDER
        classes.add(Snapshots.class);
        classes.add(Types.class);
        classes.add(Volumes.class);

        // NEUTRON
        classes.add(Networks.class);
        classes.add(Ports.class);
        classes.add(Subnets.class);

        // NOVA EXTENSIONS
        classes.add(Keypairs.class);
        classes.add(VolumeAttachments.class);
        classes.add(SecurityGroups.class);
        classes.add(SecurityGroupRules.class);
        classes.add(FloatingIPs.class);

        return classes;
    }
}
