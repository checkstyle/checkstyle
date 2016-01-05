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

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class IllegalCatchCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IllegalCatchCheck.class);

        final String[] expected = {
            "6:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "7:11: " + getCheckMessage(MSG_KEY, "Exception"),
            "8:11: " + getCheckMessage(MSG_KEY, "Throwable"),
            "14:11: " + getCheckMessage(MSG_KEY, "java.lang.RuntimeException"),
            "15:11: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
            "16:11: " + getCheckMessage(MSG_KEY, "java.lang.Throwable"),
        };

        verify(checkConfig, getPath("InputIllegalCatch.java"), expected);
    }

    @Test
    public void testIllegalClassNames() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IllegalCatchCheck.class);
        checkConfig.addAttribute("illegalClassNames",
                                 "java.lang.Error, java.lang.Exception, java.lang.Throwable");

        final String[] expected = {
            "7:11: " + getCheckMessage(MSG_KEY, "Exception"),
            "8:11: " + getCheckMessage(MSG_KEY, "Throwable"),
            "15:11: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
            "16:11: " + getCheckMessage(MSG_KEY, "java.lang.Throwable"),
        };

        verify(checkConfig, getPath("InputIllegalCatch.java"), expected);
    }

    @Test
    public void testIllegalClassNamesBad() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IllegalCatchCheck.class);
        checkConfig.addAttribute("illegalClassNames",
                                 "java.lang.Error, java.lang.Exception, NullPointerException");

        // check that incorrect names don't break the Check
        checkConfig.addAttribute("illegalClassNames",
                "java.lang.IOException.");

        final String[] expected = {
            "7:11: " + getCheckMessage(MSG_KEY, "Exception"),
            "15:11: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
        };

        verify(checkConfig, getPath("InputIllegalCatch.java"), expected);
    }

    @Test
    public void testMultipleTypes() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IllegalCatchCheck.class);

        final String[] expected = {
            "7:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "10:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "13:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "16:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
        };

        verify(checkConfig, getPath("InputIllegalCatch2.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalCatchCheck check = new IllegalCatchCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
