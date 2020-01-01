////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class PropertiesExpanderTest {

    @Test
    public void testCtorException() {
        try {
            final Object test = new PropertiesExpander(null);
            fail("exception expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            assertEquals("cannot pass null", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testDefaultProperties() {
        final Properties properties = new Properties(System.getProperties());
        properties.setProperty("test", "checkstyle");
        final String propertiesUserHome = properties.getProperty("user.home");
        assertEquals(System.getProperty("user.home"), propertiesUserHome,
                "Invalid user.home property");
        assertEquals("checkstyle", properties.getProperty("test"),
                "Invalid checkstyle property");

        final PropertiesExpander expander = new PropertiesExpander(properties);
        final String expanderUserHome = expander.resolve("user.home");
        assertEquals(System.getProperty("user.home"), expanderUserHome,
                "Invalid user.home property");
        assertEquals("checkstyle", expander.resolve("test"),
                "Invalid checkstyle property");
    }

}
