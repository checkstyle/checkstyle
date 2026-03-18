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
import java.util.BitSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the order of parameters in Javadoc comments.
 * </div>
 *
 * @since 6.0
 */
@StatelessCheck
public class JavadocParamOrderCheck extends AbstractJavadocCheck {
    /**
    * A key is pointing to the warning message text in "messages.properties"
    * file.
    */
    public static final String MSG_JAVADOC_PARAM_ORDER = "javadoc.param.order";

    /**
     * Specify block tags targeted.
     */
    private final BitSet target = TokenUtil.asBitSet(
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF
    );

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
            final int parentType = getParentType(blockComment);

            if (target.get(parentType)) {
                // Walk up to METHOD_DEF or CTOR_DEF
                DetailAST declaration = blockComment.getParent();
                if (parentType == TokenTypes.METHOD_DEF) {
                    declaration = declaration.getParent();
                }
                checkParamOrder(ast, declaration);
            }
    }

    /**
     * Checks the order of parameters in the Javadoc comment against the order of parameters
     * in the method declaration.
     *
     * @param javadocContent the DetailNode representing the content of the Javadoc comment
     * @param declaration the DetailAST representing the method or constructor declaration associated with the Javadoc comment
     */
    private void checkParamOrder(DetailNode javadocContent, DetailAST declaration) {
        final List<String> methodParamNames = getMethodParamNames(declaration);
        final Map<String, DetailNode> javadocParamNodes =
                getParamTagNodes(javadocContent, methodParamNames);
        final List<String> javadocParamOrder = new ArrayList<>(javadocParamNodes.keySet());

        // i = pointer into method params (source of truth for correct order)
        // j = pointer into Javadoc params (what we actually have documented
        int j = 0;
        for (final String paramName : methodParamNames) {
            // If this method param wasn't documented at all, skip it
            if (!javadocParamNodes.containsKey(paramName)) {
                continue;
            }

            // The next documented param in Javadoc order should match methodParam
            final String javadocParamName = javadocParamOrder.get(j);
            if (!paramName.equals(javadocParamName)) {
                log(javadocParamNodes.get(javadocParamName).getLineNumber(),
                        MSG_JAVADOC_PARAM_ORDER);
            }
            j++;
        }
    }

    /**
     * Extracts the parameter tag nodes from the Javadoc content
     * and maps them to their corresponding parameter names.
     * Only parameters that are defined in the method declaration are included in the map.
     *
     * @param javadocContentNode the DetailNode representing the content of the Javadoc comment
     * @param methodParamNames the list of parameter names defined in the method declaration
     * @return a map of parameter names to their corresponding DetailNode in the Javadoc comment
     */
     private static Map<String, DetailNode> getParamTagNodes(DetailNode javadocContentNode,
                                                             List<String> methodParamNames) {
        final Map<String, DetailNode> paramNodes = new LinkedHashMap<>();
        DetailNode blockTag = javadocContentNode.getFirstChild();

        while (blockTag != null) {
            if (blockTag.getType() == JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG) {
                final DetailNode child = blockTag.getFirstChild();
                if (child != null &&
                        child.getType() == JavadocCommentsTokenTypes.PARAM_BLOCK_TAG) {
                    final String name = getParamName(child);
                    if (name != null && methodParamNames.contains(name)) {
                        paramNodes.putIfAbsent(name, child);
                    }
                }
            }
            blockTag = blockTag.getNextSibling();
        }
        return paramNodes;
    }

    /**
     * Extracts the parameter names from a method declaration by finding the
     * {@code PARAMETERS} token and retrieving the names of each parameter defined.
     *
     * @param methodDef the DetailAST representing the method declaration
     * @return a list of parameter names defined in the method declaration
     */
    private static List<String> getMethodParamNames(DetailAST methodDef) {
        final List<String> paramNames = new ArrayList<>();
        DetailAST params = methodDef.findFirstToken(TokenTypes.PARAMETERS);

        if (params != null) {
            DetailAST paramDef = params.getFirstChild();
            while (paramDef != null) {
                if (paramDef.getType() == TokenTypes.PARAMETER_DEF) {
                    DetailAST ident = paramDef.findFirstToken(TokenTypes.IDENT);
                    if (ident != null) {
                        paramNames.add(ident.getText());
                    }
                }
                paramDef = paramDef.getNextSibling();
            }
        }

        return paramNames;
    }

    /**
     * Extracts the parameter name from a {@code PARAM_BLOCK_TAG} node by
     * finding its {@code PARAMETER_NAME} child.
     *
     * @param paramBlockTag a node of type {@code PARAM_BLOCK_TAG}
     * @return the parameter name text, or {@code null} if not found
     */
    private static String getParamName(DetailNode paramBlockTag) {
        DetailNode child = paramBlockTag.getFirstChild();
        while (child != null) {
            if (child.getType() == JavadocCommentsTokenTypes.PARAMETER_NAME) {
                return child.getText();
            }
            child = child.getNextSibling();
        }
        return null;
    }


    /**
     * Gets the parent type of the block comment.
     * If the parent is a {@code TYPE} or {@code MODIFIERS},
     * it returns the grandparent type instead.
     *
     * @param blockComment the DetailAST representing the block comment.
     * @return the parent type
     */
    private static int getParentType(DetailAST blockComment) {
        final DetailAST parent = blockComment.getParent();
        int parentType = parent.getType();

        if (parentType == TokenTypes.TYPE || parentType == TokenTypes.MODIFIERS) {
            return parent.getParent().getType();
        }

        return parentType;
    }
}
