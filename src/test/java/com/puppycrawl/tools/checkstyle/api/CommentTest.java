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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommentTest {

    @Test
    public void test() {
        final String[] text = {"test"};
        final Comment comment = new Comment(text, 1, 2, 3);

        assertArrayEquals(text, comment.getText(), "invalid text");
        assertEquals(2, comment.getStartLineNo(), "invalid start line");
        assertEquals(2, comment.getEndLineNo(), "invalid end line");
        assertEquals(1, comment.getStartColNo(), "invalid start column");
        assertEquals(3, comment.getEndColNo(), "invalid end column");
        assertEquals(
                "Comment[text=[test], startLineNo=2, endLineNo=2, startColNo=1, endColNo=3]",
                comment.toString(), "invalid string");
    }

    @Test
    public void testIntersects() {
        final String[] text = {"test", "test"};
        final Comment comment = new Comment(text, 2, 4, 4);

        assertFalse(comment.intersects(1, 1, 1, 1), "invalid");
        assertFalse(comment.intersects(5, 5, 5, 5), "invalid");
        assertTrue(comment.intersects(1, 1, 5, 5), "invalid");
        assertTrue(comment.intersects(1, 1, 3, 5), "invalid");
        assertTrue(comment.intersects(3, 5, 5, 5), "invalid");
    }

    @Test
    public void testIntersects2() {
        final String[] text = {"a"};
        final Comment comment = new Comment(text, 2, 2, 2);

        assertTrue(comment.intersects(2, 2, 2, 2), "invalid");
    }

    @Test
    public void testIntersects3() {
        final String[] text = {"test"};
        final Comment comment = new Comment(text, 1, 1, 2);

        assertFalse(comment.intersects(1, Integer.MAX_VALUE, 1, 2), "invalid");
    }
}
