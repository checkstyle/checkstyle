///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.maven.doxia.macro.MacroExecutionException;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.site.JavadocScraperResultUtil;
import com.puppycrawl.tools.checkstyle.site.ModuleJavadocParsingUtil;
import com.puppycrawl.tools.checkstyle.site.PropertyDetails;
import com.puppycrawl.tools.checkstyle.site.SiteUtil;

/** Class which handles all the metadata generation and writing calls. */
public final class MetadataGeneratorUtil {

    /** Stop instances being created. **/
    private MetadataGeneratorUtil() {
    }

    /**
     * Generate metadata from the module source files available in the input argument path.
     *
     * @param path arguments
     * @param moduleFolders folders to check
     * @throws IOException ioException
     * @throws CheckstyleException checkstyleException
     */
    public static void generate(String path, String... moduleFolders)
            throws IOException, CheckstyleException {
        final List<File> modulesToProcess =
            getTargetFiles(path, moduleFolders);

        try {
            for (File file : modulesToProcess) {
                final String fileName = file.getName();

                if (fileName.startsWith("Abstract")
                    && !"AbstractClassNameCheck.java".equals(fileName)) {
                    continue;
                }

                final ModuleDetails moduleDetails = getModuleDetails(file);
                writeMetadataFile(moduleDetails);
            }
        }
        catch (MacroExecutionException macroException) {
            throw new CheckstyleException("Failed to execute macro", macroException);
        }
    }

    /**
     * Generate metadata for the given file.
     *
     * @param file file to generate metadata for.
     * @return module details.
     * @throws MacroExecutionException macroExecutionException
     */
    private static ModuleDetails getModuleDetails(File file) throws MacroExecutionException {
        final String moduleName = SiteUtil.FINAL_CHECK.matcher(SiteUtil.getModuleName(file))
            .replaceAll("");

        final Object instance = SiteUtil.getModuleInstance(moduleName);
        final Class<?> clss = instance.getClass();
        final String fullyQualifiedName = clss.getName();

        final String parentModule = SiteUtil.getParentModule(clss);
        final Object parentModuleInstance = SiteUtil.getModuleInstance(parentModule);
        final String parentModuleString = parentModuleInstance.getClass().getName();

        final ModuleType moduleType = getModuleType(moduleName);

        final Set<String> messageKeys = SiteUtil.getMessageKeys(clss);

        final String className = SiteUtil.getModuleName(file);
        final Set<String> properties = SiteUtil.getPropertiesForDocumentation(clss, instance);
        final Map<String, PropertyDetails> scrapedPropertyDetails = SiteUtil
                .buildPropertyDetails(properties, className, file.toPath(), instance);
        String description = JavadocScraperResultUtil.getModuleDescription();

        final String notes = JavadocScraperResultUtil.getModuleNotes();
        if (!notes.isEmpty()) {
            description = description + "\n\n " + notes;
        }

        final List<ModulePropertyDetails> propertiesDetails = getPropertiesDetails(
                scrapedPropertyDetails.values(), className, instance);

        return new ModuleDetails(moduleName, fullyQualifiedName, parentModuleString,
            description, moduleType, propertiesDetails, new ArrayList<>(messageKeys));
    }

    /**
     * Get module type(check/filter/filefilter) based on module name.
     *
     * @param moduleName module name.
     * @return module type.
     */
    private static ModuleType getModuleType(String moduleName) {
        final ModuleType result;
        if (moduleName.endsWith("FileFilter")) {
            result = ModuleType.FILEFILTER;
        }
        else if (moduleName.endsWith("Filter")) {
            result = ModuleType.FILTER;
        }
        else {
            result = ModuleType.CHECK;
        }
        return result;
    }

    /**
     * Get property details for the given property - name, description, type, default value.
     *
     * @param propertiesDetails property details list.
     * @param className the class name of the module.
     * @param instance the instance of the module.
     * @return property details.
     * @throws MacroExecutionException if an error occurs.
     */
    private static List<ModulePropertyDetails> getPropertiesDetails(
            Collection<PropertyDetails> propertiesDetails,
            String className, Object instance)
            throws MacroExecutionException {
        final List<ModulePropertyDetails> result = new ArrayList<>(propertiesDetails.size());
        for (PropertyDetails details : propertiesDetails) {
            final String property = details.getName();
            final String description = details.getDescription();
            final Field propertyField = SiteUtil.getField(instance.getClass(), property);

            final String type = SiteUtil.getType(propertyField, property, className, instance);

            final String defaultValue = getPropertyDefaultValue(details);
            final String validationType = getValidationType(property, propertyField);

            result.add(new ModulePropertyDetails(property, type, defaultValue,
                    validationType, description));
        }
        return result;
    }

    /**
     * Get default value for the given property.
     *
     * @param details the property details.
     * @return default value.
     */
    private static String getPropertyDefaultValue(PropertyDetails details) {
        final String defaultValue;
        if (details.getDefaultValueTokens().isEmpty()) {
            final String raw = details.getDefaultValue();
            if ("{}".equals(raw)) {
                defaultValue = "";
            }
            else {
                defaultValue = raw;
            }
        }
        else {
            defaultValue = String.join(SiteUtil.COMMA, details.getDefaultValueTokens());
        }
        return defaultValue;
    }

    /**
     * Write metadata file for the given module.
     *
     * @param moduleDetails module details.
     * @throws CheckstyleException if an error occurs during writing metadata file.
     */
    private static void writeMetadataFile(ModuleDetails moduleDetails)
            throws CheckstyleException {
        try {
            XmlMetaWriter.write(moduleDetails);
        }
        catch (TransformerException | ParserConfigurationException example) {
            throw new CheckstyleException(
                    "Failed to write metadata into XML file for module: "
                            + moduleDetails.getName(), example);
        }
    }

    /**
     * Get validation type for the given property.
     *
     * @param propertyName name of property.
     * @param propertyField field of property.
     * @return validation type.
     */
    private static String getValidationType(String propertyName, Field propertyField) {
        final String validationType;
        if (SiteUtil.TOKENS.equals(propertyName) || SiteUtil.JAVADOC_TOKENS.equals(propertyName)) {
            validationType = "tokenSet";
        }
        else if (propertyField != null
                && ModuleJavadocParsingUtil.isPropertySpecialTokenProp(propertyField)) {
            validationType = "tokenTypesSet";
        }
        else {
            validationType = null;
        }
        return validationType;
    }

    /**
     * Get files that represent modules.
     *
     * @param moduleFolders folders to check
     * @param path          rootPath
     * @return files for scrapping javadoc and generation of metadata files
     * @throws IOException ioException
     */
    private static List<File> getTargetFiles(String path, String... moduleFolders)
            throws IOException {
        final List<File> validFiles = new ArrayList<>();
        for (String folder : moduleFolders) {
            try (Stream<Path> files = Files.walk(Path.of(path + "/" + folder))) {
                validFiles.addAll(
                        files.map(Path::toFile)
                        .filter(file -> {
                            final String fileName = file.getName();
                            return fileName.endsWith("SuppressWarningsHolder.java")
                                    || fileName.endsWith("Check.java")
                                    || fileName.endsWith("Filter.java");
                        })
                        .toList());
            }
        }

        return validFiles;
    }

}
