////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks that
 * <a href="https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#firstsentence">
 * Javadoc summary sentence</a> does not contain phrases that are not recommended to use.
 * Summaries that contain only the {@code {@inheritDoc}} tag are skipped for normal summary javadoc
 * but are violations for inline summary javadoc. Check also violate Javadoc that does not contain
 * first sentence. </p><p> According to
 * <a href="https://docs.oracle.com/javase/11/docs/specs/doc-comment-spec.html#general-syntax">
 * Documentation Comment Specification for the Standard Doclet</a> "The first sentence of the
 * initial description should be a summary sentence that contains a concise but complete description
 * of the declared entity. Descriptive text may include HTML tags and entities, and inline tags
 * as described below."
 * </p>
 * <p>
 * Allowed tags inside inline summary javadoc are
 * <a href="https://docs.oracle.com/javase/11/docs/specs/doc-comment-spec.html#code">
 * {&#64;code}</a>,
 * <a href="https://docs.oracle.com/javase/11/docs/specs/doc-comment-spec.html#link">
 * {&#64;link}</a>,
 * <a href="https://docs.oracle.com/javase/11/docs/specs/doc-comment-spec.html#docroot">
 * {&#64;docRoot}</a>,
 * <a href="https://docs.oracle.com/javase/11/docs/specs/doc-comment-spec.html#linkplain">
 * {&#64;linkplain}</a>,
 * <a href="https://docs.oracle.com/javase/11/docs/specs/doc-comment-spec.html#literal">
 * {&#64;literal}</a>,
 * <a href="https://docs.oracle.com/javase/11/docs/specs/doc-comment-spec.html#value">
 * {&#64;value}</a>,
 * <a href="https://docs.oracle.com/javase/11/docs/specs/doc-comment-spec.html#index">
 * {&#64;index}</a>
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code forbiddenSummaryFragments} - Specify the regexp for forbidden summary fragments.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
 * </li>
 * <li>
 * Property {@code period} - Specify the period symbol at the end of first javadoc sentence.
 * Type is {@code java.lang.String}.
 * Default value is {@code "."}.
 * </li>
 * </ul>
 * <p>
 * To configure the default check to validate that first sentence is not empty and first
 * sentence is not missing:
 * </p>
 * <pre>
 * &lt;module name=&quot;SummaryJavadocCheck&quot;/&gt;
 * </pre>
 * <p>
 * Example of {@code {@inheritDoc}} without summary.
 * </p>
 * <pre>
 * public class Test extends Exception {
 * //Valid
 *   &#47;**
 *    * {&#64;inheritDoc}
 *    *&#47;
 *   public String ValidFunction(){
 *     return "";
 *   }
 *   //Violation
 *   &#47;**
 *    *
 *    *&#47;
 *   public String InvalidFunction(){
 *     return "";
 *   }
 * }
 * </pre>
 * <p>
 * Example of {@code {@inheritDoc}} is not permitted for Inline Summary Javadoc.
 * </p>
 * <pre>
 * public class Test extends Exception {
 *   //Violation
 *   &#47;**
 *    * {&#64;summary {&#64;inheritDoc}.}
 *    *&#47;
 *   public String InvalidFunctionOne(){
 *     return "";
 *   }
 *   //Violation
 *   &#47;**
 *    * {&#64;summary }
 *    *&#47;
 *   public String InvalidFunctionTwo(){
 *     return "";
 *   }
 * }
 * </pre>
 * <p>
 * To ensure that summary do not contain phrase like "This method returns",
 * use following config:
 * </p>
 * <pre>
 * &lt;module name="SummaryJavadocCheck"&gt;
 *   &lt;property name="forbiddenSummaryFragments"
 *     value="^This method returns.*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To specify period symbol at the end of first javadoc sentence:
 * </p>
 * <pre>
 * &lt;module name="SummaryJavadocCheck"&gt;
 *   &lt;property name="period" value="。"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of period property.
 * </p>
 * <pre>
 * public class TestClass {
 *  &#47;**
 *   * This is invalid java doc.
 *   *&#47;
 *   void invalidJavaDocMethod() {
 *   }
 *  &#47;**
 *   * This is valid java doc。
 *   *&#47;
 *   void validJavaDocMethod() {
 *   }
 * }
 * </pre>
 * <p>
 * Example of period property for inline summary javadoc.
 * </p>
 * <pre>
 * public class TestClass {
 *  &#47;**
 *   * {&#64;summary This is invalid java doc.}
 *   *&#47;
 *   public void invalidJavaDocMethod() { // violation
 *   }
 *  &#47;**
 *   * {&#64;summary This is valid java doc。}
 *   *&#47;
 *   public void validJavaDocMethod() { // ok
 *   }
 * }
 * </pre>
 * <p>
 * Example of inline summary javadoc with inline tags.
 * </p>
 * <pre>
 * public class TestClass {
 *  &#47;**
 *   * {&#64;summary {&#64;code someCode} valid inline javadoc.}
 *   *&#47;
 *   public void validJavaDoc() {} // ok
 *  &#47;**
 *   * {&#64;summary {&#64;inheritDoc} invalid inline javadoc.}
 *   *&#47;
 *   public void invalidJavaDocMethod() { // violation
 *   }
 * }
 * </pre>
 * <p>
 * Example of inline summary javadoc with HTML tags.
 * </p>
 * <pre>
 * public class Test {
 *  &#47;**
 *   * {&#64;summary first sentence is normally the summary.
 *   * Use of html tags:
 *   * &lt;ul&gt;
 *   * &lt;li&gt;Item one.&lt;/li&gt;
 *   * &lt;li&gt;Item two.&lt;/li&gt;
 *   * &lt;/ul&gt;}
 *   *&#47;
 *   public void validInlineJavadoc() { // ok
 *   }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
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
     * This regexp is used to convert multiline javadoc to single line without stars.
     */
    private static final Pattern JAVADOC_MULTILINE_TO_SINGLELINE_PATTERN =
            Pattern.compile("\n[ ]+(\\*)|^[ ]+(\\*)");

    /**
     * This regexp is used to remove html tags, whitespaces and asterisks from string.
     */
    private static final Pattern REDUNDANT_ELEMENTS_PATTERN =
            Pattern.compile("<[^>]*>|(, + *)|(\\*)");
    /**
     * This regexp is used to extract content inside summary javadoc tag from a string.
     */
    private static final Pattern SUMMARY_PATTERN = Pattern.compile("\\{@summary ([\\S\\s]+)}");
    /**
     * This regexp is used to remove javadoc inline tags from string.
     */
    private static final Pattern INLINE_TAG_NODE_PATTERN = Pattern.compile("\\{@[\\s\\S]+}");
    /** Period literal. */
    private static final String PERIOD = ".";

    /** Summary tag text. */
    private static final String SUMMARY_TEXT = "@summary";

    /** Set of allowed Tokens tags in summary java doc. */
    private static final Set<Integer> ALLOWED_TYPES = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(JavadocTokenTypes.TEXT,
                    JavadocTokenTypes.WS,
                    JavadocTokenTypes.DESCRIPTION,
                    JavadocTokenTypes.TEXT))
    );

    /**
     * Set of allowed inline tags in summary java doc.
     *
     * <p>These tags are allowed with respect to
     * https://docs.oracle.com/javase/11/docs/specs/doc-comment-spec.html#general-syntax .
     * According to which all the HTML tags and inline tags described in it are allowed.
     * "@inheritDoc" is not included as it is forbidden by check.</p>
     */
    private static final Set<String> ALLOWED_INLINE_TAGS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    "@code",
                    "@link",
                    "@input",
                    "@docRoot",
                    "@index",
                    "@linkplain",
                    "@literal",
                    "@value"
            ))
    );

    /**
     * Specify the regexp for forbidden summary fragments.
     */
    private Pattern forbiddenSummaryFragments = CommonUtil.createPattern("^$");

    /**
     * Specify the period symbol at the end of first javadoc sentence.
     */
    private String period = PERIOD;

    /**
     * Setter to specify the regexp for forbidden summary fragments.
     *
     * @param pattern a pattern.
     */
    public void setForbiddenSummaryFragments(Pattern pattern) {
        forbiddenSummaryFragments = pattern;
    }

    /**
     * Setter to specify the period symbol at the end of first javadoc sentence.
     *
     * @param period period's value.
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
        if (containsSummaryTag(ast)) {
            validateSummaryTag(ast);
        }
        else if (!startsWithInheritDoc(ast)) {
            final String summaryDoc = getSummarySentence(ast);
            if (summaryDoc.isEmpty()) {
                log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC_MISSING);
            }
            else if (!period.isEmpty()) {
                final String firstSentence = getFirstSentence(ast);
                final int endOfSentence = firstSentence.lastIndexOf(period);
                if (!summaryDoc.contains(period)) {
                    log(ast.getLineNumber(), MSG_SUMMARY_FIRST_SENTENCE);
                }
                if (endOfSentence != -1
                        && containsForbiddenFragment(firstSentence.substring(0, endOfSentence))) {
                    log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC);
                }
            }
        }
    }

    /**
     * Checks if summary tag present.
     *
     * @param javadoc Javadoc root node.
     * @return true, if first sentence contains @summary tag.
     */
    private static boolean containsSummaryTag(DetailNode javadoc) {
        final DetailNode node = getFirstInlineTag(javadoc);
        return node != null && isSummaryTag(node);
    }

    /**
     * Finds and return first inline tag node from a javadoc root node.
     *
     * @param javadoc Javadoc root node.
     * @return First inline tag node or null if no node is found.
     */
    private static DetailNode getFirstInlineTag(DetailNode javadoc) {
        DetailNode node = null;
        final DetailNode[] children = javadoc.getChildren();
        for (DetailNode child: children) {
            // If present as a children of javadoc
            if (child.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                node = child;
            }
            // If nested inside html tag
            else if (child.getType() == JavadocTokenTypes.HTML_ELEMENT) {
                node = getInlineTagNodeWithinHtmlElement(child);
            }

            if (node != null) {
                break;
            }
        }
        return node;
    }

    /**
     * Returns inline javadoc tag node inside html tags from a HTML tag.
     *
     * @param ast html tag node.
     * @return Inline summary javadoc tag node or null if no node is found.
     */
    private static DetailNode getInlineTagNodeWithinHtmlElement(DetailNode ast) {
        DetailNode node = ast;
        DetailNode result = null;
        // node can never be null as it is always called when there is HTML element
        if (node.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
            result = node;
        }
        else if (node.getType() == JavadocTokenTypes.HTML_TAG) {
            // HTML_TAG always has more than 2 children.
            node = node.getChildren()[1];
            result = getInlineTagNodeWithinHtmlElement(node);
        }
        else if (node.getType() == JavadocTokenTypes.HTML_ELEMENT
                // Condition for SINGLETON html element which cannot contain summary node
                && node.getChildren()[0].getChildren().length > 1) {
            // Html elements have one tested tag before actual content inside it
            node = node.getChildren()[0].getChildren()[1];
            result = getInlineTagNodeWithinHtmlElement(node);
        }
        return result;
    }

    /**
     * Checks if the first tag inside ast is summary tag.
     *
     * @param javadoc root node.
     * @return true, if first tag is summary tag.
     */
    private static boolean isSummaryTag(DetailNode javadoc) {
        final DetailNode[] child = javadoc.getChildren();

        // Checking size of ast is not required, since ast contains
        // children of Inline Tag, as at least 2 children will be present which are
        // RCURLY and LCURLY.
        return child[1].getType() == JavadocTokenTypes.CUSTOM_NAME
                && SUMMARY_TEXT.equals(child[1].getText());
    }

    /**
     * Check the summary of inline form.
     *
     * @param ast Javadoc root Node.
     */
    private void validateSummaryTag(DetailNode ast) {
        final String inlineSummary = getInlineSummary(ast);
        final String summaryVisible = getVisibleContent(inlineSummary);
        if (summaryVisible.isEmpty()) {
            log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC_MISSING);
        }
        else if (!period.isEmpty()) {
            if (isPeriodAtEnd(summaryVisible, period)) {
                log(ast.getLineNumber(), MSG_SUMMARY_MISSING_PERIOD);
            }
            else if (!containsCorrectInlineTags(ast)) {
                log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC);
            }
        }
    }

    /**
     * Gets whole content of summary tag.
     *
     * @param javadoc javadoc root node.
     * @return Summary sentence of javadoc root node.
     */
    private String getInlineSummary(DetailNode javadoc) {
        final FileContents file = getFileContents();
        final DetailNode[] children = javadoc.getChildren();
        final int javadocEndLineNo = children[children.length - 1].getLineNumber();
        final TextBlock textBlock = file.getJavadocBefore(javadocEndLineNo + 1);
        return Arrays.toString(textBlock.getText());
    }

    /**
     * Gets the string that is visible to user in javadoc.
     *
     * @param summary Whole content of summary javadoc.
     * @return string that is visible to user in javadoc.
     */
    private static String getVisibleContent(String summary) {
        final Matcher matcher = SUMMARY_PATTERN.matcher(summary);
        String comment = "";
        if (matcher.find()) {
            comment = removeRedundantElementsFromString(matcher.group(1));
            comment = INLINE_TAG_NODE_PATTERN.matcher(comment).replaceAll("");
        }
        return comment;
    }

    /**
     * Checks if the string ends with period.
     *
     * @param sentence string to check for period at end.
     * @param period String to check within sentence.
     * @return true, is sentence ends with period.
     */
    private static boolean isPeriodAtEnd(String sentence, String period) {
        final String summarySentence = sentence.trim();
        return summarySentence.lastIndexOf(period) != summarySentence.length() - 1;
    }

    /**
     * Finds and return if the summary sentence has allowed Inline Tags.
     *
     * @param ast Children of javadoc Inline Tag DetailNode[].
     * @return true, if first sentence contains allowed tags.
     */
    private static boolean containsCorrectInlineTags(DetailNode ast) {
        boolean found = true;
        final DetailNode node = getFirstInlineTag(ast);
        final ListIterator<DetailNode> inlineNodes = getNestedInlineJavadocTagNodes(node);
        while (found && inlineNodes.hasNext()) {
            // node.next() is an inline tag node and its second child is the name of it
            found = ALLOWED_INLINE_TAGS.contains(inlineNodes.next().getChildren()[1].getText());
        }
        return found;
    }

    /**
     * Gets list iterator for inline javadoc tag nodes which are present in other javadoc tag node.
     *
     * @param javadoc Inline tag DetailNode.
     * @return List iterator of type DetailNode.
     */
    private static ListIterator<DetailNode> getNestedInlineJavadocTagNodes(DetailNode javadoc) {
        final List<DetailNode> inlineNode = new ArrayList<>();
        final DetailNode inlineTagNode = JavadocUtil.findFirstToken(javadoc,
                JavadocTokenTypes.DESCRIPTION);
        for (DetailNode child : inlineTagNode.getChildren()) {
            if (child.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                inlineNode.add(child);
            }
        }
        return inlineNode.listIterator();
    }

    /**
     * Remove html tags from string.
     * This is required as ANTLR does not parses html lists sometimes as HTML Elements.
     * The issue link to this issue is
     * <a href="https://github.com/checkstyle/checkstyle/issues/9703">Issue #9703</a>
     *
     * @param summarySentence string to clean.
     * @return string without html tags.
     */
    private static String removeRedundantElementsFromString(String summarySentence) {
        return REDUNDANT_ELEMENTS_PATTERN.matcher(summarySentence).replaceAll("");
    }

    /**
     * Tests if first sentence contains forbidden summary fragment.
     *
     * @param firstSentence String with first sentence.
     * @return true, if first sentence contains forbidden summary fragment.
     */
    private boolean containsForbiddenFragment(String firstSentence) {
        final String javadocText = JAVADOC_MULTILINE_TO_SINGLELINE_PATTERN
                .matcher(firstSentence).replaceAll(" ").trim();
        return forbiddenSummaryFragments.matcher(trimExcessWhitespaces(javadocText)).find();
    }

    /**
     * Trims the given {@code text} of duplicate whitespaces.
     *
     * @param text The text to transform.
     * @return The finalized form of the text.
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
     * @param root The root node to examine.
     * @return {@code true} if the javadoc starts with an {&#64;inheritDoc}.
     */
    private static boolean startsWithInheritDoc(DetailNode root) {
        boolean found = false;
        final DetailNode[] children = root.getChildren();

        for (int i = 0; !found; i++) {
            final DetailNode child = children[i];
            if (child.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG
                    && child.getChildren()[1].getType() == JavadocTokenTypes.INHERIT_DOC_LITERAL) {
                found = true;
            }
            else if (child.getType() != JavadocTokenTypes.LEADING_ASTERISK
                    && !CommonUtil.isBlank(child.getText())) {
                break;
            }
        }

        return found;
    }

    /**
     * Finds and returns summary sentence.
     *
     * @param ast Javadoc root node.
     * @return violation string
     */
    private static String getSummarySentence(DetailNode ast) {
        boolean flag = true;
        final StringBuilder result = new StringBuilder(256);
        for (DetailNode child : ast.getChildren()) {
            if (ALLOWED_TYPES.contains(child.getType())) {
                result.append(child.getText());
            }
            else if (child.getType() == JavadocTokenTypes.HTML_ELEMENT
                    && CommonUtil.isBlank(result.toString().trim())) {
                result.append(getStringInsideTag(result.toString(),
                        child.getChildren()[0].getChildren()[0]));
            }
            else if (child.getType() == JavadocTokenTypes.JAVADOC_TAG) {
                flag = false;
            }
            if (!flag) {
                break;
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
     * Finds and returns first sentence.
     *
     * @param ast Javadoc root node.
     * @return first sentence.
     */
    private static String getFirstSentence(DetailNode ast) {
        final StringBuilder result = new StringBuilder(256);
        final String periodSuffix = PERIOD + ' ';
        for (DetailNode child : ast.getChildren()) {
            final String text;
            if (child.getChildren().length == 0) {
                text = child.getText();
            }
            else {
                text = getFirstSentence(child);
            }

            if (text.contains(periodSuffix)) {
                result.append(text, 0, text.indexOf(periodSuffix) + 1);
                break;
            }

            result.append(text);
        }
        return result.toString();
    }

}
