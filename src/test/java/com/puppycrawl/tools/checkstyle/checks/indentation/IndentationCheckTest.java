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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_CHILD_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_CHILD_ERROR_MULTI;
import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_ERROR_MULTI;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Unit test for IndentationCheck.
 */
public class IndentationCheckTest extends AbstractModuleTestSupport {

    private static final Pattern LINE_WITH_COMMENT_REGEX =
                    Pattern.compile(".*?//indent:(\\d+)(?: ioffset:(\\d+))?"
                        + " exp:(>=)?(\\d+(?:,\\d+)*?)( warn)?$");

    private static final IndentComment[] EMPTY_INDENT_COMMENT_ARRAY = new IndentComment[0];

    private static IndentComment[] getLinesWithWarnAndCheckComments(String aFileName,
            final int tabWidth)
                    throws IOException {
        final List<IndentComment> result = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(aFileName),
                StandardCharsets.UTF_8)) {
            int lineNumber = 1;
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                final Matcher match = LINE_WITH_COMMENT_REGEX.matcher(line);
                if (match.matches()) {
                    final IndentComment warn = new IndentComment(match, lineNumber);
                    final int actualIndent = getLineStart(line, tabWidth);

                    if (actualIndent != warn.getIndent()) {
                        throw new IllegalStateException(String.format(Locale.ROOT,
                                        "File \"%1$s\" has incorrect indentation in comment. "
                                                        + "Line %2$d: comment:%3$d, actual:%4$d.",
                                        aFileName,
                                        lineNumber,
                                        warn.getIndent(),
                                        actualIndent));
                    }

                    if (!isCommentConsistent(warn)) {
                        throw new IllegalStateException(String.format(Locale.ROOT,
                                        "File \"%1$s\" has inconsistent comment on line %2$d",
                                        aFileName,
                                        lineNumber));
                    }

                    if (warn.isWarning()) {
                        result.add(warn);
                    }
                }
                else if (!line.isEmpty()) {
                    throw new IllegalStateException(String.format(Locale.ROOT,
                                    "File \"%1$s\" has no indentation comment or its format "
                                                    + "malformed. Error on line: %2$d",
                                    aFileName,
                                    lineNumber));
                }
                lineNumber++;
            }
        }
        return result.toArray(EMPTY_INDENT_COMMENT_ARRAY);
    }

    private static boolean isCommentConsistent(IndentComment comment) {
        final String[] levels = comment.getExpectedWarning().split(", ");
        final int indent = comment.getIndent() + comment.getIndentOffset();
        final boolean result;
        if (levels.length > 1) {
            // multi
            final boolean containsActualLevel =
                            Arrays.asList(levels).contains(String.valueOf(indent));

            result = containsActualLevel != comment.isWarning();
        }
        else {
            final int expectedWarning = Integer.parseInt(comment.getExpectedWarning());

            if (comment.isExpectedNonStrict()) {
                // non-strict
                final boolean test = indent >= expectedWarning;
                result = test != comment.isWarning();
            }
            else {
                // single
                final boolean test = expectedWarning == indent;
                result = test != comment.isWarning();
            }
        }
        return result;
    }

    private static int getLineStart(String line, final int tabWidth) {
        int lineStart = 0;
        for (int index = 0; index < line.length(); ++index) {
            if (!Character.isWhitespace(line.charAt(index))) {
                lineStart = CommonUtil.lengthExpandedTabs(line, index, tabWidth);
                break;
            }
        }
        return lineStart;
    }

    private void verifyWarns(Configuration config, String filePath,
                    String... expected)
                    throws Exception {
        final int tabWidth = Integer.parseInt(config.getAttribute("tabWidth"));
        final IndentComment[] linesWithWarn =
                        getLinesWithWarnAndCheckComments(filePath, tabWidth);
        verify(config, filePath, expected, linesWithWarn);
        assertEquals(linesWithWarn.length, expected.length,
            "Expected warning count in UT does not match warn comment count in input file");
    }

    private void verify(Configuration config, String filePath, String[] expected,
            final IndentComment... linesWithWarn) throws Exception {
        final Checker checker = createChecker(config);
        checker.addListener(new IndentAudit(linesWithWarn));
        verify(checker, filePath, expected);
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/indentation/indentation";
    }

    @Test
    public void testGetRequiredTokens() {
        final IndentationCheck checkObj = new IndentationCheck();
        final int[] requiredTokens = checkObj.getRequiredTokens();
        final HandlerFactory handlerFactory = new HandlerFactory();
        final int[] expected = handlerFactory.getHandledTypes();
        Arrays.sort(expected);
        Arrays.sort(requiredTokens);
        assertArrayEquals(expected, requiredTokens, "Default required tokens are invalid");
    }

    @Test
    public void testGetAcceptableTokens() {
        final IndentationCheck checkObj = new IndentationCheck();
        final int[] acceptableTokens = checkObj.getAcceptableTokens();
        final HandlerFactory handlerFactory = new HandlerFactory();
        final int[] expected = handlerFactory.getHandledTypes();
        Arrays.sort(expected);
        Arrays.sort(acceptableTokens);
        assertArrayEquals(expected, acceptableTokens, "Default acceptable tokens are invalid");
    }

    @Test
    public void testThrowsIndentProperty() {
        final IndentationCheck indentationCheck = new IndentationCheck();

        indentationCheck.setThrowsIndent(1);

        assertEquals(1, indentationCheck.getThrowsIndent(), "Invalid throws indent");
    }

    @Test
    public void testStrictCondition() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "4");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "true");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationStrictCondition.java"), expected);
    }

    @Test
    public void forbidOldStyle() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "true");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = {
            "20: " + getCheckMessage(MSG_ERROR, "int", 29, 12),
            "21: " + getCheckMessage(MSG_ERROR, "int", 29, 12),
        };
        verifyWarns(checkConfig, getPath("InputIndentationMethodCStyle.java"), expected);
    }

    @Test
    public void testZeroCaseLevel() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "0");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationZeroCaseLevel.java"), expected);
    }

    @Test
    public void testAndroidStyle() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = {
            "42: " + getCheckMessage(MSG_ERROR, "extends", 3, 8),
            "44: " + getCheckMessage(MSG_ERROR, "member def type", 3, 4),
            "47: " + getCheckMessage(MSG_ERROR, "foo", 8, 12),
            "50: " + getCheckMessage(MSG_ERROR, "int", 8, 12),
            "53: " + getCheckMessage(MSG_ERROR, "true", 13, 16),
            "56: " + getCheckMessage(MSG_ERROR, "+", 16, 20),
            "57: " + getCheckMessage(MSG_ERROR, "if", 8, 12),
            "60: " + getCheckMessage(MSG_ERROR, "if rcurly", 11, 12),
            "62: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 7, 8),
        };
        verifyWarns(checkConfig, getPath("InputIndentationAndroidStyle.java"), expected);
    }

    @Test
    public void testMethodCallLineWrap() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "53: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 18, 20),
            "54: " + getCheckMessage(MSG_ERROR, "method call rparen", 14, 16),
            "75: " + getCheckMessage(MSG_ERROR, "lambda arguments", 12, 16),
        };
        verifyWarns(checkConfig, getPath("InputIndentationMethodCallLineWrap.java"), expected);
    }

    @Test
    public void testDifficultAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "40: " + getCheckMessage(MSG_ERROR, "@", 0, 4),
            "41: " + getCheckMessage(MSG_ERROR, "@", 0, 4),
            "50: " + getCheckMessage(MSG_ERROR, "@", 6, 8),
        };
        verifyWarns(checkConfig, getPath("InputIndentationDifficultAnnotations.java"), expected);
    }

    @Test
    public void testAnnotationClosingParenthesisEndsInSameIndentationAsOpening() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("forceStrictCondition", "true");
        checkConfig.addAttribute("tabWidth", "4");

        final String[] expected = {
            "34: " + getCheckMessage(MSG_ERROR, ")", 16, 0),
            "36: " + getCheckMessage(MSG_ERROR, ")", 16, 0),
            "40: " + getCheckMessage(MSG_ERROR, ")", 8, 4),
            "42: " + getCheckMessage(MSG_ERROR, ")", 8, 4),
            "46: " + getCheckMessage(MSG_ERROR, ")", 8, 4),
        };

        verifyWarns(checkConfig,
            getPath("InputIndentation"
                + "AnnotationClosingParenthesisEndsInSameIndentationAsOpening.java"),
                expected);
    }

    @Test
    public void testAnonClassesFromGuava() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationFromGuava2.java"), expected);
    }

    @Test
    public void testAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationFromGuava.java"), expected);
    }

    @Test
    public void testCorrectIfAndParameters() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationCorrectIfAndParameter.java"), expected);
    }

    @Test
    public void testAnonymousClasses() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationAnonymousClasses.java"), expected);
    }

    @Test
    public void testArrays() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "2");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationArrays.java"), expected);
    }

    @Test
    public void testLabels() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationLabels.java"), expected);
    }

    @Test
    public void testClassesAndMethods() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationClassesMethods.java"), expected);
    }

    @Test
    public void testCtorCall() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = {
            "28: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 4, 6),
            "29: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 4, 6),
            "30: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 4, 6),
            "34: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 4, 6),
            "35: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 4, 6),
            "39: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 4, 6),
            "40: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 4, 8),
            "41: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 4, 8),
            "45: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 4, 6),
            "46: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 4, 8),
            "50: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 4, 6),
            "51: " + getCheckMessage(MSG_ERROR, "(", 4, 8),
            "52: " + getCheckMessage(MSG_ERROR, "x", 4, 8),
            "56: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 4, 6),
            "57: " + getCheckMessage(MSG_ERROR, "method call lparen", 4, 6),
            "62: " + getCheckMessage(MSG_ERROR, ".", 4, 10),
            "63: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 4, 8),
            "68: " + getCheckMessage(MSG_ERROR, "super", 4, 10),
            "69: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 4, 8),
        };
        verifyWarns(checkConfig, getPath("InputIndentationCtorCall.java"), expected);
    }

    @Test
    public void testMembers() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_ERROR, "=", 5, 6),
            "57: " + getCheckMessage(MSG_ERROR, "class def rcurly", 3, 2),
        };

        verifyWarns(checkConfig, getPath("InputIndentationMembers.java"), expected);
    }

    @Test
    public void testInvalidLabel() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "24: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 10, "8, 12"),
            "33: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 2, "4, 8"),
            "36: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 18, "8, 12"),
            "37: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 18, 8),
            "39: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 6, "8, 12"),
            "41: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 6, "8, 12"),
        };
        verifyWarns(checkConfig, getPath("InputIndentationInvalidLabelIndent.java"), expected);
    }

    @Test
    public void testInvalidLabelWithWhileLoop() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "18: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 9, "4, 8"),
            "19: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "label", 9, "8, 12"),
        };
        verifyWarns(checkConfig, getPath("InputIndentationInvalidLabelWithWhileLoopIndent.java"),
            expected);
    }

    @Test
    public void testValidLabel() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationValidLabelIndent.java"), expected);
    }

    @Test
    public void testValidIfWithChecker() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidIfIndent.java");
        final String[] expected = {
            "231: " + getCheckMessage(MSG_ERROR, "(", 8, 12),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidDotWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidDotIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidMethodWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidMethodIndent.java");
        final String[] expected = {
            "129: " + getCheckMessage(MSG_ERROR, "void", 4, 8),
            "130: " + getCheckMessage(MSG_ERROR, "method5", 4, 8),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidMethodWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidMethodIndent.java");
        final String[] expected = {
            "23: " + getCheckMessage(MSG_ERROR, "ctor def rcurly", 6, 4),
            "26: " + getCheckMessage(MSG_ERROR, "ctor def modifier", 6, 4),
            "27: " + getCheckMessage(MSG_ERROR, "ctor def lcurly", 2, 4),
            "28: " + getCheckMessage(MSG_ERROR, "ctor def rcurly", 6, 4),
            "31: " + getCheckMessage(MSG_ERROR, "method def modifier", 2, 4),
            "32: " + getCheckMessage(MSG_ERROR, "method def rcurly", 6, 4),
            "69: " + getCheckMessage(MSG_ERROR, "method def modifier", 5, 4),
            "70: " + getCheckMessage(MSG_ERROR, "final", 5, 9),
            "71: " + getCheckMessage(MSG_ERROR, "void", 5, 9),
            "72: " + getCheckMessage(MSG_ERROR, "method5", 4, 9),
            "80: " + getCheckMessage(MSG_ERROR, "method def modifier", 3, 4),
            "81: " + getCheckMessage(MSG_ERROR, "final", 3, 7),
            "82: " + getCheckMessage(MSG_ERROR, "void", 3, 7),
            "83: " + getCheckMessage(MSG_ERROR, "method6", 5, 7),
            "93: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 4, 8),
            "98: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 6, 8),
            "99: " + getCheckMessage(MSG_ERROR, "if", 6, 8),
            "100: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "101: " + getCheckMessage(MSG_ERROR, "if rcurly", 6, 8),
            "104: " + getCheckMessage(MSG_ERROR, "Arrays", 10, 12),
            "113: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "122: " + getCheckMessage(MSG_ERROR, "new", 10, 12),
            "126: " + getCheckMessage(MSG_ERROR, "new", 10, 12),
            "127: " + getCheckMessage(MSG_ERROR, ")", 6, 8),
            "131: " + getCheckMessage(MSG_ERROR, "method call rparen", 6, 8),
            "145: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "148: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "158: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 6, 12),
            "170: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 4, 8),
            "175: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 4, 8),
            "179: " + getCheckMessage(MSG_ERROR, "int", 0, 8),
            "180: " + getCheckMessage(MSG_ERROR, "method9", 4, 8),
            "190: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 12, 8),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidSwitchWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidSwitchIndent.java");
        final String[] expected = {
            "30: " + getCheckMessage(MSG_ERROR, "switch", 6, 8),
            "32: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 12),
            "33: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "37: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "39: " + getCheckMessage(MSG_CHILD_ERROR, "case", 14, 12),
            "40: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 12),
            "43: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 12),
            "44: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "45: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "53: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "54: " + getCheckMessage(MSG_CHILD_ERROR, "block", 18, 16),
            "55: " + getCheckMessage(MSG_ERROR, "block rcurly", 10, 12),
            "59: " + getCheckMessage(MSG_ERROR, "block lcurly", 10, 12),
            "62: " + getCheckMessage(MSG_ERROR, "block rcurly", 14, 12),
            "66: " + getCheckMessage(MSG_ERROR, "block lcurly", 14, 12),
            "69: " + getCheckMessage(MSG_ERROR, "block rcurly", 10, 12),
            "76: " + getCheckMessage(MSG_CHILD_ERROR, "case", 14, 16),
            "81: " + getCheckMessage(MSG_CHILD_ERROR, "case", 14, 16),
            "89: " + getCheckMessage(MSG_ERROR, "switch rcurly", 6, 8),
            "92: " + getCheckMessage(MSG_ERROR, "switch lcurly", 6, 8),
            "93: " + getCheckMessage(MSG_ERROR, "switch rcurly", 10, 8),
            "95: " + getCheckMessage(MSG_ERROR, "switch lcurly", 10, 8),
            "96: " + getCheckMessage(MSG_ERROR, "switch rcurly", 6, 8),
            "99: " + getCheckMessage(MSG_CHILD_ERROR, "case", 8, 12),
            "100: " + getCheckMessage(MSG_ERROR, "if", 12, 16),
            "101: " + getCheckMessage(MSG_CHILD_ERROR, "if", 16, 20),
            "102: " + getCheckMessage(MSG_ERROR, "else", 12, 16),
            "103: " + getCheckMessage(MSG_CHILD_ERROR, "else", 16, 20),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidSwitchWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidSwitchIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidArrayInitDefaultIndentWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidArrayInitDefaultIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidArrayInitWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "8");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidArrayInitIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidArrayInitWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidArrayInitIndent.java");
        final String[] expected = {
            "21: " + getCheckMessage(MSG_ERROR, "member def type", 2, 4),
            "22: " + getCheckMessage(MSG_ERROR, "member def type", 6, 4),
            "24: " + getCheckMessage(MSG_ERROR, "member def type", 2, 4),
            "28: " + getCheckMessage(MSG_ERROR, "member def type", 6, 4),
            "29: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 8, 10),
            "30: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 4, "6, 10"),
            "33: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 9, 8),
            "34: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 7, 8),
            "35: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 9, 8),
            "40: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization lcurly", 2, "4, 8"),
            "44: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 6, "4, 8"),
            "48: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization lcurly", 2, "4, 8"),
            "52: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "array initialization", 20,
                "8, 31, 33"),
            "53: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "array initialization", 4, "8, 31, 33"),
            "58: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 6, 8),
            "63: " + getCheckMessage(MSG_ERROR, "member def type", 2, 4),
            "65: " + getCheckMessage(MSG_ERROR, "member def type", 6, 4),
            "66: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 2, "6, 10"),
            "69: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 6, 8),
            "76: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "89: " + getCheckMessage(MSG_ERROR, "1", 8, 12),
            "100: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "101: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 14, 12),
            "104: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "105: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 14, 12),
            "106: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 6, "8, 12"),
            "109: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization lcurly", 6, "8, 12"),
            "110: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 14, 12),
            "111: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "112: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 6, "8, 12"),
        };

        // Test input for this test case is not checked due to issue #693.
        verify(checkConfig, fileName, expected);
    }

    @Test
    public void testValidTryWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidTryIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidTryWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidTryIndent.java");
        final String[] expected = {
            "25: " + getCheckMessage(MSG_ERROR, "try", 9, 8),
            "26: " + getCheckMessage(MSG_ERROR, "try rcurly", 7, 8),
            "28: " + getCheckMessage(MSG_ERROR, "catch rcurly", 7, 8),
            "30: " + getCheckMessage(MSG_ERROR, "try", 4, 8),
            "31: " + getCheckMessage(MSG_CHILD_ERROR, "try", 8, 12),
            "32: " + getCheckMessage(MSG_ERROR, "try rcurly", 4, 8),
            "33: " + getCheckMessage(MSG_CHILD_ERROR, "finally", 8, 12),
            "38: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 8, 12),
            "43: " + getCheckMessage(MSG_ERROR, "try rcurly", 10, 8),
            "45: " + getCheckMessage(MSG_ERROR, "catch rcurly", 6, 8),
            "52: " + getCheckMessage(MSG_ERROR, "catch rcurly", 5, 8),
            "59: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 10, 12),
            "60: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 14, 12),
            "61: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 10, 12),
            "63: " + getCheckMessage(MSG_ERROR, "catch", 6, 8),
            "70: " + getCheckMessage(MSG_ERROR, "try lcurly", 10, 8),
            "72: " + getCheckMessage(MSG_ERROR, "try rcurly", 10, 8),
            "74: " + getCheckMessage(MSG_ERROR, "catch lcurly", 6, 8),
            "77: " + getCheckMessage(MSG_ERROR, "catch rcurly", 10, 8),
            "80: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 10, 12),
            "86: " + getCheckMessage(MSG_ERROR, "try", 0, 8),
            "87: " + getCheckMessage(MSG_ERROR, "try rcurly", 0, 8),
            "88: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 0, 12),
            "89: " + getCheckMessage(MSG_ERROR, "catch rcurly", 0, 8),
            "91: " + getCheckMessage(MSG_ERROR, "try", 0, 8),
            "92: " + getCheckMessage(MSG_ERROR, "try rcurly", 0, 8),
            "93: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 0, 12),
            "94: " + getCheckMessage(MSG_ERROR, "catch rcurly", 0, 8),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidClassDefWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidClassDefIndent.java");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_ERROR, "class def modifier", 2, 0),
            "28: " + getCheckMessage(MSG_ERROR, "class def lcurly", 2, 0),
            "31: " + getCheckMessage(MSG_ERROR, "class def rcurly", 2, 0),
            "34: " + getCheckMessage(MSG_ERROR, "class def ident", 2, 0),
            "38: " + getCheckMessage(MSG_ERROR, "class def rcurly", 2, 0),
            "43: " + getCheckMessage(MSG_ERROR, "extends", 2, 4),
            "44: " + getCheckMessage(MSG_ERROR, "implements", 2, 4),
            "50: " + getCheckMessage(MSG_ERROR, "extends", 2, 4),
            "58: " + getCheckMessage(MSG_ERROR, "implements", 2, 4),
            "59: " + getCheckMessage(MSG_ERROR, "java", 2, 4),
            "64: " + getCheckMessage(MSG_ERROR, "class def modifier", 2, 0),
            "65: " + getCheckMessage(MSG_ERROR, "class def lcurly", 2, 0),
            "73: " + getCheckMessage(MSG_ERROR, "class def rcurly", 2, 0),
            "77: " + getCheckMessage(MSG_ERROR, "extends", 2, 4),
            "86: " + getCheckMessage(MSG_ERROR, "class def ident", 2, 4),
            "88: " + getCheckMessage(MSG_ERROR, "class def ident", 6, 4),
            "91: " + getCheckMessage(MSG_ERROR, "class def ident", 2, 4),
            "95: " + getCheckMessage(MSG_ERROR, "member def modifier", 6, 8),
            "101: " + getCheckMessage(MSG_ERROR, "int", 10, 12),
            "106: " + getCheckMessage(MSG_ERROR, "member def modifier", 6, 8),
            "111: " + getCheckMessage(MSG_ERROR, "class def rcurly", 6, 4),
            "113: " + getCheckMessage(MSG_ERROR, "class def ident", 6, 4),
            "119: " + getCheckMessage(MSG_ERROR, "class def ident", 6, 8),
            "122: " + getCheckMessage(MSG_ERROR, "class def ident", 10, 8),
            "124: " + getCheckMessage(MSG_ERROR, "class def rcurly", 10, 8),
            "127: " + getCheckMessage(MSG_ERROR, "member def type", 10, 12),
            "132: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 10, 8),
            "133: " + getCheckMessage(MSG_ERROR_MULTI, "object def lcurly", 8, "10, 14"),
            "137: " + getCheckMessage(MSG_ERROR_MULTI, "object def rcurly", 8, "10, 14"),
            "141: " + getCheckMessage(MSG_ERROR_MULTI, "object def lcurly", 6, "8, 12"),
            "142: " + getCheckMessage(MSG_ERROR, "method def modifier", 12, 10),
            "144: " + getCheckMessage(MSG_ERROR, "method def rcurly", 12, 10),
            "145: " + getCheckMessage(MSG_ERROR_MULTI, "object def rcurly", 6, "8, 12"),
            "150: " + getCheckMessage(MSG_ERROR, "method def modifier", 10, 12),
            "152: " + getCheckMessage(MSG_ERROR, "method def rcurly", 10, 12),
            "188: " + getCheckMessage(MSG_ERROR, "class", 0, 4),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidBlockWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidBlockIndent.java");
        final String[] expected = {
            "26: " + getCheckMessage(MSG_ERROR, "block lcurly", 7, 8),
            "27: " + getCheckMessage(MSG_ERROR, "block lcurly", 9, 8),
            "29: " + getCheckMessage(MSG_ERROR, "block lcurly", 9, 8),
            "30: " + getCheckMessage(MSG_ERROR, "block rcurly", 7, 8),
            "32: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 8),
            "34: " + getCheckMessage(MSG_ERROR, "block rcurly", 6, 8),
            "35: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 8),
            "38: " + getCheckMessage(MSG_ERROR, "block lcurly", 9, 8),
            "39: " + getCheckMessage(MSG_CHILD_ERROR, "block", 13, 12),
            "41: " + getCheckMessage(MSG_CHILD_ERROR, "block", 13, 12),
            "42: " + getCheckMessage(MSG_ERROR, "block rcurly", 9, 8),
            "45: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 8),
            "46: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "48: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "49: " + getCheckMessage(MSG_ERROR, "block rcurly", 6, 8),
            "52: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 8),
            "55: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "59: " + getCheckMessage(MSG_ERROR, "block lcurly", 10, 12),
            "63: " + getCheckMessage(MSG_ERROR, "block rcurly", 10, 12),
            "68: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "70: " + getCheckMessage(MSG_ERROR, "block lcurly", 10, 12),
            "71: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "86: " + getCheckMessage(MSG_ERROR, "block rcurly", 10, 12),
            "95: " + getCheckMessage(MSG_ERROR, "static initialization", 2, 4),
            "96: " + getCheckMessage(MSG_ERROR, "static initialization", 6, 4),
            "100: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 7, 8),
            "103: " + getCheckMessage(MSG_ERROR, "static initialization", 6, 4),
            "105: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 2, 4),
            "107: " + getCheckMessage(MSG_ERROR, "static initialization", 2, 4),
            "109: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 6, 4),
            "111: " + getCheckMessage(MSG_ERROR, "static initialization", 2, 4),
            "113: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 6, 8),
            "116: " + getCheckMessage(MSG_ERROR, "static initialization lcurly", 2, 4),
            "117: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 6, 8),
            "118: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 6, 4),
            "123: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 6, 8),
            "128: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 4, 8),
            "129: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 2, 4),
            "134: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 6, 4),
            "137: " + getCheckMessage(MSG_ERROR, "block lcurly", 2, 4),
            "138: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 4),
            "141: " + getCheckMessage(MSG_ERROR, "block lcurly", 2, 4),
            "143: " + getCheckMessage(MSG_ERROR, "block rcurly", 6, 4),
            "145: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 4),
            "147: " + getCheckMessage(MSG_ERROR, "block rcurly", 2, 4),
            "150: " + getCheckMessage(MSG_CHILD_ERROR, "block", 6, 8),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidIfWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidIfIndent.java");
        final String[] expected = {
            "55: " + getCheckMessage(MSG_ERROR, "if", 1, 8),
            "60: " + getCheckMessage(MSG_ERROR, "if", 9, 8),
            "61: " + getCheckMessage(MSG_ERROR, "if lcurly", 9, 8),
            "62: " + getCheckMessage(MSG_ERROR, "if rcurly", 7, 8),
            "64: " + getCheckMessage(MSG_ERROR, "if", 6, 8),
            "65: " + getCheckMessage(MSG_ERROR, "if lcurly", 5, 8),
            "66: " + getCheckMessage(MSG_ERROR, "if rcurly", 5, 8),
            "70: " + getCheckMessage(MSG_ERROR, "if rcurly", 10, 8),
            "71: " + getCheckMessage(MSG_ERROR, "else rcurly", 7, 8),
            "74: " + getCheckMessage(MSG_ERROR, "if", 9, 8),

            "75: " + getCheckMessage(MSG_ERROR, "if lcurly", 7, 8),
            "77: " + getCheckMessage(MSG_ERROR, "else", 9, 8),
            "79: " + getCheckMessage(MSG_ERROR, "else rcurly", 9, 8),
            "82: " + getCheckMessage(MSG_ERROR, "if", 10, 8),
            "83: " + getCheckMessage(MSG_ERROR, "if rcurly", 7, 8),
            "84: " + getCheckMessage(MSG_ERROR, "else", 9, 8),
            "85: " + getCheckMessage(MSG_ERROR, "else lcurly", 7, 8),
            "86: " + getCheckMessage(MSG_ERROR, "else rcurly", 9, 8),

            "90: " + getCheckMessage(MSG_ERROR, "if", 9, 8),
            "91: " + getCheckMessage(MSG_ERROR, "if lcurly", 9, 8),
            "92: " + getCheckMessage(MSG_ERROR, "if rcurly", 9, 8),
            "93: " + getCheckMessage(MSG_ERROR, "else lcurly", 7, 8),
            "94: " + getCheckMessage(MSG_ERROR, "else rcurly", 10, 8),
            "97: " + getCheckMessage(MSG_ERROR, "if", 6, 8),
            "98: " + getCheckMessage(MSG_ERROR, "if lcurly", 10, 8),
            "99: " + getCheckMessage(MSG_ERROR, "if rcurly", 10, 8),
            "100: " + getCheckMessage(MSG_ERROR, "else rcurly", 7, 8),
            "103: " + getCheckMessage(MSG_ERROR, "if", 5, 8),
            "104: " + getCheckMessage(MSG_ERROR, "if rcurly", 11, 8),
            "105: " + getCheckMessage(MSG_ERROR, "else", 5, 8),
            "106: " + getCheckMessage(MSG_ERROR, "else rcurly", 11, 8),

            "126: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),
            "131: " + getCheckMessage(MSG_ERROR, "if lcurly", 10, 8),
            "132: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "137: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),
            "138: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "140: " + getCheckMessage(MSG_CHILD_ERROR, "else", 10, 12),
            "141: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 12),

            "148: " + getCheckMessage(MSG_CHILD_ERROR, "if", 16, 12),
            "149: " + getCheckMessage(MSG_ERROR, "if rcurly", 9, 8),
            "152: " + getCheckMessage(MSG_CHILD_ERROR, "else", 16, 12),
            "158: " + getCheckMessage(MSG_CHILD_ERROR, "if", 0, 12),
            "162: " + getCheckMessage(MSG_CHILD_ERROR, "else", 40, 12),
            "169: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),

            "172: " + getCheckMessage(MSG_CHILD_ERROR, "else", 14, 12),
            "178: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "180: " + getCheckMessage(MSG_CHILD_ERROR, "else", 10, 12),
            "184: " + getCheckMessage(MSG_ERROR, "if", 10, 8),
            "185: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),
            "186: " + getCheckMessage(MSG_ERROR, "if rcurly", 10, 8),
            "187: " + getCheckMessage(MSG_ERROR, "else", 10, 8),

            "188: " + getCheckMessage(MSG_CHILD_ERROR, "else", 14, 12),
            "189: " + getCheckMessage(MSG_ERROR, "else rcurly", 10, 8),
            "192: " + getCheckMessage(MSG_CHILD_ERROR, "if", 9, 12),
            "193: " + getCheckMessage(MSG_CHILD_ERROR, "if", 11, 12),
            "197: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "200: " + getCheckMessage(MSG_ERROR, "if rcurly", 7, 8),
            "207: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "209: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),

            "225: " + getCheckMessage(MSG_ERROR, "if", 10, 12),
            "229: " + getCheckMessage(MSG_CHILD_ERROR, "if", 18, 20),
            "240: " + getCheckMessage(MSG_ERROR, "if rparen", 10, 8),
            "245: " + getCheckMessage(MSG_ERROR, "if rparen", 6, 8),
            "251: " + getCheckMessage(MSG_ERROR, "if lparen", 6, 8),
            "253: " + getCheckMessage(MSG_ERROR, "if rparen", 6, 8),
            "256: " + getCheckMessage(MSG_ERROR, "if", 0, 8),
            "257: " + getCheckMessage(MSG_CHILD_ERROR, "if", 0, 12),
            "258: " + getCheckMessage(MSG_CHILD_ERROR, "if", 0, 12),
            "259: " + getCheckMessage(MSG_ERROR, "if rcurly", 0, 8),
            "260: " + getCheckMessage(MSG_ERROR, "if", 0, 8),
            "262: " + getCheckMessage(MSG_ERROR, "else", 0, 8),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidWhileWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidWhileIndent.java");
        final String[] expected = {
            "25: " + getCheckMessage(MSG_ERROR, "while", 9, 8),
            "26: " + getCheckMessage(MSG_ERROR, "while rcurly", 7, 8),
            "28: " + getCheckMessage(MSG_ERROR, "while", 7, 8),
            "29: " + getCheckMessage(MSG_ERROR, "while lcurly", 9, 8),
            "30: " + getCheckMessage(MSG_ERROR, "while rcurly", 9, 8),

            "32: " + getCheckMessage(MSG_ERROR, "while", 9, 8),
            "33: " + getCheckMessage(MSG_ERROR, "while lcurly", 6, 8),
            "34: " + getCheckMessage(MSG_CHILD_ERROR, "while", 14, 12),
            "35: " + getCheckMessage(MSG_ERROR, "while rcurly", 6, 8),

            "37: " + getCheckMessage(MSG_ERROR, "while", 10, 8),
            "39: " + getCheckMessage(MSG_ERROR, "while rcurly", 10, 8),
            "41: " + getCheckMessage(MSG_ERROR, "while", 10, 8),
            "44: " + getCheckMessage(MSG_ERROR, "while rcurly", 10, 8),

            "46: " + getCheckMessage(MSG_ERROR, "while", 6, 8),
            "47: " + getCheckMessage(MSG_ERROR, "while lcurly", 10, 8),
            "50: " + getCheckMessage(MSG_ERROR, "while rcurly", 6, 8),
            "53: " + getCheckMessage(MSG_ERROR, "if", 14, 12),
            "54: " + getCheckMessage(MSG_CHILD_ERROR, "if", 18, 16),
            "55: " + getCheckMessage(MSG_ERROR, "if rcurly", 14, 12),
            "56: " + getCheckMessage(MSG_CHILD_ERROR, "while", 14, 12),
            "57: " + getCheckMessage(MSG_ERROR, "while rcurly", 10, 8),

            "60: " + getCheckMessage(MSG_CHILD_ERROR, "while", 10, 12),
            "66: " + getCheckMessage(MSG_CHILD_ERROR, "while", 10, 12),
            "71: " + getCheckMessage(MSG_CHILD_ERROR, "while", 10, 12),
            "78: " + getCheckMessage(MSG_ERROR, "while rparen", 5, 8),
            "85: " + getCheckMessage(MSG_ERROR, "while rparen", 10, 8),
            "92: " + getCheckMessage(MSG_ERROR, "while rparen", 10, 8),
            "99: " + getCheckMessage(MSG_CHILD_ERROR, "while", 8, 12),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidInvalidAnonymousClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidAnonymousClassIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidForWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidForIndent.java");
        final String[] expected = {
            "26: " + getCheckMessage(MSG_ERROR, "for", 6, 8),
            "27: " + getCheckMessage(MSG_ERROR, "for rcurly", 10, 8),
            "29: " + getCheckMessage(MSG_ERROR, "for", 9, 8),
            "30: " + getCheckMessage(MSG_ERROR, "for lcurly", 6, 8),
            "31: " + getCheckMessage(MSG_ERROR, "for rcurly", 6, 8),
            "35: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),

            "36: " + getCheckMessage(MSG_ERROR, "for rcurly", 10, 8),
            "39: " + getCheckMessage(MSG_ERROR, "for lcurly", 10, 8),
            "40: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "48: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "54: " + getCheckMessage(MSG_ERROR, "for", 7, 8),

            "55: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "64: " + getCheckMessage(MSG_CHILD_ERROR, "for", 7, 12),

            "69: " + getCheckMessage(MSG_ERROR, "for", 6, 8),
            "70: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "71: " + getCheckMessage(MSG_CHILD_ERROR, "for", 14, 16),
            "72: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "81: " + getCheckMessage(MSG_ERROR, "for rparen", 12, 8),
            "86: " + getCheckMessage(MSG_ERROR, "method def modifier", 2, 4),
            "87: " + getCheckMessage(MSG_ERROR, "for", 4, 8),
            "88: " + getCheckMessage(MSG_CHILD_ERROR, "for", 8, 12),
            "89: " + getCheckMessage(MSG_CHILD_ERROR, "for", 6, 12),
            "90: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 16),
            "92: " + getCheckMessage(MSG_ERROR, "for", 0, 8),
            "93: " + getCheckMessage(MSG_ERROR, "for lparen", 0, 8),
            "94: " + getCheckMessage(MSG_CHILD_ERROR, "for", 0, 12),
            "95: " + getCheckMessage(MSG_ERROR, ";", 0, 4),
            "96: " + getCheckMessage(MSG_CHILD_ERROR, "for", 0, 12),
            "97: " + getCheckMessage(MSG_ERROR, ";", 0, 4),
            "98: " + getCheckMessage(MSG_CHILD_ERROR, "for", 0, 12),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidForWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidForIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidDoWhileWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidDoWhileIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidDoWhileWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationInvalidDoWhileIndent.java");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_ERROR, "do..while", 0, 8),
            "8: " + getCheckMessage(MSG_ERROR, "do..while", 0, 8),
            "9: " + getCheckMessage(MSG_ERROR, "do..while", 0, 8),
            "10: " + getCheckMessage(MSG_ERROR, "do..while rcurly", 0, 8),
            "11: " + getCheckMessage(MSG_ERROR, "do..while", 0, 8),
            "12: " + getCheckMessage(MSG_ERROR, "do..while while", 0, 8),
            "13: " + getCheckMessage(MSG_ERROR, "do..while", 0, 8),
            "14: " + getCheckMessage(MSG_ERROR, "do..while lcurly", 0, 8),
            "15: " + getCheckMessage(MSG_ERROR, "do..while", 0, 8),
            "16: " + getCheckMessage(MSG_ERROR, "do..while while", 0, 8),
            "17: " + getCheckMessage(MSG_ERROR, "do..while lparen", 0, 8),
            "18: " + getCheckMessage(MSG_ERROR, "do..while", 0, 8),
            "19: " + getCheckMessage(MSG_ERROR, "do..while lparen", 0, 8),
            "20: " + getCheckMessage(MSG_ERROR, "do..while", 0, 8),
            "21: " + getCheckMessage(MSG_ERROR, "do..while lparen", 0, 8),
            "22: " + getCheckMessage(MSG_CHILD_ERROR, "do..while", 0, 8),
            "23: " + getCheckMessage(MSG_ERROR, "do..while rparen", 0, 8),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidBlockWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidBlockIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidWhileWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidWhileIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidClassDefWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidClassDefIndent.java");
        final String[] expected = {
            "49: " + getCheckMessage(MSG_ERROR, "class", 0, 4),
            "71: " + getCheckMessage(MSG_ERROR, "int", 8, 12),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidInterfaceDefWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidInterfaceDefIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidCommaWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputIndentationValidCommaIndent.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testTabs() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "29: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 9, 8),
        };
        verifyWarns(checkConfig, getPath("InputIndentationUseTabs.java"), expected);
    }

    @Test
    public void testIndentationLevel() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "2");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "29: " + getCheckMessage(MSG_CHILD_ERROR, "ctor def", 5, 4),
        };
        verifyWarns(checkConfig, getPath("InputIndentationUseTwoSpaces.java"), expected);
    }

    @Test
    public void testThrowsIndentationLevel() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationInvalidThrowsIndent.java"), expected);
    }

    @Test
    public void testThrowsIndentationLevel2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("basicOffset", "1");
        checkConfig.addAttribute("forceStrictCondition", "true");
        checkConfig.addAttribute("lineWrappingIndentation", "3");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "5");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_ERROR, "Exception", 0, 6),
            "10: " + getCheckMessage(MSG_ERROR, "NullPointerException", 0, 6),
            "13: " + getCheckMessage(MSG_ERROR, "throws", 0, 6),
            "16: " + getCheckMessage(MSG_ERROR, "throws", 0, 6),
            "18: " + getCheckMessage(MSG_ERROR, "throws", 0, 6),
            "19: " + getCheckMessage(MSG_ERROR, "Exception", 0, 6),
            "22: " + getCheckMessage(MSG_ERROR, "throws", 0, 6),
            "23: " + getCheckMessage(MSG_ERROR, "Exception", 0, 6),
            "24: " + getCheckMessage(MSG_ERROR, "NullPointerException", 0, 6),
            "27: " + getCheckMessage(MSG_ERROR, "throws", 0, 6),
            "28: " + getCheckMessage(MSG_ERROR, "Exception", 0, 6),
            "31: " + getCheckMessage(MSG_ERROR, "throws", 0, 6),
            "37: " + getCheckMessage(MSG_ERROR, "throws", 0, 6),
        };
        verifyWarns(checkConfig, getPath("InputIndentationInvalidThrowsIndent2.java"), expected);
    }

    @Test
    public void testCaseLevel() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "0");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "27: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 8),
        };
        verifyWarns(checkConfig, getPath("InputIndentationCaseLevel.java"), expected);
    }

    @Test
    public void testBraceAdjustment() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "2");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "28: " + getCheckMessage(MSG_ERROR, "if rcurly", 8, 10),
        };
        verifyWarns(checkConfig, getPath("InputIndentationBraceAdjustment.java"), expected);
    }

    @Test
    public void testInvalidAssignWithChecker() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_ERROR, "getLineNo", 10, 12),
            "24: " + getCheckMessage(MSG_ERROR, "getLine", 10, 12),
            "28: " + getCheckMessage(MSG_ERROR, "=", 9, 12),
            "29: " + getCheckMessage(MSG_ERROR, "1", 10, 12),
        };
        verifyWarns(checkConfig, getPath("InputIndentationInvalidAssignIndent.java"), expected);
    }

    @Test
    public void testInvalidImportIndent() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "8");
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_ERROR, ".", 2, 4),
            "5: " + getCheckMessage(MSG_ERROR, "import", 1, 0),
        };
        verifyWarns(checkConfig, getPath("InputIndentationInvalidImportIndent.java"), expected);
    }

    @Test
    public void testValidAssignWithChecker() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationValidAssignIndent.java"), expected);
    }

    @Test
    public void test15Extensions() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentation15Extensions.java"), expected);
    }

    @Test
    public void testTryResources() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationValidTryResourcesIndent.java"),
               expected);
    }

    @Test
    public void testSwitchCustom() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationSwitchCustom.java"),
               expected);
    }

    @Test
    public void testSynchronizedStatement() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = {
            "27: " + getCheckMessage(MSG_CHILD_ERROR, "synchronized", 0, 12),
            "30: " + getCheckMessage(MSG_ERROR, "synchronized lparen", 12, 8),
        };
        verifyWarns(checkConfig, getPath("InputIndentationSynchronizedStatement.java"), expected);
    }

    @Test
    public void testSynchronizedMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationSynchronizedMethod.java"), expected);
    }

    @Test
    public void testAnonymousClassInMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "8");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "2");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        checkConfig.addAttribute("arrayInitIndent", "2");
        final String[] expected = {
            "19: " + getCheckMessage(MSG_ERROR, "method def modifier", 8, 2),
            "20: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 16, 4),
            "21: " + getCheckMessage(MSG_ERROR_MULTI, "method def modifier", 24, "18, 20, 22"),
            "23: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "method def", 32, "20, 22, 24"),
            "24: " + getCheckMessage(MSG_ERROR_MULTI, "method def rcurly", 24, "18, 20, 22"),
            "26: " + getCheckMessage(MSG_ERROR, "method def rcurly", 8, 2),
        };
        verifyWarns(checkConfig, getPath("InputIndentationAnonymousClassInMethod.java"), expected);
    }

    @Test
    public void testAnonymousClassInMethodWithCurlyOnNewLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("throwsIndent", "4");
        checkConfig.addAttribute("arrayInitIndent", "4");
        final String[] expected = {
            "40: " + getCheckMessage(MSG_ERROR_MULTI, "object def rcurly", 18, "8, 12, 16"),
            "42: " + getCheckMessage(MSG_ERROR, "new", 14, 16),
            "48: " + getCheckMessage(MSG_ERROR_MULTI, "object def rcurly", 14, "8, 12, 16"),
            "60: " + getCheckMessage(MSG_ERROR_MULTI, "object def lcurly", 18, "8, 12, 16"),
            "66: " + getCheckMessage(MSG_ERROR_MULTI, "object def rcurly", 18, "8, 12, 16"),
            "69: " + getCheckMessage(MSG_ERROR_MULTI, "object def lcurly", 14, "8, 12, 16"),
            "75: " + getCheckMessage(MSG_ERROR_MULTI, "object def rcurly", 14, "8, 12, 16"),
        };
        verifyWarns(checkConfig,
            getPath("InputIndentationAnonymousClassInMethodCurlyOnNewLine.java"), expected);
    }

    @Test
    public void testAnnotationDefinition() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationAnnotationDefinition.java"), expected);
    }

    @Test
    public void testPackageDeclaration() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_ERROR, "package def", 1, 0),
        };
        verifyWarns(checkConfig, getPath("InputIndentationPackageDeclaration.java"), expected);
    }

    @Test
    public void testPackageDeclaration2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = {
            "2: " + getCheckMessage(MSG_ERROR, "package def", 1, 0),
        };
        verifyWarns(checkConfig,
            getPath("package-info.java"), expected);
    }

    @Test
    public void testPackageDeclaration3() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationPackageDeclaration3.java"), expected);
    }

    @Test
    public void testPackageDeclaration4() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = {
            "2: " + getCheckMessage(MSG_ERROR, "com", 0, 4),
            "3: " + getCheckMessage(MSG_ERROR, "checks", 0, 4),
        };
        verifyWarns(checkConfig, getPath("InputIndentationPackageDeclaration4.java"), expected);
    }

    @Test
    public void testLambda1() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "2");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String[] expected = {
            "46: " + getCheckMessage(MSG_ERROR, "block lcurly", 5, 4),
            "47: " + getCheckMessage(MSG_ERROR, "block rcurly", 5, 4),
            "50: " + getCheckMessage(MSG_ERROR, "lambda arguments", 9, 8),
            "51: " + getCheckMessage(MSG_ERROR, "lambda", 11, 12),
            "52: " + getCheckMessage(MSG_ERROR, "block lcurly", 9, 8),
            "64: " + getCheckMessage(MSG_CHILD_ERROR, "block", 7, 6),
            "65: " + getCheckMessage(MSG_ERROR, "block rcurly", 5, 4),
            "179: " + getCheckMessage(MSG_CHILD_ERROR, "block", 9, 10),
            "180: " + getCheckMessage(MSG_CHILD_ERROR, "block", 11, 10),
            "185: " + getCheckMessage(MSG_ERROR, "block rcurly", 7, 8),
        };
        verifyWarns(checkConfig, getPath("InputIndentationLambda1.java"), expected);
    }

    @Test
    public void testLambda2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationLambda2.java"), expected);
    }

    @Test
    public void testLambda3() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        final String[] expected = {
            "15: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 12, 8),
            "23: " + getCheckMessage(MSG_ERROR_MULTI, "lambda arguments", 20, "12, 16"),
            "29: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 12, 8),
            "30: " + getCheckMessage(MSG_CHILD_ERROR, "block", 12, 16),
            "31: " + getCheckMessage(MSG_ERROR, "block rcurly", 8, 12),
            "43: " + getCheckMessage(MSG_ERROR, "lambda arguments", 20, 16),
            "65: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 12, 8),
            "67: " + getCheckMessage(MSG_ERROR, "lambda", 28, 24),
            "87: " + getCheckMessage(MSG_ERROR, "method def rcurly", 12, 8),
        };
        verifyWarns(checkConfig, getPath("InputIndentationLambda3.java"), expected);
    }

    @Test
    public void testLambda4() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationLambda4.java"), expected);
    }

    @Test
    public void testLambda5() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "3");
        checkConfig.addAttribute("basicOffset", "3");
        checkConfig.addAttribute("caseIndent", "0");
        checkConfig.addAttribute("lineWrappingIndentation", "6");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationLambda5.java"), expected);
    }

    @Test
    public void testSeparatedStatements() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String fileName = getPath("InputIndentationSeparatedStatements.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testSeparatedLineWithJustSpaces() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String fileName = getPath("InputIndentationSeparatedStatementWithSpaces.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, fileName, expected);
    }

    @Test
    public void testTwoStatementsPerLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "4");
        final String fileName = getPath("InputIndentationTwoStatementsPerLine.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testMethodChaining() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "4");
        final String fileName = getPath("InputIndentationChainedMethods.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testMultipleAnnotationsWithWrappedLines() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("forceStrictCondition", "true");
        final String fileName =
            getPath("InputIndentationCorrectMultipleAnnotationsWithWrappedLines.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testMethodPrecedeByAnnotationsWithParameterOnSeparateLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "2");
        checkConfig.addAttribute("throwsIndent", "4");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("arrayInitIndent", "2");
        final String fileName =
            getPath("InputIndentationMethodPrecededByAnnotationWithParameterOnSeparateLine.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, fileName, expected);
    }

    @Test
    public void testAnnotationIncorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String fileName =
            getPath("InputIndentationAnnotationIncorrect.java");
        final String[] expected = {
            "11: " + getCheckMessage(MSG_ERROR, "(", 4, 8),
            "14: " + getCheckMessage(MSG_ERROR, "(", 8, 12),
            "19: " + getCheckMessage(MSG_ERROR, "(", 4, 8),
        };
        verify(checkConfig, fileName, expected);
    }

    @Test
    public void testInputAnnotationScopeIndentationCheck() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("forceStrictCondition", "true");
        final String fileName = getPath("InputIndentationAnnotationScopeIndentationCheck.java");
        final String[] expected = {
            "9: " + getCheckMessage(MSG_ERROR, "}", 8, 0),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInputAnnotationDefIndentationCheck() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("forceStrictCondition", "true");
        final String fileName = getPath("InputIndentationCustomAnnotation.java");
        final String[] expected = {
            "14: " + getCheckMessage(MSG_ERROR, "annotation def modifier", 5, 0),
            "15: " + getCheckMessage(MSG_ERROR, "annotation def rcurly", 5, 0),
            "16: " + getCheckMessage(MSG_ERROR, "@", 5, 0),
            "17: " + getCheckMessage(MSG_ERROR, "annotation field def modifier", 0, 4),
            "18: " + getCheckMessage(MSG_ERROR, "annotation def rcurly", 5, 0),
            "20: " + getCheckMessage(MSG_ERROR, "annotation def modifier", 3, 0),
            "22: " + getCheckMessage(MSG_ERROR, "annotation field def modifier", 0, 4),
            "23: " + getCheckMessage(MSG_ERROR, "annotation def rcurly", 5, 0),
            "25: " + getCheckMessage(MSG_ERROR, "@", 5, 0),
            "26: " + getCheckMessage(MSG_ERROR, "AnnotationWithLineWrap", 5, 0),
            "30: " + getCheckMessage(MSG_ERROR, "annotation def modifier", 5, 0),
            "31: " + getCheckMessage(MSG_ERROR, "annotation def modifier", 3, 0),
            "34: " + getCheckMessage(MSG_ERROR, "annotation field def modifier", 5, 4),
            "35: " + getCheckMessage(MSG_ERROR, "annotation def modifier", 3, 4),
            "36: " + getCheckMessage(MSG_ERROR, "annotation def modifier", 0, 4),
            "37: " + getCheckMessage(MSG_ERROR, "@", 0, 4),
            "38: " + getCheckMessage(MSG_ERROR, "AnnotationInnerLineWrap", 8, 4),
            "41: " + getCheckMessage(MSG_ERROR, "annotation field def modifier", 7, 8),
            "58: " + getCheckMessage(MSG_ERROR, "AnnotationInnerLineWrap2", 4, 0),
            "59: " + getCheckMessage(MSG_ERROR, "annotation field def modifier", 3, 4),
            "60: " + getCheckMessage(MSG_ERROR, "annotation field def modifier", 7, 4),
            "61: " + getCheckMessage(MSG_ERROR, "annotation def rcurly", 4, 0),
            "72: " + getCheckMessage(MSG_ERROR, "annotation def modifier", 3, 4),
            "117: " + getCheckMessage(MSG_ERROR, "annotation def modifier", 5, 4),
            "128: " + getCheckMessage(MSG_ERROR, "interface", 1, 0),
            "134: " + getCheckMessage(MSG_ERROR, "@", 11, 0),
            "137: " + getCheckMessage(MSG_ERROR, "@", 16, 0),
            "144: " + getCheckMessage(MSG_ERROR, "@", 12, 4),
            "148: " + getCheckMessage(MSG_ERROR, "class def ident", 16, 0),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testTryResourcesStrict() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("forceStrictCondition", "true");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String fileName = getPath("InputIndentationTryWithResourcesStrict.java");
        final String[] expected = {
            "26: " + getCheckMessage(MSG_ERROR, "try resource", 0, 12),
            "28: " + getCheckMessage(MSG_ERROR_MULTI, "try rparen", 13, "8, 12"),
            "33: " + getCheckMessage(MSG_ERROR, "newBufferedWriter", 0, 16),
            "39: " + getCheckMessage(MSG_ERROR, "newBufferedWriter", 0, 12),
            "59: " + getCheckMessage(MSG_ERROR, "try resource", 20, 16),
            "84: " + getCheckMessage(MSG_ERROR, "writ", 19, 12),
            "91: " + getCheckMessage(MSG_ERROR, "writ", 19, 16),
            "98: " + getCheckMessage(MSG_ERROR, "writ", 21, 16),
            "113: " + getCheckMessage(MSG_ERROR, "zipFileName", 17, 16),
            "120: " + getCheckMessage(MSG_ERROR, "zipFileName", 15, 16),
            "130: " + getCheckMessage(MSG_ERROR, "try", 7, 8),
            "135: " + getCheckMessage(MSG_CHILD_ERROR, "try", 15, 12),
            "141: " + getCheckMessage(MSG_ERROR, "try resource", 11, 12),
            "142: " + getCheckMessage(MSG_CHILD_ERROR, "try", 9, 12),
            "146: " + getCheckMessage(MSG_ERROR, "try resource", 11, 12),
            "147: " + getCheckMessage(MSG_ERROR, "newBufferedWriter", 11, 16),
            "148: " + getCheckMessage(MSG_CHILD_ERROR, "try", 13, 12),
            "150: " + getCheckMessage(MSG_ERROR, "try", 7, 8),
            "151: " + getCheckMessage(MSG_ERROR_MULTI, "try rparen", 7, "8, 12"),
            "155: " + getCheckMessage(MSG_ERROR, "try", 9, 8),
            "161: " + getCheckMessage(MSG_ERROR, ".", 13, 12),
            "167: " + getCheckMessage(MSG_ERROR, ".", 11, 12),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testTryResourcesNotStrict() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        final String fileName = getPath("InputIndentationTryResourcesNotStrict.java");
        final String[] expected = {
            "27: " + getCheckMessage(MSG_ERROR, "try resource", 0, 12),
            "33: " + getCheckMessage(MSG_ERROR, "newBufferedWriter", 0, 16),
            "39: " + getCheckMessage(MSG_ERROR, "newBufferedWriter", 0, 12),
            "120: " + getCheckMessage(MSG_ERROR, "zipFileName", 15, 16),
            "130: " + getCheckMessage(MSG_ERROR, "try", 7, 8),
            "135: " + getCheckMessage(MSG_CHILD_ERROR, "try", 15, 12),
            "141: " + getCheckMessage(MSG_ERROR, "try resource", 11, 12),
            "142: " + getCheckMessage(MSG_CHILD_ERROR, "try", 9, 12),
            "146: " + getCheckMessage(MSG_ERROR, "try resource", 11, 12),
            "147: " + getCheckMessage(MSG_ERROR, "newBufferedWriter", 11, 16),
            "148: " + getCheckMessage(MSG_CHILD_ERROR, "try", 13, 12),
            "150: " + getCheckMessage(MSG_ERROR, "try", 7, 8),
            "151: " + getCheckMessage(MSG_ERROR_MULTI, "try rparen", 7, "8, 12"),
            "164: " + getCheckMessage(MSG_ERROR, ".", 8, 12),
            "172: " + getCheckMessage(MSG_ERROR, "new", 11, 12),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    /**
     * Verifies that the arguments of {@link IndentationCheck#MSG_ERROR},
     * {@link IndentationCheck#MSG_CHILD_ERROR}, {@link IndentationCheck#MSG_CHILD_ERROR_MULTI},
     * {@link IndentationCheck#MSG_CHILD_ERROR_MULTI} are in appropriate order.
     *
     * <p>In other tests, the argument 0 and text before it are chopped off and only the rest of
     * messages are verified. Therefore, the argument 0 is required to be the first argument in
     * the messages above. If we update the messages in the future, it is required to keep the
     * arguments in appropriate order to ensure other tests will work.</p>
     *
     * @see IndentComment#getExpectedMessagePostfix(String)
     */
    @Test
    public void testArgumentOrderOfErrorMessages() {
        final Object[] arguments = {"##0##", "##1##", "##2##"};
        final String[] messages = {
            getCheckMessage(MSG_ERROR, arguments),
            getCheckMessage(MSG_CHILD_ERROR, arguments),
            getCheckMessage(MSG_ERROR_MULTI, arguments),
            getCheckMessage(MSG_CHILD_ERROR_MULTI, arguments),
        };
        final boolean isInOrder = Arrays.stream(messages).allMatch(msg -> {
            final int indexOfArgumentZero = msg.indexOf((String) arguments[0]);
            return Arrays.stream(arguments)
                    .map(String.class::cast)
                    .mapToInt(msg::indexOf)
                    .allMatch(index -> index >= indexOfArgumentZero);
        });
        assertTrue(isInOrder,
                "the argument 0 of error messages (indentation.error, indentation.child.error,"
                        + " indentation.error.multi, indentation.child.error.multi)"
                        + " is required to be the first argument of them");
    }

    @Test
    public void testEmptyArray() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationEmptyArray.java"), expected);
    }

    @Test
    public void testNewHandler() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = {
            "8: " + getCheckMessage(MSG_ERROR, "Object", 0, 12),
            "10: " + getCheckMessage(MSG_ERROR, "(", 0, 12),
            "13: " + getCheckMessage(MSG_CHILD_ERROR, "operator new", 0, 8),
            "15: " + getCheckMessage(MSG_ERROR, "operator new lparen", 0, 8),
        };
        verifyWarns(checkConfig, getPath("InputIndentationNewHandler.java"), expected);
    }

    @Test
    public void testChainedMethodWithBracketOnNewLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "2");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "2");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "2");
        checkConfig.addAttribute("throwsIndent", "2");
        final String[] expected = {
            "44: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 6, 8),
            "45: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 10),
            "47: " + getCheckMessage(MSG_ERROR, "method call rparen", 6, 8),
            "61: " + getCheckMessage(MSG_ERROR, "foo", 5, 8),
            "82: " + getCheckMessage(MSG_ERROR, "if rcurly", 4, 6),
            "84: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 2, 4),
        };
        final String fileName = "InputIndentationChainedMethodWithBracketOnNewLine.java";
        verifyWarns(checkConfig, getPath(fileName), expected);
    }

    private static final class IndentAudit implements AuditListener {

        private final IndentComment[] comments;
        private int position;

        /* package */ IndentAudit(IndentComment... comments) {
            this.comments = Arrays.copyOf(comments, comments.length);
        }

        @Override
        public void auditStarted(AuditEvent event) {
            // No code needed
        }

        @Override
        public void auditFinished(AuditEvent event) {
            // No code needed
        }

        @Override
        public void fileStarted(AuditEvent event) {
            // No code needed
        }

        @Override
        public void fileFinished(AuditEvent event) {
            // No code needed
        }

        @Override
        public void addError(AuditEvent event) {
            final int line = event.getLine();
            final String message = event.getMessage();

            if (position >= comments.length) {
                fail("found a warning when none was expected for #" + position + " at line " + line
                        + " with message " + message);
            }

            final IndentComment comment = comments[position];
            position++;

            final String possibleExceptedMessages = Arrays.stream(comment.getExpectedMessages())
                    .reduce("", (cur, next) -> cur + "\"" + next + "\", ");
            final String assertMessage = String.format(
                    Locale.ROOT,
                    "input expected warning #%d at line %d to report one of the following: %s"
                            + "but got instead: %d: %s",
                    position, comment.getLineNumber(), possibleExceptedMessages, line, message);
            assertTrue(line == comment.getLineNumber()
                    && Arrays.stream(comment.getExpectedMessages()).anyMatch(message::endsWith),
                    assertMessage);
        }

        @Override
        public void addException(AuditEvent event, Throwable throwable) {
            // No code needed
        }

    }

    private static final class IndentComment {

        /** Used to locate the index of argument zero of error messages. */
        private static final String FAKE_ARGUMENT_ZERO = "##0##";
        private final int lineNumber;
        private final int indent;
        /** Used for when violations report nodes not first on the line. */
        private final int indentOffset;
        private final boolean expectedNonStrict;
        private final String expectedWarning;
        private final boolean warning;

        /* package */ IndentComment(Matcher match, int lineNumber) {
            this.lineNumber = lineNumber;
            indent = Integer.parseInt(match.group(1));
            if (match.group(2) == null) {
                indentOffset = 0;
            }
            else {
                indentOffset = Integer.parseInt(match.group(2));
            }
            expectedNonStrict = match.group(3) != null;
            expectedWarning = match.group(4).replace(",", ", ");
            warning = match.group(5) != null;
        }

        public String[] getExpectedMessages() {
            final String[] expectedMessages;
            if (expectedWarning.contains(",")) {
                expectedMessages = new String[] {
                    getExpectedMessagePostfix(MSG_ERROR_MULTI),
                    getExpectedMessagePostfix(MSG_CHILD_ERROR_MULTI),
                };
            }
            else {
                expectedMessages = new String[] {
                    getExpectedMessagePostfix(MSG_ERROR),
                    getExpectedMessagePostfix(MSG_CHILD_ERROR),
                };
            }
            return expectedMessages;
        }

        private String getExpectedMessagePostfix(final String messageKey) {
            final String msg = getCheckMessage(IndentationCheck.class, messageKey,
                    FAKE_ARGUMENT_ZERO, indent + indentOffset, expectedWarning);
            final int indexOfMsgPostfix = msg.indexOf(FAKE_ARGUMENT_ZERO)
                    + FAKE_ARGUMENT_ZERO.length();
            return msg.substring(indexOfMsgPostfix);
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public int getIndent() {
            return indent;
        }

        public int getIndentOffset() {
            return indentOffset;
        }

        public boolean isExpectedNonStrict() {
            return expectedNonStrict;
        }

        public String getExpectedWarning() {
            return expectedWarning;
        }

        public boolean isWarning() {
            return warning;
        }

    }

}
