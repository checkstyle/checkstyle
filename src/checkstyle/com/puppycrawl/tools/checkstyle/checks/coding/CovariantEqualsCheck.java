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

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>Checks that if a class defines a covariant method equals,
 * then it defines method equals(java.lang.Object).
 * Inspired by findbugs,
 * http://www.cs.umd.edu/~pugh/java/bugs/docs/findbugsPaper.pdf
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="CovariantEquals"/&gt;
 * </pre>
 * @author Rick Giles
 * @version 1.0
 */
public class CovariantEqualsCheck
    extends Check
{
    /** Stack of class attributes. Used for inner classes */
    private LinkedList mClassStack = new LinkedList();

    /**
     * Records the covariant equals method definitions of a class and whether
     * the class defines method equals(java.lang.Object).
     */
    private class ClassAttributes
    {
        /** root AST node for class definition */
        private DetailAST mRootAST;

        /** Set of AST nodes for defined equals methods.
         * Empty if the class defines method equals(java.lang.Object)
         */
        private Set mEqualsNodes = new HashSet();

        /** true if class defines method equals(java.lang.Object) */
        private boolean mHasEqualsObject = false;

        /**
         * Constructs a ClassAttributes for a class definition.
         * @param aAST the root AST node for the class definition.
         */
        public ClassAttributes(DetailAST aAST)
        {
            mRootAST = aAST;
        }

        /**
         * Returns the root AST for the class definition.
         * @return the root AST for the class definition.
         */
        public DetailAST getRootAST()
        {
            return mRootAST;
        }

        /**
         * Adds a AST node for the definition of an equals method.
         * @param aAST the node of an equals method definition.
         */
        public void addEqualsNode(DetailAST aAST)
        {
            if (!mHasEqualsObject) {
                mEqualsNodes.add(aAST);
            }
        }

        /**
         * Returns the set of AST nodes for equals method definitions.
         * The set is empty if the class defines method
         * equals(java.lang.Object).
         * @return the set of AST nodes for equals method definitions.
         */
        public Set getEqualsNodes()
        {
            return mEqualsNodes;
        }

        /**
         * Records the definition of method equals(java.lang.Object).
         */
        public void setHasEqualsObject()
        {
            mHasEqualsObject = true;
            mEqualsNodes.clear();
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.METHOD_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aRootAST)
    {
        mClassStack.clear();
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.METHOD_DEF) {
            if (isEqualsMethod(aAST) && !ScopeUtils.inInterfaceBlock(aAST)) {
                final DetailAST definer = getDefiner(aAST);
                if (mClassStack.isEmpty()) {
                    mClassStack.add(new ClassAttributes(definer));
                }
                ClassAttributes attrs =
                    (ClassAttributes) mClassStack.getLast();
                final DetailAST currentRoot = attrs.getRootAST();
                if (definer != currentRoot) {
                    final ClassAttributes definerAttrs =
                        new ClassAttributes(definer);
                    mClassStack.add(definerAttrs);
                    attrs = definerAttrs;
                }
                if (hasObjectParameter(aAST)) {
                    attrs.setHasEqualsObject();
                }
                else {
                    attrs.addEqualsNode(aAST);
                }
            }
        }
    }

    /**
     * Determines the definer of an AST node. The definer is a class,
     * interface or new (anonymous class).
     * @param aAST the defined AST node.
     * @return the definer of aAST.
     */
    private DetailAST getDefiner(DetailAST aAST)
    {
        for (DetailAST token = aAST.getParent();
             token != null;
             token = token.getParent())
        {
            final int type = token.getType();
            if ((type == TokenTypes.CLASS_DEF)
                || (type == TokenTypes.INTERFACE_DEF)
                || (type == TokenTypes.LITERAL_NEW))
            {
                return token;
            }

        }
        return null;
    }

    /**
     * Tests whether a method definition AST defines an equals covariant.
     * @param aAST the method definition AST to test.
     * Precondition: aAST is a TokenTypes.METHOD_DEF node.
     * @return true if aAST defines an equals covariant.
     */
    private boolean isEqualsMethod(DetailAST aAST)
    {
        // non-static, non-abstract?
        final DetailAST modifiers = aAST.findFirstToken(TokenTypes.MODIFIERS);
        if (modifiers.branchContains(TokenTypes.LITERAL_STATIC)
            || modifiers.branchContains(TokenTypes.ABSTRACT))
        {
            return false;
        }

        // named "equals"?
        final DetailAST nameNode = aAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameNode.getText();
        if (!name.equals("equals")) {
            return false;
        }

        // one parameter?
        final DetailAST paramsNode =
            aAST.findFirstToken(TokenTypes.PARAMETERS);
        return (paramsNode.getChildCount() == 1);
    }

    /**
     * Tests whether a method definition AST has exactly one
     * parameter of type Object.
     * @param aAST the method definition AST to test.
     * Precondition: aAST is a TokenTypes.METHOD_DEF node.
     * @return true if aAST has exactly one parameter of type Object.
     */
    private boolean hasObjectParameter(DetailAST aAST)
    {
        // one parameter?
        final DetailAST paramsNode =
            aAST.findFirstToken(TokenTypes.PARAMETERS);
        if (paramsNode.getChildCount() != 1) {
            return false;
        }

        // parameter type "Object"?
        final DetailAST paramNode =
            paramsNode.findFirstToken(TokenTypes.PARAMETER_DEF);
        final DetailAST typeNode = paramNode.findFirstToken(TokenTypes.TYPE);
        final FullIdent fullIdent = FullIdent.createFullIdentBelow(typeNode);
        final String name = fullIdent.getText();
        return (name.equals("Object") || name.equals("java.lang.Object"));
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void leaveToken(DetailAST aAST)
    {
        final int type = aAST.getType();
        if ((type == TokenTypes.LITERAL_NEW)
            || (type == TokenTypes.CLASS_DEF))
        {
            // pop class stack
            if (!mClassStack.isEmpty()) {
                final ClassAttributes attrs =
                    (ClassAttributes) mClassStack.getLast();
                if (attrs.getRootAST() == aAST) {
                    mClassStack.removeLast();
                    final Set equalsNodes = attrs.getEqualsNodes();
                    final Iterator it = equalsNodes.iterator();
                    while (it.hasNext()) {
                        final DetailAST equalsAST = (DetailAST) it.next();
                        final DetailAST nameNode =
                            equalsAST.findFirstToken(TokenTypes.IDENT);
                        log(nameNode.getLineNo(), nameNode.getColumnNo(),
                            "covariant.equals");
                    }
                }
            }
        }
    }
}
