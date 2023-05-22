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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.SingleSpaceSeparatorCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SingleSpaceSeparatorCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/singlespaceseparator";
    }

    @Test
    public void testNoSpaceErrors() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorNoErrors.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testNoStackoverflowError() throws Exception {
        verifyWithLimitedResources(getPath("InputSingleSpaceSeparatorNoStackoverflowError.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final SingleSpaceSeparatorCheck check = new SingleSpaceSeparatorCheck();

        assertWithMessage("Invalid acceptable tokens")
            .that(check.getAcceptableTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testSpaceErrors() throws Exception {
        final String[] expected = {
            "8:10: " + getCheckMessage(MSG_KEY),
            "8:28: " + getCheckMessage(MSG_KEY),
            "11:9: " + getCheckMessage(MSG_KEY),
            "13:19: " + getCheckMessage(MSG_KEY),
            "13:52: " + getCheckMessage(MSG_KEY),
            "14:21: " + getCheckMessage(MSG_KEY),
            "15:12: " + getCheckMessage(MSG_KEY),
            "15:16: " + getCheckMessage(MSG_KEY),
            "18:4: " + getCheckMessage(MSG_KEY),
            "19:6: " + getCheckMessage(MSG_KEY),
            "20:8: " + getCheckMessage(MSG_KEY),
            "21:9: " + getCheckMessage(MSG_KEY),
            "24:14: " + getCheckMessage(MSG_KEY),
            "24:24: " + getCheckMessage(MSG_KEY),
            "24:33: " + getCheckMessage(MSG_KEY),
            "25:16: " + getCheckMessage(MSG_KEY),
            "25:23: " + getCheckMessage(MSG_KEY),
            "26:17: " + getCheckMessage(MSG_KEY),
            "26:24: " + getCheckMessage(MSG_KEY),
            "27:20: " + getCheckMessage(MSG_KEY),
            "28:22: " + getCheckMessage(MSG_KEY),
            "33:22: " + getCheckMessage(MSG_KEY),
            "33:28: " + getCheckMessage(MSG_KEY),
            "34:15: " + getCheckMessage(MSG_KEY),
            "34:24: " + getCheckMessage(MSG_KEY),
            "34:32: " + getCheckMessage(MSG_KEY),
            "34:47: " + getCheckMessage(MSG_KEY),
            "35:17: " + getCheckMessage(MSG_KEY),
            "35:23: " + getCheckMessage(MSG_KEY),
            "37:17: " + getCheckMessage(MSG_KEY),
            "37:34: " + getCheckMessage(MSG_KEY),
            "38:8: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorErrors.java"), expected);
    }

    @Test
    public void testSpaceErrorsAroundComments() throws Exception {
        final String[] expected = {
            "12:11: " + getCheckMessage(MSG_KEY),
            "12:43: " + getCheckMessage(MSG_KEY),
            "13:14: " + getCheckMessage(MSG_KEY),
            "20:14: " + getCheckMessage(MSG_KEY),
            "20:25: " + getCheckMessage(MSG_KEY),
            "21:8: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorComments.java"), expected);
    }

    @Test
    public void testSpaceErrorsInChildNodes() throws Exception {
        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorChildNodes.java"), expected);
    }

    @Test
    public void testMinColumnNo() throws Exception {
        final String[] expected = {
            "12:4: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorMinColumnNo.java"), expected);
    }

    @Test
    public void testWhitespaceInStartOfTheLine() throws Exception {
        final String[] expected = {
            "12:7: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorStartOfTheLine.java"), expected);
    }

    @Test
    public void testSpaceErrorsIfCommentsIgnored() throws Exception {
        final String[] expected = {
            "20:14: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorComments2.java"), expected);
    }

    @Test
    public void testEmpty() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorEmpty.java"), expected);
    }

    @Test
    public void testSpaceErrorsWithEmoji() throws Exception {
        final String[] expected = {
            "14:18: " + getCheckMessage(MSG_KEY),
            "16:17: " + getCheckMessage(MSG_KEY),
            "18:27: " + getCheckMessage(MSG_KEY),
            "24:46: " + getCheckMessage(MSG_KEY),
            "27:9: " + getCheckMessage(MSG_KEY),
            "33:17: " + getCheckMessage(MSG_KEY),
            "36:14: " + getCheckMessage(MSG_KEY),
            "36:25: " + getCheckMessage(MSG_KEY),
            "36:37: " + getCheckMessage(MSG_KEY),
            "37:43: " + getCheckMessage(MSG_KEY),
            "37:46: " + getCheckMessage(MSG_KEY),
            "38:15: " + getCheckMessage(MSG_KEY),
            "40:16: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorWithEmoji.java"), expected);
    }

    @Test
    public void testSpaceErrorsAroundCommentsWithEmoji() throws Exception {
        final String[] expected = {
            "25:22: " + getCheckMessage(MSG_KEY),
            "25:26: " + getCheckMessage(MSG_KEY),
            "26:26: " + getCheckMessage(MSG_KEY),
            "27:13: " + getCheckMessage(MSG_KEY),
            "34:8: " + getCheckMessage(MSG_KEY),
            "36:37: " + getCheckMessage(MSG_KEY),
            "38:46: " + getCheckMessage(MSG_KEY),
            "40:24: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
            getPath("InputSingleSpaceSeparatorCommentsWithEmoji.java"), expected);
    }
}
