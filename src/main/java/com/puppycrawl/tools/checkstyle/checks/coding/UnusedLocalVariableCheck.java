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
import java.util.Deque;

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
 * public void method(int a) {
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
     * An array of increment tokens.
     */
    private static final int[] INCREMENT_AND_DECREMENT_KINDS = {
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.INC,
            TokenTypes.DEC,
    };

    /**
     * Index values for {@link VariableDesc#scopeOfPatternVariableIncludingLineAndColumn}
     */
    private static class PatternVariableScope {
        static final int LINE_START = 0;
        static final int COLUMN_START = 1;
        static final int LINE_END = 2;
        static final int COLUMN_END = 3;
    }

    /**
     * Keeps VariableDesc objects inside stack of declared variables.
     */
    private Deque<VariableDesc> variables;

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.DOT,
                TokenTypes.VARIABLE_DEF,
                TokenTypes.IDENT,
                TokenTypes.SLIST,
                TokenTypes.LITERAL_INSTANCEOF,
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
                    desc.setScopeOfVariable(ast.getParent());
                    variables.push(desc);
                }
                break;
            case TokenTypes.IDENT:
                if (!TokenUtil.isOfType(ast.getParent().getType(),
                        TokenTypes.VARIABLE_DEF, TokenTypes.DOT, TokenTypes.LITERAL_NEW)) {
                    checkIdentifierAst(ast);
                }
                break;
            case TokenTypes.SLIST:
                break;
            case TokenTypes.LITERAL_INSTANCEOF:
                final DetailAST patternVariableDef = ast.findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF);
                if (patternVariableDef != null) {
                    final DetailAST identToken = patternVariableDef.findFirstToken(TokenTypes.IDENT);
                    final VariableDesc variableDesc
                            = new VariableDesc(identToken.getText(), patternVariableDef);
                    variableDesc.registerAsPatternVariable();
                    setScopeOfPatternVariable(ast, patternVariableDef, variableDesc);
                    variables.push(variableDesc);
                }
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.SLIST) {
            int size = variables.size();
            while (size > 0 && variables.peek().getScopeOfVariable() == ast) {
                final VariableDesc variableDesc = variables.pop();
                if (!variableDesc.isUsedAgain()) {
                    final DetailAST currAst = variableDesc.getVariableAst();
                    log(currAst, MSG_UNUSED_LOCAL_VARIABLE, variableDesc.name);
                }
                size--;
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
     * if present, otherwise null
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
                    && !isLeftHandSideValue(identAst)) {
                if (variableDesc.isPatternVariable()) {
                    if (isIdentUsedForPatternVariable(identAst, variableDesc)) {
                        variableDesc.registerAsUsedAgain();
                    }
                }
                else {
                    variableDesc.registerAsUsedAgain();
                }
                break;
            }
        }
    }

    /**
     * Checks if a given DetailAST of type {@link TokenTypes#IDENT}
     * is used for a pattern variable.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @param variableDesc information about the local variable
     * @return True if identAst is used for a pattern variable else false.
     */
    private boolean isIdentUsedForPatternVariable(DetailAST identAst, VariableDesc variableDesc) {
        boolean result = false;
        final int identLine = identAst.getLineNo();
        final int identColumn = identAst.getColumnNo();
        final int[] scopeOfPatternVariableIncludingLineAndColumn = variableDesc
                .getScopeOfPatternVariableIncludingLineAndColumn();
        if (variableDesc.isFullScopePatternVariable()) {
            if (identLine > scopeOfPatternVariableIncludingLineAndColumn[
                    PatternVariableScope.LINE_START]
                    || identColumn > scopeOfPatternVariableIncludingLineAndColumn[
                            PatternVariableScope.COLUMN_START]) {
                final DetailAST rcurly = variableDesc.getScopeOfVariable()
                        .getLastChild();
                if (identLine <= rcurly.getLineNo()) {
                    result = true;
                }
            }
        }
        else {
            if (isIdentPresentInTheScopeOfPatternVariable(identLine, identColumn,
                    scopeOfPatternVariableIncludingLineAndColumn)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Checks if an ident is present between
     * {@link VariableDesc#scopeOfPatternVariableIncludingLineAndColumn}
     *
     * @param identLine Line number of corresponding ident DetailAst
     * @param identColumn Column number of corresponding ident DetailAst
     * @param scopeOfPatternVariableIncludingLineAndColumn
     * {@link VariableDesc#scopeOfPatternVariableIncludingLineAndColumn}
     * @return True if ident is present in the scope of pattern variable else false;
     */
    private boolean isIdentPresentInTheScopeOfPatternVariable(int identLine, int identColumn,
                                                              int[] scopeOfPatternVariableIncludingLineAndColumn) {
        boolean result = false;
        if (scopeOfPatternVariableIncludingLineAndColumn[PatternVariableScope.LINE_START]
                == scopeOfPatternVariableIncludingLineAndColumn[PatternVariableScope.LINE_END]) {
            if (scopeOfPatternVariableIncludingLineAndColumn[
                    PatternVariableScope.LINE_START] == identLine
                    && identColumn >= scopeOfPatternVariableIncludingLineAndColumn[
                            PatternVariableScope.COLUMN_START]
                    && identColumn <= scopeOfPatternVariableIncludingLineAndColumn[
                            PatternVariableScope.COLUMN_END]) {
                result = true;
            }
        }
        else {
            if (identLine == scopeOfPatternVariableIncludingLineAndColumn[
                    PatternVariableScope.LINE_START]) {
                if (identColumn > scopeOfPatternVariableIncludingLineAndColumn[
                        PatternVariableScope.COLUMN_START]) {
                    result = true;
                }
            }
            else if (identLine < scopeOfPatternVariableIncludingLineAndColumn[
                    PatternVariableScope.LINE_END]) {
                result = true;
            }
            else {
                if (identColumn < scopeOfPatternVariableIncludingLineAndColumn[
                        PatternVariableScope.COLUMN_END]) {
                    result = true;
                }
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
        int scopeLineStart = patternVariable.getLineNo();
        int scopeLineEnd = scopeLineStart;
        int scopeColumnStart = patternVariable.getColumnNo();
        int scopeColumnEnd = scopeColumnStart;
        while (ancestorOfInstanceOfAst.getType() == TokenTypes.LAND) {
            if (ancestorOfInstanceOfAst.getLineNo() > patternVariable.getLineNo()
                    || (ancestorOfInstanceOfAst.getLineNo() == patternVariable.getLineNo()
                    && ancestorOfInstanceOfAst.getColumnNo() > patternVariable.getColumnNo())) {
                ancestorOfInstanceOfAst = ancestorOfInstanceOfAst.getParent();
                scopeLineEnd = ancestorOfInstanceOfAst.getLineNo();
                scopeColumnEnd = ancestorOfInstanceOfAst.getColumnNo();
            }
            else {
                ancestorOfInstanceOfAst = ancestorOfInstanceOfAst.getParent();
            }
        }

        variableDesc.setScopeOfPatternVariableIncludingLineAndColumn(scopeLineStart,
                scopeColumnStart, scopeLineEnd, scopeColumnEnd);

        setScopeOfPatternVariableExcludingLineAndColumn(variableDesc, ancestorOfInstanceOfAst);
    }

    /**
     * Set the scope of pattern variable excluding the information
     * about its line and column number.
     *
     * @param variableDesc information about the local variable
     * @param ancestorOfInstanceOfAst ancestor of DetailAst of type
     * {@link TokenTypes#LITERAL_INSTANCEOF}
     */
    private static void setScopeOfPatternVariableExcludingLineAndColumn(
            VariableDesc variableDesc, DetailAST ancestorOfInstanceOfAst) {

        if (ancestorOfInstanceOfAst.getType() == TokenTypes.EXPR
                && ancestorOfInstanceOfAst.getParent().getType() != TokenTypes.ELIST) {
            variableDesc.registerAsFullScopePatternVariable();
        }

        while (ancestorOfInstanceOfAst.getType() != TokenTypes.EXPR) {
            ancestorOfInstanceOfAst = ancestorOfInstanceOfAst.getParent();
        }
        DetailAST grandParent = ancestorOfInstanceOfAst.getParent();
        if (grandParent.getType() != TokenTypes.LITERAL_DO) {
            final DetailAST scopeAst;
            if (grandParent.getType() == TokenTypes.ELIST) {
                while (grandParent.getType() != TokenTypes.SLIST) {
                    grandParent = grandParent.getParent();
                }
                scopeAst = grandParent;
            }
            else {
                scopeAst = grandParent.findFirstToken(TokenTypes.SLIST);

            }
            variableDesc.setScopeOfVariable(scopeAst);
        }
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
        return TokenUtil.isOfType(parent.getType(), INCREMENT_AND_DECREMENT_KINDS)
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
         * DetailAst of type {@link TokenTypes#VARIABLE_DEF}.
         */
        private final DetailAST variableAst;

        /**
         * The scope of variable is determined by the DetailAst of type
         * {@link TokenTypes#SLIST} which is enclosing {@link VariableDesc#variableAst}.
         */
        private DetailAST scopeOfVariable;

        /**
         * Is the variable used again.
         */
        private boolean isUsedAgain;

        /**
         * Is the variable a pattern variable.
         */
        private boolean isPatternVariable;

        /**
         * Is the pattern variable having full scope i.e. it is even accessible
         * inside the if statement, etc.
         * Eg-
         * <pre>
         * {@code
         * if(o instanceof String s) {
         *     s.concat("abc");
         * }}
         * </pre>
         * Here s is accessible inside the if statement, having full scope.
         */
        private boolean isFullScopePatternVariable;

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
         * Get the associated {@link VariableDesc#scopeOfVariable}.
         *
         * @return {@link VariableDesc#scopeOfVariable}.
         */
        public DetailAST getScopeOfVariable() {
            return scopeOfVariable;
        }

        private int[] getScopeOfPatternVariableIncludingLineAndColumn() {
            return scopeOfPatternVariableIncludingLineAndColumn;
        }

        /**
         * Is the variable used again or not.
         *
         * @return true if variable is used again else false;
         */
        public boolean isUsedAgain() {
            return isUsedAgain;
        }

        /**
         * Is the variable a pattern variable or not.
         *
         * @return true if variable is a pattern variable else false;
         */
        public boolean isPatternVariable() {
            return isPatternVariable;
        }

        /**
         * Is the pattern
         *
         * @return
         */
        private boolean isFullScopePatternVariable() {
            return isFullScopePatternVariable;
        }

        /**
         * Register the variable as used again.
         */
        public void registerAsUsedAgain() {
            this.isUsedAgain = true;
        }

        /**
         * Register the variable as a pattern variable.
         */
        public void registerAsPatternVariable() {
            this.isPatternVariable = true;
        }

        /**
         * Register as a full scope pattern variable.
         */
        private void registerAsFullScopePatternVariable() {
            this.isFullScopePatternVariable = true;
        }

        /**
         * Set the scope of variable.
         *
         * @param scopeOfVariable {@link VariableDesc#scopeOfVariable}
         */
        public void setScopeOfVariable(DetailAST scopeOfVariable) {
            this.scopeOfVariable = scopeOfVariable;
        }

        /**
         * Set the scope of pattern variable including line and column.
         *
         * @param scopeLineStart starting line of scope
         * @param scopeColumnStart starting column of scope
         * @param scopeLineEnd ending line of scope
         * @param scopeColumnEnd end column of scope
         */
        private void setScopeOfPatternVariableIncludingLineAndColumn(int scopeLineStart,
                                                                     int scopeColumnStart, int scopeLineEnd, int scopeColumnEnd) {
            scopeOfPatternVariableIncludingLineAndColumn = new int[]{
                    scopeLineStart, scopeColumnStart, scopeLineEnd, scopeColumnEnd
            };
        }
    }
}
