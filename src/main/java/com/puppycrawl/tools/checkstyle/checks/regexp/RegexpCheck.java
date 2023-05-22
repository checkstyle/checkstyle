///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
 * <p>
 * Checks that a specified pattern exists, exists less than
 * a set number of times, or does not exist in the file.
 * </p>
 * <p>
 * This check combines all the functionality provided by
 * <a href="https://checkstyle.org/config_header.html#RegexpHeader">RegexpHeader</a>
 * except supplying the regular expression from a file.
 * </p>
 * <p>
 * It differs from them in that it works in multiline mode. Its regular expression
 * can span multiple lines and it checks this against the whole file at once.
 * The others work in single-line mode. Their single or multiple regular expressions
 * can only span one line. They check each of these against each line in the file in turn.
 * </p>
 * <p>
 * <b>Note:</b> Because of the different mode of operation there may be some
 * changes in the regular expressions used to achieve a particular end.
 * </p>
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
 * <p>
 * <b>Note:</b> Not all regular expression engines are created equal.
 * Some provide extra functions that others do not and some elements
 * of the syntax may vary. This check makes use of the
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/regex/package-summary.html">
 * java.util.regex package</a>; please check its documentation for details
 * of how to construct a regular expression to achieve a particular goal.
 * </p>
 * <p>
 * <b>Note:</b> When entering a regular expression as a parameter in
 * the XML config file you must also take into account the XML rules. e.g.
 * if you want to match a &lt; symbol you need to enter &amp;lt;.
 * The regular expression should be entered on one line.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specify the pattern to match against.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
 * </li>
 * <li>
 * Property {@code message} - Specify message which is used to notify about
 * violations, if empty then the default (hard-coded) message is used.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code illegalPattern} - Control whether the pattern is required or illegal.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
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
 * Property {@code ignoreComments} - Control whether to ignore matches found within comments.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <p>
 * The following examples are mainly copied from the other 3 checks mentioned above,
 * to show how the same results can be achieved using this check in place of them.
 * </p>
 * <p>
 * <b>To use like Required Regexp check:</b>
 * </p>
 * <p>
 * An example of how to configure the check to make sure a copyright statement
 * is included in the file:
 * </p>
 * <p>
 * The statement.
 * </p>
 * <pre>
 * // This code is copyrighted
 * </pre>
 * <p>
 * The check.
 * </p>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property name="format" value="// This code is copyrighted"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Your statement may be multiline.
 * </p>
 * <pre>
 * // This code is copyrighted
 * // (c) MyCompany
 * </pre>
 * <p>
 * Then the check would be.
 * </p>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property name="format" value="// This code is copyrighted\n// \(c\) MyCompany"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * <b>Note:</b> To search for parentheses () in a regular expression you must
 * escape them like \(\). This is required by the regexp engine, otherwise it will
 * think they are special instruction characters.
 * </p>
 * <p>
 * And to make sure it appears only once:
 * </p>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property name="format" value="// This code is copyrighted\n// \(c\) MyCompany"/&gt;
 *   &lt;property name="duplicateLimit" value="0"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * It can also be useful to attach a meaningful message to the check:
 * </p>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property name="format" value="// This code is copyrighted\n// \(c\) MyCompany"/&gt;
 *   &lt;property name="message" value="Copyright"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * <b>To use like illegal regexp check:</b>
 * </p>
 * <p>
 * An example of how to configure the check to make sure there are no calls to
 * {@code System.out.println}:
 * </p>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;!-- . matches any character, so we need to escape it and use \. to match dots. --&gt;
 *   &lt;property name="format" value="System\.out\.println"/&gt;
 *   &lt;property name="illegalPattern" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * You may want to make the above check ignore comments, like this:
 * </p>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property name="format" value="System\.out\.println"/&gt;
 *   &lt;property name="illegalPattern" value="true"/&gt;
 *   &lt;property name="ignoreComments" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of how to configure the check to find trailing whitespace at the end of a line:
 * </p>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property name="format" value="[ \t]+$"/&gt;
 *   &lt;property name="illegalPattern" value="true"/&gt;
 *   &lt;property name="message" value="Trailing whitespace"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of how to configure the check to find case-insensitive occurrences of "debug":
 * </p>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property name="format" value="(?i)debug"/&gt;
 *   &lt;property name="illegalPattern" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * <b>Note:</b> The (?i) at the beginning of the regular expression tells the
 * regexp engine to ignore the case.
 * </p>
 * <p>
 * There is also a feature to limit the number of violations reported.
 * When the limit is reached the check aborts with a message reporting that
 * the limit has been reached. The default limit setting is 100,
 * but this can be change as shown in the following example.
 * </p>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property name="format" value="(?i)debug"/&gt;
 *   &lt;property name="illegalPattern" value="true"/&gt;
 *   &lt;property name="errorLimit" value="1000"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * <b>To use like <a href="https://checkstyle.org/config_header.html#RegexpHeader">
 * RegexpHeader</a>:</b>
 * </p>
 * <p>
 * To configure the check to verify that each file starts with the following multiline header.
 * </p>
 * <p>
 * Note the following:
 * </p>
 * <ul>
 * <li>
 * \A means the start of the file.
 * </li>
 * <li>
 * The date can be any 4-digit number.
 * </li>
 * </ul>
 * <pre>
 * // Copyright (C) 2004 MyCompany
 * // All rights reserved
 * </pre>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property
 *     name="format"
 *     value="\A// Copyright \(C\) \d\d\d\d MyCompany\n// All rights reserved"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * A more complex example. Note how the import and javadoc multilines are handled,
 * there can be any number of them.
 * </p>
 * <pre>
 * ///////////////////////////////////////////////////////////////////////
 * // checkstyle:
 * // Checks Java source code for adherence to a set of rules.
 * // Copyright (C) 2004  Oliver Burn
 * // Last modification by $Author A.N.Other$
 * ///////////////////////////////////////////////////////////////////////
 *
 * package com.puppycrawl.checkstyle;
 *
 * import java.util.thing1;
 * import java.util.thing2;
 * import java.util.thing3;
 *
 * &#47;**
 * * javadoc line 1
 * * javadoc line 2
 * * javadoc line 3
 * *&#47;
 * </pre>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property
 *     name="format"
 *     value="\A/{71}\n// checkstyle:\n// Checks Java source code for
 *     adherence to a set of rules\.\n// Copyright \(C\) \d\d\d\d  Oliver Burn\n
 *     // Last modification by \$Author.*\$\n/{71}\n\npackage [\w\.]*;\n\n
 *     (import [\w\.]*;\n)*\n/\*\*\n( \*[^/]*\n)* \*&#47;"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * <b>More examples:</b>
 * </p>
 * <p>
 * The next 2 examples deal with the following example Java source file:
 * </p>
 * <pre>
 * &#47;*
 * * PID.java
 * *
 * * Copyright (c) 2001 ACME
 * * 123 Some St.
 * * Somewhere.
 * *
 * * This software is the confidential and proprietary information of ACME.
 * * ("Confidential Information"). You shall not disclose such
 * * Confidential Information and shall use it only in accordance with
 * * the terms of the license agreement you entered into with ACME.
 * *
 * * $Log: config_misc.xml,v $
 * * Revision 1.7  2007/01/16 12:16:35  oburn
 * * Removing all reference to mailing lists
 * *
 * * Revision 1.6  2005/12/25 16:13:10  o_sukhodolsky
 * * Fix for rfe 1248106 (TYPECAST is now accepted by NoWhitespaceAfter)
 * *
 * * Fix for rfe 953266 (thanks to Paul Guyot (pguyot) for submitting patch)
 * * IllegalType can be configured to accept some abstract classes which
 * * matches to regexp of illegal type names (property legalAbstractClassNames)
 * *
 * * TrailingComment now can be configured to accept some trailing comments
 * * (such as NOI18N) (property legalComment, rfe 1385344).
 * *
 * * Revision 1.5  2005/11/06 11:54:12  oburn
 * * Incorporate excellent patch [ 1344344 ] Consolidation of regexp checks.
 * *
 * * Revision 1.3.8.1  2005/10/11 14:26:32  someone
 * * Fix for bug 251.  The broken bit is fixed
 * *&#47;
 *
 * package com.acme.tools;
 *
 * import com.acme.thing1;
 * import com.acme.thing2;
 * import com.acme.thing3;
 *
 * &#47;**
 * *
 * * &lt;P&gt;
 * *   &lt;I&gt;This software is the confidential and proprietary information of
 * *   ACME (&lt;B&gt;"Confidential Information"&lt;/B&gt;). You shall not
 * *   disclose such Confidential Information and shall use it only in
 * *   accordance with the terms of the license agreement you entered into
 * *   with ACME.&lt;/I&gt;
 * * &lt;/P&gt;
 * *
 * * &amp;#169; copyright 2002 ACME
 * *
 * * &#64;author   Some Body
 * *&#47;
 * public class PID extends StateMachine implements WebObject.Constants {
 *
 * &#47;** javadoc. *&#47;
 * public static final int A_SETPOINT = 1;
 * .
 * .
 * .
 * } // class PID
 * </pre>
 * <p>
 * This checks for the presence of the header, the first 16 lines.
 * </p>
 * <p>
 * Note the following:
 * </p>
 * <ul>
 * <li>
 * Line 2 and 13 contain the file name. These are checked to make sure they
 * are the same, and that they match the class name.
 * </li>
 * <li>
 * The date can be any 4-digit number.
 * </li>
 * </ul>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property
 *     name="format"
 *     value="\A/\*\n \* (\w*)\.java\n \*\n \* Copyright \(c\)
 *     \d\d\d\d ACME\n \* 123 Some St\.\n \* Somewhere\.\n \*\n
 *     \* This software is the confidential and proprietary information
 *     of ACME\.\n \* \(&amp;quot;Confidential Information&amp;quot;\)\. You
 *     shall not disclose such\n \* Confidential Information and shall
 *     use it only in accordance with\n \* the terms of the license
 *     agreement you entered into with ACME\.\n \*\n
 *     \* \$Log: config_misc\.xml,v $
 *     \* Revision 1\.7  2007/01/16 12:16:35  oburn
 *     \* Removing all reference to mailing lists
 *     \* \
 *     \* Revision 1.6  2005/12/25 16:13:10  o_sukhodolsky
 *     \* Fix for rfe 1248106 \(TYPECAST is now accepted by NoWhitespaceAfter\)
 *     \* \
 *     \* Fix for rfe 953266 \(thanks to Paul Guyot \(pguyot\) for submitting patch\)
 *     \* IllegalType can be configured to accept some abstract classes which
 *     \* matches to regexp of illegal type names \(property legalAbstractClassNames\)
 *     \*
 *     \* TrailingComment now can be configured to accept some trailing comments
 *     \* \(such as NOI18N\) \(property legalComment, rfe 1385344\).
 *     \*
 *     \* Revision 1.5  2005/11/06 11:54:12  oburn
 *     \* Incorporate excellent patch \[ 1344344 \] Consolidation of regexp checks.
 *     \* \\n(.*\n)*([\w|\s]*( class | interface )\1)"/&gt;
 *   &lt;property name="message" value="Correct header not found"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * This checks for the presence of a copyright notice within the class javadoc, lines 24 to 37.
 * </p>
 * <pre>
 * &lt;module name="Regexp"&gt;
 *   &lt;property
 *     name="format"
 *     value="(/\*\*\n)( \*.*\n)*( \* &lt;P&gt;\n \*   &lt;I&gt;
 *     This software is the confidential and proprietary information of\n
 *     \*   ACME \(&lt;B&gt;&amp;quot;Confidential Information&amp;quot;&lt;/B&gt;
 *     \)\. You shall not\n \*   disclose such Confidential Information
 *     and shall use it only in\n \*   accordance with the terms of the
 *     license agreement you entered into\n \*   with ACME\.&lt;/I&gt;\n
 *     \* &lt;/P&gt;\n \*\n \* &amp;#169; copyright \d\d\d\d ACME\n
 *     \*\n \* &#64;author .*)(\n\s\*.*)*&#47;\n[\w|\s]*( class | interface )"/&gt;
 *   &lt;property name="message"
 *     value="Copyright in class/interface Javadoc"/&gt;
 *   &lt;property name="duplicateLimit" value="0"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * <b>Note:</b> To search for things that mean something in XML, like &lt;
 * you need to escape them like &amp;lt;. This is required so the XML parser
 * does not act on them, but instead passes the correct character to the regexp engine.
 * </p>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
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

    /** Tracks number of matches made. */
    private int matchCount;

    /** Tracks number of errors. */
    private int errorCount;

    /** Specify the pattern to match against. */
    private Pattern format = Pattern.compile("^$", Pattern.MULTILINE);

    /** The matcher. */
    private Matcher matcher;

    /**
     * Setter to specify message which is used to notify about violations,
     * if empty then the default (hard-coded) message is used.
     *
     * @param message custom message which should be used in report.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Setter to control whether to ignore matches found within comments.
     *
     * @param ignoreComments True if comments should be ignored.
     */
    public void setIgnoreComments(boolean ignoreComments) {
        this.ignoreComments = ignoreComments;
    }

    /**
     * Setter to control whether the pattern is required or illegal.
     *
     * @param illegalPattern True if pattern is not allowed.
     */
    public void setIllegalPattern(boolean illegalPattern) {
        this.illegalPattern = illegalPattern;
    }

    /**
     * Setter to specify the maximum number of violations before the check will abort.
     *
     * @param errorLimit the number of errors to report.
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
     */
    public void setDuplicateLimit(int duplicateLimit) {
        this.duplicateLimit = duplicateLimit;
        checkForDuplicates = duplicateLimit > DEFAULT_DUPLICATE_LIMIT;
    }

    /**
     * Setter to specify the pattern to match against.
     *
     * @param pattern the new pattern
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

    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    @Override
    public void beginTree(DetailAST rootAST) {
        matcher = format.matcher(getFileContents().getText().getFullText());
        matchCount = 0;
        errorCount = 0;
        findMatch();
    }

    /** Recursive method that finds the matches. */
    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    private void findMatch() {
        final boolean foundMatch = matcher.find();
        if (foundMatch) {
            final FileText text = getFileContents().getText();
            final LineColumn start = text.lineColumn(matcher.start());
            final int startLine = start.getLine();

            final boolean ignore = isIgnore(startLine, text, start);

            if (!ignore) {
                matchCount++;
                if (illegalPattern || checkForDuplicates
                        && matchCount - 1 > duplicateLimit) {
                    errorCount++;
                    logMessage(startLine);
                }
            }
            if (canContinueValidation(ignore)) {
                findMatch();
            }
        }
        else if (!illegalPattern && matchCount == 0) {
            logMessage(0);
        }
    }

    /**
     * Check if we can stop validation.
     *
     * @param ignore flag
     * @return true is we can continue
     */
    private boolean canContinueValidation(boolean ignore) {
        return errorCount <= errorLimit - 1
                && (ignore || illegalPattern || checkForDuplicates);
    }

    /**
     * Detect ignore situation.
     *
     * @param startLine position of line
     * @param text file text
     * @param start line column
     * @return true is that need to be ignored
     */
    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    private boolean isIgnore(int startLine, FileText text, LineColumn start) {
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
     */
    private void logMessage(int lineNumber) {
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

        if (illegalPattern) {
            log(lineNumber, MSG_ILLEGAL_REGEXP, msg);
        }
        else {
            if (lineNumber > 0) {
                log(lineNumber, MSG_DUPLICATE_REGEXP, msg);
            }
            else {
                log(lineNumber, MSG_REQUIRED_REGEXP, msg);
            }
        }
    }

}
