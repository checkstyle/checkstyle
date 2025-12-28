///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for parents of blocks ('if', 'else', 'while', etc).
 *
 * <P>
 * The "block" handler classes use a common superclass BlockParentHandler,
 * employing the Template Method pattern.
 * </P>
 *
 * <UL>
 *   <LI>template method to get the lcurly</LI>
 *   <LI>template method to get the rcurly</LI>
 *   <LI>if curlies aren't present, then template method to get expressions
 *       is called</LI>
 *   <LI>now all the repetitious code which checks for BOL, if curlies are on
 *       same line, etc. can be collapsed into the superclass</LI>
 * </UL>
 *
 */
public class BlockParentHandler extends AbstractExpressionHandler {

    /**
     * Children checked by parent handlers.
     */
    private static final int[] CHECKED_CHILDREN = {
        TokenTypes.VARIABLE_DEF,
        TokenTypes.EXPR,
        TokenTypes.ANNOTATION,
        TokenTypes.OBJBLOCK,
        TokenTypes.LITERAL_BREAK,
        TokenTypes.LITERAL_RETURN,
        TokenTypes.LITERAL_THROW,
        TokenTypes.LITERAL_CONTINUE,
        TokenTypes.CTOR_CALL,
        TokenTypes.SUPER_CTOR_CALL,
    };

