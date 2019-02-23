////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

/**
 * Unit test for ConfigurationLoader.
 */
public class ConfigurationLoaderTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/configurationloader";
    }

    private Configuration loadConfiguration(String name) throws Exception {
        return loadConfiguration(name, new Properties());
    }

    private Configuration loadConfiguration(
        String name, Properties props) throws Exception {
        final String fName = getPath(name);

        return ConfigurationLoader.loadConfiguration(fName, new PropertiesExpander(props));
    }

    /**
     * Non meaningful javadoc just to contain "noinspection" tag.
     * Till https://youtrack.jetbrains.com/issue/IDEA-187209
     * @return method class
     * @throws Exception if smth wrong
     * @noinspection JavaReflectionMemberAccess
     */
    private static Method getReplacePropertiesMethod() throws Exception {
        final Class<?>[] params = new Class<?>[3];
        params[0] = String.class;
        params[1] = PropertyResolver.class;
        params[2] = String.class;
        final Class<ConfigurationLoader> configurationLoaderClass = ConfigurationLoader.class;
        final Method replacePropertiesMethod =
            configurationLoaderClass.getDeclaredMethod("replaceProperties", params);
        replacePropertiesMethod.setAccessible(true);
        return replacePropertiesMethod;
    }

    @Test
    public void testResourceLoadConfiguration() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        // load config that's only found in the classpath
        final DefaultConfiguration config =
            (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                getPath("InputConfigurationLoaderChecks.xml"), new PropertiesExpander(props));

        //verify the root, and property substitution
        final Properties attributes = new Properties();
        attributes.setProperty("tabWidth", "4");
        attributes.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 3, attributes);
    }

    @Test
    public void testResourceLoadConfigurationWithMultiThreadConfiguration() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        final PropertiesExpander propertiesExpander = new PropertiesExpander(props);
        final String configPath = getPath("InputConfigurationLoaderChecks.xml");
        final ThreadModeSettings multiThreadModeSettings =
            new ThreadModeSettings(4, 2);

        try {
            ConfigurationLoader.loadConfiguration(
                configPath, propertiesExpander, multiThreadModeSettings);
            fail("An exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                "Multi thread mode for Checker module is not implemented",
                ex.getMessage());
        }
    }

    @Test
    public void testResourceLoadConfigurationWithSingleThreadConfiguration() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        final PropertiesExpander propertiesExpander = new PropertiesExpander(props);
        final String configPath = getPath("InputConfigurationLoaderChecks.xml");
        final ThreadModeSettings singleThreadModeSettings =
            ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;

        final DefaultConfiguration config =
            (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                configPath, propertiesExpander, singleThreadModeSettings);

        final Properties attributes = new Properties();
        attributes.setProperty("tabWidth", "4");
        attributes.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 3, attributes);
    }

    @Test
    public void testEmptyConfiguration() throws Exception {
        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration("InputConfigurationLoaderEmpty.xml");
        verifyConfigNode(config, "Checker", 0, new Properties());
    }

    @Test
    public void testEmptyModuleResolver() throws Exception {
        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "InputConfigurationLoaderEmpty.xml", new Properties());
        verifyConfigNode(config, "Checker", 0, new Properties());
    }

    @Test
    public void testMissingPropertyName() throws Exception {
        try {
            loadConfiguration("InputConfigurationLoaderMissingPropertyName.xml");
            fail("missing property name");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().contains("\"name\""));
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().contains("\"property\""));
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().endsWith(":8:41"));
        }
    }

    @Test
    public void testMissingPropertyNameInMethodWithBooleanParameter() throws Exception {
        try {
            final String fName = getPath("InputConfigurationLoaderMissingPropertyName.xml");
            ConfigurationLoader.loadConfiguration(fName, new PropertiesExpander(new Properties()),
                    false);

            fail("missing property name");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().contains("\"name\""));
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().contains("\"property\""));
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().endsWith(":8:41"));
        }
    }

    @Test
    public void testMissingPropertyValue() throws Exception {
        try {
            loadConfiguration("InputConfigurationLoaderMissingPropertyValue.xml");
            fail("missing property value");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().contains("\"value\""));
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().contains("\"property\""));
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().endsWith(":8:43"));
        }
    }

    @Test
    public void testMissingConfigName() throws Exception {
        try {
            loadConfiguration("InputConfigurationLoaderMissingConfigName.xml");
            fail("missing module name");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().contains("\"name\""));
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().contains("\"module\""));
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().endsWith(":7:23"));
        }
    }

    @Test
    public void testMissingConfigParent() throws Exception {
        try {
            loadConfiguration("InputConfigurationLoaderMissingConfigParent.xml");
            fail("missing module parent");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().contains("\"property\""));
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().contains("\"module\""));
            assertTrue("Invalid exception message: " + ex.getMessage(),
                    ex.getMessage().endsWith(":8:38"));
        }
    }

    @Test
    public void testCheckstyleChecks() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "InputConfigurationLoaderChecks.xml", props);

        //verify the root, and property substitution
        final Properties atts = new Properties();
        atts.setProperty("tabWidth", "4");
        atts.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 3, atts);

        //verify children
        final Configuration[] children = config.getChildren();
        atts.clear();
        verifyConfigNode(
            (DefaultConfiguration) children[1], "JavadocPackage", 0, atts);
        verifyConfigNode(
            (DefaultConfiguration) children[2], "Translation", 0, atts);
        atts.setProperty("testName", "testValue");
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
        atts.setProperty("format", "System.out.println");
        verifyConfigNode(
            (DefaultConfiguration) grandchildren[grandchildren.length - 1],
            "GenericIllegalRegexp",
            0,
            atts);
        atts.clear();
        atts.setProperty("tokens", "DOT");
        atts.setProperty("allowLineBreaks", "true");
        verifyConfigNode(
            (DefaultConfiguration) grandchildren[6],
            "NoWhitespaceAfter",
            0,
            atts);
    }

    @Test
    public void testCustomMessages() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "InputConfigurationLoaderCustomMessages.xml", props);

        final Configuration[] children = config.getChildren();
        final Configuration[] grandchildren = children[0].getChildren();

        final String expectedKey = "name.invalidPattern";
        assertTrue("Messages should contain key: " + expectedKey,
            grandchildren[0].getMessages()
            .containsKey(expectedKey));
    }

    private static void verifyConfigNode(
        DefaultConfiguration config, String name, int childrenLength,
        Properties atts) throws Exception {
        assertEquals("name.", name, config.getName());
        assertEquals(
            "children.length.",
            childrenLength,
            config.getChildren().length);

        final String[] attNames = config.getAttributeNames();
        assertEquals("attributes.length", atts.size(), attNames.length);

        for (String attName : attNames) {
            assertEquals(
                "attribute[" + attName + "]",
                atts.getProperty(attName),
                config.getAttribute(attName));
        }
    }

    @Test
    public void testReplacePropertiesNoReplace() throws Exception {
        final String[] testValues = {null, "", "a", "$a", "{a",
                                     "{a}", "a}", "$a}", "$", "a$b", };
        final Properties props = initProperties();
        for (String testValue : testValues) {
            final String value = (String) getReplacePropertiesMethod().invoke(
                null, testValue, new PropertiesExpander(props), null);
            assertEquals("\"" + testValue + "\"", value, testValue);
        }
    }

    @Test
    public void testReplacePropertiesSyntaxError() throws Exception {
        final Properties props = initProperties();
        try {
            final String value = (String) getReplacePropertiesMethod().invoke(
                null, "${a", new PropertiesExpander(props), null);
            fail("expected to fail, instead got: " + value);
        }
        catch (InvocationTargetException ex) {
            assertEquals("Invalid exception cause message",
                "Syntax error in property: ${a", ex.getCause().getMessage());
        }
    }

    @Test
    public void testReplacePropertiesMissingProperty() throws Exception {
        final Properties props = initProperties();
        try {
            final String value = (String) getReplacePropertiesMethod().invoke(
                null, "${c}", new PropertiesExpander(props), null);
            fail("expected to fail, instead got: " + value);
        }
        catch (InvocationTargetException ex) {
            assertEquals("Invalid exception cause message",
                "Property ${c} has not been set", ex.getCause().getMessage());
        }
    }

    @Test
    public void testReplacePropertiesReplace() throws Exception {
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
        for (String[] testValue : testValues) {
            final String value = (String) getReplacePropertiesMethod().invoke(
                null, testValue[0], new PropertiesExpander(props), null);
            assertEquals("\"" + testValue[0] + "\"",
                testValue[1], value);
        }
    }

    private static Properties initProperties() {
        final Properties props = new Properties();
        props.setProperty("a", "A");
        props.setProperty("b", "B");
        return props;
    }

    @Test
    public void testExternalEntity() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        System.setProperty(
                XmlLoader.LoadExternalDtdFeatureProvider.ENABLE_EXTERNAL_DTD_LOAD, "true");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "InputConfigurationLoaderExternalEntity.xml", props);

        final Properties atts = new Properties();
        atts.setProperty("tabWidth", "4");
        atts.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 2, atts);
    }

    @Test
    public void testExternalEntitySubdirectory() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        System.setProperty(
                XmlLoader.LoadExternalDtdFeatureProvider.ENABLE_EXTERNAL_DTD_LOAD, "true");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "subdir/InputConfigurationLoaderExternalEntitySubDir.xml", props);

        final Properties attributes = new Properties();
        attributes.setProperty("tabWidth", "4");
        attributes.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 2, attributes);
    }

    @Test
    public void testExternalEntityFromUri() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        System.setProperty(
                XmlLoader.LoadExternalDtdFeatureProvider.ENABLE_EXTERNAL_DTD_LOAD, "true");

        final File file = new File(
                getPath("subdir/InputConfigurationLoaderExternalEntitySubDir.xml"));
        final DefaultConfiguration config =
            (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                    file.toURI().toString(), new PropertiesExpander(props));

        final Properties atts = new Properties();
        atts.setProperty("tabWidth", "4");
        atts.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 2, atts);
    }

    @Test
    public void testNonExistentPropertyName() throws Exception {
        try {
            loadConfiguration("InputConfigurationLoaderNonexistentProperty.xml");
            fail("exception in expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Invalid exception message",
                "unable to parse configuration stream", ex.getMessage());
            assertSame("Expected cause of type SAXException",
                SAXException.class, ex.getCause().getClass());
            assertSame("Expected cause of type CheckstyleException",
                CheckstyleException.class, ex.getCause().getCause().getClass());
            assertEquals("Invalid exception cause message",
                "Property ${nonexistent} has not been set",
                ex.getCause().getCause().getMessage());
        }
    }

    @Test
    public void testConfigWithIgnore() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml"),
                        new PropertiesExpander(new Properties()), true);

        final Configuration[] children = config.getChildren();
        assertEquals("Invalid children count", 0, children[0].getChildren().length);
    }

    @Test
    public void testConfigWithIgnoreUsingInputSource() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(new InputSource(
                        new File(getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml"))
                            .toURI().toString()),
                        new PropertiesExpander(new Properties()), true);

        final Configuration[] children = config.getChildren();
        assertEquals("Invalid children count", 0, children[0].getChildren().length);
    }

    @Test
    public void testConfigCheckerWithIgnore() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        getPath("InputConfigurationLoaderCheckerIgnoreSeverity.xml"),
                        new PropertiesExpander(new Properties()), true);

        final Configuration[] children = config.getChildren();
        assertEquals("Invalid children count", 0, children.length);
    }

    @Test
    public void testLoadConfigurationWrongUrl() {
        try {
            final DefaultConfiguration config =
                    (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                            ";InputConfigurationLoaderModuleIgnoreSeverity.xml",
                            new PropertiesExpander(new Properties()), true);

            final Configuration[] children = config.getChildren();
            assertEquals("Invalid children count", 0, children[0].getChildren().length);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Invalid exception message",
                    "Unable to find: ;InputConfigurationLoaderModuleIgnoreSeverity.xml",
                    ex.getMessage());
        }
    }

    @Test
    public void testLoadConfigurationDeprecated() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        Files.newInputStream(Paths.get(
                            getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml"))),
                        new PropertiesExpander(new Properties()), true);

        final Configuration[] children = config.getChildren();
        assertEquals("Invalid children count",
            0, children[0].getChildren().length);
    }

    @Test
    public void testReplacePropertiesDefault() throws Exception {
        final Properties props = new Properties();
        final String defaultValue = "defaultValue";

        final String value = (String) getReplacePropertiesMethod().invoke(
            null, "${checkstyle.basedir}", new PropertiesExpander(props), defaultValue);

        assertEquals("Invalid property value", defaultValue, value);
    }

    @Test
    public void testLoadConfigurationFromClassPath() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml"),
                        new PropertiesExpander(new Properties()), true);

        final Configuration[] children = config.getChildren();
        assertEquals("Invalid children count",
            0, children[0].getChildren().length);
    }

    @Test
    public void testParsePropertyString() throws Exception {
        final List<String> propertyRefs = new ArrayList<>();
        final List<String> fragments = new ArrayList<>();

        Whitebox.invokeMethod(ConfigurationLoader.class,
                "parsePropertyString", "$",
               fragments, propertyRefs);
        assertEquals("Fragments list has unexpected amount of items",
                1, fragments.size());
    }

    @Test
    public void testConstructors() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");
        final String fName = getPath("InputConfigurationLoaderChecks.xml");

        final Configuration configuration = ConfigurationLoader.loadConfiguration(fName,
                new PropertiesExpander(props), ConfigurationLoader.IgnoredModulesOptions.OMIT);
        assertEquals("Name is not expected", "Checker", configuration.getName());

        final DefaultConfiguration configuration1 =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        new InputSource(Files.newInputStream(Paths.get(
                            getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml")))),
                        new PropertiesExpander(new Properties()),
                        ConfigurationLoader.IgnoredModulesOptions.EXECUTE);

        final Configuration[] children = configuration1.getChildren();
        assertEquals("Unexpected children size", 1, children[0].getChildren().length);
    }

}
