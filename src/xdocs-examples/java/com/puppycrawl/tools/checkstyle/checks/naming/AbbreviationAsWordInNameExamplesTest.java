///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AbbreviationAsWordInNameExamplesTest extends AbstractExamplesModuleTestSupport {

    private static final int DEFAULT_EXPECTED_CAPITAL_COUNT = 4;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/abbreviationaswordinname";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "18:7: " + getCheckMessage(MSG_KEY, "CURRENT_COUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "44:8: " + getCheckMessage(MSG_KEY, "incrementCOUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "46:15: " + getCheckMessage(MSG_KEY, "incrementGLOBAL", DEFAULT_EXPECTED_CAPITAL_COUNT),
        };

        verifyWithInlineXmlConfig(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21:7: " + getCheckMessage(MSG_KEY, "CURRENT_COUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "23:14: " + getCheckMessage(MSG_KEY, "GLOBAL_COUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "45:15: " + getCheckMessage(MSG_KEY, "printCOUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "47:8: " + getCheckMessage(MSG_KEY, "incrementCOUNTER", DEFAULT_EXPECTED_CAPITAL_COUNT),
            "49:15: " + getCheckMessage(MSG_KEY, "incrementGLOBAL", DEFAULT_EXPECTED_CAPITAL_COUNT),
        };

        verifyWithInlineXmlConfig(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final int expectedCapitalCount = 1;

        final String[] expected = {
            "23:7: " + getCheckMessage(MSG_KEY, "CURRENT_COUNTER", expectedCapitalCount),
            "25:14: " + getCheckMessage(MSG_KEY, "GLOBAL_COUNTER", expectedCapitalCount),
            "29:7: " + getCheckMessage(MSG_KEY, "secondNUM", expectedCapitalCount),
            "31:14: " + getCheckMessage(MSG_KEY, "fourthNUm", expectedCapitalCount),
            "36:7: " + getCheckMessage(MSG_KEY, "nextXYZ", expectedCapitalCount),
            "39:14: " + getCheckMessage(MSG_KEY, "nextID", expectedCapitalCount),
        };

        verifyWithInlineXmlConfig(getPath("Example3.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final int expectedCapitalCount = 2;

        final String[] expected = {
            "22:7: " + getCheckMessage(MSG_KEY, "secondMYNum", expectedCapitalCount),
            "23:7: " + getCheckMessage(MSG_KEY, "thirdNUM", expectedCapitalCount),
            "26:10: " + getCheckMessage(MSG_KEY, "firstXML", expectedCapitalCount),
        };

        verifyWithInlineXmlConfig(getPath("UseCase1.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final int expectedCapitalCount = 1;

        final String[] expected = {
            "24:7: " + getCheckMessage(MSG_KEY, "CURRENT_COUNTER", expectedCapitalCount),
            "26:14: " + getCheckMessage(MSG_KEY, "GLOBAL_COUNTER", expectedCapitalCount),
            "28:21: " + getCheckMessage(MSG_KEY, "stringsFOUND", expectedCapitalCount),
            "30:7: " + getCheckMessage(MSG_KEY, "secondNUM", expectedCapitalCount),
            "32:14: " + getCheckMessage(MSG_KEY, "fourthNUm", expectedCapitalCount),
            "33:10: " + getCheckMessage(MSG_KEY, "firstXML", expectedCapitalCount),
            "34:10: " + getCheckMessage(MSG_KEY, "firstURL", expectedCapitalCount),
            "35:13: " + getCheckMessage(MSG_KEY, "TOTAL", expectedCapitalCount),
            "37:7: " + getCheckMessage(MSG_KEY, "nextXYZ", expectedCapitalCount),
            "38:13: " + getCheckMessage(MSG_KEY, "countID", expectedCapitalCount),
            "40:14: " + getCheckMessage(MSG_KEY, "nextID", expectedCapitalCount),
        };

        verifyWithInlineXmlConfig(getPath("Example5.java"), expected);
    }

    @Test
    public void testUseCase2() throws Exception {
        final int expectedCapitalCount = 1;

        final String[] expected = {
            "21:7: " + getCheckMessage(MSG_KEY, "counterXYZ", expectedCapitalCount),
            "23:13: " + getCheckMessage(MSG_KEY, "customerID", expectedCapitalCount),
            "26:20: " + getCheckMessage(MSG_KEY, "MAX_ALLOWED", expectedCapitalCount),
        };

        verifyWithInlineXmlConfig(getPath("UseCase2.java"), expected);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineXmlConfig(getPath("UseCase3.java"), expected);
    }

}
