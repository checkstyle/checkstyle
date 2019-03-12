////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Checks that particular classes or interfaces are never used.
 * This includes usage as type parameters, type arguments or type bounds in various places,
 * classes or interfaces to extend or implement, method return types, field types,
 * local variable types and parameter types.
 *
 * <p>Rationale:
 * Helps reduce coupling on concrete classes.
 *
 * <p>Check has following properties:
 *
 * <p><b>illegalAbstractClassNameFormat</b> - Pattern for illegal abstract class names.
 *
 * <p><b>legalAbstractClassNames</b> - Abstract classes that may be used as types.
 *
 * <p><b>illegalClassNames</b> - Classes that should not be used as types in variable
   declarations, return values or parameters.
 * It is possible to set illegal class names via short or
 * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.7">
 *  canonical</a> name.
 *  Specifying illegal type invokes analyzing imports and Check puts violations at
 *   corresponding declarations
 *  (of variables, methods or parameters). This helps to avoid ambiguous cases, e.g.:
 *
 * <p>{@code java.awt.List} was set as illegal class name, then, code like:
 *
 * <p>{@code
 * import java.util.List;<br>
 * ...<br>
 * List list; //No violation here
 * }
 *
 * <p>will be ok.
 *
 * <p><b>validateAbstractClassNames</b> - controls whether to validate abstract class names.
 * Default value is <b>false</b>
 * </p>
 *
 * <p><b>ignoredMethodNames</b> - Methods that should not be checked.
 *
 * <p><b>memberModifiers</b> - To check only methods, fields, classes and interface with any of the
 * specified modifiers. This property does not affect method calls nor method references.
 *
 * <p>In most cases it's justified to put following classes to <b>illegalClassNames</b>:
 * <ul>
 * <li>GregorianCalendar</li>
 * <li>Hashtable</li>
 * <li>ArrayList</li>
 * <li>LinkedList</li>
 * <li>Vector</li>
 * </ul>
 *
 * <p>as methods that are differ from interface methods are rear used, so in most cases user will
 *  benefit from checking for them.
 * </p>
 *
 */
