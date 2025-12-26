///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AbbreviationAsWordInNameCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/abbreviationaswordinname";
    }

    @Test
    void testDefault() throws Exception {
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "24:16: " + getWarningMessage("FactoryWithBADNAme", expectedCapitalCount),
            "27:16: " + getWarningMessage("AbstractCLASSName1", expectedCapitalCount),
            "47:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "52:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "53:21: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "54:21: " + getWarningMessage("marazmaticVARIABLEName", expectedCapitalCount),
            "55:21: " + getWarningMessage("MARAZMATICVariableName", expectedCapitalCount),
            "73:20: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    void typeNamesForThreePermittedCapitalLetters() throws Exception {
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "22:16: " + getWarningMessage("FactoryWithBADNAme2", expectedCapitalCount),
            "25:16: " + getWarningMessage("AbstractCLASSName2", expectedCapitalCount),
            "45:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "50:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType2.java"), expected);
    }

    @Test
    void typeNamesForFourPermittedCapitalLetters() throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "45:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType3.java"), expected);
    }

    @Test
    void typeNamesForFivePermittedCapitalLetters() throws Exception {
        final int expectedCapitalCount = 6;
        final String[] expected = {
            "45:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "50:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType4.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNames() throws Exception {
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "46:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "51:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "52:21: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "53:17: " + getWarningMessage("marazmaticVARIABLEName", expectedCapitalCount),
            "54:17: " + getWarningMessage("MARAZMATICVariableName", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType5.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithNoIgnores() throws Exception {
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "48:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "53:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "54:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "82:16: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "88:23: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "94:22: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "100:29: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameNoIgnore.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithNoIgnoresPart2() throws Exception {
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "43:17: " + getWarningMessage("InnerClassOneVIOLATION", expectedCapitalCount),
            "47:18: " + getWarningMessage("InnerClassTwoVIOLATION", expectedCapitalCount),
            "51:24: " + getWarningMessage("InnerClassThreeVIOLATION", expectedCapitalCount),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameNoIgnorePart2.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithIgnores() throws Exception {
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "48:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "53:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "54:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameIgnore.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithIgnoresPart2() throws Exception {
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "44:17: " + getWarningMessage("InnerClassOneVIOLATION", expectedCapitalCount),
            "48:18: " + getWarningMessage("InnerClassTwoVIOLATION", expectedCapitalCount),
            "52:24: " + getWarningMessage("InnerClassThreeVIOLATION", expectedCapitalCount),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameIgnorePart2.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithIgnoresFinal() throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "28:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "48:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "53:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "54:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "74:20: "
                    + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "76:28: " + getWarningMessage("s2erialNUMBER",
                    expectedCapitalCount), // no ignore for static
        };

        verifyWithInlineConfigParser(
                getPath(
                "InputAbbreviationAsWordInNameIgnoreFinal.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithIgnoresStatic() throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "28:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "48:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "53:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "54:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "74:20: "
                + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "75:26: "
                + getWarningMessage("s1erialNUMBER", expectedCapitalCount), // no ignore for final
        };

        verifyWithInlineConfigParser(
                getPath(
                "InputAbbreviationAsWordInNameIgnoreStatic.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithIgnoresStaticFinal() throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "28:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "48:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "53:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "54:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "74:20: "
                    + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "75:26: " + getWarningMessage("s1erialNUMBER",
                    expectedCapitalCount), // no ignore for final
            "76:28: " + getWarningMessage("s2erialNUMBER",
                    expectedCapitalCount), // no ignore for static
        };

        verifyWithInlineConfigParser(
                getPath(
                "InputAbbreviationAsWordInNameIgnoreStaticFinal.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithIgnoresNonStaticFinal() throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "28:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "48:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "53:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "54:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "74:20: "
                    + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "77:34: " // no ignore for static final
                    + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "82:16: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "88:23: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "94:22: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "100:29: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath(
                "InputAbbreviationAsWordInNameIgnoreNonStaticFinal.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithIgnoresNonStaticFinalPart2()
            throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "28:16: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "32:23: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "36:22: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "40:29: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "43:17: " + getWarningMessage("InnerClassOneVIOLATION", expectedCapitalCount),
            "47:18: " + getWarningMessage("InnerClassTwoVIOLATION", expectedCapitalCount),
            "51:24: " + getWarningMessage("InnerClassThreeVIOLATION", expectedCapitalCount),
        };
        verifyWithInlineConfigParser(
                getPath(
                "InputAbbreviationAsWordInNameIgnoreNonStaticFinalPart2.java"), expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithIgnoresFinalKeepStaticFinal()
            throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "28:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "48:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "53:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "54:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "74:20: "
                    + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "76:28: " + getWarningMessage("s2erialNUMBER",
                    expectedCapitalCount), // no ignore for static
            "77:34: " // no ignore for static final
                    + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "82:16: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "88:23: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "94:22: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "100:29: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameIgnoreFinalKeepStaticFinal.java"),
                expected);
    }

    @Test
    void typeAndVariablesAndMethodNamesWithIgnoresStaticKeepStaticFinal()
            throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "28:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "48:15: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "53:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "54:25: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "74:20: "
                    + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "75:26: " + getWarningMessage("s1erialNUMBER",
                    expectedCapitalCount), // no ignore for final
            "77:34: " // no ignore for static final
                    + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "82:16: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "88:23: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "94:22: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "100:29: "
                    + getWarningMessage("VALUEEEE", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameIgnoreStaticKeepStaticFinal.java"),
                expected);
    }

    @Test
    void typeNamesForThreePermittedCapitalLettersWithOverriddenMethod()
            throws Exception {
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "35:20: " + getWarningMessage("oveRRRRRrriddenMethod", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameOverridableMethod.java"), expected);
    }

    @Test
    void overriddenMethod()
            throws Exception {
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "21:16: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
            "29:20: " + getWarningMessage("oveRRRRRrriddenMethod", expectedCapitalCount),
            "37:20: " + getWarningMessage("oveRRRRRrriddenMethod", expectedCapitalCount),
            "49:20: " + getWarningMessage("oveRRRRRrriddenMethod", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameOverridableMethod2.java"), expected);
    }

    @Test
    void typeNamesForZeroPermittedCapitalLetter() throws Exception {
        final int expectedCapitalCount = 1;
        final String[] expected = {
            "20:16: " + getWarningMessage("NonAAAAbstractClassName6", expectedCapitalCount),
            "23:16: " + getWarningMessage("FactoryWithBADNAme66", expectedCapitalCount),
            "26:16: " + getWarningMessage("AbstractCLASSName6", expectedCapitalCount),
            "46:11: " + getWarningMessage("AbstractINNERRClass", expectedCapitalCount),
            "51:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "52:21: " + getWarningMessage("marazmaticMETHODName", expectedCapitalCount),
            "53:17: " + getWarningMessage("marazmaticVARIABLEName", expectedCapitalCount),
            "54:17: " + getWarningMessage("MARAZMATICVariableName", expectedCapitalCount),
            "60:7: " + getWarningMessage("RIGHT", expectedCapitalCount),
            "61:7: " + getWarningMessage("LEFT", expectedCapitalCount),
            "62:7: " + getWarningMessage("UP", expectedCapitalCount),
            "63:7: " + getWarningMessage("DOWN", expectedCapitalCount),
            "71:16: " + getWarningMessage("NonAAAAbstractClassName26", expectedCapitalCount),
            "72:16: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
            "73:22: " + getWarningMessage("s1erialNUMBER", expectedCapitalCount),
            "74:24: " + getWarningMessage("s2erialNUMBER", expectedCapitalCount),
            "75:30: " + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "80:12: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "86:19: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "92:18: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "98:25: " + getWarningMessage("VALUEEEE", expectedCapitalCount),
            "102:7: " + getWarningMessage("FIleNameFormatException6", expectedCapitalCount),
            "104:31: " + getWarningMessage("serialVersionUID", expectedCapitalCount),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType6.java"), expected);
    }

    @Test
    void typeNamesForZeroPermittedCapitalLetterPart2()
            throws Exception {
        final int expectedCapitalCount = 1;

        final String[] expected = {
            "20:9: " + getWarningMessage("userID", expectedCapitalCount),
            "29:12: " + getWarningMessage("VALUE", expectedCapitalCount),
            "33:19: " + getWarningMessage("VALUE", expectedCapitalCount),
            "37:18: " + getWarningMessage("VALUE", expectedCapitalCount),
            "41:25: " + getWarningMessage("VALUE", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType6Part2.java"), expected);

    }

    @Test
    void nullPointerException() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameAbstractMultisetSetCount.java"),
                expected);
    }

    @Test
    void abbreviationAsWordInNameCheckEnhancedInstanceof()
            throws Exception {

        final int expectedCapitalCount = 4;

        final String[] expected = {
            "25:36: " + getWarningMessage("STRING", expectedCapitalCount),
            "26:43: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "35:41: " + getWarningMessage("ssSTRING", expectedCapitalCount),
            "38:35: " + getWarningMessage("XMLHTTP", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputAbbreviationAsWordInNameCheckEnhancedInstanceof.java"),
                expected);
    }

    @Test
    void abbreviationAsWordInNameCheckEnhancedInstanceofAllowXmlLength1()
            throws Exception {

        final int expectedCapitalCount = 2;

        final String[] expected = {
            "25:36: " + getWarningMessage("STRING", expectedCapitalCount),
            "26:43: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "34:39: " + getWarningMessage("aTXT", expectedCapitalCount),
            "35:41: " + getWarningMessage("ssSTRING", expectedCapitalCount),
            "38:35: " + getWarningMessage("XMLHTTP", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputAbbreviationAsWordInNameCheckEnhanced"
                                + "InstanceofAllowXmlLength1.java"),
                expected);
    }

    @Test
    void abbreviationAsWordInNameCheckRecords()
            throws Exception {

        final int expectedCapitalCount = 4;

        final String[] expected = {
            "22:11: " + getWarningMessage("myCLASS", expectedCapitalCount),
            "23:13: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "24:14: " + getWarningMessage("METHOD", expectedCapitalCount),
            "26:31: " + getWarningMessage("STRING", expectedCapitalCount),
            "27:17: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "33:12: " + getWarningMessage("myRECORD1", expectedCapitalCount),
            "33:29: " + getWarningMessage("STRING", expectedCapitalCount),
            "35:14: " + getWarningMessage("METHOD", expectedCapitalCount),
            "40:17: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "45:12: " + getWarningMessage("myRECORD2", expectedCapitalCount),
            "50:17: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "54:12: " + getWarningMessage("myRECORD3", expectedCapitalCount),
            "54:29: " + getWarningMessage("STRING", expectedCapitalCount),
            "54:41: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "54:57: " + getWarningMessage("NODES", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputAbbreviationAsWordInNameCheckRecords.java"),
                expected);
    }

    @Test
    void abbreviationAsWordInNameCheckRecordPatterns()
            throws Exception {

        final int expectedCapitalCount = 4;

        final String[] expected = {
            "23:39: " + getWarningMessage("POINT", expectedCapitalCount),
            "27:60: " + getWarningMessage("COLOR", expectedCapitalCount),
            "31:53: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "31:71: " + getWarningMessage("STRING", expectedCapitalCount),
            "39:52: " + getWarningMessage("COLOR", expectedCapitalCount),
            "40:52: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "40:68: " + getWarningMessage("COLOR", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputAbbreviationAsWordInNameCheckRecordPatterns.java"),
                expected);
    }

    private String getWarningMessage(String typeName, int expectedCapitalCount) {
        return getCheckMessage(MSG_KEY, typeName, expectedCapitalCount);
    }

    @Test
    void receiver() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameReceiver.java"),
                expected);
    }

    @Test
    void inputAbbreviationAsWordInNameTypeSnakeStyle() throws Exception {
        final String[] expected = {
            "13:20: " + getWarningMessage("FLAG_IS_FIRST_RUN", 4),
            "16:17: " + getWarningMessage("HYBRID_LOCK_PATH", 4),
            "21:17: " + getWarningMessage("__DEMOS__TESTS_VAR", 4),
            "28:16: " + getWarningMessage("TESTING_FAM_23456", 4),
            "33:16: " + getWarningMessage("TESTING_23456_FAM", 4),
            "38:16: " + getWarningMessage("_234VIOLATION", 4),
            "41:16: " + getWarningMessage("VIOLATION23456", 4),
            "72:21: " + getWarningMessage("getIsFIRST_Run", 4),
            "77:21: " + getWarningMessage("getBoolean_VALUES", 4),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameTypeSnakeStyle.java"), expected);
    }

    @Test
    void annotation() throws Exception {
        final String[] expected = {
            "16:12: " + getWarningMessage("readMETHOD", 4),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameAnnotation.java"), expected);
    }

}
