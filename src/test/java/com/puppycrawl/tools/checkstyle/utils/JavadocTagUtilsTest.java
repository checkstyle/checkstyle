package com.puppycrawl.tools.checkstyle.utils;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableList;
import com.puppycrawl.tools.checkstyle.api.LineColumn;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag;
import org.junit.Test;

/**
 * Created by nnaze on 4/28/17.
 */
public class JavadocTagUtilsTest {

    @Test
    public void testExtractInlineTags() {
        final String[] text = {
                "/** @see elsewhere ",
                " * {@link List }, {@link List link text }",
                "   {@link List#add(Object) link text}",
                " * {@link Class link text}",
                " */"};
        ImmutableList<JavadocTagUtils.Tag> tags = JavadocTagUtils.extractInlineTags(text);

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

        ImmutableList<JavadocTagUtils.Tag> tags = JavadocTagUtils.extractInlineTags(text);

        assertEquals(1, tags.size());
        assertTag(tags.get(0), "link", "foo bar baz", 2, 4);
    }

    private void assertTag(JavadocTagUtils.Tag tag, String name, String value, int line, int col) {
        assertEquals(name, tag.name());
        assertEquals(value, tag.value());
        assertEquals(line, tag.position().getLine());
        assertEquals(col, tag.position().getColumn());
    }

    @Test
    public void testCollapseWhitespace() {
        assertEquals("a b", JavadocTagUtils.collapseWhitespace("  a  b  "));
        assertEquals("", JavadocTagUtils.collapseWhitespace("    "));
        assertEquals("a b c", JavadocTagUtils.collapseWhitespace("a\nb\nc\n"));
    }

    @Test
    public void testGetLineColumnOfIndex() {
        String source = "abc\n" + "def\n" + "hij\n";

        LineColumn lineColumn = JavadocTagUtils.getLineColumnOfIndex(source, source.indexOf("a"));
        assertEquals(1, lineColumn.getLine());
        assertEquals(0, lineColumn.getColumn());

        lineColumn = JavadocTagUtils.getLineColumnOfIndex(source, source.indexOf("e"));
        assertEquals(2, lineColumn.getLine());
        assertEquals(1, lineColumn.getColumn());

        lineColumn = JavadocTagUtils.getLineColumnOfIndex(source, source.indexOf("j"));
        assertEquals(3, lineColumn.getLine());
        assertEquals(2, lineColumn.getColumn());
    }

    @Test
    public void testConvertLinesToString() {
        String[] lines = {
                "abc",
                "def",
                "ghi"};
        assertEquals(
                "abc\ndef\nghi\n",
                JavadocTagUtils.convertLinesToString(lines));
    }

    @Test
    public void extractInlineTags() {
        String[] source = {
                "  {@link foo}"
        };

        ImmutableList<JavadocTagUtils.Tag> tags = JavadocTagUtils
                .extractInlineTags(source);

        assertEquals(1, tags.size());

        JavadocTagUtils.Tag tag = tags.get(0);
        assertTag(tag, "link", "foo", 1, 3);
    }

    @Test
    public void testRemoveLeadingJavaDoc() {
        assertEquals(" abc", JavadocTagUtils.removeLeadingJavaDoc("  * abc"));

        String source = JavadocTagUtils.convertLinesToString(new String[]{
                " * {@link foo",
                " * bar",
                " * baz}"});

        assertEquals(" {@link foo\n" + " bar\n" + " baz}\n",
                JavadocTagUtils.removeLeadingJavaDoc(source));
    }
}
