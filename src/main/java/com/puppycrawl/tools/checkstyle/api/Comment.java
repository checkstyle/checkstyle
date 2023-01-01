///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

/**
 * Representation of the comment block.
 *
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
     * Creates new instance.
     *
     * @param text the lines that make up the comment.
     * @param firstCol number of the first column of the comment.
     * @param lastLine number of the last line of the comment.
     * @param lastCol number of the last column of the comment.
     */
    public Comment(final String[] text, final int firstCol,
            final int lastLine, final int lastCol) {
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

    @Override
    public boolean intersects(int startLine, int startCol,
                              int endLine, int endCol) {
        // compute a single number for start and end
        // to simplify conditional logic
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
