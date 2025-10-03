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

/**
 * <div>
 * Checks that there is at least one whitespace after the leading asterisk.
 * Although spaces after asterisks are optional in the Javadoc comments, their absence
 * makes the documentation difficult to read. It is the de facto standard to put at least
 * one whitespace after the leading asterisk.
 * </div>
 *
 * @since 8.32
 */
@StatelessCheck
public class JavadocMissingWhitespaceAfterAsteriskCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "javadoc.missing.whitespace";

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.JAVADOC_CONTENT,
            JavadocCommentsTokenTypes.LEADING_ASTERISK,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode detailNode) {
        final DetailNode nextNode = resolveNextNode(detailNode);

        if (nextNode != null) {
            final String text = nextNode.getText();
            final int lastAsteriskPosition = getLastLeadingAsteriskPosition(text);

            if (!isLast(lastAsteriskPosition, text)
                    && !Character.isWhitespace(text.charAt(lastAsteriskPosition + 1))) {
                log(nextNode.getLineNumber(), nextNode.getColumnNumber(), MSG_KEY);
            }
        }
    }

    /**
     * Resolves the first child node related to the given Javadoc {@link DetailNode}.
     *
     * <p>
     * The resolution works in two steps:
     * <ul>
     *   <li>If the current node is of type {@code JAVADOC_CONTENT}, use its first child;
     *       otherwise use its next sibling.</li>
     *   <li>If that base node has a first child, return it regardless of its type.</li>
     * </ul>
     *
     * <p>
     * The returned node may or may not be of type {@code TEXT}. If it is not,
     * the violation logic will treat it as a violation later.
     *
     * @param detailNode the Javadoc node to resolve from
     * @return the first child node if available; otherwise {@code null}
     */
    private static DetailNode resolveNextNode(DetailNode detailNode) {
        final DetailNode baseNode;
        if (detailNode.getType() == JavadocCommentsTokenTypes.JAVADOC_CONTENT) {
            baseNode = detailNode.getFirstChild();
        }
        else {
            baseNode = detailNode.getNextSibling();
        }

        DetailNode nextNode = baseNode;
        if (baseNode != null && baseNode.getFirstChild() != null) {
            nextNode = baseNode.getFirstChild();
        }

        return nextNode;
    }

    /**
     * Checks if the character position is the last one of the string.
     *
     * @param position the position of the character
     * @param text String literal.
     * @return true if the character position is the last one of the string.
     *
     */
    private static boolean isLast(int position, String text) {
        return position == text.length() - 1;
    }

    /**
     * Finds the position of the last leading asterisk in the string.
     * If {@code text} contains no leading asterisk, -1 will be returned.
     *
     * @param text String literal.
     * @return the index of the last leading asterisk.
     *
     */
    private static int getLastLeadingAsteriskPosition(String text) {
        int index = -1;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != '*') {
                break;
            }
            index++;
        }

        return index;
    }

}
