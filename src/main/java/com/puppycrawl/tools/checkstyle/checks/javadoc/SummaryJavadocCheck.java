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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
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
 * <a href="https://docs.oracle.com/javase/10/docs/specs/doc-comment-spec.html#general-syntax">
 * Documentation Comment Specification for the Standard Doclet</a> "The first sentence of the
 * initial description should be a summary sentence that contains a concise but complete description
 * of the declared entity. Descriptive text may include HTML tags and entities, and inline tags
 * as described below."
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
 * Example of {@code {@inheritDoc}} without summary for Inline Summary Javadoc.
 * </p>
 * <pre>
 * public class Test extends Exception {
 *   //Valid
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
     * This regexp is used to convert multiline javadoc to single line without stars.
     */
    private static final Pattern JAVADOC_MULTILINE_TO_SINGLELINE_PATTERN =
            Pattern.compile("\n[ ]+(\\*)|^[ ]+(\\*)");

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
     * https://docs.oracle.com/javase/10/docs/specs/doc-comment-spec.html#general-syntax .
     * According to which all the HTML tags and inline tags described in it are allowed.
     * "@inheritDoc" is not included as it is forbidden by check.</p>
     *
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

    /** Specify the regexp for forbidden summary fragments. */
    private Pattern forbiddenSummaryFragments = CommonUtil.createPattern("^$");

    /** Specify the period symbol at the end of first javadoc sentence. */
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
     * Check the summary when of inline form.
     *
     * @param ast Javadoc root Node.
     */
    public void validateSummaryTag(DetailNode ast) {
        final String inlineSummaryDoc = getInlineSummarySentence(ast);
        if (inlineSummaryDoc.isEmpty()) {
            log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC_MISSING);
        }
        else if (!period.isEmpty()) {
            if (!inlineSummaryDoc.contains(period)) {
                log(ast.getLineNumber(), MSG_SUMMARY_FIRST_SENTENCE);
            }

            if (isFirstSentenceAllowed(ast)) {
                log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC);
            }
        }
    }

    /**
     * Checks if the first sentence is allowed.
     *
     * @param ast Javadoc root node.
     * @return true, if sentence is allowed.
     */
    private boolean isFirstSentenceAllowed(DetailNode ast) {
        final String inlineFirstSentence = getInlineFirstSentence(ast);
        final int endOfInlineSentence = inlineFirstSentence.lastIndexOf(period);
        return endOfInlineSentence != -1
                && (containsForbiddenFragment(inlineFirstSentence.substring(0,
                endOfInlineSentence))
                || !containsCorrectInlineTags(ast)
                || !validInlineTagInsideHtmlFormat(ast));
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
     * Checks if period is at the end of sentence.
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
     * Concatenates string within text of html tags.
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
        final StringBuilder result = new StringBuilder(100);
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
     * Finds and return if summary tag present.
     *
     * @param javadoc Javadoc root node.
     * @return true, if first sentence contains @summary tag.
     */
    private static boolean containsSummaryTag(DetailNode javadoc) {
        boolean contains = false;
        for (DetailNode node : javadoc.getChildren()) {
            if (node.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                final DetailNode[] child = node.getChildren();

                // Checking size of child is not required, since child contains
                // children of Inline Tag, as at least 2 children will be present
                // which are RCURLY and LCURLY.
                if (child[1].getType() == JavadocTokenTypes.CUSTOM_NAME
                    && SUMMARY_TEXT.equals(child[1].getText())) {
                    contains = true;
                }
            }
        }

        if (containsSummaryTagWithHtmlTag(javadoc)) {
            contains = true;
        }

        return contains;
    }

    /**
     * Finds and return if summary tag present as HTML Format.
     *
     * @param javadoc Javadoc root node.
     * @return true, if first sentence contains @summary tag.
     */
    private static boolean containsSummaryTagWithHtmlTag(DetailNode javadoc) {
        boolean contains = false;
        if (getInlineTagNodeInsideHtml(javadoc) != null) {
            final DetailNode[] htmlChild = getInlineTagNodeInsideHtml(javadoc).getChildren();

            // Checking size of htmlChild is not required, since htmlChild contains
            // children of Inline Tag, as at least 2 children will be present which are
            // RCURLY and LCURLY.
            contains = htmlChild[1].getType() == JavadocTokenTypes.CUSTOM_NAME
                    && SUMMARY_TEXT.equals(htmlChild[1].getText());
        }
        return contains;
    }

    /**
     * Returns inline tag Node of html type summary.
     *
     * @param ast Root node.
     * @return DetailNode of inline tag.
     */
    private static DetailNode getInlineTagNodeInsideHtml(DetailNode ast) {
        DetailNode inlineNode = null;
        for (DetailNode node : ast.getChildren()) {
            if (node.getType() == JavadocTokenTypes.HTML_ELEMENT) {
                for (DetailNode child : node.getChildren()) {
                    if (child.getType() == JavadocTokenTypes.PARAGRAPH) {
                        inlineNode = getInlineTagNodeFromParagraph(child);
                    }
                }
            }
        }
        return inlineNode;
    }

    /**
     * Returns inline tag Node inside paragraph of html type summary.
     *
     * @param paragraph Root node.
     * @return DetailNode of inline tag.
     */
    private static DetailNode getInlineTagNodeFromParagraph(DetailNode paragraph) {
        DetailNode inlineNode = null;
        for (DetailNode inline : paragraph.getChildren()) {
            if (inline.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                inlineNode = inline;
                break;
            }
        }
        return inlineNode;
    }

    /**
     * Finds and return if the Inline Tag has allowed Inline Tags.
     *
     * @param ast Children of javadoc Inline Tag DetailNode[].
     * @return true, if first sentence contains allowed tags.
     */
    private static boolean containsCorrectInlineTags(DetailNode ast) {
        boolean contains = true;
        for (DetailNode node : ast.getChildren()) {
            final DetailNode[] child = node.getChildren();
            if (child.length > 1
                && child[1].getType() == JavadocTokenTypes.CUSTOM_NAME
                && getInlineTag(child) != null
                && !ALLOWED_INLINE_TAGS.contains(getInlineTag(child).getText())) {
                contains = false;
                break;
            }
        }
        return contains;
    }

    /**
     * Finds and return if the HTML Format Inline Tag has allowed Inline Tags.
     *
     * @param ast Javadoc root Node.
     * @return true, if first sentence contains allowed tags.
     */
    private static boolean validInlineTagInsideHtmlFormat(DetailNode ast) {
        boolean contains = true;
        if (getInlineTagNodeInsideHtml(ast) != null) {
            for (DetailNode node : getInlineTagNodeInsideHtml(ast).getChildren()) {
                if (node.getType() == JavadocTokenTypes.DESCRIPTION) {
                    contains = checkInlineTagInsideDescription(node);
                }
            }
        }
        return contains;
    }

    /**
     * Finds and return if the Inline Tag in description Node has allowed Inline Tags.
     *
     * @param description Description Node.
     * @return true, if first sentence contains allowed tags.
     */
    private static boolean checkInlineTagInsideDescription(DetailNode description) {
        boolean checks = true;
        for (DetailNode nestedInlineTag : description.getChildren()) {
            if (nestedInlineTag.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                final DetailNode[] child = nestedInlineTag.getChildren();

                // Checking size of child is not required, since child contains
                // children of Inline Tag, as at least 2 children will be present which are
                // RCURLY and LCURLY.
                if (child[1].getType() == JavadocTokenTypes.CUSTOM_NAME
                        && !ALLOWED_INLINE_TAGS.contains(child[1].getText())) {
                    checks = false;
                    break;
                }
            }
        }
        return checks;
    }

    /**
     * Get Inline tag inside Inline tag from a javadoc node.
     *
     * @param javadoc Inline tag DetailNode.
     * @return DetailNode with CUSTOM_NAME;
     */
    private static DetailNode getInlineTag(DetailNode... javadoc) {
        DetailNode tagName = null;
        if (getInlineTagNode(javadoc) != null) {
            for (DetailNode child : getInlineTagNode(javadoc).getChildren()) {
                if (child.getType() == JavadocTokenTypes.CUSTOM_NAME) {
                    tagName = child;
                }
            }
        }
        return tagName;
    }

    /**
     * Gets inline tag Node from a javadoc node.
     *
     * @param javadoc Inline tag DetailNode.
     * @return DetailNode with CUSTOM_NAME;
     */
    private static DetailNode getInlineTagNode(DetailNode... javadoc) {
        DetailNode tagNode = null;
        for (DetailNode node : javadoc) {
            if (node.getType() == JavadocTokenTypes.DESCRIPTION) {
                for (DetailNode nodeChild : node.getChildren()) {
                    if (nodeChild.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                        tagNode = nodeChild;
                    }
                }
            }
        }
        return tagNode;
    }

    /**
     * Checks if period is at the end of sentence.
     *
     * @param ast Javadoc root node.
     * @return violation string
     */
    private static String getInlineSummarySentence(DetailNode ast) {
        final StringBuilder result = new StringBuilder(256);
        if (getDescriptionNode(ast) != null) {
            for (DetailNode child : getDescriptionNode(ast).getChildren()) {
                if (child.getType() == JavadocTokenTypes.TEXT) {
                    result.append(child.getText());
                }
            }
        }

        if (getInlineTagNodeInsideHtml(ast) != null) {
            final DetailNode descriptionNode = getDescriptionNodeInsideHtml(
                getInlineTagNodeInsideHtml(ast));
            if (descriptionNode != null) {
                for (DetailNode child : descriptionNode.getChildren()) {
                    if (child.getType() == JavadocTokenTypes.TEXT) {
                        result.append(child.getText());
                    }
                }
            }
        }
        return result.toString().trim();
    }

    /**
     * Get Description node from ast.
     *
     * @param ast Javadoc root node.
     * @return Description DetailNode.
     */
    private static DetailNode getDescriptionNode(DetailNode ast) {
        DetailNode descriptionNode = null;
        for (DetailNode node : ast.getChildren()) {
            if (node.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                for (DetailNode dataType : node.getChildren()) {
                    if (dataType.getType() == JavadocTokenTypes.DESCRIPTION) {
                        descriptionNode = dataType;
                    }
                }
            }
        }
        return descriptionNode;
    }

    /**
     * Finds and returns first sentence.
     *
     * @param ast Javadoc root node.
     * @return first sentence.
     */
    private static String getInlineFirstSentence(DetailNode ast) {
        final StringBuilder result = new StringBuilder(256);
        for (DetailNode node : ast.getChildren()) {
            if (node.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                result.append(returnInlineFirstSentence(node));
            }

            if (node.getType() == JavadocTokenTypes.HTML_ELEMENT) {
                result.append(returnInlineFirstSentence(
                        Objects.requireNonNull(getInlineTagNodeInsideHtml(ast))));
            }
        }
        return result.toString();
    }

    /**
     * Sets result string in method getInlineFirstSentence.
     *
     * @param node JavaDoc Inline node.
     * @return first sentence.
     */
    public static String returnInlineFirstSentence(DetailNode node) {
        final StringBuilder result = new StringBuilder(256);
        final String periodSuffix = PERIOD;
        for (DetailNode child : node.getChildren()) {
            if (ALLOWED_TYPES.contains(child.getType())) {
                final String text;
                if (child.getChildren().length == 0) {
                    text = child.getText();
                }
                else {
                    text = getTextFromDescription(child);
                }

                if (text.contains(periodSuffix)) {
                    result.append(text, 0, text.indexOf(periodSuffix) + 1);
                    break;
                }

                result.append(text);
            }
        }

        return result.toString();
    }

    /**
     * Returns the text from an inline tag Description.
     *
     * @param ast Inline tag Node.
     * @return Text of inline tag description.
     */
    private static String getTextFromDescription(DetailNode ast) {
        final String period = PERIOD + ' ';
        StringBuilder inlineText = null;
        for (DetailNode nodeChild : ast.getChildren()) {
            if (nodeChild.getType() == JavadocTokenTypes.TEXT) {
                if (inlineText == null) {
                    inlineText = new StringBuilder(nodeChild.getText());
                }
                else {
                    inlineText.append(nodeChild.getText());
                }

                if (inlineText.toString().contains(period)) {
                    break;
                }
            }
        }
        return inlineText.toString();
    }

    /**
     * Returns Description Node inside HTML Type description.
     *
     * @param ast Inline Tag node inside HTML.
     * @return Description Node.
     */
    private static DetailNode getDescriptionNodeInsideHtml(DetailNode ast) {
        DetailNode node = null;
        for (DetailNode child: ast.getChildren()) {
            if (child.getType() == JavadocTokenTypes.DESCRIPTION) {
                node = child;
                break;
            }
        }
        return node;
    }
}
