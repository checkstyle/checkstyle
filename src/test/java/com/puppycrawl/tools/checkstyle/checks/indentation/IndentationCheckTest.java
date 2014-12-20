////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

/**
 *
 * @author  jrichard
 */
public class IndentationCheckTest extends BaseCheckTestSupport
{

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
            "28: 'extends' have incorrect indentation level 3, expected level should be 8.",
            "30: 'member def type' have incorrect indentation level 3, expected level should be 4.",
            "33: 'foo' have incorrect indentation level 8, expected level should be 12.",
            "36: 'int' have incorrect indentation level 8, expected level should be 12.",
            "39: 'true' have incorrect indentation level 13, expected level should be 16.",
            "42: '+' have incorrect indentation level 16, expected level should be 20.",
            "43: 'if' have incorrect indentation level 8, expected level should be 12.",
            "46: 'if rcurly' have incorrect indentation level 11, expected level should be 12.",
            "48: 'method def' child have incorrect indentation level 7, expected level should be 8.",
        };
        verify(checkConfig, getPath("indentation/InputAndroidStyle.java"), expected);
    }

    public void testMethodCallLineWrap() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
            "36: 'method call' child have incorrect indentation level 18, expected level should be 20.",
            "37: 'method call rparen' have incorrect indentation level 14, expected level should be 16.",
        };
        verify(checkConfig, getPath("indentation/InputMethodCallLineWrap.java"), expected);
    }

    @Test
    public void testDifficultAnnotations() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
            "29: '@' have incorrect indentation level 0, expected level should be 4.",
            "30: '@' have incorrect indentation level 0, expected level should be 4.",
            "39: '@' have incorrect indentation level 6, expected level should be 8.",
        };
        verify(checkConfig, getPath("indentation/InputDifficultAnnotations.java"), expected);
    }

    @Test
    public void testDifficultAnnotations() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
            "29: '@' have incorrect indentation level 0, expected level should be 4.",
            "30: '@' have incorrect indentation level 0, expected level should be 4.",
            "39: '@' have incorrect indentation level 6, expected level should be 8.",
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
            "10: '=' have incorrect indentation level 5, expected level should be 6.",
            "45: 'class def rcurly' have incorrect indentation level 3, expected level should be 2.",
        };

        verify(checkConfig, getPath("indentation/InputMembers.java"), expected);
    }

    @Test
    public void testInvalidLabel() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
            "20: 'label' child have incorrect indentation level 10, expected level should be one of the following: 8, 12.",
            "29: 'label' child have incorrect indentation level 2, expected level should be one of the following: 4, 8.",
            "32: 'label' child have incorrect indentation level 18, expected level should be one of the following: 8, 12.",
            "33: 'ctor def' child have incorrect indentation level 18, expected level should be 8.",
            "35: 'label' child have incorrect indentation level 6, expected level should be one of the following: 8, 12.",
            "37: 'label' child have incorrect indentation level 6, expected level should be one of the following: 8, 12.",
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
            "229: '(' have incorrect indentation level 8, expected level should be 12.",
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
            "125: 'void' have incorrect indentation level 4, expected level should be 8.",
            "126: 'method5' have incorrect indentation level 4, expected level should be 8.",
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
            "17: 'ctor def rcurly' have incorrect indentation level 6, expected level should be 4.",
            "20: 'ctor def modifier' have incorrect indentation level 6, expected level should be 4.",
            "21: 'ctor def lcurly' have incorrect indentation level 2, expected level should be 4.",
            "22: 'ctor def rcurly' have incorrect indentation level 6, expected level should be 4.",
            "25: 'method def modifier' have incorrect indentation level 2, expected level should be 4.",
            "26: 'method def rcurly' have incorrect indentation level 6, expected level should be 4.",
            "63: 'method def modifier' have incorrect indentation level 5, expected level should be 4.",
            "64: 'final' have incorrect indentation level 5, expected level should be 9.",
            "65: 'void' have incorrect indentation level 5, expected level should be 9.",
            "66: 'method5' have incorrect indentation level 4, expected level should be 9.",
            "74: 'method def modifier' have incorrect indentation level 3, expected level should be 4.",
            "75: 'final' have incorrect indentation level 3, expected level should be 7.",
            "76: 'void' have incorrect indentation level 3, expected level should be 7.",
            "77: 'method6' have incorrect indentation level 5, expected level should be 7.",
            "87: 'ctor def' child have incorrect indentation level 4, expected level should be 8.",
            "87: 'method call' child have incorrect indentation level 4, expected level should be 8.",
            "92: 'member def type' have incorrect indentation level 6, expected level should be 8.",
            "92: 'method def' child have incorrect indentation level 6, expected level should be 8.",
            "93: 'if' have incorrect indentation level 6, expected level should be 8.",
            "94: 'if' child have incorrect indentation level 10, expected level should be 12.",
            "94: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "95: 'if rcurly' have incorrect indentation level 6, expected level should be 8.",
            "98: 'Arrays' have incorrect indentation level 10, expected level should be 12.",
            "107: '+' have incorrect indentation level 10, expected level should be 12.",
            "107: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "116: 'new' have incorrect indentation level 10, expected level should be 12.",
            "120: 'new' have incorrect indentation level 10, expected level should be 12.",
            "121: ')' have incorrect indentation level 6, expected level should be 8.",
            "125: 'method call rparen' have incorrect indentation level 6, expected level should be 8.",
            "139: '6' have incorrect indentation level 10, expected level should be 12.",
            "139: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "142: '6' have incorrect indentation level 10, expected level should be 12.",
            "142: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "152: 'method call' child have incorrect indentation level 6, expected level should be 12.",
            "164: 'method def' child have incorrect indentation level 4, expected level should be 8.",
            "169: 'method def' child have incorrect indentation level 4, expected level should be 8.",
            "173: 'int' have incorrect indentation level 0, expected level should be 8.",
            "174: 'method9' have incorrect indentation level 4, expected level should be 8.",
            "184: 'method def' child have incorrect indentation level 12, expected level should be 8.",
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
            "26: 'switch' have incorrect indentation level 6, expected level should be 8.",
            "28: 'case' child have incorrect indentation level 10, expected level should be 12.",
            "29: 'block' child have incorrect indentation level 14, expected level should be 16.",
            "29: 'method call' child have incorrect indentation level 14, expected level should be 16.",
            "33: 'block' child have incorrect indentation level 14, expected level should be 16.",
            "35: 'case' child have incorrect indentation level 14, expected level should be 12.",
            "36: 'case' child have incorrect indentation level 10, expected level should be 12.",
            "39: 'case' child have incorrect indentation level 10, expected level should be 12.",
            "40: 'block' child have incorrect indentation level 14, expected level should be 16.",
            "40: 'method call' child have incorrect indentation level 14, expected level should be 16.",
            "41: 'block' child have incorrect indentation level 14, expected level should be 16.",
            "49: 'block' child have incorrect indentation level 14, expected level should be 16.",
            "49: 'method call' child have incorrect indentation level 14, expected level should be 16.",
            "50: 'block' child have incorrect indentation level 18, expected level should be 16.",
            "51: 'block rcurly' have incorrect indentation level 10, expected level should be 12.",
            "55: 'block lcurly' have incorrect indentation level 10, expected level should be 12.",
            "58: 'block rcurly' have incorrect indentation level 14, expected level should be 12.",
            "62: 'block lcurly' have incorrect indentation level 14, expected level should be 12.",
            "65: 'block rcurly' have incorrect indentation level 10, expected level should be 12.",
            "72: 'case' child have incorrect indentation level 14, expected level should be 16.",
            "77: 'case' child have incorrect indentation level 14, expected level should be 16.",
            "85: 'switch rcurly' have incorrect indentation level 6, expected level should be 8.",
            "88: 'switch lcurly' have incorrect indentation level 6, expected level should be 8.",
            "89: 'switch rcurly' have incorrect indentation level 10, expected level should be 8.",
            "91: 'switch lcurly' have incorrect indentation level 10, expected level should be 8.",
            "92: 'switch rcurly' have incorrect indentation level 6, expected level should be 8.",
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
            "17: 'member def type' have incorrect indentation level 2, expected level should be 4.",
            "18: 'member def type' have incorrect indentation level 6, expected level should be 4.",
            "20: 'member def type' have incorrect indentation level 2, expected level should be 4.",
            "24: 'member def type' have incorrect indentation level 6, expected level should be 4.",
            "25: 'array initialization' child have incorrect indentation level 8, expected level should be 10.",
            "26: 'array initialization rcurly' have incorrect indentation level 4, expected level should be one of the following: 6, 10.",
            "29: 'array initialization' child have incorrect indentation level 9, expected level should be 8.",
            "30: 'array initialization' child have incorrect indentation level 7, expected level should be 8.",
            "31: 'array initialization' child have incorrect indentation level 9, expected level should be 8.",
            "36: 'array initialization lcurly' have incorrect indentation level 2, expected level should be 4.",
            "40: 'array initialization rcurly' have incorrect indentation level 6, expected level should be one of the following: 4, 8.",
            "44: 'array initialization lcurly' have incorrect indentation level 2, expected level should be 4.",
            "48: 'array initialization' child have incorrect indentation level 20, expected level should be one of the following: 8, 31, 33.",
            "49: 'array initialization' child have incorrect indentation level 4, expected level should be one of the following: 8, 31, 33.",
            "54: 'array initialization' child have incorrect indentation level 6, expected level should be 8.",
            "59: 'member def type' have incorrect indentation level 2, expected level should be 4.",
            "61: 'member def type' have incorrect indentation level 6, expected level should be 4.",
            "62: 'array initialization rcurly' have incorrect indentation level 2, expected level should be one of the following: 6, 10.",
            "65: 'array initialization' child have incorrect indentation level 6, expected level should be 8.",
            "72: 'array initialization' child have incorrect indentation level 10, expected level should be 12.",
            "85: '1' have incorrect indentation level 8, expected level should be 12.",
            "85: 'array initialization' child have incorrect indentation level 8, expected level should be 12.",
            "96: 'array initialization' child have incorrect indentation level 10, expected level should be 12.",
            "97: 'array initialization' child have incorrect indentation level 14, expected level should be 12.",
            "100: 'array initialization' child have incorrect indentation level 10, expected level should be 12.",
            "101: 'array initialization' child have incorrect indentation level 14, expected level should be 12.",
            "102: 'array initialization rcurly' have incorrect indentation level 6, expected level should be one of the following: 8, 12.",
            "105: 'array initialization lcurly' have incorrect indentation level 6, expected level should be 8.",
            "106: 'array initialization' child have incorrect indentation level 14, expected level should be 12.",
            "107: 'array initialization' child have incorrect indentation level 10, expected level should be 12.",
            "108: 'array initialization rcurly' have incorrect indentation level 6, expected level should be one of the following: 8, 12.",
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
            "21: 'try' have incorrect indentation level 9, expected level should be 8.",
            "22: 'try rcurly' have incorrect indentation level 7, expected level should be 8.",
            "24: 'catch rcurly' have incorrect indentation level 7, expected level should be 8.",
            "26: 'try' have incorrect indentation level 4, expected level should be 8.",
            "27: 'method call' child have incorrect indentation level 8, expected level should be 12.",
            "27: 'try' child have incorrect indentation level 8, expected level should be 12.",
            "28: 'try rcurly' have incorrect indentation level 4, expected level should be 8.",
            "29: 'finally' child have incorrect indentation level 8, expected level should be 12.",
            "29: 'method call' child have incorrect indentation level 8, expected level should be 12.",
            "34: 'catch' child have incorrect indentation level 8, expected level should be 12.",
            "34: 'method call' child have incorrect indentation level 8, expected level should be 12.",
            "39: 'try rcurly' have incorrect indentation level 10, expected level should be 8.",
            "41: 'catch rcurly' have incorrect indentation level 6, expected level should be 8.",
            "48: 'catch rcurly' have incorrect indentation level 5, expected level should be 8.",
            "55: 'catch' child have incorrect indentation level 10, expected level should be 12.",
            "55: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "56: 'catch' child have incorrect indentation level 14, expected level should be 12.",
            "57: 'catch' child have incorrect indentation level 10, expected level should be 12.",
            "57: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "59: 'catch' have incorrect indentation level 6, expected level should be 8.",
            "66: 'try lcurly' have incorrect indentation level 10, expected level should be 8.",
            "68: 'try rcurly' have incorrect indentation level 10, expected level should be 8.",
            "70: 'catch lcurly' have incorrect indentation level 6, expected level should be 8.",
            "73: 'catch rcurly' have incorrect indentation level 10, expected level should be 8.",
            "76: 'catch' child have incorrect indentation level 10, expected level should be 12.",
            "76: 'method call' child have incorrect indentation level 10, expected level should be 12.",
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
            "19: 'class def modifier' have incorrect indentation level 2, expected level should be 0.",
            "25: 'class def lcurly' have incorrect indentation level 2, expected level should be 0.",
            "28: 'class def rcurly' have incorrect indentation level 2, expected level should be 0.",
            "31: 'class def ident' have incorrect indentation level 2, expected level should be 0.",
            "35: 'class def rcurly' have incorrect indentation level 2, expected level should be 0.",
            "40: 'extends' have incorrect indentation level 2, expected level should be 4.",
            "41: 'implements' have incorrect indentation level 2, expected level should be 4.",
            "47: 'extends' have incorrect indentation level 2, expected level should be 4.",
            "55: 'implements' have incorrect indentation level 2, expected level should be 4.",
            "56: 'java' have incorrect indentation level 2, expected level should be 4.",
            "61: 'class def modifier' have incorrect indentation level 2, expected level should be 0.",
            "62: 'class def lcurly' have incorrect indentation level 2, expected level should be 0.",
            "70: 'class def rcurly' have incorrect indentation level 2, expected level should be 0.",
            "74: 'extends' have incorrect indentation level 2, expected level should be 4.",
            "83: 'class def ident' have incorrect indentation level 2, expected level should be 4.",
            "85: 'class def ident' have incorrect indentation level 6, expected level should be 4.",
            "88: 'class def ident' have incorrect indentation level 2, expected level should be 4.",
            "92: 'member def modifier' have incorrect indentation level 6, expected level should be 8.",
            "98: 'int' have incorrect indentation level 10, expected level should be 12.",
            "103: 'member def modifier' have incorrect indentation level 6, expected level should be 8.",
            "108: 'class def rcurly' have incorrect indentation level 6, expected level should be 4.",
            "110: 'class def ident' have incorrect indentation level 6, expected level should be 4.",
            "116: 'class def ident' have incorrect indentation level 6, expected level should be 8.",
            "119: 'class def ident' have incorrect indentation level 10, expected level should be 8.",
            "121: 'class def rcurly' have incorrect indentation level 10, expected level should be 8.",
            "124: 'member def type' have incorrect indentation level 10, expected level should be 12.",
            "129: 'method def' child have incorrect indentation level 10, expected level should be 8.",
            "130: 'object def lcurly' have incorrect indentation level 8, expected level should be one of the following: 10, 14.",
            "134: 'object def rcurly' have incorrect indentation level 8, expected level should be one of the following: 10, 14.",
            "138: 'object def lcurly' have incorrect indentation level 6, expected level should be one of the following: 8, 12.",

            "139: 'method def modifier' have incorrect indentation level 12, expected level should be 10.",
            "141: 'method def rcurly' have incorrect indentation level 12, expected level should be 10.",

            "142: 'object def rcurly' have incorrect indentation level 6, expected level should be one of the following: 8, 12.",
            "147: 'method def modifier' have incorrect indentation level 10, expected level should be 12.",
            "149: 'method def rcurly' have incorrect indentation level 10, expected level should be 12.",
            "185: 'class' have incorrect indentation level 0, expected level should be 4.",
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
            "22: 'block lcurly' have incorrect indentation level 7, expected level should be 8.",
            "23: 'block lcurly' have incorrect indentation level 9, expected level should be 8.",
            "25: 'block lcurly' have incorrect indentation level 9, expected level should be 8.",
            "26: 'block rcurly' have incorrect indentation level 7, expected level should be 8.",
            "28: 'block lcurly' have incorrect indentation level 6, expected level should be 8.",
            "30: 'block rcurly' have incorrect indentation level 6, expected level should be 8.",
            "31: 'block lcurly' have incorrect indentation level 6, expected level should be 8.",
            "34: 'block lcurly' have incorrect indentation level 9, expected level should be 8.",
            "35: 'block' child have incorrect indentation level 13, expected level should be 12.",
            "35: 'member def type' have incorrect indentation level 13, expected level should be 12.",
            "37: 'block' child have incorrect indentation level 13, expected level should be 12.",
            "38: 'block rcurly' have incorrect indentation level 9, expected level should be 8.",
            "41: 'block lcurly' have incorrect indentation level 6, expected level should be 8.",
            "42: 'block' child have incorrect indentation level 10, expected level should be 12.",
            "42: 'member def type' have incorrect indentation level 10, expected level should be 12.",
            "44: 'block' child have incorrect indentation level 10, expected level should be 12.",
            "45: 'block rcurly' have incorrect indentation level 6, expected level should be 8.",
            "48: 'block lcurly' have incorrect indentation level 6, expected level should be 8.",
            "51: 'block' child have incorrect indentation level 10, expected level should be 12.",
            "51: 'member def type' have incorrect indentation level 10, expected level should be 12.",
            "55: 'block lcurly' have incorrect indentation level 10, expected level should be 12.",
            "59: 'block rcurly' have incorrect indentation level 10, expected level should be 12.",
            "64: 'block' child have incorrect indentation level 10, expected level should be 12.",
            "66: 'block lcurly' have incorrect indentation level 10, expected level should be 12.",
            "67: 'block' child have incorrect indentation level 14, expected level should be 16.",
            "67: 'member def type' have incorrect indentation level 14, expected level should be 16.",
            "82: 'block rcurly' have incorrect indentation level 10, expected level should be 12.",
            "91: 'static initialization' have incorrect indentation level 2, expected level should be 4.",
            "92: 'static initialization' have incorrect indentation level 6, expected level should be 4.",
            "96: 'member def type' have incorrect indentation level 7, expected level should be 8.",
            "96: 'static initialization' child have incorrect indentation level 7, expected level should be 8.",
            "99: 'static initialization' have incorrect indentation level 6, expected level should be 4.",
            "101: 'static initialization rcurly' have incorrect indentation level 2, expected level should be 4.",
            "103: 'static initialization' have incorrect indentation level 2, expected level should be 4.",
            "105: 'static initialization rcurly' have incorrect indentation level 6, expected level should be 4.",
            "107: 'static initialization' have incorrect indentation level 2, expected level should be 4.",
            "109: 'member def type' have incorrect indentation level 6, expected level should be 8.",
            "109: 'static initialization' child have incorrect indentation level 6, expected level should be 8.",
            "112: 'static initialization lcurly' have incorrect indentation level 2, expected level should be 4.",
            "113: 'member def type' have incorrect indentation level 6, expected level should be 8.",
            "113: 'static initialization' child have incorrect indentation level 6, expected level should be 8.",
            "114: 'static initialization rcurly' have incorrect indentation level 6, expected level should be 4.",
            "119: 'member def type' have incorrect indentation level 6, expected level should be 8.",
            "119: 'static initialization' child have incorrect indentation level 6, expected level should be 8.",
            "124: 'member def type' have incorrect indentation level 4, expected level should be 8.",
            "124: 'static initialization' child have incorrect indentation level 4, expected level should be 8.",
            "125: 'static initialization rcurly' have incorrect indentation level 2, expected level should be 4.",
            "130: 'static initialization rcurly' have incorrect indentation level 6, expected level should be 4.",
            "133: 'block lcurly' have incorrect indentation level 2, expected level should be 4.",
            "134: 'block lcurly' have incorrect indentation level 6, expected level should be 4.",
            "137: 'block lcurly' have incorrect indentation level 2, expected level should be 4.",
            "139: 'block rcurly' have incorrect indentation level 6, expected level should be 4.",
            "141: 'block lcurly' have incorrect indentation level 6, expected level should be 4.",
            "143: 'block rcurly' have incorrect indentation level 2, expected level should be 4.",
            "146: 'block' child have incorrect indentation level 6, expected level should be 8.",
            "146: 'member def type' have incorrect indentation level 6, expected level should be 8.",
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
            "50: 'if' have incorrect indentation level 1, expected level should be 8.",
            "55: 'if' have incorrect indentation level 9, expected level should be 8.",
            "56: 'if lcurly' have incorrect indentation level 9, expected level should be 8.",
            "57: 'if rcurly' have incorrect indentation level 7, expected level should be 8.",
            "59: 'if' have incorrect indentation level 6, expected level should be 8.",
            "60: 'if lcurly' have incorrect indentation level 5, expected level should be 8.",
            "61: 'if rcurly' have incorrect indentation level 5, expected level should be 8.",
            "65: 'if rcurly' have incorrect indentation level 10, expected level should be 8.",
            "66: 'else rcurly' have incorrect indentation level 7, expected level should be 8.",
            "69: 'if' have incorrect indentation level 9, expected level should be 8.",
            "70: 'if lcurly' have incorrect indentation level 7, expected level should be 8.",
            "72: 'else' have incorrect indentation level 9, expected level should be 8.",
            "74: 'else rcurly' have incorrect indentation level 9, expected level should be 8.",
            "77: 'if' have incorrect indentation level 10, expected level should be 8.",
            "78: 'if rcurly' have incorrect indentation level 7, expected level should be 8.",
            "79: 'else' have incorrect indentation level 9, expected level should be 8.",
            "80: 'else lcurly' have incorrect indentation level 7, expected level should be 8.",
            "81: 'else rcurly' have incorrect indentation level 9, expected level should be 8.",
            "85: 'if' have incorrect indentation level 9, expected level should be 8.",
            "86: 'if lcurly' have incorrect indentation level 9, expected level should be 8.",
            "87: 'if rcurly' have incorrect indentation level 9, expected level should be 8.",
            "88: 'else lcurly' have incorrect indentation level 7, expected level should be 8.",
            "89: 'else rcurly' have incorrect indentation level 10, expected level should be 8.",
            "92: 'if' have incorrect indentation level 6, expected level should be 8.",
            "93: 'if lcurly' have incorrect indentation level 10, expected level should be 8.",
            "94: 'if rcurly' have incorrect indentation level 10, expected level should be 8.",
            "95: 'else rcurly' have incorrect indentation level 7, expected level should be 8.",
            "98: 'if' have incorrect indentation level 5, expected level should be 8.",
            "99: 'if rcurly' have incorrect indentation level 11, expected level should be 8.",
            "100: 'else' have incorrect indentation level 5, expected level should be 8.",
            "101: 'else rcurly' have incorrect indentation level 11, expected level should be 8.",
            "121: 'if' child have incorrect indentation level 14, expected level should be 12.",
            "126: 'if lcurly' have incorrect indentation level 10, expected level should be 8.",
            "127: 'if' child have incorrect indentation level 10, expected level should be 12.",
            "127: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "132: 'if' child have incorrect indentation level 14, expected level should be 12.",
            "133: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "135: 'else' child have incorrect indentation level 10, expected level should be 12.",
            "135: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "136: 'method call' child have incorrect indentation level 8, expected level should be 12.",
            "143: 'if' child have incorrect indentation level 16, expected level should be 12.",
            "144: 'if rcurly' have incorrect indentation level 9, expected level should be 8.",
            "147: 'else' child have incorrect indentation level 16, expected level should be 12.",
            "153: 'if' child have incorrect indentation level 0, expected level should be 12.",
            "153: 'method call' child have incorrect indentation level 0, expected level should be 12.",
            "157: 'else' child have incorrect indentation level 40, expected level should be 12.",
            "164: 'if' child have incorrect indentation level 14, expected level should be 12.",
            "167: 'else' child have incorrect indentation level 14, expected level should be 12.",
            "173: 'if' child have incorrect indentation level 10, expected level should be 12.",
            "173: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "175: 'else' child have incorrect indentation level 10, expected level should be 12.",
            "175: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "179: 'if' have incorrect indentation level 10, expected level should be 8.",
            "180: 'if' child have incorrect indentation level 14, expected level should be 12.",
            "181: 'if rcurly' have incorrect indentation level 10, expected level should be 8.",
            "182: 'else' have incorrect indentation level 10, expected level should be 8.",
            "183: 'else' child have incorrect indentation level 14, expected level should be 12.",
            "184: 'else rcurly' have incorrect indentation level 10, expected level should be 8.",
            "187: '&&' have incorrect indentation level 9, expected level should be 12.",
            "187: 'if' child have incorrect indentation level 9, expected level should be 12.",
            "188: '&&' have incorrect indentation level 11, expected level should be 12.",
            "188: 'if' child have incorrect indentation level 11, expected level should be 12.",
            "192: 'if' child have incorrect indentation level 10, expected level should be 12.",
            "195: 'if rcurly' have incorrect indentation level 7, expected level should be 8.",
            "202: 'if' child have incorrect indentation level 10, expected level should be 12.",
            "202: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "204: 'if' child have incorrect indentation level 10, expected level should be 12.",
            "204: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "220: 'if' have incorrect indentation level 10, expected level should be 12.",
            "224: 'if' child have incorrect indentation level 18, expected level should be 20.",
            "224: 'method call' child have incorrect indentation level 18, expected level should be 20.",
            "228: 'if rcurly' have incorrect indentation level 40, expected level should be 8.",
            "235: 'if rparen' have incorrect indentation level 10, expected level should be 8.",
            "240: 'if rparen' have incorrect indentation level 6, expected level should be 8.",
            "246: '(' have incorrect indentation level 6, expected level should be 12.",
            "246: 'if lparen' have incorrect indentation level 6, expected level should be 8.",
            "248: 'if rparen' have incorrect indentation level 6, expected level should be 8.",
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
            "21: 'while' have incorrect indentation level 9, expected level should be 8.",
            "22: 'while rcurly' have incorrect indentation level 7, expected level should be 8.",
            "24: 'while' have incorrect indentation level 7, expected level should be 8.",
            "25: 'while lcurly' have incorrect indentation level 9, expected level should be 8.",
            "26: 'while rcurly' have incorrect indentation level 9, expected level should be 8.",
            "28: 'while' have incorrect indentation level 9, expected level should be 8.",
            "29: 'while lcurly' have incorrect indentation level 6, expected level should be 8.",
            "30: 'while' child have incorrect indentation level 14, expected level should be 12.",
            "31: 'while rcurly' have incorrect indentation level 6, expected level should be 8.",
            "33: 'while' have incorrect indentation level 10, expected level should be 8.",
            "35: 'while rcurly' have incorrect indentation level 10, expected level should be 8.",
            "37: 'while' have incorrect indentation level 10, expected level should be 8.",
            "40: 'while rcurly' have incorrect indentation level 10, expected level should be 8.",
            "42: 'while' have incorrect indentation level 6, expected level should be 8.",
            "43: 'while lcurly' have incorrect indentation level 10, expected level should be 8.",
            "46: 'while rcurly' have incorrect indentation level 6, expected level should be 8.",
            "49: 'if' have incorrect indentation level 14, expected level should be 12.",
            "50: 'if' child have incorrect indentation level 18, expected level should be 16.",
            "51: 'if rcurly' have incorrect indentation level 14, expected level should be 12.",
            "52: 'while' child have incorrect indentation level 14, expected level should be 12.",
            "53: 'while rcurly' have incorrect indentation level 10, expected level should be 8.",
            "56: 'while' child have incorrect indentation level 10, expected level should be 12.",
            "62: 'while' child have incorrect indentation level 10, expected level should be 12.",
            "67: 'while' child have incorrect indentation level 10, expected level should be 12.",
            "74: 'while rparen' have incorrect indentation level 5, expected level should be 8.",
            "81: 'while rparen' have incorrect indentation level 10, expected level should be 8.",
            "88: 'while rparen' have incorrect indentation level 10, expected level should be 8.",
            "95: 'while' child have incorrect indentation level 8, expected level should be 12.",
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
            "22: 'for' have incorrect indentation level 6, expected level should be 8.",
            "23: 'for rcurly' have incorrect indentation level 10, expected level should be 8.",
            "25: 'for' have incorrect indentation level 9, expected level should be 8.",
            "26: 'for lcurly' have incorrect indentation level 6, expected level should be 8.",
            "27: 'for rcurly' have incorrect indentation level 6, expected level should be 8.",
            "31: 'for' child have incorrect indentation level 10, expected level should be 12.",
            "31: 'method call' child have incorrect indentation level 10, expected level should be 12.",
            "32: 'for rcurly' have incorrect indentation level 10, expected level should be 8.",
            "35: 'for lcurly' have incorrect indentation level 10, expected level should be 8.",
            "36: 'for' child have incorrect indentation level 10, expected level should be 12.",
            "36: 'member def type' have incorrect indentation level 10, expected level should be 12.",
            "44: 'for' child have incorrect indentation level 10, expected level should be 12.",
            "44: 'i' have incorrect indentation level 10, expected level should be 12.",
            "50: 'for' have incorrect indentation level 7, expected level should be 8.",
            "51: 'for' child have incorrect indentation level 10, expected level should be 12.",
            "51: 'int' have incorrect indentation level 10, expected level should be 11.",
            "51: 'member def type' have incorrect indentation level 10, expected level should be 12.",
            "60: 'for' child have incorrect indentation level 7, expected level should be 12.",
            "60: 'i' have incorrect indentation level 7, expected level should be 12.",
            "65: 'for' have incorrect indentation level 6, expected level should be 8.",
            "66: 'for' child have incorrect indentation level 10, expected level should be 12.",
            "67: 'for' child have incorrect indentation level 14, expected level should be 16.",
            "68: 'for' child have incorrect indentation level 10, expected level should be 12.",
            "73: 'for rcurly' have incorrect indentation level 39, expected level should be 8.",
            "77: 'for rparen' have incorrect indentation level 12, expected level should be 8.",
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
            "46: 'class' have incorrect indentation level 0, expected level should be 4.",
            "68: 'int' have incorrect indentation level 8, expected level should be 12.",
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
            "25: 'ctor def' child have incorrect indentation level 9, expected level should be 8.",
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
            "25: 'ctor def' child have incorrect indentation level 5, expected level should be 4.",
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
            "23: 'case' child have incorrect indentation level 10, expected level should be 8.",
        };
        verify(checkConfig, getPath("indentation/InputCaseLevel.java"), expected);
    }

    @Test
    public void testBraceAdjustment() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("braceAdjustment", Integer.valueOf(2).toString());
        final String[] expected = {
            "24: 'if rcurly' have incorrect indentation level 8, expected level should be 10.",
        };
        verify(checkConfig, getPath("indentation/InputBraceAdjustment.java"), expected);
    }

    @Test
    public void testInvalidAssignWithChecker() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        final String[] expected = {
            "8: 'getLineNo' have incorrect indentation level 10, expected level should be 12.",
            "10: 'getLine' have incorrect indentation level 10, expected level should be 12.",
            "14: '=' have incorrect indentation level 9, expected level should be 12.",
            "15: '1' have incorrect indentation level 10, expected level should be 12.",
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
        verify(checkConfig, getPath("Input15Extensions.java"), expected);
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
