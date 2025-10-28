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

import java.util.Arrays;
import java.util.Objects;

/**
 * Representation of the comment block.
 *
 * <p>This class stores the text and positional metadata of a comment block
 * found in source code. It implements {@link TextBlock} to provide information
 * about where the comment starts and ends, both in terms of lines and columns.
 */
public class Comment implements TextBlock {

    /** Text of the comment. */
    private final String[] text;

    /** Number of first line of the comment. */
    private final int startLineNo;

    /** Number of last line of the comment. */
    private final int endLineNo;

    /** Number of first column of the comment. */
    private final int startColNo;

    /** Number of last column of the comment. */
    private final int endColNo;

    /**
     * Creates new instance of {@code Comment}.
     *
     * @param text      the lines that make up the comment (must not be null)
     * @param firstCol  number of the first column of the comment
     * @param lastLine  number of the last line of the comment
     * @param lastCol   number of the last column of the comment
     * @throws IllegalArgumentException if text is null or empty
     */
    public Comment(final String[] text, final int firstCol,
                   final int lastLine, final int lastCol) {
        // ✅ Enhancement 1: Basic validation
        if (text == null || text.length == 0) {
            throw new IllegalArgumentException("Comment text must not be null or empty");
        }

        this.text = text.clone();
        startLineNo = lastLine - text.length + 1;
        endLineNo = lastLine;
        startColNo = firstCol;
        endColNo = lastCol;
    }

    @Override
    public final String[] getText() {
        return text.clone();
    }

    @Override
    public final int getStartLineNo() {
        return startLineNo;
    }

    @Override
    public final int getEndLineNo() {
        return endLineNo;
    }

    @Override
    public int getStartColNo() {
        return startColNo;
    }

    @Override
    public int getEndColNo() {
        return endColNo;
    }

    /**
     * Checks whether this comment block intersects with a given range of lines and columns.
     *
     * @param startLine start line of the range
     * @param startCol  start column of the range
     * @param endLine   end line of the range
     * @param endCol    end column of the range
     * @return true if the ranges overlap; false otherwise
     */
    @Override
    public boolean intersects(int startLine, int startCol,
                              int endLine, int endCol) {
        final long multiplier = Integer.MAX_VALUE;
        final long thisStart = startLineNo * multiplier + startColNo;
        final long thisEnd = endLineNo * multiplier + endColNo;
        final long inStart = startLine * multiplier + startCol;
        final long inEnd = endLine * multiplier + endCol;

        return thisEnd >= inStart && inEnd >= thisStart;
    }

    @Override
    public String toString() {
        return "Comment[text=" + Arrays.toString(text)
                + ", startLineNo=" + startLineNo
                + ", endLineNo=" + endLineNo
                + ", startColNo=" + startColNo
                + ", endColNo=" + endColNo + ']';
    }
}
