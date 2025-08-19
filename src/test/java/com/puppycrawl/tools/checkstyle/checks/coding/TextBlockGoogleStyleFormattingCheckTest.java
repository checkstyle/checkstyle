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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_CLOSE_QUOTES_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_INDENTATION_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_OPEN_QUOTES_ERROR;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class TextBlockGoogleStyleFormattingCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/textblockgooglestyleformatting";
    }

    @Test
    public void testdefaultTextBlockFormat() throws Exception {
        final String[] expected = {
            "15:37: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "15:37: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "28:33: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "28:33: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "42:36: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "42:36: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "49:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "49:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "57:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "57:16: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "65:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "66:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "71:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "73:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "78:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "92:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "93:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "106:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "106:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "108:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "108:19: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "125:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "125:19: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "127:18: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "127:18: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "137:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "138:34: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "151:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "159:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "159:15: " + getCheckMessage(MSG_INDENTATION_ERROR),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting.java"), expected);
    }

    @Test
    public void testdefaultTextBlockFormat2() throws Exception {
        final String[] expected = {
            "15:37: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "15:37: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "16:14: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "21:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "22:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "29:33: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "29:33: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "30:38: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "36:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "37:34: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "44:36: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "44:36: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "45:31: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "51:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "51:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "53:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "59:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "59:16: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "60:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "67:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "68:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "73:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "75:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "82:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "82:16: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "83:41: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "89:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "90:41: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "96:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "97:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "106:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "106:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "108:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "108:19: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "109:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "110:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "111:26: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "121:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "121:19: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "122:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "122:29: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "122:29: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "123:26: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "132:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "133:34: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "141:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "142:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "147:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "148:27: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "155:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "155:15: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "156:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "162:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "163:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
        };
        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting2.java"), expected);
    }

    @Test
    public void testTextBlockFormatNotVerticallyAligned() throws Exception {
        final String[] expected = {
            "31:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "38:21: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "44:1: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "51:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "59:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "78:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "109:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "112:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "122:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormattingNotVerticallyAligned.java"),
                expected);
    }
}
