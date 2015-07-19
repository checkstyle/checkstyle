////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NeedBracesCheckTest extends BaseCheckTestSupport {
    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NeedBracesCheck.class);
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
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testSigleLineStatements() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "38: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "46: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "53: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "59: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "88: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "92: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
        };
        verify(checkConfig, getPath("InputBracesSingleLineStatements.java"), expected);
    }

    @Test
    public void testSigleLineLambda() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
        };
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/blocks/InputSingleLineLambda.java").getCanonicalPath(),
                expected);
    }

    @Test
    public void testSigleLineCaseDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_CASE, LITERAL_DEFAULT");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "72: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
        };
        verify(checkConfig, getPath("InputBracesSingleLineStatements.java"), expected);
    }

    @Test
    public void testCycles() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_WHILE, LITERAL_DO, LITERAL_FOR");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputNeedBracesCheckTest.java"), expected);
    }

    @Test
    public void testConditions() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(NeedBracesCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_ELSE, LITERAL_CASE, LITERAL_DEFAULT");
        checkConfig.addAttribute("allowSingleLineStatement", "true");
        final String[] expected = {
            "29: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "35: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "36: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "38: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "41: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "44: " + getCheckMessage(MSG_KEY_NEED_BRACES, "case"),
            "49: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
            "56: " + getCheckMessage(MSG_KEY_NEED_BRACES, "default"),
        };
        verify(checkConfig, getPath("InputNeedBracesCheckTest.java"), expected);
    }
}
