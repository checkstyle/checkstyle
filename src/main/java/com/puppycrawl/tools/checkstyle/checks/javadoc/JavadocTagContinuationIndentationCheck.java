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

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks the indentation of the continuation lines in at-clauses.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code offset} - Specify how many spaces to use for new indentation level.
 * Default value is {@code 4}.
 * </li>
 * </ul>
 * <p>
 * To configure the default check:
 * </p>
 * <pre>
 * &lt;module name="JavadocTagContinuationIndentation"/&gt;
 * </pre>
 * <p>
 * To configure the check with two spaces indentation:
 * </p>
 * <pre>
 * &lt;module name="JavadocTagContinuationIndentation"&gt;
 *   &lt;property name="offset" value="2"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @since 6.0
 *
 */
@StatelessCheck
public class JavadocTagContinuationIndentationCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "tag.continuation.indent";

    /** Default tag continuation indentation. */
    private static final int DEFAULT_INDENTATION = 4;

    /**
     * Specify how many spaces to use for new indentation level.
     */
    private int offset = DEFAULT_INDENTATION;

    /**
     * Setter to specify how many spaces to use for new indentation level.
     *
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
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (!isInlineDescription(ast)) {
            final List<DetailNode> textNodes = getAllNewlineNodes(ast);
            for (DetailNode newlineNode : textNodes) {
                final DetailNode textNode = JavadocUtil.getNextSibling(JavadocUtil
                        .getNextSibling(newlineNode));
                if (textNode != null && textNode.getType() == JavadocTokenTypes.TEXT) {
                    final String text = textNode.getText();
                    if (!CommonUtil.isBlank(text.trim())
                            && (text.length() <= offset
                                    || !text.substring(1, offset + 1).trim().isEmpty())) {
                        log(textNode.getLineNumber(), MSG_KEY, offset);
                    }
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
        DetailNode node = JavadocUtil.getFirstChild(descriptionNode);
        while (JavadocUtil.getNextSibling(node) != null) {
            if (node.getType() == JavadocTokenTypes.NEWLINE) {
                textNodes.add(node);
            }
            node = JavadocUtil.getNextSibling(node);
        }
        return textNodes;
    }

    /**
     * Checks, if description node is a description of in-line tag.
     * @param description DESCRIPTION node.
     * @return true, if description node is a description of in-line tag.
     */
    private static boolean isInlineDescription(DetailNode description) {
        boolean isInline = false;
        DetailNode inlineTag = description.getParent();
        while (inlineTag != null) {
            if (inlineTag.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                isInline = true;
                break;
            }
            inlineTag = inlineTag.getParent();
        }
        return isInline;
    }

}
