////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
            "12:13: Fall through from previous branch of the switch statement.",
            "36:13: Fall through from previous branch of the switch statement.",
            "51:13: Fall through from previous branch of the switch statement.",
            "68:13: Fall through from previous branch of the switch statement.",
            "85:13: Fall through from previous branch of the switch statement.",
            "103:13: Fall through from previous branch of the switch statement.",
            "121:13: Fall through from previous branch of the switch statement.",
            "367:11: Fall through from previous branch of the switch statement.",
            "370:11: Fall through from previous branch of the switch statement.",
            "372:40: Fall through from previous branch of the switch statement.",
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
            "12:13: Fall through from previous branch of the switch statement.",
            "36:13: Fall through from previous branch of the switch statement.",
            "51:13: Fall through from previous branch of the switch statement.",
            "68:13: Fall through from previous branch of the switch statement.",
            "85:13: Fall through from previous branch of the switch statement.",
            "103:13: Fall through from previous branch of the switch statement.",
            "121:13: Fall through from previous branch of the switch statement.",
            "121:13: Fall through from the last branch of the switch statement.",
            "367:11: Fall through from previous branch of the switch statement.",
            "370:11: Fall through from previous branch of the switch statement.",
            "372:40: Fall through from previous branch of the switch statement.",
            "374:11: Fall through from the last branch of the switch statement.",
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
            "12:13: Fall through from previous branch of the switch statement.",
            "36:13: Fall through from previous branch of the switch statement.",
            "51:13: Fall through from previous branch of the switch statement.",
            "68:13: Fall through from previous branch of the switch statement.",
            "85:13: Fall through from previous branch of the switch statement.",
            "103:13: Fall through from previous branch of the switch statement.",
            "121:13: Fall through from previous branch of the switch statement.",
            "143:11: Fall through from previous branch of the switch statement.",
            "168:11: Fall through from previous branch of the switch statement.",
            "184:11: Fall through from previous branch of the switch statement.",
            "202:11: Fall through from previous branch of the switch statement.",
            "220:11: Fall through from previous branch of the switch statement.",
            "239:11: Fall through from previous branch of the switch statement.",
            "250:26: Fall through from previous branch of the switch statement.",
            "264:11: Fall through from previous branch of the switch statement.",
            "279:11: Fall through from previous branch of the switch statement.",
            "282:11: Fall through from previous branch of the switch statement.",
            "286:11: Fall through from previous branch of the switch statement.",
            "288:25: Fall through from previous branch of the switch statement.",
            "304:11: Fall through from previous branch of the switch statement.",
            "307:11: Fall through from previous branch of the switch statement.",
            "309:25: Fall through from previous branch of the switch statement.",
            "325:11: Fall through from previous branch of the switch statement.",
            "328:11: Fall through from previous branch of the switch statement.",
            "330:23: Fall through from previous branch of the switch statement.",
            "346:11: Fall through from previous branch of the switch statement.",
            "349:11: Fall through from previous branch of the switch statement.",
            "351:30: Fall through from previous branch of the switch statement.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);

    }
}
