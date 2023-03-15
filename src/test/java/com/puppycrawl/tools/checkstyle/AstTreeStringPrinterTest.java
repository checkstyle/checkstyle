///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AstTreeStringPrinterTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/asttreestringprinter";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(AstTreeStringPrinter.class))
                .isTrue();
    }

    @Test
    public void testParseFileThrowable() throws Exception {
        final File input = new File(getNonCompilablePath("InputAstTreeStringPrinter.java"));
        try {
            AstTreeStringPrinter.printFileAst(input, JavaParser.Options.WITHOUT_COMMENTS);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid class")
                .that(ex.getCause())
                .isInstanceOf(IllegalStateException.class);
            assertWithMessage("Invalid exception message")
                .that(ex.getCause().getMessage())
                .isEqualTo("2:0: no viable alternative at input 'classD'");
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
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "`--CLASS_DEF -> CLASS_DEF [3:0]",
            "    |--MODIFIERS -> MODIFIERS [3:0]",
            "    |   `--LITERAL_PUBLIC -> public [3:0]");
        final DetailAST nodeToPrint = ast.getFirstChild().getNextSibling()
                .getFirstChild().getFirstChild();
        final String result = AstTreeStringPrinter.printBranch(nodeToPrint);
        assertWithMessage("Branches do not match")
            .that(result)
            .isEqualTo(expected);
    }

    @Test
    public void testPrintAst() throws Exception {
        final FileText text = new FileText(
                new File(getPath("InputAstTreeStringPrinterComments.java")).getAbsoluteFile(),
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        final String actual = toLfLineEnding(AstTreeStringPrinter.printAst(text,
                JavaParser.Options.WITHOUT_COMMENTS));
        final String expected = toLfLineEnding(Files.readString(Paths.get(
                getPath("ExpectedAstTreeStringPrinter.txt"))));

        assertWithMessage("Print AST output is invalid")
            .that(actual)
            .isEqualTo(expected);
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

    @Test
    public void testTextBlocksEscapesAreOneChar() throws Exception {
        final String inputFilename = "InputAstTreeStringPrinterTextBlocksEscapesAreOneChar.java";
        final DetailAST ast = JavaParser.parseFile(
                new File(getNonCompilablePath(inputFilename)), JavaParser.Options.WITHOUT_COMMENTS)
                .getFirstChild();

        final DetailAST objectBlockNode = ast.findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST variableDefNode = objectBlockNode.findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST textBlockContentNode =
                variableDefNode.findFirstToken(TokenTypes.ASSIGN)
                        .findFirstToken(TokenTypes.EXPR)
                        .getFirstChild()
                        .findFirstToken(TokenTypes.TEXT_BLOCK_CONTENT);

        final String textBlockContent = textBlockContentNode.getText();

        assertWithMessage("Text block content contains \"\\n\" as substring")
            .that(textBlockContent)
            .doesNotContain("\\n");
        assertWithMessage("Text block content line terminator is counted as one character")
            .that(textBlockContent)
            .hasLength(1);
        assertWithMessage("Text block content contains only a line terminator")
            .that(textBlockContent)
            .matches("\n");
    }

}
