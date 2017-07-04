////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifier;

public class XdocsPagesTest {
    private static final Path AVAILABLE_CHECKS_PATH = Paths.get("src/xdocs/checks.xml");
    private static final String LINK_TEMPLATE =
            "(?s).*<a href=\"config_\\w+\\.html#%1$s\">%1$s</a>.*";

    private static final Pattern VERSION = Pattern.compile("\\d+\\.\\d+(\\.\\d+)?");

    private static final Pattern DESCRIPTION_VERSION = Pattern
            .compile("^Since Checkstyle \\d+\\.\\d+(\\.\\d+)?");

    private static final List<String> XML_FILESET_LIST = Arrays.asList(
            "TreeWalker",
            "name=\"Checker\"",
            "name=\"Header\"",
            "name=\"Translation\"",
            "name=\"SeverityMatchFilter\"",
            "name=\"SuppressionFilter\"",
            "name=\"SuppressionCommentFilter\"",
            "name=\"SuppressWithNearbyCommentFilter\"",
            "name=\"SuppressWarningsFilter\"",
            "name=\"BeforeExecutionExclusionFileFilter\"",
            "name=\"RegexpHeader\"",
            "name=\"RegexpOnFilename\"",
            "name=\"RegexpSingleline\"",
            "name=\"RegexpMultiline\"",
            "name=\"JavadocPackage\"",
            "name=\"NewlineAtEndOfFile\"",
            "name=\"UniqueProperties\"",
            "name=\"FileLength\"",
            "name=\"FileTabCharacter\""
    );

    private static final Set<String> CHECK_PROPERTIES = getProperties(AbstractCheck.class);
    private static final Set<String> JAVADOC_CHECK_PROPERTIES =
            getProperties(AbstractJavadocCheck.class);
    private static final Set<String> FILESET_PROPERTIES = getProperties(AbstractFileSetCheck.class);

    private static final List<String> UNDOCUMENTED_PROPERTIES = Arrays.asList(
            "Checker.classLoader",
            "Checker.classloader",
            "Checker.moduleClassLoader",
            "Checker.moduleFactory",
            "TreeWalker.classLoader",
            "TreeWalker.moduleFactory",
            "TreeWalker.cacheFile",
            "TreeWalker.upChild",
            "SuppressWithNearbyCommentFilter.fileContents",
            "SuppressionCommentFilter.fileContents"
    );

    private static final Set<String> SUN_MODULES = Collections.unmodifiableSet(
        new HashSet<>(CheckUtil.getConfigSunStyleModules()));
    private static final Set<String> GOOGLE_MODULES = Collections.unmodifiableSet(
        new HashSet<>(CheckUtil.getConfigGoogleStyleModules()));

    @Test
    public void testAllChecksPresentOnAvailableChecksPage() throws Exception {
        final String availableChecks = new String(Files.readAllBytes(AVAILABLE_CHECKS_PATH), UTF_8);

        CheckUtil.getSimpleNames(CheckUtil.getCheckstyleChecks())
            .forEach(checkName -> {
                if (!isPresent(availableChecks, checkName)) {
                    Assert.fail(checkName + " is not correctly listed on Available Checks page"
                        + " - add it to " + AVAILABLE_CHECKS_PATH);
                }
            });
    }

    private static boolean isPresent(String availableChecks, String checkName) {
        final String linkPattern = String.format(Locale.ROOT, LINK_TEMPLATE, checkName);
        return availableChecks.matches(linkPattern);
    }

    @Test
    public void testAllXmlExamples() throws Exception {
        for (Path path : XdocUtil.getXdocsFilePaths()) {
            final String input = new String(Files.readAllBytes(path), UTF_8);
            final String fileName = path.getFileName().toString();

            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("source");

            for (int position = 0; position < sources.getLength(); position++) {
                final String unserializedSource = sources.item(position).getTextContent()
                        .replace("...", "").trim();

                if (unserializedSource.charAt(0) != '<'
                        || unserializedSource.charAt(unserializedSource.length() - 1) != '>'
                        // no dtd testing yet
                        || unserializedSource.contains("<!")) {
                    continue;
                }

                buildAndValidateXml(fileName, unserializedSource);
            }
        }
    }

