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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.DetailNode;

/**
 *
 * Implementation of DetailNode interface that is mutable.
 *
 * @author Baratali Izmailov
 *
 */
public class JavadocNodeImpl implements DetailNode
{
    /**
     * Node index among parent's children
     */
    private int mIndex;

    /**
     * Node type
     */
    private int mType;

    /**
     * Node's text content
     */
    private String mText;

    /**
     * Line number
     */
    private int mLineNumber;

    /**
     * Column number
     */
    private int mColumnNumber;

    /**
     * Array of child nodes
     */
    private DetailNode[] mChildren;

    /**
     * Parent node
     */
    private DetailNode mParent;

    @Override
    public int getType()
    {
        return mType;
    }

    @Override
    public String getText()
    {
        return mText;
    }

    @Override
    public int getLineNumber()
    {
        return mLineNumber;
    }

    @Override
    public int getColumnNumber()
    {
        return mColumnNumber;
    }

    @Override
    public DetailNode[] getChildren()
    {
        return mChildren;
    }

    @Override
    public DetailNode getParent()
    {
        return mParent;
    }

    @Override
    public int getIndex()
    {
        return mIndex;
    }

    public void setType(int aType)
    {
        this.mType = aType;
    }

    public void setText(String aText)
    {
        this.mText = aText;
    }

    public void setLineNumber(int aLineNumber)
    {
        this.mLineNumber = aLineNumber;
    }

    public void setColumnNumber(int aColumnNumber)
    {
        this.mColumnNumber = aColumnNumber;
    }

    public void setChildren(DetailNode[] aChildren)
    {
        this.mChildren = aChildren;
    }

    public void setParent(DetailNode aParent)
    {
        this.mParent = aParent;
    }

    public void setIndex(int aIndex)
    {
        this.mIndex = aIndex;
    }

    @Override
    public String toString()
    {
        return JavadocUtils.getTokenName(getType())
                + "[" + getLineNumber() + "x" + getColumnNumber() + "]";
    }

}
