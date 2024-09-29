///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

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
 * <li>There is one blank line between each of two paragraphs.</li>
 * <li>Each paragraph but the first has &lt;p&gt; immediately
 * before the first word, with no space after.</li>
 * </ul>
 * <ul>
 * <li>
 * Property {@code allowNewlineParagraph} - Control whether the &lt;p&gt; tag
 * should be placed immediately before the first word.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
 * Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
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
 * {@code javadoc.paragraph.line.before}
 * </li>
 * <li>
 * {@code javadoc.paragraph.misplaced.tag}
 * </li>
 * <li>
 * {@code javadoc.paragraph.redundant.paragraph}
 * </li>
 * <li>
 * {@code javadoc.paragraph.tag.after}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.unclosedHtml}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * </ul>
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
     * Control whether the &lt;p&gt; tag should be placed immediately before the first word.
     */
    private boolean allowNewlineParagraph = true;

    /**
     * Setter to control whether the &lt;p&gt; tag should be placed
     * immediately before the first word.
     *
     * @param value value to set.
     * @since 6.9
     */
    public void setAllowNewlineParagraph(boolean value) {
        allowNewlineParagraph = value;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.NEWLINE,
            JavadocTokenTypes.HTML_ELEMENT,
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
        if (!allowNewlineParagraph && isImmediatelyFollowedByNewLine(tag)) {
            log(tag.getLineNumber(), MSG_MISPLACED_TAG);
        }
        if (isImmediatelyFollowedByText(tag)) {
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
        DetailNode currentNode = node;
        while (currentNode.getType() == JavadocTokenTypes.LEADING_ASTERISK
                || currentNode.getType() == JavadocTokenTypes.NEWLINE) {
            currentNode = JavadocUtil.getNextSibling(currentNode);
        }
        return currentNode;
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
        DetailNode newLine = node;
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
        return nextSibling.getType() == JavadocTokenTypes.EOF
                || nextSibling.getText().startsWith(" ");
    }

    /**
     * Tests whether the paragraph tag is immediately followed by the new line.
     *
     * @param tag html tag.
     * @return true, if the paragraph tag is immediately followed by the text.
     */
    private static boolean isImmediatelyFollowedByNewLine(DetailNode tag) {
        final DetailNode nextSibling = JavadocUtil.getNextSibling(tag);
        return nextSibling.getType() == JavadocTokenTypes.NEWLINE;
    }

}
