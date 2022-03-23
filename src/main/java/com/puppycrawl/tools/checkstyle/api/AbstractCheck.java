////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * The base class for checks.
 *
 * @see <a href="{@docRoot}/../writingchecks.html" target="_top">Writing
 * your own checks</a>
 * @noinspection NoopMethodInAbstractClass
 */
public abstract class AbstractCheck extends AbstractViolationReporter {

    /** The tokens the check is interested in. */
    private final Set<String> tokens = new HashSet<>();

    /**
     * Returns the default token a check is interested in. Only used if the
     * configuration for a check does not define the tokens.
     *
     * @return the default tokens
     * @see TokenTypes
     */
    public abstract int[] getDefaultTokens();

    /**
     * The configurable token set.
     * Used to protect Checks against malicious users who specify an
     * unacceptable token set in the configuration file.
     * The default implementation returns the check's default tokens.
     *
     * @return the token set this check is designed for.
     * @see TokenTypes
     */
    public abstract int[] getAcceptableTokens();

    /**
     * The tokens that this check must be registered for.
     *
     * @return the token set this must be registered for.
     * @see TokenTypes
     */
    public abstract int[] getRequiredTokens();

    /**
     * Whether comment nodes are required or not.
     *
     * @return false as a default value.
     */
    public boolean isCommentNodesRequired() {
        return false;
    }

    /**
     * Adds a set of tokens the check is interested in.
     *
     * @param strRep the string representation of the tokens interested in
     * @noinspection WeakerAccess
     */
    public final void setTokens(String... strRep) {
        Collections.addAll(tokens, strRep);
    }

    /**
     * Returns the tokens registered for the check.
     *
     * @return the set of token names
     */
    public final Set<String> getTokenNames() {
        return Collections.unmodifiableSet(tokens);
    }

    /**
     * Initialize the check. This is the time to verify that the check has
     * everything required to perform it job.
     */
    public void init() {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Called before the starting to process a tree. Ideal place to initialize
     * information that is to be collected whilst processing a tree.
     *
     * @param rootAST the root of the tree
     */
    public void beginTree(DetailAST rootAST) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Called after finished processing a tree. Ideal place to report on
     * information collected whilst processing a tree.
     *
     * @param rootAST the root of the tree
     */
    public void finishTree(DetailAST rootAST) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Called to process a token.
     *
     * @param ast the token to process
     */
    public void visitToken(DetailAST ast) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Called after all the child nodes have been process.
     *
     * @param ast the token leaving
     */
    public void leaveToken(DetailAST ast) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Helper method to log a Violation.
     *
     * @param ast a node to get line id column numbers associated
     *             with the violation
     * @param key key to locale violation format
     * @param args arguments to format
     */
    public final void log(DetailAST ast, String key, Object... args) {
        // CommonUtil.lengthExpandedTabs returns column number considering tabulation
        // characters, it takes line from the file by line number, ast column number and tab
        // width as arguments. Returned value is 0-based, but user must see column number starting
        // from 1, that is why result of the method CommonUtil.lengthExpandedTabs
        // is increased by one.

        final int col = 1 + CommonUtil.lengthExpandedTabs(
            getLines()[ast.getLineNo() - 1], ast.getColumnNo(), getTabWidth());
        addViolation(
            Violation.createDetailedViolation(
                        ast.getLineNo(),
                        col,
                        ast.getColumnNo(),
                        ast.getType(),
                        getMessageBundle(),
                        key,
                        args,
                        getSeverityLevel(),
                        getId(),
                        getClass(),
                        getCustomMessages().get(key)));
    }

    /**
     * Returns code point representation of file text from given line number.
     *
     * @param index index of the line
     * @return the array of Unicode code points
     */
    public final int[] getLineCodePoints(int index) {
        return getLine(index).codePoints().toArray();
    }

}
