package com.google.checkstyle.test.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class IndentationConfigurationBuilder extends ConfigurationBuilder
{
    private static final int TAB_WIDTH = 4;

    public IndentationConfigurationBuilder(File aROOT)
        throws CheckstyleException, IOException
    {
        super(aROOT);
    }

    @Override
    public Integer[] getLinesWithWarn(String aFileName) throws IOException {
        return getLinesWithWarnAndCheckComments(aFileName, TAB_WIDTH);
    }

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

    protected static Integer[] getLinesWithWarnAndCheckComments(String aFileName,
                    final int tabWidth)
                    throws IOException
    {
        List<Integer> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            int lineNumber = 1;
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                Matcher match = LINE_WITH_COMMENT_REGEX.matcher(line);
                if (match.matches()) {
                    final String comment = match.group(1);
                    final int indentInComment = getIndentFromComment(comment);
                    final int actualIndent = getLineStart(line, tabWidth);

                    if (actualIndent != indentInComment) {
                        throw new IllegalStateException(String.format(
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
                        throw new IllegalStateException(String.format(
                                        "File \"%1$s\" has inconsistent comment on line %2$d",
                                        aFileName,
                                        lineNumber));
                    }
                }
                else if (NONEMPTY_LINE_REGEX.matcher(line).matches()) {
                    throw new IllegalStateException(String.format(
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

    private static int getIndentFromComment(String comment)
    {
        final Matcher match = GET_INDENT_FROM_COMMENT_REGEX.matcher(comment);
        match.matches();
        return Integer.parseInt(match.group(1));
    }

    private static boolean isWarnComment(String comment)
    {
        return comment.endsWith(" warn");
    }

    private static boolean isCommentConsistent(String comment)
    {
        final int indentInComment = getIndentFromComment(comment);
        final boolean isWarnComment = isWarnComment(comment);

        Matcher match = MULTILEVEL_COMMENT_REGEX.matcher(comment);
        if (match.matches()) {
            final String[] levels = match.group(1).split(",");
            final String indentInCommentStr = String.valueOf(indentInComment);
            final boolean containsActualLevel =
                            Arrays.asList(levels).contains(indentInCommentStr);

            return containsActualLevel && !isWarnComment
                    || !containsActualLevel && isWarnComment;
        }

        match = SINGLE_LEVEL_COMMENT_REGEX.matcher(comment);
        if (match.matches()) {
            final int expectedLevel = Integer.parseInt(match.group(1));

            return expectedLevel == indentInComment && !isWarnComment
                    || expectedLevel != indentInComment && isWarnComment;
        }

        match = NON_STRICT_LEVEL_COMMENT_REGEX.matcher(comment);
        if (match.matches()) {
            final int expectedMinimalIndent = Integer.parseInt(match.group(1));

            return indentInComment >= expectedMinimalIndent && !isWarnComment
                    || indentInComment < expectedMinimalIndent && isWarnComment;
        }

        throw new IllegalArgumentException("Cannot determine if commit is consistent");
    }

    private static int getLineStart(String line, final int tabWidth)
    {
        for (int index = 0; index < line.length(); ++index) {
            if (!Character.isWhitespace(line.charAt(index))) {
                return CommonUtils.lengthExpandedTabs(line, index, tabWidth);
            }
        }
        return 0;
    }
}