////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.IntStream;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that a local variable is declared and/or assigned, but not used.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;UnusedLocalVariable&quot;/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public void method(int b) {
 *     int a = 10; // violation
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
 * {@code unused.local.var}
 * </li>
 * </ul>
 *
 * @since 9.2
 */
@FileStatefulCheck
public class UnusedLocalVariableCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_LOCAL_VARIABLE = "unused.local.var";

    /**
     * An array of increment and decrement tokens.
     */
    private static final int[] INCREMENT_AND_DECREMENT_TOKENS = {
        TokenTypes.POST_INC,
        TokenTypes.POST_DEC,
        TokenTypes.INC,
        TokenTypes.DEC,
    };

    /**
     * An array of scope tokens.
     */
    private static final int[] SCOPES = {
        TokenTypes.SLIST,
        TokenTypes.LITERAL_IF,
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_WHILE,
    };

    /**
     * An array of scope tokens which are exclusive to pattern variables.
     */
    private static final int[] SCOPES_EXCLUSIVE_TO_PATTERN_VARIABLES = {
        TokenTypes.EXPR,
        TokenTypes.ELIST,
    };

    /**
     * Keeps VariableDesc objects inside stack of declared variables.
     */
    private Deque<VariableDesc> variables;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.DOT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.SLIST,
            TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.EXPR,
            TokenTypes.ELIST,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
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
    public void beginTree(DetailAST root) {
        variables = new ArrayDeque<>();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.DOT:
                final DetailAST firstDotOrMethodCallAst = getFirstDotOrMethodCallToken(ast);
                if (firstDotOrMethodCallAst == null
                        && ast.findFirstToken(TokenTypes.LITERAL_NEW) == null
                        && ast.findFirstToken(TokenTypes.LITERAL_SUPER) == null
                        && ast.findFirstToken(TokenTypes.LITERAL_CLASS) == null
                        && ast.findFirstToken(TokenTypes.LITERAL_THIS) == null) {
                    checkIdentifierAst(ast.findFirstToken(TokenTypes.IDENT));
                }
                break;
            case TokenTypes.VARIABLE_DEF:
                if (ast.getParent().getType() != TokenTypes.OBJBLOCK) {
                    final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                    final VariableDesc desc = new VariableDesc(ident.getText(), ast);
                    setScopeOfVariable(ast, desc);
                    variables.push(desc);
                }
                break;
            case TokenTypes.IDENT:
                if (!TokenUtil.isOfType(ast.getParent().getType(),
                        TokenTypes.VARIABLE_DEF, TokenTypes.DOT,
                        TokenTypes.LITERAL_NEW, TokenTypes.PATTERN_VARIABLE_DEF)) {
                    checkIdentifierAst(ast);
                }
                break;
            case TokenTypes.LITERAL_INSTANCEOF:
                final DetailAST patternVariableDef = ast
                        .findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF);
                if (patternVariableDef != null) {
                    final DetailAST identToken = patternVariableDef
                            .findFirstToken(TokenTypes.IDENT);
                    final VariableDesc variableDesc =
                            new VariableDesc(identToken.getText(), patternVariableDef);
                    setScopeOfPatternVariable(ast, patternVariableDef, variableDesc);
                    variables.push(variableDesc);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (TokenUtil.isOfType(ast.getType(),
                IntStream.concat(Arrays.stream(SCOPES),
                        Arrays.stream(SCOPES_EXCLUSIVE_TO_PATTERN_VARIABLES)).toArray())) {
            while (!variables.isEmpty() && variables.peek().getScope() == ast) {
                final VariableDesc variableDesc = variables.pop();
                if (!variableDesc.isUsed()) {
                    final DetailAST currAst = variableDesc.getVariableAst();
                    log(currAst, MSG_UNUSED_LOCAL_VARIABLE, variableDesc.name);
                }
            }
        }
    }

    /**
     * Gets the first DetailAst of type {@link TokenTypes#DOT} or
     * {@link TokenTypes#METHOD_CALL} token which is the
     * child of {@link TokenTypes#EXPR}.
     *
     * @param exprAst DetailAst of type {@link TokenTypes#EXPR}
     * @return DetailAst of type {@link TokenTypes#DOT} or {@link TokenTypes#METHOD_CALL}
     *         if present, otherwise null
     */
    private static DetailAST getFirstDotOrMethodCallToken(DetailAST exprAst) {
        final DetailAST result;
        final DetailAST dotToken = exprAst.findFirstToken(TokenTypes.DOT);
        if (dotToken == null) {
            result = exprAst.findFirstToken(TokenTypes.METHOD_CALL);
        }
        else {
            result = dotToken;
        }
        return result;
    }

    /**
     * Checks the identifier ast.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     */
    private void checkIdentifierAst(DetailAST identAst) {
        for (VariableDesc variableDesc : variables) {
            if (identAst.getText().equals(variableDesc.getName())
                    && !isLeftHandSideValue(identAst)
                    && isIdentUsedForVariable(identAst, variableDesc)) {
                variableDesc.registerAsUsed();
                break;
            }
        }
    }

    /**
     * Set the scope of variable.
     *
     * @param variableDef DetailAst of type {@link TokenTypes#VARIABLE_DEF}
     * @param variableDesc information about the local variable
     */
    private static void setScopeOfVariable(DetailAST variableDef, VariableDesc variableDesc) {
        final DetailAST parentAst = variableDef.getParent();
        if (parentAst.getType() == TokenTypes.SLIST) {
            variableDesc.setScope(parentAst);
        }
        else {
            final DetailAST grandParent = parentAst.getParent();
            final DetailAST scopeAst = grandParent.findFirstToken(TokenTypes.SLIST);
            if (scopeAst == null) {
                variableDesc.setScope(grandParent);
            }
            else {
                variableDesc.setScope(scopeAst);
            }
        }
    }

    /**
     * Checks if a given DetailAST of type {@link TokenTypes#IDENT}
     * is used for a variable.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @param variableDesc information about the local variable
     * @return True if identAst is used for a variable else false.
     */
    private boolean isIdentUsedForVariable(DetailAST identAst, VariableDesc variableDesc) {
        return TokenUtil.isOfType(variableDesc.getScope().getType(), SCOPES)
                || isIdentPresentInTheScopeOfPatternVariable(
                        identAst.getLineNo(), identAst.getColumnNo(),
                variableDesc.getScopeOfPatternVariableIncludingLineAndColumn());
    }

    /**
     * Checks if an ident is present between
     * {@link VariableDesc#scopeOfPatternVariableIncludingLineAndColumn}.
     *
     * @param identLine Line number of corresponding ident DetailAst
     * @param identColumn Column number of corresponding ident DetailAst
     * @param scopeOfPatternVariableIncludingLineAndColumn
     *     {@link VariableDesc#scopeOfPatternVariableIncludingLineAndColumn}
     * @return True if ident is present in the scope of pattern variable else false;
     */
    private static boolean isIdentPresentInTheScopeOfPatternVariable(int identLine,
            int identColumn, int... scopeOfPatternVariableIncludingLineAndColumn) {
        boolean result = false;
        if (scopeOfPatternVariableIncludingLineAndColumn[PatternVariableScope.LINE_START]
                == scopeOfPatternVariableIncludingLineAndColumn[PatternVariableScope.LINE_END]) {
            if (scopeOfPatternVariableIncludingLineAndColumn[
                    PatternVariableScope.LINE_START] == identLine
                    && identColumn < scopeOfPatternVariableIncludingLineAndColumn[
                    PatternVariableScope.COLUMN_END]) {
                result = true;
            }
        }
        else {
            if (identLine < scopeOfPatternVariableIncludingLineAndColumn[
                    PatternVariableScope.LINE_END]
                    || identColumn < scopeOfPatternVariableIncludingLineAndColumn[
                            PatternVariableScope.COLUMN_END]) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Set the scope of pattern variable.
     *
     * @param instanceOfAst DetailAst of type {@link TokenTypes#LITERAL_INSTANCEOF}
     * @param patternVariable DetailAst of type {@link TokenTypes#LITERAL_INSTANCEOF}
     * @param variableDesc information about the local variable
     */
    private static void setScopeOfPatternVariable(DetailAST instanceOfAst,
                                                  DetailAST patternVariable,
                                                  VariableDesc variableDesc) {
        DetailAST ancestorOfInstanceOfAst = instanceOfAst.getParent();
        final int scopeLineStart = patternVariable.getLineNo();
        int scopeLineEnd = scopeLineStart;
        int scopeColumnEnd = patternVariable.getColumnNo();
        while (ancestorOfInstanceOfAst.getType() == TokenTypes.LAND) {
            if (ancestorOfInstanceOfAst.getLineNo() >= scopeLineEnd) {
                final DetailAST lastChildOfLand = ancestorOfInstanceOfAst.getLastChild();
                final DetailAST grandChildOfLand = lastChildOfLand.getLastChild();
                if (grandChildOfLand == null) {
                    scopeLineEnd = lastChildOfLand.getLineNo();
                    scopeColumnEnd = lastChildOfLand.getColumnNo();
                }
                else {
                    scopeLineEnd = grandChildOfLand.getLineNo();
                    scopeColumnEnd = grandChildOfLand.getColumnNo();
                }
            }
            ancestorOfInstanceOfAst = ancestorOfInstanceOfAst.getParent();
        }

        variableDesc.setScopeOfPatternVariableIncludingLineAndColumn(scopeLineStart,
                scopeLineEnd, scopeColumnEnd);

        setScopeOfPatternVariableExcludingLineAndColumn(variableDesc, ancestorOfInstanceOfAst);
    }

    /**
     * Set the scope of pattern variable excluding the information
     * about its line and column number.
     *
     * @param variableDesc information about the local variable
     * @param ancestorOfInstanceOfAst ancestor of DetailAst of type
     *                                {@link TokenTypes#LITERAL_INSTANCEOF}
     */
    private static void setScopeOfPatternVariableExcludingLineAndColumn(
            VariableDesc variableDesc, DetailAST ancestorOfInstanceOfAst) {

        DetailAST ast = ancestorOfInstanceOfAst;
        DetailAST grandParent = ast.getParent();
        final boolean fullScopeVariable = ast.getType() == TokenTypes.EXPR
                && !TokenUtil.isOfType(grandParent.getType(), TokenTypes.ELIST,
                TokenTypes.LITERAL_DO);

        while (ast.getType() != TokenTypes.EXPR) {
            ast = ast.getParent();
        }
        grandParent = ast.getParent();
        DetailAST scopeAst;
        if (fullScopeVariable) {
            scopeAst = grandParent.findFirstToken(TokenTypes.SLIST);
            if (scopeAst == null) {
                scopeAst = grandParent;
            }
        }
        else if (grandParent.getType() == TokenTypes.ELIST) {
            scopeAst = grandParent;
        }
        else {
            scopeAst = ast;
        }
        variableDesc.setScope(scopeAst);

    }

    /**
     * Checks whether the DetailAst of type {@link TokenTypes#IDENT} is
     * used as left-hand side value. An identifier is being used as a left-hand side
     * value if it is used as the left operand of an assignment or as the
     * operand of a stand-alone increment
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @return True if identAst is used as a left-hand side value otherwise false.
     */
    private static boolean isLeftHandSideValue(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return TokenUtil.isOfType(parent.getType(), INCREMENT_AND_DECREMENT_TOKENS)
                || parent.getType() == TokenTypes.ASSIGN
                && identAst != parent.getLastChild();
    }

    /**
     * Maintains information about the local variable.
     */
    private static final class VariableDesc {

        /**
         * The name of the variable.
         */
        private final String name;

        /**
         * DetailAst of type {@link TokenTypes#VARIABLE_DEF} or
         * {@link TokenTypes#PATTERN_VARIABLE_DEF}.
         */
        private final DetailAST variableAst;

        /**
         * The scope of variable is determined by the DetailAst of type
         * {@link TokenTypes#SLIST} which is enclosing {@link VariableDesc#variableAst} or
         * by {@link TokenTypes#EXPR} in some cases.
         */
        private DetailAST scope;

        /**
         * Is the variable used again.
         */
        private boolean used;

        /**
         * Scope of pattern variable defined with the line number and column number.
         * Eg-
         * <pre>
         * {@code
         * if(o instanceof String s || randomMethod());}
         * </pre>
         * Here the scope of String s is from the point from where it starts and
         * ends at the point where it is no more accessible, something like 10:21 to 10:30.
         * This is the scope it has in the expression.
         */
        private int[] scopeOfPatternVariableIncludingLineAndColumn;

        /**
         * Create a new VariableDesc instance.
         *
         * @param name        name of the variable
         * @param variableAst DetailAst of type {@link TokenTypes#VARIABLE_DEF}
         */
        /* package */ VariableDesc(String name, DetailAST variableAst) {
            this.name = name;
            this.variableAst = variableAst;
        }

        /**
         * Get the name of variable.
         *
         * @return name of variable
         */
        public String getName() {
            return name;
        }

        /**
         * Get the associated {@link VariableDesc#variableAst}.
         *
         * @return {@link VariableDesc#variableAst}
         */
        public DetailAST getVariableAst() {
            return variableAst;
        }

        /**
         * Get the associated {@link VariableDesc#scope}.
         *
         * @return {@link VariableDesc#scope}.
         */
        public DetailAST getScope() {
            return scope;
        }

        /**
         * Get the scope of pattern variable including line and column.
         *
         * @return {@link VariableDesc#scopeOfPatternVariableIncludingLineAndColumn}
         */
        private int[] getScopeOfPatternVariableIncludingLineAndColumn() {
            return scopeOfPatternVariableIncludingLineAndColumn;
        }

        /**
         * Is the variable used again or not.
         *
         * @return true if variable is used again else false;
         */
        public boolean isUsed() {
            return used;
        }

        /**
         * Register the variable as used again.
         */
        public void registerAsUsed() {
            this.used = true;
        }

        /**
         * Set the scope of variable.
         *
         * @param scope {@link VariableDesc#scope}
         */
        public void setScope(DetailAST scope) {
            this.scope = scope;
        }

        /**
         * Set the scope of pattern variable including line and column.
         * No need to set the scopeColumnStart as it has no use.
         *
         * @param scopeLineStart starting line of scope
         * @param scopeLineEnd ending line of scope
         * @param scopeColumnEnd end column of scope
         */
        private void setScopeOfPatternVariableIncludingLineAndColumn(int scopeLineStart,
                int scopeLineEnd, int scopeColumnEnd) {
            scopeOfPatternVariableIncludingLineAndColumn = new int[] {
                scopeLineStart, scopeLineEnd, scopeColumnEnd,
            };
        }
    }

    /**
     * Index values for {@link VariableDesc#scopeOfPatternVariableIncludingLineAndColumn}.
     */
    private static class PatternVariableScope {

        /** Index number for start line. */
        public static final int LINE_START = 0;

        /** Index number for end line. */
        public static final int LINE_END = 1;

        /** Index number for end column. */
        public static final int COLUMN_END = 2;
    }
}
