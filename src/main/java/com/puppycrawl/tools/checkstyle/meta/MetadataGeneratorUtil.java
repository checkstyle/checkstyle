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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.maven.doxia.macro.MacroExecutionException;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.MetadataGeneratorLogger;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
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
     * @param out OutputStream for error messages
     * @param moduleFolders folders to check
     * @throws IOException ioException
     * @throws CheckstyleException checkstyleException
     */
    public static void generate(String path, OutputStream out, String... moduleFolders)
            throws IOException, CheckstyleException {
        JavadocMetadataScraper.resetModuleDetailsStore();

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        final DefaultConfiguration scraperCheckConfig =
                        new DefaultConfiguration(JavadocMetadataScraper.class.getName());
        final DefaultConfiguration defaultConfiguration =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration treeWalkerConfig =
                new DefaultConfiguration(TreeWalker.class.getName());
        defaultConfiguration.addProperty("charset", StandardCharsets.UTF_8.name());
        defaultConfiguration.addChild(treeWalkerConfig);
        treeWalkerConfig.addChild(scraperCheckConfig);
        checker.configure(defaultConfiguration);

        checker.addListener(new MetadataGeneratorLogger(out, OutputStreamOptions.NONE));

        final List<File> moduleFiles = getTargetFiles(path, moduleFolders);
        try {
            final List<File> filesWithPropertiesMacro =
                getFilesWhoseTemplateContainsPropertiesMacro(moduleFiles);
            moduleFiles.removeAll(filesWithPropertiesMacro);

            checker.process(moduleFiles);

            for (File file : filesWithPropertiesMacro) {
                final ModuleDetails moduleDetails = getModuleDetails(file);
                writeMetadataFile(moduleDetails);
            }
        }
        catch (MacroExecutionException macroException) {
            throw new CheckstyleException(macroException.getMessage());
        }
    }

    /**
     * Get all module files whose template contains properties macro.
     *
     * @param files files to check.
     * @return files whose template contains properties macro.
     * @throws CheckstyleException checkstyleException
     * @throws MacroExecutionException macroExecutionException
     */
    private static List<File> getFilesWhoseTemplateContainsPropertiesMacro(List<File> files)
            throws CheckstyleException, MacroExecutionException {
        final List<Path> templatesWithPropertiesMacro =
                SiteUtil.getTemplatesThatContainPropertiesMacro();
        final List<File> filesWithPropertiesMacro = new ArrayList<>();
        for (File file : files) {
            final Path templatePath = SiteUtil.getTemplatePath(
                SiteUtil.removeCheckSuffix(SiteUtil.getModuleName(file))
            );
            if (templatesWithPropertiesMacro.contains(templatePath)) {
                filesWithPropertiesMacro.add(file);
            }
        }
        return filesWithPropertiesMacro;
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
            try (Stream<Path> files = Files.walk(Paths.get(path + "/" + folder))) {
                validFiles.addAll(
                        files.map(Path::toFile)
                        .filter(file -> {
                            final String fileName = file.getName();
                            return fileName.endsWith("SuppressWarningsHolder.java")
                                    || fileName.endsWith("Check.java")
                                    || fileName.endsWith("Filter.java");
                        })
                        .collect(Collectors.toList()));
            }
        }

        return validFiles;
    }

    /**
     * Generate metadata for the given file.
     *
     * @param file file to generate metadata for.
     * @return module details.
     * @throws MacroExecutionException macroExecutionException
     */
    private static ModuleDetails getModuleDetails(File file) throws MacroExecutionException {
        final String moduleName = SiteUtil.removeCheckSuffix(SiteUtil.getModuleName(file));

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
        final Map<String, DetailNode> javadocs = SiteUtil
                .getPropertiesJavadocs(properties, className, file);
        final DetailNode moduleJavadoc = javadocs.get(className);
        final String description = SiteUtil.getDescriptionFromJavadoc(moduleJavadoc);

        final List<ModulePropertyDetails> propertiesDetails = getPropertiesDetails(properties,
                javadocs, className, instance);

        return new ModuleDetails(moduleName, fullyQualifiedName, parentModuleString, description,
                moduleType, propertiesDetails, new ArrayList<>(messageKeys));
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
        final List<ModulePropertyDetails> result = new ArrayList<>();
        for (String property : properties) {
            final String description = getPropertyDescription(property,
                    javadocs.get(property));
            final Field field = SiteUtil.getField(instance.getClass(), property);
            final String type =
                    SiteUtil.getFieldClass(field, property, className, instance).getCanonicalName();
            final String defaultValue = getPropertyDefaultValue(property, field, instance,
                    className);
            final String validationType = getValidationType(property);

            result.add(new ModulePropertyDetails(property, type, defaultValue,
                    validationType, description));
        }
        return result;
    }

    /**
     * Get validation type for the given property.
     *
     * @param property property name.
     * @return validation type.
     */
    private static String getValidationType(String property) {
        final String validationType;
        if (SiteUtil.TOKENS.equals(property) || SiteUtil.JAVADOC_TOKENS.equals(property)) {
            validationType = "tokenSet";
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
        String propertyDescription;
        if (SiteUtil.TOKENS.equals(property)) {
            propertyDescription = "tokens to check";
        }
        else if (SiteUtil.JAVADOC_TOKENS.equals(property)) {
            propertyDescription = "javadoc tokens to check";
        }
        else {
            propertyDescription = SiteUtil
                    .getDescriptionFromJavadoc(propertyJavadoc)
                    .substring("Setter to ".length());
            final String firstLetterCapitalized = propertyDescription
                    .substring(0, 1)
                    .toUpperCase(Locale.ROOT);
            propertyDescription = firstLetterCapitalized + propertyDescription.substring(1);
        }
        return propertyDescription;
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
                        .collect(Collectors.toList());
            defaultValue = String.join(SiteUtil.COMMA, configurableTokens);
        }
        else if (SiteUtil.JAVADOC_TOKENS.equals(property)) {
            final AbstractJavadocCheck check = (AbstractJavadocCheck) instance;
            final List<String> configurableTokens = SiteUtil
                    .getDifference(check.getDefaultJavadocTokens(),
                            check.getRequiredJavadocTokens())
                    .stream()
                    .map(JavadocUtil::getTokenName)
                    .collect(Collectors.toList());
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
        catch (TransformerException | ParserConfigurationException ex) {
            throw new CheckstyleException(
                            "Failed to write metadata into XML file for module: "
                                    + moduleDetails.getName(), ex);
        }
    }
}
