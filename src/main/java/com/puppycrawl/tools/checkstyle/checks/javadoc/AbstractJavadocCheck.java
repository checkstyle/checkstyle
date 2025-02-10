///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser;
import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.ParseErrorMessage;
import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.ParseStatus;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * Base class for Checks that process Javadoc comments.
 *
 * @noinspection NoopMethodInAbstractClass
 * @noinspectionreason NoopMethodInAbstractClass - we allow each
 *      check to define these methods, as needed. They
 *      should be overridden only by demand in subclasses
 */
public abstract class AbstractJavadocCheck extends AbstractCheck {

    /**
     * Message key of error message. Missed close HTML tag breaks structure
     * of parse tree, so parser stops parsing and generates such error
     * message. This case is special because parser prints error like
     * {@code "no viable alternative at input 'b \n *\n'"} and it is not
     * clear that error is about missed close HTML tag.
     */
    public static final String MSG_JAVADOC_MISSED_HTML_CLOSE =
            JavadocDetailNodeParser.MSG_JAVADOC_MISSED_HTML_CLOSE;

    /**
     * Message key of error message.
     */
    public static final String MSG_JAVADOC_WRONG_SINGLETON_TAG =
            JavadocDetailNodeParser.MSG_JAVADOC_WRONG_SINGLETON_TAG;

    /**
     * Parse error while rule recognition.
     */
    public static final String MSG_JAVADOC_PARSE_RULE_ERROR =
            JavadocDetailNodeParser.MSG_JAVADOC_PARSE_RULE_ERROR;

    /**
     * Message key of error message.
     */
    public static final String MSG_KEY_UNCLOSED_HTML_TAG =
            JavadocDetailNodeParser.MSG_UNCLOSED_HTML_TAG;

    /**
     * Key is the block comment node "lineNo". Value is {@link DetailNode} tree.
     * Map is stored in {@link ThreadLocal}
     * to guarantee basic thread safety and avoid shared, mutable state when not necessary.
     */
    private static final ThreadLocal<Map<Integer, ParseStatus>> TREE_CACHE =
            ThreadLocal.withInitial(HashMap::new);

    /**
     * The file context.
     *
     * @noinspection ThreadLocalNotStaticFinal
     * @noinspectionreason ThreadLocalNotStaticFinal - static context is
     *       problematic for multithreading
     */
    private final ThreadLocal<FileContext> context = ThreadLocal.withInitial(FileContext::new);

    /** The javadoc tokens the check is interested in. */
    private final Set<Integer> javadocTokens = new HashSet<>();

    /**
     * This property determines if a check should log a violation upon encountering javadoc with
     * non-tight html. The default return value for this method is set to false since checks
     * generally tend to be fine with non-tight html. It can be set through config file if a check
     * is to log violation upon encountering non-tight HTML in javadoc.
     *
     * @see ParseStatus#isNonTight()
     * @see <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
     *     Tight HTML rules</a>
     */
    private boolean violateExecutionOnNonTightHtml;

    /**
     * Returns the default javadoc token types a check is interested in.
     *
     * @return the default javadoc token types
     * @see JavadocTokenTypes
     */
    public abstract int[] getDefaultJavadocTokens();

    /**
     * Called to process a Javadoc token.
     *
     * @param ast
     *        the token to process
     */
    public abstract void visitJavadocToken(DetailNode ast);

    /**
     * The configurable javadoc token set.
     * Used to protect Checks against malicious users who specify an
     * unacceptable javadoc token set in the configuration file.
     * The default implementation returns the check's default javadoc tokens.
     *
     * @return the javadoc token set this check is designed for.
     * @see JavadocTokenTypes
     */
    public int[] getAcceptableJavadocTokens() {
        final int[] defaultJavadocTokens = getDefaultJavadocTokens();
        final int[] copy = new int[defaultJavadocTokens.length];
        System.arraycopy(defaultJavadocTokens, 0, copy, 0, defaultJavadocTokens.length);
        return copy;
    }

