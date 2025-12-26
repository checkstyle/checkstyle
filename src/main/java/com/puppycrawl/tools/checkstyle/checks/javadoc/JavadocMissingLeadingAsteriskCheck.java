///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks if the javadoc has
 * <a href="https://docs.oracle.com/en/java/javase/14/docs/specs/javadoc/doc-comment-spec.html#leading-asterisks">
 * leading asterisks</a> on each line.
 * </div>
 *
 * <p>
 * The check does not require asterisks on the first line, nor on the last line if it is blank.
 * All other lines in a Javadoc should start with {@code *}, including blank lines and code blocks.
 * </p>
 *
 * @since 8.38
 */
@StatelessCheck
public class JavadocMissingLeadingAsteriskCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_ASTERISK = "javadoc.missing.asterisk";

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.NEWLINE,
        };
    }

    @Override
    public int[] getAcceptableJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode detailNode) {
        if (!isInsideHtmlComment(detailNode)) {
            final DetailNode nextSibling = detailNode.getNextSibling();

            if (nextSibling != null && !isLeadingAsterisk(nextSibling)
                        && !isLastLine(nextSibling)) {
                log(nextSibling.getLineNumber(), MSG_MISSING_ASTERISK);
            }
        }
    }

    /**
     * Checks whether the given node is inside an HTML comment.
     *
     * @param detailNode the node to process
     * @return {@code true} if the node is inside an HTML comment
     */
    private static boolean isInsideHtmlComment(DetailNode detailNode) {
        final var parentType = detailNode.getParent().getType();
        return parentType == JavadocCommentsTokenTypes.HTML_COMMENT_CONTENT
                || parentType == JavadocCommentsTokenTypes.HTML_COMMENT;

    }

    /**
     * Checks whether the given node is a leading asterisk.
     *
     * @param detailNode the node to process
     * @return {@code true} if the node is {@link JavadocCommentsTokenTypes#LEADING_ASTERISK}
     */
    private static boolean isLeadingAsterisk(DetailNode detailNode) {
        return detailNode.getType() == JavadocCommentsTokenTypes.LEADING_ASTERISK;
    }

    /**
     * Checks whether this node is the end of a Javadoc comment,
     * optionally preceded by blank text.
     *
     * @param detailNode the node to process
     * @return {@code true} if the node is {@code null}
     */
    private static boolean isLastLine(DetailNode detailNode) {
        final DetailNode node;
        if (CommonUtil.isBlank(detailNode.getText())) {
            node = detailNode.getNextSibling();
        }
        else {
            node = detailNode;
        }
        return node == null;
    }

}
