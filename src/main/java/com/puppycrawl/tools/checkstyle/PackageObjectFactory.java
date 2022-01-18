////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;

/**
 * A factory for creating objects from package names and names.
 * Consider the below example for better understanding.
 * <ul>
 *     <li>module name - name of java class that represents module;</li>
 *     <li>module full name - fully qualifies name of java class that represents module;</li>
 *     <li>check module short name - name of Check without 'Check' suffix;</li>
 *     <li>check module name - name of java class that represents Check (with 'Check' suffix);</li>
 *     <li>
 *         check module full name - fully qualifies name of java class
 *         that represents Check (with 'Check' suffix).
 *     </li>
 * </ul>
 */
public class PackageObjectFactory implements ModuleFactory {

    /**
     * Enum class to define loading options.
     */
    public enum ModuleLoadOption {

        /**
         * Searching from registered checkstyle modules and from packages given in constructor.
         **/
        SEARCH_REGISTERED_PACKAGES,
        /**
         * As SEARCH_REGISTERED_PACKAGES and also try to load class from all of packages given in
         * constructor.
         * Required for eclipse-cs plugin.
         **/
        TRY_IN_ALL_REGISTERED_PACKAGES,

    }

    /** Base package of checkstyle modules checks. */
    public static final String BASE_PACKAGE = "com.puppycrawl.tools.checkstyle";

    /** Exception message when it is unable to create a class instance. */
    public static final String UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE =
            "PackageObjectFactory.unableToInstantiateExceptionMessage";

    /** Exception message when there is ambiguous module name in config file. */
    public static final String AMBIGUOUS_MODULE_NAME_EXCEPTION_MESSAGE =
            "PackageObjectFactory.ambiguousModuleNameExceptionMessage";

    /** Suffix of checks. */
    public static final String CHECK_SUFFIX = "Check";

    /** Character separate package names in qualified name of java class. */
    public static final String PACKAGE_SEPARATOR = ".";

    /** Exception message when null class loader is given. */
    public static final String NULL_LOADER_MESSAGE = "moduleClassLoader must not be null";

    /** Exception message when null package name is given. */
    public static final String NULL_PACKAGE_MESSAGE = "package name must not be null";

    /** Separator to use in strings. */
    public static final String STRING_SEPARATOR = ", ";

    /** Map of Checkstyle module names to their fully qualified names. */
    private static final Map<String, String> NAME_TO_FULL_MODULE_NAME;

    /** A list of package names to prepend to class names. */
    private final Set<String> packages;

    /** The class loader used to load Checkstyle core and custom modules. */
    private final ClassLoader moduleClassLoader;

    /** Map of third party Checkstyle module names to the set of their fully qualified names. */
    private Map<String, Set<String>> thirdPartyNameToFullModuleNames;

    /** Module load option which defines class search type. */
    private ModuleLoadOption moduleLoadOption;

    static {
        final Map<String, String> moduleNames = new HashMap<>();
        moduleNames.putAll(fillChecksFromAnnotationPackage());
        moduleNames.putAll(fillChecksFromBlocksPackage());
        moduleNames.putAll(fillChecksFromCodingPackage());
        moduleNames.putAll(fillChecksFromDesignPackage());
        moduleNames.putAll(fillChecksFromHeaderPackage());
        moduleNames.putAll(fillChecksFromImportsPackage());
        moduleNames.putAll(fillChecksFromIndentationPackage());
        moduleNames.putAll(fillChecksFromJavadocPackage());
        moduleNames.putAll(fillChecksFromMetricsPackage());
        moduleNames.putAll(fillChecksFromModifierPackage());
        moduleNames.putAll(fillChecksFromNamingPackage());
        moduleNames.putAll(fillChecksFromRegexpPackage());
        moduleNames.putAll(fillChecksFromSizesPackage());
        moduleNames.putAll(fillChecksFromWhitespacePackage());
        moduleNames.putAll(fillModulesFromChecksPackage());
        moduleNames.putAll(fillModulesFromFilefiltersPackage());
        moduleNames.putAll(fillModulesFromFiltersPackage());
        moduleNames.putAll(fillModulesFromCheckstylePackage());
        NAME_TO_FULL_MODULE_NAME = Collections.unmodifiableMap(moduleNames);
    }

    /**
     * Creates a new {@code PackageObjectFactory} instance.
     *
     * @param packageNames the list of package names to use
     * @param moduleClassLoader class loader used to load Checkstyle
     *          core and custom modules
     */
    public PackageObjectFactory(Set<String> packageNames, ClassLoader moduleClassLoader) {
        this(packageNames, moduleClassLoader, ModuleLoadOption.SEARCH_REGISTERED_PACKAGES);
    }