    /**
     * The javadoc tokens that this check must be registered for.
     *
     * @return the javadoc token set this must be registered for.
     * @see JavadocTokenTypes
     */
    public int[] getRequiredJavadocTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    /**
     * This method determines if a check should process javadoc containing non-tight html tags.
     * This method must be overridden in checks extending {@code AbstractJavadocCheck} which
     * are not supposed to process javadoc containing non-tight html tags.
     *
     * @return true if the check should or can process javadoc containing non-tight html tags;
     *     false otherwise
     * @see ParseStatus#isNonTight()
     * @see <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
     *     Tight HTML rules</a>
     */
    public boolean acceptJavadocWithNonTightHtml() {
        return true;
    }

    /**
     * Setter to control when to print violations if the Javadoc being examined by this check
     * violates the tight html rules defined at
     * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
     *     Tight-HTML Rules</a>.
     *
     * @param shouldReportViolation value to which the field shall be set to
     * @since 8.3
     */
    public final void setViolateExecutionOnNonTightHtml(boolean shouldReportViolation) {
        violateExecutionOnNonTightHtml = shouldReportViolation;
    }

    /**
     * Adds a set of tokens the check is interested in.
     *
     * @param strRep the string representation of the tokens interested in
     */
    public final void setJavadocTokens(String... strRep) {
        for (String str : strRep) {
            javadocTokens.add(JavadocUtil.getTokenId(str));
        }
    }

    @Override
    public void init() {
        validateDefaultJavadocTokens();
        if (javadocTokens.isEmpty()) {
            javadocTokens.addAll(
                    Arrays.stream(getDefaultJavadocTokens()).boxed()
                        .collect(Collectors.toUnmodifiableList()));
        }
        else {
            final int[] acceptableJavadocTokens = getAcceptableJavadocTokens();
            Arrays.sort(acceptableJavadocTokens);
            for (Integer javadocTokenId : javadocTokens) {
                if (Arrays.binarySearch(acceptableJavadocTokens, javadocTokenId) < 0) {
                    final String message = String.format(Locale.ROOT, "Javadoc Token \"%s\" was "
                            + "not found in Acceptable javadoc tokens list in check %s",
                            JavadocUtil.getTokenName(javadocTokenId), getClass().getName());
                    throw new IllegalStateException(message);
                }
            }
        }
    }

    /**
     * Validates that check's required javadoc tokens are subset of default javadoc tokens.
     *
     * @throws IllegalStateException when validation of default javadoc tokens fails
     */
    private void validateDefaultJavadocTokens() {
        final Set<Integer> defaultTokens = Arrays.stream(getDefaultJavadocTokens())
                .boxed()
                .collect(Collectors.toUnmodifiableSet());

        final List<Integer> missingRequiredTokenNames = Arrays.stream(getRequiredJavadocTokens())
                .boxed()
                .filter(token -> !defaultTokens.contains(token))
                .collect(Collectors.toUnmodifiableList());

        if (!missingRequiredTokenNames.isEmpty()) {
            final String message = String.format(Locale.ROOT,
                        "Javadoc Token \"%s\" from required javadoc "
                            + "tokens was not found in default "
                            + "javadoc tokens list in check %s",
                        missingRequiredTokenNames.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")),
                        getClass().getName());
            throw new IllegalStateException(message);
        }
    }

