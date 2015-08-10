////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.util.Objects;

/**
 * Immutable line and column numbers.
 *
 * @author Martin von Gagern
 */
public class LineColumn implements Comparable<LineColumn> {

    /** The one-based line number */
    private final int line;

    /** The zero-based column number */
    private final int col;

    /**
     * Constructs a new pair of line and column numbers.
     * @param line the one-based line number
     * @param col the zero-based column number
     */
    public LineColumn(int line, int col) {
        this.line = line;
        this.col = col;
    }

    /** @return the one-based line number */
    public int getLine() {
        return line;
    }

    /** @return the zero-based column number */
    public int getColumn() {
        return col;
    }

    @Override
    public int compareTo(LineColumn lineColumn) {
        return getLine() != lineColumn.getLine()
            ? Integer.compare(getLine(), lineColumn.getLine())
            : Integer.compare(getColumn(), lineColumn.getColumn());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LineColumn lineColumn = (LineColumn) o;
        return Objects.equals(line, lineColumn.line)
                && Objects.equals(col, lineColumn.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, col);
    }
}
