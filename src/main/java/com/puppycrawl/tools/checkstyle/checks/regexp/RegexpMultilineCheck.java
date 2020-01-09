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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import java.io.File;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <p>
 * Checks that a specified pattern matches across multiple lines in any file type.
 * </p>
 * <p>
 * Rationale: This check can be used to when the regular expression can be span multiple lines.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specify the format of the regular expression to match.
 * Default value is {@code "$."}.
 * </li>
 * <li>
 * Property {@code message} - Specify the message which is used to notify about
 * violations, if empty then default (hard-coded) message is used.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code ignoreCase} - Control whether to ignore case when searching.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code minimum} - Specify the minimum number of matches required in each file.
 * Default value is {@code 0}.
 * </li>
 * <li>
 * Property {@code maximum} - Specify the maximum number of matches required in each file.
 * Default value is {@code 0}.
 * </li>
 * <li>
 * Property {@code matchAcrossLines} - Control whether to match expressions
 * across multiple lines.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code fileExtensions} - Specify the file type extension of files to process.
 * Default value is {@code all files}.
 * </li>
 * </ul>
 * <p>
 * To configure the check to find calls to print to the console:
 * </p>
 * <pre>
 * &lt;module name="RegexpMultiline"&gt;
 *   &lt;property name="format"
 *     value="System\.(out)|(err)\.print(ln)?\("/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to match text that spans multiple lines,
 * like normal code in a Java file:
 * </p>
 * <pre>
 * &lt;module name="RegexpMultiline"&gt;
 *   &lt;property name="matchAcrossLines" value="true"/&gt;
 *   &lt;property name="format" value="System\.out.*print\("/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of violation from the above config:
 * </p>
 * <pre>
 * void method() {
 *   System.out. // violation
 *   print("Example");
 *   System.out.
 *   print("Example");
 * }
 * </pre>
 * <p>
 * Note: Beware of the greedy regular expression used in the above example.
 * {@code .*} will match as much as possible and not produce multiple violations
 * in the file if multiple groups of lines could match the expression. To prevent
 * an expression being too greedy, avoid overusing matching all text or allow it
 * to be optional, like {@code .*?}. Changing the example expression to not be
 * greedy will allow multiple violations in the example to be found in the same file.
 * </p>
 *
 * @since 5.0
 */
@StatelessCheck
public class RegexpMultilineCheck extends AbstractFileSetCheck {

    /** Specify the format of the regular expression to match. */
    private String format = "$.";
    /**
     * Specify the message which is used to notify about violations,
     * if empty then default (hard-coded) message is used.
     */
    private String message;
    /** Specify the minimum number of matches required in each file. */
    private int minimum;
    /** Specify the maximum number of matches required in each file. */
    private int maximum;
    /** Control whether to ignore case when searching. */
    private boolean ignoreCase;
    /** Control whether to match expressions across multiple lines. */
    private boolean matchAcrossLines;

    /** The detector to use. */
    private MultilineDetector detector;

    @Override
    public void beginProcessing(String charset) {
        final DetectorOptions options = DetectorOptions.newBuilder()
            .reporter(this)
            .compileFlags(getRegexCompileFlags())
            .format(format)
            .message(message)
            .minimum(minimum)
            .maximum(maximum)
            .ignoreCase(ignoreCase)
            .build();
        detector = new MultilineDetector(options);
    }

    @Override
    protected void processFiltered(File file, FileText fileText) {
        detector.processLines(fileText);
    }

    /**
     * Retrieves the compile flags for the regular expression being built based
     * on {@code matchAcrossLines}.
     * @return The compile flags.
     */
    private int getRegexCompileFlags() {
        final int result;

        if (matchAcrossLines) {
            result = Pattern.DOTALL;
        }
        else {
            result = Pattern.MULTILINE;
        }

        return result;
    }

    /**
     * Setter to specify the format of the regular expression to match.
     *
     * @param format the format of the regular expression to match.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Setter to specify the message which is used to notify about violations,
     * if empty then default (hard-coded) message is used.
     *
     * @param message the message to report for a match.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Setter to specify the minimum number of matches required in each file.
     *
     * @param minimum the minimum number of matches required in each file.
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * Setter to specify the maximum number of matches required in each file.
     *
     * @param maximum the maximum number of matches required in each file.
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    /**
     * Setter to control whether to ignore case when searching.
     *
     * @param ignoreCase whether to ignore case when searching.
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * Setter to control whether to match expressions across multiple lines.
     *
     * @param matchAcrossLines whether to match expressions across multiple lines.
     */
    public void setMatchAcrossLines(boolean matchAcrossLines) {
        this.matchAcrossLines = matchAcrossLines;
    }

}
