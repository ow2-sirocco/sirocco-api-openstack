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

package org.ow2.sirocco.cloudmanager.api.openstack.server.resources.nova.functions.queries;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.JaxRsRequestInfo;
import org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.RequestHelper;
import org.ow2.sirocco.cloudmanager.core.api.QueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class AbstractQuery implements Function<JaxRsRequestInfo, QueryParams> {

    protected static Logger LOG = LoggerFactory.getLogger(AbstractQuery.class);

    @Override
    public QueryParams apply(org.ow2.sirocco.cloudmanager.api.openstack.commons.resource.JaxRsRequestInfo input) {
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
                Query q = getQuery(key.toLowerCase());
                q.name = getSiroccoParamName(key.toLowerCase());
                q.operator = "=";
                q.value = getSiroccoParamValue(key, p.get(key));
                return q;
            }
        }), new Predicate<Query>() {
            // do not keep query with null names. It means that there is no mapping between openstack and sirocco...
            @Override
            public boolean apply(ServerListQuery.Query input) {
                return input.name != null;
            }
        }));

        List<String> f = Lists.transform(filter, new Function<Query, String>() {
            @Override
            public String apply(ServerListQuery.Query input) {
                return input.toString();
            }
        });
        LOG.info("Query list : " + f);

        return new QueryParams.Builder().filters(f).build();
    }

    /**
     * Get the sirocco parameter name from the openstack one
     *
     * @param openstackParamName
     * @return
     */
    protected abstract String getSiroccoParamName(String openstackParamName);

    /**
     * Get the sirocco parameter value from the openstack one.
     * For example, this can be used to translate paramaters values in the right format (date is a good exemple...).
     *
     * @param openstackParamName
     * @param openstackParamValue
     * @return
     */
    protected String getSiroccoParamValue(String openstackParamName, String openstackParamValue) {
        return openstackParamValue;
    }

    protected Query getQuery(String paramName) {
        return new Query();
    }

    public class Query {
        String name;
        String operator;
        String value;

        public Query() {
        }

        public Query(String name, String operator, String value) {
            this.name = name;
            this.operator = operator;
            this.value = value;
        }

        @Override
        public String toString() {
            return name + operator + "'" + value + "'";
        }
    }

}
