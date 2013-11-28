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

package org.ow2.sirocco.cloudmanager.api.openstack.server.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class MapHelper {

    /**
     * Updates the orignal map with the values of the updates map. It will not add any entry to the original map.
     *
     * @param original
     * @param updates
     * @return
     */
    public static Map<String, String> updateMap(final Map<String, String> original, final Map<String, String> updates) {
        if (updates == null) {
            return original;
        }
        return Maps.transformEntries(original, new Maps.EntryTransformer<String, String, String>() {
            @Override
            public String transformEntry(String key, String value) {

                if (updates.get(key) != null) {
                    return updates.get(key);
                } else {
                    return value;
                }
            }
        });
    }

    /**
     * Replace the values of the orignal map entries with the replace ones, add missing ones and remove the ones which are not defined in the replace map.
     *
     * @param original
     * @param replace
     * @return
     */
    public static Map<String, String> replaceMap(final Map<String, String> original, final Map<String, String> replace) {
        if (replace == null || replace.size() == 0) {
            return Maps.newHashMap();
        }

        // first, remove entries which are not defined in the replace map
        Map<String, String> filter = Maps.filterEntries(original, new Predicate<Map.Entry<String, String>>() {
            @Override
            public boolean apply(java.util.Map.Entry<String, String> input) {
                return replace.get(input.getKey()) != null;
            }
        });

        // then update the values of the filtered map
        Map<String, String> updated = Maps.transformEntries(filter, new Maps.EntryTransformer<String, String, String>() {
            @Override
            public String transformEntry(String key, String value) {
                if (replace.get(key) != null) {
                    return replace.get(key);
                } else {
                    return value;
                }
            }
        });

        // then add the entries from the replace map which are not in the updated map
        Map<String, String> result = Maps.newHashMap(updated);
        result.putAll(Maps.difference(updated, replace).entriesOnlyOnRight());
        return result;
    }
}
