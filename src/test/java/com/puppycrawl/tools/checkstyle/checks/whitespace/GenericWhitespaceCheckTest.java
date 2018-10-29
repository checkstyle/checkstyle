////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
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
        assertArrayEquals("Default required tokens are invalid",
            expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "16:15: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "16:23: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "16:43: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "16:45: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "16:53: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "17:13: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "17:15: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "17:20: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "17:22: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "17:30: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "17:32: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "17:32: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "17:52: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "17:54: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "17:59: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "17:61: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "17:69: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "17:71: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "17:71: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "30:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "30:21: " + getCheckMessage(MSG_WS_ILLEGAL_FOLLOW, ">"),
            "42:21: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "42:30: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
            "60:60: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "&"),
            "63:60: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
        };
        verify(checkConfig, getPath("InputGenericWhitespaceDefault.java"), expected);
    }

    @Test
    public void testAtTheStartOfTheLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
            "12:1: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
        };
        verify(checkConfig, getPath("InputGenericWhitespaceAtStartOfTheLine.java"), expected);
    }

    @Test
    public void testNestedGeneric() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(GenericWhitespaceCheck.class);
        final String[] expected = {
            "11:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "&"),
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
            "10:70: " + getCheckMessage(MSG_WS_FOLLOWED, ">"),
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
        assertArrayEquals("Default acceptable tokens are invalid", expected, actual);
    }

    @Test
    public void testWrongTokenType() {
        final GenericWhitespaceCheck genericWhitespaceCheckObj = new GenericWhitespaceCheck();
        final DetailAST ast = new DetailAST();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.INTERFACE_DEF, "interface"));
        try {
            genericWhitespaceCheckObj.visitToken(ast);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                "Unknown type interface[0x-1]", ex.getMessage());
        }
    }

}
