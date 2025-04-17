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

package com.puppycrawl.tools.checkstyle;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
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
    private static final Map<String, String> NAME_TO_FULL_MODULE_NAME = new HashMap<>();

    /** Package names to prepend to class names. */
    private final Set<String> packages;

    /** The class loader used to load Checkstyle core and custom modules. */
    private final ClassLoader moduleClassLoader;

    /** Map of third party Checkstyle module names to the set of their fully qualified names. */
    private Map<String, Set<String>> thirdPartyNameToFullModuleNames;

    /** Module load option which defines class search type. */
    private ModuleLoadOption moduleLoadOption;

    static {
        fillShortToFullModuleNamesMap();
    }

    /**
     * Creates a new {@code PackageObjectFactory} instance.
     *
     * @param packageNames package names to use
     * @param moduleClassLoader class loader used to load Checkstyle
     *          core and custom modules
     */
    public PackageObjectFactory(Set<String> packageNames, ClassLoader moduleClassLoader) {
        this(packageNames, moduleClassLoader, ModuleLoadOption.SEARCH_REGISTERED_PACKAGES);
    }

    /**
     * Creates a new {@code PackageObjectFactory} instance.
     *
     * @param packageNames package names to use
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
                instance = createObjectFromClassPath(name);
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
            final LocalizedMessage exceptionMessage = new LocalizedMessage(
                Definitions.CHECKSTYLE_BUNDLE, getClass(),
                UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE, name, attemptedNames);
            throw new CheckstyleException(exceptionMessage.getMessage());
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
     * Create object with the help of the classpath.
     *
     * @param name name of module.
     * @return instance of module if it is found in modules map and no ambiguous classes exist.
     * @throws CheckstyleException if the class fails to instantiate or there are ambiguous classes.
     */
    private Object createObjectFromClassPath(String name)
            throws CheckstyleException {
        thirdPartyNameToFullModuleNames = lazyLoad(
                thirdPartyNameToFullModuleNames,
                () -> generateThirdPartyNameToFullModuleName(moduleClassLoader)
        );
        final Set<String> fullModuleNames = thirdPartyNameToFullModuleNames.get(name);
        Object instance = null;
        if (fullModuleNames == null) {
            final Set<String> fullCheckModuleNames =
                    thirdPartyNameToFullModuleNames.get(name + CHECK_SUFFIX);
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
            final LocalizedMessage exceptionMessage = new LocalizedMessage(
                    Definitions.CHECKSTYLE_BUNDLE, getClass(),
                    AMBIGUOUS_MODULE_NAME_EXCEPTION_MESSAGE, name, optionalNames);
            throw new CheckstyleException(exceptionMessage.getMessage());
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
                    Collectors.mapping(
                        Class::getCanonicalName,
                        Collectors.toCollection(HashSet::new))));
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
            .collect(Collectors.toUnmodifiableList());
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
     * Initialize object by supplier if object is null.
     *
     * @param <T> type of object
     * @param object object to initialize
     * @param supplier function to initialize if object is null
     * @return object as it was provided in method or initialized
     */
    private static <T> T lazyLoad(T object, Supplier<T> supplier) {
        final T result;
        if (object == null) {
            result = supplier.get();
        }
        else {
            result = object;
        }
        return result;
    }

    /**
     * Fill short-to-full module names map.
     */
    private static void fillShortToFullModuleNamesMap() {
        fillChecksFromAnnotationPackage();
        fillChecksFromBlocksPackage();
        fillChecksFromCodingPackage();
        fillChecksFromDesignPackage();
        fillChecksFromHeaderPackage();
        fillChecksFromImportsPackage();
        fillChecksFromIndentationPackage();
        fillChecksFromJavadocPackage();
        fillChecksFromMetricsPackage();
        fillChecksFromModifierPackage();
        fillChecksFromNamingPackage();
        fillChecksFromRegexpPackage();
        fillChecksFromSizesPackage();
        fillChecksFromWhitespacePackage();
        fillModulesFromChecksPackage();
        fillModulesFromFilefiltersPackage();
        fillModulesFromFiltersPackage();
        fillModulesFromCheckstylePackage();
    }

    /**
     * Fill short-to-full module names map with Checks from annotation package.
     */
    private static void fillChecksFromAnnotationPackage() {
        NAME_TO_FULL_MODULE_NAME.put("AnnotationLocationCheck",
                BASE_PACKAGE + ".checks.annotation.AnnotationLocationCheck");
        NAME_TO_FULL_MODULE_NAME.put("AnnotationOnSameLineCheck",
                BASE_PACKAGE + ".checks.annotation.AnnotationOnSameLineCheck");
        NAME_TO_FULL_MODULE_NAME.put("AnnotationUseStyleCheck",
                BASE_PACKAGE + ".checks.annotation.AnnotationUseStyleCheck");
        NAME_TO_FULL_MODULE_NAME.put("MissingDeprecatedCheck",
                BASE_PACKAGE + ".checks.annotation.MissingDeprecatedCheck");
        NAME_TO_FULL_MODULE_NAME.put("MissingOverrideCheck",
                BASE_PACKAGE + ".checks.annotation.MissingOverrideCheck");
        NAME_TO_FULL_MODULE_NAME.put("PackageAnnotationCheck",
                BASE_PACKAGE + ".checks.annotation.PackageAnnotationCheck");
        NAME_TO_FULL_MODULE_NAME.put("SuppressWarningsCheck",
                BASE_PACKAGE + ".checks.annotation.SuppressWarningsCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from blocks package.
     */
    private static void fillChecksFromBlocksPackage() {
        NAME_TO_FULL_MODULE_NAME.put("AvoidNestedBlocksCheck",
                BASE_PACKAGE + ".checks.blocks.AvoidNestedBlocksCheck");
        NAME_TO_FULL_MODULE_NAME.put("EmptyBlockCheck",
                BASE_PACKAGE + ".checks.blocks.EmptyBlockCheck");
        NAME_TO_FULL_MODULE_NAME.put("EmptyCatchBlockCheck",
                BASE_PACKAGE + ".checks.blocks.EmptyCatchBlockCheck");
        NAME_TO_FULL_MODULE_NAME.put("LeftCurlyCheck",
                BASE_PACKAGE + ".checks.blocks.LeftCurlyCheck");
        NAME_TO_FULL_MODULE_NAME.put("NeedBracesCheck",
                BASE_PACKAGE + ".checks.blocks.NeedBracesCheck");
        NAME_TO_FULL_MODULE_NAME.put("RightCurlyCheck",
                BASE_PACKAGE + ".checks.blocks.RightCurlyCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from coding package.
     */
    // -@cs[ExecutableStatementCount] splitting this method is not reasonable.
    // -@cs[JavaNCSS] splitting this method is not reasonable.
    private static void fillChecksFromCodingPackage() {
        NAME_TO_FULL_MODULE_NAME.put("ArrayTrailingCommaCheck",
                BASE_PACKAGE + ".checks.coding.ArrayTrailingCommaCheck");
        NAME_TO_FULL_MODULE_NAME.put("AvoidDoubleBraceInitializationCheck",
                BASE_PACKAGE + ".checks.coding.AvoidDoubleBraceInitializationCheck");
        NAME_TO_FULL_MODULE_NAME.put("AvoidInlineConditionalsCheck",
                BASE_PACKAGE + ".checks.coding.AvoidInlineConditionalsCheck");
        NAME_TO_FULL_MODULE_NAME.put("AvoidNoArgumentSuperConstructorCallCheck",
                BASE_PACKAGE + ".checks.coding.AvoidNoArgumentSuperConstructorCallCheck");
        NAME_TO_FULL_MODULE_NAME.put("ConstructorsDeclarationGroupingCheck",
                BASE_PACKAGE + ".checks.coding.ConstructorsDeclarationGroupingCheck");
        NAME_TO_FULL_MODULE_NAME.put("CovariantEqualsCheck",
                BASE_PACKAGE + ".checks.coding.CovariantEqualsCheck");
        NAME_TO_FULL_MODULE_NAME.put("DeclarationOrderCheck",
                BASE_PACKAGE + ".checks.coding.DeclarationOrderCheck");
        NAME_TO_FULL_MODULE_NAME.put("DefaultComesLastCheck",
                BASE_PACKAGE + ".checks.coding.DefaultComesLastCheck");
        NAME_TO_FULL_MODULE_NAME.put("EmptyStatementCheck",
                BASE_PACKAGE + ".checks.coding.EmptyStatementCheck");
        NAME_TO_FULL_MODULE_NAME.put("EqualsAvoidNullCheck",
                BASE_PACKAGE + ".checks.coding.EqualsAvoidNullCheck");
        NAME_TO_FULL_MODULE_NAME.put("EqualsHashCodeCheck",
                BASE_PACKAGE + ".checks.coding.EqualsHashCodeCheck");
        NAME_TO_FULL_MODULE_NAME.put("ExplicitInitializationCheck",
                BASE_PACKAGE + ".checks.coding.ExplicitInitializationCheck");
        NAME_TO_FULL_MODULE_NAME.put("FallThroughCheck",
                BASE_PACKAGE + ".checks.coding.FallThroughCheck");
        NAME_TO_FULL_MODULE_NAME.put("FinalLocalVariableCheck",
                BASE_PACKAGE + ".checks.coding.FinalLocalVariableCheck");
        NAME_TO_FULL_MODULE_NAME.put("HiddenFieldCheck",
                BASE_PACKAGE + ".checks.coding.HiddenFieldCheck");
        NAME_TO_FULL_MODULE_NAME.put("IllegalCatchCheck",
                BASE_PACKAGE + ".checks.coding.IllegalCatchCheck");
        NAME_TO_FULL_MODULE_NAME.put("IllegalInstantiationCheck",
                BASE_PACKAGE + ".checks.coding.IllegalInstantiationCheck");
        NAME_TO_FULL_MODULE_NAME.put("IllegalThrowsCheck",
                BASE_PACKAGE + ".checks.coding.IllegalThrowsCheck");
        NAME_TO_FULL_MODULE_NAME.put("IllegalTokenCheck",
                BASE_PACKAGE + ".checks.coding.IllegalTokenCheck");
        NAME_TO_FULL_MODULE_NAME.put("IllegalTokenTextCheck",
                BASE_PACKAGE + ".checks.coding.IllegalTokenTextCheck");
        NAME_TO_FULL_MODULE_NAME.put("IllegalTypeCheck",
                BASE_PACKAGE + ".checks.coding.IllegalTypeCheck");
        NAME_TO_FULL_MODULE_NAME.put("InnerAssignmentCheck",
                BASE_PACKAGE + ".checks.coding.InnerAssignmentCheck");
        NAME_TO_FULL_MODULE_NAME.put("MagicNumberCheck",
                BASE_PACKAGE + ".checks.coding.MagicNumberCheck");
        NAME_TO_FULL_MODULE_NAME.put("MissingCtorCheck",
                BASE_PACKAGE + ".checks.coding.MissingCtorCheck");
        NAME_TO_FULL_MODULE_NAME.put("MissingNullCaseInSwitchCheck",
                BASE_PACKAGE + ".checks.coding.MissingNullCaseInSwitchCheck");
        NAME_TO_FULL_MODULE_NAME.put("MissingSwitchDefaultCheck",
                BASE_PACKAGE + ".checks.coding.MissingSwitchDefaultCheck");
        NAME_TO_FULL_MODULE_NAME.put("ModifiedControlVariableCheck",
                BASE_PACKAGE + ".checks.coding.ModifiedControlVariableCheck");
        NAME_TO_FULL_MODULE_NAME.put("MultipleStringLiteralsCheck",
                BASE_PACKAGE + ".checks.coding.MultipleStringLiteralsCheck");
        NAME_TO_FULL_MODULE_NAME.put("MultipleVariableDeclarationsCheck",
                BASE_PACKAGE + ".checks.coding.MultipleVariableDeclarationsCheck");
        NAME_TO_FULL_MODULE_NAME.put("NestedForDepthCheck",
                BASE_PACKAGE + ".checks.coding.NestedForDepthCheck");
        NAME_TO_FULL_MODULE_NAME.put("NestedIfDepthCheck",
                BASE_PACKAGE + ".checks.coding.NestedIfDepthCheck");
        NAME_TO_FULL_MODULE_NAME.put("NestedTryDepthCheck",
                BASE_PACKAGE + ".checks.coding.NestedTryDepthCheck");
        NAME_TO_FULL_MODULE_NAME.put("NoCloneCheck",
                BASE_PACKAGE + ".checks.coding.NoCloneCheck");
        NAME_TO_FULL_MODULE_NAME.put("NoEnumTrailingCommaCheck",
                BASE_PACKAGE + ".checks.coding.NoEnumTrailingCommaCheck");
        NAME_TO_FULL_MODULE_NAME.put("NoFinalizerCheck",
                BASE_PACKAGE + ".checks.coding.NoFinalizerCheck");
        NAME_TO_FULL_MODULE_NAME.put("OneStatementPerLineCheck",
                BASE_PACKAGE + ".checks.coding.OneStatementPerLineCheck");
        NAME_TO_FULL_MODULE_NAME.put("OverloadMethodsDeclarationOrderCheck",
                BASE_PACKAGE + ".checks.coding.OverloadMethodsDeclarationOrderCheck");
        NAME_TO_FULL_MODULE_NAME.put("PackageDeclarationCheck",
                BASE_PACKAGE + ".checks.coding.PackageDeclarationCheck");
        NAME_TO_FULL_MODULE_NAME.put("ParameterAssignmentCheck",
                BASE_PACKAGE + ".checks.coding.ParameterAssignmentCheck");
        NAME_TO_FULL_MODULE_NAME.put("RequireThisCheck",
                BASE_PACKAGE + ".checks.coding.RequireThisCheck");
        NAME_TO_FULL_MODULE_NAME.put("ReturnCountCheck",
                BASE_PACKAGE + ".checks.coding.ReturnCountCheck");
        NAME_TO_FULL_MODULE_NAME.put("SimplifyBooleanExpressionCheck",
                BASE_PACKAGE + ".checks.coding.SimplifyBooleanExpressionCheck");
        NAME_TO_FULL_MODULE_NAME.put("SimplifyBooleanReturnCheck",
                BASE_PACKAGE + ".checks.coding.SimplifyBooleanReturnCheck");
        NAME_TO_FULL_MODULE_NAME.put("StringLiteralEqualityCheck",
                BASE_PACKAGE + ".checks.coding.StringLiteralEqualityCheck");
        NAME_TO_FULL_MODULE_NAME.put("SuperCloneCheck",
                BASE_PACKAGE + ".checks.coding.SuperCloneCheck");
        NAME_TO_FULL_MODULE_NAME.put("SuperFinalizeCheck",
                BASE_PACKAGE + ".checks.coding.SuperFinalizeCheck");
        NAME_TO_FULL_MODULE_NAME.put("UnnecessaryParenthesesCheck",
                BASE_PACKAGE + ".checks.coding.UnnecessaryParenthesesCheck");
        NAME_TO_FULL_MODULE_NAME.put("UnnecessarySemicolonAfterOuterTypeDeclarationCheck",
                BASE_PACKAGE
                        + ".checks.coding.UnnecessarySemicolonAfterOuterTypeDeclarationCheck");
        NAME_TO_FULL_MODULE_NAME.put("UnnecessarySemicolonAfterTypeMemberDeclarationCheck",
                BASE_PACKAGE
                    + ".checks.coding.UnnecessarySemicolonAfterTypeMemberDeclarationCheck");
        NAME_TO_FULL_MODULE_NAME.put("UnnecessarySemicolonInEnumerationCheck",
                BASE_PACKAGE + ".checks.coding.UnnecessarySemicolonInEnumerationCheck");
        NAME_TO_FULL_MODULE_NAME.put("UnnecessarySemicolonInTryWithResourcesCheck",
                BASE_PACKAGE + ".checks.coding.UnnecessarySemicolonInTryWithResourcesCheck");
        NAME_TO_FULL_MODULE_NAME.put("VariableDeclarationUsageDistanceCheck",
                BASE_PACKAGE + ".checks.coding.VariableDeclarationUsageDistanceCheck");
        NAME_TO_FULL_MODULE_NAME.put("WhenShouldBeUsed",
                BASE_PACKAGE + ".checks.coding.WhenShouldBeUsedCheck");
        NAME_TO_FULL_MODULE_NAME.put("NoArrayTrailingCommaCheck",
                BASE_PACKAGE + ".checks.coding.NoArrayTrailingCommaCheck");
        NAME_TO_FULL_MODULE_NAME.put("MatchXpathCheck",
                BASE_PACKAGE + ".checks.coding.MatchXpathCheck");
        NAME_TO_FULL_MODULE_NAME.put("UnusedLocalVariableCheck",
                BASE_PACKAGE + ".checks.coding.UnusedLocalVariableCheck");
        NAME_TO_FULL_MODULE_NAME.put("UnusedCatchParameterShouldBeUnnamedCheck",
                BASE_PACKAGE + ".checks.coding.UnusedCatchParameterShouldBeUnnamedCheck");
        NAME_TO_FULL_MODULE_NAME.put("UnusedLambdaParameterShouldBeUnnamedCheck",
                BASE_PACKAGE + ".checks.coding.UnusedLambdaParameterShouldBeUnnamedCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from design package.
     */
    private static void fillChecksFromDesignPackage() {
        NAME_TO_FULL_MODULE_NAME.put("DesignForExtensionCheck",
                BASE_PACKAGE + ".checks.design.DesignForExtensionCheck");
        NAME_TO_FULL_MODULE_NAME.put("FinalClassCheck",
                BASE_PACKAGE + ".checks.design.FinalClassCheck");
        NAME_TO_FULL_MODULE_NAME.put("HideUtilityClassConstructorCheck",
                BASE_PACKAGE + ".checks.design.HideUtilityClassConstructorCheck");
        NAME_TO_FULL_MODULE_NAME.put("InnerTypeLastCheck",
                BASE_PACKAGE + ".checks.design.InnerTypeLastCheck");
        NAME_TO_FULL_MODULE_NAME.put("InterfaceIsTypeCheck",
                BASE_PACKAGE + ".checks.design.InterfaceIsTypeCheck");
        NAME_TO_FULL_MODULE_NAME.put("MutableExceptionCheck",
                BASE_PACKAGE + ".checks.design.MutableExceptionCheck");
        NAME_TO_FULL_MODULE_NAME.put("OneTopLevelClassCheck",
                BASE_PACKAGE + ".checks.design.OneTopLevelClassCheck");
        NAME_TO_FULL_MODULE_NAME.put("SealedShouldHavePermitsListCheck",
                BASE_PACKAGE + ".checks.design.SealedShouldHavePermitsListCheck");
        NAME_TO_FULL_MODULE_NAME.put("ThrowsCountCheck",
                BASE_PACKAGE + ".checks.design.ThrowsCountCheck");
        NAME_TO_FULL_MODULE_NAME.put("VisibilityModifierCheck",
                BASE_PACKAGE + ".checks.design.VisibilityModifierCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from header package.
     */
    private static void fillChecksFromHeaderPackage() {
        NAME_TO_FULL_MODULE_NAME.put("HeaderCheck",
                BASE_PACKAGE + ".checks.header.HeaderCheck");
        NAME_TO_FULL_MODULE_NAME.put("RegexpHeaderCheck",
                BASE_PACKAGE + ".checks.header.RegexpHeaderCheck");
        NAME_TO_FULL_MODULE_NAME.put("MultiFileRegexpHeaderCheck",
                BASE_PACKAGE + ".checks.header.MultiFileRegexpHeaderCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from imports package.
     */
    private static void fillChecksFromImportsPackage() {
        NAME_TO_FULL_MODULE_NAME.put("AvoidStarImportCheck",
                BASE_PACKAGE + ".checks.imports.AvoidStarImportCheck");
        NAME_TO_FULL_MODULE_NAME.put("AvoidStaticImportCheck",
                BASE_PACKAGE + ".checks.imports.AvoidStaticImportCheck");
        NAME_TO_FULL_MODULE_NAME.put("CustomImportOrderCheck",
                BASE_PACKAGE + ".checks.imports.CustomImportOrderCheck");
        NAME_TO_FULL_MODULE_NAME.put("IllegalImportCheck",
                BASE_PACKAGE + ".checks.imports.IllegalImportCheck");
        NAME_TO_FULL_MODULE_NAME.put("ImportControlCheck",
                BASE_PACKAGE + ".checks.imports.ImportControlCheck");
        NAME_TO_FULL_MODULE_NAME.put("ImportOrderCheck",
                BASE_PACKAGE + ".checks.imports.ImportOrderCheck");
        NAME_TO_FULL_MODULE_NAME.put("RedundantImportCheck",
                BASE_PACKAGE + ".checks.imports.RedundantImportCheck");
        NAME_TO_FULL_MODULE_NAME.put("UnusedImportsCheck",
                BASE_PACKAGE + ".checks.imports.UnusedImportsCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from indentation package.
     */
    private static void fillChecksFromIndentationPackage() {
        NAME_TO_FULL_MODULE_NAME.put("CommentsIndentationCheck",
                BASE_PACKAGE + ".checks.indentation.CommentsIndentationCheck");
        NAME_TO_FULL_MODULE_NAME.put("IndentationCheck",
                BASE_PACKAGE + ".checks.indentation.IndentationCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from javadoc package.
     */
    private static void fillChecksFromJavadocPackage() {
        NAME_TO_FULL_MODULE_NAME.put("AtclauseOrderCheck",
                BASE_PACKAGE + ".checks.javadoc.AtclauseOrderCheck");
        NAME_TO_FULL_MODULE_NAME.put("InvalidJavadocPositionCheck",
                BASE_PACKAGE + ".checks.javadoc.InvalidJavadocPositionCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocBlockTagLocationCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocBlockTagLocationCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocContentLocationCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocContentLocationCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocLeadingAsteriskAlignCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocLeadingAsteriskAlignCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocMethodCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocMethodCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocMissingLeadingAsteriskCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocMissingLeadingAsteriskCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocMissingWhitespaceAfterAsteriskCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocMissingWhitespaceAfterAsteriskCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocPackageCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocPackageCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocParagraphCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocParagraphCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocStyleCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocStyleCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocTagContinuationIndentationCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocTagContinuationIndentationCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocTypeCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocTypeCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavadocVariableCheck",
                BASE_PACKAGE + ".checks.javadoc.JavadocVariableCheck");
        NAME_TO_FULL_MODULE_NAME.put("MissingJavadocMethodCheck",
                BASE_PACKAGE + ".checks.javadoc.MissingJavadocMethodCheck");
        NAME_TO_FULL_MODULE_NAME.put("MissingJavadocPackageCheck",
                BASE_PACKAGE + ".checks.javadoc.MissingJavadocPackageCheck");
        NAME_TO_FULL_MODULE_NAME.put("MissingJavadocTypeCheck",
                BASE_PACKAGE + ".checks.javadoc.MissingJavadocTypeCheck");
        NAME_TO_FULL_MODULE_NAME.put("NonEmptyAtclauseDescriptionCheck",
                BASE_PACKAGE + ".checks.javadoc.NonEmptyAtclauseDescriptionCheck");
        NAME_TO_FULL_MODULE_NAME.put("RequireEmptyLineBeforeBlockTagGroupCheck",
                BASE_PACKAGE + ".checks.javadoc.RequireEmptyLineBeforeBlockTagGroupCheck");
        NAME_TO_FULL_MODULE_NAME.put("SingleLineJavadocCheck",
                BASE_PACKAGE + ".checks.javadoc.SingleLineJavadocCheck");
        NAME_TO_FULL_MODULE_NAME.put("SummaryJavadocCheck",
                BASE_PACKAGE + ".checks.javadoc.SummaryJavadocCheck");
        NAME_TO_FULL_MODULE_NAME.put("WriteTagCheck",
                BASE_PACKAGE + ".checks.javadoc.WriteTagCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from metrics package.
     */
    private static void fillChecksFromMetricsPackage() {
        NAME_TO_FULL_MODULE_NAME.put("BooleanExpressionComplexityCheck",
                BASE_PACKAGE + ".checks.metrics.BooleanExpressionComplexityCheck");
        NAME_TO_FULL_MODULE_NAME.put("ClassDataAbstractionCouplingCheck",
                BASE_PACKAGE + ".checks.metrics.ClassDataAbstractionCouplingCheck");
        NAME_TO_FULL_MODULE_NAME.put("ClassFanOutComplexityCheck",
                BASE_PACKAGE + ".checks.metrics.ClassFanOutComplexityCheck");
        NAME_TO_FULL_MODULE_NAME.put("CyclomaticComplexityCheck",
                BASE_PACKAGE + ".checks.metrics.CyclomaticComplexityCheck");
        NAME_TO_FULL_MODULE_NAME.put("JavaNCSSCheck",
                BASE_PACKAGE + ".checks.metrics.JavaNCSSCheck");
        NAME_TO_FULL_MODULE_NAME.put("NPathComplexityCheck",
                BASE_PACKAGE + ".checks.metrics.NPathComplexityCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from modifier package.
     */
    private static void fillChecksFromModifierPackage() {
        NAME_TO_FULL_MODULE_NAME.put("ClassMemberImpliedModifierCheck",
            BASE_PACKAGE + ".checks.modifier.ClassMemberImpliedModifierCheck");
        NAME_TO_FULL_MODULE_NAME.put("InterfaceMemberImpliedModifierCheck",
            BASE_PACKAGE + ".checks.modifier.InterfaceMemberImpliedModifierCheck");
        NAME_TO_FULL_MODULE_NAME.put("ModifierOrderCheck",
                BASE_PACKAGE + ".checks.modifier.ModifierOrderCheck");
        NAME_TO_FULL_MODULE_NAME.put("RedundantModifierCheck",
                BASE_PACKAGE + ".checks.modifier.RedundantModifierCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from naming package.
     */
    private static void fillChecksFromNamingPackage() {
        NAME_TO_FULL_MODULE_NAME.put("AbbreviationAsWordInNameCheck",
                BASE_PACKAGE + ".checks.naming.AbbreviationAsWordInNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("AbstractClassNameCheck",
                BASE_PACKAGE + ".checks.naming.AbstractClassNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("CatchParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.CatchParameterNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("ClassTypeParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.ClassTypeParameterNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("ConstantNameCheck",
                BASE_PACKAGE + ".checks.naming.ConstantNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("InterfaceTypeParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.InterfaceTypeParameterNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("LambdaParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.LambdaParameterNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("LocalFinalVariableNameCheck",
                BASE_PACKAGE + ".checks.naming.LocalFinalVariableNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("LocalVariableNameCheck",
                BASE_PACKAGE + ".checks.naming.LocalVariableNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("MemberNameCheck",
                BASE_PACKAGE + ".checks.naming.MemberNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("MethodNameCheck",
                BASE_PACKAGE + ".checks.naming.MethodNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("MethodTypeParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.MethodTypeParameterNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("PackageNameCheck",
                BASE_PACKAGE + ".checks.naming.PackageNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("ParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.ParameterNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("RecordComponentNameCheck",
                BASE_PACKAGE + ".checks.naming.RecordComponentNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("RecordTypeParameterNameCheck",
                BASE_PACKAGE + ".checks.naming.RecordTypeParameterNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("StaticVariableNameCheck",
                BASE_PACKAGE + ".checks.naming.StaticVariableNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("TypeNameCheck",
                BASE_PACKAGE + ".checks.naming.TypeNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("PatternVariableNameCheck",
                BASE_PACKAGE + ".checks.naming.PatternVariableNameCheck");
        NAME_TO_FULL_MODULE_NAME.put("IllegalIdentifierNameCheck",
            BASE_PACKAGE + ".checks.naming.IllegalIdentifierNameCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from regexp package.
     */
    private static void fillChecksFromRegexpPackage() {
        NAME_TO_FULL_MODULE_NAME.put("RegexpCheck",
                BASE_PACKAGE + ".checks.regexp.RegexpCheck");
        NAME_TO_FULL_MODULE_NAME.put("RegexpMultilineCheck",
                BASE_PACKAGE + ".checks.regexp.RegexpMultilineCheck");
        NAME_TO_FULL_MODULE_NAME.put("RegexpOnFilenameCheck",
                BASE_PACKAGE + ".checks.regexp.RegexpOnFilenameCheck");
        NAME_TO_FULL_MODULE_NAME.put("RegexpSinglelineCheck",
                BASE_PACKAGE + ".checks.regexp.RegexpSinglelineCheck");
        NAME_TO_FULL_MODULE_NAME.put("RegexpSinglelineJavaCheck",
                BASE_PACKAGE + ".checks.regexp.RegexpSinglelineJavaCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from sizes package.
     */
    private static void fillChecksFromSizesPackage() {
        NAME_TO_FULL_MODULE_NAME.put("AnonInnerLengthCheck",
                BASE_PACKAGE + ".checks.sizes.AnonInnerLengthCheck");
        NAME_TO_FULL_MODULE_NAME.put("ExecutableStatementCountCheck",
                BASE_PACKAGE + ".checks.sizes.ExecutableStatementCountCheck");
        NAME_TO_FULL_MODULE_NAME.put("FileLengthCheck",
                BASE_PACKAGE + ".checks.sizes.FileLengthCheck");
        NAME_TO_FULL_MODULE_NAME.put("LambdaBodyLengthCheck",
                BASE_PACKAGE + ".checks.sizes.LambdaBodyLengthCheck");
        NAME_TO_FULL_MODULE_NAME.put("LineLengthCheck",
                BASE_PACKAGE + ".checks.sizes.LineLengthCheck");
        NAME_TO_FULL_MODULE_NAME.put("MethodCountCheck",
                BASE_PACKAGE + ".checks.sizes.MethodCountCheck");
        NAME_TO_FULL_MODULE_NAME.put("MethodLengthCheck",
                BASE_PACKAGE + ".checks.sizes.MethodLengthCheck");
        NAME_TO_FULL_MODULE_NAME.put("OuterTypeNumberCheck",
                BASE_PACKAGE + ".checks.sizes.OuterTypeNumberCheck");
        NAME_TO_FULL_MODULE_NAME.put("ParameterNumberCheck",
                BASE_PACKAGE + ".checks.sizes.ParameterNumberCheck");
        NAME_TO_FULL_MODULE_NAME.put("RecordComponentNumberCheck",
                BASE_PACKAGE + ".checks.sizes.RecordComponentNumberCheck");
    }

    /**
     * Fill short-to-full module names map with Checks from whitespace package.
     */
    private static void fillChecksFromWhitespacePackage() {
        NAME_TO_FULL_MODULE_NAME.put("EmptyForInitializerPadCheck",
                BASE_PACKAGE + ".checks.whitespace.EmptyForInitializerPadCheck");
        NAME_TO_FULL_MODULE_NAME.put("EmptyForIteratorPadCheck",
                BASE_PACKAGE + ".checks.whitespace.EmptyForIteratorPadCheck");
        NAME_TO_FULL_MODULE_NAME.put("EmptyLineSeparatorCheck",
                BASE_PACKAGE + ".checks.whitespace.EmptyLineSeparatorCheck");
        NAME_TO_FULL_MODULE_NAME.put("FileTabCharacterCheck",
                BASE_PACKAGE + ".checks.whitespace.FileTabCharacterCheck");
        NAME_TO_FULL_MODULE_NAME.put("GenericWhitespaceCheck",
                BASE_PACKAGE + ".checks.whitespace.GenericWhitespaceCheck");
        NAME_TO_FULL_MODULE_NAME.put("MethodParamPadCheck",
                BASE_PACKAGE + ".checks.whitespace.MethodParamPadCheck");
        NAME_TO_FULL_MODULE_NAME.put("NoLineWrapCheck",
                BASE_PACKAGE + ".checks.whitespace.NoLineWrapCheck");
        NAME_TO_FULL_MODULE_NAME.put("NoWhitespaceAfterCheck",
                BASE_PACKAGE + ".checks.whitespace.NoWhitespaceAfterCheck");
        NAME_TO_FULL_MODULE_NAME.put("NoWhitespaceBeforeCheck",
                BASE_PACKAGE + ".checks.whitespace.NoWhitespaceBeforeCheck");
        NAME_TO_FULL_MODULE_NAME.put("NoWhitespaceBeforeCaseDefaultColonCheck",
                BASE_PACKAGE + ".checks.whitespace.NoWhitespaceBeforeCaseDefaultColonCheck");
        NAME_TO_FULL_MODULE_NAME.put("OperatorWrapCheck",
                BASE_PACKAGE + ".checks.whitespace.OperatorWrapCheck");
        NAME_TO_FULL_MODULE_NAME.put("ParenPadCheck",
                BASE_PACKAGE + ".checks.whitespace.ParenPadCheck");
        NAME_TO_FULL_MODULE_NAME.put("SeparatorWrapCheck",
                BASE_PACKAGE + ".checks.whitespace.SeparatorWrapCheck");
        NAME_TO_FULL_MODULE_NAME.put("SingleSpaceSeparatorCheck",
                BASE_PACKAGE + ".checks.whitespace.SingleSpaceSeparatorCheck");
        NAME_TO_FULL_MODULE_NAME.put("TypecastParenPadCheck",
                BASE_PACKAGE + ".checks.whitespace.TypecastParenPadCheck");
        NAME_TO_FULL_MODULE_NAME.put("WhitespaceAfterCheck",
                BASE_PACKAGE + ".checks.whitespace.WhitespaceAfterCheck");
        NAME_TO_FULL_MODULE_NAME.put("WhitespaceAroundCheck",
                BASE_PACKAGE + ".checks.whitespace.WhitespaceAroundCheck");
    }

    /**
     * Fill short-to-full module names map with modules from checks package.
     */
    private static void fillModulesFromChecksPackage() {
        NAME_TO_FULL_MODULE_NAME.put("ArrayTypeStyleCheck",
                BASE_PACKAGE + ".checks.ArrayTypeStyleCheck");
        NAME_TO_FULL_MODULE_NAME.put("AvoidEscapedUnicodeCharactersCheck",
                BASE_PACKAGE + ".checks.AvoidEscapedUnicodeCharactersCheck");
        NAME_TO_FULL_MODULE_NAME.put("DescendantTokenCheck",
                BASE_PACKAGE + ".checks.DescendantTokenCheck");
        NAME_TO_FULL_MODULE_NAME.put("FinalParametersCheck",
                BASE_PACKAGE + ".checks.FinalParametersCheck");
        NAME_TO_FULL_MODULE_NAME.put("NewlineAtEndOfFileCheck",
                BASE_PACKAGE + ".checks.NewlineAtEndOfFileCheck");
        NAME_TO_FULL_MODULE_NAME.put("NoCodeInFileCheck",
                BASE_PACKAGE + ".checks.NoCodeInFileCheck");
        NAME_TO_FULL_MODULE_NAME.put("OuterTypeFilenameCheck",
                BASE_PACKAGE + ".checks.OuterTypeFilenameCheck");
        NAME_TO_FULL_MODULE_NAME.put("OrderedPropertiesCheck",
                BASE_PACKAGE + ".checks.OrderedPropertiesCheck");
        NAME_TO_FULL_MODULE_NAME.put("SuppressWarningsHolder",
                BASE_PACKAGE + ".checks.SuppressWarningsHolder");
        NAME_TO_FULL_MODULE_NAME.put("TodoCommentCheck",
                BASE_PACKAGE + ".checks.TodoCommentCheck");
        NAME_TO_FULL_MODULE_NAME.put("TrailingCommentCheck",
                BASE_PACKAGE + ".checks.TrailingCommentCheck");
        NAME_TO_FULL_MODULE_NAME.put("TranslationCheck",
                BASE_PACKAGE + ".checks.TranslationCheck");
        NAME_TO_FULL_MODULE_NAME.put("UncommentedMainCheck",
                BASE_PACKAGE + ".checks.UncommentedMainCheck");
        NAME_TO_FULL_MODULE_NAME.put("UniquePropertiesCheck",
                BASE_PACKAGE + ".checks.UniquePropertiesCheck");
        NAME_TO_FULL_MODULE_NAME.put("UpperEllCheck",
                BASE_PACKAGE + ".checks.UpperEllCheck");
    }

    /**
     * Fill short-to-full module names map with modules from filefilters package.
     */
    private static void fillModulesFromFilefiltersPackage() {
        NAME_TO_FULL_MODULE_NAME.put("BeforeExecutionExclusionFileFilter",
                BASE_PACKAGE + ".filefilters.BeforeExecutionExclusionFileFilter");
    }

    /**
     * Fill short-to-full module names map with modules from filters package.
     */
    private static void fillModulesFromFiltersPackage() {
        NAME_TO_FULL_MODULE_NAME.put("SeverityMatchFilter",
                BASE_PACKAGE + ".filters.SeverityMatchFilter");
        NAME_TO_FULL_MODULE_NAME.put("SuppressWithPlainTextCommentFilter",
            BASE_PACKAGE + ".filters.SuppressWithPlainTextCommentFilter");
        NAME_TO_FULL_MODULE_NAME.put("SuppressionCommentFilter",
                BASE_PACKAGE + ".filters.SuppressionCommentFilter");
        NAME_TO_FULL_MODULE_NAME.put("SuppressionFilter",
                BASE_PACKAGE + ".filters.SuppressionFilter");
        NAME_TO_FULL_MODULE_NAME.put("SuppressionSingleFilter",
                BASE_PACKAGE + ".filters.SuppressionSingleFilter");
        NAME_TO_FULL_MODULE_NAME.put("SuppressionXpathFilter",
                BASE_PACKAGE + ".filters.SuppressionXpathFilter");
        NAME_TO_FULL_MODULE_NAME.put("SuppressionXpathSingleFilter",
                BASE_PACKAGE + ".filters.SuppressionXpathSingleFilter");
        NAME_TO_FULL_MODULE_NAME.put("SuppressWarningsFilter",
                BASE_PACKAGE + ".filters.SuppressWarningsFilter");
        NAME_TO_FULL_MODULE_NAME.put("SuppressWithNearbyCommentFilter",
                BASE_PACKAGE + ".filters.SuppressWithNearbyCommentFilter");
        NAME_TO_FULL_MODULE_NAME.put("SuppressWithNearbyTextFilter",
                BASE_PACKAGE + ".filters.SuppressWithNearbyTextFilter");
    }

    /**
     * Fill short-to-full module names map with modules from checkstyle package.
     */
    private static void fillModulesFromCheckstylePackage() {
        NAME_TO_FULL_MODULE_NAME.put("Checker", BASE_PACKAGE + ".Checker");
        NAME_TO_FULL_MODULE_NAME.put("TreeWalker", BASE_PACKAGE + ".TreeWalker");
    }

}
