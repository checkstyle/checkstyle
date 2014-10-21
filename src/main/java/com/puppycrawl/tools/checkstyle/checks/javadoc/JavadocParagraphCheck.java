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
        final DetailNode previousSibling = JavadocUtils.getPreviousSibling(aAst);
        if (aAst.getType() == JavadocTokenTypes.NEWLINE && previousSibling != null
            && previousSibling.getType() == JavadocTokenTypes.LEADING_ASTERISK)
        {
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
        final DetailNode nearestToken = getNearestToken(aNewline);
        if (!isLastEmptyLine(aNewline)
            && (nearestToken == null || nearestToken.getType() != JavadocTokenTypes.JAVADOC_TAG
                && nearestToken.getLineNumber() - aNewline.getLineNumber() != 1))
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
        if (newLine == null || aTag.getLineNumber() - newLine.getLineNumber() != 1)
        {
            log(aTag.getLineNumber(), "javadoc.paragraph.line.before");
        }
    }

    /**
     * Some javadoc.
     * @param aNode Some javadoc.
     * @return Some javadoc.
     */
    private DetailNode getNearestToken(DetailNode aNode)
    {
        DetailNode tag = JavadocUtils.getNextSibling(aNode);
        while (tag != null) {
            if (tag.getType() == JavadocTokenTypes.HTML_ELEMENT
                && JavadocUtils.getFirstChild(tag).getType() == JavadocTokenTypes.P_TAG_OPEN)
            {
                break;
            }
            tag = JavadocUtils.getNextSibling(tag);
        }
        return tag;
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
            if (newLine.getType() == JavadocTokenTypes.NEWLINE && previousSibling != null
                && previousSibling.getType() == JavadocTokenTypes.LEADING_ASTERISK)
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
        boolean result = false;
        DetailNode nextSibling = JavadocUtils.getNextSibling(aNewLine);
        if (nextSibling.getType() == JavadocTokenTypes.TEXT
                && nextSibling.getChildren().length == 1)
        {
            result =  true;
        }
        else {
            nextSibling = JavadocUtils.getNextSibling(nextSibling);
            if (JavadocUtils.getNextSibling(nextSibling).getType()
                    == JavadocTokenTypes.JAVADOC_TAG)
            {
                result =  true;
            }
        }
        return result;
    }
}
