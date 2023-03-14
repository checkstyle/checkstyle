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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NonEmptyAtclauseDescriptionCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
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
            "37: " + getCheckMessage(MSG_KEY),
            // this is a case with description that is sequences of spaces
            "38: " + getCheckMessage(MSG_KEY),
            // this is a case with description that is sequences of spaces
            "39: " + getCheckMessage(MSG_KEY),
            // this is a case with description that is sequences of spaces
            "50: " + getCheckMessage(MSG_KEY),
            // this is a case with description that is sequences of spaces
            "51: " + getCheckMessage(MSG_KEY),
            // this is a case with description that is sequences of spaces
            "52: " + getCheckMessage(MSG_KEY),
            "92: " + getCheckMessage(MSG_KEY),
            "93: " + getCheckMessage(MSG_KEY),
            "94: " + getCheckMessage(MSG_KEY),
            "95: " + getCheckMessage(MSG_KEY),
            "96: " + getCheckMessage(MSG_KEY),
            "97: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(getPath("InputNonEmptyAtclauseDescriptionOne.java"), expected);
    }

    @Test
    public void testCheckTwo() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_KEY),
            "17: " + getCheckMessage(MSG_KEY),
            "18: " + getCheckMessage(MSG_KEY),
            "19: " + getCheckMessage(MSG_KEY),
            "20: " + getCheckMessage(MSG_KEY),
            "51: " + getCheckMessage(MSG_KEY),
            "60: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(getPath("InputNonEmptyAtclauseDescriptionTwo.java"), expected);
    }
}