    /**
     * Creates a new {@code PackageObjectFactory} instance.
     *
     * @param packageNames the list of package names to use
     * @param moduleClassLoader class loader used to load Checkstyle
     *          core and custom modules
     * @param moduleLoadOption loading option
     * @throws IllegalArgumentException if moduleClassLoader is null or packageNames contains null
     */
    public PackageObjectFactory(Set<String> packageNames, ClassLoader moduleClassLoader,
            ModuleLoadOption moduleLoadOption) {
        if (moduleClassLoader == null) {
            throw new IllegalArgumentException(NULL_LOADER_MESSAGE);
        }
        if (packageNames.contains(null)) {
            throw new IllegalArgumentException(NULL_PACKAGE_MESSAGE);
        }

        // create a copy of the given set, but retain ordering
        packages = new LinkedHashSet<>(packageNames);
        this.moduleClassLoader = moduleClassLoader;
        this.moduleLoadOption = moduleLoadOption;
    }

    /**
     * Creates a new {@code PackageObjectFactory} instance.
     *
     * @param packageName The package name to use
     * @param moduleClassLoader class loader used to load Checkstyle
     *          core and custom modules
     * @throws IllegalArgumentException if moduleClassLoader is null or packageNames is null
     */
    public PackageObjectFactory(String packageName, ClassLoader moduleClassLoader) {
        if (moduleClassLoader == null) {
            throw new IllegalArgumentException(NULL_LOADER_MESSAGE);
        }
        if (packageName == null) {
            throw new IllegalArgumentException(NULL_PACKAGE_MESSAGE);
        }

        packages = Collections.singleton(packageName);
        this.moduleClassLoader = moduleClassLoader;
    }

    /**
     * Creates a new instance of a class from a given name, or that name
     * concatenated with &quot;Check&quot;. If the name is
     * a class name, creates an instance of the named class. Otherwise, creates
     * an instance of a class name obtained by concatenating the given name
     * to a package name from a given list of package names.
     *
     * @param name the name of a class.
     * @return the {@code Object} created by loader.
     * @throws CheckstyleException if an error occurs.
     */
    @Override
    public Object createModule(String name) throws CheckstyleException {
        Object instance = null;
        // if the name is a simple class name, try to find it in maps at first
        if (!name.contains(PACKAGE_SEPARATOR)) {
            instance = createFromStandardCheckSet(name);
            // find the name in third party map
            if (instance == null) {
                if (thirdPartyNameToFullModuleNames == null) {
                    thirdPartyNameToFullModuleNames =
                            generateThirdPartyNameToFullModuleName(moduleClassLoader);
                }
                instance = createObjectFromMap(name, thirdPartyNameToFullModuleNames);
            }
        }
        if (instance == null) {
            instance = createObject(name);
        }
        if (instance == null
                && moduleLoadOption == ModuleLoadOption.TRY_IN_ALL_REGISTERED_PACKAGES) {
            instance = createModuleByTryInEachPackage(name);
        }
        if (instance == null) {
            String attemptedNames = null;
            if (!name.contains(PACKAGE_SEPARATOR)) {
                final String nameCheck = name + CHECK_SUFFIX;
                attemptedNames = joinPackageNamesWithClassName(name, packages)
                        + STRING_SEPARATOR + nameCheck + STRING_SEPARATOR
                        + joinPackageNamesWithClassName(nameCheck, packages);
            }
            final Violation exceptionMessage = new Violation(1,
                Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
                new String[] {name, attemptedNames}, null, getClass(), null);
            throw new CheckstyleException(exceptionMessage.getViolation());
        }
        return instance;
    }

    /**
     * Create object from one of Checkstyle module names.
     *
     * @param name name of module.
     * @return instance of module.
     * @throws CheckstyleException if the class fails to instantiate or there are ambiguous classes.
     */
    private Object createFromStandardCheckSet(String name) throws CheckstyleException {
        final String fullModuleName = NAME_TO_FULL_MODULE_NAME.get(name);
        Object instance = null;
        if (fullModuleName == null) {
            final String fullCheckModuleName =
                    NAME_TO_FULL_MODULE_NAME.get(name + CHECK_SUFFIX);
            if (fullCheckModuleName != null) {
                instance = createObject(fullCheckModuleName);
            }
        }
        else {
            instance = createObject(fullModuleName);
        }
        return instance;
    }

    /**
     * Create object with the help of the supplied map.
     *
     * @param name name of module.
     * @param map the supplied map.
     * @return instance of module if it is found in modules map and no ambiguous classes exist.
     * @throws CheckstyleException if the class fails to instantiate or there are ambiguous classes.
     */
    private Object createObjectFromMap(String name, Map<String, Set<String>> map)
            throws CheckstyleException {
        final Set<String> fullModuleNames = map.get(name);
        Object instance = null;
        if (fullModuleNames == null) {
            final Set<String> fullCheckModuleNames = map.get(name + CHECK_SUFFIX);
            if (fullCheckModuleNames != null) {
                instance = createObjectFromFullModuleNames(name, fullCheckModuleNames);
            }
        }
        else {
            instance = createObjectFromFullModuleNames(name, fullModuleNames);
        }
        return instance;
    }

