////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import com.google.common.base.CharMatcher;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtils;

/**
 * <p>
 * Checks that <a href=
 * "http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html#firstsentence">
 * Javadoc summary sentence</a> does not contain phrases that are not recommended to use.
 * Check also violate javadoc that does not contain first sentence.
 * By default Check validate that first sentence is not empty:</p><br>
 * <pre>
 * &lt;module name=&quot;SummaryJavadocCheck&quot;/&gt;
 * </pre>
 *
 * <p>To ensure that summary do not contain phrase like "This method returns",
 *  use following config:
 *
 * <pre>
 * &lt;module name=&quot;SummaryJavadocCheck&quot;&gt;
 *     &lt;property name=&quot;forbiddenSummaryFragments&quot;
 *     value=&quot;^This method returns.*&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To specify period symbol at the end of first javadoc sentence - use following config:
 * </p>
 * <pre>
 * &lt;module name=&quot;SummaryJavadocCheck&quot;&gt;
 *     &lt;property name=&quot;period&quot;
 *     value=&quot;period&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 *
 * @author max
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
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

    /** Inherit doc literal. */
    private static final String INHERIT_DOC = "{@inheritDoc}";

    /** Set of allowed Tokens tags in summary java doc. */
    private static final Set<Integer> ALLOWED_TYPES = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(JavadocTokenTypes.TEXT,
                    JavadocTokenTypes.WS))
    );

    /** Regular expression for forbidden summary fragments. */
    private Pattern forbiddenSummaryFragments = CommonUtils.createPattern("^$");

    /** Period symbol at the end of first javadoc sentence. */
    private String period = PERIOD;

    /**
     * Sets custom value of regular expression for forbidden summary fragments.
     * @param pattern a pattern.
     */
    public void setForbiddenSummaryFragments(Pattern pattern) {
        forbiddenSummaryFragments = pattern;
    }

    /**
     * Sets value of period symbol at the end of first javadoc sentence.
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
        String firstSentence = getFirstSentence(ast);
        final int endOfSentence = firstSentence.lastIndexOf(period);
        final String summaryDoc = getSummarySentence(ast);
        if (summaryDoc.isEmpty()) {
            log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC_MISSING);
        }
        else if (!period.isEmpty()
                && !summaryDoc.contains(period)
                && !summaryDoc.equals(INHERIT_DOC)) {
            log(ast.getLineNumber(), MSG_SUMMARY_FIRST_SENTENCE);
        }
        if (endOfSentence != -1) {
            firstSentence = firstSentence.substring(0, endOfSentence);
            if (containsForbiddenFragment(firstSentence)) {
                log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC);
            }
        }
    }

    /**
     * Checks if period is at the end of sentence.
     * @param ast Javadoc root node.
     * @return error string
     */
    private static String getSummarySentence(DetailNode ast) {
        boolean flag = true;
        final StringBuilder result = new StringBuilder(256);
        for (DetailNode child : ast.getChildren()) {
            if (ALLOWED_TYPES.contains(child.getType())) {
                result.append(child.getText());
            }
            else if (child.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG
                    && getContentOfChild(child).equals(INHERIT_DOC)) {
                result.append(INHERIT_DOC);
            }
            else if (child.getType() == JavadocTokenTypes.HTML_ELEMENT
                    && CommonUtils.isBlank(result.toString().trim())) {
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
     * Returns content when token type is javadoc inline tag.
     * @param child javadoc inline tag ast.
     * @return content of child nodes as string.
     */
    private static String getContentOfChild(DetailNode child) {
        final StringBuilder contents = new StringBuilder(256);
        for (DetailNode node : child.getChildren()) {
            contents.append(node.getText().trim());
        }
        return contents.toString();
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
            tempNode = JavadocUtils.getNextSibling(tempNode);
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

            if (child.getType() != JavadocTokenTypes.JAVADOC_INLINE_TAG
                && text.contains(periodSuffix)) {
                result.append(text.substring(0, text.indexOf(periodSuffix) + 1));
                break;
            }
            else {
                result.append(text);
            }
        }
        return result.toString();
    }

    /**
     * Tests if first sentence contains forbidden summary fragment.
     * @param firstSentence String with first sentence.
     * @return true, if first sentence contains forbidden summary fragment.
     */
    private boolean containsForbiddenFragment(String firstSentence) {
        String javadocText = JAVADOC_MULTILINE_TO_SINGLELINE_PATTERN
                .matcher(firstSentence).replaceAll(" ");
        javadocText = CharMatcher.whitespace().trimAndCollapseFrom(javadocText, ' ');
        return forbiddenSummaryFragments.matcher(javadocText).find();
    }
}
