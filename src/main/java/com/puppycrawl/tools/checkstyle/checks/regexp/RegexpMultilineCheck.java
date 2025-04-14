///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.regexp;

import java.nio.file.Path;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <div>
 * Checks that a specified pattern matches across multiple lines in any file type.
 * </div>
 *
 * <p>
 * Rationale: This check can be used to when the regular expression can be span multiple lines.
 * </p>
 * <ul>
 * <li>
 * Property {@code fileExtensions} - Specify the file extensions of the files to process.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code format} - Specify the format of the regular expression to match.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "$."}.
 * </li>
 * <li>
 * Property {@code ignoreCase} - Control whether to ignore case when searching.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code matchAcrossLines} - Control whether to match expressions
 * across multiple lines.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code maximum} - Specify the maximum number of matches required in each file.
 * Type is {@code int}.
 * Default value is {@code 0}.
 * </li>
 * <li>
 * Property {@code message} - Specify the message which is used to notify about
 * violations, if empty then default (hard-coded) message is used.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code minimum} - Specify the minimum number of matches required in each file.
 * Type is {@code int}.
 * Default value is {@code 0}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code regexp.StackOverflowError}
 * </li>
 * <li>
 * {@code regexp.empty}
 * </li>
 * <li>
 * {@code regexp.exceeded}
 * </li>
 * <li>
 * {@code regexp.minimum}
 * </li>
 * </ul>
 *
 * @since 5.0
 */
@StatelessCheck
public class RegexpMultilineCheck extends AbstractFileSetCheck {

    /** Specify the format of the regular expression to match. */
    @XdocsPropertyType(PropertyType.PATTERN)
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
    protected void processFiltered(Path file, FileText fileText) {
        detector.processLines(fileText);
    }

    /**
     * Retrieves the compile-flags for the regular expression being built based
     * on {@code matchAcrossLines}.
     *
     * @return The compile-flags.
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
     * @since 5.0
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Setter to specify the message which is used to notify about violations,
     * if empty then default (hard-coded) message is used.
     *
     * @param message the message to report for a match.
     * @since 5.0
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Setter to specify the minimum number of matches required in each file.
     *
     * @param minimum the minimum number of matches required in each file.
     * @since 5.0
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * Setter to specify the maximum number of matches required in each file.
     *
     * @param maximum the maximum number of matches required in each file.
     * @since 5.0
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    /**
     * Setter to control whether to ignore case when searching.
     *
     * @param ignoreCase whether to ignore case when searching.
     * @since 5.0
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * Setter to control whether to match expressions across multiple lines.
     *
     * @param matchAcrossLines whether to match expressions across multiple lines.
     * @since 8.25
     */
    public void setMatchAcrossLines(boolean matchAcrossLines) {
        this.matchAcrossLines = matchAcrossLines;
    }

}
