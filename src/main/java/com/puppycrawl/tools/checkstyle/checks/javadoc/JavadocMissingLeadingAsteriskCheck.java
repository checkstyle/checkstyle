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

import org.apache.commons.lang3.StringUtils;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

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
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations if the
 * Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.missing.asterisk}
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
            JavadocTokenTypes.NEWLINE,
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
        DetailNode nextSibling = getNextNode(detailNode);

        // Till https://github.com/checkstyle/checkstyle/issues/9005
        // Due to bug in the Javadoc parser there may be phantom description nodes.
        while (TokenUtil.isOfType(nextSibling.getType(),
                JavadocTokenTypes.DESCRIPTION, JavadocTokenTypes.WS)) {
            nextSibling = getNextNode(nextSibling);
        }

        if (!isLeadingAsterisk(nextSibling) && !isLastLine(nextSibling)) {
            log(nextSibling.getLineNumber(), MSG_MISSING_ASTERISK);
        }
    }

    /**
     * Gets next node in the ast (sibling or parent sibling for the last node).
     *
     * @param detailNode the node to process
     * @return next node.
     */
    private static DetailNode getNextNode(DetailNode detailNode) {
        DetailNode node = JavadocUtil.getFirstChild(detailNode);
        if (node == null) {
            node = JavadocUtil.getNextSibling(detailNode);
            if (node == null) {
                DetailNode parent = detailNode;
                do {
                    parent = parent.getParent();
                    node = JavadocUtil.getNextSibling(parent);
                } while (node == null);
            }
        }
        return node;
    }

    /**
     * Checks whether the given node is a leading asterisk.
     *
     * @param detailNode the node to process
     * @return {@code true} if the node is {@link JavadocTokenTypes#LEADING_ASTERISK}
     */
    private static boolean isLeadingAsterisk(DetailNode detailNode) {
        return detailNode.getType() == JavadocTokenTypes.LEADING_ASTERISK;
    }

    /**
     * Checks whether this node is the end of a Javadoc comment,
     * optionally preceded by blank text.
     *
     * @param detailNode the node to process
     * @return {@code true} if the node is {@link JavadocTokenTypes#EOF}
     */
    private static boolean isLastLine(DetailNode detailNode) {
        final DetailNode node;
        if (StringUtils.isBlank(detailNode.getText())) {
            node = getNextNode(detailNode);
        }
        else {
            node = detailNode;
        }
        return node.getType() == JavadocTokenTypes.EOF;
    }

}
