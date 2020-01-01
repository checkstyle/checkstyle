////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        0x000a,
        0x000b,
        0x000c,
        0x000d,
        0x000e,
        0x000f,
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
        0x001a,
        0x001b,
        0x001c,
        0x001d,
        0x001e,
        0x001f,
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
        0x008a,
        0x008b,
        0x008c,
        0x008d,
        0x008e,
        0x008f,
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
        0x009a,
        0x009b,
        0x009c,
        0x009d,
        0x009e,
        0x009f,
    };

    // Other control characters which do not occur in the C0 or C1 sets
    // https://en.wiktionary.org/wiki/Appendix:Control_characters#Unicode_control_characters
    private static final int[] OTHER_CONTROL_CHARACTER_INDICES = {
        0x00ad,
        0x034f,
        0x070f,
        0x180e,
        0x200b,
        0x200c,
        0x200d,
        0x200e,
        0x200f,
        0x202a,
        0x202b,
        0x202c,
        0x202d,
        0x202e,
        0x2060,
        0x2061,
        0x2062,
        0x2063,
        0x2064,
        0x206a,
        0x206b,
        0x206c,
        0x206d,
        0x206e,
        0x206f,
        0xfeff,
        0xfff9,
        0xfffa,
        0xfffb,
    };

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/avoidescapedunicodecharacters";
    }

    @Test
    public void testGetRequiredTokens() {
        final AvoidEscapedUnicodeCharactersCheck checkObj =
            new AvoidEscapedUnicodeCharactersCheck();
        final int[] expected = {
            TokenTypes.STRING_LITERAL,
            TokenTypes.CHAR_LITERAL,
        };
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Required tokens differ from expected");
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY),
            "9: " + getCheckMessage(MSG_KEY),
            "11: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "16: " + getCheckMessage(MSG_KEY),
            "20: " + getCheckMessage(MSG_KEY),
            "24: " + getCheckMessage(MSG_KEY),
            "25: " + getCheckMessage(MSG_KEY),
            "27: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
            "32: " + getCheckMessage(MSG_KEY),
            "33: " + getCheckMessage(MSG_KEY),
            "34: " + getCheckMessage(MSG_KEY),
            "42: " + getCheckMessage(MSG_KEY),
            "59: " + getCheckMessage(MSG_KEY),
            "60: " + getCheckMessage(MSG_KEY),
            "61: " + getCheckMessage(MSG_KEY),
            "62: " + getCheckMessage(MSG_KEY),
            "72: " + getCheckMessage(MSG_KEY),
            "73: " + getCheckMessage(MSG_KEY),
            "74: " + getCheckMessage(MSG_KEY),
            "75: " + getCheckMessage(MSG_KEY),
            "76: " + getCheckMessage(MSG_KEY),
            "77: " + getCheckMessage(MSG_KEY),
            "79: " + getCheckMessage(MSG_KEY),
            "82: " + getCheckMessage(MSG_KEY),
            "86: " + getCheckMessage(MSG_KEY),
            "87: " + getCheckMessage(MSG_KEY),
            "88: " + getCheckMessage(MSG_KEY),
            "89: " + getCheckMessage(MSG_KEY),
            "92: " + getCheckMessage(MSG_KEY),
            "93: " + getCheckMessage(MSG_KEY),
            "94: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
            "104: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testAllowEscapesForControlCharacterSet() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowEscapesForControlCharacters", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY),
            "9: " + getCheckMessage(MSG_KEY),
            "11: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "16: " + getCheckMessage(MSG_KEY),
            "24: " + getCheckMessage(MSG_KEY),
            "25: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
            "32: " + getCheckMessage(MSG_KEY),
            "33: " + getCheckMessage(MSG_KEY),
            "34: " + getCheckMessage(MSG_KEY),
            "42: " + getCheckMessage(MSG_KEY),
            "59: " + getCheckMessage(MSG_KEY),
            "60: " + getCheckMessage(MSG_KEY),
            "61: " + getCheckMessage(MSG_KEY),
            "62: " + getCheckMessage(MSG_KEY),
            "73: " + getCheckMessage(MSG_KEY),
            "74: " + getCheckMessage(MSG_KEY),
            "75: " + getCheckMessage(MSG_KEY),
            "76: " + getCheckMessage(MSG_KEY),
            "77: " + getCheckMessage(MSG_KEY),
            "79: " + getCheckMessage(MSG_KEY),
            "82: " + getCheckMessage(MSG_KEY),
            "86: " + getCheckMessage(MSG_KEY),
            "87: " + getCheckMessage(MSG_KEY),
            "88: " + getCheckMessage(MSG_KEY),
            "89: " + getCheckMessage(MSG_KEY),
            "92: " + getCheckMessage(MSG_KEY),
            "94: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
            "104: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testAllowByTailComment() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowByTailComment", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "24: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
            "33: " + getCheckMessage(MSG_KEY),
            "34: " + getCheckMessage(MSG_KEY),
            "59: " + getCheckMessage(MSG_KEY),
            "60: " + getCheckMessage(MSG_KEY),
            "61: " + getCheckMessage(MSG_KEY),
            "62: " + getCheckMessage(MSG_KEY),
            "73: " + getCheckMessage(MSG_KEY),
            "74: " + getCheckMessage(MSG_KEY),
            "75: " + getCheckMessage(MSG_KEY),
            "76: " + getCheckMessage(MSG_KEY),
            "77: " + getCheckMessage(MSG_KEY),
            "79: " + getCheckMessage(MSG_KEY),
            "82: " + getCheckMessage(MSG_KEY),
            "92: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
            "104: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testAllowAllCharactersEscaped() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowIfAllCharactersEscaped", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY),
            "9: " + getCheckMessage(MSG_KEY),
            "11: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "16: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
            "32: " + getCheckMessage(MSG_KEY),
            "33: " + getCheckMessage(MSG_KEY),
            "42: " + getCheckMessage(MSG_KEY),
            "86: " + getCheckMessage(MSG_KEY),
            "87: " + getCheckMessage(MSG_KEY),
            "88: " + getCheckMessage(MSG_KEY),
            "89: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void allowNonPrintableEscapes() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowNonPrintableEscapes", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY),
            "9: " + getCheckMessage(MSG_KEY),
            "11: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "16: " + getCheckMessage(MSG_KEY),
            "24: " + getCheckMessage(MSG_KEY),
            "25: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
            "32: " + getCheckMessage(MSG_KEY),
            "33: " + getCheckMessage(MSG_KEY),
            "34: " + getCheckMessage(MSG_KEY),
            "42: " + getCheckMessage(MSG_KEY),
            "86: " + getCheckMessage(MSG_KEY),
            "87: " + getCheckMessage(MSG_KEY),
            "88: " + getCheckMessage(MSG_KEY),
            "89: " + getCheckMessage(MSG_KEY),
            "93: " + getCheckMessage(MSG_KEY),
            "94: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
            "104: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AvoidEscapedUnicodeCharactersCheck check = new AvoidEscapedUnicodeCharactersCheck();
        final int[] actual = check.getAcceptableTokens();
        final int[] expected = {TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL };
        assertArrayEquals(expected, actual, "Acceptable tokens differ from expected");
    }

    @Test
    public void testAllowEscapesForControlCharacterSetForAllCharacters() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowEscapesForControlCharacters", "true");

        final int indexOfStartLineInInputFile = 6;
        final String message = getCheckMessage(MSG_KEY);
        final String[] expected = IntStream.rangeClosed(0, 0xffff)
                .parallel()
                .filter(val -> !isControlCharacter(val))
                .mapToObj(msg -> indexOfStartLineInInputFile + msg + ": " + message)
                .toArray(String[]::new);
        verify(checkConfig,
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
        final Method countMatches = Whitebox.getMethod(AvoidEscapedUnicodeCharactersCheck.class,
                "countMatches", Pattern.class, String.class);
        final AvoidEscapedUnicodeCharactersCheck check = new AvoidEscapedUnicodeCharactersCheck();
        final int actual = (int) countMatches.invoke(check,
                Pattern.compile("\\\\u[a-fA-F0-9]{4}"), "\\u1234");
        assertEquals(1, actual, "Unexpected matches count");
    }

    /**
     * Testing, that all elements in the constant NON_PRINTABLE_CHARS are sorted.
     * This is very convenient for the sake of maintainability.
     *
     * @throws Exception when code tested throws some exception
     */
    @Test
    public void testNonPrintableCharsAreSorted() throws Exception {
        // Getting Field Value via Reflection, because the field is private
        final Field field = TestUtil.getClassDeclaredField(
                AvoidEscapedUnicodeCharactersCheck.class, "NON_PRINTABLE_CHARS");
        field.setAccessible(true);
        String expression = ((Pattern) field.get(null)).pattern();

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
        final String[] expressionParts = expression.split("\\|");
        final Pattern unicodeCharPattern = Pattern.compile("^\\\\\\\\u[0-9A-F]{4}$");
        String lastChar = null;
        for (int i = 0; i < expressionParts.length; i++) {
            final String currentChar = expressionParts[i];
            final Matcher matcher = unicodeCharPattern.matcher(currentChar);
            if (!matcher.matches()) {
                final String message = "Character '" + currentChar + "' (at position " + i
                        + ") doesn't match the pattern";
                assertTrue(matcher.matches(), message);
            }
            if (lastChar != null) {
                final String message = "Character '" + lastChar + "' should be after '"
                        + currentChar + "', position: " + i;
                assertTrue(lastChar.compareTo(currentChar) < 0, message);
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
