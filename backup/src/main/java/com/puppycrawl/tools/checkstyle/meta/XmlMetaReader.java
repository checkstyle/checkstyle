///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.meta;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class having utilities required to read module details from an XML metadata file of a module.
 * This class is used by plugins that need load of metadata from XML files.
 */
public final class XmlMetaReader {

    /** Name tag of metadata XML files. */
    private static final String XML_TAG_NAME = "name";

    /** Description tag of metadata XML files. */
    private static final String XML_TAG_DESCRIPTION = "description";

    /**
     * Do no allow {@code XmlMetaReader} instances to be created.
     */
    private XmlMetaReader() {
    }

    /**
     * Utility to load all the metadata files present in the checkstyle JAR including third parties'
     * module metadata files.
     * checkstyle metadata files are grouped in a folder hierarchy similar to that of their
     * corresponding source files.
     * Third party(e.g. SevNTU Checks) metadata files are prefixed with {@code checkstylemeta-}
     * to their file names.
     *
     * @param thirdPartyPackages fully qualified third party package names(can be only a
     *                           hint, e.g. for SevNTU it can be com.github.sevntu / com.github)
     * @return list of module details found in the classpath satisfying the above conditions
     * @throws IllegalStateException if there was a problem reading the module metadata files
     */
    public static List<ModuleDetails> readAllModulesIncludingThirdPartyIfAny(
            String... thirdPartyPackages) {
        final Set<String> standardModuleFileNames = new Reflections(
                "com.puppycrawl.tools.checkstyle.meta", Scanners.Resources)
                .getResources(Pattern.compile(".*\\.xml"));
        final Set<String> allMetadataSources = new HashSet<>(standardModuleFileNames);
        for (String packageName : thirdPartyPackages) {
            final Set<String> thirdPartyModuleFileNames =
                    new Reflections(packageName, Scanners.Resources)
                            .getResources(Pattern.compile(".*checkstylemeta-.*\\.xml"));
            allMetadataSources.addAll(thirdPartyModuleFileNames);
        }

        final List<ModuleDetails> result = new ArrayList<>(allMetadataSources.size());
        for (String fileName : allMetadataSources) {
            final ModuleType moduleType;
            if (fileName.endsWith("FileFilter.xml")) {
                moduleType = ModuleType.FILEFILTER;
            }
            else if (fileName.endsWith("Filter.xml")) {
                moduleType = ModuleType.FILTER;
            }
            else {
                moduleType = ModuleType.CHECK;
            }
            final ModuleDetails moduleDetails;
            try {
                moduleDetails = read(XmlMetaReader.class.getResourceAsStream("/" + fileName),
                        moduleType);
            }
            catch (ParserConfigurationException | IOException | SAXException ex) {
                throw new IllegalStateException("Problem to read all modules including third "
                        + "party if any. Problem detected at file: " + fileName, ex);
            }
            result.add(moduleDetails);
        }

        return result;
    }

    /**
     * Read the module details from the supplied input stream of the module's XML metadata file.
     *
     * @param moduleMetadataStream input stream object of a module's metadata file
     * @param moduleType type of module
     * @return module detail object extracted from the XML metadata file
     * @throws ParserConfigurationException if a parser configuration exception occurs
     * @throws IOException if a IO exception occurs
     * @throws SAXException if a SAX exception occurs during parsing the XML file
     */
    public static ModuleDetails read(InputStream moduleMetadataStream, ModuleType moduleType)
            throws ParserConfigurationException, IOException, SAXException {
        ModuleDetails result = null;
        if (moduleType != null) {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(moduleMetadataStream);
            final Element root = document.getDocumentElement();
            final Element element = getDirectChildsByTag(root, "module").get(0);
            final Element module = getDirectChildsByTag(element, moduleType.getLabel()).get(0);
            result = new ModuleDetails();

            result.setModuleType(moduleType);
            populateModule(module, result);
        }
        return result;
    }

