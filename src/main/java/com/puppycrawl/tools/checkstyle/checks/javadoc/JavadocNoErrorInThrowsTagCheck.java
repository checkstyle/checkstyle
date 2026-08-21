///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that {@code Error} types are not documented in {@code @throws} or
 * {@code @exception} Javadoc tags.
 * </div>
 *
 * <p>
 * Per the documentation comments style guide, errors should not be documented
 * because they are unpredictable. This check reports {@code Error},
 * {@code java.lang.Error}, and documented throwable names whose simple name ends
 * with {@code Error}.
 * </p>
 *
 * @since 14.1.0
 */
@StatelessCheck
public class JavadocNoErrorInThrowsTagCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "javadoc.no.error.in.throws.tag";

    /**
     * Creates a new {@code JavadocNoErrorInThrowsTagCheck} instance.
     */
    public JavadocNoErrorInThrowsTagCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final DetailNode identifier = JavadocUtil.findFirstToken(
                ast, JavadocCommentsTokenTypes.IDENTIFIER);

        if (identifier != null && isErrorType(identifier.getText())) {
            final String tagName = getTagName(ast);
            log(ast, MSG_KEY, identifier.getText(), tagName);
        }
    }

    /**
     * Checks whether class name is an Error type name.
     *
     * @param className class name
     * @return true if class name is an Error type name
     */
    private static boolean isErrorType(String className) {
        return className.endsWith("Error");
    }

    /**
     * Gets the Javadoc tag name from a throws or exception block tag.
     *
     * @param ast throws or exception block tag
     * @return tag name with leading at sign
     */
    private static String getTagName(DetailNode ast) {
        return "@" + JavadocUtil.findFirstToken(ast, JavadocCommentsTokenTypes.TAG_NAME).getText();
    }

}
