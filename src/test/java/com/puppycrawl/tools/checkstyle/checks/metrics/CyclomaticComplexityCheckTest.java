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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class CyclomaticComplexityCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "metrics" + File.separator + filename);
    }

    @Test
    public void testSwitchBlockAsSingleDecisionPointSetToTrue() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CyclomaticComplexityCheck.class);
        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("switchBlockAsSingleDecisionPoint", "true");

        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig, getPath("InputComplexitySwitchBlocks.java"), expected);
    }

    @Test
    public void testSwitchBlockAsSingleDecisionPointSetToFalse() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CyclomaticComplexityCheck.class);
        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("switchBlockAsSingleDecisionPoint", "false");

        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_KEY, 5, 0),
        };

        verify(checkConfig, getPath("InputComplexitySwitchBlocks.java"), expected);
    }

    @Test
    public void test() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CyclomaticComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "7:17: " + getCheckMessage(MSG_KEY, 2, 0),
            "17:5: " + getCheckMessage(MSG_KEY, 6, 0),
            "27:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "34:5: " + getCheckMessage(MSG_KEY, 5, 0),
            "48:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "58:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "67:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "76:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "79:13: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig, getPath("InputComplexity.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final CyclomaticComplexityCheck cyclomaticComplexityCheckObj =
            new CyclomaticComplexityCheck();
        final int[] actual = cyclomaticComplexityCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.QUESTION,
            TokenTypes.LAND,
            TokenTypes.LOR,
        };
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testHighMax() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CyclomaticComplexityCheck.class);
        checkConfig.addAttribute("max", "100");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputComplexitySwitchBlocks.java"), expected);
    }
}
