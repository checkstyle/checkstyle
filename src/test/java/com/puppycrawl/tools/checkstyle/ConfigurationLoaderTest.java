///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader.IgnoredModulesOptions;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

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

        // verify the root, and property substitution
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
            assertWithMessage("An exception is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Multi thread mode for Checker module is not implemented");
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
            assertWithMessage("missing property name").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .contains("\"name\"");
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .contains("\"property\"");
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .endsWith(":8:41");
        }
    }

    @Test
    public void testMissingPropertyNameInMethodWithBooleanParameter() throws Exception {
        try {
            final String fName = getPath("InputConfigurationLoaderMissingPropertyName.xml");
            ConfigurationLoader.loadConfiguration(fName, new PropertiesExpander(new Properties()),
                    IgnoredModulesOptions.EXECUTE);

            assertWithMessage("missing property name").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .contains("\"name\"");
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .contains("\"property\"");
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .endsWith(":8:41");
        }
    }

    @Test
    public void testMissingPropertyValue() throws Exception {
        try {
            loadConfiguration("InputConfigurationLoaderMissingPropertyValue.xml");
            assertWithMessage("missing property value").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .contains("\"value\"");
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .contains("\"property\"");
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .endsWith(":8:43");
        }
    }

    @Test
    public void testMissingConfigName() throws Exception {
        try {
            loadConfiguration("InputConfigurationLoaderMissingConfigName.xml");
            assertWithMessage("missing module name").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .contains("\"name\"");
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .contains("\"module\"");
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .endsWith(":7:23");
        }
    }

    @Test
    public void testMissingConfigParent() throws Exception {
        try {
            loadConfiguration("InputConfigurationLoaderMissingConfigParent.xml");
            assertWithMessage("missing module parent").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .contains("\"property\"");
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .contains("\"module\"");
            assertWithMessage("Invalid exception message: " + ex.getMessage())
                    .that(ex.getMessage())
                    .endsWith(":8:38");
        }
    }

    @Test
    public void testCheckstyleChecks() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "InputConfigurationLoaderChecks.xml", props);

        // verify the root, and property substitution
        final Properties atts = new Properties();
        atts.setProperty("tabWidth", "4");
        atts.setProperty("basedir", "basedir");
        verifyConfigNode(config, "Checker", 3, atts);

        // verify children
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

        // verify TreeWalker's first, last, NoWhitespaceAfterCheck
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
        final List<String> messages = new ArrayList<>(grandchildren[0].getMessages().values());
        final String expectedKey = "name.invalidPattern";
        final List<String> expectedMessages = Collections
                .singletonList("Member ''{0}'' must start with ''m'' (checked pattern ''{1}'').");
        assertWithMessage("Messages should contain key: " + expectedKey)
                .that(grandchildren[0].getMessages())
                .containsKey(expectedKey);
        assertWithMessage("Message is not expected")
                .that(messages)
                .isEqualTo(expectedMessages);
    }

    private static void verifyConfigNode(
        DefaultConfiguration config, String name, int childrenLength,
        Properties atts) throws Exception {
        assertWithMessage("name.")
            .that(config.getName())
            .isEqualTo(name);
        assertWithMessage("children.length.")
            .that(config.getChildren().length)
            .isEqualTo(childrenLength);

        final String[] attNames = config.getPropertyNames();
        assertWithMessage("attributes.length")
            .that(attNames.length)
            .isEqualTo(atts.size());

        for (String attName : attNames) {
            final String attribute = config.getProperty(attName);
            assertWithMessage("attribute[" + attName + "]")
                .that(attribute)
                .isEqualTo(atts.getProperty(attName));
        }
    }

    @Test
    public void testReplacePropertiesNoReplace() throws Exception {
        final String[] testValues = {"", "a", "$a", "{a",
                                     "{a}", "a}", "$a}", "$", "a$b", };
        final Properties props = initProperties();
        for (String testValue : testValues) {
            final String value = (String) getReplacePropertiesMethod().invoke(
                null, testValue, new PropertiesExpander(props), null);
            assertWithMessage("\"" + testValue + "\"")
                .that(testValue)
                .isEqualTo(value);
        }
    }

    @Test
    public void testReplacePropertiesSyntaxError() throws Exception {
        final Properties props = initProperties();
        try {
            final String value = (String) getReplacePropertiesMethod().invoke(
                null, "${a", new PropertiesExpander(props), null);
            assertWithMessage("expected to fail, instead got: " + value).fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Invalid exception cause message")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("Syntax error in property: ${a");
        }
    }

    @Test
    public void testReplacePropertiesMissingProperty() throws Exception {
        final Properties props = initProperties();
        try {
            final String value = (String) getReplacePropertiesMethod().invoke(
                null, "${c}", new PropertiesExpander(props), null);
            assertWithMessage("expected to fail, instead got: " + value).fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Invalid exception cause message")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("Property ${c} has not been set");
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
            assertWithMessage("\"" + testValue[0] + "\"")
                .that(value)
                .isEqualTo(testValue[1]);
        }
    }

    private static Properties initProperties() {
        final Properties props = new Properties();
        props.setProperty("a", "A");
        props.setProperty("b", "B");
        return props;
    }

    @Test
    public void testSystemEntity() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");

        final DefaultConfiguration config =
            (DefaultConfiguration) loadConfiguration(
                "InputConfigurationLoaderSystemDoctype.xml", props);

        final Properties atts = new Properties();
        atts.setProperty("tabWidth", "4");

        verifyConfigNode(config, "Checker", 0, atts);
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
    public void testIncorrectTag() throws Exception {
        final Class<?> aClassParent = ConfigurationLoader.class;
        final Constructor<?> ctorParent = aClassParent.getDeclaredConstructor(
                PropertyResolver.class, boolean.class, ThreadModeSettings.class);
        ctorParent.setAccessible(true);
        final Object objParent = ctorParent.newInstance(null, true, null);

        final Class<?> aClass = Class.forName("com.puppycrawl.tools.checkstyle."
                + "ConfigurationLoader$InternalLoader");
        final Constructor<?> constructor = aClass.getDeclaredConstructor(objParent.getClass());
        constructor.setAccessible(true);

        final Object obj = constructor.newInstance(objParent);

        try {
            TestUtil.invokeMethod(obj, "startElement", "", "", "hello", null);

            assertWithMessage("InvocationTargetException is expected").fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Invalid exception cause message")
                .that(ex)
                    .hasCauseThat()
                        .hasMessageThat()
                        .isEqualTo("Unknown name:" + "hello" + ".");
        }
    }

    @Test
    public void testNonExistentPropertyName() throws Exception {
        try {
            loadConfiguration("InputConfigurationLoaderNonexistentProperty.xml");
            assertWithMessage("exception in expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("unable to parse configuration stream");
            assertWithMessage("Expected cause of type SAXException")
                .that(ex.getCause())
                .isInstanceOf(SAXException.class);
            assertWithMessage("Expected cause of type CheckstyleException")
                .that(ex.getCause().getCause())
                .isInstanceOf(CheckstyleException.class);
            assertWithMessage("Invalid exception cause message")
                .that(ex)
                .hasCauseThat()
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("Property ${nonexistent} has not been set");
        }
    }

    @Test
    public void testConfigWithIgnore() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml"),
                        new PropertiesExpander(new Properties()), IgnoredModulesOptions.OMIT);

        final Configuration[] children = config.getChildren();
        final int length = children[0].getChildren().length;
        assertWithMessage("Invalid children count")
            .that(length)
            .isEqualTo(0);
    }

    @Test
    public void testConfigWithIgnoreUsingInputSource() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(new InputSource(
                        new File(getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml"))
                            .toURI().toString()),
                        new PropertiesExpander(new Properties()), IgnoredModulesOptions.OMIT);

        final Configuration[] children = config.getChildren();
        final int length = children[0].getChildren().length;
        assertWithMessage("Invalid children count")
            .that(length)
            .isEqualTo(0);
    }

    @Test
    public void testConfigCheckerWithIgnore() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        getPath("InputConfigurationLoaderCheckerIgnoreSeverity.xml"),
                        new PropertiesExpander(new Properties()), IgnoredModulesOptions.OMIT);

        final Configuration[] children = config.getChildren();
        assertWithMessage("Invalid children count")
            .that(children.length)
            .isEqualTo(0);
    }

    @Test
    public void testLoadConfigurationWrongUrl() {
        try {
            ConfigurationLoader.loadConfiguration(
                    ";InputConfigurationLoaderModuleIgnoreSeverity.xml",
                    new PropertiesExpander(new Properties()), IgnoredModulesOptions.OMIT);

            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Unable to find: ;InputConfigurationLoaderModuleIgnoreSeverity.xml");
        }
    }

    @Test
    public void testLoadConfigurationDeprecated() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        new InputSource(Files.newInputStream(Path.of(
                            getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml")))),
                        new PropertiesExpander(new Properties()), IgnoredModulesOptions.OMIT);

        final Configuration[] children = config.getChildren();
        final int length = children[0].getChildren().length;
        assertWithMessage("Invalid children count")
            .that(length)
            .isEqualTo(0);
    }

    @Test
    public void testReplacePropertiesDefault() throws Exception {
        final Properties props = new Properties();
        final String defaultValue = "defaultValue";

        final String value = (String) getReplacePropertiesMethod().invoke(
            null, "${checkstyle.basedir}", new PropertiesExpander(props), defaultValue);

        assertWithMessage("Invalid property value")
            .that(value)
            .isEqualTo(defaultValue);
    }

    @Test
    public void testLoadConfigurationFromClassPath() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml"),
                        new PropertiesExpander(new Properties()), IgnoredModulesOptions.OMIT);

        final Configuration[] children = config.getChildren();
        final int length = children[0].getChildren().length;
        assertWithMessage("Invalid children count")
            .that(length)
            .isEqualTo(0);
    }

    @Test
    public void testLoadConfigurationFromClassPathWithNonAsciiSymbolsInPath() throws Exception {
        final DefaultConfiguration config =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                    getResourcePath("æ£µ¥/InputConfigurationLoaderDefaultProperty.xml"),
                        new PropertiesExpander(new Properties()));

        final Properties expectedPropertyValues = new Properties();
        expectedPropertyValues.setProperty("tabWidth", "2");
        expectedPropertyValues.setProperty("basedir", ".");
        // charset property uses 2 variables, one is not defined, so default becomes a result value
        expectedPropertyValues.setProperty("charset", "ASCII");
        verifyConfigNode(config, "Checker", 0, expectedPropertyValues);
    }

    @Test
    public void testParsePropertyString() throws Exception {
        final List<String> propertyRefs = new ArrayList<>();
        final List<String> fragments = new ArrayList<>();

        TestUtil.invokeStaticMethod(ConfigurationLoader.class,
                "parsePropertyString", "$",
               fragments, propertyRefs);
        assertWithMessage("Fragments list has unexpected amount of items")
            .that(fragments)
            .hasSize(1);
    }

    @Test
    public void testConstructors() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.basedir", "basedir");
        final String fName = getPath("InputConfigurationLoaderChecks.xml");

        final Configuration configuration = ConfigurationLoader.loadConfiguration(fName,
                new PropertiesExpander(props), ConfigurationLoader.IgnoredModulesOptions.OMIT);
        assertWithMessage("Name is not expected")
            .that(configuration.getName())
            .isEqualTo("Checker");

        final DefaultConfiguration configuration1 =
                (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                        new InputSource(Files.newInputStream(Path.of(
                            getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml")))),
                        new PropertiesExpander(new Properties()),
                        ConfigurationLoader.IgnoredModulesOptions.EXECUTE);

        final Configuration[] children = configuration1.getChildren();
        final int length = children[0].getChildren().length;
        assertWithMessage("Unexpected children size")
            .that(length)
            .isEqualTo(1);
    }

    @Test
    public void testConfigWithIgnoreExceptionalAttributes() {
        try (MockedConstruction<DefaultConfiguration> mocked = mockConstruction(
                DefaultConfiguration.class, (mock, context) -> {
                    when(mock.getPropertyNames()).thenReturn(new String[] {"severity"});
                    when(mock.getName()).thenReturn("MemberName");
                    when(mock.getProperty("severity")).thenThrow(CheckstyleException.class);
                })) {
            final CheckstyleException ex = assertThrows(CheckstyleException.class, () -> {
                ConfigurationLoader.loadConfiguration(
                        getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml"),
                        new PropertiesExpander(new Properties()), IgnoredModulesOptions.OMIT);
            });
            final String expectedMessage =
                "Problem during accessing 'severity' attribute for MemberName";
            assertWithMessage("Invalid exception cause message")
                .that(ex)
                .hasCauseThat()
                    .hasMessageThat()
                    .isEqualTo(expectedMessage);
        }
    }

    @Test
    public void testLoadConfiguration3() throws Exception {
        final String[] configFiles = {
            "InputConfigurationLoaderOldConfig0.xml",
            "InputConfigurationLoaderOldConfig1.xml",
            "InputConfigurationLoaderOldConfig2.xml",
            "InputConfigurationLoaderOldConfig3.xml",
            "InputConfigurationLoaderOldConfig4.xml",
            "InputConfigurationLoaderOldConfig5.xml",
            "InputConfigurationLoaderOldConfig6.xml",
            "InputConfigurationLoaderOldConfig7.xml",
        };

        for (String configFile : configFiles) {
            final DefaultConfiguration config =
                    (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                            new InputSource(Files.newInputStream(Path.of(
                                    getPath(configFile)))),
                            new PropertiesExpander(new Properties()),
                            IgnoredModulesOptions.OMIT);

            assertWithMessage("should have properties")
                    .that(config.getPropertyNames()).asList()
                    .contains("severity");

            assertWithMessage("should have properties")
                    .that(config.getPropertyNames()).asList()
                    .contains("fileExtensions");

            assertWithMessage("")
                    .that(config.getAttribute("severity"))
                    .isEqualTo("error");

            assertWithMessage("")
                    .that(config.getAttribute("fileExtensions"))
                    .isEqualTo("java, properties, xml");

            assertWithMessage("")
                    .that(config.getChildren().length)
                    .isEqualTo(1);

            final Configuration[] children = config.getChildren();
            final Configuration[] grandchildren = children[0].getChildren();

            assertWithMessage("")
                    .that(children[0].getPropertyNames()).asList()
                    .contains("severity");

            assertWithMessage("")
                    .that(grandchildren[0].getPropertyNames()).asList()
                    .contains("query");
        }
    }

    @Test
    public void testDefaultValuesForNonDefinedProperties() throws Exception {
        final Properties props = new Properties();
        props.setProperty("checkstyle.charset.base", "UTF");

        final File file = new File(
                getPath("InputConfigurationLoaderDefaultProperty.xml"));
        final DefaultConfiguration config =
            (DefaultConfiguration) ConfigurationLoader.loadConfiguration(
                    file.toURI().toString(), new PropertiesExpander(props));

        final Properties expectedPropertyValues = new Properties();
        expectedPropertyValues.setProperty("tabWidth", "2");
        expectedPropertyValues.setProperty("basedir", ".");
        // charset property uses 2 variables, one is not defined, so default becomes a result value
        expectedPropertyValues.setProperty("charset", "ASCII");
        verifyConfigNode(config, "Checker", 0, expectedPropertyValues);
    }

}
