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

package com.puppycrawl.tools.checkstyle.api;

import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.Utils;

/**
 * The base class for checks.
 *
 * @author Oliver Burn
 * @see <a href="{@docRoot}/../writingchecks.html" target="_top">Writing
 * your own checks</a>
 */
public abstract class Check extends AbstractViolationReporter {
    /** default tab width for column reporting */
    private static final int DEFAULT_TAB_WIDTH = 8;

    /** the current file contents */
    private FileContents fileContents;

    /** the tokens the check is interested in */
    private final Set<String> tokens = Sets.newHashSet();

    /** the object for collecting messages. */
    private LocalizedMessages messages;

    /** the tab width for column reporting */
    private int tabWidth = DEFAULT_TAB_WIDTH; // meaningful default

    /**
     * The class loader to load external classes. Not initialised as this must
     * be set by my creator.
     */
    private ClassLoader classLoader;

    public boolean isCommentNodesRequired() {
        return false;
    }

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
    public int[] getAcceptableTokens() {
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
    public int[] getRequiredTokens() {
        return new int[] {};
    }

    /**
     * Adds a set of tokens the check is interested in.
     * @param strRep the string representation of the tokens interested in
     */
    public final void setTokens(String... strRep) {
        Collections.addAll(tokens, strRep);
    }

    /**
     * Returns the tokens registered for the check.
     * @return the set of token names
     */
    public final Set<String> getTokenNames() {
        return Collections.unmodifiableSet(tokens);
    }

    /**
     * Set the global object used to collect messages.
     * @param messages the messages to log with
     */
    public final void setMessages(LocalizedMessages messages) {
        this.messages = messages;
    }

    /**
     * Initialise the check. This is the time to verify that the check has
     * everything required to perform it job.
     */
    public void init() {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Destroy the check. It is being retired from service.
     */
    public void destroy() {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Called before the starting to process a tree. Ideal place to initialise
     * information that is to be collected whilst processing a tree.
     * @param rootAST the root of the tree
     */
    public void beginTree(DetailAST rootAST) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Called after finished processing a tree. Ideal place to report on
     * information collected whilst processing a tree.
     * @param rootAST the root of the tree
     */
    public void finishTree(DetailAST rootAST) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Called to process a token.
     * @param ast the token to process
     */
    public void visitToken(DetailAST ast) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Called after all the child nodes have been process.
     * @param ast the token leaving
     */
    public void leaveToken(DetailAST ast) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Returns the lines associated with the tree.
     * @return the file contents
     */
    public final String[] getLines() {
        return getFileContents().getLines();
    }

    /**
     * Returns the line associated with the tree.
     * @param index index of the line
     * @return the line from the file contents
     */
    public final String getLine(int index) {
        return getFileContents().getLine(index);
    }

    /**
     * Set the file contents associated with the tree.
     * @param contents the manager
     */
    public final void setFileContents(FileContents contents) {
        fileContents = contents;
    }

    /**
     * Returns the file contents associated with the tree.
     * @return the file contents
     */
    public final FileContents getFileContents() {
        return fileContents;
    }

    /**
     * Set the class loader associated with the tree.
     * @param classLoader the class loader
     */
    public final void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Returns the class loader associated with the tree.
     * @return the class loader
     */
    public final ClassLoader getClassLoader() {
        return classLoader;
    }

    /** @return the tab width to report errors with */
    protected final int getTabWidth() {
        return tabWidth;
    }

    /**
     * Set the tab width to report errors with.
     * @param tabWidth an {@code int} value
     */
    public final void setTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
    }

    @Override
    public final void log(int line, String key, Object... args) {
        messages.add(
            new LocalizedMessage(
                line,
                getMessageBundle(),
                key,
                args,
                getSeverityLevel(),
                getId(),
                getClass(),
                getCustomMessages().get(key)));
    }

    @Override
    public final void log(int lineNo, int colNo, String key,
            Object... args) {
        final int col = 1 + Utils.lengthExpandedTabs(
            getLines()[lineNo - 1], colNo, getTabWidth());
        messages.add(
            new LocalizedMessage(
                lineNo,
                col,
                getMessageBundle(),
                key,
                args,
                getSeverityLevel(),
                getId(),
                getClass(),
                getCustomMessages().get(key)));
    }
}
