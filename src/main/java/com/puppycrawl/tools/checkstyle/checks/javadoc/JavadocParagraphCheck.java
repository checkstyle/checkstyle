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
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks the Javadoc paragraph.
 * </p>
 * <p>
 * Checks that:
 * </p>
 * <ul>
 * <li>There is one blank line between each of two paragraphs
 * and one blank line before the at-clauses block if it is present.</li>
 * <li>Each paragraph but the first has &lt;p&gt; immediately
 * before the first word, with no space after.</li>
 * </ul>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
 * Tight-HTML Rules</a>.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowNewlineParagraph} - Control whether the &lt;p&gt; tag
 * should be placed immediately before the first word.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code atClauseRequiresEmptyLineBefore} - Control whether the group of at-clauses at
 * the bottom of a javadoc like {@code @return}, require an empty line before the first at-clause.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the default check:
 * </p>
 * <pre>
 * &lt;module name=&quot;JavadocParagraph&quot;/&gt;
 * </pre>
 * <p>
 * By default, the check will report a violation if there is a new line
 * or whitespace after the &lt;p&gt; tag:
 * </p>
 * <pre>
 * &#47;**
 *  * No tag (ok).
 *  *
 *  * &lt;p&gt;Tag immediately before the text (ok).
 *  * &lt;p&gt;No blank line before the tag (violation).
 *  *
 *  * &lt;p&gt;
 *  * New line after tag (violation).
 *  *
 *  * &lt;p&gt; Whitespace after tag (violation).
 *  *
 *  *&#47;
 * public class TestClass {
 * }
 * </pre>
 * <p>
 * To allow newlines and spaces immediately after the &lt;p&gt; tag:
 * </p>
 * <pre>
 * &lt;module name=&quot;JavadocParagraph&quot;&gt;
 *   &lt;property name=&quot;allowNewlineParagraph&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * In case of {@code allowNewlineParagraph} set to {@code false}
 * the following example will not have any violations:
 * </p>
 * <pre>
 * &#47;**
 *  * No tag (ok).
 *  *
 *  * &lt;p&gt;Tag immediately before the text (ok).
 *  * &lt;p&gt;No blank line before the tag (violation).
 *  *
 *  * &lt;p&gt;
 *  * New line after tag (ok).
 *  *
 *  * &lt;p&gt; Whitespace after tag (ok).
 *  *
 *  *&#47;
 * public class TestClass {
 * }
 * </pre>
 * <p>
 * By default {@code atClauseRequiresEmptyLineBefore} is set to {@code false}. As a result the
 * following example will not have any violations:
 * </p>
 * <pre>
 * &#47;**
 *  * No tag (ok).
 *  * &#64;return something (violation ignored)
 *  *&#47;
 * public class TestClass {
 * }
 * </pre>
 * <p>
 * To require empty lines before the at-clauses add this to your config:
 * </p>
 * <pre>
 * &lt;module name=&quot;JavadocParagraph&quot;&gt;
 *   &lt;property name=&quot;atClauseRequiresEmptyLineBefore&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @since 6.0
 */
