////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtility;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * Checks visibility of class members. Only static final, immutable or annotated
 * by specified annotation members may be public,
 * other class members must be private unless allowProtected/Package is set.
 * <p>
 * Public members are not flagged if the name matches the public
 * member regular expression (contains "^serialVersionUID$" by
 * default).
 * </p>
 * Rationale: Enforce encapsulation.
 * <p>
 * Check also has options making it less strict:
 * </p>
 * <p>
 * <b>ignoreAnnotationCanonicalNames</b> - the list of annotations canonical names
 * which ignore variables in consideration, if user will provide short annotation name
 * that type will match to any named the same type without consideration of package,
 * list by default:
 * </p>
 * <ul>
 * <li>org.junit.Rule</li>
 * <li>org.junit.ClassRule</li>
 * <li>com.google.common.annotations.VisibleForTesting</li>
 * </ul>
 * <p>
 * For example such public field will be skipped by default value of list above:
 * </p>
 *
 * <pre>
 * {@code @org.junit.Rule
 * public TemporaryFolder publicJUnitRule = new TemporaryFolder();
 * }
 * </pre>
 *
 * <p>
 * <b>allowPublicFinalFields</b> - which allows public final fields. Default value is <b>false</b>.
 * </p>
 * <p>
 * <b>allowPublicImmutableFields</b> - which allows immutable fields to be
 * declared as public if defined in final class. Default value is <b>false</b>
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
 * Classes known to be immutable are listed in <b>immutableClassCanonicalNames</b> by their
 * <b>canonical</b> names. List by default:
 * </p>
 * <ul>
 * <li>java.lang.String</li>
 * <li>java.lang.Integer</li>
 * <li>java.lang.Byte</li>
 * <li>java.lang.Character</li>
 * <li>java.lang.Short</li>
 * <li>java.lang.Boolean</li>
 * <li>java.lang.Long</li>
 * <li>java.lang.Double</li>
 * <li>java.lang.Float</li>
 * <li>java.lang.StackTraceElement</li>
 * <li>java.lang.BigInteger</li>
 * <li>java.lang.BigDecimal</li>
 * <li>java.io.File</li>
 * <li>java.util.Locale</li>
 * <li>java.util.UUID</li>
 * <li>java.net.URL</li>
 * <li>java.net.URI</li>
 * <li>java.net.Inet4Address</li>
 * <li>java.net.Inet6Address</li>
 * <li>java.net.InetSocketAddress</li>
 * </ul>
 * <p>
 * User can override this list via adding <b>canonical</b> class names to
 * <b>immutableClassCanonicalNames</b>, if user will provide short class name all
 * that type will match to any named the same type without consideration of package.
 * </p>
 * <p>
 * <b>Rationale</b>: Forcing all fields of class to have private modified by default is good
 * in most cases, but in some cases it drawbacks in too much boilerplate get/set code.
 * One of such cases are immutable classes.
 * </p>
 * <p>
 * <b>Restriction</b>: Check doesn't check if class is immutable, there's no checking
 * if accessory methods are missing and all fields are immutable, we only check
 * <b>if current field is immutable by matching a name to user defined list of immutable classes
 * and defined in final class</b>
 * </p>
 * <p>
 * Star imports are out of scope of this Check. So if one of type imported via <b>star import</b>
 * collides with user specified one by its short name - there won't be Check's violation.
 * </p>
 * Examples:
 * <p>
 * The check will rise 3 violations if it is run with default configuration against the following
 * code example:
 * </p>
 *
 * <pre>
 * {@code
 * public class ImmutableClass
 * {
 *     public int intValue; // violation
 *     public java.lang.String notes; // violation
 *     public BigDecimal value; // violation
 *
 *     public ImmutableClass(int intValue, BigDecimal value, String notes)
 *     {
 *         this.intValue = intValue;
 *         this.value = value;
 *         this.notes = notes;
 *     }
 * }
 * }
 * </pre>
 *
 * <p>
 * To configure the Check passing fields of type com.google.common.collect.ImmutableSet and
 * java.util.List:
 * </p>
 * <p>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;allowPublicImmutableFields&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;immutableClassCanonicalNames&quot; value=&quot;java.util.List,
 *   com.google.common.collect.ImmutableSet&quot;/&gt;
 * &lt;/module&gt;
 * </p>
 *
 * <pre>
 * {@code
 * public final class ImmutableClass
 * {
 *     public final ImmutableSet&lt;String&gt; includes; // No warning
 *     public final ImmutableSet&lt;String&gt; excludes; // No warning
 *     public final BigDecimal value; // Warning here, type BigDecimal isn't specified as immutable
 *
 *     public ImmutableClass(Collection&lt;String&gt; includes, Collection&lt;String&gt; excludes,
 *                  BigDecimal value)
 *     {
 *         this.includes = ImmutableSet.copyOf(includes);
 *         this.excludes = ImmutableSet.copyOf(excludes);
 *         this.value = value;
 *         this.notes = notes;
 *     }
 * }
 * }
 * </pre>
 *
 * <p>
 * To configure the Check passing fields annotated with
 * </p>
 * <pre>@com.annotation.CustomAnnotation</pre>:

 * <p>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;ignoreAnnotationCanonicalNames&quot; value=&quot;
 *   com.annotation.CustomAnnotation&quot;/&gt;
 * &lt;/module&gt;
 * </p>
 *
 * <pre>
 * {@code @com.annotation.CustomAnnotation
 * String customAnnotated; // No warning
 * }
 * {@code @CustomAnnotation
 * String shortCustomAnnotated; // No warning
 * }
 * </pre>
 *
 * <p>
 * To configure the Check passing fields annotated with short annotation name
 * </p>
 * <pre>@CustomAnnotation</pre>:
 *
 * <p>
 * &lt;module name=&quot;VisibilityModifier&quot;&gt;
 *   &lt;property name=&quot;ignoreAnnotationCanonicalNames&quot;
 *   value=&quot;CustomAnnotation&quot;/&gt;
 * &lt;/module&gt;
 * </p>
 *
 * <pre>
 * {@code @CustomAnnotation
 * String customAnnotated; // No warning
 * }
 * {@code @com.annotation.CustomAnnotation
 * String customAnnotated1; // No warning
 * }
 * {@code @mypackage.annotation.CustomAnnotation
 * String customAnnotatedAnotherPackage; // another package but short name matches
 *                                       // so no violation
 * }
 * </pre>
 *
 *
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
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

    /** Regexp for public members that should be ignored. Note:
     * Earlier versions of checkstyle used ^f[A-Z][a-zA-Z0-9]*$ as the
     * default to allow CMP for EJB 1.1 with the default settings.
     * With EJB 2.0 it is not longer necessary to have public access
     * for persistent fields.
     */
    private Pattern publicMemberPattern = Pattern.compile("^serialVersionUID$");

    /** List of ignore annotations short names. */
    private final List<String> ignoreAnnotationShortNames =
            getClassShortNames(DEFAULT_IGNORE_ANNOTATIONS);

    /** List of immutable classes short names. */
    private final List<String> immutableClassShortNames =
        getClassShortNames(DEFAULT_IMMUTABLE_TYPES);

    /** List of ignore annotations canonical names. */
    private List<String> ignoreAnnotationCanonicalNames =
        new ArrayList<>(DEFAULT_IGNORE_ANNOTATIONS);

    /** Whether protected members are allowed. */
    private boolean protectedAllowed;

    /** Whether package visible members are allowed. */
    private boolean packageAllowed;

    /** Allows immutable fields of final classes to be declared as public. */
    private boolean allowPublicImmutableFields;

    /** Allows final fields to be declared as public. */
    private boolean allowPublicFinalFields;

    /** List of immutable classes canonical names. */
    private List<String> immutableClassCanonicalNames = new ArrayList<>(DEFAULT_IMMUTABLE_TYPES);

    /**
     * Set the list of ignore annotations.
     * @param annotationNames array of ignore annotations canonical names.
     */
    public void setIgnoreAnnotationCanonicalNames(String... annotationNames) {
        ignoreAnnotationCanonicalNames = Arrays.asList(annotationNames);
    }

    /**
     * Set whether protected members are allowed.
     * @param protectedAllowed whether protected members are allowed
     */
    public void setProtectedAllowed(boolean protectedAllowed) {
        this.protectedAllowed = protectedAllowed;
    }

    /**
     * Set whether package visible members are allowed.
     * @param packageAllowed whether package visible members are allowed
     */
    public void setPackageAllowed(boolean packageAllowed) {
        this.packageAllowed = packageAllowed;
    }

    /**
     * Set the pattern for public members to ignore.
     * @param pattern
     *        pattern for public members to ignore.
     */
    public void setPublicMemberPattern(Pattern pattern) {
        publicMemberPattern = pattern;
    }

    /**
     * Sets whether public immutable fields are allowed.
     * @param allow user's value.
     */
    public void setAllowPublicImmutableFields(boolean allow) {
        allowPublicImmutableFields = allow;
    }

    /**
     * Sets whether public final fields are allowed.
     * @param allow user's value.
     */
    public void setAllowPublicFinalFields(boolean allow) {
        allowPublicFinalFields = allow;
    }

    /**
     * Set the list of immutable classes types names.
     * @param classNames array of immutable types canonical names.
     */
    public void setImmutableClassCanonicalNames(String... classNames) {
        immutableClassCanonicalNames = Arrays.asList(classNames);
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IMPORT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
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
     * @param variableDef {@link TokenTypes#VARIABLE_DEF VARIABLE_DEF}
     * @return true if current variable definition is definition of an anonymous class.
     */
    private static boolean isAnonymousClassVariable(DetailAST variableDef) {
        return variableDef.getParent().getType() != TokenTypes.OBJBLOCK;
    }

    /**
     * Checks access modifier of given variable.
     * If it is not proper according to Check - puts violation on it.
     * @param variableDef variable to check.
     */
    private void visitVariableDef(DetailAST variableDef) {
        final boolean inInterfaceOrAnnotationBlock =
                ScopeUtils.isInInterfaceOrAnnotationBlock(variableDef);

        if (!inInterfaceOrAnnotationBlock && !hasIgnoreAnnotation(variableDef)) {
            final DetailAST varNameAST = variableDef.findFirstToken(TokenTypes.TYPE)
                .getNextSibling();
            final String varName = varNameAST.getText();
            if (!hasProperAccessModifier(variableDef, varName)) {
                log(varNameAST.getLineNo(), varNameAST.getColumnNo(),
                        MSG_KEY, varName);
            }
        }
    }

    /**
     * Checks if variable def has ignore annotation.
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
            if (!immutableClassCanonicalNames.contains(canonicalName)
                     && immutableClassShortNames.contains(shortName)) {
                immutableClassShortNames.remove(shortName);
            }
            if (!ignoreAnnotationCanonicalNames.contains(canonicalName)
                     && ignoreAnnotationShortNames.contains(shortName)) {
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
     * @param variableDef Variable definition node.
     * @return true if allowed.
     */
    private boolean isAllowedPublicField(DetailAST variableDef) {
        return allowPublicFinalFields && isFinalField(variableDef)
            || allowPublicImmutableFields && isImmutableFieldDefinedInFinalClass(variableDef);
    }

    /**
     * Checks whether immutable field is defined in final class.
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
     * @param defAST AST for a variable or class definition.
     * @return the set of modifier Strings for defAST.
     */
    private static Set<String> getModifiers(DetailAST defAST) {
        final AST modifiersAST = defAST.findFirstToken(TokenTypes.MODIFIERS);
        final Set<String> modifiersSet = new HashSet<>();
        if (modifiersAST != null) {
            AST modifier = modifiersAST.getFirstChild();
            while (modifier != null) {
                modifiersSet.add(modifier.getText());
                modifier = modifier.getNextSibling();
            }
        }
        return modifiersSet;
    }

    /**
     * Returns the visibility scope for the variable.
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
     * @param variableDef Field in consideration.
     * @return true if field is immutable.
     */
    private boolean isImmutableField(DetailAST variableDef) {
        boolean result = false;
        if (isFinalField(variableDef)) {
            final DetailAST type = variableDef.findFirstToken(TokenTypes.TYPE);
            final boolean isCanonicalName = isCanonicalName(type);
            final String typeName = getTypeName(type, isCanonicalName);
            final DetailAST typeArgs = getGenericTypeArgs(type, isCanonicalName);
            if (typeArgs == null) {
                result = !isCanonicalName && isPrimitive(type)
                    || immutableClassShortNames.contains(typeName)
                    || isCanonicalName && immutableClassCanonicalNames.contains(typeName);
            }
            else {
                final List<String> argsClassNames = getTypeArgsClassNames(typeArgs);
                result = (immutableClassShortNames.contains(typeName)
                    || isCanonicalName && immutableClassCanonicalNames.contains(typeName))
                    && areImmutableTypeArguments(argsClassNames);
            }
        }
        return result;
    }

    /**
     * Checks whether type definition is in canonical form.
     * @param type type definition token.
     * @return true if type definition is in canonical form.
     */
    private static boolean isCanonicalName(DetailAST type) {
        return type.getFirstChild().getType() == TokenTypes.DOT;
    }

    /**
     * Returns generic type arguments token.
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
     * @param typeArgsClassNames type arguments class names.
     * @return true if all of generic type arguments are immutable.
     */
    private boolean areImmutableTypeArguments(List<String> typeArgsClassNames) {
        return !typeArgsClassNames.stream().filter(
            typeName -> {
                return !immutableClassShortNames.contains(typeName)
                    && !immutableClassCanonicalNames.contains(typeName);
            }).findFirst().isPresent();
    }

    /**
     * Checks whether current field is final.
     * @param variableDef field in consideration.
     * @return true if current field is final.
     */
    private static boolean isFinalField(DetailAST variableDef) {
        final DetailAST modifiers = variableDef.findFirstToken(TokenTypes.MODIFIERS);
        return modifiers.branchContains(TokenTypes.FINAL);
    }

    /**
     * Gets the name of type from given ast {@link TokenTypes#TYPE TYPE} node.
     * If type is specified via its canonical name - canonical name will be returned,
     * else - short type's name.
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
     * @param type Ast {@link TokenTypes#TYPE TYPE} node.
     * @return true if current type is primitive type.
     */
    private static boolean isPrimitive(DetailAST type) {
        return type.getFirstChild().getType() != TokenTypes.IDENT;
    }

    /**
     * Gets canonical type's name from given {@link TokenTypes#TYPE TYPE} node.
     * @param type DetailAST {@link TokenTypes#TYPE TYPE} node.
     * @return canonical type's name
     */
    private static String getCanonicalName(DetailAST type) {
        final StringBuilder canonicalNameBuilder = new StringBuilder(256);
        DetailAST toVisit = type.getFirstChild();
        while (toVisit != null) {
            toVisit = getNextSubTreeNode(toVisit, type);
            if (toVisit != null && toVisit.getType() == TokenTypes.IDENT) {
                canonicalNameBuilder.append(toVisit.getText());
                final DetailAST nextSubTreeNode = getNextSubTreeNode(toVisit, type);
                if (nextSubTreeNode != null) {
                    if (nextSubTreeNode.getType() == TokenTypes.TYPE_ARGUMENTS) {
                        break;
                    }
                    canonicalNameBuilder.append('.');
                }
            }
        }
        return canonicalNameBuilder.toString();
    }

    /**
     * Gets the next node of a syntactical tree (child of a current node or
     * sibling of a current node, or sibling of a parent of a current node).
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
            if (toVisitAst == null) {
                if (currentNode.getParent().equals(subTreeRootAst)
                         && currentNode.getParent().getColumnNo() == subTreeRootAst.getColumnNo()) {
                    break;
                }
                currentNode = currentNode.getParent();
            }
        }
        return toVisitAst;
    }

    /**
     * Gets the list with short names classes.
     * These names are taken from array of classes canonical names.
     * @param canonicalClassNames canonical class names.
     * @return the list of short names of classes.
     */
    private static List<String> getClassShortNames(List<String> canonicalClassNames) {
        final List<String> shortNames = new ArrayList<>();
        for (String canonicalClassName : canonicalClassNames) {
            final String shortClassName = canonicalClassName
                    .substring(canonicalClassName.lastIndexOf('.') + 1,
                    canonicalClassName.length());
            shortNames.add(shortClassName);
        }
        return shortNames;
    }

    /**
     * Gets the short class name from given canonical name.
     * @param canonicalClassName canonical class name.
     * @return short name of class.
     */
    private static String getClassShortName(String canonicalClassName) {
        return canonicalClassName
                .substring(canonicalClassName.lastIndexOf('.') + 1,
                canonicalClassName.length());
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

        final DetailAST holder = AnnotationUtility.getAnnotationHolder(variableDef);

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
