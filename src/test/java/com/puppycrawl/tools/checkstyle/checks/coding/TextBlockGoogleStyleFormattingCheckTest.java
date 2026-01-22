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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_CLOSE_QUOTES_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_OPEN_QUOTES_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck.MSG_VERTICALLY_UNALIGNED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TextBlockGoogleStyleFormattingCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
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
            "13:37: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "15:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "24:33: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "26:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "36:36: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "38:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "41:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "44:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "47:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "49:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "55:32: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "55:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "63:42: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "63:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "70:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "84:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "85:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "97:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "99:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "99:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "101:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormat1() throws Exception {
        final String[] expected = {
            "17:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "19:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "19:18: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "21:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "28:35: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "28:35: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "44:15: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "48:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "50:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormatting1.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormat2() throws Exception {
        final String[] expected = {
            "13:37: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "14:14: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "14:14: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "21:32: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "21:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "28:33: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "29:38: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "29:38: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "37:34: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "37:34: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "44:36: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "45:31: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "45:31: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "51:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "53:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "53:42: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "59:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "60:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "60:32: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "69:32: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "69:32: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "77:42: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "77:42: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "84:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "85:41: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "85:41: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "93:41: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "93:41: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "101:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "101:24: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting2.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormat3() throws Exception {
        final String[] expected = {
            "18:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "20:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "20:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "21:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "21:24: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "23:26: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "23:26: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "34:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "35:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "35:24: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "35:29: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "36:26: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "36:26: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "45:35: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "45:35: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "54:24: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "54:24: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "61:27: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "61:27: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "68:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "69:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "69:22: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "77:22: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "77:22: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting3.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormat4() throws Exception {
        final String[] expected = {
            "17:25: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "26:29: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "27:36: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "27:36: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "27:51: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "29:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "34:20: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "37:44: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "37:44: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "47:17: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "50:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "59:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "62:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "64:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "71:12: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "76:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "79:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "91:16: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "93:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "95:12: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "105:12: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };
        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting4.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormat5() throws Exception {
        final String[] expected = {
            "17:45: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "19:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "25:44: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "27:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "32:51: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "34:26: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "39:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "43:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "47:23: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "49:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "53:20: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "55:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "62:48: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "65:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "74:28: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "76:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "76:15: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "78:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
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
            "24:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "27:33: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "32:21: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "39:66: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "42:65: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormattingWithTabs.java"),
                expected);
    }

    @Test
    public void testTextBlockFormatNotVerticallyAligned() throws Exception {
        final String[] expected = {
            "28:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "33:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "39:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "44:1: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "51:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "69:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "99:13: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "102:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "111:12: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormattingNotVerticallyAligned.java"),
                expected);
    }

    @Test
    public void testDefaultTextBlockFormatWithIf() throws Exception {
        final String[] expected = {
            "16:18: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "17:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "24:13: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "25:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "25:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "25:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "26:19: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "26:19: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "36:20: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "37:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "37:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "37:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "38:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "38:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "51:18: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "52:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "52:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "53:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "53:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "53:24: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "54:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "54:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "64:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "65:20: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "65:20: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "72:38: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "73:20: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "73:20: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "80:33: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "81:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "81:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "88:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "89:18: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "89:18: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "96:21: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "97:19: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "97:19: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "98:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "99:27: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "99:27: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };

        verifyWithInlineConfigParser(
            getPath("InputTextBlockGoogleStyleFormatting6.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormatWithNewKeyword() throws Exception {
        final String[] expected = {
            "13:42: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "15:33: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "21:31: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "23:25: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "25:55: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "27:25: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "36:70: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "36:70: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "46:21: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "48:24: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "60:36: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "62:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "66:23: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "68:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "74:25: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "100:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "102:21: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };
        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormatting7.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormatInCtor() throws Exception {
        final String[] expected = {
            "24:66: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "24:66: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "38:23: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "40:17: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "45:20: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "53:14: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };
        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormatting8.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormatInAnnotations() throws Exception {
        final String[] expected = {
            "13:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "14:19: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "14:19: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "21:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "22:15: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
            "22:15: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
            "30:22: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "32:9: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };
        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormatting9.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormatIndentationOfContent() throws Exception {
        final String[] expected = {
            "47:17: " + getCheckMessage(MSG_CLOSE_QUOTES_ERROR),
        };
        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormatting10.java"), expected);
    }

    @Test
    public void testDefaultTextBlockFormatAnnotations() throws Exception {
        final String[] expected = {
            "59:27: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "66:19: " + getCheckMessage(MSG_OPEN_QUOTES_ERROR),
            "68:1: " + getCheckMessage(MSG_VERTICALLY_UNALIGNED),
        };
        verifyWithInlineConfigParser(
                getPath("InputTextBlockGoogleStyleFormattingAnnotations.java"), expected);
    }
}
