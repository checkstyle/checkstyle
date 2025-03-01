///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;
import static java.lang.Integer.parseInt;

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
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader.IgnoredModulesOptions;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XdocGenerator;
import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Generates xdocs pages from templates and performs validations.
 * Before running this test, the following commands have to be executed:
 * - mvn clean compile - Required for next command
 * - mvn plexus-component-metadata:generate-metadata - Required to find custom macros and parser
 */
public class XdocsPagesTest {
    private static final Path SITE_PATH = Path.of("src/site/site.xml");

    private static final Path AVAILABLE_CHECKS_PATH = Path.of("src/site/xdoc/checks.xml");
    private static final String LINK_TEMPLATE =
            "(?s).*<a href=\"[^\"]+#%1$s\">([\\r\\n\\s])*%1$s([\\r\\n\\s])*</a>.*";

    private static final Pattern VERSION = Pattern.compile("\\d+\\.\\d+(\\.\\d+)?");

    private static final Pattern DESCRIPTION_VERSION = Pattern
            .compile("^Since Checkstyle \\d+\\.\\d+(\\.\\d+)?");

    private static final List<String> XML_FILESET_LIST = List.of(
            "TreeWalker",
            "name=\"Checker\"",
            "name=\"Header\"",
            "name=\"LineLength\"",
            "name=\"Translation\"",
            "name=\"SeverityMatchFilter\"",
            "name=\"SuppressWithNearbyTextFilter\"",
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

    private static final Set<String> UNDOCUMENTED_PROPERTIES = Set.of(
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

    private static final Set<String> PROPERTIES_ALLOWED_GET_TYPES_FROM_METHOD = Set.of(
            // static field (all upper case)
            "SuppressWarningsHolder.aliasList",
            // loads string into memory similar to file
            "Header.header",
            "RegexpHeader.header",
            // property is an int, but we cut off excess to accommodate old versions
            "RedundantModifier.jdkVersion",
            // until https://github.com/checkstyle/checkstyle/issues/13376
            "CustomImportOrder.customImportOrderRules"
    );

    private static final Set<String> SUN_MODULES = Collections.unmodifiableSet(
        CheckUtil.getConfigSunStyleModules());
    // ignore the not yet properly covered modules while testing newly added ones
    // add proper sections to the coverage report and integration tests
    // and then remove this list eventually
    private static final Set<String> IGNORED_SUN_MODULES = Set.of(
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
        CheckUtil.getConfigGoogleStyleModules());

    private static final Set<String> NON_MODULE_XDOC = Set.of(
        "config_system_properties.xml",
        "sponsoring.xml",
        "consulting.xml",
        "index.xml",
        "extending.xml",
        "contributing.xml",
        "running.xml",
        "checks.xml",
        "property_types.xml",
        "google_style.xml",
        "sun_style.xml",
        "style_configs.xml",
        "writingfilters.xml",
        "writingfilefilters.xml",
        "eclipse.xml",
        "netbeans.xml",
        "idea.xml",
        "beginning_development.xml",
        "writingchecks.xml",
        "config.xml",
        "report_issue.xml"
    );
    private static final String MUST_BE_IN_ALPHABETICAL_ORDER = " must be in alphabetical order: ";

    @TempDir
    private static File temporaryFolder;

    /**
     * Generate xdoc content from templates before validation.
     * This method will be removed once
     * <a href="https://github.com/checkstyle/checkstyle/issues/13426">#13426</a> is resolved.
     *
     * @throws Exception if something goes wrong
     */
    @BeforeAll
    public static void generateXdocContent() throws Exception {
        XdocGenerator.generateXdocContent(temporaryFolder);
    }

    @Test
    public void testAllChecksPresentOnAvailableChecksPage() throws Exception {
        final String availableChecks = Files.readString(AVAILABLE_CHECKS_PATH);

        CheckUtil.getSimpleNames(CheckUtil.getCheckstyleChecks())
            .stream()
            .filter(checkName -> {
                return !"JavadocMetadataScraper".equals(checkName)
                    && !"ClassAndPropertiesSettersJavadocScraper".equals(checkName);
            })
            .forEach(checkName -> {
                if (!isPresent(availableChecks, checkName)) {
                    assertWithMessage(
                            checkName + " is not correctly listed on Available Checks page"
                                    + " - add it to " + AVAILABLE_CHECKS_PATH).fail();
                }
            });
    }

    private static boolean isPresent(String availableChecks, String checkName) {
        final String linkPattern = String.format(Locale.ROOT, LINK_TEMPLATE, checkName);
        return availableChecks.matches(linkPattern);
    }

    @Test
    public void testAllConfigsHaveLinkInSite() throws Exception {
        final String siteContent = Files.readString(SITE_PATH);

        for (Path path : XdocUtil.getXdocsConfigFilePaths(XdocUtil.getXdocsFilePaths())) {
            final String expectedFile = path.toString()
                    .replace(".xml", ".html")
                    .replaceAll("\\\\", "/")
                    .replaceAll("src[\\\\/]site[\\\\/]xdoc[\\\\/]", "");
            final boolean isConfigHtmlFile = Pattern.matches("config_[a-z]+.html", expectedFile);
            final boolean isChecksIndexHtmlFile = "checks/index.html".equals(expectedFile);
            final boolean isOldReleaseNotes = path.toString().contains("releasenotes_");
            final boolean isInnerPage = "report_issue.html".equals(expectedFile);

            if (!isConfigHtmlFile && !isChecksIndexHtmlFile
                && !isOldReleaseNotes && !isInnerPage) {
                final String expectedLink = String.format(Locale.ROOT, "href=\"%s\"", expectedFile);
                assertWithMessage("Expected to find link to '" + expectedLink + "' in " + SITE_PATH)
                        .that(siteContent)
                        .contains(expectedLink);
            }
        }
    }

    @Test
    public void testAllChecksPageInSyncWithChecksSummaries() throws Exception {
        final Pattern endOfSentence = Pattern.compile("(.*?\\.)\\s", Pattern.DOTALL);
        final Map<String, String> summaries = readSummaries();

        for (Path path : XdocUtil.getXdocsConfigFilePaths(XdocUtil.getXdocsFilePaths())) {
            final String fileName = path.getFileName().toString();
            if (isNonModulePage(fileName)
                || path.toString().contains("filefilters")
                || path.toString().contains("filters")) {
                continue;
            }

            final String input = Files.readString(path);
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
                    .that(matcher.find())
                    .isTrue();
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

    @Test
    public void testCategoryIndexPageTableInSyncWithAllChecksPageTable() throws Exception {
        final Map<String, String> summaries = readSummaries();
        for (Path path : XdocUtil.getXdocsConfigFilePaths(XdocUtil.getXdocsFilePaths())) {
            final String fileName = path.getFileName().toString();
            if (!"index.xml".equals(fileName)
                    || path.getParent().toString().contains("filters")) {
                continue;
            }

            final String input = Files.readString(path);
            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("tr");

            for (int position = 0; position < sources.getLength(); position++) {
                final Node tableRow = sources.item(position);
                final Iterator<Node> cells = XmlUtil
                        .findChildElementsByTag(tableRow, "td").iterator();
                final String checkName = XmlUtil.sanitizeXml(cells.next().getTextContent());
                final String description = XmlUtil.sanitizeXml(cells.next().getTextContent());
                assertWithMessage("The summary for check " + checkName
                        + " in the file \"" + path + "\""
                        + " should match the summary"
                        + " for this check in the file \"" + AVAILABLE_CHECKS_PATH + "\"")
                    .that(description)
                    .isEqualTo(summaries.get(checkName));
            }
        }
    }

    @Test
    public void testAlphabetOrderInNames() throws Exception {
        final String input = Files.readString(SITE_PATH);
        final Document document = XmlUtil.getRawXml(SITE_PATH.toString(), input, input);
        final NodeList nodes = document.getElementsByTagName("item");

        for (int nodeIndex = 0; nodeIndex < nodes.getLength(); nodeIndex++) {
            final Node current = nodes.item(nodeIndex);

            if ("Checks".equals(XmlUtil.getNameAttributeOfNode(current))) {
                final List<String> groupNames = getNames(current);
                final List<String> groupNamesSorted = groupNames.stream()
                        .sorted()
                        .collect(Collectors.toUnmodifiableList());

                assertWithMessage("Group names must be in alphabetical order. ")
                        .that(groupNames)
                        .containsExactlyElementsIn(groupNamesSorted)
                        .inOrder();

                Node groupNode = current.getFirstChild();
                int index = 0;
                final int totalGroups = XmlUtil.getChildrenElements(current).size();
                while (index < totalGroups) {
                    if ("item".equals(groupNode.getNodeName())) {
                        final List<String> checkNames = getNames(groupNode);
                        final List<String> checkNamesSorted = checkNames.stream()
                                .sorted()
                                .collect(Collectors.toUnmodifiableList());
                        assertWithMessage("Check Names" + MUST_BE_IN_ALPHABETICAL_ORDER + SITE_PATH)
                                .that(checkNames)
                                .containsExactlyElementsIn(checkNamesSorted)
                                .inOrder();
                        index++;
                    }
                    groupNode = groupNode.getNextSibling();
                }
            }
            if ("Filters".equals(XmlUtil.getNameAttributeOfNode(current))) {
                final List<String> filterNames = getNames(current);
                final List<String> filterNamesSorted = filterNames.stream()
                        .sorted()
                        .collect(Collectors.toUnmodifiableList());
                assertWithMessage("Filter" + MUST_BE_IN_ALPHABETICAL_ORDER + SITE_PATH)
                        .that(filterNames)
                        .containsExactlyElementsIn(filterNamesSorted)
                        .inOrder();
            }
            if ("File Filters".equals(XmlUtil.getNameAttributeOfNode(current))) {
                final List<String> fileFilterNames = getNames(current);
                final List<String> fileFilterNamesSorted = fileFilterNames.stream()
                        .sorted()
                        .collect(Collectors.toUnmodifiableList());
                assertWithMessage("File Filter" + MUST_BE_IN_ALPHABETICAL_ORDER + SITE_PATH)
                        .that(fileFilterNames)
                        .containsExactlyElementsIn(fileFilterNamesSorted)
                        .inOrder();
            }
        }
    }

    @Test
    public void testAlphabetOrderAtIndexPages() throws Exception {
        final Path allChecks = Paths.get("src/site/xdoc/checks.xml");
        validateOrder(allChecks, "Check");

        final String[] groupNames = {"annotation", "blocks", "design",
            "coding", "header", "imports", "javadoc", "metrics",
            "misc", "modifier", "naming", "regexp", "sizes", "whitespace"};
        for (String name : groupNames) {
            final Path checks = Paths.get("src/site/xdoc/checks/" + name + "/index.xml");
            validateOrder(checks, "Check");
        }
        final Path filters = Paths.get("src/site/xdoc/filters/index.xml");
        validateOrder(filters, "Filter");

        final Path fileFilters = Paths.get("src/site/xdoc/filefilters/index.xml");
        validateOrder(fileFilters, "File Filter");
    }

    public static void validateOrder(Path path, String name) throws Exception {
        final String input = Files.readString(path);
        final Document document = XmlUtil.getRawXml(path.toString(), input, input);
        final NodeList nodes = document.getElementsByTagName("div");

        for (int nodeIndex = 0; nodeIndex < nodes.getLength(); nodeIndex++) {
            final Node current = nodes.item(nodeIndex);
            final List<String> names = getNamesFromIndexPage(current);
            final List<String> namesSorted = names.stream()
                    .sorted()
                    .collect(Collectors.toUnmodifiableList());

            assertWithMessage(path + MUST_BE_IN_ALPHABETICAL_ORDER)
                    .that(names)
                    .containsExactlyElementsIn(namesSorted)
                    .inOrder();
        }
    }

    private static List<String> getNamesFromIndexPage(Node node) {
        final List<String> result = new ArrayList<>();
        final Set<Node> children = XmlUtil.findChildElementsByTag(node, "a");

        Node current = node.getFirstChild();
        Node treeNode = current;
        boolean getFirstChild = false;
        int index = 0;
        while (current != null && index < children.size()) {
            if ("tr".equals(current.getNodeName())) {
                treeNode = current.getNextSibling();
            }
            if ("a".equals(current.getNodeName())) {
                final String name = current.getFirstChild().getTextContent()
                    .replace(" ", "").replace("\n", "");
                result.add(name);
                current = treeNode;
                getFirstChild = false;
                index++;
            }
            else if (getFirstChild) {
                current = current.getFirstChild();
                getFirstChild = false;
            }
            else {
                current = current.getNextSibling();
                getFirstChild = true;
            }
        }
        return result;
    }

    private static List<String> getNames(Node node) {
        final Set<Node> children = XmlUtil.getChildrenElements(node);
        final List<String> result = new ArrayList<>();
        Node current = node.getFirstChild();
        int index = 0;
        while (index < children.size()) {
            if ("item".equals(current.getNodeName())) {
                final String name = XmlUtil.getNameAttributeOfNode(current);
                result.add(name);
                index++;
            }
            current = current.getNextSibling();
        }
        return result;
    }

    private static Map<String, String> readSummaries() throws Exception {
        final String fileName = AVAILABLE_CHECKS_PATH.getFileName().toString();
        final String input = Files.readString(AVAILABLE_CHECKS_PATH);
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
            final String input = Files.readString(path);
            final String fileName = path.getFileName().toString();

            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList subSections = document.getElementsByTagName("subsection");

            for (int position = 0; position < subSections.getLength(); position++) {
                final Node subSection = subSections.item(position);
                final Node name = subSection.getAttributes().getNamedItem("name");

                assertWithMessage("All sub-sections in '" + fileName + "' must have a name")
                    .that(name)
                    .isNotNull();

                final Node id = subSection.getAttributes().getNamedItem("id");

                assertWithMessage("All sub-sections in '" + fileName + "' must have an id")
                    .that(id)
                    .isNotNull();

                final String sectionName;
                final String nameString = name.getNodeValue();
                final String idString = id.getNodeValue();
                final String expectedId;

                if ("google_style.xml".equals(fileName)) {
                    sectionName = "Google";
                    expectedId = (sectionName + " " + nameString).replace(' ', '_');
                }
                else if ("sun_style.xml".equals(fileName)) {
                    sectionName = "Sun";
                    expectedId = (sectionName + " " + nameString).replace(' ', '_');
                }
                else if (path.toString().contains("filters")
                        || path.toString().contains("checks")) {
                    // Checks and filters have their own xdocs files, so the section name
                    // is the same as the section id.
                    sectionName = XmlUtil.getNameAttributeOfNode(subSection.getParentNode());
                    expectedId = nameString.replace(' ', '_');
                }
                else {
                    sectionName = XmlUtil.getNameAttributeOfNode(subSection.getParentNode());
                    expectedId = (sectionName + " " + nameString).replace(' ', '_');
                }

                assertWithMessage(fileName + " sub-section " + nameString + " for section "
                        + sectionName + " must match")
                    .that(idString)
                    .isEqualTo(expectedId);
            }
        }
    }

    @Test
    public void testAllXmlExamples() throws Exception {
        for (Path path : XdocUtil.getXdocsFilePaths()) {
            final String input = Files.readString(path);
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
                assertWithMessage("Xml is invalid, old or has outdated structure")
                        .that(fileName.startsWith("anttask")
                                || fileName.startsWith("releasenotes")
                                || fileName.startsWith("writingjavadocchecks")
                                || isValidCheckstyleXml(fileName, code, unserializedSource))
                        .isTrue();
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
                properties.setProperty("config.folder",
                        new File("config").getCanonicalPath());

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

            if (isNonModulePage(fileName)) {
                continue;
            }

            final String input = Files.readString(path);
            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("section");
            String lastSectionName = null;

            for (int position = 0; position < sources.getLength(); position++) {
                final Node section = sources.item(position);
                final String sectionName = XmlUtil.getNameAttributeOfNode(section);

                if ("Content".equals(sectionName) || "Overview".equals(sectionName)) {
                    assertWithMessage(fileName + " section '" + sectionName + "' should be first")
                        .that(lastSectionName)
                        .isNull();
                    continue;
                }

                assertWithMessage(
                        fileName + " section '" + sectionName + "' shouldn't end with 'Check'")
                                .that(sectionName.endsWith("Check"))
                                .isFalse();
                if (lastSectionName != null) {
                    assertWithMessage(fileName + " section '" + sectionName
                            + "' is out of order compared to '" + lastSectionName + "'")
                                    .that(sectionName.toLowerCase(Locale.ENGLISH).compareTo(
                                            lastSectionName.toLowerCase(Locale.ENGLISH)) >= 0)
                                    .isTrue();
                }

                validateCheckSection(moduleFactory, fileName, sectionName, section);

                lastSectionName = sectionName;
            }
        }
    }

    public static boolean isNonModulePage(String fileName) {
        return NON_MODULE_XDOC.contains(fileName)
            || fileName.startsWith("releasenotes")
            || Pattern.matches("config_[a-z]+.xml", fileName);
    }

    @Test
    public void testAllCheckSectionsEx() throws Exception {
        final ModuleFactory moduleFactory = TestUtil.getPackageObjectFactory();

        final Path path = Path.of(XdocUtil.DIRECTORY_PATH + "/config.xml");
        final String fileName = path.getFileName().toString();

        final String input = Files.readString(path);
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

            assertWithMessage(fileName + " section '" + sectionName + "' should be in order")
                .that(subSectionName)
                .isEqualTo(getSubSectionName(subSectionPos));

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
            assertWithMessage(fileName + " section '" + sectionName
                    + "' should contain up to 'Package' sub-section")
                    .that(subSectionPos)
                    .isGreaterThan(5);
        }
        else {
            assertWithMessage(fileName + " section '" + sectionName
                    + "' should contain up to 'Parent' sub-section")
                    .that(subSectionPos)
                    .isGreaterThan(6);
        }
    }

    private static void validateSinceDescriptionSection(String fileName, String sectionName,
            Node subSection) {
        assertWithMessage(fileName + " section '" + sectionName
                    + "' should have a valid version at the start of the description like:\n"
                    + DESCRIPTION_VERSION.pattern())
                .that(DESCRIPTION_VERSION.matcher(subSection.getTextContent().trim()).find())
                .isTrue();
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
            assertWithMessage(fileName + " section '" + sectionName
                    + "' should have no properties to show")
                .that(properties)
                .isNotEmpty();

            final Set<Node> nodes = XmlUtil.getChildrenElements(subSection);
            assertWithMessage(fileName + " section '" + sectionName
                    + "' subsection 'Properties' should have one child node")
                .that(nodes)
                .hasSize(1);

            final Node div = nodes.iterator().next();
            assertWithMessage(fileName + " section '" + sectionName
                        + "' subsection 'Properties' has unexpected child node")
                .that(div.getNodeName())
                .isEqualTo("div");
            final String wrapperMessage = fileName + " section '" + sectionName
                    + "' subsection 'Properties' wrapping div for table needs the"
                    + " class 'wrapper'";
            assertWithMessage(wrapperMessage)
                    .that(div.hasAttributes())
                    .isTrue();
            assertWithMessage(wrapperMessage)
                .that(div.getAttributes().getNamedItem("class").getNodeValue())
                .isNotNull();
            assertWithMessage(wrapperMessage)
                    .that(div.getAttributes().getNamedItem("class").getNodeValue())
                    .contains("wrapper");

            final Node table = XmlUtil.getFirstChildElement(div);
            assertWithMessage(fileName + " section '" + sectionName
                    + "' subsection 'Properties' has unexpected child node")
                .that(table.getNodeName())
                .isEqualTo("table");

            validatePropertySectionPropertiesOrder(fileName, sectionName, table, properties);

            validatePropertySectionProperties(fileName, sectionName, table, instance,
                    properties);
        }

        assertWithMessage(
                fileName + " section '" + sectionName + "' should show properties: " + properties)
            .that(properties)
            .isEmpty();
    }

    private static void validatePropertySectionPropertiesOrder(String fileName, String sectionName,
                                                               Node table, Set<String> properties) {
        final Set<Node> rows = XmlUtil.getChildrenElements(table);
        final List<String> orderedPropertyNames = new ArrayList<>(properties);
        final List<String> tablePropertyNames = new ArrayList<>();

        // javadocTokens and tokens should be last
        if (orderedPropertyNames.contains("javadocTokens")) {
            orderedPropertyNames.remove("javadocTokens");
            orderedPropertyNames.add("javadocTokens");
        }
        if (orderedPropertyNames.contains("tokens")) {
            orderedPropertyNames.remove("tokens");
            orderedPropertyNames.add("tokens");
        }

        rows
            .stream()
            // First row is header row
            .skip(1)
            .forEach(row -> {
                final List<Node> columns = new ArrayList<>(XmlUtil.getChildrenElements(row));
                assertWithMessage(fileName + " section '" + sectionName
                        + "' should have the requested columns")
                    .that(columns)
                    .hasSize(5);

                final String propertyName = columns.get(0).getTextContent();
                tablePropertyNames.add(propertyName);
            });

        assertWithMessage(fileName + " section '" + sectionName
                + "' should have properties in the requested order")
            .that(tablePropertyNames)
            .isEqualTo(orderedPropertyNames);
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

            assertWithMessage(fileName + " section '" + sectionName
                    + "' should have the requested columns")
                .that(columns)
                .hasSize(5);

            if (skip) {
                assertWithMessage(fileName + " section '" + sectionName
                                + "' should have the specific title")
                    .that(columns.get(0).getTextContent())
                    .isEqualTo("name");
                assertWithMessage(fileName + " section '" + sectionName
                                + "' should have the specific title")
                    .that(columns.get(1).getTextContent())
                    .isEqualTo("description");
                assertWithMessage(fileName + " section '" + sectionName
                                + "' should have the specific title")
                    .that(columns.get(2).getTextContent())
                    .isEqualTo("type");
                assertWithMessage(fileName + " section '" + sectionName
                                + "' should have the specific title")
                    .that(columns.get(3).getTextContent())
                    .isEqualTo("default value");
                assertWithMessage(fileName + " section '" + sectionName
                                + "' should have the specific title")
                    .that(columns.get(4).getTextContent())
                    .isEqualTo("since");

                skip = false;
                continue;
            }

            assertWithMessage(fileName + " section '" + sectionName
                        + "' should have token properties last")
                    .that(didTokens)
                    .isFalse();

            final String propertyName = columns.get(0).getTextContent();
            assertWithMessage(fileName + " section '" + sectionName
                        + "' should not contain the property: " + propertyName)
                    .that(properties.remove(propertyName))
                    .isTrue();

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
                assertWithMessage(fileName + " section '" + sectionName
                        + "' should have javadoc token properties next to last, before tokens")
                                .that(didJavadocTokens)
                                .isFalse();

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
        assertWithMessage("%s section '%s' should have a description for %s"
                        + " that starts with uppercase character",
                        fileName, sectionName, propertyName)
                .that(Character.isUpperCase(columns.get(1).getTextContent().trim().charAt(0)))
                .isTrue();

        final String actualTypeName = columns.get(2).getTextContent().replace("\n", "")
                .replace("\r", "").replaceAll(" +", " ").trim();

        assertWithMessage(
                fileName + " section '" + sectionName + "' should have a type for " + propertyName)
                        .that(actualTypeName)
                        .isNotEmpty();

        final Field field = getField(instance.getClass(), propertyName);
        final Class<?> fieldClass = getFieldClass(fileName, sectionName, instance, field,
                propertyName);

        final String expectedTypeName = Optional.ofNullable(field)
                .map(nonNullField -> nonNullField.getAnnotation(XdocsPropertyType.class))
                .map(propertyType -> propertyType.value().getDescription())
                .orElse(fieldClass.getSimpleName());
        final String expectedValue = getModulePropertyExpectedValue(sectionName, propertyName,
                field, fieldClass, instance);

        assertWithMessage(fileName + " section '" + sectionName
                        + "' should have the type for " + propertyName)
            .that(actualTypeName)
            .isEqualTo(expectedTypeName);

        if (expectedValue != null) {
            final String actualValue = columns.get(3).getTextContent().trim()
                    .replaceAll("\\s+", " ")
                    .replaceAll("\\s,", ",");

            assertWithMessage(fileName + " section '" + sectionName
                            + "' should have the value for " + propertyName)
                .that(actualValue)
                .isEqualTo(expectedValue);
        }
    }

    private static void validatePropertySectionPropertyTokens(String fileName, String sectionName,
            AbstractCheck check, List<Node> columns) {
        assertWithMessage(fileName + " section '" + sectionName
                        + "' should have the basic token description")
            .that(columns.get(1).getTextContent())
            .isEqualTo("tokens to check");

        final String acceptableTokenText = columns.get(2).getTextContent().trim();
        String expectedAcceptableTokenText = "subset of tokens "
                + CheckUtil.getTokenText(check.getAcceptableTokens(),
                check.getRequiredTokens());
        if (isAllTokensAcceptable(check)) {
            expectedAcceptableTokenText = "set of any supported tokens";
        }
        assertWithMessage(fileName + " section '" + sectionName
                        + "' should have all the acceptable tokens")
            .that(acceptableTokenText
                        .replaceAll("\\s+", " ")
                        .replaceAll("\\s,", ",")
                        .replaceAll("\\s\\.", "."))
            .isEqualTo(expectedAcceptableTokenText);
        assertWithMessage(fileName + "'s acceptable token section: " + sectionName
                + "should have ',' & '.' at beginning of the next corresponding lines.")
                        .that(isInvalidTokenPunctuation(acceptableTokenText))
                        .isFalse();

        final String defaultTokenText = columns.get(3).getTextContent().trim();
        final String expectedDefaultTokenText = CheckUtil.getTokenText(check.getDefaultTokens(),
                check.getRequiredTokens());
        if (expectedDefaultTokenText.isEmpty()) {
            assertWithMessage("Empty tokens should have 'empty' string in xdoc")
                .that(defaultTokenText)
                .isEqualTo("empty");
        }
        else {
            assertWithMessage(fileName + " section '" + sectionName
                    + "' should have all the default tokens")
                .that(defaultTokenText
                            .replaceAll("\\s+", " ")
                            .replaceAll("\\s,", ",")
                            .replaceAll("\\s\\.", "."))
                .isEqualTo(expectedDefaultTokenText);
            assertWithMessage(fileName + "'s default token section: " + sectionName
                    + "should have ',' or '.' at beginning of the next corresponding lines.")
                            .that(isInvalidTokenPunctuation(defaultTokenText))
                            .isFalse();
        }

    }

    private static boolean isAllTokensAcceptable(AbstractCheck check) {
        return Arrays.equals(check.getAcceptableTokens(), TokenUtil.getAllTokenIds());
    }

    private static void validatePropertySectionPropertyJavadocTokens(String fileName,
            String sectionName, AbstractJavadocCheck check, List<Node> columns) {
        assertWithMessage(fileName + " section '" + sectionName
                        + "' should have the basic token javadoc description")
            .that(columns.get(1).getTextContent())
            .isEqualTo("javadoc tokens to check");

        final String acceptableTokenText = columns.get(2).getTextContent().trim();
        assertWithMessage(fileName + " section '" + sectionName
                        + "' should have all the acceptable javadoc tokens")
            .that(acceptableTokenText
                        .replaceAll("\\s+", " ")
                        .replaceAll("\\s,", ",")
                        .replaceAll("\\s\\.", "."))
            .isEqualTo("subset of javadoc tokens "
                        + CheckUtil.getJavadocTokenText(check.getAcceptableJavadocTokens(),
                check.getRequiredJavadocTokens()));
        assertWithMessage(fileName + "'s acceptable javadoc token section: " + sectionName
                + "should have ',' & '.' at beginning of the next corresponding lines.")
                        .that(isInvalidTokenPunctuation(acceptableTokenText))
                        .isFalse();

        final String defaultTokenText = columns.get(3).getTextContent().trim();
        assertWithMessage(fileName + " section '" + sectionName
                        + "' should have all the default javadoc tokens")
            .that(defaultTokenText
                        .replaceAll("\\s+", " ")
                        .replaceAll("\\s,", ",")
                        .replaceAll("\\s\\.", "."))
            .isEqualTo(CheckUtil.getJavadocTokenText(check.getDefaultJavadocTokens(),
                check.getRequiredJavadocTokens()));
        assertWithMessage(fileName + "'s default javadoc token section: " + sectionName
                + "should have ',' & '.' at beginning of the next corresponding lines.")
                        .that(isInvalidTokenPunctuation(defaultTokenText))
                        .isFalse();
    }

    private static boolean isInvalidTokenPunctuation(String tokenText) {
        return Pattern.compile("\\w,").matcher(tokenText).find()
                || Pattern.compile("\\w\\.").matcher(tokenText).find();
    }

    /**
     * Gets the name of the bean property's default value for the class.
     *
     * @param sectionName The name of the section/module being worked on
     * @param propertyName The property name to work with
     * @param field The bean property's field
     * @param fieldClass The bean property's type
     * @param instance The class instance to work with
     * @return String form of property's default value
     * @noinspection IfStatementWithTooManyBranches
     * @noinspectionreason IfStatementWithTooManyBranches - complex nature of getting properties
     *      from XML files requires giant if/else statement
     */
    private static String getModulePropertyExpectedValue(String sectionName, String propertyName,
            Field field, Class<?> fieldClass, Object instance) throws Exception {
        String result = null;

        if (field != null) {
            final Object value = field.get(instance);

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
                result = "the charset property of the parent"
                    + " <a href=\"https://checkstyle.org/config.html#Checker\">Checker</a> module";
            }
            else if ("PropertyCacheFile".equals(fieldClass.getSimpleName())) {
                result = "null (no cache file)";
            }
            else if (fieldClass == boolean.class) {
                result = value.toString();
            }
            else if (fieldClass == int.class) {
                result = value.toString();
            }
            else if (fieldClass == int[].class) {
                result = getIntArrayPropertyValue(value);
            }
            else if (fieldClass == double[].class) {
                result = Arrays.toString((double[]) value).replace("[", "").replace("]", "")
                        .replace(".0", "");
                if (result.isEmpty()) {
                    result = "{}";
                }
            }
            else if (fieldClass == String[].class) {
                result = getStringArrayPropertyValue(propertyName, value);
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
                result = getPatternArrayPropertyValue(value);
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
                assertWithMessage("Unknown property type: " + fieldClass.getSimpleName()).fail();
            }

            if (result == null) {
                result = "null";
            }
        }

        return result;
    }

