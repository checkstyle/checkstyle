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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <p>
 * Checks that a specified pattern matches a single line in any file type.
 * </p>
 * <p>
 * Rationale: This check can be used to prototype checks and to find common bad
 * practice such as calling {@code ex.printStacktrace()},
 * {@code System.out.println()}, {@code System.exit()}, etc.
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
 * Property {@code fileExtensions} - Specify the file type extension of files to process.
 * Default value is {@code all files}.
 * </li>
 * </ul>
 * <p>
 * To configure the check to find trailing whitespace at the end of a line:
 * </p>
 * <pre>
 * &lt;module name="RegexpSingleline"&gt;
 *   &lt;!-- \s matches whitespace character, $ matches end of line. --&gt;
 *   &lt;property name="format" value="\s+$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to find trailing whitespace at the end of a line,
 * with some <i>slack</i> of allowing two occurrences per file:
 * </p>
 * <pre>
 * &lt;module name="RegexpSingleline"&gt;
 *   &lt;property name="format" value="\s+$"/&gt;
 *   &lt;!-- next line not required as 0 is the default --&gt;
 *   &lt;property name="minimum" value="0"/&gt;
 *   &lt;property name="maximum" value="2"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of how to configure the check to make sure a copyright statement
 * is included in the file:
 * </p>
 * <pre>
 * &lt;module name="RegexpSingleline"&gt;
 *   &lt;property name="format" value="This file is copyrighted"/&gt;
 *   &lt;property name="minimum" value="1"/&gt;
 *   &lt;!--  Need to specify a maximum, so 10 times is more than enough. --&gt;
 *   &lt;property name="maximum" value="10"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @since 5.0
 */
@StatelessCheck
public class RegexpSinglelineCheck extends AbstractFileSetCheck {

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

    /** The detector to use. */
    private SinglelineDetector detector;

    @Override
    public void beginProcessing(String charset) {
        final DetectorOptions options = DetectorOptions.newBuilder()
            .reporter(this)
            .compileFlags(0)
            .format(format)
            .message(message)
            .minimum(minimum)
            .maximum(maximum)
            .ignoreCase(ignoreCase)
            .build();
        detector = new SinglelineDetector(options);
    }

    @Override
    protected void processFiltered(File file, FileText fileText) {
        detector.processLines(fileText);
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

}