    /**
     * Construct an instance of this handler with the given indentation check,
     * name, abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param name          the name of the handler
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     * @noinspection WeakerAccess
     * @noinspectionreason WeakerAccess - we avoid 'protected' when possible
     */
    public BlockParentHandler(IndentationCheck indentCheck,
        String name, DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, name, ast, parent);
    }

    /**
     * Returns array of token types which should be checked among children.
     *
     * @return array of token types to check.
     */
    protected int[] getCheckedChildren() {
        return CHECKED_CHILDREN.clone();
    }

    /**
     * Get the top level expression being managed by this handler.
     *
     * @return the top level expression
     */
    protected DetailAST getTopLevelAst() {
        return getMainAst();
    }

    /**
     * Check the indent of the top level token.
     */
    protected void checkTopLevelToken() {
        final DetailAST topLevel = getTopLevelAst();

        if (topLevel != null
                && !getIndent().isAcceptable(expandedTabsColumnNo(topLevel))
                && isOnStartOfLine(topLevel)) {
            logError(topLevel, "", expandedTabsColumnNo(topLevel));
        }
    }

    /**
     * Determines if this block expression has curly braces.
     *
     * @return true if curly braces are present, false otherwise
     */
    private boolean hasCurlies() {
        return getLeftCurly() != null && getRightCurly() != null;
    }

    /**
     * Get the left curly brace portion of the expression we are handling.
     *
     * @return the left curly brace expression
     */
    protected DetailAST getLeftCurly() {
        return getMainAst().findFirstToken(TokenTypes.SLIST);
    }

    /**
     * Get the right curly brace portion of the expression we are handling.
     *
     * @return the right curly brace expression
     */
    protected DetailAST getRightCurly() {
        final DetailAST slist = getMainAst().findFirstToken(TokenTypes.SLIST);
        return slist.findFirstToken(TokenTypes.RCURLY);
    }

    /**
     * Check the indentation of the left curly brace.
     */
    private void checkLeftCurly() {
        // the lcurly can either be at the correct indentation, or nested
        // with a previous expression
        final DetailAST lcurly = getLeftCurly();
        final int lcurlyPos = expandedTabsColumnNo(lcurly);

        if (!curlyIndent().isAcceptable(lcurlyPos) && isOnStartOfLine(lcurly)) {
            logError(lcurly, "lcurly", lcurlyPos, curlyIndent());
        }
    }

    /**
     * Get the expected indentation level for the curly braces.
     *
     * @return the curly brace indentation level
     */
    protected IndentLevel curlyIndent() {
        final DetailAST lcurly = getLeftCurly();
        IndentLevel expIndentLevel = new IndentLevel(getIndent(), getBraceAdjustment());
        if (!isOnStartOfLine(lcurly) || checkIfCodeBlock()) {
            expIndentLevel = new IndentLevel(getIndent(), 0);
        }
        return expIndentLevel;
    }

    /**
     * Checks if lcurly is a Code block.
     *
     * @return true if lcurly is a code block
     */
    private boolean checkIfCodeBlock() {
        return getMainAst().getType() == TokenTypes.SLIST
                && getParent() instanceof BlockParentHandler
                && getParent().getParent() instanceof BlockParentHandler;
    }

    /**
     * Determines if child elements within the expression may be nested.
     *
     * @return false
     */
    protected boolean canChildrenBeNested() {
        return false;
    }

    /**
     * Check the indentation of the right curly brace.
     */
    private void checkRightCurly() {
        final DetailAST rcurly = getRightCurly();
        final int rcurlyPos = expandedTabsColumnNo(rcurly);

        if (!curlyIndent().isAcceptable(rcurlyPos)
                && isOnStartOfLine(rcurly)) {
            logError(rcurly, "rcurly", rcurlyPos, curlyIndent());
        }
    }

    /**
     * Get the child element that is not a list of statements.
     *
     * @return the non-list child element
     */
    protected DetailAST getNonListChild() {
        return getMainAst().findFirstToken(TokenTypes.RPAREN).getNextSibling();
    }

    /**
     * Check the indentation level of a child that is not a list of statements.
     */
    private void checkNonListChild() {
        final DetailAST nonList = getNonListChild();
        if (nonList != null) {
            final IndentLevel expected = new IndentLevel(getIndent(), getBasicOffset());
            checkExpressionSubtree(nonList, expected, false, false);

            final DetailAST nonListStartAst = getFirstAstNode(nonList);
            if (nonList != nonListStartAst) {
                checkExpressionSubtree(nonListStartAst, expected, false, false);
            }
        }
    }

    /**
     * Get the child element representing the list of statements.
     *
     * @return the statement list child
     */
    protected DetailAST getListChild() {
        return getMainAst().findFirstToken(TokenTypes.SLIST);
    }

    /**
     * Get the right parenthesis portion of the expression we are handling.
     *
     * @return the right parenthesis expression
     */
    private DetailAST getRightParen() {
        return getMainAst().findFirstToken(TokenTypes.RPAREN);
    }

    /**
     * Get the left parenthesis portion of the expression we are handling.
     *
     * @return the left parenthesis expression
     */
    private DetailAST getLeftParen() {
        return getMainAst().findFirstToken(TokenTypes.LPAREN);
    }

    @Override
    public void checkIndentation() {
        checkTopLevelToken();
        // separate to allow for eventual configuration
        checkLeftParen(getLeftParen());
        checkRightParen(getLeftParen(), getRightParen());
        if (hasCurlies()) {
            checkLeftCurly();
            checkRightCurly();
        }
        final DetailAST listChild = getListChild();
        if (listChild == null) {
            checkNonListChild();
        }
        else {
            // NOTE: switch statements usually don't have curlies
            if (!hasCurlies() || !TokenUtil.areOnSameLine(getLeftCurly(), getRightCurly())) {
                checkChildren(listChild,
                        getCheckedChildren(),
                        getChildrenExpectedIndent(),
                        true,
                        canChildrenBeNested());
                // Only check return statement line wrapping for method/constructor bodies
                // to avoid issues with other contexts
                if (getMainAst().getType() == TokenTypes.METHOD_DEF
                        || getMainAst().getType() == TokenTypes.CTOR_DEF) {
                    checkReturnStatementLineWrapping(listChild);
                }
            }
        }
    }

    /**
     * Gets indentation level expected for children.
     *
     * @return indentation level expected for children
     */
    protected IndentLevel getChildrenExpectedIndent() {
        IndentLevel indentLevel = new IndentLevel(getIndent(), getBasicOffset());
        // if we have multileveled expected level then we should
        // try to suggest single level to children using curlies'
        // levels.
        if (getIndent().isMultiLevel() && hasCurlies()) {
            if (isOnStartOfLine(getLeftCurly())) {
                indentLevel = new IndentLevel(expandedTabsColumnNo(getLeftCurly())
                        + getBasicOffset());
            }
            else if (isOnStartOfLine(getRightCurly())) {
                final IndentLevel level = new IndentLevel(curlyIndent(), getBasicOffset());
                indentLevel = IndentLevel.addAcceptable(level, level.getFirstIndentLevel()
                        + getLineWrappingIndent());
            }
        }
        if (hasCurlies() && isOnStartOfLine(getLeftCurly())) {
            indentLevel = IndentLevel.addAcceptable(indentLevel,
                    curlyIndent().getFirstIndentLevel() + getBasicOffset());
        }
        return indentLevel;
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        return getChildrenExpectedIndent();
    }

    /**
     * A shortcut for {@code IndentationCheck} property.
     *
     * @return value of lineWrappingIndentation property
     *         of {@code IndentationCheck}
     */
    private int getLineWrappingIndent() {
        return getIndentCheck().getLineWrappingIndentation();
    }

    /**
     * Checks line wrapping indentation for return statements that span multiple lines.
     * This ensures that lambda arrows (->) and commas in return statement continuations
     * are properly checked for indentation violations.
     *
     * @param listChild the statement list containing return statements
     */
    private void checkReturnStatementLineWrapping(DetailAST listChild) {
        if (listChild != null) {
            for (DetailAST child = listChild.getFirstChild();
                    child != null;
                    child = child.getNextSibling()) {
                if (child.getType() == TokenTypes.LITERAL_RETURN) {
                    checkReturnStatement(child);
                }
            }
        }
    }

    /**
     * Checks a single return statement for line wrapping indentation violations.
     *
     * @param returnStmt the return statement AST node
     */
    private void checkReturnStatement(DetailAST returnStmt) {
        final DetailAST returnExpr = returnStmt.getFirstChild();
        if (returnExpr == null
                || isInsideNestedStructure(returnStmt)
                || shouldSkipReturnExpression(returnExpr)) {
            return;
        }
        final DetailAST lastNode = getLastNode(returnExpr);
        if (isValidMultiLineReturn(returnStmt, returnExpr, lastNode)) {
            final int returnLineStart = getLineStart(returnStmt);
            // Use the semicolon after the return statement as the end node
            // This ensures all nodes in the return expression are included
            final DetailAST semicolon = returnStmt.getLastChild();
            try {
                if (semicolon != null && semicolon.getType() == TokenTypes.SEMI) {
                    // Pass semicolon.getNextSibling() to ensure semicolon is included
                    final DetailAST endNode = semicolon.getNextSibling();
                    // Start from returnExpr (not returnStmt) to avoid checking the 'return' keyword itself
                    // but include everything up to and including the semicolon
                    checkWrappingIndentation(returnExpr, endNode != null ? endNode : semicolon,
                            getLineWrappingIndent(), returnLineStart, true);
                } else {
                    // Fallback: use lastNode.getNextSibling()
                    final DetailAST endNode = lastNode.getNextSibling();
                    checkWrappingIndentation(returnExpr, endNode != null ? endNode : lastNode,
                            getLineWrappingIndent(), returnLineStart, true);
                }
            } catch (NullPointerException | IllegalStateException exception) {
                // Skip if there's an issue with traversal - this can happen
                // when lastNode is not properly reachable from returnExpr
                // through the AST traversal used by LineWrappingHandler
                // This is a defensive measure to prevent crashes in edge cases
            }
        }
    }

    /**
     * Checks if return expression should be skipped.
     *
     * @param returnExpr the return expression AST node
     * @return true if the expression should be skipped
     */
    private static boolean shouldSkipReturnExpression(DetailAST returnExpr) {
        final int type = returnExpr.getType();
        return type == TokenTypes.LITERAL_SWITCH
                || type == TokenTypes.ANNOTATION_DEF;
    }

    /**
     * Checks if return statement is a valid multi-line return that should be checked.
     *
     * @param returnStmt the return statement AST node
     * @param returnExpr the return expression AST node
     * @param lastNode the last node in the return expression
     * @return true if this is a valid multi-line return to check
     */
    private static boolean isValidMultiLineReturn(DetailAST returnStmt,
                                                  DetailAST returnExpr,
                                                  DetailAST lastNode) {
        return lastNode != null
                && lastNode != returnExpr
                && !TokenUtil.areOnSameLine(returnStmt, lastNode)
                && returnStmt.getLineNo() < lastNode.getLineNo()
                && isProperDescendant(returnExpr, lastNode);
    }

    /**
     * Checks if a return statement is inside a nested structure that has its own
     * indentation handler. This prevents conflicts with handlers for switch statements,
     * annotations, etc.
     *
     * @param returnStmt the return statement AST node
     * @return true if the return statement is inside a nested structure
     */
    private static boolean isInsideNestedStructure(DetailAST returnStmt) {
        DetailAST parent = returnStmt.getParent();
        while (parent != null) {
            final int type = parent.getType();
            if (isNestedStructureType(type)) {
                return true;
            }
            if (type == TokenTypes.SLIST) {
                if (isInsideNestedMethod(parent)) {
                    return true;
                }
            }
            parent = parent.getParent();
        }
        return false;
    }

    /**
     * Checks if a type represents a nested structure with its own handler.
     *
     * @param type the token type to check
     * @return true if the type is a nested structure
     */
    private static boolean isNestedStructureType(int type) {
        return type == TokenTypes.LITERAL_SWITCH
                || type == TokenTypes.LITERAL_CASE
                || type == TokenTypes.LITERAL_DEFAULT
                || type == TokenTypes.ANNOTATION_DEF
                || type == TokenTypes.ENUM_DEF
                || type == TokenTypes.INTERFACE_DEF
                || type == TokenTypes.CLASS_DEF;
    }

    /**
     * Checks if a statement list is inside a method that's within a nested structure.
     *
     * @param slist the statement list AST node
     * @return true if the statement list is inside a nested method
     */
    private static boolean isInsideNestedMethod(DetailAST slist) {
        final DetailAST slistParent = slist.getParent();
        if (slistParent == null) {
            return false;
        }
        final int slistParentType = slistParent.getType();
        if (slistParentType != TokenTypes.METHOD_DEF
                && slistParentType != TokenTypes.CTOR_DEF) {
            return false;
        }
        DetailAST methodParent = slistParent.getParent();
        while (methodParent != null) {
            final int methodParentType = methodParent.getType();
            if (isNestedStructureType(methodParentType)) {
                return true;
            }
            if (methodParentType == TokenTypes.PACKAGE_DEF
                    || methodParentType == TokenTypes.IMPORT
                    || methodParentType == TokenTypes.STATIC_IMPORT) {
                break;
            }
            methodParent = methodParent.getParent();
        }
        return false;
    }

    /**
     * Checks if target node is a proper descendant of source node.
     * This ensures safe traversal in line wrapping checks.
     *
     * @param source the source node
     * @param target the target node to check
     * @return true if target is a proper descendant of source
     */
    private static boolean isProperDescendant(DetailAST source, DetailAST target) {
        boolean result = false;
        if (source != null && target != null && source != target) {
            // Check if target is a descendant of source
            DetailAST current = target.getParent();
            while (current != null) {
                if (current == source) {
                    result = true;
                    break;
                }
                current = current.getParent();
            }
        }
        return result;
    }

    /**
     * Gets the last node in the subtree, traversing to the deepest rightmost node.
     *
     * @param ast the root of the subtree
     * @return the last node in the subtree
     */
    private static DetailAST getLastNode(DetailAST ast) {
        DetailAST last = ast;
        DetailAST child = ast.getLastChild();
        while (child != null) {
            last = child;
            child = child.getLastChild();
        }
        return last;
    }

}
