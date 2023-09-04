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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.maven.doxia.macro.MacroExecutionException;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.MetadataGeneratorLogger;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.site.SiteUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

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
                SiteUtil.getFilesWhoseTemplateContainsPropertiesMacro(moduleFiles);
            moduleFiles.removeAll(filesWithPropertiesMacro);

            checker.process(moduleFiles);

            for (File file : filesWithPropertiesMacro) {
                generateMetadata(file);
            }
        }
        catch (MacroExecutionException macroException) {
            throw new CheckstyleException(macroException.getMessage());
        }
    }

    /**
     * Generate metadata for the given file.
     *
     * @param file file to generate metadata for.
     * @throws MacroExecutionException macroExecutionException
     */
    private static void generateMetadata(File file) throws MacroExecutionException {
        final ModuleDetails moduleDetails = new ModuleDetails();

        final String moduleName = SiteUtil.removeCheckSuffix(SiteUtil.getModuleName(file));
        moduleDetails.setName(moduleName);

        final Object instance = SiteUtil.getModuleInstance(moduleName);
        final Class<?> clss = instance.getClass();
        final String fullyQualifiedName = clss.getName();
        moduleDetails.setFullQualifiedName(fullyQualifiedName);

        final String parentModule = SiteUtil.getParentModule(clss);
        final Object parentModuleInstance = SiteUtil.getModuleInstance(parentModule);
        moduleDetails.setParent(parentModuleInstance.getClass().getName());

        final ModuleType moduleType = getModuleType(moduleName);
        moduleDetails.setModuleType(moduleType);

        final Set<String> messageKeys = SiteUtil.getMessageKeys(clss);
        moduleDetails.addToViolationMessages(new ArrayList<>(messageKeys));

        final String className = SiteUtil.getModuleName(file);
        final Set<String> properties = SiteUtil.getPropertiesForDocumentation(clss, instance);
        final Map<String, DetailNode> javadocs = SiteUtil
                .getPropertiesJavadocs(properties, className, file);
        final DetailNode moduleJavadoc = javadocs.get(className);
        moduleDetails.setDescription(SiteUtil.getDescriptionFromJavadoc(moduleJavadoc));

        for (String property : properties) {
            final ModulePropertyDetails propertyDetails = new ModulePropertyDetails();
            propertyDetails.setName(property);

            final DetailNode propertyJavadoc = javadocs.get(property);
            final String propertyDescription = getPropertyDescription(property, propertyJavadoc);
            propertyDetails.setDescription(propertyDescription);

            final Field field = SiteUtil.getField(clss, property);
            final Class<?> propertyType = SiteUtil.getFieldClass(field, property, className, instance);
            propertyDetails.setType(propertyType.getName());

            String defaultValue = SiteUtil.getDefaultValue(property, field, instance, className);
            if (defaultValue.startsWith("\"") && defaultValue.endsWith("\"")) {
                // remove quotes from default value (e.g. "true" -> true)
                defaultValue = defaultValue.substring(1, defaultValue.length() - 1);
            }

            propertyDetails.setDefaultValue(defaultValue);

            if (SiteUtil.TOKENS.equals(property) || SiteUtil.JAVADOC_TOKENS.equals(property)) {
                propertyDetails.setValidationType("tokenSet");
            }

            moduleDetails.addToProperties(propertyDetails);
        }

        try {
            XmlMetaWriter.write(moduleDetails);
        }
        catch (TransformerException | ParserConfigurationException ex) {
            throw new IllegalStateException(
                            "Failed to write metadata into XML file for module: "
                                    + "todoComment", ex);
        }
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
}
