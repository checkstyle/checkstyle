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

import static com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck.MSG_FALL_THROUGH;
import static com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck.MSG_FALL_THROUGH_LAST;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class FallThroughCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/fallthrough";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FallThroughCheck.class);
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "46:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "55:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "61:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "78:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "95:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "131:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "187:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "377:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "380:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "382:40: " + getCheckMessage(MSG_FALL_THROUGH),
            "424:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "432:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "444:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "454:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "490:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "491:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "492:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "529:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "531:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "533:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "535:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "537:15: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verify(checkConfig,
               getPath("InputFallThroughDefault.java"),
               expected);
    }

    @Test
    public void testTryWithResources() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FallThroughCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
               getNonCompilablePath("InputFallThrough.java"),
               expected);
    }

    @Test
    public void testLastCaseGroup() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FallThroughCheck.class);
        checkConfig.addAttribute("checkLastCaseGroup", "true");
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "46:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "55:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "61:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "78:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "95:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "131:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "131:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "187:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "377:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "380:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "382:40: " + getCheckMessage(MSG_FALL_THROUGH),
            "384:11: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "424:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "432:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "444:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "454:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "490:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "491:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "492:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verify(checkConfig,
               getPath("InputFallThrough.java"),
               expected);
    }

    @Test
    public void testOwnPattern() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FallThroughCheck.class);
        checkConfig.addAttribute("reliefPattern", "Continue with next case");

        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "46:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "55:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "61:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "78:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "95:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "131:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "153:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "178:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "187:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "194:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "212:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "230:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "260:26: " + getCheckMessage(MSG_FALL_THROUGH),
            "274:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "289:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "292:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "296:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "298:25: " + getCheckMessage(MSG_FALL_THROUGH),
            "314:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "317:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "319:25: " + getCheckMessage(MSG_FALL_THROUGH),
            "335:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "338:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "340:23: " + getCheckMessage(MSG_FALL_THROUGH),
            "356:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "359:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "361:30: " + getCheckMessage(MSG_FALL_THROUGH),
            "424:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "432:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "444:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "454:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "481:12: " + getCheckMessage(MSG_FALL_THROUGH),
            "490:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "491:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "492:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verify(checkConfig,
               getPath("InputFallThrough3.java"),
               expected);
    }

    @Test
    public void testOwnPatternTryWithResources() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FallThroughCheck.class);
        checkConfig.addAttribute("reliefPattern", "Continue with next case");

        final String[] expected = {
            "54:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "58:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "64:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "70:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "77:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verify(checkConfig,
               getNonCompilablePath("InputFallThrough2.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        final FallThroughCheck check = new FallThroughCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

    @Test
    public void testFallThroughNoElse() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FallThroughCheck.class);
        final String[] expected = {
            "28:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "43:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "47:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "54:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "68:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "75:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "88:21: " + getCheckMessage(MSG_FALL_THROUGH),
            "94:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "96:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verify(checkConfig,
            getPath("InputFallThrough2.java"),
            expected);
    }

}
