package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import junit.framework.TestCase;

import java.util.Properties;


/**
 * @author Rick Giles
 * @version 4-Dec-2002
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

        return ConfigurationLoader.loadConfiguration(fName, aProps);
    }

    public void testEmptyConfiguration() throws Exception
    {
        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration("empty_configuration.xml");
        verifyConfigNode(config, "configuration", 0, new Properties());
    }

    public void testMissingPropertyName()
    {
        try {
            loadConfiguration("missing_property_name.xml");
            fail("missing property name");
        }
        catch (CheckstyleException ex) {
            assertTrue(
                "exception error",
                ex.getMessage().indexOf("missing property name") >= 0);
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
                "exception error",
                ex.getMessage().indexOf("missing value for property") >= 0);
        }
    }

    public void testMissingConfigName()
    {
        try {
            loadConfiguration("missing_config_name.xml");
            fail("missing config name");
        }
        catch (CheckstyleException ex) {
            assertTrue(
                "exception error",
                ex.getMessage().indexOf("missing config name") >= 0);
        }
    }

    public void testMissingConfigParent()
    {
        try {
            loadConfiguration("missing_config_parent.xml");
            fail("missing config parent");
        }
        catch (CheckstyleException ex) {
            assertTrue(
                "exception error",
                ex.getMessage().indexOf("has no config parent") >= 0);
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
        verifyConfigNode(config, "configuration", 3, atts);

        //verify children
        final Configuration[] children = config.getChildren();
        atts.clear();
        verifyConfigNode(
            (DefaultConfiguration) children[1], "PackageHtmlCheck", 0, atts);
        verifyConfigNode(
            (DefaultConfiguration) children[2], "TranslationCheck", 0, atts);
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
            "GenericIllegalRegexpCheck",
            0,
            atts);
        atts.clear();
        atts.put("tokens", "DOT");
        atts.put("allowLineBreaks", "true");
        verifyConfigNode(
            (DefaultConfiguration) grandchildren[6],
            "NoWhitespaceAfterCheck",
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
}
