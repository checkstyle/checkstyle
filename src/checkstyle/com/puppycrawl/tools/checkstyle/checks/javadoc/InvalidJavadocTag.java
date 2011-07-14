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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

/**
 * Value object for storing data about an invalid Javadoc validTags.
 * @author Oliver Burn
 */
public final class InvalidJavadocTag
{
    /** The line in which the invalid tag occurs. */
    private final int mLine;
    /** The column in which the invalid tag occurs. */
    private final int mCol;
    /** The name of the invalid tag. */
    private final String mName;

    /**
     * Creates an instance.
     * @param aLine the line of the tag
     * @param aCol the column of the tag
     * @param aName the name of the invalid tag
     */
    public InvalidJavadocTag(int aLine, int aCol, String aName)
    {
        mLine = aLine;
        mCol = aCol;
        mName = aName;
    }

    public int getLine()
    {
        return mLine;
    }

    public int getCol()
    {
        return mCol;
    }

    public String getName()
    {
        return mName;
    }
}
