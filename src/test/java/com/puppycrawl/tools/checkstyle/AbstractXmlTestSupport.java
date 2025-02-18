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

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;
import com.puppycrawl.tools.checkstyle.internal.utils.ConfigurationUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;

public abstract class AbstractXmlTestSupport extends AbstractModuleTestSupport {

    /**
     * Returns the output stream xml.
     *
     * @param outputStream the byte array output stream.
     * @return returns a raw xml document of the interface {@link Document}.
     * @throws ParserConfigurationException can cause exception when parsing output stream.
     */
    protected static Document getOutputStreamXml(ByteArrayOutputStream outputStream)
            throws ParserConfigurationException {
        final String xml = outputStream.toString(StandardCharsets.UTF_8);

        return XmlUtil.getRawXml("audit output", xml, xml);
    }

    /**
     * Verifies the generated xml.
     *
     * @param expectedOutputFile the expected output file.
     * @param actualOutputStream the byte array output stream.
     * @param messages an array of expected messages/content of xml document.
     * @throws Exception can throw ComparisonFailure exception if xml encoding,
     *     version don't match.
     */
    protected static void verifyXml(String expectedOutputFile,
            ByteArrayOutputStream actualOutputStream, String... messages) throws Exception {
        verifyXml(expectedOutputFile, actualOutputStream, null, messages);
    }

    /**
     * Verifies the actual generated xml document by comparing with expected document.
     *
     * @param expectedOutputFile the expected output file.
     * @param actualOutputStream the actual byte array output stream.
     * @param ordered an ordered predicate for xml nodes.
     * @param messages an array of expected messages/content of xml document.
     * @throws Exception can throw ComparisonFailure exception if xml encoding,
     *     version do not match.
     */
    protected static void verifyXml(String expectedOutputFile,
            ByteArrayOutputStream actualOutputStream,
            BiPredicate<Node, Node> ordered, String... messages) throws Exception {
        String expectedContents = readFile(expectedOutputFile);

        for (int i = 0; i < messages.length; i++) {
            expectedContents = expectedContents.replace("$" + i, messages[i]);
        }

        final Document expectedDocument = XmlUtil.getRawXml("audit output", expectedContents,
                expectedContents);
        final Document actualDocument = getOutputStreamXml(actualOutputStream);

        assertWithMessage("xml encoding should be the same")
                .that(actualDocument.getXmlEncoding())
                .isEqualTo(expectedDocument.getXmlEncoding());

        assertWithMessage("xml version should be the same")
                .that(actualDocument.getXmlVersion())
                .isEqualTo(expectedDocument.getXmlVersion());

        verifyXmlNode(expectedDocument, actualDocument, "/", ordered);
    }

    /**
     * Verifies if xml nodes in actual xml document and expected xml document match or not.
     *
     * @param expected the expected xml node. A {@link Node} interface.
     * @param actual the actual xml node. A {@link Node} interface.
     * @param path the path to the current xml node that are compared.
     * @param ordered an ordered predicate for xml nodes.
     */
    private static void verifyXmlNodes(Node expected, Node actual, String path,
            BiPredicate<Node, Node> ordered) {
        final Node expectedFirstChild = expected.getFirstChild();
        final Node actualFirstChild = actual.getFirstChild();

        if (expectedFirstChild == null) {
            assertWithMessage("no children nodes should exist: %s", path)
                    .that(actualFirstChild)
                    .isNull();
            assertWithMessage("text should be the same: %s", path)
                    .that(actual.getNodeValue())
                    .isEqualTo(expected.getNodeValue());
        }
        else {
            assertWithMessage("children nodes should exist: %s", path)
                    .that(actualFirstChild)
                    .isNotNull();

            if (ordered == null) {
                Node actualChild = actualFirstChild;

                for (Node expectedChild = expectedFirstChild; expectedChild != null;
                        expectedChild = expectedChild.getNextSibling()) {
                    verifyXmlNode(expectedChild, actualChild, path, ordered);

                    actualChild = actualChild.getNextSibling();
                }

                assertWithMessage("node have same number of children: %s", path)
                        .that(actualChild)
                        .isNull();
            }
            else {
                final Set<Node> expectedChildren = XmlUtil.getChildrenElements(expected);
                final Set<Node> actualChildren = XmlUtil.getChildrenElements(actual);

                assertWithMessage("node have same number of children: %s", path)
                        .that(actualChildren)
                        .hasSize(expectedChildren.size());

                for (Node expectedChild : expectedChildren) {
                    Node foundChild = null;

                    for (Node actualChild : actualChildren) {
                        if (ordered.test(expectedChild, actualChild)) {
                            foundChild = actualChild;
                            break;
                        }
                    }

                    assertWithMessage("node should exist: %s%s/", path, expectedChild.getNodeName())
                            .that(foundChild)
                            .isNotNull();

                    verifyXmlNode(expectedChild, foundChild, path, ordered);
                }
            }
        }
    }

