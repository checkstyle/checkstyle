///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.google.checkstyle.test.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public abstract class AbstractIndentationTestSupport extends AbstractGoogleModuleTestSupport {

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

    @Override
    protected Integer[] getLinesWithWarn(String fileName) throws IOException {
        return getLinesWithWarnAndCheckComments(fileName, TAB_WIDTH);
    }

    /**
     * Returns line numbers for lines with 'warn' comments.
     *
     * @param aFileName file name.
     * @param tabWidth tab width.
     * @return array of line numbers containing 'warn' comments ('warn').
     * @throws IOException while reading the file for checking lines.
     * @throws IllegalStateException if file has incorrect indentation in comment or
     *     comment is inconsistent or if file has no indentation comment.
     */
    private static Integer[] getLinesWithWarnAndCheckComments(String aFileName,
            final int tabWidth)
                    throws IOException {
        final List<Integer> result = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(
                Path.of(aFileName), StandardCharsets.UTF_8)) {
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
        return result.toArray(new Integer[0]);
    }

    /**
     * Returns amount of indentation from the comment.
     *
     * @param comment the indentation comment to be checked.
     * @return amount of indentation in comment.
     */
    private static int getIndentFromComment(String comment) {
        final Matcher match = GET_INDENT_FROM_COMMENT_REGEX.matcher(comment);
        match.matches();
        return Integer.parseInt(match.group(1));
    }

    /**
     * Checks if comment is a warn comment (ends with "warn") or not.
     *
     * @param comment the comment to be checked.
     * @return true if comment ends with " warn" else returns false.
     */
    private static boolean isWarnComment(String comment) {
        return comment.endsWith(" warn");
    }

    /**
     * Checks if a comment of comment type is consistent or not.
     *
     * @param comment the comment to be checked.
     * @return true if comment is consistent based on expected indent level, actual indent level
     *     and if comment is a warn comment else it returns false.
     * @throws IllegalArgumentException if comment type is unknown and cannot determine consistency.
     * @throws IllegalStateException if cannot determine that comment is consistent(default case).
     */
    private static boolean isCommentConsistent(String comment) {
        final int indentInComment = getIndentFromComment(comment);
        final boolean isWarnComment = isWarnComment(comment);

        final boolean result;
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

    /**
     * Checks if a Non Strict Comment is consistent or not.
     *
     * @param comment the comment to be checked.
     * @param indentInComment the actual indentation in that comment.
     * @param isWarnComment if comment is Warn comment or not.
     * @return true if Non Strict comment is consistent else returns false.
     */
    private static boolean isNonStrictCommentConsistent(String comment,
            int indentInComment, boolean isWarnComment) {
        final Matcher nonStrictLevelMatch = NON_STRICT_LEVEL_COMMENT_REGEX.matcher(comment);
        nonStrictLevelMatch.matches();
        final int expectedMinimalIndent = Integer.parseInt(nonStrictLevelMatch.group(1));

        return indentInComment >= expectedMinimalIndent && !isWarnComment
                || indentInComment < expectedMinimalIndent && isWarnComment;
    }

    /**
     * Checks if a Single Level comment is consistent or not.
     *
     * @param comment the comment to be checked.
     * @param indentInComment the actual indentation in that comment.
     * @param isWarnComment if comment is Warn comment or not.
     * @return true if Single Level comment is consistent or not else returns false.
     */
    private static boolean isSingleLevelCommentConsistent(String comment,
            int indentInComment, boolean isWarnComment) {
        final Matcher singleLevelMatch = SINGLE_LEVEL_COMMENT_REGEX.matcher(comment);
        singleLevelMatch.matches();
        final int expectedLevel = Integer.parseInt(singleLevelMatch.group(1));

        return expectedLevel == indentInComment && !isWarnComment
                || expectedLevel != indentInComment && isWarnComment;
    }

    /**
     * Checks if a Multi-Level comment is consistent or not.
     *
     * @param comment the comment to be checked.
     * @param indentInComment the actual indentation in that comment.
     * @param isWarnComment if comment is Warn comment or not.
     * @return true if Multi-Level comment is consistent or not else returns false.
     */
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

    /**
     * Returns the type of Comment by matching with specific regex for each type.
     * Possible types include {@link CommentType#MULTILEVEL}, {@link CommentType#SINGLE_LEVEL},
     * {@link CommentType#NON_STRICT_LEVEL}, and {@link CommentType#UNKNOWN}.
     *
     * @param comment the comment whose type is to be returned.
     * @return {@link CommentType} instance for the given comment.
     */
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

    /**
     * Returns starting position of a Line.
     *
     * @param line the line whose starting position is required.
     * @param tabWidth tab width (passed value is 4 to this method).
     * @return starting position of given line.
     */
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

    private enum CommentType {

        MULTILEVEL,
        SINGLE_LEVEL,
        NON_STRICT_LEVEL,
        UNKNOWN,

    }

}
