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
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks that Javadoc block tags for field declarations do not contain tags that
 * are semantically invalid for fields. Specifically, {@code @return}, {@code @param},
 * and {@code @throws}/{@code @exception} tags are meaningless on field declarations
 * since fields neither return values, take parameters, nor throw exceptions.
 * </div>
 *
 * <p>
 * Field declarations do not have return types, parameters, or throw declarations.
 * Therefore, {@code @return}, {@code @param}, and {@code @throws}/{@code @exception}
 * Javadoc block tags used in their Javadoc comments are considered inappropriate
 * and should be removed or replaced with proper documentation.
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
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ScopeUtil.isClassFieldDef(ast)) {
            final DetailAST blockCommentNode = JavadocUtil.getAttachedJavadocComment(ast);
            if (blockCommentNode != null) {
                currentAst = ast;
                super.visitToken(blockCommentNode);
            }
        }
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final String tagName = switch (ast.getType()) {
            case JavadocCommentsTokenTypes.PARAM_BLOCK_TAG -> "param";
            case JavadocCommentsTokenTypes.RETURN_BLOCK_TAG -> "return";
            case JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG -> "exception";
            case JavadocCommentsTokenTypes.THROWS_BLOCK_TAG -> "throws";
            default -> throw new IllegalArgumentException("Unknown javadoc token type " + ast);
        };

        final String fieldName = currentAst.findFirstToken(TokenTypes.IDENT).getText();
        log(currentAst, MSG_KEY, tagName, fieldName);
    }

}
