////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class DefaultConfigurationTest {

    @Test
    public void testRemoveChild() {
        final DefaultConfiguration config = new DefaultConfiguration("MyConfig");
        final DefaultConfiguration configChild = new DefaultConfiguration("childConfig");
        assertEquals(0, config.getChildren().length);
        config.addChild(configChild);
        assertEquals(1, config.getChildren().length);
        config.removeChild(configChild);
        assertEquals(0, config.getChildren().length);
    }

    @Test
    public void testExceptionForNonExistingAttribute() {
        final String name = "MyConfig";
        final DefaultConfiguration config = new DefaultConfiguration(name);
        final String attributeName = "NonExisting#$%";
        try {
            config.getAttribute(attributeName);
            fail("Exception is expected");
        }
        catch (CheckstyleException expected) {
            assertEquals("missing key '" + attributeName + "' in " + name,
                    expected.getMessage());
        }
    }

    @Test
    public void testDefaultMultiThreadConfiguration() throws Exception {
        final String name = "MyConfig";
        final DefaultConfiguration config = new DefaultConfiguration(name);
        final ThreadModeSettings singleThreadMode =
                ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;
        assertEquals(singleThreadMode, config.getThreadModeSettings());
    }

    @Test
    public void testMultiThreadConfiguration() throws Exception {
        final String name = "MyConfig";
        final ThreadModeSettings multiThreadMode =
                new ThreadModeSettings(4, 2);
        final DefaultConfiguration config = new DefaultConfiguration(name, multiThreadMode);
        assertEquals(multiThreadMode, config.getThreadModeSettings());
    }
}
