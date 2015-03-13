////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.util.regex.Pattern;

import com.google.common.base.CharMatcher;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * <p>
 * Checks that <a href="
 * http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html#firstsentence">
 * Javadoc summary sentence</a> does not contain phrases that are not recommended to use.
 * By default Check validate that first sentence is not empty:</p><br/>
 * <pre>
 * &lt;module name=&quot;SummaryJavadocCheck&quot;/&gt;
 * </pre>
 * <p>
 * To ensure that summary do not contain phrase like "This method returns" , use following config:
 * <p>
 * <pre>
 * &lt;module name=&quot;SummaryJavadocCheck&quot;&gt;
 *     &lt;property name=&quot;forbiddenSummaryFragments&quot;
 *     value=&quot;^This method returns.*&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To specify period symbol at the end of first javadoc sentence - use following config:
 * <pre>
 * &lt;module name=&quot;SummaryJavadocCheck&quot;&gt;
 *     &lt;property name=&quot;period&quot;
 *     value=&quot;period&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * </p>
 *
 * @author max
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public class SummaryJavadocCheck extends AbstractJavadocCheck
{

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String SUMMARY_FIRST_SENTENCE = "summary.first.sentence";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String SUMMARY_JAVADOC = "summary.javaDoc";

    /**
     * Regular expression for forbidden summary fragments.
     */
    private Pattern forbiddenSummaryFragments = Utils.createPattern("^$");

    /**
     * Period symbol at the end of first javadoc sentence.
     */
    private String period = ".";

    /**
     * Sets custom value of regular expression for forbidden summary fragments.
     * @param pattern user's value.
     */
    public void setForbiddenSummaryFragments(String pattern)
    {
        forbiddenSummaryFragments = Utils.createPattern(pattern);
    }

    /**
     * Sets value of period symbol at the end of first javadoc sentence.
     * @param period period's value.
     */
    public void setPeriod(String period)
    {
        this.period = period;
    }

    @Override
    public int[] getDefaultJavadocTokens()
    {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast)
    {
        String firstSentence = getFirstSentence(ast);
        final int endOfSentence = firstSentence.length() - 1;
        if (firstSentence.length() < 5) {
            log(ast.getLineNumber(), SUMMARY_FIRST_SENTENCE);
        }
        else {
            firstSentence = firstSentence.substring(0, endOfSentence);
            if (containsForbiddenFragment(firstSentence)) {
                log(ast.getLineNumber(), SUMMARY_JAVADOC);
            }
        }
    }

    /**
     * Finds and returns first sentence.
     * @param ast Javadoc root node.
     * @return first sentence.
     */
    private String getFirstSentence(DetailNode ast)
    {
        final StringBuilder result = new StringBuilder();
        for (DetailNode child : ast.getChildren()) {
            if (child.getType() != JavadocTokenTypes.JAVADOC_INLINE_TAG
                && child.getText().contains(". "))
            {
                result.append(getCharsTillDot(child));
                break;
            }
            else {
                result.append(child.getText());
            }
        }
        return result.toString();
    }

    /**
     * Finds and returns chars till first dot.
     * @param textNode node with javadoc text.
     * @return String with chars till first dot.
     */
    private String getCharsTillDot(DetailNode textNode)
    {
        final StringBuilder result = new StringBuilder();
        for (DetailNode child : textNode.getChildren()) {
            result.append(child.getText());
            if (".".equals(child.getText())
                && JavadocUtils.getNextSibling(child).getType() == JavadocTokenTypes.WS)
            {
                break;
            }
        }
        return result.toString();
    }

    /**
     * Tests if first sentence contains forbidden summary fragment.
     * @param firstSentence String with first sentence.
     * @return true, if first sentence contains forbidden summary fragment.
     */
    private boolean containsForbiddenFragment(String firstSentence)
    {
        // This regexp is used to convert multiline javdoc to single line without stars.
        String javadocText = firstSentence.replaceAll("\n[ ]+(\\*)|^[ ]+(\\*)", " ");
        javadocText = CharMatcher.WHITESPACE.trimAndCollapseFrom(javadocText, ' ');
        return forbiddenSummaryFragments.matcher(javadocText).find();
    }
}
