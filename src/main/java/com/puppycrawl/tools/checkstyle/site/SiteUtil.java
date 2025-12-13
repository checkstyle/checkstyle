///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.site;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.module.ModuleDescriptor.Version;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.maven.doxia.macro.MacroExecutionException;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.PropertyCacheFile;
import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpMultilineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineJavaCheck;
import com.puppycrawl.tools.checkstyle.internal.annotation.PreserveOrder;
import com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraperUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Utility class for site generation.
 */
public final class SiteUtil {

    /** The string 'tokens'. */
    public static final String TOKENS = "tokens";
    /** The string 'javadocTokens'. */
    public static final String JAVADOC_TOKENS = "javadocTokens";
    /** The string '.'. */
    public static final String DOT = ".";
    /** The string ','. */
    public static final String COMMA = ",";
    /** The whitespace. */
    public static final String WHITESPACE = " ";
    /** The string ', '. */
    public static final String COMMA_SPACE = COMMA + WHITESPACE;
    /** The string 'TokenTypes'. */
    public static final String TOKEN_TYPES = "TokenTypes";
    /** The path to the TokenTypes.html file. */
    public static final String PATH_TO_TOKEN_TYPES =
            "apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html";
    /** The path to the JavadocTokenTypes.html file. */
    public static final String PATH_TO_JAVADOC_TOKEN_TYPES =
            "apidocs/com/puppycrawl/tools/checkstyle/api/JavadocTokenTypes.html";
    /** The string of JavaDoc module marking 'Since version'. */
    public static final String SINCE_VERSION = "Since version";
    /** The 'Check' pattern at the end of string. */
    public static final Pattern FINAL_CHECK = Pattern.compile("Check$");
    /** The string 'fileExtensions'. */
    public static final String FILE_EXTENSIONS = "fileExtensions";
    /** The string 'charset'. */
    public static final String CHARSET = "charset";
    /** The url of the checkstyle website. */
    private static final String CHECKSTYLE_ORG_URL = "https://checkstyle.org/";
    /** The string 'checks'. */
    private static final String CHECKS = "checks";
    /** The string 'naming'. */
    private static final String NAMING = "naming";
    /** The string 'src'. */
    private static final String SRC = "src";
    /** Template file extension. */
    private static final String TEMPLATE_FILE_EXTENSION = ".xml.template";

    /** Precompiled regex pattern to remove the "Setter to " prefix from strings. */
    private static final Pattern SETTER_PATTERN = Pattern.compile("^Setter to ");

    /** Class name and their corresponding parent module name. */
    private static final Map<Class<?>, String> CLASS_TO_PARENT_MODULE = Map.ofEntries(
        Map.entry(AbstractCheck.class, TreeWalker.class.getSimpleName()),
        Map.entry(TreeWalkerFilter.class, TreeWalker.class.getSimpleName()),
        Map.entry(AbstractFileSetCheck.class, Checker.class.getSimpleName()),
        Map.entry(Filter.class, Checker.class.getSimpleName()),
        Map.entry(BeforeExecutionFileFilter.class, Checker.class.getSimpleName())
    );

    /** Set of properties that every check has. */
    private static final Set<String> CHECK_PROPERTIES =
            getProperties(AbstractCheck.class);

    /** Set of properties that every Javadoc check has. */
    private static final Set<String> JAVADOC_CHECK_PROPERTIES =
            getProperties(AbstractJavadocCheck.class);

    /** Set of properties that every FileSet check has. */
    private static final Set<String> FILESET_PROPERTIES =
            getProperties(AbstractFileSetCheck.class);

    /**
     * Check and property name.
     */
    private static final String HEADER_CHECK_HEADER = "HeaderCheck.header";

    /**
     * Check and property name.
     */
    private static final String REGEXP_HEADER_CHECK_HEADER = "RegexpHeaderCheck.header";

    /**
     * The string 'api'.
     */
    private static final String API = "api";

    /** Set of properties that are undocumented. Those are internal properties. */
    private static final Set<String> UNDOCUMENTED_PROPERTIES = Set.of(
        "SuppressWithNearbyCommentFilter.fileContents",
        "SuppressionCommentFilter.fileContents"
    );

    /** Properties that can not be gathered from class instance. */
    private static final Set<String> PROPERTIES_ALLOWED_GET_TYPES_FROM_METHOD = Set.of(
        // static field (all upper case)
        "SuppressWarningsHolder.aliasList",
        // loads string into memory similar to file
        HEADER_CHECK_HEADER,
        REGEXP_HEADER_CHECK_HEADER,
        // property is an int, but we cut off excess to accommodate old versions
        "RedundantModifierCheck.jdkVersion",
        // until https://github.com/checkstyle/checkstyle/issues/13376
        "CustomImportOrderCheck.customImportOrderRules"
    );

    /** Map of all superclasses properties and their javadocs. */
    private static final Map<String, DetailNode> SUPER_CLASS_PROPERTIES_JAVADOCS =
            new HashMap<>();

    /** Path to main source code folder. */
    private static final String MAIN_FOLDER_PATH = Path.of(
            SRC, "main", "java", "com", "puppycrawl", "tools", "checkstyle").toString();

    /** List of files who are superclasses and contain certain properties that checks inherit. */
    private static final List<Path> MODULE_SUPER_CLASS_PATHS = List.of(
        Path.of(MAIN_FOLDER_PATH, CHECKS, NAMING, "AbstractAccessControlNameCheck.java"),
        Path.of(MAIN_FOLDER_PATH, CHECKS, NAMING, "AbstractNameCheck.java"),
        Path.of(MAIN_FOLDER_PATH, CHECKS, "javadoc", "AbstractJavadocCheck.java"),
        Path.of(MAIN_FOLDER_PATH, API, "AbstractFileSetCheck.java"),
        Path.of(MAIN_FOLDER_PATH, API, "AbstractCheck.java"),
        Path.of(MAIN_FOLDER_PATH, CHECKS, "header", "AbstractHeaderCheck.java"),
        Path.of(MAIN_FOLDER_PATH, CHECKS, "metrics", "AbstractClassCouplingCheck.java"),
        Path.of(MAIN_FOLDER_PATH, CHECKS, "whitespace", "AbstractParenPadCheck.java")
    );

    /**
     * Private utility constructor.
     */
    private SiteUtil() {
    }

