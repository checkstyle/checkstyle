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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks for missing Javadoc comments for class, enum, interface, and annotation interface
 * definitions. The scope to verify is specified using the {@code Scope} class and defaults
 * to {@code Scope.PUBLIC}. To verify another scope, set property scope to one of the
 * {@code Scope} constants.
 * </div>
 *
 * @since 8.20
 */
@FileStatefulCheck
public final class MissingJavadocTypeCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_MISSING = "javadoc.missing";

    /**
     * Stores all Javadoc comment nodes collected during the tree traversal.
     * Used to match a Javadoc comment to a type declaration.
     */
    private final List<DetailAST> javadocComments = new ArrayList<>();

    /** Specify the visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PUBLIC;

    /** Specify the visibility scope where Javadoc comments are not checked. */
    private Scope excludeScope;

    /**
     * Specify annotations that allow missed documentation.
     * If annotation is present in target sources in multiple forms of qualified
     * name, all forms should be listed in this property.
     */
    private Set<String> skipAnnotations = Set.of("Generated");

    /**
     * Creates a new {@code MissingJavadocTypeCheck} instance.
     */
    public MissingJavadocTypeCheck() {
        // no code by default
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are checked.
     *
     * @param scope a scope.
     * @since 8.20
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are not checked.
     *
     * @param excludeScope a scope.
     * @since 8.20
     */
    public void setExcludeScope(Scope excludeScope) {
        this.excludeScope = excludeScope;
    }

    /**
     * Setter to specify annotations that allow missed documentation.
     * If annotation is present in target sources in multiple forms of qualified
     * name, all forms should be listed in this property.
     *
     * @param userAnnotations user's value.
     * @since 8.20
     */
    public void setSkipAnnotations(String... userAnnotations) {
        skipAnnotations = Set.of(userAnnotations);
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitJavadocToken(DetailNode node) {
        // no-op
    }

    @Override
    public void beginTree(DetailAST node) {
        javadocComments.clear();
        collectCommentNodes(node);
    }

    /**
     * Collects all Javadoc comment nodes in the AST tree and stores them
     * in {@code javadocComments}. These comments are later used to determine
     * whether a type declaration has an associated Javadoc comment.
     *
     * @param ast the root AST node from which comment nodes are collected
     */
    private void collectCommentNodes(DetailAST ast) {
        DetailAST current = ast;
        while (current != null) {
            if (current.getType() == TokenTypes.BLOCK_COMMENT_BEGIN
                    && JavadocUtil.isJavadocComment(current)) {
                javadocComments.add(current);
            }
            if (current.getFirstChild() != null) {
                current = current.getFirstChild();
            }
            else {
                DetailAST parent = current;
                while (parent != null && current.getNextSibling() == null) {
                    current = parent;
                    parent = parent.getParent();
                }
                current = current.getNextSibling();
            }
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast) && !hasJavadoc(ast)) {
            log(ast, MSG_JAVADOC_MISSING);
        }
    }

    /**
     * Determines whether the specified type AST node has a valid Javadoc
     * comment immediately preceding it, with no intervening executable code.
     *
     * @param ast the AST node representing the type definition
     * @return {@code true} if a valid Javadoc comment exists before the type;
     *         {@code false} otherwise
     */
    private boolean hasJavadoc(DetailAST ast) {
        DetailAST best = null;

        for (DetailAST comment : javadocComments) {
            final int endLine = comment.getLineNo();
            if (endLine <= ast.getLineNo()) {
                best = comment;
            }
        }
        return best != null && noInterveningCode(best, ast);
    }

    /**
     * Checks whether there is any executable code between the Javadoc comment
     * and the type declaration by walking AST siblings between them.
     *
     * @param javadoc the AST node representing the Javadoc comment
     * @param type    the AST node representing the type declaration
     * @return {@code true} if no executable code exists between them;
     *         {@code false} otherwise
     */
    private static boolean noInterveningCode(DetailAST javadoc, DetailAST type) {
        DetailAST detailAST = javadoc;
        final int typeStartLine = type.getLineNo();
        boolean hasOnlyJavadoc = true;
        while (detailAST != null) {
            final int siblingLine = detailAST.getLineNo();

            if (siblingLine < typeStartLine) {
                final int tokenType = detailAST.getType();
                if (!isAllowedBetweenJavadocAndType(tokenType)) {
                    hasOnlyJavadoc = false;
                    break;
                }
            }
            detailAST = detailAST.getNextSibling();
        }
        return hasOnlyJavadoc;
    }

    /**
     * Returns whether the given token type is permitted to appear between
     * a Javadoc comment and a type declaration.
     *
     * @param tokenType the token type to check
     * @return {@code true} if the token is allowed between Javadoc and a type;
     *         {@code false} otherwise
     */
    private static boolean isAllowedBetweenJavadocAndType(int tokenType) {
        return tokenType == TokenTypes.BLOCK_COMMENT_BEGIN
                || tokenType == TokenTypes.SINGLE_LINE_COMMENT;

    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast) {
        return ScopeUtil.getSurroundingScope(ast)
            .map(surroundingScope -> {
                return surroundingScope.isIn(scope)
                    && (excludeScope == null || !surroundingScope.isIn(excludeScope))
                    && !AnnotationUtil.containsAnnotation(ast, skipAnnotations);
            })
            .orElse(Boolean.FALSE);
    }

}
