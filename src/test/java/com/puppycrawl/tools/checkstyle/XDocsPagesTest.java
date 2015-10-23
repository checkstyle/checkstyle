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

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.io.Files;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

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
            "name=\"RegexpMultiline\"",
            "name=\"JavadocPackage\"",
            "name=\"NewlineAtEndOfFile\"",
            "name=\"UniqueProperties\"",
            "name=\"FileLength\"",
            "name=\"FileTabCharacter\""
    );

    @Test
    public void testAllChecksPresentOnAvailableChecksPage() throws IOException {
        final String availableChecks = Files.toString(AVAILABLE_CHECKS_FILE, UTF_8);
        for (File file : Files.fileTreeTraverser().preOrderTraversal(JAVA_SOURCES_DIRECTORY)) {
            final String fileName = file.getName();
            if (fileName.matches(CHECK_FILE_NAME) && !CHECKS_ON_PAGE_IGNORE_LIST.contains(fileName)) {
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

            final String source = Files.toString(file, UTF_8);
            int position = -1;

            while (true) {
                position = source.indexOf("<source>", position + 1);

                if (position == -1) {
                    break;
                }

                final int nextPosition = source.indexOf("</source>", position);
                final String unserializedSource = source.substring(position + 8, nextPosition)
                        .trim().replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"")
                        .replace("&amp;", "&").replace("...", "");

                position = nextPosition;

                if (unserializedSource.charAt(0) != '<'
                        || unserializedSource.charAt(unserializedSource.length() - 1) != '>') {
                    continue;
                }

                // no dtd testing yet
                if (unserializedSource.contains("<!")) {
                    continue;
                }

                buildAndTestXml(file.getName(), unserializedSource);
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

        testRawXml(fileName, code, unserializedSource);

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

    private static void testRawXml(String fileName, String code, String unserializedSource)
            throws ParserConfigurationException {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);

            final DocumentBuilder builder = factory.newDocumentBuilder();

            builder.parse(new InputSource(new StringReader(code)));
        }
        catch (IOException | SAXException e) {
            Assert.fail(fileName + " has invalid xml (" + e.getMessage() + "): "
                    + unserializedSource);
        }
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
}
