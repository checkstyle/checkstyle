////
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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LineColumn;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks that a specified pattern exists, exists less than
 * a set number of times, or does not exist in the file.
 * </div>
 *
 * <p>
 * This check combines all the functionality provided by
 * <a href="https://checkstyle.org/checks/header/regexpheader.html#RegexpHeader">RegexpHeader</a>
 * except supplying the regular expression from a file.
 * </p>
 *
 * <p>
 * It differs from them in that it works in multiline mode. Its regular expression
 * can span multiple lines and it checks this against the whole file at once.
 * The others work in single-line mode. Their single or multiple regular expressions
 * can only span one line. They check each of these against each line in the file in turn.
 * </p>
 *
 * <p>
 * <b>Note:</b> Because of the different mode of operation there may be some
 * changes in the regular expressions used to achieve a particular end.
 * </p>
 *
 * <p>
 * In multiline mode...
 * </p>
 * <ul>
 * <li>
 * {@code ^} means the beginning of a line, as opposed to beginning of the input.
 * </li>
 * <li>
 * For beginning of the input use {@code \A}.
 * </li>
 * <li>
 * {@code $} means the end of a line, as opposed to the end of the input.
 * </li>
 * <li>
 * For end of input use {@code \Z}.
 * </li>
 * <li>
 * Each line in the file is terminated with a line feed character.
 * </li>
 * </ul>
 *
 * <p>
 * <b>Note:</b> Not all regular expression engines are created equal.
 * Some provide extra functions that others do not and some elements
 * of the syntax may vary. This check makes use of the
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/regex/package-summary.html">
 * java.util.regex package</a>; please check its documentation for details
 * of how to construct a regular expression to achieve a particular goal.
 * </p>
 *
 * <p>
 * <b>Note:</b> When entering a regular expression as a parameter in
 * the XML config file you must also take into account the XML rules. e.g.
 * if you want to match a &lt; symbol you need to enter &amp;lt;.
 * The regular expression should be entered on one line.
 * </p>
 *
 * <p>
 * <b>Note:</b> To search for parentheses () in a regular expression
 * you must escape them like \(\). This is required by the regexp engine,
 * otherwise it will think they are special instruction characters.
 * </p>
 *
 * <p>
 * <b>Note:</b> To search for things that mean something in XML, like
 * &lt; you need to escape them like &amp;lt;. This is required so the
 * XML parser does not act on them, but instead passes the correct
 * character to the regexp engine.
 * </p>
 * <ul>
 * <li>
 * Property {@code duplicateLimit} - Control whether to check for duplicates
 * of a required pattern, any negative value means no checking for duplicates,
 * any positive value is used as the maximum number of allowed duplicates,
 * if the limit is exceeded violations will be logged.
 * Type is {@code int}.
 * Default value is {@code 0}.
 * </li>
 * <li>
 * Property {@code errorLimit} - Specify the maximum number of violations before
 * the check will abort.
 * Type is {@code int}.
 * Default value is {@code 100}.
 * </li>
 * <li>
 * Property {@code format} - Specify the pattern to match against.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
 * </li>
 * <li>
 * Property {@code ignoreComments} - Control whether to ignore matches found within comments.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code illegalPattern} - Control whether the pattern is required or illegal.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code message} - Specify message which is used to notify about
 * violations, if empty then the default (hard-coded) message is used.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code duplicate.regexp}
 * </li>
 * <li>
 * {@code illegal.regexp}
 * </li>
 * <li>
 * {@code required.regexp}
 * </li>
 * </ul>
 *
 * @since 4.0
 */
