////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.FileContents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

/**
 * Responsible for walking an abstract syntax tree and notifying interested
 * checks at each each node.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
class TreeWalker
{
    // TODO: really need to optimise the performance of this class.

    /** maps from token name to checks */
    private final Map mTokenToChecks = new HashMap();
    /** all the registered checks */
    private final Set mAllChecks = new HashSet();
    /** collects the error messages */
    private final LocalizedMessages mMessages;
    /** the tab width for error reporting */
    private final int mTabWidth;

    /**
     * Creates a new <code>TreeWalker</code> instance.
     *
     * @param aMessages used to collect messages
     * @param aTabWidth the tabwidth to use
     */
    public TreeWalker(LocalizedMessages aMessages, int aTabWidth)
    {
        mMessages = aMessages;
        mTabWidth = aTabWidth;
    }

    /**
     * Register a check for a given configuration.
     * @param aCheck the check to register
     * @param aConfig the configuration to use
     */
    void registerCheck(Check aCheck, CheckConfiguration aConfig)
    {
        aCheck.setMessages(mMessages);
        aCheck.setTabWidth(mTabWidth);
        if (!aConfig.getTokens().isEmpty()) {
            int acceptableTokens[] = aCheck.getAcceptableTokens();
            Arrays.sort(acceptableTokens);
            final Iterator it = aConfig.getTokens().iterator();
            while (it.hasNext()) {
                String token = (String) it.next();
                int tokenId = TokenTypes.getTokenId(token);
                if (Arrays.binarySearch(acceptableTokens, tokenId) >= 0) {
                    registerCheck(token, aCheck);
                }
                // TODO: else error message?
            }
        }
        else {
            final int[] tokens = aCheck.getDefaultTokens();
            for (int i = 0; i < tokens.length; i++) {
                registerCheck(tokens[i], aCheck);
            }
        }
        mAllChecks.add(aCheck);
    }

    /**
     * Register a check for a specified token id.
     * @param aTokenID the id of the token
     * @param aCheck the check to register
     */
    private void registerCheck(int aTokenID, Check aCheck)
    {
        registerCheck(TokenTypes.getTokenName(aTokenID), aCheck);
    }

    /**
     * Register a check for a specified token name
     * @param aToken the name of the token
     * @param aCheck the check to register
     */
    private void registerCheck(String aToken, Check aCheck)
    {
        ArrayList visitors = (ArrayList) mTokenToChecks.get(aToken);
        if (visitors == null) {
            visitors = new ArrayList();
            mTokenToChecks.put(aToken, visitors);
        }

        visitors.add(aCheck);
    }

    /**
     * Initiates the walk of an AST.
     * @param aAST the root AST
     * @param aContents the contents of the file the AST was generated from
     */
    void walk(DetailAST aAST, FileContents aContents)
    {
        mMessages.reset();
        notifyBegin(aContents);
        aAST.setParent(null);
        process(aAST);
        notifyEnd();
    }

    /**
     * Notify interested checks that about to begin walking a tree.
     * @param aContents the contents of the file the AST was generated from
     */
    private void notifyBegin(FileContents aContents)
    {
        // TODO: do not track Context properly for token
        final Iterator it = mAllChecks.iterator();
        while (it.hasNext()) {
            final Check check = (Check) it.next();
            final HashMap treeContext = new HashMap();
            check.setTreeContext(treeContext);
            check.setFileContents(aContents);
            check.beginTree();
        }
    }

    /**
     * Notify checks that finished walking a tree.
     */
    private void notifyEnd()
    {
        final Iterator it = mAllChecks.iterator();
        while (it.hasNext()) {
            final Check check = (Check) it.next();
            check.finishTree();
        }
    }

    /**
     * Recursively processes a node calling interested checks at each node.
     * @param aAST the node to start from
     */
    private void process(DetailAST aAST)
    {
        if (aAST == null) {
            return;
        }

        notifyVisit(aAST);

        final DetailAST child = (DetailAST) aAST.getFirstChild();
        if (child != null) {
            child.setParent(aAST);
            process(child);
        }

        notifyLeave(aAST);

        final DetailAST sibling = (DetailAST) aAST.getNextSibling();
        if (sibling != null) {
            sibling.setParent(aAST.getParent());
            process(sibling);
        }

    }

    /**
     * Notify interested checks that visiting a node.
     * @param aAST the node to notify for
     */
    private void notifyVisit(DetailAST aAST)
    {
        final ArrayList visitors =
            (ArrayList) mTokenToChecks.get(TokenTypes.getTokenName(
                                               aAST.getType()));
        if (visitors != null) {
            final Map ctx = new HashMap();
            for (int i = 0; i < visitors.size(); i++) {
                final Check check = (Check) visitors.get(i);
                check.setTokenContext(ctx);
                check.visitToken(aAST);
            }
        }
    }

    /**
     * Notify interested checks that leaving a node.
     * @param aAST the node to notify for
     */
    private void notifyLeave(DetailAST aAST)
    {
        final ArrayList visitors =
            (ArrayList) mTokenToChecks.get(TokenTypes.getTokenName(
                                               aAST.getType()));
        if (visitors != null) {
            for (int i = 0; i < visitors.size(); i++) {
                final Check check = (Check) visitors.get(i);
                // TODO: need to setup the token context
                check.leaveToken(aAST);
            }
        }
    }
}
