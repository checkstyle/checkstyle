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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that the order of {@code @param} tags in a Javadoc comment matches
 * the order of the corresponding parameters in the method or constructor
 * declaration. Type parameters (e.g. {@code <T>}) are included and must
 * appear before normal parameters, matching their position in the declaration.
 * </div>
 *
 * <p>
 * Only the intersection of documented parameter names and declared parameter
 * names is checked. No violation is raised for extra tags or missing tags.
 * </p>
 *
 * @since 13.3.0
 */
@StatelessCheck
public class JavadocParamOrderCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_PARAM_ORDER = "javadoc.param.order";

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.JAVADOC_CONTENT,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final DetailAST blockComment = getBlockCommentAst();
        final DetailAST parent = blockComment.getParent();
        final int parentType = getParentType(blockComment);

        if (parentType == TokenTypes.METHOD_DEF || parentType == TokenTypes.CTOR_DEF) {
            final DetailAST declaration;
            // The block comment is always a child of TYPE, MODIFIERS, or TYPE_PARAMETERS,
            // all of which are direct children of METHOD_DEF / CTOR_DEF.
            // For a non-generic constructor, the comment may be a direct child of CTOR_DEF.
            if (parent.getType() == TokenTypes.TYPE
                    || parent.getType() == TokenTypes.MODIFIERS
                    || parent.getType() == TokenTypes.TYPE_PARAMETERS) {
                declaration = parent.getParent();
            }
            else {
                declaration = parent;
            }
            checkParamOrder(ast, declaration);
        }
    }

    /**
     * Checks that {@code @param} tags appear in the same relative order
     * as the corresponding parameters in the declaration.
     *
     * @param javadocContent the {@code JAVADOC_CONTENT} node
     * @param declaration    the {@code METHOD_DEF} or {@code CTOR_DEF} node
     */
    private void checkParamOrder(DetailNode javadocContent, DetailAST declaration) {
        final List<String> methodParamNames = getMethodParamNames(declaration);
        final Map<String, DetailNode> javadocParamNodes =
                getParamTagNodes(javadocContent, methodParamNames);
        final List<String> javadocParamOrder = new ArrayList<>(javadocParamNodes.keySet());

        int  javadocParamIndex = 0;
        for (final String paramName : methodParamNames) {
            if (!javadocParamNodes.containsKey(paramName)) {
                continue;
            }

            final String javadocParamName = javadocParamOrder.get(javadocParamIndex);
            if (!paramName.equals(javadocParamName)) {
                log(javadocParamNodes.get(javadocParamName).getLineNumber(),
                        MSG_JAVADOC_PARAM_ORDER);
            }
            javadocParamIndex++;
        }
    }

    /**
     * Returns matched {@code @param} tag nodes in Javadoc order,
     * filtered to only names present in {@code methodParamNames}.
     * Duplicate tags for the same name keep only the first occurrence.
     *
     * @param javadocContentNode the {@code JAVADOC_CONTENT} node
     * @param methodParamNames   declared parameter names
     * @return insertion-ordered map of param name to its tag node
     */
    private static Map<String, DetailNode> getParamTagNodes(DetailNode javadocContentNode,
                                                            List<String> methodParamNames) {
        final Map<String, DetailNode> paramNodes = new LinkedHashMap<>();
        DetailNode blockTag = javadocContentNode.getFirstChild();

        while (blockTag != null) {
            if (blockTag.getType() == JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG) {
                final DetailNode child = blockTag.getFirstChild();
                if (child.getType() == JavadocCommentsTokenTypes.PARAM_BLOCK_TAG) {
                    final String name = getParamName(child);
                    if (methodParamNames.contains(name)) {
                        paramNodes.putIfAbsent(name, child);
                    }
                }
            }
            blockTag = blockTag.getNextSibling();
        }
        return paramNodes;
    }

    /**
     * Returns declared parameter names in source order: type parameters first,
     * then normal parameters.
     *
     * @param methodDef the {@code METHOD_DEF} or {@code CTOR_DEF} node
     * @return ordered list of parameter names
     */
    private static List<String> getMethodParamNames(DetailAST methodDef) {
        final List<String> paramNames = new ArrayList<>();

        // Type parameters first: TYPE_PARAMETERS -> TYPE_PARAMETER -> IDENT
        final DetailAST typeParams = methodDef.findFirstToken(TokenTypes.TYPE_PARAMETERS);
        if (typeParams != null) {
            DetailAST typeParam = typeParams.getFirstChild();
            while (typeParam != null) {
                if (typeParam.getType() == TokenTypes.TYPE_PARAMETER) {
                    paramNames.add(typeParam.findFirstToken(TokenTypes.IDENT).getText());
                }
                typeParam = typeParam.getNextSibling();
            }
        }

        // Normal parameters: PARAMETERS -> PARAMETER_DEF -> IDENT
        final DetailAST params = methodDef.findFirstToken(TokenTypes.PARAMETERS);
        DetailAST paramDef = params.getFirstChild();
        while (paramDef != null) {
            if (paramDef.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST ident = paramDef.findFirstToken(TokenTypes.IDENT);
                paramNames.add(ident.getText());
            }
            paramDef = paramDef.getNextSibling();
        }

        return paramNames;
    }

    /**
     * Returns the parameter name from a {@code PARAM_BLOCK_TAG} node.
     * Angle brackets are stripped from type parameter names (e.g. {@code <T>} to {@code T}).
     *
     * @param paramBlockTag a {@code PARAM_BLOCK_TAG} node
     * @return the bare parameter name, or {@code null} if not found
     */
    private static String getParamName(DetailNode paramBlockTag) {
        DetailNode child = paramBlockTag.getFirstChild();
        String name = null;
        while (child != null) {
            if (child.getType() == JavadocCommentsTokenTypes.PARAMETER_NAME) {
                name = child.getText();
                if (name.startsWith("<") && name.endsWith(">")) {
                    name = name.substring(1, name.length() - 1);
                }
                break;
            }
            child = child.getNextSibling();
        }
        return name;
    }

    /**
     * Returns the token type of the declaration owning this block comment.
     * Handles comments attached to {@code TYPE}, {@code MODIFIERS},
     * or {@code TYPE_PARAMETERS} by returning the grandparent type.
     *
     * @param blockComment the block-comment node
     * @return the token type of the enclosing declaration
     */
    private static int getParentType(DetailAST blockComment) {
        final DetailAST parent = blockComment.getParent();
        int parentType = parent.getType();

        if (parentType == TokenTypes.TYPE
                || parentType == TokenTypes.MODIFIERS
                || parentType == TokenTypes.TYPE_PARAMETERS) {
            parentType = parent.getParent().getType();
        }

        return parentType;
    }
}
