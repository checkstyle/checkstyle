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
import java.util.AbstractMap;
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
        return Stream.of(
                new AbstractMap.SimpleEntry<>("AnnotationLocationCheck",
                    BASE_PACKAGE + ".checks.annotation.AnnotationLocationCheck"),
                new AbstractMap.SimpleEntry<>("AnnotationOnSameLineCheck",
                    BASE_PACKAGE + ".checks.annotation.AnnotationOnSameLineCheck"),
                new AbstractMap.SimpleEntry<>("AnnotationUseStyleCheck",
                    BASE_PACKAGE + ".checks.annotation.AnnotationUseStyleCheck"),
                new AbstractMap.SimpleEntry<>("MissingDeprecatedCheck",
                    BASE_PACKAGE + ".checks.annotation.MissingDeprecatedCheck"),
                new AbstractMap.SimpleEntry<>("MissingOverrideCheck",
                    BASE_PACKAGE + ".checks.annotation.MissingOverrideCheck"),
                new AbstractMap.SimpleEntry<>("PackageAnnotationCheck",
                    BASE_PACKAGE + ".checks.annotation.PackageAnnotationCheck"),
                new AbstractMap.SimpleEntry<>("SuppressWarningsCheck",
                    BASE_PACKAGE + ".checks.annotation.SuppressWarningsCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from blocks package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromBlocksPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("AvoidNestedBlocksCheck",
                    BASE_PACKAGE + ".checks.blocks.AvoidNestedBlocksCheck"),
                new AbstractMap.SimpleEntry<>("EmptyBlockCheck",
                    BASE_PACKAGE + ".checks.blocks.EmptyBlockCheck"),
                new AbstractMap.SimpleEntry<>("EmptyCatchBlockCheck",
                    BASE_PACKAGE + ".checks.blocks.EmptyCatchBlockCheck"),
                new AbstractMap.SimpleEntry<>("LeftCurlyCheck",
                    BASE_PACKAGE + ".checks.blocks.LeftCurlyCheck"),
                new AbstractMap.SimpleEntry<>("NeedBracesCheck",
                    BASE_PACKAGE + ".checks.blocks.NeedBracesCheck"),
                new AbstractMap.SimpleEntry<>("RightCurlyCheck",
                    BASE_PACKAGE + ".checks.blocks.RightCurlyCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
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
        //noinspection RedundantTypeArguments (explicit type arguments speedup compilation and analysis time)
        return Stream.<AbstractMap.SimpleEntry<String, String>>of(
                new AbstractMap.SimpleEntry<>("ArrayTrailingCommaCheck",
                    BASE_PACKAGE + ".checks.coding.ArrayTrailingCommaCheck"),
                new AbstractMap.SimpleEntry<>("AvoidDoubleBraceInitializationCheck",
                    BASE_PACKAGE + ".checks.coding.AvoidDoubleBraceInitializationCheck"),
                new AbstractMap.SimpleEntry<>("AvoidInlineConditionalsCheck",
                    BASE_PACKAGE + ".checks.coding.AvoidInlineConditionalsCheck"),
                new AbstractMap.SimpleEntry<>("AvoidNoArgumentSuperConstructorCallCheck",
                    BASE_PACKAGE + ".checks.coding.AvoidNoArgumentSuperConstructorCallCheck"),
                new AbstractMap.SimpleEntry<>("CovariantEqualsCheck",
                    BASE_PACKAGE + ".checks.coding.CovariantEqualsCheck"),
                new AbstractMap.SimpleEntry<>("DeclarationOrderCheck",
                    BASE_PACKAGE + ".checks.coding.DeclarationOrderCheck"),
                new AbstractMap.SimpleEntry<>("DefaultComesLastCheck",
                    BASE_PACKAGE + ".checks.coding.DefaultComesLastCheck"),
                new AbstractMap.SimpleEntry<>("EmptyStatementCheck",
                    BASE_PACKAGE + ".checks.coding.EmptyStatementCheck"),
                new AbstractMap.SimpleEntry<>("EqualsAvoidNullCheck",
                    BASE_PACKAGE + ".checks.coding.EqualsAvoidNullCheck"),
                new AbstractMap.SimpleEntry<>("EqualsHashCodeCheck",
                    BASE_PACKAGE + ".checks.coding.EqualsHashCodeCheck"),
                new AbstractMap.SimpleEntry<>("ExplicitInitializationCheck",
                    BASE_PACKAGE + ".checks.coding.ExplicitInitializationCheck"),
                new AbstractMap.SimpleEntry<>("FallThroughCheck",
                    BASE_PACKAGE + ".checks.coding.FallThroughCheck"),
                new AbstractMap.SimpleEntry<>("FinalLocalVariableCheck",
                    BASE_PACKAGE + ".checks.coding.FinalLocalVariableCheck"),
                new AbstractMap.SimpleEntry<>("HiddenFieldCheck",
                    BASE_PACKAGE + ".checks.coding.HiddenFieldCheck"),
                new AbstractMap.SimpleEntry<>("IllegalCatchCheck",
                    BASE_PACKAGE + ".checks.coding.IllegalCatchCheck"),
                new AbstractMap.SimpleEntry<>("IllegalInstantiationCheck",
                    BASE_PACKAGE + ".checks.coding.IllegalInstantiationCheck"),
                new AbstractMap.SimpleEntry<>("IllegalThrowsCheck",
                    BASE_PACKAGE + ".checks.coding.IllegalThrowsCheck"),
                new AbstractMap.SimpleEntry<>("IllegalTokenCheck",
                    BASE_PACKAGE + ".checks.coding.IllegalTokenCheck"),
                new AbstractMap.SimpleEntry<>("IllegalTokenTextCheck",
                    BASE_PACKAGE + ".checks.coding.IllegalTokenTextCheck"),
                new AbstractMap.SimpleEntry<>("IllegalTypeCheck",
                    BASE_PACKAGE + ".checks.coding.IllegalTypeCheck"),
                new AbstractMap.SimpleEntry<>("InnerAssignmentCheck",
                    BASE_PACKAGE + ".checks.coding.InnerAssignmentCheck"),
                new AbstractMap.SimpleEntry<>("MagicNumberCheck",
                    BASE_PACKAGE + ".checks.coding.MagicNumberCheck"),
                new AbstractMap.SimpleEntry<>("MissingCtorCheck",
                    BASE_PACKAGE + ".checks.coding.MissingCtorCheck"),
                new AbstractMap.SimpleEntry<>("MissingSwitchDefaultCheck",
                    BASE_PACKAGE + ".checks.coding.MissingSwitchDefaultCheck"),
                new AbstractMap.SimpleEntry<>("ModifiedControlVariableCheck",
                    BASE_PACKAGE + ".checks.coding.ModifiedControlVariableCheck"),
                new AbstractMap.SimpleEntry<>("MultipleStringLiteralsCheck",
                    BASE_PACKAGE + ".checks.coding.MultipleStringLiteralsCheck"),
                new AbstractMap.SimpleEntry<>("MultipleVariableDeclarationsCheck",
                    BASE_PACKAGE + ".checks.coding.MultipleVariableDeclarationsCheck"),
                new AbstractMap.SimpleEntry<>("NestedForDepthCheck",
                    BASE_PACKAGE + ".checks.coding.NestedForDepthCheck"),
                new AbstractMap.SimpleEntry<>("NestedIfDepthCheck",
                    BASE_PACKAGE + ".checks.coding.NestedIfDepthCheck"),
                new AbstractMap.SimpleEntry<>("NestedTryDepthCheck",
                    BASE_PACKAGE + ".checks.coding.NestedTryDepthCheck"),
                new AbstractMap.SimpleEntry<>("NoCloneCheck",
                    BASE_PACKAGE + ".checks.coding.NoCloneCheck"),
                new AbstractMap.SimpleEntry<>("NoEnumTrailingCommaCheck",
                    BASE_PACKAGE + ".checks.coding.NoEnumTrailingCommaCheck"),
                new AbstractMap.SimpleEntry<>("NoFinalizerCheck",
                    BASE_PACKAGE + ".checks.coding.NoFinalizerCheck"),
                new AbstractMap.SimpleEntry<>("OneStatementPerLineCheck",
                    BASE_PACKAGE + ".checks.coding.OneStatementPerLineCheck"),
                new AbstractMap.SimpleEntry<>("OverloadMethodsDeclarationOrderCheck",
                    BASE_PACKAGE + ".checks.coding.OverloadMethodsDeclarationOrderCheck"),
                new AbstractMap.SimpleEntry<>("PackageDeclarationCheck",
                    BASE_PACKAGE + ".checks.coding.PackageDeclarationCheck"),
                new AbstractMap.SimpleEntry<>("ParameterAssignmentCheck",
                    BASE_PACKAGE + ".checks.coding.ParameterAssignmentCheck"),
                new AbstractMap.SimpleEntry<>("RequireThisCheck",
                    BASE_PACKAGE + ".checks.coding.RequireThisCheck"),
                new AbstractMap.SimpleEntry<>("ReturnCountCheck",
                    BASE_PACKAGE + ".checks.coding.ReturnCountCheck"),
                new AbstractMap.SimpleEntry<>("SimplifyBooleanExpressionCheck",
                    BASE_PACKAGE + ".checks.coding.SimplifyBooleanExpressionCheck"),
                new AbstractMap.SimpleEntry<>("SimplifyBooleanReturnCheck",
                    BASE_PACKAGE + ".checks.coding.SimplifyBooleanReturnCheck"),
                new AbstractMap.SimpleEntry<>("StringLiteralEqualityCheck",
                    BASE_PACKAGE + ".checks.coding.StringLiteralEqualityCheck"),
                new AbstractMap.SimpleEntry<>("SuperCloneCheck",
                    BASE_PACKAGE + ".checks.coding.SuperCloneCheck"),
                new AbstractMap.SimpleEntry<>("SuperFinalizeCheck",
                    BASE_PACKAGE + ".checks.coding.SuperFinalizeCheck"),
                new AbstractMap.SimpleEntry<>("UnnecessaryParenthesesCheck",
                    BASE_PACKAGE + ".checks.coding.UnnecessaryParenthesesCheck"),
                new AbstractMap.SimpleEntry<>("UnnecessarySemicolonAfterOuterTypeDeclarationCheck",
                    BASE_PACKAGE
                        + ".checks.coding.UnnecessarySemicolonAfterOuterTypeDeclarationCheck"),
                new AbstractMap.SimpleEntry<>(
                    "UnnecessarySemicolonAfterTypeMemberDeclarationCheck",
                    BASE_PACKAGE
                        + ".checks.coding.UnnecessarySemicolonAfterTypeMemberDeclarationCheck"),
                new AbstractMap.SimpleEntry<>("UnnecessarySemicolonInEnumerationCheck",
                    BASE_PACKAGE + ".checks.coding.UnnecessarySemicolonInEnumerationCheck"),
                new AbstractMap.SimpleEntry<>("UnnecessarySemicolonInTryWithResourcesCheck",
                    BASE_PACKAGE + ".checks.coding.UnnecessarySemicolonInTryWithResourcesCheck"),
                new AbstractMap.SimpleEntry<>("VariableDeclarationUsageDistanceCheck",
                    BASE_PACKAGE + ".checks.coding.VariableDeclarationUsageDistanceCheck"),
                new AbstractMap.SimpleEntry<>("NoArrayTrailingCommaCheck",
                    BASE_PACKAGE + ".checks.coding.NoArrayTrailingCommaCheck"),
                new AbstractMap.SimpleEntry<>("MatchXpathCheck",
                    BASE_PACKAGE + ".checks.coding.MatchXpathCheck"),
                new AbstractMap.SimpleEntry<>("UnusedLocalVariableCheck",
                    BASE_PACKAGE + ".checks.coding.UnusedLocalVariableCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from design package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromDesignPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("DesignForExtensionCheck",
                    BASE_PACKAGE + ".checks.design.DesignForExtensionCheck"),
                new AbstractMap.SimpleEntry<>("FinalClassCheck",
                    BASE_PACKAGE + ".checks.design.FinalClassCheck"),
                new AbstractMap.SimpleEntry<>("HideUtilityClassConstructorCheck",
                    BASE_PACKAGE + ".checks.design.HideUtilityClassConstructorCheck"),
                new AbstractMap.SimpleEntry<>("InnerTypeLastCheck",
                    BASE_PACKAGE + ".checks.design.InnerTypeLastCheck"),
                new AbstractMap.SimpleEntry<>("InterfaceIsTypeCheck",
                    BASE_PACKAGE + ".checks.design.InterfaceIsTypeCheck"),
                new AbstractMap.SimpleEntry<>("MutableExceptionCheck",
                    BASE_PACKAGE + ".checks.design.MutableExceptionCheck"),
                new AbstractMap.SimpleEntry<>("OneTopLevelClassCheck",
                    BASE_PACKAGE + ".checks.design.OneTopLevelClassCheck"),
                new AbstractMap.SimpleEntry<>("ThrowsCountCheck",
                    BASE_PACKAGE + ".checks.design.ThrowsCountCheck"),
                new AbstractMap.SimpleEntry<>("VisibilityModifierCheck",
                    BASE_PACKAGE + ".checks.design.VisibilityModifierCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from header package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromHeaderPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("HeaderCheck",
                    BASE_PACKAGE + ".checks.header.HeaderCheck"),
                new AbstractMap.SimpleEntry<>("RegexpHeaderCheck",
                    BASE_PACKAGE + ".checks.header.RegexpHeaderCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from imports package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromImportsPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("AvoidStarImportCheck",
                    BASE_PACKAGE + ".checks.imports.AvoidStarImportCheck"),
                new AbstractMap.SimpleEntry<>("AvoidStaticImportCheck",
                    BASE_PACKAGE + ".checks.imports.AvoidStaticImportCheck"),
                new AbstractMap.SimpleEntry<>("CustomImportOrderCheck",
                    BASE_PACKAGE + ".checks.imports.CustomImportOrderCheck"),
                new AbstractMap.SimpleEntry<>("IllegalImportCheck",
                    BASE_PACKAGE + ".checks.imports.IllegalImportCheck"),
                new AbstractMap.SimpleEntry<>("ImportControlCheck",
                    BASE_PACKAGE + ".checks.imports.ImportControlCheck"),
                new AbstractMap.SimpleEntry<>("ImportOrderCheck",
                    BASE_PACKAGE + ".checks.imports.ImportOrderCheck"),
                new AbstractMap.SimpleEntry<>("RedundantImportCheck",
                    BASE_PACKAGE + ".checks.imports.RedundantImportCheck"),
                new AbstractMap.SimpleEntry<>("UnusedImportsCheck",
                    BASE_PACKAGE + ".checks.imports.UnusedImportsCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from indentation package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromIndentationPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("CommentsIndentationCheck",
                    BASE_PACKAGE + ".checks.indentation.CommentsIndentationCheck"),
                new AbstractMap.SimpleEntry<>("IndentationCheck",
                    BASE_PACKAGE + ".checks.indentation.IndentationCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from javadoc package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromJavadocPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("AtclauseOrderCheck",
                    BASE_PACKAGE + ".checks.javadoc.AtclauseOrderCheck"),
                new AbstractMap.SimpleEntry<>("InvalidJavadocPositionCheck",
                    BASE_PACKAGE + ".checks.javadoc.InvalidJavadocPositionCheck"),
                new AbstractMap.SimpleEntry<>("JavadocBlockTagLocationCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocBlockTagLocationCheck"),
                new AbstractMap.SimpleEntry<>("JavadocContentLocationCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocContentLocationCheck"),
                new AbstractMap.SimpleEntry<>("JavadocMethodCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocMethodCheck"),
                new AbstractMap.SimpleEntry<>("JavadocMissingLeadingAsteriskCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocMissingLeadingAsteriskCheck"),
                new AbstractMap.SimpleEntry<>("JavadocMissingWhitespaceAfterAsteriskCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocMissingWhitespaceAfterAsteriskCheck"),
                new AbstractMap.SimpleEntry<>("JavadocPackageCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocPackageCheck"),
                new AbstractMap.SimpleEntry<>("JavadocParagraphCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocParagraphCheck"),
                new AbstractMap.SimpleEntry<>("JavadocStyleCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocStyleCheck"),
                new AbstractMap.SimpleEntry<>("JavadocTagContinuationIndentationCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocTagContinuationIndentationCheck"),
                new AbstractMap.SimpleEntry<>("JavadocTypeCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocTypeCheck"),
                new AbstractMap.SimpleEntry<>("JavadocVariableCheck",
                    BASE_PACKAGE + ".checks.javadoc.JavadocVariableCheck"),
                new AbstractMap.SimpleEntry<>("MissingJavadocMethodCheck",
                    BASE_PACKAGE + ".checks.javadoc.MissingJavadocMethodCheck"),
                new AbstractMap.SimpleEntry<>("MissingJavadocPackageCheck",
                    BASE_PACKAGE + ".checks.javadoc.MissingJavadocPackageCheck"),
                new AbstractMap.SimpleEntry<>("MissingJavadocTypeCheck",
                    BASE_PACKAGE + ".checks.javadoc.MissingJavadocTypeCheck"),
                new AbstractMap.SimpleEntry<>("NonEmptyAtclauseDescriptionCheck",
                    BASE_PACKAGE + ".checks.javadoc.NonEmptyAtclauseDescriptionCheck"),
                new AbstractMap.SimpleEntry<>("RequireEmptyLineBeforeBlockTagGroupCheck",
                    BASE_PACKAGE + ".checks.javadoc.RequireEmptyLineBeforeBlockTagGroupCheck"),
                new AbstractMap.SimpleEntry<>("SingleLineJavadocCheck",
                    BASE_PACKAGE + ".checks.javadoc.SingleLineJavadocCheck"),
                new AbstractMap.SimpleEntry<>("SummaryJavadocCheck",
                    BASE_PACKAGE + ".checks.javadoc.SummaryJavadocCheck"),
                new AbstractMap.SimpleEntry<>("WriteTagCheck",
                    BASE_PACKAGE + ".checks.javadoc.WriteTagCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from metrics package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromMetricsPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("BooleanExpressionComplexityCheck",
                    BASE_PACKAGE + ".checks.metrics.BooleanExpressionComplexityCheck"),
                new AbstractMap.SimpleEntry<>("ClassDataAbstractionCouplingCheck",
                    BASE_PACKAGE + ".checks.metrics.ClassDataAbstractionCouplingCheck"),
                new AbstractMap.SimpleEntry<>("ClassFanOutComplexityCheck",
                    BASE_PACKAGE + ".checks.metrics.ClassFanOutComplexityCheck"),
                new AbstractMap.SimpleEntry<>("CyclomaticComplexityCheck",
                    BASE_PACKAGE + ".checks.metrics.CyclomaticComplexityCheck"),
                new AbstractMap.SimpleEntry<>("JavaNCSSCheck",
                    BASE_PACKAGE + ".checks.metrics.JavaNCSSCheck"),
                new AbstractMap.SimpleEntry<>("NPathComplexityCheck",
                    BASE_PACKAGE + ".checks.metrics.NPathComplexityCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from modifier package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromModifierPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("ClassMemberImpliedModifierCheck",
                    BASE_PACKAGE + ".checks.modifier.ClassMemberImpliedModifierCheck"),
                new AbstractMap.SimpleEntry<>("InterfaceMemberImpliedModifierCheck",
                    BASE_PACKAGE + ".checks.modifier.InterfaceMemberImpliedModifierCheck"),
                new AbstractMap.SimpleEntry<>("ModifierOrderCheck",
                    BASE_PACKAGE + ".checks.modifier.ModifierOrderCheck"),
                new AbstractMap.SimpleEntry<>("RedundantModifierCheck",
                    BASE_PACKAGE + ".checks.modifier.RedundantModifierCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from naming package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromNamingPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("AbbreviationAsWordInNameCheck",
                    BASE_PACKAGE + ".checks.naming.AbbreviationAsWordInNameCheck"),
                new AbstractMap.SimpleEntry<>("AbstractClassNameCheck",
                    BASE_PACKAGE + ".checks.naming.AbstractClassNameCheck"),
                new AbstractMap.SimpleEntry<>("CatchParameterNameCheck",
                    BASE_PACKAGE + ".checks.naming.CatchParameterNameCheck"),
                new AbstractMap.SimpleEntry<>("ClassTypeParameterNameCheck",
                    BASE_PACKAGE + ".checks.naming.ClassTypeParameterNameCheck"),
                new AbstractMap.SimpleEntry<>("ConstantNameCheck",
                    BASE_PACKAGE + ".checks.naming.ConstantNameCheck"),
                new AbstractMap.SimpleEntry<>("InterfaceTypeParameterNameCheck",
                    BASE_PACKAGE + ".checks.naming.InterfaceTypeParameterNameCheck"),
                new AbstractMap.SimpleEntry<>("LambdaParameterNameCheck",
                    BASE_PACKAGE + ".checks.naming.LambdaParameterNameCheck"),
                new AbstractMap.SimpleEntry<>("LocalFinalVariableNameCheck",
                    BASE_PACKAGE + ".checks.naming.LocalFinalVariableNameCheck"),
                new AbstractMap.SimpleEntry<>("LocalVariableNameCheck",
                    BASE_PACKAGE + ".checks.naming.LocalVariableNameCheck"),
                new AbstractMap.SimpleEntry<>("MemberNameCheck",
                    BASE_PACKAGE + ".checks.naming.MemberNameCheck"),
                new AbstractMap.SimpleEntry<>("MethodNameCheck",
                    BASE_PACKAGE + ".checks.naming.MethodNameCheck"),
                new AbstractMap.SimpleEntry<>("MethodTypeParameterNameCheck",
                    BASE_PACKAGE + ".checks.naming.MethodTypeParameterNameCheck"),
                new AbstractMap.SimpleEntry<>("PackageNameCheck",
                    BASE_PACKAGE + ".checks.naming.PackageNameCheck"),
                new AbstractMap.SimpleEntry<>("ParameterNameCheck",
                    BASE_PACKAGE + ".checks.naming.ParameterNameCheck"),
                new AbstractMap.SimpleEntry<>("RecordComponentNameCheck",
                    BASE_PACKAGE + ".checks.naming.RecordComponentNameCheck"),
                new AbstractMap.SimpleEntry<>("RecordTypeParameterNameCheck",
                    BASE_PACKAGE + ".checks.naming.RecordTypeParameterNameCheck"),
                new AbstractMap.SimpleEntry<>("StaticVariableNameCheck",
                    BASE_PACKAGE + ".checks.naming.StaticVariableNameCheck"),
                new AbstractMap.SimpleEntry<>("TypeNameCheck",
                    BASE_PACKAGE + ".checks.naming.TypeNameCheck"),
                new AbstractMap.SimpleEntry<>("PatternVariableNameCheck",
                    BASE_PACKAGE + ".checks.naming.PatternVariableNameCheck"),
                new AbstractMap.SimpleEntry<>("IllegalIdentifierNameCheck",
                    BASE_PACKAGE + ".checks.naming.IllegalIdentifierNameCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from regexp package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromRegexpPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("RegexpCheck",
                    BASE_PACKAGE + ".checks.regexp.RegexpCheck"),
                new AbstractMap.SimpleEntry<>("RegexpMultilineCheck",
                    BASE_PACKAGE + ".checks.regexp.RegexpMultilineCheck"),
                new AbstractMap.SimpleEntry<>("RegexpOnFilenameCheck",
                    BASE_PACKAGE + ".checks.regexp.RegexpOnFilenameCheck"),
                new AbstractMap.SimpleEntry<>("RegexpSinglelineCheck",
                    BASE_PACKAGE + ".checks.regexp.RegexpSinglelineCheck"),
                new AbstractMap.SimpleEntry<>("RegexpSinglelineJavaCheck",
                    BASE_PACKAGE + ".checks.regexp.RegexpSinglelineJavaCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from sizes package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromSizesPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("AnonInnerLengthCheck",
                    BASE_PACKAGE + ".checks.sizes.AnonInnerLengthCheck"),
                new AbstractMap.SimpleEntry<>("ExecutableStatementCountCheck",
                    BASE_PACKAGE + ".checks.sizes.ExecutableStatementCountCheck"),
                new AbstractMap.SimpleEntry<>("FileLengthCheck",
                    BASE_PACKAGE + ".checks.sizes.FileLengthCheck"),
                new AbstractMap.SimpleEntry<>("LambdaBodyLengthCheck",
                    BASE_PACKAGE + ".checks.sizes.LambdaBodyLengthCheck"),
                new AbstractMap.SimpleEntry<>("LineLengthCheck",
                    BASE_PACKAGE + ".checks.sizes.LineLengthCheck"),
                new AbstractMap.SimpleEntry<>("MethodCountCheck",
                    BASE_PACKAGE + ".checks.sizes.MethodCountCheck"),
                new AbstractMap.SimpleEntry<>("MethodLengthCheck",
                    BASE_PACKAGE + ".checks.sizes.MethodLengthCheck"),
                new AbstractMap.SimpleEntry<>("OuterTypeNumberCheck",
                    BASE_PACKAGE + ".checks.sizes.OuterTypeNumberCheck"),
                new AbstractMap.SimpleEntry<>("ParameterNumberCheck",
                    BASE_PACKAGE + ".checks.sizes.ParameterNumberCheck"),
                new AbstractMap.SimpleEntry<>("RecordComponentNumberCheck",
                    BASE_PACKAGE + ".checks.sizes.RecordComponentNumberCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with Checks from whitespace package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillChecksFromWhitespacePackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("EmptyForInitializerPadCheck",
                    BASE_PACKAGE + ".checks.whitespace.EmptyForInitializerPadCheck"),
                new AbstractMap.SimpleEntry<>("EmptyForIteratorPadCheck",
                    BASE_PACKAGE + ".checks.whitespace.EmptyForIteratorPadCheck"),
                new AbstractMap.SimpleEntry<>("EmptyLineSeparatorCheck",
                    BASE_PACKAGE + ".checks.whitespace.EmptyLineSeparatorCheck"),
                new AbstractMap.SimpleEntry<>("FileTabCharacterCheck",
                    BASE_PACKAGE + ".checks.whitespace.FileTabCharacterCheck"),
                new AbstractMap.SimpleEntry<>("GenericWhitespaceCheck",
                    BASE_PACKAGE + ".checks.whitespace.GenericWhitespaceCheck"),
                new AbstractMap.SimpleEntry<>("MethodParamPadCheck",
                    BASE_PACKAGE + ".checks.whitespace.MethodParamPadCheck"),
                new AbstractMap.SimpleEntry<>("NoLineWrapCheck",
                    BASE_PACKAGE + ".checks.whitespace.NoLineWrapCheck"),
                new AbstractMap.SimpleEntry<>("NoWhitespaceAfterCheck",
                    BASE_PACKAGE + ".checks.whitespace.NoWhitespaceAfterCheck"),
                new AbstractMap.SimpleEntry<>("NoWhitespaceBeforeCheck",
                    BASE_PACKAGE + ".checks.whitespace.NoWhitespaceBeforeCheck"),
                new AbstractMap.SimpleEntry<>("NoWhitespaceBeforeCaseDefaultColonCheck",
                    BASE_PACKAGE + ".checks.whitespace.NoWhitespaceBeforeCaseDefaultColonCheck"),
                new AbstractMap.SimpleEntry<>("OperatorWrapCheck",
                    BASE_PACKAGE + ".checks.whitespace.OperatorWrapCheck"),
                new AbstractMap.SimpleEntry<>("ParenPadCheck",
                    BASE_PACKAGE + ".checks.whitespace.ParenPadCheck"),
                new AbstractMap.SimpleEntry<>("SeparatorWrapCheck",
                    BASE_PACKAGE + ".checks.whitespace.SeparatorWrapCheck"),
                new AbstractMap.SimpleEntry<>("SingleSpaceSeparatorCheck",
                    BASE_PACKAGE + ".checks.whitespace.SingleSpaceSeparatorCheck"),
                new AbstractMap.SimpleEntry<>("TypecastParenPadCheck",
                    BASE_PACKAGE + ".checks.whitespace.TypecastParenPadCheck"),
                new AbstractMap.SimpleEntry<>("WhitespaceAfterCheck",
                    BASE_PACKAGE + ".checks.whitespace.WhitespaceAfterCheck"),
                new AbstractMap.SimpleEntry<>("WhitespaceAroundCheck",
                    BASE_PACKAGE + ".checks.whitespace.WhitespaceAroundCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with modules from checks package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillModulesFromChecksPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("ArrayTypeStyleCheck",
                    BASE_PACKAGE + ".checks.ArrayTypeStyleCheck"),
                new AbstractMap.SimpleEntry<>("AvoidEscapedUnicodeCharactersCheck",
                    BASE_PACKAGE + ".checks.AvoidEscapedUnicodeCharactersCheck"),
                new AbstractMap.SimpleEntry<>("DescendantTokenCheck",
                    BASE_PACKAGE + ".checks.DescendantTokenCheck"),
                new AbstractMap.SimpleEntry<>("FinalParametersCheck",
                    BASE_PACKAGE + ".checks.FinalParametersCheck"),
                new AbstractMap.SimpleEntry<>("NewlineAtEndOfFileCheck",
                    BASE_PACKAGE + ".checks.NewlineAtEndOfFileCheck"),
                new AbstractMap.SimpleEntry<>("NoCodeInFileCheck",
                    BASE_PACKAGE + ".checks.NoCodeInFileCheck"),
                new AbstractMap.SimpleEntry<>("OuterTypeFilenameCheck",
                    BASE_PACKAGE + ".checks.OuterTypeFilenameCheck"),
                new AbstractMap.SimpleEntry<>("OrderedPropertiesCheck",
                    BASE_PACKAGE + ".checks.OrderedPropertiesCheck"),
                new AbstractMap.SimpleEntry<>("SuppressWarningsHolder",
                    BASE_PACKAGE + ".checks.SuppressWarningsHolder"),
                new AbstractMap.SimpleEntry<>("TodoCommentCheck",
                    BASE_PACKAGE + ".checks.TodoCommentCheck"),
                new AbstractMap.SimpleEntry<>("TrailingCommentCheck",
                    BASE_PACKAGE + ".checks.TrailingCommentCheck"),
                new AbstractMap.SimpleEntry<>("TranslationCheck",
                    BASE_PACKAGE + ".checks.TranslationCheck"),
                new AbstractMap.SimpleEntry<>("UncommentedMainCheck",
                    BASE_PACKAGE + ".checks.UncommentedMainCheck"),
                new AbstractMap.SimpleEntry<>("UniquePropertiesCheck",
                    BASE_PACKAGE + ".checks.UniquePropertiesCheck"),
                new AbstractMap.SimpleEntry<>("UpperEllCheck",
                    BASE_PACKAGE + ".checks.UpperEllCheck"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with modules from filefilters package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillModulesFromFilefiltersPackage() {
        return Collections.singletonMap("BeforeExecutionExclusionFileFilter",
            BASE_PACKAGE + ".filefilters.BeforeExecutionExclusionFileFilter");
    }

    /**
     * Fill short-to-full module names map with modules from filters package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillModulesFromFiltersPackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("SeverityMatchFilter",
                    BASE_PACKAGE + ".filters.SeverityMatchFilter"),
                new AbstractMap.SimpleEntry<>("SuppressWithPlainTextCommentFilter",
                    BASE_PACKAGE + ".filters.SuppressWithPlainTextCommentFilter"),
                new AbstractMap.SimpleEntry<>("SuppressionCommentFilter",
                    BASE_PACKAGE + ".filters.SuppressionCommentFilter"),
                new AbstractMap.SimpleEntry<>("SuppressionFilter",
                    BASE_PACKAGE + ".filters.SuppressionFilter"),
                new AbstractMap.SimpleEntry<>("SuppressionSingleFilter",
                    BASE_PACKAGE + ".filters.SuppressionSingleFilter"),
                new AbstractMap.SimpleEntry<>("SuppressionXpathFilter",
                    BASE_PACKAGE + ".filters.SuppressionXpathFilter"),
                new AbstractMap.SimpleEntry<>("SuppressionXpathSingleFilter",
                    BASE_PACKAGE + ".filters.SuppressionXpathSingleFilter"),
                new AbstractMap.SimpleEntry<>("SuppressWarningsFilter",
                    BASE_PACKAGE + ".filters.SuppressWarningsFilter"),
                new AbstractMap.SimpleEntry<>("SuppressWithNearbyCommentFilter",
                    BASE_PACKAGE + ".filters.SuppressWithNearbyCommentFilter"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

    /**
     * Fill short-to-full module names map with modules from checkstyle package.
     *
     * @return map of package names
     */
    private static Map<String, String> fillModulesFromCheckstylePackage() {
        return Stream.of(
                new AbstractMap.SimpleEntry<>("Checker", BASE_PACKAGE + ".Checker"),
                new AbstractMap.SimpleEntry<>("TreeWalker", BASE_PACKAGE + ".TreeWalker"))
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                Collections::unmodifiableMap));
    }

}
