////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.WS_ILLEGAL_FOLLOW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.WS_PRECEDED;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import antlr.CommonHiddenStreamToken;

import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class GenericWhitespaceCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(GenericWhitespaceCheck.class);
        final Map<Class<?>, Integer> x = Maps.newHashMap();
        for (final Map.Entry<Class<?>, Integer> entry : x.entrySet()) {
            entry.getValue();
        }
        //for (final Entry<Class<?>, Integer> entry : entrySet())
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final GenericWhitespaceCheck checkObj = new GenericWhitespaceCheck();
        final int[] expected = {
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
        };
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(WS_PRECEDED, "<"),
            "16:15: " + getCheckMessage(WS_FOLLOWED, "<"),
            "16:23: " + getCheckMessage(WS_PRECEDED, ">"),
            "16:43: " + getCheckMessage(WS_PRECEDED, "<"),
            "16:45: " + getCheckMessage(WS_FOLLOWED, "<"),
            "16:53: " + getCheckMessage(WS_PRECEDED, ">"),
            "17:13: " + getCheckMessage(WS_PRECEDED, "<"),
            "17:15: " + getCheckMessage(WS_FOLLOWED, "<"),
            "17:20: " + getCheckMessage(WS_PRECEDED, "<"),
            "17:22: " + getCheckMessage(WS_FOLLOWED, "<"),
            "17:30: " + getCheckMessage(WS_PRECEDED, ">"),
            "17:32: " + getCheckMessage(WS_FOLLOWED, ">"),
            "17:32: " + getCheckMessage(WS_PRECEDED, ">"),
            "17:52: " + getCheckMessage(WS_PRECEDED, "<"),
            "17:54: " + getCheckMessage(WS_FOLLOWED, "<"),
            "17:59: " + getCheckMessage(WS_PRECEDED, "<"),
            "17:61: " + getCheckMessage(WS_FOLLOWED, "<"),
            "17:69: " + getCheckMessage(WS_PRECEDED, ">"),
            "17:71: " + getCheckMessage(WS_FOLLOWED, ">"),
            "17:71: " + getCheckMessage(WS_PRECEDED, ">"),
            "30:17: " + getCheckMessage(WS_NOT_PRECEDED, "<"),
            "30:21: " + getCheckMessage(WS_ILLEGAL_FOLLOW, ">"),
            "42:21: " + getCheckMessage(WS_PRECEDED, "<"),
            "42:30: " + getCheckMessage(WS_FOLLOWED, ">"),
            "60:60: " + getCheckMessage(WS_NOT_PRECEDED, "&"),
            "63:60: " + getCheckMessage(WS_FOLLOWED, ">"),
        };
        verify(checkConfig, getPath("InputGenericWhitespace.java"), expected);
    }

    @Test
    public void testGh47() throws Exception {
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputGh47.java"), expected);
    }

    @Test
    public void testInnerClass() throws Exception {
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputGenericWhitespaceInnerClass.java"), expected);
    }

    @Test
    public void testMethodReferences() throws Exception {
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputMethodReferences3.java"), expected);
    }

    @Test
    public void testMethodReferences2() throws Exception {
        final String[] expected = {
            "7:69: " + getCheckMessage(WS_FOLLOWED, ">"),
        };
        verify(checkConfig, getNonCompilablePath("InputGenericWhitespaceMethodRef.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final GenericWhitespaceCheck genericWhitespaceCheckObj = new GenericWhitespaceCheck();
        final int[] actual = genericWhitespaceCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
        };
        assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongTokenType() {
        final GenericWhitespaceCheck genericWhitespaceCheckObj = new GenericWhitespaceCheck();
        final DetailAST ast = new DetailAST();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.INTERFACE_DEF, "interface"));
        genericWhitespaceCheckObj.visitToken(ast);
    }
}
