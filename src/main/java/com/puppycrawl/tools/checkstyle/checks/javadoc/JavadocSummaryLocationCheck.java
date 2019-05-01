////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import java.util.Locale;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

/**
 * <p>
 * Checks that the Javadoc text begins on the same position
 * for all Javadoc comments in the project.
 * </p>
 * <p>
 * It is possible to enforce two different styles:
 * </p>
 * <ul>
 * <li>
 * {@code First line} - Javadoc starts on the first line:
 * <pre>
 * &#47;** Summary text.
 * * More details.
 * *&#47;
 * public void method();
 * </pre>
 * </li>
 * <li>
 * {@code Second line} - Javadoc starts on the second line:
 * <pre>
 * &#47;**
 * * Summary text.
 * * More details.
 * *&#47;
 * public void method();
 * </pre>
 * </li>
 * </ul>
 * <p>
 * This check does not validate the Javadoc summary itself nor its presence.
 * The check will not report any violations for missing or malformed javadoc summary.
 * To validate the Javadoc summary use
 * <a href="https://checkstyle.org/config_javadoc.html#SummaryJavadoc">SummaryJavadoc</a> check.
 * </p>
 * <p>
 * The <a href="https://docs.oracle.com/en/java/javase/11/docs/specs/doc-comment-spec.html">
 * Documentation Comment Specification</a> permits leading asterisks on the first line.
 * For these Javadoc comments:
 * </p>
 * <pre>
 * &#47;***
 *   * Some text.
 *   *&#47;
 * &#47;************
 *   * Some text.
 *   *&#47;
 * &#47;**           **
 *   * Some text.
 *   *&#47;
 * </pre>
 * <p>
 * The documentation generated will be just "Some text." without any asterisks.
 * Since these asterisks will not appear in the generated documentation,
 * they should not be considered as the beginning of the Javadoc summary.
 * In such cases, the check assumes that the Javadoc summary begins on the second line.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
 * Tight-HTML Rules</a>.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code location} - Specify the policy on placement of the javadoc summary.
 * Default value is {@code second_line}.
 * </li>
 * </ul>
 * <p>
 * By default Check validate that the summary is located on the second line:
 * </p>
 * <pre>
 * &lt;module name="JavadocSummaryLocationCheck"/&gt;
 * </pre>
 * <p>
 * This setting produces a violation for each multi-line comment starting
 * on the same line as the initial asterisks:
 * </p>
 * <pre>
 * &#47;** This comment causes a violation because it starts on the first line
 *   * and spans several lines.
 *   *&#47;
 * &#47;**
 *   * This comment is OK because it starts on the second line.
 *   *&#47;
 * &#47;** This comment is OK because it is on the single line. *&#47;
 *</pre>
 * <p>
 * To ensure that summary is located on the first line:
 * </p>
 * <pre>
 * &lt;module name="JavadocSummaryLocationCheck"&gt;
 *   &lt;property name="location" value="FIRST_LINE"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * This setting produces a violation for each comment not
 * starting on the same line as the initial asterisks:
 * </p>
 * <pre>
 * &#47;** This comment is OK because it starts on the first line.
 *    * There may be additional text.
 *    *&#47;
 * &#47;**
 *   * This comment causes a violation because it starts on the second line.
 *   *&#47;
 * &#47;** This single-line comment also is OK. *&#47;
 * </pre>
 *
 * @since 8.26
 */
@StatelessCheck
public class JavadocSummaryLocationCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_JAVADOC_SUMMARY_FIRST_LINE = "javadoc.summary.first.line";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_JAVADOC_SUMMARY_SECOND_LINE = "javadoc.summary.second.line";

    /**
     * The pattern for detecting text ignored by the javadoc tool.
     */
    private static final Pattern OPTIONAL_PREFIX = Pattern.compile("^(\\**\\s*){1,2}?$");

    /**
     * Specify the policy on placement of the javadoc summary.
     */
    private JavadocSummaryLocation location = JavadocSummaryLocation.SECOND_LINE;

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
        if (isMultilineComment(ast)) {
            final int blankLines = countBlankLines(ast);
            if (blankLines >= 0) {
                if (location == JavadocSummaryLocation.FIRST_LINE) {
                    if (blankLines != 0) {
                        log(ast.getLineNumber(), MSG_JAVADOC_SUMMARY_FIRST_LINE);
                    }
                }
                else if (blankLines != 1) {
                    log(ast.getLineNumber(), MSG_JAVADOC_SUMMARY_SECOND_LINE);
                }
            }
        }
    }

    /**
     * Setter to specify the policy on placement of the javadoc summary.
     * @param value string to decode location from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setLocation(String value) {
        location = JavadocSummaryLocation.valueOf(value.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Checks if a DetailNode of type {@code JavadocTokenTypes.JAVADOC} has
     * more than one line.
     *
     * @param node node to check
     * @return {@code true} for
     */
    private static boolean isMultilineComment(DetailNode node) {
        final DetailNode[] children = node.getChildren();
        final int firstChildLineNo = children[0].getLineNumber();
        final int lastChildLineNo = children[children.length - 1].getLineNumber();
        return firstChildLineNo != lastChildLineNo;
    }

    /**
     * Returns the number of blank lines before any text or tag.
     * All lines consists only of asterisks and whitespaces are treated as blank.
     *
     * @param node to process
     * @return the number of blank lines or {@code -1} if the comment is completely blank
     */
    private static int countBlankLines(DetailNode node) {
        int blankLinesCount = 0;
        boolean emptyComment = false;
        for (DetailNode child : node.getChildren()) {
            if (child.getType() == JavadocTokenTypes.EOF) {
                emptyComment = true;
            }
            else if (child.getType() == JavadocTokenTypes.NEWLINE) {
                ++blankLinesCount;
            }
            else if (child.getType() != JavadocTokenTypes.LEADING_ASTERISK
                    && !isOptionalPrefix(child)) {
                break;
            }
        }
        if (emptyComment) {
            blankLinesCount = -1;
        }
        return blankLinesCount;
    }

    /**
     * <p>
     * Checks that the text node consists only of asterisks and spaces.
     * This is to avoid false positives for comments like this:
     * </p>
     * <pre>
     * &#47***
     *  * Some Javadoc.
     *  *&#47;
     * </pre>
     * <p>
     * There is an extra asterisk on the first line. Since this asterisk will not appear in
     * the generated documentation, it should not be considered as the beginning of the text.
     * </p>
     *
     * @param child node to check
     * @return {@code true} if the node contains only optional prefix
     */
    private static boolean isOptionalPrefix(DetailNode child) {
        return child.getType() == JavadocTokenTypes.TEXT
            && OPTIONAL_PREFIX.matcher(child.getText()).matches();
    }

}
