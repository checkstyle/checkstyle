////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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

import java.util.LinkedList;

import antlr.collections.AST;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that an overriding clone() method invokes super.clone().
 * </p>
 * <p>
 * Reference:<a
 * href="http://java.sun.com/j2se/1.4.1/docs/api/java/lang/Object.html#clone()">
 * Object.clone</a>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="SuperClone"/&gt;
 * </pre>
 * @author Rick Giles
 */
public class SuperCloneCheck
        extends Check
{
    /**
     * Stack node for a clone method definition and a record of
     * whether the clone method has a call to super.clone().
     * @author Rick Giles
     */
    private class CloneNode
    {
        /** clone method definition */
        private DetailAST mCloneMethod;

        /** true if the clone method calls super.clone() */
        private boolean mCallsSuper;

        /**
         * Constructs a stack node for a clone method definition.
         * @param aAST AST for the clone method definition.
         */
        public CloneNode(DetailAST aAST)
        {
            mCloneMethod = aAST;
            mCallsSuper = false;
        }

        /**
         * Records that the clone method has a call to super.clone().
         */
        public void setCallsSuper()
        {
            mCallsSuper = true;
        }

        /**
         * Determines whether the clone method has a call to
         * super.clone().
         * @return true if the clone method has a call to
         * super.clone().
         */
        public boolean getCallsSuper()
        {
            return mCallsSuper;
        }

        /**
         * Returns the clone method definition AST.
         * @return the clone method definition AST.
         */
        public DetailAST getCloneMethod()
        {
            return mCloneMethod;
        }
    }

    /** stack of clone methods */
    private final LinkedList mCloneStack = new LinkedList();

    /** @see Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.LITERAL_SUPER,
        };
    }

    /**
     *
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public void beginTree(DetailAST aRootAST)
    {
        mCloneStack.clear();
    }

    /**
     *
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public void visitToken(DetailAST aAST)
    {
        if (isCloneMethod(aAST)) {
            mCloneStack.add(new CloneNode(aAST));
        }
        else if (isSuperClone(aAST)) {
            final CloneNode cloneNode = (CloneNode) mCloneStack.getLast();
            cloneNode.setCallsSuper();
        }
    }

    /**
     *  Determines whether a 'super' literal calls super.clone()
     * within a clone() method.
     * @param aAST the AST node for a 'super' literal.
     * @return true if aAST is a call to super.clone within a
     * clone() method.
     */
    private boolean isSuperClone(DetailAST aAST)
    {
        if (aAST.getType() != TokenTypes.LITERAL_SUPER) {
            return false;
        }
        // dot operator?
        DetailAST parent = aAST.getParent();
        if ((parent == null) || (parent.getType() != TokenTypes.DOT)) {
            return false;
        }

        // named 'clone'?
        final AST sibling = aAST.getNextSibling();
        if ((sibling == null) || (sibling.getType() != TokenTypes.IDENT)) {
            return false;
        }
        final String name = sibling.getText();
        if (!"clone".equals(name)) {
            return false;
        }

        // 0 parameters?
        final DetailAST args = (DetailAST) parent.getNextSibling();
        if ((args == null) || (args.getType() != TokenTypes.ELIST)) {
            return false;
        }
        if (args.getChildCount() != 0) {
            return false;
        }

        // in a clone() method?
        while (parent != null) {
            if (parent.getType() == TokenTypes.METHOD_DEF) {
                return isCloneMethod(parent);
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

    /**
     *
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public void leaveToken(DetailAST aAST)
    {
        if (isCloneMethod(aAST)) {
            final CloneNode cloneNode = (CloneNode) mCloneStack.removeLast();
            if (!cloneNode.getCallsSuper()) {
                final DetailAST methodAST = cloneNode.getCloneMethod();
                final DetailAST nameAST =
                    methodAST.findFirstToken(TokenTypes.IDENT);
                log(nameAST.getLineNo(), nameAST.getColumnNo(),
                    "missing.super.call",
                    new Object[] {nameAST.getText()});
            }
        }
    }

    /**
     * Determines whether an AST is a class clone method definition,
     * i.e. defines a method clone' and 0 parameters.
     * @param aAST the method definition AST.
     * @return true if the method of aAST is clone().
     */
    private boolean isCloneMethod(DetailAST aAST)
    {
        if ((aAST.getType() != TokenTypes.METHOD_DEF)
            || ScopeUtils.inInterfaceBlock(aAST))
        {
            return false;
        }
        final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();
        if (!"clone".equals(name)) {
            return false;
        }
        final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
        return (params.getChildCount() == 0);
    }
}
