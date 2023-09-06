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

/**
 * A block of text from an input file that does not necessarily
 * have any grammatical structure.
 *
 */
public interface TextBlock {

    /**
     * The text content of the text block.
     * Each line is represented by one array entry.
     * The linebreak characters are not part of the text content.
     *
     * @return the text content of the text block.
     */
    String[] getText();

    /**
     * The line in the input file where the text block starts.
     * Counting starts from 1.
     *
     * @return first line of the text block
     */
    int getStartLineNo();

    /**
     * The last line of the text block in the input file.
     * Counting starts from 1.
     *
     * @return last line of the text block
     */
    int getEndLineNo();

    /**
     * The column in the input file where the text block starts.
     * Counting starts from 0.
     *
     * @return first line of the text block
     */
    int getStartColNo();

    /**
     * The column in the input file where the text block ends.
     * Counting starts from 0.
     *
     * @return last line of the text block
     */
    int getEndColNo();

    /**
     * Checks if this comment intersects with a specified
     * part of the file.
     *
     * @param startLineNo the starting line number in the file
     * @param startColNo the starting column number in the file
     * @param endLineNo the ending line number in the file
     * @param endColNo the ending column number in the file
     * @return true if the positions intersects with this comment.
     */
    boolean intersects(int startLineNo, int startColNo,
                       int endLineNo, int endColNo);

}