    private static void buildAndValidateXml(String fileName, String unserializedSource)
            throws IOException, ParserConfigurationException, CheckstyleException {
        // not all examples come with the full xml structure
        String code = unserializedSource
            // don't corrupt our own cachefile
            .replace("target/cachefile", "target/cachefile-test");

        if (!hasFileSetClass(code)) {
            code = "<module name=\"TreeWalker\">\n" + code + "\n</module>";
        }
        if (!code.contains("name=\"Checker\"")) {
            code = "<module name=\"Checker\">\n" + code + "\n</module>";
        }
        if (!code.startsWith("<?xml")) {
            final String dtdPath = new File(
                    "src/main/resources/com/puppycrawl/tools/checkstyle/configuration_1_3.dtd")
                    .getCanonicalPath();

            code = "<?xml version=\"1.0\"?>\n<!DOCTYPE module PUBLIC "
                    + "\"-//Puppy Crawl//DTD Check Configuration 1.3//EN\" \"" + dtdPath + "\">\n"
                    + code;
        }

        // validate only
        XmlUtil.getRawXml(fileName, code, unserializedSource);

        // can't test ant structure, or old and outdated checks
        if (!fileName.startsWith("anttask") && !fileName.startsWith("releasenotes")) {
            validateCheckstyleXml(fileName, code, unserializedSource);
        }
    }

    private static boolean hasFileSetClass(String xml) {
        boolean found = false;

        for (String find : XML_FILESET_LIST) {
            if (xml.contains(find)) {
                found = true;
                break;
            }
        }

        return found;
    }

    private static void validateCheckstyleXml(String fileName, String code,
            String unserializedSource) throws IOException, CheckstyleException {
        // can't process non-existent examples, or out of context snippets
        if (!code.contains("com.mycompany") && !code.contains("checkstyle-packages")
                && !code.contains("MethodLimit") && !code.contains("<suppress ")
                && !code.contains("<import-control ")
                && !unserializedSource.startsWith("<property ")
                && !unserializedSource.startsWith("<taskdef ")) {
            // validate checkstyle structure and contents
            try {
                final Properties properties = new Properties();

                properties.setProperty("checkstyle.header.file",
                        new File("config/java.header").getCanonicalPath());

                final PropertiesExpander expander = new PropertiesExpander(properties);
                final Configuration config = ConfigurationLoader.loadConfiguration(new InputSource(
                        new StringReader(code)), expander, false);
                final Checker checker = new Checker();

                try {
                    final ClassLoader moduleClassLoader = Checker.class.getClassLoader();
                    checker.setModuleClassLoader(moduleClassLoader);
                    checker.configure(config);
                }
                finally {
                    checker.destroy();
                }
            }
            catch (CheckstyleException ex) {
                throw new CheckstyleException(fileName + " has invalid Checkstyle xml ("
                        + ex.getMessage() + "): " + unserializedSource, ex);
            }
        }
    }

    @Test
    public void testAllCheckSections() throws Exception {
        final ModuleFactory moduleFactory = TestUtils.getPackageObjectFactory();

        for (Path path : XdocUtil.getXdocsConfigFilePaths(XdocUtil.getXdocsFilePaths())) {
            final String fileName = path.getFileName().toString();

            if ("config_reporting.xml".equals(fileName)) {
                continue;
            }

            final String input = new String(Files.readAllBytes(path), UTF_8);
            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("section");
            String lastSectionName = null;

            for (int position = 0; position < sources.getLength(); position++) {
                final Node section = sources.item(position);
                final String sectionName = section.getAttributes().getNamedItem("name")
                        .getNodeValue();

                if ("Content".equals(sectionName) || "Overview".equals(sectionName)) {
                    Assert.assertNull(fileName + " section '" + sectionName + "' should be first",
                            lastSectionName);
                    continue;
                }

                Assert.assertTrue(fileName + " section '" + sectionName
                        + "' shouldn't end with 'Check'", !sectionName.endsWith("Check"));
                if (lastSectionName != null) {
                    Assert.assertTrue(
                            fileName + " section '" + sectionName
                                    + "' is out of order compared to '" + lastSectionName + "'",
                            sectionName.toLowerCase(Locale.ENGLISH).compareTo(
                                    lastSectionName.toLowerCase(Locale.ENGLISH)) >= 0);
                }

                validateCheckSection(moduleFactory, fileName, sectionName, section);

                lastSectionName = sectionName;
            }
        }
    }

    @Test
    public void testAllCheckSectionsEx() throws Exception {
        final ModuleFactory moduleFactory = TestUtils.getPackageObjectFactory();

        final Path path = Paths.get(XdocUtil.DIRECTORY_PATH + "/config.xml");
        final String fileName = path.getFileName().toString();

        final String input = new String(Files.readAllBytes(path), UTF_8);
        final Document document = XmlUtil.getRawXml(fileName, input, input);
        final NodeList sources = document.getElementsByTagName("section");

        for (int position = 0; position < sources.getLength(); position++) {
            final Node section = sources.item(position);
            final String sectionName = section.getAttributes().getNamedItem("name")
                    .getNodeValue();

            if (!"Checker".equals(sectionName) && !"TreeWalker".equals(sectionName)) {
                continue;
            }

            validateCheckSection(moduleFactory, fileName, sectionName, section);
        }
    }

