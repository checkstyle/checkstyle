////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
            "20:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "44:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "53:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "59:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "76:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "93:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "129:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "185:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "375:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "378:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "380:40: " + getCheckMessage(MSG_FALL_THROUGH),
            "422:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "430:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "442:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "452:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "488:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "489:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "490:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "527:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "529:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "531:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "533:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "535:15: " + getCheckMessage(MSG_FALL_THROUGH),
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
            "14:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "38:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "47:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "53:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "70:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "87:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "123:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "123:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "179:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "369:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "372:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "374:40: " + getCheckMessage(MSG_FALL_THROUGH),
            "376:11: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "416:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "424:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "436:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "446:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "482:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "483:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "484:9: " + getCheckMessage(MSG_FALL_THROUGH),
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
            "14:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "38:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "47:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "53:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "70:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "87:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "123:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "145:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "170:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "179:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "186:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "204:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "222:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "252:26: " + getCheckMessage(MSG_FALL_THROUGH),
            "266:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "281:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "284:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "288:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "290:25: " + getCheckMessage(MSG_FALL_THROUGH),
            "306:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "309:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "311:25: " + getCheckMessage(MSG_FALL_THROUGH),
            "327:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "330:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "332:23: " + getCheckMessage(MSG_FALL_THROUGH),
            "348:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "351:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "353:30: " + getCheckMessage(MSG_FALL_THROUGH),
            "416:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "424:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "436:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "446:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "473:12: " + getCheckMessage(MSG_FALL_THROUGH),
            "482:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "483:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "484:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verify(checkConfig,
               getPath("InputFallThrough.java"),
               expected);
    }

    @Test
    public void testOwnPatternTryWithResources() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FallThroughCheck.class);
        checkConfig.addAttribute("reliefPattern", "Continue with next case");

        final String[] expected = {
            "46:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "50:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "56:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "62:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "69:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verify(checkConfig,
               getNonCompilablePath("InputFallThrough.java"),
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
            "20:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "35:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "39:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "46:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "60:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "67:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "80:21: " + getCheckMessage(MSG_FALL_THROUGH),
            "86:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "88:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verify(checkConfig,
            getPath("InputFallThrough2.java"),
            expected);
    }

}
