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
    /** resuable constant for message formating */
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /** name to store lines under */
    private static final String LINES_ATTRIBUTE = "lines";
    /** name to store filename under */
    private static final String FILENAME_ATTRIBUTE = "filename";

    /** the global context for the check */
    private Map mGlobalContext;
    /**
     * the object for collecting messages. decided to not put in the global
     * context for performance and ease of use.
     */
    private LocalizedMessages mMessages;
    /** the context for the check across an AST */
    private Map mTreeContext;
    /** the context for a check across a token. */
    private Map mTokenContext;
    /** the tab with for column reporting */
    private int mTabWidth = 8; // meaningful default

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
     * @param aContext the context
     */
    public void setGlobalContext(Map aContext)
    {
        mGlobalContext = aContext;
    }

    /**
     * Set the global object used to collect messages.
     * @param aMessages the messages to log with
     */
    public void setMessages(LocalizedMessages aMessages)
    {
        mMessages = aMessages;
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
    public final void setLines(String[] aLines)
    {
        getTreeContext().put(LINES_ATTRIBUTE, aLines);
    }

    /**
     * Returns the lines associated with the tree.
     * @return the file contents
     */
    public final String[] getLines()
    {
        return (String[]) getTreeContext().get(LINES_ATTRIBUTE);
    }

    /**
     * Set the name of the file associated with the tree.
     * @param aFilename the file name
     */
    public final void setFilename(String aFilename)
    {
        getTreeContext().put(FILENAME_ATTRIBUTE, aFilename);
    }

    /** @return the tab width to report errors with */
    public int getTabWidth()
    {
        return mTabWidth;
    }

    /**
     * Set the tab width to report errors with
     * @param aTabWidth an <code>int</code> value
     */
    public void setTabWidth(int aTabWidth)
    {
        mTabWidth = aTabWidth;
    }

    /**
     * Returns the filename associated with the tree.
     * @return the file name
     */
    public final String getFilename()
    {
        return (String) getTreeContext().get(FILENAME_ATTRIBUTE);
    }

    /**
     * Log an error message.
     *
     * @param aLine the line number where the error was found
     * @param aKey the message that describes the error
     */
    public final void log(int aLine, String aKey)
    {
        log(aLine, aKey, EMPTY_OBJECT_ARRAY);
    }

    /**
     * Log an error message.
     *
     * @param aLine the line number where the error was found
     * @param aKey the message that describes the error
     * @param aArgs the details of the message
     *
     * @see java.text.MessageFormat
     */
    protected final void log(int aLine, String aKey, Object aArgs[])
    {
        mMessages.add(new LocalizedMessage(
                aLine, getResourceBundle(), aKey, aArgs));
    }


    /**
     * Helper method to log a LocalizedMessage. Column defaults to 0.
     *
     * @param aLineNo line number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 first argument
     */
    public void log(int aLineNo, String aKey, Object aArg0)
    {
        log(aLineNo, aKey, new Object[] {aArg0});
    }

    /**
     * Helper method to log a LocalizedMessage. Column defaults to 0.
     *
     * @param aLineNo line number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 first argument
     * @param aArg1 second argument
     */
    public void log(int aLineNo, String aKey, Object aArg0, Object aArg1)
    {
        log(aLineNo, aKey, new Object[] {aArg0, aArg1});
    }

    /**
     * Helper method to log a LocalizedMessage. Column defaults to 0.
     *
     * @param aLineNo line number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 first argument
     * @param aArg1 second argument
     * @param aArg2 third argument
     */
    public void log(int aLineNo, String aKey,
             Object aArg0, Object aArg1, Object aArg2)
    {
        log(aLineNo, aKey, new Object[] {aArg0, aArg1, aArg2});
    }


    /**
     * Helper method to log a LocalizedMessage.
     *
     * @param aLineNo line number to associate with the message
     * @param aColNo column number to associate with the message
     * @param aKey key to locale message format
     */
    public void log(int aLineNo, int aColNo, String aKey)
    {
        log(aLineNo, aColNo, aKey, EMPTY_OBJECT_ARRAY);
    }

    /**
     * Helper method to log a LocalizedMessage.
     *
     * @param aLineNo line number to associate with the message
     * @param aColNo column number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 an <code>Object</code> value
     */
    public void log(int aLineNo, int aColNo, String aKey,
                    Object aArg0)
    {
        log(aLineNo, aColNo, aKey, new Object[] {aArg0});
    }

    /**
     * Helper method to log a LocalizedMessage.
     *
     * @param aLineNo line number to associate with the message
     * @param aColNo column number to associate with the message
     * @param aKey key to locale message format
     * @param aArg0 an <code>Object</code> value
     * @param aArg1 an <code>Object</code> value
     */
    public void log(int aLineNo, int aColNo, String aKey,
                    Object aArg0, Object aArg1)
    {
        log(aLineNo, aColNo, aKey, new Object[] {aArg0, aArg1});
    }

    /**
     * Helper method to log a LocalizedMessage.
     *
     * @param aLineNo line number to associate with the message
     * @param aColNo column number to associate with the message
     * @param aKey key to locale message format
     * @param aArgs arguments for message
     */
    public void log(int aLineNo, int aColNo, String aKey, Object[] aArgs)
    {
        final int col = 1 + Utils.lengthExpandedTabs(
            getLines()[aLineNo - 1], aColNo, getTabWidth());
        mMessages.add(new LocalizedMessage(
            aLineNo, col, getResourceBundle(), aKey, aArgs));
    }


    /**
     * TODO: Should this method be protected or should we keep the api simple?
     * Returns the name of a a resource bundle that contains the messages
     * used by this check.
     *
     * The default implementation expects the resource files to be named
     * messages.properties, messages_de.properties, etc. The file should
     * be placed in the same package as the Check implementation.
     *
     * Example: If you write com/foo/MyCoolCheck, create resource files
     * com/foo/messages.properties, com/foo/messages_de.properties, etc.
     *
     * @return name of a resource bundle that contains the messages
     * used by this check
     */
    private String getResourceBundle()
    {
        // PERF: check perf impact, maybe cache result
        final String className = this.getClass().getName();
        final String packageName =
                className.substring(0, className.lastIndexOf('.'));
        return packageName + "." + "messages";
    }
}
