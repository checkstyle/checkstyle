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

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class PropertiesExpanderTest {

    @Test
    public void testCtorException() {
        try {
            final Object test = new PropertiesExpander(null);
            Assert.fail("exception expected but got " + test);
        }
        catch (IllegalArgumentException ex) {
            Assert.assertEquals("Invalid exception message", "cannot pass null", ex.getMessage());
        }
    }

    @Test
    public void testDefaultProperties() {
        final Properties properties = new Properties(System.getProperties());
        properties.setProperty("test", "checkstyle");
        Assert.assertEquals("Invalid user.home property",
                System.getProperty("user.home"), properties.getProperty("user.home"));
        Assert.assertEquals("Invalid checkstyle property",
                "checkstyle", properties.getProperty("test"));

        final PropertiesExpander expander = new PropertiesExpander(properties);
        Assert.assertEquals("Invalid user.home property",
                System.getProperty("user.home"), expander.resolve("user.home"));
        Assert.assertEquals("Invalid checkstyle property",
                "checkstyle", expander.resolve("test"));
    }

}
