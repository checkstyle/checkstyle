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
            "30:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "42:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "43:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "45:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "46:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "59:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "60:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "62:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "64:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "83:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "84:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "86:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "88:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "90:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "98:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "100:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "101:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "104:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "105:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "106:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "107:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "108:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "109:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
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
            "44:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "47:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "61:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "63:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "65:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "85:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "87:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "89:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "91:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "99:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "101:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "102:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "105:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "106:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "107:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "108:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "109:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "110:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestItWithAllowsOn.java"), expected);
    }

    @Test
    public void testSingleLineStatements() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "32:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "38:43: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "47:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "55:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "62:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "65:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "71:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "100:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "104:11: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "116:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "123:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
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
            "16:29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "19:22: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "24:60: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "25:27: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestSingleLineLambda.java"), expected);
    }

    @Test
    public void testDoNotAllowSingleLineLambda() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        final String[] expected = {
            "14:28: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "15:29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "16:29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "19:22: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "24:60: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "25:27: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
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
            "81:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "84:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "131:17: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "133:17: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
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
            "18:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "19:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "22:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "25:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "33:17: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "34:17: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
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
            "50:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "53:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "65:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
        };
        verify(checkConfig, getPath("InputNeedBracesTestConditions.java"), expected);
    }

    @Test
    public void testAllowEmptyLoopBodyTrue() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("allowEmptyLoopBody", "true");
        final String[] expected = {
            "106:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
        };
        verify(checkConfig, getPath("InputNeedBracesLoopBodyTrue.java"), expected);
    }

    @Test
    public void testAllowEmptyLoopBodyFalse() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NeedBracesCheck.class);
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "23:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "27:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "28:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "32:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "37:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "42:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "48:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "54:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "59:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "63:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "69:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "76:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "98:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "102:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "106:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "117:9: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
        };
        verify(checkConfig, getPath("InputNeedBracesLoopBodyFalse.java"), expected);
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
            "16:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "18:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "20:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "23:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "26:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "33:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "36:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "39:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "42:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "49:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "50:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "53:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "56:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "59:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "73:47: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
            "80:13: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
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
