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

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that multiple {@code @throws} and {@code @exception} Javadoc tags are listed
 * alphabetically by exception name.
 * </div>
 *
 * @since 13.11.0
 */
@FileStatefulCheck
public class JavadocThrowsOrderCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "javadoc.throws.order";

    /** The greatest exception name found so far in the current Javadoc tree. */
    @Nullable
    private String previousExceptionName;

    /**
     * Creates a new {@code JavadocThrowsOrderCheck} instance.
     */
    public JavadocThrowsOrderCheck() {
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

        if (identifier != null) {
            final String exceptionName = identifier.getText();
            final String previousName = previousExceptionName;
            if (previousName != null && exceptionName.compareTo(previousName) < 0) {
                final String tagName = getTagName(ast);
                log(ast, MSG_KEY, tagName, exceptionName, previousName);
            }
            else {
                previousExceptionName = exceptionName;
            }
        }
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        previousExceptionName = null;
    }

    /**
     * Gets the tag name from a throws or exception block tag.
     *
     * @param ast throws or exception block tag
     * @return tag name
     */
    private static String getTagName(DetailNode ast) {
        return JavadocUtil.findFirstToken(ast, JavadocCommentsTokenTypes.TAG_NAME).getText();
    }

}
