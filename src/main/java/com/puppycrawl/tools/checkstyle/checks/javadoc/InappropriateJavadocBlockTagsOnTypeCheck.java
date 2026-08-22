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
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that Javadoc block tags for type definitions (class, interface, enum, record,
 * annotation) do not contain tags that are semantically invalid for types.
 * Specifically, {@code @return} and {@code @throws}/{@code @exception} tags are meaningless
 * on type declarations since types neither return values nor throw exceptions.
 * </div>
 *
 * <p>
 * Type declarations (classes, interfaces, enums, records, annotations) do not have
 * return types or throw declarations. Therefore, {@code @return} and
 * {@code @throws}/{@code @exception} Javadoc block tags used in their Javadoc comments
 * are considered inappropriate and should be removed or replaced with proper documentation.
 * </p>
 *
 * @since 14.1.0
 */
@FileStatefulCheck
public class InappropriateJavadocBlockTagsOnTypeCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_INAPPROPRIATE_TAG = "javadoc.inappropriate.tag";

    /** Java AST node whose attached Javadoc is currently being processed. */
    private DetailAST currentAst;

    /**
     * Creates a new {@code InappropriateJavadocBlockTagsOnTypeCheck} instance.
     */
    public InappropriateJavadocBlockTagsOnTypeCheck() {
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
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ANNOTATION_DEF,
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
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final boolean canHaveParams = TokenUtil.isOfType(currentAst,
                TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.RECORD_DEF);

        final boolean validTag = canHaveParams
                && ast.getType() == JavadocCommentsTokenTypes.PARAM_BLOCK_TAG;

        if (!validTag) {
            final String tagName;
            switch (ast.getType()) {
                case JavadocCommentsTokenTypes.PARAM_BLOCK_TAG -> tagName = "param";
                case JavadocCommentsTokenTypes.RETURN_BLOCK_TAG -> tagName = "return";
                case JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG -> tagName = "exception";
                case JavadocCommentsTokenTypes.THROWS_BLOCK_TAG -> tagName = "throws";
                default -> throw new IllegalArgumentException("Unknown javadoc token type " + ast);
            }

            final String typeName = currentAst.findFirstToken(TokenTypes.IDENT).getText();
            log(currentAst, MSG_INAPPROPRIATE_TAG, tagName, typeName);
        }
    }

}
