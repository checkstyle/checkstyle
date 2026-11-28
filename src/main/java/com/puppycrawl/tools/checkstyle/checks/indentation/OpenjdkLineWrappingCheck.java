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

import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
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
 * @since 14.5.0
 */
@FileStatefulCheck
public class OpenjdkLineWrappingCheck extends AbstractCheck {

    /** Message key for a continuation with an incorrect column. */
    public static final String MSG_ERROR_MULTI = "indentation.error.multi";

    /** OpenJDK continuation indentation. */
    private static final int WRAPPING_OFFSET = 8;

    /** OpenJDK block indentation. */
    private static final int BLOCK_OFFSET = 4;

    /** Binary and ternary operators whose left operand can anchor a continuation. */
    private static final Set<Integer> OPERATORS = Set.of(
        TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.STAR, TokenTypes.DIV, TokenTypes.MOD,
        TokenTypes.LAND, TokenTypes.LOR, TokenTypes.BAND, TokenTypes.BOR, TokenTypes.BXOR,
        TokenTypes.EQUAL, TokenTypes.NOT_EQUAL, TokenTypes.LT, TokenTypes.LE,
        TokenTypes.GT, TokenTypes.GE, TokenTypes.SL, TokenTypes.SR, TokenTypes.BSR,
        TokenTypes.QUESTION, TokenTypes.LITERAL_INSTANCEOF);

    /** Scope of a root expression, before any owner expression is entered. */
    private static final Scope ROOT_SCOPE = new Scope(null, null, null);

