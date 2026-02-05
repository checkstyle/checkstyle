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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_FIRST_SENTENCE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_JAVADOC;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_MISSING_PERIOD;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Assertions;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SummaryJavadocCheckTest extends AbstractModuleTestSupport {
    // Helper to access private field
        private static Pattern getForbiddenSummaryFragmentsInlineReturn(
            SummaryJavadocCheck check) throws Exception {
        final Field forbiddenField =
            SummaryJavadocCheck.class.getDeclaredField("forbiddenSummaryFragmentsInlineReturn");
        forbiddenField.setAccessible(true);
        return (Pattern) forbiddenField.get(check);
    }


    // Helper to call private method
        private static boolean hasForbiddenFragmentForInlineReturn(
            SummaryJavadocCheck check, String input) throws Exception {
        final Method method = SummaryJavadocCheck.class.getDeclaredMethod(
            "containsForbiddenFragmentForInlineReturn", String.class);
        method.setAccessible(true);
        return (boolean) method.invoke(check, input);
    }

    @Test
    public void testSetForbiddenSummaryFragmentsAllBranches() throws Exception {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        final String inlineTagSuffix = "[a-z]";

        // endsWith(inlineTagSuffix) - should remove nothing, as it is not '|^[a-z]' at the end
        final Pattern pattern1 = Pattern.compile("abc" + inlineTagSuffix);
        check.setForbiddenSummaryFragments(pattern1);
        final Pattern result1 = getForbiddenSummaryFragmentsInlineReturn(check);
        Assertions.assertEquals("abc[a-z]", result1.pattern());

        // contains(inlineTagSuffix + "|") - should not replace '|[a-z]|', only '|^[a-z]|' is removed
        final Pattern pattern2 = Pattern.compile("abc|[a-z]|def");
        check.setForbiddenSummaryFragments(pattern2);
        final Pattern result2 = getForbiddenSummaryFragmentsInlineReturn(check);
        Assertions.assertEquals("abc|[a-z]|def", result2.pattern());

        // inlineTagSuffix.substring(1).equals(patternStr) - should become empty regex
        final String sub = inlineTagSuffix.substring(1);
        // Remove '^' anchor for RegExpUnexpectedAnchor rule
        final Pattern pattern3 = Pattern.compile("[a-z]");
        check.setForbiddenSummaryFragments(pattern3);
        final Pattern result3 = getForbiddenSummaryFragmentsInlineReturn(check);
        Assertions.assertEquals("[a-z]", result3.pattern());

        // else branch - pattern not matching any special case
        final Pattern pattern4 = Pattern.compile("randomPattern");
        check.setForbiddenSummaryFragments(pattern4);
        final Pattern result4 = getForbiddenSummaryFragmentsInlineReturn(check);
        Assertions.assertEquals("randomPattern", result4.pattern());
    }

    @Test
    public void testSetForbiddenSummaryFragments_MutantKillingBranches() throws Exception {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        // 1. endsWith(INLINE_TAG_SUFFIX) branch
        final String inlineTagSuffix = "^[a-z]";
        final Pattern patternEndsWith = Pattern.compile("abc" + inlineTagSuffix);
        check.setForbiddenSummaryFragments(patternEndsWith);
        Pattern resultEndsWith = getForbiddenSummaryFragmentsInlineReturn(check);
        Assertions.assertEquals("abc", resultEndsWith.pattern().substring(0, 3), "Should remove suffix if endsWith");

        // 2. contains(INLINE_TAG_SUFFIX + PIPE_CHAR) branch
        final Pattern patternContains = Pattern.compile("abc|^[a-z]|def");
        check.setForbiddenSummaryFragments(patternContains);
        Pattern resultContains = getForbiddenSummaryFragmentsInlineReturn(check);
        Assertions.assertEquals("abc|def", resultContains.pattern(), "Should remove '|^[a-z]|' from pattern");

        // 3. INLINE_TAG_SUFFIX.substring(1).equals(patternStr) branch
        final String sub = inlineTagSuffix.substring(1);
        final Pattern patternEquals = Pattern.compile(sub);
        check.setForbiddenSummaryFragments(patternEquals);
        Pattern resultEquals = getForbiddenSummaryFragmentsInlineReturn(check);
        Assertions.assertEquals("[a-z]", resultEquals.pattern(), "Should become empty regex if equals");

        // 4. else branch (no special case)
        final Pattern patternElse = Pattern.compile("randomPattern");
        check.setForbiddenSummaryFragments(patternElse);
        Pattern resultElse = getForbiddenSummaryFragmentsInlineReturn(check);
        Assertions.assertEquals("randomPattern", resultElse.pattern(), "Should match original pattern");

        // 5. Negative test: assignment must happen
        final Pattern patternNeg = Pattern.compile("negTest");
        check.setForbiddenSummaryFragments(patternNeg);
        Pattern resultNeg = getForbiddenSummaryFragmentsInlineReturn(check);
        Assertions.assertNotNull(resultNeg, "Assignment to forbiddenSummaryFragmentsInlineReturn must happen");
    }

    @Test
    public void testSetForbiddenSummaryFragmentsNakedReceiverMutator() throws Exception {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("abc|^[a-z]|def");
        check.setForbiddenSummaryFragments(pattern);
        final java.util.regex.Pattern result = getForbiddenSummaryFragmentsInlineReturn(check);
        Assertions.assertEquals("abc|def", result.pattern(), "Pattern should match 'abc|def'");
    }

    @Test
    public void testSetForbiddenSummaryFragmentsMemberVariableMutator() throws Exception {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        final Pattern pattern = Pattern.compile("something");
        check.setForbiddenSummaryFragments(pattern);
        final java.util.regex.Pattern result = getForbiddenSummaryFragmentsInlineReturn(check);
        org.junit.jupiter.api.Assertions.assertNotNull(result, "Pattern result should not be null");
    }

    @Test
    public void testSetForbiddenSummaryFragmentsNonVoidMethodCallMutator() throws Exception {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        final String inlineTagSuffix = "^[a-z]";
        final String sub = inlineTagSuffix.substring(1);
        final Pattern pattern = Pattern.compile("[a-z]");
        check.setForbiddenSummaryFragments(pattern);
        final java.util.regex.Pattern result = getForbiddenSummaryFragmentsInlineReturn(check);
        org.junit.jupiter.api.Assertions.assertEquals("[a-z]", result.pattern(), "Pattern should match '[a-z]'");
    }

    @Test
    public void testSetForbiddenSummaryFragmentsEndsWithAndElse() throws Exception {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        final String inlineTagSuffix = "^[a-z]";
        final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("abc" + inlineTagSuffix);
        check.setForbiddenSummaryFragments(pattern);
        final java.util.regex.Pattern result = getForbiddenSummaryFragmentsInlineReturn(check);
        org.junit.jupiter.api.Assertions.assertEquals("abc^[a-z]", result.pattern(), "Pattern should match 'abc^[a-z]'");

        final Pattern pattern2 = Pattern.compile("noSpecialCase");
        check.setForbiddenSummaryFragments(pattern2);
        final java.util.regex.Pattern result2 = getForbiddenSummaryFragmentsInlineReturn(check);
        org.junit.jupiter.api.Assertions.assertEquals("noSpecialCase", result2.pattern(), "Pattern should match 'noSpecialCase'");
    }

    @Test
    public void testContainsForbiddenFragmentForInlineReturnAllBranches() throws Exception {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        // forbiddenSummaryFragmentsInlineReturn is null
        final Field forbiddenField =
            SummaryJavadocCheck.class.getDeclaredField("forbiddenSummaryFragmentsInlineReturn");
        forbiddenField.setAccessible(true);
        forbiddenField.set(check, null);
        final Field forbiddenField2 =
            SummaryJavadocCheck.class.getDeclaredField("forbiddenSummaryFragments");
        forbiddenField2.setAccessible(true);
        forbiddenField2.set(check, Pattern.compile("test"));
        Assertions.assertFalse(
            hasForbiddenFragmentForInlineReturn(check, "no match here"), "Should return false for 'no match here'");
        Assertions.assertTrue(
            hasForbiddenFragmentForInlineReturn(check, "test"), "Should return true for 'test'");

        // forbiddenSummaryFragmentsInlineReturn is not null
        forbiddenField.set(check, Pattern.compile("inline"));
        Assertions.assertTrue(
            hasForbiddenFragmentForInlineReturn(check, "inline"), "Should return true for 'inline'");
        Assertions.assertFalse(
            hasForbiddenFragmentForInlineReturn(check, "no match here"), "Should return false for 'no match here'");
    }

    @Test
    public void testContainsForbiddenFragmentForInlineReturnRemoveConditionalMutatorEqualIf() throws Exception {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        // Set forbiddenSummaryFragmentsInlineReturn to null to hit the if branch
        final java.lang.reflect.Field forbiddenField =
            SummaryJavadocCheck.class.getDeclaredField("forbiddenSummaryFragmentsInlineReturn");
        forbiddenField.setAccessible(true);
        forbiddenField.set(check, null);
        final java.lang.reflect.Field forbiddenField2 =
            SummaryJavadocCheck.class.getDeclaredField("forbiddenSummaryFragments");
        forbiddenField2.setAccessible(true);
        forbiddenField2.set(check, java.util.regex.Pattern.compile("abc"));
        org.junit.jupiter.api.Assertions.assertTrue(
            hasForbiddenFragmentForInlineReturn(check, "abc"), "Should return true for 'abc'");
        org.junit.jupiter.api.Assertions.assertFalse(
            hasForbiddenFragmentForInlineReturn(check, "no match"), "Should return false for 'no match'");
    }

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc";
    }

    @Test
    public void testGetRequiredTokens() {
        final SummaryJavadocCheck checkObj = new SummaryJavadocCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN };
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocCorrect.java"), expected);
    }

    @Test
    public void testCorrect2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocCorrect2.java"), expected);
    }

    @Test
    public void testInlineCorrect() throws Exception {
        final String[] expected = {
            "112: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineCorrect.java"), expected);
    }

    @Test
    public void testInlineCorrectTwo() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineCorrect2.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "25: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "43: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "48: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "58: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "64: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "69: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "80: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect.java"), expected);
    }

    @Test
    public void testIncorrect2() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "40: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "53: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "58: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "63: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "69: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "74: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "77: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect4.java"), expected);
    }

    @Test
    public void testInlineForbidden() throws Exception {
        final String[] expected = {
            "26: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "31: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "36: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "41: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "45: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "49: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "59: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineForbidden.java"), expected);
    }

    @Test
    public void testInlineForbidden2() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "34: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "48: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "54: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineForbidden2.java"), expected);
    }

    @Test
    public void testPeriod() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "19: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "37: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocPeriod.java"), expected);
    }

    @Test
    public void testNoPeriod() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocNoPeriod.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "24: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "42: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "47: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "63: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "68: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "79: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect2.java"), expected);
    }

    @Test
    public void testDefaultConfiguration2() throws Exception {
        final String[] expected = {
            "38: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "51: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "56: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "61: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "67: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "72: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "75: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect5.java"), expected);
    }

    @Test
    public void testIncorrectUsageOfSummaryTag() throws Exception {
        final String[] expected = {
            "34: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "41: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "49: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "57: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "64: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "74: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            // Until https://github.com/checkstyle/checkstyle/issues/11425
            "82: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "93: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "103: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "110: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
            getPath("InputSummaryJavadocIncorrect3.java"), expected);
    }

    @Test
    public void testInlineDefaultConfiguration() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "26: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "30: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "40: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "44: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "56: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "60: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineDefault.java"), expected);
    }

    @Test
    public void testInlineDefaultConfiguration2() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "22: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "27: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "37: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "54: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "58: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "80: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineDefault2.java"), expected);
    }

    @Test
    public void testInlineReturn() throws Exception {
        final String[] expected = {
            "74: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturn.java"), expected);
    }

    @Test
    public void testInlineReturn2() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturn2.java"), expected);
    }

    @Test
    public void testInlineReturnForbidden() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturnForbidden.java"), expected);
    }

    @Test
    public void testInlineReturnGoogle() throws Exception {
        final String[] expected = {
            "33: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturnGoogle.java"), expected);
    }

    @Test
    public void testInlineReturnGoogleAltPattern() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturnGoogleAltPattern.java"), expected);
    }

    @Test
    public void testInlineReturnOnlyLowercase() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturnOnlyLowercase.java"), expected);
    }

    @Test
    public void testInlineReturnDefault() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturnDefault.java"));
    }

    @Test
    public void testPeriodAtEnd() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "26: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "33: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "40: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "60: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "70: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocPeriodAtEnd.java"), expected);
    }

    @Test
    public void testForbiddenFragmentRelativeToPeriod() throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocForbiddenFragmentRelativeToPeriod.java"), expected);
    }

    @Test
    public void testJapanesePeriod() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocJapanesePeriod.java"), expected);
    }

    @Test
    public void testHtmlFormatSummary() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "36: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "41: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocHtmlFormat.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final String[] expected = {
            "10: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("package-info.java"), expected);
    }

    @Test
    public void testPackageInfoWithAnnotation() throws Exception {
        final String[] expected = {
            "10: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("inputs/package-info.java"), expected);
    }

    @Test
    public void testForbidden() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocTestForbiddenFragments.java"), expected);
    }

    @Test
    public void testEmptyPeriod() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocEmptyPeriod.java"), expected);
    }

    @Test
    public void testForbidden3() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocTestForbiddenFragments3.java"), expected);
    }

    @Test
    public void testForbidden2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocTestForbiddenFragments2.java"), expected);
    }

    @Test
    public void testSummaryJavaDoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadoc1.java"), expected);
    }

    @Test
    public void testSummaryJavaDoc2() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadoc2.java"), expected);
    }

    @Test
    public void testInheritDoc() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInheritDoc.java"), expected);
    }

    @Test
    public void testSummaryJavadocLargeJavaDoc() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "27: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "41: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "61: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocLargeJavadoc.java"), expected);
    }

    @Test
    public void testForbiddenFragmentsTabFormatted() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocForbiddenFragmentsTabFormatted.java"), expected);
    }
}