    private static void validateCheckSection(ModuleFactory moduleFactory, String fileName,
            String sectionName, Node section) throws Exception {
        final Object instance;

        try {
            instance = moduleFactory.createModule(sectionName);
        }
        catch (CheckstyleException ex) {
            throw new CheckstyleException(fileName + " couldn't find class: " + sectionName, ex);
        }

        int subSectionPos = 0;
        for (Node subSection : XmlUtil.getChildrenElements(section)) {
            final String subSectionName = subSection.getAttributes().getNamedItem("name")
                    .getNodeValue();

            // can be in different orders, and completely optional
            if ("Notes".equals(subSectionName)
                    || "Rule Description".equals(subSectionName)) {
                continue;
            }

            // optional sections that can be skipped if they have nothing to report
            if (subSectionPos == 1 && !"Properties".equals(subSectionName)) {
                validatePropertySection(fileName, sectionName, null, instance);
                subSectionPos++;
            }
            if (subSectionPos == 4 && !"Error Messages".equals(subSectionName)) {
                validateErrorSection(fileName, sectionName, null, instance);
                subSectionPos++;
            }

            Assert.assertEquals(fileName + " section '" + sectionName
                    + "' should be in order", getSubSectionName(subSectionPos),
                    subSectionName);

            switch (subSectionPos) {
                case 0:
                    validateSinceDescriptionSection(fileName, sectionName, subSection);
                    break;
                case 1:
                    validatePropertySection(fileName, sectionName, subSection, instance);
                    break;
                case 2:
                    break;
                case 3:
                    validateUsageExample(fileName, sectionName, subSection);
                    break;
                case 4:
                    validateErrorSection(fileName, sectionName, subSection, instance);
                    break;
                case 5:
                    validatePackageSection(fileName, sectionName, subSection, instance);
                    break;
                case 6:
                    validateParentSection(fileName, sectionName, subSection);
                    break;
                default:
                    break;
            }

            subSectionPos++;
        }
    }

    private static void validateSinceDescriptionSection(String fileName, String sectionName,
            Node subSection) {
        Assert.assertTrue(fileName + " section '" + sectionName
                + "' should have a valid version at the start of the description like:\n"
                + DESCRIPTION_VERSION.pattern(),
                DESCRIPTION_VERSION.matcher(subSection.getTextContent().trim()).find());
    }

