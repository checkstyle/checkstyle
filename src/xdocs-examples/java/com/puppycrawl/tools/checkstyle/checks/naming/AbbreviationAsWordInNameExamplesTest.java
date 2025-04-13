///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AbbreviationAsWordInNameExamplesTest extends AbstractExamplesModuleTestSupport {
    private static final int DEFAULT_EXPECTED_CAPITAL_COUNT = 4;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/abbreviationaswordinname";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "18:7: " + getCheckMessage(MSG_KEY, "CURRENT_COUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "27:8: " + getCheckMessage(MSG_KEY, "incrementCOUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "31:15: " + getCheckMessage(MSG_KEY, "incrementGLOBAL", DEFAULT_EXPECTED_CAPITAL_COUNT),
        };

        verifyWithInlineXmlConfig(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21:7: " + getCheckMessage(MSG_KEY, "CURRENT_COUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "23:14: " + getCheckMessage(MSG_KEY, "GLOBAL_COUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "27:15: " + getCheckMessage(MSG_KEY, "printCOUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "31:8: " + getCheckMessage(MSG_KEY, "incrementCOUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "35:15: " + getCheckMessage(MSG_KEY, "incrementGLOBAL", DEFAULT_EXPECTED_CAPITAL_COUNT),
        };

        verifyWithInlineXmlConfig(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final int expectedCapitalCount = 1;

        final String[] expected = {
            "21:7: " + getCheckMessage(MSG_KEY, "secondNUM", expectedCapitalCount),
            "23:14: " + getCheckMessage(MSG_KEY, "fourthNUm", expectedCapitalCount),
        };

        verifyWithInlineXmlConfig(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final int expectedCapitalCount = 2;

        final String[] expected = {
            "22:7: " + getCheckMessage(MSG_KEY, "secondMYNum", expectedCapitalCount),
            "23:7: " + getCheckMessage(MSG_KEY, "thirdNUM", expectedCapitalCount),
            "26:10: " + getCheckMessage(MSG_KEY, "firstXML", expectedCapitalCount),
        };

        verifyWithInlineXmlConfig(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final int expectedCapitalCount = 1;

        final String[] expected = {
            "21:7: " + getCheckMessage(MSG_KEY, "counterXYZ", expectedCapitalCount),
            "23:13: " + getCheckMessage(MSG_KEY, "customerID", expectedCapitalCount),
            "24:14: " + getCheckMessage(MSG_KEY, "nextID", expectedCapitalCount),
        };

        verifyWithInlineXmlConfig(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final int expectedCapitalCount = 1;

        final String[] expected = {
            "21:7: " + getCheckMessage(MSG_KEY, "counterXYZ", expectedCapitalCount),
            "23:13: " + getCheckMessage(MSG_KEY, "customerID", expectedCapitalCount),
            "26:20: " + getCheckMessage(MSG_KEY, "MAX_ALLOWED", expectedCapitalCount),
        };

        verifyWithInlineXmlConfig(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineXmlConfig(getPath("Example7.java"), expected);
    }
}
