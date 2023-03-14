///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks that there is at least one whitespace after the leading asterisk.
 * Although spaces after asterisks are optional in the Javadoc comments, their absence
 * makes the documentation difficult to read. It is the de facto standard to put at least
 * one whitespace after the leading asterisk.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the default check:
 * </p>
 * <pre>
 * &lt;module name="JavadocMissingWhitespaceAfterAsterisk"/&gt;
 * </pre>
 * <p>
 * Code Example:
 * </p>
 * <pre>
 * &#47;** This is valid single-line Javadoc. *&#47;
 * class TestClass {
 *   &#47;**
 *     *This is invalid Javadoc.
 *     *&#47;
 *   int invalidJavaDoc;
 *   &#47;**
 *     * This is valid Javadoc.
 *     *&#47;
 *   void validJavaDocMethod() {
 *   }
 *   &#47;**This is invalid single-line Javadoc. *&#47;
 *   void invalidSingleLineJavaDocMethod() {
 *   }
 *   &#47;** This is valid single-line Javadoc. *&#47;
 *   void validSingleLineJavaDocMethod() {
 *   }
 * }
 * </pre>
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
 * {@code javadoc.missing.whitespace}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * </ul>
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
            JavadocTokenTypes.JAVADOC,
            JavadocTokenTypes.LEADING_ASTERISK,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode detailNode) {
        final DetailNode textNode;

        if (detailNode.getType() == JavadocTokenTypes.JAVADOC) {
            textNode = JavadocUtil.getFirstChild(detailNode);
        }
        else {
            textNode = JavadocUtil.getNextSibling(detailNode);
        }

        if (textNode != null && textNode.getType() != JavadocTokenTypes.EOF) {
            final String text = textNode.getText();
            final int lastAsteriskPosition = getLastLeadingAsteriskPosition(text);

            if (!isLast(lastAsteriskPosition, text)
                    && !Character.isWhitespace(text.charAt(lastAsteriskPosition + 1))) {
                log(textNode.getLineNumber(), textNode.getColumnNumber(), MSG_KEY);
            }
        }
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
