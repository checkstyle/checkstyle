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

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class DetailNodeTreeStringPrinterTest extends AbstractPathTestSupport {

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
        final String actual = DetailNodeTreeStringPrinter.printFileAst(
            new File(getPath("InputJavadocComment.javadoc")))
                .replaceAll("\\\\r\\\\n", "\\\\n");
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("expectedInputJavadocComment.txt"))), StandardCharsets.UTF_8)
            .replaceAll("\\\\r\\\\n", "\\\\n");
        assertEquals("Invalid parsing result", expected, actual);
    }

    @Test
    public void testParseFileWithError() throws Exception {
        LocalizedMessage.setLocale(Locale.ROOT);
        try {
            DetailNodeTreeStringPrinter.printFileAst(
                    new File(getPath("InputJavadocWithError.javadoc")));
            Assert.fail("Javadoc parser didn't failed on missing end tag");
        }
        catch (IllegalArgumentException ex) {
            final String expected = "[ERROR:0] Javadoc comment at column 1 has parse error. "
                    + "Missed HTML close tag 'qwe'. Sometimes it means that close tag missed "
                    + "for one of previous tags.";
            assertEquals("Invalidexception message", expected, ex.getMessage());
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
        final String actual = DetailNodeTreeStringPrinter.printFileAst(
                new File(getPath("InputNoUnnecessaryTextInJavadocAst.javadoc")))
                .replaceAll("\\\\r\\\\n", "\\\\n");
        final String expected = new String(Files.readAllBytes(Paths.get(
                getPath("expectedNoUnnecessaryTextInJavadocAst.txt"))), StandardCharsets.UTF_8)
                .replaceAll("\\\\r\\\\n", "\\\\n");
        assertEquals("Invalid parsing result", expected, actual);
    }
}
