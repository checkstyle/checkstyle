///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for fall-through in {@code switch} statements.
 * Finds locations where a {@code case} <b>contains</b> Java code but lacks a
 * {@code break}, {@code return}, {@code yield}, {@code throw} or {@code continue} statement.
 * </p>
 * <p>
 * The check honors special comments to suppress the warning.
 * By default, the texts
 * "fallthru", "fall thru", "fall-thru",
 * "fallthrough", "fall through", "fall-through"
 * "fallsthrough", "falls through", "falls-through" (case-sensitive).
 * The comment containing these words must be all on one line,
 * and must be on the last non-empty line before the {@code case} triggering
 * the warning or on the same line before the {@code case}(ugly, but possible).
 * </p>
 * <p>
 * Note: The check assumes that there is no unreachable code in the {@code case}.
 * </p>
 * <ul>
 * <li>
 * Property {@code checkLastCaseGroup} - Control whether the last case group must be checked.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code reliefPattern} - Define the RegExp to match the relief comment that suppresses
 * the warning about a fall through.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "falls?[ -]?thr(u|ough)"}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="FallThrough"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public void foo() throws Exception {
 *   int i = 0;
 *   while (i &gt;= 0) {
 *     switch (i) {
 *       case 1:
 *         i++;
 *       case 2: // violation, previous case contains code but lacks
 *               // break, return, yield, throw or continue statement
 *         i++;
 *         break;
 *       case 3: // OK
 *         i++;
 *         return;
 *       case 4: // OK
 *         i++;
 *         throw new Exception();
 *       case 5: // OK
 *         i++;
 *         continue;
 *       case 6: // OK
 *       case 7: // Previous case: OK, case does not contain code
 *               // This case: OK, by default the last case might not have statement
 *               // that transfer control
 *         i++;
 *     }
 *   }
 * }
 * public int bar() {
 *   int i = 0;
 *   return switch (i) {
 *     case 1:
 *       i++;
 *     case 2: // violation, previous case contains code but lacks
 *             // break, return, yield, throw or continue statement
 *     case 3: // OK, case does not contain code
 *       i++;
 *       yield 11;
 *     default: // OK
 *       yield -1;
 *   };
 * }
 * </pre>
 * <p>
 * Example how to suppress violations by comment:
 * </p>
 * <pre>
 * switch (i) {
 *   case 1:
 *     i++; // fall through
 *
 *   case 2: // OK
 *     i++;
 *     // fallthru
 *   case 3: { // OK
 *     i++;
 *   }
 *   &#47;* fall-thru *&#47;
 *   case 4: // OK
 *     i++;
 *     // Fallthru
 *   case 5: // violation, "Fallthru" in case 4 should be "fallthru"
 *     i++;
 *     // fall through
 *     i++;
 *   case 6: // violation, the comment must be on the last non-empty line before 'case'
 *     i++;
 *   &#47;* fall through *&#47;case 7: // OK, comment can appear on the same line but before 'case'
 *     i++;
 * }
 * </pre>
 * <p>
 * To configure the check to enable check for last case group:
 * </p>
 * <pre>
 * &lt;module name=&quot;FallThrough&quot;&gt;
 *    &lt;property name=&quot;checkLastCaseGroup&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * switch (i) {
 *   case 1:
 *     break;
 *   case 2: // Previous case: OK
 *           // This case: violation, last case must have statement that transfer control
 *     i++;
 * }
 * </pre>
 * <p>
 * To configure the check with custom relief pattern:
 * </p>
 * <pre>
 * &lt;module name=&quot;FallThrough&quot;&gt;
 *    &lt;property name=&quot;reliefPattern&quot; value=&quot;FALL?[ -]?THROUGH&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * switch (i) {
 *   case 1:
 *     i++;
 *     // FALL-THROUGH
 *   case 2: // OK, "FALL-THROUGH" matches the regular expression "FALL?[ -]?THROUGH"
 *     i++;
 *     // fall-through
 *   case 3: // violation, "fall-through" doesn't match
 *     break;
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
 * {@code fall.through}
 * </li>
 * <li>
 * {@code fall.through.last}
 * </li>
 * </ul>
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
     */
    public void setReliefPattern(Pattern pattern) {
        reliefPattern = pattern;
    }

    /**
     * Setter to control whether the last case group must be checked.
     *
     * @param value new value of the property.
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

            if (slist != null && !isTerminated(slist, true, true)
                && !hasFallThroughComment(ast)) {
                if (isLastGroup) {
                    log(ast, MSG_FALL_THROUGH_LAST);
                }
                else {
                    log(nextGroup, MSG_FALL_THROUGH);
                }
            }
        }
    }

    /**
     * Checks if a given subtree terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param ast root of given subtree
     * @param useBreak should we consider break as terminator
     * @param useContinue should we consider continue as terminator
     * @return true if the subtree is terminated.
     */
    private boolean isTerminated(final DetailAST ast, boolean useBreak,
                                 boolean useContinue) {
        final boolean terminated;

        switch (ast.getType()) {
            case TokenTypes.LITERAL_RETURN:
            case TokenTypes.LITERAL_YIELD:
            case TokenTypes.LITERAL_THROW:
                terminated = true;
                break;
            case TokenTypes.LITERAL_BREAK:
                terminated = useBreak;
                break;
            case TokenTypes.LITERAL_CONTINUE:
                terminated = useContinue;
                break;
            case TokenTypes.SLIST:
                terminated = checkSlist(ast, useBreak, useContinue);
                break;
            case TokenTypes.LITERAL_IF:
                terminated = checkIf(ast, useBreak, useContinue);
                break;
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
                terminated = checkLoop(ast);
                break;
            case TokenTypes.LITERAL_TRY:
                terminated = checkTry(ast, useBreak, useContinue);
                break;
            case TokenTypes.LITERAL_SWITCH:
                terminated = checkSwitch(ast, useContinue);
                break;
            case TokenTypes.LITERAL_SYNCHRONIZED:
                terminated = checkSynchronized(ast, useBreak, useContinue);
                break;
            default:
                terminated = false;
        }
        return terminated;
    }

    /**
     * Checks if a given SLIST terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param slistAst SLIST to check
     * @param useBreak should we consider break as terminator
     * @param useContinue should we consider continue as terminator
     * @return true if SLIST is terminated.
     */
    private boolean checkSlist(final DetailAST slistAst, boolean useBreak,
                               boolean useContinue) {
        DetailAST lastStmt = slistAst.getLastChild();

        if (lastStmt.getType() == TokenTypes.RCURLY) {
            lastStmt = lastStmt.getPreviousSibling();
        }

        if (lastStmt != null && (lastStmt.getType() == TokenTypes.SINGLE_LINE_COMMENT
                || lastStmt.getType() == TokenTypes.BLOCK_COMMENT_BEGIN)) {
            lastStmt = lastStmt.getPreviousSibling();
        }

        return lastStmt != null
            && isTerminated(lastStmt, useBreak, useContinue);
    }

    /**
     * Checks if a given IF terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param ast IF to check
     * @param useBreak should we consider break as terminator
     * @param useContinue should we consider continue as terminator
     * @return true if IF is terminated.
     */
    private boolean checkIf(final DetailAST ast, boolean useBreak,
                            boolean useContinue) {
        final DetailAST thenStmt = ast.findFirstToken(TokenTypes.RPAREN)
                .getNextSibling();
        final DetailAST elseStmt = thenStmt.getNextSibling();

        return elseStmt != null
                && isTerminated(thenStmt, useBreak, useContinue)
                && isTerminated(elseStmt.getFirstChild(), useBreak, useContinue);
    }

    /**
     * Checks if a given loop terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param ast loop to check
     * @return true if loop is terminated.
     */
    private boolean checkLoop(final DetailAST ast) {
        final DetailAST loopBody;
        if (ast.getType() == TokenTypes.LITERAL_DO) {
            final DetailAST lparen = ast.findFirstToken(TokenTypes.DO_WHILE);
            loopBody = lparen.getPreviousSibling();
        }
        else {
            final DetailAST rparen = ast.findFirstToken(TokenTypes.RPAREN);
            loopBody = rparen.getNextSibling();
        }
        return isTerminated(loopBody, false, false);
    }

    /**
     * Checks if a given try/catch/finally block terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param ast loop to check
     * @param useBreak should we consider break as terminator
     * @param useContinue should we consider continue as terminator
     * @return true if try/catch/finally block is terminated
     */
    private boolean checkTry(final DetailAST ast, boolean useBreak,
                             boolean useContinue) {
        final DetailAST finalStmt = ast.getLastChild();
        boolean isTerminated = finalStmt.getType() == TokenTypes.LITERAL_FINALLY
                && isTerminated(finalStmt.findFirstToken(TokenTypes.SLIST), useBreak, useContinue);

        if (!isTerminated) {
            DetailAST firstChild = ast.getFirstChild();

            if (firstChild.getType() == TokenTypes.RESOURCE_SPECIFICATION) {
                firstChild = firstChild.getNextSibling();
            }

            isTerminated = isTerminated(firstChild,
                    useBreak, useContinue);

            DetailAST catchStmt = ast.findFirstToken(TokenTypes.LITERAL_CATCH);
            while (catchStmt != null
                    && isTerminated
                    && catchStmt.getType() == TokenTypes.LITERAL_CATCH) {
                final DetailAST catchBody =
                        catchStmt.findFirstToken(TokenTypes.SLIST);
                isTerminated = isTerminated(catchBody, useBreak, useContinue);
                catchStmt = catchStmt.getNextSibling();
            }
        }
        return isTerminated;
    }

    /**
     * Checks if a given switch terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param literalSwitchAst loop to check
     * @param useContinue should we consider continue as terminator
     * @return true if switch is terminated
     */
    private boolean checkSwitch(final DetailAST literalSwitchAst, boolean useContinue) {
        DetailAST caseGroup = literalSwitchAst.findFirstToken(TokenTypes.CASE_GROUP);
        boolean isTerminated = caseGroup != null;
        while (isTerminated && caseGroup.getType() != TokenTypes.RCURLY) {
            final DetailAST caseBody =
                caseGroup.findFirstToken(TokenTypes.SLIST);
            isTerminated = caseBody != null && isTerminated(caseBody, false, useContinue);
            caseGroup = caseGroup.getNextSibling();
        }
        return isTerminated;
    }

    /**
     * Checks if a given synchronized block terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param synchronizedAst synchronized block to check.
     * @param useBreak should we consider break as terminator
     * @param useContinue should we consider continue as terminator
     * @return true if synchronized block is terminated
     */
    private boolean checkSynchronized(final DetailAST synchronizedAst, boolean useBreak,
                                      boolean useContinue) {
        return isTerminated(
            synchronizedAst.findFirstToken(TokenTypes.SLIST), useBreak, useContinue);
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
        final DetailAST nextCase = currentCase.getNextSibling();
        final DetailAST ast;
        final DetailAST lastAST;
        if (nextCase.getType() == TokenTypes.CASE_GROUP) {
            ast = nextCase;
            lastAST = nextCase.getLastChild();
        }
        else {
            ast = currentCase;
            lastAST = currentCase.getParent().getLastChild();
        }
        boolean reliefComment = false;
        final List<DetailAST> commentContent = getCommentContentNodes(ast, lastAST);
        for (DetailAST detailAST : commentContent) {
            if (reliefPattern.matcher(detailAST.getText()).find()) {
                reliefComment = true;
            }
        }
        return reliefComment || commentAsChildOfSameGroup(reliefPattern, currentCase);
    }

    /**
     * This method will check if the comment are child of same case group ast.
     *
     * @param reliefPattern The regular expression pattern to match the comment text
     * @param currentCase AST of the case that falls through to the next case.
     * @return true if relief comment found
     */
    private static boolean commentAsChildOfSameGroup(Pattern reliefPattern,
                                                     DetailAST currentCase) {
        boolean reliefComment = false;
        final List<DetailAST> commentContent = getCommentContentNodes(currentCase.getLastChild(),
                currentCase.getLastChild().getLastChild());
        for (DetailAST detailAST : commentContent) {
            if (reliefPattern.matcher(detailAST.getText()).find()) {
                reliefComment = true;
            }
        }
        return reliefComment;
    }

    /**
     * This method will return the comment content node between two nodes.
     *
     * @param fromNode starting node
     * @param toNode last node to check
     * @return list of commentContent node
     */
    private static List<DetailAST> getCommentContentNodes(DetailAST fromNode, DetailAST toNode) {
        final List<DetailAST> commentContentNode = new LinkedList<>();
        DetailAST node = fromNode;
        while (true) {
            if (node.getType() == TokenTypes.COMMENT_CONTENT) {
                commentContentNode.add(node);
            }
            if (node.equals(toNode)) {
                break;
            }
            DetailAST visitNode = node.getFirstChild();
            while (visitNode == null) {
                visitNode = node.getNextSibling();
                node = node.getParent();
            }
            node = visitNode;
        }
        return commentContentNode;
    }
}
