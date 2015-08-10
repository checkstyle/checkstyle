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

/**
 * Representation of the comment block.
 *
 * @author o_sukhodolsky
 */
public class Comment implements TextBlock {
    /** text of the comment. */
    private final String[] text;

    /** number of first line of the comment. */
    private final int firstLine;

    /** number of last line of the comment. */
    private final int lastLine;

    /** number of first column of the comment. */
    private final int firstCol;

    /** number of last column of the comment. */
    private final int lastCol;

    /**
     * Creates new instance.
     * @param text the lines that make up the comment.
     * @param firstCol number of the first column of the comment.
     * @param lastLine number of the last line of the comment.
     * @param lastCol number of the last column of the comment.
     */
    public Comment(final String[] text, final int firstCol,
            final int lastLine, final int lastCol) {
        this.text = new String[text.length];
        System.arraycopy(text, 0, this.text, 0, this.text.length);
        firstLine = lastLine - this.text.length + 1;
        this.lastLine = lastLine;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
    }

    @Override
    public final String[] getText() {
        return text.clone();
    }

    @Override
    public final int getStartLineNo() {
        return firstLine;
    }

    @Override
    public final int getEndLineNo() {
        return lastLine;
    }

    @Override
    public int getStartColNo() {
        return firstCol;
    }

    @Override
    public int getEndColNo() {
        return lastCol;
    }

    @Override
    public boolean intersects(int startLineNo, int startColNo,
                              int endLineNo, int endColNo) {
        // compute a single number for start and end
        // to simplify conditional logic
        final long multiplier = Integer.MAX_VALUE;
        final long thisStart = firstLine * multiplier + firstCol;
        final long thisEnd = lastLine * multiplier + lastCol;
        final long inStart = startLineNo * multiplier + startColNo;
        final long inEnd = endLineNo * multiplier + endColNo;

        return !(thisEnd < inStart || inEnd < thisStart);
    }

    @Override
    public String toString() {
        return "Comment[" + firstLine + ":" + firstCol + "-"
            + lastLine + ":" + lastCol + "]";
    }
}
