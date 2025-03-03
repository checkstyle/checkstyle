///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.util.List;

import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.LocalizedMessage;
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
        assertWithMessage("Invalid valid tags size")
            .that(allTags.getValidTags())
            .hasSize(5);
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
        assertWithMessage("Invalid valid tags size")
            .that(allTags.getValidTags())
            .hasSize(1);
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
        assertWithMessage("Invalid valid tags size")
            .that(blockTags.getValidTags())
            .hasSize(1);
        assertWithMessage("Invalid valid tags size")
            .that(inlineTags.getValidTags())
            .hasSize(2);
    }

    @Test
    public void testInlineTagLinkText() {
        final String[] text = {
            "/** {@link List link text }",
        };
        final Comment comment = new Comment(text, 1, 1, text[0].length());
        final List<JavadocTag> tags = JavadocUtil.getJavadocTags(
            comment, JavadocUtil.JavadocTagType.ALL).getValidTags();
        assertWithMessage("Invalid first arg")
            .that(tags.get(0).getFirstArg())
            .isEqualTo("List link text");
    }

    @Test
    public void testInlineTagMethodRef() {
        final String[] text = {
            "/** {@link List#add(Object)}",
        };
        final Comment comment = new Comment(text, 1, 1, text[0].length());
        final List<JavadocTag> tags = JavadocUtil.getJavadocTags(
            comment, JavadocUtil.JavadocTagType.ALL).getValidTags();
        assertWithMessage("Invalid first arg")
            .that(tags.get(0).getFirstArg())
            .isEqualTo("List#add(Object)");
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

        assertWithMessage("Invalid tags size")
            .that(tags)
            .hasSize(2);

        final JavadocTag seeTag = tags.get(0);
        assertWithMessage("Invalid tag name")
            .that(seeTag.getTagName())
            .isEqualTo(JavadocTagInfo.SEE.getName());
        assertWithMessage("Invalid line number")
            .that(seeTag.getLineNo())
            .isEqualTo(1);
        assertWithMessage("Invalid column number")
            .that(seeTag.getColumnNo())
            .isEqualTo(4);

        final JavadocTag linkTag = tags.get(1);
        assertWithMessage("Invalid tag name")
            .that(linkTag.getTagName())
            .isEqualTo(JavadocTagInfo.LINK.getName());
        assertWithMessage("Invalid line number")
            .that(linkTag.getLineNo())
            .isEqualTo(2);
        assertWithMessage("Invalid column number")
            .that(linkTag.getColumnNo())
            .isEqualTo(10);
    }

    @Test
    public void testInlineTagPositions() {
        final String[] text = {"/** Also {@link Name value} */"};
        final Comment comment = new Comment(text, 1, 0, text[0].length());

        final List<JavadocTag> tags = JavadocUtil.getJavadocTags(
            comment, JavadocUtil.JavadocTagType.INLINE).getValidTags();

        assertWithMessage("Invalid tags size")
            .that(tags)
            .hasSize(1);
        final int lineNo = tags.get(0).getLineNo();
        assertWithMessage("Unexpected line number")
            .that(lineNo)
            .isEqualTo(0);
        final int columnNo = tags.get(0).getColumnNo();
        assertWithMessage("Unexpected column number")
            .that(columnNo)
            .isEqualTo(10);
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
        assertWithMessage("Unexpected invalid tags size")
            .that(allTags.getInvalidTags())
            .hasSize(2);
        assertTag("Unexpected invalid tag", new InvalidJavadocTag(1, 4, "fake"),
                allTags.getInvalidTags().get(0));
        assertTag("Unexpected invalid tag", new InvalidJavadocTag(2, 4, "bogus"),
                allTags.getInvalidTags().get(1));
        assertWithMessage("Unexpected valid tags size")
            .that(allTags.getValidTags())
            .hasSize(1);
        assertTag("Unexpected valid tag", new JavadocTag(3, 4, "link", "List valid"),
                allTags.getValidTags().get(0));
    }

    @Test
    public void testEmptyBlockComment() {
        final String emptyComment = "";
        assertWithMessage("Should return false when empty string is passed")
                .that(JavadocUtil.isJavadocComment(emptyComment))
                .isFalse();
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

        assertWithMessage("Should return false when empty block comment is passed")
                .that(JavadocUtil.isJavadocComment(commentBegin))
                .isFalse();
    }

    @Test
    public void testEmptyJavadocComment() {
        final String emptyJavadocComment = "*";
        assertWithMessage("Should return true when empty javadoc comment is passed")
                .that(JavadocUtil.isJavadocComment(emptyJavadocComment))
                .isTrue();
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
        assertWithMessage("Should return true when empty javadoc comment ast is passed")
                .that(JavadocUtil.isJavadocComment(commentBegin))
                .isTrue();
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(JavadocUtil.class))
                .isTrue();
    }

    @Test
    public void testGetTokenNameForId() {
        assertWithMessage("Invalid token name")
            .that(JavadocUtil.getTokenName(JavadocTokenTypes.EOF))
            .isEqualTo("EOF");
    }

    @Test
    public void testGetTokenNameForLargeId() {
        try {
            JavadocUtil.getTokenName(30073);
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalArgumentException ex) {
            LocalizedMessage expectedMessage = getUnknownTokenIdMessage(30073);
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(expectedMessage.getMessage());
        }
    }

    @Test
    public void testGetTokenNameForInvalidId() {
        try {
            JavadocUtil.getTokenName(110);
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalArgumentException ex) {
            LocalizedMessage expectedMessage = getUnknownTokenIdMessage(110);
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(expectedMessage.getMessage());
        }
    }

    @Test
    public void testGetTokenNameForLowerBoundInvalidId() {
        try {
            JavadocUtil.getTokenName(10095);
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalArgumentException ex) {
            LocalizedMessage expectedMessage = getUnknownTokenIdMessage(10095);
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(expectedMessage.getMessage());
        }
    }

    @Test
    public void testGetTokenIdThatIsUnknown() {
        try {
            JavadocUtil.getTokenId("");
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalArgumentException ex) {
            LocalizedMessage expectedMessage = getUnknownTokenNameMessage("");
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(expectedMessage.getMessage());
        }
    }

    @Test
    public void testGetTokenId() {
        final int tokenId = JavadocUtil.getTokenId("JAVADOC");

        assertWithMessage("Invalid token id")
            .that(tokenId)
            .isEqualTo(JavadocTokenTypes.JAVADOC);
    }

    @Test
    public void testGetJavadocCommentContent() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        final DetailAstImpl javadoc = new DetailAstImpl();

        javadoc.setText("1javadoc");
        detailAST.setFirstChild(javadoc);
        final String commentContent = JavadocUtil.getJavadocCommentContent(detailAST);

        assertWithMessage("Invalid comment content")
            .that(commentContent)
            .isEqualTo("javadoc");
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

        assertWithMessage("Invalid first token")
            .that(result)
            .isEqualTo(body);
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

        assertWithMessage("Unexpected node")
            .that(previousSibling)
            .isEqualTo(previousNode);
    }

    @Test
    public void testGetLastTokenName() {
        assertWithMessage("Unexpected token name")
            .that(JavadocUtil.getTokenName(10094))
            .isEqualTo("RP");
    }

    @Test
    public void testEscapeAllControlChars() {
        assertWithMessage("invalid result")
            .that(JavadocUtil.escapeAllControlChars("abc"))
            .isEqualTo("abc");
        assertWithMessage("invalid result")
            .that(JavadocUtil.escapeAllControlChars("1\\r2\\n3\\t"))
            .isEqualTo("1\\r2\\n3\\t");
    }

    private static void assertTag(String message, InvalidJavadocTag expected,
            InvalidJavadocTag actual) {
        assertWithMessage(message + " line")
            .that(actual.getLine())
            .isEqualTo(expected.getLine());
        assertWithMessage(message + " column")
            .that(actual.getCol())
            .isEqualTo(expected.getCol());
        assertWithMessage(message + " name")
            .that(actual.getName())
            .isEqualTo(expected.getName());
    }

    private static void assertTag(String message, JavadocTag expected,
            JavadocTag actual) {
        assertWithMessage(message + " line")
            .that(actual.getLineNo())
            .isEqualTo(expected.getLineNo());
        assertWithMessage(message + " column")
            .that(actual.getColumnNo())
            .isEqualTo(expected.getColumnNo());
        assertWithMessage(message + " first arg")
            .that(actual.getFirstArg())
            .isEqualTo(expected.getFirstArg());
        assertWithMessage(message + " tag name")
            .that(actual.getTagName())
            .isEqualTo(expected.getTagName());
    }

    private static LocalizedMessage getUnknownTokenIdMessage(Object... arguments) {
        return new LocalizedMessage(
            Definitions.CHECKSTYLE_BUNDLE,
            JavadocUtil.class,
            "util.javadoc.unknownTokenId",
            arguments
        );
    }

    private static LocalizedMessage getUnknownTokenNameMessage(Object... arguments) {
        return new LocalizedMessage(
            Definitions.CHECKSTYLE_BUNDLE,
            JavadocUtil.class,
            "util.javadoc.unknownTokenName",
            arguments
        );
    }

}
