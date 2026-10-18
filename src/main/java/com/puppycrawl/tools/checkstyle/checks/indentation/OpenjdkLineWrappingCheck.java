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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks continuation-line alignment in expressions according to the
 * <a href="https://checkstyle.org/styleguides/openjdk-java-style-v6/openjdk-styleguide.html#wrapping-lines">
 * OpenJDK Java Style Guide</a>.
 * </div>
 *
 * <p>
 * A continuation may use eight additional columns relative to the expression's first line,
 * the previous code line, or the start of the wrapped expression. It may also align with a
 * preceding argument or method call in a chain. Sibling alignment must stand out from the
 * indentation of the surrounding block. Tabs are expanded using {@code tabWidth}.
 * </p>
 *
 * <p>
 * This check complements {@code Indentation}; it does not check block indentation, declaration
 * wrapping, or closing delimiters. The guide's exception for logical argument grouping is
 * subjective, so this check validates columns without inferring semantic groups.
 * </p>
 *
 * @since 14.4.0
 */
@FileStatefulCheck
public class OpenjdkLineWrappingCheck extends AbstractCheck {

    /** Message key for a continuation with an incorrect column. */
    public static final String MSG_ERROR_MULTI = "indentation.error.multi";

    /** OpenJDK continuation indentation. */
    private static final int WRAPPING_OFFSET = 8;

    /** OpenJDK block indentation. */
    private static final int BLOCK_OFFSET = 4;

    /** Empty expression context at the start of each file. */
    private static final WrappingContext ROOT_CONTEXT = new WrappingContext(0, Set.of(), null);

    /** Offsets inherited by the expression currently being visited. */
    private WrappingContext context = ROOT_CONTEXT;

