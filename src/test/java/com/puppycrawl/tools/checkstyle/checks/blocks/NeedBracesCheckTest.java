////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
            "24:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "36:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "37:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "39:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "40:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "53:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "54:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "56:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "58:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "77:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "78:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "80:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "82:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "84:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "92:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "94:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "95:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "98:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "99:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "100:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "101:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "102:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "103:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestIt.java"), expected);
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
            "41:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "44:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "58:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "60:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "62:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "82:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "84:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "86:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "88:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "96:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "98:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "99:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "102:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "103:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "104:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "105:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "106:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "107:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestItWithAllowsOn.java"), expected);
    }

    @Test
    public void testSingleLineStatements() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "27:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "33:43: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "42:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "50:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "57:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "60:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "66:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "95:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "99:11: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "111:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "118:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestSingleLineStatements.java"), expected);
    }

    @Test
    public void testSingleLineLambda() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "12:29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "15:22: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "20:60: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "21:27: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestSingleLineLambda.java"), expected);
    }

    @Test
    public void testDoNotAllowSingleLineLambda() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        final String[] expected = {
            "9:28: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "10:29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "11:29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "14:22: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "19:60: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "20:27: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestNotAllowSingleLineLambda.java"), expected);
    }

    @Test
    public void testSingleLineCaseDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_CASE, LITERAL_DEFAULT");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "77:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "80:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "127:17: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "129:17: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestSingleLineCaseDefault.java"), expected);
    }

    @Test
    public void testSingleLineCaseDefault2() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_CASE, LITERAL_DEFAULT");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputNeedBracesTestSingleLineCaseDefault2.java"), expected);
    }

    @Test
    public void testSingleLineCaseDefaultNoSingleLine() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_CASE, LITERAL_DEFAULT");
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "14:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "17:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "20:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "28:17: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "29:17: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestCaseDefaultNoSingleLine.java"), expected);
    }

    @Test
    public void testCycles() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_WHILE, LITERAL_DO, LITERAL_FOR");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputNeedBracesTestCycles.java"), expected);
    }

    @Test
    public void testConditions() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_ELSE, LITERAL_CASE, LITERAL_DEFAULT");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "46:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "49:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "61:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestConditions.java"), expected);
    }

    @Test
    public void testAllowEmptyLoopBodyTrue() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("allowEmptyLoopBody", "true");
        final String[] expected = {
            "101:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestAllowEmptyLoopBodyTrue.java"), expected);
    }

    @Test
    public void testAllowEmptyLoopBodyFalse() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "17:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "21:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "22:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "26:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "31:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "36:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "42:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "48:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "53:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "57:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "63:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "70:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "92:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "96:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "100:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "111:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestAllowEmptyLoopBodyFalse.java"), expected);
    }

    @Test
    public void testEmptySingleLineDefaultStmt() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputNeedBracesEmptySingleLineDefaultStmt.java"), expected);
    }

    @Test
    public void testNeedBracesSwitchExpressionNoSingleLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens",
            "LITERAL_CASE, LITERAL_DEFAULT, LAMBDA");
        checkConfig.addAttribute("allowSingleLineStatement", "false");

        final String[] expected = {
            "12:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "14:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "16:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "19:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "22:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "29:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "32:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "35:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "38:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "45:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "46:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "49:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "52:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "55:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "69:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "76:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            };
        verify(checkConfig,
            getNonCompilablePath("InputNeedBracesTestSwitchExpressionNoSingleLine.java"),
            expected);
    }

    @Test
    public void testNeedBracesSwitchExpression() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens",
            "LITERAL_CASE, LITERAL_DEFAULT, LAMBDA");
        checkConfig.addAttribute("allowSingleLineStatement", "true");

        final String[] expected = {
            "12:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "14:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "16:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "19:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "22:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "29:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "32:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "35:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "38:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "45:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "46:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "49:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "52:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "55:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "69:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            };
        verify(checkConfig,
            getNonCompilablePath("InputNeedBracesTestSwitchExpression.java"),
            expected);
    }

}
