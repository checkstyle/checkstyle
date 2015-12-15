////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks for fall through in switch statements
 * Finds locations where a case <b>contains</b> Java code -
 * but lacks a break, return, throw or continue statement.
 *
 * <p>
 * The check honors special comments to suppress warnings about
 * the fall through. By default the comments "fallthru",
 * "fall through", "falls through" and "fallthrough" are recognized.
 * </p>
 * <p>
 * The following fragment of code will NOT trigger the check,
 * because of the comment "fallthru" and absence of any Java code
 * in case 5.
 * </p>
 * <pre>
 * case 3:
 *     x = 2;
 *     // fallthru
 * case 4:
 * case 5:
 * case 6:
 *     break;
 * </pre>
 * <p>
 * The recognized relief comment can be configured with the property
 * {@code reliefPattern}. Default value of this regular expression
 * is "fallthru|fall through|fallthrough|falls through".
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="FallThrough"&gt;
 *     &lt;property name=&quot;reliefPattern&quot;
 *                  value=&quot;Fall Through&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author o_sukhodolsky
 */
public class FallThroughCheck extends Check {

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

    /** Do we need to check last case group. */
    private boolean checkLastCaseGroup;

    /** Relief pattern to allow fall through to the next case branch. */
    private String reliefPattern = "fallthru|falls? ?through";

