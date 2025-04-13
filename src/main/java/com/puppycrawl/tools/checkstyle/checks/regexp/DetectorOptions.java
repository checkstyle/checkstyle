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

import java.util.Optional;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter;

/**
 * Options for a detector.
 */
public final class DetectorOptions {

    /**
     * Flags to compile a regular expression with.
     * See {@link Pattern#flags()}.
     */
    private int compileFlags;
    /** Used for reporting violations. */
    private AbstractViolationReporter reporter;
    /**
     * Format of the regular expression to check for.
     */
    private String format;
    /** The message to report on detection. If blank, then use the format. */
    private String message;
    /** Minimum number of times regular expression should occur in a file. */
    private int minimum;
    /** Maximum number of times regular expression should occur in a file. */
    private int maximum;
    /** Whether to ignore case when matching. */
    private boolean ignoreCase;
    /** Used to determine whether to suppress a detected match. */
    private MatchSuppressor suppressor;
    /** Pattern created from format. Lazily initialized. */
    private Pattern pattern;

    /** Default constructor.*/
    private DetectorOptions() {
    }

    /**
     * Returns new Builder object.
     *
     * @return Builder object.
     */
    public static Builder newBuilder() {
        return new DetectorOptions().new Builder();
    }

    /**
     * Format of the regular expression.
     *
     * @return format of the regular expression.
     */
    public String getFormat() {
        return format;
    }

    /**
     * The violation reporter to use.
     *
     * @return the violation reporter to use.
     */
    public AbstractViolationReporter getReporter() {
        return reporter;
    }

    /**
     * The message to report violations with.
     *
     * @return the message to report violations with.
     */
    public String getMessage() {
        return message;
    }

    /**
     * The minimum number of allowed detections.
     *
     * @return the minimum number of allowed detections.
     */
    public int getMinimum() {
        return minimum;
    }

    /**
     * The maximum number of allowed detections.
     *
     * @return the maximum number of allowed detections.
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * The suppressor to use.
     *
     * @return the suppressor to use.
     */
    public MatchSuppressor getSuppressor() {
        return suppressor;
    }

    /**
     * The pattern to use when matching.
     *
     * @return the pattern to use when matching.
     */
    public Pattern getPattern() {
        return pattern;
    }

    /** Class which implements Builder pattern to build DetectorOptions instance. */
    public final class Builder {

        /**
         * Specifies the violation reporter and returns Builder object.
         *
         * @param val for reporting violations.
         * @return Builder object.
         * @noinspection ReturnOfInnerClass
         * @noinspectionreason ReturnOfInnerClass - builder is only used in enclosing class
         */
        public Builder reporter(AbstractViolationReporter val) {
            reporter = val;
            return this;
        }

        /**
         * Specifies the compile-flags to compile a regular expression with
         * and returns Builder object.
         *
         * @param val the format to use when matching lines.
         * @return Builder object.
         * @noinspection ReturnOfInnerClass
         * @noinspectionreason ReturnOfInnerClass - builder is only used in enclosing class
         */
        public Builder compileFlags(int val) {
            compileFlags = val;
            return this;
        }

        /**
         * Specifies the format to use when matching lines and returns Builder object.
         *
         * @param val the format to use when matching lines.
         * @return Builder object.
         * @noinspection ReturnOfInnerClass
         * @noinspectionreason ReturnOfInnerClass - builder is only used in enclosing class
         */
        public Builder format(String val) {
            format = val;
            return this;
        }

        /**
         * Specifies message to use when reporting a match and returns Builder object.
         *
         * @param val message to use when reporting a match.
         * @return Builder object.
         * @noinspection ReturnOfInnerClass
         * @noinspectionreason ReturnOfInnerClass - builder is only used in enclosing class
         */
        public Builder message(String val) {
            message = val;
            return this;
        }

        /**
         * Specifies the minimum allowed number of detections and returns Builder object.
         *
         * @param val the minimum allowed number of detections.
         * @return Builder object.
         * @noinspection ReturnOfInnerClass
         * @noinspectionreason ReturnOfInnerClass - builder is only used in enclosing class
         */
        public Builder minimum(int val) {
            minimum = val;
            return this;
        }

        /**
         * Specifies the maximum allowed number of detections and returns Builder object.
         *
         * @param val the maximum allowed number of detections.
         * @return Builder object.
         * @noinspection ReturnOfInnerClass
         * @noinspectionreason ReturnOfInnerClass - builder is only used in enclosing class
         */
        public Builder maximum(int val) {
            maximum = val;
            return this;
        }

        /**
         * Specifies whether to ignore case when matching and returns Builder object.
         *
         * @param val whether to ignore case when matching.
         * @return Builder object.
         * @noinspection ReturnOfInnerClass, BooleanParameter
         * @noinspectionreason ReturnOfInnerClass - builder is only used in enclosing class
         * @noinspectionreason BooleanParameter - check fields are boolean
         */
        public Builder ignoreCase(boolean val) {
            ignoreCase = val;
            return this;
        }

        /**
         * Specifies the suppressor to use and returns Builder object.
         *
         * @param val the suppressor to use.
         * @return current instance
         * @noinspection ReturnOfInnerClass
         * @noinspectionreason ReturnOfInnerClass - builder is only used in enclosing class
         */
        public Builder suppressor(MatchSuppressor val) {
            suppressor = val;
            return this;
        }

        /**
         * Returns new DetectorOptions instance.
         *
         * @return DetectorOptions instance.
         */
        public DetectorOptions build() {
            message = Optional.ofNullable(message).orElse("");
            suppressor = Optional.ofNullable(suppressor).orElse(NeverSuppress.INSTANCE);
            pattern = Optional.ofNullable(format).map(this::createPattern).orElse(null);
            return DetectorOptions.this;
        }

        /**
         * Creates pattern to use by DetectorOptions instance.
         *
         * @param formatValue the format to use.
         * @return Pattern object.
         */
        private Pattern createPattern(String formatValue) {
            int options = compileFlags;
            if (ignoreCase) {
                options |= Pattern.CASE_INSENSITIVE;
            }
            return Pattern.compile(formatValue, options);
        }

    }

}
