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

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import antlr.NoViableAltException;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;

public class AstTreeStringPrinterTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/astprinter";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(AstTreeStringPrinter.class, true);
    }

    @Test
    public void testParseFileThrowable() throws Exception {
        final File input = new File(getNonCompilablePath("InputAstTreeStringPrinter.java"));
        try {
            AstTreeStringPrinter.printFileAst(input,
                    AstTreeStringPrinter.PrintOptions.WITHOUT_COMMENTS);
            Assert.fail("exception expected");
        }
        catch (CheckstyleException ex) {
            Assert.assertSame("Invalid class",
                    NoViableAltException.class, ex.getCause().getClass());
            Assert.assertEquals("Invalid exception message",
                    input.getAbsolutePath() + ":1:1: unexpected token: classD",
                    ex.getCause().toString());
        }
    }

    @Test
    public void testParseFile() throws Exception {
        verifyAst(getPath("expectedInputAstTreeStringPrinter.txt"),
                getPath("InputAstTreeStringPrinterComments.java"),
                AstTreeStringPrinter.PrintOptions.WITHOUT_COMMENTS);
    }

    @Test
    public void testPrintAst() throws Exception {
        final FileText text = new FileText(
                new File(getPath("InputAstTreeStringPrinterComments.java")).getAbsoluteFile(),
                System.getProperty("file.encoding", "UTF-8"));
        final String actual = AstTreeStringPrinter.printAst(text,
                AstTreeStringPrinter.PrintOptions.WITHOUT_COMMENTS);
        final String expected = new String(Files.readAllBytes(Paths.get(
                getPath("expectedInputAstTreeStringPrinter.txt"))), StandardCharsets.UTF_8);

        Assert.assertEquals("Print AST output is invalid", expected, actual);
    }

    @Test
    public void testParseFileWithComments() throws Exception {
        verifyAst(getPath("expectedInputAstTreeStringPrinterComments.txt"),
                getPath("InputAstTreeStringPrinterComments.java"),
                AstTreeStringPrinter.PrintOptions.WITH_COMMENTS);
    }

    @Test
    public void testParseFileWithJavadoc1() throws Exception {
        verifyJavaAndJavadocAst(getPath("expectedInputAstTreeStringPrinterJavadoc.txt"),
                getPath("InputAstTreeStringPrinterJavadoc.java"));
    }

    @Test
    public void testParseFileWithJavadoc2() throws Exception {
        verifyJavaAndJavadocAst(getPath("expectedInputAstTreeStringPrinterJavaAndJavadoc.txt"),
                getPath("InputAstTreeStringPrinterJavaAndJavadoc.java"));
    }

    @Test
    public void testParseFileWithJavadoc3() throws Exception {
        verifyJavaAndJavadocAst(
                getPath("expectedInputAstTreeStringPrinterAttributesAndMethodsJavadoc.txt"),
                getPath("InputAstTreeStringPrinterAttributesAndMethodsJavadoc.java")
        );
    }

    @Test
    public void testJavadocPosition() throws Exception {
        verifyJavaAndJavadocAst(getPath("expectedJavadocPosition.txt"),
                getPath("InputJavadocPosition.java"));
    }

    @Test
    public void testAstTreeBlockComments() throws Exception {
        verifyAst(getPath("InputFullOfBlockCommentsAst.txt"),
                getPath("InputFullOfBlockComments.java"),
                AstTreeStringPrinter.PrintOptions.WITH_COMMENTS);
    }

    @Test
    public void testAstTreeBlockCommentsCarriageReturn() throws Exception {
        verifyAst(getPath("InputFullOfBlockCommentsAstCR.txt"),
                getPath("InputFullOfBlockCommentsCR.java"),
                AstTreeStringPrinter.PrintOptions.WITH_COMMENTS);
    }

    @Test
    public void testAstTreeSingleLineComments() throws Exception {
        verifyAst(getPath("InputFullOfSinglelineCommentsAst.txt"),
                getPath("InputFullOfSinglelineComments.java"),
                AstTreeStringPrinter.PrintOptions.WITH_COMMENTS);
    }
}
