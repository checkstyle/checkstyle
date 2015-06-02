////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.Comment;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class JavadocUtilsTest {

    @Test
    public void testTags() {
        final String[] text = {
            "/** @see elsewhere ",
            " * {@link List }, {@link List link text }",
            "   {@link List#add(Object) link text}",
            " * {@link Class link text}",
        };
        final Comment comment = new Comment(text, 1, 4, text[3].length());
        final JavadocTags allTags =
            JavadocUtils.getJavadocTags(comment, JavadocUtils.JavadocTagType.ALL);
        assertEquals(5, allTags.getValidTags().size());
    }

    @Test
    public void testTagType() {
        final String[] text = {
            "/** @see block",
            " * {@link List inline}, {@link List#add(Object)}",
        };
        final Comment comment = new Comment(text, 1, 2, text[1].length());
        final JavadocTags blockTags =
            JavadocUtils.getJavadocTags(comment, JavadocUtils.JavadocTagType.BLOCK);
        final JavadocTags inlineTags =
            JavadocUtils.getJavadocTags(comment, JavadocUtils.JavadocTagType.INLINE);
        assertEquals(1, blockTags.getValidTags().size());
        assertEquals(2, inlineTags.getValidTags().size());
    }

    @Test
    public void testInlineTagLinkText() {
        final String[] text = {
            "/** {@link List link text }",
        };
        final Comment comment = new Comment(text, 1, 1, text[0].length());
        final List<JavadocTag> tags = JavadocUtils.getJavadocTags(
            comment, JavadocUtils.JavadocTagType.ALL).getValidTags();
        assertEquals("List link text", tags.get(0).getArg1());
    }

    @Test
    public void testInlineTagMethodRef() {
        final String[] text = {
            "/** {@link List#add(Object)}",
        };
        final Comment comment = new Comment(text, 1, 1, text[0].length());
        final List<JavadocTag> tags = JavadocUtils.getJavadocTags(
            comment, JavadocUtils.JavadocTagType.ALL).getValidTags();
        assertEquals("List#add(Object)", tags.get(0).getArg1());
    }

    @Test
    public void testTagPositions() {
        final String[] text = {
            "/** @see elsewhere",
            "    also {@link Name value} */",
        };
        final Comment comment = new Comment(text, 1, 2, text[1].length());

        final List<JavadocTag> tags = JavadocUtils.getJavadocTags(
            comment, JavadocUtils.JavadocTagType.ALL).getValidTags();

        assertEquals(2, tags.size());
        for (final JavadocTag tag : tags) {
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
        final String[] text = {
            "/** @fake block",
            " * {@bogus inline}",
            " * {@link List valid}",
        };
        final Comment comment = new Comment(text, 1, 3, text[2].length());
        final JavadocTags allTags =
            JavadocUtils.getJavadocTags(comment, JavadocUtils.JavadocTagType.ALL);
        assertEquals(2, allTags.getInvalidTags().size());
        assertEquals(1, allTags.getValidTags().size());
    }

    @Test
    public void testEmptyBlockComment() {
        final String emptyComment = "";
        assertFalse(JavadocUtils.isJavadocComment(emptyComment));
    }

    @Test
    public void testEmptyBlockCommentAst() {
        DetailAST commentBegin = new DetailAST();
        commentBegin.setType(TokenTypes.BLOCK_COMMENT_BEGIN);
        commentBegin.setText("/*");

        DetailAST commentContent = new DetailAST();
        commentContent.setType(TokenTypes.COMMENT_CONTENT);
        commentContent.setText("");

        DetailAST commentEnd = new DetailAST();
        commentEnd.setType(TokenTypes.BLOCK_COMMENT_END);
        commentEnd.setText("*/");

        commentBegin.setFirstChild(commentContent);
        commentContent.setNextSibling(commentEnd);

        assertFalse(JavadocUtils.isJavadocComment(commentBegin));
    }

    @Test
    public void testEmptyJavadocComment() {
        final String emptyJavadocComment = "*";
        assertTrue(JavadocUtils.isJavadocComment(emptyJavadocComment));
    }

    @Test
    public void testEmptyJavadocCommentAst() {
        DetailAST commentBegin = new DetailAST();
        commentBegin.setType(TokenTypes.BLOCK_COMMENT_BEGIN);
        commentBegin.setText("/*");

        DetailAST javadocCommentContent = new DetailAST();
        javadocCommentContent.setType(TokenTypes.COMMENT_CONTENT);
        javadocCommentContent.setText("*");

        DetailAST commentEnd = new DetailAST();
        commentEnd.setType(TokenTypes.BLOCK_COMMENT_END);
        commentEnd.setText("*/");

        commentBegin.setFirstChild(javadocCommentContent);
        javadocCommentContent.setNextSibling(commentEnd);

        assertTrue(JavadocUtils.isJavadocComment(commentBegin));
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(JavadocUtils.class);
    }

    @Test
    public void testBranchContains() {
        JavadocNodeImpl node = new JavadocNodeImpl();
        JavadocNodeImpl firstChild = new JavadocNodeImpl();
        JavadocNodeImpl secondChild = new JavadocNodeImpl();

        node.setType(JavadocTokenTypes.JAVADOC);
        firstChild.setType(JavadocTokenTypes.BODY_TAG_OPEN);
        secondChild.setType(JavadocTokenTypes.CODE_LITERAL);

        node.setChildren(firstChild, secondChild);
        assertFalse(JavadocUtils.branchContains(node, JavadocTokenTypes.AUTHOR_LITERAL));

        firstChild.setParent(node);
        secondChild.setParent(node);
        assertFalse(JavadocUtils.branchContains(node, JavadocTokenTypes.AUTHOR_LITERAL));

        secondChild.setType(JavadocTokenTypes.AUTHOR_LITERAL);
        assertTrue(JavadocUtils.branchContains(node, JavadocTokenTypes.AUTHOR_LITERAL));
    }
}
