////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
        private final DetailAST mMethod;

        /** true if the overriding method calls the super method */
        private boolean mCallsSuper;

        /**
         * Constructs a stack node for a method definition.
         * @param aAST AST for the method definition.
         */
        public MethodNode(DetailAST aAST)
        {
            mMethod = aAST;
            mCallsSuper = false;
        }

        /**
         * Records that the overriding method has a call to the super method.
         */
        public void setCallsSuper()
        {
            mCallsSuper = true;
        }

        /**
         * Determines whether the overriding method has a call to the super
         * method.
         * @return true if the overriding method has a call to the super
         * method.
         */
        public boolean getCallsSuper()
        {
            return mCallsSuper;
        }

        /**
         * Returns the overriding method definition AST.
         * @return the overriding method definition AST.
         */
        public DetailAST getMethod()
        {
            return mMethod;
        }
    }

    /** stack of methods */
    private final LinkedList<MethodNode> mMethodStack = Lists.newLinkedList();

    @Override
    public int[] getDefaultTokens()
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
    public void beginTree(DetailAST aRootAST)
    {
        mMethodStack.clear();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if (isOverridingMethod(aAST)) {
            mMethodStack.add(new MethodNode(aAST));
        }
        else if (isSuperCall(aAST)) {
            final MethodNode methodNode = mMethodStack.getLast();
            methodNode.setCallsSuper();
        }
    }

    /**
     *  Determines whether a 'super' literal is a call to the super method
     * for this check.
     * @param aAST the AST node of a 'super' literal.
     * @return true if aAST is a call to the super method
     * for this check.
     */
    private boolean isSuperCall(DetailAST aAST)
    {
        if (aAST.getType() != TokenTypes.LITERAL_SUPER) {
            return false;
        }
        // dot operator?
        DetailAST parent = aAST.getParent();
        if ((parent == null) || (parent.getType() != TokenTypes.DOT)) {
            return false;
        }

        // same name of method
        AST sibling = aAST.getNextSibling();
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
    public void leaveToken(DetailAST aAST)
    {
        if (isOverridingMethod(aAST)) {
            final MethodNode methodNode =
                mMethodStack.removeLast();
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
     * @param aAST the method definition AST.
     * @return true if the method of aAST is a method for this check.
     */
    private boolean isOverridingMethod(DetailAST aAST)
    {
        if ((aAST.getType() != TokenTypes.METHOD_DEF)
            || ScopeUtils.inInterfaceOrAnnotationBlock(aAST))
        {
            return false;
        }
        final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();
        if (!getMethodName().equals(name)) {
            return false;
        }
        final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
        return (params.getChildCount() == 0);
    }
}
