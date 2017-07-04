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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_MISSED_HTML_CLOSE;
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_PARSE_RULE_ERROR;
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_WRONG_SINGLETON_TAG;
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.ParseErrorMessage;
import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class DetailNodeTreeStringPrinterTest extends AbstractTreeTestSupport {

    // [REFLECTION]
    // DetailNodeTreeStringPrinter#getParseErrorMessage is used for creating error messages
    // for validating those obtained in UTs against the ones created.
    private static final Method GET_PARSE_ERROR_MESSAGE = Whitebox.getMethod(
            DetailNodeTreeStringPrinter.class, "getParseErrorMessage", ParseErrorMessage.class);

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/astprinter";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(DetailNodeTreeStringPrinter.class, true);
    }

    @Test
    public void testParseFile() throws Exception {
        verifyJavadocTree(getPath("expectedInputJavadocComment.txt"),
                getPath("InputJavadocComment.javadoc"));
    }

    @Test
    public void testParseFileWithError() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(
                    new File(getPath("InputJavadocWithError.javadoc")));
            Assert.fail("Javadoc parser didn't fail on missing end tag");
        }
        catch (IllegalArgumentException ex) {
            final String expected = (String) GET_PARSE_ERROR_MESSAGE.invoke(null,
                    new ParseErrorMessage(0, MSG_JAVADOC_MISSED_HTML_CLOSE, 1, "qwe"));
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

    @Test
    public void testCreationOfFakeCommentBlock() throws Exception {
        final Method createFakeBlockComment =
                Whitebox.getMethod(DetailNodeTreeStringPrinter.class,
                        "createFakeBlockComment", String.class);

        final DetailAST testCommentBlock =
                (DetailAST) createFakeBlockComment.invoke(null, "test_comment");
        assertEquals("Invalid token type",
                TokenTypes.BLOCK_COMMENT_BEGIN, testCommentBlock.getType());
        assertEquals("Invalid text", "/*", testCommentBlock.getText());
        assertEquals("Invalid line number", 0, testCommentBlock.getLineNo());

        final DetailAST contentCommentBlock = testCommentBlock.getFirstChild();
        assertEquals("Invalid tiken type",
                TokenTypes.COMMENT_CONTENT, contentCommentBlock.getType());
        assertEquals("Invalid text", "*test_comment", contentCommentBlock.getText());
        assertEquals("Invalid line number", 0, contentCommentBlock.getLineNo());
        assertEquals("Invalid column number", -1, contentCommentBlock.getColumnNo());

        final DetailAST endCommentBlock = contentCommentBlock.getNextSibling();
        assertEquals("Invalid tiken type", TokenTypes.BLOCK_COMMENT_END, endCommentBlock.getType());
        assertEquals("Invalid text", "*/", endCommentBlock.getText());
    }

    @Test
    public void testNoUnnecessaryTextinJavadocAst() throws Exception {
        verifyJavadocTree(getPath("expectedNoUnnecessaryTextInJavadocAst.txt"),
                getPath("InputNoUnnecessaryTextInJavadocAst.javadoc"));
    }

    @Test
    public void testMissedHtmlTagParseErrorMessage() throws Exception {
        final String actual = (String) GET_PARSE_ERROR_MESSAGE.invoke(null,
                new ParseErrorMessage(35, MSG_JAVADOC_MISSED_HTML_CLOSE, 7, "xyz"));
        final LocalizedMessage localizedMessage = new LocalizedMessage(
                35,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.messages",
                MSG_JAVADOC_MISSED_HTML_CLOSE,
                new Object[] {7, "xyz"},
                "",
                DetailNodeTreeStringPrinter.class,
                null);
        final String expected = "[ERROR:35] " + localizedMessage.getMessage();
        assertEquals("Javadoc parse error message for missed HTML tag doesn't meet expectations",
                expected, actual);
    }

    @Test
    public void testParseErrorMessage() throws Exception {
        final String actual = (String) GET_PARSE_ERROR_MESSAGE.invoke(null,
                new ParseErrorMessage(10, MSG_JAVADOC_PARSE_RULE_ERROR,
                        9, "no viable alternative at input ' xyz'", "SOME_JAVADOC_ELEMENT"));
        final LocalizedMessage localizedMessage = new LocalizedMessage(
                10,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.messages",
                MSG_JAVADOC_PARSE_RULE_ERROR,
                new Object[] {9, "no viable alternative at input ' xyz'", "SOME_JAVADOC_ELEMENT"},
                "",
                DetailNodeTreeStringPrinter.class,
                null);
        final String expected = "[ERROR:10] " + localizedMessage.getMessage();
        assertEquals("Javadoc parse error message doesn't meet expectations", expected, actual);
    }

    @Test
    public void testWrongSingletonParseErrorMessage() throws Exception {
        final String actual = (String) GET_PARSE_ERROR_MESSAGE.invoke(null,
                new ParseErrorMessage(100, MSG_JAVADOC_WRONG_SINGLETON_TAG,
                        9, "tag"));
        final LocalizedMessage localizedMessage = new LocalizedMessage(
                100,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.messages",
                MSG_JAVADOC_WRONG_SINGLETON_TAG,
                new Object[] {9, "tag"},
                "",
                DetailNodeTreeStringPrinter.class,
                null);
        final String expected = "[ERROR:100] " + localizedMessage.getMessage();
        assertEquals("Javadoc parse error message for void elements with close tag "
                + "doesn't meet expectations", expected, actual);
    }

    @Test
    public void testUnescapedJavaCodeWithGenericsInJavadoc() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(new File(getPath(
                    "InputUnescapedJavaCodeWithGenericsInJavadoc.javadoc")));
        }
        catch (IllegalArgumentException ex) {
            final String expected = (String) GET_PARSE_ERROR_MESSAGE.invoke(null,
                    new ParseErrorMessage(35, MSG_JAVADOC_MISSED_HTML_CLOSE, 7, "parsing"));
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

    @Test
    public void testNoViableAltException() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(new File(getPath(
                    "InputNoViableAltException.javadoc")));
        }
        catch (IllegalArgumentException ex) {
            final String expected = (String) GET_PARSE_ERROR_MESSAGE.invoke(null,
                    new ParseErrorMessage(0, MSG_JAVADOC_PARSE_RULE_ERROR,
                            9, "no viable alternative at input ' <<'", "JAVADOC_TAG"));
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

    @Test
    public void testHtmlTagCloseBeforeTagOpen() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(new File(getPath(
                    "InputHtmlTagCloseBeforeTagOpen.javadoc"
            )));
        }
        catch (IllegalArgumentException ex) {
            final String expected = (String) GET_PARSE_ERROR_MESSAGE.invoke(null,
                    new ParseErrorMessage(0, MSG_JAVADOC_PARSE_RULE_ERROR,
                            4, "no viable alternative at input '</tag'", "HTML_ELEMENT"));
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

    @Test
    public void testWrongHtmlTagOrder() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(new File(getPath(
                    "InputWrongHtmlTagOrder.javadoc"
            )));
        }
        catch (IllegalArgumentException ex) {
            final String expected = (String) GET_PARSE_ERROR_MESSAGE.invoke(null,
                    new ParseErrorMessage(0, MSG_JAVADOC_MISSED_HTML_CLOSE, 10, "tag2"));
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }

    @Test
    public void testOmittedStartTagForHtmlElement() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(new File(getPath(
                    "InputOmittedStartTagForHtmlElement.javadoc"
            )));
        }
        catch (IllegalArgumentException ex) {
            final String expected = (String) GET_PARSE_ERROR_MESSAGE.invoke(null,
                    new ParseErrorMessage(0, MSG_JAVADOC_MISSED_HTML_CLOSE, 3, "a"));
            assertEquals("Generated and expected parse error messages don't match",
                    expected, ex.getMessage());
        }
    }
}
