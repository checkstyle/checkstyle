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
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Deque;
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

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.PropertyCacheFile;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpMultilineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineJavaCheck;
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
    /** The string ', '. */
    public static final String COMMA_SPACE = ", ";
    /** The string 'TokenTypes'. */
    public static final String TOKEN_TYPES = "TokenTypes";
    /** The path to the TokenTypes.html file. */
    public static final String PATH_TO_TOKEN_TYPES =
            "apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html";
    /** The path to the JavadocTokenTypes.html file. */
    public static final String PATH_TO_JAVADOC_TOKEN_TYPES =
            "apidocs/com/puppycrawl/tools/checkstyle/api/JavadocTokenTypes.html";
    /** The url of the checkstyle website. */
    private static final String CHECKSTYLE_ORG_URL = "https://checkstyle.org/";
    /** The string 'charset'. */
    private static final String CHARSET = "charset";
    /** The string '{}'. */
    private static final String CURLY_BRACKETS = "{}";
    /** The string 'fileExtensions'. */
    private static final String FILE_EXTENSIONS = "fileExtensions";
    /** The string 'checks'. */
    private static final String CHECKS = "checks";
    /** The string 'naming'. */
    private static final String NAMING = "naming";
    /** The string 'src'. */
    private static final String SRC = "src";

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

    /**
     * Frequent version.
     */
    private static final String VERSION_6_9 = "6.9";

    /**
     * Frequent version.
     */
    private static final String VERSION_5_0 = "5.0";

    /**
     * Frequent version.
     */
    private static final String VERSION_3_2 = "3.2";

    /**
     * Frequent version.
     */
    private static final String VERSION_8_24 = "8.24";

    /**
     * Frequent version.
     */
    private static final String VERSION_8_36 = "8.36";

    /**
     * Frequent version.
     */
    private static final String VERSION_3_0 = "3.0";

    /**
     * Frequent version.
     */
    private static final String VERSION_7_7 = "7.7";

    /**
     * Frequent version.
     */
    private static final String VERSION_5_7 = "5.7";

    /**
     * Frequent version.
     */
    private static final String VERSION_5_1 = "5.1";

    /**
     * Frequent version.
     */
    private static final String VERSION_3_4 = "3.4";

    /**
     * Map of properties whose since version is different from module version but
     * are not specified in code because they are inherited from their super class(es).
     * Until <a href="https://github.com/checkstyle/checkstyle/issues/14052">#14052</a>.
     *
     * @noinspection JavacQuirks
     * @noinspectionreason JavacQuirks until #14052
     */
    private static final Map<String, String> SINCE_VERSION_FOR_INHERITED_PROPERTY = Map.ofEntries(
        Map.entry("MissingDeprecatedCheck.violateExecutionOnNonTightHtml", VERSION_8_24),
        Map.entry("NonEmptyAtclauseDescriptionCheck.violateExecutionOnNonTightHtml", "8.3"),
        Map.entry("HeaderCheck.charset", VERSION_5_0),
        Map.entry("HeaderCheck.fileExtensions", VERSION_6_9),
        Map.entry("HeaderCheck.headerFile", VERSION_3_2),
        Map.entry(HEADER_CHECK_HEADER, VERSION_5_0),
        Map.entry("RegexpHeaderCheck.charset", VERSION_5_0),
        Map.entry("RegexpHeaderCheck.fileExtensions", VERSION_6_9),
        Map.entry("RegexpHeaderCheck.headerFile", VERSION_3_2),
        Map.entry(REGEXP_HEADER_CHECK_HEADER, VERSION_5_0),
        Map.entry("ClassDataAbstractionCouplingCheck.excludeClassesRegexps", VERSION_7_7),
        Map.entry("ClassDataAbstractionCouplingCheck.excludedClasses", VERSION_5_7),
        Map.entry("ClassDataAbstractionCouplingCheck.excludedPackages", VERSION_7_7),
        Map.entry("ClassDataAbstractionCouplingCheck.max", VERSION_3_4),
        Map.entry("ClassFanOutComplexityCheck.excludeClassesRegexps", VERSION_7_7),
        Map.entry("ClassFanOutComplexityCheck.excludedClasses", VERSION_5_7),
        Map.entry("ClassFanOutComplexityCheck.excludedPackages", VERSION_7_7),
        Map.entry("ClassFanOutComplexityCheck.max", VERSION_3_4),
        Map.entry("NonEmptyAtclauseDescriptionCheck.javadocTokens", "7.3"),
        Map.entry("FileTabCharacterCheck.fileExtensions", VERSION_5_0),
        Map.entry("NewlineAtEndOfFileCheck.fileExtensions", "3.1"),
        Map.entry("JavadocPackageCheck.fileExtensions", VERSION_5_0),
        Map.entry("OrderedPropertiesCheck.fileExtensions", "8.22"),
        Map.entry("UniquePropertiesCheck.fileExtensions", VERSION_5_7),
        Map.entry("TranslationCheck.fileExtensions", VERSION_3_0),
        Map.entry("LineLengthCheck.fileExtensions", VERSION_8_24),
        // until https://github.com/checkstyle/checkstyle/issues/14052
        Map.entry("JavadocBlockTagLocationCheck.violateExecutionOnNonTightHtml", VERSION_8_24),
        Map.entry("JavadocLeadingAsteriskAlignCheck.violateExecutionOnNonTightHtml", "10.18"),
        Map.entry("JavadocMissingLeadingAsteriskCheck.violateExecutionOnNonTightHtml", "8.38"),
        Map.entry(
            "RequireEmptyLineBeforeBlockTagGroupCheck.violateExecutionOnNonTightHtml",
            VERSION_8_36),
        Map.entry("ParenPadCheck.option", VERSION_3_0),
        Map.entry("TypecastParenPadCheck.option", VERSION_3_2),
        Map.entry("FileLengthCheck.fileExtensions", VERSION_5_0),
        Map.entry("StaticVariableNameCheck.applyToPackage", VERSION_5_0),
        Map.entry("StaticVariableNameCheck.applyToPrivate", VERSION_5_0),
        Map.entry("StaticVariableNameCheck.applyToProtected", VERSION_5_0),
        Map.entry("StaticVariableNameCheck.applyToPublic", VERSION_5_0),
        Map.entry("StaticVariableNameCheck.format", VERSION_3_0),
        Map.entry("TypeNameCheck.applyToPackage", VERSION_5_0),
        Map.entry("TypeNameCheck.applyToPrivate", VERSION_5_0),
        Map.entry("TypeNameCheck.applyToProtected", VERSION_5_0),
        Map.entry("TypeNameCheck.applyToPublic", VERSION_5_0),
        Map.entry("RegexpMultilineCheck.fileExtensions", VERSION_5_0),
        Map.entry("RegexpOnFilenameCheck.fileExtensions", "6.15"),
        Map.entry("RegexpSinglelineCheck.fileExtensions", VERSION_5_0),
        Map.entry("ClassTypeParameterNameCheck.format", VERSION_5_0),
        Map.entry("CatchParameterNameCheck.format", "6.14"),
        Map.entry("LambdaParameterNameCheck.format", "8.11"),
        Map.entry("IllegalIdentifierNameCheck.format", VERSION_8_36),
        Map.entry("ConstantNameCheck.format", VERSION_3_0),
        Map.entry("ConstantNameCheck.applyToPackage", VERSION_5_0),
        Map.entry("ConstantNameCheck.applyToPrivate", VERSION_5_0),
        Map.entry("ConstantNameCheck.applyToProtected", VERSION_5_0),
        Map.entry("ConstantNameCheck.applyToPublic", VERSION_5_0),
        Map.entry("InterfaceTypeParameterNameCheck.format", "5.8"),
        Map.entry("LocalFinalVariableNameCheck.format", VERSION_3_0),
        Map.entry("LocalVariableNameCheck.format", VERSION_3_0),
        Map.entry("MemberNameCheck.format", VERSION_3_0),
        Map.entry("MemberNameCheck.applyToPackage", VERSION_3_4),
        Map.entry("MemberNameCheck.applyToPrivate", VERSION_3_4),
        Map.entry("MemberNameCheck.applyToProtected", VERSION_3_4),
        Map.entry("MemberNameCheck.applyToPublic", VERSION_3_4),
        Map.entry("MethodNameCheck.format", VERSION_3_0),
        Map.entry("MethodNameCheck.applyToPackage", VERSION_5_1),
        Map.entry("MethodNameCheck.applyToPrivate", VERSION_5_1),
        Map.entry("MethodNameCheck.applyToProtected", VERSION_5_1),
        Map.entry("MethodNameCheck.applyToPublic", VERSION_5_1),
        Map.entry("MethodTypeParameterNameCheck.format", VERSION_5_0),
        Map.entry("ParameterNameCheck.format", VERSION_3_0),
        Map.entry("PatternVariableNameCheck.format", VERSION_8_36),
        Map.entry("RecordTypeParameterNameCheck.format", VERSION_8_36),
        Map.entry("RecordComponentNameCheck.format", "8.40"),
        Map.entry("TypeNameCheck.format", VERSION_3_0)
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
        Path.of(MAIN_FOLDER_PATH, "api", "AbstractFileSetCheck.java"),
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
        final Set<Field> messageKeyFields = getCheckMessageKeys(module);
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
    private static Set<Field> getCheckMessageKeys(Class<?> module)
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
                checkstyleMessages.addAll(getCheckMessageKeys(superModule));
            }

            // special cases that require additional classes
            if (module == RegexpMultilineCheck.class) {
                checkstyleMessages.addAll(getCheckMessageKeys(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector")));
            }
            else if (module == RegexpSinglelineCheck.class
                    || module == RegexpSinglelineJavaCheck.class) {
                checkstyleMessages.addAll(getCheckMessageKeys(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.SinglelineDetector")));
            }

            return checkstyleMessages;
        }
        catch (ClassNotFoundException ex) {
            final String message = String.format(Locale.ROOT, "Couldn't find class: %s",
                    module.getName());
            throw new MacroExecutionException(message, ex);
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
            // required for package/private classes
            field.trySetAccessible();
            return field.get(instance);
        }
        catch (IllegalAccessException ex) {
            throw new MacroExecutionException("Couldn't get field value", ex);
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
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("Couldn't find class: " + moduleName, ex);
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
            final ClassLoader cl = ViolationMessagesMacro.class.getClassLoader();
            final Set<String> packageNames = PackageNamesLoader.getPackageNames(cl);
            return new PackageObjectFactory(packageNames, cl);
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("Couldn't load checkstyle modules", ex);
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
        return System.lineSeparator() + " ".repeat(amountOfSpaces);
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
                            && path.toString().endsWith(".xml.template");
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

        final Map<String, DetailNode> unmodifiableJavadocs =
                ClassAndPropertiesSettersJavadocScraper.getJavadocsForModuleOrProperty();
        final Map<String, DetailNode> javadocs = new LinkedHashMap<>(unmodifiableJavadocs);

        properties.forEach(property -> {
            final DetailNode superClassPropertyJavadoc =
                    SUPER_CLASS_PROPERTIES_JAVADOCS.get(property);
            if (superClassPropertyJavadoc != null) {
                javadocs.putIfAbsent(property, superClassPropertyJavadoc);
            }
        });

        assertAllPropertySetterJavadocsAreFound(properties, moduleName, javadocs);

        return javadocs;
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
            final Map<String, DetailNode> superclassJavadocs =
                ClassAndPropertiesSettersJavadocScraper.getJavadocsForModuleOrProperty();
            SUPER_CLASS_PROPERTIES_JAVADOCS.putAll(superclassJavadocs);
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
    public static String getPropertyDescription(
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
                    DescriptionExtractor.getDescriptionFromJavadoc(javadoc, moduleName))
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
     * @param propertyName the name of the property.
     * @param propertyJavadoc the Javadoc of the property setter method.
     * @return the since version of the property.
     * @throws MacroExecutionException if the since version could not be extracted.
     */
    public static String getSinceVersion(String moduleName, DetailNode moduleJavadoc,
                                         String propertyName, DetailNode propertyJavadoc)
            throws MacroExecutionException {
        final String sinceVersion;
        final String superClassSinceVersion = SINCE_VERSION_FOR_INHERITED_PROPERTY
                   .get(moduleName + DOT + propertyName);
        if (superClassSinceVersion != null) {
            sinceVersion = superClassSinceVersion;
        }
        else if (TOKENS.equals(propertyName)
                        || JAVADOC_TOKENS.equals(propertyName)) {
            // Use module's since version for inherited properties
            sinceVersion = getSinceVersionFromJavadoc(moduleJavadoc);
        }
        else {
            sinceVersion = getSinceVersionFromJavadoc(propertyJavadoc);
        }

        if (sinceVersion == null) {
            final String message = String.format(Locale.ROOT,
                    "Failed to find '@since' version for '%s' property"
                            + " in '%s' and all parent classes.", propertyName, moduleName);
            throw new MacroExecutionException(message);
        }

        return sinceVersion;
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
            .map(tag -> JavadocUtil.findFirstToken(tag, JavadocTokenTypes.DESCRIPTION))
            .map(description -> JavadocUtil.findFirstToken(description, JavadocTokenTypes.TEXT))
            .map(DetailNode::getText)
            .orElse(null);
    }

    /**
     * Find the since Javadoc tag node in the given Javadoc.
     *
     * @param javadoc the Javadoc to search.
     * @return the since Javadoc tag node or null if not found.
     */
    private static DetailNode getSinceJavadocTag(DetailNode javadoc) {
        final DetailNode[] children = javadoc.getChildren();
        DetailNode javadocTagWithSince = null;
        for (final DetailNode child : children) {
            if (child.getType() == JavadocTokenTypes.JAVADOC_TAG) {
                final DetailNode sinceNode = JavadocUtil.findFirstToken(
                        child, JavadocTokenTypes.SINCE_LITERAL);
                if (sinceNode != null) {
                    javadocTagWithSince = child;
                    break;
                }
            }
        }
        return javadocTagWithSince;
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
                .map(propertyType -> propertyType.value().getDescription())
                .orElseGet(fieldClass::getSimpleName);
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
        if (CHARSET.equals(propertyName)) {
            result = "the charset property of the parent"
                    + " <a href=\"https://checkstyle.org/config.html#Checker\">Checker</a> module";
        }
        else if (classInstance instanceof PropertyCacheFile) {
            result = "null (no cache file)";
        }
        else if (fieldClass == boolean.class) {
            result = value.toString();
        }
        else if (fieldClass == int.class) {
            result = value.toString();
        }
        else if (fieldClass == int[].class) {
            result = getIntArrayPropertyValue(value);
        }
        else if (fieldClass == double[].class) {
            result = removeSquareBrackets(Arrays.toString((double[]) value).replace(".0", ""));
            if (result.isEmpty()) {
                result = CURLY_BRACKETS;
            }
        }
        else if (fieldClass == String[].class) {
            result = getStringArrayPropertyValue(propertyName, value);
        }
        else if (fieldClass == URI.class || fieldClass == String.class) {
            if (value != null) {
                result = '"' + value.toString() + '"';
            }
        }
        else if (fieldClass == Pattern.class) {
            if (value != null) {
                result = '"' + value.toString().replace("\n", "\\n").replace("\t", "\\t")
                        .replace("\r", "\\r").replace("\f", "\\f") + '"';
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
        else {
            final String message = String.format(Locale.ROOT,
                    "Unknown property type: %s", fieldClass.getSimpleName());
            throw new MacroExecutionException(message);
        }

        if (result == null) {
            result = "null";
        }

        return result;
    }

    /**
     * Gets the name of the bean property's default value for the Pattern array class.
     *
     * @param fieldValue The bean property's value
     * @return String form of property's default value
     */
    private static String getPatternArrayPropertyValue(Object fieldValue) {
        Object value = fieldValue;
        if (value instanceof Collection) {
            final Collection<?> collection = (Collection<?>) value;

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

        if (result.isEmpty()) {
            result = CURLY_BRACKETS;
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
     * @param propertyName The bean property's name
     * @param value The bean property's value
     * @return String form of property's default value
     */
    private static String getStringArrayPropertyValue(String propertyName, Object value) {
        String result;
        if (value == null) {
            result = "";
        }
        else {
            try (Stream<?> valuesStream = getValuesStream(value)) {
                result = valuesStream
                    .map(String.class::cast)
                    .sorted()
                    .collect(Collectors.joining(COMMA_SPACE));
            }
        }

        if (result.isEmpty()) {
            if (FILE_EXTENSIONS.equals(propertyName)) {
                result = "all files";
            }
            else {
                result = CURLY_BRACKETS;
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
        if (value instanceof Collection) {
            final Collection<?> collection = (Collection<?>) value;
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
            String result = stream
                    .mapToObj(TokenUtil::getTokenName)
                    .sorted()
                    .collect(Collectors.joining(COMMA_SPACE));
            if (result.isEmpty()) {
                result = CURLY_BRACKETS;
            }
            return result;
        }
    }

    /**
     * Get the int stream from the given value.
     *
     * @param value the value to get the int stream from.
     * @return the int stream.
     */
    private static IntStream getIntStream(Object value) {
        final IntStream stream;
        if (value instanceof Collection) {
            final Collection<?> collection = (Collection<?>) value;
            stream = collection.stream()
                    .mapToInt(int.class::cast);
        }
        else if (value instanceof BitSet) {
            stream = ((BitSet) value).stream();
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
    private static Class<?> getFieldClass(Field field, String propertyName,
                                          String moduleName, Object instance)
            throws MacroExecutionException {
        Class<?> result = null;

        if (PROPERTIES_ALLOWED_GET_TYPES_FROM_METHOD
                .contains(moduleName + DOT + propertyName)) {
            result = getPropertyClass(propertyName, instance);
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
                .collect(Collectors.toUnmodifiableList());
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
        final Path templatePath = getTemplatePath(moduleName.replace("Check", ""));
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

    /** Utility class for extracting description from a method's Javadoc. */
    private static final class DescriptionExtractor {

        /**
         * Extracts the description from the javadoc detail node. Performs a DFS traversal on the
         * detail node and extracts the text nodes.
         *
         * @param javadoc the Javadoc to extract the description from.
         * @param moduleName the name of the module.
         * @return the description of the setter.
         * @throws MacroExecutionException if the description could not be extracted.
         * @noinspection TooBroadScope
         * @noinspectionreason TooBroadScope - complex nature of method requires large scope
         */
        // -@cs[NPathComplexity] Splitting would not make the code more readable
        // -@cs[CyclomaticComplexity] Splitting would not make the code more readable.
        private static String getDescriptionFromJavadoc(DetailNode javadoc, String moduleName)
                throws MacroExecutionException {
            boolean isInCodeLiteral = false;
            boolean isInHtmlElement = false;
            boolean isInHrefAttribute = false;
            final StringBuilder description = new StringBuilder(128);
            final Deque<DetailNode> queue = new ArrayDeque<>();
            final List<DetailNode> descriptionNodes = getDescriptionNodes(javadoc);
            Lists.reverse(descriptionNodes).forEach(queue::push);

            // Perform DFS traversal on description nodes
            while (!queue.isEmpty()) {
                final DetailNode node = queue.pop();
                Lists.reverse(Arrays.asList(node.getChildren())).forEach(queue::push);

                if (node.getType() == JavadocTokenTypes.HTML_TAG_NAME
                        && "href".equals(node.getText())) {
                    isInHrefAttribute = true;
                }
                if (isInHrefAttribute && node.getType() == JavadocTokenTypes.ATTR_VALUE) {
                    final String href = node.getText();
                    if (href.contains(CHECKSTYLE_ORG_URL)) {
                        handleInternalLink(description, moduleName, href);
                    }
                    else {
                        description.append(href);
                    }

                    isInHrefAttribute = false;
                    continue;
                }
                if (node.getType() == JavadocTokenTypes.HTML_ELEMENT) {
                    isInHtmlElement = true;
                }
                if (node.getType() == JavadocTokenTypes.END
                        && node.getParent().getType() == JavadocTokenTypes.HTML_ELEMENT_END) {
                    description.append(node.getText());
                    isInHtmlElement = false;
                }
                if (node.getType() == JavadocTokenTypes.TEXT
                        // If a node has children, its text is not part of the description
                        || isInHtmlElement && node.getChildren().length == 0
                            // Some HTML elements span multiple lines, so we avoid the asterisk
                            && node.getType() != JavadocTokenTypes.LEADING_ASTERISK) {
                    description.append(node.getText());
                }
                if (node.getType() == JavadocTokenTypes.CODE_LITERAL) {
                    isInCodeLiteral = true;
                    description.append("<code>");
                }
                if (isInCodeLiteral
                        && node.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG_END) {
                    isInCodeLiteral = false;
                    description.append("</code>");
                }
            }
            return description.toString().trim();
        }

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

        /**
         * Extracts description nodes from javadoc.
         *
         * @param javadoc the Javadoc to extract the description from.
         * @return the description nodes of the setter.
         */
        private static List<DetailNode> getDescriptionNodes(DetailNode javadoc) {
            final DetailNode[] children = javadoc.getChildren();
            final List<DetailNode> descriptionNodes = new ArrayList<>();
            for (final DetailNode child : children) {
                if (isEndOfDescription(child)) {
                    break;
                }
                descriptionNodes.add(child);
            }
            return descriptionNodes;
        }

        /**
         * Determines if the given child index is the end of the description. The end of the
         * description is defined as 4 consecutive nodes of type NEWLINE, LEADING_ASTERISK, NEWLINE,
         * LEADING_ASTERISK. This is an asterisk that is alone on a line. Just like the one below
         * this line.
         *
         * @param child the child to check.
         * @return true if the given child index is the end of the description.
         */
        private static boolean isEndOfDescription(DetailNode child) {
            final DetailNode nextSibling = JavadocUtil.getNextSibling(child);
            final DetailNode secondNextSibling = JavadocUtil.getNextSibling(nextSibling);
            final DetailNode thirdNextSibling = JavadocUtil.getNextSibling(secondNextSibling);

            return child.getType() == JavadocTokenTypes.NEWLINE
                        && nextSibling.getType() == JavadocTokenTypes.LEADING_ASTERISK
                        && secondNextSibling.getType() == JavadocTokenTypes.NEWLINE
                        && thirdNextSibling.getType() == JavadocTokenTypes.LEADING_ASTERISK;
        }
    }
}
