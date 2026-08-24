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
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks that Javadoc block tags for fields do not contain tags that are semantically
 * invalid for fields.
 * Specifically, {@code @return}, {@code @param}, {@code @throws}, and {@code @exception}
 * tags are meaningless on field declarations.
 * </div>
 *
 * <p>
 * Fields do not return values, take parameters, or throw exceptions. Therefore,
 * {@code @return}, {@code @param}, {@code @throws}, and {@code @exception}
 * tags are inappropriate in their Javadocs.
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

    /** Name of the field currently being checked. */
    @Nullable
    private String currentFieldName;

    /**
     * Creates a new {@code InappropriateJavadocBlockTagsOnFieldCheck} instance.
     */
    public InappropriateJavadocBlockTagsOnFieldCheck() {
        // no code by default
    }

    @Override
    public final int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
        };
    }

    @Override
    public final int[] getAcceptableJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
        };
    }

    @Override
    public final int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public final int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public final int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public final void visitToken(DetailAST ast) {
        if (!ScopeUtil.isClassFieldDef(ast)) {
            return;
        }
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        if (ident == null) {
            return;
        }
        final DetailAST blockCommentNode = JavadocUtil.getAttachedJavadocComment(ast);
        if (blockCommentNode != null) {
            currentFieldName = ident.getText();
            super.visitToken(blockCommentNode);
        }
    }

    @Override
    public final void visitJavadocToken(DetailNode ast) {
        final String tagName;
        switch (ast.getType()) {
            case JavadocCommentsTokenTypes.PARAM_BLOCK_TAG:
                tagName = "param";
                break;
            case JavadocCommentsTokenTypes.RETURN_BLOCK_TAG:
                tagName = "return";
                break;
            case JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG:
                tagName = "exception";
                break;
            case JavadocCommentsTokenTypes.THROWS_BLOCK_TAG:
                tagName = "throws";
                break;
            default:
                throw new IllegalArgumentException("Unknown javadoc token type " + ast);
        }

        log(ast, MSG_KEY, tagName, currentFieldName);
    }

}
