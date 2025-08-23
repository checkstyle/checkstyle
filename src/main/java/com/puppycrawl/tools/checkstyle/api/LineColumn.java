///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.api;

/**
 * Immutable line and column numbers.
 *
 * @param line   The one-based line number.
 * @param column The zero-based column number.
 */
public record LineColumn(int line, int column) implements
        Comparable<LineColumn> {

    /**
     * Constructs a new pair of line and column numbers.
     *
     * @param line   the one-based line number
     * @param column the zero-based column number
     */
    public LineColumn {
    }

    /**
     * Gets the one-based line number.
     *
     * @return the one-based line number
     */
    @Override
    public int line() {
        return line;
    }

    /**
     * Gets the zero-based column number.
     *
     * @return the zero-based column number
     */
    @Override
    public int column() {
        return column;
    }

    @Override
    public int compareTo(LineColumn lineColumn) {
        final int result;
        if (line == lineColumn.line) {
            result = Integer.compare(column, lineColumn.column);
        }
        else {
            result = Integer.compare(line, lineColumn.line);
        }
        return result;
    }

}