@StatelessCheck
public class JavadocParagraphCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_TAG_AFTER = "javadoc.paragraph.tag.after";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LINE_BEFORE = "javadoc.paragraph.line.before";

    /**
     * The key in "messages.properties" for the message that describes an at-clause in javadoc
     * requiring an empty line before it.
     */
    public static final String MSG_JAVADOC_TAG_LINE_BEFORE = "javadoc.tag.line.before";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_REDUNDANT_PARAGRAPH = "javadoc.paragraph.redundant.paragraph";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISPLACED_TAG = "javadoc.paragraph.misplaced.tag";

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

    /**
     * Control whether the &lt;p&gt; tag should be placed immediately before the first word.
     */
    private boolean allowNewlineParagraph = true;

    /**
     * Control whether the group of at-clauses at the bottom of a javadoc like {@code @return},
     * require an empty line before the first at-clause.
     */
    private boolean atClauseRequiresEmptyLineBefore;

    /**
     * Setter to control whether the &lt;p&gt; tag should be placed
     * immediately before the first word.
     *
     * @param value value to set.
     */
    public void setAllowNewlineParagraph(boolean value) {
        allowNewlineParagraph = value;
    }

    /**
     * Setter to control whether the group of at-clauses at the bottom of a javadoc like
     * {@code @return}, require an empty line before the first at-clause.
     *
     * @param value value to set.
     */
    public void setAtClauseRequiresEmptyLineBefore(boolean value) {
        atClauseRequiresEmptyLineBefore = value;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.NEWLINE,
            JavadocTokenTypes.HTML_ELEMENT,
            JavadocTokenTypes.JAVADOC_TAG,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (ast.getType() == JavadocTokenTypes.NEWLINE && isEmptyLine(ast)) {
            checkEmptyLine(ast);
        }
        else if (ast.getType() == JavadocTokenTypes.HTML_ELEMENT
                && JavadocUtil.getFirstChild(ast).getType() == JavadocTokenTypes.P_TAG_START) {
            checkParagraphTag(ast);
        }
        else if (atClauseRequiresEmptyLineBefore
                && ast.getType() == JavadocTokenTypes.JAVADOC_TAG) {
            checkAtClause(ast);
        }
    }

    /**
     * Determines whether or not the next line after empty line has paragraph tag in the beginning.
     *
     * @param newline NEWLINE node.
     */
    private void checkEmptyLine(DetailNode newline) {
        final DetailNode nearestToken = getNearestNode(newline);
        if (nearestToken.getType() == JavadocTokenTypes.TEXT
                && !CommonUtil.isBlank(nearestToken.getText())) {
            log(newline.getLineNumber(), MSG_TAG_AFTER);
        }
    }

    /**
     * Determines whether or not the line with paragraph tag has previous empty line.
     *
     * @param tag html tag.
     */
    private void checkParagraphTag(DetailNode tag) {
        final DetailNode newLine = getNearestEmptyLine(tag);
        if (isFirstParagraph(tag)) {
            log(tag.getLineNumber(), MSG_REDUNDANT_PARAGRAPH);
        }
        else if (newLine == null || tag.getLineNumber() - newLine.getLineNumber() != 1) {
            log(tag.getLineNumber(), MSG_LINE_BEFORE);
        }
        if (allowNewlineParagraph && isImmediatelyFollowedByText(tag)) {
            log(tag.getLineNumber(), MSG_MISPLACED_TAG);
        }
    }

    /**
     * Returns nearest node.
     *
     * @param node DetailNode node.
     * @return nearest node.
     */
    private static DetailNode getNearestNode(DetailNode node) {
        DetailNode tag = JavadocUtil.getNextSibling(node);
        while (tag.getType() == JavadocTokenTypes.LEADING_ASTERISK
                || tag.getType() == JavadocTokenTypes.NEWLINE) {
            tag = JavadocUtil.getNextSibling(tag);
        }
        return tag;
    }

    /**
     * Determines whether or not the line is empty line.
     *
     * @param newLine NEWLINE node.
     * @return true, if line is empty line.
     */
    private static boolean isEmptyLine(DetailNode newLine) {
        boolean result = false;
        DetailNode previousSibling = JavadocUtil.getPreviousSibling(newLine);
        if (previousSibling != null
                && previousSibling.getParent().getType() == JavadocTokenTypes.JAVADOC) {
            if (previousSibling.getType() == JavadocTokenTypes.TEXT
                    && CommonUtil.isBlank(previousSibling.getText())) {
                previousSibling = JavadocUtil.getPreviousSibling(previousSibling);
            }
            result = previousSibling != null
                    && previousSibling.getType() == JavadocTokenTypes.LEADING_ASTERISK;
        }
        return result;
    }

    /**
     * Determines whether or not the line with paragraph tag is first line in javadoc.
     *
     * @param paragraphTag paragraph tag.
     * @return true, if line with paragraph tag is first line in javadoc.
     */
    private static boolean isFirstParagraph(DetailNode paragraphTag) {
        boolean result = true;
        DetailNode previousNode = JavadocUtil.getPreviousSibling(paragraphTag);
        while (previousNode != null) {
            if (previousNode.getType() == JavadocTokenTypes.TEXT
                    && !CommonUtil.isBlank(previousNode.getText())
                || previousNode.getType() != JavadocTokenTypes.LEADING_ASTERISK
                    && previousNode.getType() != JavadocTokenTypes.NEWLINE
                    && previousNode.getType() != JavadocTokenTypes.TEXT) {
                result = false;
                break;
            }
            previousNode = JavadocUtil.getPreviousSibling(previousNode);
        }
        return result;
    }

    /**
     * Finds and returns nearest empty line in javadoc.
     *
     * @param node DetailNode node.
     * @return Some nearest empty line in javadoc.
     */
    private static DetailNode getNearestEmptyLine(DetailNode node) {
        DetailNode newLine = JavadocUtil.getPreviousSibling(node);
        while (newLine != null) {
            final DetailNode previousSibling = JavadocUtil.getPreviousSibling(newLine);
            if (newLine.getType() == JavadocTokenTypes.NEWLINE && isEmptyLine(newLine)) {
                break;
            }
            newLine = previousSibling;
        }
        return newLine;
    }

    /**
     * Tests whether the paragraph tag is immediately followed by the text.
     *
     * @param tag html tag.
     * @return true, if the paragraph tag is immediately followed by the text.
     */
    private static boolean isImmediatelyFollowedByText(DetailNode tag) {
        final DetailNode nextSibling = JavadocUtil.getNextSibling(tag);
        return nextSibling.getType() == JavadocTokenTypes.NEWLINE
                || nextSibling.getType() == JavadocTokenTypes.EOF
                || CommonUtil.startsWithChar(nextSibling.getText(), ' ');
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
    public static boolean insufficientConsecutiveNewlines(DetailNode atClause) {
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
