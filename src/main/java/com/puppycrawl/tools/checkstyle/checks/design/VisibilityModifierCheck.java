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

package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks visibility of class members. Only static final, immutable or annotated
 * by specified annotation members may be public;
 * other class members must be private unless the property {@code protectedAllowed}
 * or {@code packageAllowed} is set.
 * </p>
 * <p>
 * Public members are not flagged if the name matches the public
 * member regular expression (contains {@code "^serialVersionUID$"} by
 * default).
 * </p>
 * <p>
 * Note that Checkstyle 2 used to include {@code "^f[A-Z][a-zA-Z0-9]*$"} in the default pattern
 * to allow names used in container-managed persistence for Enterprise JavaBeans (EJB) 1.1 with
 * the default settings. With EJB 2.0 it is no longer necessary to have public access for
 * persistent fields, so the default has been changed.
 * </p>
 * <p>
 * Rationale: Enforce encapsulation.
 * </p>
 * <p>
 * Check also has options making it less strict:
 * </p>
 * <p>
 * <b>ignoreAnnotationCanonicalNames</b>- the list of annotations which ignore
 * variables in consideration. If user will provide short annotation name that
 * type will match to any named the same type without consideration of package.
 * </p>
 * <p>
 * <b>allowPublicFinalFields</b>- which allows public final fields.
 * </p>
 * <p>
 * <b>allowPublicImmutableFields</b>- which allows immutable fields to be
 * declared as public if defined in final class.
 * </p>
 * <p>
 * Field is known to be immutable if:
 * </p>
 * <ul>
 * <li>It's declared as final</li>
 * <li>Has either a primitive type or instance of class user defined to be immutable
 * (such as String, ImmutableCollection from Guava and etc)</li>
 * </ul>
 * <p>
 * Classes known to be immutable are listed in <b>immutableClassCanonicalNames</b>
 * by their canonical names.
 * </p>
 * <p>
 * Property Rationale: Forcing all fields of class to have private modifier by default is
 * good in most cases, but in some cases it drawbacks in too much boilerplate get/set code.
 * One of such cases are immutable classes.
 * </p>
 * <p>
 * Restriction: Check doesn't check if class is immutable, there's no checking
 * if accessory methods are missing and all fields are immutable, we only check
 * if current field is immutable or final.
 * Under the flag <b>allowPublicImmutableFields</b>, the enclosing class must
 * also be final, to encourage immutability.
 * Under the flag <b>allowPublicFinalFields</b>, the final modifier
 * on the enclosing class is optional.
 * </p>
 * <p>
 * Star imports are out of scope of this Check. So if one of type imported via
 * star import collides with user specified one by its short name - there
 * won't be Check's violation.
 * </p>
 * <ul>
 * <li>
 * Property {@code packageAllowed} - Control whether package visible members are allowed.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code protectedAllowed} - Control whether protected members are allowed.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code publicMemberPattern} - Specify pattern for public members that should be ignored.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^serialVersionUID$"}.
 * </li>
 * <li>
 * Property {@code allowPublicFinalFields} - Allow final fields to be declared as public.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowPublicImmutableFields} - Allow immutable fields to be
 * declared as public if defined in final class.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code immutableClassCanonicalNames} - Specify immutable classes canonical names.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code java.io.File, java.lang.Boolean, java.lang.Byte,
 * java.lang.Character, java.lang.Double, java.lang.Float, java.lang.Integer,
 * java.lang.Long, java.lang.Short, java.lang.StackTraceElement, java.lang.String,
 * java.math.BigDecimal, java.math.BigInteger, java.net.Inet4Address, java.net.Inet6Address,
 * java.net.InetSocketAddress, java.net.URI, java.net.URL, java.util.Locale, java.util.UUID}.
 * </li>
 * <li>
 * Property {@code ignoreAnnotationCanonicalNames} - Specify the list of annotations canonical
 * names which ignore variables in consideration.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code com.google.common.annotations.VisibleForTesting,
 * org.junit.ClassRule, org.junit.Rule}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;/&gt;
 * </pre>
 * <p>
 * To configure the check so that it allows package visible members:
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;packageAllowed&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check so that it allows no public members:
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;publicMemberPattern&quot; value=&quot;^$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the Check so that it allows public immutable fields (mostly for immutable classes):
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;allowPublicImmutableFields&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of allowed public immutable fields:
 * </p>
 * <pre>
 * public class ImmutableClass
 * {
 *   public final ImmutableSet&lt;String&gt; includes; // No warning
 *   public final ImmutableSet&lt;String&gt; excludes; // No warning
 *   public final java.lang.String notes; // No warning
 *   public final BigDecimal value; // No warning
 *
 *   public ImmutableClass(Collection&lt;String&gt; includes, Collection&lt;String&gt; excludes,
 *                BigDecimal value, String notes)
 *   {
 *     this.includes = ImmutableSet.copyOf(includes);
 *     this.excludes = ImmutableSet.copyOf(excludes);
 *     this.value = value;
 *     this.notes = notes;
 *   }
 * }
 * </pre>
 * <p>
 * To configure the Check in order to allow user specified immutable class names:
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;allowPublicImmutableFields&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;immutableClassCanonicalNames&quot; value=&quot;
 *   com.google.common.collect.ImmutableSet&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of allowed public immutable fields:
 * </p>
 * <pre>
 * public class ImmutableClass
 * {
 *   public final ImmutableSet&lt;String&gt; includes; // No warning
 *   public final ImmutableSet&lt;String&gt; excludes; // No warning
 *   public final java.lang.String notes; // Warning here because
 *                                        //'java.lang.String' wasn't specified as allowed class
 *   public final int someValue; // No warning
 *
 *   public ImmutableClass(Collection&lt;String&gt; includes, Collection&lt;String&gt; excludes,
 *                String notes, int someValue)
 *   {
 *     this.includes = ImmutableSet.copyOf(includes);
 *     this.excludes = ImmutableSet.copyOf(excludes);
 *     this.value = value;
 *     this.notes = notes;
 *     this.someValue = someValue;
 *   }
 * }
 * </pre>
 * <p>
 * Note, if allowPublicImmutableFields is set to true, the check will also check
 * whether generic type parameters are immutable. If at least one generic type
 * parameter is mutable, there will be a violation.
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;allowPublicImmutableFields&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;immutableClassCanonicalNames&quot;
 *     value=&quot;com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableMap,
 *       java.lang.String&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of how the check works:
 * </p>
 * <pre>
 * public final class Test {
 *   public final String s;
 *   public final ImmutableSet&lt;String&gt; names;
 *   public final ImmutableSet&lt;Object&gt; objects; // violation (Object class is mutable)
 *   public final ImmutableMap&lt;String, Object&gt; links; // violation (Object class is mutable)
 *
 *   public Test() {
 *     s = "Hello!";
 *     names = ImmutableSet.of();
 *     objects = ImmutableSet.of();
 *     links = ImmutableMap.of();
 *   }
 * }
 * </pre>
 * <p>
 * To configure the Check passing fields annotated with @com.annotation.CustomAnnotation:
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;ignoreAnnotationCanonicalNames&quot; value=
 *   &quot;com.annotation.CustomAnnotation&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of allowed field:
 * </p>
 * <pre>
 * class SomeClass
 * {
 *   &#64;com.annotation.CustomAnnotation
 *   String annotatedString; // no warning
 *   &#64;CustomAnnotation
 *   String shortCustomAnnotated; // no warning
 * }
 * </pre>
 * <p>
 * To configure the Check passing fields annotated with &#64;org.junit.Rule,
 * &#64;org.junit.ClassRule and &#64;com.google.common.annotations.VisibleForTesting annotations:
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;/&gt;
 * </pre>
 * <p>
 * Example of allowed fields:
 * </p>
 * <pre>
 * class SomeClass
 * {
 *   &#64;org.junit.Rule
 *   public TemporaryFolder publicJUnitRule = new TemporaryFolder(); // no warning
 *   &#64;org.junit.ClassRule
 *   public static TemporaryFolder publicJUnitClassRule = new TemporaryFolder(); // no warning
 *   &#64;com.google.common.annotations.VisibleForTesting
 *   public String testString = ""; // no warning
 * }
 * </pre>
 * <p>
 * To configure the Check passing fields annotated with short annotation name:
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;ignoreAnnotationCanonicalNames&quot;
 *   value=&quot;CustomAnnotation&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of allowed fields:
 * </p>
 * <pre>
 * class SomeClass
 * {
 *   &#64;CustomAnnotation
 *   String customAnnotated; // no warning
 *   &#64;com.annotation.CustomAnnotation
 *   String customAnnotated1; // no warning
 *   &#64;mypackage.annotation.CustomAnnotation
 *   String customAnnotatedAnotherPackage; // another package but short name matches
 *                                         // so no violation
 * }
 * </pre>
 * <p>
 * To understand the difference between allowPublicImmutableFields and allowPublicFinalFields
 * options, please, study the following examples.
 * </p>
 * <p>
 * 1) To configure the check to use only 'allowPublicImmutableFields' option:
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;allowPublicImmutableFields&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code example:
 * </p>
 * <pre>
 * public class InputPublicImmutable {
 *   public final int someIntValue; // violation
 *   public final ImmutableSet&lt;String&gt; includes; // violation
 *   public final java.lang.String notes; // violation
 *   public final BigDecimal value; // violation
 *   public final List list; // violation
 *
 *   public InputPublicImmutable(Collection&lt;String&gt; includes,
 *         BigDecimal value, String notes, int someValue, List l) {
 *     this.includes = ImmutableSet.copyOf(includes);
 *     this.value = value;
 *     this.notes = notes;
 *     this.someIntValue = someValue;
 *     this.list = l;
 *   }
 * }
 * </pre>
 * <p>
 * 2) To configure the check to use only 'allowPublicFinalFields' option:
 * </p>
 * <pre>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;allowPublicFinalFields&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code example:
 * </p>
 * <pre>
 * public class InputPublicImmutable {
 *   public final int someIntValue;
 *   public final ImmutableSet&lt;String&gt; includes;
 *   public final java.lang.String notes;
 *   public final BigDecimal value;
 *   public final List list;
 *
 *   public InputPublicImmutable(Collection&lt;String&gt; includes,
 *         BigDecimal value, String notes, int someValue, List l) {
 *     this.includes = ImmutableSet.copyOf(includes);
 *     this.value = value;
 *     this.notes = notes;
 *     this.someIntValue = someValue;
 *     this.list = l;
 *   }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code variable.notPrivate}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@FileStatefulCheck
