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

package com.puppycrawl.tools.checkstyle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.BiPredicate;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;

public abstract class AbstractXmlTestSupport extends AbstractModuleTestSupport {

    /**
     * Returns the output stream xml. This implementation uses
     * {@link XmlUtil#getRawXml(String, String, String)} method inside to get xml doc.
     *
     * @param outputStream The byte array output stream.
     * @return returns a raw xml document of the interface {@link Document}.
     * @throws ParserConfigurationException can cause exception when parsing output stream.
     */
    protected static Document getOutputStreamXml(ByteArrayOutputStream outputStream)
            throws ParserConfigurationException {
        final String xml = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);

        return XmlUtil.getRawXml("audit output", xml, xml);
    }

    /**
     * Verifies the generated xml.
     *
     * @param expectedOutputFile The expected output file.
     * @param actualOutputStream The byte array output stream.
     * @param messages An array of expected messages/content of xml document.
     * @throws Exception can throw ComparisonFailure exception if xml encoding,
     *     version don't match.
     */
    protected static void verifyXml(String expectedOutputFile,
            ByteArrayOutputStream actualOutputStream, String... messages) throws Exception {
        verifyXml(expectedOutputFile, actualOutputStream, null, messages);
    }

    /**
     * Verifies the actual generated xml document by comparing with expected document.
     * This implementation uses {@link #getOutputStreamXml(ByteArrayOutputStream)},
     * {@link #verifyXmlNode(Node, Node, String, BiPredicate)} methods inside.
     *
     * @param expectedOutputFile The expected output file.
     * @param actualOutputStream The actual byte array output stream.
     * @param ordered an ordered predicate for xml nodes.
     * @param messages An array of expected messages/content of xml document.
     * @throws Exception can throw ComparisonFailure exception if xml encoding,
     *     version donot match.
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

        assertEquals(expectedDocument.getXmlEncoding(), actualDocument.getXmlEncoding(),
                "xml encoding should be the same");
        assertEquals(expectedDocument.getXmlVersion(), actualDocument.getXmlVersion(),
                "xml version should be the same");
        verifyXmlNode(expectedDocument, actualDocument, "/", ordered);
    }

    /**
     * Verifies if xml nodes in actual generated xml document and expected xml document match
     * or not. This implementation uses {@link #verifyXmlNode(Node, Node, String, BiPredicate)}
     * method inside.
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
            assertNull(actualFirstChild, "no children nodes should exist: " + path);
            assertEquals(expected.getNodeValue(), actual.getNodeValue(),
                    "text should be the same: " + path);
        }
        else {
            assertNotNull(actualFirstChild, "children nodes should exist: " + path);

            if (ordered == null) {
                Node actualChild = actualFirstChild;

                for (Node expectedChild = expectedFirstChild; expectedChild != null;
                        expectedChild = expectedChild.getNextSibling()) {
                    verifyXmlNode(expectedChild, actualChild, path, ordered);

                    actualChild = actualChild.getNextSibling();
                }

                assertNull(actualChild, "node have same number of children: " + path);
            }
            else {
                final Set<Node> expectedChildren = XmlUtil.getChildrenElements(expected);
                final Set<Node> actualChildren = XmlUtil.getChildrenElements(actual);

                assertEquals(expectedChildren.size(), actualChildren.size(),
                        "node have same number of children: " + path);

                for (Node expectedChild : expectedChildren) {
                    Node foundChild = null;

                    for (Node actualChild : actualChildren) {
                        if (ordered.test(expectedChild, actualChild)) {
                            foundChild = actualChild;
                            break;
                        }
                    }

                    assertNotNull(foundChild,
                            "node should exist: " + path + expectedChild.getNodeName() + "/");

                    verifyXmlNode(expectedChild, foundChild, path, ordered);
                }
            }
        }
    }

    /**
     * Verifies if a xml node in actual xml document and expected xml document match or not based on
     * their name, type, and attributes. This implementation uses
     * {@link #verifyXmlAttributes(NamedNodeMap, NamedNodeMap, String)}, and
     * {@link #verifyXmlNodes(Node, Node, String, BiPredicate)} methods inside.
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
                fail("no node should exist: " + path + actual.getNodeName() + "/");
            }
        }
        else {
            final String newPath = path + expected.getNodeName() + "/";

            assertNotNull(actual, "node should exist: " + newPath);
            assertEquals(expected.getNodeName(), actual.getNodeName(),
                    "node should have same name: " + newPath);
            assertEquals(expected.getNodeType(), actual.getNodeType(),
                    "node should have same type: " + newPath);

            verifyXmlAttributes(expected.getAttributes(), actual.getAttributes(), newPath);

            verifyXmlNodes(expected, actual, newPath, ordered);
        }
    }

    /**
     * Verifies xml attributes of collection of actual nodes by comparing with collection
     * of expected node attributes. This implementation uses
     * {@link #verifyXmlAttribute(Node, Node, String)} method inside.
     *
     * @param expected collection of expected nodes. A {@link NamedNodeMap} interface.
     * @param actual collection of actual nodes. A {@link NamedNodeMap} interface.
     * @param path the path to these xml nodes that are compared.
     */
    private static void verifyXmlAttributes(NamedNodeMap expected, NamedNodeMap actual,
            String path) {
        if (expected == null) {
            assertNull(actual, "no attributes should exist: " + path);
        }
        else {
            assertNotNull(actual, "attributes should exist: " + path);

            for (int i = 0; i < expected.getLength(); i++) {
                verifyXmlAttribute(expected.item(i), actual.item(i), path);
            }

            assertEquals(expected.getLength(), actual.getLength(),
                    "node have same number of attributes: " + path);
        }
    }

    /**
     * Verifies xml attributes of actual node(like name, and node value) by comparing with
     * expected node attributes.
     *
     * @param expected the expected xml node. A {@link Node} interface.
     * @param actual the actual xml node. A {@link Node} interface.
     * @param path the path to the current xml nodes that are compared.
     */
    private static void verifyXmlAttribute(Node expected, Node actual, String path) {
        final String expectedName = expected.getNodeName();

        assertNotNull(actual,
                "attribute value for '" + expectedName + "' should not be null: " + path);

        assertEquals(expectedName, actual.getNodeName(), "attribute name should match: " + path);

        // ignore checkstyle version in xml as it changes each release
        if (!"/#document/checkstyle".equals(path) && !"version".equals(expectedName)) {
            assertEquals(expected.getNodeValue(), actual.getNodeValue(),
                    "attribute value for '" + expectedName + "' should match: " + path);
        }
    }

}
