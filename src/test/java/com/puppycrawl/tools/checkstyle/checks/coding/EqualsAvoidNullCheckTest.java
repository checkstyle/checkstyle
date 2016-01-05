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

import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck.MSG_EQUALS_IGNORE_CASE_AVOID_NULL;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class EqualsAvoidNullCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testEqualsWithDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EqualsAvoidNullCheck.class);

        final String[] expected = {
            "37:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "39:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "41:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "43:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "45:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "47:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "69:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "71:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "73:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "75:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "77:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "79:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "238:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "244:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "245:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "247:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "248:27: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "249:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "250:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "251:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "268:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "269:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "270:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "272:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "273:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "274:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "295:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "300:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "301:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "308:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "311:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "312:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "314:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "315:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "316:33: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "319:70: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "320:51: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "320:77: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "321:55: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "335:28: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "336:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "337:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "338:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "339:37: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "343:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "344:44: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "345:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "346:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "357:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "368:30: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "394:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "415:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "416:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "417:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verify(checkConfig, getPath("InputEqualsAvoidNull.java"), expected);
    }

    @Test
    public void testEqualsWithoutEqualsIgnoreCase() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(EqualsAvoidNullCheck.class);
        checkConfig.addAttribute("ignoreEqualsIgnoreCase", "true");

        final String[] expected = {
            "238:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "244:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "245:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "247:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "248:27: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "249:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "250:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "251:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "268:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "269:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "270:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "272:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "273:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "274:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "295:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "300:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "301:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "308:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "311:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "312:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "314:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "315:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "316:33: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "319:70: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "320:51: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "320:77: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "321:55: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "335:28: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "336:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "337:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "338:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "339:37: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "343:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "344:44: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "345:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "346:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "357:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "368:30: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "394:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "415:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "416:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "417:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verify(checkConfig, getPath("InputEqualsAvoidNull.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final EqualsAvoidNullCheck check = new EqualsAvoidNullCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
