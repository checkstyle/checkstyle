////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.Attributes;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

/**
 * @author Rick Giles
 * @author lkuehne
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ConfigurationLoader.class, ConfigurationLoaderTest.class })
public class ConfigurationLoaderTest {

    private Configuration loadConfiguration(String name)
        throws CheckstyleException {
        return loadConfiguration(name, new Properties());
    }

    private Configuration loadConfiguration(
        String name, Properties props) throws CheckstyleException {
        final String fName =
            "src/test/resources/com/puppycrawl/tools/checkstyle/configs/" + name;

        return ConfigurationLoader.loadConfiguration(
                fName, new PropertiesExpander(props));
    }

    @Test
    public void testResourceLoadConfiguration() throws Exception {
        final Properties props = new Properties();
        props.put("checkstyle.basedir", "basedir");

        // load config that's only found in the classpath
        final DefaultConfiguration config = (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
            "src/test/resources/com/puppycrawl/tools/checkstyle/configs/checkstyle_checks.xml", new PropertiesExpander(props));

        //verify the root, and property substitution
        final Properties atts = new Properties();
        atts.put("tabWidth", "4");
        atts.put("basedir", "basedir");
        verifyConfigNode(config, "Checker", 3, atts);
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
            assertTrue(
                ex.getMessage().endsWith(
                    "Attribute \"name\" is required and must be specified "
                        + "for element type \"property\".:8:41"));
        }
    }

    @Test
    public void testMissingPropertyValue() {
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
    public void testMissingConfigName() {
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
    public void testMissingConfigParent() {
        try {
            loadConfiguration("missing_config_parent.xml");
            fail("missing module parent");
        }
        catch (CheckstyleException ex) {
            assertTrue(
                ex.getMessage().endsWith(
                    "Document root element \"property\", must match DOCTYPE "
                        + "root \"module\".:8:38"));
        }
    }

    @Test
    public void testCheckstyleChecks() throws Exception {
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
    public void testCustomMessages() throws CheckstyleException {
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
        DefaultConfiguration config, String name, int childrenLength,
        Properties atts) throws Exception {
        assertEquals("name.", name, config.getName());
        assertEquals(
            "children.length.",
            childrenLength,
            config.getChildren().length);

        final String[] attNames = config.getAttributeNames();
        assertEquals("attributes.length", atts.size(), attNames.length);

        for (int i = 0; i < attNames.length; i++) {
            assertEquals(
                "attribute[" + attNames[i] + "]",
                atts.get(attNames[i]),
                config.getAttribute(attNames[i]));
        }
    }

    @Test
    public void testReplacePropertiesNoReplace()
        throws CheckstyleException {
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
    public void testReplacePropertiesSyntaxError() {
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
    public void testReplacePropertiesMissingProperty() {
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
        throws CheckstyleException {
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

    private Properties initProperties() {
        final Properties props = new Properties();
        props.put("a", "A");
        props.put("b", "B");
        return props;
    }

    @Test
    public void testExternalEntity() throws Exception {
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
    public void testExternalEntitySubdir() throws Exception {
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
    public void testExternalEntityFromURI() throws Exception {
        final Properties props = new Properties();
        props.put("checkstyle.basedir", "basedir");

        final File file = new File(
                "src/test/resources/com/puppycrawl/tools/checkstyle/configs/subdir/including.xml");
        final DefaultConfiguration config =
            (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                    file.toURI().toString(), new PropertiesExpander(props));

        final Properties atts = new Properties();
        atts.put("tabWidth", "4");
        atts.put("basedir", "basedir");
        verifyConfigNode(config, "Checker", 2, atts);
    }

    @Test
    public void testIncorrectTag() throws Exception {
        try {
            Class<?> aClassParent = ConfigurationLoader.class;
            Constructor<?> ctorParent = null;
            Constructor<?>[] parentConstructors = aClassParent.getDeclaredConstructors();
            for (Constructor<?> constr: parentConstructors) {
                constr.setAccessible(true);
                ctorParent = constr;
            }
            Object objParent = ctorParent.newInstance(null, true);

            Class<?> aClass = Class.forName("com.puppycrawl.tools.checkstyle."
                    + "ConfigurationLoader$InternalLoader");
            Constructor<?> constructor = null;
            Constructor<?>[] constructors = aClass.getDeclaredConstructors();
            for (Constructor<?> constr: constructors) {
                constr.setAccessible(true);
                constructor = constr;
            }

            Object obj = constructor.newInstance(objParent);

            Class<?>[] param = new Class<?>[4];
            param[0] = String.class;
            param[1] = String.class;
            param[2] = String.class;
            param[3] = Attributes.class;
            Method method = aClass.getDeclaredMethod("startElement", param);

            method.invoke(obj, "", "", "hello", null);

            fail("Exception is expected");

        }
        catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException);
            assertEquals("Unknown name:" + "hello" + ".", e.getCause().getMessage());
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
                        "src/test/resources/com/puppycrawl/tools/checkstyle/configs/"
                                + "config_with_ignore.xml",
                        new PropertiesExpander(new Properties()), true);

        final Configuration[] children = config.getChildren();
        assertTrue(children[0].getChildren().length == 0);
    }

    @Test
    public void testConfigCheckerWithIgnore() throws CheckstyleException {

        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        "src/test/resources/com/puppycrawl/tools/checkstyle/configs/"
                                + "config_with_checker_ignore.xml",
                        new PropertiesExpander(new Properties()), true);

        final Configuration[] children = config.getChildren();
        assertTrue(children.length == 0);
    }

    @Test
    public void testLoadConfiguration_WrongURL() throws CheckstyleException {
        try {
            final DefaultConfiguration config =
                    (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                            ";config_with_ignore.xml",
                            new PropertiesExpander(new Properties()), true);

            final Configuration[] children = config.getChildren();
            assertTrue(children[0].getChildren().length == 0);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("unable to find ;config_with_ignore.xml", ex.getMessage());
        }
    }

    @Test
    public void testLoadConfiguration_URISyntaxException() throws CheckstyleException {
        mockStatic(ConfigurationLoader.class);

        PropertiesExpander expander = new PropertiesExpander(new Properties());

        when(ConfigurationLoader.class.getResource("config_with_ignore.xml"))
                .thenThrow(URISyntaxException.class);
        when(ConfigurationLoader.loadConfiguration("config_with_ignore.xml",
                expander,
                true))
                .thenCallRealMethod();

        try {
            ConfigurationLoader.loadConfiguration(
                    "config_with_ignore.xml", expander, true);

            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getCause() instanceof  URISyntaxException);
            assertEquals("unable to find config_with_ignore.xml", ex.getMessage());
        }
    }

    @Test
    public void testLoadConfiguration_Deprecated() throws CheckstyleException {
        try {
            @SuppressWarnings("deprecation")
            final DefaultConfiguration config =
                    (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                            new FileInputStream(
                                    "src/test/resources/com/puppycrawl/tools/checkstyle/configs/"
                                    + "config_with_ignore.xml"),
                            new PropertiesExpander(new Properties()), true);

            final Configuration[] children = config.getChildren();
            assertTrue(children[0].getChildren().length == 0);
        }
        catch (CheckstyleException ex) {
            fail("unexpected exception");
        }
        catch (FileNotFoundException e) {
            fail("unexpected exception");
        }
    }

    @Test
    public void testReplacePropertiesDefault() throws Exception {
        final Properties props = new Properties();
        String defaultValue = "defaultValue";

        String value = ConfigurationLoader.replaceProperties("${checkstyle.basedir}",
                new PropertiesExpander(props), defaultValue);

        assertEquals(defaultValue, value);
    }

    @Test
    public void testLoadConfigurationFromClassPath() throws CheckstyleException {
        try {
            final DefaultConfiguration config =
                    (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                            "/com/puppycrawl/tools/checkstyle/configs/"
                                    + "config_with_ignore.xml",
                            new PropertiesExpander(new Properties()), true);

            final Configuration[] children = config.getChildren();
            assertTrue(children[0].getChildren().length == 0);
        }
        catch (CheckstyleException ex) {
            fail("unexpected exception");
        }
    }
}
