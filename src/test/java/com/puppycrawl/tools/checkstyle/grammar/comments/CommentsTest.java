////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammar.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.Comment;

public class CommentsTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/comments";
    }

    @Test
    public void testCompareExpectedTreeWithInput1() throws Exception {
        verifyAst(getPath("InputComments1Ast.txt"), getPath("InputComments1.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testCompareExpectedTreeWithInput2() throws Exception {
        verifyAst(getPath("InputComments2Ast.txt"), getPath("InputComments2.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testToString() {
        final Comment comment = new Comment(new String[] {"value"}, 1, 2, 3);
        assertEquals(
                "Comment[text=[value], startLineNo=2, endLineNo=2, startColNo=1, endColNo=3]",
                comment.toString(), "Invalid toString result");
    }

    @Test
    public void testGetCommentMeasures() {
        final String[] commentText = {"/**",
            "     * Creates new instance.",
            "     * @param text the lines that make up the comment.",
            "     * @param firstCol number of the first column of the comment.",
            "     * @param lastLine number of the last line of the comment.",
            "     * @param lastCol number of the last column of the comment.",
            "     */"};
        final Comment comment = new Comment(commentText, 5, 49, 66);

        assertEquals(43, comment.getStartLineNo(), "Invalid comment start line number");
        assertEquals(5, comment.getStartColNo(), "Invalid comment start column number");
        assertEquals(49, comment.getEndLineNo(), "Invalid comment end line number");
        assertEquals(66, comment.getEndColNo(), "Invalid comment end column number");
    }

    @Test
    public void testIntersects() {
        final String[] commentText = {"// compute a single number for start and end",
            "// to simplify conditional logic"};
        final Comment comment = new Comment(commentText, 9, 89, 53);

        assertTrue(comment.intersects(89, 9, 89, 41), "Invalid intersection result");
        assertTrue(comment.intersects(89, 53, 90, 50), "Invalid intersection result");
        assertTrue(comment.intersects(87, 7, 88, 9), "Invalid intersection result");
        assertFalse(comment.intersects(90, 7, 91, 20), "Invalid intersection result");
        assertFalse(comment.intersects(89, 56, 89, 80), "Invalid intersection result");
    }

}
