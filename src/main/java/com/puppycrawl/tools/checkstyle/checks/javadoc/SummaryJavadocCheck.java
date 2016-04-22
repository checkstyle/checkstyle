////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.text.BreakIterator;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Locale;
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
 * @author <a href="mailto:ybbpgfjtey@126.com">Guo Yuhang</a>
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

    /** This regexp is used to trim and remove asterisks at the end of a sentence. */
    private static final Pattern REMOVE_TAIL_ASTERISKS_PATTERN =
            Pattern.compile("[\\s*]+$");

    /** Period literal. */
    private static final String PERIOD = ".";

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
     * @param pattern user's value.
     */
    public void setForbiddenSummaryFragments(String pattern) {
        forbiddenSummaryFragments = CommonUtils.createPattern(pattern);
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
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final String firstSentence = getFirstSentence(ast);
        if (firstSentence.isEmpty()) {
            log(ast.getLineNumber(), MSG_SUMMARY_FIRST_SENTENCE);
        }
        else if (firstSentence.endsWith(period)) {
            if (containsForbiddenFragment(firstSentence)) {
                log(ast.getLineNumber(), MSG_SUMMARY_JAVADOC);
            }
        }
        else {
            if (!firstSentence.startsWith("{@inheritDoc}")) {
                log(ast.getLineNumber(), MSG_SUMMARY_FIRST_SENTENCE);
            }
        }
    }

    /**
     * Finds and returns first sentence.
     * @param ast Javadoc root node.
     * @return first sentence.
     * @see BreakIterator
     */
    private static String getFirstSentence(DetailNode ast) {
        final String sentences = trimAndRemoveTailAsterisks(getPlainJavadoc(ast));
        final BreakIterator cutter = BreakIterator.getSentenceInstance(Locale.getDefault());
        cutter.setText(sentences);
        final int firstBreakIndex = cutter.next();
        final String firstSentence;
        if (firstBreakIndex == BreakIterator.DONE) {
            // If there's no senetence breaker ( period, question mark, exclamation mark, etc...)
            // then return full text;
            firstSentence = sentences;
        }
        else {
            // Cut and get first sentence.
            firstSentence = trimAndRemoveTailAsterisks(sentences.substring(0, firstBreakIndex));
        }
        return firstSentence;
    }

    /**
     * Get plain text of full javadoc.
     *
     * <p>Will remove html tag, remove leading asterisks.
     * @param ast Javadoc root node.
     * @return plain text.
     */
    private static String getPlainJavadoc(DetailNode ast) {
        final StringBuilder result = new StringBuilder();
        final Deque<DetailNode> astVisitor =
                new ArrayDeque<>(Arrays.asList(ast.getChildren()));
        DetailNode node = astVisitor.poll();
        while (node != null) {
            // Get all text
            if (node.getType() == JavadocTokenTypes.TEXT
                    || node.getType() == JavadocTokenTypes.NEWLINE
                    || node.getType() == JavadocTokenTypes.WS) {
                result.append(node.getText());
            }
            else if (node.getType() == JavadocTokenTypes.JAVADOC_TAG) {
                // For compatbility, accept text in javadoc tag.
                // add tag name, and unfold other children.
                result.append(node.getChildren()[0].getText());
                final DetailNode[] children = node.getChildren();
                for (int i = children.length - 1; i >= 1; i--) {
                    astVisitor.addFirst(children[i]);
                }
            }
            else if (node.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                // Special treatment of inline tag, remove period in it.
                result.append(node.getText().replace('.', '#').replace('!', '#').replace('?', '#'));
            }
            else {
                // Unfold html content, but keep in order.
                // Other element that doesn't has children will be ignored.
                final DetailNode[] children = node.getChildren();
                for (int i = children.length - 1; i >= 0; i--) {
                    astVisitor.addFirst(children[i]);
                }
            }
            node = astVisitor.poll();
        }
        return result.toString();
    }

    /**
     * Trim and remove tail astrisks of a sentence.
     * @param sentence String to be trimed.
     * @return String without astrisk in the end.
     */
    private static String trimAndRemoveTailAsterisks(String sentence) {
        return REMOVE_TAIL_ASTERISKS_PATTERN.matcher(sentence).replaceAll("").trim();
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
