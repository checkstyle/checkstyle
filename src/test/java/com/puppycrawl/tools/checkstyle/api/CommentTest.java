///
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
///

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

public class CommentTest {

    @Test
    public void test() {
        final String[] text = {"test"};
        final Comment comment = new Comment(text, 1, 2, 3);

        assertWithMessage("invalid text")
                .that(comment.getText())
                .isEqualTo(text);
        assertWithMessage("invalid start line")
                .that(comment.getStartLineNo())
                .isEqualTo(2);
        assertWithMessage("invalid end line")
                .that(comment.getEndLineNo())
                .isEqualTo(2);
        assertWithMessage("invalid start column")
                .that(comment.getStartColNo())
                .isEqualTo(1);
        assertWithMessage("invalid end column")
                .that(comment.getEndColNo())
                .isEqualTo(3);
        assertWithMessage("invalid string")
                .that(comment.toString())
                .isEqualTo("Comment[text=[test], startLineNo=2,"
                        + " endLineNo=2, startColNo=1, endColNo=3]");
    }

    @Test
    public void testIntersects() {
        final String[] text = {"test", "test"};
        final Comment comment = new Comment(text, 2, 4, 4);

        final String message = "invalid";
        assertWithMessage(message)
                .that(comment.intersects(1, 1, 1, 1))
                .isFalse();
        assertWithMessage(message)
                .that(comment.intersects(5, 5, 5, 5))
                .isFalse();
        assertWithMessage(message)
                .that(comment.intersects(1, 1, 5, 5))
                .isTrue();
        assertWithMessage(message)
                .that(comment.intersects(1, 1, 3, 5))
                .isTrue();
        assertWithMessage(message)
                .that(comment.intersects(3, 5, 5, 5))
                .isTrue();
    }

    @Test
    public void testIntersects2() {
        final String[] text = {"a"};
        final Comment comment = new Comment(text, 2, 2, 2);

        assertWithMessage("invalid")
                .that(comment.intersects(2, 2, 2, 2))
                .isTrue();
    }

    @Test
    public void testIntersects3() {
        final String[] text = {"test"};
        final Comment comment = new Comment(text, 1, 1, 2);

        assertWithMessage("invalid")
                .that(comment.intersects(1, Integer.MAX_VALUE, 1, 2))
                .isFalse();
    }
}
