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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.AbstractPathTestSupport.addEndOfLine;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static com.puppycrawl.tools.checkstyle.utils.XpathUtil.getTextAttributeValue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.xpath.AbstractNode;
import com.puppycrawl.tools.checkstyle.xpath.RootNode;

public class XpathUtilTest {

    @TempDir
    public File tempFolder;

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(XpathUtil.class))
                .isTrue();
    }

    @Test
    public void testSupportsTextAttribute() {
        assertWithMessage("Should return true for supported token types")
                .that(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.IDENT)))
                .isTrue();
        assertWithMessage("Should return true for supported token types")
                .that(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.NUM_INT)))
                .isTrue();
        assertWithMessage("Should return true for supported token types")
                .that(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.STRING_LITERAL)))
                .isTrue();
        assertWithMessage("Should return true for supported token types")
                .that(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.CHAR_LITERAL)))
                .isTrue();
        assertWithMessage("Should return true for supported token types")
                .that(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.NUM_DOUBLE)))
                .isTrue();
        assertWithMessage("Should return false for unsupported token types")
                .that(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.VARIABLE_DEF)))
                .isFalse();
        assertWithMessage("Should return false for unsupported token types")
                .that(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.OBJBLOCK)))
                .isFalse();
        assertWithMessage("Should return true for supported token types")
                .that(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.LITERAL_CHAR)))
                .isFalse();
    }

    @Test
    public void testGetValue() {
        assertWithMessage("Returned value differs from expected")
            .that(getTextAttributeValue(
                createDetailAST(TokenTypes.STRING_LITERAL, "\"HELLO WORLD\"")))
            .isEqualTo("HELLO WORLD");
        assertWithMessage("Returned value differs from expected")
            .that(getTextAttributeValue(createDetailAST(TokenTypes.NUM_INT, "123")))
            .isEqualTo("123");
        assertWithMessage("Returned value differs from expected")
            .that(getTextAttributeValue(createDetailAST(TokenTypes.IDENT, "HELLO WORLD")))
            .isEqualTo("HELLO WORLD");
        assertWithMessage("Returned value differs from expected")
            .that(getTextAttributeValue(createDetailAST(TokenTypes.STRING_LITERAL, "HELLO WORLD")))
            .isNotEqualTo("HELLO WORLD");
    }

    @Test
    public void testPrintXpathNotComment() throws Exception {
        final String fileContent = "class Test { public void method() {int a = 5;}}";
        final File file = File.createTempFile("junit", null, tempFolder);
        Files.write(file.toPath(), fileContent.getBytes(StandardCharsets.UTF_8));
        final String expected = addEndOfLine(
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "`--CLASS_DEF -> CLASS_DEF [1:0]",
            "    `--OBJBLOCK -> OBJBLOCK [1:11]",
            "        |--METHOD_DEF -> METHOD_DEF [1:13]",
            "        |   `--SLIST -> { [1:34]",
            "        |       |--VARIABLE_DEF -> VARIABLE_DEF [1:35]",
            "        |       |   |--IDENT -> a [1:39]");
        final String result = XpathUtil.printXpathBranch(
            "//CLASS_DEF//METHOD_DEF//VARIABLE_DEF//IDENT", file);
        assertWithMessage("Branch string is different")
            .that(result)
            .isEqualTo(expected);
    }

    @Test
    public void testPrintXpathComment() throws Exception {
        final String fileContent = "class Test { /* comment */ }";
        final File file = File.createTempFile("junit", null, tempFolder);
        Files.write(file.toPath(), fileContent.getBytes(StandardCharsets.UTF_8));
        final String expected = addEndOfLine(
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "`--CLASS_DEF -> CLASS_DEF [1:0]",
            "    `--OBJBLOCK -> OBJBLOCK [1:11]",
            "        |--BLOCK_COMMENT_BEGIN -> /* [1:13]");
        final String result = XpathUtil.printXpathBranch(
            "//CLASS_DEF//BLOCK_COMMENT_BEGIN", file);
        assertWithMessage("Branch string is different")
            .that(result)
            .isEqualTo(expected);
    }

    @Test
    public void testPrintXpathTwo() throws Exception {
        final String fileContent = "class Test { public void method() {int a = 5; int b = 5;}}";
        final File file = File.createTempFile("junit", null, tempFolder);
        Files.write(file.toPath(), fileContent.getBytes(StandardCharsets.UTF_8));
        final String expected = addEndOfLine(
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "`--CLASS_DEF -> CLASS_DEF [1:0]",
            "    `--OBJBLOCK -> OBJBLOCK [1:11]",
            "        |--METHOD_DEF -> METHOD_DEF [1:13]",
            "        |   `--SLIST -> { [1:34]",
            "        |       |--VARIABLE_DEF -> VARIABLE_DEF [1:35]",
            "        |       |   |--IDENT -> a [1:39]",
            "---------",
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "`--CLASS_DEF -> CLASS_DEF [1:0]",
            "    `--OBJBLOCK -> OBJBLOCK [1:11]",
            "        |--METHOD_DEF -> METHOD_DEF [1:13]",
            "        |   `--SLIST -> { [1:34]",
            "        |       |--VARIABLE_DEF -> VARIABLE_DEF [1:46]",
            "        |       |   |--IDENT -> b [1:50]");
        final String result = XpathUtil.printXpathBranch(
            "//CLASS_DEF//METHOD_DEF//VARIABLE_DEF//IDENT", file);
        assertWithMessage("Branch string is different")
            .that(result)
            .isEqualTo(expected);
    }

    @Test
    public void testInvalidXpath() throws IOException {
        final String fileContent = "class Test { public void method() {int a = 5; int b = 5;}}";
        final File file = File.createTempFile("junit", null, tempFolder);
        Files.write(file.toPath(), fileContent.getBytes(StandardCharsets.UTF_8));
        final String invalidXpath = "\\//CLASS_DEF"
                + "//METHOD_DEF//VARIABLE_DEF//IDENT";
        try {
            XpathUtil.printXpathBranch(invalidXpath, file);
            assertWithMessage("Should end with exception").fail();
        }
        catch (CheckstyleException ex) {
            final String expectedMessage =
                "Error during evaluation for xpath: " + invalidXpath
                    + ", file: " + file.getCanonicalPath();
            assertWithMessage("Exception message is different")
                .that(ex.getMessage())
                .isEqualTo(expectedMessage);
        }
    }

    @Test
    public void testCreateChildren() {
        final DetailAstImpl rootAst = new DetailAstImpl();
        final DetailAstImpl elementAst = new DetailAstImpl();
        rootAst.addChild(elementAst);
        final RootNode rootNode = new RootNode(rootAst);
        final List<AbstractNode> children =
                XpathUtil.createChildren(rootNode, rootNode, elementAst);

        assertWithMessage("Expected one child node")
                .that(children)
                .hasSize(1);
        assertWithMessage("Node depth should be 1")
                .that(children.get(0).getDepth())
                .isEqualTo(1);
    }

    private static DetailAST createDetailAST(int type) {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(type);
        return detailAST;
    }

    private static DetailAST createDetailAST(int type, String text) {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(type);
        detailAST.setText(text);
        return detailAST;
    }
}
