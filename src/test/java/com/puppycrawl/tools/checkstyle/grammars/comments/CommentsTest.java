////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammars.comments;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Comment;

public class CommentsTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("grammars" + File.separator
                + "comments" + File.separator + filename);
    }

    @Test
    public void testCompareExpectedTreeWithInput1() throws Exception {
        verifyAst(getPath("InputComments1Ast.txt"), getPath("InputComments1.java"), true);
    }

    @Test
    public void testCompareExpectedTreeWithInput2() throws Exception {
        verifyAst(getPath("InputComments2Ast.txt"), getPath("InputComments2.java"), true);
    }

    @Test
    public void testToString() {
        final Comment comment = new Comment(new String[] {"value"}, 1, 2, 3);
        Assert.assertEquals("Comment[2:1-2:3]", comment.toString());
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

        Assert.assertEquals(43, comment.getStartLineNo());
        Assert.assertEquals(5, comment.getStartColNo());
        Assert.assertEquals(49, comment.getEndLineNo());
        Assert.assertEquals(66, comment.getEndColNo());
    }

    @Test
    public void testIntersects() {
        final String[] commentText = {"// compute a single number for start and end",
            "// to simplify conditional logic"};
        final Comment comment = new Comment(commentText, 9, 89, 53);

        Assert.assertTrue(comment.intersects(89, 9, 89, 41));
        Assert.assertTrue(comment.intersects(89, 53, 90, 50));
        Assert.assertTrue(comment.intersects(87, 7, 88, 9));
        Assert.assertFalse(comment.intersects(90, 7, 91, 20));
        Assert.assertFalse(comment.intersects(89, 56, 89, 80));
    }
}
