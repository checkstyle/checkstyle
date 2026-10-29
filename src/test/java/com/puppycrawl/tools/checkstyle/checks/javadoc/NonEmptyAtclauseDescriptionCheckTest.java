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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck.MSG_KEY;

import java.nio.charset.CodingErrorAction;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NonEmptyAtclauseDescriptionCheckTest
        extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/nonemptyatclausedescription";
    }

    @Test
    public void testGetAcceptableTokens() {
        final NonEmptyAtclauseDescriptionCheck checkObj =
            new NonEmptyAtclauseDescriptionCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final NonEmptyAtclauseDescriptionCheck checkObj =
            new NonEmptyAtclauseDescriptionCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testCheckOne() throws Exception {
        final String[] expected = {
            // this is a case with description that is sequences of spaces
            "39: " + getCheckMessage(MSG_KEY),
            // this is a case with description that is sequences of spaces
            "40: " + getCheckMessage(MSG_KEY),
            // this is a case with description that is sequences of spaces
            "41: " + getCheckMessage(MSG_KEY),
            // this is a case with description that is sequences of spaces
            "53: " + getCheckMessage(MSG_KEY),
            // this is a case with description that is sequences of spaces
            "54: " + getCheckMessage(MSG_KEY),
            // this is a case with description that is sequences of spaces
            "55: " + getCheckMessage(MSG_KEY),
            "97: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
            "99: " + getCheckMessage(MSG_KEY),
            "100: " + getCheckMessage(MSG_KEY),
            "101: " + getCheckMessage(MSG_KEY),
            "102: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(getPath("InputNonEmptyAtclauseDescriptionOne.java"), expected);
    }

    @Test
    public void testCheckSince() throws Exception {
        final String[] expected = {
            "28: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputNonEmptyAtclauseDescriptionSince.java"), expected);
    }

    @Test
    public void testCheckTwo() throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_KEY),
            "22: " + getCheckMessage(MSG_KEY),
            "23: " + getCheckMessage(MSG_KEY),
            "24: " + getCheckMessage(MSG_KEY),
            "25: " + getCheckMessage(MSG_KEY),
            "53: " + getCheckMessage(MSG_KEY),
            "63: " + getCheckMessage(MSG_KEY),
            "80: " + getCheckMessage(MSG_KEY),
            "82: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(getPath("InputNonEmptyAtclauseDescriptionTwo.java"), expected);
    }

    /**
     * This tests that the check does not fail when the input file contains malformed characters
     * for a particular charset. The test file contains the character {@code ü} which is not
     * mappable to US-ASCII. It makes sure that malformed characters than cannot be mapped are
     * replaced with the default replacement character using {@link CodingErrorAction#REPLACE}.
     *
     * @throws Exception exception
     */
    @Test
    public void testDecoderOnMalformedInput() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineXmlConfig(
                getPath("InputNonEmptyAtclauseDescriptionDifferentCharset.java"),
                expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNonEmptyAtclauseDescriptionThree.java"), expected);
    }

}
