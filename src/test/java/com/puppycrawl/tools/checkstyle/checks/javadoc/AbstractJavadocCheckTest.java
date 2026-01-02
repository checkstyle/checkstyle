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
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_PARSE_RULE_ERROR;
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_UNCLOSED_HTML_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_TAG_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_FIRST_SENTENCE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.util.UUID;

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
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

@ExtendWith(SystemErrGuard.class)
public class AbstractJavadocCheckTest extends AbstractModuleTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/abstractjavadoc";
    }

    /**
     * Configures the environment for each test.
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
                    "no viable alternative at input 'see'", "SEE_TAG"),
            "65: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 13,
                    "no viable alternative at input '}'", "REFERENCE"),
            "73: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 19,
                    "no viable alternative at input '}'", "REFERENCE"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocJavadocTagsWithoutArgs.java"), expected);
    }

    @Test
    public void testNumberFormatException() throws Exception {
        final String[] expected = {
            "8: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 52,
                    "mismatched input '}' expecting IDENTIFIER", "MEMBER_REFERENCE"),
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
            "9: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 77,
                    "mismatched input '(' expecting <EOF>", "JAVADOC"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocInvalidAtSeeReference.java"), expected);
        assertWithMessage("Error is unexpected")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testAntlrLexerError(@SysErr Capturable systemErr) throws Exception {
        final String[] expected = {
            "10: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    29, "token recognition error at: '-'", "fieldName"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocInvalidLexing.java"), expected);
        assertWithMessage("Error is unexpected")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testCheckReuseAfterParseErrorWithFollowingAntlrErrorInTwoFiles(
            @SysErr Capturable systemErr) throws Exception {
        final String[] expectedMessagesForFile1 = {
            "11: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 5,
                    "mismatched input '<EOF>' expecting JAVADOC_INLINE_TAG_END", "INLINE_TAG"),
        };
        verifyWithInlineConfigParser(getPath(
                "InputAbstractJavadocParsingErrors2.java"), expectedMessagesForFile1);

        final String[] expectedMessagesForFile2 = {
            "9: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 77,
                    "mismatched input '(' expecting <EOF>", "JAVADOC"),
        };
        verifyWithInlineConfigParser(getPath(
                "InputAbstractJavadocInvalidAtSeeReference2.java"), expectedMessagesForFile2);

        assertWithMessage("Error is unexpected")
                .that(systemErr.getCapturedData())
                .isEqualTo("");
    }

    @Test
    public void testCheckReuseAfterParseErrorWithFollowingAntlrErrorInSingleFile()
            throws Exception {
        final String[] expected = {
            "10: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 81,
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
    public void testCacheWithBlockCommentInSingleLineComment() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocCache3.java"), expected);
    }

    @Test
    public void testCacheWithTwoBlockCommentAtSameLine() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(SummaryJavadocCheck.class, MSG_SUMMARY_FIRST_SENTENCE),
        };
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocCache4.java"), expected);
    }

    @Test
    public void testPositionOne() throws Exception {
        JavadocCatchCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocPositionOne.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            .isEqualTo(21);
    }

    @Test
    public void testPositionTwo() throws Exception {
        JavadocCatchCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocPositionTwo.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            .isEqualTo(29);
    }

    @Test
    public void testPositionThree() throws Exception {
        JavadocCatchCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocPositionThree.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            .isEqualTo(15);
    }

    @Test
    public void testPositionWithSinglelineCommentsOne() throws Exception {
        JavadocCatchCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocPositionWithSinglelineCommentsOne.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            .isEqualTo(21);
    }

    @Test
    public void testPositionWithSinglelineCommentsTwo() throws Exception {
        JavadocCatchCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocPositionWithSinglelineCommentsTwo.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            .isEqualTo(29);
    }

    @Test
    public void testPositionWithSinglelineCommentsThree() throws Exception {
        JavadocCatchCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocPositionWithSinglelineCommentsThree.java"), expected);
        assertWithMessage("Invalid number of javadocs")
            .that(JavadocCatchCheck.javadocsNumber)
            .isEqualTo(15);
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
        final int[] defaultJavadocTokens = {JavadocCommentsTokenTypes.JAVADOC_CONTENT};
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
        final int[] defaultJavadocTokens = {JavadocCommentsTokenTypes.JAVADOC_CONTENT,
            JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG_END,
            JavadocCommentsTokenTypes.HTML_COMMENT,
            JavadocCommentsTokenTypes.HTML_ELEMENT,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.NEWLINE};
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
        check.setJavadocTokens("RETURN_BLOCK_TAG");
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
        catch (IllegalStateException exc) {
            final String expected = "Javadoc Token "
                    + "\"RETURN_BLOCK_TAG\" was not found in "
                    + "Acceptable javadoc tokens list in check "
                    + TokenIsNotInAcceptablesCheck.class.getName();
            assertWithMessage("Invalid exception, should start with: %s", expected)
                    .that(exc.getMessage())
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
            execute(checkConfig, pathToEmptyFile.toString());
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (IllegalStateException exc) {
            final String expected = "Javadoc Token \""
                    + JavadocCommentsTokenTypes.RETURN_BLOCK_TAG + "\" from required"
                    + " javadoc tokens was not found in default javadoc tokens list in check "
                    + RequiredTokenIsNotInDefaultsJavadocCheck.class.getName();
            assertWithMessage("Invalid exception, should start with: %s", expected)
                    .that(exc.getMessage())
                    .startsWith(expected);
        }
    }

    @Test
    public void testVisitLeaveTokenOne() throws Exception {
        JavadocVisitLeaveCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocLeaveTokenOne.java"), expected);
        assertWithMessage("Javadoc visit count should be greater than zero")
                .that(JavadocVisitLeaveCheck.visitCount)
                .isGreaterThan(0);
        assertWithMessage("Javadoc visit and leave count should be equal")
            .that(JavadocVisitLeaveCheck.leaveCount)
            .isEqualTo(JavadocVisitLeaveCheck.visitCount);
    }

    @Test
    public void testVisitLeaveTokenTwo() throws Exception {
        JavadocVisitLeaveCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocLeaveTokenTwo.java"), expected);
        assertWithMessage("Javadoc visit count should be greater than zero")
                .that(JavadocVisitLeaveCheck.visitCount)
                .isGreaterThan(0);
        assertWithMessage("Javadoc visit and leave count should be equal")
            .that(JavadocVisitLeaveCheck.leaveCount)
            .isEqualTo(JavadocVisitLeaveCheck.visitCount);
    }

    @Test
    public void testVisitLeaveTokenThree() throws Exception {
        JavadocVisitLeaveCheck.clearCounter();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputAbstractJavadocLeaveTokenThree.java"), expected);
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
            "27: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    29, "token recognition error at: '-'", "fieldName"),
            "40: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    30, "token recognition error at: '-'", " "),
            "72: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    32, "mismatched input 'description' expecting <EOF>", "JAVADOC"),
            "88: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    34, "mismatched input 'description' "
                             + "expecting JAVADOC_INLINE_TAG_END", "INLINE_TAG"),
            "95: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    39, "mismatched input 'description'"
                            + " expecting JAVADOC_INLINE_TAG_END", "INLINE_TAG"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNoWsBeforeDescriptionInJavadocTags.java"),
                expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckOne() throws Exception {
        final String[] expected = {
            "12: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "19: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "22: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "28: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "35: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "54: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "64: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
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
    public void testNonTightHtmlTagIntolerantCheckReportingNoViolationTwo() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTagsNoViolationTwo.java"), expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckVisitCountOne() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "28: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "35: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "47: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "55: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "65: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "77:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "SEE_BLOCK_TAG"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTagsVisitCountOne.java"),
                expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckVisitCountTwo() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "47: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "57:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "SEE_BLOCK_TAG"),
            "82: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTagsVisitCountTwo.java"),
                expected);
    }

    @Test
    public void testVisitCountForCheckAcceptingJavadocWithNonTightHtml() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "23: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "27: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 68,
                    "no viable alternative at input '</'", "HTML_ELEMENT"),
            "34: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "48: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "66: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "66:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "PARAM_BLOCK_TAG"),
            "78: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTags2.java"), expected);
    }

    @Test
    public void testVisitCountForCheckAcceptingJavadocWithNonTightHtml3() throws Exception {
        final String[] expected = {
            "15:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "PARAM_BLOCK_TAG"),
            "30:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "PARAM_BLOCK_TAG"),
            "36: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "38:8: " + getCheckMessage(NonTightHtmlTagCheck.MSG_KEY, "PARAM_BLOCK_TAG"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocNonTightHtmlTags3.java"), expected);
    }

    @Test
    public void testLeaveJavadocToken() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAbstractJavadocLeaveToken.java"), expected);
    }

    public static class JavadocLeaveTokenCheck extends AbstractJavadocCheck {

        private static int visitCount;
        private static int leaveCount;

        @Override
        public int[] getDefaultJavadocTokens() {
            return new int[] {JavadocCommentsTokenTypes.HTML_ELEMENT};
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            visitCount++;
        }

        @Override
        public void leaveJavadocToken(DetailNode ast) {
            leaveCount++;
        }

        @Override
        public void finishJavadocTree(DetailNode ast) {
            if (visitCount != leaveCount) {
                throw new IllegalStateException("mismatch in visitCount and leaveCount");
            }
        }
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
            return new int[] {JavadocCommentsTokenTypes.JAVADOC_CONTENT};
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            assertWithMessage(ast.toString())
                .that(ast.getText())
                .isEqualTo("JAVADOC_CONTENT");
            final DetailNode text = JavadocUtil.findFirstToken(ast, JavadocCommentsTokenTypes.TEXT);
            assertWithMessage("Empty javadoc text at %s", ast)
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
            return new int[] {JavadocCommentsTokenTypes.RETURN_BLOCK_TAG};
        }

        @Override
        public int[] getDefaultJavadocTokens() {
            return new int[] {JavadocCommentsTokenTypes.DEPRECATED_BLOCK_TAG};
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
            return new int[] {JavadocCommentsTokenTypes.DEPRECATED_BLOCK_TAG};
        }

        @Override
        public int[] getDefaultJavadocTokens() {
            return new int[] {JavadocCommentsTokenTypes.DEPRECATED_BLOCK_TAG};
        }

        @Override
        public int[] getAcceptableJavadocTokens() {
            return new int[] {JavadocCommentsTokenTypes.DEPRECATED_BLOCK_TAG};
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
            return new int[] {JavadocCommentsTokenTypes.TEXT};
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
                JavadocCommentsTokenTypes.SEE_BLOCK_TAG,
            };
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            if (reportVisitJavadocToken) {
                // We're reusing messages from JavadocTypeCheck
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
                JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
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
