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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck.MSG_KEY;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.google.common.base.Splitter;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class AvoidEscapedUnicodeCharactersCheckTest extends AbstractModuleTestSupport {

    // C0 (ASCII and derivatives)
    // https://en.wiktionary.org/wiki/Appendix:Control_characters#C0_.28ASCII_and_derivatives.29
    private static final int[] C0_CONTROL_CHARACTER_INDICES = {
        0x0000,
        0x0001,
        0x0002,
        0x0003,
        0x0004,
        0x0005,
        0x0006,
        0x0007,
        0x0008,
        0x0009,
        0x000A,
        0x000B,
        0x000C,
        0x000D,
        0x000E,
        0x000F,
        0x0010,
        0x0011,
        0x0012,
        0x0013,
        0x0014,
        0x0015,
        0x0016,
        0x0017,
        0x0018,
        0x0019,
        0x001A,
        0x001B,
        0x001C,
        0x001D,
        0x001E,
        0x001F,
    };

    // C1 set
    // https://en.wiktionary.org/wiki/Appendix:Control_characters#C1_set
    private static final int[] C1_CONTROL_CHARACTER_INDICES = {
        0x0080,
        0x0081,
        0x0082,
        0x0083,
        0x0084,
        0x0085,
        0x0086,
        0x0087,
        0x0088,
        0x0089,
        0x008A,
        0x008B,
        0x008C,
        0x008D,
        0x008E,
        0x008F,
        0x0090,
        0x0091,
        0x0092,
        0x0093,
        0x0094,
        0x0095,
        0x0096,
        0x0097,
        0x0098,
        0x0099,
        0x009A,
        0x009B,
        0x009C,
        0x009D,
        0x009E,
        0x009F,
    };

    // Other control characters which do not occur in the C0 or C1 sets
    // https://en.wiktionary.org/wiki/Appendix:Control_characters#Unicode_control_characters
    private static final int[] OTHER_CONTROL_CHARACTER_INDICES = {
        0x00AD,
        0x034F,
        0x070F,
        0x180E,
        0x200B,
        0x200C,
        0x200D,
        0x200E,
        0x200F,
        0x202A,
        0x202B,
        0x202C,
        0x202D,
        0x202E,
        0x2060,
        0x2061,
        0x2062,
        0x2063,
        0x2064,
        0x206A,
        0x206B,
        0x206C,
        0x206D,
        0x206E,
        0x206F,
        0xFEFF,
        0xFFF9,
        0xFFFA,
        0xFFFB,
    };

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/avoidescapedunicodecharacters";
    }

    @Test
    public void testGetRequiredTokens() {
        final AvoidEscapedUnicodeCharactersCheck checkObj =
            new AvoidEscapedUnicodeCharactersCheck();
        final int[] expected = {
            TokenTypes.STRING_LITERAL,
            TokenTypes.CHAR_LITERAL,
            TokenTypes.TEXT_BLOCK_CONTENT,
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
        assertWithMessage("Required tokens differ from expected")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "17:38: " + getCheckMessage(MSG_KEY),
            "19:38: " + getCheckMessage(MSG_KEY),
            "21:38: " + getCheckMessage(MSG_KEY),
            "25:38: " + getCheckMessage(MSG_KEY),
            "27:38: " + getCheckMessage(MSG_KEY),
            "32:24: " + getCheckMessage(MSG_KEY),
            "37:36: " + getCheckMessage(MSG_KEY),
            "39:36: " + getCheckMessage(MSG_KEY),
            "42:24: " + getCheckMessage(MSG_KEY),
            "47:38: " + getCheckMessage(MSG_KEY),
            "49:38: " + getCheckMessage(MSG_KEY),
            "51:38: " + getCheckMessage(MSG_KEY),
            "53:47: " + getCheckMessage(MSG_KEY),
            "62:32: " + getCheckMessage(MSG_KEY),
            "80:35: " + getCheckMessage(MSG_KEY),
            "82:35: " + getCheckMessage(MSG_KEY),
            "84:35: " + getCheckMessage(MSG_KEY),
            "86:35: " + getCheckMessage(MSG_KEY),
            "97:24: " + getCheckMessage(MSG_KEY),
            "98:24: " + getCheckMessage(MSG_KEY),
            "99:24: " + getCheckMessage(MSG_KEY),
            "100:24: " + getCheckMessage(MSG_KEY),
            "101:24: " + getCheckMessage(MSG_KEY),
            "102:24: " + getCheckMessage(MSG_KEY),
            "104:24: " + getCheckMessage(MSG_KEY),
            "107:31: " + getCheckMessage(MSG_KEY),
            "107:48: " + getCheckMessage(MSG_KEY),
            "114:38: " + getCheckMessage(MSG_KEY),
            "116:38: " + getCheckMessage(MSG_KEY),
            "118:38: " + getCheckMessage(MSG_KEY),
            "120:38: " + getCheckMessage(MSG_KEY),
            "124:31: " + getCheckMessage(MSG_KEY),
            "124:45: " + getCheckMessage(MSG_KEY),
            "128:34: " + getCheckMessage(MSG_KEY),
            "130:46: " + getCheckMessage(MSG_KEY),
            "135:38: " + getCheckMessage(MSG_KEY),
            "142:38: " + getCheckMessage(MSG_KEY),
            "145:46: " + getCheckMessage(MSG_KEY),
            "147:55: " + getCheckMessage(MSG_KEY),
            "149:46: " + getCheckMessage(MSG_KEY),
            "151:55: " + getCheckMessage(MSG_KEY),
            "153:46: " + getCheckMessage(MSG_KEY),
            "155:55: " + getCheckMessage(MSG_KEY),
            "157:46: " + getCheckMessage(MSG_KEY),
            "159:55: " + getCheckMessage(MSG_KEY),
            "161:46: " + getCheckMessage(MSG_KEY),
            "163:55: " + getCheckMessage(MSG_KEY),
            "165:48: " + getCheckMessage(MSG_KEY),
            "167:57: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testAllowEscapesForControlCharacterSet() throws Exception {
        final String[] expected = {
            "17:38: " + getCheckMessage(MSG_KEY),
            "19:38: " + getCheckMessage(MSG_KEY),
            "21:38: " + getCheckMessage(MSG_KEY),
            "25:38: " + getCheckMessage(MSG_KEY),
            "27:38: " + getCheckMessage(MSG_KEY),
            "36:36: " + getCheckMessage(MSG_KEY),
            "38:36: " + getCheckMessage(MSG_KEY),
            "45:38: " + getCheckMessage(MSG_KEY),
            "47:38: " + getCheckMessage(MSG_KEY),
            "49:38: " + getCheckMessage(MSG_KEY),
            "51:47: " + getCheckMessage(MSG_KEY),
            "60:32: " + getCheckMessage(MSG_KEY),
            "78:35: " + getCheckMessage(MSG_KEY),
            "80:35: " + getCheckMessage(MSG_KEY),
            "82:35: " + getCheckMessage(MSG_KEY),
            "84:35: " + getCheckMessage(MSG_KEY),
            "96:24: " + getCheckMessage(MSG_KEY),
            "97:24: " + getCheckMessage(MSG_KEY),
            "98:24: " + getCheckMessage(MSG_KEY),
            "99:24: " + getCheckMessage(MSG_KEY),
            "100:24: " + getCheckMessage(MSG_KEY),
            "102:24: " + getCheckMessage(MSG_KEY),
            "105:31: " + getCheckMessage(MSG_KEY),
            "105:48: " + getCheckMessage(MSG_KEY),
            "112:38: " + getCheckMessage(MSG_KEY),
            "113:38: " + getCheckMessage(MSG_KEY),
            "115:38: " + getCheckMessage(MSG_KEY),
            "117:38: " + getCheckMessage(MSG_KEY),
            "121:45: " + getCheckMessage(MSG_KEY),
            "124:46: " + getCheckMessage(MSG_KEY),
            "129:38: " + getCheckMessage(MSG_KEY),
            "136:38: " + getCheckMessage(MSG_KEY),
            "138:46: " + getCheckMessage(MSG_KEY),
            "140:55: " + getCheckMessage(MSG_KEY),
            "142:46: " + getCheckMessage(MSG_KEY),
            "144:55: " + getCheckMessage(MSG_KEY),
            "146:46: " + getCheckMessage(MSG_KEY),
            "148:55: " + getCheckMessage(MSG_KEY),
            "150:46: " + getCheckMessage(MSG_KEY),
            "152:55: " + getCheckMessage(MSG_KEY),
            "154:46: " + getCheckMessage(MSG_KEY),
            "156:55: " + getCheckMessage(MSG_KEY),
            "158:48: " + getCheckMessage(MSG_KEY),
            "160:57: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharacters1.java"), expected);
    }

    @Test
    public void testAllowByTailComment() throws Exception {
        final String[] expected = {
            "17:38: " + getCheckMessage(MSG_KEY),
            "26:38: " + getCheckMessage(MSG_KEY),
            "36:36: " + getCheckMessage(MSG_KEY),
            "44:38: " + getCheckMessage(MSG_KEY),
            "47:38: " + getCheckMessage(MSG_KEY),
            "49:47: " + getCheckMessage(MSG_KEY),
            "75:35: " + getCheckMessage(MSG_KEY),
            "77:35: " + getCheckMessage(MSG_KEY),
            "79:35: " + getCheckMessage(MSG_KEY),
            "81:35: " + getCheckMessage(MSG_KEY),
            "93:24: " + getCheckMessage(MSG_KEY),
            "95:24: " + getCheckMessage(MSG_KEY),
            "97:24: " + getCheckMessage(MSG_KEY),
            "99:24: " + getCheckMessage(MSG_KEY),
            "101:24: " + getCheckMessage(MSG_KEY),
            "104:24: " + getCheckMessage(MSG_KEY),
            "108:31: " + getCheckMessage(MSG_KEY),
            "108:48: " + getCheckMessage(MSG_KEY),
            "121:31: " + getCheckMessage(MSG_KEY),
            "121:45: " + getCheckMessage(MSG_KEY),
            "130:38: " + getCheckMessage(MSG_KEY),
            "136:38: " + getCheckMessage(MSG_KEY),
            "138:46: " + getCheckMessage(MSG_KEY),
            "142:46: " + getCheckMessage(MSG_KEY),
            "145:46: " + getCheckMessage(MSG_KEY),
            "148:46: " + getCheckMessage(MSG_KEY),
            "151:46: " + getCheckMessage(MSG_KEY),
            "154:48: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharacters2.java"), expected);
    }

    @Test
    public void testAllowAllCharactersEscaped() throws Exception {
        final String[] expected = {
            "17:38: " + getCheckMessage(MSG_KEY),
            "19:38: " + getCheckMessage(MSG_KEY),
            "21:38: " + getCheckMessage(MSG_KEY),
            "26:38: " + getCheckMessage(MSG_KEY),
            "28:38: " + getCheckMessage(MSG_KEY),
            "44:38: " + getCheckMessage(MSG_KEY),
            "46:38: " + getCheckMessage(MSG_KEY),
            "48:38: " + getCheckMessage(MSG_KEY),
            "58:32: " + getCheckMessage(MSG_KEY),
            "103:38: " + getCheckMessage(MSG_KEY),
            "105:38: " + getCheckMessage(MSG_KEY),
            "107:38: " + getCheckMessage(MSG_KEY),
            "109:38: " + getCheckMessage(MSG_KEY),
            "119:38: " + getCheckMessage(MSG_KEY),
            "127:46: " + getCheckMessage(MSG_KEY),
            "129:55: " + getCheckMessage(MSG_KEY),
            "131:46: " + getCheckMessage(MSG_KEY),
            "133:55: " + getCheckMessage(MSG_KEY),
            "135:46: " + getCheckMessage(MSG_KEY),
            "137:55: " + getCheckMessage(MSG_KEY),
            "139:46: " + getCheckMessage(MSG_KEY),
            "141:55: " + getCheckMessage(MSG_KEY),
            "143:46: " + getCheckMessage(MSG_KEY),
            "145:55: " + getCheckMessage(MSG_KEY),
            "147:48: " + getCheckMessage(MSG_KEY),
            "149:57: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharacters3.java"), expected);
    }

    @Test
    public void allowNonPrintableEscapesOne() throws Exception {
        final String[] expected = {
            "17:38: " + getCheckMessage(MSG_KEY),
            "19:38: " + getCheckMessage(MSG_KEY),
            "21:38: " + getCheckMessage(MSG_KEY),
            "26:38: " + getCheckMessage(MSG_KEY),
            "28:38: " + getCheckMessage(MSG_KEY),
            "37:36: " + getCheckMessage(MSG_KEY),
            "39:36: " + getCheckMessage(MSG_KEY),
            "46:38: " + getCheckMessage(MSG_KEY),
            "48:38: " + getCheckMessage(MSG_KEY),
            "50:38: " + getCheckMessage(MSG_KEY),
            "52:47: " + getCheckMessage(MSG_KEY),
            "61:32: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharacters4One.java"), expected);
    }

    @Test
    public void allowNonPrintableEscapesTwo() throws Exception {
        final String[] expected = {
            "17:38: " + getCheckMessage(MSG_KEY),
            "19:38: " + getCheckMessage(MSG_KEY),
            "21:38: " + getCheckMessage(MSG_KEY),
            "23:38: " + getCheckMessage(MSG_KEY),
            "28:34: " + getCheckMessage(MSG_KEY),
            "30:46: " + getCheckMessage(MSG_KEY),
            "35:38: " + getCheckMessage(MSG_KEY),
            "42:38: " + getCheckMessage(MSG_KEY),
            "45:46: " + getCheckMessage(MSG_KEY),
            "47:55: " + getCheckMessage(MSG_KEY),
            "49:46: " + getCheckMessage(MSG_KEY),
            "51:55: " + getCheckMessage(MSG_KEY),
            "53:46: " + getCheckMessage(MSG_KEY),
            "55:55: " + getCheckMessage(MSG_KEY),
            "57:46: " + getCheckMessage(MSG_KEY),
            "59:55: " + getCheckMessage(MSG_KEY),
            "61:46: " + getCheckMessage(MSG_KEY),
            "63:55: " + getCheckMessage(MSG_KEY),
            "65:48: " + getCheckMessage(MSG_KEY),
            "67:57: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharacters4Two.java"), expected);
    }

    @Test
    public void testAllowByTailCommentWithEmoji() throws Exception {
        final String[] expected = {
            "15:24: " + getCheckMessage(MSG_KEY),
            "18:24: " + getCheckMessage(MSG_KEY),
            "23:30: " + getCheckMessage(MSG_KEY),
            "33:18: " + getCheckMessage(MSG_KEY),
            "36:18: " + getCheckMessage(MSG_KEY),
            "38:18: " + getCheckMessage(MSG_KEY),
            "41:18: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharacters5.java"), expected);
    }

    @Test
    public void testAvoidEscapedUnicodeCharactersTextBlocksAllowByComment() throws Exception {
        final String[] expected = {
            "18:30: " + getCheckMessage(MSG_KEY),
            "20:30: " + getCheckMessage(MSG_KEY),
            "22:30: " + getCheckMessage(MSG_KEY),
            "25:39: " + getCheckMessage(MSG_KEY),
            "30:33: " + getCheckMessage(MSG_KEY),
            "33:33: " + getCheckMessage(MSG_KEY),
            "36:33: " + getCheckMessage(MSG_KEY),
            "41:42: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath(
                "InputAvoidEscapedUnicodeCharactersTextBlocksAllowByComment.java"),
            expected);
    }

    @Test
    public void testAvoidEscapedUnicodeCharactersTextBlocks() throws Exception {
        final String[] expected = {
            "17:30: " + getCheckMessage(MSG_KEY),
            "19:30: " + getCheckMessage(MSG_KEY),
            "21:30: " + getCheckMessage(MSG_KEY),
            "23:39: " + getCheckMessage(MSG_KEY),
            "29:33: " + getCheckMessage(MSG_KEY),
            "32:33: " + getCheckMessage(MSG_KEY),
            "35:33: " + getCheckMessage(MSG_KEY),
            "38:42: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharactersTextBlocks.java"),
            expected);
    }

    @Test
    public void testAvoidEscapedUnicodeCharactersEscapedS() throws Exception {
        final String[] expected = {
            "17:21: " + getCheckMessage(MSG_KEY),
            "19:22: " + getCheckMessage(MSG_KEY),
            "31:39: " + getCheckMessage(MSG_KEY),
            "35:39: " + getCheckMessage(MSG_KEY),
            "39:39: " + getCheckMessage(MSG_KEY),
            "43:22: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharactersEscapedS.java"),
                expected);
    }

    @Test
    public void testAvoidEscapedUnicodeCharactersControlChars() throws Exception {
        final String[] expected = {
            "17:34: " + getCheckMessage(MSG_KEY),
            "24:18: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharactersControlChars.java"),
                expected);
    }

    @Test
    public void testAvoidEscapedUnicodeCharactersAllowByTailComment() throws Exception {
        final String[] expected = {
            "16:17: " + getCheckMessage(MSG_KEY),
            "23:17: " + getCheckMessage(MSG_KEY),
            "33:20: " + getCheckMessage(MSG_KEY),
            "39:18: " + getCheckMessage(MSG_KEY),
            "44:32: " + getCheckMessage(MSG_KEY),
            "47:18: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharactersAllowByTailComment.java"),
                expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AvoidEscapedUnicodeCharactersCheck check = new AvoidEscapedUnicodeCharactersCheck();
        final int[] actual = check.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.STRING_LITERAL,
            TokenTypes.CHAR_LITERAL,
            TokenTypes.TEXT_BLOCK_CONTENT,
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
        assertWithMessage("Acceptable tokens differ from expected")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testAllowEscapesForControlCharacterSetForAllCharacters() throws Exception {

        final int indexOfStartLineInInputFile = 16;
        final String message = getCheckMessage(MSG_KEY);
        final String[] expected = IntStream.rangeClosed(0, 0xFFFF)
                .parallel()
                .filter(val -> !isControlCharacter(val))
                .mapToObj(msg -> indexOfStartLineInInputFile + msg + ":54: " + message)
                .toArray(String[]::new);
        verifyWithInlineConfigParser(
                getPath("InputAvoidEscapedUnicodeCharactersAllEscapedUnicodeCharacters.java"),
                expected);
    }

    /**
     * Method countMatches is used only inside isOnlyUnicodeValidChars method, and when
     * pitest mutates 316:13 countMatches++ to countMatches-- it makes no difference for
     * isOnlyUnicodeValidChars method as it applies countMatches to both cases in comparison.
     * It is possible to kill mutation in countMatches method by changing code in
     * isOnlyUnicodeValidChars, but it creates new uncoverable mutations and makes code harder
     * to understand.
     *
     * @throws Exception when code tested throws some exception
     */
    @Test
    public void testCountMatches() throws Exception {
        final AvoidEscapedUnicodeCharactersCheck check = new AvoidEscapedUnicodeCharactersCheck();
        final int actual = TestUtil.invokeMethod(check, "countMatches", Integer.class,
                Pattern.compile("\\\\u[a-fA-F\\d]{4}"), "\\u1234");
        assertWithMessage("Unexpected matches count")
            .that(actual)
            .isEqualTo(1);
    }

    /**
     * Testing, that all elements in the constant NON_PRINTABLE_CHARS are sorted.
     * This is very convenient for the sake of maintainability.
     */
    @Test
    public void testNonPrintableCharsAreSorted() {
        String expression = TestUtil.getInternalStaticState(
                AvoidEscapedUnicodeCharactersCheck.class,
                "NON_PRINTABLE_CHARS", Pattern.class).pattern();

        // Replacing expressions like "\\u000[bB]" with "\\u000B"
        final String[] charExpressions = {"Aa", "Bb", "Cc", "Dd", "Ee", "Ff"};
        for (String charExpression : charExpressions) {
            final String regex = "\\[[" + charExpression + "]{2}]";
            expression = expression.replaceAll(regex, charExpression.substring(0, 1));
        }

        // Replacing duplications like "\\uF{3}9" with "\\uFFF9"
        for (int i = 4; i > 1; i--) {
            final String regex = "([A-F])\\{" + i + "}";
            String replacement = "$1$1{" + (i - 1) + "}";
            if (i == 2) {
                replacement = "$1$1";
            }
            expression = expression.replaceAll(regex, replacement);
        }

        // Verifying character order
        final List<String> expressionParts = Splitter.on("|").splitToList(expression);
        final Pattern unicodeCharPattern = Pattern.compile("^\\\\\\\\u[\\dA-F]{4}$");
        String lastChar = null;
        for (int i = 0; i < expressionParts.size(); i++) {
            final String currentChar = expressionParts.get(i);
            final Matcher matcher = unicodeCharPattern.matcher(currentChar);
            if (!matcher.matches()) {
                final String message = "Character '" + currentChar + "' (at position " + i
                        + ") doesn't match the pattern";
                assertWithMessage(message)
                        .that(matcher.matches())
                        .isTrue();
            }
            if (lastChar != null) {
                final String message = "Character '" + lastChar + "' should be after '"
                        + currentChar + "', position: " + i;
                assertWithMessage(message)
                        .that(lastChar.compareTo(currentChar) < 0)
                        .isTrue();
            }
            lastChar = currentChar;
        }
    }

    private static boolean isControlCharacter(final int character) {
        return Arrays.binarySearch(C0_CONTROL_CHARACTER_INDICES, character) >= 0
                || Arrays.binarySearch(C1_CONTROL_CHARACTER_INDICES, character) >= 0
                || Arrays.binarySearch(OTHER_CONTROL_CHARACTER_INDICES, character) >= 0;
    }

}
