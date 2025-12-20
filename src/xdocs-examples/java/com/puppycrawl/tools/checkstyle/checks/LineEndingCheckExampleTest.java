///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.LineEndingCheck.MSG_KEY_WRONG_ENDING_CRLF;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingCheck.MSG_KEY_WRONG_ENDING_LF;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

import org.junit.jupiter.api.Test;

public class LineEndingCheckExampleTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/lineending";
    }

    @Test
    public void testExample1() throws Exception {
        String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("Example1.java"), expected
        );
    }

    @Test
    public void testExample2() throws Exception {
        String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("Example2.java"), expected
        );
    }

    @Test
    public void testExample3() throws Exception {
        String[] expected = {
                "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "8: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "9: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "10: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "11: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "12: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "13: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
                "14: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF)

        };

        final DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "lf");
        checkConfig.addProperty("fileExtensions", "java");

        verify(checkConfig,
                getPath("Example3.java"), expected
        );
    }

    @Test
    public void testExample4() throws Exception {
        String[] expected = {
                "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "8: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "9: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "10: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "11: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "12: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "13: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
                "14: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF)
        };
        final DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "CRlf");
        checkConfig.addProperty("fileExtensions", "java");

        verify(checkConfig,
                getPath("Example4.java"), expected
        );
    }
}
