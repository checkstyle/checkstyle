////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * The base class for checks.
 *
 * @see <a href="{@docRoot}/../writingchecks.html" target="_top">Writing
 *     your own checks</a>
 * @noinspection NoopMethodInAbstractClass
 * @noinspectionreason NoopMethodInAbstractClass - we allow each check to
 *      define these methods, as needed. They should be overridden only
 *      by demand in subclasses
 */
public abstract class AbstractCheck extends AbstractViolationReporter {

    /**
     * The check context.
     *
     * @noinspection ThreadLocalNotStaticFinal
     * @noinspectionreason ThreadLocalNotStaticFinal - static context
     *      is problematic for multithreading
     */
    private final ThreadLocal<FileContext> context = ThreadLocal.withInitial(FileContext::new);

    /** The tokens the check is interested in. */
    private final Set<String> tokens = new HashSet<>();

    /**
     * The tab width for column reporting. Default is uninitialized as the value is inherited from
     * the parent module.
     */
    private int tabWidth;

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
     * @noinspectionreason WeakerAccess - we avoid 'protected' when possible
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
     * Returns the sorted set of {@link Violation}.
     *
     * @return the sorted set of {@link Violation}.
     */
    public SortedSet<Violation> getViolations() {
        return new TreeSet<>(context.get().violations);
    }

    /**
     * Clears the sorted set of {@link Violation} of the check.
     */
    public final void clearViolations() {
        context.get().violations.clear();
    }

    /**
     * Initialize the check. This is the time to verify that the check has
     * everything required to perform its job.
     */
    public void init() {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Destroy the check. It is being retired from service.
     */
    public void destroy() {
        context.remove();
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
     * Set the file contents associated with the tree.
     *
     * @param contents the manager
     */
    public final void setFileContents(FileContents contents) {
        context.get().fileContents = contents;
    }

    /**
     * Returns the file contents associated with the tree.
     *
     * @return the file contents
     * @deprecated
     *      Usage of this method is no longer accepted.
     *      Please use AST based methods instead.
     * @noinspection WeakerAccess
     * @noinspectionreason WeakerAccess - we avoid 'protected' when possible
     */
    @Deprecated(since = "9.3")
    public final FileContents getFileContents() {
        return context.get().fileContents;
    }

    /**
     * Get tab width to report audit events with.
     *
     * @return the tab width to audit events with
     */
    protected final int getTabWidth() {
        return tabWidth;
    }

    /**
     * Set the tab width to report audit events with.
     *
     * @param tabWidth an {@code int} value
     */
    public final void setTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
    }

    @Override
    public final void log(int line, String key, Object... args) {
        context.get().violations.add(
            new Violation(
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
        final int col = 1 + CommonUtil.lengthExpandedTabs(
            getLines()[lineNo - 1], colNo, tabWidth);
        context.get().violations.add(
            new Violation(
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
            getLines()[ast.getLineNo() - 1], ast.getColumnNo(), tabWidth);
        context.get().violations.add(
            new Violation(
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
     * Returns the lines associated with the tree.
     *
     * @return the file contents
     */
    public final String[] getLines() {
        return context.get().fileContents.getLines();
    }

    /**
     * Returns the line associated with the tree.
     *
     * @param index index of the line
     * @return the line from the file contents
     */
    public final String getLine(int index) {
        return context.get().fileContents.getLine(index);
    }

    /**
     * Returns full path to the file.
     *
     * @return full path to file.
     */
    public final String getFilePath() {
        return context.get().fileContents.getFileName();
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

    /**
     * The actual context holder.
     */
    private static final class FileContext {

        /** The sorted set for collecting violations. */
        private final SortedSet<Violation> violations = new TreeSet<>();

        /** The current file contents. */
        private FileContents fileContents;

    }

}
