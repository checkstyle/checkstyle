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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_CLOSE_QUOTES_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_OPEN_QUOTES_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_VERTICALLY_UNALIGNED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class TextBlockGoogleStyleFormattingCheckExamplesTest
        extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/textblockgooglestyleformatting";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "15:41: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "17:7: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "25:32: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "27:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "36:12: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "46:12: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "48:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "14:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "14:32: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "26:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "26:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "38:38: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "38:38: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "49:31: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "49:31: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "18:12: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "28:12: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "38:7: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }
}
