////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.meta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public final class XmlMetaWriter {
    private static final Pattern FILEPATH_CONVERSION = Pattern.compile("\\.");

    public void write(ModuleDetails moduleDetails) throws IOException {
        final Document document = DocumentHelper.createDocument();
        final Element root = document.addElement("checkstyle-metadata").addElement("module");
        Element checkModule = null;
        final ModuleType moduleType = moduleDetails.getModuleType();
        if (moduleType == ModuleType.CHECK) {
            checkModule = root.addElement("check");
        }
        else if (moduleType == ModuleType.FILTER) {
            checkModule = root.addElement("filter");
        }
        else if (moduleType == ModuleType.FILEFILTER) {
            checkModule = root.addElement("file-filter");
        }
        checkModule.addAttribute("name", moduleDetails.getName());
        checkModule.addAttribute("fully-qualified-name", moduleDetails.getFullQualifiedName());
        checkModule.addAttribute("parent", moduleDetails.getParent());
        checkModule.addElement("description").addCDATA(moduleDetails.getDescription());
        if (!moduleDetails.getProperties().isEmpty()) {
            final Element properties = checkModule.addElement("properties");
            for (ModulePropertyDetails modulePropertyDetails : moduleDetails.getProperties()) {
                final Element property = properties.addElement("property");
                property.addAttribute("name", modulePropertyDetails.getName());
                property.addAttribute("type", modulePropertyDetails.getType());
                property.addAttribute("default-value", modulePropertyDetails.getDefaultValue());
                property.addAttribute("validation-type", modulePropertyDetails.getValidationType());
                property.addElement("description").addCDATA(modulePropertyDetails.getDescription());
            }
        }

        if (!moduleDetails.getViolationMessageKeys().isEmpty()) {
            final Element violationMessages = checkModule.addElement("message-keys");
            for (String msg : moduleDetails.getViolationMessageKeys()) {
                violationMessages.addElement("message-key").addAttribute("key", msg);
            }
        }

        writeToFile(document, moduleDetails);
    }

    private static void writeToFile(Document document, ModuleDetails moduleDetails)
            throws IOException {
        final String rootOutputPath = System.getProperty("user.dir") + "/src/main/resources";
        String fileSeperator = System.getProperty("file.separator");
        if (System.getProperty("os.name").toLowerCase(Locale.ENGLISH).contains("win")) {
            fileSeperator = "\\" + fileSeperator;
        }
        final String moduleFilePath = FILEPATH_CONVERSION
                .matcher(moduleDetails.getFullQualifiedName())
                .replaceAll(fileSeperator);
        final String modifiedPath;
        if (moduleFilePath.contains("puppycrawl")) {
            final int idxOfCheckstyle =
                    moduleFilePath.indexOf("checkstyle") + "checkstyle".length();
            // make sure all folders are created
            modifiedPath = rootOutputPath + System.getProperty("file.separator")
                    + moduleFilePath.substring(0, idxOfCheckstyle)
                    + System.getProperty("file.separator") + "meta"
                    + System.getProperty("file.separator")
                    + moduleFilePath.substring(idxOfCheckstyle + 1) + ".xml";
        }
        else {
            String moduleName = moduleDetails.getName();
            if (moduleDetails.getModuleType() == ModuleType.CHECK) {
                moduleName += "Check";
            }
            modifiedPath = rootOutputPath + System.getProperty("file.separator") + "checkstylemeta-"
                    + moduleName + ".xml";
        }
        if (!moduleDetails.getDescription().isEmpty()) {
            final OutputFormat format = OutputFormat.createPrettyPrint();
            // log to ensure folders exist
            final XMLWriter writer = new XMLWriter(Files.newOutputStream(Paths.get(modifiedPath)),
                    format);
            writer.write(document);
        }
    }
}

