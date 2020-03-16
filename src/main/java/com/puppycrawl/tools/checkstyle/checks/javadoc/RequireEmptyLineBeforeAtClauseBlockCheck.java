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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks that one blank line before the at-clauses block if it is present in Javadoc.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
 * Tight-HTML Rules</a>.
 * Type is {@code boolean}. Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;RequireEmptyLineBeforeAtClauseBlock&quot;/&gt;
 * </pre>
 * <p>
 * By default, the check will report a violation if there is no blank line before the at-clause.
 * </p>
 * <pre>
 * &#47;**
 *  * testMethod's javadoc.
 *  * &#64;return something (violation)
 *  *&#47;
 * public boolean testMethod() {
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
 * {@code javadoc.tag.line.before}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * </ul>
 *
 * @since 8.35
 */
@StatelessCheck
public class RequireEmptyLineBeforeAtClauseBlockCheck extends AbstractJavadocCheck {

    /**
     * The key in "messages.properties" for the message that describes an at-clause in javadoc
     * requiring an empty line before it.
     */
    public static final String MSG_JAVADOC_TAG_LINE_BEFORE = "javadoc.tag.line.before";

    /**
     * When javadoc has only a javadoc tag like {@literal @} in it, the JAVADOC_TAG in a JAVADOC
     * detail node will always have 3 siblings before it. The parse tree looks like:
     * <pre>
     * JAVADOC
     * \
     *  -- NEWLINE
     *  -- LEADING_ASTERISK
     *  -- WS
     *  -- JAVADOC_TAG
     * </pre>
     */
    private static final int NUM_OF_SIBLINGS_BEFORE_ONLY_JAVADOC_TAG = 3;

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC_TAG,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        // No need to filter token because overridden getDefaultJavadocTokens ensures that we only
        // receive JAVADOC_TAG DetailNode.
        checkAtClause(ast);
    }

    /**
     * Logs when there is no empty line before the at clause.
     *
     * @param atClause the at clause to check for an empty space before it.
     */
    private void checkAtClause(DetailNode atClause) {
        if (!isAnotherAtClauseBefore(atClause)
                && !isOnlyAtClauseNothingElse(atClause)
                && insufficientConsecutiveNewlines(atClause)) {
            log(atClause.getLineNumber(),
                    MSG_JAVADOC_TAG_LINE_BEFORE,
                    atClause.getChildren()[0].getText());
        }
    }

    private static boolean isAnotherAtClauseBefore(DetailNode atClause) {
        boolean found = false;
        DetailNode currentClause = JavadocUtil.getPreviousSibling(atClause);
        while (currentClause != null) {
            if (currentClause.getType() == JavadocTokenTypes.JAVADOC_TAG) {
                found = true;
                break;
            }
            currentClause = JavadocUtil.getPreviousSibling(currentClause);
        }
        return found;
    }

    private static boolean isOnlyAtClauseNothingElse(DetailNode atClause) {
        int count = 0;
        DetailNode currentClause = JavadocUtil.getPreviousSibling(atClause);
        while (currentClause != null) {
            count++;
            currentClause = JavadocUtil.getPreviousSibling(currentClause);
        }
        return count == NUM_OF_SIBLINGS_BEFORE_ONLY_JAVADOC_TAG;
    }

    /**
     * Iterates through the previous siblings of the atClause looking for empty lines until
     * there are no more siblings or it hits something other than asterisk, whitespace or newline.
     * If it finds at least one empty line, return true. Return false otherwise.
     *
     * @param atClause the atClause to check if there are sufficient empty lines before it.
     * @return true if there are enough empty lines before the atClause.
     */
    private static boolean insufficientConsecutiveNewlines(DetailNode atClause) {
        int count = 0;
        DetailNode currentClause = JavadocUtil.getPreviousSibling(atClause);
        while (currentClause != null
                && count <= 1
                && (currentClause.getType() == JavadocTokenTypes.NEWLINE
                || currentClause.getType() == JavadocTokenTypes.WS
                || currentClause.getType() == JavadocTokenTypes.LEADING_ASTERISK)) {
            if (currentClause.getType() == JavadocTokenTypes.NEWLINE) {
                count++;
            }
            currentClause = JavadocUtil.getPreviousSibling(currentClause);
        }

        return count <= 1;
    }
}
