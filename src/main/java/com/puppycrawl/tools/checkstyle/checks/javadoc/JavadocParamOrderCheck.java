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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that {@code @param} tags in Javadoc comments are in the same order as the
 * parameters in the declaration.
 * </div>
 *
 * <p>
 * Type parameters must come before regular parameters. For record declarations, record
 * components are treated as regular parameters and must be documented after type parameters.
 * For compact constructors, the expected parameter order is the order of the record components
 * in the record declaration.
 * </p>
 *
 * <p>
 * The check does not validate missing, extra, or duplicate {@code @param} tags. It reports only
 * tags that move backward in the declaration order.
 * </p>
 *
 * @since 13.11.0
 */
@FileStatefulCheck
public class JavadocParamOrderCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "javadoc.param.order";

    /** Html element start symbol. */
    private static final String ELEMENT_START = "<";

    /** Html element end symbol. */
    private static final String ELEMENT_END = ">";

    /** Javadoc param tag names, mapped by their corresponding Javadoc node. */
    private final Map<DetailNode, String> javadocTags = new LinkedHashMap<>();

    /**
     * Creates a new {@code JavadocParamOrderCheck} instance.
     */
    public JavadocParamOrderCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.RECORD_DEF,
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
        };
    }

    @Override
    public final void visitToken(final DetailAST ast) {
        final DetailAST blockCommentNode = JavadocUtil.getAttachedJavadocComment(ast);
        if (blockCommentNode != null) {
            javadocTags.clear();
            super.visitToken(blockCommentNode);
            for (Map.Entry<DetailNode, String> javadocTag : getMisorderedParamTags(ast)) {
                log(javadocTag.getKey(), MSG_KEY, javadocTag.getValue());
            }
        }
    }

    @Override
    public void visitJavadocToken(final DetailNode ast) {
        collectParam(ast);
    }

    /**
     * Collects a param tag.
     *
     * @param ast the param tag node
     */
    private void collectParam(final DetailNode ast) {
        final DetailNode parameterName = JavadocUtil.findFirstToken(
                ast, JavadocCommentsTokenTypes.PARAMETER_NAME);
        if (parameterName != null) {
            javadocTags.put(ast, parameterName.getText());
        }
    }

    /**
     * Gets collected Javadoc param tags that violate the expected declaration order.
     *
     * @param ast Java AST node whose Javadoc is being checked
     * @return collected Javadoc param tags that violate the expected declaration order
     */
    private List<Map.Entry<DetailNode, String>> getMisorderedParamTags(final DetailAST ast) {
        final List<String> expectedParamOrder = getExpectedParamOrder(ast);
        final List<Map.Entry<DetailNode, String>> misorderedTags = new ArrayList<>();

        int maxIndexOfPreviousParam = -1;
        for (Map.Entry<DetailNode, String> javadocTag : javadocTags.entrySet()) {
            final int currentIndex = expectedParamOrder.indexOf(javadocTag.getValue());

            if (currentIndex >= 0) {
                if (currentIndex < maxIndexOfPreviousParam) {
                    misorderedTags.add(javadocTag);
                }
                else {
                    maxIndexOfPreviousParam = currentIndex;
                }
            }
        }
        return misorderedTags;
    }

    /**
     * Gets expected param tag order for the current AST node.
     *
     * @param ast Java AST node whose Javadoc is being checked
     * @return expected param tag order
     */
    private static List<String> getExpectedParamOrder(final DetailAST ast) {
        final List<String> expectedParamOrder = new ArrayList<>();

        addTypeParameterNames(expectedParamOrder, ast);

        switch (ast.getType()) {
            case TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF ->
                addParameterNames(expectedParamOrder, ast);

            case TokenTypes.RECORD_DEF ->
                addRecordComponentNames(expectedParamOrder, ast);

            case TokenTypes.COMPACT_CTOR_DEF ->
                addRecordComponentNames(expectedParamOrder, getRecordDef(ast));

            default -> {
                // No formal parameters for type definitions other than records.
            }
        }

        return expectedParamOrder;
    }

    /**
     * Adds type parameter names from the supplied AST node.
     *
     * @param paramNames destination list
     * @param ast node to inspect
     */
    private static void addTypeParameterNames(final Collection<String> paramNames,
            final DetailAST ast) {
        for (String typeParamName : CheckUtil.getTypeParameterNames(ast)) {
            paramNames.add(ELEMENT_START + typeParamName + ELEMENT_END);
        }
    }

    /**
     * Adds parameter names from the supplied method or constructor AST node.
     *
     * @param paramNames destination list
     * @param ast node to inspect
     */
    private static void addParameterNames(final Collection<String> paramNames,
            final DetailAST ast) {
        final DetailAST parameters = NullUtil.notNull(ast.findFirstToken(TokenTypes.PARAMETERS));
        TokenUtil.forEachChild(parameters, TokenTypes.PARAMETER_DEF,
                paramDef -> addParameterName(paramNames, paramDef));
    }

    /**
     * Adds a parameter name from the supplied parameter definition AST node.
     *
     * @param paramNames destination list
     * @param paramDef parameter definition node
     */
    private static void addParameterName(final Collection<String> paramNames,
            final DetailAST paramDef) {
        if (!CheckUtil.isReceiverParameter(paramDef)) {
            final DetailAST ident = NullUtil.notNull(paramDef.findFirstToken(TokenTypes.IDENT));
            paramNames.add(ident.getText());
        }
    }

    /**
     * Adds record component names from the supplied record AST node.
     *
     * @param paramNames destination list
     * @param recordDef record definition node
     */
    private static void addRecordComponentNames(final Collection<String> paramNames,
            final DetailAST recordDef) {
        for (DetailAST component : getRecordComponents(recordDef)) {
            paramNames.add(component.getText());
        }
    }

    /**
     * Finds the nearest ancestor record definition node for the given AST node.
     *
     * @param ast the AST node to start searching from
     * @return the nearest {@code RECORD_DEF} AST node
     */
    private static DetailAST getRecordDef(final DetailAST ast) {
        DetailAST current = ast;
        while (current.getType() != TokenTypes.RECORD_DEF) {
            current = current.getParent();
        }
        return current;
    }

    /**
     * Gets record component identifier nodes from a record definition.
     *
     * @param recordDef record definition node
     * @return record component identifier nodes
     */
    private static List<DetailAST> getRecordComponents(final DetailAST recordDef) {
        final List<DetailAST> components = new ArrayList<>();
        final DetailAST recordDecl = NullUtil.notNull(
                recordDef.findFirstToken(TokenTypes.RECORD_COMPONENTS));

        DetailAST child = recordDecl.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.RECORD_COMPONENT_DEF) {
                components.add(NullUtil.notNull(child.findFirstToken(TokenTypes.IDENT)));
            }
            child = child.getNextSibling();
        }
        return components;
    }

}
