////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class InnerAssignmentCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/innerassignment";
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InnerAssignmentCheck.class);
        final String[] expected = {
            "22:15: " + getCheckMessage(MSG_KEY),
            "23:19: " + getCheckMessage(MSG_KEY),
            "25:39: " + getCheckMessage(MSG_KEY),
            "27:35: " + getCheckMessage(MSG_KEY),

            "45:16: " + getCheckMessage(MSG_KEY),
            "46:24: " + getCheckMessage(MSG_KEY),
            "47:19: " + getCheckMessage(MSG_KEY),
            "48:17: " + getCheckMessage(MSG_KEY),
            "49:29: " + getCheckMessage(MSG_KEY),
            "50:20: " + getCheckMessage(MSG_KEY),
            "51:17: " + getCheckMessage(MSG_KEY),
            "52:20: " + getCheckMessage(MSG_KEY),
            "53:20: " + getCheckMessage(MSG_KEY),
            "54:16: " + getCheckMessage(MSG_KEY),
            "55:20: " + getCheckMessage(MSG_KEY),
            "56:32: " + getCheckMessage(MSG_KEY),
            "96:19: " + getCheckMessage(MSG_KEY),
            "185:22: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputInnerAssignment.java"), expected);
    }

    @Test
    public void testLambdaExpression() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InnerAssignmentCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(checkConfig,
                getPath("InputInnerAssignmentLambdaExpressions.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final InnerAssignmentCheck check = new InnerAssignmentCheck();
        assertNotNull(check.getAcceptableTokens(), "Unexpected acceptable tokens");
        assertNotNull(check.getDefaultTokens(), "Unexpected default tokens");
        assertNotNull(check.getRequiredTokens(), "Unexpected required tokens");
    }

}
