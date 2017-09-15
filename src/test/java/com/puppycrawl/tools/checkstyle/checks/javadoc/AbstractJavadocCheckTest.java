////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_JAVADOC_PARSE_RULE_ERROR;
import static com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser.MSG_UNCLOSED_HTML_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_JAVADOC_MISSED_HTML_CLOSE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_JAVADOC_WRONG_SINGLETON_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_FIRST_SENTENCE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.BlockCommentPosition;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class AbstractJavadocCheckTest extends AbstractModuleTestSupport {

    @Rule
    public final SystemErrRule systemErr = new SystemErrRule().enableLog().mute();

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/abstractjavadoc";
    }

    @Test
    public void testJavadocTagsWithoutArgs() throws Exception {
        final DefaultConfiguration checkconfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "5: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 7,
                    "mismatched input '<EOF>' expecting {WS, NEWLINE}", "JAVADOC_TAG"),
            "10: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 4,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "13: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 6,
                    "mismatched input '<EOF>' expecting {WS, NEWLINE}", "JAVADOC_TAG"),
            "16: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 8,
                    "mismatched input '<EOF>' expecting {WS, NEWLINE}", "JAVADOC_TAG"),
            "22: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 10,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "27: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 7,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "32: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 7,
                    "mismatched input '<EOF>' expecting {WS, NEWLINE}", "JAVADOC_TAG"),
            "37: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 6,
                    "no viable alternative at input '<EOF>'", "JAVADOC_TAG"),
            "58: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 13,
                    "mismatched input '}' expecting {LEADING_ASTERISK, WS, NEWLINE}",
                    "JAVADOC_INLINE_TAG"),
            "65: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 19,
                    "no viable alternative at input '}'", "REFERENCE"),
        };
        verify(checkconfig, getPath("InputAbstractJavadocJavadocTagsWithoutArgs.java"), expected);
    }

    @Test
    public void testNumberFormatException() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 52,
                    "mismatched input ';' expecting MEMBER", "REFERENCE"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocNumberFormatException.java"), expected);
    }

    @Test
    public void testCustomTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocCustomTag.java"), expected);
    }

    @Test
    public void testParsingErrors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "8: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 35, "img"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocParsingErrors.java"), expected);
        assertEquals("Error is unexpected", "", systemErr.getLog());
    }

    @Test
    public void testWithMultipleChecks() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        final DefaultConfiguration checksConfig = createModuleConfig(TreeWalker.class);
        checksConfig.addChild(createModuleConfig(AtclauseOrderCheck.class));
        checksConfig.addChild(createModuleConfig(JavadocParagraphCheck.class));
        checkerConfig.addChild(checksConfig);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);

        verify(checker, getPath("InputAbstractJavadocCorrectParagraph.java"));
    }

    @Test
    public void testAntlrError() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 78,
                    "mismatched input '(' expecting <EOF>", "JAVADOC"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocInvalidAtSeeReference.java"), expected);
        assertEquals("Error is unexpected", "", systemErr.getLog());
    }

    @Test
    public void testCheckReuseAfterParseErrorWithFollowingAntlrErrorInTwoFiles() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final Map<String, List<String>> expectedMessages = new LinkedHashMap<>(2);
        expectedMessages.put(getPath("InputAbstractJavadocParsingErrors.java"), asList(
            "4: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "8: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 35, "img")
        ));
        expectedMessages.put(getPath("InputAbstractJavadocInvalidAtSeeReference.java"),
            singletonList("4: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 78,
                    "mismatched input '(' expecting <EOF>", "JAVADOC")
        ));
        verify(createChecker(checkConfig), new File[] {
            new File(getPath("InputAbstractJavadocParsingErrors.java")),
            new File(getPath("InputAbstractJavadocInvalidAtSeeReference.java")), },
                expectedMessages);
        assertEquals("Error is unexpected", "", systemErr.getLog());
    }

    @Test
    public void testCheckReuseAfterParseErrorWithFollowingAntlrErrorInSingleFile()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage(MSG_JAVADOC_MISSED_HTML_CLOSE, 4, "unclosedTag"),
            "8: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR, 82,
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
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocPosition.java"), expected);
        assertEquals("Invalid number of javadocs",
            58, JavadocCatchCheck.javadocsNumber);
    }

    @Test
    public void testPositionWithSinglelineComments()
            throws Exception {
        JavadocCatchCheck.clearCounter();
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocCatchCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputAbstractJavadocPositionWithSinglelineComments.java"), expected);
        assertEquals("Invalid number of javadocs",
                58, JavadocCatchCheck.javadocsNumber);
    }

    @Test
    public void testPositionOnlyComments()
            throws Exception {
        JavadocCatchCheck.clearCounter();
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocCatchCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocPositionOnlyComments.java"), expected);
        assertEquals("Invalid number of javadocs",
                0, JavadocCatchCheck.javadocsNumber);
    }

    @Test
    public void testBlockCommentPositionHasPrivateConstr() throws Exception {
        Assert.assertTrue("Constructor is not private",
                TestUtil.isUtilsClassHasPrivateConstructor(BlockCommentPosition.class, true));
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

        Assert.assertNotNull("Default tokens should not be null", check.getDefaultTokens());
        Assert.assertArrayEquals("Acceptable tokens should be equal to default",
                check.getDefaultTokens(), check.getAcceptableTokens());
        Assert.assertArrayEquals("REquired tokens should be equal to default",
                check.getDefaultTokens(), check.getRequiredTokens());
        Assert.assertArrayEquals("Invalid default javadoc tokens",
                defaultJavadocTokens, check.getDefaultJavadocTokens());
        Assert.assertArrayEquals("Invalid acceptable javadoc tokens",
                defaultJavadocTokens, check.getAcceptableJavadocTokens());
        Assert.assertNotEquals("Invalid required javadoc tokens",
                defaultJavadocTokens, check.getRequiredJavadocTokens());
    }

    @Test
    public void testAcceptableTokensFail()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(TokenIsNotInAcceptablesJavadocCheck.class);
        checkConfig.addAttribute("javadocTokens", "RETURN_LITERAL");
        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, getPath("InputAbstractJavadocMain.java"), expected);
            Assert.fail("CheckstyleException is expected");
        }
        catch (IllegalStateException ex) {
            final String expected = "Javadoc Token "
                    + "\"RETURN_LITERAL\" was not found in "
                    + "Acceptable javadoc tokens list in check";
            Assert.assertTrue("Invalid exception, should start with: " + expected,
                    ex.getMessage().startsWith(expected));
        }
    }

    @Test
    public void testAcceptableTokensPass()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(TokenIsNotInAcceptablesJavadocCheck.class);
        checkConfig.addAttribute("javadocTokens", "DEPRECATED_LITERAL");

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocMain.java"), expected);
    }

    @Test
    public void testRequiredTokenIsNotInDefaultTokens() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RequiredTokenIsNotInDefaultsJavadocCheck.class);
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
            verify(checkConfig, pathToEmptyFile, expected);
            Assert.fail("CheckstyleException is expected");
        }
        catch (IllegalStateException ex) {
            final String expected = "Javadoc Token \""
                    + JavadocTokenTypes.RETURN_LITERAL + "\" from required"
                    + " javadoc tokens was not found in default javadoc tokens list in check";
            Assert.assertTrue("Invalid exception, should start with: " + expected,
                    ex.getMessage().startsWith(expected));
        }
    }

    @Test
    public void testVisitLeaveToken()
            throws Exception {
        JavadocVisitLeaveCheck.clearCounter();
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocVisitLeaveCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocPosition.java"), expected);
        Assert.assertTrue("Javadoc visit count should be greater than zero",
                JavadocVisitLeaveCheck.visitCount > 0);
        assertEquals("Javadoc visit and leave count should be equal",
                JavadocVisitLeaveCheck.visitCount, JavadocVisitLeaveCheck.leaveCount);
    }

    @Test
    public void testNoWsBeforeDescriptionInJavadocTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "13: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    23, "mismatched input 'd' expecting <EOF>", "JAVADOC"),
            "22: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    30, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "28: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    39, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "40: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    34, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "48: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    31, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "57: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    15, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "64: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    32, "mismatched input '-' expecting <EOF>", "JAVADOC"),
            "71: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    17, "mismatched input '<' expecting <EOF>", "JAVADOC"),
            "78: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    34, "no viable alternative at input '-'", "JAVADOC_INLINE_TAG"),
            "85: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    39, "no viable alternative at input '-'", "JAVADOC_INLINE_TAG"),
            "92: " + getCheckMessage(MSG_JAVADOC_PARSE_RULE_ERROR,
                    19, "no viable alternative at input '<'", "JAVADOC_INLINE_TAG"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocNoWsBeforeDescriptionInJavadocTags.java"),
                expected);
    }

    @Test
    public void testWrongSingletonTagInJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TempCheck.class);
        final String[] expected = {
            "5: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "embed"),
            "10: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "keygen"),
            "15: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "SOURCE"),
            "20: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "TRACK"),
            "25: " + getCheckMessage(MSG_JAVADOC_WRONG_SINGLETON_TAG, 9, "WBR"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocWrongSingletonTagInJavadoc.java"),
                expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheck() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NonTightHtmlTagIntolerantCheck.class);
        checkConfig.addAttribute("violateExecutionOnNonTightHtml", "true");
        final String[] expected = {
            "6: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "13: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "16: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "21: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "27: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "34: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "54: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "71: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "80: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "124: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocNonTightHtmlTags.java"), expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckReportingNoViolation() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NonTightHtmlTagIntolerantCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAbstractJavadocNonTightHtmlTags.java"), expected);
    }

    @Test
    public void testNonTightHtmlTagIntolerantCheckVisitCount()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NonTightHtmlTagIntolerantCheck.class);
        checkConfig.addAttribute("violateExecutionOnNonTightHtml", "true");
        checkConfig.addAttribute("reportVisitJavadocToken", "true");
        final String[] expected = {
            "6: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "13: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "16: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "21: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "27: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "34: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "41:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "54: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "62:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "71: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "80: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "99:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "105:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "109:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "124: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
        };
        verify(checkConfig, getPath("InputAbstractJavadocNonTightHtmlTags.java"), expected);
    }

    @Test
    public void testVisitCountForCheckAcceptingJavadocWithNonTightHtml()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NonTightHtmlTagTolerantCheck.class);
        checkConfig.addAttribute("violateExecutionOnNonTightHtml", "true");
        checkConfig.addAttribute("reportVisitJavadocToken", "true");
        final String[] expected = {
            "4:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "5:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "6: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "6:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "7:4: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "7:39: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "13: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "13:9: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "13:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "16: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "16:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "20:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "21: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "21:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "21:30: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "26:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "26:22: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "27: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "32:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "33:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "34: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "34:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "34:23: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "39:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "39:20: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "39:34: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "41: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "li"),
            "41:16: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "41:21: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "49:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "51: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "51:22: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "56:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "57:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "60: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "tr"),
            "79:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "85: " + getCheckMessage(MSG_UNCLOSED_HTML_TAG, "p"),
            "85:9: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "85:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "85:33: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };
        verify(checkConfig, getPath("InputAbstractJavadocNonTightHtmlTags2.java"), expected);
    }

    private static class TempCheck extends AbstractJavadocCheck {

        @Override
        public int[] getDefaultJavadocTokens() {
            return CommonUtils.EMPTY_INT_ARRAY;
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            // do nothing
        }
    }

    private static class JavadocCatchCheck extends AbstractJavadocCheck {
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
            assertEquals(ast.toString(), "JAVADOC", ast.getText());
            javadocsNumber++;
        }
    }

    private static class RequiredTokenIsNotInDefaultsJavadocCheck extends AbstractJavadocCheck {
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
            return CommonUtils.EMPTY_INT_ARRAY;
        }

        @Override
        public void visitJavadocToken(DetailNode ast) {
            // not used
        }
    }

    private static class TokenIsNotInAcceptablesJavadocCheck extends AbstractJavadocCheck {
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

    private static class JavadocVisitLeaveCheck extends AbstractJavadocCheck {
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
