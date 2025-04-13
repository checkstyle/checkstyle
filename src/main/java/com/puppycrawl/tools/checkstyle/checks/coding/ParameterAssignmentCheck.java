////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Disallows assignment of parameters.
 * </div>
 *
 * <p>
 * Rationale:
 * Parameter assignment is often considered poor
 * programming practice. Forcing developers to declare
 * parameters as final is often onerous. Having a check
 * ensure that parameters are never assigned would give
 * the best of both worlds.
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
 * {@code parameter.assignment}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public final class ParameterAssignmentCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "parameter.assignment";

    /** Stack of methods' parameters. */
    private final Deque<Set<String>> parameterNamesStack = new ArrayDeque<>();
    /** Current set of parameters. */
    private Set<String> parameterNames;

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.ASSIGN,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.SR_ASSIGN,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.SL_ASSIGN,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.INC,
            TokenTypes.POST_INC,
            TokenTypes.DEC,
            TokenTypes.POST_DEC,
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        // clear data
        parameterNamesStack.clear();
        parameterNames = Collections.emptySet();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int type = ast.getType();
        if (TokenUtil.isOfType(type, TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF)) {
            visitMethodDef(ast);
        }
        else if (type == TokenTypes.LAMBDA) {
            if (ast.getParent().getType() != TokenTypes.SWITCH_RULE) {
                visitLambda(ast);
            }
        }
        else {
            checkNestedIdent(ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        final int type = ast.getType();
        if (TokenUtil.isOfType(type, TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF)
            || type == TokenTypes.LAMBDA
            && ast.getParent().getType() != TokenTypes.SWITCH_RULE) {
            parameterNames = parameterNamesStack.pop();
        }
    }

    /**
     * Check if nested ident is parameter.
     *
     * @param ast parent of node of ident
     */
    private void checkNestedIdent(DetailAST ast) {
        final DetailAST identAST = ast.getFirstChild();

        if (identAST != null
            && identAST.getType() == TokenTypes.IDENT
            && parameterNames.contains(identAST.getText())) {
            log(ast, MSG_KEY, identAST.getText());
        }
    }

    /**
     * Creates new set of parameters and store old one in stack.
     *
     * @param ast a method to process.
     */
    private void visitMethodDef(DetailAST ast) {
        parameterNamesStack.push(parameterNames);
        parameterNames = new HashSet<>();

        visitMethodParameters(ast.findFirstToken(TokenTypes.PARAMETERS));
    }

    /**
     * Creates new set of parameters and store old one in stack.
     *
     * @param lambdaAst node of type {@link TokenTypes#LAMBDA}.
     */
    private void visitLambda(DetailAST lambdaAst) {
        parameterNamesStack.push(parameterNames);
        parameterNames = new HashSet<>();

        DetailAST parameterAst = lambdaAst.findFirstToken(TokenTypes.PARAMETERS);
        if (parameterAst == null) {
            parameterAst = lambdaAst.getFirstChild();
        }
        visitLambdaParameters(parameterAst);
    }

    /**
     * Creates new parameter set for given method.
     *
     * @param ast a method for process.
     */
    private void visitMethodParameters(DetailAST ast) {
        visitParameters(ast);
    }

    /**
     * Creates new parameter set for given lambda expression.
     *
     * @param ast a lambda expression parameter to process
     */
    private void visitLambdaParameters(DetailAST ast) {
        if (ast.getType() == TokenTypes.IDENT) {
            parameterNames.add(ast.getText());
        }
        else {
            visitParameters(ast);
        }
    }

    /**
     * Visits parameter list and adds parameter names to the set.
     *
     * @param parametersAst ast node of type {@link TokenTypes#PARAMETERS}.
     */
    private void visitParameters(DetailAST parametersAst) {
        DetailAST parameterDefAST =
            parametersAst.findFirstToken(TokenTypes.PARAMETER_DEF);

        while (parameterDefAST != null) {
            if (!CheckUtil.isReceiverParameter(parameterDefAST)) {
                final DetailAST param =
                    parameterDefAST.findFirstToken(TokenTypes.IDENT);
                parameterNames.add(param.getText());
            }
            parameterDefAST = parameterDefAST.getNextSibling();
        }
    }

}