    /**
     * Called before the starting to process a tree.
     *
     * @param rootAst
     *        the root of the tree
     * @noinspection WeakerAccess
     * @noinspectionreason WeakerAccess - we avoid 'protected' when possible
     */
    public void beginJavadocTree(DetailNode rootAst) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Called after finished processing a tree.
     *
     * @param rootAst
     *        the root of the tree
     * @noinspection WeakerAccess
     * @noinspectionreason WeakerAccess - we avoid 'protected' when possible
     */
    public void finishJavadocTree(DetailNode rootAst) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Called after all the child nodes have been process.
     *
     * @param ast
     *        the token leaving
     */
    public void leaveJavadocToken(DetailNode ast) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Defined final to not allow JavadocChecks to change default tokens.
     *
     * @return default tokens
     */
    @Override
    public final int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public final int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN };
    }

    /**
     * Defined final because all JavadocChecks require comment nodes.
     *
     * @return true
     */
    @Override
    public final boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public final void beginTree(DetailAST rootAST) {
        TREE_CACHE.get().clear();
    }

    @Override
    public final void finishTree(DetailAST rootAST) {
        // No code, prevent override in subclasses
    }

    @Override
    public final void visitToken(DetailAST blockCommentNode) {
        if (JavadocUtil.isJavadocComment(blockCommentNode)) {
            // store as field, to share with child Checks
            context.get().blockCommentAst = blockCommentNode;

            final int treeCacheKey = blockCommentNode.getLineNo();

            final ParseStatus result = TREE_CACHE.get()
                    .computeIfAbsent(treeCacheKey, lineNumber -> {
                        return context.get().parser.parseJavadocAsDetailNode(blockCommentNode);
                    });

            if (result.getParseErrorMessage() == null) {
                if (acceptJavadocWithNonTightHtml() || !result.isNonTight()) {
                    processTree(result.getTree());
                }

                if (violateExecutionOnNonTightHtml && result.isNonTight()) {
                    log(result.getFirstNonTightHtmlTag().getLine(),
                            MSG_KEY_UNCLOSED_HTML_TAG,
                            result.getFirstNonTightHtmlTag().getText());
                }
            }
            else {
                final ParseErrorMessage parseErrorMessage = result.getParseErrorMessage();
                log(parseErrorMessage.getLineNumber(),
                        parseErrorMessage.getMessageKey(),
                        parseErrorMessage.getMessageArguments());
            }
        }
    }

    /**
     * Getter for block comment in Java language syntax tree.
     *
     * @return A block comment in the syntax tree.
     */
    protected DetailAST getBlockCommentAst() {
        return context.get().blockCommentAst;
    }

    /**
     * Processes JavadocAST tree notifying Check.
     *
     * @param root
     *        root of JavadocAST tree.
     */
    private void processTree(DetailNode root) {
        beginJavadocTree(root);
        walk(root);
        finishJavadocTree(root);
    }

    /**
     * Processes a node calling Check at interested nodes.
     *
     * @param root
     *        the root of tree for process
     */
    private void walk(DetailNode root) {
        DetailNode curNode = root;
        while (curNode != null) {
            final boolean waitsForProcessing = shouldBeProcessed(curNode);

            if (waitsForProcessing) {
                visitJavadocToken(curNode);
            }
            DetailNode toVisit = JavadocUtil.getFirstChild(curNode);
            while (curNode != null && toVisit == null) {
                if (waitsForProcessing) {
                    leaveJavadocToken(curNode);
                }

                toVisit = JavadocUtil.getNextSibling(curNode);
                curNode = curNode.getParent();
            }
            curNode = toVisit;
        }
    }

    /**
     * Checks whether the current node should be processed by the check.
     *
     * @param curNode current node.
     * @return true if the current node should be processed by the check.
     */
    private boolean shouldBeProcessed(DetailNode curNode) {
        return javadocTokens.contains(curNode.getType());
    }

    @Override
    public void destroy() {
        super.destroy();
        context.remove();
        TREE_CACHE.remove();
    }

    /**
     * The file context holder.
     */
    private static final class FileContext {

        /**
         * Parses content of Javadoc comment as DetailNode tree.
         */
        private final JavadocDetailNodeParser parser = new JavadocDetailNodeParser();

        /**
         * DetailAST node of considered Javadoc comment that is just a block comment
         * in Java language syntax tree.
         */
        private DetailAST blockCommentAst;

    }

}
