package com.puppycrawl.tools.checkstyle.utils;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import org.junit.Test;

/**
 * Tests BlockTagUtils.
 */
public class BlockTagUtilsTest {

    @Test
    public void testExtractBlockTags() {
        final String[] text = {
            "/** @foo abc ",
            " * @bar def  ",
            "   @baz ghi  ",
            " * @qux jkl",
                 " */"
        };

        ImmutableList<TagUtils.Tag> tags = BlockTagUtils.extractBlockTags(text);

        assertEquals(4, tags.size());

        TagUtils.Tag tag1 = tags.get(0);
        assertEquals("foo", tag1.name());
        assertEquals("abc", tag1.value());
        assertEquals(1, tag1.position().getLine());
        assertEquals(4, tag1.position().getColumn());

        TagUtils.Tag tag2 = tags.get(1);
        assertEquals("bar", tag2.name());
        assertEquals("def", tag2.value());
        assertEquals(2, tag2.position().getLine());
        assertEquals(3, tag2.position().getColumn());

        TagUtils.Tag tag3 = tags.get(2);
        assertEquals("baz", tag3.name());
        assertEquals("ghi", tag3.value());
        assertEquals(3, tag3.position().getLine());
        assertEquals(3, tag3.position().getColumn());

        TagUtils.Tag tag4 = tags.get(3);
        assertEquals("qux", tag4.name());
        assertEquals("jkl", tag4.value());
        assertEquals(4, tag4.position().getLine());
        assertEquals(3, tag4.position().getColumn());
    }

    @Test
    public void testVersionStringFormat() {
        final String[] text = {
            "/** ",
            " * @version 1.0",
            " */"
        };
        ImmutableList<TagUtils.Tag> tags = BlockTagUtils.extractBlockTags(text);
        assertEquals(1, tags.size());
        assertEquals("version", tags.get(0).name());
        assertEquals("1.0", tags.get(0).value());
    }

    @Test
    public void testOddVersionString() {
        final String[] text = {
            "/**",
            " * Foo",
            " * @version 1.0 */"};

        ImmutableList<TagUtils.Tag> tags = BlockTagUtils.extractBlockTags(text);
        assertEquals(1, tags.size());
        assertEquals("version", tags.get(0).name());
        assertEquals("1.0", tags.get(0).value());
    }
}