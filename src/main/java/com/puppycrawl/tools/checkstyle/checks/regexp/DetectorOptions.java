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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter;

/**
 * Options for a detector.
 * @author Oliver Burn
 */
class DetectorOptions {
    /**
     * Flags to compile a regular expression with.
     * See {@link Pattern#flags()}.
     */
    private final int compileFlags;
    /** Used for reporting violations. */
    private final AbstractViolationReporter reporter;
    /**
     * Format of the regular expression to check for. Default value is pattern that never matches
     * any string.
     */
    private String format = "$.";
    /** The message to report on detection. If blank, then use the format. */
    private String message = "";
    /** Minimum number of times regular expression should occur in a file. */
    private int minimum;
    /** Maximum number of times regular expression should occur in a file. */
    private int maximum;
    /** Whether to ignore case when matching. */
    private boolean ignoreCase;
    /** Used to determine whether to suppress a detected match. */
    private MatchSuppressor suppressor = NeverSuppress.INSTANCE;

    /**
     * Creates an instance.
     * @param compileFlags the flags to create the regular expression with.
     * @param reporter used to report violations.
     */
    public DetectorOptions(int compileFlags,
            AbstractViolationReporter reporter) {
        this.compileFlags = compileFlags;
        this.reporter = reporter;
    }

    /**
     * The format to use when matching lines.
     * @param format the format to use when matching lines.
     * @return current instance
     */
    public DetectorOptions setFormat(String format) {
        this.format = format;
        return this;
    }

    /**
     * Message to use when reporting a match.
     * @param message message to use when reporting a match.
     * @return current instance.
     */
    public DetectorOptions setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Set the minimum allowed number of detections.
     * @param minimum the minimum allowed number of detections.
     * @return current instance
     */
    public DetectorOptions setMinimum(int minimum) {
        this.minimum = minimum;
        return this;
    }

    /**
     * Set the maximum allowed number of detections.
     * @param maximum the maximum allowed number of detections.
     * @return current instance
     */
    public DetectorOptions setMaximum(int maximum) {
        this.maximum = maximum;
        return this;
    }

    /**
     * Set the suppressor to use.
     * @param sup the suppressor to use.
     * @return current instance
     */
    public DetectorOptions setSuppressor(MatchSuppressor sup) {
        suppressor = sup;
        return this;
    }

    /**
     * Set whether to ignore case when matching.
     * @param ignore whether to ignore case when matching.
     * @return current instance
     */
    public DetectorOptions setIgnoreCase(boolean ignore) {
        ignoreCase = ignore;
        return this;
    }

    /**
     * Format of the regular expression.
     * @return format of the regular expression.
     */
    public String getFormat() {
        return format;
    }

    /**
     * The violation reporter to use.
     * @return the violation reporter to use.
     */
    public AbstractViolationReporter getReporter() {
        return reporter;
    }

    /**
     * The message to report errors with.
     * @return the message to report errors with.
     */
    public String getMessage() {
        return message;
    }

    /**
     * The minimum number of allowed detections.
     * @return the minimum number of allowed detections.
     */
    public int getMinimum() {
        return minimum;
    }

    /**
     * The maximum number of allowed detections.
     * @return the maximum number of allowed detections.
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * The suppressor to use.
     * @return the suppressor to use.
     */
    public MatchSuppressor getSuppressor() {
        return suppressor;
    }

    /**
     * Whether to ignore case when matching.
     * @return whether to ignore case when matching.
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    /**
     * The pattern to use when matching.
     * @return the pattern to use when matching.
     */
    public Pattern getPattern() {
        final int options = isIgnoreCase() ? compileFlags
                | Pattern.CASE_INSENSITIVE : compileFlags;
        return Pattern.compile(format, options);
    }
}
