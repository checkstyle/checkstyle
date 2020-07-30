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
        checkConfig.addAttribute("ignoreStaticFinal", "false");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "44:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "49:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "50:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "78:16: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "84:23: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "90:22: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "96:29: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "134:17: " + getWarningMessage("InnerClassOneVIOLATION", expectedCapitalCount),
            "138:18: " + getWarningMessage("InnerClassTwoVIOLATION", expectedCapitalCount),
            "142:24: " + getWarningMessage("InnerClassThreeVIOLATION", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameNoIgnore.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnores() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "5");
        checkConfig.addAttribute("allowedAbbreviations", "NUMBER,MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "true");
        checkConfig.addAttribute("ignoreFinal", "true");
        checkConfig.addAttribute("ignoreStaticFinal", "true");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "44:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "49:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "50:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "134:17: " + getWarningMessage("InnerClassOneVIOLATION", expectedCapitalCount),
            "138:18: " + getWarningMessage("InnerClassTwoVIOLATION", expectedCapitalCount),
            "142:24: " + getWarningMessage("InnerClassThreeVIOLATION", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInNameIgnore.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresFinal() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "4");
        checkConfig.addAttribute("allowedAbbreviations", "MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "false");
        checkConfig.addAttribute("ignoreFinal", "true");
        checkConfig.addAttribute("ignoreStaticFinal", "true");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "24:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "44:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "49:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "50:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "70:20: "
                + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "72:28: "
                + getWarningMessage("s2erialNUMBER", expectedCapitalCount), // no ignore for static
        };

        verify(checkConfig, getPath(
                "InputAbbreviationAsWordInNameIgnoreFinal.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresStatic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "4");
        checkConfig.addAttribute("allowedAbbreviations", "MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "true");
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("ignoreStaticFinal", "true");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "24:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "44:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "49:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "50:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "70:20: "
                + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "71:26: "
                + getWarningMessage("s1erialNUMBER", expectedCapitalCount), // no ignore for final
        };

        verify(checkConfig, getPath(
                "InputAbbreviationAsWordInNameIgnoreStatic.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresStaticFinal() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "4");
        checkConfig.addAttribute("allowedAbbreviations", "MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "false");
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("ignoreStaticFinal", "true");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "24:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "44:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "49:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "50:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "70:20: "
                + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "71:26: "
                + getWarningMessage("s1erialNUMBER", expectedCapitalCount), // no ignore for final
            "72:28: "
                + getWarningMessage("s2erialNUMBER", expectedCapitalCount), // no ignore for static
        };

        verify(checkConfig, getPath(
                "InputAbbreviationAsWordInNameIgnoreStaticFinal.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresNonStaticFinal() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "4");
        checkConfig.addAttribute("allowedAbbreviations", "MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "true");
        checkConfig.addAttribute("ignoreFinal", "true");
        checkConfig.addAttribute("ignoreStaticFinal", "false");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "24:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "44:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "49:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "50:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "70:20: "
                + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "73:34: " // no ignore for static final
                + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "78:16: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "84:23: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "90:22: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "96:29: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "119:16: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "123:23: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "127:22: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "131:29: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "134:17: " + getWarningMessage("InnerClassOneVIOLATION", expectedCapitalCount),
            "138:18: " + getWarningMessage("InnerClassTwoVIOLATION", expectedCapitalCount),
            "142:24: " + getWarningMessage("InnerClassThreeVIOLATION", expectedCapitalCount),
        };

        verify(checkConfig, getPath(
                "InputAbbreviationAsWordInNameIgnoreNonStaticFinal.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresFinalKeepStaticFinal()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "4");
        checkConfig.addAttribute("allowedAbbreviations", "MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "false");
        checkConfig.addAttribute("ignoreFinal", "true");
        checkConfig.addAttribute("ignoreStaticFinal", "false");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "24:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "44:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "49:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "50:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "70:20: "
                + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "72:28: "
                + getWarningMessage("s2erialNUMBER", expectedCapitalCount), // no ignore for static
            "73:34: " // no ignore for static final
                + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "78:16: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "84:23: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "90:22: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "96:29: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
        };

        verify(checkConfig,
                getPath("InputAbbreviationAsWordInNameIgnoreFinalKeepStaticFinal.java"),
                expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresStaticKeepStaticFinal()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", "4");
        checkConfig.addAttribute("allowedAbbreviations", "MARAZMATIC,VARIABLE");
        checkConfig.addAttribute("ignoreStatic", "true");
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("ignoreStaticFinal", "false");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "24:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "44:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "49:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "50:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "70:20: "
                + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "71:26: "
                + getWarningMessage("s1erialNUMBER", expectedCapitalCount), // no ignore for final
            "73:34: " // no ignore for static final
                + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "78:16: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "84:23: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "90:22: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "96:29: "
                + getWarningMessage("VALUEEEE", expectedCapitalCount),
        };

        verify(checkConfig,
                getPath("InputAbbreviationAsWordInNameIgnoreStaticKeepStaticFinal.java"),
                expected);
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
        checkConfig.addAttribute("ignoreStaticFinal", "false");
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

    @Test
    public void testAbbreviationAsWordInNameCheckEnhancedInstanceof()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final int expectedCapitalCount = 4;

        final String[] expected = {
            "19:36: " + getWarningMessage("STRING", expectedCapitalCount),
            "20:43: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "29:41: " + getWarningMessage("ssSTRING", expectedCapitalCount),
            "32:35: " + getWarningMessage("XMLHTTP", expectedCapitalCount),
        };

        verify(checkConfig,
                getNonCompilablePath(
                        "InputAbbreviationAsWordInNameCheckEnhancedInstanceof.java"),
                expected);
    }

    @Test
    public void testAbbreviationAsWordInNameCheckEnhancedInstanceofAllowXmlLength1()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviations", "XML");
        checkConfig.addAttribute("allowedAbbreviationLength", "1");

        final int expectedCapitalCount = 2;

        final String[] expected = {
            "19:36: " + getWarningMessage("STRING", expectedCapitalCount),
            "20:43: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "28:39: " + getWarningMessage("aTXT", expectedCapitalCount),
            "29:41: " + getWarningMessage("ssSTRING", expectedCapitalCount),
            "32:35: " + getWarningMessage("XMLHTTP", expectedCapitalCount),
        };

        verify(checkConfig,
                getNonCompilablePath(
                        "InputAbbreviationAsWordInNameCheckEnhanced"
                                + "InstanceofAllowXmlLength1.java"),
                expected);
    }

    @Test
    public void testAbbreviationAsWordInNameCheckRecords()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final int expectedCapitalCount = 4;

        final String[] expected = {
            "19:11: " + getWarningMessage("myCLASS", expectedCapitalCount),
            "20:13: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "21:14: " + getWarningMessage("METHOD", expectedCapitalCount),
            "23:31: " + getWarningMessage("STRING", expectedCapitalCount),
            "24:17: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "30:12: " + getWarningMessage("myRECORD1", expectedCapitalCount),
            "30:29: " + getWarningMessage("STRING", expectedCapitalCount),
            "32:14: " + getWarningMessage("METHOD", expectedCapitalCount),
            "37:17: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "42:12: " + getWarningMessage("myRECORD2", expectedCapitalCount),
            "47:17: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "51:12: " + getWarningMessage("myRECORD3", expectedCapitalCount),
            "51:29: " + getWarningMessage("STRING", expectedCapitalCount),
            "51:41: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "51:57: " + getWarningMessage("NODES", expectedCapitalCount),
        };

        verify(checkConfig,
                getNonCompilablePath(
                        "InputAbbreviationAsWordInNameCheckRecords.java"),
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
