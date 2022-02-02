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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks that
 * <a href="https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#firstsentence">
 * Javadoc summary sentence</a> does not contain phrases that are not recommended to use.
 * Summaries that contain only the {@code {@inheritDoc}} tag are skipped.
 * Check also violate Javadoc that does not contain first sentence.
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
 * Example of non permitted empty javadoc for Inline Summary Javadoc.
 * </p>
 * <pre>
 * public class Test extends Exception {
 *   &#47;**
 *    * {&#64;summary  }
 *    *&#47;
 *   public String InvalidFunctionOne(){ // violation
 *     return "";
 *   }
 *
 *   &#47;**
 *    * {&#64;summary &lt;p&gt; &lt;p/&gt;}
 *    *&#47;
 *   public String InvalidFunctionTwo(){ // violation
 *     return "";
 *   }
 *
 *   &#47;**
 *    * {&#64;summary &lt;p&gt;This is summary for validFunctionThree.&lt;p/&gt;}
 *    *&#47;
 *   public void validFunctionThree(){} // ok
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
 * Example of inline summary javadoc with HTML tags.
 * </p>
 * <pre>
 * public class Test {
 *  &#47;**
 *   * {&#64;summary First sentence is normally the summary.
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
     * This regexp is used to remove html tags, whitespace, and asterisks from a string.
     */
    private static final Pattern HTML_ELEMENTS =
            Pattern.compile("<[^>]*>");

    /**
     * This regexp is used to extract the content of a summary javadoc tag.
     */
    private static final Pattern SUMMARY_PATTERN = Pattern.compile("\\{@summary ([\\S\\s]+)}");
    /** Period literal. */
    private static final String PERIOD = ".";

    /** Summary tag text. */
    private static final String SUMMARY_TEXT = "@summary";

    /** Set of allowed Tokens tags in summary java doc. */
    private static final Set<Integer> ALLOWED_TYPES = Set.of(
                    JavadocTokenTypes.WS,
                    JavadocTokenTypes.DESCRIPTION,
                    JavadocTokenTypes.TEXT);

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
     * @param javadoc javadoc root node.
     * @return {@code true} if first sentence contains @summary tag.
     */
    private static boolean containsSummaryTag(DetailNode javadoc) {
        final Optional<DetailNode> node = Arrays.stream(javadoc.getChildren())
                .filter(SummaryJavadocCheck::isInlineTagPresent)
                .findFirst()
                .map(SummaryJavadocCheck::getInlineTagNodeWithinHtmlElement);

        return node.isPresent() && isSummaryTag(node.get());
    }

    /**
     * Checks if the inline tag node is present.
     *
     * @param ast ast node to check.
     * @return true, if the inline tag node is present.
     */
    private static boolean isInlineTagPresent(DetailNode ast) {
        return ast.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG
                || ast.getType() == JavadocTokenTypes.HTML_ELEMENT
                && getInlineTagNodeWithinHtmlElement(ast) != null;
    }

    /**
     * Returns an inline javadoc tag node that is within a html tag.
     *
     * @param ast html tag node.
     * @return inline summary javadoc tag node or null if no node is found.
     */
    private static DetailNode getInlineTagNodeWithinHtmlElement(DetailNode ast) {
        DetailNode node = ast;
        DetailNode result = null;
        // node can never be null as this method is called when there is a HTML_ELEMENT
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
     * @return {@code true} if first tag is summary tag.
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
     * Checks the inline summary (if present) for {@code period} at end and forbidden fragments.
     *
     * @param ast javadoc root node.
     */
    private void validateSummaryTag(DetailNode ast) {
        final String inlineSummary = getInlineSummary();
        final String summaryVisible = getVisibleContent(inlineSummary);
        if (summaryVisible.isEmpty()) {
            log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC_MISSING);
        }
        else if (!period.isEmpty()) {
            if (isPeriodAtEnd(summaryVisible, period)) {
                log(ast.getLineNumber(), MSG_SUMMARY_MISSING_PERIOD);
            }
            else if (containsForbiddenFragment(inlineSummary)) {
                log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC);
            }
        }
    }

    /**
     * Gets entire content of summary tag.
     *
     * @return summary sentence of javadoc root node.
     */
    private String getInlineSummary() {
        final DetailAST blockCommentAst = getBlockCommentAst();
        final String javadocText = blockCommentAst.getFirstChild().getText();
        final Matcher matcher = SUMMARY_PATTERN.matcher(javadocText);
        String comment = "";
        if (matcher.find()) {
            comment = matcher.group(1);
        }
        return JAVADOC_MULTILINE_TO_SINGLELINE_PATTERN.matcher(comment)
                .replaceAll("");
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
     * Checks if the string ends with period.
     *
     * @param sentence string to check for period at end.
     * @param period string to check within sentence.
     * @return {@code true} if sentence ends with period.
     */
    private static boolean isPeriodAtEnd(String sentence, String period) {
        final String summarySentence = sentence.trim();
        return summarySentence.lastIndexOf(period) != summarySentence.length() - 1;
    }

    /**
     * Tests if first sentence contains forbidden summary fragment.
     *
     * @param firstSentence string with first sentence.
     * @return {@code true} if first sentence contains forbidden summary fragment.
     */
    private boolean containsForbiddenFragment(String firstSentence) {
        final String javadocText = JAVADOC_MULTILINE_TO_SINGLELINE_PATTERN
                .matcher(firstSentence).replaceAll(" ").trim();
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
     * @param ast javadoc root node.
     * @return violation string.
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
