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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_AFTER;
import static com.puppycrawl.tools.checkstyle.checks.blocks.EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.EmptyLineWrappingInBlockCheck.MSG_NO_EMPTY_LINE_AFTER;
import static com.puppycrawl.tools.checkstyle.checks.blocks.EmptyLineWrappingInBlockCheck.MSG_NO_EMPTY_LINE_BEFORE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class EmptyLineWrappingInBlockCheckTest extends AbstractModuleTestSupport {
    private static final String ALL_TOKENS = "CLASS_DEF, CTOR_DEF, ENUM_DEF, INTERFACE_DEF, "
        + "LITERAL_DO, LITERAL_TRY, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_SWITCH, "
        + "LITERAL_WHILE, METHOD_DEF, STATIC_INIT";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/emptylinewrappinginblock";
    }

    @Test
    public void testGetRequiredTokens() {
        final EmptyLineWrappingInBlockCheck checkObj = new EmptyLineWrappingInBlockCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
            "EmptyLineWrappingInBlockCheck#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputEmptyLineWrappingInBlockDefault.java"),
            expected);
    }

    @Test
    public void testTopSeparatorWithEmptyLinePolicy() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        checkConfig.addAttribute("topSeparator", "empty_line");
        checkConfig.addAttribute("tokens", ALL_TOKENS);

        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "7:5: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "27:5: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "29:9: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "32:9: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "35:9: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "42:1: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
        };

        verify(checkConfig,
            getPath("InputEmptyLineWrappingInBlockTopSeparatorEmptyLine.java"),
            expected);
    }

    @Test
    public void testTopSeparatorWithNoEmptyLinePolicy() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        checkConfig.addAttribute("topSeparator", "no_empty_line");
        checkConfig.addAttribute("tokens", ALL_TOKENS);

        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "5:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "8:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "25:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "28:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "32:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "36:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "44:1: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
        };

        verify(checkConfig,
            getPath("InputEmptyLineWrappingInBlockTopSeparatorNoEmptyLine.java"),
            expected);
    }

    @Test
    public void testBottomSeparatorWithNoEmptyLinePolicy() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        checkConfig.addAttribute("bottomSeparator", "no_empty_line");
        checkConfig.addAttribute("tokens", ALL_TOKENS);

        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "4:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "7:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "23:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "26:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "30:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "34:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "46:1: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
        };

        verify(checkConfig,
            getPath("InputEmptyLineWrappingInBlockBottomSeparatorNoEmptyLine.java"),
            expected);
    }

    @Test
    public void testBottomSeparatorWithEmptyLinePolicy() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        checkConfig.addAttribute("bottomSeparator", "empty_line");
        checkConfig.addAttribute("tokens", ALL_TOKENS);

        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "4:5: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "7:5: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "27:5: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "29:9: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "32:9: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "35:9: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "43:1: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
        };

        verify(checkConfig,
            getPath("InputEmptyLineWrappingInBlockBottomSeparatorEmptyLine.java"),
            expected);
    }
}
