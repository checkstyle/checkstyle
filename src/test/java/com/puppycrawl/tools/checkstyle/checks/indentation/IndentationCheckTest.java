////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.indentation.AbstractExpressionHandler.MSG_CHILD_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.indentation.AbstractExpressionHandler.MSG_CHILD_ERROR_MULTI;
import static com.puppycrawl.tools.checkstyle.checks.indentation.AbstractExpressionHandler.MSG_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.indentation.AbstractExpressionHandler.MSG_ERROR_MULTI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.Utils;

/**
 *
 * @author  jrichard
 */
public class IndentationCheckTest extends BaseCheckTestSupport {
    private static final Pattern NONEMPTY_LINE_REGEX =
                    Pattern.compile(".*?\\S+.*?");

    private static final Pattern LINE_WITH_COMMENT_REGEX =
                    Pattern.compile(".*?\\S+.*?(//indent:(\\d+) exp:((>=\\d+)|(\\d+(,\\d+)*?))( warn)?)");

    private static final Pattern GET_INDENT_FROM_COMMENT_REGEX =
                    Pattern.compile("//indent:(\\d+).*?");

    private static final Pattern MULTILEVEL_COMMENT_REGEX =
                    Pattern.compile("//indent:\\d+ exp:(\\d+(,\\d+)+?)( warn)?");

    private static final Pattern SINGLELEVEL_COMMENT_REGEX =
                    Pattern.compile("//indent:\\d+ exp:(\\d+)( warn)?");

    private static final Pattern NONSTRICT_LEVEL_COMMENT_REGEX =
                    Pattern.compile("//indent:\\d+ exp:>=(\\d+)( warn)?");

