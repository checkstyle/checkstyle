////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

import antlr.collections.AST;
import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.LinkedList;

/**
 * <p>
 * Abstract class for checking that an overriding method with no parameters
 * invokes the super method.
 * </p>
 * @author Rick Giles
 */
public abstract class AbstractSuperCheck
        extends Check
{
    /**
     * Stack node for a method definition and a record of
     * whether the method has a call to the super method.
     * @author Rick Giles
     */
    private static class MethodNode
    {
        /** method definition */
        private final DetailAST method;

        /** true if the overriding method calls the super method */
        private boolean callsSuper;

        /**
         * Constructs a stack node for a method definition.
         * @param ast AST for the method definition.
         */
        public MethodNode(DetailAST ast)
        {
            method = ast;
            callsSuper = false;
        }

        /**
         * Records that the overriding method has a call to the super method.
         */
        public void setCallsSuper()
        {
            callsSuper = true;
        }

        /**
         * Determines whether the overriding method has a call to the super
         * method.
         * @return true if the overriding method has a call to the super
         * method.
         */
        public boolean getCallsSuper()
        {
            return callsSuper;
        }

        /**
         * Returns the overriding method definition AST.
         * @return the overriding method definition AST.
         */
        public DetailAST getMethod()
        {
            return method;
        }
    }

    /** stack of methods */
    private final LinkedList<MethodNode> methodStack = Lists.newLinkedList();

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.LITERAL_SUPER,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.LITERAL_SUPER,
        };
    }

    /**
     * Returns the name of the overriding method.
     * @return the name of the overriding method.
     */
    protected abstract String getMethodName();

    @Override
    public void beginTree(DetailAST rootAST)
    {
        methodStack.clear();
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        if (isOverridingMethod(ast)) {
            methodStack.add(new MethodNode(ast));
        }
        else if (isSuperCall(ast)) {
            final MethodNode methodNode = methodStack.getLast();
            methodNode.setCallsSuper();
        }
    }

    /**
     *  Determines whether a 'super' literal is a call to the super method
     * for this check.
     * @param ast the AST node of a 'super' literal.
     * @return true if ast is a call to the super method
     * for this check.
     */
    private boolean isSuperCall(DetailAST ast)
    {
        if (ast.getType() != TokenTypes.LITERAL_SUPER) {
            return false;
        }
        // dot operator?
        DetailAST parent = ast.getParent();
        if ((parent == null) || (parent.getType() != TokenTypes.DOT)) {
            return false;
        }

        // same name of method
        AST sibling = ast.getNextSibling();
        // ignore type parameters
        if ((sibling != null)
            && (sibling.getType() == TokenTypes.TYPE_ARGUMENTS))
        {
            sibling = sibling.getNextSibling();
        }
        if ((sibling == null) || (sibling.getType() != TokenTypes.IDENT)) {
            return false;
        }
        final String name = sibling.getText();
        if (!getMethodName().equals(name)) {
            return false;
        }

        // 0 parameters?
        final DetailAST args = parent.getNextSibling();
        if ((args == null) || (args.getType() != TokenTypes.ELIST)) {
            return false;
        }
        if (args.getChildCount() != 0) {
            return false;
        }

        // in an overriding method for this check?
        while (parent != null) {
            if (parent.getType() == TokenTypes.METHOD_DEF) {
                return isOverridingMethod(parent);
            }
            else if ((parent.getType() == TokenTypes.CTOR_DEF)
                || (parent.getType() == TokenTypes.INSTANCE_INIT))
            {
                return false;
            }
            parent = parent.getParent();
        }
        return false;
    }

    @Override
    public void leaveToken(DetailAST ast)
    {
        if (isOverridingMethod(ast)) {
            final MethodNode methodNode =
                methodStack.removeLast();
            if (!methodNode.getCallsSuper()) {
                final DetailAST methodAST = methodNode.getMethod();
                final DetailAST nameAST =
                    methodAST.findFirstToken(TokenTypes.IDENT);
                log(nameAST.getLineNo(), nameAST.getColumnNo(),
                    "missing.super.call", nameAST.getText());
            }
        }
    }

    /**
     * Determines whether an AST is a method definition for this check,
     * with 0 parameters.
     * @param ast the method definition AST.
     * @return true if the method of ast is a method for this check.
     */
    private boolean isOverridingMethod(DetailAST ast)
    {
        if ((ast.getType() != TokenTypes.METHOD_DEF)
            || ScopeUtils.inInterfaceOrAnnotationBlock(ast))
        {
            return false;
        }
        final DetailAST nameAST = ast.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();
        if (!getMethodName().equals(name)) {
            return false;
        }
        final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
        return (params.getChildCount() == 0);
    }
}
