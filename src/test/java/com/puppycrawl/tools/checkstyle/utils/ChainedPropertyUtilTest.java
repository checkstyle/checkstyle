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

package com.puppycrawl.tools.checkstyle.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class ChainedPropertyUtilTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/chainedpropertyutil";
    }

    @Test
    public void testPropertyChaining() throws Exception {
        final File propertiesFile =
            new File(getPath("InputChainedPropertyUtil.properties"));
        final Properties properties = loadProperties(propertiesFile);
        final Properties resolvedProperties =
            ChainedPropertyUtil.getResolvedProperties(properties);
        final PropertiesExpander expander = new PropertiesExpander(resolvedProperties);
        final String message = "Unexpected property resolution.";

        assertEquals("/home", expander.resolve("basedir"), message);
        assertEquals("/home/checkstyle", expander.resolve("checkstyle.dir"), message);
        assertEquals("/home/checkstyle/configs", expander.resolve("config.dir"), message);
        assertEquals("/home/checkstyle/configs/suppressions.xml",
            expander.resolve("checkstyle.suppressions.file"), message);
        assertEquals("/home/checkstyle", expander.resolve("checkstyle.dir"), message);
        assertEquals("/home/checkstyle", expander.resolve("checkstyle.dir"), message);
    }

    @Test
    public void testPropertyChainingPropertyNotFound() throws Exception {
        final File propertiesFile =
            new File(getPath("InputChainedPropertyUtilUndefinedProperty.properties"));
        final Properties properties = loadProperties(propertiesFile);
        final String expected =
            ChainedPropertyUtil.UNDEFINED_PROPERTY_MESSAGE + "some.other.prop";
        final String message = "Undefined property reference expected.";

        try {
            ChainedPropertyUtil.getResolvedProperties(properties);
            fail(message);
        }
        catch (CheckstyleException exception) {
            assertEquals(expected, exception.getMessage(), message);
        }
    }

    /**
     * Loads properties from a File.
     *
     * @param file the properties file
     * @return the properties in file
     * @throws CheckstyleException when cannot load properties file
     */
    private static Properties loadProperties(File file) throws CheckstyleException {
        final Properties properties = new Properties();

        try (InputStream stream = Files.newInputStream(file.toPath())) {
            properties.load(stream);
        }
        catch (final IOException ex) {
            throw new CheckstyleException(ex.getMessage(), ex);
        }

        return properties;
    }
}
