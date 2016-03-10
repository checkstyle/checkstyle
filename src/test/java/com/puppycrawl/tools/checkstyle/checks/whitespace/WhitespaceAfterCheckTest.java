////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck.MSG_WS_TYPECAST;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class WhitespaceAfterCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(WhitespaceAfterCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final WhitespaceAfterCheck checkObj = new WhitespaceAfterCheck();
        assertArrayEquals(CommonUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "42:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "71:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testCast() throws Exception {
        final String[] expected = {
            "88:21: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testSemi() throws Exception {
        final String[] expected = {
            "54:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "54:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "103:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testEmptyForIterator() throws Exception {
        final String[] expected = {
            "14:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "17:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testTypeArgumentAndParameterCommas() throws Exception {
        final String[] expected = {
            "11:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "11:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "11:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verify(checkConfig, getPath("InputGenerics.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWhitespaceAround.java"),
               expected);
    }
}
