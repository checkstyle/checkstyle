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

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for fall-through in {@code switch} statements.
 * Finds locations where a {@code case} <b>contains</b> Java code but lacks a
 * {@code break}, {@code return}, {@code yield}, {@code throw} or {@code continue} statement.
 * </div>
 *
 * <p>
 * The check honors special comments to suppress the warning.
 * By default, the texts
 * "fallthru", "fall thru", "fall-thru",
 * "fallthrough", "fall through", "fall-through"
 * "fallsthrough", "falls through", "falls-through" (case-sensitive).
 * The comment containing these words must be all on one line,
 * and must be on the last non-empty line before the {@code case} triggering
 * the warning or on the same line before the {@code case}(ugly, but possible).
 * Any other comment may follow on the same line.
 * </p>
 *
 * <p>
 * Note: The check assumes that there is no unreachable code in the {@code case}.
 * </p>
 *
 * <p>
 * Note: The check handles {@code while (true)}, {@code do-while (true)},
 * and {@code for (;;)} as infinite loops. It will not examine variables or
 * complex expressions to determine if a loop is infinite.
 * </p>
 *
 * @since 3.4
 */
@StatelessCheck
public class FallThroughCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_FALL_THROUGH = "fall.through";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_FALL_THROUGH_LAST = "fall.through.last";

    /** Control whether the last case group must be checked. */
    private boolean checkLastCaseGroup;

    /**
     * Define the RegExp to match the relief comment that suppresses
     * the warning about a fall through.
     */
    private Pattern reliefPattern = Pattern.compile("falls?[ -]?thr(u|ough)");

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.CASE_GROUP};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    /**
     * Setter to define the RegExp to match the relief comment that suppresses
     * the warning about a fall through.
     *
     * @param pattern
     *            The regular expression pattern.
     * @since 4.0
     */
    public void setReliefPattern(Pattern pattern) {
        reliefPattern = pattern;
    }

    /**
     * Setter to control whether the last case group must be checked.
     *
     * @param value new value of the property.
     * @since 4.0
     */
    public void setCheckLastCaseGroup(boolean value) {
        checkLastCaseGroup = value;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST nextGroup = ast.getNextSibling();
        final boolean isLastGroup = nextGroup.getType() != TokenTypes.CASE_GROUP;
        if (!isLastGroup || checkLastCaseGroup) {
            final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);

            if (slist != null && !CheckUtil.isTerminated(slist) && !hasFallThroughComment(ast)
                    && !isTerminatedByInfiniteLoop(slist)) {
                if (isLastGroup) {
                    log(ast, MSG_FALL_THROUGH_LAST);
                }
                else {
                    log(ast, MSG_FALL_THROUGH);
                }
            }
        }
    }

    /**
     * Determines if the fall through case between {@code currentCase} and
     * {@code nextCase} is relieved by an appropriate comment.
     *
     * <p>Handles</p>
     * <pre>
     * case 1:
     * /&#42; FALLTHRU &#42;/ case 2:
     *
     * switch(i) {
     * default:
     * /&#42; FALLTHRU &#42;/}
     *
     * case 1:
     * // FALLTHRU
     * case 2:
     *
     * switch(i) {
     * default:
     * // FALLTHRU
     * </pre>
     *
     * @param currentCase AST of the case that falls through to the next case.
     * @return True if a relief comment was found
     */
    private boolean hasFallThroughComment(DetailAST currentCase) {
        final DetailAST nextSibling = currentCase.getNextSibling();
        final DetailAST ast;
        if (nextSibling.getType() == TokenTypes.CASE_GROUP) {
            ast = nextSibling.getFirstChild();
        }
        else {
            ast = currentCase;
        }
        return hasReliefComment(ast);
    }

    /**
     * Check if there is any fall through comment.
     *
     * @param ast ast to check
     * @return true if relief comment found
     */
    private boolean hasReliefComment(DetailAST ast) {
        final DetailAST nonCommentAst = CheckUtil.getNextNonCommentAst(ast);
        boolean result = false;
        if (nonCommentAst != null) {
            final int prevLineNumber = nonCommentAst.getPreviousSibling().getLineNo();
            result = Stream.iterate(nonCommentAst.getPreviousSibling(),
                            Objects::nonNull,
                            DetailAST::getPreviousSibling)
                    .takeWhile(sibling -> sibling.getLineNo() == prevLineNumber)
                    .map(DetailAST::getFirstChild)
                    .filter(Objects::nonNull)
                    .anyMatch(firstChild -> reliefPattern.matcher(firstChild.getText()).find());
        }
        return result;
    }

    /**
     * Checks if a statement list is terminated by an infinite loop.
     *
     * @param ast the AST node.
     * @return true if the last statement is an infinite loop.
     */
    private static boolean isTerminatedByInfiniteLoop(DetailAST ast) {
        if (ast == null) {
            return false;
        }

        DetailAST lastStatement = ast.getLastChild();

        while (lastStatement != null
                && (lastStatement.getType() == TokenTypes.RCURLY
                || lastStatement.getType() == TokenTypes.LCURLY
                || lastStatement.getType() == TokenTypes.SEMI
                || TokenUtil.isCommentType(lastStatement.getType()))) {

            lastStatement = lastStatement.getPreviousSibling();
        }

        if (lastStatement == null) {
            return false;
        }

        if (lastStatement.getType() == TokenTypes.SLIST) {
            return isTerminatedByInfiniteLoop(lastStatement);
        }

        return isInfiniteLoop(lastStatement);
    }

    /**
     * Checks if a given AST node represents an infinite loop.
     *
     * @param ast the AST node to check.
     * @return true if it is an infinite loop (e.g., while(true), for(;;), do-while(true)).
     */
    private static boolean isInfiniteLoop(DetailAST ast) {
        final int type = ast.getType();
        boolean result = false;

        if (type == TokenTypes.LITERAL_WHILE || type == TokenTypes.LITERAL_DO) {
            final DetailAST expr = ast.findFirstToken(TokenTypes.EXPR);
            result = isTrueExpression(expr) && !hasUnlabeledBreak(getLoopBody(ast));
        }
        else if (type == TokenTypes.LITERAL_FOR) {
            final DetailAST forEach = ast.findFirstToken(TokenTypes.FOR_EACH_CLAUSE);
            if (forEach == null) {
                final DetailAST condition = ast.findFirstToken(TokenTypes.FOR_CONDITION);
                // A for-loop condition is empty if it is null, has no children, 
                // or has an empty EXPR child (for(;;))
                final boolean isConditionEmpty = condition == null 
                        || condition.getChildCount() == 0
                        || (condition.getChildCount() == 1 
                            && condition.getFirstChild().getChildCount() == 0);
                
                result = isConditionEmpty && !hasUnlabeledBreak(ast.getLastChild());
            }
        }
        return result;
    }

    /**
     * Finds the body of a loop.
     *
     * @param loop the loop node.
     * @return the body node.
     */
    private static DetailAST getLoopBody(DetailAST loop) {
        final DetailAST body;
        if (loop.getType() == TokenTypes.LITERAL_DO) {
            body = loop.getFirstChild();
        }
        else {
            body = loop.getLastChild();
        }
        return body;
    }

    /**
     * Checks if an EXPR AST node evaluates to a literal 'true'.
     *
     * @param expr the EXPR AST node.
     * @return true if it contains a LITERAL_TRUE.
     */
    private static boolean isTrueExpression(DetailAST expr) {
        if (expr == null) {
            return false;
        }
        DetailAST child = expr.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.LITERAL_TRUE) {
                return true;
            }
            child = child.getNextSibling();
        }
        return false;
    }

    /**
     * Checks if an AST node is a loop or switch.
     *
     * @param type the token type.
     * @return true if it is a loop or switch.
     */
    private static boolean isLoopOrSwitch(int type) {
        return type == TokenTypes.LITERAL_FOR
                || type == TokenTypes.LITERAL_WHILE
                || type == TokenTypes.LITERAL_DO
                || type == TokenTypes.LITERAL_SWITCH;
    }

    /**
     * Checks if an AST node or its children contain an unlabeled break.
     *
     * @param ast the AST node to check.
     * @return true if an unlabeled break is found.
     */
    private static boolean hasUnlabeledBreak(DetailAST ast) {
        boolean result = false;
        if (ast != null) {
            final int type = ast.getType();
            if (type == TokenTypes.LITERAL_BREAK
                    && ast.findFirstToken(TokenTypes.IDENT) == null) {
                result = true;
            }
            else if (!isLoopOrSwitch(type)) {
                DetailAST child = ast.getFirstChild();
                while (child != null && !result) {
                    result = hasUnlabeledBreak(child);
                    child = child.getNextSibling();
                }
            }
        }
        return result;
    }
}
