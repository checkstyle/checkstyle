////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.BiPredicate;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
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

        verifyXmlNode(expectedDocument, actualDocument, "/", ordered);
    }

    private static void verifyXmlNodes(Node expected, Node actual, String path,
            BiPredicate<Node, Node> ordered) {
        final Node expectedFirstChild = expected.getFirstChild();
        final Node actualFirstChild = actual.getFirstChild();

        if (expectedFirstChild == null) {
            Assert.assertNull("no children nodes should exist: " + path, actualFirstChild);
            Assert.assertEquals("text should be the same: " + path, expected.getNodeValue(),
                    actual.getNodeValue());
        }
        else {
            Assert.assertNotNull("children nodes should exist: " + path, actualFirstChild);

            if (ordered == null) {
                Node actualChild = actualFirstChild;

                for (Node expectedChild = expectedFirstChild; expectedChild != null;
                        expectedChild = expectedChild.getNextSibling()) {
                    verifyXmlNode(expectedChild, actualChild, path, ordered);

                    actualChild = actualChild.getNextSibling();
                }

                Assert.assertNull("node have same number of children: " + path, actualChild);
            }
            else {
                final Set<Node> expectedChildren = XmlUtil.getChildrenElements(expected);
                final Set<Node> actualChildren = XmlUtil.getChildrenElements(actual);

                Assert.assertEquals("node have same number of children: " + path,
                        expectedChildren.size(), actualChildren.size());

                for (Node expectedChild : expectedChildren) {
                    Node foundChild = null;

                    for (Node actualChild : actualChildren) {
                        if (ordered.test(expectedChild, actualChild)) {
                            foundChild = actualChild;
                            break;
                        }
                    }

                    Assert.assertNotNull("node should exist: " + path + expectedChild.getNodeName()
                            + "/", foundChild);

                    verifyXmlNode(expectedChild, foundChild, path, ordered);
                }
            }
        }
    }

    private static void verifyXmlNode(Node expected, Node actual, String path,
            BiPredicate<Node, Node> ordered) {
        if (expected == null) {
            if (actual != null) {
                Assert.fail("no node should exist: " + path + actual.getNodeName() + "/");
            }
        }
        else {
            final String newPath = path + expected.getNodeName() + "/";

            Assert.assertNotNull("node should exist: " + newPath, actual);
            Assert.assertEquals("node should have same name: " + newPath, expected.getNodeName(),
                    actual.getNodeName());
            Assert.assertEquals("node should have same type: " + newPath, expected.getNodeType(),
                    actual.getNodeType());

            verifyXmlAttributes(expected.getAttributes(), actual.getAttributes(), newPath);

            verifyXmlNodes(expected, actual, newPath, ordered);
        }
    }

    private static void verifyXmlAttributes(NamedNodeMap expected, NamedNodeMap actual,
            String path) {
        if (expected == null) {
            Assert.assertNull("no attributes should exist: " + path, actual);
        }
        else {
            Assert.assertNotNull("attributes should exist: " + path, actual);

            for (int i = 0; i < expected.getLength(); i++) {
                verifyXmlAttribute(expected.item(i), actual.item(i), path);
            }

            Assert.assertEquals("node have same number of attributes: " + path,
                    expected.getLength(), actual.getLength());
        }
    }

    private static void verifyXmlAttribute(Node expected, Node actual, String path) {
        final String expectedName = expected.getNodeName();

        Assert.assertNotNull("attribute value for '" + expectedName + "' should not be null: "
                + path, actual);

        Assert.assertEquals("attribute name should match: " + path, expectedName,
                actual.getNodeName());

        // ignore checkstyle version in xml as it changes each release
        if (!"/#document/checkstyle".equals(path) && !"version".equals(expectedName)) {
            Assert.assertEquals("attribute value for '" + expectedName + "' should match: " + path,
                    expected.getNodeValue(), actual.getNodeValue());
        }
    }

}
