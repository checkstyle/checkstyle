////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class FallThroughCheckTest extends AbstractModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/fallthrough";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FallThroughCheck.class);
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "38:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "47:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "53:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "70:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "87:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "123:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "179:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "369:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "372:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "374:40: " + getCheckMessage(MSG_FALL_THROUGH),
            "416:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "424:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "436:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "446:9: " + getCheckMessage(MSG_FALL_THROUGH),

        };
        verify(checkConfig,
               getPath("InputFallThrough.java"),
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
        };
        verify(checkConfig,
               getPath("InputFallThrough.java"),
               expected);
    }

    @Test
    public void testOwnPattern() throws Exception {
        final String ownPattern = "Continue with next case";
        final DefaultConfiguration checkConfig =
            createModuleConfig(FallThroughCheck.class);
        checkConfig.addAttribute("reliefPattern", ownPattern);

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
            "491:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "495:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "501:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "507:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "514:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verify(checkConfig,
               getPath("InputFallThrough.java"),
               expected);

    }

    @Test
    public void testTokensNotNull() {
        final FallThroughCheck check = new FallThroughCheck();
        Assert.assertNotNull("Acceptable tokens should not be null", check.getAcceptableTokens());
        Assert.assertNotNull("Default tokens should not be null", check.getDefaultTokens());
        Assert.assertNotNull("Rrequired tokens should not be null", check.getRequiredTokens());
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
