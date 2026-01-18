///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that
 * <a href="https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#firstsentence">
 * Javadoc summary sentence</a> does not contain phrases that are not recommended to use.
 * Summaries that contain only the {@code {@inheritDoc}} tag are skipped.
 * Summaries that contain a non-empty {@code {@return}} are allowed.
 * Check also violate Javadoc that does not contain first sentence, though with {@code {@return}} a
 * period is not required as the Javadoc tool adds it.
 * </div>
 *
 * <p>
 * Note: For defining a summary, both the first sentence and the @summary tag approaches
 * are supported.
 * </p>
 *
 * @since 6.0
 */
@FileStatefulCheck
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
            Pattern.compile("\n[ \\t]+(\\*)|^[ \\t]+(\\*)");

    /**
     * This regexp is used to remove html tags, whitespace, and asterisks from a string.
     */
    private static final Pattern HTML_ELEMENTS =
            Pattern.compile("<[^>]*>");

    /** Default period literal. */
    private static final String DEFAULT_PERIOD = ".";

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
     * Whether to validate untagged summary text in Javadoc.
     */
    private boolean shouldValidateUntaggedSummary = true;

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
            JavadocCommentsTokenTypes.JAVADOC_CONTENT,
            JavadocCommentsTokenTypes.SUMMARY_INLINE_TAG,
            JavadocCommentsTokenTypes.RETURN_INLINE_TAG,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (isSummaryTag(ast) && isDefinedFirst(ast.getParent())) {
            shouldValidateUntaggedSummary = false;
            validateSummaryTag(ast);
        }
        else if (isInlineReturnTag(ast)) {
            shouldValidateUntaggedSummary = false;
            validateInlineReturnTag(ast);
        }
    }

    @Override
    public void leaveJavadocToken(DetailNode ast) {
        if (ast.getType() == JavadocCommentsTokenTypes.JAVADOC_CONTENT) {
            if (shouldValidateUntaggedSummary && !startsWithInheritDoc(ast)) {
                validateUntaggedSummary(ast);
            }
            shouldValidateUntaggedSummary = true;
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
     * Whether the {@code {@summary}} tag is defined first in the javadoc.
     *
     * @param inlineTagNode node of type {@link JavadocCommentsTokenTypes#JAVADOC_INLINE_TAG}
     * @return {@code true} if the {@code {@summary}} tag is defined first in the javadoc
     */
    private static boolean isDefinedFirst(DetailNode inlineTagNode) {
        boolean isDefinedFirst = true;
        DetailNode currentAst = inlineTagNode.getPreviousSibling();
        while (currentAst != null && isDefinedFirst) {
            switch (currentAst.getType()) {
                case JavadocCommentsTokenTypes.TEXT ->
                    isDefinedFirst = currentAst.getText().isBlank();
                case JavadocCommentsTokenTypes.HTML_ELEMENT ->
                    isDefinedFirst = isHtmlTagWithoutText(currentAst);
                case JavadocCommentsTokenTypes.LEADING_ASTERISK,
                     JavadocCommentsTokenTypes.NEWLINE -> {
                    // Ignore formatting tokens
                }
                default -> isDefinedFirst = false;
            }
            currentAst = currentAst.getPreviousSibling();
        }
        return isDefinedFirst;
    }

    /**
     * Whether some text is present inside the HTML element or tag.
     *
     * @param node DetailNode of type {@link JavadocCommentsTokenTypes#HTML_ELEMENT}
     * @return {@code true} if some text is present inside the HTML element
     */
    public static boolean isHtmlTagWithoutText(DetailNode node) {
        boolean isEmpty = true;
        final DetailNode htmlContentToken =
             JavadocUtil.findFirstToken(node, JavadocCommentsTokenTypes.HTML_CONTENT);

        if (htmlContentToken != null) {
            final DetailNode child = htmlContentToken.getFirstChild();
            isEmpty = child.getType() == JavadocCommentsTokenTypes.HTML_ELEMENT
                        && isHtmlTagWithoutText(child);
        }
        return isEmpty;
    }

    /**
     * Checks if the given node is an inline summary tag.
     *
     * @param javadocInlineTag node
     * @return {@code true} if inline tag is of
     *       type {@link JavadocCommentsTokenTypes#SUMMARY_INLINE_TAG}
     */
    private static boolean isSummaryTag(DetailNode javadocInlineTag) {
        return javadocInlineTag.getType() == JavadocCommentsTokenTypes.SUMMARY_INLINE_TAG;
    }

    /**
     * Checks if the given node is an inline return node.
     *
     * @param javadocInlineTag node
     * @return {@code true} if inline tag is of
     *       type {@link JavadocCommentsTokenTypes#RETURN_INLINE_TAG}
     */
    private static boolean isInlineReturnTag(DetailNode javadocInlineTag) {
        return javadocInlineTag.getType() == JavadocCommentsTokenTypes.RETURN_INLINE_TAG;
    }

    /**
     * Checks the inline summary (if present) for {@code period} at end and forbidden fragments.
     *
     * @param inlineSummaryTag node of type {@link JavadocCommentsTokenTypes#SUMMARY_INLINE_TAG}
     */
    private void validateSummaryTag(DetailNode inlineSummaryTag) {
        final DetailNode descriptionNode = JavadocUtil.findFirstToken(
                inlineSummaryTag, JavadocCommentsTokenTypes.DESCRIPTION);
        final String inlineSummary = getContentOfInlineCustomTag(descriptionNode);
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
     * @param inlineReturnTag node of type {@link JavadocCommentsTokenTypes#RETURN_INLINE_TAG}
     */
    private void validateInlineReturnTag(DetailNode inlineReturnTag) {
        final DetailNode descriptionNode = JavadocUtil.findFirstToken(
                inlineReturnTag, JavadocCommentsTokenTypes.DESCRIPTION);
        final String inlineReturn = getContentOfInlineCustomTag(descriptionNode);
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
     * @param descriptionNode node of type {@link JavadocCommentsTokenTypes#DESCRIPTION}
     * @return String consisting of the content of inline custom tag.
     */
    public static String getContentOfInlineCustomTag(DetailNode descriptionNode) {
        final StringBuilder customTagContent = new StringBuilder(256);
        DetailNode curNode = descriptionNode;
        while (curNode != null) {
            if (curNode.getFirstChild() == null
                && curNode.getType() != JavadocCommentsTokenTypes.LEADING_ASTERISK) {
                customTagContent.append(curNode.getText());
            }

            DetailNode toVisit = curNode.getFirstChild();
            while (curNode != descriptionNode && toVisit == null) {
                toVisit = curNode.getNextSibling();
                curNode = curNode.getParent();
            }

            curNode = toVisit;
        }
        return customTagContent.toString();
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

        for (int index = 0; index < text.length(); index++) {
            final char letter = text.charAt(index);
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
        DetailNode node = root.getFirstChild();

        while (node != null) {
            if (node.getType() == JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG
                    && node.getFirstChild().getType()
                            == JavadocCommentsTokenTypes.INHERIT_DOC_INLINE_TAG) {
                found = true;
            }
            if ((node.getType() == JavadocCommentsTokenTypes.TEXT
                    || node.getType() == JavadocCommentsTokenTypes.HTML_ELEMENT)
                    && !CommonUtil.isBlank(node.getText())) {
                break;
            }
            node = node.getNextSibling();
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
        DetailNode node = ast.getFirstChild();
        while (node != null) {
            if (node.getType() == JavadocCommentsTokenTypes.TEXT) {
                result.append(node.getText());
            }
            else {
                final String summary = result.toString();
                if (CommonUtil.isBlank(summary)
                        && node.getType() == JavadocCommentsTokenTypes.HTML_ELEMENT) {
                    final DetailNode htmlContentToken = JavadocUtil.findFirstToken(
                            node, JavadocCommentsTokenTypes.HTML_CONTENT);
                    result.append(getStringInsideHtmlTag(summary, htmlContentToken));
                }
            }
            node = node.getNextSibling();
        }
        return result.toString().trim();
    }

    /**
     * Get concatenated string within text of html tags.
     *
     * @param result javadoc string
     * @param detailNode htmlContent node
     * @return java doc tag content appended in result
     */
    private static String getStringInsideHtmlTag(String result, DetailNode detailNode) {
        final StringBuilder contents = new StringBuilder(result);
        if (detailNode != null) {
            DetailNode tempNode = detailNode.getFirstChild();
            while (tempNode != null) {
                if (tempNode.getType() == JavadocCommentsTokenTypes.TEXT) {
                    contents.append(tempNode.getText());
                }
                tempNode = tempNode.getNextSibling();
            }
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
            sentenceParts.add(text);
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
        final Stream<String> result;
        if (node.getFirstChild() == null) {
            result = Stream.of(node.getText());
        }
        else {
            final List<Stream<String>> childStreams = new ArrayList<>();
            DetailNode child = node.getFirstChild();
            while (child != null) {
                childStreams.add(streamTextParts(child));
                child = child.getNextSibling();
            }
            result = childStreams.stream().flatMap(Function.identity());
        }
        return result;
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
            periodIndex = text.indexOf(period, afterPeriodIndex);
        }
        return result;
    }
}