    protected static Integer[] getLinesWithWarnAndCheckComments(String aFileName,
                    final int tabWidth)
                    throws IOException {
        int lineNumber = 1;
        List<Integer> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                Matcher match = LINE_WITH_COMMENT_REGEX.matcher(line);
                if (match.matches()) {
                    final String comment = match.group(1);
                    final int indentInComment = getIndentFromComment(comment);
                    final int actualIndent = getLineStart(line, tabWidth);

                    if (actualIndent != indentInComment) {
                        throw new RuntimeException(String.format(
                                        "File \"%1$s\" has incorrect indentation in comment."
                                                        + "Line %2$d: comment:%3$d, actual:%4$d.",
                                        aFileName,
                                        lineNumber,
                                        indentInComment,
                                        actualIndent));
                    }

                    if (isWarnComment(comment)) {
                        result.add(lineNumber);
                    }

                    if (!isCommentConsistent(comment)) {
                        throw new RuntimeException(String.format(
                                        "File \"%1$s\" has inconsistent comment on line %2$d",
                                        aFileName,
                                        lineNumber));
                    }
                }
                else if (NONEMPTY_LINE_REGEX.matcher(line).matches()) {
                    throw new RuntimeException(String.format(
                                    "File \"%1$s\" has no indentation comment or its format "
                                                    + "malformed. Error on line: %2$d",
                                    aFileName,
                                    lineNumber));
                }
                lineNumber++;
            }
        }
        return result.toArray(new Integer[result.size()]);
    }

    private static int getIndentFromComment(String comment) {
        final Matcher match = GET_INDENT_FROM_COMMENT_REGEX.matcher(comment);
        match.matches();
        return Integer.parseInt(match.group(1));
    }

    private static boolean isWarnComment(String comment) {
        return comment.endsWith(" warn");
    }

    private static boolean isCommentConsistent(String comment) {
        final int indentInComment = getIndentFromComment(comment);
        final boolean isWarnComment = isWarnComment(comment);

        Matcher match;

        match = MULTILEVEL_COMMENT_REGEX.matcher(comment);
        if (match.matches()) {
            final String[] levels = match.group(1).split(",");
            final String indentInCommentStr = String.valueOf(indentInComment);
            final boolean containsActualLevel =
                            Arrays.asList(levels).contains(indentInCommentStr);

            return containsActualLevel && !isWarnComment
                    || !containsActualLevel && isWarnComment;
        }

        match = SINGLELEVEL_COMMENT_REGEX.matcher(comment);
        if (match.matches()) {
            final int expectedLevel = Integer.parseInt(match.group(1));

            return expectedLevel == indentInComment && !isWarnComment
                    || expectedLevel != indentInComment && isWarnComment;
        }

        match = NONSTRICT_LEVEL_COMMENT_REGEX.matcher(comment);
        if (match.matches()) {
            final int expectedMinimalIndent = Integer.parseInt(match.group(1));

            return indentInComment >= expectedMinimalIndent && !isWarnComment
                    || indentInComment < expectedMinimalIndent && isWarnComment;
        }

        throw new IllegalArgumentException();
    }

    private static int getLineStart(String line, final int tabWidth) {
        for (int index = 0; index < line.length(); ++index) {
            if (!Character.isWhitespace(line.charAt(index))) {
                return Utils.lengthExpandedTabs(line, index, tabWidth);
            }
        }
        return 0;
    }

    private void verifyWarns(Configuration config, String filePath,
                    String[] expected, int warnCountCorrection)
                    throws Exception {
        final int tabWidth = Integer.parseInt(config.getAttribute("tabWidth"));
        Integer[] linesWithWarn =
                        getLinesWithWarnAndCheckComments(filePath, tabWidth);
        Assert.assertEquals("Expected warning count in UT does not match warn"
                        + " comment count in input file", linesWithWarn.length
                        + warnCountCorrection,
                        expected.length);
        verify(config, filePath, expected);
    }

    private void verifyWarns(Configuration config, String filePath,
                    String[] expected)
                    throws Exception {
        verifyWarns(config, filePath, expected, 0);
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
        verifyWarns(checkConfig, getPath("indentation/InputMethodCStyle.java"), expected);
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
        final String[] expected = {};
        verifyWarns(checkConfig, getPath("indentation/InputZeroCaseLevel.java"), expected);
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
        verifyWarns(checkConfig, getPath("indentation/InputAndroidStyle.java"), expected);
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
        verifyWarns(checkConfig, getPath("indentation/InputMethodCallLineWrap.java"), expected);
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
        verifyWarns(checkConfig, getPath("indentation/InputDifficultAnnotations.java"), expected);
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
        final String[] expected = {
        };
        verifyWarns(checkConfig, getPath("indentation/FromGuava2.java"), expected);
    }

    @Test
    public void testAnnotatins() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);

        checkConfig.addAttribute("arrayInitIndent", "4");
        checkConfig.addAttribute("basicOffset", "2");
        checkConfig.addAttribute("braceAdjustment", "0");
        checkConfig.addAttribute("caseIndent", "4");
        checkConfig.addAttribute("forceStrictCondition", "false");
        checkConfig.addAttribute("lineWrappingIndentation", "4");
        checkConfig.addAttribute("tabWidth", "4");
        checkConfig.addAttribute("throwsIndent", "4");
        final String[] expected = {};
        verifyWarns(checkConfig, getPath("indentation/FromGuava.java"), expected);
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
        final String[] expected = {};
        verifyWarns(checkConfig, getPath("indentation/IndentationCorrectIfAndParameterInput.java"), expected);
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
        final String[] expected = {};
        verifyWarns(checkConfig, getPath("indentation/InputAnonymousClasses.java"), expected);
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
        final String[] expected = {
        };
        verifyWarns(checkConfig, getPath("indentation/InputArrays.java"), expected);
    }

    @Test
    public void testLables() throws Exception {
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
        };
        verifyWarns(checkConfig, getPath("indentation/InputLabels.java"), expected);
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
        final String[] expected = {
        };
        verifyWarns(checkConfig, getPath("indentation/InputClassesMethods.java"), expected);
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

        verifyWarns(checkConfig, getPath("indentation/InputMembers.java"), expected);
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
        verifyWarns(checkConfig, getPath("indentation/InputInvalidLabelIndent.java"), expected);
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
        final String[] expected = {
        };
        verifyWarns(checkConfig, getPath("indentation/InputValidLabelIndent.java"), expected);
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
        final String fname = getPath("indentation/InputValidIfIndent.java");
        final String[] expected = {
            "231: " + getCheckMessage(MSG_ERROR, "(", 8, 12),
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidDotIndent.java");
        final String[] expected = {
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidMethodIndent.java");
        final String[] expected = {
            "129: " + getCheckMessage(MSG_ERROR, "void", 4, 8),
            "130: " + getCheckMessage(MSG_ERROR, "method5", 4, 8),
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputInvalidMethodIndent.java");
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
            "93: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 4, 8),
            "98: " + getCheckMessage(MSG_ERROR, "member def type", 6, 8),
            "98: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 6, 8),
            "99: " + getCheckMessage(MSG_ERROR, "if", 6, 8),
            "100: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "100: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "101: " + getCheckMessage(MSG_ERROR, "if rcurly", 6, 8),
            "104: " + getCheckMessage(MSG_ERROR, "Arrays", 10, 12),
            "113: " + getCheckMessage(MSG_ERROR, "+", 10, 12),
            "113: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "122: " + getCheckMessage(MSG_ERROR, "new", 10, 12),
            "126: " + getCheckMessage(MSG_ERROR, "new", 10, 12),
            "127: " + getCheckMessage(MSG_ERROR, ")", 6, 8),
            "131: " + getCheckMessage(MSG_ERROR, "method call rparen", 6, 8),
            "145: " + getCheckMessage(MSG_ERROR, "6", 10, 12),
            "145: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "148: " + getCheckMessage(MSG_ERROR, "6", 10, 12),
            "148: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "158: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 6, 12),
            "170: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 4, 8),
            "175: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 4, 8),
            "179: " + getCheckMessage(MSG_ERROR, "int", 0, 8),
            "180: " + getCheckMessage(MSG_ERROR, "method9", 4, 8),
            "190: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 12, 8),
        };
        verifyWarns(checkConfig, fname, expected, 6);
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
        final String fname = getPath("indentation/InputInvalidSwitchIndent.java");
        final String[] expected = {
            "30: " + getCheckMessage(MSG_ERROR, "switch", 6, 8),
            "32: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 12),
            "33: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "33: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 14, 16),
            "37: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "39: " + getCheckMessage(MSG_CHILD_ERROR, "case", 14, 12),
            "40: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 12),
            "43: " + getCheckMessage(MSG_CHILD_ERROR, "case", 10, 12),
            "44: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "44: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 14, 16),
            "45: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "53: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "53: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 14, 16),
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
        verifyWarns(checkConfig, fname, expected, 3);
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
        final String fname = getPath("indentation/InputValidSwitchIndent.java");
        final String[] expected = {
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidArrayInitDefaultIndent.java");
        final String[] expected = {
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidArrayInitIndent.java");
        final String[] expected = {};
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputInvalidArrayInitIndent.java");
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
            "40: " + getCheckMessage(MSG_ERROR, "array initialization lcurly", 2, 4),
            "44: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 6, "4, 8"),
            "48: " + getCheckMessage(MSG_ERROR, "array initialization lcurly", 2, 4),
            "52: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "array initialization", 20, "8, 31, 33"),
            "53: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "array initialization", 4, "8, 31, 33"),
            "58: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 6, 8),
            "63: " + getCheckMessage(MSG_ERROR, "member def type", 2, 4),
            "65: " + getCheckMessage(MSG_ERROR, "member def type", 6, 4),
            "66: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 2, "6, 10"),
            "69: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 6, 8),
            "76: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "89: " + getCheckMessage(MSG_ERROR, "1", 8, 12),
            "89: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 8, 12),
            "100: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "101: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 14, 12),
            "104: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "105: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 14, 12),
            "106: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 6, "8, 12"),
            "109: " + getCheckMessage(MSG_ERROR, "array initialization lcurly", 6, 8),
            "110: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 14, 12),
            "111: " + getCheckMessage(MSG_CHILD_ERROR, "array initialization", 10, 12),
            "112: " + getCheckMessage(MSG_ERROR_MULTI, "array initialization rcurly", 6, "8, 12"),
        };

        //Test input for this test case is not checked due to issue #693.
        verify(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidTryIndent.java");
        final String[] expected = {
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputInvalidTryIndent.java");
        final String[] expected = {
            "25: " + getCheckMessage(MSG_ERROR, "try", 9, 8),
            "26: " + getCheckMessage(MSG_ERROR, "try rcurly", 7, 8),
            "28: " + getCheckMessage(MSG_ERROR, "catch rcurly", 7, 8),
            "30: " + getCheckMessage(MSG_ERROR, "try", 4, 8),
            "31: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 12),
            "31: " + getCheckMessage(MSG_CHILD_ERROR, "try", 8, 12),
            "32: " + getCheckMessage(MSG_ERROR, "try rcurly", 4, 8),
            "33: " + getCheckMessage(MSG_CHILD_ERROR, "finally", 8, 12),
            "33: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 12),
            "38: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 8, 12),
            "38: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 12),
            "43: " + getCheckMessage(MSG_ERROR, "try rcurly", 10, 8),
            "45: " + getCheckMessage(MSG_ERROR, "catch rcurly", 6, 8),
            "52: " + getCheckMessage(MSG_ERROR, "catch rcurly", 5, 8),
            "59: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 10, 12),
            "59: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "60: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 14, 12),
            "61: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 10, 12),
            "61: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "63: " + getCheckMessage(MSG_ERROR, "catch", 6, 8),
            "70: " + getCheckMessage(MSG_ERROR, "try lcurly", 10, 8),
            "72: " + getCheckMessage(MSG_ERROR, "try rcurly", 10, 8),
            "74: " + getCheckMessage(MSG_ERROR, "catch lcurly", 6, 8),
            "77: " + getCheckMessage(MSG_ERROR, "catch rcurly", 10, 8),
            "80: " + getCheckMessage(MSG_CHILD_ERROR, "catch", 10, 12),
            "80: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
        };
        verifyWarns(checkConfig, fname, expected, 6);
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
        final String fname = getPath("indentation/InputInvalidClassDefIndent.java");
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
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputInvalidBlockIndent.java");
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
            "39: " + getCheckMessage(MSG_ERROR, "member def type", 13, 12),
            "41: " + getCheckMessage(MSG_CHILD_ERROR, "block", 13, 12),
            "42: " + getCheckMessage(MSG_ERROR, "block rcurly", 9, 8),
            "45: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 8),
            "46: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "46: " + getCheckMessage(MSG_ERROR, "member def type", 10, 12),
            "48: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "49: " + getCheckMessage(MSG_ERROR, "block rcurly", 6, 8),
            "52: " + getCheckMessage(MSG_ERROR, "block lcurly", 6, 8),
            "55: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "55: " + getCheckMessage(MSG_ERROR, "member def type", 10, 12),
            "59: " + getCheckMessage(MSG_ERROR, "block lcurly", 10, 12),
            "63: " + getCheckMessage(MSG_ERROR, "block rcurly", 10, 12),
            "68: " + getCheckMessage(MSG_CHILD_ERROR, "block", 10, 12),
            "70: " + getCheckMessage(MSG_ERROR, "block lcurly", 10, 12),
            "71: " + getCheckMessage(MSG_CHILD_ERROR, "block", 14, 16),
            "71: " + getCheckMessage(MSG_ERROR, "member def type", 14, 16),
            "86: " + getCheckMessage(MSG_ERROR, "block rcurly", 10, 12),
            "95: " + getCheckMessage(MSG_ERROR, "static initialization", 2, 4),
            "96: " + getCheckMessage(MSG_ERROR, "static initialization", 6, 4),
            "100: " + getCheckMessage(MSG_ERROR, "member def type", 7, 8),
            "100: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 7, 8),
            "103: " + getCheckMessage(MSG_ERROR, "static initialization", 6, 4),
            "105: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 2, 4),
            "107: " + getCheckMessage(MSG_ERROR, "static initialization", 2, 4),
            "109: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 6, 4),
            "111: " + getCheckMessage(MSG_ERROR, "static initialization", 2, 4),
            "113: " + getCheckMessage(MSG_ERROR, "member def type", 6, 8),
            "113: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 6, 8),
            "116: " + getCheckMessage(MSG_ERROR, "static initialization lcurly", 2, 4),
            "117: " + getCheckMessage(MSG_ERROR, "member def type", 6, 8),
            "117: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 6, 8),
            "118: " + getCheckMessage(MSG_ERROR, "static initialization rcurly", 6, 4),
            "123: " + getCheckMessage(MSG_ERROR, "member def type", 6, 8),
            "123: " + getCheckMessage(MSG_CHILD_ERROR, "static initialization", 6, 8),
            "128: " + getCheckMessage(MSG_ERROR, "member def type", 4, 8),
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
            "150: " + getCheckMessage(MSG_ERROR, "member def type", 6, 8),
        };
        verifyWarns(checkConfig, fname, expected, 10);
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
        final String fname = getPath("indentation/InputInvalidIfIndent.java");
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
            "132: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "137: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),
            "138: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "140: " + getCheckMessage(MSG_CHILD_ERROR, "else", 10, 12),
            "140: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "141: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 8, 12),

            "148: " + getCheckMessage(MSG_CHILD_ERROR, "if", 16, 12),
            "149: " + getCheckMessage(MSG_ERROR, "if rcurly", 9, 8),
            "152: " + getCheckMessage(MSG_CHILD_ERROR, "else", 16, 12),
            "158: " + getCheckMessage(MSG_CHILD_ERROR, "if", 0, 12),
            "158: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 0, 12),
            "162: " + getCheckMessage(MSG_CHILD_ERROR, "else", 40, 12),
            "169: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),

            "172: " + getCheckMessage(MSG_CHILD_ERROR, "else", 14, 12),
            "178: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "178: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "180: " + getCheckMessage(MSG_CHILD_ERROR, "else", 10, 12),
            "180: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "184: " + getCheckMessage(MSG_ERROR, "if", 10, 8),
            "185: " + getCheckMessage(MSG_CHILD_ERROR, "if", 14, 12),
            "186: " + getCheckMessage(MSG_ERROR, "if rcurly", 10, 8),
            "187: " + getCheckMessage(MSG_ERROR, "else", 10, 8),

            "188: " + getCheckMessage(MSG_CHILD_ERROR, "else", 14, 12),
            "189: " + getCheckMessage(MSG_ERROR, "else rcurly", 10, 8),
            "192: " + getCheckMessage(MSG_ERROR, "&&", 9, 12),
            "192: " + getCheckMessage(MSG_CHILD_ERROR, "if", 9, 12),
            "193: " + getCheckMessage(MSG_ERROR, "&&", 11, 12),
            "193: " + getCheckMessage(MSG_CHILD_ERROR, "if", 11, 12),
            "197: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "200: " + getCheckMessage(MSG_ERROR, "if rcurly", 7, 8),
            "207: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "207: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),
            "209: " + getCheckMessage(MSG_CHILD_ERROR, "if", 10, 12),
            "209: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),

            "225: " + getCheckMessage(MSG_ERROR, "if", 10, 12),
            "229: " + getCheckMessage(MSG_CHILD_ERROR, "if", 18, 20),
            "229: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 18, 20),
            "233: " + getCheckMessage(MSG_ERROR, "if rcurly", 40, 8),
            "240: " + getCheckMessage(MSG_ERROR, "if rparen", 10, 8),
            "245: " + getCheckMessage(MSG_ERROR, "if rparen", 6, 8),
            "251: " + getCheckMessage(MSG_ERROR, "(", 6, 12),
            "251: " + getCheckMessage(MSG_ERROR, "if lparen", 6, 8),
            "253: " + getCheckMessage(MSG_ERROR, "if rparen", 6, 8),
        };
        verifyWarns(checkConfig, fname, expected, 11);
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
        final String fname = getPath("indentation/InputInvalidWhileIndent.java");
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
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputInvalidForIndent.java");
        final String[] expected = {
            "26: " + getCheckMessage(MSG_ERROR, "for", 6, 8),
            "27: " + getCheckMessage(MSG_ERROR, "for rcurly", 10, 8),
            "29: " + getCheckMessage(MSG_ERROR, "for", 9, 8),
            "30: " + getCheckMessage(MSG_ERROR, "for lcurly", 6, 8),
            "31: " + getCheckMessage(MSG_ERROR, "for rcurly", 6, 8),
            "35: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "35: " + getCheckMessage(MSG_CHILD_ERROR, "method call", 10, 12),

            "36: " + getCheckMessage(MSG_ERROR, "for rcurly", 10, 8),
            "39: " + getCheckMessage(MSG_ERROR, "for lcurly", 10, 8),
            "40: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "40: " + getCheckMessage(MSG_ERROR, "member def type", 10, 12),
            "48: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "48: " + getCheckMessage(MSG_ERROR, "i", 10, 12),
            "54: " + getCheckMessage(MSG_ERROR, "for", 7, 8),

            "55: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "55: " + getCheckMessage(MSG_ERROR, "int", 10, 11),
            "55: " + getCheckMessage(MSG_ERROR, "member def type", 10, 12),
            "64: " + getCheckMessage(MSG_CHILD_ERROR, "for", 7, 12),
            "64: " + getCheckMessage(MSG_ERROR, "i", 7, 12),

            "69: " + getCheckMessage(MSG_ERROR, "for", 6, 8),
            "70: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "71: " + getCheckMessage(MSG_CHILD_ERROR, "for", 14, 16),
            "72: " + getCheckMessage(MSG_CHILD_ERROR, "for", 10, 12),
            "77: " + getCheckMessage(MSG_ERROR, "for rcurly", 39, 8),
            "81: " + getCheckMessage(MSG_ERROR, "for rparen", 12, 8),
        };
        verifyWarns(checkConfig, fname, expected, 6);
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
        final String fname = getPath("indentation/InputValidForIndent.java");
        final String[] expected = {
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidDoWhileIndent.java");
        final String[] expected = {
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidBlockIndent.java");
        final String[] expected = {
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidWhileIndent.java");
        final String[] expected = {
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidClassDefIndent.java");
        final String[] expected = {
            "49: " + getCheckMessage(MSG_ERROR, "class", 0, 4),
            "71: " + getCheckMessage(MSG_ERROR, "int", 8, 12),
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidInterfaceDefIndent.java");
        final String[] expected = {
        };
        verifyWarns(checkConfig, fname, expected);
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
        final String fname = getPath("indentation/InputValidCommaIndent.java");
        final String[] expected = {
        };
        verifyWarns(checkConfig, fname, expected);
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
        verifyWarns(checkConfig, getPath("indentation/InputUseTabs.java"), expected);
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
        verifyWarns(checkConfig, getPath("indentation/InputUseTwoSpaces.java"), expected);
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
        final String[] expected = {
        };
        verifyWarns(checkConfig, getPath("indentation/InvalidInputThrowsIndent.java"), expected);
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
        verifyWarns(checkConfig, getPath("indentation/InputCaseLevel.java"), expected);
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
        verifyWarns(checkConfig, getPath("indentation/InputBraceAdjustment.java"), expected);
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
        verifyWarns(checkConfig, getPath("indentation/InputInvalidAssignIndent.java"), expected);
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
        final String[] expected = {
        };
        verifyWarns(checkConfig, getPath("indentation/InputValidAssignIndent.java"), expected);
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
        final String[] expected = {};
        verifyWarns(checkConfig, getPath("indentation/Input15Extensions.java"), expected);
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
        final String[] expected = {};
        verifyWarns(checkConfig, getPath("indentation/InputValidTryResourcesIndent.java"),
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
        final String[] expected = {};
        verifyWarns(checkConfig, getPath("indentation/InputSwitchCustom.java"),
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
        final String[] expected = {
        };
        verifyWarns(checkConfig, getPath("indentation/InputSynchronizedStatement.java"), expected);
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
        final String[] expected = {
        };
        verifyWarns(checkConfig, getPath("indentation/InputSynchronizedMethod.java"), expected);
    }

    @Test
    public void testAnnotationDefinition() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(IndentationCheck.class);
        checkConfig.addAttribute("tabWidth", "4");
        final String[] expected = {
        };
        verifyWarns(checkConfig, getPath("indentation/InputAnnotationDefinition.java"), expected);
    }
}
