////
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

package com.puppycrawl.tools.checkstyle.meta;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class to write module details object into an XML file.
 */
public final class XmlMetaWriter {

    /** Compiled pattern for {@code .} used for generating file paths from package names. */
    private static final Pattern FILEPATH_CONVERSION = Pattern.compile("\\.");

    /** Name tag of metadata XML files. */
    private static final String XML_TAG_NAME = "name";

    /** Description tag of metadata XML files. */
    private static final String XML_TAG_DESCRIPTION = "description";

    /** Default(UNIX) file separator. */
    private static final String DEFAULT_FILE_SEPARATOR = "/";

    /**
     * Do no allow {@code XmlMetaWriter} instances to be created.
     */
    private XmlMetaWriter() {
    }

    /**
     * Helper function to write module details to XML file.
     *
     * @param moduleDetails module details
     * @throws TransformerException if a transformer exception occurs
     * @throws ParserConfigurationException if a parser configuration exception occurs
     */
    public static void write(ModuleDetails moduleDetails) throws TransformerException,
        ParserConfigurationException {
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        final Document doc = dBuilder.newDocument();

        final Element rootElement = doc.createElement("checkstyle-metadata");
        final Element rootChild = doc.createElement("module");
        rootElement.appendChild(rootChild);

        doc.appendChild(rootElement);

        final Element checkModule = doc.createElement(moduleDetails.getModuleType().getLabel());
        rootChild.appendChild(checkModule);

        checkModule.setAttribute(XML_TAG_NAME, moduleDetails.getName());
        checkModule.setAttribute("fully-qualified-name",
            moduleDetails.getFullQualifiedName());
        checkModule.setAttribute("parent", moduleDetails.getParent());

        final Element desc = doc.createElement(XML_TAG_DESCRIPTION);
        final Node cdataDesc = doc.createCDATASection(moduleDetails.getDescription());
        desc.appendChild(cdataDesc);
        checkModule.appendChild(desc);
        createPropertySection(moduleDetails, checkModule, doc);
        if (!moduleDetails.getViolationMessageKeys().isEmpty()) {
            final Element messageKeys = doc.createElement("message-keys");
            for (String msg : moduleDetails.getViolationMessageKeys()) {
                final Element messageKey = doc.createElement("message-key");
                messageKey.setAttribute("key", msg);
                messageKeys.appendChild(messageKey);
            }
            checkModule.appendChild(messageKeys);
        }

        writeToFile(doc, moduleDetails);
    }

    /**
     * Create the property section of the module detail object.
     *
     * @param moduleDetails module details
     * @param checkModule root doc element
     * @param doc document object
     */
    private static void createPropertySection(ModuleDetails moduleDetails, Element checkModule,
                                              Document doc) {
        final List<ModulePropertyDetails> moduleProperties = moduleDetails.getProperties();
        if (!moduleProperties.isEmpty()) {
            final Element properties = doc.createElement("properties");
            checkModule.appendChild(properties);
            for (ModulePropertyDetails modulePropertyDetails : moduleProperties) {
                final Element property = doc.createElement("property");
                properties.appendChild(property);
                property.setAttribute(XML_TAG_NAME, modulePropertyDetails.getName());
                property.setAttribute("type", modulePropertyDetails.getType());
                final String defaultValue = modulePropertyDetails.getDefaultValue();
                if (defaultValue != null) {
                    property.setAttribute("default-value", defaultValue);
                }
                final String validationType = modulePropertyDetails.getValidationType();
                if (validationType != null) {
                    property.setAttribute("validation-type", validationType);
                }
                final Element propertyDesc = doc.createElement(XML_TAG_DESCRIPTION);
                propertyDesc.appendChild(doc.createCDATASection(
                    modulePropertyDetails.getDescription()));
                property.appendChild(propertyDesc);
            }
        }
    }

    /**
     * Function to write the prepared document object into an XML file.
     *
     * @param document document updated with all module metadata
     * @param moduleDetails the corresponding module details object
     * @throws TransformerException if a transformer exception occurs
     */
    private static void writeToFile(Document document, ModuleDetails moduleDetails)
        throws TransformerException {
        String fileSeparator = DEFAULT_FILE_SEPARATOR;
        if (System.getProperty("os.name").toLowerCase(Locale.ENGLISH).contains("win")) {
            fileSeparator = "\\" + fileSeparator;
        }
        final String modifiedPath;
        final String xmlExtension = ".xml";
        final String rootOutputPath = System.getProperty("user.dir") + "/src/main/resources";
        if (moduleDetails.getFullQualifiedName().startsWith("com.puppycrawl.tools.checkstyle")) {
            final String moduleFilePath = FILEPATH_CONVERSION
                .matcher(moduleDetails.getFullQualifiedName())
                .replaceAll(fileSeparator);
            final String checkstyleString = "checkstyle";
            final int indexOfCheckstyle =
                moduleFilePath.indexOf(checkstyleString) + checkstyleString.length();

            modifiedPath = rootOutputPath + DEFAULT_FILE_SEPARATOR
                + moduleFilePath.substring(0, indexOfCheckstyle) + "/meta/"
                + moduleFilePath.substring(indexOfCheckstyle + 1) + xmlExtension;
        }
        else {
            String moduleName = moduleDetails.getName();
            if (moduleDetails.getModuleType() == ModuleType.CHECK) {
                moduleName += "Check";
            }
            modifiedPath = rootOutputPath + "/checkstylemeta-" + moduleName + xmlExtension;
        }

        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        final DOMSource source = new DOMSource(document);
        final StreamResult result = new StreamResult(new File(modifiedPath));
        transformer.transform(source, result);

    }
}

