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

import static com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OneStatementPerLineCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/onestatementperline";
    }

    @Test
    public void testMultiCaseClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OneStatementPerLineCheck.class);
        final String[] expected = {
            "13:59: " + getCheckMessage(MSG_KEY),
            "93:21: " + getCheckMessage(MSG_KEY),
            "120:14: " + getCheckMessage(MSG_KEY),
            "146:15: " + getCheckMessage(MSG_KEY),
            "158:23: " + getCheckMessage(MSG_KEY),
            "178:19: " + getCheckMessage(MSG_KEY),
            "181:59: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig,
            getPath("InputOneStatementPerLineSingleLine.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final OneStatementPerLineCheck check = new OneStatementPerLineCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

    @Test
    public void testWithMultilineStatements() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OneStatementPerLineCheck.class);
        checkConfig.addProperty("treatTryResourcesAsStatement", "true");
        final String[] expected = {
            "49:21: " + getCheckMessage(MSG_KEY),
            "66:17: " + getCheckMessage(MSG_KEY),
            "74:17: " + getCheckMessage(MSG_KEY),
            "86:10: " + getCheckMessage(MSG_KEY),
            "95:28: " + getCheckMessage(MSG_KEY),
            "140:39: " + getCheckMessage(MSG_KEY),
            "173:46: " + getCheckMessage(MSG_KEY),
            "184:47: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig,
            getPath("InputOneStatementPerLineMultiline.java"),
            expected);
    }

    @Test
    public void oneStatementNonCompilableInputTest() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OneStatementPerLineCheck.class);
        final String[] expected = {
            "39:6: " + getCheckMessage(MSG_KEY),
            "44:58: " + getCheckMessage(MSG_KEY),
            "45:58: " + getCheckMessage(MSG_KEY),
            "45:74: " + getCheckMessage(MSG_KEY),
            "46:50: " + getCheckMessage(MSG_KEY),
            "50:85: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getNonCompilablePath("InputOneStatementPerLine.java"), expected);
    }

    @Test
    public void testResourceReferenceVariableIgnored() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OneStatementPerLineCheck.class);
        checkConfig.addProperty("treatTryResourcesAsStatement", "true");
        final String[] expected = {
            "32:42: " + getCheckMessage(MSG_KEY),
            "36:43: " + getCheckMessage(MSG_KEY),
            "42:46: " + getCheckMessage(MSG_KEY),
            "46:46: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig,
                getNonCompilablePath("InputOneStatementPerLineTryWithResources.java"),
                expected);
    }

    @Test
    public void testResourcesIgnored() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OneStatementPerLineCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getNonCompilablePath("InputOneStatementPerLineTryWithResourcesIgnore.java"),
                expected);
    }

}