    /**
     * Gets the name of the bean property's default value for the Pattern array class.
     *
     * @param fieldValue The bean property's value
     * @return String form of property's default value
     */
    private static String getPatternArrayPropertyValue(Object fieldValue) {
        Object value = fieldValue;
        String result;
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

            result = Arrays.toString(newArray).replace("[", "").replace("]", "");
        }
        else {
            result = "";
        }

        if (result.isEmpty()) {
            result = "{}";
        }
        return result;
    }

    /**
     * Gets the name of the bean property's default value for the string array class.
     *
     * @param propertyName The bean property's name
     * @param value The bean property's value
     * @return String form of property's default value
     */
    private static String getStringArrayPropertyValue(String propertyName, Object value) {
        String result;
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
        return result;
    }

    /**
     * Returns the name of the bean property's default value for the int array class.
     *
     * @param value The bean property's value.
     * @return String form of property's default value.
     */
    private static String getIntArrayPropertyValue(Object value) {
        final IntStream stream;
        if (value instanceof Collection) {
            final Collection<?> collection = (Collection<?>) value;
            stream = collection.stream()
                    .mapToInt(number -> (int) number);
        }
        else if (value instanceof BitSet) {
            stream = ((BitSet) value).stream();
        }
        else {
            stream = Arrays.stream((int[]) value);
        }
        String result = stream
                .mapToObj(TokenUtil::getTokenName)
                .sorted()
                .collect(Collectors.joining(", "));
        if (result.isEmpty()) {
            result = "{}";
        }
        return result;
    }

    /**
     * Returns the bean property's field.
     *
     * @param fieldClass The bean property's type
     * @param propertyName The bean property's name
     * @return the bean property's field
     */
    private static Field getField(Class<?> fieldClass, String propertyName) {
        Field result = null;
        Class<?> currentClass = fieldClass;

        while (!Object.class.equals(currentClass)) {
            try {
                result = currentClass.getDeclaredField(propertyName);
                result.trySetAccessible();
                break;
            }
            catch (NoSuchFieldException ignored) {
                currentClass = currentClass.getSuperclass();
            }
        }

        return result;
    }

    private static Class<?> getFieldClass(String fileName, String sectionName, Object instance,
            Field field, String propertyName) throws Exception {
        Class<?> result = null;

        if (PROPERTIES_ALLOWED_GET_TYPES_FROM_METHOD.contains(sectionName + "." + propertyName)) {
            final PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(instance,
                    propertyName);
            result = descriptor.getPropertyType();
        }
        if (field != null && result == null) {
            result = field.getType();
        }
        if (result == null) {
            assertWithMessage(
                    fileName + " section '" + sectionName + "' could not find field "
                            + propertyName)
                    .fail();
        }
        if (field != null && (result == List.class || result == Set.class)) {
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
                assertWithMessage("Unknown parameterized type: " + parameterClass.getSimpleName())
                        .fail();
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
                    .collect(Collectors.toUnmodifiableSet());
        }
        return result;
    }

    private static void validateViolationSection(String fileName, String sectionName,
                                                 Node subSection,
                                                 Object instance) throws Exception {
        final Class<?> clss = instance.getClass();
        final Set<Field> fields = CheckUtil.getCheckMessages(clss, true);
        final Set<String> list = new TreeSet<>();

        for (Field field : fields) {
            // below is required for package/private classes
            field.trySetAccessible();

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
            assertWithMessage(fileName + " section '" + sectionName
                    + "' should have the expected error keys")
                .that(expectedText.toString())
                .isEqualTo("");
        }
        else {
            final String subsectionTextContent = subSection.getTextContent()
                    .replaceAll("\n\\s+", "\n")
                    .replaceAll("\\s+", " ")
                    .trim();
            assertWithMessage(fileName + " section '" + sectionName
                            + "' should have the expected error keys")
                .that(subsectionTextContent)
                .isEqualTo(expectedText.toString().replaceAll("\n", " ").trim());

            for (Node node : XmlUtil.findChildElementsByTag(subSection, "a")) {
                final String url = node.getAttributes().getNamedItem("href").getTextContent();
                final String linkText = node.getTextContent().trim();
                final String expectedUrl;

                if ("see the documentation".equals(linkText)) {
                    expectedUrl = "../../config.html#Custom_messages";
                }
                else {
                    expectedUrl = "https://github.com/search?q="
                            + "path%3Asrc%2Fmain%2Fresources%2F"
                            + clss.getPackage().getName().replace(".", "%2F")
                            + "%20path%3A**%2Fmessages*.properties+repo%3Acheckstyle%2F"
                            + "checkstyle+%22" + linkText + "%22";
                }

                assertWithMessage(fileName + " section '" + sectionName
                        + "' should have matching url for '" + linkText + "'")
                    .that(url)
                    .isEqualTo(expectedUrl);
            }
        }
    }

    private static void validateUsageExample(String fileName, String sectionName, Node subSection) {
        final String text = subSection.getTextContent().replace("Checkstyle Style", "")
                .replace("Google Style", "").replace("Sun Style", "").trim();

        assertWithMessage(fileName + " section '" + sectionName
                + "' has unknown text in 'Example of Usage': " + text)
            .that(text)
            .isEmpty();

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
                        + "path%3Aconfig%20path%3A**%2Fcheckstyle-checks.xml+"
                        + "repo%3Acheckstyle%2Fcheckstyle+" + sectionName;
            }
            else if ("Google Style".equals(linkText)) {
                hasGoogle = true;
                expectedUrl = "https://github.com/search?q="
                        + "path%3Asrc%2Fmain%2Fresources%20path%3A**%2Fgoogle_checks.xml+"
                        + "repo%3Acheckstyle%2Fcheckstyle+"
                        + sectionName;

                assertWithMessage(fileName + " section '" + sectionName
                            + "' should be in google_checks.xml or not reference 'Google Style'")
                        .that(GOOGLE_MODULES)
                        .contains(sectionName);
            }
            else if ("Sun Style".equals(linkText)) {
                hasSun = true;
                expectedUrl = "https://github.com/search?q="
                        + "path%3Asrc%2Fmain%2Fresources%20path%3A**%2Fsun_checks.xml+"
                        + "repo%3Acheckstyle%2Fcheckstyle+"
                        + sectionName;

                assertWithMessage(fileName + " section '" + sectionName
                            + "' should be in sun_checks.xml or not reference 'Sun Style'")
                        .that(SUN_MODULES)
                        .contains(sectionName);
            }

            assertWithMessage(fileName + " section '" + sectionName
                    + "' should have matching url")
                .that(url)
                .isEqualTo(expectedUrl);
        }

        assertWithMessage(fileName + " section '" + sectionName
                    + "' should have a checkstyle section")
                .that(hasCheckstyle)
                .isTrue();
        assertWithMessage(fileName + " section '" + sectionName
                    + "' should have a google section since it is in it's config")
                .that(hasGoogle || !GOOGLE_MODULES.contains(sectionName))
                .isTrue();
        assertWithMessage(fileName + " section '" + sectionName
                    + "' should have a sun section since it is in it's config")
                .that(hasSun || !SUN_MODULES.contains(sectionName))
                .isTrue();
    }

    private static void validatePackageSection(String fileName, String sectionName,
            Node subSection, Object instance) {
        assertWithMessage(fileName + " section '" + sectionName
                        + "' should have matching package")
            .that(subSection.getTextContent().trim())
            .isEqualTo(instance.getClass().getPackage().getName());
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

        assertWithMessage(fileName + " section '" + sectionName + "' should have matching parent")
            .that(subSection.getTextContent().trim())
            .isEqualTo(expected);
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
            final String input = Files.readString(path);
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
                    assertWithMessage("Missing modules list for style file '" + fileName + "'")
                            .fail();
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
            styleChecks.remove("SuppressWithNearbyCommentFilter");
            styleChecks.remove("SuppressionCommentFilter");
            styleChecks.remove("SuppressWarningsFilter");
            styleChecks.remove("SuppressWarningsHolder");
            styleChecks.remove("SuppressWithNearbyTextFilter");

            assertWithMessage(
                    fileName + " requires the following check(s) to appear: " + styleChecks)
                .that(styleChecks)
                .isEmpty();
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
                    // equal up to here and last rule has fewer parts,
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
                    assertWithMessage(outOfOrderReason)
                        .that(numericRuleNumberPart)
                        .isAtLeast(numericLastRuleNumberPart);
                }
                else {
                    assertWithMessage(outOfOrderReason)
                        .that(ruleNumberPart.compareToIgnoreCase(lastRuleNumberPart))
                        .isAtLeast(0);
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
                    assertWithMessage(fileName + " rule '" + ruleName + "' and rule '"
                            + lastRuleName + "' have the same rule number").fail();
                }
                else {
                    assertWithMessage(outOfOrderReason).fail();
                }
            }
        }

        return ruleNumberParts;
    }

    private static void validateStyleAnchors(Set<Node> anchors, String fileName, String ruleName) {
        assertWithMessage(fileName + " rule '" + ruleName + "' must have two row anchors")
            .that(anchors)
            .hasSize(2);

        final int space = ruleName.indexOf(' ');
        assertWithMessage(fileName + " rule '" + ruleName
                + "' must have have a space between the rule's number and the rule's name")
            .that(space)
            .isNotEqualTo(-1);

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

            assertWithMessage(fileName + " rule '" + ruleName + "' anchor "
                    + position + " should have matching name/url")
                .that(actualUrl)
                .isEqualTo(expectedUrl);

            position++;
        }
    }

    private static void validateStyleModules(Set<Node> checks, Set<Node> configs,
            Set<String> styleChecks, String styleName, String ruleName) {
        final Iterator<Node> itrChecks = checks.iterator();
        final Iterator<Node> itrConfigs = configs.iterator();
        final boolean isGoogleDocumentation = "google".equals(styleName);

        if (isGoogleDocumentation) {
            validateChapterWiseTesting(itrChecks, itrConfigs, styleChecks, styleName, ruleName);
        }
        else {
            validateModuleWiseTesting(itrChecks, itrConfigs, styleChecks, styleName, ruleName);
        }

        assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' has too many configs")
                .that(itrConfigs.hasNext())
                .isFalse();
    }

    private static void validateModuleWiseTesting(Iterator<Node> itrChecks,
          Iterator<Node> itrConfigs, Set<String> styleChecks, String styleName, String ruleName) {
        while (itrChecks.hasNext()) {
            final Node module = itrChecks.next();
            final String moduleName = module.getTextContent().trim();
            final String href = module.getAttributes().getNamedItem("href").getTextContent();
            final boolean moduleIsCheck = href.startsWith("checks/");

            if (!moduleIsCheck) {
                continue;
            }

            assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' module '" + moduleName
                        + "' shouldn't end with 'Check'")
                    .that(moduleName.endsWith("Check"))
                    .isFalse();

            styleChecks.remove(moduleName);

            for (String configName : new String[] {"config", "test"}) {
                Node config = null;

                try {
                    config = itrConfigs.next();
                }
                catch (NoSuchElementException ignore) {
                    assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' module '"
                            + moduleName + "' is missing the config link: " + configName).fail();
                }

                assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' module '"
                                + moduleName + "' has mismatched config/test links")
                    .that(config.getTextContent().trim())
                    .isEqualTo(configName);

                final String configUrl = config.getAttributes().getNamedItem("href")
                        .getTextContent();

                if ("config".equals(configName)) {
                    final String expectedUrl = "https://github.com/search?q="
                            + "path%3Asrc%2Fmain%2Fresources%20path%3A**%2F" + styleName
                            + "_checks.xml+repo%3Acheckstyle%2Fcheckstyle+" + moduleName;

                    assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' module '"
                                    + moduleName + "' should have matching " + configName + " url")
                        .that(configUrl)
                        .isEqualTo(expectedUrl);
                }
                else if ("test".equals(configName)) {
                    assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' module '"
                                + moduleName + "' should have matching " + configName + " url")
                            .that(configUrl)
                            .startsWith("https://github.com/checkstyle/checkstyle/"
                                    + "blob/master/src/it/java/com/" + styleName
                                    + "/checkstyle/test/");
                    assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' module '"
                                + moduleName + "' should have matching " + configName + " url")
                            .that(configUrl)
                            .endsWith("/" + moduleName + "Test.java");

                    assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' module '"
                                + moduleName + "' should have a test that exists")
                            .that(new File(configUrl.substring(53).replace('/',
                                            File.separatorChar)).exists())
                            .isTrue();
                }
            }
        }
    }

    private static void validateChapterWiseTesting(Iterator<Node> itrChecks,
          Iterator<Node> itrSample, Set<String> styleChecks, String styleName, String ruleName) {
        boolean hasChecks = false;
        final Set<String> usedModules = new HashSet<>();

        while (itrChecks.hasNext()) {
            final Node module = itrChecks.next();
            final String moduleName = module.getTextContent().trim();
            final String href = module.getAttributes().getNamedItem("href").getTextContent();
            final boolean moduleIsCheck = href.startsWith("checks/");

            final String partialConfigUrl = "https://github.com/search?q="
                    + "path%3Asrc%2Fmain%2Fresources%20path%3A**%2F" + styleName;

            if (!moduleIsCheck) {
                if (href.startsWith(partialConfigUrl)) {
                    assertWithMessage("google_style.xml rule '" + ruleName + "' module '"
                            + moduleName + "' has too many config links").fail();
                }
                continue;
            }

            hasChecks = true;

            assertWithMessage("The module '" + moduleName + "' in the rule '" + ruleName
                    + "' of the style guide '" + styleName
                    + "_style.xml' should not appear more than once in the section.")
                    .that(usedModules)
                    .doesNotContain(moduleName);

            usedModules.add(moduleName);

            assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' module '"
                    + moduleName + "' shouldn't end with 'Check'")
                    .that(moduleName.endsWith("Check"))
                    .isFalse();

            styleChecks.remove(moduleName);

            if (itrChecks.hasNext()) {
                final Node config = itrChecks.next();

                final String configUrl = config.getAttributes()
                                       .getNamedItem("href").getTextContent();

                final String expectedUrl =
                    partialConfigUrl + "_checks.xml+repo%3Acheckstyle%2Fcheckstyle+" + moduleName;

                assertWithMessage(
                        "google_style.xml rule '" + ruleName + "' module '" + moduleName
                            + "' should have matching config url")
                    .that(configUrl)
                    .isEqualTo(expectedUrl);
            }
            else {
                assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' module '"
                        + moduleName + "' is missing the config link").fail();
            }
        }

        if (itrSample.hasNext()) {
            assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' should have checks"
                    + " if it has sample links")
                    .that(hasChecks)
                    .isTrue();

            final Node sample = itrSample.next();
            final String inputFolderUrl = sample.getAttributes().getNamedItem("href")
                    .getTextContent();
            final String extractedChapterNumber = getExtractedChapterNumber(ruleName);
            final String extractedSectionNumber = getExtractedSectionNumber(ruleName);

            assertWithMessage("google_style.xml rule '" + ruleName + "' rule '"
                    + "' should have matching sample url")
                    .that(inputFolderUrl)
                    .startsWith("https://github.com/checkstyle/checkstyle/"
                            + "tree/master/src/it/resources/com/google/checkstyle/test/");

            assertWithMessage("google_style.xml rule '" + ruleName
                    + "' should have matching sample url")
                .that(inputFolderUrl)
                .containsMatch(
                    "/chapter" + extractedChapterNumber
                          + "\\D[^/]+/rule" + extractedSectionNumber + "\\D");

            assertWithMessage("google_style.xml rule '" + ruleName
                    + "' should have a inputs test folder that exists")
                    .that(new File(inputFolderUrl.substring(53).replace('/',
                            File.separatorChar)).exists())
                    .isTrue();

            assertWithMessage(styleName + "_style.xml rule '" + ruleName
                    + "' has too many samples link")
                    .that(itrSample.hasNext())
                    .isFalse();
        }
        else {
            assertWithMessage(styleName + "_style.xml rule '" + ruleName + "' is missing"
                 + " sample link")
                .that(hasChecks)
                .isFalse();
        }
    }

    private static String getExtractedChapterNumber(String ruleName) {
        final Pattern pattern = Pattern.compile("^\\d+");
        final Matcher matcher = pattern.matcher(ruleName);
        matcher.find();
        return matcher.group();
    }

    private static String getExtractedSectionNumber(String ruleName) {
        final Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)*");
        final Matcher matcher = pattern.matcher(ruleName);
        matcher.find();
        return matcher.group().replaceAll("\\.", "");
    }

    @Test
    public void testAllExampleMacrosHaveParagraphWithIdBeforeThem() throws Exception {
        for (Path path : XdocUtil.getXdocsTemplatesFilePaths()) {
            final String fileName = path.getFileName().toString();
            final String input = Files.readString(path);
            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("macro");

            for (int position = 0; position < sources.getLength(); position++) {
                final Node macro = sources.item(position);
                final String macroName = macro.getAttributes()
                        .getNamedItem("name").getTextContent();

                if (!"example".equals(macroName)) {
                    continue;
                }

                final Node precedingParagraph = getPrecedingParagraph(macro);
                assertWithMessage(fileName
                        + ": paragraph before example macro should have an id attribute")
                        .that(precedingParagraph.hasAttributes())
                        .isTrue();

                final Node idAttribute = precedingParagraph.getAttributes().getNamedItem("id");
                assertWithMessage(fileName
                        + ": paragraph before example macro should have an id attribute")
                        .that(idAttribute)
                        .isNotNull();

                validatePrecedingParagraphId(macro, fileName, idAttribute);
            }
        }
    }

    private static void validatePrecedingParagraphId(
            Node macro, String fileName, Node idAttribute) {
        String exampleName = "";
        String exampleType = "";
        final NodeList params = macro.getChildNodes();
        for (int paramPosition = 0; paramPosition < params.getLength(); paramPosition++) {
            final Node item = params.item(paramPosition);

            if (!"param".equals(item.getNodeName())) {
                continue;
            }

            final String paramName = item.getAttributes()
                    .getNamedItem("name").getTextContent();
            final String paramValue = item.getAttributes()
                    .getNamedItem("value").getTextContent();
            if ("path".equals(paramName)) {
                exampleName = paramValue.substring(paramValue.lastIndexOf('/') + 1,
                        paramValue.lastIndexOf('.'));
            }
            else if ("type".equals(paramName)) {
                exampleType = paramValue;
            }
        }

        final String id = idAttribute.getTextContent();
        final String expectedId = String.format(Locale.ROOT, "%s-%s", exampleName,
                exampleType);
        if (expectedId.startsWith("package-info")) {
            assertWithMessage(fileName
                + ": paragraph before example macro should have the expected id value")
                .that(id)
                .endsWith(expectedId);
        }
        else {
            assertWithMessage(fileName
                + ": paragraph before example macro should have the expected id value")
                .that(id)
                .isEqualTo(expectedId);
        }
    }

    private static Node getPrecedingParagraph(Node macro) {
        Node precedingNode = macro.getPreviousSibling();
        while (!"p".equals(precedingNode.getNodeName())) {
            precedingNode = precedingNode.getPreviousSibling();
        }
        return precedingNode;
    }
}
