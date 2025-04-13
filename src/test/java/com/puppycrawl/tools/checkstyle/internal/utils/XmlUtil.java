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

package com.puppycrawl.tools.checkstyle.internal.utils;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.puppycrawl.tools.checkstyle.XmlLoader;

/**
 * XmlUtil.
 *
 * @noinspection ClassOnlyUsedInOnePackage
 * @noinspectionreason ClassOnlyUsedInOnePackage - class is internal tool, and only used in testing
 */
public final class XmlUtil {

    private XmlUtil() {
    }

    public static Document getRawXml(String fileName, String code, String unserializedSource)
            throws ParserConfigurationException {
        Document rawXml = null;
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setFeature(
                    XmlLoader.LoadExternalDtdFeatureProvider.EXTERNAL_GENERAL_ENTITIES, false);
            factory.setFeature(
                    XmlLoader.LoadExternalDtdFeatureProvider.LOAD_EXTERNAL_DTD, false);

            final DocumentBuilder builder = factory.newDocumentBuilder();

            rawXml = builder.parse(new InputSource(new StringReader(code)));
        }
        catch (IOException | SAXException ex) {
            assertWithMessage(fileName + " has invalid xml (" + ex.getMessage() + "): "
                    + unserializedSource).fail();
        }

        return rawXml;
    }

    public static Set<Node> getChildrenElements(Node node) {
        final Set<Node> result = new LinkedHashSet<>();

        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() != Node.TEXT_NODE) {
                result.add(child);
            }
        }

        return result;
    }

    public static Node getFirstChildElement(Node node) {
        Node firstChildElement = null;
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() != Node.TEXT_NODE) {
                firstChildElement = child;
                break;
            }
        }

        return firstChildElement;
    }

    /**
     * Returns the {@code Node} that has an id attribute with the given value.
     * The id should be unique within the Xml Document.
     *
     * @param node to retrieve information.
     * @param id the unique {@code id} value for a node.
     * @return the matching node or {@code null} if none matches.
     */
    public static Node findChildElementById(Node node, String id) {
        Node childElement = null;
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            final NamedNodeMap attributes = child.getAttributes();
            if (attributes != null) {
                final Node attribute = attributes.getNamedItem("id");
                if (attribute != null && id.equals(attribute.getNodeValue())) {
                    childElement = child;
                    break;
                }
            }
        }

        return childElement;
    }

    public static Set<Node> findChildElementsByTag(Node node, String tag) {
        final Set<Node> result = new LinkedHashSet<>();

        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (tag.equals(child.getNodeName())) {
                result.add(child);
            }
            else if (child.hasChildNodes()) {
                result.addAll(findChildElementsByTag(child, tag));
            }
        }

        return result;
    }

    /**
     * Returns the value of the "name" attribute for the given node.
     *
     * @param node to retrieve the name
     * @return the value of the attribute "name"
     */
    public static String getNameAttributeOfNode(Node node) {
        return node.getAttributes().getNamedItem("name").getNodeValue();
    }

    /**
     * Sanitizes the given string for safe use in XML documents.
     * <ul>
     * <li>Removes all whitespaces at the beginning and at the end of the string;</li>
     * <li>Replaces repeated whitespaces in the middle of the string with a single space;</li>
     * <li>Replaces XML entities with escaped values.</li>
     * </ul>
     *
     * @param rawXml the text to sanitize
     * @return the sanitized text
     */
    public static String sanitizeXml(String rawXml) {
        return rawXml
                .replaceAll("(^\\s+|\\s+$)", "")
                .replaceAll("\\s+", " ")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

}
