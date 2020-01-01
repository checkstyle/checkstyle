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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AbbreviationAsWordInNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/abbreviationaswordinname";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "9:16: " + getWarningMessage("FactoryWithBADNAme", expectedCapitalCount),
            "12:16: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "32:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38:21: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "39:21: " + getWarningMessage("marazmaticVARIABLEName", expectedCapitalCount),
            "40:21: " + getWarningMessage("MARAZMATICVariableName", expectedCapitalCount),
            "58:20: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameType.java"), expected);
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
            "9:16: " + getWarningMessage("FactoryWithBADNAme", expectedCapitalCount),
            "12:16: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "32:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
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
            "32:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
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
            "32:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
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
            "32:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38:21: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "39:21: " + getWarningMessage("marazmaticVARIABLEName", expectedCapitalCount),
            "40:21: " + getWarningMessage("MARAZMATICVariableName", expectedCapitalCount),
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
            "32:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38:21: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "66:16: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "72:23: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "78:22: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "84:29: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
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
            "32:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38:21: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
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
            "12:16: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "32:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38:21: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "58:20: "
                + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "60:28: "
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
            "32:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38:21: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
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
            "22:20: " + getWarningMessage("oveRRRRRrriddenMethod", expectedCapitalCount),
        };

        verify(checkConfig,
                getPath("InputAbbreviationAsWordInNameOverridableMethod.java"), expected);
    }

    @Test
    public void testOverriddenMethod()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("ignoreOverriddenMethods", "false");
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "6:20: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
            "14:24: " + getWarningMessage("oveRRRRRrriddenMethod", expectedCapitalCount),
            "22:20: " + getWarningMessage("oveRRRRRrriddenMethod", expectedCapitalCount),
            "34:24: " + getWarningMessage("oveRRRRRrriddenMethod", expectedCapitalCount),
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
            "6:16: " + getWarningMessage("NonAAAAbstractClassName", expectedCapitalCount),
            "9:16: " + getWarningMessage("FactoryWithBADNAme", expectedCapitalCount),
            "12:16: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "32:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38:21: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "39:21: " + getWarningMessage("marazmaticVARIABLEName", expectedCapitalCount),
            "40:21: " + getWarningMessage("MARAZMATICVariableName", expectedCapitalCount),
            "46:7: " + getWarningMessage("RIGHT", expectedCapitalCount),
            "47:7: " + getWarningMessage("LEFT", expectedCapitalCount),
            "48:7: " + getWarningMessage("UP", expectedCapitalCount),
            "49:7: " + getWarningMessage("DOWN", expectedCapitalCount),
            "57:16: " + getWarningMessage("NonAAAAbstractClassName2", expectedCapitalCount),
            "58:20: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
            "59:26: " + getWarningMessage("s1erialNUMBER", expectedCapitalCount),
            "60:28: " + getWarningMessage("s2erialNUMBER", expectedCapitalCount),
            "61:34: " + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "66:16: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "72:23: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "78:22: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "84:29: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "88:7: " + getWarningMessage("FIleNameFormatException", expectedCapitalCount),
            "90:31: " + getWarningMessage("serialVersionUID", expectedCapitalCount),
            "98:9: " + getWarningMessage("userID", expectedCapitalCount),
            "107:12: " + getWarningMessage("VALUE", expectedCapitalCount),
            "111:19: " + getWarningMessage("VALUE", expectedCapitalCount),
            "115:18: " + getWarningMessage("VALUE", expectedCapitalCount),
            "119:25: " + getWarningMessage("VALUE", expectedCapitalCount),
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
