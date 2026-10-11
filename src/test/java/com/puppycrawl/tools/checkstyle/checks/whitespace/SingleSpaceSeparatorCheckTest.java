///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
    public String getPackageLocation() {
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
            "15:9: " + getCheckMessage(MSG_KEY),
            "17:19: " + getCheckMessage(MSG_KEY),
            "17:52: " + getCheckMessage(MSG_KEY),
            "22:21: " + getCheckMessage(MSG_KEY),
            "23:12: " + getCheckMessage(MSG_KEY),
            "23:16: " + getCheckMessage(MSG_KEY),
            "30:4: " + getCheckMessage(MSG_KEY),
            "32:6: " + getCheckMessage(MSG_KEY),
            "34:8: " + getCheckMessage(MSG_KEY),
            "36:9: " + getCheckMessage(MSG_KEY),
            "39:14: " + getCheckMessage(MSG_KEY),
            "39:24: " + getCheckMessage(MSG_KEY),
            "39:33: " + getCheckMessage(MSG_KEY),
            "44:16: " + getCheckMessage(MSG_KEY),
            "44:23: " + getCheckMessage(MSG_KEY),
            "48:17: " + getCheckMessage(MSG_KEY),
            "48:24: " + getCheckMessage(MSG_KEY),
            "53:20: " + getCheckMessage(MSG_KEY),
            "55:22: " + getCheckMessage(MSG_KEY),
            "60:22: " + getCheckMessage(MSG_KEY),
            "60:28: " + getCheckMessage(MSG_KEY),
            "64:15: " + getCheckMessage(MSG_KEY),
            "64:24: " + getCheckMessage(MSG_KEY),
            "64:32: " + getCheckMessage(MSG_KEY),
            "64:47: " + getCheckMessage(MSG_KEY),
            "71:17: " + getCheckMessage(MSG_KEY),
            "73:17: " + getCheckMessage(MSG_KEY),
            "73:34: " + getCheckMessage(MSG_KEY),
            "78:8: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorErrors.java"), expected);
    }

    @Test
    public void testSpaceErrorsAroundComments() throws Exception {
        final String[] expected = {
            "12:11: " + getCheckMessage(MSG_KEY),
            "12:43: " + getCheckMessage(MSG_KEY),
            "16:14: " + getCheckMessage(MSG_KEY),
            "23:14: " + getCheckMessage(MSG_KEY),
            "23:25: " + getCheckMessage(MSG_KEY),
            "28:8: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorComments.java"), expected);
    }

    @Test
    public void testSpaceErrorsInChildNodes() throws Exception {
        final String[] expected = {
            "13:16: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorChildNodes.java"), expected);
    }

    @Test
    public void testMinColumnNo() throws Exception {
        final String[] expected = {
            "13:4: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorMinColumnNo.java"), expected);
    }

    @Test
    public void testWhitespaceInStartOfTheLine() throws Exception {
        final String[] expected = {
            "13:7: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorStartOfTheLine.java"), expected);
    }

    @Test
    public void testSpaceErrorsIfCommentsIgnored() throws Exception {
        final String[] expected = {
            "21:14: " + getCheckMessage(MSG_KEY),
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
            "41:43: " + getCheckMessage(MSG_KEY),
            "41:46: " + getCheckMessage(MSG_KEY),
            "45:15: " + getCheckMessage(MSG_KEY),
            "47:16: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputSingleSpaceSeparatorWithEmoji.java"), expected);
    }

    @Test
    public void testSpaceErrorsAroundCommentsWithEmoji() throws Exception {
        final String[] expected = {
            "25:22: " + getCheckMessage(MSG_KEY),
            "25:26: " + getCheckMessage(MSG_KEY),
            "29:26: " + getCheckMessage(MSG_KEY),
            "30:13: " + getCheckMessage(MSG_KEY),
            "37:8: " + getCheckMessage(MSG_KEY),
            "39:37: " + getCheckMessage(MSG_KEY),
            "41:46: " + getCheckMessage(MSG_KEY),
            "43:24: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
            getPath("InputSingleSpaceSeparatorCommentsWithEmoji.java"), expected);
    }

    @Test
    public void testSpaceErrorsAroundForElse() throws Exception {
        final String[] expected = {
            "13:14: " + getCheckMessage(MSG_KEY),
            "17:17: " + getCheckMessage(MSG_KEY),
            "17:28: " + getCheckMessage(MSG_KEY),
            "24:16: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
            getPath("InputSingleSpaceSeparatorReservedWords.java"), expected);
    }

}
