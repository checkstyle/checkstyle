////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;

import antlr.CommonAST;
import antlr.Token;
import antlr.collections.AST;

/**
 * An extension of the CommonAST that records the line and column number.
 * The idea was taken from http://www.jguru.com/jguru/faq/view.jsp?EID=62654.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class DetailAST
    extends CommonAST
{
    /** constant to indicate if not calculated the child count */
    private static final int NOT_INITIALIZED = Integer.MIN_VALUE;

    /** the line number **/
    private int mLineNo = NOT_INITIALIZED;
    /** the column number **/
    private int mColumnNo = NOT_INITIALIZED;

    /** number of children */
    private int mChildCount = NOT_INITIALIZED;
    /** the parent token */
    private DetailAST mParent;

    /** @see antlr.CommonAST **/
    public void initialize(Token aTok)
    {
        super.initialize(aTok);
        mLineNo = aTok.getLine();
        mColumnNo = aTok.getColumn() - 1; // expect columns to start @ 0
    }

    /** @see antlr.CommonAST **/
    public void initialize(AST aAST)
    {
        final DetailAST da = (DetailAST) aAST;
        setText(da.getText());
        setType(da.getType());
        mLineNo = da.getLineNo();
        mColumnNo = da.getColumnNo();
    }

    public void setFirstChild(AST aAST)
    {
        mChildCount = NOT_INITIALIZED;
        super.setFirstChild(aAST);
    }



    /**
     * Returns the number of child nodes one level below this node. That is is
     * does not recurse down the tree.
     * @return the number of child nodes
     */
    public int getChildCount()
    {
        // lazy init
        if (mChildCount == NOT_INITIALIZED) {
            mChildCount = 0;
            AST child = getFirstChild();

            while (child != null) {
                mChildCount += 1;
                child = child.getNextSibling();
            }
        }
        return mChildCount;
    }

    /**
     * Set the parent token.
     * @param aParent the parent token
     */
    // TODO: Check visibility, could be private if set in setFirstChild() and friends
    public void setParent(DetailAST aParent)
    {
        mParent = aParent;
    }

    /**
     * Returns the parent token
     * @return the parent token
     */
    public DetailAST getParent()
    {
        return mParent;
    }

    /** @return the line number **/
    public int getLineNo()
    {
        if (mLineNo == NOT_INITIALIZED) {
            // an inner AST that has been initiaized
            // with initialize(String text)
            DetailAST child = (DetailAST) getFirstChild();
            DetailAST sibling = (DetailAST) getNextSibling();
            if (child != null) {
                return child.getLineNo();
            } else if (sibling != null) {
                return sibling.getLineNo();
            }
        }
        return mLineNo;
    }

    /** @return the column number **/
    public int getColumnNo()
    {
        if (mColumnNo == NOT_INITIALIZED) {
            // an inner AST that has been initiaized
            // with initialize(String text)
            DetailAST child = (DetailAST) getFirstChild();
            DetailAST sibling = (DetailAST) getNextSibling();
            if (child != null) {
                return child.getColumnNo();
            } else if (sibling != null) {
                return sibling.getColumnNo();
            }
        }
        return mColumnNo;
    }

    /** @return a string representation of the object **/
    public String toString()
    {
        return super.toString() + " {line = " + getLineNo() + ", col = "
            + getColumnNo() + "}";
    }

    public DetailAST getLastChild()
    {
        AST ast = getFirstChild();
        while (ast.getNextSibling() != null) {
            ast = ast.getNextSibling();
        }
        return (DetailAST) ast;
    }
}