@FileStatefulCheck
public class RegexpCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ILLEGAL_REGEXP = "illegal.regexp";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_REQUIRED_REGEXP = "required.regexp";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_DUPLICATE_REGEXP = "duplicate.regexp";

    /** Default duplicate limit. */
    private static final int DEFAULT_DUPLICATE_LIMIT = -1;

    /** Default error report limit. */
    private static final int DEFAULT_ERROR_LIMIT = 100;

    /** Error count exceeded message. */
    private static final String ERROR_LIMIT_EXCEEDED_MESSAGE =
        "The error limit has been exceeded, "
            + "the check is aborting, there may be more unreported errors.";

    /**
     * Specify message which is used to notify about violations,
     * if empty then the default (hard-coded) message is used.
     */
    private String message;

    /** Control whether to ignore matches found within comments. */
    private boolean ignoreComments;

    /** Control whether the pattern is required or illegal. */
    private boolean illegalPattern;

    /** Specify the maximum number of violations before the check will abort. */
    private int errorLimit = DEFAULT_ERROR_LIMIT;

    /**
     * Control whether to check for duplicates of a required pattern,
     * any negative value means no checking for duplicates,
     * any positive value is used as the maximum number of allowed duplicates,
     * if the limit is exceeded violations will be logged.
     */
    private int duplicateLimit;

    /** Boolean to say if we should check for duplicates. */
    private boolean checkForDuplicates;

    /** Specify the pattern to match against. */
    private Pattern format = Pattern.compile("^$", Pattern.MULTILINE);

    /**
     * Setter to specify message which is used to notify about violations,
     * if empty then the default (hard-coded) message is used.
     *
     * @param message custom message which should be used in report.
     * @since 4.0
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Setter to control whether to ignore matches found within comments.
     *
     * @param ignoreComments True if comments should be ignored.
     * @since 4.0
     */
    public void setIgnoreComments(boolean ignoreComments) {
        this.ignoreComments = ignoreComments;
    }

    /**
     * Setter to control whether the pattern is required or illegal.
     *
     * @param illegalPattern True if pattern is not allowed.
     * @since 4.0
     */
    public void setIllegalPattern(boolean illegalPattern) {
        this.illegalPattern = illegalPattern;
    }

    /**
     * Setter to specify the maximum number of violations before the check will abort.
     *
     * @param errorLimit the number of errors to report.
     * @since 4.0
     */
    public void setErrorLimit(int errorLimit) {
        this.errorLimit = errorLimit;
    }

    /**
     * Setter to control whether to check for duplicates of a required pattern,
     * any negative value means no checking for duplicates,
     * any positive value is used as the maximum number of allowed duplicates,
     * if the limit is exceeded violations will be logged.
     *
     * @param duplicateLimit negative values mean no duplicate checking,
     *     any positive value is used as the limit.
     * @since 4.0
     */
    public void setDuplicateLimit(int duplicateLimit) {
        this.duplicateLimit = duplicateLimit;
        checkForDuplicates = duplicateLimit > DEFAULT_DUPLICATE_LIMIT;
    }

    /**
     * Setter to specify the pattern to match against.
     *
     * @param pattern the new pattern
     * @since 4.0
     */
    public final void setFormat(Pattern pattern) {
        format = CommonUtil.createPattern(pattern.pattern(), Pattern.MULTILINE);
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        processRegexpMatches();
    }

    /**
     * Processes the regexp matches and logs the number of errors in the file.
     *
     */
    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    private void processRegexpMatches() {
        final Matcher matcher = format.matcher(getFileContents().getText().getFullText());
        int errorCount = 0;
        int matchCount = 0;
        final FileText text = getFileContents().getText();
        while (errorCount < errorLimit && matcher.find()) {
            final LineColumn start = text.lineColumn(matcher.start());
            final int startLine = start.getLine();

            final boolean ignore = isIgnore(startLine, text, start, matcher);
            if (!ignore) {
                matchCount++;
                if (illegalPattern || checkForDuplicates
                    && matchCount - 1 > duplicateLimit) {
                    errorCount++;
                    logMessage(startLine, errorCount);
                }
            }
        }
        if (!illegalPattern && matchCount == 0) {
            final String msg = getMessage(errorCount);
            log(1, MSG_REQUIRED_REGEXP, msg);
        }
    }

    /**
     * Detect ignore situation.
     *
     * @param startLine position of line
     * @param text file text
     * @param start line column
     * @param matcher The matcher
     * @return true is that need to be ignored
     */
    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    private boolean isIgnore(int startLine, FileText text, LineColumn start, Matcher matcher) {
        final LineColumn end;
        if (matcher.end() == 0) {
            end = text.lineColumn(0);
        }
        else {
            end = text.lineColumn(matcher.end() - 1);
        }
        boolean ignore = false;
        if (ignoreComments) {
            final FileContents theFileContents = getFileContents();
            final int startColumn = start.getColumn();
            final int endLine = end.getLine();
            final int endColumn = end.getColumn();
            ignore = theFileContents.hasIntersectionWithComment(startLine,
                startColumn, endLine, endColumn);
        }
        return ignore;
    }

    /**
     * Displays the right message.
     *
     * @param lineNumber the line number the message relates to.
     * @param errorCount number of errors in the file.
     */
    private void logMessage(int lineNumber, int errorCount) {
        final String msg = getMessage(errorCount);

        if (illegalPattern) {
            log(lineNumber, MSG_ILLEGAL_REGEXP, msg);
        }
        else {
            log(lineNumber, MSG_DUPLICATE_REGEXP, msg);
        }
    }

    /**
     * Provide right message.
     *
     * @param errorCount number of errors in the file.
     * @return message for violation.
     */
    private String getMessage(int errorCount) {
        String msg;

        if (message == null || message.isEmpty()) {
            msg = format.pattern();
        }
        else {
            msg = message;
        }

        if (errorCount >= errorLimit) {
            msg = ERROR_LIMIT_EXCEEDED_MESSAGE + msg;
        }

        return msg;
    }
}
