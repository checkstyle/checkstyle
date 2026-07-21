///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
    public void testDefault() throws Exception {
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "25:16: " + getWarningMessage("FactoryWithHARDName", expectedCapitalCount),
            "29:16: " + getWarningMessage("AbstractCLASSName1", expectedCapitalCount),
            "50:11: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "56:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "58:21: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
            "60:21: " + getWarningMessage("systematicVARIABLEName", expectedCapitalCount),
            "62:21: " + getWarningMessage("SYSTEMATICVariableName", expectedCapitalCount),
            "81:20: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType.java"), expected);
    }

    @Test
    public void testTypeNamesForThreePermittedCapitalLetters() throws Exception {
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "23:16: " + getWarningMessage("FactoryWithHARDName2", expectedCapitalCount),
            "27:16: " + getWarningMessage("AbstractCLASSName2", expectedCapitalCount),
            "48:11: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "54:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType2.java"), expected);
    }

    @Test
    public void testTypeNamesForFourPermittedCapitalLetters() throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "46:11: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType3.java"), expected);
    }

    @Test
    public void testTypeNamesForFivePermittedCapitalLetters() throws Exception {
        final int expectedCapitalCount = 6;
        final String[] expected = {
            "46:11: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "52:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType4.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNames() throws Exception {
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "47:11: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "53:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "55:21: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
            "57:17: " + getWarningMessage("systematicVARIABLEName", expectedCapitalCount),
            "59:17: " + getWarningMessage("SYSTEMATICVariableName", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType5.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithNoIgnores() throws Exception {
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "49:15: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "55:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "57:25: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
            "86:16: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "93:23: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "100:22: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "107:29: " + getWarningMessage("VALUELONG", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameNoIgnore.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithNoIgnoresPart2() throws Exception {
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "44:17: " + getWarningMessage("InnerClassOneVIOLATION", expectedCapitalCount),
            "49:18: " + getWarningMessage("InnerClassTwoVIOLATION", expectedCapitalCount),
            "54:24: " + getWarningMessage("InnerClassThreeVIOLATION", expectedCapitalCount),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameNoIgnorePart2.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnores() throws Exception {
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "49:15: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "55:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "57:25: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameIgnore.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresPart2() throws Exception {
        final int expectedCapitalCount = 6;

        final String[] expected = {
            "45:17: " + getWarningMessage("InnerClassOneVIOLATION", expectedCapitalCount),
            "50:18: " + getWarningMessage("InnerClassTwoVIOLATION", expectedCapitalCount),
            "55:24: " + getWarningMessage("InnerClassThreeVIOLATION", expectedCapitalCount),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameIgnorePart2.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresFinal() throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "29:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "50:15: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "56:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "58:25: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
            "79:20: "
                    + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "82:28: " + getWarningMessage("s2erialNUMBER",
                    expectedCapitalCount), // no ignore for static
        };

        verifyWithInlineConfigParser(
                getPath(
                "InputAbbreviationAsWordInNameIgnoreFinal.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresStatic() throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "29:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "50:15: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "56:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "58:25: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
            "79:20: "
                + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "81:26: "
                + getWarningMessage("s1erialNUMBER", expectedCapitalCount), // no ignore for final
        };

        verifyWithInlineConfigParser(
                getPath(
                "InputAbbreviationAsWordInNameIgnoreStatic.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresStaticFinal() throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "29:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "50:15: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "56:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "58:25: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
            "79:20: "
                    + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "81:26: " + getWarningMessage("s1erialNUMBER",
                    expectedCapitalCount), // no ignore for final
            "83:28: " + getWarningMessage("s2erialNUMBER",
                    expectedCapitalCount), // no ignore for static
        };

        verifyWithInlineConfigParser(
                getPath(
                "InputAbbreviationAsWordInNameIgnoreStaticFinal.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresNonStaticFinal() throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "26:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "44:15: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "50:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "52:25: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
            "72:20: "
                    + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "76:34: " // no ignore for static final
                    + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "81:16: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
            "86:23: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
            "91:22: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
            "96:29: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath(
                "InputAbbreviationAsWordInNameIgnoreNonStaticFinal.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresNonStaticFinalPart2()
            throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "29:16: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "34:23: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "39:22: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "44:29: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "48:17: " + getWarningMessage("InnerClassOneVIOLATION", expectedCapitalCount),
            "53:18: " + getWarningMessage("InnerClassTwoVIOLATION", expectedCapitalCount),
            "58:24: " + getWarningMessage("InnerClassThreeVIOLATION", expectedCapitalCount),
        };
        verifyWithInlineConfigParser(
                getPath(
                "InputAbbreviationAsWordInNameIgnoreNonStaticFinalPart2.java"), expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresFinalKeepStaticFinal()
            throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "26:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "44:15: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "50:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "52:25: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
            "72:20: "
                    + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "75:28: " + getWarningMessage("s2erialNUMBER",
                    expectedCapitalCount), // no ignore for static
            "77:34: " // no ignore for static final
                    + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "82:16: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
            "87:23: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
            "92:22: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
            "98:29: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameIgnoreFinalKeepStaticFinal.java"),
                expected);
    }

    @Test
    public void testTypeAndVariablesAndMethodNamesWithIgnoresStaticKeepStaticFinal()
            throws Exception {
        final int expectedCapitalCount = 5;

        final String[] expected = {
            "26:20: " + getWarningMessage("AbstractCLASSName", expectedCapitalCount),
            "45:15: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "51:15: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "53:25: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
            "73:20: "
                    + getWarningMessage("serialNUMBER", expectedCapitalCount), // not in ignore list
            "75:26: " + getWarningMessage("s1erialNUMBER",
                    expectedCapitalCount), // no ignore for final
            "78:34: " // no ignore for static final
                    + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "83:16: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
            "88:23: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
            "93:22: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
            "98:29: "
                    + getWarningMessage("VALUELONG", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameIgnoreStaticKeepStaticFinal.java"),
                expected);
    }

    @Test
    public void testTypeNamesForThreePermittedCapitalLettersWithOverriddenMethod()
            throws Exception {
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "36:20: " + getWarningMessage("overRIDDENMethod", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameOverridableMethod.java"), expected);
    }

    @Test
    public void testOverriddenMethod()
            throws Exception {
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "22:16: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
            "31:20: " + getWarningMessage("overRIDDENMethod", expectedCapitalCount),
            "40:20: " + getWarningMessage("overRIDDENMethod", expectedCapitalCount),
            "53:20: " + getWarningMessage("overRIDDENMethod", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameOverridableMethod2.java"), expected);
    }

    @Test
    public void testTypeNamesForZeroPermittedCapitalLetter() throws Exception {
        final int expectedCapitalCount = 1;
        final String[] expected = {
            "21:16: " + getWarningMessage("NonAAAAbstractClassName6", expectedCapitalCount),
            "24:16: " + getWarningMessage("FactoryWithHARDName66", expectedCapitalCount),
            "27:16: " + getWarningMessage("AbstractCLASSName6", expectedCapitalCount),
            "45:11: " + getWarningMessage("AbstractINNERSClass", expectedCapitalCount),
            "51:11: " + getWarningMessage("WellNamedFACTORY", expectedCapitalCount),
            "53:21: " + getWarningMessage("systematicMETHODName", expectedCapitalCount),
            "55:17: " + getWarningMessage("systematicVARIABLEName", expectedCapitalCount),
            "57:17: " + getWarningMessage("SYSTEMATICVariableName", expectedCapitalCount),
            "64:7: " + getWarningMessage("RIGHT", expectedCapitalCount),
            "66:7: " + getWarningMessage("LEFT", expectedCapitalCount),
            "68:7: " + getWarningMessage("UP", expectedCapitalCount),
            "70:7: " + getWarningMessage("DOWN", expectedCapitalCount),
            "78:16: " + getWarningMessage("NonAAAAbstractClassName26", expectedCapitalCount),
            "80:16: " + getWarningMessage("serialNUMBER", expectedCapitalCount),
            "82:22: " + getWarningMessage("s1erialNUMBER", expectedCapitalCount),
            "84:24: " + getWarningMessage("s2erialNUMBER", expectedCapitalCount),
            "86:30: " + getWarningMessage("s3erialNUMBER", expectedCapitalCount),
            "91:12: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "96:19: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "101:18: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "106:25: " + getWarningMessage("VALUELONG", expectedCapitalCount),
            "110:7: " + getWarningMessage("FIleNameFormatException6", expectedCapitalCount),
            "112:31: " + getWarningMessage("serialVersionUID", expectedCapitalCount),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType6.java"), expected);
    }

    @Test
    public void testTypeNamesForZeroPermittedCapitalLetterPart2()
            throws Exception {
        final int expectedCapitalCount = 1;

        final String[] expected = {
            "21:9: " + getWarningMessage("userID", expectedCapitalCount),
            "31:12: " + getWarningMessage("VALUE", expectedCapitalCount),
            "36:19: " + getWarningMessage("VALUE", expectedCapitalCount),
            "41:18: " + getWarningMessage("VALUE", expectedCapitalCount),
            "46:25: " + getWarningMessage("VALUE", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameType6Part2.java"), expected);

    }

    @Test
    public void testNullPointerException() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameAbstractMultisetSetCount.java"),
                expected);
    }

    @Test
    public void testAbbreviationAsWordInNameCheckEnhancedInstanceof()
            throws Exception {

        final int expectedCapitalCount = 4;

        final String[] expected = {
            "26:36: " + getWarningMessage("STRING", expectedCapitalCount),
            "28:43: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "38:41: " + getWarningMessage("ssSTRING", expectedCapitalCount),
            "42:35: " + getWarningMessage("XMLHTTP", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputAbbreviationAsWordInNameCheckEnhancedInstanceof.java"),
                expected);
    }

    @Test
    public void testAbbreviationAsWordInNameCheckEnhancedInstanceofAllowXmlLength1()
            throws Exception {

        final int expectedCapitalCount = 2;

        final String[] expected = {
            "26:36: " + getWarningMessage("STRING", expectedCapitalCount),
            "28:43: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "37:39: " + getWarningMessage("aTXT", expectedCapitalCount),
            "39:41: " + getWarningMessage("ssSTRING", expectedCapitalCount),
            "43:35: " + getWarningMessage("XMLHTTP", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputAbbreviationAsWordInNameCheckEnhanced"
                                + "InstanceofAllowXmlLength1.java"),
                expected);
    }

    @Test
    public void testAbbreviationAsWordInNameCheckRecords()
            throws Exception {

        final int expectedCapitalCount = 4;

        final String[] expected = {
            "23:11: " + getWarningMessage("myCLASS", expectedCapitalCount),
            "25:13: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "27:14: " + getWarningMessage("METHOD", expectedCapitalCount),
            "30:31: " + getWarningMessage("STRING", expectedCapitalCount),
            "32:17: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "38:12: " + getWarningMessage("myRECORD1", expectedCapitalCount),
            "38:29: " + getWarningMessage("STRING", expectedCapitalCount),
            "44:14: " + getWarningMessage("METHOD", expectedCapitalCount),
            "50:17: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "56:12: " + getWarningMessage("myRECORD2", expectedCapitalCount),
            "62:17: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "66:12: " + getWarningMessage("myRECORD3", expectedCapitalCount),
            "66:29: " + getWarningMessage("STRING", expectedCapitalCount),
            "66:41: " + getWarningMessage("INTEGER", expectedCapitalCount),
            "66:57: " + getWarningMessage("NODES", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputAbbreviationAsWordInNameCheckRecords.java"),
                expected);
    }

    @Test
    public void testAbbreviationAsWordInNameCheckRecordPatterns()
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
                getNonCompilablePath(
                        "InputAbbreviationAsWordInNameCheckRecordPatterns.java"),
                expected);
    }

    private String getWarningMessage(String typeName, int expectedCapitalCount) {
        return getCheckMessage(MSG_KEY, typeName, expectedCapitalCount);
    }

    @Test
    public void testReceiver() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameReceiver.java"),
                expected);
    }

    @Test
    public void testInputAbbreviationAsWordInNameTypeSnakeStyle() throws Exception {
        final String[] expected = {
            "21:20: " + getWarningMessage("FLAG_IS_FIRST_RUN", 4),
            "24:17: " + getWarningMessage("HYBRID_LOCK_PATH", 4),
            "29:17: " + getWarningMessage("__DEMOS__TESTS_VAR", 4),
            "36:16: " + getWarningMessage("TESTING_FAM_23456", 4),
            "41:16: " + getWarningMessage("TESTING_23456_FAM", 4),
            "46:16: " + getWarningMessage("_234VIOLATION", 4),
            "49:16: " + getWarningMessage("VIOLATION23456", 4),
            "80:21: " + getWarningMessage("getIsFIRST_Run", 4),
            "85:21: " + getWarningMessage("getBoolean_VALUES", 4),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameTypeSnakeStyle.java"), expected);
    }

    @Test
    public void testAnnotation() throws Exception {
        final String[] expected = {
            "26:12: " + getWarningMessage("readMETHOD", 4),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbbreviationAsWordInNameAnnotation.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final int expectedCapitalCount = 4;

        final String[] expected = {
            "19:5: " + getWarningMessage("useHTTPSConnection", expectedCapitalCount),
            "25:9: " + getWarningMessage("localHTTPSConnection", expectedCapitalCount),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputAbbreviationAsWordInNameCompactSourceFile.java"), expected);
    }

}
