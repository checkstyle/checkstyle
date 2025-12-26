///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.LambdaBodyLengthCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class LambdaBodyLengthCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/lambdabodylength";
    }

    @Test
    void getRequiredTokens() {
        final LambdaBodyLengthCheck checkObj = new LambdaBodyLengthCheck();
        final int[] expected = {TokenTypes.LAMBDA};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    void getAcceptableTokens() {
        final LambdaBodyLengthCheck lambdaBodyLengthCheckObj =
                new LambdaBodyLengthCheck();
        final int[] actual = lambdaBodyLengthCheckObj.getAcceptableTokens();
        final int[] expected = {TokenTypes.LAMBDA};

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    void testDefault() throws Exception {
        final String[] expected = {
            "16:27: " + getCheckMessage(MSG_KEY, 12, 10),
            "29:27: " + getCheckMessage(MSG_KEY, 12, 10),
            "42:27: " + getCheckMessage(MSG_KEY, 11, 10),
            "55:35: " + getCheckMessage(MSG_KEY, 13, 10),
            "59:15: " + getCheckMessage(MSG_KEY, 11, 10),
            "71:34: " + getCheckMessage(MSG_KEY, 11, 10),
        };
        verifyWithInlineConfigParser(
                getPath("InputLambdaBodyLengthDefault.java"), expected);
    }

    @Test
    void defaultSwitchExpressions() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputLambdaBodyLengthSwitchExps.java"), expected);
    }

    @Test
    void maxLimitIsDifferent() throws Exception {
        final String[] expected = {
            "16:27: " + getCheckMessage(MSG_KEY, 4, 3),
            "21:27: " + getCheckMessage(MSG_KEY, 4, 3),
            "32:35: " + getCheckMessage(MSG_KEY, 5, 3),
            "39:34: " + getCheckMessage(MSG_KEY, 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputLambdaBodyLengthMax.java"), expected);
    }

}
