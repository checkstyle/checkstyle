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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.text.NumberFormat;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.metrics.NPathComplexityCheck.MSG_KEY;
import static org.junit.Assert.fail;

public class NPathComplexityCheckTest extends BaseCheckTestSupport {
    @Test
    public void testCalculation() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        String[] expected = {
            "4:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "7:17: " + getCheckMessage(MSG_KEY, 2, 0),
            "17:5: " + getCheckMessage(MSG_KEY, 5, 0),
            "27:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "34:5: " + getCheckMessage(MSG_KEY, 7, 0),
            "48:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "58:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "67:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "76:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "79:13: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }

    @Test
    public void testIntegerOverflow() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final long largerThanMaxInt = 3486784401L;
        // use i18n for number formatting so test wont fail in non-english environments
        final String expectedComplexity = NumberFormat.getInstance().format(largerThanMaxInt);

        String[] expected = {
            "9:5: " + getCheckMessage(MSG_KEY, largerThanMaxInt, 0),
        };

        verify(checkConfig, getPath("ComplexityOverflow.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);
        String[] expected = {
        };

        try {
            createChecker(checkConfig);
            verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
        }
        catch (Exception ex) {
            // Exception is not expected
            fail();
        }
    }
}
