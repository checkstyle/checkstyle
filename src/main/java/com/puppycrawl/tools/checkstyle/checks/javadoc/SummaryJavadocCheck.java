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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that
 * <a href="https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#firstsentence">
 * Javadoc summary sentence</a> does not contain phrases that are not recommended to use.
 * Summaries that contain only the {@code {@inheritDoc}} tag are skipped.
 * Summaries that contain a non-empty {@code {@return}} are allowed.
 * Check also violate Javadoc that does not contain first sentence, though with {@code {@return}} a
 * period is not required as the Javadoc tool adds it.
 * <br/><br/>
 * Note: In past, summary could only be implemented using the first sentence of each Javadoc.
 * Now,we can also denote summary by the corresponding @summary tag. We support both
 * approaches.
 * </div>
 *
 * <ul>
 * <li>
 * Property {@code forbiddenSummaryFragments} - Specify the regexp for forbidden summary fragments.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
 * </li>
 * <li>
 * Property {@code period} - Specify the period symbol. Used to check the first sentence ends with a
 * period. Periods that are not followed by a whitespace character are ignored (eg. the period in
 * v1.0). Because some periods include whitespace built into the character, if this is set to a
 * non-default value any period will end the sentence, whether it is followed by whitespace or not.
 * Type is {@code java.lang.String}.
 * Default value is {@code "."}.
 * </li>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.unclosedHtml}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * <li>
 * {@code summary.first.sentence}
 * </li>
 * <li>
 * {@code summary.javaDoc}
 * </li>
 * <li>
 * {@code summary.javaDoc.missing}
 * </li>
 * <li>
 * {@code summary.javaDoc.missing.period}
 * </li>
 * </ul>
 *
 * @since 6.0
 */
