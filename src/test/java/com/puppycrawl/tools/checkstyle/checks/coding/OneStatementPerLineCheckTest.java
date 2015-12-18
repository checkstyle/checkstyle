////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class OneStatementPerLineCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testMultiCaseClass() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(OneStatementPerLineCheck.class);
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
            getPath("InputOneStatementPerLine.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final OneStatementPerLineCheck check = new OneStatementPerLineCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }

    @Test
    public void testWithMultilineStatements() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(OneStatementPerLineCheck.class);
        final String[] expected = {
            "44:21: " + getCheckMessage(MSG_KEY),
            "61:17: " + getCheckMessage(MSG_KEY),
            "69:17: " + getCheckMessage(MSG_KEY),
            "81:10: " + getCheckMessage(MSG_KEY),
            "90:28: " + getCheckMessage(MSG_KEY),
            "135:39: " + getCheckMessage(MSG_KEY),
            "168:110: " + getCheckMessage(MSG_KEY),
            "179:107: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig,
            getPath("InputOneStatementPerLine2.java"),
            expected);
    }

    @Test
    public void oneStatementNonCompilableInputTest() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(OneStatementPerLineCheck.class);
        final String[] expected = {
            "31:6: " + getCheckMessage(MSG_KEY),
            "36:58: " + getCheckMessage(MSG_KEY),
            "37:58: " + getCheckMessage(MSG_KEY),
            "37:74: " + getCheckMessage(MSG_KEY),
            "38:50: " + getCheckMessage(MSG_KEY),
            "42:91: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getNonCompilablePath("InputOneStatementPerLine.java"), expected);
    }
}