    /**
     * Get string values of the message keys from the given check class.
     *
     * @param module class to examine.
     * @return a set of checkstyle's module message keys.
     * @throws MacroExecutionException if extraction of message keys fails.
     */
    public static Set<String> getMessageKeys(Class<?> module)
            throws MacroExecutionException {
        final Set<Field> messageKeyFields = getCheckMessageKeysFields(module);
        // We use a TreeSet to sort the message keys alphabetically
        final Set<String> messageKeys = new TreeSet<>();
        for (Field field : messageKeyFields) {
            messageKeys.add(getFieldValue(field, module).toString());
        }
        return messageKeys;
    }

    /**
     * Gets the check's messages keys.
     *
     * @param module class to examine.
     * @return a set of checkstyle's module message fields.
     * @throws MacroExecutionException if the attempt to read a protected class fails.
     * @noinspection ChainOfInstanceofChecks
     * @noinspectionreason ChainOfInstanceofChecks - We will deal with this at
     *                     <a href="https://github.com/checkstyle/checkstyle/issues/13500">13500</a>
     *
     */
    private static Set<Field> getCheckMessageKeysFields(Class<?> module)
            throws MacroExecutionException {
        try {
            final Set<Field> checkstyleMessages = new HashSet<>();

            // get all fields from current class
            final Field[] fields = module.getDeclaredFields();

            for (Field field : fields) {
                if (field.getName().startsWith("MSG_")) {
                    checkstyleMessages.add(field);
                }
            }

            // deep scan class through hierarchy
            final Class<?> superModule = module.getSuperclass();

            if (superModule != null) {
                checkstyleMessages.addAll(getCheckMessageKeysFields(superModule));
            }

            // special cases that require additional classes
            if (module == RegexpMultilineCheck.class) {
                checkstyleMessages.addAll(getCheckMessageKeysFields(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector")));
            }
            else if (module == RegexpSinglelineCheck.class
                    || module == RegexpSinglelineJavaCheck.class) {
                checkstyleMessages.addAll(getCheckMessageKeysFields(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.SinglelineDetector")));
            }

            return checkstyleMessages;
        }
        catch (ClassNotFoundException exc) {
            final String message = String.format(Locale.ROOT, "Couldn't find class: %s",
                    module.getName());
            throw new MacroExecutionException(message, exc);
        }
    }

    /**
     * Returns the value of the given field.
     *
     * @param field the field.
     * @param instance the instance of the module.
     * @return the value of the field.
     * @throws MacroExecutionException if the value could not be retrieved.
     */
    public static Object getFieldValue(Field field, Object instance)
            throws MacroExecutionException {
        try {
            Object fieldValue = null;

            if (field != null) {
                // required for package/private classes
                field.trySetAccessible();
                fieldValue = field.get(instance);
            }

            return fieldValue;
        }
        catch (IllegalAccessException exc) {
            throw new MacroExecutionException("Couldn't get field value", exc);
        }
    }

    /**
     * Returns the instance of the module with the given name.
     *
     * @param moduleName the name of the module.
     * @return the instance of the module.
     * @throws MacroExecutionException if the module could not be created.
     */
    public static Object getModuleInstance(String moduleName) throws MacroExecutionException {
        final ModuleFactory factory = getPackageObjectFactory();
        try {
            return factory.createModule(moduleName);
        }
        catch (CheckstyleException exc) {
            throw new MacroExecutionException("Couldn't find class: " + moduleName, exc);
        }
    }

    /**
     * Returns the default PackageObjectFactory with the default package names.
     *
     * @return the default PackageObjectFactory.
     * @throws MacroExecutionException if the PackageObjectFactory cannot be created.
     */
    private static PackageObjectFactory getPackageObjectFactory() throws MacroExecutionException {
        try {
            final ClassLoader cl = SiteUtil.class.getClassLoader();
            final Set<String> packageNames = PackageNamesLoader.getPackageNames(cl);
            return new PackageObjectFactory(packageNames, cl);
        }
        catch (CheckstyleException exc) {
            throw new MacroExecutionException("Couldn't load checkstyle modules", exc);
        }
    }

    /**
     * Construct a string with a leading newline character and followed by
     * the given amount of spaces. We use this method only to match indentation in
     * regular xdocs and have minimal diff when parsing the templates.
     * This method exists until
     * <a href="https://github.com/checkstyle/checkstyle/issues/13426">13426</a>
     *
     * @param amountOfSpaces the amount of spaces to add after the newline.
     * @return the constructed string.
     */
    public static String getNewlineAndIndentSpaces(int amountOfSpaces) {
        return System.lineSeparator() + WHITESPACE.repeat(amountOfSpaces);
    }

    /**
     * Returns path to the template for the given module name or throws an exception if the
     * template cannot be found.
     *
     * @param moduleName the module whose template we are looking for.
     * @return path to the template.
     * @throws MacroExecutionException if the template cannot be found.
     */
    public static Path getTemplatePath(String moduleName) throws MacroExecutionException {
        final String fileNamePattern = ".*[\\\\/]"
                + moduleName.toLowerCase(Locale.ROOT) + "\\..*";
        return getXdocsTemplatesFilePaths()
            .stream()
            .filter(path -> path.toString().matches(fileNamePattern))
            .findFirst()
            .orElse(null);
    }

    /**
     * Gets xdocs template file paths. These are files ending with .xml.template.
     * This method will be changed to gather .xml once
     * <a href="https://github.com/checkstyle/checkstyle/issues/13426">#13426</a> is resolved.
     *
     * @return a set of xdocs template file paths.
     * @throws MacroExecutionException if an I/O error occurs.
     */
    public static Set<Path> getXdocsTemplatesFilePaths() throws MacroExecutionException {
        final Path directory = Path.of("src/site/xdoc");
        try (Stream<Path> stream = Files.find(directory, Integer.MAX_VALUE,
                (path, attr) -> {
                    return attr.isRegularFile()
                            && path.toString().endsWith(TEMPLATE_FILE_EXTENSION);
                })) {
            return stream.collect(Collectors.toUnmodifiableSet());
        }
        catch (IOException ioException) {
            throw new MacroExecutionException("Failed to find xdocs templates", ioException);
        }
    }

    /**
     * Returns the parent module name for the given module class. Returns either
     * "TreeWalker" or "Checker". Returns null if the module class is null.
     *
     * @param moduleClass the module class.
     * @return the parent module name as a string.
     * @throws MacroExecutionException if the parent module cannot be found.
     */
    public static String getParentModule(Class<?> moduleClass)
                throws MacroExecutionException {
        String parentModuleName = "";
        Class<?> parentClass = moduleClass.getSuperclass();

        while (parentClass != null) {
            parentModuleName = CLASS_TO_PARENT_MODULE.get(parentClass);
            if (parentModuleName != null) {
                break;
            }
            parentClass = parentClass.getSuperclass();
        }

        // If parent class is not found, check interfaces
        if (parentModuleName == null || parentModuleName.isEmpty()) {
            final Class<?>[] interfaces = moduleClass.getInterfaces();
            for (Class<?> interfaceClass : interfaces) {
                parentModuleName = CLASS_TO_PARENT_MODULE.get(interfaceClass);
                if (parentModuleName != null) {
                    break;
                }
            }
        }

        if (parentModuleName == null || parentModuleName.isEmpty()) {
            final String message = String.format(Locale.ROOT,
                    "Failed to find parent module for %s", moduleClass.getSimpleName());
            throw new MacroExecutionException(message);
        }

        return parentModuleName;
    }

    /**
     * Get a set of properties for the given class that should be documented.
     *
     * @param clss the class to get the properties for.
     * @param instance the instance of the module.
     * @return a set of properties for the given class.
     */
    public static Set<String> getPropertiesForDocumentation(Class<?> clss, Object instance) {
        final Set<String> properties =
                getProperties(clss).stream()
                    .filter(prop -> {
                        return !isGlobalProperty(clss, prop) && !isUndocumentedProperty(clss, prop);
                    })
                    .collect(Collectors.toCollection(HashSet::new));
        properties.addAll(getNonExplicitProperties(instance, clss));
        return new TreeSet<>(properties);
    }

    /**
     * Gets the javadoc of module class.
     *
     * @param moduleClassName name of module class.
     * @param modulePath module's path.
     * @return javadoc of module.
     * @throws MacroExecutionException if an error occurs during processing.
     */
    public static DetailNode getModuleJavadoc(String moduleClassName, Path modulePath)
            throws MacroExecutionException {

        processModule(moduleClassName, modulePath);
        return JavadocScraperResultUtil.getModuleJavadocNode();
    }

    /**
     * Get the javadocs of the properties of the module. If the property is not present in the
     * module, then the javadoc of the property from the superclass(es) is used.
     *
     * @param properties the properties of the module.
     * @param moduleName the name of the module.
     * @param modulePath the module file path.
     * @return the javadocs of the properties of the module.
     * @throws MacroExecutionException if an error occurs during processing.
     */
    public static Map<String, DetailNode> getPropertiesJavadocs(Set<String> properties,
                                                                String moduleName, Path modulePath)
            throws MacroExecutionException {
        // lazy initialization
        if (SUPER_CLASS_PROPERTIES_JAVADOCS.isEmpty()) {
            processSuperclasses();
        }

        processModule(moduleName, modulePath);

        final Map<String, DetailNode> unmodifiablePropertiesJavadocs =
                JavadocScraperResultUtil.getPropertiesJavadocNode();
        final Map<String, DetailNode> propertiesJavadocs =
            new LinkedHashMap<>(unmodifiablePropertiesJavadocs);

        properties.forEach(property -> {
            final DetailNode superClassPropertyJavadoc =
                    SUPER_CLASS_PROPERTIES_JAVADOCS.get(property);
            if (superClassPropertyJavadoc != null) {
                propertiesJavadocs.putIfAbsent(property, superClassPropertyJavadoc);
            }
        });

        assertAllPropertySetterJavadocsAreFound(properties, moduleName, propertiesJavadocs);

        return propertiesJavadocs;
    }

    /**
     * Assert that each property has a corresponding setter javadoc that is not null.
     * 'tokens' and 'javadocTokens' are excluded from this check, because their
     * description is different from the description of the setter.
     *
     * @param properties the properties of the module.
     * @param moduleName the name of the module.
     * @param javadocs the javadocs of the properties of the module.
     * @throws MacroExecutionException if an error occurs during processing.
     */
    private static void assertAllPropertySetterJavadocsAreFound(
            Set<String> properties, String moduleName, Map<String, DetailNode> javadocs)
            throws MacroExecutionException {
        for (String property : properties) {
            final boolean isDocumented = javadocs.containsKey(property)
                   || SUPER_CLASS_PROPERTIES_JAVADOCS.containsKey(property)
                   || TOKENS.equals(property) || JAVADOC_TOKENS.equals(property);
            if (!isDocumented) {
                throw new MacroExecutionException(String.format(Locale.ROOT,
                   "%s: Missing documentation for property '%s'. Check superclasses.",
                        moduleName, property));
            }
        }
    }

    /**
     * Collect the properties setters javadocs of the superclasses.
     *
     * @throws MacroExecutionException if an error occurs during processing.
     */
    private static void processSuperclasses() throws MacroExecutionException {
        for (Path superclassPath : MODULE_SUPER_CLASS_PATHS) {
            final Path fileNamePath = superclassPath.getFileName();
            if (fileNamePath == null) {
                throw new MacroExecutionException("Invalid superclass path: " + superclassPath);
            }
            final String superclassName = CommonUtil.getFileNameWithoutExtension(
                fileNamePath.toString());
            processModule(superclassName, superclassPath);
            final Map<String, DetailNode> superclassPropertiesJavadocs =
                JavadocScraperResultUtil.getPropertiesJavadocNode();
            SUPER_CLASS_PROPERTIES_JAVADOCS.putAll(superclassPropertiesJavadocs);
        }
    }

    /**
     * Scrape the Javadocs of the class and its properties setters with
     * ClassAndPropertiesSettersJavadocScraper.
     *
     * @param moduleName the name of the module.
     * @param modulePath the module Path.
     * @throws MacroExecutionException if an error occurs during processing.
     */
    private static void processModule(String moduleName, Path modulePath)
            throws MacroExecutionException {
        if (!Files.isRegularFile(modulePath)) {
            final String message = String.format(Locale.ROOT,
                    "File %s is not a file. Please check the 'modulePath' property.", modulePath);
            throw new MacroExecutionException(message);
        }
        ClassAndPropertiesSettersJavadocScraper.initialize(moduleName);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        final DefaultConfiguration scraperCheckConfig =
                        new DefaultConfiguration(
                                ClassAndPropertiesSettersJavadocScraper.class.getName());
        final DefaultConfiguration defaultConfiguration =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration treeWalkerConfig =
                new DefaultConfiguration(TreeWalker.class.getName());
        defaultConfiguration.addProperty(CHARSET, StandardCharsets.UTF_8.name());
        defaultConfiguration.addChild(treeWalkerConfig);
        treeWalkerConfig.addChild(scraperCheckConfig);
        try {
            checker.configure(defaultConfiguration);
            final List<File> filesToProcess = List.of(modulePath.toFile());
            checker.process(filesToProcess);
            checker.destroy();
        }
        catch (CheckstyleException checkstyleException) {
            final String message = String.format(Locale.ROOT, "Failed processing %s", moduleName);
            throw new MacroExecutionException(message, checkstyleException);
        }
    }

    /**
     * Get a set of properties for the given class.
     *
     * @param clss the class to get the properties for.
     * @return a set of properties for the given class.
     */
    public static Set<String> getProperties(Class<?> clss) {
        final Set<String> result = new TreeSet<>();
        final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(clss);

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getWriteMethod() != null) {
                result.add(propertyDescriptor.getName());
            }
        }

        return result;
    }

    /**
     * Checks if the property is a global property. Global properties come from the base classes
     * and are common to all checks. For example id, severity, tabWidth, etc.
     *
     * @param clss the class of the module.
     * @param propertyName the name of the property.
     * @return true if the property is a global property.
     */
    private static boolean isGlobalProperty(Class<?> clss, String propertyName) {
        return AbstractCheck.class.isAssignableFrom(clss)
                    && CHECK_PROPERTIES.contains(propertyName)
                || AbstractJavadocCheck.class.isAssignableFrom(clss)
                    && JAVADOC_CHECK_PROPERTIES.contains(propertyName)
                || AbstractFileSetCheck.class.isAssignableFrom(clss)
                    && FILESET_PROPERTIES.contains(propertyName);
    }

    /**
     * Checks if the property is supposed to be documented.
     *
     * @param clss the class of the module.
     * @param propertyName the name of the property.
     * @return true if the property is supposed to be documented.
     */
    private static boolean isUndocumentedProperty(Class<?> clss, String propertyName) {
        return UNDOCUMENTED_PROPERTIES.contains(clss.getSimpleName() + DOT + propertyName);
    }

    /**
     * Gets properties that are not explicitly captured but should be documented if
     * certain conditions are met.
     *
     * @param instance the instance of the module.
     * @param clss the class of the module.
     * @return the non explicit properties.
     */
    private static Set<String> getNonExplicitProperties(
            Object instance, Class<?> clss) {
        final Set<String> result = new TreeSet<>();
        if (AbstractCheck.class.isAssignableFrom(clss)) {
            final AbstractCheck check = (AbstractCheck) instance;

            final int[] acceptableTokens = check.getAcceptableTokens();
            Arrays.sort(acceptableTokens);
            final int[] defaultTokens = check.getDefaultTokens();
            Arrays.sort(defaultTokens);
            final int[] requiredTokens = check.getRequiredTokens();
            Arrays.sort(requiredTokens);

            if (!Arrays.equals(acceptableTokens, defaultTokens)
                    || !Arrays.equals(acceptableTokens, requiredTokens)) {
                result.add(TOKENS);
            }
        }

        if (AbstractJavadocCheck.class.isAssignableFrom(clss)) {
            final AbstractJavadocCheck check = (AbstractJavadocCheck) instance;
            result.add("violateExecutionOnNonTightHtml");

            final int[] acceptableJavadocTokens = check.getAcceptableJavadocTokens();
            Arrays.sort(acceptableJavadocTokens);
            final int[] defaultJavadocTokens = check.getDefaultJavadocTokens();
            Arrays.sort(defaultJavadocTokens);
            final int[] requiredJavadocTokens = check.getRequiredJavadocTokens();
            Arrays.sort(requiredJavadocTokens);

            if (!Arrays.equals(acceptableJavadocTokens, defaultJavadocTokens)
                    || !Arrays.equals(acceptableJavadocTokens, requiredJavadocTokens)) {
                result.add(JAVADOC_TOKENS);
            }
        }

        if (AbstractFileSetCheck.class.isAssignableFrom(clss)) {
            result.add(FILE_EXTENSIONS);
        }
        return result;
    }

    /**
     * Get the description of the property.
     *
     * @param propertyName the name of the property.
     * @param javadoc the Javadoc of the property setter method.
     * @param moduleName the name of the module.
     * @return the description of the property.
     * @throws MacroExecutionException if the description could not be extracted.
     */
    public static String getPropertyDescriptionForXdoc(
            String propertyName, DetailNode javadoc, String moduleName)
            throws MacroExecutionException {
        final String description;
        if (TOKENS.equals(propertyName)) {
            description = "tokens to check";
        }
        else if (JAVADOC_TOKENS.equals(propertyName)) {
            description = "javadoc tokens to check";
        }
        else {
            final String descriptionString = SETTER_PATTERN.matcher(
                    getDescriptionFromJavadocForXdoc(javadoc, moduleName))
                    .replaceFirst("");

            final String firstLetterCapitalized = descriptionString.substring(0, 1)
                    .toUpperCase(Locale.ROOT);
            description = firstLetterCapitalized + descriptionString.substring(1);
        }
        return description;
    }

    /**
     * Get the since version of the property.
     *
     * @param moduleName the name of the module.
     * @param moduleJavadoc the Javadoc of the module.
     * @param propertyJavadoc the Javadoc of the property setter method.
     * @return the since version of the property.
     * @throws MacroExecutionException if the module since version could not be extracted.
     */
    public static String getPropertySinceVersion(String moduleName, DetailNode moduleJavadoc,
                                                 DetailNode propertyJavadoc)
            throws MacroExecutionException {
        final String sinceVersion;

        final Optional<String> specifiedPropertyVersionInPropertyJavadoc =
            getPropertyVersionFromItsJavadoc(propertyJavadoc);

        if (specifiedPropertyVersionInPropertyJavadoc.isPresent()) {
            sinceVersion = specifiedPropertyVersionInPropertyJavadoc.get();
        }
        else {
            final String moduleSince = getSinceVersionFromJavadoc(moduleJavadoc);

            if (moduleSince == null) {
                throw new MacroExecutionException(
                        "Missing @since on module " + moduleName);
            }

            String propertySetterSince = null;
            if (propertyJavadoc != null) {
                propertySetterSince = getSinceVersionFromJavadoc(propertyJavadoc);
            }

            if (propertySetterSince != null
                    && isVersionAtLeast(propertySetterSince, moduleSince)) {
                sinceVersion = propertySetterSince;
            }
            else {
                sinceVersion = moduleSince;
            }
        }

        return sinceVersion;
    }

    /**
     * Extract the property since version from its Javadoc.
     *
     * @param propertyJavadoc the property Javadoc to extract the since version from.
     * @return the Optional of property version specified in its javadoc.
     */
    @Nullable
    private static Optional<String> getPropertyVersionFromItsJavadoc(DetailNode propertyJavadoc) {
        final Optional<DetailNode> propertyJavadocTag =
            getPropertySinceJavadocTag(propertyJavadoc);

        return propertyJavadocTag
            .map(tag -> JavadocUtil.findFirstToken(tag, JavadocCommentsTokenTypes.DESCRIPTION))
            .map(description -> {
                return JavadocUtil.findFirstToken(description, JavadocCommentsTokenTypes.TEXT);
            })
            .map(DetailNode::getText)
            .map(String::trim);
    }

    /**
     * Find the propertySince Javadoc tag node in the given property Javadoc.
     *
     * @param javadoc the Javadoc to search.
     * @return the Optional of propertySince Javadoc tag node or null if not found.
     */
    private static Optional<DetailNode> getPropertySinceJavadocTag(DetailNode javadoc) {
        Optional<DetailNode> propertySinceJavadocTag = Optional.empty();
        DetailNode child = javadoc.getFirstChild();

        while (child != null) {
            if (child.getType() == JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG) {
                final DetailNode customBlockTag = JavadocUtil.findFirstToken(
                        child, JavadocCommentsTokenTypes.CUSTOM_BLOCK_TAG);

                if (customBlockTag != null
                        && "propertySince".equals(JavadocUtil.findFirstToken(
                            customBlockTag, JavadocCommentsTokenTypes.TAG_NAME).getText())) {
                    propertySinceJavadocTag = Optional.of(customBlockTag);
                    break;
                }
            }
            child = child.getNextSibling();
        }

        return propertySinceJavadocTag;
    }

    /**
     * Gets all javadoc nodes of selected type.
     *
     * @param allNodes Nodes to choose from.
     * @param neededType the Javadoc token type to select.
     * @return the List of DetailNodes of selected type.
     */
    public static List<DetailNode> getNodesOfSpecificType(DetailNode[] allNodes, int neededType) {
        return Arrays.stream(allNodes)
            .filter(child -> child.getType() == neededType)
            .toList();
    }

    /**
     * Extract the since version from the Javadoc.
     *
     * @param javadoc the Javadoc to extract the since version from.
     * @return the since version of the setter.
     */
    @Nullable
    private static String getSinceVersionFromJavadoc(DetailNode javadoc) {
        final DetailNode sinceJavadocTag = getSinceJavadocTag(javadoc);
        return Optional.ofNullable(sinceJavadocTag)
            .map(tag -> JavadocUtil.findFirstToken(tag, JavadocCommentsTokenTypes.DESCRIPTION))
            .map(description -> {
                return JavadocUtil.findFirstToken(
                        description, JavadocCommentsTokenTypes.TEXT);
            })
            .map(DetailNode::getText)
            .map(String::trim)
            .orElse(null);
    }

    /**
     * Find the since Javadoc tag node in the given Javadoc.
     *
     * @param javadoc the Javadoc to search.
     * @return the since Javadoc tag node or null if not found.
     */
    private static DetailNode getSinceJavadocTag(DetailNode javadoc) {
        DetailNode child = javadoc.getFirstChild();
        DetailNode javadocTagWithSince = null;

        while (child != null) {
            if (child.getType() == JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG) {
                final DetailNode sinceNode = JavadocUtil.findFirstToken(
                        child, JavadocCommentsTokenTypes.SINCE_BLOCK_TAG);

                if (sinceNode != null) {
                    javadocTagWithSince = sinceNode;
                    break;
                }
            }
            child = child.getNextSibling();
        }

        return javadocTagWithSince;
    }

    /**
     * Returns {@code true} if {@code actualVersion} ≥ {@code requiredVersion}.
     * Both versions have any trailing "-SNAPSHOT" stripped before comparison.
     *
     * @param actualVersion   e.g. "8.3" or "8.3-SNAPSHOT"
     * @param requiredVersion e.g. "8.3"
     * @return {@code true} if actualVersion exists, and, numerically, is at least requiredVersion
     */
    private static boolean isVersionAtLeast(String actualVersion,
                                            String requiredVersion) {
        final Version actualVersionParsed = Version.parse(actualVersion);
        final Version requiredVersionParsed = Version.parse(requiredVersion);

        return actualVersionParsed.compareTo(requiredVersionParsed) >= 0;
    }

    /**
     * Get the type of the property.
     *
     * @param field the field to get the type of.
     * @param propertyName the name of the property.
     * @param moduleName the name of the module.
     * @param instance the instance of the module.
     * @return the type of the property.
     * @throws MacroExecutionException if an error occurs during getting the type.
     */
    public static String getType(Field field, String propertyName,
                                 String moduleName, Object instance)
            throws MacroExecutionException {
        final Class<?> fieldClass = getFieldClass(field, propertyName, moduleName, instance);
        return Optional.ofNullable(field)
                .map(nonNullField -> nonNullField.getAnnotation(XdocsPropertyType.class))
                .filter(propertyType -> propertyType.value() != PropertyType.TOKEN_ARRAY)
                .map(propertyType -> propertyType.value().getDescription())
                .orElseGet(fieldClass::getTypeName);
    }

    /**
     * Get the default value of the property.
     *
     * @param propertyName the name of the property.
     * @param field the field to get the default value of.
     * @param classInstance the instance of the class to get the default value of.
     * @param moduleName the name of the module.
     * @return the default value of the property.
     * @throws MacroExecutionException if an error occurs during getting the default value.
     * @noinspection IfStatementWithTooManyBranches
     * @noinspectionreason IfStatementWithTooManyBranches - complex nature of getting properties
     *      from XML files requires giant if/else statement
     */
    // -@cs[CyclomaticComplexity] Splitting would not make the code more readable
    public static String getDefaultValue(String propertyName, Field field,
                                         Object classInstance, String moduleName)
            throws MacroExecutionException {
        final Object value = getFieldValue(field, classInstance);
        final Class<?> fieldClass = getFieldClass(field, propertyName, moduleName, classInstance);
        String result = null;

        if (classInstance instanceof PropertyCacheFile) {
            result = "null (no cache file)";
        }
        else if (fieldClass == boolean.class
                || fieldClass == int.class
                || fieldClass == URI.class
                || fieldClass == String.class) {
            if (value != null) {
                result = value.toString();
            }
        }
        else if (fieldClass == int[].class
                || ModuleJavadocParsingUtil.isPropertySpecialTokenProp(field)) {
            result = getIntArrayPropertyValue(value);
        }
        else if (fieldClass == double[].class) {
            result = removeSquareBrackets(Arrays.toString((double[]) value).replace(".0", ""));
        }
        else if (fieldClass == String[].class) {
            final boolean preserveOrder = hasPreserveOrderAnnotation(field);
            result = getStringArrayPropertyValue(value, preserveOrder);
        }
        else if (fieldClass == Pattern.class) {
            if (value != null) {
                result = value.toString().replace("\n", "\\n").replace("\t", "\\t")
                        .replace("\r", "\\r").replace("\f", "\\f");
            }
        }
        else if (fieldClass == Pattern[].class) {
            result = getPatternArrayPropertyValue(value);
        }
        else if (fieldClass.isEnum()) {
            if (value != null) {
                result = value.toString().toLowerCase(Locale.ENGLISH);
            }
        }
        else if (fieldClass == AccessModifierOption[].class) {
            result = removeSquareBrackets(Arrays.toString((Object[]) value));
        }

        if (result == null) {
            result = "null";
        }

        return result;
    }

    /**
     * Checks if a field has the {@code PreserveOrder} annotation.
     *
     * @param field the field to check
     * @return true if the field has {@code PreserveOrder} annotation, false otherwise
     */
    private static boolean hasPreserveOrderAnnotation(Field field) {
        return field != null && field.isAnnotationPresent(PreserveOrder.class);
    }

    /**
     * Gets the name of the bean property's default value for the Pattern array class.
     *
     * @param fieldValue The bean property's value
     * @return String form of property's default value
     */
    private static String getPatternArrayPropertyValue(Object fieldValue) {
        Object value = fieldValue;
        if (value instanceof Collection<?> collection) {
            value = collection.stream()
                    .map(Pattern.class::cast)
                    .toArray(Pattern[]::new);
        }

        String result = "";
        if (value != null && Array.getLength(value) > 0) {
            result = removeSquareBrackets(
                    Arrays.stream((Pattern[]) value)
                    .map(Pattern::pattern)
                    .collect(Collectors.joining(COMMA_SPACE)));
        }

        return result;
    }

    /**
     * Removes square brackets [ and ] from the given string.
     *
     * @param value the string to remove square brackets from.
     * @return the string without square brackets.
     */
    private static String removeSquareBrackets(String value) {
        return value
                .replace("[", "")
                .replace("]", "");
    }

    /**
     * Gets the name of the bean property's default value for the string array class.
     *
     * @param value The bean property's value
     * @param preserveOrder whether to preserve the original order
     * @return String form of property's default value
     */
    private static String getStringArrayPropertyValue(Object value, boolean preserveOrder) {
        final String result;
        if (value == null) {
            result = "";
        }
        else {
            try (Stream<?> valuesStream = getValuesStream(value)) {
                final List<String> stringList = valuesStream
                    .map(String.class::cast)
                    .collect(Collectors.toCollection(ArrayList<String>::new));

                if (preserveOrder) {
                    result = String.join(COMMA_SPACE, stringList);
                }
                else {
                    result = stringList.stream()
                    .sorted()
                    .collect(Collectors.joining(COMMA_SPACE));
                }
            }
        }
        return result;
    }

    /**
     * Generates a stream of values from the given value.
     *
     * @param value the value to generate the stream from.
     * @return the stream of values.
     */
    private static Stream<?> getValuesStream(Object value) {
        final Stream<?> valuesStream;
        if (value instanceof Collection<?> collection) {
            valuesStream = collection.stream();
        }
        else {
            final Object[] array = (Object[]) value;
            valuesStream = Arrays.stream(array);
        }
        return valuesStream;
    }

    /**
     * Returns the name of the bean property's default value for the int array class.
     *
     * @param value The bean property's value.
     * @return String form of property's default value.
     */
    private static String getIntArrayPropertyValue(Object value) {
        try (IntStream stream = getIntStream(value)) {
            return stream
                    .mapToObj(TokenUtil::getTokenName)
                    .sorted()
                    .collect(Collectors.joining(COMMA_SPACE));
        }
    }

    /**
     * Get the int stream from the given value.
     *
     * @param value the value to get the int stream from.
     * @return the int stream.
     * @noinspection ChainOfInstanceofChecks
     * @noinspectionreason ChainOfInstanceofChecks - We will deal with this at
     *                     <a href="https://github.com/checkstyle/checkstyle/issues/13500">13500</a>
     */
    private static IntStream getIntStream(Object value) {
        final IntStream stream;
        if (value instanceof Collection<?> collection) {
            stream = collection.stream()
                    .mapToInt(int.class::cast);
        }
        else if (value instanceof BitSet set) {
            stream = set.stream();
        }
        else {
            stream = Arrays.stream((int[]) value);
        }
        return stream;
    }

    /**
     * Gets the class of the given field.
     *
     * @param field the field to get the class of.
     * @param propertyName the name of the property.
     * @param moduleName the name of the module.
     * @param instance the instance of the module.
     * @return the class of the field.
     * @throws MacroExecutionException if an error occurs during getting the class.
     */
    // -@cs[CyclomaticComplexity] Splitting would not make the code more readable
    // -@cs[ForbidWildcardAsReturnType] Implied by design to return different types
    public static Class<?> getFieldClass(Field field, String propertyName,
                                          String moduleName, Object instance)
            throws MacroExecutionException {
        Class<?> result = null;

        if (PROPERTIES_ALLOWED_GET_TYPES_FROM_METHOD
                .contains(moduleName + DOT + propertyName)) {
            result = getPropertyClass(propertyName, instance);
        }
        if (ModuleJavadocParsingUtil.isPropertySpecialTokenProp(field)) {
            result = String[].class;
        }
        if (field != null && result == null) {
            result = field.getType();
        }

        if (result == null) {
            throw new MacroExecutionException(
                    "Could not find field " + propertyName + " in class " + moduleName);
        }

        if (field != null && (result == List.class || result == Set.class)) {
            final ParameterizedType type = (ParameterizedType) field.getGenericType();
            final Class<?> parameterClass = (Class<?>) type.getActualTypeArguments()[0];

            if (parameterClass == Integer.class) {
                result = int[].class;
            }
            else if (parameterClass == String.class) {
                result = String[].class;
            }
            else if (parameterClass == Pattern.class) {
                result = Pattern[].class;
            }
            else {
                final String message = "Unknown parameterized type: "
                        + parameterClass.getSimpleName();
                throw new MacroExecutionException(message);
            }
        }
        else if (result == BitSet.class) {
            result = int[].class;
        }

        return result;
    }

    /**
     * Gets the class of the given java property.
     *
     * @param propertyName the name of the property.
     * @param instance the instance of the module.
     * @return the class of the java property.
     * @throws MacroExecutionException if an error occurs during getting the class.
     */
    // -@cs[ForbidWildcardAsReturnType] Object is received as param, no prediction on type of field
    public static Class<?> getPropertyClass(String propertyName, Object instance)
            throws MacroExecutionException {
        final Class<?> result;
        try {
            final PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(instance,
                    propertyName);
            result = descriptor.getPropertyType();
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exc) {
            throw new MacroExecutionException(exc.getMessage(), exc);
        }
        return result;
    }

    /**
     * Get the difference between two lists of tokens.
     *
     * @param tokens the list of tokens to remove from.
     * @param subtractions the tokens to remove.
     * @return the difference between the two lists.
     */
    public static List<Integer> getDifference(int[] tokens, int... subtractions) {
        final Set<Integer> subtractionsSet = Arrays.stream(subtractions)
                .boxed()
                .collect(Collectors.toUnmodifiableSet());
        return Arrays.stream(tokens)
                .boxed()
                .filter(token -> !subtractionsSet.contains(token))
                .toList();
    }

    /**
     * Gets the field with the given name from the given class.
     *
     * @param fieldClass the class to get the field from.
     * @param propertyName the name of the field.
     * @return the field we are looking for.
     */
    public static Field getField(Class<?> fieldClass, String propertyName) {
        Field result = null;
        Class<?> currentClass = fieldClass;

        while (!Object.class.equals(currentClass)) {
            try {
                result = currentClass.getDeclaredField(propertyName);
                result.trySetAccessible();
                break;
            }
            catch (NoSuchFieldException ignored) {
                currentClass = currentClass.getSuperclass();
            }
        }

        return result;
    }

    /**
     * Constructs string with relative link to the provided document.
     *
     * @param moduleName the name of the module.
     * @param document the path of the document.
     * @return relative link to the document.
     * @throws MacroExecutionException if link to the document cannot be constructed.
     */
    public static String getLinkToDocument(String moduleName, String document)
            throws MacroExecutionException {
        final Path templatePath = getTemplatePath(FINAL_CHECK.matcher(moduleName).replaceAll(""));
        if (templatePath == null) {
            throw new MacroExecutionException(
                    String.format(Locale.ROOT,
                            "Could not find template for %s", moduleName));
        }
        final Path templatePathParent = templatePath.getParent();
        if (templatePathParent == null) {
            throw new MacroExecutionException("Failed to get parent path for " + templatePath);
        }
        return templatePathParent
                .relativize(Path.of(SRC, "site/xdoc", document))
                .toString()
                .replace(".xml", ".html")
                .replace('\\', '/');
    }

    /**
     * Get all templates whose content contains properties macro.
     *
     * @return templates whose content contains properties macro.
     * @throws CheckstyleException if file could not be read.
     * @throws MacroExecutionException if template file is not found.
     */
    public static List<Path> getTemplatesThatContainPropertiesMacro()
            throws CheckstyleException, MacroExecutionException {
        final List<Path> result = new ArrayList<>();
        final Set<Path> templatesPaths = getXdocsTemplatesFilePaths();
        for (Path templatePath: templatesPaths) {
            final String content = getFileContents(templatePath);
            final String propertiesMacroDefinition = "<macro name=\"properties\"";
            if (content.contains(propertiesMacroDefinition)) {
                result.add(templatePath);
            }
        }
        return result;
    }

    /**
     * Get file contents as string.
     *
     * @param pathToFile path to file.
     * @return file contents as string.
     * @throws CheckstyleException if file could not be read.
     */
    private static String getFileContents(Path pathToFile) throws CheckstyleException {
        final String content;
        try {
            content = Files.readString(pathToFile);
        }
        catch (IOException ioException) {
            final String message = String.format(Locale.ROOT, "Failed to read file: %s",
                    pathToFile);
            throw new CheckstyleException(message, ioException);
        }
        return content;
    }

    /**
     * Get the module name from the file. The module name is the file name without the extension.
     *
     * @param file file to extract the module name from.
     * @return module name.
     */
    public static String getModuleName(File file) {
        final String fullFileName = file.getName();
        return CommonUtil.getFileNameWithoutExtension(fullFileName);
    }

    /**
     * Extracts the description from the javadoc detail node. Performs a DFS traversal on the
     * detail node and extracts the text nodes. This description is additionally processed to
     * fit Xdoc format.
     *
     * @param javadoc the Javadoc to extract the description from.
     * @param moduleName the name of the module.
     * @return the description of the setter.
     * @throws MacroExecutionException if the description could not be extracted.
     */
    // -@cs[NPathComplexity] Splitting would not make the code more readable
    // -@cs[CyclomaticComplexity] Splitting would not make the code more readable.
    // -@cs[ExecutableStatementCount] Splitting would not make the code more readable.
    private static String getDescriptionFromJavadocForXdoc(DetailNode javadoc, String moduleName)
            throws MacroExecutionException {
        boolean isInCodeLiteral = false;
        boolean isInHtmlElement = false;
        boolean isInHrefAttribute = false;
        final StringBuilder description = new StringBuilder(128);
        final List<DetailNode> descriptionNodes = getFirstJavadocParagraphNodes(javadoc);
        DetailNode node = descriptionNodes.get(0);
        final DetailNode endNode = descriptionNodes.get(descriptionNodes.size() - 1);

        while (node != null) {
            if (node.getType() == JavadocCommentsTokenTypes.TAG_ATTR_NAME
                    && "href".equals(node.getText())) {
                isInHrefAttribute = true;
            }
            if (isInHrefAttribute && node.getType()
                     == JavadocCommentsTokenTypes.ATTRIBUTE_VALUE) {
                final String href = node.getText();
                if (href.contains(CHECKSTYLE_ORG_URL)) {
                    DescriptionExtractor.handleInternalLink(description, moduleName, href);
                }
                else {
                    description.append(href);
                }

                isInHrefAttribute = false;
            }
            else {
                if (node.getType() == JavadocCommentsTokenTypes.HTML_ELEMENT) {
                    isInHtmlElement = true;
                }
                if (node.getType() == JavadocCommentsTokenTypes.TAG_CLOSE
                        && node.getParent().getType() == JavadocCommentsTokenTypes.HTML_TAG_END) {
                    description.append(node.getText());
                    isInHtmlElement = false;
                }
                if (node.getType() == JavadocCommentsTokenTypes.TEXT
                        // If a node has children, its text is not part of the description
                        || isInHtmlElement && node.getFirstChild() == null
                            // Some HTML elements span multiple lines, so we avoid the asterisk
                            && node.getType() != JavadocCommentsTokenTypes.LEADING_ASTERISK) {
                    if (isInCodeLiteral) {
                        description.append(node.getText().trim());
                    }
                    else {
                        description.append(node.getText());
                    }
                }
                if (node.getType() == JavadocCommentsTokenTypes.TAG_NAME
                        && node.getParent().getType()
                                  == JavadocCommentsTokenTypes.CODE_INLINE_TAG) {
                    isInCodeLiteral = true;
                    description.append("<code>");
                }
                if (isInCodeLiteral
                        && node.getType() == JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG_END) {
                    isInCodeLiteral = false;
                    description.append("</code>");
                }

            }

            DetailNode toVisit = node.getFirstChild();
            while (node != endNode && toVisit == null) {
                toVisit = node.getNextSibling();
                node = node.getParent();
            }

            node = toVisit;
        }

        return description.toString().trim();
    }

    /**
     * Get 1st paragraph from the Javadoc with no additional processing.
     *
     * @param javadoc the Javadoc to extract first paragraph from.
     * @return first paragraph of javadoc.
     */
    public static String getFirstParagraphFromJavadoc(DetailNode javadoc) {
        final String result;
        final List<DetailNode> firstParagraphNodes = getFirstJavadocParagraphNodes(javadoc);
        if (firstParagraphNodes.isEmpty()) {
            result = "";
        }
        else {
            final DetailNode startNode = firstParagraphNodes.get(0);
            final DetailNode endNode = firstParagraphNodes.get(firstParagraphNodes.size() - 1);
            result = JavadocMetadataScraperUtil.constructSubTreeText(startNode, endNode);
        }
        return result;
    }

    /**
     * Extracts first paragraph nodes from javadoc.
     *
     * @param javadoc the Javadoc to extract the description from.
     * @return the first paragraph nodes of the setter.
     */
    public static List<DetailNode> getFirstJavadocParagraphNodes(DetailNode javadoc) {
        final List<DetailNode> firstParagraphNodes = new ArrayList<>();

        for (DetailNode child = javadoc.getFirstChild();
                child != null; child = child.getNextSibling()) {
            if (isEndOfFirstJavadocParagraph(child)) {
                break;
            }
            firstParagraphNodes.add(child);
        }
        return firstParagraphNodes;
    }

    /**
     * Determines if the given child index is the end of the first Javadoc paragraph. The end
     * of the description is defined as 4 consecutive nodes of type NEWLINE, LEADING_ASTERISK,
     * NEWLINE, LEADING_ASTERISK. This is an asterisk that is alone on a line. Just like the
     * one below this line.
     *
     * @param child the child to check.
     * @return true if the given child index is the end of the first javadoc paragraph.
     */
    public static boolean isEndOfFirstJavadocParagraph(DetailNode child) {
        final DetailNode nextSibling = child.getNextSibling();
        final DetailNode secondNextSibling = nextSibling.getNextSibling();
        final DetailNode thirdNextSibling = secondNextSibling.getNextSibling();

        return child.getType() == JavadocCommentsTokenTypes.NEWLINE
                    && nextSibling.getType() == JavadocCommentsTokenTypes.LEADING_ASTERISK
                    && secondNextSibling.getType() == JavadocCommentsTokenTypes.NEWLINE
                    && thirdNextSibling.getType() == JavadocCommentsTokenTypes.LEADING_ASTERISK;
    }

    /**
     * Simplifies type name just to the name of the class, rather than entire package.
     *
     * @param fullTypeName full type name.
     * @return simplified type name, that is, name of the class.
     */
    public static String simplifyTypeName(String fullTypeName) {
        final int simplifiedStartIndex;

        if (fullTypeName.contains("$")) {
            simplifiedStartIndex = fullTypeName.lastIndexOf('$') + 1;
        }
        else {
            simplifiedStartIndex = fullTypeName.lastIndexOf('.') + 1;
        }

        return fullTypeName.substring(simplifiedStartIndex);
    }

    /** Utility class for extracting description from a method's Javadoc. */
    private static final class DescriptionExtractor {

        /**
         * Converts the href value to a relative link to the document and appends it to the
         * description.
         *
         * @param description the description to append the relative link to.
         * @param moduleName the name of the module.
         * @param value the href value.
         * @throws MacroExecutionException if the relative link could not be created.
         */
        private static void handleInternalLink(StringBuilder description,
                                               String moduleName, String value)
                throws MacroExecutionException {
            String href = value;
            href = href.replace(CHECKSTYLE_ORG_URL, "");
            // Remove first and last characters, they are always double quotes
            href = href.substring(1, href.length() - 1);

            final String relativeHref = getLinkToDocument(moduleName, href);
            final char doubleQuote = '\"';
            description.append(doubleQuote).append(relativeHref).append(doubleQuote);
        }
    }
}
