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
import com.puppycrawl.tools.checkstyle.utils.NullUtil;

/**
 * <div>
 * Checks that Javadoc block tags for field declarations do not contain tags that
 * are semantically invalid for fields. Specifically, {@code @author}, {@code @version},
 * {@code @param}, {@code @return}, {@code @throws}/{@code @exception}, {@code @uses},
 * and {@code @provides} tags are meaningless on field declarations.
 * </div>
 *
 * <p>
 * Field declarations do not have parameters, return types, or throw declarations,
 * and module/type-level tags like {@code @author}, {@code @version}, {@code @uses},
 * and {@code @provides} are inappropriate on field declarations. Therefore, such
 * Javadoc block tags are considered inappropriate and should be removed.
 * </p>
 *
 * @since 14.1.0
 */
@FileStatefulCheck
public class InappropriateJavadocBlockTagsOnFieldCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "javadoc.inappropriate.tag";

    /** Java AST node whose attached Javadoc is currently being processed. */
    private DetailAST currentAst;

    /**
     * Creates a new {@code InappropriateJavadocBlockTagsOnFieldCheck} instance.
     */
    public InappropriateJavadocBlockTagsOnFieldCheck() {
        // no code by default
    }

    /**
     * Setter to control when to print violations if the Javadoc being examined by this check
     * violates the tight html rules defined at
     * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
     *     Tight-HTML Rules</a>.
     *
     * @param shouldReportViolation value to which the field shall be set to
     * @since 14.1.0
     * @propertySince 14.1.0
     */
    @Override
    public void setViolateExecutionOnNonTightHtml(boolean shouldReportViolation) {
        super.setViolateExecutionOnNonTightHtml(shouldReportViolation);
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
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.AUTHOR_BLOCK_TAG,
            JavadocCommentsTokenTypes.VERSION_BLOCK_TAG,
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
            JavadocCommentsTokenTypes.USES_BLOCK_TAG,
            JavadocCommentsTokenTypes.PROVIDES_BLOCK_TAG,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST blockCommentNode = JavadocUtil.getAttachedJavadocComment(ast);
        if (blockCommentNode != null) {
            currentAst = ast;
            super.visitToken(blockCommentNode);
        }
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final String tagName = getTagName(ast);
        final DetailAST ident = NullUtil.notNull(currentAst.findFirstToken(TokenTypes.IDENT));
        final String fieldName = ident.getText();
        log(currentAst, MSG_KEY, tagName, fieldName);
    }

    /**
     * Returns the tag name corresponding to the given Javadoc token type.
     *
     * @param ast the Javadoc token
     * @return the tag name as a string
     * @throws IllegalArgumentException if the token type is not recognized as a Javadoc
     *                                  block tag that is inappropriate for field declarations.
     */
    private static String getTagName(DetailNode ast) {
        return switch (ast.getType()) {
            case JavadocCommentsTokenTypes.AUTHOR_BLOCK_TAG -> "author";
            case JavadocCommentsTokenTypes.VERSION_BLOCK_TAG -> "version";
            case JavadocCommentsTokenTypes.PARAM_BLOCK_TAG -> "param";
            case JavadocCommentsTokenTypes.RETURN_BLOCK_TAG -> "return";
            case JavadocCommentsTokenTypes.THROWS_BLOCK_TAG -> "throws";
            case JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG -> "exception";
            case JavadocCommentsTokenTypes.USES_BLOCK_TAG -> "uses";
            case JavadocCommentsTokenTypes.PROVIDES_BLOCK_TAG -> "provides";
            default -> throw new IllegalArgumentException("Unknown javadoc token type " + ast);
        };
    }

}
