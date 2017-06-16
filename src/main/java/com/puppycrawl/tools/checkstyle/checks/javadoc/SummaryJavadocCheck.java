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
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.google.common.base.CharMatcher;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Checks that <a href=
 * "http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html#firstsentence">
 * Javadoc summary sentence</a> does not contain phrases that are not recommended to use.
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
     * This regexp is used to convert multiline javadoc to single line without stars.
     */
    private static final Pattern JAVADOC_MULTILINE_TO_SINGLELINE_PATTERN =
            Pattern.compile("\n[ ]+(\\*)|^[ ]+(\\*)");

    /** Period literal. */
    private static final String PERIOD = ".";

    /**
     * Stores allowed values in document for inherit doc literal.
     */
    private static final Set<Integer> SKIP_TOKENS = new HashSet<>(
        Arrays.asList(JavadocTokenTypes.NEWLINE,
                       JavadocTokenTypes.LEADING_ASTERISK,
                       JavadocTokenTypes.EOF)
    );
    /**
     * Regular expression for forbidden summary fragments.
     */
    private Pattern forbiddenSummaryFragments = CommonUtils.createPattern("^$");

    /**
     * Period symbol at the end of first javadoc sentence.
     */
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
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        String firstSentence = getFirstSentence(ast);
        final int endOfSentence = firstSentence.lastIndexOf(period);
        if (endOfSentence == -1) {
            if (!isOnlyInheritDoc(ast)) {
                log(ast.getLineNumber(), MSG_SUMMARY_FIRST_SENTENCE);
            }
        }
        else {
            firstSentence = firstSentence.substring(0, endOfSentence);
            if (containsForbiddenFragment(firstSentence)) {
                log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC);
            }
        }
    }

    /**
     * Finds if inheritDoc is placed properly in java doc.
     * @param ast Javadoc root node.
     * @return true if inheritDoc is valid or false.
     */
    private static boolean isOnlyInheritDoc(DetailNode ast) {
        boolean extraTextFound = false;
        boolean containsInheritDoc = false;
        for (DetailNode child : ast.getChildren()) {
            if (child.getType() == JavadocTokenTypes.TEXT) {
                if (!CommonUtils.isBlank(child.getText())) {
                    extraTextFound = true;
                }
            }
            else if (child.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                if (child.getChildren()[1].getType() == JavadocTokenTypes.INHERIT_DOC_LITERAL) {
                    containsInheritDoc = true;
                }
                else {
                    extraTextFound = true;
                }
            }
            else if (!SKIP_TOKENS.contains(child.getType())) {
                extraTextFound = true;
            }
            if (extraTextFound) {
                break;
            }
        }
        return containsInheritDoc && !extraTextFound;
    }

    /**
     * Finds and returns first sentence.
     * @param ast Javadoc root node.
     * @return first sentence.
     */
    private static String getFirstSentence(DetailNode ast) {
        final StringBuilder result = new StringBuilder();
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
        javadocText = CharMatcher.WHITESPACE.trimAndCollapseFrom(javadocText, ' ');
        return forbiddenSummaryFragments.matcher(javadocText).find();
    }
}
