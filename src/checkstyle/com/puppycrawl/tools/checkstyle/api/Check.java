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
package com.puppycrawl.tools.checkstyle.api;

import java.util.Map;

/**
 * The base class for checks.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public abstract class Check
{
    /** name to store lines under */
    private static final String LINES_ATTRIBUTE = "lines";
    /** name to store filename under */
    private static final String FILENAME_ATTRIBUTE = "filename";

    /** the global context for the check */
    private Map mGlobalContext;
    /** the context for the check across an AST */
    private Map mTreeContext;
    /** the context for a check across a token. */
    private Map mTokenContext;

    /**
     * Returns the default token a check is interested in. Only used if the
     * configuration for a check does not define the tokens.
     * @return the default tokens
     */
    public abstract int[] getDefaultTokens();

    /**
     * Return the global context object for check. This context is valid for
     * the lifetime of the check.
     * @return the context object
     */
    public Map getGlobalContext()
    {
        return mGlobalContext;
    }

    /**
     * Set the global context for the check.
     * @param aGlobalContext the global context
     */
    public void setGlobalContext(Map aGlobalContext)
    {
        mGlobalContext = aGlobalContext;
    }

    /**
     * Return the tree context object for check. This context is valid for
     * the lifetime of a abstract syntax tree.
     * @return the context object
     */
    public Map getTreeContext()
    {
        return mTreeContext;
    }

    /**
     * Set the tree context for the check.
     * @param aContext the context
     */
    public void setTreeContext(Map aContext)
    {
        mTreeContext = aContext;
    }

    /**
     * Return the tree context object for check. This context is valid for
     * the lifetime of a abstract syntax tree.
     * @return the context object
     */
    public Map getTokenContext()
    {
        return mTokenContext;
    }

    /**
     * Set the token context for the check.
     * @param aContext the global context
     */
    public void setTokenContext(Map aContext)
    {
        mTokenContext = aContext;
    }

    /**
     * Initialse the check. This is the time to verify that the check has
     * everything required to perform it job.
     */
    public void init()
    {

    }

    /**
     * Destroy the check. It is being retired from service.
     */
    public void destroy()
    {
    }

    /**
     * Called before the starting to process a tree. Ideal place to initialise
     * information that is to be collected whilst processing a tree.
     */
    public void beginTree()
    {
    }

    /**
     * Called after finished processing a tree. Ideal place to report on
     * information collected whilst processing a tree.
     */
    public void finishTree()
    {
    }

    /**
     * Called to process a token.
     * @param aAST the token to process
     */
    public void visitToken(DetailAST aAST)
    {
    }

    /**
     * Called after all the child nodes have been process.
     * @param aAST the token leaving
     */
    public void leaveToken(DetailAST aAST)
    {
    }

    /**
     * Set the lines associated with the tree.
     * @param aLines the file contents
     */
    public void setLines(String[] aLines)
    {
        getTreeContext().put(LINES_ATTRIBUTE, aLines);
    }

    /**
     * Returns the lines associated with the tree.
     * @return the file contents
     */
    public String[] getLines()
    {
        return (String[]) getTreeContext().get(LINES_ATTRIBUTE);
    }

    /**
     * Set the name of the file associated with the tree.
     * @param aFilename the file name
     */
    public void setFilename(String aFilename)
    {
        getTreeContext().put(FILENAME_ATTRIBUTE, aFilename);
    }

    /**
     * Returns the filename associated with the tree.
     * @return the file name
     */
    public String getFilename()
    {
        return (String) getTreeContext().get(FILENAME_ATTRIBUTE);
    }

    /** @see needs to be fixed */
    public void log(int aLine, String aMessage)
    {
        final String fname = (String) getTreeContext().get(FILENAME_ATTRIBUTE);
        System.out.println(fname + ":" + aLine + ": " + aMessage);
    }
}
