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

package com.puppycrawl.tools.checkstyle.checks.javadoc.utils;

import static com.google.common.truth.Truth.assertWithMessage;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class BlockTagUtilTest {

    @Test
    public void testHasPrivateConstructor() throws Exception {
        assertWithMessage("Constructor is not private")
                .that(TestUtil.isUtilsClassHasPrivateConstructor(BlockTagUtil.class))
                .isTrue();
    }

    @Test
    public void testExtractBlockTags() {
        final String[] text = {
            "/** @foo abc ",
            " * @bar def  ",
            "   @baz ghi  ",
            " * @qux jkl",
            " * @mytag",
            " */",
        };

        final List<TagInfo> tags = BlockTagUtil.extractBlockTags(text);
        assertWithMessage("Invalid tags size")
            .that(tags)
            .hasSize(5);

        final TagInfo tag1 = tags.get(0);
        assertTagEquals(tag1, "foo", "abc", 1, 4);

        final TagInfo tag2 = tags.get(1);
        assertTagEquals(tag2, "bar", "def", 2, 3);

        final TagInfo tag3 = tags.get(2);
        assertTagEquals(tag3, "baz", "ghi", 3, 3);

        final TagInfo tag4 = tags.get(3);
        assertTagEquals(tag4, "qux", "jkl", 4, 3);

        final TagInfo tag5 = tags.get(4);
        assertTagEquals(tag5, "mytag", "", 5, 3);
    }

    @Test
    public void testExtractBlockTagFirstLine() {
        final String[] text = {
            "/** @foo",
            " */",
        };

        final List<TagInfo> tags = BlockTagUtil.extractBlockTags(text);
        assertWithMessage("Invalid tags size")
            .that(tags)
            .hasSize(1);

        final TagInfo tag1 = tags.get(0);
        assertTagEquals(tag1, "foo", "", 1, 4);
    }

    @Test
    public void testVersionStringFormat() {
        final String[] text = {
            "/** ",
            " * @version 1.0",
            " */",
        };
        final List<TagInfo> tags = BlockTagUtil.extractBlockTags(text);
        assertWithMessage("Invalid tags size")
            .that(tags)
            .hasSize(1);
        assertWithMessage("Invalid tag name")
            .that(tags.get(0).getName())
            .isEqualTo("version");
        assertWithMessage("Invalid tag value")
            .that(tags.get(0).getValue())
            .isEqualTo("1.0");
    }

    @Test
    public void testOddVersionString() {
        final String[] text = {
            "/**",
            " * Foo",
            " * @version 1.0 */"};

        final List<TagInfo> tags = BlockTagUtil.extractBlockTags(text);
        assertWithMessage("Invalid tags size")
            .that(tags)
            .hasSize(1);
        assertWithMessage("Invalid tag name")
            .that(tags.get(0).getName())
            .isEqualTo("version");
        assertWithMessage("Invalid tag value")
            .that(tags.get(0).getValue())
            .isEqualTo("1.0");
    }

    private static void assertTagEquals(TagInfo tag, String name, String value,
            int line, int column) {
        assertWithMessage("Invalid tag name")
            .that(tag.getName())
            .isEqualTo(name);
        assertWithMessage("Invalid tag value")
            .that(tag.getValue())
            .isEqualTo(value);
        assertWithMessage("Invalid tag line")
            .that(tag.getPosition().getLine())
            .isEqualTo(line);
        assertWithMessage("Invalid tag column")
            .that(tag.getPosition().getColumn())
            .isEqualTo(column);
    }

}
