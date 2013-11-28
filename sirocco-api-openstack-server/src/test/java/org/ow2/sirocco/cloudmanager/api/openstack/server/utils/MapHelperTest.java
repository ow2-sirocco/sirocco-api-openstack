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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.*;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class MapHelperTest {

    @Test
    public void testUpdateNullUpdate() {
        Map<String,String> in = ImmutableMap.of("foo", "1", "bar", "2", "baz", "3");
        Map<String, String> out = MapHelper.updateMap(in, null);
        assertTrue(Maps.difference(out, in).areEqual());
    }

    @Test
    public void testEmptyUpdate() {
        Map<String,String> in = ImmutableMap.of("foo", "1", "bar", "2", "baz", "3");
        Map<String, String> updates = Maps.newHashMap();
        Map<String, String> out = MapHelper.updateMap(in, updates);
        assertTrue(Maps.difference(out, in).areEqual());
    }

    @Test
    public void testOneUpdate() {
        Map<String,String> in = ImmutableMap.of("foo", "1", "bar", "2", "baz", "3");
        Map<String, String> updates = ImmutableMap.of("foo", "A");
        Map<String, String> out = MapHelper.updateMap(in, updates);
        assertFalse(Maps.difference(out, in).areEqual());
        assertEquals(updates.get("foo"), out.get("foo"));

        assertTrue(Maps.difference(out, in).entriesInCommon().size() == 2);
        assertTrue(Maps.difference(out, in).entriesDiffering().size() == 1);
    }

    @Test
    public void testUpdateWithMoreElements() {
        Map<String,String> in = ImmutableMap.of("foo", "1");
        Map<String, String> updates = ImmutableMap.of("foo", "A", "bar", "B");
        Map<String, String> out = MapHelper.updateMap(in, updates);
        assertFalse(Maps.difference(out, in).areEqual());
        assertEquals(updates.get("foo"), out.get("foo"));
        assertEquals(1, out.size());
    }

    @Test
    public void testReplaceNull() {
        Map<String,String> in = ImmutableMap.of("foo", "1", "bar", "2", "baz", "3");
        Map<String, String> out = MapHelper.replaceMap(in, null);
        assertTrue(out.size() == 0);
    }

    @Test
    public void testReplaceEmpty() {
        Map<String,String> in = ImmutableMap.of("foo", "1", "bar", "2", "baz", "3");
        Map<String, String> updates = Maps.newHashMap();

        Map<String, String> out = MapHelper.replaceMap(in, updates);
        assertTrue(out.size() == 0);
    }

    @Test
    public void testReplaceOne() {
        Map<String,String> in = ImmutableMap.of("foo", "1", "bar", "2", "baz", "3");
        Map<String, String> replace = ImmutableMap.of("foo", "A");

        Map<String, String> out = MapHelper.replaceMap(in, replace);
        assertTrue(out.size() == 1);
        assertEquals(replace.get("foo"), out.get("foo"));
    }

    @Test
    public void testReplaceTwo() {
        Map<String, String> in = ImmutableMap.of("foo", "1", "bar", "2", "baz", "3");
        Map<String, String> replace = ImmutableMap.of("foo", "A", "bar", "B");

        Map<String, String> out = MapHelper.replaceMap(in, replace);
        assertTrue(out.size() == 2);

        System.out.println(out);

        assertEquals(replace.get("foo"), out.get("foo"));
        assertEquals(replace.get("bar"), out.get("bar"));
    }

    @Test
    public void testReplaceOneAndAddOne() {
        Map<String, String> in = ImmutableMap.of("foo", "1", "bar", "2");
        Map<String, String> replace = ImmutableMap.of("foo", "A", "baz", "B");

        Map<String, String> out = MapHelper.replaceMap(in, replace);
        assertTrue(out.size() == 2);

        System.out.println(out);

        assertEquals(replace.get("foo"), out.get("foo"));
        assertNull(out.get("bar"));
        assertNotNull(out.get("baz"));
        assertEquals(replace.get("baz"), out.get("baz"));
    }

    @Test
    public void testReplacePutAll() {
        Map<String, String> in = Maps.newHashMap();
        Map<String, String> replace = ImmutableMap.of("foo", "A", "baz", "B");

        Map<String, String> out = MapHelper.replaceMap(in, replace);
        assertTrue(out.size() == 2);

        System.out.println(out);

        assertEquals(replace.get("foo"), out.get("foo"));
        assertEquals(replace.get("baz"), out.get("baz"));
    }

}
