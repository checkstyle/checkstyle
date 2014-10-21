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

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

/**
 * <p>
 * Checks the indentation of the continuation lines in at-clauses.
 * </p>
 * <p>
 * Default configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;JavadocTagContinuationIndentation&quot;&gt;
 *     &lt;property name=&quot;offset&quot; value=&quot;4&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author max
 *
 */
public class JavadocTagContinuationIndentationCheck extends AbstractJavadocCheck
{

    /** Default indentation */
    private static final int DEFAULT_INDENTATION = 4;

    /**
     * How many spaces to use for new indentation level.
     */
    private int mOffset = DEFAULT_INDENTATION;

    /**
     * Some javadoc.
     * @param aOffset Some javadoc.
     */
    public void setOffset(int aOffset)
    {
        mOffset = aOffset;
    }

    @Override
    public int[] getDefaultJavadocTokens()
    {
        return new int[] {JavadocTokenTypes.DESCRIPTION };
    }

    @Override
    public void visitJavadocToken(DetailNode aAst)
    {
        final List<DetailNode> textNodes = getAllNewlineNodes(aAst);
        if (isInlineDescription(aAst)) {
            return;
        }
        for (DetailNode newlineNode : textNodes) {
            final DetailNode textNode = JavadocUtils.getNextSibling(JavadocUtils
                    .getNextSibling(newlineNode));
            if (textNode != null && textNode.getType() == JavadocTokenTypes.TEXT
                    && textNode.getChildren().length > 1)
            {
                final DetailNode whitespace = JavadocUtils.getFirstChild(textNode);
                if (whitespace.getType() == JavadocTokenTypes.WS
                        && whitespace.getText().length() - 1 < mOffset)
                {
                    log(textNode.getLineNumber(), "tag.continuation.indent", mOffset);
                }
            }
        }
    }

    /**
     * Some javadoc.
     * @param aDescriptionNode Some javadoc.
     * @return Some javadoc.
     */
    private List<DetailNode> getAllNewlineNodes(DetailNode aDescriptionNode)
    {
        final List<DetailNode> textNodes = new ArrayList<DetailNode>();
        DetailNode node = JavadocUtils.getFirstChild(aDescriptionNode);
        while (JavadocUtils.getNextSibling(node) != null) {
            if (node.getType() == JavadocTokenTypes.NEWLINE) {
                textNodes.add(node);
            }
            node = JavadocUtils.getNextSibling(node);
        }
        return textNodes;
    }

    /**
     * Some javadoc.
     * @param aDescription Some javadoc.
     * @return Some javadoc.
     */
    private boolean isInlineDescription(DetailNode aDescription)
    {
        DetailNode inlineTag = aDescription.getParent();
        while (inlineTag != null) {
            if (inlineTag.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                return true;
            }
            inlineTag = inlineTag.getParent();
        }
        return false;
    }
}
