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

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

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

    protected static Document getOutputStreamXml(ByteArrayOutputStream outputStream)
            throws ParserConfigurationException {
        final String xml = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);

        return XmlUtil.getRawXml("audit output", xml, xml);
    }

    protected static void verifyXml(String expectedOutputFile,
            ByteArrayOutputStream actualOutputStream, String... messages) throws Exception {
        verifyXml(expectedOutputFile, actualOutputStream, null, messages);
    }

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
                        .that(actualChildren.size())
                        .isEqualTo(expectedChildren.size());

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
}
