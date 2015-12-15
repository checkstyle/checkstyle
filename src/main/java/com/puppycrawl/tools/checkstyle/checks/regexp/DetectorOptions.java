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

import org.apache.commons.lang3.ObjectUtils;

import com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter;

/**
 * Options for a detector.
 * @author Oliver Burn
 */
public final class DetectorOptions {

    /** Flags to compile a regular expression with. See {@link Pattern#flags()}. */
    private final int compileFlags;
    /** Used for reporting violations. */
    private final AbstractViolationReporter reporter;
    /**
     * Format of the regular expression to check for.
     */
    private final String format;
    /** The message to report on detection. If blank, then use the format. */
    private final String message;
    /** Minimum number of times regular expression should occur in a file. */
    private final int minimum;
    /** Maximum number of times regular expression should occur in a file. */
    private final int maximum;
    /** Whether to ignore case when matching. */
    private final boolean ignoreCase;
    /** Used to determine whether to suppress a detected match. */
    private final MatchSuppressor suppressor;
    /** Pattern created from format. Lazily initialized. */
    private Pattern pattern;

    /**
     * Constructor to create the DetectorOptions object from builder.
     * @param builder builder.
     */
    private DetectorOptions(Builder builder) {
        compileFlags = builder.compilationFlags;
        reporter = builder.reporter;
        format = builder.regexpFormat;
        message = builder.reportMessage;
        minimum = builder.min;
        maximum = builder.max;
        ignoreCase = builder.ignoreCaseFlag;
        suppressor = builder.matchSuppressor;
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
     * The pattern to use when matching.
     * @return the pattern to use when matching.
     */
    public Pattern getPattern() {
        if (pattern != null) {
            return pattern;
        }
        int options = compileFlags;

        if (ignoreCase) {
            options |= Pattern.CASE_INSENSITIVE;
        }
        pattern = Pattern.compile(format, options);
        return pattern;
    }

    /** Class which implements Builder pattern to build DetectorOptions instance. */
    public static final class Builder {

        /** Used for reporting violations. */
        private final AbstractViolationReporter reporter;
        /** Flags to compile a regular expression with. See {@link Pattern#flags()}. */
        private int compilationFlags;
        /**
         * Format of the regular expression to check for.
         */
        private String regexpFormat;
        /** The message to report on detection. If blank, then use the format. */
        private String reportMessage;
        /** Minimum number of times regular expression should occur in a file. */
        private int min;
        /** Maximum number of times regular expression should occur in a file. */
        private int max;
        /** Whether to ignore case when matching. */
        private boolean ignoreCaseFlag;
        /** Used to determine whether to suppress a detected match. */
        private MatchSuppressor matchSuppressor;

        /**
         * Constructor to create the Builder object with the required field.
         * @param reporter for reporting violations.
         */
        public Builder(AbstractViolationReporter reporter) {
            this.reporter = reporter;
        }

        /**
         * Specifies the compile flags to compile a regular expression with
         * and returns Builder object.
         * @param val the format to use when matching lines.
         * @return Builder object.
         */
        public Builder compileFlags(int val) {
            compilationFlags = val;
            return this;
        }

        /**
         * Specifies the format to use when matching lines and returns Builder object.
         * @param val the format to use when matching lines.
         * @return Builder object.
         */
        public Builder format(String val) {
            regexpFormat = val;
            return this;
        }

        /**
         * Specifies message to use when reporting a match and returns Builder object.
         * @param val message to use when reporting a match.
         * @return Builder object.
         */
        public Builder message(String val) {
            reportMessage = val;
            return this;
        }

        /**
         * Specifies the minimum allowed number of detections and returns Builder object.
         * @param val the minimum allowed number of detections.
         * @return Builder object.
         */
        public Builder minimum(int val) {
            min = val;
            return this;
        }

        /**
         * Specifies the maximum allowed number of detections and returns Builder object.
         * @param val the maximum allowed number of detections.
         * @return Builder object.
         */
        public Builder maximum(int val) {
            max = val;
            return this;
        }

        /**
         * Specifies whether to ignore case when matching and returns Builder object.
         * @param val whether to ignore case when matching.
         * @return Builder object.
         */
        public Builder ignoreCase(boolean val) {
            ignoreCaseFlag = val;
            return this;
        }

        /**
         * Specifies the suppressor to use and returns Builder object.
         * @param val the suppressor to use.
         * @return current instance
         */
        public Builder suppressor(MatchSuppressor val) {
            matchSuppressor = val;
            return this;
        }

        /**
         * Returns new DetectorOptions instance.
         * @return DetectorOptions instance.
         */
        public DetectorOptions build() {
            reportMessage = ObjectUtils.defaultIfNull(reportMessage, "");
            matchSuppressor = ObjectUtils.defaultIfNull(matchSuppressor, NeverSuppress.INSTANCE);
            return new DetectorOptions(this);
        }
    }
}
