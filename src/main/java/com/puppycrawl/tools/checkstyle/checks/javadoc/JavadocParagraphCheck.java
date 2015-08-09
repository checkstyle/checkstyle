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

import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

/**
 * Checks that:
 * <ul>
 * <li>There is one blank line between each of two paragraphs
 * and one blank line before the at-clauses block if it is present.</li>
 * <li>Each paragraph but the first has &lt;p&gt; immediately
 * before the first word, with no space after.</li>
 * </ul>
 *
 * <p>
 * The check can be specified by option tagImmediatelyBeforeFirstWord,
 * which says whether the &lt;p&gt; tag should be placed immediately before
 * the first word.
 *
 * <p>
 * Default configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;JavadocParagraph&quot;/&gt;
 * </pre>
 *
 * <p>
 * To allow newlines and spaces immediately after the &lt;p&gt; tag:
 * <pre>
 * &lt;module name=&quot;JavadocParagraph&quot;&gt;
 *      &lt;property name=&quot;tagImmediatelyBeforeFirstWord&quot;
 *                   value==&quot;false&quot;/&gt;
 * &lt;/module&quot;&gt;
 * </pre>
 *
 * <p>
 * In case of tagImmediatelyBeforeFirstWord set to false
 * the following example will not have any violations:
 * <pre>
 *   /**
 *    * &lt;p&gt;
 *    * Some Javadoc.
 *    *
 *    * &lt;p&gt;  Some Javadoc.
 *    *
 *    * &lt;p&gt;
 *    * &lt;pre&gt;
 *    * Some preformatted Javadoc.
 *    * &lt;/pre&gt;
 *    *
 *    *&#47;
 * </pre>
 * @author maxvetrenko
 * @author Vladislav Lisetskiy
 *
 */
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
     * Whether the &lt;p&gt; tag should be placed immediately before the first word.
     */
    private boolean tagImmediatelyBeforeFirstWord = true;

    /**
     * Sets tagImmediatelyBeforeFirstWord.
     * @param value value to set.
     */
    public void setAllowNewlineParagraph(boolean value) {
        tagImmediatelyBeforeFirstWord = value;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.NEWLINE,
            JavadocTokenTypes.HTML_ELEMENT,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (ast.getType() == JavadocTokenTypes.NEWLINE && isEmptyLine(ast)) {
            checkEmptyLine(ast);
        }
        else if (ast.getType() == JavadocTokenTypes.HTML_ELEMENT
                && JavadocUtils.getFirstChild(ast).getType() == JavadocTokenTypes.P_TAG_OPEN) {
            checkParagraphTag(ast);
        }
    }

    /**
     * Determines whether or not the next line after empty line has paragraph tag in the beginning.
     * @param newline NEWLINE node.
     */
    private void checkEmptyLine(DetailNode newline) {
        final DetailNode nearestToken = getNearestNode(newline);
        if (!isLastEmptyLine(newline) && nearestToken.getChildren().length > 1) {
            log(newline.getLineNumber(), MSG_TAG_AFTER);
        }
    }

    /**
     * Determines whether or not the line with paragraph tag has previous empty line.
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
        if (tagImmediatelyBeforeFirstWord && isImmediatelyFollowedByText(tag)) {
            log(tag.getLineNumber(), MSG_MISPLACED_TAG);
        }
    }

    /**
     * Returns nearest node.
     * @param node DetailNode node.
     * @return nearest node.
     */
    private static DetailNode getNearestNode(DetailNode node) {
        DetailNode tag = JavadocUtils.getNextSibling(node);
        while (tag.getType() == JavadocTokenTypes.LEADING_ASTERISK
                || tag.getType() == JavadocTokenTypes.NEWLINE) {
            tag = JavadocUtils.getNextSibling(tag);
        }
        return tag;
    }

    /**
     * Determines whether or not the line is empty line.
     * @param newLine NEWLINE node.
     * @return true, if line is empty line.
     */
    private static boolean isEmptyLine(DetailNode newLine) {
        DetailNode previousSibling = JavadocUtils.getPreviousSibling(newLine);
        if (previousSibling == null
                || previousSibling.getParent().getType() != JavadocTokenTypes.JAVADOC) {
            return false;
        }
        if (previousSibling.getType() == JavadocTokenTypes.TEXT
                && previousSibling.getChildren().length == 1) {
            previousSibling = JavadocUtils.getPreviousSibling(previousSibling);
        }
        return previousSibling != null
                && previousSibling.getType() == JavadocTokenTypes.LEADING_ASTERISK;
    }

    /**
     * Determines whether or not the line with paragraph tag is first line in javadoc.
     * @param paragraphTag paragraph tag.
     * @return true, if line with paragraph tag is first line in javadoc.
     */
    private static boolean isFirstParagraph(DetailNode paragraphTag) {
        DetailNode previousNode = JavadocUtils.getPreviousSibling(paragraphTag);
        while (previousNode != null) {
            if (previousNode.getType() == JavadocTokenTypes.TEXT
                    && previousNode.getChildren().length > 1
                || previousNode.getType() != JavadocTokenTypes.LEADING_ASTERISK
                    && previousNode.getType() != JavadocTokenTypes.NEWLINE
                    && previousNode.getType() != JavadocTokenTypes.TEXT) {
                return false;
            }
            previousNode = JavadocUtils.getPreviousSibling(previousNode);
        }
        return true;
    }

    /**
     * Finds and returns nearest empty line in javadoc.
     * @param node DetailNode node.
     * @return Some nearest empty line in javadoc.
     */
    private static DetailNode getNearestEmptyLine(DetailNode node) {
        DetailNode newLine = JavadocUtils.getPreviousSibling(node);
        while (newLine != null) {
            final DetailNode previousSibling = JavadocUtils.getPreviousSibling(newLine);
            if (newLine.getType() == JavadocTokenTypes.NEWLINE && isEmptyLine(newLine)) {
                break;
            }
            newLine = previousSibling;
        }
        return newLine;
    }

    /**
     * Tests if NEWLINE node is a last node in javadoc.
     * @param newLine NEWLINE node.
     * @return true, if NEWLINE node is a last node in javadoc.
     */
    private static boolean isLastEmptyLine(DetailNode newLine) {
        DetailNode nextNode = JavadocUtils.getNextSibling(newLine);
        while (nextNode != null && nextNode.getType() != JavadocTokenTypes.JAVADOC_TAG) {
            if (nextNode.getType() == JavadocTokenTypes.TEXT
                    && nextNode.getChildren().length > 1
                    || nextNode.getType() == JavadocTokenTypes.HTML_ELEMENT) {
                return false;
            }
            nextNode = JavadocUtils.getNextSibling(nextNode);
        }
        return true;
    }

    /**
     * Tests whether the paragraph tag is immediately followed by the text.
     * @param tag html tag.
     * @return true, if the paragraph tag is immediately followed by the text.
     */
    private static boolean isImmediatelyFollowedByText(DetailNode tag) {
        final DetailNode nextSibling = JavadocUtils.getNextSibling(tag);
        return nextSibling.getType() == JavadocTokenTypes.NEWLINE
                || nextSibling.getType() == JavadocTokenTypes.EOF
                || Utils.startsWithChar(nextSibling.getText(), ' ');
    }
}