@StatelessCheck
public class SummaryJavadocCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SUMMARY_FIRST_SENTENCE = "summary.first.sentence";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SUMMARY_JAVADOC = "summary.javaDoc";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SUMMARY_JAVADOC_MISSING = "summary.javaDoc.missing";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_SUMMARY_MISSING_PERIOD = "summary.javaDoc.missing.period";

    /**
     * This regexp is used to convert multiline javadoc to single-line without stars.
     */
    private static final Pattern JAVADOC_MULTILINE_TO_SINGLELINE_PATTERN =
            Pattern.compile("\n +(\\*)|^ +(\\*)");

    /**
     * This regexp is used to remove html tags, whitespace, and asterisks from a string.
     */
    private static final Pattern HTML_ELEMENTS =
            Pattern.compile("<[^>]*>");

    /** Default period literal. */
    private static final String DEFAULT_PERIOD = ".";

    /** Summary tag text. */
    private static final String SUMMARY_TEXT = "@summary";

    /** Return tag text. */
    private static final String RETURN_TEXT = "@return";

    /** Set of allowed Tokens tags in summary java doc. */
    private static final BitSet ALLOWED_TYPES = TokenUtil.asBitSet(
                    JavadocTokenTypes.WS,
                    JavadocTokenTypes.DESCRIPTION,
                    JavadocTokenTypes.TEXT);

    /**
     * Specify the regexp for forbidden summary fragments.
     */
    private Pattern forbiddenSummaryFragments = CommonUtil.createPattern("^$");

    /**
     * Specify the period symbol. Used to check the first sentence ends with a period. Periods that
     * are not followed by a whitespace character are ignored (eg. the period in v1.0). Because some
     * periods include whitespace built into the character, if this is set to a non-default value
     * any period will end the sentence, whether it is followed by whitespace or not.
     */
    private String period = DEFAULT_PERIOD;

    /**
     * Setter to specify the regexp for forbidden summary fragments.
     *
     * @param pattern a pattern.
     * @since 6.0
     */
    public void setForbiddenSummaryFragments(Pattern pattern) {
        forbiddenSummaryFragments = pattern;
    }

    /**
     * Setter to specify the period symbol. Used to check the first sentence ends with a period.
     * Periods that are not followed by a whitespace character are ignored (eg. the period in v1.0).
     * Because some periods include whitespace built into the character, if this is set to a
     * non-default value any period will end the sentence, whether it is followed by whitespace or
     * not.
     *
     * @param period period's value.
     * @since 6.2
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final Optional<DetailNode> inlineTagNode = getInlineTagNode(ast);
        boolean shouldValidateUntaggedSummary = true;
        if (inlineTagNode.isPresent()) {
            final DetailNode node = inlineTagNode.get();
            if (isSummaryTag(node) && isDefinedFirst(node)) {
                shouldValidateUntaggedSummary = false;
                validateSummaryTag(node);
            }
            else if (isInlineReturnTag(node)) {
                shouldValidateUntaggedSummary = false;
                validateInlineReturnTag(node);
            }
        }
        if (shouldValidateUntaggedSummary && !startsWithInheritDoc(ast)) {
            validateUntaggedSummary(ast);
        }
    }

    /**
     * Checks the javadoc text for {@code period} at end and forbidden fragments.
     *
     * @param ast the javadoc text node
     */
    private void validateUntaggedSummary(DetailNode ast) {
        final String summaryDoc = getSummarySentence(ast);
        if (summaryDoc.isEmpty()) {
            log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC_MISSING);
        }
        else if (!period.isEmpty()) {
            if (summaryDoc.contains(period)) {
                final Optional<String> firstSentence = getFirstSentence(ast, period);

                if (firstSentence.isPresent()) {
                    if (containsForbiddenFragment(firstSentence.get())) {
                        log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC);
                    }
                }
                else {
                    log(ast.getLineNumber(), MSG_SUMMARY_FIRST_SENTENCE);
                }
            }
            else {
                log(ast.getLineNumber(), MSG_SUMMARY_FIRST_SENTENCE);
            }
        }
    }

    /**
     * Gets the node for the inline tag if present.
     *
     * @param javadoc javadoc root node.
     * @return the node for the inline tag if present.
     */
    private static Optional<DetailNode> getInlineTagNode(DetailNode javadoc) {
        return Arrays.stream(javadoc.getChildren())
            .filter(SummaryJavadocCheck::isInlineTagPresent)
            .findFirst()
            .map(SummaryJavadocCheck::getInlineTagNodeForAst);
    }

    /**
     * Whether the {@code {@summary}} tag is defined first in the javadoc.
     *
     * @param inlineSummaryTag node of type {@link JavadocTokenTypes#JAVADOC_INLINE_TAG}
     * @return {@code true} if the {@code {@summary}} tag is defined first in the javadoc
     */
    private static boolean isDefinedFirst(DetailNode inlineSummaryTag) {
        boolean isDefinedFirst = true;
        DetailNode currentAst = inlineSummaryTag;
        while (currentAst != null && isDefinedFirst) {
            switch (currentAst.getType()) {
                case JavadocTokenTypes.TEXT:
                    isDefinedFirst = currentAst.getText().isBlank();
                    break;
                case JavadocTokenTypes.HTML_ELEMENT:
                    isDefinedFirst = !isTextPresentInsideHtmlTag(currentAst);
                    break;
                default:
                    break;
            }
            currentAst = JavadocUtil.getPreviousSibling(currentAst);
        }
        return isDefinedFirst;
    }

    /**
     * Whether some text is present inside the HTML element or tag.
     *
     * @param node DetailNode of type {@link JavadocTokenTypes#HTML_TAG}
     *             or {@link JavadocTokenTypes#HTML_ELEMENT}
     * @return {@code true} if some text is present inside the HTML element or tag
     */
    public static boolean isTextPresentInsideHtmlTag(DetailNode node) {
        DetailNode nestedChild = JavadocUtil.getFirstChild(node);
        if (node.getType() == JavadocTokenTypes.HTML_ELEMENT) {
            nestedChild = JavadocUtil.getFirstChild(nestedChild);
        }
        boolean isTextPresentInsideHtmlTag = false;
        while (nestedChild != null && !isTextPresentInsideHtmlTag) {
            switch (nestedChild.getType()) {
                case JavadocTokenTypes.TEXT:
                    isTextPresentInsideHtmlTag = !nestedChild.getText().isBlank();
                    break;
                case JavadocTokenTypes.HTML_TAG:
                case JavadocTokenTypes.HTML_ELEMENT:
                    isTextPresentInsideHtmlTag = isTextPresentInsideHtmlTag(nestedChild);
                    break;
                default:
                    break;
            }
            nestedChild = JavadocUtil.getNextSibling(nestedChild);
        }
        return isTextPresentInsideHtmlTag;
    }

    /**
     * Checks if the inline tag node is present.
     *
     * @param ast ast node to check.
     * @return true, if the inline tag node is present.
     */
    private static boolean isInlineTagPresent(DetailNode ast) {
        return getInlineTagNodeForAst(ast) != null;
    }

    /**
     * Returns an inline javadoc tag node that is within a html tag.
     *
     * @param ast html tag node.
     * @return inline summary javadoc tag node or null if no node is found.
     */
    private static DetailNode getInlineTagNodeForAst(DetailNode ast) {
        DetailNode node = ast;
        DetailNode result = null;
        // node can never be null as this method is called when there is a HTML_ELEMENT
        if (node.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
            result = node;
        }
        else if (node.getType() == JavadocTokenTypes.HTML_TAG) {
            // HTML_TAG always has more than 2 children.
            node = node.getChildren()[1];
            result = getInlineTagNodeForAst(node);
        }
        else if (node.getType() == JavadocTokenTypes.HTML_ELEMENT
                // Condition for SINGLETON html element which cannot contain summary node
                && node.getChildren()[0].getChildren().length > 1) {
            // Html elements have one tested tag before actual content inside it
            node = node.getChildren()[0].getChildren()[1];
            result = getInlineTagNodeForAst(node);
        }
        return result;
    }

    /**
     * Checks if the javadoc inline tag is {@code {@summary}} tag.
     *
     * @param javadocInlineTag node of type {@link JavadocTokenTypes#JAVADOC_INLINE_TAG}
     * @return {@code true} if inline tag is summary tag.
     */
    private static boolean isSummaryTag(DetailNode javadocInlineTag) {
        return isInlineTagWithName(javadocInlineTag, SUMMARY_TEXT);
    }

    /**
     * Checks if the first tag inside ast is {@code {@return}} tag.
     *
     * @param javadocInlineTag node of type {@link JavadocTokenTypes#JAVADOC_INLINE_TAG}
     * @return {@code true} if first tag is return tag.
     */
    private static boolean isInlineReturnTag(DetailNode javadocInlineTag) {
        return isInlineTagWithName(javadocInlineTag, RETURN_TEXT);
    }

    /**
     * Checks if the first tag inside ast is a tag with the given name.
     *
     * @param javadocInlineTag node of type {@link JavadocTokenTypes#JAVADOC_INLINE_TAG}
     * @param name name of inline tag.
     *
     * @return {@code true} if first tag is a tag with the given name.
     */
    private static boolean isInlineTagWithName(DetailNode javadocInlineTag, String name) {
        final DetailNode[] child = javadocInlineTag.getChildren();

        // Checking size of ast is not required, since ast contains
        // children of Inline Tag, as at least 2 children will be present which are
        // RCURLY and LCURLY.
        return name.equals(child[1].getText());
    }

    /**
     * Checks the inline summary (if present) for {@code period} at end and forbidden fragments.
     *
     * @param inlineSummaryTag node of type {@link JavadocTokenTypes#JAVADOC_INLINE_TAG}
     */
    private void validateSummaryTag(DetailNode inlineSummaryTag) {
        final String inlineSummary = getContentOfInlineCustomTag(inlineSummaryTag);
        final String summaryVisible = getVisibleContent(inlineSummary);
        if (summaryVisible.isEmpty()) {
            log(inlineSummaryTag.getLineNumber(), MSG_SUMMARY_JAVADOC_MISSING);
        }
        else if (!period.isEmpty()) {
            final boolean isPeriodNotAtEnd =
                    summaryVisible.lastIndexOf(period) != summaryVisible.length() - 1;
            if (isPeriodNotAtEnd) {
                log(inlineSummaryTag.getLineNumber(), MSG_SUMMARY_MISSING_PERIOD);
            }
            else if (containsForbiddenFragment(inlineSummary)) {
                log(inlineSummaryTag.getLineNumber(), MSG_SUMMARY_JAVADOC);
            }
        }
    }

    /**
     * Checks the inline return for forbidden fragments.
     *
     * @param inlineReturnTag node of type {@link JavadocTokenTypes#JAVADOC_INLINE_TAG}
     */
    private void validateInlineReturnTag(DetailNode inlineReturnTag) {
        final String inlineReturn = getContentOfInlineCustomTag(inlineReturnTag);
        final String returnVisible = getVisibleContent(inlineReturn);
        if (returnVisible.isEmpty()) {
            log(inlineReturnTag.getLineNumber(), MSG_SUMMARY_JAVADOC_MISSING);
        }
        else if (containsForbiddenFragment(inlineReturn)) {
            log(inlineReturnTag.getLineNumber(), MSG_SUMMARY_JAVADOC);
        }
    }

    /**
     * Gets the content of inline custom tag.
     *
     * @param inlineTag inline tag node.
     * @return String consisting of the content of inline custom tag.
     */
    public static String getContentOfInlineCustomTag(DetailNode inlineTag) {
        final DetailNode[] childrenOfInlineTag = inlineTag.getChildren();
        final StringBuilder customTagContent = new StringBuilder(256);
        final int indexOfContentOfSummaryTag = 3;
        if (childrenOfInlineTag.length != indexOfContentOfSummaryTag) {
            DetailNode currentNode = childrenOfInlineTag[indexOfContentOfSummaryTag];
            while (currentNode.getType() != JavadocTokenTypes.JAVADOC_INLINE_TAG_END) {
                extractInlineTagContent(currentNode, customTagContent);
                currentNode = JavadocUtil.getNextSibling(currentNode);
            }
        }
        return customTagContent.toString();
    }

    /**
     * Extracts the content of inline custom tag recursively.
     *
     * @param node DetailNode
     * @param customTagContent content of custom tag
     */
    private static void extractInlineTagContent(DetailNode node,
        StringBuilder customTagContent) {
        final DetailNode[] children = node.getChildren();
        if (children.length == 0) {
            customTagContent.append(node.getText());
        }
        else {
            for (DetailNode child : children) {
                if (child.getType() != JavadocTokenTypes.LEADING_ASTERISK) {
                    extractInlineTagContent(child, customTagContent);
                }
            }
        }
    }

    /**
     * Gets the string that is visible to user in javadoc.
     *
     * @param summary entire content of summary javadoc.
     * @return string that is visible to user in javadoc.
     */
    private static String getVisibleContent(String summary) {
        final String visibleSummary = HTML_ELEMENTS.matcher(summary).replaceAll("");
        return visibleSummary.trim();
    }

    /**
     * Tests if first sentence contains forbidden summary fragment.
     *
     * @param firstSentence string with first sentence.
     * @return {@code true} if first sentence contains forbidden summary fragment.
     */
    private boolean containsForbiddenFragment(String firstSentence) {
        final String javadocText = JAVADOC_MULTILINE_TO_SINGLELINE_PATTERN
                .matcher(firstSentence).replaceAll(" ");
        return forbiddenSummaryFragments.matcher(trimExcessWhitespaces(javadocText)).find();
    }

    /**
     * Trims the given {@code text} of duplicate whitespaces.
     *
     * @param text the text to transform.
     * @return the finalized form of the text.
     */
    private static String trimExcessWhitespaces(String text) {
        final StringBuilder result = new StringBuilder(256);
        boolean previousWhitespace = true;

        for (char letter : text.toCharArray()) {
            final char print;
            if (Character.isWhitespace(letter)) {
                if (previousWhitespace) {
                    continue;
                }

                previousWhitespace = true;
                print = ' ';
            }
            else {
                previousWhitespace = false;
                print = letter;
            }

            result.append(print);
        }

        return result.toString();
    }

    /**
     * Checks if the node starts with an {&#64;inheritDoc}.
     *
     * @param root the root node to examine.
     * @return {@code true} if the javadoc starts with an {&#64;inheritDoc}.
     */
    private static boolean startsWithInheritDoc(DetailNode root) {
        boolean found = false;

        for (DetailNode child : root.getChildren()) {
            if (child.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG
                    && child.getChildren()[1].getType() == JavadocTokenTypes.INHERIT_DOC_LITERAL) {
                found = true;
            }
            if ((child.getType() == JavadocTokenTypes.TEXT
                    || child.getType() == JavadocTokenTypes.HTML_ELEMENT)
                    && !CommonUtil.isBlank(child.getText())) {
                break;
            }
        }

        return found;
    }

    /**
     * Finds and returns summary sentence.
     *
     * @param ast javadoc root node.
     * @return violation string.
     */
    private static String getSummarySentence(DetailNode ast) {
        final StringBuilder result = new StringBuilder(256);
        for (DetailNode child : ast.getChildren()) {
            if (child.getType() != JavadocTokenTypes.EOF
                    && ALLOWED_TYPES.get(child.getType())) {
                result.append(child.getText());
            }
            else {
                final String summary = result.toString();
                if (child.getType() == JavadocTokenTypes.HTML_ELEMENT
                        && CommonUtil.isBlank(summary)) {
                    result.append(getStringInsideTag(summary,
                            child.getChildren()[0].getChildren()[0]));
                }
            }
        }
        return result.toString().trim();
    }

    /**
     * Get concatenated string within text of html tags.
     *
     * @param result javadoc string
     * @param detailNode javadoc tag node
     * @return java doc tag content appended in result
     */
    private static String getStringInsideTag(String result, DetailNode detailNode) {
        final StringBuilder contents = new StringBuilder(result);
        DetailNode tempNode = detailNode;
        while (tempNode != null) {
            if (tempNode.getType() == JavadocTokenTypes.TEXT) {
                contents.append(tempNode.getText());
            }
            tempNode = JavadocUtil.getNextSibling(tempNode);
        }
        return contents.toString();
    }

    /**
     * Finds the first sentence.
     *
     * @param ast The Javadoc root node.
     * @param period The configured period symbol.
     * @return An Optional containing the first sentence
     *     up to and excluding the period, or an empty
     *     Optional if no ending was found.
     */
    private static Optional<String> getFirstSentence(DetailNode ast, String period) {
        final List<String> sentenceParts = new ArrayList<>();
        Optional<String> result = Optional.empty();
        for (String text : (Iterable<String>) streamTextParts(ast)::iterator) {
            final Optional<String> sentenceEnding = findSentenceEnding(text, period);

            if (sentenceEnding.isPresent()) {
                sentenceParts.add(sentenceEnding.get());
                result = Optional.of(String.join("", sentenceParts));
                break;
            }
            else {
                sentenceParts.add(text);
            }
        }
        return result;
    }

    /**
     * Streams through all the text under the given node.
     *
     * @param node The Javadoc node to examine.
     * @return All the text in all nodes that have no child nodes.
     */
    private static Stream<String> streamTextParts(DetailNode node) {
        final Stream<String> stream;
        if (node.getChildren().length == 0) {
            stream = Stream.of(node.getText());
        }
        else {
            stream = Stream.of(node.getChildren())
                .flatMap(SummaryJavadocCheck::streamTextParts);
        }
        return stream;
    }

    /**
     * Finds the end of a sentence. The end of sentence detection here could be replaced in the
     * future by Java's built-in BreakIterator class.
     *
     * @param text The string to search.
     * @param period The period character to find.
     * @return An Optional containing the string up to and excluding the period,
     *     or empty Optional if no ending was found.
     */
    private static Optional<String> findSentenceEnding(String text, String period) {
        int periodIndex = text.indexOf(period);
        Optional<String> result = Optional.empty();
        while (periodIndex >= 0) {
            final int afterPeriodIndex = periodIndex + period.length();

            // Handle western period separately as it is only the end of a sentence if followed
            // by whitespace. Other period characters often include whitespace in the character.
            if (!DEFAULT_PERIOD.equals(period)
                || afterPeriodIndex >= text.length()
                || Character.isWhitespace(text.charAt(afterPeriodIndex))) {
                final String resultStr = text.substring(0, periodIndex);
                result = Optional.of(resultStr);
                break;
            }
            else {
                periodIndex = text.indexOf(period, afterPeriodIndex);
            }
        }
        return result;
    }
}
