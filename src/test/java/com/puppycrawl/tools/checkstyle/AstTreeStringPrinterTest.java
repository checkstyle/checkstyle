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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import antlr.NoViableAltException;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileText;

public class AstTreeStringPrinterTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/asttreestringprinter";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(AstTreeStringPrinter.class, true),
                "Constructor is not private");
    }

    @Test
    public void testParseFileThrowable() throws Exception {
        final File input = new File(getNonCompilablePath("InputAstTreeStringPrinter.java"));
        try {
            AstTreeStringPrinter.printFileAst(input, JavaParser.Options.WITHOUT_COMMENTS);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertSame(NoViableAltException.class, ex.getCause().getClass(), "Invalid class");
            assertEquals(input.getAbsolutePath() + ":2:1: unexpected token: classD",
                    ex.getCause().toString(), "Invalid exception message");
        }
    }

    @Test
    public void testParseFile() throws Exception {
        verifyAst(getPath("ExpectedAstTreeStringPrinter.txt"),
                getPath("InputAstTreeStringPrinterComments.java"),
                JavaParser.Options.WITHOUT_COMMENTS);
    }

    @Test
    public void testPrintBranch() throws Exception {
        final DetailAST ast = JavaParser.parseFile(
            new File(getPath("InputAstTreeStringPrinterPrintBranch.java")),
            JavaParser.Options.WITH_COMMENTS);
        final String expected = addEndOfLine(
            "CLASS_DEF -> CLASS_DEF [3:0]",
            "|--MODIFIERS -> MODIFIERS [3:0]",
            "|   `--LITERAL_PUBLIC -> public [3:0]");
        final DetailAST nodeToPrint = ast.getNextSibling().getFirstChild().getFirstChild();
        final String result = AstTreeStringPrinter.printBranch(nodeToPrint);
        assertThat("Branches do not match", result, is(expected));
    }

    @Test
    public void testPrintAst() throws Exception {
        final FileText text = new FileText(
                new File(getPath("InputAstTreeStringPrinterComments.java")).getAbsoluteFile(),
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        final String actual = toLfLineEnding(AstTreeStringPrinter.printAst(text,
                JavaParser.Options.WITHOUT_COMMENTS));
        final String expected = toLfLineEnding(new String(Files.readAllBytes(Paths.get(
                getPath("ExpectedAstTreeStringPrinter.txt"))), StandardCharsets.UTF_8));

        assertEquals(expected, actual, "Print AST output is invalid");
    }

    @Test
    public void testParseFileWithComments() throws Exception {
        verifyAst(getPath("ExpectedAstTreeStringPrinterComments.txt"),
                getPath("InputAstTreeStringPrinterComments.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testParseFileWithJavadoc1() throws Exception {
        verifyJavaAndJavadocAst(getPath("ExpectedAstTreeStringPrinterJavadoc.txt"),
                getPath("InputAstTreeStringPrinterJavadoc.java"));
    }

    @Test
    public void testParseFileWithJavadoc2() throws Exception {
        verifyJavaAndJavadocAst(getPath("ExpectedAstTreeStringPrinterJavaAndJavadoc.txt"),
                getPath("InputAstTreeStringPrinterJavaAndJavadoc.java"));
    }

    @Test
    public void testParseFileWithJavadoc3() throws Exception {
        verifyJavaAndJavadocAst(
                getPath("ExpectedAstTreeStringPrinterAttributesAndMethodsJavadoc.txt"),
                getPath("InputAstTreeStringPrinterAttributesAndMethodsJavadoc.java")
        );
    }

    @Test
    public void testJavadocPosition() throws Exception {
        verifyJavaAndJavadocAst(getPath("ExpectedAstTreeStringPrinterJavadocPosition.txt"),
                getPath("InputAstTreeStringPrinterJavadocPosition.java"));
    }

    @Test
    public void testAstTreeBlockComments() throws Exception {
        verifyAst(getPath("ExpectedAstTreeStringPrinterFullOfBlockComments.txt"),
                getPath("InputAstTreeStringPrinterFullOfBlockComments.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testAstTreeBlockCommentsCarriageReturn() throws Exception {
        verifyAst(getPath("ExpectedAstTreeStringPrinterFullOfBlockCommentsCR.txt"),
                getPath("InputAstTreeStringPrinterFullOfBlockCommentsCR.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testAstTreeSingleLineComments() throws Exception {
        verifyAst(getPath("ExpectedAstTreeStringPrinterFullOfSinglelineComments.txt"),
                getPath("InputAstTreeStringPrinterFullOfSinglelineComments.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

}
