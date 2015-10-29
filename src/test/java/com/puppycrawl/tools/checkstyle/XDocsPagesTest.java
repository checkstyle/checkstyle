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

import static java.nio.charset.StandardCharsets.UTF_8;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.io.Files;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

public class XDocsPagesTest {
    private static final File JAVA_SOURCES_DIRECTORY = new File("src/main/java");
    private static final File XDOCS_DIRECTORY = new File("src/xdocs");
    private static final String AVAILABLE_CHECKS_PATH = "src/xdocs/checks.xml";
    private static final File AVAILABLE_CHECKS_FILE = new File(AVAILABLE_CHECKS_PATH);
    private static final String CHECK_FILE_NAME = ".+Check.java$";
    private static final String CHECK_SUFFIX = "Check.java";
    private static final String LINK_TEMPLATE =
            "(?s).*<a href=\"config_\\w+\\.html#%1$s\">%1$s</a>.*";

    private static final List<String> CHECKS_ON_PAGE_IGNORE_LIST = Arrays.asList(
            "AbstractAccessControlNameCheck.java",
            "AbstractClassCouplingCheck.java",
            "AbstractComplexityCheck.java",
            "AbstractFileSetCheck.java",
            "AbstractFormatCheck.java",
            "AbstractHeaderCheck.java",
            "AbstractIllegalCheck.java",
            "AbstractIllegalMethodCheck.java",
            "AbstractJavadocCheck.java",
            "AbstractNameCheck.java",
            "AbstractNestedDepthCheck.java",
            "AbstractOptionCheck.java",
            "AbstractParenPadCheck.java",
            "AbstractSuperCheck.java",
            "AbstractTypeAwareCheck.java",
            "AbstractTypeParameterNameCheck.java",
            "FileSetCheck.java"
    );

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
            "name=\"RegexpHeader\"",
            "name=\"RegexpSingleline\"",
            "name=\"RegexpMultiline\"",
            "name=\"JavadocPackage\"",
            "name=\"NewlineAtEndOfFile\"",
            "name=\"UniqueProperties\"",
            "name=\"FileLength\"",
            "name=\"FileTabCharacter\""
    );

    private static final Set<String> CHECK_PROPERTIES = getProperties(Check.class);
    private static final Set<String> FILESET_PROPERTIES = getProperties(AbstractFileSetCheck.class);

    private static final List<String> UNDOCUMENTED_PROPERTIES = Arrays.asList(
            "SuppressWarningsCheck.compileFlags",
            "SuppressWithNearbyCommentFilter.fileContents",
            "IllegalTokenTextCheck.compileFlags",
            "ReturnCountCheck.compileFlags",
            "IllegalTypeCheck.compileFlags",
            "MutableExceptionCheck.compileFlags",
            "AbstractClassNameCheck.compileFlags",
            "ClassTypeParameterNameCheck.compileFlags",
            "ConstantNameCheck.compileFlags",
            "InterfaceTypeParameterNameCheck.compileFlags",
            "LocalFinalVariableNameCheck.compileFlags",
            "LocalVariableNameCheck.compileFlags",
            "MemberNameCheck.compileFlags",
            "MethodNameCheck.compileFlags",
            "MethodTypeParameterNameCheck.compileFlags",
            "ParameterNameCheck.compileFlags",
            "StaticVariableNameCheck.compileFlags",
            "TypeNameCheck.compileFlags",
            "SuppressionCommentFilter.fileContents",
            "MethodNameCheck.applyToPackage",
            "MethodNameCheck.applyToPrivate",
            "MethodNameCheck.applyToProtected",
            "MethodNameCheck.applyToPublic");

    @Test
    public void testAllChecksPresentOnAvailableChecksPage() throws IOException {
        final String availableChecks = Files.toString(AVAILABLE_CHECKS_FILE, UTF_8);
        for (File file : Files.fileTreeTraverser().preOrderTraversal(JAVA_SOURCES_DIRECTORY)) {
            final String fileName = file.getName();
            if (fileName.matches(CHECK_FILE_NAME)
                    && !CHECKS_ON_PAGE_IGNORE_LIST.contains(fileName)) {
                final String checkName = fileName.replace(CHECK_SUFFIX, "");
                if (!isPresent(availableChecks, checkName)) {
                    Assert.fail(checkName + " is not correctly listed on Available Checks page"
                            + " - add it to " + AVAILABLE_CHECKS_PATH);
                }
            }
        }
    }

    private static boolean isPresent(String availableChecks, String checkName) {
        final String linkPattern = String.format(Locale.ROOT, LINK_TEMPLATE, checkName);
        return availableChecks.matches(linkPattern);
    }

    @Test
    public void testAllXmlExamples() throws Exception {
        for (File file : Files.fileTreeTraverser().preOrderTraversal(XDOCS_DIRECTORY)) {
            if (file.isDirectory()) {
                continue;
            }

            final String input = Files.toString(file, UTF_8);
            final String fileName = file.getName();

            final Document document = getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("source");

            for (int position = 0; position < sources.getLength(); position++) {
                final String unserializedSource = sources.item(position).getTextContent()
                        .replace("...", "").trim();

                if (unserializedSource.charAt(0) != '<'
                        || unserializedSource.charAt(unserializedSource.length() - 1) != '>') {
                    continue;
                }

                // no dtd testing yet
                if (unserializedSource.contains("<!")) {
                    continue;
                }

                buildAndTestXml(fileName, unserializedSource);
            }
        }
    }

    private static void buildAndTestXml(String fileName, String unserializedSource)
            throws IOException, ParserConfigurationException {
        // not all examples come with the full xml structure
        String code = unserializedSource;

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

        // test only
        getRawXml(fileName, code, unserializedSource);

        // can't test ant structure, or old and outdated checks
        if (!fileName.startsWith("anttask") && !fileName.startsWith("releasenotes")) {
            testCheckstyleXml(fileName, code, unserializedSource);
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

    private static Document getRawXml(String fileName, String code, String unserializedSource)
            throws ParserConfigurationException {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);

            final DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(new InputSource(new StringReader(code)));
        }
        catch (IOException | SAXException e) {
            Assert.fail(fileName + " has invalid xml (" + e.getMessage() + "): "
                    + unserializedSource);
        }

        return null;
    }

    private static void testCheckstyleXml(String fileName, String code, String unserializedSource)
            throws IOException {
        // can't process non-existent examples, or out of context snippets
        if (code.contains("com.mycompany") || code.contains("checkstyle-packages")
                || code.contains("MethodLimit") || code.contains("<suppress ")
                || code.contains("<import-control ") || unserializedSource.startsWith("<property ")
                || unserializedSource.startsWith("<taskdef ")) {
            return;
        }

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
        catch (CheckstyleException e) {
            Assert.fail(fileName + " has invalid Checkstyle xml (" + e.getMessage() + "): "
                    + unserializedSource);
        }
    }

    @Test
    public void testAllCheckSections() throws Exception {
        final ClassLoader cl = XDocsPagesTest.class.getClassLoader();
        final Set<String> packageNames = PackageNamesLoader.getPackageNames(cl);
        final ModuleFactory moduleFactory = new PackageObjectFactory(packageNames, cl);

        for (File file : Files.fileTreeTraverser().preOrderTraversal(XDOCS_DIRECTORY)) {
            final String fileName = file.getName();

            if (!fileName.startsWith("config_") || "config_reporting.xml".equals(fileName)) {
                continue;
            }

            final String input = Files.toString(file, UTF_8);
            final Document document = getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("section");

            for (int position = 0; position < sources.getLength(); position++) {
                final Node section = sources.item(position);
                final String sectionName = section.getAttributes().getNamedItem("name")
                        .getNodeValue();

                if ("Content".equals(sectionName) || "Overview".equals(sectionName)) {
                    continue;
                }

                Assert.assertTrue(fileName + " section '" + sectionName
                        + "' shouldn't end with 'Check'", !sectionName.endsWith("Check"));

                validateCheckSection(moduleFactory, fileName, sectionName, section);
            }
        }
    }

    private static void validateCheckSection(ModuleFactory moduleFactory, String fileName,
            String sectionName, Node section) {
        Object instance = null;

        try {
            instance = moduleFactory.createModule(sectionName);
        }
        catch (CheckstyleException e) {
            Assert.fail(fileName + " couldn't find class: " + sectionName);
        }

        int subSectionPos = 0;
        for (Node subSection : getChildrenElements(section)) {
            final String subSectionName = subSection.getAttributes().getNamedItem("name")
                    .getNodeValue();

            // can be in different orders, and completely optional
            if ("Notes".equals(subSectionName)
                    || "Rule Description".equals(subSectionName)) {
                continue;
            }

            if (subSectionPos == 1 && !"Properties".equals(subSectionName)) {
                validatePropertySection(fileName, sectionName, null, instance);
                subSectionPos++;
            }

            Assert.assertEquals(fileName + " section '" + sectionName
                    + "' should be in order", getSubSectionName(subSectionPos),
                    subSectionName);

            switch (subSectionPos) {
                case 0:
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
                    validatePackageSection(fileName, sectionName, subSection, instance);
                    break;
                case 5:
                    validateParentSection(fileName, sectionName, subSection);
                    break;
                default:
                    break;
            }

            subSectionPos++;
        }
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
                result = "Package";
                break;
            case 5:
                result = "Parent Module";
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    private static void validatePropertySection(String fileName, String sectionName,
            Node subSection, Object instance) {
        final Set<String> properties = getProperties(instance.getClass());
        final Class<?> clss = instance.getClass();

        // remove global properties that don't need documentation
        if (hasParentModule(sectionName)) {
            properties.removeAll(CHECK_PROPERTIES);
        }
        else if (AbstractFileSetCheck.class.isAssignableFrom(clss)) {
            properties.removeAll(FILESET_PROPERTIES);

            // override
            properties.add("fileExtensions");
        }

        // remove undocumented properties
        for (String p : new HashSet<>(properties)) {
            if (UNDOCUMENTED_PROPERTIES.contains(clss.getSimpleName() + "." + p)) {
                properties.remove(p);
            }
        }

        final Check check;

        if (Check.class.isAssignableFrom(clss)) {
            check = (Check) instance;

            if (!Arrays.equals(check.getAcceptableTokens(), check.getDefaultTokens())
                    || !Arrays.equals(check.getAcceptableTokens(), check.getRequiredTokens())) {
                properties.add("tokens");
            }
        }
        else {
            check = null;
        }

        if (subSection != null) {
            Assert.assertTrue(fileName + " section '" + sectionName
                    + "' should have no properties to show", !properties.isEmpty());

            validatePropertySectionProperties(fileName, sectionName, subSection, check,
                    properties);
        }

        Assert.assertTrue(fileName + " section '" + sectionName + "' should show properties: "
                + properties, properties.isEmpty());
    }

    private static void validatePropertySectionProperties(String fileName, String sectionName,
            Node subSection, Check check, Set<String> properties) {
        boolean skip = true;
        boolean didTokens = false;

        for (Node row : getChildrenElements(getFirstChildElement(subSection))) {
            if (skip) {
                skip = false;
                continue;
            }
            Assert.assertFalse(fileName + " section '" + sectionName
                    + "' should have token properties last", didTokens);

            final List<Node> columns = new ArrayList<>(getChildrenElements(row));

            final String propertyName = columns.get(0).getTextContent();
            Assert.assertTrue(fileName + " section '" + sectionName
                    + "' should not contain the property: " + propertyName,
                    properties.remove(propertyName));

            if ("tokens".equals(propertyName)) {
                Assert.assertEquals(fileName + " section '" + sectionName
                        + "' should have the basic token description", "tokens to check",
                        columns.get(1).getTextContent());
                Assert.assertEquals(fileName + " section '" + sectionName
                        + "' should have all the acceptable tokens", "subset of tokens "
                        + getTokenText(check.getAcceptableTokens(), check.getRequiredTokens()),
                        columns.get(2).getTextContent().replaceAll("\\s+", " ").trim());
                Assert.assertEquals(fileName + " section '" + sectionName
                        + "' should have all the default tokens",
                        getTokenText(check.getDefaultTokens(), check.getRequiredTokens()),
                        columns.get(3).getTextContent().replaceAll("\\s+", " ").trim());
                didTokens = true;
            }
            else {
                Assert.assertFalse(fileName + " section '" + sectionName
                        + "' should have a description for " + propertyName, columns.get(1)
                        .getTextContent().trim().isEmpty());
                Assert.assertFalse(fileName + " section '" + sectionName
                        + "' should have a type for " + propertyName, columns.get(2)
                        .getTextContent().trim().isEmpty());
                // default can be empty string
            }
        }
    }

    private static void validateUsageExample(String fileName, String sectionName, Node subSection) {
        Assert.assertNull(fileName + " section '" + sectionName
                + "' should have no xml examples, they belong in 'Examples'",
                findChildElementByTag(subSection, "source"));
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

    private static Set<Node> getChildrenElements(Node node) {
        final Set<Node> result = new LinkedHashSet<>();

        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() != Node.TEXT_NODE) {
                result.add(child);
            }
        }

        return result;
    }

    private static Node getFirstChildElement(Node node) {
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() != Node.TEXT_NODE) {
                return child;
            }
        }

        return null;
    }

    private static Node findChildElementByTag(Node node, String tag) {
        Node result = null;

        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (tag.equals(child.getNodeName())) {
                result = child;
                break;
            }

            if (child.hasChildNodes()) {
                result = findChildElementByTag(child, tag);

                if (result != null) {
                    break;
                }
            }
        }

        return result;
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

    private static String getTokenText(int[] tokens, int... subtractions) {
        if (Arrays.equals(tokens, TokenUtils.getAllTokenIds()) && subtractions.length == 0) {
            return "TokenTypes.";
        }
        else {
            final StringBuilder result = new StringBuilder();
            boolean first = true;

            for (int token : tokens) {
                boolean found = false;

                for (int subtraction : subtractions) {
                    if (subtraction == token) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    continue;
                }

                if (first) {
                    first = false;
                }
                else {
                    result.append(", ");
                }

                result.append(TokenUtils.getTokenName(token));
            }

            if (result.length() == 0) {
                result.append("empty");
            }
            else {
                result.append(".");
            }

            return result.toString();
        }
    }
}
