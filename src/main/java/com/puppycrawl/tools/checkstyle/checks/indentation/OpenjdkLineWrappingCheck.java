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

import java.util.Iterator;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
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
@StatelessCheck
public class OpenjdkLineWrappingCheck extends AbstractCheck {

    /** Message key for a continuation with an incorrect column. */
    public static final String MSG_ERROR_MULTI = "indentation.error.multi";

    /** OpenJDK continuation indentation. */
    private static final int WRAPPING_OFFSET = 8;

    /** OpenJDK block indentation. */
    private static final int BLOCK_OFFSET = 4;

    /** Operators whose operands can be aligned as sibling expressions. */
    private static final Set<Integer> SIBLING_OPERATORS = Set.of(
        TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.STAR, TokenTypes.DIV, TokenTypes.MOD,
        TokenTypes.LAND, TokenTypes.LOR, TokenTypes.BAND, TokenTypes.BOR, TokenTypes.BXOR,
        TokenTypes.EQUAL, TokenTypes.NOT_EQUAL, TokenTypes.LT, TokenTypes.LE,
        TokenTypes.GT, TokenTypes.GE, TokenTypes.SL, TokenTypes.SR, TokenTypes.BSR,
        TokenTypes.QUESTION);

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
        return new int[] {TokenTypes.EXPR};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isRootExpression(ast)) {
            checkExpression(ast);
        }
    }

    /**
     * Checks an expression independently of expressions inside nested blocks.
     *
     * @param expression expression to check
     */
    private void checkExpression(DetailAST expression) {
        final NavigableMap<Integer, DetailAST> lines = new TreeMap<>();
        collectTokens(expression, lines);
        final Iterator<DetailAST> iterator = lines.values().iterator();
        DetailAST previous = iterator.next();
        while (iterator.hasNext()) {
            final DetailAST token = iterator.next();
            if (column(token) == indent(token.getLineNo())
                    && !TokenUtil.isOfType(token, TokenTypes.RPAREN, TokenTypes.RBRACK,
                            TokenTypes.TEXT_BLOCK_LITERAL_END)) {
                final Set<Integer> expected = expectedColumns(token, previous, expression);
                final int actual = column(token);
                if (!expected.contains(actual)) {
                    final String expectedColumns = expected.toString();
                    log(token, MSG_ERROR_MULTI, token.getText(), actual,
                            expectedColumns.substring(1, expectedColumns.length() - 1));
                }
            }
            previous = token;
        }
    }

    /**
     * Finds the valid columns for a continuation using its expression ancestors.
     *
     * @param token continuation token
     * @param previous first token of the preceding code line
     * @param root complete expression
     * @return valid columns
     */
    private Set<Integer> expectedColumns(DetailAST token, DetailAST previous, DetailAST root) {
        final Set<Integer> result = new TreeSet<>();
        result.add(indent(previous.getLineNo()) + WRAPPING_OFFSET);
        boolean insideExpression = true;
        for (DetailAST node = token; !node.equals(root.getParent()); node = node.getParent()) {
            if (node.getType() == TokenTypes.EXPR) {
                addExpressionColumns(node, token, root, insideExpression, result);
                insideExpression = false;
            }
            else if (insideExpression && token.getType() == TokenTypes.DOT
                    && node.getType() == TokenTypes.DOT) {
                addChainColumn(node, result);
            }
            else if (insideExpression && SIBLING_OPERATORS.contains(node.getType())) {
                addOperandColumns(node, token, root, result);
            }
        }
        return result;
    }

    /**
     * Adds offsets from an expression's start and preceding argument alignments.
     *
     * @param node expression ancestor
     * @param token continuation token
     * @param root complete expression
     * @param allowSiblings whether this is the token's nearest expression
     * @param columns valid columns
     */
    private void addExpressionColumns(DetailAST node, DetailAST token, DetailAST root,
                                      boolean allowSiblings, Set<Integer> columns) {
        DetailAST expression = node.getFirstChild();
        if (expression.getType() == TokenTypes.ASSIGN) {
            expression = expression.getLastChild();
        }
        final DetailAST start = firstToken(expression);
        if (start.getLineNo() < token.getLineNo()) {
            columns.add(indent(start.getLineNo()) + WRAPPING_OFFSET);
            columns.add(column(start) + WRAPPING_OFFSET);
        }
        if (allowSiblings && node.getParent().getType() == TokenTypes.ELIST) {
            addSiblingColumns(node, root, columns);
        }
    }

    /**
     * Adds alignment with a preceding method call for a leading chaining dot.
     *
     * @param dot member access expression
     * @param columns valid columns
     */
    private void addChainColumn(DetailAST dot, Set<Integer> columns) {
        if (dot.getFirstChild().getType() == TokenTypes.METHOD_CALL) {
            final DetailAST target = dot.getFirstChild().getFirstChild();
            if (target.getType() == TokenTypes.DOT) {
                columns.add(column(target));
            }
        }
    }

    /**
     * Adds offsets and alignment from the preceding operand of an operator expression.
     *
     * @param operator expression operator
     * @param token continuation token
     * @param root complete expression
     * @param columns valid columns
     */
    private void addOperandColumns(DetailAST operator, DetailAST token,
                                  DetailAST root, Set<Integer> columns) {
        final DetailAST start = firstToken(operator.getFirstChild());
        if (start.getLineNo() < token.getLineNo()) {
            columns.add(column(start) + WRAPPING_OFFSET);
            if (column(start) > indent(firstToken(root).getLineNo()) + BLOCK_OFFSET) {
                columns.add(column(start));
            }
        }
    }

    /**
     * Adds the alignment of preceding arguments when it stands out from the block.
     *
     * @param argument current argument
     * @param root complete expression
     * @param columns valid columns
     */
    private void addSiblingColumns(DetailAST argument, DetailAST root, Set<Integer> columns) {
        final int blockColumn = indent(firstToken(root).getLineNo()) + BLOCK_OFFSET;
        for (DetailAST sibling = argument.getPreviousSibling(); sibling != null;
                sibling = sibling.getPreviousSibling()) {
            if (sibling.getType() == TokenTypes.EXPR) {
                final int siblingColumn = column(firstToken(sibling));
                if (siblingColumn > blockColumn) {
                    columns.add(siblingColumn);
                }
            }
        }
    }

    /**
     * Determines whether this expression belongs to an independent statement or block.
     *
     * @param expression expression to inspect
     * @return true for an independent expression
     */
    private static boolean isRootExpression(DetailAST expression) {
        boolean result = true;
        for (DetailAST parent = expression.getParent();
                parent != null && result
                    && !TokenUtil.isOfType(parent, TokenTypes.SLIST, TokenTypes.OBJBLOCK,
                            TokenTypes.ARRAY_INIT, TokenTypes.LITERAL_SWITCH);
                parent = parent.getParent()) {
            if (parent.getType() == TokenTypes.EXPR) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Collects the leftmost syntax token on each line, excluding nested block bodies.
     *
     * @param ast subtree to inspect
     * @param lines leftmost tokens by line number
     */
    private static void collectTokens(DetailAST ast, NavigableMap<Integer, DetailAST> lines) {
        if (!TokenUtil.isOfType(ast, TokenTypes.EXPR, TokenTypes.ELIST,
                TokenTypes.SLIST, TokenTypes.OBJBLOCK, TokenTypes.ARRAY_INIT)) {
            final DetailAST current = lines.get(ast.getLineNo());
            if (current == null || ast.getColumnNo() < current.getColumnNo()) {
                lines.put(ast.getLineNo(), ast);
            }
        }
        if (!TokenUtil.isOfType(ast, TokenTypes.SLIST, TokenTypes.OBJBLOCK,
                TokenTypes.ARRAY_INIT, TokenTypes.LITERAL_SWITCH)) {
            for (DetailAST child = ast.getFirstChild(); child != null;
                    child = child.getNextSibling()) {
                collectTokens(child, lines);
            }
        }
    }

    /**
     * Finds the earliest token in a subtree in source order.
     *
     * @param ast subtree to inspect
     * @return earliest token
     */
    private static DetailAST firstToken(DetailAST ast) {
        DetailAST result = ast;
        for (DetailAST child = ast.getFirstChild(); child != null; child = child.getNextSibling()) {
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

}
