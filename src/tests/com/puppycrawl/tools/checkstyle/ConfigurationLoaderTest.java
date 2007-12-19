package com.puppycrawl.tools.checkstyle;

import java.util.Properties;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import junit.framework.TestCase;


/**
 * @author Rick Giles
 * @author lkuehne
 * @version $Revision$
 */
public class ConfigurationLoaderTest extends TestCase
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

    public void testEmptyConfiguration() throws Exception
    {
        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration("empty_configuration.xml");
        verifyConfigNode(config, "Checker", 0, new Properties());
    }

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

    public void testReplacePropertiesNoReplace()
        throws CheckstyleException
    {
        final String[] testValues = {null, "", "a", "$a", "{a",
                                       "{a}", "a}", "$a}", "$", "a$b"};
        final Properties props = initProperties();
        for (int i = 0; i < testValues.length; i++) {
            final String value = ConfigurationLoader.replaceProperties(
                testValues[i], new PropertiesExpander(props), null);
            assertEquals("\"" + testValues[i] + "\"", value, testValues[i]);
        }
    }

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

}
