////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 *<p>
 * Checks that
 * <a href="https://www.oracle.com/technetwork/java/javase/documentation/index-137868.html#firstsentence">
 * Javadoc summary sentence</a> does not contain phrases that are not recommended to use.
 * Summaries that contain only the {@code {@inheritDoc}} tag are skipped.
 * Check also violate Javadoc that does not contain first sentence.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code forbiddenSummaryFragments} - Specify the regexp for forbidden summary fragments.
 * Default value is {@code "^$" (empty)}.
 * </li>
 * <li>
 * Property {@code period} - Specify the period symbol at the end of first javadoc sentence.
 * Default value is {@code "."}.
 * </li>
 * </ul>
 * <p>
 * By default Check validate that first sentence is not empty and first sentence is not missing:
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
 *   &#47;**
 *   * This is invalid java doc.
 *   *&#47;
 *   void invalidJavaDocMethod() {
 *   }
 *   &#47;**
 *   * This is valid java doc。
 *   *&#47;
 *   void validJavaDocMethod() {
 *   }
 * }
 * </pre>
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

    /** Set of allowed Tokens tags in summary java doc. */
    private static final Set<Integer> ALLOWED_TYPES = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(JavadocTokenTypes.TEXT,
                    JavadocTokenTypes.WS))
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
        if (!startsWithInheritDoc(ast)) {
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
     * Checks if the node starts with an {&#64;inheritDoc}.
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

}
