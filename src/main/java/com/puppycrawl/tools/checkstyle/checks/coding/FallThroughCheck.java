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

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
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

            if (slist != null && !isTerminated(slist, true, true, new HashSet<>())
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
     * When analyzing fall-through cases in switch statements, a Set of String labels
     * is used to keep track of the labels encountered in the enclosing switch statements.
     *
     * @param ast root of given subtree
     * @param useBreak should we consider break as terminator
     * @param useContinue should we consider continue as terminator
     * @param labelsForCurrentSwitchScope the Set labels for the current scope of the switch
     * @return true if the subtree is terminated.
     */
    private boolean isTerminated(final DetailAST ast, boolean useBreak,
                                 boolean useContinue, Set<String> labelsForCurrentSwitchScope) {
        final boolean terminated;

        switch (ast.getType()) {
            case TokenTypes.LITERAL_RETURN:
            case TokenTypes.LITERAL_YIELD:
            case TokenTypes.LITERAL_THROW:
                terminated = true;
                break;
            case TokenTypes.LITERAL_BREAK:
                terminated =
                        useBreak || hasLabel(ast, labelsForCurrentSwitchScope);
                break;
            case TokenTypes.LITERAL_CONTINUE:
                terminated =
                        useContinue || hasLabel(ast, labelsForCurrentSwitchScope);
                break;
            case TokenTypes.SLIST:
                terminated =
                        checkSlist(ast, useBreak, useContinue, labelsForCurrentSwitchScope);
                break;
            case TokenTypes.LITERAL_IF:
                terminated =
                        checkIf(ast, useBreak, useContinue, labelsForCurrentSwitchScope);
                break;
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
                terminated = checkLoop(ast, labelsForCurrentSwitchScope);
                break;
            case TokenTypes.LITERAL_TRY:
                terminated =
                        checkTry(ast, useBreak, useContinue, labelsForCurrentSwitchScope);
                break;
            case TokenTypes.LITERAL_SWITCH:
                terminated =
                        checkSwitch(ast, useContinue, labelsForCurrentSwitchScope);
                break;
            case TokenTypes.LITERAL_SYNCHRONIZED:
                terminated =
                        checkSynchronized(ast, useBreak, useContinue, labelsForCurrentSwitchScope);
                break;
            case TokenTypes.LABELED_STAT:
                labelsForCurrentSwitchScope.add(ast.getFirstChild().getText());
                terminated =
                        isTerminated(ast.getLastChild(), useBreak, useContinue,
                                labelsForCurrentSwitchScope);
                break;
            default:
                terminated = false;
        }
        return terminated;
    }

    /**
     * Checks if given break or continue ast has outer label.
     *
     * @param statement break or continue node
     * @param labelsForCurrentSwitchScope the Set labels for the current scope of the switch
     * @return true if local label used
     */
    private static boolean hasLabel(DetailAST statement, Set<String> labelsForCurrentSwitchScope) {
        return Optional.ofNullable(statement)
                .map(DetailAST::getFirstChild)
                .filter(child -> child.getType() == TokenTypes.IDENT)
                .map(DetailAST::getText)
                .filter(label -> !labelsForCurrentSwitchScope.contains(label))
                .isPresent();
    }

    /**
     * Checks if a given SLIST terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param slistAst SLIST to check
     * @param useBreak should we consider break as terminator
     * @param useContinue should we consider continue as terminator
     * @param labels label names
     * @return true if SLIST is terminated.
     */
    private boolean checkSlist(final DetailAST slistAst, boolean useBreak,
                               boolean useContinue, Set<String> labels) {
        DetailAST lastStmt = slistAst.getLastChild();

        if (lastStmt.getType() == TokenTypes.RCURLY) {
            lastStmt = lastStmt.getPreviousSibling();
        }

        while (TokenUtil.isOfType(lastStmt, TokenTypes.SINGLE_LINE_COMMENT,
                TokenTypes.BLOCK_COMMENT_BEGIN)) {
            lastStmt = lastStmt.getPreviousSibling();
        }

        return lastStmt != null
            && isTerminated(lastStmt, useBreak, useContinue, labels);
    }

    /**
     * Checks if a given IF terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param ast IF to check
     * @param useBreak should we consider break as terminator
     * @param useContinue should we consider continue as terminator
     * @param labels label names
     * @return true if IF is terminated.
     */
    private boolean checkIf(final DetailAST ast, boolean useBreak,
                            boolean useContinue, Set<String> labels) {
        final DetailAST thenStmt = getNextNonCommentAst(ast.findFirstToken(TokenTypes.RPAREN));

        final DetailAST elseStmt = getNextNonCommentAst(thenStmt);

        return elseStmt != null
                && isTerminated(thenStmt, useBreak, useContinue, labels)
                && isTerminated(elseStmt.getLastChild(), useBreak, useContinue, labels);
    }

    /**
     * This method will skip the comment content while finding the next ast of current ast.
     *
     * @param ast current ast
     * @return next ast after skipping comment
     */
    private static DetailAST getNextNonCommentAst(DetailAST ast) {
        DetailAST nextSibling = ast.getNextSibling();
        while (TokenUtil.isOfType(nextSibling, TokenTypes.SINGLE_LINE_COMMENT,
                TokenTypes.BLOCK_COMMENT_BEGIN)) {
            nextSibling = nextSibling.getNextSibling();
        }
        return nextSibling;
    }

    /**
     * Checks if a given loop terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param ast loop to check
     * @param labels label names
     * @return true if loop is terminated.
     */
    private boolean checkLoop(final DetailAST ast, Set<String> labels) {
        final DetailAST loopBody;
        if (ast.getType() == TokenTypes.LITERAL_DO) {
            final DetailAST lparen = ast.findFirstToken(TokenTypes.DO_WHILE);
            loopBody = lparen.getPreviousSibling();
        }
        else {
            final DetailAST rparen = ast.findFirstToken(TokenTypes.RPAREN);
            loopBody = rparen.getNextSibling();
        }
        return isTerminated(loopBody, false, false, labels);
    }

    /**
     * Checks if a given try/catch/finally block terminated by return, throw or,
     * if allowed break, continue.
     *
     * @param ast loop to check
     * @param useBreak should we consider break as terminator
     * @param useContinue should we consider continue as terminator
     * @param labels label names
     * @return true if try/catch/finally block is terminated
     */
    private boolean checkTry(final DetailAST ast, boolean useBreak,
                             boolean useContinue, Set<String> labels) {
        final DetailAST finalStmt = ast.getLastChild();
        boolean isTerminated = finalStmt.getType() == TokenTypes.LITERAL_FINALLY
                && isTerminated(finalStmt.findFirstToken(TokenTypes.SLIST),
                useBreak, useContinue, labels);

        if (!isTerminated) {
            DetailAST firstChild = ast.getFirstChild();

            if (firstChild.getType() == TokenTypes.RESOURCE_SPECIFICATION) {
                firstChild = firstChild.getNextSibling();
            }

            isTerminated = isTerminated(firstChild,
                    useBreak, useContinue, labels);

            DetailAST catchStmt = ast.findFirstToken(TokenTypes.LITERAL_CATCH);
            while (catchStmt != null
                    && isTerminated
                    && catchStmt.getType() == TokenTypes.LITERAL_CATCH) {
                final DetailAST catchBody =
                        catchStmt.findFirstToken(TokenTypes.SLIST);
                isTerminated = isTerminated(catchBody, useBreak, useContinue, labels);
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
     * @param labels label names
     * @return true if switch is terminated
     */
    private boolean checkSwitch(DetailAST literalSwitchAst,
                                boolean useContinue, Set<String> labels) {
        DetailAST caseGroup = literalSwitchAst.findFirstToken(TokenTypes.CASE_GROUP);
        boolean isTerminated = caseGroup != null;
        while (isTerminated && caseGroup.getType() != TokenTypes.RCURLY) {
            final DetailAST caseBody =
                caseGroup.findFirstToken(TokenTypes.SLIST);
            isTerminated = caseBody != null
                    && isTerminated(caseBody, false, useContinue, labels);
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
     * @param labels label names
     * @return true if synchronized block is terminated
     */
    private boolean checkSynchronized(final DetailAST synchronizedAst, boolean useBreak,
                                      boolean useContinue, Set<String> labels) {
        return isTerminated(
            synchronizedAst.findFirstToken(TokenTypes.SLIST), useBreak, useContinue, labels);
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
        final DetailAST nonCommentAst = getNextNonCommentAst(ast);
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

}
