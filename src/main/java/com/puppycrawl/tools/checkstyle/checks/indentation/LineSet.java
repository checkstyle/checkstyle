////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.util.SortedMap;

import com.google.common.collect.Maps;

/**
 * Represents a set of lines.
 *
 * @author jrichard
 */
public class LineSet {
    /**
     * Maps line numbers to their start column.
     */
    private final SortedMap<Integer, Integer> lines = Maps.newTreeMap();

    /**
     * Get the starting column for a given line number.
     *
     * @param lineNum   the specified line number
     *
     * @return the starting column for the given line number
     */
    public Integer getStartColumn(Integer lineNum) {
        return lines.get(lineNum);
    }

    /**
     * Get the starting column for the first line.
     *
     * @return the starting column for the first line.
     */
    public int firstLineCol() {
        final Integer firstLineKey = lines.firstKey();
        return lines.get(firstLineKey);
    }

    /**
     * Get the line number of the first line.
     *
     * @return the line number of the first line
     */
    public int firstLine() {
        return lines.firstKey();
    }

    /**
     * Get the line number of the last line.
     *
     * @return the line number of the last line
     */
    public int lastLine() {
        return lines.lastKey();
    }

    /**
     * Add a line to this set of lines.
     *
     * @param lineNum   the line to add
     * @param col       the starting column of the new line
     */
    public void addLineAndCol(int lineNum, int col) {
        lines.put(lineNum, col);
    }

    /**
     * Determines if this set of lines is empty.
     *
     * @return true if it is empty, false otherwise
     */
    public boolean isEmpty() {
        return lines.isEmpty();
    }

    @Override
    public String toString() {
        return "LineSet[firstLine=" + firstLine() + ", lastLine=" + lastLine() + "]";
    }
}
