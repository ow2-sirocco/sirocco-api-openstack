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

package org.ow2.sirocco.cloudmanager.api.openstack.commons.provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Configure the way date and specific fields are serialized with Jackson
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Provider
public class JacksonConfigurator implements ContextResolver<ObjectMapper> {
    /**
     * Mapper to access to the configuration.
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Default constructor.
     */
    public JacksonConfigurator() {
        this.mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Zulu"));
        this.mapper.setDateFormat(dateFormat);

        this.mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        // OpenStack API adds resource name as root value
        this.mapper.enable(SerializationConfig.Feature.WRAP_ROOT_VALUE);
        this.mapper.enable(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE);
        this.mapper.enable(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        // Use to avoid Error 500 when a field is unknown
        this.mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * {@inheritDoc}
     *
     * @see javax.ws.rs.ext.ContextResolver#getContext(java.lang.Class)
     */
    @Override
    public ObjectMapper getContext(final Class<?> arg0) {
        return this.mapper;
    }
}
