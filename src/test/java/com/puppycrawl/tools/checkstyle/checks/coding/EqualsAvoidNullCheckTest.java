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

import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck
    .MSG_EQUALS_IGNORE_CASE_AVOID_NULL;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class EqualsAvoidNullCheckTest extends BaseCheckTestSupport {
    @Test
    public void testEqualsWithDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EqualsAvoidNullCheck.class);

        final String[] expected = {
            "18:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "20:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "22:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "24:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "26:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "28:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "37:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "39:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "41:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "43:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "45:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "47:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "57:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "59:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "61:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "63:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "65:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "67:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "69:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "71:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "73:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "75:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "77:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "79:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "225:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "227:34: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputEqualsAvoidNull.java"), expected);
    }

    @Test
    public void testEqualsWithoutEqualsIgnoreCase() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(EqualsAvoidNullCheck.class);
        checkConfig.addAttribute("ignoreEqualsIgnoreCase", "true");

        final String[] expected = {
            "18:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "20:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "22:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "24:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "26:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "28:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "57:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "59:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "61:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "63:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "65:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "67:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "225:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputEqualsAvoidNull.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        EqualsAvoidNullCheck check = new EqualsAvoidNullCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
