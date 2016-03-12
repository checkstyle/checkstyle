////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class AbbreviationAsWordInNameCheckTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "naming" + File.separator + filename);
    }

    @Test
    public void testTypeNamesForThreePermittedCapitalLetters() throws Exception {

        final DefaultConfiguration checkConfig =
            createCheckConfig(AbbreviationAsWordInNameCheck.class);
        final int expectedCapitalCount = 3;
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "III");
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "9: "  + getWarningMessage("FactoryWithBADNAme", expectedCapitalCount),
            "12: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInTypeName.java"), expected);
    }

    @Test
    public void testTypeNamesForFourPermittedCapitalLetters() throws Exception {

        final int expectedCapitalCount = 4;
        final DefaultConfiguration checkConfig =
            createCheckConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "CLASS,FACTORY");
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInTypeName.java"), expected);
    }

    @Test
    public void testTypeNamesForFivePermittedCapitalLetters() throws Exception {

        final int expectedCapitalCount = 5;
        final DefaultConfiguration checkConfig =
            createCheckConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "CLASS");
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final String[] expected = {
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInTypeName.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNames() throws Exception {

        final int expectedCapitalCount = 5;
        final DefaultConfiguration checkConfig =
            createCheckConfig(AbbreviationAsWordInNameCheck.class);
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "CLASS");
        checkConfig.addAttribute("tokens", "CLASS_DEF"
            + ",VARIABLE_DEF"
            + ",METHOD_DEF,ENUM_DEF,ENUM_CONSTANT_DEF"
            + ",PARAMETER_DEF,INTERFACE_DEF,ANNOTATION_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "39: " + getWarningMessage("marazmaticVARIABLEName", expectedCapitalCount),
            "40: " + getWarningMessage("MARAZMATICVariableName", expectedCapitalCount),
            "58: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInTypeName.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithNoIgnores() throws Exception {

        final int expectedCapitalCount = 5;
        final DefaultConfiguration checkConfig =
            createCheckConfig(AbbreviationAsWordInNameCheck.class);
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
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "66: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "72: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "78: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "84: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInTypeName.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnores() throws Exception {

        final int expectedCapitalCount = 5;
        final DefaultConfiguration checkConfig =
            createCheckConfig(AbbreviationAsWordInNameCheck.class);
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
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInTypeName.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresFinal() throws Exception {

        final int expectedCapitalCount = 4;
        final DefaultConfiguration checkConfig =
            createCheckConfig(AbbreviationAsWordInNameCheck.class);
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
            "12: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "58: " + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "60: "
                + getWarningMessage("s2erialNUMBER", expectedCapitalCount), // no ignore for static
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInTypeName.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresStatic() throws Exception {

        final int expectedCapitalCount = 5;
        final DefaultConfiguration checkConfig =
            createCheckConfig(AbbreviationAsWordInNameCheck.class);
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
            "32: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "37: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "38: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "58: " + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "59: "
                + getWarningMessage("s1erialNUMBER", expectedCapitalCount), // no ignore for final
        };

        verify(checkConfig, getPath("InputAbbreviationAsWordInTypeName.java"), expected);
    }

    @Test
    public void testTypeNamesForThreePermittedCapitalLettersWithOverriddenMethod()
        throws Exception {

        final DefaultConfiguration checkConfig =
            createCheckConfig(AbbreviationAsWordInNameCheck.class);
        final int expectedCapitalCount = 3;
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("allowedAbbreviations", "");
        checkConfig.addAttribute("tokens", "CLASS_DEF, METHOD_DEF");
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");

        final String[] expected = {
            "22: " + getWarningMessage("oveRRRRRrriddenMethod", expectedCapitalCount),
        };

        verify(checkConfig,
                getPath("InputAbbreviationAsWordInTypeNameOverridableMethod.java"), expected);
    }

    @Test
    public void testTypeNamesForZeroPermittedCapitalLetter() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AbbreviationAsWordInNameCheck.class);
        final int expectedCapitalCount = 0;
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
            "3: " + getWarningMessage("IIIInputAbstractClassName", expectedCapitalCount),
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
                getPath("InputAbbreviationAsWordInTypeName.java"), expected);
    }

    @Test
    public void testNullPointerException() throws Exception {

        final DefaultConfiguration checkConfig =
            createCheckConfig(AbbreviationAsWordInNameCheck.class);
        final int expectedCapitalCount = 1;
        checkConfig.addAttribute("allowedAbbreviationLength", String.valueOf(expectedCapitalCount));
        checkConfig.addAttribute("ignoreFinal", "false");
        checkConfig.addAttribute("allowedAbbreviations", null);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputAbstractMultisetSetCount.java"), expected);
    }

    private String getWarningMessage(String typeName, int expectedCapitalCount) {
        return getCheckMessage(MSG_KEY, typeName, expectedCapitalCount);
    }
}
