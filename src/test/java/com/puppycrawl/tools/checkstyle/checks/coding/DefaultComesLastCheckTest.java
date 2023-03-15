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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck.MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class DefaultComesLastCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/defaultcomeslast";
    }

    @Test
    public void testSkipIfLastAndSharedWithCase() throws Exception {
        final String[] expected = {
            "23:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "31:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "39:21: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "43:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "63:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "83:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "95:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "104:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputDefaultComesLastSkipIfLastAndSharedWithCase.java"),
                expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "31:9: " + getCheckMessage(MSG_KEY),
            "38:24: " + getCheckMessage(MSG_KEY),
            "43:13: " + getCheckMessage(MSG_KEY),
            "51:13: " + getCheckMessage(MSG_KEY),
            "59:13: " + getCheckMessage(MSG_KEY),
            "67:21: " + getCheckMessage(MSG_KEY),
            "71:13: " + getCheckMessage(MSG_KEY),
            "75:21: " + getCheckMessage(MSG_KEY),
            "80:13: " + getCheckMessage(MSG_KEY),
            "91:13: " + getCheckMessage(MSG_KEY),
            "102:13: " + getCheckMessage(MSG_KEY),
            "112:13: " + getCheckMessage(MSG_KEY),
            "120:13: " + getCheckMessage(MSG_KEY),
            "131:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputDefaultComesLast.java"),
               expected);
    }

    @Test
    public void testDefaultMethodsInJava8()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputDefaultComesLastDefaultMethodsInInterface.java"),
                expected);
    }

    @Test
    public void testDefaultComesLastSwitchExpressions() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY),
            "32:13: " + getCheckMessage(MSG_KEY),
            "46:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputDefaultComesLastSwitchExpressions.java"),
            expected);
    }

    @Test
    public void testDefaultComesLastSwitchExpressionsSkipIfLast() throws Exception {

        final String[] expected = {
            "33:13: " + getCheckMessage(MSG_KEY),
            "48:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputDefaultComesLastSwitchExpressionsSkipIfLast.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final DefaultComesLastCheck check = new DefaultComesLastCheck();
        assertWithMessage("Acceptable tokens should not be null")
                .that(check.getAcceptableTokens())
                .isNotNull();
        assertWithMessage("Default tokens should not be null")
                .that(check.getDefaultTokens())
                .isNotNull();
        assertWithMessage("Required tokens should not be null")
                .that(check.getRequiredTokens())
                .isNotNull();
    }

}