    /**
     * Create Object from optional full module names.
     * In most case, there should be only one element in {@code fullModuleName}, otherwise
     * an exception would be thrown.
     *
     * @param name name of module
     * @param fullModuleNames the supplied full module names set
     * @return instance of module if there is only one element in {@code fullModuleName}
     * @throws CheckstyleException if the class fails to instantiate or there are more than one
     *      element in {@code fullModuleName}
     */
    private Object createObjectFromFullModuleNames(String name, Set<String> fullModuleNames)
            throws CheckstyleException {
        final Object returnValue;
        if (fullModuleNames.size() == 1) {
            returnValue = createObject(fullModuleNames.iterator().next());
        }
        else {
            final String optionalNames = fullModuleNames.stream()
                    .sorted()
                    .collect(Collectors.joining(STRING_SEPARATOR));
            final Violation exceptionMessage = new Violation(1,
                    Definitions.CHECKSTYLE_BUNDLE, AMBIGUOUS_MODULE_NAME_EXCEPTION_MESSAGE,
                    new String[] {name, optionalNames}, null, getClass(), null);
            throw new CheckstyleException(exceptionMessage.getViolation());
        }
        return returnValue;
    }

    /**
     * Generate the map of third party Checkstyle module names to the set of their fully qualified
     * names.
     *
     * @param loader the class loader used to load Checkstyle package names
     * @return the map of third party Checkstyle module names to the set of their fully qualified
     *      names
     */
    private Map<String, Set<String>> generateThirdPartyNameToFullModuleName(ClassLoader loader) {
        Map<String, Set<String>> returnValue;
        try {
            returnValue = ModuleReflectionUtil.getCheckstyleModules(packages, loader).stream()
                .collect(Collectors.groupingBy(Class::getSimpleName,
                    Collectors.mapping(Class::getCanonicalName, Collectors.toSet())));
        }
        catch (IOException ignore) {
            returnValue = Collections.emptyMap();
        }
        return returnValue;
    }