    /**
     * Verifies if a xml node in actual xml document and expected xml document match or not based on
     * their name, type, and attributes.
     *
     * @param expected the expected xml node. A {@link Node} interface.
     * @param actual the actual xml node. A {@link Node} interface.
     * @param path the path to the current xml nodes that are compared.
     * @param ordered an ordered predicate for xml nodes.
     */
    private static void verifyXmlNode(Node expected, Node actual, String path,
            BiPredicate<Node, Node> ordered) {
        if (expected == null) {
            if (actual != null) {
                assertWithMessage("no node should exist: %s%s/", path, actual.getNodeName())
                        .fail();
            }
        }
        else {
            final String newPath = path + expected.getNodeName() + "/";

            assertWithMessage("node should exist: %s", newPath)
                    .that(actual)
                    .isNotNull();
            assertWithMessage("node should have same name: %s", newPath)
                    .that(actual.getNodeName())
                    .isEqualTo(expected.getNodeName());
            assertWithMessage("node should have same type: %s", newPath)
                    .that(actual.getNodeType())
                    .isEqualTo(expected.getNodeType());

            verifyXmlAttributes(expected.getAttributes(), actual.getAttributes(), newPath);

            verifyXmlNodes(expected, actual, newPath, ordered);
        }
    }

    /**
     * Verifies xml attributes of collection of actual nodes by comparing with collection
     * of expected node attributes.
     *
     * @param expected collection of expected nodes. A {@link NamedNodeMap} interface.
     * @param actual collection of actual nodes. A {@link NamedNodeMap} interface.
     * @param path the path to these xml nodes that are compared.
     */
    private static void verifyXmlAttributes(NamedNodeMap expected, NamedNodeMap actual,
            String path) {
        if (expected == null) {
            assertWithMessage("no attributes should exist: %s", path)
                    .that(actual)
                    .isNull();
        }
        else {
            assertWithMessage("attributes should exist: %s", path)
                    .that(actual)
                    .isNotNull();

            for (int i = 0; i < expected.getLength(); i++) {
                verifyXmlAttribute(expected.item(i), actual.item(i), path);
            }

            assertThat(actual.getLength())
                    .isEqualTo(expected.getLength());
            assertWithMessage("node have same number of attributes: %s", path)
                    .that(actual.getLength())
                    .isEqualTo(expected.getLength());
        }
    }

    /**
     * Verifies xml attributes of actual node (like name, and node value) by comparing with
     * expected node attributes.
     *
     * @param expected the expected xml node. A {@link Node} interface.
     * @param actual the actual xml node. A {@link Node} interface.
     * @param path the path to the current xml nodes that are compared.
     */
    private static void verifyXmlAttribute(Node expected, Node actual, String path) {
        final String expectedName = expected.getNodeName();

        assertWithMessage("attribute value for '%s' should not be null: %s", expectedName, path)
                .that(actual)
                .isNotNull();

        assertWithMessage("attribute name should match: %s", path)
                .that(actual.getNodeName())
                .isEqualTo(expectedName);

        // ignore checkstyle version in xml as it changes each release
        if (!"/#document/checkstyle".equals(path) && !"version".equals(expectedName)) {
            assertWithMessage("attribute value for '%s' should match: %s", expectedName, path)
                    .that(actual.getNodeValue())
                    .isEqualTo(expected.getNodeValue());
        }
    }

    /**
     * Verifies XML output using inline configuration parser and XML logger.
     *
     * @param filePath the path to the test file
     * @param expectedXmlReportPath the path to the expected XML report
     * @throws Exception if an error occurs
     */
    protected final void verifyWithInlineConfigParserAndXmlLogger(
            String filePath, String expectedXmlReportPath) throws Exception {
        final String configFilePath = getPath(filePath);
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig = testInputConfiguration.createConfiguration();
        final String basePath = new File(getPath("")).getAbsolutePath();

        final Checker checker = createChecker(parsedConfig);
        checker.setBasedir(basePath);

        final ByteArrayOutputStream actualXmlOutput = new ByteArrayOutputStream();
        final XMLLogger logger = new XMLLogger(actualXmlOutput,
                AbstractAutomaticBean.OutputStreamOptions.CLOSE);
        checker.addListener(logger);
        final List<File> filesToCheck = Collections.singletonList(new File(configFilePath));
        checker.process(filesToCheck);

        verifyXml(getPath(expectedXmlReportPath), actualXmlOutput);
    }

    /**
     * Verifies the XML output of Checkstyle using a given XML configuration and expected report.
     * Loads the configuration, processes target files, and compares the output with the
     * expected XML.
     *
     * @param inputFileWithConfig the path of xml configuration file
     * @param expectedXmlReportPath the path to the expected XML report
     * @param targetFilePaths list of file paths to check
     * @throws Exception if an error occurs
     */
    protected final void verifyWithInlineConfigParserAndXmlLogger(
            String inputFileWithConfig, String expectedXmlReportPath,
            List<String> targetFilePaths) throws Exception {
        final String configFilePath = getPath(inputFileWithConfig);
        final Configuration configuration = ConfigurationUtil
                .loadConfiguration(configFilePath);
        final String basePath = new File(getPath("")).getAbsolutePath();
        final Checker checker = createChecker(configuration);
        checker.setBasedir(basePath);
        final ByteArrayOutputStream actualXmlOutput = new ByteArrayOutputStream();
        final XMLLogger logger = new XMLLogger(actualXmlOutput,
                AbstractAutomaticBean.OutputStreamOptions.CLOSE);
        checker.addListener(logger);

        final List<File> filesToCheck = new ArrayList<>();
        for (String path : targetFilePaths) {
            filesToCheck.add(new File(getPath(path)));
        }

        checker.process(filesToCheck);
        verifyXml(getPath(expectedXmlReportPath), actualXmlOutput);
    }
}