    /** Creates a new {@code OpenjdkLineWrappingCheck} instance. */
    public OpenjdkLineWrappingCheck() {
        // no code by default
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
            TokenTypes.EXPR, TokenTypes.ARRAY_INIT,
            TokenTypes.SLIST, TokenTypes.OBJBLOCK, TokenTypes.LITERAL_SWITCH,
            TokenTypes.METHOD_CALL, TokenTypes.CTOR_CALL, TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_NEW, TokenTypes.DOT, TokenTypes.TYPE_ARGUMENTS,
            TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.STAR, TokenTypes.DIV, TokenTypes.MOD,
            TokenTypes.LAND, TokenTypes.LOR, TokenTypes.BAND, TokenTypes.BOR, TokenTypes.BXOR,
            TokenTypes.EQUAL, TokenTypes.NOT_EQUAL, TokenTypes.LT, TokenTypes.LE,
            TokenTypes.GT, TokenTypes.GE, TokenTypes.SL, TokenTypes.SR, TokenTypes.BSR,
            TokenTypes.QUESTION, TokenTypes.ASSIGN, TokenTypes.PLUS_ASSIGN,
            TokenTypes.MINUS_ASSIGN, TokenTypes.STAR_ASSIGN, TokenTypes.DIV_ASSIGN,
            TokenTypes.MOD_ASSIGN, TokenTypes.SL_ASSIGN, TokenTypes.SR_ASSIGN,
            TokenTypes.BSR_ASSIGN, TokenTypes.BAND_ASSIGN, TokenTypes.BOR_ASSIGN,
            TokenTypes.BXOR_ASSIGN, TokenTypes.UNARY_PLUS, TokenTypes.UNARY_MINUS,
            TokenTypes.INC, TokenTypes.DEC, TokenTypes.POST_INC, TokenTypes.POST_DEC,
            TokenTypes.LNOT, TokenTypes.BNOT, TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.TYPECAST, TokenTypes.INDEX_OP, TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.METHOD_REF, TokenTypes.LAMBDA,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        context = ROOT_CONTEXT;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.SLIST, TokenTypes.OBJBLOCK, TokenTypes.LITERAL_SWITCH ->
                context = new WrappingContext(0, Set.of(), context);
            case TokenTypes.EXPR -> {
                enterExpression(ast);
                checkParentheses(ast);
            }
            case TokenTypes.ARRAY_INIT -> {
                enterExpression(ast);
                checkArguments(ast, ast);
            }
            case TokenTypes.CTOR_CALL, TokenTypes.SUPER_CTOR_CALL -> {
                enterExpression(ast);
                checkArguments(NullUtil.notNull(ast.findFirstToken(TokenTypes.ELIST)), ast);
            }
            case TokenTypes.METHOD_CALL, TokenTypes.LITERAL_NEW -> {
                final DetailAST arguments = ast.findFirstToken(TokenTypes.ELIST);
                if (arguments != null) {
                    checkArguments(arguments, ast);
                }
            }
            case TokenTypes.TYPE_ARGUMENTS -> {
                if (!context.columns().isEmpty()) {
                    checkArguments(ast, ast);
                }
            }
            case TokenTypes.LAMBDA -> {
                if (ast.hasChildren()) {
                    enterExpression(ast);
                    checkOperator(ast);
                }
            }
            case TokenTypes.DOT -> {
                if (!context.columns().isEmpty()) {
                    checkDot(ast);
                }
            }
            default -> {
                if (ast.hasChildren() && !context.columns().isEmpty()) {
                    checkOperator(ast);
                }
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (TokenUtil.isOfType(ast, TokenTypes.EXPR, TokenTypes.ARRAY_INIT,
                TokenTypes.CTOR_CALL, TokenTypes.SUPER_CTOR_CALL,
                TokenTypes.SLIST, TokenTypes.OBJBLOCK, TokenTypes.LITERAL_SWITCH)
                || ast.getType() == TokenTypes.LAMBDA && ast.hasChildren()) {
            context = NullUtil.notNull(context.parent());
        }
    }

    /**
     * Inherits expression offsets without searching ancestors for every continuation.
     *
     * @param expression expression or initializer being entered
     */
    private void enterExpression(DetailAST expression) {
        DetailAST start = expression;
        if (start.getType() == TokenTypes.EXPR) {
            start = start.getFirstChild();
        }
        if (start.getType() == TokenTypes.ASSIGN) {
            start = start.getLastChild();
        }
        start = firstToken(start);
        final WrappingContext parent = context;
        final int blockColumn;
        if (parent.columns().isEmpty()) {
            blockColumn = indent(start.getLineNo()) + BLOCK_OFFSET;
        }
        else {
            blockColumn = parent.blockColumn();
        }
        final Set<Integer> columns = new TreeSet<>(parent.columns());
        columns.add(indent(start.getLineNo()) + WRAPPING_OFFSET);
        columns.add(column(start) + WRAPPING_OFFSET);
        context = new WrappingContext(blockColumn, columns, context);
    }

    /**
     * Checks arguments, array elements, or type arguments against their preceding sibling.
     *
     * @param list node containing the arguments
     * @param owner opening parenthesis or brace
     */
    private void checkArguments(DetailAST list, DetailAST owner) {
        int previousLine = owner.getLineNo();
        DetailAST sibling = null;
        for (DetailAST argument = list.getFirstChild(); argument != null;
                argument = argument.getNextSibling()) {
            if (TokenUtil.isOfType(argument, TokenTypes.EXPR, TokenTypes.ARRAY_INIT,
                    TokenTypes.TYPE_ARGUMENT, TokenTypes.LAMBDA)) {
                final Set<Integer> columns = new TreeSet<>(context.columns());
                if (sibling != null) {
                    addSiblingColumn(sibling, columns);
                }
                checkContinuation(firstToken(argument), previousLine, columns);
                previousLine = lastLine(argument);
                sibling = firstToken(argument);
            }
        }
    }

    /**
     * Checks a chaining dot against the previous call's dot.
     *
     * @param dot member access expression
     */
    private void checkDot(DetailAST dot) {
        final DetailAST target = dot.getFirstChild();
        final Set<Integer> columns = new TreeSet<>(context.columns());
        if (target.getType() == TokenTypes.METHOD_CALL) {
            final DetailAST previousDot = target.getFirstChild();
            if (previousDot.getType() == TokenTypes.DOT) {
                columns.add(column(previousDot));
            }
        }
        checkContinuation(dot, lastLine(target), columns);
        checkContinuation(firstToken(dot.getLastChild()), dot.getLineNo(), context.columns());
    }

    /**
     * Checks an operator and its operands using the left operand as the alignment anchor.
     *
     * @param operator expression operator
     */
    private void checkOperator(DetailAST operator) {
        final DetailAST left = operator.getFirstChild();
        final DetailAST start = firstToken(left);
        final Set<Integer> columns = new TreeSet<>(context.columns());
        columns.add(column(start) + WRAPPING_OFFSET);
        addSiblingColumn(start, columns);
        checkContinuation(operator, lastLine(left), columns);
        checkContinuation(start, operator.getLineNo(), columns);
        int previousLine = operator.getLineNo();
        for (DetailAST operand = left.getNextSibling(); operand != null;
                operand = operand.getNextSibling()) {
            checkContinuation(firstToken(operand), previousLine, columns);
            previousLine = lastLine(operand);
        }
    }

    /**
     * Checks the expression immediately following an opening parenthesis.
     *
     * @param expression expression which may contain parentheses
     */
    private void checkParentheses(DetailAST expression) {
        for (DetailAST child = expression.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.LPAREN) {
                checkContinuation(firstToken(child.getNextSibling()), child.getLineNo(),
                        context.columns());
            }
        }
    }

