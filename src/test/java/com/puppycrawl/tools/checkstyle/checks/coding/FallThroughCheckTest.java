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

import static com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck.MSG_FALL_THROUGH;
import static com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck.MSG_FALL_THROUGH_LAST;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class FallThroughCheckTest extends BaseCheckTestSupport {

    @Test
    public void testDefault() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(FallThroughCheck.class);
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "38:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "53:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "70:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "87:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "105:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "123:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "369:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "372:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "374:40: " + getCheckMessage(MSG_FALL_THROUGH),
            "416:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "424:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "436:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "446:9: " + getCheckMessage(MSG_FALL_THROUGH),

        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);
    }

    @Test
    public void testLastCaseGroup() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(FallThroughCheck.class);
        checkConfig.addAttribute("checkLastCaseGroup", "true");
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "38:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "53:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "70:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "87:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "105:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "123:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "123:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
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
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);
    }

    @Test
    public void testOwnPattern() throws Exception {
        final String ownPattern = "Continue with next case";
        final DefaultConfiguration checkConfig =
            createCheckConfig(FallThroughCheck.class);
        checkConfig.addAttribute("reliefPattern", ownPattern);

        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "38:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "53:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "70:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "87:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "105:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "123:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "145:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "170:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "186:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "204:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "222:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "241:11: " + getCheckMessage(MSG_FALL_THROUGH),
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
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);

    }

    @Test
    public void testTokensNotNull() {
        FallThroughCheck check = new FallThroughCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