    /** Relief regexp. */
    private Pattern regExp;

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CASE_GROUP};
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[]{TokenTypes.CASE_GROUP};
    }

    /**
     * Set the relief pattern.
     *
     * @param pattern
     *            The regular expression pattern.
     */
    public void setReliefPattern(String pattern) {
        reliefPattern = pattern;
    }

    /**
     * Configures whether we need to check last case group or not.
     * @param value new value of the property.
     */
    public void setCheckLastCaseGroup(boolean value) {
        checkLastCaseGroup = value;
    }

    @Override
    public void init() {
        super.init();
        regExp = Pattern.compile(reliefPattern);
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST nextGroup = ast.getNextSibling();
        final boolean isLastGroup = nextGroup.getType() != TokenTypes.CASE_GROUP;
        if (isLastGroup && !checkLastCaseGroup) {
            // we do not need to check last group
            return;
        }

        final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);

        if (slist != null && !isTerminated(slist, true, true)
            && !hasFallThroughComment(ast, nextGroup)) {
            if (isLastGroup) {
                log(ast, MSG_FALL_THROUGH_LAST);
            }
            else {
                log(nextGroup, MSG_FALL_THROUGH);
            }
        }
    }

    /**
     * Checks if a given subtree terminated by return, throw or,
     * if allowed break, continue.
     * @param ast root of given subtree
     * @param useBreak should we consider break as terminator.
     * @param useContinue should we consider continue as terminator.
     * @return true if the subtree is terminated.
     */
    private boolean isTerminated(final DetailAST ast, boolean useBreak,
                                 boolean useContinue) {
        boolean terminated;

        switch (ast.getType()) {
            case TokenTypes.LITERAL_RETURN:
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
            default:
                terminated = false;
        }
        return terminated;
    }

    /**
     * Checks if a given SLIST terminated by return, throw or,
     * if allowed break, continue.
     * @param slistAst SLIST to check
     * @param useBreak should we consider break as terminator.
     * @param useContinue should we consider continue as terminator.
     * @return true if SLIST is terminated.
     */
    private boolean checkSlist(final DetailAST slistAst, boolean useBreak,
                               boolean useContinue) {
        DetailAST lastStmt = slistAst.getLastChild();

        if (lastStmt.getType() == TokenTypes.RCURLY) {
            lastStmt = lastStmt.getPreviousSibling();
        }

        return lastStmt != null
            && isTerminated(lastStmt, useBreak, useContinue);
    }

    /**
     * Checks if a given IF terminated by return, throw or,
     * if allowed break, continue.
     * @param ast IF to check
     * @param useBreak should we consider break as terminator.
     * @param useContinue should we consider continue as terminator.
     * @return true if IF is terminated.
     */
    private boolean checkIf(final DetailAST ast, boolean useBreak,
                            boolean useContinue) {
        final DetailAST thenStmt = ast.findFirstToken(TokenTypes.RPAREN)
                .getNextSibling();
        final DetailAST elseStmt = thenStmt.getNextSibling();
        boolean isTerminated = isTerminated(thenStmt, useBreak, useContinue);

        if (isTerminated && elseStmt != null) {
            isTerminated = isTerminated(elseStmt.getFirstChild(),
                useBreak, useContinue);
        }
        else if (elseStmt == null) {
            isTerminated = false;
        }
        return isTerminated;
    }

    /**
     * Checks if a given loop terminated by return, throw or,
     * if allowed break, continue.
     * @param ast loop to check
     * @return true if loop is terminated.
     */
    private boolean checkLoop(final DetailAST ast) {
        DetailAST loopBody;
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
     * @param ast loop to check
     * @param useBreak should we consider break as terminator.
     * @param useContinue should we consider continue as terminator.
     * @return true if try/catch/finally block is terminated.
     */
    private boolean checkTry(final DetailAST ast, boolean useBreak,
                             boolean useContinue) {
        final DetailAST finalStmt = ast.getLastChild();
        boolean isTerminated = false;
        if (finalStmt.getType() == TokenTypes.LITERAL_FINALLY) {
            isTerminated = isTerminated(finalStmt.findFirstToken(TokenTypes.SLIST),
                                useBreak, useContinue);
        }

        if (!isTerminated) {
            isTerminated = isTerminated(ast.getFirstChild(),
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
     * @param literalSwitchAst loop to check
     * @param useContinue should we consider continue as terminator.
     * @return true if switch is terminated.
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
     * Determines if the fall through case between {@code currentCase} and
     * {@code nextCase} is relieved by a appropriate comment.
     *
     * @param currentCase AST of the case that falls through to the next case.
     * @param nextCase AST of the next case.
     * @return True if a relief comment was found
     */
    private boolean hasFallThroughComment(DetailAST currentCase, DetailAST nextCase) {
        boolean allThroughComment = false;
        final int endLineNo = nextCase.getLineNo();
        final int endColNo = nextCase.getColumnNo();

        // Remember: The lines number returned from the AST is 1-based, but
        // the lines number in this array are 0-based. So you will often
        // see a "lineNo-1" etc.
        final String[] lines = getLines();

        // Handle:
        //    case 1:
        //    /+ FALLTHRU +/ case 2:
        //    ....
        // and
        //    switch(i) {
        //    default:
        //    /+ FALLTHRU +/}
        //
        final String linePart = lines[endLineNo - 1].substring(0, endColNo);
        if (matchesComment(regExp, linePart, endLineNo)) {
            allThroughComment = true;
        }
        else {
            // Handle:
            //    case 1:
            //    .....
            //    // FALLTHRU
            //    case 2:
            //    ....
            // and
            //    switch(i) {
            //    default:
            //    // FALLTHRU
            //    }
            final int startLineNo = currentCase.getLineNo();
            for (int i = endLineNo - 2; i > startLineNo - 1; i--) {
                if (!lines[i].trim().isEmpty()) {
                    allThroughComment = matchesComment(regExp, lines[i], i + 1);
                    break;
                }
            }
        }
        return allThroughComment;
    }

    /**
     * Does a regular expression match on the given line and checks that a
     * possible match is within a comment.
     * @param pattern The regular expression pattern to use.
     * @param line The line of test to do the match on.
     * @param lineNo The line number in the file.
     * @return True if a match was found inside a comment.
     */
    private boolean matchesComment(Pattern pattern, String line, int lineNo
    ) {
        final Matcher matcher = pattern.matcher(line);

        final boolean hit = matcher.find();

        if (hit) {
            final int startMatch = matcher.start();
            // -1 because it returns the char position beyond the match
            final int endMatch = matcher.end() - 1;
            return getFileContents().hasIntersectionWithComment(lineNo,
                    startMatch, lineNo, endMatch);
        }
        return false;
    }
}
