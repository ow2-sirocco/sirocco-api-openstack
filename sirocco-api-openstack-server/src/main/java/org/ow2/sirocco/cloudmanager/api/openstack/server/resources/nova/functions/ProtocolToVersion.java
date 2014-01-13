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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions;

import com.google.common.base.Function;

/**
 * Change the Sirocco protocol to openstack IP version (4, 6).
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ProtocolToVersion implements Function<String, Integer> {

    public static int V4 = 4;

    public static int V6 = 6;

    static Integer DEFAULT = V4;

    @Override
    public Integer apply(String protocol) {
        if (protocol == null) {
            return DEFAULT;
        }

        Integer result;
        try {
            // first try to parse int from string...
            result = Integer.parseInt(protocol);

            if (result != V4 && result != V6) {
                result = DEFAULT;
            }

        } catch (NumberFormatException e) {
            if (protocol.contains("" + V4)) {
                result = V4;
            } else if (protocol.contains("" + V6)) {
                result = V6;
            } else {
                result = DEFAULT;
            }
        }
        return result;
    }
}
