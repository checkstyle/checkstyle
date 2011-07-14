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
package com.puppycrawl.tools.checkstyle.api;

import java.util.BitSet;

import antlr.CommonAST;
import antlr.Token;
import antlr.collections.AST;

/**
 * An extension of the CommonAST that records the line and column
 * number.  The idea was taken from <a target="_top"
 * href="http://www.jguru.com/jguru/faq/view.jsp?EID=62654">Java Guru
 * FAQ: How can I include line numbers in automatically generated
 * ASTs?</a>.
 * @author Oliver Burn
 * @author lkuehne
 * @version 1.0
 * @see <a href="http://www.antlr.org/">ANTLR Website</a>
 */
public final class DetailAST extends CommonAST
{
    /** For Serialisation that will never happen. */
    private static final long serialVersionUID = -2580884815577559874L;

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
    /** previous sibling */
    private DetailAST mPreviousSibling;

    /**
     * All token types in this branch.
     * Token 'x' (where x is an int) is in this branch
     * if mBranchTokenTypes.get(x) is true.
     */
    private BitSet mBranchTokenTypes;

    @Override
    public void initialize(Token aTok)
    {
        super.initialize(aTok);
        mLineNo = aTok.getLine();
        mColumnNo = aTok.getColumn() - 1; // expect columns to start @ 0
    }

    @Override
    public void initialize(AST aAST)
    {
        final DetailAST da = (DetailAST) aAST;
        setText(da.getText());
        setType(da.getType());
        mLineNo = da.getLineNo();
        mColumnNo = da.getColumnNo();
    }

    @Override
    public void setFirstChild(AST aAST)
    {
        mChildCount = NOT_INITIALIZED;
        super.setFirstChild(aAST);
        if (aAST != null) {
            ((DetailAST) aAST).setParent(this);
        }
    }

    @Override
    public void setNextSibling(AST aAST)
    {
        super.setNextSibling(aAST);
        if ((aAST != null) && (mParent != null)) {
            ((DetailAST) aAST).setParent(mParent);
        }
        if (aAST != null) {
            ((DetailAST) aAST).setPreviousSibling(this);
        }
    }

    /**
     * Sets previous sibling.
     * @param aAST a previous sibling
     */
    void setPreviousSibling(DetailAST aAST)
    {
        mPreviousSibling = aAST;
    }

    @Override
    public void addChild(AST aAST)
    {
        super.addChild(aAST);
        if (aAST != null) {
            ((DetailAST) aAST).setParent(this);
            (getFirstChild()).setParent(this);
        }
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
    // TODO: should be private but that breaks the DetailASTTest
    // until we manage parent in DetailAST instead of externally
    void setParent(DetailAST aParent)
    {
        // TODO: Check visibility, could be private
        // if set in setFirstChild() and friends
        mParent = aParent;
        final DetailAST nextSibling = getNextSibling();
        if (nextSibling != null) {
            nextSibling.setParent(aParent);
            nextSibling.setPreviousSibling(this);
        }
    }

    /**
     * Returns the parent token.
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
            // an inner AST that has been initialized
            // with initialize(String text)
            final DetailAST child = getFirstChild();
            final DetailAST sibling = getNextSibling();
            if (child != null) {
                return child.getLineNo();
            }
            else if (sibling != null) {
                return sibling.getLineNo();
            }
        }
        return mLineNo;
    }

    /** @return the column number **/
    public int getColumnNo()
    {
        if (mColumnNo == NOT_INITIALIZED) {
            // an inner AST that has been initialized
            // with initialize(String text)
            final DetailAST child = getFirstChild();
            final DetailAST sibling = getNextSibling();
            if (child != null) {
                return child.getColumnNo();
            }
            else if (sibling != null) {
                return sibling.getColumnNo();
            }
        }
        return mColumnNo;
    }

    /** @return the last child node */
    public DetailAST getLastChild()
    {
        DetailAST ast = getFirstChild();
        while ((ast != null) && (ast.getNextSibling() != null)) {
            ast = ast.getNextSibling();
        }
        return ast;
    }

    /**
     * @return the token types that occur in the branch as a sorted set.
     */
    private BitSet getBranchTokenTypes()
    {
        // lazy init
        if (mBranchTokenTypes == null) {

            mBranchTokenTypes = new BitSet();
            mBranchTokenTypes.set(getType());

            // add union of all childs
            DetailAST child = getFirstChild();
            while (child != null) {
                final BitSet childTypes = child.getBranchTokenTypes();
                mBranchTokenTypes.or(childTypes);

                child = child.getNextSibling();
            }
        }
        return mBranchTokenTypes;
    }

    /**
     * Checks if this branch of the parse tree contains a token
     * of the provided type.
     * @param aType a TokenType
     * @return true if and only if this branch (including this node)
     * contains a token of type <code>aType</code>.
     */
    public boolean branchContains(int aType)
    {
        return getBranchTokenTypes().get(aType);
    }

    /**
     * Returns the number of direct child tokens that have the specified type.
     * @param aType the token type to match
     * @return the number of matching token
     */
    public int getChildCount(int aType)
    {
        int count = 0;
        for (AST i = getFirstChild(); i != null; i = i.getNextSibling()) {
            if (i.getType() == aType) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the previous sibling or null if no such sibling exists.
     * @return the previous sibling or null if no such sibling exists.
     */
    public DetailAST getPreviousSibling()
    {
        return mPreviousSibling;
    }

    /**
     * Returns the first child token that makes a specified type.
     * @param aType the token type to match
     * @return the matching token, or null if no match
     */
    public DetailAST findFirstToken(int aType)
    {
        DetailAST retVal = null;
        for (DetailAST i = getFirstChild(); i != null; i = i.getNextSibling()) {
            if (i.getType() == aType) {
                retVal = i;
                break;
            }
        }
        return retVal;
    }

    @Override
    public String toString()
    {
        return super.toString() + "[" + getLineNo() + "x" + getColumnNo() + "]";
    }

    @Override
    public DetailAST getNextSibling()
    {
        return (DetailAST) super.getNextSibling();
    }

    @Override
    public DetailAST getFirstChild()
    {
        return (DetailAST) super.getFirstChild();
    }
}
