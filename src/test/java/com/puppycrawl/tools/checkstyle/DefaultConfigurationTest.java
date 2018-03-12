////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class DefaultConfigurationTest {

    @Test
    public void testGetAttributeNames() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        config.addAttribute("attribute", "value");
        final String[] actual = config.getAttributeNames();
        final String[] expected = {"attribute"};
        assertArrayEquals("Invalid attribute names", expected, actual);
    }

    @Test
    public void testAddAttributeAndGetAttribute() throws CheckstyleException {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        config.addAttribute("attribute", "first");
        assertEquals("Invalid attribute value", "first", config.getAttribute("attribute"));
        config.addAttribute("attribute", "second");
        assertEquals("Invalid attribute value", "first,second", config.getAttribute("attribute"));
    }

    @Test
    public void testGetName() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        assertEquals("Invalid configuration name", "MyConfig", config.getName());
    }

    @Test
    public void testRemoveChild() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        final DefaultConfiguration configChild = new DefaultConfiguration("childConfig");
        assertEquals("Invalid children count", 0, config.getChildren().length);
        config.addChild(configChild);
        assertEquals("Invalid children count", 1, config.getChildren().length);
        config.removeChild(configChild);
        assertEquals("Invalid children count", 0, config.getChildren().length);
    }

    @Test
    public void testAddMessageAndGetMessages() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        config.addMessage("key", "value");
        final Map<String, String> expected = new TreeMap<>();
        expected.put("key", "value");
        assertEquals("Invalid message map", expected, config.getMessages());
    }

    @Test
    public void testExceptionForNonExistentAttribute() {
        final String name = "MyConfig";
        final DefaultConfiguration config = new DefaultConfiguration(name);
        final String attributeName = "NonExistent#$%";
        try {
            config.getAttribute(attributeName);
            fail("Exception is expected");
        }
        catch (CheckstyleException expected) {
            assertEquals("Invalid exception message",
                    "missing key '" + attributeName + "' in " + name,
                    expected.getMessage());
        }
    }

    @Test
    public void testDefaultMultiThreadConfiguration() {
        final String name = "MyConfig";
        final DefaultConfiguration config = new DefaultConfiguration(name);
        final ThreadModeSettings singleThreadMode =
                ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;
        assertEquals("Invalid thread mode", singleThreadMode, config.getThreadModeSettings());
    }

    @Test
    public void testMultiThreadConfiguration() {
        final String name = "MyConfig";
        final ThreadModeSettings multiThreadMode =
                new ThreadModeSettings(4, 2);
        final DefaultConfiguration config = new DefaultConfiguration(name, multiThreadMode);
        assertEquals("Invalid thread mode", multiThreadMode, config.getThreadModeSettings());
    }

}
