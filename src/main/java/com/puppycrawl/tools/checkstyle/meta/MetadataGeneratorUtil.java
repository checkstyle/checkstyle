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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.maven.doxia.macro.MacroExecutionException;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.site.ModuleJavadocParsingUtil;
import com.puppycrawl.tools.checkstyle.site.SiteUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

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
            throw new CheckstyleException(macroException.getMessage(), macroException);
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
        final Map<String, DetailNode> propertiesJavadocs = SiteUtil
                .getPropertiesJavadocs(properties, className, file.toPath());
        final DetailNode moduleJavadoc = SiteUtil.getModuleJavadoc(className, file.toPath());
        String description = ModuleJavadocParsingUtil
            .getModuleDescription(moduleJavadoc);

        final String notes = ModuleJavadocParsingUtil.getModuleNotes(moduleJavadoc);
        if (!notes.isEmpty()) {
            description = description + "\n\n " + notes;
        }

        final List<ModulePropertyDetails> propertiesDetails = getPropertiesDetails(
            properties, propertiesJavadocs, className, instance);

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
     * @param properties properties of the module.
     * @param javadocs javadocs of the module.
     * @param className the class name of the module.
     * @param instance the instance of the module.
     * @return property details.
     * @throws MacroExecutionException if an error occurs.
     */
    private static List<ModulePropertyDetails> getPropertiesDetails(
            Set<String> properties, Map<String, DetailNode> javadocs,
            String className, Object instance)
            throws MacroExecutionException {
        final List<ModulePropertyDetails> result = new ArrayList<>(properties.size());
        for (String property : properties) {
            final String description = getPropertyDescription(property,
                    javadocs.get(property));
            final Field propertyField = SiteUtil.getField(instance.getClass(), property);

            final String type = SiteUtil.getType(propertyField, property, className, instance);

            final String defaultValue = getPropertyDefaultValue(property, propertyField, instance,
                    className);
            final String validationType = getValidationType(property, propertyField);

            result.add(new ModulePropertyDetails(property, type, defaultValue,
                    validationType, description));
        }
        return result;
    }

    /**
     * Get default value for the given property.
     *
     * @param property property name.
     * @param field field.
     * @param instance instance of the module.
     * @param className class name of the module.
     * @return default value.
     * @throws MacroExecutionException if an error occurs.
     */
    private static String getPropertyDefaultValue(String property, Field field, Object instance,
            String className) throws MacroExecutionException {
        final String defaultValue;
        if (SiteUtil.TOKENS.equals(property)) {
            final AbstractCheck check = (AbstractCheck) instance;
            final List<String> configurableTokens = SiteUtil
                        .getDifference(check.getDefaultTokens(),
                                check.getRequiredTokens())
                        .stream()
                        .map(TokenUtil::getTokenName)
                        .toList();
            defaultValue = String.join(SiteUtil.COMMA, configurableTokens);
        }
        else if (SiteUtil.JAVADOC_TOKENS.equals(property)) {
            final AbstractJavadocCheck check = (AbstractJavadocCheck) instance;
            final List<String> configurableTokens = SiteUtil
                    .getDifference(check.getDefaultJavadocTokens(),
                            check.getRequiredJavadocTokens())
                    .stream()
                    .map(JavadocUtil::getTokenName)
                    .toList();
            defaultValue = String.join(SiteUtil.COMMA, configurableTokens);
        }
        else {
            defaultValue = SiteUtil.getDefaultValue(property, field, instance, className);
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
     * Get property description from property javadoc.
     *
     * @param property property name.
     * @param propertyJavadoc property javadoc.
     * @return property description.
     */
    private static String getPropertyDescription(String property, DetailNode propertyJavadoc) {
        final String propertyDescription;
        if (SiteUtil.TOKENS.equals(property)) {
            propertyDescription = "tokens to check";
        }
        else if (SiteUtil.JAVADOC_TOKENS.equals(property)) {
            propertyDescription = "javadoc tokens to check";
        }
        else {
            final String firstJavadocParagraph = SiteUtil
                    .getFirstParagraphFromJavadoc(propertyJavadoc);
            final String setterToString = "Setter to ";

            if (firstJavadocParagraph.contains(setterToString)) {
                final String unprocessedPropertyDescription = firstJavadocParagraph
                    .substring(setterToString.length());
                final String firstLetterCapitalized = unprocessedPropertyDescription
                        .substring(0, 1)
                        .toUpperCase(Locale.ROOT);

                propertyDescription = firstLetterCapitalized
                    + unprocessedPropertyDescription.substring(1);
            }
            else {
                propertyDescription = firstJavadocParagraph;
            }

        }
        return propertyDescription;
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
