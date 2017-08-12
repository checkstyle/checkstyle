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

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.Comment;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTags;

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
        assertEquals("Invalid valid tags size", 5, allTags.getValidTags().size());
    }

    @Test
    public void testBlockTag() {
        final String[] text = {
            "/** @see elsewhere ",
            " */",
        };
        final Comment comment = new Comment(text, 1, 4, text[1].length());
        final JavadocTags allTags =
            JavadocUtils.getJavadocTags(comment, JavadocUtils.JavadocTagType.ALL);
        assertEquals("Invalid valid tags size", 1, allTags.getValidTags().size());
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
        assertEquals("Invalid valid tags size", 1, blockTags.getValidTags().size());
        assertEquals("Invalid valid tags size", 2, inlineTags.getValidTags().size());
    }

    @Test
    public void testInlineTagLinkText() {
        final String[] text = {
            "/** {@link List link text }",
        };
        final Comment comment = new Comment(text, 1, 1, text[0].length());
        final List<JavadocTag> tags = JavadocUtils.getJavadocTags(
            comment, JavadocUtils.JavadocTagType.ALL).getValidTags();
        assertEquals("Invalid first arg", "List link text", tags.get(0).getFirstArg());
    }

    @Test
    public void testInlineTagMethodRef() {
        final String[] text = {
            "/** {@link List#add(Object)}",
        };
        final Comment comment = new Comment(text, 1, 1, text[0].length());
        final List<JavadocTag> tags = JavadocUtils.getJavadocTags(
            comment, JavadocUtils.JavadocTagType.ALL).getValidTags();
        assertEquals("Invalid first arg", "List#add(Object)", tags.get(0).getFirstArg());
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

        assertEquals("Invalid tags size", 2, tags.size());

        final JavadocTag seeTag = tags.get(0);
        assertEquals("Invalid tag name", JavadocTagInfo.SEE.getName(), seeTag.getTagName());
        assertEquals("Invalid line number", 1, seeTag.getLineNo());
        assertEquals("Invalid column number", 4, seeTag.getColumnNo());

        final JavadocTag linkTag = tags.get(1);
        assertEquals("Invalid tag name", JavadocTagInfo.LINK.getName(), linkTag.getTagName());
        assertEquals("Invalid line number", 2, linkTag.getLineNo());
        assertEquals("Invalid column number", 10, linkTag.getColumnNo());
    }

    @Test
    public void testInlineTagPositions() {
        final String[] text = {"/** Also {@link Name value} */"};
        final Comment comment = new Comment(text, 1, 0, text[0].length());

        final List<JavadocTag> tags = JavadocUtils.getJavadocTags(
            comment, JavadocUtils.JavadocTagType.INLINE).getValidTags();

        assertEquals("Invalid tags size", 1, tags.size());

        assertEquals("Unexpected line number", 0, tags.get(0).getLineNo());
        assertEquals("Unexpected column number", 10, tags.get(0).getColumnNo());
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
        assertEquals("Unexpected invalid tags size", 2, allTags.getInvalidTags().size());
        assertEquals("Unexpected valid tags size", 1, allTags.getValidTags().size());
    }

    @Test
    public void testEmptyBlockComment() {
        final String emptyComment = "";
        assertFalse("Should return false when empty string is passed",
                JavadocUtils.isJavadocComment(emptyComment));
    }

    @Test
    public void testEmptyBlockCommentAst() {
        final DetailAST commentBegin = new DetailAST();
        commentBegin.setType(TokenTypes.BLOCK_COMMENT_BEGIN);
        commentBegin.setText("/*");

        final DetailAST commentContent = new DetailAST();
        commentContent.setType(TokenTypes.COMMENT_CONTENT);
        commentContent.setText("");

        final DetailAST commentEnd = new DetailAST();
        commentEnd.setType(TokenTypes.BLOCK_COMMENT_END);
        commentEnd.setText("*/");

        commentBegin.setFirstChild(commentContent);
        commentContent.setNextSibling(commentEnd);

        assertFalse("Should return false when empty block comment is passed",
                JavadocUtils.isJavadocComment(commentBegin));
    }

    @Test
    public void testEmptyJavadocComment() {
        final String emptyJavadocComment = "*";
        assertTrue("Should return true when empty jabadoc comment is passed",
                JavadocUtils.isJavadocComment(emptyJavadocComment));
    }

    @Test
    public void testEmptyJavadocCommentAst() {
        final DetailAST commentBegin = new DetailAST();
        commentBegin.setType(TokenTypes.BLOCK_COMMENT_BEGIN);
        commentBegin.setText("/*");

        final DetailAST javadocCommentContent = new DetailAST();
        javadocCommentContent.setType(TokenTypes.COMMENT_CONTENT);
        javadocCommentContent.setText("*");

        final DetailAST commentEnd = new DetailAST();
        commentEnd.setType(TokenTypes.BLOCK_COMMENT_END);
        commentEnd.setText("*/");

        commentBegin.setFirstChild(javadocCommentContent);
        javadocCommentContent.setNextSibling(commentEnd);

        final DetailAST commentBeginParent = new DetailAST();
        commentBeginParent.setType(TokenTypes.MODIFIERS);
        commentBeginParent.setFirstChild(commentBegin);

        final DetailAST aJavadocPosition = new DetailAST();
        aJavadocPosition.setType(TokenTypes.METHOD_DEF);
        aJavadocPosition.setFirstChild(commentBeginParent);
        assertTrue("Should return true when empty javadoc comment ast is passed",
                JavadocUtils.isJavadocComment(commentBegin));
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(JavadocUtils.class, true);
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
        assertFalse("Should return true when branch contains node passed",
                JavadocUtils.containsInBranch(node, JavadocTokenTypes.AUTHOR_LITERAL));

        firstChild.setParent(node);
        secondChild.setParent(node);
        assertFalse("Should return false when branch does not contain node passed",
                JavadocUtils.containsInBranch(node, JavadocTokenTypes.AUTHOR_LITERAL));

        secondChild.setType(JavadocTokenTypes.AUTHOR_LITERAL);
        assertTrue("Should return true when branch contains node passed",
                JavadocUtils.containsInBranch(node, JavadocTokenTypes.AUTHOR_LITERAL));
    }

    @Test
    public void testGetTokenNameForId() {
        assertEquals("Invalid token name",
                "EOF", JavadocUtils.getTokenName(JavadocTokenTypes.EOF));
    }

    @Test
    public void testGetTokenNameForLargeId() {
        try {
            JavadocUtils.getTokenName(30073);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "Unknown javadoc token id. Given id: 30073", ex.getMessage());
        }
    }

    @Test
    public void testGetTokenNameForInvalidId() {
        try {
            JavadocUtils.getTokenName(100);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "Unknown javadoc token id. Given id: 100", ex.getMessage());
        }
    }

    @Test
    public void testGetTokenIdThatIsUnknown() {
        try {
            JavadocUtils.getTokenId("");
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "Unknown javadoc token name. Given name ", ex.getMessage());
        }
    }

    @Test
    public void testGetTokenId() {
        final int tokenId = JavadocUtils.getTokenId("JAVADOC");

        assertEquals("Invalid token id", JavadocTokenTypes.JAVADOC, tokenId);
    }

    @Test
    public void testGetJavadocCommentContent() {
        final DetailAST detailAST = new DetailAST();
        final DetailAST javadoc = new DetailAST();

        javadoc.setText("1javadoc");
        detailAST.setFirstChild(javadoc);
        final String commentContent = JavadocUtils.getJavadocCommentContent(detailAST);

        assertEquals("Invalid comment content", "javadoc", commentContent);
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

        final DetailNode result = JavadocUtils.findFirstToken(javadocNode, JavadocTokenTypes.BODY);

        assertEquals("Invalid first token", body, result);
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

        assertEquals("Unexpected node", previousNode, JavadocUtils.getPreviousSibling(node));
    }

    @Test
    public void testGetTokenNames() {
        assertEquals("Unexpected token name",
            "HTML_COMMENT", JavadocUtils.getTokenName(20074));
    }
}
