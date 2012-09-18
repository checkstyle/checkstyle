////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Rick Giles
 * @author lkuehne
 */
public class ConfigurationLoaderTest
{
    private Configuration loadConfiguration(String aName)
        throws CheckstyleException
    {
        return loadConfiguration(aName, new Properties());
    }

    private Configuration loadConfiguration(
        String aName, Properties aProps) throws CheckstyleException
    {
        final String fName =
            System.getProperty("testinputs.dir") + "/configs/" + aName;

        return ConfigurationLoader.loadConfiguration(
            fName, new PropertiesExpander(aProps));
    }


    @Test
    public void testResourceLoadConfiguration() throws Exception
    {
        // load config that's only found in the classpath
        final DefaultConfiguration config = (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
            "/checkstyle/checkstyle_checks.xml", new PropertiesExpander(new Properties()));
        verifyConfigNode(config, "Checker", 3, new Properties());
    }

    @Test
    public void testEmptyConfiguration() throws Exception
    {
        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration("empty_configuration.xml");
        verifyConfigNode(config, "Checker", 0, new Properties());
    }

    @Test
    public void testMissingPropertyName()
    {
        try {
            loadConfiguration("missing_property_name.xml");
            fail("missing property name");
        }
        catch (CheckstyleException ex) {
            assertTrue(
                ex.getMessage().endsWith(
                    "Attribute \"name\" is required and must be specified "
                        + "for element type \"property\".:8:41"));
        }
    }

    @Test
    public void testMissingPropertyValue()
    {
        try {
            loadConfiguration("missing_property_value.xml");
            fail("missing property value");
        }
        catch (CheckstyleException ex) {
            assertTrue(
                ex.getMessage().endsWith(
                    "Attribute \"value\" is required and must be specified "
                        + "for element type \"property\".:8:41"));
        }
    }

    @Test
    public void testMissingConfigName()
    {
        try {
            loadConfiguration("missing_config_name.xml");
            fail("missing module name");
        }
        catch (CheckstyleException ex) {
            assertTrue(
                ex.getMessage().endsWith(
                    "Attribute \"name\" is required and must be specified "
                        + "for element type \"module\".:7:23"));
        }
    }

    @Test
    public void testMissingConfigParent()
    {
        try {
            loadConfiguration("missing_config_parent.xml");
            fail("missing module parent");
        }
        catch (CheckstyleException ex) {
            assertTrue(
                ex.getMessage().endsWith(
                    "Document root element \"property\", must match DOCTYPE "
                        + "root \"module\".:7:38"));
        }
    }

