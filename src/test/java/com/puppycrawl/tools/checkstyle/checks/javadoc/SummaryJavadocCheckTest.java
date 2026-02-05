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

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SummaryJavadocCheckTest extends AbstractModuleTestSupport {
    private static Pattern getForbiddenSummaryFragmentsInlineReturn(
            SummaryJavadocCheck check) {
        return TestUtil.getInternalState(
                check, "forbiddenSummaryFragmentsInlineReturn", Pattern.class);
    }

    private static boolean hasForbiddenFragmentForInlineReturn(
            SummaryJavadocCheck check, String input) throws Exception {
        return TestUtil.invokeMethod(
                check, "containsForbiddenFragmentForInlineReturn", Boolean.class, input);
    }

    @Test
    public void testSetForbiddenSummaryFragmentsMutantsBranches() {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        final Pattern endsWithPattern = Pattern.compile("abc|^[a-z]");
        check.setForbiddenSummaryFragments(endsWithPattern);
        final Pattern resultEndsWith =
                getForbiddenSummaryFragmentsInlineReturn(check);
        assertWithMessage("endsWith pattern")
                .that(resultEndsWith.pattern())
                .isEqualTo("abc");

        final Pattern containsPattern = Pattern.compile("abc|^[a-z]|def");
        check.setForbiddenSummaryFragments(containsPattern);
        final Pattern resultContains =
                getForbiddenSummaryFragmentsInlineReturn(check);
        assertWithMessage("contains pattern")
                .that(resultContains.pattern())
                .isEqualTo("abc|def");

        final Pattern equalsPattern = Pattern.compile("^[a-z]");
        check.setForbiddenSummaryFragments(equalsPattern);
        final Pattern resultEquals =
                getForbiddenSummaryFragmentsInlineReturn(check);
        assertWithMessage("equals pattern")
                .that(resultEquals.pattern())
                .isEqualTo("^$");

        final Pattern elsePattern = Pattern.compile("randomPattern");
        check.setForbiddenSummaryFragments(elsePattern);
        final Pattern resultElse =
                getForbiddenSummaryFragmentsInlineReturn(check);
        assertWithMessage("else pattern identity")
                .that(resultElse)
                .isSameInstanceAs(elsePattern);
    }

    @Test
    public void testSetForbiddenSummaryFragmentsMutantsAssignmentAndIdentity() {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();

        final Pattern baselinePattern = Pattern.compile("baseline");
        check.setForbiddenSummaryFragments(baselinePattern);
        final Pattern resultBaseline =
                getForbiddenSummaryFragmentsInlineReturn(check);
        assertWithMessage("baseline pattern identity")
                .that(resultBaseline)
                .isSameInstanceAs(baselinePattern);

        final Pattern endsWithPattern = Pattern.compile("abc|^[a-z]");
        check.setForbiddenSummaryFragments(endsWithPattern);
        final Pattern resultEndsWith =
                getForbiddenSummaryFragmentsInlineReturn(check);
        assertWithMessage("endsWith pattern mutation")
                .that(resultEndsWith.pattern())
                .isEqualTo("abc");
        assertWithMessage("endsWith pattern not same")
                .that(resultEndsWith)
                .isNotSameInstanceAs(endsWithPattern);

        final Pattern equalsPattern = Pattern.compile("^[a-z]");
        check.setForbiddenSummaryFragments(equalsPattern);
        final Pattern resultEquals =
                getForbiddenSummaryFragmentsInlineReturn(check);
        assertWithMessage("equals pattern mutation")
                .that(resultEquals.pattern())
                .isEqualTo("^$");
        assertWithMessage("equals pattern not same")
                .that(resultEquals)
                .isNotSameInstanceAs(equalsPattern);

        final Pattern noInlinePattern = Pattern.compile("abc|def");
        check.setForbiddenSummaryFragments(noInlinePattern);
        final Pattern resultNeg =
                getForbiddenSummaryFragmentsInlineReturn(check);
        assertWithMessage("noInline pattern identity")
                .that(resultNeg)
                .isSameInstanceAs(noInlinePattern);
    }

    @Test
    public void testContainsForbiddenFragmentForInlineReturnAllBranches()
            throws Exception {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        // forbiddenSummaryFragmentsInlineReturn is null
        TestUtil.setInternalState(
                check, "forbiddenSummaryFragmentsInlineReturn", null);
        TestUtil.setInternalState(
                check, "forbiddenSummaryFragments", Pattern.compile("test"));
        assertWithMessage("match found")
                .that(hasForbiddenFragmentForInlineReturn(check, "no match here"))
                .isFalse();
        assertWithMessage("no match found")
                .that(hasForbiddenFragmentForInlineReturn(check, "test"))
                .isTrue();

        // forbiddenSummaryFragmentsInlineReturn is not null
        TestUtil.setInternalState(check, "forbiddenSummaryFragmentsInlineReturn",
                Pattern.compile("inline"));
        assertWithMessage("inline match found")
                .that(hasForbiddenFragmentForInlineReturn(check, "inline"))
                .isTrue();
        assertWithMessage("inline no match")
                .that(hasForbiddenFragmentForInlineReturn(check, "no match here"))
                .isFalse();
    }

    @Test
    public void testContainsForbiddenFragmentForInlineReturnRemoveConditionalMutatorEqualIf()
            throws Exception {
        final SummaryJavadocCheck check = new SummaryJavadocCheck();
        // Set forbiddenSummaryFragmentsInlineReturn to null to hit if branch
        TestUtil.setInternalState(
                check, "forbiddenSummaryFragmentsInlineReturn", null);
        TestUtil.setInternalState(
                check, "forbiddenSummaryFragments", Pattern.compile("abc"));
        assertWithMessage("match abc")
                .that(hasForbiddenFragmentForInlineReturn(check, "abc"))
                .isTrue();
        assertWithMessage("no match")
                .that(hasForbiddenFragmentForInlineReturn(check, "no match"))
                .isFalse();
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
            "112:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
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
            "20:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "25:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "43:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "48:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "58:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "64:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "69:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "80:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect.java"), expected);
    }

    @Test
    public void testIncorrect2() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "40:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "53:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "58:13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "63:13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "69:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "74:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "77:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect4.java"), expected);
    }

    @Test
    public void testInlineForbidden() throws Exception {
        final String[] expected = {
            "26:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "31:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "36:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "41:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "45:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "49:12: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "59:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineForbidden.java"), expected);
    }

    @Test
    public void testInlineForbidden2() throws Exception {
        final String[] expected = {
            "20:20: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "34:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "48:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "54:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineForbidden2.java"), expected);
    }

    @Test
    public void testPeriod() throws Exception {
        final String[] expected = {
            "14:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "19:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "37:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
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
            "19:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "24:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "42:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "47:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "63:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "68:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "79:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect2.java"), expected);
    }

    @Test
    public void testDefaultConfiguration2() throws Exception {
        final String[] expected = {
            "38:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "51:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "56:13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "61:13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "67:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "72:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "75:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect5.java"), expected);
    }

    @Test
    public void testIncorrectUsageOfSummaryTag() throws Exception {
        final String[] expected = {
            "34:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "41:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "49:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "57:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "64:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "74:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            // Until https://github.com/checkstyle/checkstyle/issues/11425
            "82:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "93:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "103:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "110:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
            getPath("InputSummaryJavadocIncorrect3.java"), expected);
    }

    @Test
    public void testInlineDefaultConfiguration() throws Exception {
        final String[] expected = {
            "22:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "26:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "30:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "40:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "44:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "56:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "60:12: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineDefault.java"), expected);
    }

    @Test
    public void testInlineDefaultConfiguration2() throws Exception {
        final String[] expected = {
            "18:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "22:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "27:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "37:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "54:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "58:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "80:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineDefault2.java"), expected);
    }

    @Test
    public void testInlineReturn() throws Exception {
        final String[] expected = {
            "74:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "90:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturn.java"), expected);
    }

    @Test
    public void testInlineReturn2() throws Exception {
        final String[] expected = {
            "15:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturn2.java"), expected);
    }

    @Test
    public void testInlineReturnForbidden() throws Exception {
        final String[] expected = {
            "14:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "21:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "28:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

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
            "19:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "26:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "33:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "40:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "60:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "70:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocPeriodAtEnd.java"), expected);
    }

    @Test
    public void testForbiddenFragmentRelativeToPeriod() throws Exception {
        final String[] expected = {
            "23:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
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
            "22:17: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "36:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "41:11: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocHtmlFormat.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final String[] expected = {
            "10:4: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("package-info.java"), expected);
    }

    @Test
    public void testPackageInfoWithAnnotation() throws Exception {
        final String[] expected = {
            "10:4: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("inputs/package-info.java"), expected);
    }

    @Test
    public void testForbidden() throws Exception {
        final String[] expected = {
            "14:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
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
            "14:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
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
            "15:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadoc2.java"), expected);
    }

    @Test
    public void testInheritDoc() throws Exception {
        final String[] expected = {
            "14:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInheritDoc.java"), expected);
    }

    @Test
    public void testSummaryJavadocLargeJavaDoc() throws Exception {
        final String[] expected = {
            "13:4: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "27:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "41:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "61:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocLargeJavadoc.java"), expected);
    }

    @Test
    public void testForbiddenFragmentsTabFormatted() throws Exception {
        final String[] expected = {
            "15:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocForbiddenFragmentsTabFormatted.java"), expected);
    }
}
