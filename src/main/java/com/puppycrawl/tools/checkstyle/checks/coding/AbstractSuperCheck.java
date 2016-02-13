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

import java.util.Deque;

import antlr.collections.AST;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * <p>
 * Abstract class for checking that an overriding method with no parameters
 * invokes the super method.
 * </p>
 * @author Rick Giles
 */
public abstract class AbstractSuperCheck
        extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "missing.super.call";

    /** Stack of methods. */
    private final Deque<MethodNode> methodStack = Lists.newLinkedList();

    /**
     * Returns the name of the overriding method.
     * @return the name of the overriding method.
     */
    protected abstract String getMethodName();

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.LITERAL_SUPER,
        };
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        methodStack.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isOverridingMethod(ast)) {
            methodStack.add(new MethodNode(ast));
        }
        else if (isSuperCall(ast)) {
            final MethodNode methodNode = methodStack.getLast();
            methodNode.setCallingSuper();
        }
    }

    /**
     * Determines whether a 'super' literal is a call to the super method
     * for this check.
     * @param literalSuperAst the AST node of a 'super' literal.
     * @return true if ast is a call to the super method for this check.
     */
    private boolean isSuperCall(DetailAST literalSuperAst) {
        boolean superCall = false;

        if (literalSuperAst.getType() == TokenTypes.LITERAL_SUPER) {
            // dot operator?
            final DetailAST dotAst = literalSuperAst.getParent();

            if (!isSameNameMethod(literalSuperAst)
                && !hasArguments(dotAst)) {
                superCall = isSuperCallInOverridingMethod(dotAst);
            }
        }
        return superCall;
    }

    /**
     * Determines whether a super call in overriding method.
     *
     * @param ast The AST node of a 'dot operator' in 'super' call.
     * @return true if super call in overriding method.
     */
    private boolean isSuperCallInOverridingMethod(DetailAST ast) {
        boolean inOverridingMethod = false;
        DetailAST dotAst = ast;

        while (dotAst.getType() != TokenTypes.CTOR_DEF
                && dotAst.getType() != TokenTypes.INSTANCE_INIT) {

            if (dotAst.getType() == TokenTypes.METHOD_DEF) {
                inOverridingMethod = isOverridingMethod(dotAst);
                break;
            }
            dotAst = dotAst.getParent();

        }
        return inOverridingMethod;
    }

    /**
     * Does method have any arguments.
     * @param methodCallDotAst DOT DetailAST
     * @return true if any parameters found
     */
    private static boolean hasArguments(DetailAST methodCallDotAst) {
        final DetailAST argumentsList = methodCallDotAst.getNextSibling();
        return argumentsList.getChildCount() > 0;
    }

    /**
     * Is same name of method.
     * @param ast method AST
     * @return true if method name is the same
     */
    private boolean isSameNameMethod(DetailAST ast) {

        AST sibling = ast.getNextSibling();
        // ignore type parameters
        if (sibling != null
            && sibling.getType() == TokenTypes.TYPE_ARGUMENTS) {
            sibling = sibling.getNextSibling();
        }
        if (sibling == null) {
            return true;
        }
        final String name = sibling.getText();
        return !getMethodName().equals(name);
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (isOverridingMethod(ast)) {
            final MethodNode methodNode =
                methodStack.removeLast();
            if (!methodNode.isCallingSuper()) {
                final DetailAST methodAST = methodNode.getMethod();
                final DetailAST nameAST =
                    methodAST.findFirstToken(TokenTypes.IDENT);
                log(nameAST.getLineNo(), nameAST.getColumnNo(),
                    MSG_KEY, nameAST.getText());
            }
        }
    }

    /**
     * Determines whether an AST is a method definition for this check,
     * with 0 parameters.
     * @param ast the method definition AST.
     * @return true if the method of ast is a method for this check.
     */
    private boolean isOverridingMethod(DetailAST ast) {
        boolean overridingMethod = false;

        if (ast.getType() == TokenTypes.METHOD_DEF
                && !ScopeUtils.isInInterfaceOrAnnotationBlock(ast)) {
            final DetailAST nameAST = ast.findFirstToken(TokenTypes.IDENT);
            final String name = nameAST.getText();
            final DetailAST modifiersAST = ast.findFirstToken(TokenTypes.MODIFIERS);

            if (getMethodName().equals(name)
                    && !modifiersAST.branchContains(TokenTypes.LITERAL_NATIVE)) {
                final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
                overridingMethod = params.getChildCount() == 0;
            }
        }
        return overridingMethod;
    }

    /**
     * Stack node for a method definition and a record of
     * whether the method has a call to the super method.
     * @author Rick Giles
     */
    private static class MethodNode {
        /** Method definition. */
        private final DetailAST method;

        /** True if the overriding method calls the super method. */
        private boolean callingSuper;

        /**
         * Constructs a stack node for a method definition.
         * @param ast AST for the method definition.
         */
        MethodNode(DetailAST ast) {
            method = ast;
            callingSuper = false;
        }

        /**
         * Records that the overriding method has a call to the super method.
         */
        public void setCallingSuper() {
            callingSuper = true;
        }

        /**
         * Determines whether the overriding method has a call to the super
         * method.
         * @return true if the overriding method has a call to the super method.
         */
        public boolean isCallingSuper() {
            return callingSuper;
        }

        /**
         * Returns the overriding method definition AST.
         * @return the overriding method definition AST.
         */
        public DetailAST getMethod() {
            return method;
        }
    }
}
