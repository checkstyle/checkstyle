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
package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.indentation.ExpressionHandler.MSG_CHILD_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.indentation.ExpressionHandler.MSG_CHILD_ERROR_MULTI;
import static com.puppycrawl.tools.checkstyle.checks.indentation.ExpressionHandler.MSG_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.indentation.ExpressionHandler.MSG_ERROR_MULTI;

/**
 *
 * @author  jrichard
 */
public class IndentationCheckTest extends BaseCheckTestSupport
{

    @Test
    public void forbidCStyle() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("throwsIndent", "8");
        checkConfig.addAttribute("forceStrictCondition", "true");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_ERROR, "int", 29, 12),
            "6: " + getCheckMessage(MSG_ERROR, "int", 29, 12),
        };
        verify(checkConfig, getPath("indentation/InputMethodCStyle.java"), expected);
    }

    @Test
    public void testZeroCaseLevel() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("caseIndent", "0");
        final String[] expected = {};
        verify(checkConfig, getPath("indentation/InputZeroCaseLevel.java"), expected);
    }

    @Test
    public void testAndroidStyle() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = {
            "28: " + getCheckMessage(MSG_ERROR, "extends", 3, 8),
            "30: " + getCheckMessage(MSG_ERROR, "member def type", 3, 4),
            "33: " + getCheckMessage(MSG_ERROR, "foo", 8, 12),
            "36: " + getCheckMessage(MSG_ERROR, "int", 8, 12),
            "39: " + getCheckMessage(MSG_ERROR, "true", 13, 16),
            "42: " + getCheckMessage(MSG_ERROR, "+", 16, 20),
            "43: " + getCheckMessage(MSG_ERROR, "if", 8, 12),
            "46: " + getCheckMessage(MSG_ERROR, "if rcurly", 11, 12),
            "48: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 7, 8),
        };
        verify(checkConfig, getPath("indentation/InputAndroidStyle.java"), expected);
    }

    @Test
    public void testMethodCallLineWrap() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
            "36: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 18, 20),
            "37: " + getCheckMessage(MSG_ERROR, "method call rparen", 14, 16),
        };
        verify(checkConfig, getPath("indentation/InputMethodCallLineWrap.java"), expected);
    }

    @Test
    public void testDifficultAnnotations() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
            "29: " + getCheckMessage(MSG_ERROR, "@", 0, 4),
            "30: " + getCheckMessage(MSG_ERROR, "@", 0, 4),
            "39: " + getCheckMessage(MSG_ERROR, "@", 6, 8),
        };
        verify(checkConfig, getPath("indentation/InputDifficultAnnotations.java"), expected);
    }

    @Test
    public void testAnonClassesFromGuava() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String[] expected = {
        };
        verify(checkConfig, getPath("indentation/FromGuava2.java"), expected);
    }

    @Test
    public void testAnnotatins() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String[] expected = {};
        verify(checkConfig, getPath("indentation/FromGuava.java"), expected);
    }

    @Test
    public void testCorrectIfAndParameters() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String[] expected = {};
        verify(checkConfig, getPath("indentation/IndentationCorrectIfAndParameterInput.java"), expected);
    }

    @Test
    public void testAnonymousClasses() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String[] expected = {};
        verify(checkConfig, getPath("indentation/InputAnonymousClasses.java"), expected);
    }

    @Test
    public void testArrays() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("arrayInitIndent", "2");
        final String[] expected = {
        };
        verify(checkConfig, getPath("indentation/InputArrays.java"), expected);
    }

    @Test
    public void testLables() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String[] expected = {
        };
        verify(checkConfig, getPath("indentation/InputLabels.java"), expected);
    }

    @Test
    public void testClassesAndMethods() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String[] expected = {
        };
        verify(checkConfig, getPath("indentation/InputClassesMethods.java"), expected);
    }

    @Test
    public void testMembers() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String[] expected = {
            "10: " + getCheckMessage(MSG_ERROR, "=", 5, 6),
            "45: " + getCheckMessage(MSG_ERROR, "class def rcurly", 3, 2),
        };

        verify(checkConfig, getPath("indentation/InputMembers.java"), expected);
    }

    @Test
    public void testInvalidLabel() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
            "20: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 10, "8, 12"),
            "29: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 2, "4, 8"),
            "32: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 18, "8, 12"),
            "33: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 18, 8),
            "35: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 6, "8, 12"),
            "37: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 6, "8, 12"),
        };
        verify(checkConfig, getPath("indentation/InputInvalidLabelIndent.java"), expected);
    }

    @Test
    public void testValidLabel() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("indentation/InputValidLabelIndent.java"), expected);
    }

    @Test
    public void testValidIfWithChecker() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidIfIndent.java");
        final String[] expected = {
            "229: " + getCheckMessage(MSG_ERROR, "(", 8, 12),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidDotWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidDotIndent.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidMethodWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidMethodIndent.java");
        final String[] expected = {
            "125: " + getCheckMessage(MSG_ERROR, "void", 4, 8),
            "126: " + getCheckMessage(MSG_ERROR, "method5", 4, 8),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testInvalidMethodWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputInvalidMethodIndent.java");
        final String[] expected = {
            "17: " + getCheckMessage(MSG_ERROR, "ctor def rcurly", 6, 4),
            "20: " + getCheckMessage(MSG_ERROR, "ctor def modifier", 6, 4),
            "21: " + getCheckMessage(MSG_ERROR, "ctor def lcurly", 2, 4),
            "22: " + getCheckMessage(MSG_ERROR, "ctor def rcurly", 6, 4),
            "25: " + getCheckMessage(MSG_ERROR, "method def modifier", 2, 4),
            "26: " + getCheckMessage(MSG_ERROR, "method def rcurly", 6, 4),
            "63: " + getCheckMessage(MSG_ERROR, "method def modifier", 5, 4),
            "64: " + getCheckMessage(MSG_ERROR, "final", 5, 9),
            "65: " + getCheckMessage(MSG_ERROR, "void", 5, 9),
            "66: " + getCheckMessage(MSG_ERROR, "method5", 4, 9),
            "74: " + getCheckMessage(MSG_ERROR, "method def modifier", 3, 4),
            "75: " + getCheckMessage(MSG_ERROR, "final", 3, 7),
            "76: " + getCheckMessage(MSG_ERROR, "void", 3, 7),
            "77: " + getCheckMessage(MSG_ERROR, "method6", 5, 7),
            "87: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 4, 8),
            "87: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 4, 8),
            "92: " + getCheckMessage(MSG_ERROR, "member def type", 6, 8),
            "92: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 6, 8),
            "93: " + getCheckMessage(MSG_ERROR, "if", 6, 8),
            "94: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "94: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "95: " + getCheckMessage(MSG_ERROR, "if rcurly", 6, 8),
            "98: " + getCheckMessage(MSG_ERROR, "Arrays", 10, 12),
            "107: " + getCheckMessage(MSG_ERROR, "+", 10, 12),
            "107: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "116: " + getCheckMessage(MSG_ERROR, "new", 10, 12),
            "120: " + getCheckMessage(MSG_ERROR, "new", 10, 12),
            "121: " + getCheckMessage(MSG_ERROR, ")", 6, 8),
            "125: " + getCheckMessage(MSG_ERROR, "method call rparen", 6, 8),
            "139: " + getCheckMessage(MSG_ERROR, "6", 10, 12),
            "139: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "142: " + getCheckMessage(MSG_ERROR, "6", 10, 12),
            "142: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "152: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 6, 12),
            "164: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 4, 8),
            "169: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 4, 8),
            "173: " + getCheckMessage(MSG_ERROR, "int", 0, 8),
            "174: " + getCheckMessage(MSG_ERROR, "method9", 4, 8),
            "184: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 12, 8),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testInvalidSwitchWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputInvalidSwitchIndent.java");
        final String[] expected = {
            "26: " + getCheckMessage(MSG_ERROR, "switch", 6, 8),
            "28: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 12),
            "29: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "29: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 14, 16),
            "33: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "35: " + getCheckMessage(MSG_CHILD_ERROR, "case", 14, 12),
            "36: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 12),
            "39: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 12),
            "40: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "40: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 14, 16),
            "41: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "49: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "49: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 14, 16),
            "50: " + getCheckMessage(MSG_CHILD_ERROR, "block", 18, 16),
            "51: " + getCheckMessage(MSG_ERROR, "block rcurly", 10, 12),
            "55: " + getCheckMessage(MSG_ERROR, "block lcurly", 10, 12),
            "58: " + getCheckMessage(MSG_ERROR, "block rcurly", 14, 12),
            "62: " + getCheckMessage(MSG_ERROR, "block lcurly", 14, 12),
            "65: " + getCheckMessage(MSG_ERROR, "block rcurly", 10, 12),
            "72: " + getCheckMessage(MSG_CHILD_ERROR, "case", 14, 16),
            "77: " + getCheckMessage(MSG_CHILD_ERROR, "case", 14, 16),
            "85: " + getCheckMessage(MSG_ERROR, "switch rcurly", 6, 8),
            "88: " + getCheckMessage(MSG_ERROR, "switch lcurly", 6, 8),
            "89: " + getCheckMessage(MSG_ERROR, "switch rcurly", 10, 8),
            "91: " + getCheckMessage(MSG_ERROR, "switch lcurly", 10, 8),
            "92: " + getCheckMessage(MSG_ERROR, "switch rcurly", 6, 8),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidSwitchWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidSwitchIndent.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidArrayInitDefaultIndentWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidArrayInitDefaultIndent.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidArrayInitWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("arrayInitIndent", Integer.valueOf(8).toString());

        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidArrayInitIndent.java");
        final String[] expected = {};
        verify(c, fname, expected);
    }

    @Test
    public void testInvalidArrayInitWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputInvalidArrayInitIndent.java");
        final String[] expected = {
            "17: " + getCheckMessage(MSG_ERROR, "member def type", 2, 4),
            "18: " + getCheckMessage(MSG_ERROR, "member def type", 6, 4),
            "20: " + getCheckMessage(MSG_ERROR, "member def type", 2, 4),
            "24: " + getCheckMessage(MSG_ERROR, "member def type", 6, 4),
            "25: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 8, 10),
            "26: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 4, "6, 10"),
            "29: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 9, 8),
            "30: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 7, 8),
            "31: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 9, 8),
            "36: " + getCheckMessage(MSG_ERROR, "array initialization lcurly", 2, 4),
            "40: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 6, "4, 8"),
            "44: " + getCheckMessage(MSG_ERROR, "array initialization lcurly", 2, 4),
            "48: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "array initialization", 20, "8, 31, 33"),
            "49: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "array initialization", 4, "8, 31, 33"),
            "54: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 6, 8),
            "59: " + getCheckMessage(MSG_ERROR, "member def type", 2, 4),
            "61: " + getCheckMessage(MSG_ERROR, "member def type", 6, 4),
            "62: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 2, "6, 10"),
            "65: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 6, 8),
            "72: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "85: " + getCheckMessage(MSG_ERROR, "1", 8, 12),
            "85: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 8, 12),
            "96: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "97: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 14, 12),
            "100: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "101: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 14, 12),
            "102: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 6, "8, 12"),
            "105: " + getCheckMessage(MSG_ERROR, "array initialization lcurly", 6, 8),
            "106: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 14, 12),
            "107: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "108: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 6, "8, 12"),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidTryWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidTryIndent.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    @Test
    public void testInvalidTryWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputInvalidTryIndent.java");
        final String[] expected = {
            "21: " + getCheckMessage(MSG_ERROR, "try", 9, 8),
            "22: " + getCheckMessage(MSG_ERROR, "try rcurly", 7, 8),
            "24: " + getCheckMessage(MSG_ERROR, "catch rcurly", 7, 8),
            "26: " + getCheckMessage(MSG_ERROR, "try", 4, 8),
            "27: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 12),
            "27: " + getCheckMessage(MSG_CHILD_ERROR, "try", 8, 12),
            "28: " + getCheckMessage(MSG_ERROR, "try rcurly", 4, 8),
            "29: " + getCheckMessage(MSG_CHILD_ERROR, "finally", 8, 12),
            "29: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 12),
            "34: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 8, 12),
            "34: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 12),
            "39: " + getCheckMessage(MSG_ERROR, "try rcurly", 10, 8),
            "41: " + getCheckMessage(MSG_ERROR, "catch rcurly", 6, 8),
            "48: " + getCheckMessage(MSG_ERROR, "catch rcurly", 5, 8),
            "55: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 10, 12),
            "55: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "56: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 14, 12),
            "57: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 10, 12),
            "57: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "59: " + getCheckMessage(MSG_ERROR, "catch", 6, 8),
            "66: " + getCheckMessage(MSG_ERROR, "try lcurly", 10, 8),
            "68: " + getCheckMessage(MSG_ERROR, "try rcurly", 10, 8),
            "70: " + getCheckMessage(MSG_ERROR, "catch lcurly", 6, 8),
            "73: " + getCheckMessage(MSG_ERROR, "catch rcurly", 10, 8),
            "76: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 10, 12),
            "76: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testInvalidClassDefWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputInvalidClassDefIndent.java");
        final String[] expected = {
            "19: " + getCheckMessage(MSG_ERROR, "class def modifier", 2, 0),
            "25: " + getCheckMessage(MSG_ERROR, "class def lcurly", 2, 0),
            "28: " + getCheckMessage(MSG_ERROR, "class def rcurly", 2, 0),
            "31: " + getCheckMessage(MSG_ERROR, "class def ident", 2, 0),
            "35: " + getCheckMessage(MSG_ERROR, "class def rcurly", 2, 0),
            "40: " + getCheckMessage(MSG_ERROR, "extends", 2, 4),
            "41: " + getCheckMessage(MSG_ERROR, "implements", 2, 4),
            "47: " + getCheckMessage(MSG_ERROR, "extends", 2, 4),
            "55: " + getCheckMessage(MSG_ERROR, "implements", 2, 4),
            "56: " + getCheckMessage(MSG_ERROR, "java", 2, 4),
            "61: " + getCheckMessage(MSG_ERROR, "class def modifier", 2, 0),
            "62: " + getCheckMessage(MSG_ERROR, "class def lcurly", 2, 0),
            "70: " + getCheckMessage(MSG_ERROR, "class def rcurly", 2, 0),
            "74: " + getCheckMessage(MSG_ERROR, "extends", 2, 4),
            "83: " + getCheckMessage(MSG_ERROR, "class def ident", 2, 4),
            "85: " + getCheckMessage(MSG_ERROR, "class def ident", 6, 4),
            "88: " + getCheckMessage(MSG_ERROR, "class def ident", 2, 4),
            "92: " + getCheckMessage(MSG_ERROR, "member def modifier", 6, 8),
            "98: " + getCheckMessage(MSG_ERROR, "int", 10, 12),
            "103: " + getCheckMessage(MSG_ERROR, "member def modifier", 6, 8),
            "108: " + getCheckMessage(MSG_ERROR, "class def rcurly", 6, 4),
            "110: " + getCheckMessage(MSG_ERROR, "class def ident", 6, 4),
            "116: " + getCheckMessage(MSG_ERROR, "class def ident", 6, 8),
            "119: " + getCheckMessage(MSG_ERROR, "class def ident", 10, 8),
            "121: " + getCheckMessage(MSG_ERROR, "class def rcurly", 10, 8),
            "124: " + getCheckMessage(MSG_ERROR, "member def type", 10, 12),
            "129: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 10, 8),
            "130: " + getCheckMessage(MSG_ERROR_MULTI, "object def lcurly", 8, "10, 14"),
            "134: " + getCheckMessage(MSG_ERROR_MULTI, "object def rcurly", 8, "10, 14"),
            "138: " + getCheckMessage(MSG_ERROR_MULTI, "object def lcurly", 6, "8, 12"),

            "139: " + getCheckMessage(MSG_ERROR, "method def modifier", 12, 10),
            "141: " + getCheckMessage(MSG_ERROR, "method def rcurly", 12, 10),

            "142: " + getCheckMessage(MSG_ERROR_MULTI, "object def rcurly", 6, "8, 12"),
            "147: " + getCheckMessage(MSG_ERROR, "method def modifier", 10, 12),
            "149: " + getCheckMessage(MSG_ERROR, "method def rcurly", 10, 12),
            "185: " + getCheckMessage(MSG_ERROR, "class", 0, 4),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testInvalidBlockWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputInvalidBlockIndent.java");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_ERROR, "block lcurly", 7, 8),
            "23: " + getCheckMessage(MSG_ERROR, "block lcurly", 9, 8),
            "25: " + getCheckMessage(MSG_ERROR, "block lcurly", 9, 8),
            "26: " + getCheckMessage(MSG_ERROR, "block rcurly", 7, 8),
            "28: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 8),
            "30: " + getCheckMessage(MSG_ERROR, "block rcurly", 6, 8),
            "31: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 8),
            "34: " + getCheckMessage(MSG_ERROR, "block lcurly", 9, 8),
            "35: " + getCheckMessage(MSG_CHILD_ERROR, "block", 13, 12),
            "35: " + getCheckMessage(MSG_ERROR, "member def type", 13, 12),
            "37: " + getCheckMessage(MSG_CHILD_ERROR, "block", 13, 12),
            "38: " + getCheckMessage(MSG_ERROR, "block rcurly", 9, 8),
            "41: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 8),
            "42: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "42: " + getCheckMessage(MSG_ERROR, "member def type", 10, 12),
            "44: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "45: " + getCheckMessage(MSG_ERROR, "block rcurly", 6, 8),
            "48: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 8),
            "51: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "51: " + getCheckMessage(MSG_ERROR, "member def type", 10, 12),
            "55: " + getCheckMessage(MSG_ERROR, "block lcurly", 10, 12),
            "59: " + getCheckMessage(MSG_ERROR, "block rcurly", 10, 12),
            "64: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "66: " + getCheckMessage(MSG_ERROR, "block lcurly", 10, 12),
            "67: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "67: " + getCheckMessage(MSG_ERROR, "member def type", 14, 16),
            "82: " + getCheckMessage(MSG_ERROR, "block rcurly", 10, 12),
            "91: " + getCheckMessage(MSG_ERROR, "static initialization", 2, 4),
            "92: " + getCheckMessage(MSG_ERROR, "static initialization", 6, 4),
            "96: " + getCheckMessage(MSG_ERROR, "member def type", 7, 8),
            "96: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 7, 8),
            "99: " + getCheckMessage(MSG_ERROR, "static initialization", 6, 4),
            "101: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 2, 4),
            "103: " + getCheckMessage(MSG_ERROR, "static initialization", 2, 4),
            "105: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 6, 4),
            "107: " + getCheckMessage(MSG_ERROR, "static initialization", 2, 4),
            "109: " + getCheckMessage(MSG_ERROR, "member def type", 6, 8),
            "109: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 6, 8),
            "112: " + getCheckMessage(MSG_ERROR, "static initialization lcurly", 2, 4),
            "113: " + getCheckMessage(MSG_ERROR, "member def type", 6, 8),
            "113: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 6, 8),
            "114: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 6, 4),
            "119: " + getCheckMessage(MSG_ERROR, "member def type", 6, 8),
            "119: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 6, 8),
            "124: " + getCheckMessage(MSG_ERROR, "member def type", 4, 8),
            "124: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 4, 8),
            "125: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 2, 4),
            "130: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 6, 4),
            "133: " + getCheckMessage(MSG_ERROR, "block lcurly", 2, 4),
            "134: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 4),
            "137: " + getCheckMessage(MSG_ERROR, "block lcurly", 2, 4),
            "139: " + getCheckMessage(MSG_ERROR, "block rcurly", 6, 4),
            "141: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 4),
            "143: " + getCheckMessage(MSG_ERROR, "block rcurly", 2, 4),
            "146: " + getCheckMessage(MSG_CHILD_ERROR, "block", 6, 8),
            "146: " + getCheckMessage(MSG_ERROR, "member def type", 6, 8),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testInvalidIfWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputInvalidIfIndent.java");
        final String[] expected = {
            "50: " + getCheckMessage(MSG_ERROR, "if", 1, 8),
            "55: " + getCheckMessage(MSG_ERROR, "if", 9, 8),
            "56: " + getCheckMessage(MSG_ERROR, "if lcurly", 9, 8),
            "57: " + getCheckMessage(MSG_ERROR, "if rcurly", 7, 8),
            "59: " + getCheckMessage(MSG_ERROR, "if", 6, 8),
            "60: " + getCheckMessage(MSG_ERROR, "if lcurly", 5, 8),
            "61: " + getCheckMessage(MSG_ERROR, "if rcurly", 5, 8),
            "65: " + getCheckMessage(MSG_ERROR, "if rcurly", 10, 8),
            "66: " + getCheckMessage(MSG_ERROR, "else rcurly", 7, 8),
            "69: " + getCheckMessage(MSG_ERROR, "if", 9, 8),
            "70: " + getCheckMessage(MSG_ERROR, "if lcurly", 7, 8),
            "72: " + getCheckMessage(MSG_ERROR, "else", 9, 8),
            "74: " + getCheckMessage(MSG_ERROR, "else rcurly", 9, 8),
            "77: " + getCheckMessage(MSG_ERROR, "if", 10, 8),
            "78: " + getCheckMessage(MSG_ERROR, "if rcurly", 7, 8),
            "79: " + getCheckMessage(MSG_ERROR, "else", 9, 8),
            "80: " + getCheckMessage(MSG_ERROR, "else lcurly", 7, 8),
            "81: " + getCheckMessage(MSG_ERROR, "else rcurly", 9, 8),
            "85: " + getCheckMessage(MSG_ERROR, "if", 9, 8),
            "86: " + getCheckMessage(MSG_ERROR, "if lcurly", 9, 8),
            "87: " + getCheckMessage(MSG_ERROR, "if rcurly", 9, 8),
            "88: " + getCheckMessage(MSG_ERROR, "else lcurly", 7, 8),
            "89: " + getCheckMessage(MSG_ERROR, "else rcurly", 10, 8),
            "92: " + getCheckMessage(MSG_ERROR, "if", 6, 8),
            "93: " + getCheckMessage(MSG_ERROR, "if lcurly", 10, 8),
            "94: " + getCheckMessage(MSG_ERROR, "if rcurly", 10, 8),
            "95: " + getCheckMessage(MSG_ERROR, "else rcurly", 7, 8),
            "98: " + getCheckMessage(MSG_ERROR, "if", 5, 8),
            "99: " + getCheckMessage(MSG_ERROR, "if rcurly", 11, 8),
            "100: " + getCheckMessage(MSG_ERROR, "else", 5, 8),
            "101: " + getCheckMessage(MSG_ERROR, "else rcurly", 11, 8),
            "121: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),
            "126: " + getCheckMessage(MSG_ERROR, "if lcurly", 10, 8),
            "127: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "127: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "132: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),
            "133: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "135: " + getCheckMessage(MSG_CHILD_ERROR, "else", 10, 12),
            "135: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "136: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 12),
            "143: " + getCheckMessage(MSG_CHILD_ERROR, "if", 16, 12),
            "144: " + getCheckMessage(MSG_ERROR, "if rcurly", 9, 8),
            "147: " + getCheckMessage(MSG_CHILD_ERROR, "else", 16, 12),
            "153: " + getCheckMessage(MSG_CHILD_ERROR, "if", 0, 12),
            "153: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 0, 12),
            "157: " + getCheckMessage(MSG_CHILD_ERROR, "else", 40, 12),
            "164: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),
            "167: " + getCheckMessage(MSG_CHILD_ERROR, "else", 14, 12),
            "173: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "173: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "175: " + getCheckMessage(MSG_CHILD_ERROR, "else", 10, 12),
            "175: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "179: " + getCheckMessage(MSG_ERROR, "if", 10, 8),
            "180: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),
            "181: " + getCheckMessage(MSG_ERROR, "if rcurly", 10, 8),
            "182: " + getCheckMessage(MSG_ERROR, "else", 10, 8),
            "183: " + getCheckMessage(MSG_CHILD_ERROR, "else", 14, 12),
            "184: " + getCheckMessage(MSG_ERROR, "else rcurly", 10, 8),
            "187: " + getCheckMessage(MSG_ERROR, "&&", 9, 12),
            "187: " + getCheckMessage(MSG_CHILD_ERROR, "if", 9, 12),
            "188: " + getCheckMessage(MSG_ERROR, "&&", 11, 12),
            "188: " + getCheckMessage(MSG_CHILD_ERROR, "if", 11, 12),
            "192: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "195: " + getCheckMessage(MSG_ERROR, "if rcurly", 7, 8),
            "202: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "202: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "204: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "204: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "220: " + getCheckMessage(MSG_ERROR, "if", 10, 12),
            "224: " + getCheckMessage(MSG_CHILD_ERROR, "if", 18, 20),
            "224: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 18, 20),
            "228: " + getCheckMessage(MSG_ERROR, "if rcurly", 40, 8),
            "235: " + getCheckMessage(MSG_ERROR, "if rparen", 10, 8),
            "240: " + getCheckMessage(MSG_ERROR, "if rparen", 6, 8),
            "246: " + getCheckMessage(MSG_ERROR, "(", 6, 12),
            "246: " + getCheckMessage(MSG_ERROR, "if lparen", 6, 8),
            "248: " + getCheckMessage(MSG_ERROR, "if rparen", 6, 8),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testInvalidWhileWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputInvalidWhileIndent.java");
        final String[] expected = {
            "21: " + getCheckMessage(MSG_ERROR, "while", 9, 8),
            "22: " + getCheckMessage(MSG_ERROR, "while rcurly", 7, 8),
            "24: " + getCheckMessage(MSG_ERROR, "while", 7, 8),
            "25: " + getCheckMessage(MSG_ERROR, "while lcurly", 9, 8),
            "26: " + getCheckMessage(MSG_ERROR, "while rcurly", 9, 8),
            "28: " + getCheckMessage(MSG_ERROR, "while", 9, 8),
            "29: " + getCheckMessage(MSG_ERROR, "while lcurly", 6, 8),
            "30: " + getCheckMessage(MSG_CHILD_ERROR, "while", 14, 12),
            "31: " + getCheckMessage(MSG_ERROR, "while rcurly", 6, 8),
            "33: " + getCheckMessage(MSG_ERROR, "while", 10, 8),
            "35: " + getCheckMessage(MSG_ERROR, "while rcurly", 10, 8),
            "37: " + getCheckMessage(MSG_ERROR, "while", 10, 8),
            "40: " + getCheckMessage(MSG_ERROR, "while rcurly", 10, 8),
            "42: " + getCheckMessage(MSG_ERROR, "while", 6, 8),
            "43: " + getCheckMessage(MSG_ERROR, "while lcurly", 10, 8),
            "46: " + getCheckMessage(MSG_ERROR, "while rcurly", 6, 8),
            "49: " + getCheckMessage(MSG_ERROR, "if", 14, 12),
            "50: " + getCheckMessage(MSG_CHILD_ERROR, "if", 18, 16),
            "51: " + getCheckMessage(MSG_ERROR, "if rcurly", 14, 12),
            "52: " + getCheckMessage(MSG_CHILD_ERROR, "while", 14, 12),
            "53: " + getCheckMessage(MSG_ERROR, "while rcurly", 10, 8),
            "56: " + getCheckMessage(MSG_CHILD_ERROR, "while", 10, 12),
            "62: " + getCheckMessage(MSG_CHILD_ERROR, "while", 10, 12),
            "67: " + getCheckMessage(MSG_CHILD_ERROR, "while", 10, 12),
            "74: " + getCheckMessage(MSG_ERROR, "while rparen", 5, 8),
            "81: " + getCheckMessage(MSG_ERROR, "while rparen", 10, 8),
            "88: " + getCheckMessage(MSG_ERROR, "while rparen", 10, 8),
            "95: " + getCheckMessage(MSG_CHILD_ERROR, "while", 8, 12),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testInvalidForWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputInvalidForIndent.java");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_ERROR, "for", 6, 8),
            "23: " + getCheckMessage(MSG_ERROR, "for rcurly", 10, 8),
            "25: " + getCheckMessage(MSG_ERROR, "for", 9, 8),
            "26: " + getCheckMessage(MSG_ERROR, "for lcurly", 6, 8),
            "27: " + getCheckMessage(MSG_ERROR, "for rcurly", 6, 8),
            "31: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "31: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "32: " + getCheckMessage(MSG_ERROR, "for rcurly", 10, 8),
            "35: " + getCheckMessage(MSG_ERROR, "for lcurly", 10, 8),
            "36: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "36: " + getCheckMessage(MSG_ERROR, "member def type", 10, 12),
            "44: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "44: " + getCheckMessage(MSG_ERROR, "i", 10, 12),
            "50: " + getCheckMessage(MSG_ERROR, "for", 7, 8),
            "51: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "51: " + getCheckMessage(MSG_ERROR, "int", 10, 11),
            "51: " + getCheckMessage(MSG_ERROR, "member def type", 10, 12),
            "60: " + getCheckMessage(MSG_CHILD_ERROR, "for", 7, 12),
            "60: " + getCheckMessage(MSG_ERROR, "i", 7, 12),
            "65: " + getCheckMessage(MSG_ERROR, "for", 6, 8),
            "66: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "67: " + getCheckMessage(MSG_CHILD_ERROR, "for", 14, 16),
            "68: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "73: " + getCheckMessage(MSG_ERROR, "for rcurly", 39, 8),
            "77: " + getCheckMessage(MSG_ERROR, "for rparen", 12, 8),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidForWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidForIndent.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidDoWhileWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidDoWhileIndent.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidBlockWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidBlockIndent.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }


    @Test
    public void testValidWhileWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidWhileIndent.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidClassDefWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidClassDefIndent.java");
        final String[] expected = {
            "46: " + getCheckMessage(MSG_ERROR, "class", 0, 4),
            "68: " + getCheckMessage(MSG_ERROR, "int", 8, 12),
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidInterfaceDefWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidInterfaceDefIndent.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    @Test
    public void testValidCommaWithChecker()
        throws Exception
    {

        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("indentation/InputValidCommaIndent.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    @Test
    public void testTabs() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", Integer.valueOf(4).toString());
        checkConfig.addAttribute("tabWidth", Integer.valueOf(4).toString());
        final String[] expected = {
            "25: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 9, 8),
        };
        verify(checkConfig, getPath("indentation/InputUseTabs.java"), expected);
    }

    @Test
    public void testIndentationLevel() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", Integer.valueOf(2).toString());
        checkConfig.addAttribute("lineWrappingIndentation", Integer.valueOf(2).toString());
        final String[] expected = {
            "25: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 5, 4),
        };
        verify(checkConfig, getPath("indentation/InputUseTwoSpaces.java"), expected);
    }

    @Test
    public void testThrowsIndentationLevel() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("throwsIndent", Integer.valueOf(8).toString());
        final String[] expected = {
        };
        verify(checkConfig, getPath("indentation/InvalidInputThrowsIndent.java"), expected);
    }

    @Test
    public void testCaseLevel() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("caseIndent", Integer.valueOf(0).toString());
        final String[] expected = {
            "23: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 8),
        };
        verify(checkConfig, getPath("indentation/InputCaseLevel.java"), expected);
    }

    @Test
    public void testBraceAdjustment() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("braceAdjustment", Integer.valueOf(2).toString());
        final String[] expected = {
            "24: " + getCheckMessage(MSG_ERROR, "if rcurly", 8, 10),
        };
        verify(checkConfig, getPath("indentation/InputBraceAdjustment.java"), expected);
    }

    @Test
    public void testInvalidAssignWithChecker() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
            "8: " + getCheckMessage(MSG_ERROR, "getLineNo", 10, 12),
            "10: " + getCheckMessage(MSG_ERROR, "getLine", 10, 12),
            "14: " + getCheckMessage(MSG_ERROR, "=", 9, 12),
            "15: " + getCheckMessage(MSG_ERROR, "1", 10, 12),
        };
        verify(checkConfig, getPath("indentation/InputInvalidAssignIndent.java"), expected);
    }

    @Test
    public void testValidAssignWithChecker() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("indentation/InputValidAssignIndent.java"), expected);
    }

    @Test
    public void test15Extensions() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("indentation/Input15Extensions.java"), expected);
    }

    @Test
    public void testTryResources() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IndentationCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("indentation/InputValidTryResourcesIndent.java"),
               expected);
    }

    @Test
    public void testSwitchCustom() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        final String[] expected = {};
        verify(checkConfig, getPath("indentation/InputSwitchCustom.java"),
               expected);
    }
}