    /**
     * Populate the module detail object from XML metadata.
     *
     * @param mod root XML document element
     * @param moduleDetails module detail object, which is to be updated
     */
    private static void populateModule(Element mod, ModuleDetails moduleDetails) {
        moduleDetails.setName(getAttributeValue(mod, XML_TAG_NAME));
        moduleDetails.setFullQualifiedName(getAttributeValue(mod, "fully-qualified-name"));
        moduleDetails.setParent(getAttributeValue(mod, "parent"));
        moduleDetails.setDescription(getDirectChildsByTag(mod, XML_TAG_DESCRIPTION).get(0)
                .getFirstChild().getNodeValue());
        final List<Element> properties = getDirectChildsByTag(mod, "properties");
        if (!properties.isEmpty()) {
            final List<ModulePropertyDetails> modulePropertyDetailsList =
                    createProperties(properties.get(0));
            moduleDetails.addToProperties(modulePropertyDetailsList);
        }
        final List<String> messageKeys =
                getListContentByAttribute(mod,
                        "message-keys", "message-key", "key");
        if (messageKeys != null) {
            moduleDetails.addToViolationMessages(messageKeys);
        }
    }

    /**
     * Create module property details from the XML metadata.
     *
     * @param properties parent document element which contains property's metadata
     * @return list of property details object created
     */
    private static List<ModulePropertyDetails> createProperties(Element properties) {
        final NodeList propertyList = properties.getElementsByTagName("property");
        final int propertyListLength = propertyList.getLength();
        final List<ModulePropertyDetails> result = new ArrayList<>(propertyListLength);
        for (int i = 0; i < propertyListLength; i++) {
            final ModulePropertyDetails propertyDetails = new ModulePropertyDetails();
            final Element prop = (Element) propertyList.item(i);
            propertyDetails.setName(getAttributeValue(prop, XML_TAG_NAME));
            propertyDetails.setType(getAttributeValue(prop, "type"));
            final String defaultValueTag = "default-value";
            if (prop.hasAttribute(defaultValueTag)) {
                propertyDetails.setDefaultValue(getAttributeValue(prop, defaultValueTag));
            }
            final String validationTypeTag = "validation-type";
            if (prop.hasAttribute(validationTypeTag)) {
                propertyDetails.setValidationType(getAttributeValue(prop, validationTypeTag));
            }
            propertyDetails.setDescription(getDirectChildsByTag(prop, XML_TAG_DESCRIPTION)
                    .get(0).getFirstChild().getNodeValue());
            result.add(propertyDetails);
        }
        return result;
    }

    /**
     * Utility to get the list contents by the attribute specified.
     *
     * @param element doc element
     * @param listParent parent element of list
     * @param listOption child list element
     * @param attribute attribute key
     * @return list of strings containing the XML list data
     */
    private static List<String> getListContentByAttribute(Element element, String listParent,
                                                         String listOption, String attribute) {
        final List<Element> children = getDirectChildsByTag(element, listParent);
        List<String> result = null;
        if (!children.isEmpty()) {
            final NodeList nodeList = children.get(0).getElementsByTagName(listOption);
            final int nodeListLength = nodeList.getLength();
            final List<String> listContent = new ArrayList<>(nodeListLength);
            for (int j = 0; j < nodeListLength; j++) {
                listContent.add(getAttributeValue((Element) nodeList.item(j), attribute));
            }
            result = listContent;
        }
        return result;
    }

    /**
     * Utility to get the children of an element by tag name.
     *
     * @param element parent element
     * @param sTagName tag name of children required
     * @return list of elements retrieved
     */
    private static List<Element> getDirectChildsByTag(Element element, String sTagName) {
        final NodeList children = element.getElementsByTagName(sTagName);
        final List<Element> res = new ArrayList<>();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getParentNode().equals(element)) {
                res.add((Element) children.item(i));
            }
        }
        return res;
    }

    /**
     * Utility to get attribute value of an element.
     *
     * @param element target element
     * @param attribute attribute key
     * @return attribute value
     */
    private static String getAttributeValue(Element element, String attribute) {
        return element.getAttributes().getNamedItem(attribute).getNodeValue();
    }

}
