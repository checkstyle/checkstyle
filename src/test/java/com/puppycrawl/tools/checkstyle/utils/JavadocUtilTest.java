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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.Comment;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTags;

public class JavadocUtilTest {

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
            JavadocUtil.getJavadocTags(comment, JavadocUtil.JavadocTagType.ALL);
        assertEquals(5, allTags.getValidTags().size(), "Invalid valid tags size");
    }

    @Test
    public void testBlockTag() {
        final String[] text = {
            "/** @see elsewhere ",
            " */",
        };
        final Comment comment = new Comment(text, 1, 4, text[1].length());
        final JavadocTags allTags =
            JavadocUtil.getJavadocTags(comment, JavadocUtil.JavadocTagType.ALL);
        assertEquals(1, allTags.getValidTags().size(), "Invalid valid tags size");
    }

    @Test
    public void testTagType() {
        final String[] text = {
            "/** @see block",
            " * {@link List inline}, {@link List#add(Object)}",
        };
        final Comment comment = new Comment(text, 1, 2, text[1].length());
        final JavadocTags blockTags =
            JavadocUtil.getJavadocTags(comment, JavadocUtil.JavadocTagType.BLOCK);
        final JavadocTags inlineTags =
            JavadocUtil.getJavadocTags(comment, JavadocUtil.JavadocTagType.INLINE);
        assertEquals(1, blockTags.getValidTags().size(), "Invalid valid tags size");
        assertEquals(2, inlineTags.getValidTags().size(), "Invalid valid tags size");
    }

    @Test
    public void testInlineTagLinkText() {
        final String[] text = {
            "/** {@link List link text }",
        };
        final Comment comment = new Comment(text, 1, 1, text[0].length());
        final List<JavadocTag> tags = JavadocUtil.getJavadocTags(
            comment, JavadocUtil.JavadocTagType.ALL).getValidTags();
        assertEquals("List link text", tags.get(0).getFirstArg(), "Invalid first arg");
    }

    @Test
    public void testInlineTagMethodRef() {
        final String[] text = {
            "/** {@link List#add(Object)}",
        };
        final Comment comment = new Comment(text, 1, 1, text[0].length());
        final List<JavadocTag> tags = JavadocUtil.getJavadocTags(
            comment, JavadocUtil.JavadocTagType.ALL).getValidTags();
        assertEquals("List#add(Object)", tags.get(0).getFirstArg(), "Invalid first arg");
    }

    @Test
    public void testTagPositions() {
        final String[] text = {
            "/** @see elsewhere",
            "    also {@link Name value} */",
        };
        final Comment comment = new Comment(text, 1, 2, text[1].length());

        final List<JavadocTag> tags = JavadocUtil.getJavadocTags(
            comment, JavadocUtil.JavadocTagType.ALL).getValidTags();

        assertEquals(2, tags.size(), "Invalid tags size");

        final JavadocTag seeTag = tags.get(0);
        assertEquals(JavadocTagInfo.SEE.getName(), seeTag.getTagName(), "Invalid tag name");
        assertEquals(1, seeTag.getLineNo(), "Invalid line number");
        assertEquals(4, seeTag.getColumnNo(), "Invalid column number");

        final JavadocTag linkTag = tags.get(1);
        assertEquals(JavadocTagInfo.LINK.getName(), linkTag.getTagName(), "Invalid tag name");
        assertEquals(2, linkTag.getLineNo(), "Invalid line number");
        assertEquals(10, linkTag.getColumnNo(), "Invalid column number");
    }

    @Test
    public void testInlineTagPositions() {
        final String[] text = {"/** Also {@link Name value} */"};
        final Comment comment = new Comment(text, 1, 0, text[0].length());

        final List<JavadocTag> tags = JavadocUtil.getJavadocTags(
            comment, JavadocUtil.JavadocTagType.INLINE).getValidTags();

        final int size = tags.size();
        assertEquals(1, size, "Invalid tags size");
        final int lineNo = tags.get(0).getLineNo();
        assertEquals(0, lineNo, "Unexpected line number");
        final int columnNo = tags.get(0).getColumnNo();
        assertEquals(10, columnNo, "Unexpected column number");
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
            JavadocUtil.getJavadocTags(comment, JavadocUtil.JavadocTagType.ALL);
        assertEquals(2, allTags.getInvalidTags().size(), "Unexpected invalid tags size");
        assertTag("Unexpected invalid tag", new InvalidJavadocTag(1, 4, "fake"),
                allTags.getInvalidTags().get(0));
        assertTag("Unexpected invalid tag", new InvalidJavadocTag(2, 4, "bogus"),
                allTags.getInvalidTags().get(1));
        assertEquals(1, allTags.getValidTags().size(), "Unexpected valid tags size");
        assertTag("Unexpected valid tag", new JavadocTag(3, 4, "link", "List valid"),
                allTags.getValidTags().get(0));
    }

    @Test
    public void testEmptyBlockComment() {
        final String emptyComment = "";
        assertFalse(JavadocUtil.isJavadocComment(emptyComment),
                "Should return false when empty string is passed");
    }

    @Test
    public void testEmptyBlockCommentAst() {
        final DetailAstImpl commentBegin = new DetailAstImpl();
        commentBegin.setType(TokenTypes.BLOCK_COMMENT_BEGIN);
        commentBegin.setText("/*");

        final DetailAstImpl commentContent = new DetailAstImpl();
        commentContent.setType(TokenTypes.COMMENT_CONTENT);
        commentContent.setText("");

        final DetailAstImpl commentEnd = new DetailAstImpl();
        commentEnd.setType(TokenTypes.BLOCK_COMMENT_END);
        commentEnd.setText("*/");

        commentBegin.setFirstChild(commentContent);
        commentContent.setNextSibling(commentEnd);

        assertFalse(JavadocUtil.isJavadocComment(commentBegin),
                "Should return false when empty block comment is passed");
    }

    @Test
    public void testEmptyJavadocComment() {
        final String emptyJavadocComment = "*";
        assertTrue(JavadocUtil.isJavadocComment(emptyJavadocComment),
                "Should return true when empty javadoc comment is passed");
    }

    @Test
    public void testEmptyJavadocCommentAst() {
        final DetailAstImpl commentBegin = new DetailAstImpl();
        commentBegin.setType(TokenTypes.BLOCK_COMMENT_BEGIN);
        commentBegin.setText("/*");

        final DetailAstImpl javadocCommentContent = new DetailAstImpl();
        javadocCommentContent.setType(TokenTypes.COMMENT_CONTENT);
        javadocCommentContent.setText("*");

        final DetailAstImpl commentEnd = new DetailAstImpl();
        commentEnd.setType(TokenTypes.BLOCK_COMMENT_END);
        commentEnd.setText("*/");

        commentBegin.setFirstChild(javadocCommentContent);
        javadocCommentContent.setNextSibling(commentEnd);

        final DetailAstImpl commentBeginParent = new DetailAstImpl();
        commentBeginParent.setType(TokenTypes.MODIFIERS);
        commentBeginParent.setFirstChild(commentBegin);

        final DetailAstImpl aJavadocPosition = new DetailAstImpl();
        aJavadocPosition.setType(TokenTypes.METHOD_DEF);
        aJavadocPosition.setFirstChild(commentBeginParent);
        assertTrue(JavadocUtil.isJavadocComment(commentBegin),
                "Should return true when empty javadoc comment ast is passed");
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(JavadocUtil.class, true),
                "Constructor is not private");
    }

    @Test
    public void testBranchContains() {
        final JavadocNodeImpl node = new JavadocNodeImpl();
        final JavadocNodeImpl firstChild = new JavadocNodeImpl();
        final JavadocNodeImpl secondChild = new JavadocNodeImpl();

        node.setType(JavadocTokenTypes.JAVADOC);
        firstChild.setType(JavadocTokenTypes.BODY_TAG_START);
        secondChild.setType(JavadocTokenTypes.CODE_LITERAL);

        node.setChildren(firstChild, secondChild);
        assertFalse(JavadocUtil.containsInBranch(node, JavadocTokenTypes.AUTHOR_LITERAL),
                "Should return true when branch contains node passed");

        firstChild.setParent(node);
        secondChild.setParent(node);
        assertFalse(JavadocUtil.containsInBranch(node, JavadocTokenTypes.AUTHOR_LITERAL),
                "Should return false when branch does not contain node passed");

        secondChild.setType(JavadocTokenTypes.AUTHOR_LITERAL);
        assertTrue(JavadocUtil.containsInBranch(node, JavadocTokenTypes.AUTHOR_LITERAL),
                "Should return true when branch contains node passed");
    }

    @Test
    public void testGetTokenNameForId() {
        assertEquals("EOF", JavadocUtil.getTokenName(JavadocTokenTypes.EOF),
                "Invalid token name");
    }

    @Test
    public void testGetTokenNameForLargeId() {
        try {
            JavadocUtil.getTokenName(30073);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unknown javadoc token id. Given id: 30073", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testGetTokenNameForInvalidId() {
        try {
            JavadocUtil.getTokenName(110);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unknown javadoc token id. Given id: 110", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testGetTokenNameForLowerBoundInvalidId() {
        try {
            JavadocUtil.getTokenName(10095);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unknown javadoc token id. Given id: 10095", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testGetTokenIdThatIsUnknown() {
        try {
            JavadocUtil.getTokenId("");
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unknown javadoc token name. Given name ", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testGetTokenId() {
        final int tokenId = JavadocUtil.getTokenId("JAVADOC");

        assertEquals(JavadocTokenTypes.JAVADOC, tokenId, "Invalid token id");
    }

    @Test
    public void testGetJavadocCommentContent() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        final DetailAstImpl javadoc = new DetailAstImpl();

        javadoc.setText("1javadoc");
        detailAST.setFirstChild(javadoc);
        final String commentContent = JavadocUtil.getJavadocCommentContent(detailAST);

        assertEquals("javadoc", commentContent, "Invalid comment content");
    }

    @Test
    public void testGetFirstToken() {
        final JavadocNodeImpl javadocNode = new JavadocNodeImpl();
        final JavadocNodeImpl basetag = new JavadocNodeImpl();
        basetag.setType(JavadocTokenTypes.BASE_TAG);
        final JavadocNodeImpl body = new JavadocNodeImpl();
        body.setType(JavadocTokenTypes.BODY);

        body.setParent(javadocNode);
        basetag.setParent(javadocNode);
        javadocNode.setChildren(basetag, body);

        final DetailNode result = JavadocUtil.findFirstToken(javadocNode, JavadocTokenTypes.BODY);

        assertEquals(body, result, "Invalid first token");
    }

    @Test
    public void testGetPreviousSibling() {
        final JavadocNodeImpl root = new JavadocNodeImpl();

        final JavadocNodeImpl node = new JavadocNodeImpl();
        node.setIndex(1);
        node.setParent(root);

        final JavadocNodeImpl previousNode = new JavadocNodeImpl();
        previousNode.setIndex(0);
        node.setParent(root);

        root.setChildren(previousNode, node);

        final DetailNode previousSibling = JavadocUtil.getPreviousSibling(node);

        assertEquals(previousNode, previousSibling, "Unexpected node");
    }

    @Test
    public void testGetLastTokenName() {
        assertEquals("RP", JavadocUtil.getTokenName(10094), "Unexpected token name");
    }

    @Test
    public void testEscapeAllControlChars() {
        assertEquals("abc", JavadocUtil.escapeAllControlChars("abc"), "invalid result");
        assertEquals("1\\r2\\n3\\t",
                JavadocUtil.escapeAllControlChars("1\\r2\\n3\\t"), "invalid result");
    }

    private static void assertTag(String message, InvalidJavadocTag expected,
            InvalidJavadocTag actual) {
        assertEquals(expected.getLine(), actual.getLine(), message + " line");
        assertEquals(expected.getCol(), actual.getCol(), message + " column");
        assertEquals(expected.getName(), actual.getName(), message + " name");
    }

    private static void assertTag(String message, JavadocTag expected,
            JavadocTag actual) {
        assertEquals(expected.getLineNo(), actual.getLineNo(), message + " line");
        assertEquals(expected.getColumnNo(), actual.getColumnNo(), message + " column");
        assertEquals(expected.getFirstArg(), actual.getFirstArg(), message + " first arg");
        assertEquals(expected.getTagName(), actual.getTagName(), message + " tag name");
    }

}