    private static Object getSubSectionName(int subSectionPos) {
        final String result;

        switch (subSectionPos) {
            case 0:
                result = "Description";
                break;
            case 1:
                result = "Properties";
                break;
            case 2:
                result = "Examples";
                break;
            case 3:
                result = "Example of Usage";
                break;
            case 4:
                result = "Error Messages";
                break;
            case 5:
                result = "Package";
                break;
            case 6:
                result = "Parent Module";
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    private static void validatePropertySection(String fileName, String sectionName,
            Node subSection, Object instance) throws Exception {
        final Set<String> properties = getProperties(instance.getClass());
        final Class<?> clss = instance.getClass();

        fixCapturedProperties(sectionName, instance, clss, properties);

        if (subSection != null) {
            Assert.assertTrue(fileName + " section '" + sectionName
                    + "' should have no properties to show", !properties.isEmpty());

            validatePropertySectionProperties(fileName, sectionName, subSection, instance,
                    properties);
        }

        Assert.assertTrue(fileName + " section '" + sectionName + "' should show properties: "
                + properties, properties.isEmpty());
    }

    private static void fixCapturedProperties(String sectionName, Object instance, Class<?> clss,
            Set<String> properties) {
        // remove global properties that don't need documentation
        if (hasParentModule(sectionName)) {
            if (AbstractJavadocCheck.class.isAssignableFrom(clss)) {
                properties.removeAll(JAVADOC_CHECK_PROPERTIES);
            }
            else if (AbstractCheck.class.isAssignableFrom(clss)) {
                properties.removeAll(CHECK_PROPERTIES);
            }
        }
        if (AbstractFileSetCheck.class.isAssignableFrom(clss)) {
            properties.removeAll(FILESET_PROPERTIES);

            // override
            properties.add("fileExtensions");
        }

        // remove undocumented properties
        new HashSet<>(properties).stream()
            .filter(prop -> UNDOCUMENTED_PROPERTIES.contains(clss.getSimpleName() + "." + prop))
            .forEach(properties::remove);

        if (AbstractCheck.class.isAssignableFrom(clss)) {
            final AbstractCheck check = (AbstractCheck) instance;

            final int[] acceptableTokens = check.getAcceptableTokens();
            Arrays.sort(acceptableTokens);
            final int[] defaultTokens = check.getDefaultTokens();
            Arrays.sort(defaultTokens);
            final int[] requiredTokens = check.getRequiredTokens();
            Arrays.sort(requiredTokens);

            if (!Arrays.equals(acceptableTokens, defaultTokens)
                    || !Arrays.equals(acceptableTokens, requiredTokens)) {
                properties.add("tokens");
            }
        }

        if (AbstractJavadocCheck.class.isAssignableFrom(clss)) {
            final AbstractJavadocCheck check = (AbstractJavadocCheck) instance;

            final int[] acceptableJavadocTokens = check.getAcceptableJavadocTokens();
            Arrays.sort(acceptableJavadocTokens);
            final int[] defaultJavadocTokens = check.getDefaultJavadocTokens();
            Arrays.sort(defaultJavadocTokens);
            final int[] requiredJavadocTokens = check.getRequiredJavadocTokens();
            Arrays.sort(requiredJavadocTokens);

            if (!Arrays.equals(acceptableJavadocTokens, defaultJavadocTokens)
                    || !Arrays.equals(acceptableJavadocTokens, requiredJavadocTokens)) {
                properties.add("javadocTokens");
            }
        }
    }

    private static void validatePropertySectionProperties(String fileName, String sectionName,
            Node subSection, Object instance, Set<String> properties) throws Exception {
        boolean skip = true;
        boolean didJavadocTokens = false;
        boolean didTokens = false;

        for (Node row : XmlUtil.getChildrenElements(XmlUtil.getFirstChildElement(subSection))) {
            final List<Node> columns = new ArrayList<>(XmlUtil.getChildrenElements(row));

            Assert.assertEquals(fileName + " section '" + sectionName
                    + "' should have the requested columns", 5, columns.size());

            if (skip) {
                Assert.assertEquals(fileName + " section '" + sectionName
                        + "' should have the specific title", "name", columns.get(0)
                        .getTextContent());
                Assert.assertEquals(fileName + " section '" + sectionName
                        + "' should have the specific title", "description", columns.get(1)
                        .getTextContent());
                Assert.assertEquals(fileName + " section '" + sectionName
                        + "' should have the specific title", "type", columns.get(2)
                        .getTextContent());
                Assert.assertEquals(fileName + " section '" + sectionName
                        + "' should have the specific title", "default value", columns.get(3)
                        .getTextContent());
                Assert.assertEquals(fileName + " section '" + sectionName
                        + "' should have the specific title", "since", columns.get(4)
                        .getTextContent());

                skip = false;
                continue;
            }

            Assert.assertFalse(fileName + " section '" + sectionName
                    + "' should have token properties last", didTokens);

            final String propertyName = columns.get(0).getTextContent();
            Assert.assertTrue(fileName + " section '" + sectionName
                    + "' should not contain the property: " + propertyName,
                    properties.remove(propertyName));

            if ("tokens".equals(propertyName)) {
                final AbstractCheck check = (AbstractCheck) instance;
                validatePropertySectionPropertyTokens(fileName, sectionName, check, columns);
                didTokens = true;
            }
            else if ("javadocTokens".equals(propertyName)) {
                final AbstractJavadocCheck check = (AbstractJavadocCheck) instance;
                validatePropertySectionPropertyJavadocTokens(fileName, sectionName, check, columns);
                didJavadocTokens = true;
            }
            else {
                Assert.assertFalse(fileName + " section '" + sectionName
                        + "' should have javadoc token properties next to last, before tokens",
                        didJavadocTokens);

                validatePropertySectionPropertyEx(fileName, sectionName, instance, columns,
                        propertyName);
            }

            Assert.assertFalse(fileName + " section '" + sectionName
                    + "' should have a version for " + propertyName, columns.get(4)
                    .getTextContent().trim().isEmpty());
            Assert.assertTrue(fileName + " section '" + sectionName
                    + "' should have a valid version for " + propertyName,
                    VERSION.matcher(columns.get(4).getTextContent().trim()).matches());
        }
    }

    private static void validatePropertySectionPropertyEx(String fileName, String sectionName,
            Object instance, List<Node> columns, String propertyName) throws Exception {
        Assert.assertFalse(fileName + " section '" + sectionName
                + "' should have a description for " + propertyName, columns.get(1)
                .getTextContent().trim().isEmpty());

        final String actualTypeName = columns.get(2).getTextContent().replace("\n", "")
                .replace("\r", "").replaceAll(" +", " ").trim();

        Assert.assertFalse(fileName + " section '" + sectionName + "' should have a type for "
                + propertyName, actualTypeName.isEmpty());

        final PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(instance,
                propertyName);
        final Class<?> clss = descriptor.getPropertyType();
        final String expectedTypeName = getModulePropertyExpectedTypeName(clss, instance,
                propertyName);

        if (expectedTypeName != null) {
            final String expectedValue = getModulePropertyExpectedValue(clss, instance,
                    propertyName);

            Assert.assertEquals(fileName + " section '" + sectionName
                    + "' should have the type for " + propertyName, expectedTypeName,
                    actualTypeName);

            if (expectedValue != null) {
                final String actualValue = columns.get(3).getTextContent().replace("\n", "")
                        .replace("\r", "").replaceAll(" +", " ").trim();

                Assert.assertEquals(fileName + " section '" + sectionName
                        + "' should have the value for " + propertyName, expectedValue,
                        actualValue);
            }
        }
    }

    private static void validatePropertySectionPropertyTokens(String fileName, String sectionName,
            AbstractCheck check, List<Node> columns) {
        Assert.assertEquals(fileName + " section '" + sectionName
                + "' should have the basic token description", "tokens to check", columns.get(1)
                .getTextContent());
        Assert.assertEquals(
                fileName + " section '" + sectionName + "' should have all the acceptable tokens",
                "subset of tokens "
                        + CheckUtil.getTokenText(check.getAcceptableTokens(),
                                check.getRequiredTokens()), columns.get(2).getTextContent()
                        .replaceAll("\\s+", " ").trim());
        Assert.assertEquals(fileName + " section '" + sectionName
                + "' should have all the default tokens",
                CheckUtil.getTokenText(check.getDefaultTokens(), check.getRequiredTokens()),
                columns.get(3).getTextContent().replaceAll("\\s+", " ").trim());
    }

    private static void validatePropertySectionPropertyJavadocTokens(String fileName,
            String sectionName, AbstractJavadocCheck check, List<Node> columns) {
        Assert.assertEquals(fileName + " section '" + sectionName
                + "' should have the basic token javadoc description", "javadoc tokens to check",
                columns.get(1).getTextContent());
        Assert.assertEquals(
                fileName + " section '" + sectionName
                        + "' should have all the acceptable javadoc tokens",
                "subset of javadoc tokens "
                        + CheckUtil.getJavadocTokenText(check.getAcceptableJavadocTokens(),
                                check.getRequiredJavadocTokens()), columns.get(2).getTextContent()
                        .replaceAll("\\s+", " ").trim());
        Assert.assertEquals(
                fileName + " section '" + sectionName
                        + "' should have all the default javadoc tokens",
                CheckUtil.getJavadocTokenText(check.getDefaultJavadocTokens(),
                        check.getRequiredJavadocTokens()), columns.get(3).getTextContent()
                        .replaceAll("\\s+", " ").trim());
    }

    /** @noinspection IfStatementWithTooManyBranches */
    private static String getModulePropertyExpectedTypeName(Class<?> clss, Object instance,
            String propertyName) {
        final String instanceName = instance.getClass().getSimpleName();
        String result = null;

        if (clss == boolean.class) {
            result = "Boolean";
        }
        else if (clss == int.class) {
            result = "Integer";
        }
        else if (clss == int[].class) {
            result = "Integer Set";
        }
        else if (clss == double[].class) {
            result = "Number Set";
        }
        else if (clss == String[].class) {
            if (propertyName.endsWith("Tokens") || propertyName.endsWith("Token")
                    || "AtclauseOrderCheck".equals(instanceName) && "target".equals(propertyName)
                    || "MultipleStringLiteralsCheck".equals(instanceName)
                            && "ignoreOccurrenceContext".equals(propertyName)) {
                result = "subset of tokens TokenTypes";
            }
            else {
                result = "String Set";
            }
        }
        else if (clss == URI.class) {
            result = "URI";
        }
        else if (clss == Pattern.class) {
            result = "Regular Expression";
        }
        else if (clss == SeverityLevel.class) {
            result = "Severity";
        }
        else if (clss == Scope.class) {
            result = "Scope";
        }
        else if (clss == AccessModifier[].class) {
            result = "Access Modifier Set";
        }
        else if (clss != String.class) {
            Assert.fail("Unknown property type: " + clss.getSimpleName());
        }

        if ("SuppressWarningsHolder".equals(instanceName)) {
            result = result + " in a format of comma separated attribute=value entries. The "
                    + "attribute is the fully qualified name of the Check and value is its alias.";
        }

        return result;
    }

    private static String getModulePropertyExpectedValue(Class<?> clss, Object instance,
            String propertyName) throws Exception {
        final Field field = getField(instance.getClass(), propertyName);
        String result = null;

        if (field != null) {
            final Object value = field.get(instance);

            if (clss == boolean.class) {
                result = value.toString();
            }
            else if (clss == int.class) {
                if (value.equals(Integer.MAX_VALUE)) {
                    result = "java.lang.Integer.MAX_VALUE";
                }
                else {
                    result = value.toString();
                }
            }
            else if (clss == int[].class) {
                result = Arrays.toString((int[]) value).replace("[", "").replace("]", "");
                if (result.isEmpty()) {
                    result = "{}";
                }
            }
            else if (clss == double[].class) {
                result = Arrays.toString((double[]) value).replace("[", "").replace("]", "")
                        .replace(".0", "");
                if (result.isEmpty()) {
                    result = "{}";
                }
            }
            else if (clss == URI.class) {
                if (value != null) {
                    result = '"' + value.toString() + '"';
                }
            }
            else if (clss == Pattern.class) {
                if (value != null) {
                    result = '"' + value.toString().replace("\n", "\\n").replace("\t", "\\t")
                            .replace("\r", "\\r").replace("\f", "\\f") + '"';
                }

                if ("\"$^\"".equals(result)) {
                    result += " (empty)";
                }
            }
            else if (value != null && (clss == SeverityLevel.class || clss == Scope.class)) {
                result = value.toString().toLowerCase(Locale.ENGLISH);
            }
            else if (value != null && clss == AccessModifier[].class) {
                result = Arrays.toString((Object[]) value).replace("[", "").replace("]", "");
            }

            if (clss != String.class && clss != String[].class && result == null) {
                result = "null";
            }
        }

        return result;
    }

    private static Field getField(Class<?> clss, String propertyName) {
        Field result = null;

        if (clss != null) {
            try {
                result = clss.getDeclaredField(propertyName);
                result.setAccessible(true);
            }
            catch (NoSuchFieldException ex) {
                result = getField(clss.getSuperclass(), propertyName);
            }
        }

        return result;
    }

    private static void validateErrorSection(String fileName, String sectionName, Node subSection,
            Object instance) throws Exception {
        final Class<?> clss = instance.getClass();
        final Set<Field> fields = CheckUtil.getCheckMessages(clss);
        final Set<String> list = new TreeSet<>();

        for (Field field : fields) {
            // below is required for package/private classes
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            list.add(field.get(null).toString());
        }

        final StringBuilder expectedText = new StringBuilder(120);

        for (String s : list) {
            expectedText.append(s);
            expectedText.append('\n');
        }

        if (expectedText.length() > 0) {
            expectedText.append("All messages can be customized if the default message doesn't "
                    + "suit you.\nPlease see the documentation to learn how to.");
        }

        if (subSection == null) {
            Assert.assertEquals(fileName + " section '" + sectionName
                    + "' should have the expected error keys", "", expectedText.toString());
        }
        else {
            Assert.assertEquals(fileName + " section '" + sectionName
                    + "' should have the expected error keys", expectedText.toString().trim(),
                    subSection.getTextContent().replaceAll("\n\\s+", "\n").trim());

            for (Node node : XmlUtil.findChildElementsByTag(subSection, "a")) {
                final String url = node.getAttributes().getNamedItem("href").getTextContent();
                final String linkText = node.getTextContent().trim();
                final String expectedUrl;

                if ("see the documentation".equals(linkText)) {
                    expectedUrl = "config.html#Custom_messages";
                }
                else {
                    expectedUrl = "https://github.com/search?q="
                            + "path%3Asrc%2Fmain%2Fresources%2F"
                            + clss.getPackage().getName().replace(".", "%2F")
                            + "+filename%3Amessages*.properties+repo%3Acheckstyle%2Fcheckstyle+%22"
                            + linkText + "%22";
                }

                Assert.assertEquals(fileName + " section '" + sectionName
                        + "' should have matching url for '" + linkText + "'", expectedUrl, url);
            }
        }
    }

    private static void validateUsageExample(String fileName, String sectionName, Node subSection) {
        final String text = subSection.getTextContent().replace("Checkstyle Style", "")
                .replace("Google Style", "").replace("Sun Style", "").trim();

        Assert.assertTrue(fileName + " section '" + sectionName
                + "' has unknown text in 'Example of Usage': " + text, text.isEmpty());

        boolean hasCheckstyle = false;
        boolean hasGoogle = false;
        boolean hasSun = false;

        for (Node node : XmlUtil.findChildElementsByTag(subSection, "a")) {
            final String url = node.getAttributes().getNamedItem("href").getTextContent();
            final String linkText = node.getTextContent().trim();
            String expectedUrl = null;

            if ("Checkstyle Style".equals(linkText)) {
                hasCheckstyle = true;
                expectedUrl = "https://github.com/search?q="
                        + "path%3Aconfig+filename%3Acheckstyle_checks.xml+"
                        + "repo%3Acheckstyle%2Fcheckstyle+" + sectionName;
            }
            else if ("Google Style".equals(linkText)) {
                hasGoogle = true;
                expectedUrl = "https://github.com/search?q="
                        + "path%3Asrc%2Fmain%2Fresources+filename%3Agoogle_checks.xml+"
                        + "repo%3Acheckstyle%2Fcheckstyle+"
                        + sectionName;

                Assert.assertTrue(fileName + " section '" + sectionName
                        + "' should be in google_checks.xml or not reference 'Google Style'",
                        GOOGLE_MODULES.contains(sectionName));
            }
            else if ("Sun Style".equals(linkText)) {
                hasSun = true;
                expectedUrl = "https://github.com/search?q="
                        + "path%3Asrc%2Fmain%2Fresources+filename%3Asun_checks.xml+"
                        + "repo%3Acheckstyle%2Fcheckstyle+"
                        + sectionName;

                Assert.assertTrue(fileName + " section '" + sectionName
                        + "' should be in sun_checks.xml or not reference 'Sun Style'",
                        SUN_MODULES.contains(sectionName));
            }

            Assert.assertEquals(fileName + " section '" + sectionName
                    + "' should have matching url", expectedUrl, url);
        }

        Assert.assertTrue(fileName + " section '" + sectionName
                + "' should have a checkstyle section", hasCheckstyle);
        Assert.assertTrue(fileName + " section '" + sectionName
                + "' should have a google section since it is in it's config", hasGoogle
                || !GOOGLE_MODULES.contains(sectionName));
        Assert.assertTrue(fileName + " section '" + sectionName
                + "' should have a sun section since it is in it's config",
                hasSun || !SUN_MODULES.contains(sectionName));
    }

    private static void validatePackageSection(String fileName, String sectionName,
            Node subSection, Object instance) {
        Assert.assertEquals(fileName + " section '" + sectionName
                + "' should have matching package", instance.getClass().getPackage().getName(),
                subSection.getTextContent().trim());
    }

    private static void validateParentSection(String fileName, String sectionName,
            Node subSection) {
        final String expected;

        if (hasParentModule(sectionName)) {
            expected = "TreeWalker";
        }
        else {
            expected = "Checker";
        }

        Assert.assertEquals(
                fileName + " section '" + sectionName + "' should have matching parent",
                expected, subSection
                        .getTextContent().trim());
    }

    private static boolean hasParentModule(String sectionName) {
        final String search = "\"" + sectionName + "\"";
        boolean result = true;

        for (String find : XML_FILESET_LIST) {
            if (find.contains(search)) {
                result = false;
                break;
            }
        }

        return result;
    }

    private static Set<String> getProperties(Class<?> clss) {
        final Set<String> result = new TreeSet<>();
        final PropertyDescriptor[] map = PropertyUtils.getPropertyDescriptors(clss);

        for (PropertyDescriptor p : map) {
            if (p.getWriteMethod() != null) {
                result.add(p.getName());
            }
        }

        return result;
    }

    @Test
    public void testAllStyleRules() throws Exception {
        for (Path path : XdocUtil.getXdocsStyleFilePaths(XdocUtil.getXdocsFilePaths())) {
            final String fileName = path.getFileName().toString();
            final String input = new String(Files.readAllBytes(path), UTF_8);
            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("tr");
            Set<String> styleChecks = null;

            if (path.toFile().getName().contains("google")) {
                styleChecks = new HashSet<>(GOOGLE_MODULES);
            }
            else if (path.toFile().getName().contains("sun")) {
                styleChecks = new HashSet<>();
            }

            String lastRuleName = null;

            for (int position = 0; position < sources.getLength(); position++) {
                final Node row = sources.item(position);
                final List<Node> columns = new ArrayList<>(
                        XmlUtil.findChildElementsByTag(row, "td"));

                if (columns.isEmpty()) {
                    continue;
                }

                final String ruleName = columns.get(1).getTextContent().trim();

                if (lastRuleName != null) {
                    Assert.assertTrue(
                            fileName + " rule '" + ruleName + "' is out of order compared to '"
                                    + lastRuleName + "'",
                            ruleName.toLowerCase(Locale.ENGLISH).compareTo(
                                    lastRuleName.toLowerCase(Locale.ENGLISH)) >= 0);
                }

                if (!"--".equals(ruleName)) {
                    validateStyleAnchors(XmlUtil.findChildElementsByTag(columns.get(0), "a"),
                            fileName, ruleName);
                }

                validateStyleModules(XmlUtil.findChildElementsByTag(columns.get(2), "a"),
                        XmlUtil.findChildElementsByTag(columns.get(3), "a"), styleChecks, fileName,
                        ruleName);

                lastRuleName = ruleName;
            }

            // these modules aren't documented, but are added to the config
            styleChecks.remove("TreeWalker");
            styleChecks.remove("Checker");

            Assert.assertTrue(fileName + " requires the following check(s) to appear: "
                    + styleChecks, styleChecks.isEmpty());
        }
    }

    private static void validateStyleAnchors(Set<Node> anchors, String fileName, String ruleName) {
        Assert.assertEquals(fileName + " rule '" + ruleName + "' must have two row anchors", 2,
                anchors.size());

        final int space = ruleName.indexOf(' ');
        Assert.assertTrue(fileName + " rule '" + ruleName
                + "' must have have a space between the rule's number and the rule's name",
                space != -1);

        final String ruleNumber = ruleName.substring(0, space);

        int position = 1;

        for (Node anchor : anchors) {
            final String actualUrl;
            final String expectedUrl;

            if (position == 1) {
                actualUrl = anchor.getAttributes().getNamedItem("name").getTextContent();
                expectedUrl = ruleNumber;
            }
            else {
                actualUrl = anchor.getAttributes().getNamedItem("href").getTextContent();
                expectedUrl = "#" + ruleNumber;
            }

            Assert.assertEquals(fileName + " rule '" + ruleName + "' anchor " + position
                    + " should have matching name/url", expectedUrl, actualUrl);

            position++;
        }
    }

    private static void validateStyleModules(Set<Node> checks, Set<Node> configs,
            Set<String> styleChecks, String fileName, String ruleName) {
        final Iterator<Node> itrChecks = checks.iterator();
        final Iterator<Node> itrConfigs = configs.iterator();

        while (itrChecks.hasNext()) {
            final Node module = itrChecks.next();
            final String moduleName = module.getTextContent().trim();

            if (!module.getAttributes().getNamedItem("href").getTextContent()
                    .startsWith("config_")) {
                continue;
            }

            Assert.assertTrue(fileName + " rule '" + ruleName + "' module '" + moduleName
                    + "' shouldn't end with 'Check'", !moduleName.endsWith("Check"));

            styleChecks.remove(moduleName);

            for (String configName : new String[] {"config", "test"}) {
                Node config = null;

                try {
                    config = itrConfigs.next();
                }
                catch (NoSuchElementException ignore) {
                    Assert.fail(fileName + " rule '" + ruleName + "' module '" + moduleName
                            + "' is missing the config link: " + configName);
                }

                Assert.assertEquals(fileName + " rule '" + ruleName + "' module '" + moduleName
                        + "' has mismatched config/test links", configName, config.getTextContent()
                        .trim());

                final String configUrl = config.getAttributes().getNamedItem("href")
                        .getTextContent();

                if ("config".equals(configName)) {
                    final String expectedUrl = "https://github.com/search?q="
                            + "path%3Asrc%2Fmain%2Fresources+filename%3Agoogle_checks.xml+"
                            + "repo%3Acheckstyle%2Fcheckstyle+" + moduleName;

                    Assert.assertEquals(fileName + " rule '" + ruleName + "' module '" + moduleName
                            + "' should have matching " + configName + " url", expectedUrl,
                            configUrl);
                }
                else if ("test".equals(configName)) {
                    Assert.assertTrue(fileName + " rule '" + ruleName + "' module '" + moduleName
                            + "' should have matching " + configName + " url",
                            configUrl.startsWith("https://github.com/checkstyle/checkstyle/"
                                    + "blob/master/src/it/java/com/google/checkstyle/test/"));
                    Assert.assertTrue(fileName + " rule '" + ruleName + "' module '" + moduleName
                            + "' should have matching " + configName + " url",
                            configUrl.endsWith("/" + moduleName + "Test.java"));

                    Assert.assertTrue(fileName + " rule '" + ruleName + "' module '" + moduleName
                            + "' should have a test that exists", new File(configUrl.substring(53)
                            .replace('/', File.separatorChar)).exists());
                }
            }
        }

        Assert.assertFalse(fileName + " rule '" + ruleName + "' has too many configs",
                itrConfigs.hasNext());
    }
}
