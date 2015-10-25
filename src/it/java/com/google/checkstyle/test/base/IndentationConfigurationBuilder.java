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

package com.google.checkstyle.test.base;

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

import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class IndentationConfigurationBuilder extends ConfigurationBuilder {
    private static final int TAB_WIDTH = 4;

    private static final Pattern NONEMPTY_LINE_REGEX =
            Pattern.compile(".*?\\S+.*?");

    private static final Pattern LINE_WITH_COMMENT_REGEX =
            Pattern.compile(".*?\\S+.*?(//indent:(\\d+) exp:((>=\\d+)|(\\d+(,\\d+)*?))( warn)?)");

    private static final Pattern GET_INDENT_FROM_COMMENT_REGEX =
            Pattern.compile("//indent:(\\d+).*?");

    private static final Pattern MULTILEVEL_COMMENT_REGEX =
            Pattern.compile("//indent:\\d+ exp:(\\d+(,\\d+)+?)( warn)?");

    private static final Pattern SINGLE_LEVEL_COMMENT_REGEX =
            Pattern.compile("//indent:\\d+ exp:(\\d+)( warn)?");

    private static final Pattern NON_STRICT_LEVEL_COMMENT_REGEX =
            Pattern.compile("//indent:\\d+ exp:>=(\\d+)( warn)?");

    public IndentationConfigurationBuilder(File aRoot) {
        super(aRoot);
    }

    @Override
    public Integer[] getLinesWithWarn(String aFileName) throws IOException {
        return getLinesWithWarnAndCheckComments(aFileName, TAB_WIDTH);
    }

    private enum CommentType {
        MULTILEVEL, SINGLE_LEVEL, NON_STRICT_LEVEL, UNKNOWN
    }

    private static Integer[] getLinesWithWarnAndCheckComments(String aFileName,
            final int tabWidth)
                    throws IOException {
        final List<Integer> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(aFileName), StandardCharsets.UTF_8))) {
            int lineNumber = 1;
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                final Matcher match = LINE_WITH_COMMENT_REGEX.matcher(line);
                if (match.matches()) {
                    final String comment = match.group(1);
                    final int indentInComment = getIndentFromComment(comment);
                    final int actualIndent = getLineStart(line, tabWidth);

                    if (actualIndent != indentInComment) {
                        throw new IllegalStateException(String.format(Locale.ROOT,
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
                        throw new IllegalStateException(String.format(Locale.ROOT,
                                        "File \"%1$s\" has inconsistent comment on line %2$d",
                                        aFileName,
                                        lineNumber));
                    }
                }
                else if (NONEMPTY_LINE_REGEX.matcher(line).matches()) {
                    throw new IllegalStateException(String.format(Locale.ROOT,
                                    "File \"%1$s\" has no indentation comment or its format "
                                                    + "malformed. Error on line: %2$d(%3$s)",
                                    aFileName,
                                    lineNumber,
                                    line));
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

        boolean result;
        final CommentType type = getCommentType(comment);
        switch (type) {
            case MULTILEVEL:
                result = isMultiLevelCommentConsistent(comment, indentInComment, isWarnComment);
                break;

            case SINGLE_LEVEL:
                result = isSingleLevelCommentConsistent(comment, indentInComment, isWarnComment);
                break;

            case NON_STRICT_LEVEL:
                result = isNonStrictCommentConsistent(comment, indentInComment, isWarnComment);
                break;

            case UNKNOWN:
                throw new IllegalArgumentException("Cannot determine comment consistent");

            default:
                throw new IllegalStateException("Cannot determine comment is consistent");

        }
        return result;
    }

    private static boolean isNonStrictCommentConsistent(String comment,
            int indentInComment, boolean isWarnComment) {
        final Matcher nonStrictLevelMatch = NON_STRICT_LEVEL_COMMENT_REGEX.matcher(comment);
        nonStrictLevelMatch.matches();
        final int expectedMinimalIndent = Integer.parseInt(nonStrictLevelMatch.group(1));

        return indentInComment >= expectedMinimalIndent && !isWarnComment
                || indentInComment < expectedMinimalIndent && isWarnComment;
    }

    private static boolean isSingleLevelCommentConsistent(String comment,
            int indentInComment, boolean isWarnComment) {
        final Matcher singleLevelMatch = SINGLE_LEVEL_COMMENT_REGEX.matcher(comment);
        singleLevelMatch.matches();
        final int expectedLevel = Integer.parseInt(singleLevelMatch.group(1));

        return expectedLevel == indentInComment && !isWarnComment
                || expectedLevel != indentInComment && isWarnComment;
    }

    private static boolean isMultiLevelCommentConsistent(String comment,
            int indentInComment, boolean isWarnComment) {
        final Matcher multilevelMatch = MULTILEVEL_COMMENT_REGEX.matcher(comment);
        multilevelMatch.matches();
        final String[] levels = multilevelMatch.group(1).split(",");
        final String indentInCommentStr = String.valueOf(indentInComment);
        final boolean containsActualLevel =
                Arrays.asList(levels).contains(indentInCommentStr);

        return containsActualLevel && !isWarnComment
                || !containsActualLevel && isWarnComment;
    }

    private static CommentType getCommentType(String comment) {
        CommentType result = CommentType.UNKNOWN;
        final Matcher multilevelMatch = MULTILEVEL_COMMENT_REGEX.matcher(comment);
        if (multilevelMatch.matches()) {
            result = CommentType.MULTILEVEL;
        }
        else {
            final Matcher singleLevelMatch = SINGLE_LEVEL_COMMENT_REGEX.matcher(comment);
            if (singleLevelMatch.matches()) {
                result = CommentType.SINGLE_LEVEL;
            }
            else {
                final Matcher nonStrictLevelMatch = NON_STRICT_LEVEL_COMMENT_REGEX.matcher(comment);
                if (nonStrictLevelMatch.matches()) {
                    result = CommentType.NON_STRICT_LEVEL;
                }
            }
        }
        return result;
    }

    private static int getLineStart(String line, final int tabWidth) {
        for (int index = 0; index < line.length(); ++index) {
            if (!Character.isWhitespace(line.charAt(index))) {
                return CommonUtils.lengthExpandedTabs(line, index, tabWidth);
            }
        }
        return 0;
    }
}
