////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Checks for fall through in switch statements
 * Finds locations where a case contains Java code -
 * but lacks a break, return, throw or continue statement.
 *
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="FallThrough"/&gt;
 * </pre>
 *
 * @author o_sukhodolsky
 */
public class FallThroughCheck extends Check
{
    /** Creates new instance of the check. */
    public FallThroughCheck()
    {
        // do nothing
    }

    /** {@inheritDoc} */
    public int[] getDefaultTokens()
    {
        return new int[]{TokenTypes.CASE_GROUP};
    }

    /** {@inheritDoc} */
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    /** {@inheritDoc} */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST nextGroup = (DetailAST) aAST.getNextSibling();
        if (nextGroup == null || nextGroup.getType() != TokenTypes.CASE_GROUP) {
            // last group we shouldn't check it
            return;
        }

        final DetailAST slist = aAST.findFirstToken(TokenTypes.SLIST);

        if (!isTerminated(slist, true, true)) {
            log(nextGroup, "fall.through");
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

        if (lastStmt.getType() == TokenTypes.RCURLY) {
            lastStmt = lastStmt.getPreviousSibling();
        }

        return lastStmt != null
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
        final DetailAST thenStmt = (DetailAST)
            aAST.findFirstToken(TokenTypes.RPAREN).getNextSibling();
        final DetailAST elseStmt = (DetailAST) thenStmt.getNextSibling();
        boolean isTerminated = isTerminated(thenStmt, aUseBreak, aUseContinue);

        if (isTerminated && elseStmt != null) {
            isTerminated = isTerminated((DetailAST) elseStmt.getFirstChild(),
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
            loopBody = (DetailAST) rparen.getNextSibling();
        }
        return isTerminated(loopBody, false, false);
    }

    /**
     * Checks if a given try/cath/finally block terminated by return, throw or,
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

        boolean isTerminated = isTerminated((DetailAST) aAST.getFirstChild(),
                                            aUseBreak, aUseContinue);

        DetailAST catchStmt = aAST.findFirstToken(TokenTypes.LITERAL_CATCH);
        while (catchStmt != null && isTerminated) {
            final DetailAST catchBody =
                catchStmt.findFirstToken(TokenTypes.SLIST);
            isTerminated &= isTerminated(catchBody, aUseBreak, aUseContinue);
            catchStmt = (DetailAST) catchStmt.getNextSibling();
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
        while (isTerminated && caseGroup != null
               && caseGroup.getType() != TokenTypes.RCURLY)
        {
            final DetailAST caseBody =
                caseGroup.findFirstToken(TokenTypes.SLIST);
            isTerminated &= isTerminated(caseBody, false, aUseContinue);
            caseGroup = (DetailAST) caseGroup.getNextSibling();
        }
        return isTerminated;
    }

}
