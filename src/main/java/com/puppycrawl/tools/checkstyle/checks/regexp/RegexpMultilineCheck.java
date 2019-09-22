////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
 * Implementation of a check that looks that matches across multiple lines in
 * any file type.
 */
@StatelessCheck
public class RegexpMultilineCheck extends AbstractFileSetCheck {

    /** The format of the regular expression to match. */
    private String format = "$.";
    /** The message to report for a match. */
    private String message;
    /** The minimum number of matches required per file. */
    private int minimum;
    /** The maximum number of matches required per file. */
    private int maximum;
    /** Whether to ignore case when matching. */
    private boolean ignoreCase;
    /** Whether to match across multiple lines. */
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
     * Sets the format of the regular expression to match.
     * @param format the format of the regular expression to match.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Sets the message to report for a match.
     * @param message the message to report for a match.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the minimum number of matches required per file.
     * @param minimum the minimum number of matches required per file.
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * Sets the maximum number of matches required per file.
     * @param maximum the maximum number of matches required per file.
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    /**
     * Sets whether to ignore case when matching.
     * @param ignoreCase whether to ignore case when matching.
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * Sets whether to match across multiple lines.
     * @param matchAcrossLines whether to match across multiple lines.
     */
    public void setMatchAcrossLines(boolean matchAcrossLines) {
        this.matchAcrossLines = matchAcrossLines;
    }

}
