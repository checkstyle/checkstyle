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

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Field;

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

    /** maps from a token name to value */
    private static final Map TOKEN_NAME_TO_VALUE = new HashMap();
    /** maps from a token value to name */
    private static final Map TOKEN_VALUE_TO_NAME = new HashMap();

    /** maps from token name to checks */
    private final Map mTokenToChecks = new HashMap();
    /** all the registered checks */
    private final Set mAllChecks = new HashSet();
    /** collects the error messages */
    private final LocalizedMessages mMessages;

    // initialise the constants
    static {
        // Wow, pretty cool idea to use reflection here...
        final Field[] fields = JavaTokenTypes.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            final Field f = fields[i];
            final String name = f.getName();
            try {
                // this should NEVER fail (famous last words)
                final Integer value = new Integer(f.getInt(name));
                TOKEN_NAME_TO_VALUE.put(name, value);
                TOKEN_VALUE_TO_NAME.put(value, name);
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.exit(1);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

    }

    public TreeWalker(LocalizedMessages aMessages)
    {
        mMessages = aMessages;
    }

    /**
     * Returns the name of a token for a given ID.
     * @param aID the ID of the token name to get
     * @return a token name
     */
    static String getTokenName(int aID)
    {
        final String name = (String) TOKEN_VALUE_TO_NAME.get(new Integer(aID));
        if (name == null) {
            throw new IllegalArgumentException("given id " + aID);
        }
        return name;
    }

    /**
     * Register a check for a given configuration.
     * @param aCheck the check to register
     * @param aConfig the configuration to use
     */
    void registerCheck(Check aCheck, CheckConfiguration aConfig)
    {
        aCheck.setMessages(mMessages);
        if (!aConfig.getTokens().isEmpty()) {
            final Iterator it = aConfig.getTokens().iterator();
            while (it.hasNext()) {
                registerCheck((String) it.next(), aCheck);
            }
        }
        else {
            final int[] tokens = aCheck.getDefaultTokens();
            for (int i = 0; i < tokens.length; i++) {
                registerCheck(tokens[i], aCheck);
            }
        }
    }

    /**
     * Register a check for a specified token id.
     * @param aTokenID the id of the token
     * @param aCheck the check to register
     */
    private void registerCheck(int aTokenID, Check aCheck)
    {
        registerCheck(getTokenName(aTokenID), aCheck);
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
        mAllChecks.add(aCheck);
    }

    /**
     * Initiates the walk of an AST.
     * @param aAST the root AST
     * @param aLines the lines of the file the AST was generated from
     * @param aFilename the file name of the file the AST was generated from
     */
    void walk(DetailAST aAST, String[] aLines, String aFilename)
    {
        mMessages.reset();
        notifyBegin(aLines, aFilename);
        aAST.setParent(null);
        process(aAST);
        notifyEnd();
    }

    /**
     * Notify interested checks that about to begin walking a tree.
     * @param aLines the lines of the file the AST was generated from
     * @param aFilename the file name of the file the AST was generated from
     */
    private void notifyBegin(String[] aLines, String aFilename)
    {
        // TODO: do not track Context properly for token
        final Iterator it = mAllChecks.iterator();
        while (it.hasNext()) {
            final Check check = (Check) it.next();
            final HashMap treeContext = new HashMap();
            check.setTreeContext(treeContext);
            check.setFilename(aFilename);
            check.setLines(aLines);
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
            (ArrayList) mTokenToChecks.get(getTokenName(aAST.getType()));
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
            (ArrayList) mTokenToChecks.get(getTokenName(aAST.getType()));
        if (visitors != null) {
            for (int i = 0; i < visitors.size(); i++) {
                final Check check = (Check) visitors.get(i);
                // TODO: need to setup the token context
                check.leaveToken(aAST);
            }
        }
    }
}
