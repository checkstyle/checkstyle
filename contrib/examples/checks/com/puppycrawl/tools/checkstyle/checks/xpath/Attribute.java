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
package com.puppycrawl.tools.checkstyle.checks.xpath;

import com.puppycrawl.tools.checkstyle.api.DetailAST;


/**
 * Data holder for an XPath attribute of an element. The parent
 * element of an Attribute is a DetailAST. The name and value
 * of an Attribute are Strings.
 * @author Rick Giles
 */
public class Attribute
{
    /** element owning this attribute */
    private DetailAST mParent;

    /** name */
    private String mName;

    /** value */
    private String mValue;

    /**
     * Constructs an <code>Attribute</code>.
     * @param aParent the parent element.
     * @param aName the name.
     * @param aValue the value.
     */
    public Attribute(DetailAST aParent, String aName, String aValue)
    {
        mParent = aParent;
        mName = aName;
        mValue = aValue;
    }

    /** Returns the name of the attribute.
     * @return the name of the attribute.
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Returns the value of the attribute.
     * @return the value of the attribute.
     */
    public String getValue()
    {
        return mValue;
    }

    /**
     * Sets the name of the attribute.
     * @param aName The name to set.
     */
    public void setName(String aName)
    {
        mName = aName;
    }

    /**
     * Sets the value of the attribute.
     * @param aValue The value to set.
     */
    public void setValue(String aValue)
    {
        mValue = aValue;
    }

    /**
     * Returns the parent of the attribute.
     * @return the parent of the attribute.
     */
    public DetailAST getParent()
    {
        return mParent;
    }

    /**
     * Sets the parent of the attribute.
     * @param aParent the parent of the attribute.
     */
    public void setParent(DetailAST aParent)
    {
        mParent = aParent;
    }

}
