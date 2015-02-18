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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class FallThroughCheckTest extends BaseCheckTestSupport
{

    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(FallThroughCheck.class);
        final String[] expected = {
            "14:13: Fall through from previous branch of the switch statement.",
            "38:13: Fall through from previous branch of the switch statement.",
            "53:13: Fall through from previous branch of the switch statement.",
            "70:13: Fall through from previous branch of the switch statement.",
            "87:13: Fall through from previous branch of the switch statement.",
            "105:13: Fall through from previous branch of the switch statement.",
            "123:13: Fall through from previous branch of the switch statement.",
            "369:11: Fall through from previous branch of the switch statement.",
            "372:11: Fall through from previous branch of the switch statement.",
            "374:40: Fall through from previous branch of the switch statement.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);
    }

    @Test
    public void testLastCaseGroup() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(FallThroughCheck.class);
        checkConfig.addAttribute("checkLastCaseGroup", "true");
        final String[] expected = {
            "14:13: Fall through from previous branch of the switch statement.",
            "38:13: Fall through from previous branch of the switch statement.",
            "53:13: Fall through from previous branch of the switch statement.",
            "70:13: Fall through from previous branch of the switch statement.",
            "87:13: Fall through from previous branch of the switch statement.",
            "105:13: Fall through from previous branch of the switch statement.",
            "123:13: Fall through from previous branch of the switch statement.",
            "123:13: Fall through from the last branch of the switch statement.",
            "369:11: Fall through from previous branch of the switch statement.",
            "372:11: Fall through from previous branch of the switch statement.",
            "374:40: Fall through from previous branch of the switch statement.",
            "376:11: Fall through from the last branch of the switch statement.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);
    }

    @Test
    public void testOwnPattern() throws Exception
    {
        final String ownPattern = "Continue with next case";
        final DefaultConfiguration checkConfig =
            createCheckConfig(FallThroughCheck.class);
        checkConfig.addAttribute("reliefPattern", ownPattern);

        final String[] expected = {
            "14:13: Fall through from previous branch of the switch statement.",
            "38:13: Fall through from previous branch of the switch statement.",
            "53:13: Fall through from previous branch of the switch statement.",
            "70:13: Fall through from previous branch of the switch statement.",
            "87:13: Fall through from previous branch of the switch statement.",
            "105:13: Fall through from previous branch of the switch statement.",
            "123:13: Fall through from previous branch of the switch statement.",
            "145:11: Fall through from previous branch of the switch statement.",
            "170:11: Fall through from previous branch of the switch statement.",
            "186:11: Fall through from previous branch of the switch statement.",
            "204:11: Fall through from previous branch of the switch statement.",
            "222:11: Fall through from previous branch of the switch statement.",
            "241:11: Fall through from previous branch of the switch statement.",
            "252:26: Fall through from previous branch of the switch statement.",
            "266:11: Fall through from previous branch of the switch statement.",
            "281:11: Fall through from previous branch of the switch statement.",
            "284:11: Fall through from previous branch of the switch statement.",
            "288:11: Fall through from previous branch of the switch statement.",
            "290:25: Fall through from previous branch of the switch statement.",
            "306:11: Fall through from previous branch of the switch statement.",
            "309:11: Fall through from previous branch of the switch statement.",
            "311:25: Fall through from previous branch of the switch statement.",
            "327:11: Fall through from previous branch of the switch statement.",
            "330:11: Fall through from previous branch of the switch statement.",
            "332:23: Fall through from previous branch of the switch statement.",
            "348:11: Fall through from previous branch of the switch statement.",
            "351:11: Fall through from previous branch of the switch statement.",
            "353:30: Fall through from previous branch of the switch statement.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);

    }
}
