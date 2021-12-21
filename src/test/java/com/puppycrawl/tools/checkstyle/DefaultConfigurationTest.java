////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class DefaultConfigurationTest {

    @Test
    public void testGetPropertyNames() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        config.addProperty("property", "value");
        final String[] actual = config.getPropertyNames();
        final String[] expected = {"property"};
        assertArrayEquals(expected, actual, "Invalid property names");
    }

    @Test
    public void testAddPropertyAndGetProperty() throws CheckstyleException {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        config.addProperty("property", "first");
        assertEquals("first", config.getProperty("property"), "Invalid property value");
        config.addProperty("property", "second");
        assertEquals("first,second", config.getProperty("property"), "Invalid property value");
    }

    @Test
    public void testDeprecatedAttributeMethods() throws CheckstyleException {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        config.addAttribute("attribute", "first");
        final String[] actual = config.getAttributeNames();
        final String[] expected = {"attribute"};
        assertArrayEquals(expected, actual, "Invalid attribute names");
        assertEquals("first", config.getAttribute("attribute"), "Invalid property value");
        config.addAttribute("attribute", "second");
        assertEquals("first,second", config.getAttribute("attribute"), "Invalid property value");
    }

    @Test
    public void testGetName() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        assertEquals("MyConfig", config.getName(), "Invalid configuration name");
    }

    @Test
    public void testRemoveChild() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        final DefaultConfiguration configChild = new DefaultConfiguration("childConfig");
        assertEquals(0, config.getChildren().length, "Invalid children count");
        config.addChild(configChild);
        assertEquals(1, config.getChildren().length, "Invalid children count");
        config.removeChild(configChild);
        assertEquals(0, config.getChildren().length, "Invalid children count");
    }

    @Test
    public void testAddMessageAndGetMessages() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        config.addMessage("key", "value");
        final Map<String, String> expected = new TreeMap<>();
        expected.put("key", "value");
        assertEquals(expected, config.getMessages(), "Invalid message map");
    }

    @Test
    public void testExceptionForNonExistentProperty() {
        final String name = "MyConfig";
        final DefaultConfiguration config = new DefaultConfiguration(name);
        final String propertyName = "NonExistent#$%";
        try {
            config.getProperty(propertyName);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException expected) {
            assertEquals(
                    "missing key '" + propertyName + "' in " + name,
                    expected.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testDefaultMultiThreadConfiguration() {
        final String name = "MyConfig";
        final DefaultConfiguration config = new DefaultConfiguration(name);
        final ThreadModeSettings singleThreadMode =
                ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;
        assertEquals(singleThreadMode, config.getThreadModeSettings(), "Invalid thread mode");
    }

    @Test
    public void testMultiThreadConfiguration() {
        final String name = "MyConfig";
        final ThreadModeSettings multiThreadMode =
                new ThreadModeSettings(4, 2);
        final DefaultConfiguration config = new DefaultConfiguration(name, multiThreadMode);
        assertEquals(multiThreadMode, config.getThreadModeSettings(), "Invalid thread mode");
    }

}