    /**
     * Adds sibling alignment only when it cannot be confused with block indentation.
     *
     * @param sibling preceding argument or operand
     * @param columns valid columns
     */
    private void addSiblingColumn(DetailAST sibling, Set<Integer> columns) {
        final int siblingColumn = column(sibling);
        if (siblingColumn > context.blockColumn()) {
            columns.add(siblingColumn);
        }
    }

    /**
     * Checks a line-leading token against its owner's offsets and preceding syntax token.
     *
     * @param token continuation token
     * @param previousLine line containing the preceding syntax token
     * @param columns allowed expression and sibling columns
     */
    private void checkContinuation(DetailAST token, int previousLine, Set<Integer> columns) {
        if (token.getLineNo() > previousLine
                && column(token) == indent(token.getLineNo())
                && !TokenUtil.isOfType(token, TokenTypes.RPAREN, TokenTypes.RBRACK,
                        TokenTypes.RCURLY, TokenTypes.SLIST, TokenTypes.OBJBLOCK)) {
            final Set<Integer> expectedColumns = new TreeSet<>(columns);
            expectedColumns.add(indent(previousLine) + WRAPPING_OFFSET);
            final int actual = column(token);
            if (!expectedColumns.contains(actual)) {
                final String expected = expectedColumns.toString();
                log(token, MSG_ERROR_MULTI, token.getText(), actual,
                        expected.substring(1, expected.length() - 1));
            }
        }
    }

    /**
     * Finds the first syntax token by following the left edge, not the whole subtree.
     *
     * @param ast expression to inspect
     * @return earliest syntax token
     */
    private static DetailAST firstToken(DetailAST ast) {
        DetailAST result = ast;
        if (ast.getType() == TokenTypes.EXPR) {
            result = ast.getFirstChild();
        }
        final DetailAST child = result.getFirstChild();
        if (child != null) {
            final DetailAST candidate = firstToken(child);
            if (candidate.getLineNo() < result.getLineNo()
                    || candidate.getLineNo() == result.getLineNo()
                        && candidate.getColumnNo() < result.getColumnNo()) {
                result = candidate;
            }
        }
        return result;
    }

    /**
     * Finds an expression's last line along the right edge, including postfix operators.
     *
     * @param ast expression to inspect
     * @return last source line
     */
    private static int lastLine(DetailAST ast) {
        int result = ast.getLineNo();
        for (DetailAST child = ast.getLastChild(); child != null; child = child.getLastChild()) {
            result = Math.max(result, child.getLineNo());
        }
        return result;
    }

    /**
     * Gets a token's column after expanding tabs.
     *
     * @param token token to inspect
     * @return expanded column
     */
    private int column(DetailAST token) {
        return CommonUtil.lengthExpandedTabs(getLine(token.getLineNo() - 1),
                token.getColumnNo(), getTabWidth());
    }

    /**
     * Gets a code line's indentation after expanding tabs.
     *
     * @param lineNo one-based line number
     * @return expanded indentation
     */
    private int indent(int lineNo) {
        final String line = getLine(lineNo - 1);
        return CommonUtil.lengthExpandedTabs(line, CommonUtil.indexOfNonWhitespace(line),
                getTabWidth());
    }

    /**
     * Expression offsets and the column which would be confused with a block body.
     *
     * @param blockColumn surrounding block's indentation plus one block offset
     * @param columns inherited expression offsets
     * @param parent context restored when leaving this expression or block
     */
    private record WrappingContext(int blockColumn, Set<Integer> columns,
                                  @Nullable WrappingContext parent) {
    }

}
