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

package com.puppycrawl.tools.checkstyle.internal.utils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XdocUtil.
 * @noinspection ClassOnlyUsedInOnePackage
 */
public final class XdocUtil {

    public static final String DIRECTORY_PATH = "src/xdocs";

    private XdocUtil() {
    }

    /**
     * Gets xdocs file paths.
     * @return a list of xdocs file paths.
     * @throws IOException if an I/O error occurs.
     */
    public static Set<Path> getXdocsFilePaths() throws IOException {
        final Path directory = Paths.get(DIRECTORY_PATH);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.xml")) {
            final Set<Path> xdocs = new HashSet<>();
            for (Path entry : stream) {
                xdocs.add(entry);
            }
            return xdocs;
        }
    }

    /**
     * Gets xdocs documentation file paths.
     * @param files list of all xdoc files
     * @return a list of xdocs config file paths.
     */
    public static Set<Path> getXdocsConfigFilePaths(Set<Path> files) {
        final Set<Path> xdocs = new HashSet<>();
        for (Path entry : files) {
            final String fileName = entry.getFileName().toString();
            if (fileName.startsWith("config_")) {
                xdocs.add(entry);
            }
        }
        return xdocs;
    }

    /**
     * Gets xdocs style file paths.
     * @param files list of all xdoc files
     * @return a list of xdocs style file paths.
     */
    public static Set<Path> getXdocsStyleFilePaths(Set<Path> files) {
        final Set<Path> xdocs = new HashSet<>();
        for (Path entry : files) {
            final String fileName = entry.getFileName().toString();
            if (fileName.endsWith("_style.xml")) {
                xdocs.add(entry);
            }
        }
        return xdocs;
    }

    /**
     * Gets names of checkstyle's modules which are documented in xdocs.
     * @return a set of checkstyle's modules which have xdoc documentation.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies
     *              the configuration requested.
     * @throws IOException if any IO errors occur.
     * @throws SAXException if any parse errors occur.
     */
    public static Set<String> getModulesNamesWhichHaveXdoc() throws Exception {
        final DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();

        // Validations of XML file make parsing too slow, that is why we disable
        // all validations.
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        factory.setFeature("http://xml.org/sax/features/namespaces", false);
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setFeature(
                "http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
                false);
        factory.setFeature(
                "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                false);

        final Set<String> modulesNamesWhichHaveXdoc = new HashSet<>();

        for (Path path : getXdocsConfigFilePaths(getXdocsFilePaths())) {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(path.toFile());

            // optional, but recommended
            // FYI:
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-
            // java-how-does-it-work
            document.getDocumentElement().normalize();

            final NodeList nodeList = document.getElementsByTagName("section");

            for (int i = 0; i < nodeList.getLength(); i++) {
                final Node currentNode = nodeList.item(i);
                if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                    final Element module = (Element) currentNode;
                    final String moduleName = module.getAttribute("name");
                    if (!"Content".equals(moduleName)
                            && !"Overview".equals(moduleName)) {
                        modulesNamesWhichHaveXdoc.add(moduleName);
                    }
                }
            }
        }
        return modulesNamesWhichHaveXdoc;
    }

}
