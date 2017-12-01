////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.BeforeRightCurlyCheck.MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.BeforeRightCurlyCheck.MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class BeforeRightCurlyCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/beforerightcurly";
    }

    @Test
    public void testGetAcceptableTokens() {
        final BeforeRightCurlyCheck checkObj = new BeforeRightCurlyCheck();
        final int[] actual = checkObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.ANNOTATION_ARRAY_INIT,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ARRAY_INIT,
            TokenTypes.CASE_GROUP,
            TokenTypes.CLASS_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.METHOD_DEF,
            TokenTypes.SLIST,
            TokenTypes.STATIC_INIT,
        };
        assertArrayEquals("Default acceptable tokens are invalid", expected, actual);
    }

    @Test
    public void testGetRequiredTokens() {
        final BeforeRightCurlyCheck checkObj = new BeforeRightCurlyCheck();
        assertArrayEquals(
            "BeforeRightCurlyCheck#getRequiredTockens should return empty array by default",
            CommonUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BeforeRightCurlyCheck.class);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputBeforeRightCurlyDefault.java"), expected);
    }

    @Test
    public void testOneLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BeforeRightCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "true");

        final String[] expected = {
            "1:125: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
        };
        verify(checkConfig, getPath("InputBeforeRightCurlyOneLine.java"), expected);
    }

    @Test
    public void testAllowNoBlankLineType() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BeforeRightCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "false");
        checkConfig.addAttribute("tokens", "ANNOTATION_ARRAY_INIT,ANNOTATION_DEF,CLASS_DEF,"
            + "ENUM_DEF,ENUM_CONSTANT_DEF,INTERFACE_DEF");

        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "23:1: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "42:1: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "66:1: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "72:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "81:1: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "99:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "101:1: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
        };
        verify(checkConfig, getPath("InputBeforeRightCurlyType.java"), expected);
    }

    @Test
    public void testBlankLineType() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BeforeRightCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "true");
        checkConfig.addAttribute("tokens", "ANNOTATION_ARRAY_INIT,ANNOTATION_DEF,CLASS_DEF,"
            + "ENUM_DEF,ENUM_CONSTANT_DEF,INTERFACE_DEF");

        final String[] expected = {
            "10:34: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "21:5: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "35:1: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "52:1: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "54:29: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "57:22: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "61:1: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "90:34: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "91:1: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
        };
        verify(checkConfig, getPath("InputBeforeRightCurlyType.java"), expected);
    }

    @Test
    public void testAllowedNoBlankLineMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BeforeRightCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "false");
        checkConfig.addAttribute("tokens", "ARRAY_INIT,CTOR_DEF,METHOD_DEF,LAMBDA,STATIC_INIT,"
            + "INSTANCE_INIT,SLIST");

        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "33:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "46:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "59:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "70:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "83:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "96:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "98:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "100:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
        };
        verify(checkConfig, getPath("InputBeforeRightCurlyMethod.java"), expected);
    }

    @Test
    public void testBlankLineMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BeforeRightCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "true");
        checkConfig.addAttribute("tokens", "ARRAY_INIT,CTOR_DEF,METHOD_DEF,LAMBDA,STATIC_INIT,"
            + "INSTANCE_INIT,SLIST");

        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "27:5: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "40:5: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "54:5: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "66:44: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "71:5: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "78:5: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "89:15: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "89:19: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "90:5: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
        };
        verify(checkConfig, getPath("InputBeforeRightCurlyMethod.java"), expected);
    }

    @Test
    public void testAllowedNoBlankLineLiteral() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BeforeRightCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "false");
        checkConfig.addAttribute("tokens", "LITERAL_CASE,LITERAL_CATCH,LITERAL_DEFAULT,LITERAL_DO,"
            + "LITERAL_ELSE,LITERAL_FINALLY,LITERAL_FOR,LITERAL_IF,LITERAL_SWITCH,"
            + "LITERAL_SYNCHRONIZED,LITERAL_TRY,LITERAL_WHILE,SLIST,LITERAL_NEW,SLIST");

        final String[] expected = {
            "28:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "31:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "37:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "57:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "74:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "91:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "116:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "122:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "127:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "143:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "168:13: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "174:13: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "176:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "204:13: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "211:13: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "214:13: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "218:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
            "233:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_BEFORE_RCURLY, "}"),
        };
        verify(checkConfig, getPath("InputBeforeRightCurlyLiteral.java"), expected);
    }

    @Test
    public void testBlankLineLiteral() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BeforeRightCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "true");
        checkConfig.addAttribute("tokens", "CASE_GROUP,LITERAL_CATCH,LITERAL_DO,LITERAL_ELSE,"
            + "LITERAL_FINALLY,LITERAL_FOR,LITERAL_IF,LITERAL_SWITCH,LITERAL_SYNCHRONIZED,"
            + "LITERAL_TRY,LITERAL_WHILE,LITERAL_NEW,SLIST");

        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "21:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "22:41: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "51:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "69:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "85:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "105:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "109:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "110:18: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "137:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "154:20: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "158:13: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "159:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "183:13: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "188:13: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "191:13: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "193:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
            "228:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_BEFORE_RCURLY, "}"),
        };
        verify(checkConfig, getPath("InputBeforeRightCurlyLiteral.java"), expected);
    }

    @Test
    public void testInvalidBlankLineValue() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(BeforeRightCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "invalid-value");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputBeforeRightCurlyDefault.java"), expected);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            final String messageStart =
                "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "illegal value 'invalid-value' for property 'blankLine' of module";

            assertTrue("Invalid exception message, should start with: " + messageStart,
                ex.getMessage().startsWith(messageStart));
        }
    }

}
