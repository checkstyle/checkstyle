////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class OneStatementPerLineCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/onestatementperline";
    }

    @Test
    public void testMultiCaseClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OneStatementPerLineCheck.class);
        final String[] expected = {
            "24:59: " + getCheckMessage(MSG_KEY),
            "104:21: " + getCheckMessage(MSG_KEY),
            "131:14: " + getCheckMessage(MSG_KEY),
            "157:15: " + getCheckMessage(MSG_KEY),
            "169:23: " + getCheckMessage(MSG_KEY),
            "189:19: " + getCheckMessage(MSG_KEY),
            "192:59: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig,
            getPath("InputOneStatementPerLineSingleLine.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final OneStatementPerLineCheck check = new OneStatementPerLineCheck();
        Assert.assertNotNull("Acceptable tokens should not be null", check.getAcceptableTokens());
        Assert.assertNotNull("Default tokens should not be null", check.getDefaultTokens());
        Assert.assertNotNull("Required tokens should not be null", check.getRequiredTokens());
    }

    @Test
    public void testWithMultilineStatements() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OneStatementPerLineCheck.class);
        final String[] expected = {
            "44:21: " + getCheckMessage(MSG_KEY),
            "61:17: " + getCheckMessage(MSG_KEY),
            "69:17: " + getCheckMessage(MSG_KEY),
            "81:10: " + getCheckMessage(MSG_KEY),
            "90:28: " + getCheckMessage(MSG_KEY),
            "135:39: " + getCheckMessage(MSG_KEY),
            "168:100: " + getCheckMessage(MSG_KEY),
            "179:91: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig,
            getPath("InputOneStatementPerLineMultiline.java"),
            expected);
    }

    @Test
    public void oneStatementNonCompilableInputTest() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(OneStatementPerLineCheck.class);
        final String[] expected = {
            "32:6: " + getCheckMessage(MSG_KEY),
            "37:58: " + getCheckMessage(MSG_KEY),
            "38:58: " + getCheckMessage(MSG_KEY),
            "38:74: " + getCheckMessage(MSG_KEY),
            "39:50: " + getCheckMessage(MSG_KEY),
            "43:85: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getNonCompilablePath("InputOneStatementPerLine.java"), expected);
    }

}
