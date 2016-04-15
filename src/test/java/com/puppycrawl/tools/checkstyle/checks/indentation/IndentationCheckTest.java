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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_CHILD_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_CHILD_ERROR_MULTI;
import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_ERROR_MULTI;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Unit test for IndentationCheck.
 * @author  jrichard
 */
public class IndentationCheckTest extends BaseCheckTestSupport {
    private static final Pattern LINE_WITH_COMMENT_REGEX =
                    Pattern.compile(".*?//indent:(\\d+)(?: ioffset:(\\d+))?"
                        + " exp:(>=)?(\\d+(?:,\\d+)*?)( warn)?$");

    private static IndentComment[] getLinesWithWarnAndCheckComments(String aFileName,
            final int tabWidth)
                    throws IOException {
        final List<IndentComment> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(aFileName), StandardCharsets.UTF_8))) {
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
        return result.toArray(new IndentComment[result.size()]);
    }

    private static boolean isCommentConsistent(IndentComment comment) {
        final String[] levels = comment.getExpectedWarning().split(", ");
        final int indent = comment.getIndent() + comment.getIndentOffset();

        if (levels.length > 1) {
            // multi
            final boolean containsActualLevel =
                            Arrays.asList(levels).contains(String.valueOf(indent));

            return containsActualLevel != comment.isWarning();
        }
        else {
            final int expectedWarning = Integer.parseInt(comment.getExpectedWarning());

            if (comment.isExpectedNonStrict()) {
                // non-strict
                final boolean test = indent >= expectedWarning;
                return test != comment.isWarning();
            }
            else {
                // single
                final boolean test = expectedWarning == indent;
                return test != comment.isWarning();
            }
        }
    }

    private static int getLineStart(String line, final int tabWidth) {
        for (int index = 0; index < line.length(); ++index) {
            if (!Character.isWhitespace(line.charAt(index))) {
                return CommonUtils.lengthExpandedTabs(line, index, tabWidth);
            }
        }
        return 0;
    }

    private void verifyWarns(Configuration config, String filePath,
                    String... expected)
                    throws Exception {
        verifyWarns(config, filePath, expected, 0);
    }

    private void verifyWarns(Configuration config, String filePath,
                    String[] expected, int warnCountCorrection)
                    throws Exception {
        final int tabWidth = Integer.parseInt(config.getAttribute("tabWidth"));
        final IndentComment[] linesWithWarn =
                        getLinesWithWarnAndCheckComments(filePath, tabWidth);
        assertEquals("Expected warning count in UT does not match warn"
                        + " comment count in input file", linesWithWarn.length
                        + warnCountCorrection,
                        expected.length);
        verify(config, filePath, expected, linesWithWarn);
    }

    private void verify(Configuration config, String filePath, String[] expected,
            final IndentComment... linesWithWarn) throws Exception {
        final Checker checker = createChecker(config);
        checker.addListener(new IndentAudit(linesWithWarn));
        verify(checker, new File[] {new File(filePath)}, filePath, expected);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "indentation" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "indentation" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final IndentationCheck checkObj = new IndentationCheck();
        final HandlerFactory handlerFactory = new HandlerFactory();
        final int[] expected = handlerFactory.getHandledTypes();
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        final IndentationCheck checkObj = new IndentationCheck();
        final HandlerFactory handlerFactory = new HandlerFactory();
        final int[] expected = handlerFactory.getHandledTypes();
        assertArrayEquals(expected, checkObj.getAcceptableTokens());
    }

    @Test
    public void testThrowsIndentProperty() {
        final IndentationCheck indentationCheck = new IndentationCheck();

        indentationCheck.setThrowsIndent(1);

        assertEquals(1, indentationCheck.getThrowsIndent());
    }

    @Test
    public void forbidCStyle() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
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
        verifyWarns(checkConfig, getPath("InputMethodCStyle.java"), expected);
    }

    @Test
    public void testZeroCaseLevel() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "0");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputZeroCaseLevel.java"), expected);
    }

    @Test
    public void testAndroidStyle() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
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
        verifyWarns(checkConfig, getPath("InputAndroidStyle.java"), expected);
    }

    @Test
    public void testMethodCallLineWrap() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {
            "51: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 18, 20),
            "52: " + getCheckMessage(MSG_ERROR, "method call rparen", 14, 16),
        };
        verifyWarns(checkConfig, getPath("InputMethodCallLineWrap.java"), expected);
    }

    @Test
    public void testDifficultAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

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
        verifyWarns(checkConfig, getPath("InputDifficultAnnotations.java"), expected);
    }

    @Test
    public void testAnnotationClosingParenthesisEndsInSameIndentationAsOpening() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("forceStrictCondition", "true");
        checkConfig.addAttribute("tabWidth", "4");

        final String[] expected = {
            "19: " + getCheckMessage(MSG_ERROR, ")", 16, 0),
            "22: " + getCheckMessage(MSG_ERROR, ")",  8, 4),
        };

        verifyWarns(checkConfig,
                getPath("InputAnnotationClosingParenthesisEndsInSameIndentationAsOpening.java"),
                expected);
    }

    @Test
    public void testAnonClassesFromGuava() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputFromGuava2.java"), expected);
    }

    @Test
    public void testAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputFromGuava.java"), expected);
    }

    @Test
    public void testCorrectIfAndParameters() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputIndentationCorrectIfAndParameter.java"), expected);
    }

    @Test
    public void testAnonymousClasses() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputAnonymousClasses.java"), expected);
    }

    @Test
    public void testArrays() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "2");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputArrays.java"), expected);
    }

    @Test
    public void testLabels() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputLabels.java"), expected);
    }

    @Test
    public void testClassesAndMethods() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputClassesMethods.java"), expected);
    }

    @Test
    public void testMembers() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

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

        verifyWarns(checkConfig, getPath("InputMembers.java"), expected);
    }

    @Test
    public void testInvalidLabel() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

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
        verifyWarns(checkConfig, getPath("InputInvalidLabelIndent.java"), expected);
    }

    @Test
    public void testInvalidLabelWithWhileLoop() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

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
        verifyWarns(checkConfig, getPath("InputInvalidLabelWithWhileLoopIndent.java"),
            expected, -1);
    }

    @Test
    public void testValidLabel() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputValidLabelIndent.java"), expected);
    }

    @Test
    public void testValidIfWithChecker() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidIfIndent.java");
        final String[] expected = {
            "231: " + getCheckMessage(MSG_ERROR, "(", 8, 12),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidDotWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidDotIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidMethodWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidMethodIndent.java");
        final String[] expected = {
            "129: " + getCheckMessage(MSG_ERROR, "void", 4, 8),
            "130: " + getCheckMessage(MSG_ERROR, "method5", 4, 8),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidMethodWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputInvalidMethodIndent.java");
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
        verifyWarns(checkConfig, fileName, expected, 0);
    }

    @Test
    public void testInvalidSwitchWithChecker()
        throws Exception {

        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputInvalidSwitchIndent.java");
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
        };
        verifyWarns(checkConfig, fileName, expected, 0);
    }

    @Test
    public void testValidSwitchWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidSwitchIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidArrayInitDefaultIndentWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidArrayInitDefaultIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidArrayInitWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "8");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidArrayInitIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidArrayInitWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputInvalidArrayInitIndent.java");
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

        //Test input for this test case is not checked due to issue #693.
        verify(checkConfig, fileName, expected);
    }

    @Test
    public void testValidTryWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidTryIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidTryWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputInvalidTryIndent.java");
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
        };
        verifyWarns(checkConfig, fileName, expected, 0);
    }

    @Test
    public void testInvalidClassDefWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputInvalidClassDefIndent.java");
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
            "137: " + getCheckMessage(MSG_ERROR, "}", 8, 10),
            "141: " + getCheckMessage(MSG_ERROR_MULTI, "object def lcurly", 6, "8, 12"),
            "142: " + getCheckMessage(MSG_ERROR, "method def modifier", 12, 10),
            "144: " + getCheckMessage(MSG_ERROR, "method def rcurly", 12, 10),
            "145: " + getCheckMessage(MSG_ERROR, "}", 6, 8),
            "150: " + getCheckMessage(MSG_ERROR, "method def modifier", 10, 12),
            "152: " + getCheckMessage(MSG_ERROR, "method def rcurly", 10, 12),
            "188: " + getCheckMessage(MSG_ERROR, "class", 0, 4),
        };
        verifyWarns(checkConfig, fileName, expected, 0);
    }

    @Test
    public void testInvalidBlockWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputInvalidBlockIndent.java");
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
        verifyWarns(checkConfig, fileName, expected, 0);
    }

    @Test
    public void testInvalidIfWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputInvalidIfIndent.java");
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
            "233: " + getCheckMessage(MSG_ERROR, "if rcurly", 40, 8),
            "240: " + getCheckMessage(MSG_ERROR, "if rparen", 10, 8),
            "245: " + getCheckMessage(MSG_ERROR, "if rparen", 6, 8),
            "251: " + getCheckMessage(MSG_ERROR, "if lparen", 6, 8),
            "253: " + getCheckMessage(MSG_ERROR, "if rparen", 6, 8),
        };
        verifyWarns(checkConfig, fileName, expected, 0);
    }

    @Test
    public void testInvalidWhileWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputInvalidWhileIndent.java");
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
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputInvalidAnonymousClassIndent.java");
        final String[] expected = {
            "28: " + getCheckMessage(MSG_ERROR_MULTI, "method def rcurly", 17, "12, 16"),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testInvalidForWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputInvalidForIndent.java");
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
            "77: " + getCheckMessage(MSG_ERROR, "for rcurly", 39, 8),
            "81: " + getCheckMessage(MSG_ERROR, "for rparen", 12, 8),
        };
        verifyWarns(checkConfig, fileName, expected, 0);
    }

    @Test
    public void testValidForWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidForIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidDoWhileWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidDoWhileIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidBlockWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidBlockIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidWhileWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidWhileIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidClassDefWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidClassDefIndent.java");
        final String[] expected = {
            "49: " + getCheckMessage(MSG_ERROR, "class", 0, 4),
            "71: " + getCheckMessage(MSG_ERROR, "int", 8, 12),
        };
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidInterfaceDefWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidInterfaceDefIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testValidCommaWithChecker()
        throws Exception {

        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String fileName = getPath("InputValidCommaIndent.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testTabs() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

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
        verifyWarns(checkConfig, getPath("InputUseTabs.java"), expected);
    }

    @Test
    public void testIndentationLevel() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

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
        verifyWarns(checkConfig, getPath("InputUseTwoSpaces.java"), expected);
    }

    @Test
    public void testThrowsIndentationLevel() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputInvalidThrowsIndent.java"), expected);
    }

    @Test
    public void testCaseLevel() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

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
        verifyWarns(checkConfig, getPath("InputCaseLevel.java"), expected);
    }

    @Test
    public void testBraceAdjustment() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

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
        verifyWarns(checkConfig, getPath("InputBraceAdjustment.java"), expected);
    }

    @Test
    public void testInvalidAssignWithChecker() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

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
        verifyWarns(checkConfig, getPath("InputInvalidAssignIndent.java"), expected);
    }

    @Test
    public void testInvalidImportIndent() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("basicOffset", "8");
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_CHILD_ERROR, "import", 2, 8),
        };
        verifyWarns(checkConfig, getPath("InputInvalidImportIndent.java"), expected);
    }

    @Test
    public void testValidAssignWithChecker() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputValidAssignIndent.java"), expected);
    }

    @Test
    public void test15Extensions() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("Input15Extensions.java"), expected);
    }

    @Test
    public void testTryResources() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputValidTryResourcesIndent.java"),
               expected);
    }

    @Test
    public void testSwitchCustom() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputSwitchCustom.java"),
               expected);
    }

    @Test
    public void testSynchronizedStatement() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputSynchronizedStatement.java"), expected);
    }

    @Test
    public void testSynchronizedMethod() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "8");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputSynchronizedMethod.java"), expected);
    }

    @Test
    public void testAnonymousClassInMethod() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
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
            "24: " + getCheckMessage(MSG_ERROR_MULTI, "method def rcurly",  24, "18, 20, 22"),
            "26: " + getCheckMessage(MSG_ERROR, "method def rcurly", 8, 2),
        };
        verifyWarns(checkConfig, getPath("InputAnonymousClassInMethod.java"), expected);
    }

    @Test
    public void testAnnotationDefinition() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getPath("InputAnnotationDefinition.java"), expected);
    }

    @Test
    public void testPackageDeclaration() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_ERROR, "package def", 1, 0),
        };
        verifyWarns(checkConfig, getPath("InputPackageDeclaration.java"), expected);
    }

    @Test
    public void testLambda1() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
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
        verifyWarns(checkConfig, getNonCompilablePath("InputLambda1.java"), expected, 0);
    }

    @Test
    public void testLambda2() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("basicOffset", "4");
        checkConfig.addAttribute("lineWrappingIndentation", "8");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, getNonCompilablePath("InputLambda2.java"), expected, 0);
    }

    @Test
    public void testSeparatedStatements() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String fileName = getPath("InputSeparatedStatements.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verifyWarns(checkConfig, fileName, expected);
    }

    @Test
    public void testSeparatedLineWithJustSpaces() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String fileName = getPath("InputSeparatedStatementWithSpaces.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, fileName, expected);
    }

    private static final class IndentAudit implements AuditListener {
        private final IndentComment[] comments;
        private int position;

        private IndentAudit(IndentComment... comments) {
            this.comments = comments;
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
            final IndentComment comment = comments[position];
            position++;

            assertTrue(
                    "input expected warning #" + position + " at line " + comment.getLineNumber()
                            + " to report '" + comment.getExpectedMessage() + "' but got instead: "
                            + line + ": " + message,
                    message.endsWith(comment.getExpectedMessage()));
        }

        @Override
        public void addException(AuditEvent event, Throwable throwable) {
            // No code needed
        }
    }

    private static final class IndentComment {
        private final int lineNumber;
        private final int indent;
        /** Used for when violations report nodes not first on the line. */
        private final int indentOffset;
        private final boolean expectedNonStrict;
        private final String expectedWarning;
        private final boolean warning;

        private IndentComment(Matcher match, int lineNumber) {
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

        public String getExpectedMessage() {
            if (expectedWarning.contains(",")) {
                return "incorrect indentation level " + (indent + indentOffset)
                        + ", expected level should be one of the following: " + expectedWarning
                        + ".";
            }

            return "incorrect indentation level " + (indent + indentOffset)
                    + ", expected level should be " + expectedWarning + ".";
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
