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

package org.ow2.sirocco.cloudmanager.api.openstack.server.functions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.JaxRsRequestInfo;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.RequestHelper;
import org.ow2.sirocco.cloudmanager.api.openstack.nova.Constants;
import org.ow2.sirocco.cloudmanager.core.api.QueryParams;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;

import java.util.List;
import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServerListQuery implements Function<JaxRsRequestInfo, QueryParams> {

    /**
     * Key is the Openstack param name, Value is the Sirocco one
     */
    public static Map<String, String> mapping = Maps.newHashMap();

    /**
     * Key is the Openstack status value, value is the sirocco state equivalent
     */
    public static Map<org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status, Machine.State> STATUS = Maps.newHashMap();

    static {
        mapping.put(Constants.REQUEST_CHANGESSINCE, null);
        mapping.put(Constants.REQUEST_FLAVOR, null);
        mapping.put(Constants.REQUEST_HOST, null);
        mapping.put(Constants.REQUEST_IMAGE, "image.id");
        mapping.put(Constants.REQUEST_LIMIT, null);
        mapping.put(Constants.REQUEST_MARKER, null);
        mapping.put(Constants.REQUEST_NAME, "name");
        mapping.put(Constants.REQUEST_STATUS, "state");

        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.ACTIVE, Machine.State.STARTED);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.BUILD, Machine.State.CREATING);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.DELETED, Machine.State.DELETED);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.ERROR, Machine.State.ERROR);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.HARD_REBOOT, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.PASSWORD, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.REBOOT, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.REBUILD, Machine.State.CREATING);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.RESIZE, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.REVERT_RESIZE, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.SUSPENDED, Machine.State.SUSPENDED);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.UNKNOWN, null);
        STATUS.put(org.ow2.sirocco.cloudmanager.api.openstack.commons.Constants.Nova.Status.VERIFY_RESIZE, null);
    }

    /**
     *
     * @param input
     * @return
     *  null if there are no input parameters
     *  queryparams with filter values if there are any valid filter in the incoming request parameter
     */
    @Override
    public QueryParams apply(JaxRsRequestInfo input) {
        if (RequestHelper.getQueryParameters(input).size() == 0) {
            return null;
        }

        // let's get all parameters and map to the sirocco model...
        Map<String, List<String>> params = RequestHelper.getQueryParameters(input);
        final Map<String, String> p = Maps.transformValues(params, new Function<List<String>, String>() {
            @Override
            public String apply(List<String> input) {
                return input.get(0);
            }
        });

        List<Query> filter = Lists.newArrayList(Iterators.filter(Iterators.transform(p.keySet().iterator(), new Function<String, Query>() {
            @Override
            public Query apply(String key) {
                Query q = new Query();
                // key may be null. Will not applied if null.
                q.name = mapping.get(key.toLowerCase());
                // TODO : Operator depends in the input type
                q.operator = "=";
                q.value = p.get(key);
                return q;
            }
        }), new Predicate<Query>() {
            // do not keep query with null names. It means that there is no mapping between openstack and sirocco...
            @Override
            public boolean apply(org.ow2.sirocco.cloudmanager.api.openstack.server.functions.ServerListQuery.Query input) {
                return input.name != null;
            }
        }));

        List<String> f = Lists.transform(filter, new Function<Query, String>() {
            @Override
            public String apply(org.ow2.sirocco.cloudmanager.api.openstack.server.functions.ServerListQuery.Query input) {
                return input.toString();
            }
        });

        return new QueryParams.Builder().filters(f).build();
    }

    public class Query {
        String name;
        String operator;
        String value;

        @Override
        public String toString() {
            return name + operator + value;
        }
    }
}
