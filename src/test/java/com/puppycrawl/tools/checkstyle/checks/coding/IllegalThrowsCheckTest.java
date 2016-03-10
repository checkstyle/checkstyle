////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalThrowsCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class IllegalThrowsCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);

        final String[] expected = {
            "9:51: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "14:45: " + getCheckMessage(MSG_KEY, "java.lang.RuntimeException"),
            "14:73: " + getCheckMessage(MSG_KEY, "java.lang.Error"),
        };

        verify(checkConfig, getPath("InputIllegalThrows.java"), expected);
    }

    @Test
    public void testIllegalClassNames() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);
        checkConfig.addAttribute("illegalClassNames",
                                 "java.lang.Error, java.lang.Exception, NullPointerException");

        // check that incorrect names don't break the Check
        checkConfig.addAttribute("illegalClassNames",
                "java.lang.IOException.");

        final String[] expected = {
            "5:33: " + getCheckMessage(MSG_KEY, "NullPointerException"),
            "14:73: " + getCheckMessage(MSG_KEY, "java.lang.Error"),
        };

        verify(checkConfig, getPath("InputIllegalThrows.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck with ignoredMethodNames attribute.
     */
    @Test
    public void testIgnoreMethodNames() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);
        checkConfig.addAttribute("ignoredMethodNames", "methodTwo");

        final String[] expected = {
            "9:51: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "18:35: " + getCheckMessage(MSG_KEY, "Throwable"),
        };

        verify(checkConfig, getPath("InputIllegalThrows.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck with both the attributes specified.
     */
    @Test
    public void testIllegalClassNamesWithIgnoreMethodNames() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);
        checkConfig.addAttribute("illegalClassNames",
            "java.lang.Error, java.lang.Exception, NullPointerException, Throwable");
        checkConfig.addAttribute("ignoredMethodNames", "methodTwo");

        final String[] expected = {
            "5:33: " + getCheckMessage(MSG_KEY, "NullPointerException"),
            "18:35: " + getCheckMessage(MSG_KEY, "Throwable"),
        };

        verify(checkConfig, getPath("InputIllegalThrows.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck with <b>ignoreOverriddenMethods</b>
     * property.
     */
    @Test
    public void testIgnoreOverriddenMethods() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputIllegalThrowsIgnoreOverriddenMethods.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck without <b>ignoreOverriddenMethods</b>
     * property.
     */
    @Test
    public void testNotIgnoreOverriddenMethods() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);
        checkConfig.addAttribute("ignoreOverriddenMethods", "false");

        final String[] expected = {
            "7:36: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "12:51: " + getCheckMessage(MSG_KEY, "RuntimeException"),
        };

        verify(checkConfig, getPath("InputIllegalThrowsIgnoreOverriddenMethods.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalThrowsCheck check = new IllegalThrowsCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
