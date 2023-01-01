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

package com.puppycrawl.tools.checkstyle.checks.javadoc.utils;

import static com.google.common.truth.Truth.assertWithMessage;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class InlineTagUtilTest {

    @Test
    public void testHasPrivateConstructor() throws Exception {
        assertWithMessage("Constructor is not private")
                .that(TestUtil.isUtilsClassHasPrivateConstructor(InlineTagUtil.class))
                .isTrue();
    }

    @Test
    public void testExtractInlineTags() {
        final String[] text = {
            "/** @see elsewhere ",
            " * {@link List }, {@link List link text }",
            "   {@link List#add(Object) link text}",
            " * {@link Class link text}",
            " */"};
        final List<TagInfo> tags = InlineTagUtil.extractInlineTags(text);

        assertWithMessage("Unexpected tags size")
            .that(tags)
            .hasSize(4);

        assertTag(tags.get(0), "link", "List", 2, 4);
        assertTag(tags.get(1), "link", "List link text", 2, 19);
        assertTag(tags.get(2), "link", "List#add(Object) link text", 3, 4);
        assertTag(tags.get(3), "link", "Class link text", 4, 4);
    }

    @Test
    public void testMultiLineLinkTag() {
        final String[] text = {
            "/**",
            " * {@link foo",
            " *        bar baz}",
            " */"};

        final List<TagInfo> tags = InlineTagUtil.extractInlineTags(text);

        assertWithMessage("Unexpected tags size")
            .that(tags)
            .hasSize(1);
        assertTag(tags.get(0), "link", "foo bar baz", 2, 4);
    }

    @Test
    public void testCollapseWhitespace() {
        final String[] text = {
            "/**",
            " * {@code     foo\t\t   bar   baz\t    }",
            " */"};

        final List<TagInfo> tags = InlineTagUtil.extractInlineTags(text);

        assertWithMessage("Unexpected tags size")
            .that(tags)
            .hasSize(1);
        assertTag(tags.get(0), "code", "foo bar baz", 2, 4);
    }

    @Test
    public void extractInlineTags() {
        final String[] source = {
            "  {@link foo}",
        };

        final List<TagInfo> tags = InlineTagUtil.extractInlineTags(source);

        assertWithMessage("Unexpected tags size")
            .that(tags)
            .hasSize(1);

        final TagInfo tag = tags.get(0);
        assertTag(tag, "link", "foo", 1, 3);
    }

    @Test
    public void testBadInputExtractInlineTagsLineFeed() {
        try {
            InlineTagUtil.extractInlineTags("abc\ndef");
            assertWithMessage("IllegalArgumentException expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Unexpected error message")
                    .that(ex.getMessage().contains("newline"))
                    .isTrue();
        }
    }

    @Test
    public void testBadInputExtractInlineTagsCarriageReturn() {
        try {
            InlineTagUtil.extractInlineTags("abc\rdef");
            assertWithMessage("IllegalArgumentException expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid error message")
                    .that(ex.getMessage().contains("newline"))
                    .isTrue();
        }
    }

    private static void assertTag(TagInfo tag, String name, String value, int line, int col) {
        assertWithMessage("Unexpected tags name")
            .that(tag.getName())
            .isEqualTo(name);
        assertWithMessage("Unexpected tags value")
            .that(tag.getValue())
            .isEqualTo(value);
        assertWithMessage("Unexpected tags position")
            .that(tag.getPosition().getLine())
            .isEqualTo(line);
        assertWithMessage("Unexpected tags position")
            .that(tag.getPosition().getColumn())
            .isEqualTo(col);
    }

}
