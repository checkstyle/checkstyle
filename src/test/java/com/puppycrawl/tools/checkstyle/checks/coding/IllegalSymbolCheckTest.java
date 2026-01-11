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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IllegalSymbolCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegalsymbol";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolDefault.java"), expected);
    }

    @Test
    public void testEmojiInComment() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_KEY),
            "14:18: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolEmoji.java"), expected);
    }

    @Test
    public void testAsciiOnly() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_KEY),
            "14:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolAsciiOnly.java"),
                expected);
    }

    @Test
    public void testMultipleRanges() throws Exception {
        final String[] expected = {
            "13:19: " + getCheckMessage(MSG_KEY),
            "14:19: " + getCheckMessage(MSG_KEY),
            "15:19: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolMultipleRanges.java"), expected);
    }

    @Test
    public void testMultipleViolationsInSameToken() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_KEY),
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
            "13:16: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolStringLiteral.java"), expected);
    }

    @Test
    public void testCharLiteral() throws Exception {
        final String[] expected = {
            "13:14: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolChar.java"), expected);
    }

    @Test
    public void testTextBlock() throws Exception {
        final String[] expected = {
            "13:7: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolTextBlock.java"), expected);
    }

    @Test
    public void testLowercasePlusU() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolLowercaseU.java"), expected);
    }

    @Test
    public void testUppercase0X() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolUppercase0X.java"), expected);
    }

    @Test
    public void testHexWithoutPrefix() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolHexNoPrefix.java"), expected);
    }

    @Test
    public void testBackslashFormatU() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolBackslashU.java"), expected);
    }

    @Test
    public void testUnicodePlusFormat() throws Exception {
        final String[] expected = {
            "15:7: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolUnicodePlus.java"), expected);
    }

    @Test
    public void testInvalidRangeMultipleDashes() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputIllegalSymbolMultipleDashes.java"), expected);
    }
}
