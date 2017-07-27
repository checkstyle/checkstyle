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

package com.puppycrawl.tools.checkstyle.checks.javadoc.utils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.internal.TestUtils;

/**
 * Tests BlockTagUtils.
 */
public class BlockTagUtilsTest {

    @Test
    public void testHasPrivateConstructor() throws Exception {
        TestUtils.assertUtilsClassHasPrivateConstructor(BlockTagUtils.class, true);
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

        final List<TagInfo> tags = BlockTagUtils.extractBlockTags(text);
        assertEquals("Invalid tags size", 4, tags.size());

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
        final List<TagInfo> tags = BlockTagUtils.extractBlockTags(text);
        assertEquals("Invalid tags size", 1, tags.size());
        assertEquals("Invalid tag name", "version", tags.get(0).getName());
        assertEquals("Invalid tag value", "1.0", tags.get(0).getValue());
    }

    @Test
    public void testOddVersionString() {
        final String[] text = {
            "/**",
            " * Foo",
            " * @version 1.0 */"};

        final List<TagInfo> tags = BlockTagUtils.extractBlockTags(text);
        assertEquals("Invalid tags size", 1, tags.size());
        assertEquals("Invalid tag name", "version", tags.get(0).getName());
        assertEquals("Invalid tag value", "1.0", tags.get(0).getValue());
    }

    private static void assertTagEquals(TagInfo tag, String name, String value,
            int line, int column) {
        assertEquals("Invalid tag name", name, tag.getName());
        assertEquals("Invalid tag value", value, tag.getValue());
        assertEquals("Invalid tag line", line, tag.getPosition().getLine());
        assertEquals("Invalid tag column", column, tag.getPosition().getColumn());
    }
}