    /**
     * Returns simple check name from full modules names map.
     *
     * @param fullName name of the class for joining.
     * @return simple check name.
     */
    public static String getShortFromFullModuleNames(String fullName) {
        return NAME_TO_FULL_MODULE_NAME
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(fullName))
                .map(Entry::getKey)
                .findFirst()
                .orElse(fullName);
    }

    /**
     * Creates a string by joining package names with a class name.
     *
     * @param className name of the class for joining.
     * @param packages packages names.
     * @return a string which is obtained by joining package names with a class name.
     */
    private static String joinPackageNamesWithClassName(String className, Set<String> packages) {
        return packages.stream().collect(
            Collectors.joining(PACKAGE_SEPARATOR + className + STRING_SEPARATOR, "",
                    PACKAGE_SEPARATOR + className));
    }

    /**
     * Creates a new instance of a named class.
     *
     * @param className the name of the class to instantiate.
     * @return the {@code Object} created by loader or null.
     * @throws CheckstyleException if the class fails to instantiate.
     */
    private Object createObject(String className) throws CheckstyleException {
        Class<?> clazz = null;

        try {
            clazz = Class.forName(className, true, moduleClassLoader);
        }
        catch (final ReflectiveOperationException | NoClassDefFoundError ignored) {
            // ignore the exception
        }

        Object instance = null;

        if (clazz != null) {
            try {
                instance = clazz.getDeclaredConstructor().newInstance();
            }
            catch (final ReflectiveOperationException ex) {
                throw new CheckstyleException("Unable to instantiate " + className, ex);
            }
        }

        return instance;
    }

    /**
     * Searching to class with given name (or name concatenated with &quot;Check&quot;) in existing
     * packages. Returns instance if class found or, otherwise, null.
     *
     * @param name the name of a class.
     * @return the {@code Object} created by loader.
     * @throws CheckstyleException if an error occurs.
     */
    private Object createModuleByTryInEachPackage(String name) throws CheckstyleException {
        final List<String> possibleNames = packages.stream()
            .map(packageName -> packageName + PACKAGE_SEPARATOR + name)
            .flatMap(className -> Stream.of(className, className + CHECK_SUFFIX))
            .collect(Collectors.toList());
        Object instance = null;
        for (String possibleName : possibleNames) {
            instance = createObject(possibleName);
            if (instance != null) {
                break;
            }
        }
        return instance;
    }

    /**
     * Fill short-to-full module names map with Checks from annotation package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromAnnotationPackage() {
        return Stream.of(new String[][] {
            {"AnnotationLocationCheck",
                BASE_PACKAGE + ".checks.annotation.AnnotationLocationCheck"},
            {"AnnotationOnSameLineCheck",
                BASE_PACKAGE + ".checks.annotation.AnnotationOnSameLineCheck"},
            {"AnnotationUseStyleCheck",
                BASE_PACKAGE + ".checks.annotation.AnnotationUseStyleCheck"},
            {"MissingDeprecatedCheck",
                BASE_PACKAGE + ".checks.annotation.MissingDeprecatedCheck"},
            {"MissingOverrideCheck", BASE_PACKAGE + ".checks.annotation.MissingOverrideCheck"},
            {"PackageAnnotationCheck",
                BASE_PACKAGE + ".checks.annotation.PackageAnnotationCheck"},
            {"SuppressWarningsCheck",
                BASE_PACKAGE + ".checks.annotation.SuppressWarningsCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from blocks package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromBlocksPackage() {
        return Stream.of(new String[][] {
            {"AvoidNestedBlocksCheck", BASE_PACKAGE + ".checks.blocks.AvoidNestedBlocksCheck"},
            {"EmptyBlockCheck", BASE_PACKAGE + ".checks.blocks.EmptyBlockCheck"},
            {"EmptyCatchBlockCheck", BASE_PACKAGE + ".checks.blocks.EmptyCatchBlockCheck"},
            {"LeftCurlyCheck", BASE_PACKAGE + ".checks.blocks.LeftCurlyCheck"},
            {"NeedBracesCheck", BASE_PACKAGE + ".checks.blocks.NeedBracesCheck"},
            {"RightCurlyCheck", BASE_PACKAGE + ".checks.blocks.RightCurlyCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from coding package.
     *
     * @return map of package names
     */
    // -@cs[ExecutableStatementCount] splitting this method is not reasonable.
    // -@cs[JavaNCSS] splitting this method is not reasonable.
    private static Map<String, String> fillChecksFromCodingPackage() {
        return Stream.of(new String[][] {
            {"ArrayTrailingCommaCheck",
                BASE_PACKAGE + ".checks.coding.ArrayTrailingCommaCheck"},
            {"AvoidDoubleBraceInitializationCheck",
                BASE_PACKAGE + ".checks.coding.AvoidDoubleBraceInitializationCheck"},
            {"AvoidInlineConditionalsCheck",
                BASE_PACKAGE + ".checks.coding.AvoidInlineConditionalsCheck"},
            {"AvoidNoArgumentSuperConstructorCallCheck",
                BASE_PACKAGE + ".checks.coding.AvoidNoArgumentSuperConstructorCallCheck"},
            {"CovariantEqualsCheck", BASE_PACKAGE + ".checks.coding.CovariantEqualsCheck"},
            {"DeclarationOrderCheck", BASE_PACKAGE + ".checks.coding.DeclarationOrderCheck"},
            {"DefaultComesLastCheck", BASE_PACKAGE + ".checks.coding.DefaultComesLastCheck"},
            {"EmptyStatementCheck", BASE_PACKAGE + ".checks.coding.EmptyStatementCheck"},
            {"EqualsAvoidNullCheck", BASE_PACKAGE + ".checks.coding.EqualsAvoidNullCheck"},
            {"EqualsHashCodeCheck", BASE_PACKAGE + ".checks.coding.EqualsHashCodeCheck"},
            {"ExplicitInitializationCheck",
                BASE_PACKAGE + ".checks.coding.ExplicitInitializationCheck"},
            {"FallThroughCheck", BASE_PACKAGE + ".checks.coding.FallThroughCheck"},
            {"FinalLocalVariableCheck",
                BASE_PACKAGE + ".checks.coding.FinalLocalVariableCheck"},
            {"HiddenFieldCheck", BASE_PACKAGE + ".checks.coding.HiddenFieldCheck"},
            {"IllegalCatchCheck", BASE_PACKAGE + ".checks.coding.IllegalCatchCheck"},
            {"IllegalInstantiationCheck",
                BASE_PACKAGE + ".checks.coding.IllegalInstantiationCheck"},
            {"IllegalThrowsCheck", BASE_PACKAGE + ".checks.coding.IllegalThrowsCheck"},
            {"IllegalTokenCheck", BASE_PACKAGE + ".checks.coding.IllegalTokenCheck"},
            {"IllegalTokenTextCheck", BASE_PACKAGE + ".checks.coding.IllegalTokenTextCheck"},
            {"IllegalTypeCheck", BASE_PACKAGE + ".checks.coding.IllegalTypeCheck"},
            {"InnerAssignmentCheck", BASE_PACKAGE + ".checks.coding.InnerAssignmentCheck"},
            {"MagicNumberCheck", BASE_PACKAGE + ".checks.coding.MagicNumberCheck"},
            {"MissingCtorCheck", BASE_PACKAGE + ".checks.coding.MissingCtorCheck"},
            {"MissingSwitchDefaultCheck",
                BASE_PACKAGE + ".checks.coding.MissingSwitchDefaultCheck"},
            {"ModifiedControlVariableCheck",
                BASE_PACKAGE + ".checks.coding.ModifiedControlVariableCheck"},
            {"MultipleStringLiteralsCheck",
                BASE_PACKAGE + ".checks.coding.MultipleStringLiteralsCheck"},
            {"MultipleVariableDeclarationsCheck",
                BASE_PACKAGE + ".checks.coding.MultipleVariableDeclarationsCheck"},
            {"NestedForDepthCheck", BASE_PACKAGE + ".checks.coding.NestedForDepthCheck"},
            {"NestedIfDepthCheck", BASE_PACKAGE + ".checks.coding.NestedIfDepthCheck"},
            {"NestedTryDepthCheck", BASE_PACKAGE + ".checks.coding.NestedTryDepthCheck"},
            {"NoCloneCheck", BASE_PACKAGE + ".checks.coding.NoCloneCheck"},
            {"NoEnumTrailingCommaCheck",
                BASE_PACKAGE + ".checks.coding.NoEnumTrailingCommaCheck"},
            {"NoFinalizerCheck", BASE_PACKAGE + ".checks.coding.NoFinalizerCheck"},
            {"OneStatementPerLineCheck",
                BASE_PACKAGE + ".checks.coding.OneStatementPerLineCheck"},
            {"OverloadMethodsDeclarationOrderCheck",
                BASE_PACKAGE + ".checks.coding.OverloadMethodsDeclarationOrderCheck"},
            {"PackageDeclarationCheck",
                BASE_PACKAGE + ".checks.coding.PackageDeclarationCheck"},
            {"ParameterAssignmentCheck",
                BASE_PACKAGE + ".checks.coding.ParameterAssignmentCheck"},
            {"RequireThisCheck", BASE_PACKAGE + ".checks.coding.RequireThisCheck"},
            {"ReturnCountCheck", BASE_PACKAGE + ".checks.coding.ReturnCountCheck"},
            {"SimplifyBooleanExpressionCheck",
                BASE_PACKAGE + ".checks.coding.SimplifyBooleanExpressionCheck"},
            {"SimplifyBooleanReturnCheck",
                BASE_PACKAGE + ".checks.coding.SimplifyBooleanReturnCheck"},
            {"StringLiteralEqualityCheck",
                BASE_PACKAGE + ".checks.coding.StringLiteralEqualityCheck"},
            {"SuperCloneCheck", BASE_PACKAGE + ".checks.coding.SuperCloneCheck"},
            {"SuperFinalizeCheck", BASE_PACKAGE + ".checks.coding.SuperFinalizeCheck"},
            {"UnnecessaryParenthesesCheck",
                BASE_PACKAGE + ".checks.coding.UnnecessaryParenthesesCheck"},
            {"UnnecessarySemicolonAfterOuterTypeDeclarationCheck",
                BASE_PACKAGE + ".checks.coding.UnnecessarySemicolonAfterOuterTypeDeclarationCheck"},
            {"UnnecessarySemicolonAfterTypeMemberDeclarationCheck",
                BASE_PACKAGE
                    + ".checks.coding.UnnecessarySemicolonAfterTypeMemberDeclarationCheck"},
            {"UnnecessarySemicolonInEnumerationCheck",
                BASE_PACKAGE + ".checks.coding.UnnecessarySemicolonInEnumerationCheck"},
            {"UnnecessarySemicolonInTryWithResourcesCheck",
                BASE_PACKAGE + ".checks.coding.UnnecessarySemicolonInTryWithResourcesCheck"},
            {"VariableDeclarationUsageDistanceCheck",
                BASE_PACKAGE + ".checks.coding.VariableDeclarationUsageDistanceCheck"},
            {"NoArrayTrailingCommaCheck",
                BASE_PACKAGE + ".checks.coding.NoArrayTrailingCommaCheck"},
            {"MatchXpathCheck", BASE_PACKAGE + ".checks.coding.MatchXpathCheck"},
            {"UnusedLocalVariableCheck",
                BASE_PACKAGE + ".checks.coding.UnusedLocalVariableCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from design package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromDesignPackage() {
        return Stream.of(new String[][] {
            {"DesignForExtensionCheck",
                BASE_PACKAGE + ".checks.design.DesignForExtensionCheck"},
            {"FinalClassCheck",
                BASE_PACKAGE + ".checks.design.FinalClassCheck"},
            {"HideUtilityClassConstructorCheck",
                BASE_PACKAGE + ".checks.design.HideUtilityClassConstructorCheck"},
            {"InnerTypeLastCheck",
                BASE_PACKAGE + ".checks.design.InnerTypeLastCheck"},
            {"InterfaceIsTypeCheck",
                BASE_PACKAGE + ".checks.design.InterfaceIsTypeCheck"},
            {"MutableExceptionCheck",
                BASE_PACKAGE + ".checks.design.MutableExceptionCheck"},
            {"OneTopLevelClassCheck",
                BASE_PACKAGE + ".checks.design.OneTopLevelClassCheck"},
            {"ThrowsCountCheck",
                BASE_PACKAGE + ".checks.design.ThrowsCountCheck"},
            {"VisibilityModifierCheck",
                BASE_PACKAGE + ".checks.design.VisibilityModifierCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from header package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromHeaderPackage() {
        return Stream.of(new String[][] {
            {"HeaderCheck",
                BASE_PACKAGE + ".checks.header.HeaderCheck"},
            {"RegexpHeaderCheck",
                BASE_PACKAGE + ".checks.header.RegexpHeaderCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from imports package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromImportsPackage() {
        return Stream.of(new String[][] {
            {"AvoidStarImportCheck",
                BASE_PACKAGE + ".checks.imports.AvoidStarImportCheck"},
            {"AvoidStaticImportCheck",
                BASE_PACKAGE + ".checks.imports.AvoidStaticImportCheck"},
            {"CustomImportOrderCheck",
                BASE_PACKAGE + ".checks.imports.CustomImportOrderCheck"},
            {"IllegalImportCheck",
                BASE_PACKAGE + ".checks.imports.IllegalImportCheck"},
            {"ImportControlCheck",
                BASE_PACKAGE + ".checks.imports.ImportControlCheck"},
            {"ImportOrderCheck",
                BASE_PACKAGE + ".checks.imports.ImportOrderCheck"},
            {"RedundantImportCheck",
                BASE_PACKAGE + ".checks.imports.RedundantImportCheck"},
            {"UnusedImportsCheck",
                BASE_PACKAGE + ".checks.imports.UnusedImportsCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from indentation package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromIndentationPackage() {
        return Stream.of(new String[][] {
            {"CommentsIndentationCheck",
                BASE_PACKAGE + ".checks.indentation.CommentsIndentationCheck"},
            {"IndentationCheck",
                BASE_PACKAGE + ".checks.indentation.IndentationCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from javadoc package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromJavadocPackage() {
        return Stream.of(new String[][] {
            {"AtclauseOrderCheck", BASE_PACKAGE + ".checks.javadoc.AtclauseOrderCheck"},
            {"InvalidJavadocPositionCheck",
                BASE_PACKAGE + ".checks.javadoc.InvalidJavadocPositionCheck"},
            {"JavadocBlockTagLocationCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocBlockTagLocationCheck"},
            {"JavadocContentLocationCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocContentLocationCheck"},
            {"JavadocMethodCheck", BASE_PACKAGE + ".checks.javadoc.JavadocMethodCheck"},
            {"JavadocMissingLeadingAsteriskCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocMissingLeadingAsteriskCheck"},
            {"JavadocMissingWhitespaceAfterAsteriskCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocMissingWhitespaceAfterAsteriskCheck"},
            {"JavadocPackageCheck", BASE_PACKAGE + ".checks.javadoc.JavadocPackageCheck"},
            {"JavadocParagraphCheck", BASE_PACKAGE + ".checks.javadoc.JavadocParagraphCheck"},
            {"JavadocStyleCheck", BASE_PACKAGE + ".checks.javadoc.JavadocStyleCheck"},
            {"JavadocTagContinuationIndentationCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocTagContinuationIndentationCheck"},
            {"JavadocTypeCheck", BASE_PACKAGE + ".checks.javadoc.JavadocTypeCheck"},
            {"JavadocVariableCheck", BASE_PACKAGE + ".checks.javadoc.JavadocVariableCheck"},
            {"MissingJavadocMethodCheck",
                BASE_PACKAGE + ".checks.javadoc.MissingJavadocMethodCheck"},
            {"MissingJavadocPackageCheck",
                BASE_PACKAGE + ".checks.javadoc.MissingJavadocPackageCheck"},
            {"MissingJavadocTypeCheck",
                BASE_PACKAGE + ".checks.javadoc.MissingJavadocTypeCheck"},
            {"NonEmptyAtclauseDescriptionCheck",
                BASE_PACKAGE + ".checks.javadoc.NonEmptyAtclauseDescriptionCheck"},
            {"RequireEmptyLineBeforeBlockTagGroupCheck",
                BASE_PACKAGE + ".checks.javadoc.RequireEmptyLineBeforeBlockTagGroupCheck"},
            {"SingleLineJavadocCheck", BASE_PACKAGE + ".checks.javadoc.SingleLineJavadocCheck"},
            {"SummaryJavadocCheck", BASE_PACKAGE + ".checks.javadoc.SummaryJavadocCheck"},
            {"WriteTagCheck", BASE_PACKAGE + ".checks.javadoc.WriteTagCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from metrics package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromMetricsPackage() {
        return Stream.of(new String[][] {
            {"BooleanExpressionComplexityCheck",
                BASE_PACKAGE + ".checks.metrics.BooleanExpressionComplexityCheck"},
            {"ClassDataAbstractionCouplingCheck",
                BASE_PACKAGE + ".checks.metrics.ClassDataAbstractionCouplingCheck"},
            {"ClassFanOutComplexityCheck",
                BASE_PACKAGE + ".checks.metrics.ClassFanOutComplexityCheck"},
            {"CyclomaticComplexityCheck",
                BASE_PACKAGE + ".checks.metrics.CyclomaticComplexityCheck"},
            {"JavaNCSSCheck",
                BASE_PACKAGE + ".checks.metrics.JavaNCSSCheck"},
            {"NPathComplexityCheck",
                BASE_PACKAGE + ".checks.metrics.NPathComplexityCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from modifier package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromModifierPackage() {
        return Stream.of(new String[][] {
            {"ClassMemberImpliedModifierCheck",
                BASE_PACKAGE + ".checks.modifier.ClassMemberImpliedModifierCheck"},
            {"InterfaceMemberImpliedModifierCheck",
                BASE_PACKAGE + ".checks.modifier.InterfaceMemberImpliedModifierCheck"},
            {"ModifierOrderCheck",
                BASE_PACKAGE + ".checks.modifier.ModifierOrderCheck"},
            {"RedundantModifierCheck",
                BASE_PACKAGE + ".checks.modifier.RedundantModifierCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from naming package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromNamingPackage() {
        return Stream.of(new String[][] {
            {"AbbreviationAsWordInNameCheck",
                BASE_PACKAGE + ".checks.naming.AbbreviationAsWordInNameCheck"},
            {"AbstractClassNameCheck",
                BASE_PACKAGE + ".checks.naming.AbstractClassNameCheck"},
            {"CatchParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.CatchParameterNameCheck"},
            {"ClassTypeParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.ClassTypeParameterNameCheck"},
            {"ConstantNameCheck",
                BASE_PACKAGE + ".checks.naming.ConstantNameCheck"},
            {"InterfaceTypeParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.InterfaceTypeParameterNameCheck"},
            {"LambdaParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.LambdaParameterNameCheck"},
            {"LocalFinalVariableNameCheck",
                BASE_PACKAGE + ".checks.naming.LocalFinalVariableNameCheck"},
            {"LocalVariableNameCheck",
                BASE_PACKAGE + ".checks.naming.LocalVariableNameCheck"},
            {"MemberNameCheck",
                BASE_PACKAGE + ".checks.naming.MemberNameCheck"},
            {"MethodNameCheck",
                BASE_PACKAGE + ".checks.naming.MethodNameCheck"},
            {"MethodTypeParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.MethodTypeParameterNameCheck"},
            {"PackageNameCheck",
                BASE_PACKAGE + ".checks.naming.PackageNameCheck"},
            {"ParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.ParameterNameCheck"},
            {"RecordComponentNameCheck",
                BASE_PACKAGE + ".checks.naming.RecordComponentNameCheck"},
            {"RecordTypeParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.RecordTypeParameterNameCheck"},
            {"StaticVariableNameCheck",
                BASE_PACKAGE + ".checks.naming.StaticVariableNameCheck"},
            {"TypeNameCheck",
                BASE_PACKAGE + ".checks.naming.TypeNameCheck"},
            {"PatternVariableNameCheck",
                BASE_PACKAGE + ".checks.naming.PatternVariableNameCheck"},
            {"IllegalIdentifierNameCheck",
                BASE_PACKAGE + ".checks.naming.IllegalIdentifierNameCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from regexp package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromRegexpPackage() {
        return Stream.of(new String[][] {
            {"RegexpCheck",
                BASE_PACKAGE + ".checks.regexp.RegexpCheck"},
            {"RegexpMultilineCheck",
                BASE_PACKAGE + ".checks.regexp.RegexpMultilineCheck"},
            {"RegexpOnFilenameCheck",
                BASE_PACKAGE + ".checks.regexp.RegexpOnFilenameCheck"},
            {"RegexpSinglelineCheck",
                BASE_PACKAGE + ".checks.regexp.RegexpSinglelineCheck"},
            {"RegexpSinglelineJavaCheck",
                BASE_PACKAGE + ".checks.regexp.RegexpSinglelineJavaCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from sizes package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromSizesPackage() {
        return Stream.of(new String[][] {
            {"AnonInnerLengthCheck",
                BASE_PACKAGE + ".checks.sizes.AnonInnerLengthCheck"},
            {"ExecutableStatementCountCheck",
                BASE_PACKAGE + ".checks.sizes.ExecutableStatementCountCheck"},
            {"FileLengthCheck",
                BASE_PACKAGE + ".checks.sizes.FileLengthCheck"},
            {"LambdaBodyLengthCheck",
                BASE_PACKAGE + ".checks.sizes.LambdaBodyLengthCheck"},
            {"LineLengthCheck",
                BASE_PACKAGE + ".checks.sizes.LineLengthCheck"},
            {"MethodCountCheck",
                BASE_PACKAGE + ".checks.sizes.MethodCountCheck"},
            {"MethodLengthCheck",
                BASE_PACKAGE + ".checks.sizes.MethodLengthCheck"},
            {"OuterTypeNumberCheck",
                BASE_PACKAGE + ".checks.sizes.OuterTypeNumberCheck"},
            {"ParameterNumberCheck",
                BASE_PACKAGE + ".checks.sizes.ParameterNumberCheck"},
            {"RecordComponentNumberCheck",
                BASE_PACKAGE + ".checks.sizes.RecordComponentNumberCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from whitespace package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromWhitespacePackage() {
        return Stream.of(new String[][] {
            {"EmptyForInitializerPadCheck",
                BASE_PACKAGE + ".checks.whitespace.EmptyForInitializerPadCheck"},
            {"EmptyForIteratorPadCheck",
                BASE_PACKAGE + ".checks.whitespace.EmptyForIteratorPadCheck"},
            {"EmptyLineSeparatorCheck",
                BASE_PACKAGE + ".checks.whitespace.EmptyLineSeparatorCheck"},
            {"FileTabCharacterCheck",
                BASE_PACKAGE + ".checks.whitespace.FileTabCharacterCheck"},
            {"GenericWhitespaceCheck",
                BASE_PACKAGE + ".checks.whitespace.GenericWhitespaceCheck"},
            {"MethodParamPadCheck", BASE_PACKAGE + ".checks.whitespace.MethodParamPadCheck"},
            {"NoLineWrapCheck", BASE_PACKAGE + ".checks.whitespace.NoLineWrapCheck"},
            {"NoWhitespaceAfterCheck",
                BASE_PACKAGE + ".checks.whitespace.NoWhitespaceAfterCheck"},
            {"NoWhitespaceBeforeCheck",
                BASE_PACKAGE + ".checks.whitespace.NoWhitespaceBeforeCheck"},
            {"NoWhitespaceBeforeCaseDefaultColonCheck",
                BASE_PACKAGE + ".checks.whitespace.NoWhitespaceBeforeCaseDefaultColonCheck"},
            {"OperatorWrapCheck", BASE_PACKAGE + ".checks.whitespace.OperatorWrapCheck"},
            {"ParenPadCheck", BASE_PACKAGE + ".checks.whitespace.ParenPadCheck"},
            {"SeparatorWrapCheck", BASE_PACKAGE + ".checks.whitespace.SeparatorWrapCheck"},
            {"SingleSpaceSeparatorCheck",
                BASE_PACKAGE + ".checks.whitespace.SingleSpaceSeparatorCheck"},
            {"TypecastParenPadCheck",
                BASE_PACKAGE + ".checks.whitespace.TypecastParenPadCheck"},
            {"WhitespaceAfterCheck", BASE_PACKAGE + ".checks.whitespace.WhitespaceAfterCheck"},
            {"WhitespaceAroundCheck",
                BASE_PACKAGE + ".checks.whitespace.WhitespaceAroundCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with modules from checks package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillModulesFromChecksPackage() {
        return Stream.of(new String[][] {
            {"ArrayTypeStyleCheck", BASE_PACKAGE + ".checks.ArrayTypeStyleCheck"},
            {"AvoidEscapedUnicodeCharactersCheck",
                BASE_PACKAGE + ".checks.AvoidEscapedUnicodeCharactersCheck"},
            {"DescendantTokenCheck", BASE_PACKAGE + ".checks.DescendantTokenCheck"},
            {"FinalParametersCheck", BASE_PACKAGE + ".checks.FinalParametersCheck"},
            {"NewlineAtEndOfFileCheck", BASE_PACKAGE + ".checks.NewlineAtEndOfFileCheck"},
            {"NoCodeInFileCheck", BASE_PACKAGE + ".checks.NoCodeInFileCheck"},
            {"OuterTypeFilenameCheck", BASE_PACKAGE + ".checks.OuterTypeFilenameCheck"},
            {"OrderedPropertiesCheck", BASE_PACKAGE + ".checks.OrderedPropertiesCheck"},
            {"SuppressWarningsHolder", BASE_PACKAGE + ".checks.SuppressWarningsHolder"},
            {"TodoCommentCheck", BASE_PACKAGE + ".checks.TodoCommentCheck"},
            {"TrailingCommentCheck", BASE_PACKAGE + ".checks.TrailingCommentCheck"},
            {"TranslationCheck", BASE_PACKAGE + ".checks.TranslationCheck"},
            {"UncommentedMainCheck", BASE_PACKAGE + ".checks.UncommentedMainCheck"},
            {"UniquePropertiesCheck", BASE_PACKAGE + ".checks.UniquePropertiesCheck"},
            {"UpperEllCheck", BASE_PACKAGE + ".checks.UpperEllCheck"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with modules from filefilters package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillModulesFromFilefiltersPackage() {
        return Stream.of(new String[][] {
            {"BeforeExecutionExclusionFileFilter",
                BASE_PACKAGE + ".filefilters.BeforeExecutionExclusionFileFilter"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with modules from filters package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillModulesFromFiltersPackage() {
        return Stream.of(new String[][] {
            {"SeverityMatchFilter", BASE_PACKAGE + ".filters.SeverityMatchFilter"},
            {"SuppressWithPlainTextCommentFilter",
                BASE_PACKAGE + ".filters.SuppressWithPlainTextCommentFilter"},
            {"SuppressionCommentFilter",
                BASE_PACKAGE + ".filters.SuppressionCommentFilter"},
            {"SuppressionFilter",
                BASE_PACKAGE + ".filters.SuppressionFilter"},
            {"SuppressionSingleFilter",
                BASE_PACKAGE + ".filters.SuppressionSingleFilter"},
            {"SuppressionXpathFilter",
                BASE_PACKAGE + ".filters.SuppressionXpathFilter"},
            {"SuppressionXpathSingleFilter",
                BASE_PACKAGE + ".filters.SuppressionXpathSingleFilter"},
            {"SuppressWarningsFilter",
                BASE_PACKAGE + ".filters.SuppressWarningsFilter"},
            {"SuppressWithNearbyCommentFilter",
                BASE_PACKAGE + ".filters.SuppressWithNearbyCommentFilter"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with modules from checkstyle package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillModulesFromCheckstylePackage() {
        return Stream.of(new String[][] {
            {"Checker", BASE_PACKAGE + ".Checker"},
            {"TreeWalker", BASE_PACKAGE + ".TreeWalker"},
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::unmodifiableMap));
    }

}
