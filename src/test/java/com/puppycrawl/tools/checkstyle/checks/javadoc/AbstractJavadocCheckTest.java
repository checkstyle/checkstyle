///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_PARSE_RULE_ERROR;
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_UNCLOSED_HTML_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_JAVADOC_MISSED_HTML_CLOSE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_JAVADOC_WRONG_SINGLETON_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_TAG_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_FIRST_SENTENCE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
<<<<<<< HEAD
import java.util.UUID;
=======
>>>>>>> e7332ecb8 (Issue #11446: Update AbstractJavadocCheckTest to use execute method)

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemErrGuard;
import org.itsallcode.junit.sysextensions.SystemErrGuard.SysErr;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

@ExtendWith(SystemErrGuard.class)
public class AbstractJavadocCheckTest extends AbstractModuleTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/abstractjavadoc";
    }

    /**
     * <p>Configures the environment for each test.</p>
     * <ul>
     * <li>Start output capture for {@link System#err}</li>
     * </ul>
     *
     * @param systemErr wrapper for {@code System.err}
     */
    @BeforeEach
    public void setUp(@SysErr Capturable systemErr) {
        systemErr.captureMuted();
    }

    @Test
    public void testJavadocTagsWithoutArgs() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 4,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "26: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 10,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "35: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 7,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "41: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 6,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "29: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 10,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "35: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 7,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "46: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 6,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocJavadocTagsWithoutArgs.java"), expected);
    }

    @Test
    public void testNumberFormatException() throws Exception {
        final String[] expected = {
            "8: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 52,
                    "mismatched input ';' expecting MEMBER", "REFERENCE"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNumberFormatException.java"), expected);
    }

    @Test
    public void testCustomTag() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocCustomTag.java"), expected);
    }

    @Test
    public void testParsingErrors(@SysErr Capturable systemErr) throws Exception {
        final String[] expected = {
            "9: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "16: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 35, "img"),
        };
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocParsingErrors.java"), expected);
        assertWithMessage("Error is unexpected")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testWithMultipleChecksOne() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocCorrectParagraphOne.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testWithMultipleChecksTwo() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocCorrectParagraphTwo.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testAntlrError(@SysErr Capturable systemErr) throws Exception {
        final String[] expected = {
            "9: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 78,
                    "mismatched input '(' expecting <EOF>", "JAVADOC"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocInvalidAtSeeReference.java"), expected);
        assertWithMessage("Error is unexpected")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testCheckReuseAfterParseErrorWithFollowingAntlrErrorInTwoFiles(
            @SysErr Capturable systemErr) throws Exception {
        final String[] expectedMessagesForFile1 = {
            "9: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "16: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 35, "img"),
        };
        verifyWithInlineConfigParser(getPath(
                "InputAbstractJavadocParsingErrors2.java"), expectedMessagesForFile1);

        final String[] expectedMessagesForFile2 = {
            "9: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 78,
                    "mismatched input '(' expecting <EOF>", "JAVADOC"),
        };
        verifyWithInlineConfigParser(getPath(
                "InputAbstractJavadocInvalidAtSeeReference2.java"), expectedMessagesForFile2);

        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final Map<String, List<String>> expectedMessages = new LinkedHashMap<>(2);
        expectedMessages.put(getPath("InputAbstractJavadocParsingErrors2.java"), asList(
            "8: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "13: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 35, "img")
        ));
        expectedMessages.put(getPath("InputAbstractJavadocInvalidAtSeeReference2.java"),
            singletonList("8: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 78,
                    "mismatched input '(' expecting <EOF>", "JAVADOC")
        ));
        verify(createChecker(checkConfig), new File[] {
            new File(getPath("InputAbstractJavadocParsingErrors2.java")),
            new File(getPath("InputAbstractJavadocInvalidAtSeeReference2.java")), },
                expectedMessages);
        assertWithMessage("Error is unexpected")
                .that(systemErr.getCapturedData())
                .isEqualTo("");
    }

    @Test
    public void testCheckReuseAfterParseErrorWithFollowingAntlrErrorInSingleFile()
            throws Exception {
        final String[] expected = {
            "9: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "16: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 82,
                    "mismatched input '(' expecting <EOF>", "JAVADOC"),
        };
        verifyWithInlineConfigParser(
            getPath("InputAbstractJavadocUnclosedTagAndInvalidAtSeeReference.java"), expected);
    }

    @Test
    public void testCache() throws Exception {
        final String[] expected = {
            "12: " + getCheckMessage(SummaryJavadocCheck.class, MSG_SUMMARY_FIRST_SENTENCE),
        };
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocCache1.java"),
            getPath("InputAbstractJavadocCache2.java"), expected);
    }

    @Test
    public void testPosition() throws Exception {
        JavadocCatchCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocPosition.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            // until https://github.com/checkstyle/checkstyle/issues/12586
            // actual javadoc count is 65, but verifyWithInlineConfigParser verify file twice
            .isEqualTo(130);
    }

    @Test
    public void testPositionWithSinglelineComments() throws Exception {
        JavadocCatchCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocPositionWithSinglelineComments.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            // until https://github.com/checkstyle/checkstyle/issues/12586
            // actual javadoc count is 65, but verifyWithInlineConfigParser verify file twice
            .isEqualTo(130);
    }

    @Test
    public void testPositionOnlyComments() throws Exception {
        JavadocCatchCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocPositionOnlyComments.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            .isEqualTo(0);
    }

    @Test
    public void testTokens() {
        final int[] defaultJavadocTokens = {JavadocTokenTypes.JAVADOC};
        final AbstractJavadocCheck check = new AbstractJavadocCheck() {
            @Override
            public void visitJavadocToken(DetailNode ast) {
                // no code necessary
            }

            @Override
            public int[] getDefaultJavadocTokens() {
                return defaultJavadocTokens;
            }
        };

        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Acceptable tokens should be equal to default")
            .that(check.getAcceptableTokens())
            .isEqualTo(check.getDefaultTokens());
        assertWithMessage("Required tokens should be equal to default")
            .that(check.getRequiredTokens())
            .isEqualTo(check.getDefaultTokens());
        assertWithMessage("Invalid default javadoc tokens")
            .that(check.getDefaultJavadocTokens())
            .isEqualTo(defaultJavadocTokens);
        assertWithMessage("Invalid acceptable javadoc tokens")
            .that(check.getAcceptableJavadocTokens())
            .isEqualTo(defaultJavadocTokens);
        assertWithMessage("Invalid required javadoc tokens")
            .that(check.getRequiredJavadocTokens())
            .isNotEqualTo(defaultJavadocTokens);
    }

    @Test
    public void testTokensFail() {
        final int[] defaultJavadocTokens = {JavadocTokenTypes.JAVADOC,
            JavadocTokenTypes.AREA_HTML_TAG_NAME,
            JavadocTokenTypes.PARAGRAPH,
            JavadocTokenTypes.HR_TAG,
            JavadocTokenTypes.RETURN_LITERAL,
            JavadocTokenTypes.BR_TAG};
        final AbstractJavadocCheck check = new AbstractJavadocCheck() {
            @Override
            public void visitJavadocToken(DetailNode ast) {
                // no code necessary
            }

            @Override
            public int[] getDefaultJavadocTokens() {
                return defaultJavadocTokens;
            }
        };
        check.setJavadocTokens("RETURN_LITERAL");
        assertDoesNotThrow(check::init);
    }

    @Test
    public void testAcceptableTokensFail() throws Exception {
        final String path = getPath("InputAbstractJavadocTokensFail.java");
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verifyWithInlineConfigParser(path, expected);
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (IllegalStateException ex) {
            final String expected = "Javadoc Token "
                    + "\"RETURN_LITERAL\" was not found in "
                    + "Acceptable javadoc tokens list in check "
                    + TokenIsNotInAcceptablesCheck.class.getName();
            assertWithMessage("Invalid exception, should start with: " + expected)
                    .that(ex.getMessage())
                    .startsWith(expected);
        }
    }

    @Test
    public void testAcceptableTokensPass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocTokensPass.java"), expected);
    }

    @Test
    public void testRequiredTokenIsNotInDefaultTokens() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RequiredTokenIsNotInDefaultsJavadocCheck.class);
        final String uniqueFileName = "empty_" + UUID.randomUUID() + ".java";
        final File pathToEmptyFile = new File(temporaryFolder, uniqueFileName);

        try {
            execute(checkConfig, pathToEmptyFile);
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (IllegalStateException ex) {
            final String expected = "Javadoc Token \""
                    + JavadocTokenTypes.RETURN_LITERAL + "\" from required"
                    + " javadoc tokens was not found in default javadoc tokens list in check "
                    + RequiredTokenIsNotInDefaultsJavadocCheck.class.getName();
            assertWithMessage("Invalid exception, should start with: " + expected)
                    .that(ex.getMessage())
                    .startsWith(expected);
        }
    }

    @Test
    public void testVisitLeaveToken() throws Exception {
        JavadocVisitLeaveCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocLeaveToken.java"), expected);
        assertWithMessage("Javadoc visit count should be greater than zero")
                .that(JavadocVisitLeaveCheck.visitCount)
                .isGreaterThan(0);
        assertWithMessage("Javadoc visit and leave count should be equal")
            .that(JavadocVisitLeaveCheck.leaveCount)
            .isEqualTo(JavadocVisitLeaveCheck.visitCount);
    }

    @Test
    public void testNoWsBeforeDescriptionInJavadocTags() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    23, "mismatched input 'd' expecting <EOF>", "JAVADOC"),
            "29: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    30, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "37: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    39, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "51: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    34, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "61: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    31, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "72: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    15, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "81: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    32, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "92: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    17, "mismatched input '<' expecting <EOF>", "JAVADOC"),
            "99: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    34, "no viable alternative at input '-'", "JAVADOC_INLINE_TAG"),
            "106: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    39, "no viable alternative at input '-'", "JAVADOC_INLINE_TAG"),
            "114: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    19, "no viable alternative at input '<'", "JAVADOC_INLINE_TAG"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNoWsBeforeDescriptionInJavadocTags.java"),
                expected);
    }

    @Test
    public void testWrongSingletonTagInJavadoc() throws Exception {
        final String[] expected = {
            "10: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "embed"),
            "17: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "keygen"),
            "24: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "SOURCE"),
            "31: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "TRACK"),
            "38: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "WBR"),
            "9: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "embed"),
            "16: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "keygen"),
            "23: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "SOURCE"),
            "30: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "TRACK"),
            "37: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "WBR"),
        };
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocWrongSingletonTagInJavadoc.java"),
                expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckOne() throws Exception {
        final String[] expected = {
            "11: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "18: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "21: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "27: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "34: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "42: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "63: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "81: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "91: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "136: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTagsOne.java"), expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckTwo() throws Exception {
        final String[] expected = {
            "12: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "19: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "25: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "46: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "80: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTagsTwo.java"), expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckReportingNoViolationOne() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTagsNoViolationOne.java"), expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckVisitCount()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NonTightHtmlTagIntolerantCheck.class);
        checkConfig.addProperty("violateExecutionOnNonTightHtml", "true");
        checkConfig.addProperty("reportVisitJavadocToken", "true");
    public void testNonTightHtmlTagIntolerantCheck() throws Exception {
        final String[] expected = {
            "12: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "19: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "22: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "28: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "35: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "43: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "64: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "82: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "92: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "137: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTags.java"), expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckReportingNoViolation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTagsNoViolation.java"), expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckVisitCount() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "20: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "23: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "29: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "36: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "44: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "52:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "BODY_TAG_START"),
            "66: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "75:13: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "85: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "95: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "115:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "122:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "126:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "BODY_TAG_START"),
            "143: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTagsVisitCount.java"),
                expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckVisitCountOne() throws Exception {
        final String[] expected = {
            "10:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "11:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "13: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "13:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "14:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "14:39: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "24: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "24:9: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "24:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "28: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "28:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "33:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "34: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "34:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "34:25: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "41:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "41:22: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "42: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "49:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "50:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "51: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "51:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "51:23: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "59:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "59:20: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "59:34: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "61: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "61:16: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "61:21: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "71:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "73: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "73:22: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "80:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "81:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "84: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "106:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "113: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "113:9: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "113:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "113:33: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTagsVisitCountOne.java"),
                expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckVisitCountTwo() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "20: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "27: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "35:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "BODY_TAG_START"),
            "49: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "58:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "65:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "69:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "BODY_TAG_START"),
            "86: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTagsVisitCountTwo.java"),
                expected);
    }

    @Test
    public void testVisitCountForCheckAcceptingJavadocWithNonTightHtml() throws Exception {
        final String[] expected = {
            "11:4: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "BODY_TAG_START"),
            "12:4: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "14: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "14:4: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "15:4: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "15:39: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "30: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "30:9: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "30:13: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "37: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "37:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "44:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "45: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "45:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "45:25: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "55:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "55:22: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "56: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "65:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "66:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "67: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "67:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "67:23: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "78:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "BODY_TAG_START"),
            "78:20: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "78:34: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "80: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "80:16: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "80:21: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "96:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "98: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "98:22: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "107:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "108:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "111: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTags2.java"), expected);
    }

    @Test
    public void testVisitCountForCheckAcceptingJavadocWithNonTightHtml() throws Exception {
        final String[] expected = {
            "11:4: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "BODY_TAG_START"),
            "12:4: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "14: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "14:4: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "15:4: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "15:39: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "30: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "30:9: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "30:13: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "37: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "37:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "44:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "45: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "45:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "45:25: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "55:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "55:22: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "56: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "65:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "66:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "67: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "67:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "67:23: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "78:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "BODY_TAG_START"),
            "78:20: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "78:34: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "80: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "80:16: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "80:21: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "96:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "98: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "98:22: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "107:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "108:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "111: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTags2.java"), expected);
    }

    @Test
    public void testVisitCountForCheckAcceptingJavadocWithNonTightHtml3() throws Exception {
        final String[] expected = {
            "29:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "36: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "36:9: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "P_TAG_START"),
            "36:13: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "LI_TAG_START"),
            "36:33: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "BODY_TAG_START"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTags3.java"), expected);
    }

    public static class ParseJavadocOnlyCheck extends AbstractJavadocCheck {

        @Override
        public int[] getDefaultJavadocTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            // do nothing
        }

    }

    public static class JavadocCatchCheck extends AbstractJavadocCheck {
        private static int javadocsNumber;

        public static void clearCounter() {
            javadocsNumber = 0;
        }

        @Override
        public int[] getDefaultJavadocTokens() {
            return new int[] {JavadocTokenTypes.JAVADOC};
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            assertWithMessage(ast.toString())
                .that(ast.getText())
                .isEqualTo("JAVADOC");
            final DetailNode text = JavadocUtil.findFirstToken(ast, JavadocTokenTypes.TEXT);
            assertWithMessage("Empty javadoc text at " + ast)
                .that(text)
                .isNotNull();
            assertWithMessage(ast.toString())
                .that(text.getText())
                .isEqualTo("Javadoc");
            javadocsNumber++;
        }

    }

    public static class RequiredTokenIsNotInDefaultsJavadocCheck extends AbstractJavadocCheck {

        @Override
        public int[] getRequiredJavadocTokens() {
            return new int[] {JavadocTokenTypes.RETURN_LITERAL};
        }

        @Override
        public int[] getDefaultJavadocTokens() {
            return new int[] {JavadocTokenTypes.DEPRECATED_LITERAL};
        }

        @Override
        public int[] getAcceptableJavadocTokens() {
            return CommonUtil.EMPTY_INT_ARRAY;
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            // not used
        }

    }

    public static class TokenIsNotInAcceptablesCheck extends AbstractJavadocCheck {

        @Override
        public int[] getRequiredJavadocTokens() {
            return new int[] {JavadocTokenTypes.DEPRECATED_LITERAL};
        }

        @Override
        public int[] getDefaultJavadocTokens() {
            return new int[] {JavadocTokenTypes.DEPRECATED_LITERAL};
        }

        @Override
        public int[] getAcceptableJavadocTokens() {
            return new int[] {JavadocTokenTypes.DEPRECATED_LITERAL};
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            // not used
        }

    }

    public static class JavadocVisitLeaveCheck extends AbstractJavadocCheck {

        private static int visitCount;
        private static int leaveCount;

        public static void clearCounter() {
            visitCount = 0;
            leaveCount = 0;
        }

        @Override
        public int[] getRequiredJavadocTokens() {
            return new int[] {JavadocTokenTypes.TEXT};
        }

        @Override
        public int[] getDefaultJavadocTokens() {
            return getRequiredJavadocTokens();
        }

        @Override
        public int[] getAcceptableJavadocTokens() {
            return getRequiredJavadocTokens();
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            visitCount++;
        }

        @Override
        public void leaveJavadocToken(DetailNode ast) {
            leaveCount++;
        }

    }

    public static class NonTightHtmlTagCheck extends AbstractJavadocCheck {
        // extra variable to make it explicit in test expected array
        // that message is from NonTightHtmlTagCheck
        public static final String MSG_KEY = MSG_TAG_FORMAT;

        private boolean reportVisitJavadocToken;

        public final void setReportVisitJavadocToken(boolean reportVisitJavadocToken) {
            this.reportVisitJavadocToken = reportVisitJavadocToken;
        }

        @Override
        public int[] getDefaultJavadocTokens() {
            return new int[] {
                JavadocTokenTypes.P_TAG_START,
                JavadocTokenTypes.LI_TAG_START,
                JavadocTokenTypes.BODY_TAG_START,
            };
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            if (reportVisitJavadocToken) {
                // We reusing messages from JavadocTypeCheck
                // it is not possible to use test specific bundle of messages
                log(ast.getLineNumber(), ast.getColumnNumber(), MSG_TAG_FORMAT, ast.getText());
            }
        }

        @Override
        public boolean acceptJavadocWithNonTightHtml() {
            return false;
        }
    }

    public static class NonTightHtmlTagTolerantCheck extends AbstractJavadocCheck {
        // extra variable to make it explicit in test expected array
        // that message is from NonTightHtmlTagCheck
        public static final String MSG_KEY = MSG_TAG_FORMAT;

        private boolean reportVisitJavadocToken;

        public final void setReportVisitJavadocToken(boolean reportVisitJavadocToken) {
            this.reportVisitJavadocToken = reportVisitJavadocToken;
        }

        @Override
        public int[] getDefaultJavadocTokens() {
            return new int[] {
                JavadocTokenTypes.P_TAG_START,
                JavadocTokenTypes.LI_TAG_START,
                JavadocTokenTypes.BODY_TAG_START,
            };
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            if (reportVisitJavadocToken) {
                // We reusing messages from JavadocTypeCheck
                // it is not possible to use test specific bundle of messages
                log(ast.getLineNumber(), ast.getColumnNumber(), MSG_TAG_FORMAT, ast.getText());
            }
        }

    }
}