public class VisibilityModifierCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "variable.notPrivate";

    /** Default immutable types canonical names. */
    private static final List<String> DEFAULT_IMMUTABLE_TYPES = Collections.unmodifiableList(
        Arrays.stream(new String[] {
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Byte",
            "java.lang.Character",
            "java.lang.Short",
            "java.lang.Boolean",
            "java.lang.Long",
            "java.lang.Double",
            "java.lang.Float",
            "java.lang.StackTraceElement",
            "java.math.BigInteger",
            "java.math.BigDecimal",
            "java.io.File",
            "java.util.Locale",
            "java.util.UUID",
            "java.net.URL",
            "java.net.URI",
            "java.net.Inet4Address",
            "java.net.Inet6Address",
            "java.net.InetSocketAddress",
        }).collect(Collectors.toList()));

    /** Default ignore annotations canonical names. */
    private static final List<String> DEFAULT_IGNORE_ANNOTATIONS = Collections.unmodifiableList(
        Arrays.stream(new String[] {
            "org.junit.Rule",
            "org.junit.ClassRule",
            "com.google.common.annotations.VisibleForTesting",
        }).collect(Collectors.toList()));

    /** Name for 'public' access modifier. */
    private static final String PUBLIC_ACCESS_MODIFIER = "public";

    /** Name for 'private' access modifier. */
    private static final String PRIVATE_ACCESS_MODIFIER = "private";

    /** Name for 'protected' access modifier. */
    private static final String PROTECTED_ACCESS_MODIFIER = "protected";

    /** Name for implicit 'package' access modifier. */
    private static final String PACKAGE_ACCESS_MODIFIER = "package";

    /** Name for 'static' keyword. */
    private static final String STATIC_KEYWORD = "static";

    /** Name for 'final' keyword. */
    private static final String FINAL_KEYWORD = "final";

    /** Contains explicit access modifiers. */
    private static final String[] EXPLICIT_MODS = {
        PUBLIC_ACCESS_MODIFIER,
        PRIVATE_ACCESS_MODIFIER,
        PROTECTED_ACCESS_MODIFIER,
    };

    /**
     * Specify pattern for public members that should be ignored.
     */
    private Pattern publicMemberPattern = Pattern.compile("^serialVersionUID$");

    /** List of ignore annotations short names. */
    private final List<String> ignoreAnnotationShortNames =
            getClassShortNames(DEFAULT_IGNORE_ANNOTATIONS);

    /** List of immutable classes short names. */
    private final List<String> immutableClassShortNames =
        getClassShortNames(DEFAULT_IMMUTABLE_TYPES);

    /**
     * Specify the list of annotations canonical names which ignore variables in
     * consideration.
     */
    private List<String> ignoreAnnotationCanonicalNames =
        new ArrayList<>(DEFAULT_IGNORE_ANNOTATIONS);

    /** Control whether protected members are allowed. */
    private boolean protectedAllowed;

    /** Control whether package visible members are allowed. */
    private boolean packageAllowed;

    /** Allow immutable fields to be declared as public if defined in final class. */
    private boolean allowPublicImmutableFields;

    /** Allow final fields to be declared as public. */
    private boolean allowPublicFinalFields;

    /** Specify immutable classes canonical names. */
    private List<String> immutableClassCanonicalNames = new ArrayList<>(DEFAULT_IMMUTABLE_TYPES);

    /**
     * Setter to specify the list of annotations canonical names which ignore variables
     * in consideration.
     *
     * @param annotationNames array of ignore annotations canonical names.
     */
    public void setIgnoreAnnotationCanonicalNames(String... annotationNames) {
        ignoreAnnotationCanonicalNames = Arrays.asList(annotationNames);
    }

    /**
     * Setter to control whether protected members are allowed.
     *
     * @param protectedAllowed whether protected members are allowed
     */
    public void setProtectedAllowed(boolean protectedAllowed) {
        this.protectedAllowed = protectedAllowed;
    }

    /**
     * Setter to control whether package visible members are allowed.
     *
     * @param packageAllowed whether package visible members are allowed
     */
    public void setPackageAllowed(boolean packageAllowed) {
        this.packageAllowed = packageAllowed;
    }

    /**
     * Setter to specify pattern for public members that should be ignored.
     *
     * @param pattern
     *        pattern for public members to ignore.
     */
    public void setPublicMemberPattern(Pattern pattern) {
        publicMemberPattern = pattern;
    }

    /**
     * Setter to allow immutable fields to be declared as public if defined in final class.
     *
     * @param allow user's value.
     */
    public void setAllowPublicImmutableFields(boolean allow) {
        allowPublicImmutableFields = allow;
    }

    /**
     * Setter to allow final fields to be declared as public.
     *
     * @param allow user's value.
     */
    public void setAllowPublicFinalFields(boolean allow) {
        allowPublicFinalFields = allow;
    }

    /**
     * Setter to specify immutable classes canonical names.
     *
     * @param classNames array of immutable types canonical names.
     */
    public void setImmutableClassCanonicalNames(String... classNames) {
        immutableClassCanonicalNames = Arrays.asList(classNames);
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IMPORT,
        };
    }

    @Override
    public void beginTree(DetailAST rootAst) {
        immutableClassShortNames.clear();
        final List<String> classShortNames =
                getClassShortNames(immutableClassCanonicalNames);
        immutableClassShortNames.addAll(classShortNames);

        ignoreAnnotationShortNames.clear();
        final List<String> annotationShortNames =
                getClassShortNames(ignoreAnnotationCanonicalNames);
        ignoreAnnotationShortNames.addAll(annotationShortNames);
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.VARIABLE_DEF:
                if (!isAnonymousClassVariable(ast)) {
                    visitVariableDef(ast);
                }
                break;
            case TokenTypes.IMPORT:
                visitImport(ast);
                break;
            default:
                final String exceptionMsg = "Unexpected token type: " + ast.getText();
                throw new IllegalArgumentException(exceptionMsg);
        }
    }

    /**
     * Checks if current variable definition is definition of an anonymous class.
     *
     * @param variableDef {@link TokenTypes#VARIABLE_DEF VARIABLE_DEF}
     * @return true if current variable definition is definition of an anonymous class.
     */
    private static boolean isAnonymousClassVariable(DetailAST variableDef) {
        return variableDef.getParent().getType() != TokenTypes.OBJBLOCK;
    }

    /**
     * Checks access modifier of given variable.
     * If it is not proper according to Check - puts violation on it.
     *
     * @param variableDef variable to check.
     */
    private void visitVariableDef(DetailAST variableDef) {
        final boolean inInterfaceOrAnnotationBlock =
                ScopeUtil.isInInterfaceOrAnnotationBlock(variableDef);

        if (!inInterfaceOrAnnotationBlock && !hasIgnoreAnnotation(variableDef)) {
            final DetailAST varNameAST = variableDef.findFirstToken(TokenTypes.TYPE)
                .getNextSibling();
            final String varName = varNameAST.getText();
            if (!hasProperAccessModifier(variableDef, varName)) {
                log(varNameAST, MSG_KEY, varName);
            }
        }
    }

    /**
     * Checks if variable def has ignore annotation.
     *
     * @param variableDef {@link TokenTypes#VARIABLE_DEF VARIABLE_DEF}
     * @return true if variable def has ignore annotation.
     */
    private boolean hasIgnoreAnnotation(DetailAST variableDef) {
        final DetailAST firstIgnoreAnnotation =
                 findMatchingAnnotation(variableDef);
        return firstIgnoreAnnotation != null;
    }

    /**
     * Checks imported type. If type's canonical name was not specified in
     * <b>immutableClassCanonicalNames</b>, but it's short name collides with one from
     * <b>immutableClassShortNames</b> - removes it from the last one.
     *
     * @param importAst {@link TokenTypes#IMPORT Import}
     */
    private void visitImport(DetailAST importAst) {
        if (!isStarImport(importAst)) {
            final DetailAST type = importAst.getFirstChild();
            final String canonicalName = getCanonicalName(type);
            final String shortName = getClassShortName(canonicalName);

            // If imported canonical class name is not specified as allowed immutable class,
            // but its short name collides with one of specified class - removes the short name
            // from list to avoid names collision
            if (!immutableClassCanonicalNames.contains(canonicalName)) {
                immutableClassShortNames.remove(shortName);
            }
            if (!ignoreAnnotationCanonicalNames.contains(canonicalName)) {
                ignoreAnnotationShortNames.remove(shortName);
            }
        }
    }

    /**
     * Checks if current import is star import. E.g.:
     * <p>
     * {@code
     * import java.util.*;
     * }
     * </p>
     *
     * @param importAst {@link TokenTypes#IMPORT Import}
     * @return true if it is star import
     */
    private static boolean isStarImport(DetailAST importAst) {
        boolean result = false;
        DetailAST toVisit = importAst;
        while (toVisit != null) {
            toVisit = getNextSubTreeNode(toVisit, importAst);
            if (toVisit != null && toVisit.getType() == TokenTypes.STAR) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if current variable has proper access modifier according to Check's options.
     *
     * @param variableDef Variable definition node.
     * @param variableName Variable's name.
     * @return true if variable has proper access modifier.
     */
    private boolean hasProperAccessModifier(DetailAST variableDef, String variableName) {
        boolean result = true;

        final String variableScope = getVisibilityScope(variableDef);

        if (!PRIVATE_ACCESS_MODIFIER.equals(variableScope)) {
            result =
                isStaticFinalVariable(variableDef)
                || packageAllowed && PACKAGE_ACCESS_MODIFIER.equals(variableScope)
                || protectedAllowed && PROTECTED_ACCESS_MODIFIER.equals(variableScope)
                || isIgnoredPublicMember(variableName, variableScope)
                || isAllowedPublicField(variableDef);
        }

        return result;
    }

    /**
     * Checks whether variable has static final modifiers.
     *
     * @param variableDef Variable definition node.
     * @return true of variable has static final modifiers.
     */
    private static boolean isStaticFinalVariable(DetailAST variableDef) {
        final Set<String> modifiers = getModifiers(variableDef);
        return modifiers.contains(STATIC_KEYWORD)
                && modifiers.contains(FINAL_KEYWORD);
    }

    /**
     * Checks whether variable belongs to public members that should be ignored.
     *
     * @param variableName Variable's name.
     * @param variableScope Variable's scope.
     * @return true if variable belongs to public members that should be ignored.
     */
    private boolean isIgnoredPublicMember(String variableName, String variableScope) {
        return PUBLIC_ACCESS_MODIFIER.equals(variableScope)
            && publicMemberPattern.matcher(variableName).find();
    }

    /**
     * Checks whether the variable satisfies the public field check.
     *
     * @param variableDef Variable definition node.
     * @return true if allowed.
     */
    private boolean isAllowedPublicField(DetailAST variableDef) {
        return allowPublicFinalFields && isFinalField(variableDef)
            || allowPublicImmutableFields && isImmutableFieldDefinedInFinalClass(variableDef);
    }

    /**
     * Checks whether immutable field is defined in final class.
     *
     * @param variableDef Variable definition node.
     * @return true if immutable field is defined in final class.
     */
    private boolean isImmutableFieldDefinedInFinalClass(DetailAST variableDef) {
        final DetailAST classDef = variableDef.getParent().getParent();
        final Set<String> classModifiers = getModifiers(classDef);
        return (classModifiers.contains(FINAL_KEYWORD) || classDef.getType() == TokenTypes.ENUM_DEF)
                && isImmutableField(variableDef);
    }

    /**
     * Returns the set of modifier Strings for a VARIABLE_DEF or CLASS_DEF AST.
     *
     * @param defAST AST for a variable or class definition.
     * @return the set of modifier Strings for defAST.
     */
    private static Set<String> getModifiers(DetailAST defAST) {
        final DetailAST modifiersAST = defAST.findFirstToken(TokenTypes.MODIFIERS);
        final Set<String> modifiersSet = new HashSet<>();
        if (modifiersAST != null) {
            DetailAST modifier = modifiersAST.getFirstChild();
            while (modifier != null) {
                modifiersSet.add(modifier.getText());
                modifier = modifier.getNextSibling();
            }
        }
        return modifiersSet;
    }

    /**
     * Returns the visibility scope for the variable.
     *
     * @param variableDef Variable definition node.
     * @return one of "public", "private", "protected", "package"
     */
    private static String getVisibilityScope(DetailAST variableDef) {
        final Set<String> modifiers = getModifiers(variableDef);
        String accessModifier = PACKAGE_ACCESS_MODIFIER;
        for (final String modifier : EXPLICIT_MODS) {
            if (modifiers.contains(modifier)) {
                accessModifier = modifier;
                break;
            }
        }
        return accessModifier;
    }

    /**
     * Checks if current field is immutable:
     * has final modifier and either a primitive type or instance of class
     * known to be immutable (such as String, ImmutableCollection from Guava and etc).
     * Classes known to be immutable are listed in
     * {@link VisibilityModifierCheck#immutableClassCanonicalNames}
     *
     * @param variableDef Field in consideration.
     * @return true if field is immutable.
     */
    private boolean isImmutableField(DetailAST variableDef) {
        boolean result = false;
        if (isFinalField(variableDef)) {
            final DetailAST type = variableDef.findFirstToken(TokenTypes.TYPE);
            final boolean isCanonicalName = isCanonicalName(type);
            final String typeName = getTypeName(type, isCanonicalName);
            if (immutableClassShortNames.contains(typeName)
                    || isCanonicalName && immutableClassCanonicalNames.contains(typeName)) {
                final DetailAST typeArgs = getGenericTypeArgs(type, isCanonicalName);

                if (typeArgs == null) {
                    result = true;
                }
                else {
                    final List<String> argsClassNames = getTypeArgsClassNames(typeArgs);
                    result = areImmutableTypeArguments(argsClassNames);
                }
            }
            else {
                result = !isCanonicalName && isPrimitive(type);
            }
        }
        return result;
    }

    /**
     * Checks whether type definition is in canonical form.
     *
     * @param type type definition token.
     * @return true if type definition is in canonical form.
     */
    private static boolean isCanonicalName(DetailAST type) {
        return type.getFirstChild().getType() == TokenTypes.DOT;
    }

    /**
     * Returns generic type arguments token.
     *
     * @param type type token.
     * @param isCanonicalName whether type name is in canonical form.
     * @return generic type arguments token.
     */
    private static DetailAST getGenericTypeArgs(DetailAST type, boolean isCanonicalName) {
        final DetailAST typeArgs;
        if (isCanonicalName) {
            // if type class name is in canonical form, abstract tree has specific structure
            typeArgs = type.getFirstChild().findFirstToken(TokenTypes.TYPE_ARGUMENTS);
        }
        else {
            typeArgs = type.findFirstToken(TokenTypes.TYPE_ARGUMENTS);
        }
        return typeArgs;
    }

    /**
     * Returns a list of type parameters class names.
     *
     * @param typeArgs type arguments token.
     * @return a list of type parameters class names.
     */
    private static List<String> getTypeArgsClassNames(DetailAST typeArgs) {
        final List<String> typeClassNames = new ArrayList<>();
        DetailAST type = typeArgs.findFirstToken(TokenTypes.TYPE_ARGUMENT);
        boolean isCanonicalName = isCanonicalName(type);
        String typeName = getTypeName(type, isCanonicalName);
        typeClassNames.add(typeName);
        DetailAST sibling = type.getNextSibling();
        while (sibling.getType() == TokenTypes.COMMA) {
            type = sibling.getNextSibling();
            isCanonicalName = isCanonicalName(type);
            typeName = getTypeName(type, isCanonicalName);
            typeClassNames.add(typeName);
            sibling = type.getNextSibling();
        }
        return typeClassNames;
    }

    /**
     * Checks whether all of generic type arguments are immutable.
     * If at least one argument is mutable, we assume that the whole list of type arguments
     * is mutable.
     *
     * @param typeArgsClassNames type arguments class names.
     * @return true if all of generic type arguments are immutable.
     */
    private boolean areImmutableTypeArguments(List<String> typeArgsClassNames) {
        return typeArgsClassNames.stream().noneMatch(
            typeName -> {
                return !immutableClassShortNames.contains(typeName)
                    && !immutableClassCanonicalNames.contains(typeName);
            });
    }

    /**
     * Checks whether current field is final.
     *
     * @param variableDef field in consideration.
     * @return true if current field is final.
     */
    private static boolean isFinalField(DetailAST variableDef) {
        final DetailAST modifiers = variableDef.findFirstToken(TokenTypes.MODIFIERS);
        return modifiers.findFirstToken(TokenTypes.FINAL) != null;
    }

    /**
     * Gets the name of type from given ast {@link TokenTypes#TYPE TYPE} node.
     * If type is specified via its canonical name - canonical name will be returned,
     * else - short type's name.
     *
     * @param type {@link TokenTypes#TYPE TYPE} node.
     * @param isCanonicalName is given name canonical.
     * @return String representation of given type's name.
     */
    private static String getTypeName(DetailAST type, boolean isCanonicalName) {
        final String typeName;
        if (isCanonicalName) {
            typeName = getCanonicalName(type);
        }
        else {
            typeName = type.getFirstChild().getText();
        }
        return typeName;
    }

    /**
     * Checks if current type is primitive type (int, short, float, boolean, double, etc.).
     * As primitive types have special tokens for each one, such as:
     * LITERAL_INT, LITERAL_BOOLEAN, etc.
     * So, if type's identifier differs from {@link TokenTypes#IDENT IDENT} token - it's a
     * primitive type.
     *
     * @param type Ast {@link TokenTypes#TYPE TYPE} node.
     * @return true if current type is primitive type.
     */
    private static boolean isPrimitive(DetailAST type) {
        return type.getFirstChild().getType() != TokenTypes.IDENT;
    }

    /**
     * Gets canonical type's name from given {@link TokenTypes#TYPE TYPE} node.
     *
     * @param type DetailAST {@link TokenTypes#TYPE TYPE} node.
     * @return canonical type's name
     */
    private static String getCanonicalName(DetailAST type) {
        final StringBuilder canonicalNameBuilder = new StringBuilder(256);
        DetailAST toVisit = type.getFirstChild();
        while (toVisit != null) {
            toVisit = getNextSubTreeNode(toVisit, type);
            if (toVisit != null && toVisit.getType() == TokenTypes.IDENT) {
                if (canonicalNameBuilder.length() > 0) {
                    canonicalNameBuilder.append('.');
                }
                canonicalNameBuilder.append(toVisit.getText());
                final DetailAST nextSubTreeNode = getNextSubTreeNode(toVisit, type);
                if (nextSubTreeNode != null
                        && nextSubTreeNode.getType() == TokenTypes.TYPE_ARGUMENTS) {
                    break;
                }
            }
        }
        return canonicalNameBuilder.toString();
    }

    /**
     * Gets the next node of a syntactical tree (child of a current node or
     * sibling of a current node, or sibling of a parent of a current node).
     *
     * @param currentNodeAst Current node in considering
     * @param subTreeRootAst SubTree root
     * @return Current node after bypassing, if current node reached the root of a subtree
     *        method returns null
     */
    private static DetailAST
        getNextSubTreeNode(DetailAST currentNodeAst, DetailAST subTreeRootAst) {
        DetailAST currentNode = currentNodeAst;
        DetailAST toVisitAst = currentNode.getFirstChild();
        while (toVisitAst == null) {
            toVisitAst = currentNode.getNextSibling();
            if (currentNode.getParent().getColumnNo() == subTreeRootAst.getColumnNo()) {
                break;
            }
            currentNode = currentNode.getParent();
        }
        return toVisitAst;
    }

    /**
     * Gets the list with short names classes.
     * These names are taken from array of classes canonical names.
     *
     * @param canonicalClassNames canonical class names.
     * @return the list of short names of classes.
     */
    private static List<String> getClassShortNames(List<String> canonicalClassNames) {
        final List<String> shortNames = new ArrayList<>();
        for (String canonicalClassName : canonicalClassNames) {
            final String shortClassName = canonicalClassName
                    .substring(canonicalClassName.lastIndexOf('.') + 1);
            shortNames.add(shortClassName);
        }
        return shortNames;
    }

    /**
     * Gets the short class name from given canonical name.
     *
     * @param canonicalClassName canonical class name.
     * @return short name of class.
     */
    private static String getClassShortName(String canonicalClassName) {
        return canonicalClassName
                .substring(canonicalClassName.lastIndexOf('.') + 1);
    }

    /**
     * Checks whether the AST is annotated with
     * an annotation containing the passed in regular
     * expression and return the AST representing that
     * annotation.
     *
     * <p>
     * This method will not look for imports or package
     * statements to detect the passed in annotation.
     * </p>
     *
     * <p>
     * To check if an AST contains a passed in annotation
     * taking into account fully-qualified names
     * (ex: java.lang.Override, Override)
     * this method will need to be called twice. Once for each
     * name given.
     * </p>
     *
     * @param variableDef {@link TokenTypes#VARIABLE_DEF variable def node}.
     * @return the AST representing the first such annotation or null if
     *         no such annotation was found
     */
    private DetailAST findMatchingAnnotation(DetailAST variableDef) {
        DetailAST matchingAnnotation = null;

        final DetailAST holder = AnnotationUtil.getAnnotationHolder(variableDef);

        for (DetailAST child = holder.getFirstChild();
            child != null; child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.ANNOTATION) {
                final DetailAST ast = child.getFirstChild();
                final String name =
                    FullIdent.createFullIdent(ast.getNextSibling()).getText();
                if (ignoreAnnotationCanonicalNames.contains(name)
                         || ignoreAnnotationShortNames.contains(name)) {
                    matchingAnnotation = child;
                    break;
                }
            }
        }

        return matchingAnnotation;
    }

}
