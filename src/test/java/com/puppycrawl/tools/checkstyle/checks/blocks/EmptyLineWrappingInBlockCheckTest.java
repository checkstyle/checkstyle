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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class EmptyLineWrappingInBlockCheckTest extends AbstractModuleTestSupport {

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
        checkConfig.addAttribute("bottomSeparator", "empty_line_allowed");
        checkConfig.addAttribute("tokens", "CLASS_DEF, CTOR_DEF, INTERFACE_DEF, "
            + "LITERAL_TRY, LITERAL_FOR, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, METHOD_DEF, " +
            "ARRAY_INIT, LITERAL_SWITCH, CASE_GROUP, LITERAL_DEFAULT, LITERAL_CASE, SLIST");

        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "12:5: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "38:5: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "40:9: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "43:9: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "46:9: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "55:9: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "56:13: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "56:21: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "71:16: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "78:9: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
            "83:1: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
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
        checkConfig.addAttribute("bottomSeparator", "empty_line_allowed");
        checkConfig.addAttribute("tokens", "CLASS_DEF, CTOR_DEF, INTERFACE_DEF, "
            + "LITERAL_TRY, LITERAL_FOR, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, METHOD_DEF");

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "10:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "13:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "30:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "33:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "37:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "41:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
            "49:1: " + getCheckMessage(MSG_NO_EMPTY_LINE_BEFORE),
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
        checkConfig.addAttribute("topSeparator", "empty_line_allowed");
        checkConfig.addAttribute("tokens", "CLASS_DEF, CTOR_DEF, INTERFACE_DEF, "
            + "LITERAL_TRY, LITERAL_FOR, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, METHOD_DEF");

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "9:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "12:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "28:5: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "31:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "35:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "39:9: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
            "51:1: " + getCheckMessage(MSG_NO_EMPTY_LINE_AFTER),
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
        checkConfig.addAttribute("topSeparator", "empty_line_allowed");
        checkConfig.addAttribute("tokens", "CLASS_DEF, CTOR_DEF, INTERFACE_DEF, "
            + "LITERAL_TRY, LITERAL_FOR, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, METHOD_DEF");

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "9:5: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "12:5: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "32:5: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "34:9: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "37:9: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "40:9: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
            "48:1: " + getCheckMessage(MSG_EMPTY_LINE_AFTER),
        };

        verify(checkConfig,
            getPath("InputEmptyLineWrappingInBlockBottomSeparatorEmptyLine.java"),
            expected);
    }

    @Test
    public void testNoPackage() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EmptyLineWrappingInBlockCheck.class);
        checkConfig.addAttribute("topSeparator", "empty_line");
        checkConfig.addAttribute("bottomSeparator", "empty_line_allowed");

        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_EMPTY_LINE_BEFORE),
        };

        verify(checkConfig,
            getNonCompilablePath("InputEmptyLineWrappingInBlockTopSeparatorNoPackage.java"),
            expected);
    }
}
