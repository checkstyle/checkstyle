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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.LineEndingCheck.MSG_KEY_WRONG_ENDING;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingOption.CR;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingOption.CRLF;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingOption.LF;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class LineEndingCheckExampleTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/lineending";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("Example1.java"), expected
        );
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("Example2.java"), expected
        );
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "8: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "9: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "10: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "11: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "12: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "13: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "14: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "15: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),

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
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "8: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "9: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "10: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "11: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "12: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "13: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "14: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "15: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
        };

        final DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "cr");
        checkConfig.addProperty("fileExtensions", "java");

        verify(checkConfig,
                getPath("Example4.java"), expected
        );
    }
}
