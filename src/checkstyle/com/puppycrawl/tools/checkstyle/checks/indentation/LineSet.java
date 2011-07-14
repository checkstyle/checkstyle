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
package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.google.common.collect.Maps;
import java.util.SortedMap;

/**
 * Represents a set of lines.
 *
 * @author jrichard
 */
public class LineSet
{
    /**
     * Maps line numbers to their start column.
     */
    private final SortedMap<Integer, Integer> mLines = Maps.newTreeMap();

    /**
     * Get the starting column for a given line number.
     *
     * @param aLineNum   the specified line number
     *
     * @return the starting column for the given line number
     */
    public Integer getStartColumn(Integer aLineNum)
    {
        return mLines.get(aLineNum);
    }

    /**
     * Get the starting column for the first line.
     *
     * @return the starting column for the first line.
     */
    public int firstLineCol()
    {
        final Object firstLineKey = mLines.firstKey();
        return (mLines.get(firstLineKey)).intValue();
    }

    /**
     * Get the line number of the first line.
     *
     * @return the line number of the first line
     */
    public int firstLine()
    {
        return (mLines.firstKey()).intValue();
    }

    /**
     * Get the line number of the last line.
     *
     * @return the line number of the last line
     */
    public int lastLine()
    {
        return (mLines.lastKey()).intValue();
    }

    /**
     * Add a line to this set of lines.
     *
     * @param aLineNum   the line to add
     * @param aCol       the starting column of the new line
     */
    public void addLineAndCol(int aLineNum, int aCol)
    {
        mLines.put(aLineNum, aCol);
    }

    /**
     * Determines if this set of lines is empty.
     *
     * @return true if it is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return mLines.isEmpty();
    }

    @Override
    public String toString()
    {
        return "LineSet[ start=" + firstLine() + ", last=" + lastLine() + "]";
    }
}
