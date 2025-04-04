///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.BitSet;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Utility class for {@link FinalLocalVariableCheck}-related operations.
 */
final class FinalLocalVariableCheckUtil {

    /**
     * Assign operator types.
     */
    public static final BitSet ASSIGN_OPERATOR_TYPES = TokenUtil.asBitSet(
        TokenTypes.ASSIGN,
        TokenTypes.BAND_ASSIGN,
        TokenTypes.BOR_ASSIGN,
        TokenTypes.BSR_ASSIGN,
        TokenTypes.BXOR_ASSIGN,
        TokenTypes.DEC,
        TokenTypes.DIV_ASSIGN,
        TokenTypes.INC,
        TokenTypes.MINUS_ASSIGN,
        TokenTypes.MOD_ASSIGN,
        TokenTypes.PLUS_ASSIGN,
        TokenTypes.POST_DEC,
        TokenTypes.POST_INC,
        TokenTypes.SL_ASSIGN,
        TokenTypes.SR_ASSIGN,
        TokenTypes.STAR_ASSIGN
    );

    /**
     * Loop types.
     */
    public static final BitSet LOOP_TYPES = TokenUtil.asBitSet(
        TokenTypes.LITERAL_DO,
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_WHILE
    );

    /**
     * Private constructor to prevent instantiation.
     */
    private FinalLocalVariableCheckUtil() {
        // Private constructor to prevent instantiation.
    }

    /**
     * Checks if a parameter definition meets the criteria for final checking.
     *
     * @param paramDefAst The parameter definition AST node
     * @return true if the parameter should be checked for final declaration
     */
    public static boolean isValidParameterDefinition(DetailAST paramDefAst) {
        return !isInLambda(paramDefAst)
            && paramDefAst
            .findFirstToken(TokenTypes.MODIFIERS)
            .findFirstToken(TokenTypes.FINAL) == null
            && !isInMethodWithoutBody(paramDefAst)
            && !isMultipleTypeCatch(paramDefAst)
            && !CheckUtil.isReceiverParameter(paramDefAst);
    }

    /**
     * Checks whether the scope of a node is restricted to a specific code blocks.
     *
     * @param node       node.
     * @param blockTypes int array of all block types to check.
     * @return true if the scope of a node is restricted to specific code block types.
     */
    public static boolean isInSpecificCodeBlocks(DetailAST node, int... blockTypes) {
        boolean returnValue = false;
        for (int blockType : blockTypes) {
            for (DetailAST token = node; token != null; token = token.getParent()) {
                final int type = token.getType();
                if (type == blockType) {
                    returnValue = true;
                    break;
                }
            }
        }
        return returnValue;
    }

    /**
     * If there is an {@code else} following or token is CASE_GROUP or
     * SWITCH_RULE and there is another {@code case} following, then update the
     * uninitialized variables.
     *
     * @param ast token to be checked
     * @return true if should be updated, else false
     */
    public static boolean shouldUpdateUninitializedVariables(DetailAST ast) {
        return ast.getLastChild().getType() == TokenTypes.LITERAL_ELSE
            || isCaseTokenWithAnotherCaseFollowing(ast);
    }

    /**
     * If token is CASE_GROUP or SWITCH_RULE and there is another {@code case} following.
     *
     * @param ast token to be checked
     * @return true if token is CASE_GROUP or SWITCH_RULE and there is another {@code case}
     *     following, else false
     */
    public static boolean isCaseTokenWithAnotherCaseFollowing(DetailAST ast) {
        boolean result = false;
        if (ast.getType() == TokenTypes.CASE_GROUP) {
            result = findLastCaseGroupWhichContainsSlist(ast.getParent()) != ast;
        }
        else if (ast.getType() == TokenTypes.SWITCH_RULE) {
            result = ast.getNextSibling().getType() == TokenTypes.SWITCH_RULE;
        }
        return result;
    }

    /**
     * Returns the last token of type {@link TokenTypes#CASE_GROUP} which contains
     * {@link TokenTypes#SLIST}.
     *
     * @param literalSwitchAst ast node of type {@link TokenTypes#LITERAL_SWITCH}
     * @return the matching token, or null if no match
     */
    public static DetailAST findLastCaseGroupWhichContainsSlist(DetailAST literalSwitchAst) {
        DetailAST returnValue = null;
        for (DetailAST astIterator = literalSwitchAst.getFirstChild(); astIterator != null;
             astIterator = astIterator.getNextSibling()) {
            if (astIterator.findFirstToken(TokenTypes.SLIST) != null) {
                returnValue = astIterator;
            }
        }
        return returnValue;
    }

