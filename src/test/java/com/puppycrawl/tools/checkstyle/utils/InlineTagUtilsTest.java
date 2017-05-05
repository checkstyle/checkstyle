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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.LineColumn;
import com.puppycrawl.tools.checkstyle.internal.TestUtils;

/**
 * Tests InlineTagUtils
 */
public class InlineTagUtilsTest {

    @Test
    public void testHasPrivateConstructor() throws Exception {
        TestUtils.assertUtilsClassHasPrivateConstructor(InlineTagUtils.class);
    }

    @Test
    public void testExtractInlineTags() {
        final String[] text = {
            "/** @see elsewhere ",
            " * {@link List }, {@link List link text }",
            "   {@link List#add(Object) link text}",
            " * {@link Class link text}",
            " */"};
        final List<TagInfo> tags = InlineTagUtils.extractInlineTags(text);

        assertEquals(4, tags.size());

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

        final List<TagInfo> tags = InlineTagUtils.extractInlineTags(text);

        assertEquals(1, tags.size());
        assertTag(tags.get(0), "link", "foo bar baz", 2, 4);
    }

    private static void assertTag(TagInfo tag, String name, String value, int line, int col) {
        assertEquals(name, tag.getName());
        assertEquals(value, tag.getValue());
        assertEquals(line, tag.getPosition().getLine());
        assertEquals(col, tag.getPosition().getColumn());
    }

    @Test
    public void testCollapseWhitespace() {
        assertEquals("a b", InlineTagUtils.collapseWhitespace("  a  b  "));
        assertEquals("", InlineTagUtils.collapseWhitespace("    "));
        assertEquals("a b c", InlineTagUtils.collapseWhitespace("a\nb\nc\n"));
    }

    @Test
    public void testGetLineColumnOfIndex() {
        final String source = "abc\n" + "def\n" + "hij\n";

        final LineColumn lineColumn1 =
            InlineTagUtils.getLineColumnOfIndex(source, source.indexOf('a'));
        assertEquals(1, lineColumn1.getLine());
        assertEquals(0, lineColumn1.getColumn());

        final LineColumn lineColumn2 =
            InlineTagUtils.getLineColumnOfIndex(source, source.indexOf('e'));
        assertEquals(2, lineColumn2.getLine());
        assertEquals(1, lineColumn2.getColumn());

        final LineColumn lineColumn3 =
            InlineTagUtils.getLineColumnOfIndex(source, source.indexOf('j'));
        assertEquals(3, lineColumn3.getLine());
        assertEquals(2, lineColumn3.getColumn());
    }

    @Test
    public void testConvertLinesToString() {
        final String[] lines = {
            "abc",
            "def",
            "ghi",
        };
        assertEquals(
            "abc\ndef\nghi\n",
            InlineTagUtils.convertLinesToString(lines));
    }

    @Test
    public void extractInlineTags() {
        final String[] source = {
            "  {@link foo}",
        };

        final List<TagInfo> tags = InlineTagUtils.extractInlineTags(source);

        assertEquals(1, tags.size());

        final TagInfo tag = tags.get(0);
        assertTag(tag, "link", "foo", 1, 3);
    }

    @Test
    public void testRemoveLeadingJavaDoc() {
        assertEquals(" abc", InlineTagUtils.removeLeadingJavaDoc("  * abc"));

        final String source = InlineTagUtils.convertLinesToString(
            " * {@link foo",
            " * bar",
            " * baz}");

        assertEquals(" {@link foo\n" + " bar\n" + " baz}\n",
            InlineTagUtils.removeLeadingJavaDoc(source));
    }

    @Test
    public void testBadInputGetLineColumnOfIndexNegativeIndex() {
        try {
            InlineTagUtils.getLineColumnOfIndex("", -1);
            fail("AssertionError expected");
        }
        catch (AssertionError ex) {
            assertTrue(ex.getMessage().contains("positive"));
        }
    }

    @Test
    public void testBadInputGetLineColumnOfIndexTooLargeIndex() {
        try {
            InlineTagUtils.getLineColumnOfIndex("", 1);
            fail("AssertionError expected");
        }
        catch (AssertionError ex) {
            assertTrue(ex.getMessage().contains("less than length"));
        }
    }

    @Test
    public void testBadInputExtractInlineTagsLineFeed() {
        try {
            InlineTagUtils.extractInlineTags("abc\ndef");
            fail("AssertionError expected");
        }
        catch (AssertionError ex) {
            assertTrue(ex.getMessage().contains("newline"));
        }
    }

    @Test
    public void testBadInputExtractInlineTagsCarriageReturn() {
        try {
            InlineTagUtils.extractInlineTags("abc\rdef");
            fail("AssertionError expected");
        }
        catch (AssertionError ex) {
            assertTrue(ex.getMessage().contains("newline"));
        }
    }
}
