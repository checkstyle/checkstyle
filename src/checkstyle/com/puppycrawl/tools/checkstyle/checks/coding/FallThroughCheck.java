////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
import com.puppycrawl.tools.checkstyle.api.Utils;

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
    /** Do we need to check last case group. */
    private boolean mCheckLastGroup;

    /** Relief pattern to allow fall throught to the next case branch. */
    private String mReliefPattern = "fallthru|falls? ?through";

    /** Relief regexp. */
    private Pattern mRegExp;

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

    /**
     * Set the relief pattern.
     *
     * @param aPattern
     *            The regular expression pattern.
     */
    public void setReliefPattern(String aPattern)
    {
        mReliefPattern = aPattern;
    }

    /**
     * Configures whether we need to check last case group or not.
     * @param aValue new value of the property.
     */
    public void setCheckLastCaseGroup(boolean aValue)
    {
        mCheckLastGroup = aValue;
    }

    @Override
    public void init()
    {
        super.init();
        mRegExp = Utils.getPattern(mReliefPattern);
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final DetailAST nextGroup = aAST.getNextSibling();
        final boolean isLastGroup =
            ((nextGroup == null)
             || (nextGroup.getType() != TokenTypes.CASE_GROUP));
        if (isLastGroup && !mCheckLastGroup) {
            // we do not need to check last group
            return;
        }

        final DetailAST slist = aAST.findFirstToken(TokenTypes.SLIST);

        if (!isTerminated(slist, true, true)) {
            if (!hasFallTruComment(aAST, nextGroup)) {
                if (!isLastGroup) {
                    log(nextGroup, "fall.through");
                }
                else {
                    log(aAST, "fall.through.last");
                }
            }
        }
    }

    /**
     * Checks if a given subtree terminated by return, throw or,
     * if allowed break, continue.
     * @param aAST root of given subtree
     * @param aUseBreak should we consider break as terminator.
     * @param aUseContinue should we consider continue as terminator.
     * @return true if the subtree is terminated.
     */
    private boolean isTerminated(final DetailAST aAST, boolean aUseBreak,
                                 boolean aUseContinue)
    {
        switch (aAST.getType()) {
        case TokenTypes.LITERAL_RETURN:
        case TokenTypes.LITERAL_THROW:
            return true;
        case TokenTypes.LITERAL_BREAK:
            return aUseBreak;
        case TokenTypes.LITERAL_CONTINUE:
            return aUseContinue;
        case TokenTypes.SLIST:
            return checkSlist(aAST, aUseBreak, aUseContinue);
        case TokenTypes.LITERAL_IF:
            return checkIf(aAST, aUseBreak, aUseContinue);
        case TokenTypes.LITERAL_FOR:
        case TokenTypes.LITERAL_WHILE:
        case TokenTypes.LITERAL_DO:
            return checkLoop(aAST);
        case TokenTypes.LITERAL_TRY:
            return checkTry(aAST, aUseBreak, aUseContinue);
        case TokenTypes.LITERAL_SWITCH:
            return checkSwitch(aAST, aUseContinue);
        default:
            return false;
        }
    }

    /**
     * Checks if a given SLIST terminated by return, throw or,
     * if allowed break, continue.
     * @param aAST SLIST to check
     * @param aUseBreak should we consider break as terminator.
     * @param aUseContinue should we consider continue as terminator.
     * @return true if SLIST is terminated.
     */
    private boolean checkSlist(final DetailAST aAST, boolean aUseBreak,
                               boolean aUseContinue)
    {
        DetailAST lastStmt = aAST.getLastChild();
        if (lastStmt == null) {
            // if last case in switch is empty then slist is empty
            // since this is a last case it is not a fall-through
            return true;
        }

        if (lastStmt.getType() == TokenTypes.RCURLY) {
            lastStmt = lastStmt.getPreviousSibling();
        }

        return (lastStmt != null)
            && isTerminated(lastStmt, aUseBreak, aUseContinue);
    }

    /**
     * Checks if a given IF terminated by return, throw or,
     * if allowed break, continue.
     * @param aAST IF to check
     * @param aUseBreak should we consider break as terminator.
     * @param aUseContinue should we consider continue as terminator.
     * @return true if IF is terminated.
     */
    private boolean checkIf(final DetailAST aAST, boolean aUseBreak,
                            boolean aUseContinue)
    {
        final DetailAST thenStmt = aAST.findFirstToken(TokenTypes.RPAREN)
                .getNextSibling();
        final DetailAST elseStmt = thenStmt.getNextSibling();
        boolean isTerminated = isTerminated(thenStmt, aUseBreak, aUseContinue);

        if (isTerminated && (elseStmt != null)) {
            isTerminated = isTerminated(elseStmt.getFirstChild(),
                                        aUseBreak, aUseContinue);
        }
        return isTerminated;
    }

    /**
     * Checks if a given loop terminated by return, throw or,
     * if allowed break, continue.
     * @param aAST loop to check
     * @return true if loop is terminated.
     */
    private boolean checkLoop(final DetailAST aAST)
    {
        DetailAST loopBody = null;
        if (aAST.getType() == TokenTypes.LITERAL_DO) {
            final DetailAST lparen = aAST.findFirstToken(TokenTypes.DO_WHILE);
            loopBody = lparen.getPreviousSibling();
        }
        else {
            final DetailAST rparen = aAST.findFirstToken(TokenTypes.RPAREN);
            loopBody = rparen.getNextSibling();
        }
        return isTerminated(loopBody, false, false);
    }

    /**
     * Checks if a given try/catch/finally block terminated by return, throw or,
     * if allowed break, continue.
     * @param aAST loop to check
     * @param aUseBreak should we consider break as terminator.
     * @param aUseContinue should we consider continue as terminator.
     * @return true if try/cath/finally block is terminated.
     */
    private boolean checkTry(final DetailAST aAST, boolean aUseBreak,
                             boolean aUseContinue)
    {
        final DetailAST finalStmt = aAST.getLastChild();
        if (finalStmt.getType() == TokenTypes.LITERAL_FINALLY) {
            return isTerminated(finalStmt.findFirstToken(TokenTypes.SLIST),
                                aUseBreak, aUseContinue);
        }

        boolean isTerminated = isTerminated(aAST.getFirstChild(),
                                            aUseBreak, aUseContinue);

        DetailAST catchStmt = aAST.findFirstToken(TokenTypes.LITERAL_CATCH);
        while ((catchStmt != null) && isTerminated) {
            final DetailAST catchBody =
                catchStmt.findFirstToken(TokenTypes.SLIST);
            isTerminated &= isTerminated(catchBody, aUseBreak, aUseContinue);
            catchStmt = catchStmt.getNextSibling();
        }
        return isTerminated;
    }

    /**
     * Checks if a given switch terminated by return, throw or,
     * if allowed break, continue.
     * @param aAST loop to check
     * @param aUseContinue should we consider continue as terminator.
     * @return true if switch is terminated.
     */
    private boolean checkSwitch(final DetailAST aAST, boolean aUseContinue)
    {
        DetailAST caseGroup = aAST.findFirstToken(TokenTypes.CASE_GROUP);
        boolean isTerminated = (caseGroup != null);
        while (isTerminated && (caseGroup != null)
               && (caseGroup.getType() != TokenTypes.RCURLY))
        {
            final DetailAST caseBody =
                caseGroup.findFirstToken(TokenTypes.SLIST);
            isTerminated &= isTerminated(caseBody, false, aUseContinue);
            caseGroup = caseGroup.getNextSibling();
        }
        return isTerminated;
    }

    /**
     * Determines if the fall through case between <code>aCurrentCase</code> and
     * <code>aNextCase</code> is reliefed by a appropriate comment.
     *
     * @param aCurrentCase AST of the case that falls through to the next case.
     * @param aNextCase AST of the next case.
     * @return True if a relief comment was found
     */
    private boolean hasFallTruComment(DetailAST aCurrentCase,
            DetailAST aNextCase)
    {

        final int startLineNo = aCurrentCase.getLineNo();
        final int endLineNo = aNextCase.getLineNo();
        final int endColNo = aNextCase.getColumnNo();

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
        if (commentMatch(mRegExp, linepart, endLineNo)) {
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
                return commentMatch(mRegExp, lines[i], i + 1);
            }
        }

        // Well -- no relief comment found.
        return false;
    }

    /**
     * Does a regular expression match on the given line and checks that a
     * possible match is within a comment.
     * @param aPattern The regular expression pattern to use.
     * @param aLine The line of test to do the match on.
     * @param aLineNo The line number in the file.
     * @return True if a match was found inside a comment.
     */
    private boolean commentMatch(Pattern aPattern, String aLine, int aLineNo
    )
    {
        final Matcher matcher = aPattern.matcher(aLine);

        final boolean hit = matcher.find();

        if (hit) {
            final int startMatch = matcher.start();
            // -1 because it returns the char position beyond the match
            final int endMatch = matcher.end() - 1;
            return getFileContents().hasIntersectionWithComment(aLineNo,
                    startMatch, aLineNo, endMatch);
        }
        return false;
    }
}
