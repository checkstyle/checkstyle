////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.puppycrawl.tools.checkstyle.api.Comment;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocUtils.JavadocTags;
import java.util.List;
import org.junit.Test;

public class JavadocUtilsTest
{

    @Test
    public void testTags() {
        String [] text = { "/** @see elsewhere ",
                           " * {@link List }, {@link  List link text }",
                           "   {@link List#add(Object) link text}",
                           " * {@link Class link text}"};
        Comment comment = new Comment(text, 1, 4, text[3].length());
        JavadocTags allTags =
            JavadocUtils.getJavadocTags(comment, JavadocUtils.JavadocTagType.ALL);
        assertEquals(5, allTags.validTags.size());
    }

    @Test
    public void testTagType() {
        String [] text = { "/** @see block",
                           " * {@link List inline}, {@link List#add(Object)}" };
        Comment comment = new Comment(text, 1, 2, text[1].length());
        JavadocTags blockTags =
            JavadocUtils.getJavadocTags(comment, JavadocUtils.JavadocTagType.BLOCK);
        JavadocTags inlineTags =
            JavadocUtils.getJavadocTags(comment, JavadocUtils.JavadocTagType.INLINE);
        assertEquals(1, blockTags.validTags.size());
        assertEquals(2, inlineTags.validTags.size());
    }

    @Test
    public void testInlineTagLinkText() {
        String [] text = { "/** {@link  List link text }" };
        Comment comment = new Comment(text, 1, 1, text[0].length());
        List<JavadocTag> tags = JavadocUtils.getJavadocTags(
            comment, JavadocUtils.JavadocTagType.ALL).validTags;
        assertEquals("List link text", tags.get(0).getArg1());
    }

    @Test
    public void testInlineTagMethodRef() {
        String [] text = { "/** {@link  List#add(Object)}" };
        Comment comment = new Comment(text, 1, 1, text[0].length());
        List<JavadocTag> tags = JavadocUtils.getJavadocTags(
            comment, JavadocUtils.JavadocTagType.ALL).validTags;
        assertEquals("List#add(Object)", tags.get(0).getArg1());
    }

    @Test
    public void testTagPositions() {
        String [] text = { "/** @see elsewhere",
                           "    also {@link Name value} */" };
        Comment comment = new Comment(text, 1, 2, text[1].length());

        List<JavadocTag> tags = JavadocUtils.getJavadocTags(
            comment, JavadocUtils.JavadocTagType.ALL).validTags;

        assertEquals(2, tags.size());
        for (JavadocTag tag : tags) {
            if (JavadocTagInfo.SEE.getName().equals(tag.getTagName())) {
                assertEquals(1, tag.getLineNo());
                assertEquals(5, tag.getColumnNo());
            }
            else if (JavadocTagInfo.LINK.getName().equals(tag.getTagName())) {
                assertEquals(2, tag.getLineNo());
                assertEquals(10, tag.getColumnNo());
            }
            else {
                fail("Unexpected tag: " + tag);
            }
        }
    }

    @Test
    public void testInvalidTags() {
        String [] text = { "/** @fake block",
                           " * {@bogus inline}",
                           " * {@link List valid}"};
        Comment comment = new Comment(text, 1, 3, text[2].length());
        JavadocTags allTags =
            JavadocUtils.getJavadocTags(comment, JavadocUtils.JavadocTagType.ALL);
        assertEquals(2, allTags.invalidTags.size());
        assertEquals(1, allTags.validTags.size());
    }
}
