////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

/**
 * Immutable line and column numbers.
 *
 * @author Martin von Gagern
 */
public class LineColumn implements Comparable<LineColumn>
{

    /** The one-based line number */
    private final int mLine;

    /** The zero-based column number */
    private final int mCol;

    /**
     * Constructs a new pair of line and column numbers.
     * @param aLine the one-based line number
     * @param aCol the zero-based column number
     */
    public LineColumn(int aLine, int aCol)
    {
        mLine = aLine;
        mCol = aCol;
    }

    /** @return the one-based line number */
    public int getLine()
    {
        return mLine;
    }

    /** @return the zero-based column number */
    public int getColumn()
    {
        return mCol;
    }

    /** {@inheritDoc} */
    public int compareTo(LineColumn aLineColumn)
    {
        return (this.getLine() != aLineColumn.getLine())
            ? this.getLine() - aLineColumn.getLine()
            : this.getColumn() - aLineColumn.getColumn();
    }
}
