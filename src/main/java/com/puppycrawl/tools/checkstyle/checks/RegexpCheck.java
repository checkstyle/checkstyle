////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LineColumn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * A check that makes sure that a specified pattern exists (or not) in the file.
 * </p>
 * <p>
 * An example of how to configure the check to make sure a copyright statement
 * is included in the file (but without requirements on where in the file
 * it should be):
 * </p>
 * <pre>
 * &lt;module name="RequiredRegexp"&gt;
 *    &lt;property name="format" value="This code is copyrighted"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * And to make sure the same statement appears at the beginning of the file.
 * </p>
 * <pre>
 * &lt;module name="RequiredRegexp"&gt;
 *    &lt;property name="format" value="\AThis code is copyrighted"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Stan Quinn
 */
public class RegexpCheck extends AbstractFormatCheck
{
    /** Default duplicate limit */
    private static final int DEFAULT_DUPLICATE_LIMIT = -1;

    /** Default error report limit */
    private static final int DEFAULT_ERROR_LIMIT = 100;

    /** Error count exceeded message */
    private static final String ERROR_LIMIT_EXCEEDED_MESSAGE =
        "The error limit has been exceeded, "
        + "the check is aborting, there may be more unreported errors.";

    /** Custom message for report. */
    private String message = "";

    /** Ignore matches within comments? **/
    private boolean ignoreComments;

    /** Pattern illegal? */
    private boolean illegalPattern;

    /** Error report limit */
    private int errorLimit = DEFAULT_ERROR_LIMIT;

    /** Disallow more than x duplicates? */
    private int duplicateLimit;

    /** Boolean to say if we should check for duplicates. */
    private boolean checkForDuplicates;

    /** Tracks number of matches made */
    private int matchCount;

    /** Tracks number of errors */
    private int errorCount;

    /** The matcher */
    private Matcher matcher;

    /**
     * Instantiates an new RegexpCheck.
     */
    public RegexpCheck()
    {
        super("$^", Pattern.MULTILINE); // the empty language
    }

    /**
     * Setter for message property.
     * @param message custom message which should be used in report.
     */
    public void setMessage(String message)
    {
        this.message = (message == null) ? "" : message;
    }

    /**
     * Getter for message property.
     * I'm not sure if this gets used by anything outside,
     * I just included it because GenericIllegalRegexp had it,
     * it's being used in logMessage() so it's covered in EMMA.
     * @return custom message to be used in report.
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Sets if matches within comments should be ignored.
     * @param ignoreComments True if comments should be ignored.
     */
    public void setIgnoreComments(boolean ignoreComments)
    {
        this.ignoreComments = ignoreComments;
    }

    /**
     * Sets if pattern is illegal, otherwise pattern is required.
     * @param illegalPattern True if pattern is not allowed.
     */
    public void setIllegalPattern(boolean illegalPattern)
    {
        this.illegalPattern = illegalPattern;
    }

    /**
     * Sets the limit on the number of errors to report.
     * @param errorLimit the number of errors to report.
     */
    public void setErrorLimit(int errorLimit)
    {
        this.errorLimit = errorLimit;
    }

    /**
     * Sets the maximum number of instances of required pattern allowed.
     * @param duplicateLimit negative values mean no duplicate checking,
     * any positive value is used as the limit.
     */
    public void setDuplicateLimit(int duplicateLimit)
    {
        this.duplicateLimit = duplicateLimit;
        checkForDuplicates = (duplicateLimit > DEFAULT_DUPLICATE_LIMIT);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    @Override
    public void beginTree(DetailAST rootAST)
    {
        final Pattern pattern = getRegexp();
        matcher = pattern.matcher(getFileContents().getText().getFullText());
        matchCount = 0;
        errorCount = 0;
        findMatch();
    }

    /** recursive method that finds the matches. */
    private void findMatch()
    {
        int startLine;
        int startColumn;
        int endLine;
        int endColumn;
        boolean foundMatch;
        boolean ignore = false;

        foundMatch = matcher.find();
        if (!foundMatch && !illegalPattern && (matchCount == 0)) {
            logMessage(0);
        }
        else if (foundMatch) {
            final FileText text = getFileContents().getText();
            final LineColumn start = text.lineColumn(matcher.start());
            final LineColumn end = text.lineColumn(matcher.end() - 1);
            startLine = start.getLine();
            startColumn = start.getColumn();
            endLine = end.getLine();
            endColumn = end.getColumn();
            if (ignoreComments) {
                final FileContents theFileContents = getFileContents();
                ignore = theFileContents.hasIntersectionWithComment(startLine,
                    startColumn, endLine, endColumn);
            }
            if (!ignore) {
                matchCount++;
                if (illegalPattern || (checkForDuplicates
                        && ((matchCount - 1) > duplicateLimit)))
                {
                    errorCount++;
                    logMessage(startLine);
                }
            }
            if ((errorCount < errorLimit)
                    && (ignore || illegalPattern || checkForDuplicates))
            {
                findMatch();
            }
        }
    }

    /**
     * Displays the right message.
     * @param lineNumber the line number the message relates to.
     */
    private void logMessage(int lineNumber)
    {
        String msg = "".equals(getMessage()) ? getFormat() : message;
        if (errorCount >= errorLimit) {
            msg = ERROR_LIMIT_EXCEEDED_MESSAGE + msg;
        }
        if (illegalPattern) {
            log(lineNumber, "illegal.regexp", msg);
        }
        else {
            if (lineNumber > 0) {
                log(lineNumber, "duplicate.regexp", msg);
            }
            else {
                log(lineNumber, "required.regexp", msg);
            }
        }
    }
}

