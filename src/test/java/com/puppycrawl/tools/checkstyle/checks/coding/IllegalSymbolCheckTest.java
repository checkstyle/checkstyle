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

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalSymbolCheck.MSG_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IllegalSymbolCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegalsymbol";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "12:7: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolDefault.java"), expected);
    }

    @Test
    public void testEmojiInComment() throws Exception {
        final String[] expected = {
            "12:18: " + getCheckMessage(MSG_KEY),
            "13:18: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolEmoji.java"), expected);
    }

    @Test
    public void testAsciiOnly() throws Exception {
        final String[] expected = {
            "12:18: " + getCheckMessage(MSG_KEY),
            "13:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolAsciiOnly.java"),
                expected);
    }

    @Test
    public void testMultipleRanges() throws Exception {
        final String[] expected = {
            "12:19: " + getCheckMessage(MSG_KEY),
            "13:19: " + getCheckMessage(MSG_KEY),
            "14:19: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolMultipleRanges.java"), expected);
    }

    @Test
    public void testMultipleViolationsInSameToken() throws Exception {
        final String[] expected = {
            "12:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolMultiple.java"), expected);
    }

    @Test
    public void testNoViolationWhenNotConfigured() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolNoConfig.java"), expected);
    }

    @Test
    public void testSymbolStringLiteral() throws Exception {
        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolStringLiteral.java"), expected);
    }

    @Test
    public void testCharLiteral() throws Exception {
        final String[] expected = {
            "12:14: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolChar.java"), expected);
    }

    @Test
    public void testTextBlock() throws Exception {
        final String[] expected = {
            "12:7: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolTextBlock.java"), expected);
    }

    @Test
    public void testLowercasePlusU() throws Exception {
        final String[] expected = {
            "12:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolLowercaseU.java"), expected);
    }

    @Test
    public void testUppercase0X() throws Exception {
        final String[] expected = {
            "12:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolUppercase0X.java"), expected);
    }

    @Test
    public void testHexWithoutPrefix() throws Exception {
        final String[] expected = {
            "12:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolHexNoPrefix.java"), expected);
    }

    @Test
    public void testBackslashFormatU() throws Exception {
        final String[] expected = {
            "12:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolBackslashU.java"), expected);
    }

    @Test
    public void testUnicodePlusFormat() throws Exception {
        final String[] expected = {
            "14:7: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolUnicodePlus.java"), expected);
    }

    @Test
    public void testInvalidRangeMultipleDashes() throws Exception {
        try {
            verifyWithInlineConfigParser(
                    getPath("InputIllegalSymbolMultipleDashes.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
            fail("CheckstyleException expected");
        }
        catch (CheckstyleException exception) {
            assertTrue("Exception should indicate initialization failure",
                    exception.getMessage().contains("cannot initialize module"));
        }
    }

    @Test
    public void testSetSymbolCodesNull() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolNullConfig.java"),
                expected);
    }

    @Test
    public void testSetSymbolCodesEmptyViaConfig() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolEmptyConfig.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testInvalidSingleSymbolCode() throws Exception {
        try {
            verifyWithInlineConfigParser(
                    getPath("InputIllegalSymbolInvalidCode.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
            fail("CheckstyleException expected");
        }
        catch (CheckstyleException exception) {
            final String message = exception.getMessage();
            assertTrue("Exception should indicate initialization failure, but got: " + message,
                    message.contains("cannot initialize module")
                            || message.contains("Invalid symbol code format")
                            || message.contains("NumberFormatException")
                            || message.contains("Cannot set property"));
        }
    }

    @Test
    public void testInvalidRangeFormat() throws Exception {
        try {
            verifyWithInlineConfigParser(
                    getPath("InputIllegalSymbolInvalidRange.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
            fail("CheckstyleException expected");
        }
        catch (CheckstyleException exception) {
            final String message = exception.getMessage();
            assertTrue("Exception should indicate initialization failure, but got: " + message,
                    message.contains("cannot initialize module")
                            || message.contains("Invalid range format")
                            || message.contains("Cannot set property"));
        }
    }

    @Test
    public void testRangeStartGreaterThanEnd() throws Exception {
        try {
            verifyWithInlineConfigParser(
                    getPath("InputIllegalSymbolRangeReversed.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
            fail("CheckstyleException expected");
        }
        catch (CheckstyleException exception) {
            final String message = exception.getMessage();
            assertTrue("Exception should indicate initialization failure, but got: " + message,
                    message.contains("cannot initialize module")
                            || message.contains("Range start must be <= end")
                            || message.contains("Cannot set property"));
        }
    }

    @Test
    public void testInvalidShortCodePoint() throws Exception {
        try {
            verifyWithInlineConfigParser(
                    getPath("InputIllegalSymbolShortCode.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
            fail("CheckstyleException expected");
        }
        catch (CheckstyleException exception) {
            final String message = exception.getMessage();
            assertTrue("Exception should indicate initialization failure, but got: " + message,
                    message.contains("cannot initialize module")
                            || message.contains("Invalid code point format")
                            || message.contains("NumberFormatException")
                            || message.contains("Cannot set property"));
        }
    }

    @Test
    public void testSetSymbolCodesIgnoresEmptyEntries() throws Exception {
        final String[] expected = {
            "12:7: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolIgnoreEmpty.java"),
                expected);
    }

    @Test
    public void testSetSymbolCodesEmptyList() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolNoConfig.java"),
                expected);

        assertEquals("Expected should have 0 violations", 0, expected.length);
    }

    @Test
    public void testSetSymbolCodesBlankRangeStart() throws Exception {
        try {
            verifyWithInlineConfigParser(
                    getPath("InputIllegalSymbolBlankStart.java"),
                    CommonUtil.EMPTY_STRING_ARRAY);
            fail("CheckstyleException expected");
        }
        catch (CheckstyleException exception) {
            final String message = exception.getMessage();
            assertTrue("Exception should indicate initialization failure, but got: " + message,
                    message.contains("cannot initialize module")
                            || message.contains("Invalid range format")
                            || message.contains("Cannot set property"));
        }
    }

    @Test
    public void testContainsReturnsFalse() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolOutsideRange.java"),
                expected);
    }

    @Test
    public void testSetSymbolCodesEmptyString() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolEmptyConfig.java"),
                expected);

        assertEquals("Expected should have 0 violations", 0, expected.length);
    }

    @Test
    public void testRangeStartBoundary() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolRangeStart.java"), expected);
    }

    @Test
    public void testRangeEndBoundary() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolRangeEnd.java"), expected);
    }

    @Test
    public void testSetSymbolCodesTwice() throws Exception {
        final String[] expected = {
            "12:18: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolSetTwice.java"), expected);
    }

    @Test
    public void testSymbolNotInRange() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolNotInRange.java"), expected);
    }

    @Test
    public void testEmptyConfigDoesNothing() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolEmpty.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testJustOutsideRange() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolJustOutsideRange.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testSetThenClearConfig() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolSetThenClear.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }
}
