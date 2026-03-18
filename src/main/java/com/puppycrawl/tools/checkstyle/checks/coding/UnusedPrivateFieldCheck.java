///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that a private field is declared but never used.
 * </div>
 *
 * @since 13.4.0
 */
@FileStatefulCheck
public class UnusedPrivateFieldCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_PRIVATE_FIELD = "unused.private.field";

    /**
     * Name of the serialVersionUID field, always excluded.
     */
    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * Token types that are unacceptable parents of an IDENT.
     * If an IDENT's parent is one of these, it is not a field read.
     */
    private static final int[] UNACCEPTABLE_PARENT_OF_IDENT = {
        TokenTypes.VARIABLE_DEF,
        TokenTypes.LITERAL_NEW,
        TokenTypes.METHOD_CALL,
        TokenTypes.TYPE,
        TokenTypes.METHOD_DEF,
        TokenTypes.CLASS_DEF,
        TokenTypes.ANNOTATION,
        TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR,
    };

    /**
     * Stack of class scopes. One entry per CLASS_DEF visited.
     * Popped on leaveToken of CLASS_DEF.
     */
    private final Deque<ClassScope> classScopeStack = new ArrayDeque<>();

    /**
     * Maps each CLASS_DEF ast to the set of field names read via
     * unqualified access inside that class (including lambdas and anon
     * classes whose this refers to the enclosing class).
     */
    private final Map<DetailAST, Set<String>> usedNamesPerClass = new HashMap<>();

    /**
     * Maps each CLASS_DEF ast to a map of (fieldName -> VARIABLE_DEF ast).
     * Insertion-ordered so violations are reported top-to-bottom.
     */
    private final Map<DetailAST, Map<String, DetailAST>> privateFieldsPerClass =
            new LinkedHashMap<>();

    /**
     * Maps each top-level CLASS_DEF ast to the set of field names read
     * via qualified dot access (obj.field) anywhere in that nest.
     * Conservative — we cannot resolve the type of obj without a full
     * symbol resolver, so we mark across the whole nest.
     */
    private final Map<DetailAST, Set<String>> usedNamesViaDotPerTopLevel = new HashMap<>();

    /** Maps each CLASS_DEF ast to its top-level ancestor CLASS_DEF ast. */
    private final Map<DetailAST, DetailAST> classToTopLevel = new HashMap<>();

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.DOT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        classScopeStack.clear();
        classToTopLevel.clear();
        privateFieldsPerClass.clear();
        usedNamesPerClass.clear();
        usedNamesViaDotPerTopLevel.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF,
                 TokenTypes.ENUM_DEF,
                 TokenTypes.RECORD_DEF -> visitClassDef(ast);
            case TokenTypes.VARIABLE_DEF -> visitVariableDef(ast);
            case TokenTypes.IDENT -> visitIdent(ast);
            case TokenTypes.DOT -> visitDot(ast);
            default -> throw new IllegalStateException("Unexpected token type: " + ast.getType());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (TokenUtil.isOfType(ast,
                TokenTypes.CLASS_DEF,
                TokenTypes.ENUM_DEF,
                TokenTypes.RECORD_DEF)) {
            classScopeStack.pop();
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        for (Map.Entry<DetailAST, Map<String, DetailAST>> entry
                : privateFieldsPerClass.entrySet()) {
            final DetailAST classDef = entry.getKey();
            final Map<String, DetailAST> fields = entry.getValue();
            final DetailAST topLevel = findTopLevelClass(classDef);

            final Set<String> usedUnqualified =
                    usedNamesPerClass.getOrDefault(classDef, Set.of());
            final Set<String> usedViaDot =
                    usedNamesViaDotPerTopLevel.getOrDefault(topLevel, Set.of());

            fields.forEach((fieldName, varDefAst) -> {
                if (!usedUnqualified.contains(fieldName)
                        && !usedViaDot.contains(fieldName)) {
                    log(varDefAst, MSG_UNUSED_PRIVATE_FIELD, fieldName);
                }
            });
        }
    }

    /**
     * Handles visiting a type declaration (CLASS_DEF, ENUM_DEF, RECORD_DEF).
     * Pushes a new ClassScope onto the stack.
     *
     * @param typeDef the type declaration ast
     */
    private void visitClassDef(DetailAST typeDef) {
        final DetailAST topLevel;
        if (classScopeStack.isEmpty()) {
            topLevel = typeDef;
        }
        else {
            topLevel = classScopeStack.peekLast().topLevelClassDef();
        }
        classScopeStack.push(new ClassScope(typeDef, topLevel));
        classToTopLevel.put(typeDef, topLevel);
        privateFieldsPerClass.put(typeDef, new LinkedHashMap<>());
        usedNamesPerClass.put(typeDef, new HashSet<>());
        usedNamesViaDotPerTopLevel.computeIfAbsent(topLevel, ignored -> new HashSet<>());
    }

    /**
     * Handles visiting a VARIABLE_DEF. If it is a private field (not local,
     * not serialVersionUID), records it in privateFieldsPerClass.
     *
     * @param varDef the VARIABLE_DEF ast
     */
    private void visitVariableDef(DetailAST varDef) {
        if (isField(varDef)) {
            final DetailAST modifiers = varDef.findFirstToken(TokenTypes.MODIFIERS);
            if (modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null) {
                final DetailAST ident = varDef.findFirstToken(TokenTypes.IDENT);
                final String fieldName = ident.getText();
                if (!SERIAL_VERSION_UID.equals(fieldName)) {
                    final DetailAST currentClass = classScopeStack.peek().classDef();
                    privateFieldsPerClass.get(currentClass).put(fieldName, varDef);
                }
            }
        }
    }

    /**
     * Handles visiting an IDENT token. If it represents an unqualified
     * field read (not a declaration, type, method name, or LHS of simple
     * assign), marks it as used in the current class's used-names set.
     *
     * @param identAst the IDENT ast
     */
    private void visitIdent(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        final boolean isRightSideOfDot = parent.getType() == TokenTypes.DOT
                && identAst != parent.getFirstChild();
        if (!isRightSideOfDot
                && !TokenUtil.isOfType(parent, UNACCEPTABLE_PARENT_OF_IDENT)
                && !isLeftHandSideValue(identAst)) {
            final DetailAST currentClass = findEnclosingNonAnonClass();
            if (currentClass != null) {
                usedNamesPerClass
                        .computeIfAbsent(currentClass, ignored -> new HashSet<>())
                        .add(identAst.getText());
            }
        }
    }

    /**
     * Finds a class on the scope stack by its simple name.
     *
     * @param name the simple class name to find
     * @return the CLASS_DEF ast of the matching class, or null
     */
    private DetailAST findClassByName(String name) {
        return classScopeStack.stream()
                .map(ClassScope::classDef)
                .filter(classDef -> {
                    return isClassWithName(classDef, name);
                })
                .findFirst()
                .orElseThrow(() -> {
                    return new IllegalStateException(
                            "No class found with name: " + name);
                });
    }

    /**
     * Whether the given CLASS_DEF ast has the given simple name.
     *
     * @param classDef the CLASS_DEF ast
     * @param name the simple class name to match
     * @return true if the class has the given name
     */
    private static boolean isClassWithName(DetailAST classDef, String name) {
        final DetailAST ident = classDef.findFirstToken(TokenTypes.IDENT);
        return ident != null && name.equals(ident.getText());
    }

    /**
     * Handles visiting a DOT token. If the rightmost child is an IDENT
     * (a field name), determines whether it is a this-qualified read
     * (mark in current class only) or an object-qualified read (mark
     * conservatively in the top-level nest).
     *
     * @param dotAst the DOT ast
     */
    private void visitDot(DetailAST dotAst) {
        final DetailAST lastChild = dotAst.getLastChild();
        if (dotAst.getParent().getType() != TokenTypes.LITERAL_NEW
                && lastChild.getType() == TokenTypes.IDENT
                && !isLeftHandSideOfDot(dotAst)) {
            final String fieldName = lastChild.getText();
            final DetailAST firstChild = dotAst.getFirstChild();
            if (isThisExpression(firstChild)) {
                final DetailAST targetClass;
                if (firstChild.getType() == TokenTypes.DOT) {
                    final String className = firstChild.getFirstChild().getText();
                    targetClass = findClassByName(className);
                }
                else {
                    targetClass = findEnclosingNonAnonClass();
                }
                if (targetClass != null) {
                    usedNamesPerClass
                            .computeIfAbsent(targetClass, ignored -> new HashSet<>())
                            .add(fieldName);
                }
            }
            else if (!classScopeStack.isEmpty()) {
                final DetailAST topLevel =
                        classScopeStack.peek().topLevelClassDef();
                usedNamesViaDotPerTopLevel
                        .computeIfAbsent(topLevel, ignored -> new HashSet<>())
                        .add(fieldName);
                if (firstChild.getType() == TokenTypes.IDENT) {
                    usedNamesViaDotPerTopLevel
                            .computeIfAbsent(topLevel, ignored -> new HashSet<>())
                            .add(firstChild.getText());
                }
            }
        }
    }

    /**
     * Whether the given VARIABLE_DEF is a class field (its parent is OBJBLOCK).
     *
     * @param varDef the VARIABLE_DEF ast
     * @return true if it is a field, false if it is a local variable
     */
    private static boolean isField(DetailAST varDef) {
        return varDef.getParent().getType() == TokenTypes.OBJBLOCK;
    }

    /**
     * Whether the IDENT is used as a left-hand side value — either the
     * left operand of a simple assignment, or a standalone increment/
     * decrement (which counts as a read, so returns false for those).
     * Stolen from UnusedLocalVariableCheck.
     *
     * @param identAst the IDENT ast
     * @return true if it is a write-only left-hand side value
     */
    private static boolean isLeftHandSideValue(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return parent.getType() == TokenTypes.ASSIGN
                && identAst == parent.getFirstChild();
    }

    /**
     * Whether a DOT expression is on the left-hand side of a simple assignment.
     * e.g. obj.field = value — this is a write, not a read.
     *
     * @param dotAst the DOT ast
     * @return true if the dot is the LHS of a simple assignment
     */
    private static boolean isLeftHandSideOfDot(DetailAST dotAst) {
        final DetailAST parent = dotAst.getParent();
        return parent.getType() == TokenTypes.ASSIGN
                && dotAst == parent.getFirstChild();
    }

    /**
     * Whether the given ast represents a this expression —
     * either {@code this} or {@code EnclosingClass.this}.
     *
     * @param ast the ast to check
     * @return true if it is a this expression
     */
    private static boolean isThisExpression(DetailAST ast) {
        final boolean result;
        if (ast.getType() == TokenTypes.LITERAL_THIS) {
            result = true;
        }
        else if (ast.getType() == TokenTypes.DOT) {
            result = ast.getLastChild().getType() == TokenTypes.LITERAL_THIS;
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * Finds the nearest enclosing named class (CLASS_DEF, ENUM_DEF, RECORD_DEF)
     * on the scope stack, skipping anonymous classes. Unqualified reads inside
     * a lambda or anonymous class attribute to the enclosing named class because
     * their {@code this} refers to the enclosing instance.
     *
     * @return the nearest enclosing non-anonymous CLASS_DEF, or null
     */
    private DetailAST findEnclosingNonAnonClass() {
        return classScopeStack.stream()
                .map(ClassScope::classDef)
                .filter(classDef -> !isAnonymousClass(classDef))
                .findFirst()
                .orElse(null);
    }

    /**
     * Whether a CLASS_DEF represents an anonymous class.
     * Anonymous classes have LITERAL_NEW as their grandparent.
     *
     * @param classDef the CLASS_DEF ast
     * @return true if it is an anonymous class
     */
    private static boolean isAnonymousClass(DetailAST classDef) {
        final DetailAST parent = classDef.getParent();
        return parent.getType() == TokenTypes.OBJBLOCK
                && parent.getParent().getType() == TokenTypes.LITERAL_NEW;
    }

    /**
     * Finds the top-level class ancestor for the given class def by
     * walking up the scope stack to its last (bottom) entry.
     *
     * @param classDef the CLASS_DEF ast to find the top-level ancestor for
     * @return the top-level CLASS_DEF ast
     */
    private DetailAST findTopLevelClass(DetailAST classDef) {
        return classToTopLevel.getOrDefault(classDef, classDef);
    }

    /**
     * Maintains information about a single class scope.
     *
     * @param classDef the CLASS_DEF ast for this scope
     * @param topLevelClassDef the top-level CLASS_DEF ancestor ast
     */
    private record ClassScope(DetailAST classDef, DetailAST topLevelClassDef) {
    }
}
