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
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.*;

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
    public void testDefaultTextBlockFormat() throws Exception {
        final String[] expected = {
            "14:37: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "15: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "16:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "26:33: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "27: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "28:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "39:36: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "40: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "41:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "45:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "46: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "48:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "52:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "53: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "54:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "60:32: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "60:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "68:42: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "68:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "75:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "90:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "91: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "91:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "103:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "104: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "105:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "105:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "106: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "107:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormat1() throws Exception {
        final String[] expected = {
            "18:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "19: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "20:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "20:18: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "21: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "22:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "30:35: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "30:35: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "46:15: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "51:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "52: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "53:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormatting1.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormat2() throws Exception {
        final String[] expected = {
            "13:37: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "14: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "14:14: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "14:14: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "22:32: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "22:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "29:33: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "30: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "30:38: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "30:38: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "39:34: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "39:34: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "46:36: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "47: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "47:31: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "47:31: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "55:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "56: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "57:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "57:42: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "63:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "64: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "64:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "64:32: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "74:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "74:32: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "82:42: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "82:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "89:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "90: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "90:41: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "90:41: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "99:41: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "99:41: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "107:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "107:24: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting2.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormat3() throws Exception {
        final String[] expected = {
            "20:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "21: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "22:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "22:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "23: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "23:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "23:24: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "25:26: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "25:26: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "37:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "38: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "38:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "38:24: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "38:29: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "39: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "39:26: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "39:26: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "47:35: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "47:35: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "56:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "56:24: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "63:27: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "63:27: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "70:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "71: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "71:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "71:22: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "80:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "80:22: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting3.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormat4() throws Exception {
        final String[] expected = {
            "17:25: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "27:29: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "28: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "28:36: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "28:36: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "28:51: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "29: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "30:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "37:20: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "38: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "40:44: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "40:44: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "51:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "52: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "54:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "64:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "65: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "67:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "70:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "71: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "77:12: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "83:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "84: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "86:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "99:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "100: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "101:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "103:12: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "113:12: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };
        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting4.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormat5() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "17:45: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "19:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "26:44: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "27: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "28:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "34:51: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "35: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "36:26: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "42:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "43: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "46:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "51:23: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "52: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "53:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "58:20: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "59: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "60:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "68:48: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "69: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "71:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "81:28: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "82: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "83:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "83:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "84: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "85:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting5.java"),
                expected);
    }

    @Test
    public void testTextBlockGoogleStyleFormattingWithTabs() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "18:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "25:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "26: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "28:33: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "33:21: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "41:66: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "42: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "44:65: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormattingWithTabs.java"),
                expected);
    }

    @Test
    public void testTextBlockFormatNotVerticallyAligned() throws Exception {
        final String[] expected = {
            "28:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "33: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "34:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "40:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "45:1: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "52: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "53:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "64: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "71: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "72:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "102: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "103:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "106:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "115: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "116:12: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormattingNotVerticallyAligned.java"),
                expected);
    }

    @Test
    public void testDefaultTextBlockFormatWithIf() throws Exception {
        final String[] expected = {
            "17:18: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "18: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "18:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "25:13: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "26:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "26:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "26:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "27: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "27:19: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "27:19: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "39:20: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "40: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "40:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "40:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "40:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "41: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "41:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "41:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "57:18: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "58: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "58:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "58:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "59: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "59:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "59:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "59:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "60: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "60:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "60:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "72:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "73: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "81:38: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "82: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "82:20: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "82:20: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "73:20: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "73:20: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "90:33: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "91: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "91:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "91:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "99:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "100: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "100:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "100:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "108:21: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "109: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "109:19: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "109:19: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "110:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "111:27: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "111:27: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting6.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormatWithNewKeyword() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "13:42: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "15:33: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "23:31: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "24: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "25:25: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "28:55: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "29: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "30:25: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "39:70: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "39:70: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "49:21: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "51:24: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "64:36: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "65: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "66:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "70:23: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "71: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "72:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "79:25: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "105:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "107:21: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };
        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormatting7.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormatInCtor() throws Exception {
        final String[] expected = {
            "24:66: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "24:66: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "39:23: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "40: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "41:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "46:20: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "53: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "54:14: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };
        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormatting8.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormatInAnnotations() throws Exception {
        final String[] expected = {
            "13:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "14: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "14:19: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "14:19: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "22:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "23: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "23:15: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "23:15: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "33:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "34: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "35:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };
        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormatting9.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormatIndentationOfContent() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "28: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "50: " + getCheckMessage(MSG_TEXT_BLOCK_CONTENT),
            "50:17: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
        };
        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormatting10.java"), expected);
    }
}
