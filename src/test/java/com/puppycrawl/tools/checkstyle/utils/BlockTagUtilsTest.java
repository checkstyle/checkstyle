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

package com.puppycrawl.tools.checkstyle.utils;

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
        TestUtils.assertUtilsClassHasPrivateConstructor(BlockTagUtils.class);
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
        assertEquals(4, tags.size());

        final TagInfo tag1 = tags.get(0);
        assertEquals("foo", tag1.getName());
        assertEquals("abc", tag1.getValue());
        assertEquals(1, tag1.getPosition().getLine());
        assertEquals(4, tag1.getPosition().getColumn());

        final TagInfo tag2 = tags.get(1);
        assertEquals("bar", tag2.getName());
        assertEquals("def", tag2.getValue());
        assertEquals(2, tag2.getPosition().getLine());
        assertEquals(3, tag2.getPosition().getColumn());

        final TagInfo tag3 = tags.get(2);
        assertEquals("baz", tag3.getName());
        assertEquals("ghi", tag3.getValue());
        assertEquals(3, tag3.getPosition().getLine());
        assertEquals(3, tag3.getPosition().getColumn());

        final TagInfo tag4 = tags.get(3);
        assertEquals("qux", tag4.getName());
        assertEquals("jkl", tag4.getValue());
        assertEquals(4, tag4.getPosition().getLine());
        assertEquals(3, tag4.getPosition().getColumn());
    }

    @Test
    public void testVersionStringFormat() {
        final String[] text = {
            "/** ",
            " * @version 1.0",
            " */",
        };
        final List<TagInfo> tags = BlockTagUtils.extractBlockTags(text);
        assertEquals(1, tags.size());
        assertEquals("version", tags.get(0).getName());
        assertEquals("1.0", tags.get(0).getValue());
    }

    @Test
    public void testOddVersionString() {
        final String[] text = {
            "/**",
            " * Foo",
            " * @version 1.0 */"};

        final List<TagInfo> tags = BlockTagUtils.extractBlockTags(text);
        assertEquals(1, tags.size());
        assertEquals("version", tags.get(0).getName());
        assertEquals("1.0", tags.get(0).getValue());
    }
}
