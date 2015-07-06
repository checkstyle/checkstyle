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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck.MSG_KEY;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AbbreviationAsWordInNameCheckTest extends BaseCheckTestSupport {

    /** Warning message*/
    private String warningMessage;

    @Test
    public void testTypeNamesForThreePermitedCapitalLetters() throws Exception {

        final DefaultConfiguration checkConfig = createCheckConfig(AbbreviationAsWordInNameCheck.class);
        final int expectedCapitalCount = 3;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "III");
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "9: " + warningMessage,
            "12: " + warningMessage,
            "32: " + warningMessage,
            "37: " + warningMessage,
        };

        verify(checkConfig, getPath("naming/InputAbbreviationAsWordInTypeNameCheck.java"), expected);
    }

    @Test
    public void testTypeNamesForFourPermitedCapitalLetters() throws Exception {

        final int expectedCapitalCount = 4;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        final DefaultConfiguration checkConfig = createCheckConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "CLASS,FACTORY");
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "32: " + warningMessage,
        };

        verify(checkConfig, getPath("naming/InputAbbreviationAsWordInTypeNameCheck.java"), expected);
    }

    @Test
    public void testTypeNamesForFivePermitedCapitalLetters() throws Exception {

        final int expectedCapitalCount = 5;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        final DefaultConfiguration checkConfig = createCheckConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "CLASS");
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final String[] expected = {
            "32: " + warningMessage,
            "37: " + warningMessage,
        };

        verify(checkConfig, getPath("naming/InputAbbreviationAsWordInTypeNameCheck.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNames() throws Exception {

        final int expectedCapitalCount = 5;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        final DefaultConfiguration checkConfig = createCheckConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "CLASS");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "32: " + warningMessage,
            "37: " + warningMessage,
            "38: " + warningMessage,
            "39: " + warningMessage,
            "40: " + warningMessage,
            "58: " + warningMessage,
        };

        verify(checkConfig, getPath("naming/InputAbbreviationAsWordInTypeNameCheck.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithNoIgnores() throws Exception {

        final int expectedCapitalCount = 5;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        final DefaultConfiguration checkConfig = createCheckConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "NUMBER,MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "false");
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "32: " + warningMessage,
            "37: " + warningMessage,
            "38: " + warningMessage,
            "66: " + warningMessage,
            "72: " + warningMessage,
            "78: " + warningMessage,
            "84: " + warningMessage,
        };

        verify(checkConfig, getPath("naming/InputAbbreviationAsWordInTypeNameCheck.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnores() throws Exception {

        final int expectedCapitalCount = 5;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        final DefaultConfiguration checkConfig = createCheckConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "NUMBER,MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "true");
        checkConfig.addAttribute("ignoreFinal", "true");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "32: " + warningMessage,
            "37: " + warningMessage,
            "38: " + warningMessage,
        };

        verify(checkConfig, getPath("naming/InputAbbreviationAsWordInTypeNameCheck.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresFinal() throws Exception {

        final int expectedCapitalCount = 4;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        final DefaultConfiguration checkConfig = createCheckConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "false");
        checkConfig.addAttribute("ignoreFinal", "true");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "12: " + warningMessage,
            "32: " + warningMessage,
            "37: " + warningMessage,
            "38: " + warningMessage,
            "58: " + warningMessage, // not in ignore list
            "60: " + warningMessage, // no ignore for static
        };

        verify(checkConfig, getPath("naming/InputAbbreviationAsWordInTypeNameCheck.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresStatic() throws Exception {

        final int expectedCapitalCount = 5;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        final DefaultConfiguration checkConfig = createCheckConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "true");
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "32: " + warningMessage,
            "37: " + warningMessage,
            "38: " + warningMessage,
            "58: " + warningMessage, // not in ignore list
            "59: " + warningMessage, // no ignore for final
        };

        verify(checkConfig, getPath("naming/InputAbbreviationAsWordInTypeNameCheck.java"), expected);
    }

    @Test
    public void testTypeNamesForThreePermitedCapitalLettersWithOverridenMethod() throws Exception {

        final DefaultConfiguration checkConfig = createCheckConfig(AbbreviationAsWordInNameCheck.class);
        final int expectedCapitalCount = 3;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "");
        checkConfig.addAttribute("tokens", "CLASS_DEF, METHOD_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "22: " + warningMessage,
        };

        verify(checkConfig,
                getPath("naming/InputAbbreviationAsWordInTypeNameCheckOverridableMethod.java"), expected);
    }

    @Test
    public void testTypeNamesForZeroPermitedCapitalLetter() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AbbreviationAsWordInNameCheck.class);
        final int expectedCapitalCount = 0;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        checkConfig.addAttribute("allowedAbbreviationLength",
                String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "");
        checkConfig.addAttribute("ignoreStatic", "false");
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("ignoreOverriddenMethods", "false");
        checkConfig.addAttribute("tokens", "CLASS_DEF,INTERFACE_DEF,ENUM_DEF,"
            + "ANNOTATION_DEF,ANNOTATION_FIELD_DEF,ENUM_CONSTANT_DEF,"
            + "PARAMETER_DEF,VARIABLE_DEF,METHOD_DEF");
        final String[] expected = {
            "3: " + warningMessage,
            "6: " + warningMessage,
            "9: " + warningMessage,
            "12: " + warningMessage,
            "32: " + warningMessage,
            "37: " + warningMessage,
            "38: " + warningMessage,
            "39: " + warningMessage,
            "40: " + warningMessage,
            "46: " + warningMessage,
            "47: " + warningMessage,
            "48: " + warningMessage,
            "49: " + warningMessage,
            "57: " + warningMessage,
            "58: " + warningMessage,
            "59: " + warningMessage,
            "60: " + warningMessage,
            "61: " + warningMessage,
            "66: " + warningMessage,
            "72: " + warningMessage,
            "78: " + warningMessage,
            "84: " + warningMessage,
            "88: " + warningMessage,
            "90: " + warningMessage,
            "98: " + warningMessage,
        };
        verify(checkConfig,
                getPath("naming/InputAbbreviationAsWordInTypeNameCheck.java"), expected);
    }

    @Test
    public void testNPE() throws Exception {

        final DefaultConfiguration checkConfig = createCheckConfig(AbbreviationAsWordInNameCheck.class);
        final int expectedCapitalCount = 1;
        warningMessage = getCheckMessage(MSG_KEY, expectedCapitalCount);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("allowedAbbreviations", null);

        final String[] expected = {
        };

        verify(checkConfig, getPath("naming/AbstractMultisetSetCountTester.java"), expected);
    }
}
