///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_ILLEGAL_FOLLOW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_PRECEDED;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class GenericWhitespaceCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/genericwhitespace";
    }

    @Test
    public void testGetRequiredTokens() {
        final GenericWhitespaceCheck checkObj = new GenericWhitespaceCheck();
        final int[] expected = {
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "22:14: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "22:14: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "22:24: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "22:44: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "22:44: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "22:54: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "22:54: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "23:14: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "23:14: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "23:21: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "23:21: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "23:31: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "23:31: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "23:33: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "23:53: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "23:53: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "23:60: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "23:60: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "23:70: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "23:70: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "23:72: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "23:72: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "36:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "36:20: " + getCheckMessage(MSG_WS_ILLEGAL_FOLLOW, ">"),
            "48:22: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "48:29: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "66:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "&"),
            "69:35: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "87:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "88:34: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "89:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "89:41: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "92:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "93:35: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "94:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "94:42: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceDefault.java"), expected);
    }

    @Test
    public void testAtTheStartOfTheLine() throws Exception {
        final String[] expected = {
            "16:2: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "18:2: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceAtStartOfTheLine.java"), expected);
    }

    @Test
    public void testNestedGeneric() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "&"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceNested.java"), expected);
    }

    @Test
    public void testList() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceList.java"), expected);
    }

    @Test
    public void testInnerClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceInnerClass.java"), expected);
    }

    @Test
    public void testMethodReferences() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceMethodRef1.java"), expected);
    }

    @Test
    public void testMethodReferences2() throws Exception {
        final String[] expected = {
            "16:37: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceMethodRef2.java"), expected);
    }

    @Test
    public void testGenericEndsTheLine() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceEndsTheLine.java"), expected);
    }

    @Test
    public void testGenericWhitespaceWithEmoji() throws Exception {
        final String[] expected = {
            "35:2: " + getCheckMessage(MSG_WS_PRECEDED, '>'),
            "40:35: " + getCheckMessage(MSG_WS_PRECEDED, '<'),
            "40:42: " + getCheckMessage(MSG_WS_FOLLOWED, '>'),
            "44:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, '<'),
            "45:53: " + getCheckMessage(MSG_WS_PRECEDED, '<'),
        };
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceWithEmoji.java"), expected);
    }

    @Test
    public void testBeforeCtorInvocation() throws Exception {
        final String[] expected = {
            "17:31: " + getCheckMessage(MSG_WS_FOLLOWED, '>'),
            "19:56: " + getCheckMessage(MSG_WS_FOLLOWED, '>'),
            "19:56: " + getCheckMessage(MSG_WS_PRECEDED, '>'),
            "21:25: " + getCheckMessage(MSG_WS_FOLLOWED, '<'),
            "24:36: " + getCheckMessage(MSG_WS_FOLLOWED, '>'),
            "28:35: " + getCheckMessage(MSG_WS_PRECEDED, '<'),
            "28:35: " + getCheckMessage(MSG_WS_FOLLOWED, '<'),
            "28:47: " + getCheckMessage(MSG_WS_PRECEDED, '>'),
            "28:47: " + getCheckMessage(MSG_WS_FOLLOWED, '>'),
            "30:34: " + getCheckMessage(MSG_WS_FOLLOWED, '>'),
        };
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceBeforeCtorInvocation.java"), expected);
    }

    @Test
    public void testAfterNew() throws Exception {
        final String[] expected = {
            "17:30: " + getCheckMessage(MSG_WS_FOLLOWED, '>'),
            "21:12: " + getCheckMessage(MSG_WS_NOT_PRECEDED, '<'),
            "21:12: " + getCheckMessage(MSG_WS_FOLLOWED, '<'),
            "21:23: " + getCheckMessage(MSG_WS_PRECEDED, '>'),
            "21:23: " + getCheckMessage(MSG_WS_FOLLOWED, '>'),
            "22:22: " + getCheckMessage(MSG_WS_FOLLOWED, '>'),
            "27:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, '<'),
            "27:31: " + getCheckMessage(MSG_WS_FOLLOWED, '<'),
            "27:40: " + getCheckMessage(MSG_WS_PRECEDED, '>'),
            "27:40: " + getCheckMessage(MSG_WS_FOLLOWED, '>'),
        };
        verifyWithInlineConfigParser(
                getPath("InputGenericWhitespaceAfterNew.java"), expected);
    }

    @Test
    public void testBeforeRecordHeader() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputGenericWhitespaceBeforeRecordHeader.java"),
                expected);
    }

    /**
     * Checks if the private field {@code depth} is properly cleared during the
     * start of processing the next file in the check.
     *
     * @throws Exception if there is an error.
     */
    @Test
    public void testClearState() throws Exception {
        final GenericWhitespaceCheck check = new GenericWhitespaceCheck();
        final FileText fileText = new FileText(
                new File(getPath("InputGenericWhitespaceDefault.java")),
                StandardCharsets.UTF_8.name());
        check.setFileContents(new FileContents(fileText));
        final DetailAST root = JavaParser.parseFileText(fileText,
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> genericStart = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.GENERIC_START);

        assertWithMessage("Ast should contain GENERIC_START")
                .that(genericStart.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(
                    TestUtil.isStatefulFieldClearedDuringBeginTree(check, genericStart.get(),
                            "depth",
                            depth -> ((Number) depth).intValue() == 0))
                .isTrue();
    }

    @Test
    public void testGetAcceptableTokens() {
        final GenericWhitespaceCheck genericWhitespaceCheckObj = new GenericWhitespaceCheck();
        final int[] actual = genericWhitespaceCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testWrongTokenType() {
        final GenericWhitespaceCheck genericWhitespaceCheckObj = new GenericWhitespaceCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonToken(TokenTypes.INTERFACE_DEF, "interface"));
        try {
            genericWhitespaceCheckObj.visitToken(ast);
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Unknown type interface[0x-1]");
        }
    }

}
