////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.text.NumberFormat;
import org.junit.Test;

public class NPathComplexityCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testCalculation() throws Exception
    {
        DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        String[] expected = {
            "4:5: NPath Complexity is 2 (max allowed is 0).",
            "7:17: NPath Complexity is 2 (max allowed is 0).",
            "17:5: NPath Complexity is 5 (max allowed is 0).",
            "27:5: NPath Complexity is 3 (max allowed is 0).",
            "34:5: NPath Complexity is 7 (max allowed is 0).",
            "48:5: NPath Complexity is 3 (max allowed is 0).",
            "58:5: NPath Complexity is 3 (max allowed is 0).",
            "67:5: NPath Complexity is 3 (max allowed is 0).",
            "76:5: NPath Complexity is 1 (max allowed is 0).",
            "79:13: NPath Complexity is 2 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }

    @Test
    public void testIntegerOverflow() throws Exception
    {
        DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final long largerThanMaxInt = 3486784401L;
        // use i18n for number formatting so test wont fail in non-english environments
        final String expectedComplexity = NumberFormat.getInstance().format(largerThanMaxInt);

        String[] expected = {
            "9:5: NPath Complexity is " + expectedComplexity + " (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityOverflow.java"), expected);
    }
}