    /**
     * Check if VARIABLE_DEF is initialized or not.
     *
     * @param ast VARIABLE_DEF to be checked
     * @return true if initialized
     */
    public static boolean isInitialized(DetailAST ast) {
        return ast.getParent().getLastChild().getType() == TokenTypes.ASSIGN;
    }

    /**
     * Whether the ast is the first child of its parent.
     *
     * @param ast the ast to check.
     * @return true if the ast is the first child of its parent.
     */
    public static boolean isFirstChild(DetailAST ast) {
        return ast.getPreviousSibling() == null;
    }

    /**
     * Check if given parameter definition is a multiple type catch.
     *
     * @param parameterDefAst parameter definition
     * @return true if it is a multiple type catch, false otherwise
     */
    public static boolean isMultipleTypeCatch(DetailAST parameterDefAst) {
        return parameterDefAst
            .findFirstToken(TokenTypes.TYPE)
            .findFirstToken(TokenTypes.BOR) != null;
    }

    /**
     * Get the ast node of type {@link FinalLocalVariableCheckUtil#LOOP_TYPES} that is the ancestor
     * of the current ast node, if there is no such node, null is returned.
     *
     * @param ast ast node
     * @return ast node of type {@link FinalLocalVariableCheckUtil#LOOP_TYPES} that is the ancestor
     *     of the current ast node, null if no such node exists
     */
    public static DetailAST getParentLoop(DetailAST ast) {
        DetailAST parentLoop = ast;
        while (parentLoop != null
            && !isLoopAst(parentLoop.getType())) {
            parentLoop = parentLoop.getParent();
        }
        return parentLoop;
    }

    /**
     * Is Arithmetic operator.
     *
     * @param parentType token AST
     * @return true is token type is in arithmetic operator
     */
    public static boolean isAssignOperator(int parentType) {
        return ASSIGN_OPERATOR_TYPES.get(parentType);
    }

    /**
     * Checks if current variable is defined in
     * {@link TokenTypes#FOR_INIT for-loop init}, e.g.:
     *
     * <p>
     * {@code
     * for (int i = 0, j = 0; i < j; i++) { . . . }
     * }
     * </p>
     * {@code i, j} are defined in {@link TokenTypes#FOR_INIT for-loop init}
     *
     * @param variableDef variable definition node.
     * @return true if variable is defined in {@link TokenTypes#FOR_INIT for-loop init}
     */
    public static boolean isVariableInForInit(DetailAST variableDef) {
        return variableDef.getParent().getType() == TokenTypes.FOR_INIT;
    }

    /**
     * Checks if a parameter is within a method that has no implementation body.
     *
     * @param parameterDefAst the AST node representing the parameter definition
     * @return true if the parameter is in a method without a body
     */
    public static boolean isInMethodWithoutBody(DetailAST parameterDefAst) {
        return parameterDefAst.getParent().getParent().findFirstToken(TokenTypes.SLIST) == null;
    }

    /**
     * Check if current param is lambda's param.
     *
     * @param paramDef {@link TokenTypes#PARAMETER_DEF parameter def}.
     * @return true if current param is lambda's param.
     */
    public static boolean isInLambda(DetailAST paramDef) {
        return paramDef.getParent().getParent().getType() == TokenTypes.LAMBDA;
    }

    /**
     * Find the Class, Constructor, Enum, Method, or Field in which it is defined.
     *
     * @param ast Variable for which we want to find the scope in which it is defined
     * @return ast The Class or Constructor or Method in which it is defined.
     */
    public static DetailAST findFirstUpperNamedBlock(DetailAST ast) {
        return ScopeUtil.isClassFieldDef(ast)
            || TokenUtil.isOfType(ast,
            TokenTypes.METHOD_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.COMPACT_CTOR_DEF)
            // recursion: key to success (and failure)
            // https://reddit.com/r/learnprogramming/comments/10o0gli/recursion_bad_practice/
            // -@cs[AvoidInlineConditionals] recursion: key to success (and failure)
            ? ast
            : findFirstUpperNamedBlock(ast.getParent());
    }

    /**
     * Check if both the Variables are same.
     *
     * @param ast1 Variable to compare
     * @param ast2 Variable to compare
     * @return true if both the variables are same, otherwise false
     */
    public static boolean isEqual(DetailAST ast1, DetailAST ast2) {
        return findFirstUpperNamedBlock(ast1) == findFirstUpperNamedBlock(ast2)
            && ast1.getText().equals(ast2.getText());
    }

    /**
     * Checks whether the ast is a loop.
     *
     * @param ast the ast to check.
     * @return true if the ast is a loop.
     */
    public static boolean isLoopAst(int ast) {
        return LOOP_TYPES.get(ast);
    }

}
