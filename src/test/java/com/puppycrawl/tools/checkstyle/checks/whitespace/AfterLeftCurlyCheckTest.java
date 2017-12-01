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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.AfterLeftCurlyCheck.MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AfterLeftCurlyCheck.MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class AfterLeftCurlyCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/afterleftcurly";
    }

    @Test
    public void testGetAcceptableTokens() {
        final AfterLeftCurlyCheck checkObj = new AfterLeftCurlyCheck();
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
        final AfterLeftCurlyCheck checkObj = new AfterLeftCurlyCheck();
        assertArrayEquals(
            "AfterLeftCurlyCheck#getRequiredTockens should return empty array by default",
            CommonUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AfterLeftCurlyCheck.class);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAfterLeftCurlyDefault.java"), expected);
    }

    @Test
    public void testOneLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AfterLeftCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "true");

        final String[] expected = {
            "1:108: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
        };
        verify(checkConfig, getPath("InputAfterLeftCurlyOneLine.java"), expected);
    }

    @Test
    public void testAllowNoBlankLineType() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AfterLeftCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "false");
        checkConfig.addAttribute("tokens", "ANNOTATION_ARRAY_INIT,ANNOTATION_DEF,CLASS_DEF,"
            + "ENUM_DEF,ENUM_CONSTANT_DEF,INTERFACE_DEF");

        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "18:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "38:1: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "63:22: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "68:1: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "70:26: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "94:1: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "96:27: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
        };
        verify(checkConfig, getPath("InputAfterLeftCurlyType.java"), expected);
    }

    @Test
    public void testBlankLineType() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AfterLeftCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "true");
        checkConfig.addAttribute("tokens", "ANNOTATION_ARRAY_INIT,ANNOTATION_DEF,CLASS_DEF,"
            + "ENUM_DEF,ENUM_CONSTANT_DEF,INTERFACE_DEF");

        final String[] expected = {
            "10:18: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "16:17: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "32:1: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "49:21: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "54:24: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "56:1: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "57:17: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "89:1: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "90:27: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
        };
        verify(checkConfig, getPath("InputAfterLeftCurlyType.java"), expected);
    }

    @Test
    public void testAllowedNoBlankLineMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AfterLeftCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "false");
        checkConfig.addAttribute("tokens", "ARRAY_INIT,CTOR_DEF,METHOD_DEF,LAMBDA,STATIC_INIT,"
            + "INSTANCE_INIT,SLIST");

        final String[] expected = {
            "17:27: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "30:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "43:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "56:5: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "80:50: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "92:48: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "94:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
        };
        verify(checkConfig, getPath("InputAfterLeftCurlyMethod.java"), expected);
    }

    @Test
    public void testBlankLineMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AfterLeftCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "true");
        checkConfig.addAttribute("tokens", "ARRAY_INIT,CTOR_DEF,METHOD_DEF,LAMBDA,STATIC_INIT,"
            + "INSTANCE_INIT,SLIST");

        final String[] expected = {
            "13:29: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "25:40: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "38:12: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "52:5: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "63:5: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "66:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "76:48: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "88:50: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "89:13: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
        };
        verify(checkConfig, getPath("InputAfterLeftCurlyMethod.java"), expected);
    }

    @Test
    public void testAllowedNoBlankLineLiteral() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AfterLeftCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "false");
        checkConfig.addAttribute("tokens", "LITERAL_CASE,LITERAL_CATCH,LITERAL_DEFAULT,LITERAL_DO,"
            + "LITERAL_ELSE,LITERAL_FINALLY,LITERAL_FOR,LITERAL_IF,LITERAL_SWITCH,"
            + "LITERAL_SYNCHRONIZED,LITERAL_TRY,LITERAL_WHILE,SLIST,LITERAL_NEW,SLIST");

        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "29:31: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "35:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "55:12: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "72:26: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "89:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "114:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "120:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "124:13: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "140:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "162:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "165:21: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "171:13: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "195:9: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "200:21: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "208:13: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "211:15: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
            "226:24: " + getCheckMessage(MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY, "{"),
        };
        verify(checkConfig, getPath("InputAfterLeftCurlyLiteral.java"), expected);
    }

    @Test
    public void testBlankLineLiteral() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AfterLeftCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "true");
        checkConfig.addAttribute("tokens", "CASE_GROUP,LITERAL_CATCH,LITERAL_DO,LITERAL_ELSE,"
            + "LITERAL_FINALLY,LITERAL_FOR,LITERAL_IF,LITERAL_SWITCH,LITERAL_SYNCHRONIZED,"
            + "LITERAL_TRY,LITERAL_WHILE,LITERAL_NEW,SLIST");

        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "20:29: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "23:18: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "50:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "68:9: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "84:26: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "104:23: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "108:23: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "111:14: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "135:28: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "151:19: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "154:13: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "156:13: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "177:19: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "179:21: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "185:13: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "188:13: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
            "222:24: " + getCheckMessage(MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, "{"),
        };
        verify(checkConfig, getPath("InputAfterLeftCurlyLiteral.java"), expected);
    }

    @Test
    public void testInvalidblankLineValue() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AfterLeftCurlyCheck.class);
        checkConfig.addAttribute("blankLine", "invalid-value");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputAfterLeftCurlyDefault.java"), expected);
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
