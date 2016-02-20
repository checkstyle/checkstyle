////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

/**
 * Unit test for ConfigurationLoader.
 * @author Rick Giles
 * @author lkuehne
 */
public class ConfigurationLoaderTest {
    private static String getConfigPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/configs/" + filename;
    }

    private static Configuration loadConfiguration(String name)
        throws CheckstyleException {
        return loadConfiguration(name, new Properties());
    }

    private static Configuration loadConfiguration(
        String name, Properties props) throws CheckstyleException {
        final String fName = getConfigPath(name);

        return ConfigurationLoader.loadConfiguration(
                fName, new PropertiesExpander(props));
    }

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
                getConfigPath("checkstyle_checks.xml"), new PropertiesExpander(props));

        //verify the root, and property substitution
        final Properties attributes = new Properties();
        attributes.setProperty("tabWidth", "4");
        attributes.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 3, attributes);
    }

    @Test
    public void testEmptyConfiguration() throws Exception {
        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration("empty_configuration.xml");
        verifyConfigNode(config, "Checker", 0, new Properties());
    }

    @Test
    public void testMissingPropertyName() {
        try {
            loadConfiguration("missing_property_name.xml");
            fail("missing property name");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().contains("\"name\""));
            assertTrue(ex.getMessage().contains("\"property\""));
            assertTrue(ex.getMessage().endsWith(":8:41"));
        }
    }

    @Test
    public void testMissingPropertyValue() {
        try {
            loadConfiguration("missing_property_value.xml");
            fail("missing property value");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().contains("\"value\""));
            assertTrue(ex.getMessage().contains("\"property\""));
            assertTrue(ex.getMessage().endsWith(":8:41"));
        }
    }

    @Test
    public void testMissingConfigName() {
        try {
            loadConfiguration("missing_config_name.xml");
            fail("missing module name");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().contains("\"name\""));
            assertTrue(ex.getMessage().contains("\"module\""));
            assertTrue(ex.getMessage().endsWith(":7:23"));
        }
    }

    @Test
    public void testMissingConfigParent() {
        try {
            loadConfiguration("missing_config_parent.xml");
            fail("missing module parent");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().contains("\"property\""));
            assertTrue(ex.getMessage().contains("\"module\""));
            assertTrue(ex.getMessage().endsWith(":8:38"));
        }
    }

    @Test
    public void testCheckstyleChecks() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "checkstyle_checks.xml", props);

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
    public void testCustomMessages() throws CheckstyleException {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "custom_messages.xml", props);

        final Configuration[] children = config.getChildren();
        final Configuration[] grandchildren = children[0].getChildren();

        assertTrue(grandchildren[0].getMessages()
            .containsKey("name.invalidPattern"));
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
            assertEquals("Syntax error in property: ${a", ex.getCause().getMessage());
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
            assertEquals("Property ${c} has not been set", ex.getCause().getMessage());
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

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "including.xml", props);

        final Properties atts = new Properties();
        atts.setProperty("tabWidth", "4");
        atts.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 2, atts);
    }

    @Test
    public void testExternalEntitySubdirectory() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "subdir/including.xml", props);

        final Properties attributes = new Properties();
        attributes.setProperty("tabWidth", "4");
        attributes.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 2, attributes);
    }

    @Test
    public void testExternalEntityFromUri() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        final File file = new File(getConfigPath("subdir/including.xml"));
        final DefaultConfiguration config =
            (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                    file.toURI().toString(), new PropertiesExpander(props));

        final Properties atts = new Properties();
        atts.setProperty("tabWidth", "4");
        atts.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 2, atts);
    }

    @Test
    public void testIncorrectTag() throws Exception {
        try {
            final Class<?> aClassParent = ConfigurationLoader.class;
            Constructor<?> ctorParent = null;
            final Constructor<?>[] parentConstructors = aClassParent.getDeclaredConstructors();
            for (Constructor<?> parentConstructor: parentConstructors) {
                parentConstructor.setAccessible(true);
                ctorParent = parentConstructor;
            }
            final Class<?> aClass = Class.forName("com.puppycrawl.tools.checkstyle."
                    + "ConfigurationLoader$InternalLoader");
            Constructor<?> constructor = null;
            final Constructor<?>[] constructors = aClass.getDeclaredConstructors();
            for (Constructor<?> constr: constructors) {
                constr.setAccessible(true);
                constructor = constr;
            }

            final Class<?>[] param = new Class<?>[4];
            param[0] = String.class;
            param[1] = String.class;
            param[2] = String.class;
            param[3] = Attributes.class;
            final Method method = aClass.getDeclaredMethod("startElement", param);
            final Object objParent = ctorParent.newInstance(null, true);
            final Object obj = constructor.newInstance(objParent);

            method.invoke(obj, "", "", "hello", null);

            fail("Exception is expected");

        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IllegalStateException);
            assertEquals("Unknown name:" + "hello" + ".", ex.getCause().getMessage());
        }
    }

    @Test
    public void testNonExistingPropertyName() {
        try {
            loadConfiguration("config_nonexisting_property.xml");
            fail("exception in expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("unable to parse configuration stream", ex.getMessage());
            assertEquals("Property ${nonexisting} has not been set",
                    ex.getCause().getMessage());
        }
    }

    @Test
    public void testConfigWithIgnore() throws CheckstyleException {

        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        getConfigPath("config_with_ignore.xml"),
                        new PropertiesExpander(new Properties()), true);

        final Configuration[] children = config.getChildren();
        assertEquals(0, children[0].getChildren().length);
    }

    @Test
    public void testConfigWithIgnoreUsingInputSource() throws CheckstyleException {

        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(new InputSource(
                        new File(getConfigPath("config_with_ignore.xml")).toURI().toString()),
                        new PropertiesExpander(new Properties()), true);

        final Configuration[] children = config.getChildren();
        assertEquals(0, children[0].getChildren().length);
    }

    @Test
    public void testConfigCheckerWithIgnore() throws CheckstyleException {

        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        getConfigPath("config_with_checker_ignore.xml"),
                        new PropertiesExpander(new Properties()), true);

        final Configuration[] children = config.getChildren();
        assertEquals(0, children.length);
    }

    @Test
    public void testLoadConfigurationWrongUrl() {
        try {
            final DefaultConfiguration config =
                    (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                            ";config_with_ignore.xml",
                            new PropertiesExpander(new Properties()), true);

            final Configuration[] children = config.getChildren();
            assertEquals(0, children[0].getChildren().length);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Unable to find: ;config_with_ignore.xml", ex.getMessage());
        }
    }

    @Test
    public void testLoadConfigurationDeprecated() {
        try {
            @SuppressWarnings("deprecation")
            final DefaultConfiguration config =
                    (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                            new FileInputStream(getConfigPath("config_with_ignore.xml")),
                            new PropertiesExpander(new Properties()), true);

            final Configuration[] children = config.getChildren();
            assertEquals(0, children[0].getChildren().length);
        }
        catch (CheckstyleException | FileNotFoundException ex) {
            fail("unexpected exception");
        }
    }

    @Test
    public void testReplacePropertiesDefault() throws Exception {
        final Properties props = new Properties();
        final String defaultValue = "defaultValue";

        final String value = (String) getReplacePropertiesMethod().invoke(
            null, "${checkstyle.basedir}", new PropertiesExpander(props), defaultValue);

        assertEquals(defaultValue, value);
    }

    @Test
    public void testLoadConfigurationFromClassPath() {
        try {
            final DefaultConfiguration config =
                    (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                            "/com/puppycrawl/tools/checkstyle/configs/"
                                    + "config_with_ignore.xml",
                            new PropertiesExpander(new Properties()), true);

            final Configuration[] children = config.getChildren();
            assertEquals(0, children[0].getChildren().length);
        }
        catch (CheckstyleException ex) {
            fail("unexpected exception");
        }
    }
}
