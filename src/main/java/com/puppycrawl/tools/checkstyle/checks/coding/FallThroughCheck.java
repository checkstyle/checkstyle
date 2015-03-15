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
import com.puppycrawl.tools.checkstyle.Utils;

/**
 * Checks for fall through in switch statements
 * Finds locations where a case contains Java code -
 * but lacks a break, return, throw or continue statement.
 *
 * <p>
 * The check honors special comments to suppress warnings about
 * the fall through. By default the comments "fallthru",
 * "fall through", "falls through" and "fallthrough" are recognized.
 * </p>
 * <p>
 * The following fragment of code will NOT trigger the check,
 * because of the comment "fallthru".
 * </p>
 * <pre>
 * case 3:
 *     x = 2;
 *     // fallthru
 * case 4:
 * </pre>
 * <p>
 * The recognized relief comment can be configured with the property
 * <code>reliefPattern</code>. Default value of this regular expression
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
public class FallThroughCheck extends Check
{

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
    private boolean checkLastGroup;

    /** Relief pattern to allow fall throught to the next case branch. */
    private String reliefPattern = "fallthru|falls? ?through";

    /** Relief regexp. */
    private Pattern regExp;

    /** Creates new instance of the check. */
    public FallThroughCheck()
    {
        // do nothing
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[]{TokenTypes.CASE_GROUP};
    }

    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[]{TokenTypes.CASE_GROUP};
    }

    /**
     * Set the relief pattern.
     *
     * @param pattern
     *            The regular expression pattern.
     */
    public void setReliefPattern(String pattern)
    {
        reliefPattern = pattern;
    }

    /**
     * Configures whether we need to check last case group or not.
     * @param value new value of the property.
     */
    public void setCheckLastCaseGroup(boolean value)
    {
        checkLastGroup = value;
    }

    @Override
    public void init()
    {
        super.init();
        regExp = Utils.getPattern(reliefPattern);
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        final DetailAST nextGroup = ast.getNextSibling();
        final boolean isLastGroup =
                nextGroup == null
                 || nextGroup.getType() != TokenTypes.CASE_GROUP;
        if (isLastGroup && !checkLastGroup) {
            // we do not need to check last group
            return;
        }

        final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);

        if (slist != null && !isTerminated(slist, true, true)
            && !hasFallTruComment(ast, nextGroup))
        {
            if (!isLastGroup) {
                log(nextGroup, MSG_FALL_THROUGH);
            }
            else {
                log(ast, MSG_FALL_THROUGH_LAST);
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
                                 boolean useContinue)
    {
        switch (ast.getType()) {
            case TokenTypes.LITERAL_RETURN:
            case TokenTypes.LITERAL_THROW:
                return true;
            case TokenTypes.LITERAL_BREAK:
                return useBreak;
            case TokenTypes.LITERAL_CONTINUE:
                return useContinue;
            case TokenTypes.SLIST:
                return checkSlist(ast, useBreak, useContinue);
            case TokenTypes.LITERAL_IF:
                return checkIf(ast, useBreak, useContinue);
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
                return checkLoop(ast);
            case TokenTypes.LITERAL_TRY:
                return checkTry(ast, useBreak, useContinue);
            case TokenTypes.LITERAL_SWITCH:
                return checkSwitch(ast, useContinue);
            default:
                return false;
        }
    }

    /**
     * Checks if a given SLIST terminated by return, throw or,
     * if allowed break, continue.
     * @param ast SLIST to check
     * @param useBreak should we consider break as terminator.
     * @param useContinue should we consider continue as terminator.
     * @return true if SLIST is terminated.
     */
    private boolean checkSlist(final DetailAST ast, boolean useBreak,
                               boolean useContinue)
    {
        DetailAST lastStmt = ast.getLastChild();
        if (lastStmt == null) {
            // if last case in switch is empty then slist is empty
            // since this is a last case it is not a fall-through
            return true;
        }

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
                            boolean useContinue)
    {
        final DetailAST thenStmt = ast.findFirstToken(TokenTypes.RPAREN)
                .getNextSibling();
        final DetailAST elseStmt = thenStmt.getNextSibling();
        boolean isTerminated = isTerminated(thenStmt, useBreak, useContinue);

        if (isTerminated && elseStmt != null) {
            isTerminated = isTerminated(elseStmt.getFirstChild(),
                                        useBreak, useContinue);
        }
        return isTerminated;
    }

    /**
     * Checks if a given loop terminated by return, throw or,
     * if allowed break, continue.
     * @param ast loop to check
     * @return true if loop is terminated.
     */
    private boolean checkLoop(final DetailAST ast)
    {
        DetailAST loopBody = null;
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
     * @return true if try/cath/finally block is terminated.
     */
    private boolean checkTry(final DetailAST ast, boolean useBreak,
                             boolean useContinue)
    {
        final DetailAST finalStmt = ast.getLastChild();
        if (finalStmt.getType() == TokenTypes.LITERAL_FINALLY) {
            return isTerminated(finalStmt.findFirstToken(TokenTypes.SLIST),
                                useBreak, useContinue);
        }

        boolean isTerminated = isTerminated(ast.getFirstChild(),
                                            useBreak, useContinue);

        DetailAST catchStmt = ast.findFirstToken(TokenTypes.LITERAL_CATCH);
        while (catchStmt != null && isTerminated) {
            final DetailAST catchBody =
                catchStmt.findFirstToken(TokenTypes.SLIST);
            isTerminated &= isTerminated(catchBody, useBreak, useContinue);
            catchStmt = catchStmt.getNextSibling();
        }
        return isTerminated;
    }

    /**
     * Checks if a given switch terminated by return, throw or,
     * if allowed break, continue.
     * @param ast loop to check
     * @param useContinue should we consider continue as terminator.
     * @return true if switch is terminated.
     */
    private boolean checkSwitch(final DetailAST ast, boolean useContinue)
    {
        DetailAST caseGroup = ast.findFirstToken(TokenTypes.CASE_GROUP);
        boolean isTerminated = caseGroup != null;
        while (isTerminated && caseGroup != null
               && caseGroup.getType() != TokenTypes.RCURLY)
        {
            final DetailAST caseBody =
                caseGroup.findFirstToken(TokenTypes.SLIST);
            isTerminated &= isTerminated(caseBody, false, useContinue);
            caseGroup = caseGroup.getNextSibling();
        }
        return isTerminated;
    }

    /**
     * Determines if the fall through case between <code>currentCase</code> and
     * <code>nextCase</code> is reliefed by a appropriate comment.
     *
     * @param currentCase AST of the case that falls through to the next case.
     * @param nextCase AST of the next case.
     * @return True if a relief comment was found
     */
    private boolean hasFallTruComment(DetailAST currentCase,
            DetailAST nextCase)
    {

        final int startLineNo = currentCase.getLineNo();
        final int endLineNo = nextCase.getLineNo();
        final int endColNo = nextCase.getColumnNo();

        /*
         * Remember: The lines number returned from the AST is 1-based, but
         * the lines number in this array are 0-based. So you will often
         * see a "lineNo-1" etc.
         */
        final String[] lines = getLines();

        /*
         * Handle:
         *    case 1:
         *    /+ FALLTHRU +/ case 2:
         *    ....
         * and
         *    switch(i) {
         *    default:
         *    /+ FALLTHRU +/}
         */
        final String linepart = lines[endLineNo - 1].substring(0, endColNo);
        if (commentMatch(regExp, linepart, endLineNo)) {
            return true;
        }

        /*
         * Handle:
         *    case 1:
         *    .....
         *    // FALLTHRU
         *    case 2:
         *    ....
         * and
         *    switch(i) {
         *    default:
         *    // FALLTHRU
         *    }
         */
        for (int i = endLineNo - 2; i > startLineNo - 1; i--) {
            if (lines[i].trim().length() != 0) {
                return commentMatch(regExp, lines[i], i + 1);
            }
        }

        // Well -- no relief comment found.
        return false;
    }

    /**
     * Does a regular expression match on the given line and checks that a
     * possible match is within a comment.
     * @param pattern The regular expression pattern to use.
     * @param line The line of test to do the match on.
     * @param lineNo The line number in the file.
     * @return True if a match was found inside a comment.
     */
    private boolean commentMatch(Pattern pattern, String line, int lineNo
    )
    {
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
