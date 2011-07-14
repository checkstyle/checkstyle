////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import com.google.common.collect.Sets;
import java.util.Set;

/**
 * The base class for checks.
 *
 * @author Oliver Burn
 * @version 1.0
 * @see <a href="./{@docRoot}/../writingchecks.html" target="_top">Writing
 * your own checks</a>
 */
public abstract class Check extends AbstractViolationReporter
{
    /** default tab width for column reporting */
    private static final int DEFAULT_TAB_WIDTH = 8;

    /** the current file contents */
    private FileContents mFileContents;

    /** the tokens the check is interested in */
    private final Set<String> mTokens = Sets.newHashSet();

    /** the object for collecting messages. */
    private LocalizedMessages mMessages;

    /** the tab width for column reporting */
    private int mTabWidth = DEFAULT_TAB_WIDTH; // meaningful default

    /**
     * The class loader to load external classes. Not initialised as this must
     * be set by my creator.
     */
    private ClassLoader mLoader;

    /**
     * Returns the default token a check is interested in. Only used if the
     * configuration for a check does not define the tokens.
     * @return the default tokens
     * @see TokenTypes
     */
    public abstract int[] getDefaultTokens();

    /**
     * The configurable token set.
     * Used to protect Checks against malicious users who specify an
     * unacceptable token set in the configuration file.
     * The default implementation returns the check's default tokens.
     * @return the token set this check is designed for.
     * @see TokenTypes
     */
    public int[] getAcceptableTokens()
    {
        final int[] defaultTokens = getDefaultTokens();
        final int[] copy = new int[defaultTokens.length];
        System.arraycopy(defaultTokens, 0, copy, 0, defaultTokens.length);
        return copy;
    }

    /**
     * The tokens that this check must be registered for.
     * @return the token set this must be registered for.
     * @see TokenTypes
     */
    public int[] getRequiredTokens()
    {
        return new int[] {};
    }

    /**
     * Adds a set of tokens the check is interested in.
     * @param aStrRep the string representation of the tokens interested in
     */
    public final void setTokens(String[] aStrRep)
    {
        for (final String s : aStrRep) {
            mTokens.add(s);
        }
    }

    /**
     * Returns the tokens registered for the check.
     * @return the set of token names
     */
    public final Set<String> getTokenNames()
    {
        return mTokens;
    }

    /**
     * Set the global object used to collect messages.
     * @param aMessages the messages to log with
     */
    public final void setMessages(LocalizedMessages aMessages)
    {
        mMessages = aMessages;
    }

    /**
     * Initialise the check. This is the time to verify that the check has
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
     * @param aRootAST the root of the tree
     */
    public void beginTree(DetailAST aRootAST)
    {
    }

    /**
     * Called after finished processing a tree. Ideal place to report on
     * information collected whilst processing a tree.
     * @param aRootAST the root of the tree
     */
    public void finishTree(DetailAST aRootAST)
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
     * Returns the lines associated with the tree.
     * @return the file contents
     */
    public final String[] getLines()
    {
        return getFileContents().getLines();
    }

    /**
     * Set the file contents associated with the tree.
     * @param aContents the manager
     */
    public final void setFileContents(FileContents aContents)
    {
        mFileContents = aContents;
    }

    /**
     * Returns the file contents associated with the tree.
     * @return the file contents
     */
    public final FileContents getFileContents()
    {
        return mFileContents;
    }

    /**
     * Set the class loader associated with the tree.
     * @param aLoader the class loader
     */
    public final void setClassLoader(ClassLoader aLoader)
    {
        mLoader = aLoader;
    }

    /**
     * Returns the class loader associated with the tree.
     * @return the class loader
     */
    public final ClassLoader getClassLoader()
    {
        return mLoader;
    }

    /** @return the tab width to report errors with */
    protected final int getTabWidth()
    {
        return mTabWidth;
    }

    /**
     * Set the tab width to report errors with.
     * @param aTabWidth an <code>int</code> value
     */
    public final void setTabWidth(int aTabWidth)
    {
        mTabWidth = aTabWidth;
    }

    @Override
    public final void log(int aLine, String aKey, Object... aArgs)
    {
        mMessages.add(
            new LocalizedMessage(
                aLine,
                getMessageBundle(),
                aKey,
                aArgs,
                getSeverityLevel(),
                getId(),
                this.getClass(),
                this.getCustomMessages().get(aKey)));
    }


    @Override
    public final void log(int aLineNo, int aColNo, String aKey,
            Object... aArgs)
    {
        final int col = 1 + Utils.lengthExpandedTabs(
            getLines()[aLineNo - 1], aColNo, getTabWidth());
        mMessages.add(
            new LocalizedMessage(
                aLineNo,
                col,
                getMessageBundle(),
                aKey,
                aArgs,
                getSeverityLevel(),
                getId(),
                this.getClass(),
                this.getCustomMessages().get(aKey)));
    }
}
