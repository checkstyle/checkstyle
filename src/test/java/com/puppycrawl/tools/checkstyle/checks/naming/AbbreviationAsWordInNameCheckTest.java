////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AbbreviationAsWordInNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/abbreviationaswordinname";
    }

    @Test
    public void testTypeNamesForThreePermittedCapitalLetters() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "3");
        checkConfig.addAttribute("allowedAbbreviations", "III");
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "9: " + getWarningMessage("FactoryWithBADNAme", expectedCapitalCount),
            "12: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    public void testTypeNamesForFourPermittedCapitalLetters() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "4");
        checkConfig.addAttribute("allowedAbbreviations", "CLASS,FACTORY");
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    public void testTypeNamesForFivePermittedCapitalLetters() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "5");
        checkConfig.addAttribute("allowedAbbreviations", "CLASS");
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 6;
        final String[] expected = {
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNames() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "5");
        checkConfig.addAttribute("allowedAbbreviations", "CLASS");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "39: " + getWarningMessage("marazmaticVARIABLEName", expectedCapitalCount),
            "40: " + getWarningMessage("MARAZMATICVariableName", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithNoIgnores() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "5");
        checkConfig.addAttribute("allowedAbbreviations", "NUMBER,MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "false");
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "66: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "72: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "78: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "84: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnores() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "5");
        checkConfig.addAttribute("allowedAbbreviations", "NUMBER,MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "true");
        checkConfig.addAttribute("ignoreFinal", "true");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresFinal() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "4");
        checkConfig.addAttribute("allowedAbbreviations", "MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "false");
        checkConfig.addAttribute("ignoreFinal", "true");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "12: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "58: " + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "60: "
                + getWarningMessage("s2erialNUMBER", expectedCapitalCount), // no ignore for static
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresStatic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "5");
        checkConfig.addAttribute("allowedAbbreviations", "MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "true");
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    public void testTypeNamesForThreePermittedCapitalLettersWithOverriddenMethod()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "3");
        checkConfig.addAttribute("allowedAbbreviations", "");
        checkConfig.addAttribute("tokens", "CLASS_DEF, METHOD_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "22: " + getWarningMessage("oveRRRRRrriddenMethod", expectedCapitalCount),
        };

        verify(checkConfig,
                getPath("InputAbbreviationAsWordInNameOverridableMethod.java"), expected);
    }

    @Test
    public void testTypeNamesForZeroPermittedCapitalLetter() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength",
                "0");
        checkConfig.addAttribute("allowedAbbreviations", "");
        checkConfig.addAttribute("ignoreStatic", "false");
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("ignoreOverriddenMethods", "false");
        checkConfig.addAttribute("tokens", "CLASS_DEF,INTERFACE_DEF,ENUM_DEF,"
            + "ANNOTATION_DEF,ANNOTATION_FIELD_DEF,ENUM_CONSTANT_DEF,"
            + "PARAMETER_DEF,VARIABLE_DEF,METHOD_DEF");
        final int expectedCapitalCount = 1;
        final String[] expected = {
            "6: " + getWarningMessage("NonAAAAbstractClassName", expectedCapitalCount),
            "9: " + getWarningMessage("FactoryWithBADNAme", expectedCapitalCount),
            "12: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "39: " + getWarningMessage("marazmaticVARIABLEName", expectedCapitalCount),
            "40: " + getWarningMessage("MARAZMATICVariableName", expectedCapitalCount),
            "46: " + getWarningMessage("RIGHT", expectedCapitalCount),
            "47: " + getWarningMessage("LEFT", expectedCapitalCount),
            "48: " + getWarningMessage("UP", expectedCapitalCount),
            "49: " + getWarningMessage("DOWN", expectedCapitalCount),
            "57: " + getWarningMessage("NonAAAAbstractClassName2", expectedCapitalCount),
            "58: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
            "59: " + getWarningMessage("s1erialNUMBER", expectedCapitalCount),
            "60: " + getWarningMessage("s2erialNUMBER", expectedCapitalCount),
            "61: " + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "66: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "72: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "78: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "84: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "88: " + getWarningMessage("FIleNameFormatException", expectedCapitalCount),
            "90: " + getWarningMessage("serialVersionUID", expectedCapitalCount),
            "98: " + getWarningMessage("userID", expectedCapitalCount),
            "107: " + getWarningMessage("VALUE", expectedCapitalCount),
            "111: " + getWarningMessage("VALUE", expectedCapitalCount),
            "115: " + getWarningMessage("VALUE", expectedCapitalCount),
            "119: " + getWarningMessage("VALUE", expectedCapitalCount),
        };
        verify(checkConfig,
                getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    public void testNullPointerException() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "2");
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("allowedAbbreviations", null);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getPath("InputAbbreviationAsWordInNameAbstractMultisetSetCount.java"),
                expected);
    }

    private String getWarningMessage(String typeName, int expectedCapitalCount) {
        return getCheckMessage(MSG_KEY, typeName, expectedCapitalCount);
    }

    @Test
    public void testReceiver() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameReceiver.java"),
                expected);
    }

}