    /** Nested expressions already checked as part of their root expression. */
    private Set<DetailAST> checkedExpressions;

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
            TokenTypes.CTOR_CALL, TokenTypes.SUPER_CTOR_CALL,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        checkedExpressions = new HashSet<>();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!checkedExpressions.remove(ast)) {
            DetailAST root = ast;
            if (ast.getParent().getType() == TokenTypes.LAMBDA) {
                // lambda outside any expression, such as a variable initializer
                root = ast.getParent();
            }
            final NavigableMap<Integer, Site> sites = new TreeMap<>();
            collectSites(root, ROOT_SCOPE, sites);
            checkedExpressions.remove(ast);
            checkSites(root, sites);
        }
    }

    /**
     * Walks a root expression once, recording the first token of each line as a wrapping site
     * together with the expressions, operands and argument that own it.
     *
     * @param ast node to inspect
     * @param outer scope of the parent node
     * @param sites leftmost wrapping site by line number
     */
    private void collectSites(DetailAST ast, Scope outer, NavigableMap<Integer, Site> sites) {
        Scope scope = outer;
        if (TokenUtil.isOfType(ast, TokenTypes.EXPR, TokenTypes.ARRAY_INIT, TokenTypes.LAMBDA,
                TokenTypes.CTOR_CALL, TokenTypes.SUPER_CTOR_CALL)) {
            scope = new Scope(new Anchor(expressionStart(ast), outer.expressions()), null,
                    argumentOrNull(ast));
        }
        else if (OPERATORS.contains(ast.getType())) {
            scope = new Scope(outer.expressions(),
                    new Anchor(firstToken(ast.getFirstChild()), outer.operands()),
                    outer.argument());
        }

        if (!TokenUtil.isOfType(ast, TokenTypes.EXPR, TokenTypes.ELIST,
                TokenTypes.SLIST, TokenTypes.OBJBLOCK)) {
            final Site current = sites.get(ast.getLineNo());
            if (current == null || ast.getColumnNo() < current.token().getColumnNo()) {
                sites.put(ast.getLineNo(), new Site(ast, scope));
            }
        }
        if (!TokenUtil.isOfType(ast, TokenTypes.SLIST, TokenTypes.OBJBLOCK,
                TokenTypes.LITERAL_SWITCH)) {
            for (DetailAST child = ast.getFirstChild(); child != null;
                    child = child.getNextSibling()) {
                if (TokenUtil.isOfType(child, TokenTypes.EXPR, TokenTypes.ARRAY_INIT)) {
                    checkedExpressions.add(child);
                }
                collectSites(child, scope, sites);
            }
        }
    }

    /**
     * Validates every wrapping site after the first line of a root expression.
     *
     * @param root root expression
     * @param sites leftmost wrapping site by line number
     */
    private void checkSites(DetailAST root, NavigableMap<Integer, Site> sites) {
        final int blockColumn = indent(firstToken(root).getLineNo()) + BLOCK_OFFSET;
        final Iterator<Site> iterator = sites.values().iterator();
        int previousLine = iterator.next().token().getLineNo();
        while (iterator.hasNext()) {
            final Site site = iterator.next();
            final DetailAST token = site.token();
            if (column(token) == indent(token.getLineNo())
                    && !TokenUtil.isOfType(token, TokenTypes.RPAREN, TokenTypes.RBRACK,
                            TokenTypes.RCURLY, TokenTypes.TEXT_BLOCK_LITERAL_END)) {
                final Set<Integer> expected = validColumns(site, previousLine, blockColumn);
                final int actual = column(token);
                if (!expected.contains(actual)) {
                    final String expectedColumns = expected.toString();
                    log(token, MSG_ERROR_MULTI, token.getText(), actual,
                            expectedColumns.substring(1, expectedColumns.length() - 1));
                }
            }
            previousLine = token.getLineNo();
        }
    }

    /**
     * Builds the columns allowed by the four wrapping variants of the style guide.
     *
     * @param site wrapping site
     * @param previousLine preceding code line of the root expression
     * @param blockColumn indentation of a block body, which sibling alignment must exceed
     * @return valid columns
     */
    private Set<Integer> validColumns(Site site, int previousLine, int blockColumn) {
        final DetailAST token = site.token();
        final Set<Integer> columns = new TreeSet<>();
        columns.add(indent(previousLine) + WRAPPING_OFFSET);
        for (Anchor owner = site.scope().expressions(); owner != null; owner = owner.next()) {
            final DetailAST start = owner.token();
            if (start.getLineNo() < token.getLineNo()) {
                columns.add(indent(start.getLineNo()) + WRAPPING_OFFSET);
                columns.add(column(start) + WRAPPING_OFFSET);
            }
        }
        for (Anchor operand = site.scope().operands(); operand != null;
                operand = operand.next()) {
            final DetailAST start = operand.token();
            if (start.getLineNo() < token.getLineNo()) {
                columns.add(column(start) + WRAPPING_OFFSET);
                addAlignment(start, blockColumn, columns);
            }
        }
        final DetailAST argument = site.scope().argument();
        if (argument != null && argument.getPreviousSibling() != null) {
            addAlignment(firstToken(argument.getPreviousSibling().getPreviousSibling()),
                    blockColumn, columns);
        }
        if (token.getType() == TokenTypes.DOT
                && token.getFirstChild().getType() == TokenTypes.METHOD_CALL) {
            final DetailAST previousDot = token.getFirstChild().getFirstChild();
            if (previousDot.getType() == TokenTypes.DOT) {
                columns.add(column(previousDot));
            }
        }
        return columns;
    }

    /**
     * Adds alignment with a sibling expression when it cannot be confused with a block body.
     *
     * @param sibling first token of the sibling expression
     * @param blockColumn indentation of a block body
     * @param columns valid columns
     */
    private void addAlignment(DetailAST sibling, int blockColumn, Set<Integer> columns) {
        final int siblingColumn = column(sibling);
        if (siblingColumn > blockColumn) {
            columns.add(siblingColumn);
        }
    }

    /**
     * Finds the start of the wrapped expression, using the right-hand side of an assignment.
     *
     * @param expression expression, initializer, lambda or constructor call
     * @return first token of the wrapped expression
     */
    private static DetailAST expressionStart(DetailAST expression) {
        DetailAST start = expression;
        if (start.getType() == TokenTypes.EXPR) {
            start = start.getFirstChild();
        }
        if (start.getType() == TokenTypes.ASSIGN) {
            start = start.getLastChild();
        }
        return firstToken(start);
    }

    /**
     * Returns the node if it is an argument or array element, otherwise {@code null}.
     *
     * @param ast expression, initializer or lambda
     * @return the node when its parent is an argument list or initializer
     */
    @Nullable
    private static DetailAST argumentOrNull(DetailAST ast) {
        DetailAST result = null;
        if (TokenUtil.isOfType(ast.getParent(), TokenTypes.ELIST, TokenTypes.ARRAY_INIT)) {
            result = ast;
        }
        return result;
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
            if (CheckUtil.isBeforeInSource(candidate, result)) {
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

    /**
     * Persistent list of tokens which anchor continuation columns.
     *
     * @param token first token of an owner expression or left operand
     * @param next anchor of the enclosing node
     */
    private record Anchor(DetailAST token, @Nullable Anchor next) {
    }

    /**
     * Nodes which own a wrapping site.
     *
     * @param expressions enclosing expressions, nearest first
     * @param operands left operands of operators within the nearest expression, nearest first
     * @param argument nearest argument or array element, if the nearest expression is one
     */
    private record Scope(@Nullable Anchor expressions, @Nullable Anchor operands,
                         @Nullable DetailAST argument) {
    }

    /**
     * The first token on a line of a root expression and the nodes owning it.
     *
     * @param token first token on the line
     * @param scope nodes owning the token
     */
    private record Site(DetailAST token, Scope scope) {
    }

}
