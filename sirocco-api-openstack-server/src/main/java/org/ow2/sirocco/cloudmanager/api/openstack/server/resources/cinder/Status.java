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
package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.cinder;

import com.google.common.base.CaseFormat;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Volume status
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public enum Status {
    CREATING, AVAILABLE, ATTACHING, IN_USE, DELETING, ERROR, ERROR_DELETING, UNRECOGNIZED;

    public String value() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, name());
    }

    @Override
    public String toString() {
        return value();
    }

    public static Status fromValue(String status) {
        try {
            return valueOf(CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, checkNotNull(status, "status")));
        } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
        }
    }
}
