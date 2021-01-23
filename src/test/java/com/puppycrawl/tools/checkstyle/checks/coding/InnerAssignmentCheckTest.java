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
            "16:15: " + getCheckMessage(MSG_KEY),
            "16:19: " + getCheckMessage(MSG_KEY),
            "18:39: " + getCheckMessage(MSG_KEY),
            "20:35: " + getCheckMessage(MSG_KEY),

            "38:16: " + getCheckMessage(MSG_KEY),
            "39:24: " + getCheckMessage(MSG_KEY),
            "40:19: " + getCheckMessage(MSG_KEY),
            "41:17: " + getCheckMessage(MSG_KEY),
            "42:29: " + getCheckMessage(MSG_KEY),
            "43:20: " + getCheckMessage(MSG_KEY),
            "44:17: " + getCheckMessage(MSG_KEY),
            "44:31: " + getCheckMessage(MSG_KEY),
            "44:41: " + getCheckMessage(MSG_KEY),
            "45:16: " + getCheckMessage(MSG_KEY),
            "45:27: " + getCheckMessage(MSG_KEY),
            "46:32: " + getCheckMessage(MSG_KEY),
            "86:19: " + getCheckMessage(MSG_KEY),
            "175:22: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputInnerAssignment.java"), expected);
    }

    @Test
    public void testLambdaExpression() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InnerAssignmentCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputInnerAssignmentLambdaExpressions.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final InnerAssignmentCheck check = new InnerAssignmentCheck();
        assertNotNull(check.getAcceptableTokens(), "Unexpected acceptable tokenks");
        assertNotNull(check.getDefaultTokens(), "Unexpected default tokens");
        assertNotNull(check.getRequiredTokens(), "Unexpected required tokens");
    }

}
