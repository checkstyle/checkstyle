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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.SingleSpaceSeparatorCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SingleSpaceSeparatorCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/singlespaceseparator";
    }

    @Test
    public void testNoSpaceErrors() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(SingleSpaceSeparatorCheck.class);
        verify(checkConfig, getPath("InputSingleSpaceSeparatorNoErrors.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final SingleSpaceSeparatorCheck check = new SingleSpaceSeparatorCheck();

        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, check.getAcceptableTokens(),
                "Invalid acceptable tokens");
    }

    @Test
    public void testSpaceErrors() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(SingleSpaceSeparatorCheck.class);
        checkConfig.addAttribute("validateComments", "true");
        final String[] expected = {
            "1:10: " + getCheckMessage(MSG_KEY),
            "1:28: " + getCheckMessage(MSG_KEY),
            "4:9: " + getCheckMessage(MSG_KEY),
            "6:19: " + getCheckMessage(MSG_KEY),
            "6:52: " + getCheckMessage(MSG_KEY),
            "7:21: " + getCheckMessage(MSG_KEY),
            "8:12: " + getCheckMessage(MSG_KEY),
            "8:16: " + getCheckMessage(MSG_KEY),
            "11:4: " + getCheckMessage(MSG_KEY),
            "12:6: " + getCheckMessage(MSG_KEY),
            "13:8: " + getCheckMessage(MSG_KEY),
            "14:9: " + getCheckMessage(MSG_KEY),
            "17:14: " + getCheckMessage(MSG_KEY),
            "17:24: " + getCheckMessage(MSG_KEY),
            "17:33: " + getCheckMessage(MSG_KEY),
            "18:16: " + getCheckMessage(MSG_KEY),
            "18:23: " + getCheckMessage(MSG_KEY),
            "19:17: " + getCheckMessage(MSG_KEY),
            "19:24: " + getCheckMessage(MSG_KEY),
            "20:20: " + getCheckMessage(MSG_KEY),
            "21:22: " + getCheckMessage(MSG_KEY),
            "26:22: " + getCheckMessage(MSG_KEY),
            "26:28: " + getCheckMessage(MSG_KEY),
            "27:15: " + getCheckMessage(MSG_KEY),
            "27:24: " + getCheckMessage(MSG_KEY),
            "27:32: " + getCheckMessage(MSG_KEY),
            "27:47: " + getCheckMessage(MSG_KEY),
            "28:17: " + getCheckMessage(MSG_KEY),
            "28:23: " + getCheckMessage(MSG_KEY),
            "30:17: " + getCheckMessage(MSG_KEY),
            "30:34: " + getCheckMessage(MSG_KEY),
            "31:8: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputSingleSpaceSeparatorErrors.java"), expected);
    }

    @Test
    public void testSpaceErrorsAroundComments() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(SingleSpaceSeparatorCheck.class);
        checkConfig.addAttribute("validateComments", "true");
        final String[] expected = {
            "5:11: " + getCheckMessage(MSG_KEY),
            "5:43: " + getCheckMessage(MSG_KEY),
            "6:14: " + getCheckMessage(MSG_KEY),
            "13:14: " + getCheckMessage(MSG_KEY),
            "13:25: " + getCheckMessage(MSG_KEY),
            "14:8: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputSingleSpaceSeparatorComments.java"), expected);
    }

    @Test
    public void testSpaceErrorsInChildNodes() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(SingleSpaceSeparatorCheck.class);
        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputSingleSpaceSeparatorChildNodes.java"), expected);
    }

    @Test
    public void testMinColumnNo() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(SingleSpaceSeparatorCheck.class);
        checkConfig.addAttribute("validateComments", "true");
        final String[] expected = {
            "5:4: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputSingleSpaceSeparatorMinColumnNo.java"), expected);
    }

    @Test
    public void testWhitespaceInStartOfTheLine() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(SingleSpaceSeparatorCheck.class);
        final String[] expected = {
            "5:7: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputSingleSpaceSeparatorStartOfTheLine.java"), expected);
    }

    @Test
    public void testSpaceErrorsIfCommentsIgnored() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(SingleSpaceSeparatorCheck.class);
        final String[] expected = {
            "13:14: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputSingleSpaceSeparatorComments.java"), expected);
    }

    @Test
    public void testEmpty() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(SingleSpaceSeparatorCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSingleSpaceSeparatorEmpty.java"), expected);
    }
}