@FileStatefulCheck
public final class IllegalTypeCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "illegal.type";

    /** Types illegal by default. */
    private static final String[] DEFAULT_ILLEGAL_TYPES = {
        "StringBuffer",
        "ArrayDeque",
        "ArrayList",
        "EnumMap",
        "EnumSet",
        "HashMap",
        "HashSet",
        "Hashtable",
        "IdentityHashMap",
        "LinkedHashMap",
        "LinkedHashSet",
        "LinkedList",
        "PriorityQueue",
        "Stack",
        "TreeMap",
        "TreeSet",
        "Vector",
        "WeakHashMap",
        "ArrayBlockingQueue",
        "ConcurrentHashMap",
        "ConcurrentLinkedDeque",
        "ConcurrentLinkedQueue",
        "ConcurrentSkipListMap",
        "ConcurrentSkipListSet",
        "CopyOnWriteArrayList",
        "CopyOnWriteArraySet",
        "DelayQueue",
        "LinkedBlockingDeque",
        "LinkedBlockingQueue",
        "PriorityBlockingQueue",
        "SynchronousQueue",
        "java.lang.StringBuffer",
        "java.util.ArrayDeque",
        "java.util.ArrayList",
        "java.util.EnumMap",
        "java.util.EnumSet",
        "java.util.HashMap",
        "java.util.HashSet",
        "java.util.Hashtable",
        "java.util.IdentityHashMap",
        "java.util.LinkedHashMap",
        "java.util.LinkedHashSet",
        "java.util.LinkedList",
        "java.util.PriorityQueue",
        "java.util.Stack",
        "java.util.TreeMap",
        "java.util.TreeSet",
        "java.util.Vector",
        "java.util.WeakHashMap",
        "java.util.concurrent.ArrayBlockingQueue",
        "java.util.concurrent.ConcurrentHashMap",
        "java.util.concurrent.ConcurrentLinkedDeque",
        "java.util.concurrent.ConcurrentLinkedQueue",
        "java.util.concurrent.ConcurrentSkipListMap",
        "java.util.concurrent.ConcurrentSkipListSet",
        "java.util.concurrent.CopyOnWriteArrayList",
        "java.util.concurrent.CopyOnWriteArraySet",
        "java.util.concurrent.DelayQueue",
        "java.util.concurrent.LinkedBlockingDeque",
        "java.util.concurrent.LinkedBlockingQueue",
        "java.util.concurrent.PriorityBlockingQueue",
        "java.util.concurrent.SynchronousQueue",
    };

    /** Default ignored method names. */
    private static final String[] DEFAULT_IGNORED_METHOD_NAMES = {
        "getInitialContext",
        "getEnvironment",
    };

    /** Illegal classes. */
    private final Set<String> illegalClassNames = new HashSet<>();
    /** Illegal short classes. */
    private final Set<String> illegalShortClassNames = new HashSet<>();
    /** Legal abstract classes. */
    private final Set<String> legalAbstractClassNames = new HashSet<>();
    /** Methods which should be ignored. */
    private final Set<String> ignoredMethodNames = new HashSet<>();
    /** Check methods, fields, classes and interfaces with any corresponding modifier. */
    private List<Integer> memberModifiers;

    /** The regexp to match against. */
    private Pattern illegalAbstractClassNameFormat = Pattern.compile("^(.*[.])?Abstract.*$");

    /**
     * Controls whether to validate abstract class names.
     */
    private boolean validateAbstractClassNames;

    /** Creates new instance of the check. */
    public IllegalTypeCheck() {
        setIllegalClassNames(DEFAULT_ILLEGAL_TYPES);
        setIgnoredMethodNames(DEFAULT_IGNORED_METHOD_NAMES);
    }

    /**
     * Set the format for the specified regular expression.
     * @param pattern a pattern.
     */
    public void setIllegalAbstractClassNameFormat(Pattern pattern) {
        illegalAbstractClassNameFormat = pattern;
    }

    /**
     * Sets whether to validate abstract class names.
     * @param validateAbstractClassNames whether abstract class names must be ignored.
     */
    public void setValidateAbstractClassNames(boolean validateAbstractClassNames) {
        this.validateAbstractClassNames = validateAbstractClassNames;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.IMPORT,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_CALL,
            TokenTypes.METHOD_DEF,
            TokenTypes.METHOD_REF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        illegalShortClassNames.clear();

        for (String s : illegalClassNames) {
            if (s.indexOf('.') == -1) {
                illegalShortClassNames.add(s);
            }
        }
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.IMPORT};
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
                visitTypeDef(ast);
                break;
            case TokenTypes.METHOD_CALL:
            case TokenTypes.METHOD_REF:
                visitMethodCallOrRef(ast);
                break;
            case TokenTypes.METHOD_DEF:
                visitMethodDef(ast);
                break;
            case TokenTypes.VARIABLE_DEF:
            case TokenTypes.ANNOTATION_FIELD_DEF:
                visitVariableDef(ast);
                break;
            case TokenTypes.PARAMETER_DEF:
                visitParameterDef(ast);
                break;
            case TokenTypes.IMPORT:
                visitImport(ast);
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    /**
     * Checks if given method, field, class or interface is verifiable
     * according to <b>memberModifiers</b> option.
     * @param typeDef {@code CLASS_DEF}, {@code INTERFACE_DEF}, {@code METHOD_DEF},
     *            {@code VARIABLE_DEF} or {@code ANNOTATION_FIELD_DEF} ast node.
     * @return true if member is verifiable according to <b>memberModifiers</b> option.
     */
    private boolean isVerifiable(DetailAST typeDef) {
        boolean result = true;
        if (memberModifiers != null) {
            final DetailAST modifiersAst = typeDef.findFirstToken(TokenTypes.MODIFIERS);
            result = isContainVerifiableType(modifiersAst);
        }
        return result;
    }

    /**
     * Checks is modifiers contain verifiable type.
     *
     * @param modifiers
     *            parent node for all modifiers
     * @return true if method or variable can be verified
     */
    private boolean isContainVerifiableType(DetailAST modifiers) {
        boolean result = false;
        if (modifiers.getFirstChild() != null) {
            for (DetailAST modifier = modifiers.getFirstChild(); modifier != null;
                     modifier = modifier.getNextSibling()) {
                if (memberModifiers.contains(modifier.getType())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Checks the super type and implemented interfaces of a given type.
     * @param typeDef class or interface for check.
     */
    private void visitTypeDef(DetailAST typeDef) {
        if (isVerifiable(typeDef)) {
            checkTypeParameters(typeDef);
            final DetailAST extendsClause = typeDef.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
            if (extendsClause != null) {
                checkBaseTypes(extendsClause);
            }
            final DetailAST implementsClause = typeDef.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);
            if (implementsClause != null) {
                checkBaseTypes(implementsClause);
            }
        }
    }

    /**
     * Checks return type of a given method.
     * @param methodDef method for check.
     */
    private void visitMethodDef(DetailAST methodDef) {
        if (isVerifiable(methodDef) && isCheckedMethod(methodDef)) {
            checkClassName(methodDef);
        }
    }

    /**
     * Checks type of parameters.
     * @param parameterDef parameter list for check.
     */
    private void visitParameterDef(DetailAST parameterDef) {
        final DetailAST grandParentAST = parameterDef.getParent().getParent();

        if (grandParentAST.getType() == TokenTypes.METHOD_DEF
            && isCheckedMethod(grandParentAST)
            && isVerifiable(grandParentAST)) {
            checkClassName(parameterDef);
        }
    }

    /**
     * Checks type of given variable.
     * @param variableDef variable to check.
     */
    private void visitVariableDef(DetailAST variableDef) {
        if (isVerifiable(variableDef)) {
            checkClassName(variableDef);
        }
    }

    /**
     * Checks the type arguments of given method call/reference.
     * @param methodCallOrRef method call/reference to check.
     */
    private void visitMethodCallOrRef(DetailAST methodCallOrRef) {
        checkTypeArguments(methodCallOrRef);
    }

    /**
     * Checks imported type (as static and star imports are not supported by Check,
     *  only type is in the consideration).<br>
     * If this type is illegal due to Check's options - puts violation on it.
     * @param importAst {@link TokenTypes#IMPORT Import}
     */
    private void visitImport(DetailAST importAst) {
        if (!isStarImport(importAst)) {
            final String canonicalName = getImportedTypeCanonicalName(importAst);
            extendIllegalClassNamesWithShortName(canonicalName);
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
     * Checks type and type arguments/parameters of given method, parameter, variable or
     * method call/reference.
     * @param ast node to check.
     */
    private void checkClassName(DetailAST ast) {
        final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
        checkType(type);
        checkTypeParameters(ast);
    }

    /**
     * Checks the identifier of the given type.
     * @param type node to check.
     */
    private void checkIdent(DetailAST type) {
        final FullIdent ident = FullIdent.createFullIdent(type);
        if (isMatchingClassName(ident.getText())) {
            log(ident.getDetailAst(), MSG_KEY, ident.getText());
        }
    }

    /**
     * Checks the {@code extends} or {@code implements} statement.
     * @param clause DetailAST for either {@link TokenTypes#EXTENDS_CLAUSE} or
     *               {@link TokenTypes#IMPLEMENTS_CLAUSE}
     */
    private void checkBaseTypes(DetailAST clause) {
        DetailAST child = clause.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.IDENT) {
                checkIdent(child);
            }
            else if (child.getType() == TokenTypes.TYPE_ARGUMENTS) {
                TokenUtil.forEachChild(child, TokenTypes.TYPE_ARGUMENT, this::checkType);
            }
            child = child.getNextSibling();
        }
    }

    /**
     * Checks the given type, its arguments and parameters.
     * @param type node to check.
     */
    private void checkType(DetailAST type) {
        checkIdent(type.getFirstChild());
        checkTypeArguments(type);
        checkTypeBounds(type);
    }

    /**
     * Checks the upper and lower bounds for the given type.
     * @param type node to check.
     */
    private void checkTypeBounds(DetailAST type) {
        final DetailAST upperBounds = type.findFirstToken(TokenTypes.TYPE_UPPER_BOUNDS);
        if (upperBounds != null) {
            checkType(upperBounds);
        }
        final DetailAST lowerBounds = type.findFirstToken(TokenTypes.TYPE_LOWER_BOUNDS);
        if (lowerBounds != null) {
            checkType(lowerBounds);
        }
    }

    /**
     * Checks the type parameters of the node.
     * @param node node to check.
     */
    private void checkTypeParameters(final DetailAST node) {
        final DetailAST typeParameters = node.findFirstToken(TokenTypes.TYPE_PARAMETERS);
        if (typeParameters != null) {
            TokenUtil.forEachChild(typeParameters, TokenTypes.TYPE_PARAMETER, this::checkType);
        }
    }

    /**
     * Checks the type arguments of the node.
     * @param node node to check.
     */
    private void checkTypeArguments(final DetailAST node) {
        DetailAST typeArguments = node.findFirstToken(TokenTypes.TYPE_ARGUMENTS);
        if (typeArguments == null) {
            typeArguments = node.getFirstChild().findFirstToken(TokenTypes.TYPE_ARGUMENTS);
        }

        if (typeArguments != null) {
            TokenUtil.forEachChild(typeArguments, TokenTypes.TYPE_ARGUMENT, this::checkType);
        }
    }

    /**
     * Returns true if given class name is one of illegal classes or else false.
     * @param className class name to check.
     * @return true if given class name is one of illegal classes
     *         or if it matches to abstract class names pattern.
     */
    private boolean isMatchingClassName(String className) {
        final String shortName = className.substring(className.lastIndexOf('.') + 1);
        return illegalClassNames.contains(className)
                || illegalShortClassNames.contains(shortName)
                || validateAbstractClassNames
                    && !legalAbstractClassNames.contains(className)
                    && illegalAbstractClassNameFormat.matcher(className).find();
    }

    /**
     * Extends illegal class names set via imported short type name.
     * @param canonicalName
     *  <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.7">
     *  Canonical</a> name of imported type.
     */
    private void extendIllegalClassNamesWithShortName(String canonicalName) {
        if (illegalClassNames.contains(canonicalName)) {
            final String shortName = canonicalName
                .substring(canonicalName.lastIndexOf('.') + 1);
            illegalShortClassNames.add(shortName);
        }
    }

    /**
     * Gets imported type's
     * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.7">
     *  canonical name</a>.
     * @param importAst {@link TokenTypes#IMPORT Import}
     * @return Imported canonical type's name.
     */
    private static String getImportedTypeCanonicalName(DetailAST importAst) {
        final StringBuilder canonicalNameBuilder = new StringBuilder(256);
        DetailAST toVisit = importAst;
        while (toVisit != null) {
            toVisit = getNextSubTreeNode(toVisit, importAst);
            if (toVisit != null && toVisit.getType() == TokenTypes.IDENT) {
                if (canonicalNameBuilder.length() > 0) {
                    canonicalNameBuilder.append('.');
                }
                canonicalNameBuilder.append(toVisit.getText());
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
                if (currentNode.getParent().equals(subTreeRootAst)) {
                    break;
                }
                currentNode = currentNode.getParent();
            }
        }
        return toVisitAst;
    }

    /**
     * Returns true if method has to be checked or false.
     * @param ast method def to check.
     * @return true if we should check this method.
     */
    private boolean isCheckedMethod(DetailAST ast) {
        final String methodName =
            ast.findFirstToken(TokenTypes.IDENT).getText();
        return !ignoredMethodNames.contains(methodName);
    }

    /**
     * Set the list of illegal variable types.
     * @param classNames array of illegal variable types
     * @noinspection WeakerAccess
     */
    public void setIllegalClassNames(String... classNames) {
        illegalClassNames.clear();
        Collections.addAll(illegalClassNames, classNames);
    }

    /**
     * Set the list of ignore method names.
     * @param methodNames array of ignored method names
     * @noinspection WeakerAccess
     */
    public void setIgnoredMethodNames(String... methodNames) {
        ignoredMethodNames.clear();
        Collections.addAll(ignoredMethodNames, methodNames);
    }

    /**
     * Set the list of legal abstract class names.
     * @param classNames array of legal abstract class names
     * @noinspection WeakerAccess
     */
    public void setLegalAbstractClassNames(String... classNames) {
        Collections.addAll(legalAbstractClassNames, classNames);
    }

    /**
     * Set the list of member modifiers (of methods, fields, classes and interfaces) which should
     * be checked.
     * @param modifiers String contains modifiers.
     */
    public void setMemberModifiers(String modifiers) {
        final List<Integer> modifiersList = new ArrayList<>();
        for (String modifier : modifiers.split(",")) {
            modifiersList.add(TokenUtil.getTokenId(modifier.trim()));
        }
        memberModifiers = modifiersList;
    }

}
