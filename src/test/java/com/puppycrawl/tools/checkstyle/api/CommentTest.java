////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommentTest {

    @Test
    public void test() {
        final String[] text = {"test"};
        final Comment comment = new Comment(text, 1, 2, 3);

        assertArrayEquals("invalid text", text, comment.getText());
        assertEquals("invalid start line", 2, comment.getStartLineNo());
        assertEquals("invalid end line", 2, comment.getEndLineNo());
        assertEquals("invalid start column", 1, comment.getStartColNo());
        assertEquals("invalid end column", 3, comment.getEndColNo());
        assertEquals("invalid string",
                "Comment[text=[test], startLineNo=2, endLineNo=2, startColNo=1, endColNo=3]",
                comment.toString());
    }

    @Test
    public void testIntersects() {
        final String[] text = {"test", "test"};
        final Comment comment = new Comment(text, 2, 4, 4);

        assertFalse("invalid", comment.intersects(1, 1, 1, 1));
        assertFalse("invalid", comment.intersects(5, 5, 5, 5));
        assertTrue("invalid", comment.intersects(1, 1, 5, 5));
        assertTrue("invalid", comment.intersects(1, 1, 3, 5));
        assertTrue("invalid", comment.intersects(3, 5, 5, 5));
    }

    @Test
    public void testIntersects2() {
        final String[] text = {"a"};
        final Comment comment = new Comment(text, 2, 2, 2);

        assertTrue("invalid", comment.intersects(2, 2, 2, 2));
    }

    @Test
    public void testIntersects3() {
        final String[] text = {"test"};
        final Comment comment = new Comment(text, 1, 1, 2);

        assertFalse("invalid", comment.intersects(1, Integer.MAX_VALUE, 1, 2));
    }
}
