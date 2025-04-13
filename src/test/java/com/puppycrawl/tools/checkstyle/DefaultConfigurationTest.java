///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

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
        assertWithMessage("Invalid property names")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testAddPropertyAndGetProperty() throws CheckstyleException {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        config.addProperty("property", "first");
        assertWithMessage("Invalid property value")
            .that(config.getProperty("property"))
            .isEqualTo("first");
        config.addProperty("property", "second");
        assertWithMessage("Invalid property value")
            .that(config.getProperty("property"))
            .isEqualTo("first,second");
    }

    /*
     * This method is deprecated due to usage of deprecated DefaultConfiguration#addAttribute
     * we keep this method until https://github.com/checkstyle/checkstyle/issues/11722
     */
    @Deprecated(since = "10.2")
    @Test
    public void testDeprecatedAttributeMethods() throws CheckstyleException {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        config.addAttribute("attribute", "first");
        final String[] actual = config.getAttributeNames();
        final String[] expected = {"attribute"};
        assertWithMessage("Invalid attribute names")
            .that(actual)
            .isEqualTo(expected);
        assertWithMessage("Invalid property value")
            .that(config.getAttribute("attribute"))
            .isEqualTo("first");
        config.addAttribute("attribute", "second");
        assertWithMessage("Invalid property value")
            .that(config.getAttribute("attribute"))
            .isEqualTo("first,second");
    }

    @Test
    public void testGetName() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        assertWithMessage("Invalid configuration name")
            .that(config.getName())
            .isEqualTo("MyConfig");
    }

    @Test
    public void testRemoveChild() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        final DefaultConfiguration configChild = new DefaultConfiguration("childConfig");
        assertWithMessage("Invalid children count")
            .that(config.getChildren().length)
            .isEqualTo(0);
        config.addChild(configChild);
        assertWithMessage("Invalid children count")
            .that(config.getChildren().length)
            .isEqualTo(1);
        config.removeChild(configChild);
        assertWithMessage("Invalid children count")
            .that(config.getChildren().length)
            .isEqualTo(0);
    }

    @Test
    public void testAddMessageAndGetMessages() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        config.addMessage("key", "value");
        final Map<String, String> expected = new TreeMap<>();
        expected.put("key", "value");
        assertWithMessage("Invalid message map")
            .that(config.getMessages())
            .isEqualTo(expected);
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
            assertWithMessage("Invalid exception message")
                .that(expected.getMessage())
                .isEqualTo("missing key '" + propertyName + "' in " + name);
        }
    }

    @Test
    public void testDefaultMultiThreadConfiguration() {
        final String name = "MyConfig";
        final DefaultConfiguration config = new DefaultConfiguration(name);
        final ThreadModeSettings singleThreadMode =
                ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;
        assertWithMessage("Invalid thread mode")
            .that(config.getThreadModeSettings())
            .isEqualTo(singleThreadMode);
    }

    @Test
    public void testMultiThreadConfiguration() {
        final String name = "MyConfig";
        final ThreadModeSettings multiThreadMode =
                new ThreadModeSettings(4, 2);
        final DefaultConfiguration config = new DefaultConfiguration(name, multiThreadMode);
        assertWithMessage("Invalid thread mode")
            .that(config.getThreadModeSettings())
            .isEqualTo(multiThreadMode);
    }

}
