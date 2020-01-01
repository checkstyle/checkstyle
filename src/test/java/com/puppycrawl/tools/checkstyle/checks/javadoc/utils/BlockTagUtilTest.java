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

package com.puppycrawl.tools.checkstyle.checks.javadoc.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class BlockTagUtilTest {

    @Test
    public void testHasPrivateConstructor() throws Exception {
        assertTrue(TestUtil.isUtilsClassHasPrivateConstructor(BlockTagUtil.class, true),
                "Constructor is not private");
    }

    @Test
    public void testExtractBlockTags() {
        final String[] text = {
            "/** @foo abc ",
            " * @bar def  ",
            "   @baz ghi  ",
            " * @qux jkl",
            " */",
        };

        final List<TagInfo> tags = BlockTagUtil.extractBlockTags(text);
        assertEquals(4, tags.size(), "Invalid tags size");

        final TagInfo tag1 = tags.get(0);
        assertTagEquals(tag1, "foo", "abc", 1, 4);

        final TagInfo tag2 = tags.get(1);
        assertTagEquals(tag2, "bar", "def", 2, 3);

        final TagInfo tag3 = tags.get(2);
        assertTagEquals(tag3, "baz", "ghi", 3, 3);

        final TagInfo tag4 = tags.get(3);
        assertTagEquals(tag4, "qux", "jkl", 4, 3);
    }

    @Test
    public void testVersionStringFormat() {
        final String[] text = {
            "/** ",
            " * @version 1.0",
            " */",
        };
        final List<TagInfo> tags = BlockTagUtil.extractBlockTags(text);
        assertEquals(1, tags.size(), "Invalid tags size");
        assertEquals("version", tags.get(0).getName(), "Invalid tag name");
        assertEquals("1.0", tags.get(0).getValue(), "Invalid tag value");
    }

    @Test
    public void testOddVersionString() {
        final String[] text = {
            "/**",
            " * Foo",
            " * @version 1.0 */"};

        final List<TagInfo> tags = BlockTagUtil.extractBlockTags(text);
        assertEquals(1, tags.size(), "Invalid tags size");
        assertEquals("version", tags.get(0).getName(), "Invalid tag name");
        assertEquals("1.0", tags.get(0).getValue(), "Invalid tag value");
    }

    private static void assertTagEquals(TagInfo tag, String name, String value,
            int line, int column) {
        assertEquals(name, tag.getName(), "Invalid tag name");
        assertEquals(value, tag.getValue(), "Invalid tag value");
        assertEquals(line, tag.getPosition().getLine(), "Invalid tag line");
        assertEquals(column, tag.getPosition().getColumn(), "Invalid tag column");
    }

}
