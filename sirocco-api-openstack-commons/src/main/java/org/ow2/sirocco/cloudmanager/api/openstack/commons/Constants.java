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

package org.ow2.sirocco.cloudmanager.api.openstack.commons;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public final class Constants {

    public static final class Link {
        public static final String BOOKMARK = "bookmark";

        public static final String SELF = "self";
    }

    public static final class Nova {

        public static final String BASE_PATH = "/v2";

        public static final String TENANT_ID_PATH_PARAMETER = "tenant_id";

        public static final String SERVER_ID_PATH_PARAMETER = "server_id";

        public static final String IMAGE_ID_PATH_PARAMETER = "image_id";

        public static final String TENANT_PATH_TEMPLATE = BASE_PATH + "/{" + TENANT_ID_PATH_PARAMETER + "}";

        public static final String SERVERS_PATH = TENANT_PATH_TEMPLATE + "/servers";

        public static final String SERVERS_ACTION_PATH = SERVERS_PATH + "/{" + SERVER_ID_PATH_PARAMETER + "}/action";

        public static final String SERVER_METADATA_PATH = SERVERS_PATH + "/{" + SERVER_ID_PATH_PARAMETER + "}/metadata";

        public static final String FLAVORS_PATH = BASE_PATH + "/flavors";

        public static final String IMAGES_PATH = BASE_PATH + "/images";

        public static final String IMAGE_METADATA_PATH = BASE_PATH + "/{" + TENANT_ID_PATH_PARAMETER + "}/images/{" + IMAGE_ID_PATH_PARAMETER + "}/metadata";

        public static final String SERVER_ADDRESSES_PATH = SERVERS_PATH + "/{" + SERVER_ID_PATH_PARAMETER + "}/ips";

        /**
         * Server status
         * cf https://github.com/openstack/api-site/blob/master/api-ref/src/wadls/compute-api/src/xsd/server.xsd
         */
        public enum Status {
            ACTIVE, SUSPENDED, DELETED, RESIZE, VERIFY_RESIZE, REVERT_RESIZE, ERROR, BUILD, PASSWORD, REBUILD, REBOOT, HARD_REBOOT, UNKNOWN
        }

    }
}
