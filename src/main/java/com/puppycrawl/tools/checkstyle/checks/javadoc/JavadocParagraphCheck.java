////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
 * Default configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;JavadocParagraph&quot;/&gt;
 * </pre>
 *
 * @author maxvetrenko
 *
 */
public class JavadocParagraphCheck extends AbstractJavadocCheck
{

    @Override
    public int[] getDefaultJavadocTokens()
    {
        return new int[] {
            JavadocTokenTypes.NEWLINE,
            JavadocTokenTypes.HTML_ELEMENT,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode aAst)
    {
        if (aAst.getType() == JavadocTokenTypes.NEWLINE && isEmptyLine(aAst)) {
            checkEmptyLine(aAst);
        }
        else if (aAst.getType() == JavadocTokenTypes.HTML_ELEMENT
                && JavadocUtils.getFirstChild(aAst).getType() == JavadocTokenTypes.P_TAG_OPEN)
        {
            checkParagraphTag(aAst);
        }
    }

    /**
     * Some javadoc.
     * @param aNewline Some javadoc.
     */
    private void checkEmptyLine(DetailNode aNewline)
    {
        final DetailNode nearestToken = getNearestNode(aNewline);
        if (!isLastEmptyLine(aNewline) && nearestToken != null
                && nearestToken.getType() == JavadocTokenTypes.TEXT
                && nearestToken.getChildren().length > 1)
        {
            log(aNewline.getLineNumber(), "javadoc.paragraph.tag.after");
        }
    }

    /**
     * Some javadoc.
     * @param aTag Some javadoc.
     */
    private void checkParagraphTag(DetailNode aTag)
    {
        final DetailNode newLine = getNearestEmptyLine(aTag);
        if (isFirstParagraph(aTag)) {
            log(aTag.getLineNumber(), "javadoc.paragraph.redundant.paragraph");
        }
        else if (newLine == null || aTag.getLineNumber() - newLine.getLineNumber() != 1) {
            log(aTag.getLineNumber(), "javadoc.paragraph.line.before");
        }
    }

    /**
     * Some javadoc.
     * @param aNode Some javadoc.
     * @return Some javadoc.
     */
    private DetailNode getNearestNode(DetailNode aNode)
    {
        DetailNode tag = JavadocUtils.getNextSibling(aNode);
        while (tag != null && (tag.getType() == JavadocTokenTypes.LEADING_ASTERISK
                || tag.getType() == JavadocTokenTypes.NEWLINE))
        {
            tag = JavadocUtils.getNextSibling(tag);
        }
        return tag;
    }

    /**
     * Some javadoc.
     * @param aNewLine Some javadoc.
     * @return Some javadoc.
     */
    private boolean isEmptyLine(DetailNode aNewLine)
    {
        DetailNode previousSibling = JavadocUtils.getPreviousSibling(aNewLine);
        if (previousSibling == null
                || previousSibling.getParent().getType() != JavadocTokenTypes.JAVADOC)
        {
            return false;
        }
        if (previousSibling.getType() == JavadocTokenTypes.TEXT
                && previousSibling.getChildren().length == 1)
        {
            previousSibling = JavadocUtils.getPreviousSibling(previousSibling);
        }
        return previousSibling != null
                && previousSibling.getType() == JavadocTokenTypes.LEADING_ASTERISK;
    }

    /**
     * Some javadoc.
     * @param aParagraphTag Some javadoc.
     * @return Some javadoc.
     */
    private boolean isFirstParagraph(DetailNode aParagraphTag)
    {
        DetailNode previousNode = JavadocUtils.getPreviousSibling(aParagraphTag);
        while (previousNode != null) {
            if (previousNode.getType() == JavadocTokenTypes.TEXT
                    && previousNode.getChildren().length > 1
                || previousNode.getType() != JavadocTokenTypes.LEADING_ASTERISK
                    && previousNode.getType() != JavadocTokenTypes.NEWLINE
                    && previousNode.getType() != JavadocTokenTypes.TEXT)
            {
                return false;
            }
            previousNode = JavadocUtils.getPreviousSibling(previousNode);
        }
        return true;
    }

    /**
     * Some javadoc.
     * @param aNode Some javadoc.
     * @return Some javadoc.
     */
    private DetailNode getNearestEmptyLine(DetailNode aNode)
    {
        DetailNode newLine = JavadocUtils.getPreviousSibling(aNode);
        while (newLine != null) {
            final DetailNode previousSibling = JavadocUtils.getPreviousSibling(newLine);
            if (newLine.getType() == JavadocTokenTypes.NEWLINE && isEmptyLine(newLine))
            {
                break;
            }
            newLine = previousSibling;
        }
        return newLine;
    }

    /**
     * Some javadoc.
     * @param aNewLine Some javadoc.
     * @return Some javadoc.
     */
    private boolean isLastEmptyLine(DetailNode aNewLine)
    {
        DetailNode nextNode = JavadocUtils.getNextSibling(aNewLine);
        while (nextNode != null && nextNode.getType() != JavadocTokenTypes.JAVADOC_TAG) {
            if (nextNode.getType() == JavadocTokenTypes.TEXT
                    && nextNode.getChildren().length > 1
                    || nextNode.getType() == JavadocTokenTypes.HTML_ELEMENT)
            {
                return false;
            }
            nextNode = JavadocUtils.getNextSibling(nextNode);
        }
        return true;
    }
}
