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

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;
import static java.lang.Integer.parseInt;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.describedAs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader.IgnoredModulesOptions;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public class XdocsPagesTest {

    private static final Path AVAILABLE_CHECKS_PATH = Paths.get("src/xdocs/checks.xml");
    private static final String LINK_TEMPLATE =
            "(?s).*<a href=\"config_\\w+\\.html#%1$s\">(\\s)*%1$s</a>.*";

    private static final Pattern VERSION = Pattern.compile("\\d+\\.\\d+(\\.\\d+)?");

    private static final Pattern DESCRIPTION_VERSION = Pattern
            .compile("^Since Checkstyle \\d+\\.\\d+(\\.\\d+)?");

    private static final List<String> XML_FILESET_LIST = Arrays.asList(
            "TreeWalker",
            "name=\"Checker\"",
            "name=\"Header\"",
            "name=\"LineLength\"",
            "name=\"Translation\"",
            "name=\"SeverityMatchFilter\"",
            "name=\"SuppressWithPlainTextCommentFilter\"",
            "name=\"SuppressionFilter\"",
            "name=\"SuppressionSingleFilter\"",
            "name=\"SuppressWarningsFilter\"",
            "name=\"BeforeExecutionExclusionFileFilter\"",
            "name=\"RegexpHeader\"",
            "name=\"RegexpOnFilename\"",
            "name=\"RegexpSingleline\"",
            "name=\"RegexpMultiline\"",
            "name=\"JavadocPackage\"",
            "name=\"NewlineAtEndOfFile\"",
            "name=\"OrderedProperties\"",
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

    private static final List<String> PROPERTIES_ALLOWED_GET_TYPES_FROM_METHOD = Arrays.asList(
            // static field (all upper case)
            "SuppressWarningsHolder.aliasList",
            // loads string into memory similar to file
            "Header.header",
            "RegexpHeader.header",
            // deprecated fields
            "JavadocMethod.minLineCount",
            "JavadocMethod.allowMissingJavadoc",
            "JavadocMethod.allowMissingPropertyJavadoc",
            "JavadocMethod.ignoreMethodNamesRegex",
            "JavadocMethod.logLoadErrors",
            "JavadocMethod.suppressLoadErrors",
            "MissingDeprecated.skipNoJavadoc"
    );

    private static final Set<String> SUN_MODULES = Collections.unmodifiableSet(
        new HashSet<>(CheckUtil.getConfigSunStyleModules()));
    // ignore the not yet properly covered modules while testing newly added ones
    // add proper sections to the coverage report and integration tests
    // and then remove this list eventually
    private static final List<String> IGNORED_SUN_MODULES = Arrays.asList(
            "ArrayTypeStyle",
            "AvoidNestedBlocks",
            "AvoidStarImport",
            "ConstantName",
            "DesignForExtension",
            "EmptyBlock",
            "EmptyForIteratorPad",
            "EmptyStatement",
            "EqualsHashCode",
            "FileLength",
            "FileTabCharacter",
            "FinalClass",
            "FinalParameters",
            "GenericWhitespace",
            "HiddenField",
            "HideUtilityClassConstructor",
            "IllegalImport",
            "IllegalInstantiation",
            "InnerAssignment",
            "InterfaceIsType",
            "JavadocMethod",
            "JavadocPackage",
            "JavadocStyle",
            "JavadocType",
            "JavadocVariable",
            "LeftCurly",
            "LineLength",
            "LocalFinalVariableName",
            "LocalVariableName",
            "MagicNumber",
            "MemberName",
            "MethodLength",
            "MethodName",
            "MethodParamPad",
            "MissingJavadocMethod",
            "MissingSwitchDefault",
            "ModifierOrder",
            "NeedBraces",
            "NewlineAtEndOfFile",
            "NoWhitespaceAfter",
            "NoWhitespaceBefore",
            "OperatorWrap",
            "PackageName",
            "ParameterName",
            "ParameterNumber",
            "ParenPad",
            "RedundantImport",
            "RedundantModifier",
            "RegexpSingleline",
            "RightCurly",
            "SimplifyBooleanExpression",
            "SimplifyBooleanReturn",
            "StaticVariableName",
            "TodoComment",
            "Translation",
            "TypecastParenPad",
            "TypeName",
            "UnusedImports",
            "UpperEll",
            "VisibilityModifier",
            "WhitespaceAfter",
            "WhitespaceAround"
    );
    private static final Set<String> GOOGLE_MODULES = Collections.unmodifiableSet(
        new HashSet<>(CheckUtil.getConfigGoogleStyleModules()));

    @Test
    public void testAllChecksPresentOnAvailableChecksPage() throws Exception {
        final String availableChecks = new String(Files.readAllBytes(AVAILABLE_CHECKS_PATH), UTF_8);

        CheckUtil.getSimpleNames(CheckUtil.getCheckstyleChecks())
            .stream()
            .filter(checkName -> !"JavadocMetadataScraper".equals(checkName))
            .forEach(checkName -> {
                if (!isPresent(availableChecks, checkName)) {
                    fail(checkName + " is not correctly listed on Available Checks page"
                        + " - add it to " + AVAILABLE_CHECKS_PATH);
                }
            });
    }

    private static boolean isPresent(String availableChecks, String checkName) {
        final String linkPattern = String.format(Locale.ROOT, LINK_TEMPLATE, checkName);
        return availableChecks.matches(linkPattern);
    }

    @Test
    public void testAllChecksPageInSyncWithChecksSummaries() throws Exception {
        final Pattern endOfSentence = Pattern.compile("(.*?\\.)\\s", Pattern.DOTALL);
        final Map<String, String> summaries = readSummaries();

        for (Path path : XdocUtil.getXdocsConfigFilePaths(XdocUtil.getXdocsFilePaths())) {
            final String fileName = path.getFileName().toString();
            if ("config_system_properties.xml".equals(fileName)
                    || "config_filefilters.xml".equals(fileName)
                    || "config_filters.xml".equals(fileName)) {
                continue;
            }

            final String input = new String(Files.readAllBytes(path), UTF_8);
            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("subsection");

            for (int position = 0; position < sources.getLength(); position++) {
                final Node section = sources.item(position);
                final String sectionName = XmlUtil.getNameAttributeOfNode(section);
                if (!"Description".equals(sectionName)) {
                    continue;
                }

                final String checkName = XmlUtil.getNameAttributeOfNode(section.getParentNode());
                final Matcher matcher = endOfSentence.matcher(section.getTextContent());
                assertWithMessage(
                    "The first sentence of the \"Description\" subsection for the check "
                        + checkName + " in the file \"" + fileName + "\" should end with a period")
                    .that(matcher.find());
                final String firstSentence = XmlUtil.sanitizeXml(matcher.group(1));
                assertWithMessage("The summary for check " + checkName
                        + " in the file \"" + AVAILABLE_CHECKS_PATH + "\""
                        + " should match the first sentence of the \"Description\" subsection"
                        + " for this check in the file \"" + fileName + "\"")
                    .that(summaries.get(checkName))
                    .isEqualTo(firstSentence);
            }
        }
    }

    private static Map<String, String> readSummaries() throws Exception {
        final String fileName = AVAILABLE_CHECKS_PATH.getFileName().toString();
        final String input = new String(Files.readAllBytes(AVAILABLE_CHECKS_PATH), UTF_8);
        final Document document = XmlUtil.getRawXml(fileName, input, input);
        final NodeList rows = document.getElementsByTagName("tr");
        final Map<String, String> result = new HashMap<>();

        for (int position = 0; position < rows.getLength(); position++) {
            final Node row = rows.item(position);
            final Iterator<Node> cells = XmlUtil.findChildElementsByTag(row, "td").iterator();
            final String name = XmlUtil.sanitizeXml(cells.next().getTextContent());
            final String summary = XmlUtil.sanitizeXml(cells.next().getTextContent());

            result.put(name, summary);
        }

        return result;
    }

    @Test
    public void testAllSubSections() throws Exception {
        for (Path path : XdocUtil.getXdocsFilePaths()) {
            final String input = new String(Files.readAllBytes(path), UTF_8);
            final String fileName = path.getFileName().toString();

            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList subSections = document.getElementsByTagName("subsection");

            for (int position = 0; position < subSections.getLength(); position++) {
                final Node subSection = subSections.item(position);
                final Node name = subSection.getAttributes().getNamedItem("name");

                assertNotNull(name, "All sub-sections in '" + fileName + "' must have a name");

                final Node id = subSection.getAttributes().getNamedItem("id");

                assertNotNull(id, "All sub-sections in '" + fileName + "' must have an id");

                final String sectionName;

                if ("google_style.xml".equals(fileName)) {
                    sectionName = "Google";
                }
                else if ("sun_style.xml".equals(fileName)) {
                    sectionName = "Sun";
                }
                else {
                    sectionName = XmlUtil.getNameAttributeOfNode(subSection.getParentNode());
                }

                final String nameString = name.getNodeValue();
                final String idString = id.getNodeValue();

                assertEquals((sectionName + " " + nameString).replace(' ', '_'), idString,
                        fileName + " sub-section " + nameString + " for section "
                        + sectionName + " must match");
            }
        }
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

                if (unserializedSource.length() > 1 && (unserializedSource.charAt(0) != '<'
                        || unserializedSource.charAt(unserializedSource.length() - 1) != '>'
                        // no dtd testing yet
                        || unserializedSource.contains("<!"))) {
                    continue;
                }

                final String code = buildXml(unserializedSource);
                // validate only
                XmlUtil.getRawXml(fileName, code, unserializedSource);

                // can't test ant structure, or old and outdated checks
                assertTrue(fileName.startsWith("anttask")
                        || fileName.startsWith("releasenotes")
                        || isValidCheckstyleXml(fileName, code, unserializedSource),
                        "Xml is invalid, old or has outdated structure");
            }
        }
    }

    private static String buildXml(String unserializedSource) throws IOException {
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
                    + "\"-//Checkstyle//DTD Checkstyle Configuration 1.3//EN\" \"" + dtdPath
                    + "\">\n" + code;
        }
        return code;
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

    private static boolean isValidCheckstyleXml(String fileName, String code,
                                                String unserializedSource)
            throws IOException, CheckstyleException {
        // can't process non-existent examples, or out of context snippets
        if (!code.contains("com.mycompany") && !code.contains("checkstyle-packages")
                && !code.contains("MethodLimit") && !code.contains("<suppress ")
                && !code.contains("<suppress-xpath ")
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
                        new StringReader(code)), expander, IgnoredModulesOptions.EXECUTE);
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
        return true;
    }

    @Test
    public void testAllCheckSections() throws Exception {
        final ModuleFactory moduleFactory = TestUtil.getPackageObjectFactory();

        for (Path path : XdocUtil.getXdocsConfigFilePaths(XdocUtil.getXdocsFilePaths())) {
            final String fileName = path.getFileName().toString();

            if ("config_system_properties.xml".equals(fileName)) {
                continue;
            }

            final String input = new String(Files.readAllBytes(path), UTF_8);
            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("section");
            String lastSectionName = null;

            for (int position = 0; position < sources.getLength(); position++) {
                final Node section = sources.item(position);
                final String sectionName = XmlUtil.getNameAttributeOfNode(section);

                if ("Content".equals(sectionName) || "Overview".equals(sectionName)) {
                    assertNull(lastSectionName,
                            fileName + " section '" + sectionName + "' should be first");
                    continue;
                }

                assertFalse(sectionName.endsWith("Check"),
                        fileName + " section '" + sectionName + "' shouldn't end with 'Check'");
                if (lastSectionName != null) {
                    assertTrue(sectionName.toLowerCase(Locale.ENGLISH).compareTo(
                            lastSectionName.toLowerCase(Locale.ENGLISH)) >= 0,
                            fileName + " section '" + sectionName
                                + "' is out of order compared to '" + lastSectionName + "'");
                }

                validateCheckSection(moduleFactory, fileName, sectionName, section);

                lastSectionName = sectionName;
            }
        }
    }

    /**
     * Test contains asserts in callstack, but idea does not see them.
     *
     * @noinspection JUnitTestMethodWithNoAssertions
     */
    @Test
    public void testAllCheckSectionsEx() throws Exception {
        final ModuleFactory moduleFactory = TestUtil.getPackageObjectFactory();

        final Path path = Paths.get(XdocUtil.DIRECTORY_PATH + "/config.xml");
        final String fileName = path.getFileName().toString();

        final String input = new String(Files.readAllBytes(path), UTF_8);
        final Document document = XmlUtil.getRawXml(fileName, input, input);
        final NodeList sources = document.getElementsByTagName("section");

        for (int position = 0; position < sources.getLength(); position++) {
            final Node section = sources.item(position);
            final String sectionName = XmlUtil.getNameAttributeOfNode(section);

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
            if (subSectionPos == 0 && "p".equals(subSection.getNodeName())) {
                validateSinceDescriptionSection(fileName, sectionName, subSection);
                continue;
            }

            final String subSectionName = XmlUtil.getNameAttributeOfNode(subSection);

            // can be in different orders, and completely optional
            if ("Notes".equals(subSectionName)
                    || "Rule Description".equals(subSectionName)
                    || "Metadata".equals(subSectionName)) {
                continue;
            }

            // optional sections that can be skipped if they have nothing to report
            if (subSectionPos == 1 && !"Properties".equals(subSectionName)) {
                validatePropertySection(fileName, sectionName, null, instance);
                subSectionPos++;
            }
            if (subSectionPos == 4 && !"Violation Messages".equals(subSectionName)) {
                validateViolationSection(fileName, sectionName, null, instance);
                subSectionPos++;
            }

            assertEquals(getSubSectionName(subSectionPos), subSectionName,
                    fileName + " section '" + sectionName + "' should be in order");

            switch (subSectionPos) {
                case 0:
                    validateDescriptionSection(fileName, sectionName, subSection);
                    break;
                case 1:
                    validatePropertySection(fileName, sectionName, subSection, instance);
                    break;
                case 3:
                    validateUsageExample(fileName, sectionName, subSection);
                    break;
                case 4:
                    validateViolationSection(fileName, sectionName, subSection, instance);
                    break;
                case 5:
                    validatePackageSection(fileName, sectionName, subSection, instance);
                    break;
                case 6:
                    validateParentSection(fileName, sectionName, subSection);
                    break;
                case 2:
                default:
                    break;
            }

            subSectionPos++;
        }

        if ("Checker".equals(sectionName)) {
            assertTrue(subSectionPos >= 6, fileName + " section '" + sectionName
                    + "' should contain up to 'Package' sub-section");
        }
        else {
            assertTrue(subSectionPos >= 7, fileName + " section '" + sectionName
                    + "' should contain up to 'Parent' sub-section");
        }
    }

    private static void validateSinceDescriptionSection(String fileName, String sectionName,
            Node subSection) {
        assertTrue(DESCRIPTION_VERSION.matcher(subSection.getTextContent().trim()).find(),
                fileName + " section '" + sectionName
                        + "' should have a valid version at the start of the description like:\n"
                        + DESCRIPTION_VERSION.pattern());
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
                result = "Violation Messages";
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

    private static void validateDescriptionSection(String fileName, String sectionName,
            Node subSection) {
        if ("config_filters.xml".equals(fileName) && "SuppressionXpathFilter".equals(sectionName)) {
            validateListOfSuppressionXpathFilterIncompatibleChecks(subSection);
        }
    }

    private static void validateListOfSuppressionXpathFilterIncompatibleChecks(Node subSection) {
        assertWithMessage(
            "Incompatible check list should match XpathRegressionTest.INCOMPATIBLE_CHECK_NAMES")
            .that(getListById(subSection, "SuppressionXpathFilter_IncompatibleChecks"))
            .isEqualTo(XpathRegressionTest.INCOMPATIBLE_CHECK_NAMES);
        final Set<String> suppressionXpathFilterJavadocChecks = getListById(subSection,
                "SuppressionXpathFilter_JavadocChecks");
        assertWithMessage(
            "Javadoc check list should match XpathRegressionTest.INCOMPATIBLE_JAVADOC_CHECK_NAMES")
            .that(suppressionXpathFilterJavadocChecks)
            .isEqualTo(XpathRegressionTest.INCOMPATIBLE_JAVADOC_CHECK_NAMES);
    }

    private static void validatePropertySection(String fileName, String sectionName,
            Node subSection, Object instance) throws Exception {
        final Set<String> properties = getProperties(instance.getClass());
        final Class<?> clss = instance.getClass();

        fixCapturedProperties(sectionName, instance, clss, properties);

        if (subSection != null) {
            assertFalse(properties.isEmpty(), fileName + " section '" + sectionName
                    + "' should have no properties to show");

            final Set<Node> nodes = XmlUtil.getChildrenElements(subSection);
            assertEquals(1, nodes.size(), fileName + " section '" + sectionName
                    + "' subsection 'Properties' should have one child node");

            final Node div = nodes.iterator().next();
            assertEquals("div", div.getNodeName(), fileName + " section '" + sectionName
                        + "' subsection 'Properties' has unexpected child node");
            final String wrapperMessage = fileName + " section '" + sectionName
                    + "' subsection 'Properties' wrapping div for table needs the"
                    + " class 'wrapper'";
            assertTrue(div.hasAttributes(), wrapperMessage);
            assertNotNull(div.getAttributes().getNamedItem("class").getNodeValue(), wrapperMessage);
            assertTrue(div.getAttributes().getNamedItem("class").getNodeValue().contains("wrapper"),
                    wrapperMessage);

            final Node table = XmlUtil.getFirstChildElement(div);
            assertEquals("table", table.getNodeName(), fileName + " section '" + sectionName
                    + "' subsection 'Properties' has unexpected child node");

            validatePropertySectionProperties(fileName, sectionName, table, instance,
                    properties);
        }

        assertTrue(properties.isEmpty(),
                fileName + " section '" + sectionName + "' should show properties: " + properties);
    }

    private static void fixCapturedProperties(String sectionName, Object instance, Class<?> clss,
            Set<String> properties) {
        // remove global properties that don't need documentation
        if (hasParentModule(sectionName)) {
            if (AbstractJavadocCheck.class.isAssignableFrom(clss)) {
                properties.removeAll(JAVADOC_CHECK_PROPERTIES);

                // override
                properties.add("violateExecutionOnNonTightHtml");
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
            Node table, Object instance, Set<String> properties) throws Exception {
        boolean skip = true;
        boolean didJavadocTokens = false;
        boolean didTokens = false;

        for (Node row : XmlUtil.getChildrenElements(table)) {
            final List<Node> columns = new ArrayList<>(XmlUtil.getChildrenElements(row));

            assertEquals(5, columns.size(), fileName + " section '" + sectionName
                    + "' should have the requested columns");

            if (skip) {
                assertEquals("name", columns.get(0).getTextContent(),
                        fileName + " section '" + sectionName
                                + "' should have the specific title");
                assertEquals("description", columns.get(1).getTextContent(),
                        fileName + " section '" + sectionName
                                + "' should have the specific title");
                assertEquals("type", columns.get(2).getTextContent(),
                        fileName + " section '" + sectionName
                                + "' should have the specific title");
                assertEquals("default value", columns.get(3).getTextContent(),
                        fileName + " section '" + sectionName
                                + "' should have the specific title");
                assertEquals("since", columns.get(4).getTextContent(),
                        fileName + " section '" + sectionName
                                + "' should have the specific title");

                skip = false;
                continue;
            }

            assertFalse(didTokens, fileName + " section '" + sectionName
                    + "' should have token properties last");

            final String propertyName = columns.get(0).getTextContent();
            assertTrue(properties.remove(propertyName), fileName + " section '" + sectionName
                    + "' should not contain the property: " + propertyName);

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
                assertFalse(didJavadocTokens, fileName + " section '" + sectionName
                            + "' should have javadoc token properties next to last, before tokens");

                validatePropertySectionPropertyEx(fileName, sectionName, instance, columns,
                        propertyName);
            }

            assertWithMessage("%s section '%s' should have a version for %s",
                            fileName, sectionName, propertyName)
                    .that(columns.get(4).getTextContent().trim())
                    .isNotEmpty();
            assertWithMessage("%s section '%s' should have a valid version for %s",
                            fileName, sectionName, propertyName)
                    .that(columns.get(4).getTextContent().trim())
                    .matches(VERSION);
        }
    }

    private static void validatePropertySectionPropertyEx(String fileName, String sectionName,
            Object instance, List<Node> columns, String propertyName) throws Exception {
        assertWithMessage("%s section '%s' should have a description for %s",
                        fileName, sectionName, propertyName)
                .that(columns.get(1).getTextContent().trim())
                .isNotEmpty();

        final String actualTypeName = columns.get(2).getTextContent().replace("\n", "")
                .replace("\r", "").replaceAll(" +", " ").trim();

        assertFalse(actualTypeName.isEmpty(),
                fileName + " section '" + sectionName + "' should have a type for " + propertyName);

        final Field field = getField(instance.getClass(), propertyName);
        final Class<?> fieldClss = getFieldClass(fileName, sectionName, instance, field,
                propertyName);

        final String expectedTypeName = getModulePropertyExpectedTypeName(sectionName, fieldClss,
                instance, propertyName);
        final String expectedValue = getModulePropertyExpectedValue(sectionName, propertyName,
                field, fieldClss, instance);

        assertEquals(expectedTypeName, actualTypeName,
                fileName + " section '" + sectionName
                        + "' should have the type for " + propertyName);

        if (expectedValue != null) {
            final String actualValue = columns.get(3).getTextContent().trim()
                    .replaceAll("\\s+", " ")
                    .replaceAll("\\s,", ",");

            assertEquals(expectedValue, actualValue,
                    fileName + " section '" + sectionName
                            + "' should have the value for " + propertyName);
        }
    }

    private static void validatePropertySectionPropertyTokens(String fileName, String sectionName,
            AbstractCheck check, List<Node> columns) {
        assertEquals("tokens to check", columns.get(1).getTextContent(),
                fileName + " section '" + sectionName
                        + "' should have the basic token description");

        final String acceptableTokenText = columns.get(2).getTextContent().trim();
        String expectedAcceptableTokenText = "subset of tokens "
                + CheckUtil.getTokenText(check.getAcceptableTokens(),
                check.getRequiredTokens());
        if (isAllTokensAcceptable(check)) {
            expectedAcceptableTokenText = "set of any supported tokens";
        }
        assertEquals(expectedAcceptableTokenText, acceptableTokenText
                        .replaceAll("\\s+", " ")
                        .replaceAll("\\s,", ",")
                        .replaceAll("\\s\\.", "."),
                fileName + " section '" + sectionName
                        + "' should have all the acceptable tokens");
        assertFalse(isInvalidTokenPunctuation(acceptableTokenText),
                fileName + "'s acceptable token section: " + sectionName
                        + "should have ',' & '.' at beginning of the next corresponding lines.");

        final String defaultTokenText = columns.get(3).getTextContent().trim();
        final String expectedDefaultTokenText = CheckUtil.getTokenText(check.getDefaultTokens(),
                check.getRequiredTokens());
        if (expectedDefaultTokenText.isEmpty()) {
            assertEquals("empty", defaultTokenText,
                    "Empty tokens should have 'empty' string in xdoc");
        }
        else {
            assertEquals(expectedDefaultTokenText, defaultTokenText
                            .replaceAll("\\s+", " ")
                            .replaceAll("\\s,", ",")
                            .replaceAll("\\s\\.", "."),
                    fileName + " section '" + sectionName + "' should have all the default tokens");
            assertFalse(isInvalidTokenPunctuation(defaultTokenText),
                    fileName + "'s default token section: " + sectionName
                          + "should have ',' or '.' at beginning of the next corresponding lines.");
        }

    }

    private static boolean isAllTokensAcceptable(AbstractCheck check) {
        return Arrays.equals(check.getAcceptableTokens(), TokenUtil.getAllTokenIds());
    }

    private static void validatePropertySectionPropertyJavadocTokens(String fileName,
            String sectionName, AbstractJavadocCheck check, List<Node> columns) {
        assertEquals("javadoc tokens to check",
                columns.get(1).getTextContent(), fileName + " section '" + sectionName
                        + "' should have the basic token javadoc description");

        final String acceptableTokenText = columns.get(2).getTextContent().trim();
        assertEquals("subset of javadoc tokens "
                        + CheckUtil.getJavadocTokenText(check.getAcceptableJavadocTokens(),
                check.getRequiredJavadocTokens()),
                acceptableTokenText
                        .replaceAll("\\s+", " ")
                        .replaceAll("\\s,", ",")
                        .replaceAll("\\s\\.", "."),
                fileName + " section '" + sectionName
                        + "' should have all the acceptable javadoc tokens");
        assertFalse(isInvalidTokenPunctuation(acceptableTokenText),
                fileName + "'s acceptable javadoc token section: " + sectionName
                        + "should have ',' & '.' at beginning of the next corresponding lines.");

        final String defaultTokenText = columns.get(3).getTextContent().trim();
        assertEquals(CheckUtil.getJavadocTokenText(check.getDefaultJavadocTokens(),
                check.getRequiredJavadocTokens()),
                defaultTokenText
                        .replaceAll("\\s+", " ")
                        .replaceAll("\\s,", ",")
                        .replaceAll("\\s\\.", "."),
                fileName + " section '" + sectionName
                        + "' should have all the default javadoc tokens");
        assertFalse(isInvalidTokenPunctuation(defaultTokenText),
                fileName + "'s default javadoc token section: " + sectionName
                        + "should have ',' & '.' at beginning of the next corresponding lines.");
    }

    private static boolean isInvalidTokenPunctuation(String tokenText) {
        return Pattern.compile("\\w,").matcher(tokenText).find()
                || Pattern.compile("\\w\\.").matcher(tokenText).find();
    }

    /**
     * Get's the name of the bean property's type for the class.
     *
     * @param sectionName The name of the section/module being worked on.
     * @param fieldClass The bean property's type.
     * @param instance The class instance to work with.
     * @param propertyName The property name to work with.
     * @return String form of property's type.
     * @noinspection IfStatementWithTooManyBranches, OverlyComplexBooleanExpression
     */
    private static String getModulePropertyExpectedTypeName(String sectionName, Class<?> fieldClass,
            Object instance, String propertyName) {
        final String instanceName = instance.getClass().getSimpleName();
        String result = null;
        final String checkProperty = sectionName + ":" + propertyName;
        if (("SuppressionCommentFilter".equals(sectionName)
                || "SuppressWithNearbyCommentFilter".equals(sectionName)
                || "SuppressWithPlainTextCommentFilter".equals(sectionName))
                    && ("checkFormat".equals(propertyName)
                        || "messageFormat".equals(propertyName)
                        || "idFormat".equals(propertyName)
                        || "influenceFormat".equals(propertyName))
                || ("RegexpMultiline".equals(sectionName)
                    || "RegexpSingleline".equals(sectionName)
                    || "RegexpSinglelineJava".equals(sectionName))
                    && "format".equals(propertyName)) {
            // dynamic custom expression
            result = "Regular Expression";
        }
        else if (fieldClass == boolean.class) {
            result = "boolean";
        }
        else if (fieldClass == int.class) {
            result = "int";
        }
        else if (fieldClass == int[].class) {
            if (isPropertyTokenType(sectionName, propertyName)) {
                result = "subset of tokens TokenTypes";
            }
            else {
                result = "int[]";
            }
        }
        else if (fieldClass == double[].class) {
            result = "double[]";
        }
        else if (fieldClass == String.class) {
            result = "String";

            if ("Checker".equals(sectionName) && "localeCountry".equals(propertyName)) {
                result += " (either the empty string or an uppercase ISO 3166 2-letter code)";
            }
            else if ("Checker".equals(sectionName) && "localeLanguage".equals(propertyName)) {
                result += " (either the empty string or a lowercase ISO 639 code)";
            }
        }
        else if (fieldClass == String[].class) {
            if (propertyName.endsWith("Tokens") || propertyName.endsWith("Token")
                    || "AtclauseOrderCheck".equals(instanceName) && "target".equals(propertyName)
                    || "MultipleStringLiteralsCheck".equals(instanceName)
                            && "ignoreOccurrenceContext".equals(propertyName)) {
                result = "subset of tokens TokenTypes";
            }
            else {
                result = "String[]";
            }
        }
        else if (fieldClass == URI.class) {
            result = "URI";
        }
        else if (fieldClass == Pattern.class) {
            if ("SuppressionSingleFilter:checks".equals(checkProperty)
                || "SuppressionXpathSingleFilter:files".equals(checkProperty)
                || "SuppressionXpathSingleFilter:checks".equals(checkProperty)
                || "SuppressionXpathSingleFilter:message".equals(checkProperty)
                || "IllegalTokenText:format".equals(checkProperty)) {
                result = "Regular Expression";
            }
            else {
                result = "Pattern";
            }
        }
        else if (fieldClass == Pattern[].class) {
            if ("ImportOrder:groups".equals(checkProperty)
                || "ImportOrder:staticGroups".equals(checkProperty)
                || "ClassDataAbstractionCoupling:excludeClassesRegexps".equals(checkProperty)
                || "ClassFanOutComplexity:excludeClassesRegexps".equals(checkProperty)) {
                result = "Regular Expressions";
            }
            else {
                result = "Pattern[]";
            }
        }
        else if (fieldClass == Scope.class) {
            result = "Scope";
        }
        else if (fieldClass == AccessModifierOption[].class) {
            result = "AccessModifierOption[]";
        }
        else if ("PropertyCacheFile".equals(fieldClass.getSimpleName())) {
            result = "File";
        }
        else if (fieldClass.isEnum()) {
            result = fieldClass.getSimpleName();
        }
        else {
            fail("Unknown property type: " + fieldClass.getSimpleName());
        }

        if ("SuppressWarningsHolder".equals(instanceName)) {
            result = result + " in a format of comma separated attribute=value entries. The "
                    + "attribute is the fully qualified name of the Check and value is its alias.";
        }

        return result;
    }

    /**
     * Get's the name of the bean property's default value for the class.
     *
     * @param sectionName The name of the section/module being worked on.
     * @param propertyName The property name to work with.
     * @param field The bean property's field.
     * @param fieldClass The bean property's type.
     * @param instance The class instance to work with.
     * @return String form of property's default value.
     * @noinspection OverlyNestedMethod
     */
    private static String getModulePropertyExpectedValue(String sectionName, String propertyName,
            Field field, Class<?> fieldClass, Object instance) throws Exception {
        String result = null;

        if (field != null) {
            Object value = field.get(instance);

            // noinspection IfStatementWithTooManyBranches
            if ("Checker".equals(sectionName) && "localeCountry".equals(propertyName)) {
                result = "default locale country for the Java Virtual Machine";
            }
            else if ("Checker".equals(sectionName) && "localeLanguage".equals(propertyName)) {
                result = "default locale language for the Java Virtual Machine";
            }
            else if ("Checker".equals(sectionName) && "charset".equals(propertyName)) {
                result = "UTF-8";
            }
            else if ("charset".equals(propertyName)) {
                result = "the charset property of the parent Checker module";
            }
            else if ("PropertyCacheFile".equals(fieldClass.getSimpleName())) {
                result = "null (no cache file)";
            }
            else if (fieldClass == boolean.class) {
                result = value.toString();
            }
            else if (fieldClass == int.class) {
                if (value.equals(Integer.MAX_VALUE)) {
                    result = "2147483647";
                }
                else {
                    result = value.toString();
                }
            }
            else if (fieldClass == int[].class) {
                if (value instanceof Collection) {
                    final Collection<?> collection = (Collection<?>) value;
                    final int[] newArray = new int[collection.size()];
                    final Iterator<?> iterator = collection.iterator();
                    int index = 0;

                    while (iterator.hasNext()) {
                        newArray[index] = (Integer) iterator.next();
                        index++;
                    }

                    value = newArray;
                }

                if (isPropertyTokenType(sectionName, propertyName)) {
                    boolean first = true;

                    if (value instanceof BitSet) {
                        final BitSet list = (BitSet) value;
                        final StringBuilder sb = new StringBuilder(20);

                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i)) {
                                if (first) {
                                    first = false;
                                }
                                else {
                                    sb.append(", ");
                                }

                                sb.append(TokenUtil.getTokenName(i));
                            }
                        }

                        result = sb.toString();
                    }
                    else {
                        final StringBuilder sb = new StringBuilder(20);

                        for (int i = 0; i < Array.getLength(value); i++) {
                            if (first) {
                                first = false;
                            }
                            else {
                                sb.append(", ");
                            }

                            sb.append(TokenUtil.getTokenName((int) Array.get(value, i)));
                        }

                        result = sb.toString();
                    }
                }
                else {
                    result = Arrays.toString((int[]) value).replace("[", "").replace("]", "");
                }
                if (result.isEmpty()) {
                    result = "{}";
                }
            }
            else if (fieldClass == double[].class) {
                result = Arrays.toString((double[]) value).replace("[", "").replace("]", "")
                        .replace(".0", "");
                if (result.isEmpty()) {
                    result = "{}";
                }
            }
            else if (fieldClass == String[].class) {
                if (value == null) {
                    result = "";
                }
                else {
                    final Stream<?> valuesStream;
                    if (value instanceof Collection) {
                        final Collection<?> collection = (Collection<?>) value;
                        valuesStream = collection.stream();
                    }
                    else {
                        final Object[] array = (Object[]) value;
                        valuesStream = Arrays.stream(array);
                    }
                    result = valuesStream
                        .map(String.class::cast)
                        .sorted()
                        .collect(Collectors.joining(", "));
                }

                if (result.isEmpty()) {
                    if ("fileExtensions".equals(propertyName)) {
                        result = "all files";
                    }
                    else {
                        result = "{}";
                    }
                }
            }
            else if (fieldClass == URI.class || fieldClass == String.class) {
                if (value != null) {
                    result = '"' + value.toString() + '"';
                }
            }
            else if (fieldClass == Pattern.class) {
                if (value != null) {
                    result = '"' + value.toString().replace("\n", "\\n").replace("\t", "\\t")
                            .replace("\r", "\\r").replace("\f", "\\f") + '"';
                }
            }
            else if (fieldClass == Pattern[].class) {
                if (value instanceof Collection) {
                    final Collection<?> collection = (Collection<?>) value;
                    final Pattern[] newArray = new Pattern[collection.size()];
                    final Iterator<?> iterator = collection.iterator();
                    int index = 0;

                    while (iterator.hasNext()) {
                        final Object next = iterator.next();
                        newArray[index] = (Pattern) next;
                        index++;
                    }

                    value = newArray;
                }

                if (value != null && Array.getLength(value) > 0) {
                    final String[] newArray = new String[Array.getLength(value)];

                    for (int i = 0; i < newArray.length; i++) {
                        newArray[i] = ((Pattern) Array.get(value, i)).pattern();
                    }

                    result = Arrays.toString(newArray).replace("[", "")
                            .replace("]", "");
                }
                else {
                    result = "";
                }

                if (result.isEmpty()) {
                    result = "{}";
                }
            }
            else if (fieldClass.isEnum()) {
                if (value != null) {
                    result = value.toString().toLowerCase(Locale.ENGLISH);
                }
            }
            else if (fieldClass == AccessModifierOption[].class) {
                result = Arrays.toString((Object[]) value).replace("[", "").replace("]", "");
            }
            else {
                fail("Unknown property type: " + fieldClass.getSimpleName());
            }

            if (result == null) {
                result = "null";
            }
        }

        return result;
    }

    /**
     * Checks if the given property is takes token names as a type.
     *
     * @param sectionName The name of the section/module being worked on.
     * @param propertyName The property name to work with.
     * @return {@code true} if the property is takes token names as a type.
     * @noinspection OverlyComplexBooleanExpression
     */
    private static boolean isPropertyTokenType(String sectionName, String propertyName) {
        return "AtclauseOrder".equals(sectionName) && "target".equals(propertyName)
            || "IllegalType".equals(sectionName) && "memberModifiers".equals(propertyName)
            || "MagicNumber".equals(sectionName)
                    && "constantWaiverParentToken".equals(propertyName)
            || "MultipleStringLiterals".equals(sectionName)
                    && "ignoreOccurrenceContext".equals(propertyName)
            || "DescendantToken".equals(sectionName) && "limitedTokens".equals(propertyName);
    }

    private static Field getField(Class<?> clss, String propertyName) {
        Field result = null;

        if (clss != null) {
            try {
                result = clss.getDeclaredField(propertyName);
                result.setAccessible(true);
            }
            catch (NoSuchFieldException ignored) {
                result = getField(clss.getSuperclass(), propertyName);
            }
        }

        return result;
    }

    private static Class<?> getFieldClass(String fileName, String sectionName, Object instance,
            Field field, String propertyName) throws Exception {
        Class<?> result = null;

        if (field != null) {
            result = field.getType();
        }
        if (result == null) {
            assertTrue(
                    PROPERTIES_ALLOWED_GET_TYPES_FROM_METHOD.contains(sectionName + "."
                            + propertyName),
                    fileName + " section '" + sectionName + "' could not find field "
                            + propertyName);

            final PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(instance,
                    propertyName);
            result = descriptor.getPropertyType();
        }
        if (result == List.class || result == Set.class) {
            final ParameterizedType type = (ParameterizedType) field.getGenericType();
            final Class<?> parameterClass = (Class<?>) type.getActualTypeArguments()[0];

            if (parameterClass == Integer.class) {
                result = int[].class;
            }
            else if (parameterClass == String.class) {
                result = String[].class;
            }
            else if (parameterClass == Pattern.class) {
                result = Pattern[].class;
            }
            else {
                fail("Unknown parameterized type: " + parameterClass.getSimpleName());
            }
        }
        else if (result == BitSet.class) {
            result = int[].class;
        }

        return result;
    }

    private static Set<String> getListById(Node subSection, String id) {
        Set<String> result = null;
        final Node node = XmlUtil.findChildElementById(subSection, id);
        if (node != null) {
            result = XmlUtil.getChildrenElements(node)
                    .stream()
                    .map(Node::getTextContent)
                    .collect(Collectors.toSet());
        }
        return result;
    }

    private static void validateViolationSection(String fileName, String sectionName,
                                                 Node subSection,
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
            assertEquals("", expectedText.toString(), fileName + " section '" + sectionName
                    + "' should have the expected error keys");
        }
        else {
            assertEquals(expectedText.toString().trim(),
                    subSection.getTextContent().replaceAll("\n\\s+", "\n").trim(),
                    fileName + " section '" + sectionName
                            + "' should have the expected error keys");

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

                assertEquals(expectedUrl, url, fileName + " section '" + sectionName
                        + "' should have matching url for '" + linkText + "'");
            }
        }
    }

    private static void validateUsageExample(String fileName, String sectionName, Node subSection) {
        final String text = subSection.getTextContent().replace("Checkstyle Style", "")
                .replace("Google Style", "").replace("Sun Style", "").trim();

        assertTrue(text.isEmpty(), fileName + " section '" + sectionName
                + "' has unknown text in 'Example of Usage': " + text);

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

                assertTrue(
                        GOOGLE_MODULES.contains(sectionName), fileName + " section '" + sectionName
                            + "' should be in google_checks.xml or not reference 'Google Style'");
            }
            else if ("Sun Style".equals(linkText)) {
                hasSun = true;
                expectedUrl = "https://github.com/search?q="
                        + "path%3Asrc%2Fmain%2Fresources+filename%3Asun_checks.xml+"
                        + "repo%3Acheckstyle%2Fcheckstyle+"
                        + sectionName;

                assertTrue(
                        SUN_MODULES.contains(sectionName), fileName + " section '" + sectionName
                                + "' should be in sun_checks.xml or not reference 'Sun Style'");
            }

            assertEquals(expectedUrl, url, fileName + " section '" + sectionName
                    + "' should have matching url");
        }

        assertTrue(hasCheckstyle, fileName + " section '" + sectionName
                + "' should have a checkstyle section");
        assertTrue(hasGoogle
                || !GOOGLE_MODULES.contains(sectionName), fileName + " section '" + sectionName
                        + "' should have a google section since it is in it's config");
        assertTrue(hasSun || !SUN_MODULES.contains(sectionName),
                fileName + " section '" + sectionName
                        + "' should have a sun section since it is in it's config");
    }

    private static void validatePackageSection(String fileName, String sectionName,
            Node subSection, Object instance) {
        assertEquals(instance.getClass().getPackage().getName(),
                subSection.getTextContent().trim(), fileName + " section '" + sectionName
                        + "' should have matching package");
    }

    private static void validateParentSection(String fileName, String sectionName,
            Node subSection) {
        final String expected;

        if (!"TreeWalker".equals(sectionName) && hasParentModule(sectionName)) {
            expected = "TreeWalker";
        }
        else {
            expected = "Checker";
        }

        assertEquals(expected, subSection.getTextContent().trim(),
                fileName + " section '" + sectionName + "' should have matching parent");
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
            final String styleName = fileName.substring(0, fileName.lastIndexOf('_'));
            final String input = new String(Files.readAllBytes(path), UTF_8);
            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("tr");

            final Set<String> styleChecks;
            switch (styleName) {
                case "google":
                    styleChecks = new HashSet<>(GOOGLE_MODULES);
                    break;

                case "sun":
                    styleChecks = new HashSet<>(SUN_MODULES);
                    styleChecks.removeAll(IGNORED_SUN_MODULES);
                    break;

                default:
                    fail("Missing modules list for style file '" + fileName + "'");
                    styleChecks = null;
            }

            String lastRuleName = null;
            String[] lastRuleNumberParts = null;

            for (int position = 0; position < sources.getLength(); position++) {
                final Node row = sources.item(position);
                final List<Node> columns = new ArrayList<>(
                        XmlUtil.findChildElementsByTag(row, "td"));

                if (columns.isEmpty()) {
                    continue;
                }

                final String ruleName = columns.get(1).getTextContent().trim();
                lastRuleNumberParts = validateRuleNameOrder(
                        fileName, lastRuleName, lastRuleNumberParts, ruleName);

                if (!"--".equals(ruleName)) {
                    validateStyleAnchors(XmlUtil.findChildElementsByTag(columns.get(0), "a"),
                            fileName, ruleName);
                }

                validateStyleModules(XmlUtil.findChildElementsByTag(columns.get(2), "a"),
                        XmlUtil.findChildElementsByTag(columns.get(3), "a"), styleChecks, styleName,
                        ruleName);

                lastRuleName = ruleName;
            }

            // these modules aren't documented, but are added to the config
            styleChecks.remove("BeforeExecutionExclusionFileFilter");
            styleChecks.remove("SuppressionFilter");
            styleChecks.remove("SuppressionXpathFilter");
            styleChecks.remove("SuppressionXpathSingleFilter");
            styleChecks.remove("TreeWalker");
            styleChecks.remove("Checker");

            assertTrue(styleChecks.isEmpty(),
                    fileName + " requires the following check(s) to appear: " + styleChecks);
        }
    }

    private static String[] validateRuleNameOrder(String fileName, String lastRuleName,
                                                  String[] lastRuleNumberParts, String ruleName) {
        final String[] ruleNumberParts = ruleName.split(" ", 2)[0].split("\\.");

        if (lastRuleName != null) {
            final int ruleNumberPartsAmount = ruleNumberParts.length;
            final int lastRuleNumberPartsAmount = lastRuleNumberParts.length;
            final String outOfOrderReason = fileName + " rule '" + ruleName
                    + "' is out of order compared to '" + lastRuleName + "'";
            boolean lastRuleNumberPartWasEqual = false;
            int partIndex;
            for (partIndex = 0; partIndex < ruleNumberPartsAmount; partIndex++) {
                if (lastRuleNumberPartsAmount <= partIndex) {
                    // equal up to here and last rule has less parts,
                    // thus order is correct, stop comparing
                    break;
                }

                final String ruleNumberPart = ruleNumberParts[partIndex];
                final String lastRuleNumberPart = lastRuleNumberParts[partIndex];
                final boolean ruleNumberPartsAreNumeric = IntStream.concat(
                        ruleNumberPart.chars(),
                        lastRuleNumberPart.chars()
                ).allMatch(Character::isDigit);

                if (ruleNumberPartsAreNumeric) {
                    final int numericRuleNumberPart = parseInt(ruleNumberPart);
                    final int numericLastRuleNumberPart = parseInt(lastRuleNumberPart);
                    assertThat(outOfOrderReason,
                            numericRuleNumberPart < numericLastRuleNumberPart,
                            describedAs("'%0' should not be less than '%1'",
                                    is(false),
                                    numericRuleNumberPart, numericLastRuleNumberPart));
                }
                else {
                    assertThat(outOfOrderReason,
                            ruleNumberPart.compareToIgnoreCase(lastRuleNumberPart) < 0,
                            describedAs("'%0' should not be less than '%1'",
                                    is(false),
                                    ruleNumberPart, lastRuleNumberPart));
                }
                lastRuleNumberPartWasEqual = ruleNumberPart.equalsIgnoreCase(lastRuleNumberPart);
                if (!lastRuleNumberPartWasEqual) {
                    // number part is not equal but properly ordered,
                    // thus order is correct, stop comparing
                    break;
                }
            }
            if (ruleNumberPartsAmount == partIndex && lastRuleNumberPartWasEqual) {
                if (lastRuleNumberPartsAmount == partIndex) {
                    fail(fileName + " rule '" + ruleName + "' and rule '"
                            + lastRuleName + "' have the same rule number");
                }
                else {
                    fail(outOfOrderReason);
                }
            }
        }

        return ruleNumberParts;
    }

    private static void validateStyleAnchors(Set<Node> anchors, String fileName, String ruleName) {
        assertEquals(2,
                anchors.size(), fileName + " rule '" + ruleName + "' must have two row anchors");

        final int space = ruleName.indexOf(' ');
        assertNotEquals(-1, space,
                fileName + " rule '" + ruleName
                        + "' must have have a space between the rule's number and the rule's name");

        final String ruleNumber = ruleName.substring(0, space);

        int position = 1;

        for (Node anchor : anchors) {
            final String actualUrl;
            final String expectedUrl;

            if (position == 1) {
                actualUrl = XmlUtil.getNameAttributeOfNode(anchor);
                expectedUrl = ruleNumber;
            }
            else {
                actualUrl = anchor.getAttributes().getNamedItem("href").getTextContent();
                expectedUrl = "#" + ruleNumber;
            }

            assertEquals(expectedUrl, actualUrl, fileName + " rule '" + ruleName + "' anchor "
                    + position + " should have matching name/url");

            position++;
        }
    }

    private static void validateStyleModules(Set<Node> checks, Set<Node> configs,
            Set<String> styleChecks, String styleName, String ruleName) {
        final Iterator<Node> itrChecks = checks.iterator();
        final Iterator<Node> itrConfigs = configs.iterator();

        while (itrChecks.hasNext()) {
            final Node module = itrChecks.next();
            final String moduleName = module.getTextContent().trim();

            if (!module.getAttributes().getNamedItem("href").getTextContent()
                    .startsWith("config_")) {
                continue;
            }

            assertFalse(moduleName.endsWith("Check"),
                    styleName + "_style.xml rule '" + ruleName + "' module '" + moduleName
                        + "' shouldn't end with 'Check'");

            styleChecks.remove(moduleName);

            for (String configName : new String[] {"config", "test"}) {
                Node config = null;

                try {
                    config = itrConfigs.next();
                }
                catch (NoSuchElementException ignore) {
                    fail(styleName + "_style.xml rule '" + ruleName + "' module '"
                            + moduleName + "' is missing the config link: " + configName);
                }

                assertEquals(configName, config.getTextContent().trim(),
                        styleName + "_style.xml rule '" + ruleName + "' module '"
                                + moduleName + "' has mismatched config/test links");

                final String configUrl = config.getAttributes().getNamedItem("href")
                        .getTextContent();

                if ("config".equals(configName)) {
                    final String expectedUrl = "https://github.com/search?q="
                            + "path%3Asrc%2Fmain%2Fresources+filename%3A" + styleName
                            + "_checks.xml+repo%3Acheckstyle%2Fcheckstyle+" + moduleName;

                    assertEquals(expectedUrl, configUrl,
                            styleName + "_style.xml rule '" + ruleName + "' module '"
                                    + moduleName + "' should have matching " + configName + " url");
                }
                else if ("test".equals(configName)) {
                    assertTrue(
                            configUrl.startsWith("https://github.com/checkstyle/checkstyle/"
                                    + "blob/master/src/it/java/com/" + styleName
                                    + "/checkstyle/test/"),
                            styleName + "_style.xml rule '" + ruleName + "' module '"
                                    + moduleName + "' should have matching " + configName + " url");
                    assertTrue(configUrl.endsWith("/" + moduleName + "Test.java"),
                            styleName + "_style.xml rule '" + ruleName + "' module '"
                                    + moduleName + "' should have matching " + configName + " url");

                    assertTrue(
                            new File(configUrl.substring(53)
                                    .replace('/', File.separatorChar)).exists(),
                            styleName + "_style.xml rule '" + ruleName + "' module '"
                                    + moduleName + "' should have a test that exists");
                }
            }
        }

        assertFalse(itrConfigs.hasNext(),
                styleName + "_style.xml rule '" + ruleName + "' has too many configs");
    }

}
