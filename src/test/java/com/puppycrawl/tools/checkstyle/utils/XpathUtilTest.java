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

import static com.puppycrawl.tools.checkstyle.AbstractPathTestSupport.addEndOfLine;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static com.puppycrawl.tools.checkstyle.utils.XpathUtil.getTextAttributeValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class XpathUtilTest {

    @TempDir
    public File tempFolder;

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(XpathUtil.class, true),
                "Constructor is not private");
    }

    @Test
    public void testSupportsTextAttribute() {
        assertTrue(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.IDENT)),
                "Should return true for supported token types");
        assertTrue(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.NUM_INT)),
                "Should return true for supported token types");
        assertTrue(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.STRING_LITERAL)),
                "Should return true for supported token types");
        assertTrue(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.CHAR_LITERAL)),
                "Should return true for supported token types");
        assertTrue(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.NUM_DOUBLE)),
                "Should return true for supported token types");
        assertFalse(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.VARIABLE_DEF)),
                "Should return false for unsupported token types");
        assertFalse(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.OBJBLOCK)),
                "Should return false for unsupported token types");
        assertFalse(XpathUtil.supportsTextAttribute(createDetailAST(TokenTypes.LITERAL_CHAR)),
                "Should return true for supported token types");
    }

    @Test
    public void testGetValue() {
        assertEquals("HELLO WORLD", getTextAttributeValue(
                createDetailAST(TokenTypes.STRING_LITERAL, "\"HELLO WORLD\"")),
                "Returned value differs from expected");
        assertEquals("123", getTextAttributeValue(createDetailAST(TokenTypes.NUM_INT, "123")),
                "Returned value differs from expected");
        assertEquals("HELLO WORLD",
                getTextAttributeValue(createDetailAST(TokenTypes.IDENT, "HELLO WORLD")),
                "Returned value differs from expected");
        assertNotEquals("HELLO WORLD",
                getTextAttributeValue(createDetailAST(TokenTypes.STRING_LITERAL, "HELLO WORLD")),
                "Returned value differs from expected");
    }

    @Test
    public void testPrintXpathNotComment() throws Exception {
        final String fileContent = "class Test { public void method() {int a = 5;}}";
        final File file = File.createTempFile("junit", null, tempFolder);
        Files.write(file.toPath(), fileContent.getBytes(StandardCharsets.UTF_8));
        final String expected = addEndOfLine(
            "CLASS_DEF -> CLASS_DEF [1:0]",
            "`--OBJBLOCK -> OBJBLOCK [1:11]",
            "    |--METHOD_DEF -> METHOD_DEF [1:13]",
            "    |   `--SLIST -> { [1:34]",
            "    |       |--VARIABLE_DEF -> VARIABLE_DEF [1:35]",
            "    |       |   |--IDENT -> a [1:39]");
        final String result = XpathUtil.printXpathBranch(
            "//CLASS_DEF//METHOD_DEF//VARIABLE_DEF//IDENT", file);
        assertThat("Branch string is different", result, is(expected));
    }

    @Test
    public void testPrintXpathComment() throws Exception {
        final String fileContent = "class Test { /* comment */ }";
        final File file = File.createTempFile("junit", null, tempFolder);
        Files.write(file.toPath(), fileContent.getBytes(StandardCharsets.UTF_8));
        final String expected = addEndOfLine(
            "CLASS_DEF -> CLASS_DEF [1:0]",
            "`--OBJBLOCK -> OBJBLOCK [1:11]",
            "    |--BLOCK_COMMENT_BEGIN -> /* [1:13]");
        final String result = XpathUtil.printXpathBranch(
            "//CLASS_DEF//BLOCK_COMMENT_BEGIN", file);
        assertThat("Branch string is different", result, is(expected));
    }

    @Test
    public void testPrintXpathTwo() throws Exception {
        final String fileContent = "class Test { public void method() {int a = 5; int b = 5;}}";
        final File file = File.createTempFile("junit", null, tempFolder);
        Files.write(file.toPath(), fileContent.getBytes(StandardCharsets.UTF_8));
        final String expected = addEndOfLine(
            "CLASS_DEF -> CLASS_DEF [1:0]",
            "`--OBJBLOCK -> OBJBLOCK [1:11]",
            "    |--METHOD_DEF -> METHOD_DEF [1:13]",
            "    |   `--SLIST -> { [1:34]",
            "    |       |--VARIABLE_DEF -> VARIABLE_DEF [1:35]",
            "    |       |   |--IDENT -> a [1:39]",
            "---------",
            "CLASS_DEF -> CLASS_DEF [1:0]",
            "`--OBJBLOCK -> OBJBLOCK [1:11]",
            "    |--METHOD_DEF -> METHOD_DEF [1:13]",
            "    |   `--SLIST -> { [1:34]",
            "    |       |--VARIABLE_DEF -> VARIABLE_DEF [1:46]",
            "    |       |   |--IDENT -> b [1:50]");
        final String result = XpathUtil.printXpathBranch(
            "//CLASS_DEF//METHOD_DEF//VARIABLE_DEF//IDENT", file);
        assertThat("Branch string is different", result, is(expected));
    }

    @Test
    public void testInvalidXpath() throws IOException {
        final String fileContent = "class Test { public void method() {int a = 5; int b = 5;}}";
        final File file = File.createTempFile("junit", null, tempFolder);
        Files.write(file.toPath(), fileContent.getBytes(StandardCharsets.UTF_8));
        final String invalidXpath = "\\//CLASS_DEF//METHOD_DEF//VARIABLE_DEF//IDENT";
        try {
            XpathUtil.printXpathBranch(invalidXpath, file);
            fail("Should end with exception");
        }
        catch (CheckstyleException ex) {
            final String expectedMessage =
                "Error during evaluation for xpath: " + invalidXpath
                    + ", file: " + file.getCanonicalPath();
            assertThat("Exception message is different", ex.getMessage(), is(expectedMessage));
        }
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
