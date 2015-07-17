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
public class JavadocTagContinuationIndentationCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "tag.continuation.indent";

    /** Default tag continuation indentation */
    private static final int DEFAULT_INDENTATION = 4;

    /**
     * How many spaces to use for new indentation level.
     */
    private int offset = DEFAULT_INDENTATION;

    /**
     * Sets custom indentation level.
     * @param offset custom value.
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {JavadocTokenTypes.DESCRIPTION };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (isInlineDescription(ast)) {
            return;
        }
        final List<DetailNode> textNodes = getAllNewlineNodes(ast);
        for (DetailNode newlineNode : textNodes) {
            final DetailNode textNode = JavadocUtils.getNextSibling(JavadocUtils
                    .getNextSibling(newlineNode));
            if (textNode != null && textNode.getType() == JavadocTokenTypes.TEXT
                    && textNode.getChildren().length > 1) {
                final DetailNode whitespace = JavadocUtils.getFirstChild(textNode);
                if (whitespace.getText().length() - 1 < offset) {
                    log(textNode.getLineNumber(), MSG_KEY, offset);
                }
            }
        }
    }

    /**
     * Finds and collects all NEWLINE nodes inside DESCRIPTION node.
     * @param descriptionNode DESCRIPTION node.
     * @return List with NEWLINE nodes.
     */
    private static List<DetailNode> getAllNewlineNodes(DetailNode descriptionNode) {
        final List<DetailNode> textNodes = new ArrayList<>();
        DetailNode node = JavadocUtils.getFirstChild(descriptionNode);
        while (JavadocUtils.getNextSibling(node) != null) {
            if (node.getType() == JavadocTokenTypes.NEWLINE) {
                textNodes.add(node);
            }
            node = JavadocUtils.getNextSibling(node);
        }
        return textNodes;
    }

    /**
     * Checks, if description node is a description of in-line tag.
     * @param description DESCRIPTION node.
     * @return true, if description node is a description of in-line tag.
     */
    private static boolean isInlineDescription(DetailNode description) {
        DetailNode inlineTag = description.getParent();
        while (inlineTag != null) {
            if (inlineTag.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                return true;
            }
            inlineTag = inlineTag.getParent();
        }
        return false;
    }
}
