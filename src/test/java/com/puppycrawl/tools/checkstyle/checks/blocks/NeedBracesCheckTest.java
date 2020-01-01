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

import static com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck.MSG_KEY_NEED_BRACES;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NeedBracesCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/needbraces";
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        final String[] expected = {
            "29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "41: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "42: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "44: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "45: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "58: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "59: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "61: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "63: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "82: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "83: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "85: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "87: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "89: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "97: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "99: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "100: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "103: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "104: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "105: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "106: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "107: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "108: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
        };
        verify(checkConfig, getPath("InputNeedBraces.java"), expected);
    }

    @Test
    public void testItWithAllowsOn() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        checkConfig.addAttribute("allowEmptyLoopBody", "true");
        checkConfig.addAttribute("tokens", "LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, "
            + "LITERAL_WHILE, LITERAL_CASE, LITERAL_DEFAULT, LAMBDA");
        final String[] expected = {
            "42: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "45: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "59: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "61: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "63: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "83: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "85: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "87: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "89: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "97: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "99: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "100: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "103: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "104: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "105: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "106: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "107: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "108: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
        };
        verify(checkConfig, getPath("InputNeedBraces.java"), expected);
    }

    @Test
    public void testSingleLineStatements() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "38: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "46: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "53: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "56: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "62: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "91: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "95: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "107: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "114: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
        };
        verify(checkConfig, getPath("InputNeedBracesSingleLineStatements.java"), expected);
    }

    @Test
    public void testSingleLineLambda() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "10: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "15: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "16: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
        };
        verify(checkConfig, getPath("InputNeedBracesSingleLineLambda.java"), expected);
    }

    @Test
    public void testDoNotAllowSingleLineLambda() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "6: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "7: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "10: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "15: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "16: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
        };
        verify(checkConfig, getPath("InputNeedBracesSingleLineLambda.java"), expected);
    }

    @Test
    public void testSingleLineCaseDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_CASE, LITERAL_DEFAULT");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "72: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "75: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "122: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "124: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
        };
        verify(checkConfig, getPath("InputNeedBracesSingleLineStatements.java"), expected);
    }

    @Test
    public void testSingleLineCaseDefault2() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_CASE, LITERAL_DEFAULT");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputNeedBracesEmptyDefault.java"), expected);
    }

    @Test
    public void testSingleLineCaseDefaultNoSingleLine() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_CASE, LITERAL_DEFAULT");
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "15: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "18: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "21: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "30: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
        };
        verify(checkConfig, getPath("InputNeedBracesCaseDefaultNoSingleLine.java"), expected);
    }

    @Test
    public void testCycles() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_WHILE, LITERAL_DO, LITERAL_FOR");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputNeedBracesConditional.java"), expected);
    }

    @Test
    public void testConditions() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_ELSE, LITERAL_CASE, LITERAL_DEFAULT");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "41: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "44: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "56: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
        };
        verify(checkConfig, getPath("InputNeedBracesConditional.java"), expected);
    }

    @Test
    public void testAllowEmptyLoopBodyTrue() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("allowEmptyLoopBody", "true");
        final String[] expected = {
            "97: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
        };
        verify(checkConfig, getPath("InputNeedBracesNoBodyLoops.java"), expected);
    }

    @Test
    public void testAllowEmptyLoopBodyFalse() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        final String[] expected = {
            "10: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "14: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "18: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "19: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "23: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "28: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "33: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "39: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "45: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "50: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "54: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "60: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "67: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "89: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "93: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "97: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "108: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
        };
        verify(checkConfig, getPath("InputNeedBracesNoBodyLoops.java"), expected);
    }

    @Test
    public void testEmptySingleLineDefaultStmt() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputNeedBracesEmptyDefault.java"), expected);
    }

}
