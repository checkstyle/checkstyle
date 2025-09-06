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


import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_CLOSE_QUOTES_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_INDENTATION_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_OPEN_QUOTES_ERROR;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TextBlockGoogleStyleFormattingCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/textblockgooglestyleformatting";
    }

    @Test
    public void testGetAcceptableTokens() {
        final TextBlockGoogleStyleFormattingCheck check =
            new TextBlockGoogleStyleFormattingCheck();

        final int[] acceptableTokens = {TokenTypes.TEXT_BLOCK_LITERAL_BEGIN};
        assertWithMessage("Expected empty array")
                .that(check.getAcceptableTokens())
                .isEqualTo(acceptableTokens);
    }

    @Test
    public void testdefaultTextBlockFormat() throws Exception {
        final String[] expected = {
            "13:37: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "15:9: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "24:33: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "26:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "36:36: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "38:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "41:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "44:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "47:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "49:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "55:32: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "55:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "63:42: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "63:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "70:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "84:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "85:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "97:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "99:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "99:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "101:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "114:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "116:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "116:18: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "118:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "125:34: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "125:34: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "141:15: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "145:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "147:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting.java"), expected);
    }

    @Test
    public void testdefaultTextBlockFormat2() throws Exception {
        final String[] expected = {
            "13:37: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "14:14: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "14:14: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "21:32: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "21:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "28:33: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "29:38: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "29:38: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "37:34: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "37:34: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "44:36: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "45:31: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "45:31: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "51:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "53:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "53:42: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "59:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "60:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "60:32: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "69:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "69:32: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "77:42: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "77:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "84:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "85:41: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "85:41: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "93:41: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "93:41: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "101:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "101:24: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "111:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "113:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "113:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "114:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "114:24: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "116:26: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "116:26: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "127:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "128:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "128:24: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "128:29: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "129:26: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "129:26: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "138:34: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "138:34: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "148:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "148:24: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "155:27: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "155:27: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "162:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "163:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "163:22: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "171:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "171:22: " + getCheckMessage(MSG_INDENTATION_ERROR),
        };
        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting2.java"), expected);
    }

    @Test
    public void testdefaultTextBlockFormat3() throws Exception {
        final String[] expected = {
            "17:41: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "26:29: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "27:36: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "27:36: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "27:51: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "29:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "34:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "37:44: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "37:44: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "47:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "50:10: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "59:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "62:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "64:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "71:12: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "76:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "79:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "84:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "91:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "93:9: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "95:12: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "105:12: " + getCheckMessage(MSG_INDENTATION_ERROR),
        };
        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting3.java"), expected);
    }

    @Test
    public void testTextBlockFormatNotVerticallyAligned() throws Exception {
        final String[] expected = {
            "29:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "34:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "40:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "45:1: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "52:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "70:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "100:13: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "103:17: " + getCheckMessage(MSG_INDENTATION_ERROR),
            "112:12: " + getCheckMessage(MSG_INDENTATION_ERROR),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormattingNotVerticallyAligned.java"),
                expected);
    }
}
