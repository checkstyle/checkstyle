///
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks that the parts of a class, record, or interface declaration appear in the order
 * suggested by the
 * <a href="https://checkstyle.org/styleguides/sun-code-conventions-19990420/CodeConventions.doc2.html#a1852">
 * Code Conventions for the Java Programming Language</a>.
 * </div>
 *
 * <p>
 * According to
 * <a href="https://checkstyle.org/styleguides/sun-code-conventions-19990420/CodeConventions.doc2.html#a1852">
 * Code Conventions for the Java Programming Language</a>, the parts of a class
 * or interface declaration should appear in the following order:
 * </p>
 * <ol>
 * <li>
 * Class (static) variables. First the public class variables, then
 * protected, then package level (no access modifier), and then private.
 * </li>
 * <li> Instance variables. First the public class variables, then
 * protected, then package level (no access modifier), and then private.
 * </li>
 * <li> Constructors </li>
 * <li> Methods </li>
 * </ol>
 *
 * <p>
 * Purpose of <b>ignore*</b> option is to ignore related violations,
 * however it still impacts on other class members.
 * </p>
 *
 * <p>ATTENTION: the check skips class fields which have
 * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-8.html#jls-8.3.3">
 * forward references </a> from validation due to the fact that we have Checkstyle's limitations
 * to clearly detect user intention of fields location and grouping. For example:
 * </p>
 * <pre>
 * public class A {
 *   private double x = 1.0;
 *   private double y = 2.0;
 *   public double slope = x / y; // will be skipped from validation due to forward reference
 * }
 * </pre>
 * <ul>
 * <li>
 * Property {@code ignoreConstructors} - Control whether to ignore constructors.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code ignoreModifiers} - Control whether to ignore modifiers (fields, ...).
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code declaration.order.access}
 * </li>
 * <li>
 * {@code declaration.order.constructor}
 * </li>
 * <li>
 * {@code declaration.order.instance}
 * </li>
 * <li>
 * {@code declaration.order.static}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public class DeclarationOrderCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_CONSTRUCTOR = "declaration.order.constructor";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_STATIC = "declaration.order.static";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_INSTANCE = "declaration.order.instance";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ACCESS = "declaration.order.access";

    /** State for the VARIABLE_DEF. */
    private static final int STATE_STATIC_VARIABLE_DEF = 1;

    /** State for the VARIABLE_DEF. */
    private static final int STATE_INSTANCE_VARIABLE_DEF = 2;

    /** State for the CTOR_DEF. */
    private static final int STATE_CTOR_DEF = 3;

    /** State for the METHOD_DEF. */
    private static final int STATE_METHOD_DEF = 4;

    /**
     * List of Declaration States. This is necessary due to
     * inner classes that have their own state.
     */
    private Deque<ScopeState> scopeStates;

    /** Set of all class field names.*/
    private Set<String> classFieldNames;

    /** Control whether to ignore constructors. */
    private boolean ignoreConstructors;
    /** Control whether to ignore modifiers (fields, ...). */
    private boolean ignoreModifiers;

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
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.MODIFIERS,
            TokenTypes.OBJBLOCK,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        scopeStates = new ArrayDeque<>();
        classFieldNames = new HashSet<>();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int parentType = ast.getParent().getType();

        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK:
                scopeStates.push(new ScopeState());
                break;
            case TokenTypes.MODIFIERS:
                if (parentType == TokenTypes.VARIABLE_DEF
                    && ast.getParent().getParent().getType() == TokenTypes.OBJBLOCK) {
                    processModifiers(ast);
                }
                break;
            case TokenTypes.CTOR_DEF:
            case TokenTypes.COMPACT_CTOR_DEF:
                if (parentType == TokenTypes.OBJBLOCK) {
                    processConstructor(ast);
                }
                break;
            case TokenTypes.METHOD_DEF:
                if (parentType == TokenTypes.OBJBLOCK) {
                    final ScopeState state = scopeStates.peek();
                    // nothing can be bigger than method's state
                    state.currentScopeState = STATE_METHOD_DEF;
                }
                break;
            case TokenTypes.VARIABLE_DEF:
                if (ScopeUtil.isClassFieldDef(ast)) {
                    final DetailAST fieldDef = ast.findFirstToken(TokenTypes.IDENT);
                    classFieldNames.add(fieldDef.getText());
                }
                break;
            default:
                break;
        }
    }

    /**
     * Processes constructor.
     *
     * @param ast constructor AST.
     */
    private void processConstructor(DetailAST ast) {
        final ScopeState state = scopeStates.peek();
        if (state.currentScopeState > STATE_CTOR_DEF) {
            if (!ignoreConstructors) {
                log(ast, MSG_CONSTRUCTOR);
            }
        }
        else {
            state.currentScopeState = STATE_CTOR_DEF;
        }
    }

    /**
     * Processes modifiers.
     *
     * @param ast ast of Modifiers.
     */
    private void processModifiers(DetailAST ast) {
        final ScopeState state = scopeStates.peek();
        final boolean isStateValid = processModifiersState(ast, state);
        processModifiersSubState(ast, state, isStateValid);
    }

    /**
     * Process if given modifiers are appropriate in given state
     * ({@code STATE_STATIC_VARIABLE_DEF}, {@code STATE_INSTANCE_VARIABLE_DEF},
     * ({@code STATE_CTOR_DEF}, {@code STATE_METHOD_DEF}), if it is
     * it updates states where appropriate or logs violation.
     *
     * @param modifierAst modifiers to process
     * @param state current state
     * @return true if modifierAst is valid in given state, false otherwise
     */
    private boolean processModifiersState(DetailAST modifierAst, ScopeState state) {
        boolean isStateValid = true;
        if (modifierAst.findFirstToken(TokenTypes.LITERAL_STATIC) == null) {
            if (state.currentScopeState > STATE_INSTANCE_VARIABLE_DEF) {
                isStateValid = false;
                log(modifierAst, MSG_INSTANCE);
            }
            else if (state.currentScopeState == STATE_STATIC_VARIABLE_DEF) {
                state.declarationAccess = Scope.PUBLIC;
                state.currentScopeState = STATE_INSTANCE_VARIABLE_DEF;
            }
        }
        else if (state.currentScopeState > STATE_INSTANCE_VARIABLE_DEF
                || state.currentScopeState > STATE_STATIC_VARIABLE_DEF && !ignoreModifiers) {
            isStateValid = false;
            log(modifierAst, MSG_STATIC);
        }
        return isStateValid;
    }

    /**
     * Checks if given modifiers are valid in substate of given
     * state({@code Scope}), if it is it updates substate or else it
     * logs violation.
     *
     * @param modifiersAst modifiers to process
     * @param state current state
     * @param isStateValid is main state for given modifiers is valid
     */
    private void processModifiersSubState(DetailAST modifiersAst, ScopeState state,
                                          boolean isStateValid) {
        final Scope access = ScopeUtil.getScopeFromMods(modifiersAst);
        if (state.declarationAccess.compareTo(access) > 0) {
            if (isStateValid
                    && !ignoreModifiers
                    && !isForwardReference(modifiersAst.getParent())) {
                log(modifiersAst, MSG_ACCESS);
            }
        }
        else {
            state.declarationAccess = access;
        }
    }

    /**
     * Checks whether an identifier references a field which has been already defined in class.
     *
     * @param fieldDef a field definition.
     * @return true if an identifier references a field which has been already defined in class.
     */
    private boolean isForwardReference(DetailAST fieldDef) {
        final DetailAST exprStartIdent = fieldDef.findFirstToken(TokenTypes.IDENT);
        final Set<DetailAST> exprIdents = getAllTokensOfType(exprStartIdent, TokenTypes.IDENT);
        boolean forwardReference = false;
        for (DetailAST ident : exprIdents) {
            if (classFieldNames.contains(ident.getText())) {
                forwardReference = true;
                break;
            }
        }
        return forwardReference;
    }

    /**
     * Collects all tokens of specific type starting with the current ast node.
     *
     * @param ast ast node.
     * @param tokenType token type.
     * @return a set of all tokens of specific type starting with the current ast node.
     */
    private static Set<DetailAST> getAllTokensOfType(DetailAST ast, int tokenType) {
        DetailAST vertex = ast;
        final Set<DetailAST> result = new HashSet<>();
        final Deque<DetailAST> stack = new ArrayDeque<>();
        while (vertex != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                vertex = stack.pop();
            }
            while (vertex != null) {
                if (vertex.getType() == tokenType && !vertex.equals(ast)) {
                    result.add(vertex);
                }
                if (vertex.getNextSibling() != null) {
                    stack.push(vertex.getNextSibling());
                }
                vertex = vertex.getFirstChild();
            }
        }
        return result;
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.OBJBLOCK) {
            scopeStates.pop();
        }
    }

    /**
     * Setter to control whether to ignore constructors.
     *
     * @param ignoreConstructors whether to ignore constructors.
     * @since 5.2
     */
    public void setIgnoreConstructors(boolean ignoreConstructors) {
        this.ignoreConstructors = ignoreConstructors;
    }

    /**
     * Setter to control whether to ignore modifiers (fields, ...).
     *
     * @param ignoreModifiers whether to ignore modifiers.
     * @since 5.2
     */
    public void setIgnoreModifiers(boolean ignoreModifiers) {
        this.ignoreModifiers = ignoreModifiers;
    }

    /**
     * Private class to encapsulate the state.
     */
    private static final class ScopeState {

        /** The state the check is in. */
        private int currentScopeState = STATE_STATIC_VARIABLE_DEF;

        /** The sub-state the check is in. */
        private Scope declarationAccess = Scope.PUBLIC;

    }

}
