////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Set;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Disallow assignment of parameters.
 * </p>
 * <p>
 * Rationale:
 * Parameter assignment is often considered poor
 * programming practice. Forcing developers to declare
 * parameters as final is often onerous. Having a check
 * ensure that parameters are never assigned would give
 * the best of both worlds.
 * </p>
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
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
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
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
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        // clear data
        parameterNamesStack.clear();
        parameterNames = Collections.emptySet();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
                visitMethodDef(ast);
                break;
            case TokenTypes.ASSIGN:
            case TokenTypes.PLUS_ASSIGN:
            case TokenTypes.MINUS_ASSIGN:
            case TokenTypes.STAR_ASSIGN:
            case TokenTypes.DIV_ASSIGN:
            case TokenTypes.MOD_ASSIGN:
            case TokenTypes.SR_ASSIGN:
            case TokenTypes.BSR_ASSIGN:
            case TokenTypes.SL_ASSIGN:
            case TokenTypes.BAND_ASSIGN:
            case TokenTypes.BXOR_ASSIGN:
            case TokenTypes.BOR_ASSIGN:
                visitAssign(ast);
                break;
            case TokenTypes.INC:
            case TokenTypes.POST_INC:
            case TokenTypes.DEC:
            case TokenTypes.POST_DEC:
                visitIncDec(ast);
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
                leaveMethodDef();
                break;
            case TokenTypes.ASSIGN:
            case TokenTypes.PLUS_ASSIGN:
            case TokenTypes.MINUS_ASSIGN:
            case TokenTypes.STAR_ASSIGN:
            case TokenTypes.DIV_ASSIGN:
            case TokenTypes.MOD_ASSIGN:
            case TokenTypes.SR_ASSIGN:
            case TokenTypes.BSR_ASSIGN:
            case TokenTypes.SL_ASSIGN:
            case TokenTypes.BAND_ASSIGN:
            case TokenTypes.BXOR_ASSIGN:
            case TokenTypes.BOR_ASSIGN:
            case TokenTypes.INC:
            case TokenTypes.POST_INC:
            case TokenTypes.DEC:
            case TokenTypes.POST_DEC:
                // Do nothing
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    /**
     * Checks if this is assignments of parameter.
     * @param ast assignment to check.
     */
    private void visitAssign(DetailAST ast) {
        checkIdent(ast);
    }

    /**
     * Checks if this is increment/decrement of parameter.
     * @param ast dec/inc to check.
     */
    private void visitIncDec(DetailAST ast) {
        checkIdent(ast);
    }

    /**
     * Check if ident is parameter.
     * @param ast ident to check.
     */
    private void checkIdent(DetailAST ast) {
        if (!parameterNames.isEmpty()) {
            final DetailAST identAST = ast.getFirstChild();

            if (identAST != null
                && identAST.getType() == TokenTypes.IDENT
                && parameterNames.contains(identAST.getText())) {
                log(ast.getLineNo(), ast.getColumnNo(),
                    MSG_KEY, identAST.getText());
            }
        }
    }

    /**
     * Creates new set of parameters and store old one in stack.
     * @param ast a method to process.
     */
    private void visitMethodDef(DetailAST ast) {
        parameterNamesStack.push(parameterNames);
        parameterNames = Sets.newHashSet();

        visitMethodParameters(ast.findFirstToken(TokenTypes.PARAMETERS));
    }

    /** Restores old set of parameters. */
    private void leaveMethodDef() {
        parameterNames = parameterNamesStack.pop();
    }

    /**
     * Creates new parameter set for given method.
     * @param ast a method for process.
     */
    private void visitMethodParameters(DetailAST ast) {
        DetailAST parameterDefAST =
            ast.findFirstToken(TokenTypes.PARAMETER_DEF);

        while (parameterDefAST != null) {
            if (parameterDefAST.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST param =
                    parameterDefAST.findFirstToken(TokenTypes.IDENT);
                parameterNames.add(param.getText());
            }
            parameterDefAST = parameterDefAST.getNextSibling();
        }
    }
}
