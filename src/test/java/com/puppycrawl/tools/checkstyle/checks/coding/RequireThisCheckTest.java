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

import static com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck.MSG_METHOD;
import static com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck.MSG_VARIABLE;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class RequireThisCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_VARIABLE, "i", "\"this\""),
            "17:9: " + getCheckMessage(MSG_METHOD, "method1", "\"this\""),
            "31:9: " + getCheckMessage(MSG_VARIABLE, "i", "\"this\""),
            "49:13: " + getCheckMessage(MSG_VARIABLE, "z", "\"this\""),
            "56:9: " + getCheckMessage(MSG_VARIABLE, "z", "\"this\""),
        };
        verify(checkConfig,
               getPath("InputRequireThis.java"),
               expected);
    }

    @Test
    public void testMethodsOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("checkFields", "false");
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_METHOD, "method1", "\"this\""),
        };
        verify(checkConfig,
               getPath("InputRequireThis.java"),
               expected);
    }

    @Test
    public void testFieldsOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("checkMethods", "false");
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_VARIABLE, "i", "\"this\""),
            "31:9: " + getCheckMessage(MSG_VARIABLE, "i", "\"this\""),
            "49:13: " + getCheckMessage(MSG_VARIABLE, "z", "\"this\""),
            "56:9: " + getCheckMessage(MSG_VARIABLE, "z", "\"this\""),
        };
        verify(checkConfig,
               getPath("InputRequireThis.java"),
               expected);
    }

    @Test
    public void testGenerics() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("Input15Extensions.java"), expected);
    }

    @Test
    public void testGithubIssue41() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RequireThisCheck.class);
        final String[] expected = {
            "7:19: " + getCheckMessage(MSG_VARIABLE, "number", "\"this\""),
            "8:16: " + getCheckMessage(MSG_METHOD, "other", "\"this\""),
        };
        verify(checkConfig,
                getPath("InputRequireThis2.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final RequireThisCheck check = new RequireThisCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }

    @Test
    public void testWithAnonymousClass() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RequireThisCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputRequireThis3.java"),
                expected);
    }
}
