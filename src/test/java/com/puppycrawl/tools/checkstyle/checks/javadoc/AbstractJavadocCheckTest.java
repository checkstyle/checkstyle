///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_FIRST_SENTENCE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        final DefaultConfiguration checkconfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "9: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 7,
                    "mismatched input '<EOF>' expecting {WS, NEWLINE}", "JAVADOC_TAG"),
            "14: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 4,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "17: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 6,
                    "mismatched input '<EOF>' expecting {WS, NEWLINE}", "JAVADOC_TAG"),
            "20: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 8,
                    "mismatched input '<EOF>' expecting {WS, NEWLINE}", "JAVADOC_TAG"),
            "26: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 10,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "31: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 7,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "36: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 7,
                    "mismatched input '<EOF>' expecting {WS, NEWLINE}", "JAVADOC_TAG"),
            "41: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 6,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "62: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 13,
                    "mismatched input '}' expecting {LEADING_ASTERISK, WS, NEWLINE}",
                    "JAVADOC_INLINE_TAG"),
            "69: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 19,
                    "no viable alternative at input '}'", "REFERENCE"),
        };
        verify(checkconfig, getPath("InputAbstractJavadocJavadocTagsWithoutArgs.java"), expected);
    }

    @Test
    public void testNumberFormatException() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "7: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 52,
                    "mismatched input ';' expecting MEMBER", "REFERENCE"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocNumberFormatException.java"), expected);
    }

    @Test
    public void testCustomTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocCustomTag.java"), expected);
    }

    @Test
    public void testParsingErrors(@SysErr Capturable systemErr) throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "8: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "12: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 35, "img"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocParsingErrors.java"), expected);
        assertWithMessage("Error is unexpected")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testWithMultipleChecks() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocCorrectParagraph.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testAntlrError(@SysErr Capturable systemErr) throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "8: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 78,
                    "mismatched input '(' expecting <EOF>", "JAVADOC"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocInvalidAtSeeReference.java"), expected);
        assertWithMessage("Error is unexpected")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testCheckReuseAfterParseErrorWithFollowingAntlrErrorInTwoFiles(
            @SysErr Capturable systemErr) throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final Map<String, List<String>> expectedMessages = new LinkedHashMap<>(2);
        expectedMessages.put(getPath("InputAbstractJavadocParsingErrors2.java"), asList(
            "8: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "12: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 35, "img")
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
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "8: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "12: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 82,
                    "mismatched input '(' expecting <EOF>", "JAVADOC"),
        };
        verify(checkConfig,
            getPath("InputAbstractJavadocUnclosedTagAndInvalidAtSeeReference.java"), expected);
    }

    @Test
    public void testPosition()
            throws Exception {
        JavadocCatchCheck.clearCounter();
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocCatchCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocPosition.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            .isEqualTo(65);
    }

    @Test
    public void testPositionWithSinglelineComments()
            throws Exception {
        JavadocCatchCheck.clearCounter();
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocCatchCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputAbstractJavadocPositionWithSinglelineComments.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            .isEqualTo(65);
    }

    @Test
    public void testPositionOnlyComments()
            throws Exception {
        JavadocCatchCheck.clearCounter();
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocCatchCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocPositionOnlyComments.java"), expected);
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
    public void testAcceptableTokensFail()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(TokenIsNotInAcceptablesJavadocCheck.class);
        checkConfig.addProperty("javadocTokens", "RETURN_LITERAL");

        final String path = getPath("InputAbstractJavadocTokensFail.java");
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verify(checkConfig, path, expected);
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (IllegalStateException ex) {
            final String expected = "Javadoc Token "
                    + "\"RETURN_LITERAL\" was not found in "
                    + "Acceptable javadoc tokens list in check";
            assertWithMessage("Invalid exception, should start with: " + expected)
                    .that(ex.getMessage().startsWith(expected))
                    .isTrue();
        }
    }

    @Test
    public void testAcceptableTokensPass()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(TokenIsNotInAcceptablesJavadocCheck.class);
        checkConfig.addProperty("javadocTokens", "DEPRECATED_LITERAL");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocTokensPass.java"), expected);
    }

    @Test
    public void testRequiredTokenIsNotInDefaultTokens() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RequiredTokenIsNotInDefaultsJavadocCheck.class);
        final String pathToEmptyFile =
                File.createTempFile("empty", ".java", temporaryFolder).getPath();

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            verify(checkConfig, pathToEmptyFile, expected);
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (IllegalStateException ex) {
            final String expected = "Javadoc Token \""
                    + JavadocTokenTypes.RETURN_LITERAL + "\" from required"
                    + " javadoc tokens was not found in default javadoc tokens list in check";
            assertWithMessage("Invalid exception, should start with: " + expected)
                    .that(ex.getMessage().startsWith(expected))
                    .isTrue();
        }
    }

    @Test
    public void testVisitLeaveToken()
            throws Exception {
        JavadocVisitLeaveCheck.clearCounter();
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocVisitLeaveCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocLeaveToken.java"), expected);
        assertWithMessage("Javadoc visit count should be greater than zero")
                .that(JavadocVisitLeaveCheck.visitCount > 0)
                .isTrue();
        assertWithMessage("Javadoc visit and leave count should be equal")
            .that(JavadocVisitLeaveCheck.leaveCount)
            .isEqualTo(JavadocVisitLeaveCheck.visitCount);
    }

    @Test
    public void testNoWsBeforeDescriptionInJavadocTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "17: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    23, "mismatched input 'd' expecting <EOF>", "JAVADOC"),
            "26: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    30, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "32: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    39, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "44: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    34, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "52: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    31, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "61: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    15, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "68: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    32, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "75: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    17, "mismatched input '<' expecting <EOF>", "JAVADOC"),
            "82: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    34, "no viable alternative at input '-'", "JAVADOC_INLINE_TAG"),
            "89: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    39, "no viable alternative at input '-'", "JAVADOC_INLINE_TAG"),
            "96: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    19, "no viable alternative at input '<'", "JAVADOC_INLINE_TAG"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocNoWsBeforeDescriptionInJavadocTags.java"),
                expected);
    }

    @Test
    public void testWrongSingletonTagInJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "9: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "embed"),
            "14: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "keygen"),
            "19: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "SOURCE"),
            "24: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "TRACK"),
            "29: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "WBR"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocWrongSingletonTagInJavadoc.java"),
                expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheck() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NonTightHtmlTagIntolerantCheck.class);
        checkConfig.addProperty("violateExecutionOnNonTightHtml", "true");
        final String[] expected = {
            "11: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "18: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "21: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "26: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "32: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "39: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "59: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "76: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "85: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "129: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocNonTightHtmlTags.java"), expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckReportingNoViolation() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NonTightHtmlTagIntolerantCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocNonTightHtmlTagsNoViolation.java"),
                expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckVisitCount()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NonTightHtmlTagIntolerantCheck.class);
        checkConfig.addProperty("violateExecutionOnNonTightHtml", "true");
        checkConfig.addProperty("reportVisitJavadocToken", "true");
        final String[] expected = {
            "12: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "19: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "22: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "27: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "33: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "40: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "47:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "60: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "68:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "77: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "86: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "105:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "111:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "115:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "130: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocNonTightHtmlTagsVisitCount.java"),
                expected);
    }

    @Test
    public void testVisitCountForCheckAcceptingJavadocWithNonTightHtml()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NonTightHtmlTagTolerantCheck.class);
        checkConfig.addProperty("violateExecutionOnNonTightHtml", "true");
        checkConfig.addProperty("reportVisitJavadocToken", "true");
        final String[] expected = {
            "10:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "11:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "12: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "12:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "13:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "13:39: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "19: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "19:9: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "19:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "22: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "22:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "26:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "27: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "27:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "27:25: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "32:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "32:22: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "33: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "38:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "39:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "40: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "40:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "40:23: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "45:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "45:20: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "45:34: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "47: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "47:16: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "47:21: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "55:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "57: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "57:22: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "62:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "63:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "66: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "85:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "91: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "91:9: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "91:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "91:33: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };
        verify(checkConfig, getPath("InputAbstractJavadocNonTightHtmlTags2.java"), expected);
    }

    public static class TempCheck extends AbstractJavadocCheck {

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

    public static class TokenIsNotInAcceptablesJavadocCheck extends AbstractJavadocCheck {

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

    public static class NonTightHtmlTagIntolerantCheck extends AbstractJavadocCheck {

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
                log(ast.getLineNumber(), ast.getColumnNumber(), MSG_SUMMARY_FIRST_SENTENCE);
            }
        }

        @Override
        public boolean acceptJavadocWithNonTightHtml() {
            return false;
        }

    }

    public static class NonTightHtmlTagTolerantCheck extends AbstractJavadocCheck {

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
                log(ast.getLineNumber(), ast.getColumnNumber(), MSG_SUMMARY_FIRST_SENTENCE);
            }
        }

    }

}
