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
import java.io.File;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.metrics.BooleanExpressionComplexityCheck
.MSG_KEY;

public class BooleanExpressionComplexityCheckTest extends BaseCheckTestSupport {
    @Test
    public void test() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(BooleanExpressionComplexityCheck.class);

        String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY, 4, 3),
            "32:9: " + getCheckMessage(MSG_KEY, 6, 3),
            "38:34: " + getCheckMessage(MSG_KEY, 4, 3),
            "40:34: " + getCheckMessage(MSG_KEY, 4, 3),
        };

        verify(checkConfig, getPath("metrics" + File.separator + "BooleanExpressionComplexityCheckTestInput.java"), expected);
    }

    @Test
    public void testNoBitwise() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(BooleanExpressionComplexityCheck.class);
        checkConfig.addAttribute("max", "5");
        checkConfig.addAttribute("tokens", "BXOR,LAND,LOR");

        String[] expected = {
        };

        verify(checkConfig, getPath("metrics" + File.separator + "BooleanExpressionComplexityCheckTestInput.java"), expected);
    }

    @Test
    public void testNPE() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(BooleanExpressionComplexityCheck.class);

        String[] expected = {
        };

        verify(checkConfig, getPath("metrics" + File.separator + "InputBooleanExpressionComplexityNPE.java"), expected);
    }
}