    @Test
    public void testCheckstyleChecks() throws Exception
    {
        final Properties props = new Properties();
        props.put("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "checkstyle_checks.xml", props);

        //verify the root, and property substitution
        final Properties atts = new Properties();
        atts.put("tabWidth", "4");
        atts.put("basedir", "basedir");
        verifyConfigNode(config, "Checker", 3, atts);

        //verify children
        final Configuration[] children = config.getChildren();
        atts.clear();
        verifyConfigNode(
            (DefaultConfiguration) children[1], "JavadocPackage", 0, atts);
        verifyConfigNode(
            (DefaultConfiguration) children[2], "Translation", 0, atts);
        atts.put("testName", "testValue");
        verifyConfigNode(
            (DefaultConfiguration) children[0],
            "TreeWalker",
            8,
            atts);

        //verify TreeWalker's first, last, NoWhitespaceAfterCheck
        final Configuration[] grandchildren = children[0].getChildren();
        atts.clear();
        verifyConfigNode(
            (DefaultConfiguration) grandchildren[0],
            "AvoidStarImport",
            0,
            atts);
        atts.put("format", "System.out.println");
        verifyConfigNode(
            (DefaultConfiguration) grandchildren[grandchildren.length - 1],
            "GenericIllegalRegexp",
            0,
            atts);
        atts.clear();
        atts.put("tokens", "DOT");
        atts.put("allowLineBreaks", "true");
        verifyConfigNode(
            (DefaultConfiguration) grandchildren[6],
            "NoWhitespaceAfter",
            0,
            atts);
    }

    @Test
    public void testCustomMessages() throws CheckstyleException
    {
        final Properties props = new Properties();
        props.put("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "custom_messages.xml", props);

        final Configuration[] children = config.getChildren();
        final Configuration[] grandchildren = children[0].getChildren();

        assertTrue(((DefaultConfiguration) grandchildren[0]).getMessages()
            .containsKey("name.invalidPattern"));
    }


    private void verifyConfigNode(
        DefaultConfiguration aConfig, String aName, int aChildrenLength,
        Properties atts) throws Exception
    {
        assertEquals("name.", aName, aConfig.getName());
        assertEquals(
            "children.length.",
            aChildrenLength,
            aConfig.getChildren().length);

        final String[] attNames = aConfig.getAttributeNames();
        assertEquals("attributes.length", atts.size(), attNames.length);

        for (int i = 0; i < attNames.length; i++) {
            assertEquals(
                "attribute[" + attNames[i] + "]",
                atts.get(attNames[i]),
                aConfig.getAttribute(attNames[i]));
        }
    }

    @Test
    public void testReplacePropertiesNoReplace()
        throws CheckstyleException
    {
        final String[] testValues = {null, "", "a", "$a", "{a",
                                     "{a}", "a}", "$a}", "$", "a$b", };
        final Properties props = initProperties();
        for (int i = 0; i < testValues.length; i++) {
            final String value = ConfigurationLoader.replaceProperties(
                testValues[i], new PropertiesExpander(props), null);
            assertEquals("\"" + testValues[i] + "\"", value, testValues[i]);
        }
    }

    @Test
    public void testReplacePropertiesSyntaxError()
    {
        final Properties props = initProperties();
        try {
            final String value = ConfigurationLoader.replaceProperties(
                "${a", new PropertiesExpander(props), null);
            fail("expected to fail, instead got: " + value);
        }
        catch (CheckstyleException ex) {
            assertEquals("Syntax error in property: ${a", ex.getMessage());
        }
    }

    @Test
    public void testReplacePropertiesMissingProperty()
    {
        final Properties props = initProperties();
        try {
            final String value = ConfigurationLoader.replaceProperties(
                "${c}", new PropertiesExpander(props), null);
            fail("expected to fail, instead got: " + value);
        }
        catch (CheckstyleException ex) {
            assertEquals("Property ${c} has not been set", ex.getMessage());
        }
    }

    @Test
    public void testReplacePropertiesReplace()
        throws CheckstyleException
    {
        final String[][] testValues = {
            {"${a}", "A"},
            {"x${a}", "xA"},
            {"${a}x", "Ax"},
            {"${a}${b}", "AB"},
            {"x${a}${b}", "xAB"},
            {"${a}x${b}", "AxB"},
            {"${a}${b}x", "ABx"},
            {"x${a}y${b}", "xAyB"},
            {"${a}x${b}y", "AxBy"},
            {"x${a}${b}y", "xABy"},
            {"x${a}y${b}z", "xAyBz"},
            {"$$", "$"},
        };
        final Properties props = initProperties();
        for (int i = 0; i < testValues.length; i++) {
            final String value = ConfigurationLoader.replaceProperties(
                testValues[i][0], new PropertiesExpander(props), null);
            assertEquals("\"" + testValues[i][0] + "\"",
                testValues[i][1], value);
        }
    }

    private Properties initProperties()
    {
        final Properties props = new Properties();
        props.put("a", "A");
        props.put("b", "B");
        return props;
    }

    @Test
    public void testExternalEntity() throws Exception
    {
        final Properties props = new Properties();
        props.put("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "including.xml", props);

        final Properties atts = new Properties();
        atts.put("tabWidth", "4");
        atts.put("basedir", "basedir");
        verifyConfigNode(config, "Checker", 2, atts);
    }

    @Test
    public void testExternalEntitySubdir() throws Exception
    {
        final Properties props = new Properties();
        props.put("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "subdir/including.xml", props);

        final Properties atts = new Properties();
        atts.put("tabWidth", "4");
        atts.put("basedir", "basedir");
        verifyConfigNode(config, "Checker", 2, atts);
    }

    @Test
    public void testExternalEntityFromURI() throws Exception
    {
        final Properties props = new Properties();
        props.put("checkstyle.basedir", "basedir");

        final File file = new File(System.getProperty("testinputs.dir")
                                   + "/configs/subdir/including.xml");
        final DefaultConfiguration config =
            (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                file.toURI().toString(), new PropertiesExpander(props));

        final Properties atts = new Properties();
        atts.put("tabWidth", "4");
        atts.put("basedir", "basedir");
        verifyConfigNode(config, "Checker", 2, atts);
    }

}
