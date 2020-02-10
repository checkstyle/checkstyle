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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_ILLEGAL_FOLLOW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_PRECEDED;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
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
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = {
            "16:14: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "16:14: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "16:24: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "16:44: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "16:44: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "16:54: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "17:14: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "17:14: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "17:21: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "17:21: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "17:31: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "17:31: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "17:33: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "17:53: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "17:53: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "17:60: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "17:60: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "17:70: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "17:70: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "17:72: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "30:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "30:20: " + getCheckMessage(MSG_WS_ILLEGAL_FOLLOW, ">"),
            "42:22: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "42:29: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "60:59: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "&"),
            "63:59: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "81:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "82:34: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "83:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "83:41: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "86:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "87:35: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "88:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "88:42: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
        };
        verify(checkConfig, getPath("InputGenericWhitespaceDefault.java"), expected);
    }

    @Test
    public void testAtTheStartOfTheLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = {
            "10:2: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "12:2: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
        };
        verify(checkConfig, getPath("InputGenericWhitespaceAtStartOfTheLine.java"), expected);
    }

    @Test
    public void testNestedGeneric() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "&"),
        };
        verify(checkConfig, getPath("InputGenericWhitespaceNested.java"), expected);
    }

    @Test
    public void testList() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputGenericWhitespaceList.java"), expected);
    }

    @Test
    public void testInnerClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputGenericWhitespaceInnerClass.java"), expected);
    }

    @Test
    public void testMethodReferences() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputGenericWhitespaceMethodRef1.java"), expected);
    }

    @Test
    public void testMethodReferences2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = {
            "10:69: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
        };
        verify(checkConfig, getPath("InputGenericWhitespaceMethodRef2.java"), expected);
    }

    @Test
    public void testGenericEndsTheLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputGenericWhitespaceEndsTheLine.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final GenericWhitespaceCheck genericWhitespaceCheckObj = new GenericWhitespaceCheck();
        final int[] actual = genericWhitespaceCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
        };
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testWrongTokenType() {
        final GenericWhitespaceCheck genericWhitespaceCheckObj = new GenericWhitespaceCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.INTERFACE_DEF, "interface"));
        try {
            genericWhitespaceCheckObj.visitToken(ast);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unknown type interface[0x-1]", ex.getMessage(),
                    "Invalid exception message");
        }
    }

}
