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

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that Javadoc block tags for package definitions do not contain tags
 * that are semantically invalid for packages.
 * </div>
 *
 * <p>
 * Package definitions do not have return types or throw declarations. Therefore,
 * {@code @return} and {@code @throws}/{@code @exception} Javadoc block tags used
 * in their Javadoc comments are considered inappropriate and should be removed
 * or replaced with proper documentation.
 * </p>
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
 * {@code javadoc.inappropriate.tag}
 * </li>
 * </ul>
 *
 * @since 14.1.0
 */
@FileStatefulCheck
public class InappropriateJavadocBlockTagsOnPackageCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_INAPPROPRIATE_TAG = "javadoc.inappropriate.tag";

    /**
     * The package keyword for logging.
     */
    public static final String PACKAGE = "package";

    /** Java AST node whose attached Javadoc is currently being processed. */
    private DetailAST currentAst;

    /**
     * Creates a new {@code InappropriateJavadocBlockTagsOnPackageCheck} instance.
     */
    public InappropriateJavadocBlockTagsOnPackageCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST blockCommentNode = JavadocUtil.getAttachedJavadocCommentForPackage(ast);

        if (blockCommentNode != null) {
            currentAst = ast;
            super.visitToken(blockCommentNode);
        }
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
            JavadocCommentsTokenTypes.USES_BLOCK_TAG,
            JavadocCommentsTokenTypes.PROVIDES_BLOCK_TAG,
            JavadocCommentsTokenTypes.SERIAL_BLOCK_TAG,
            JavadocCommentsTokenTypes.SERIAL_DATA_BLOCK_TAG,
            JavadocCommentsTokenTypes.SERIAL_FIELD_BLOCK_TAG,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final String tagName = getTagName(ast);
        log(currentAst, MSG_INAPPROPRIATE_TAG, tagName, PACKAGE);
    }

    /**
     * Returns the tag name corresponding to the given Javadoc token type.
     *
     * @param ast the Javadoc token
     * @return the tag name as a string
     * @throws IllegalArgumentException if the token type is not recognized as a Javadoc
     *                                  block tag that is inappropriate for package definitions.
     */
    private static String getTagName(DetailNode ast) {
        return switch (ast.getType()) {
            case JavadocCommentsTokenTypes.PARAM_BLOCK_TAG -> "param";
            case JavadocCommentsTokenTypes.RETURN_BLOCK_TAG -> "return";
            case JavadocCommentsTokenTypes.THROWS_BLOCK_TAG -> "throws";
            case JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG -> "exception";
            case JavadocCommentsTokenTypes.USES_BLOCK_TAG -> "uses";
            case JavadocCommentsTokenTypes.PROVIDES_BLOCK_TAG -> "provides";
            case JavadocCommentsTokenTypes.SERIAL_BLOCK_TAG -> "serial";
            case JavadocCommentsTokenTypes.SERIAL_DATA_BLOCK_TAG -> "serialData";
            case JavadocCommentsTokenTypes.SERIAL_FIELD_BLOCK_TAG -> "serialField";
            default -> throw new IllegalArgumentException("Unknown token: " + ast);
        };
    }

}
