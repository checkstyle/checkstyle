////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Implementation of a check that looks for a single line in Java files.
 * Supports ignoring comments for matches.
 * @author Oliver Burn
 */
public class RegexpSinglelineJavaCheck extends AbstractCheck {

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
    /** Suppress comments. **/
    private boolean ignoreComments;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        MatchSuppressor supressor = null;
        if (ignoreComments) {
            supressor = new CommentSuppressor(getFileContents());
        }

        final DetectorOptions options = DetectorOptions.newBuilder()
            .reporter(this)
            .compileFlags(0)
            .suppressor(supressor)
            .format(format)
            .message(message)
            .minimum(minimum)
            .maximum(maximum)
            .ignoreCase(ignoreCase)
            .build();
        final SinglelineDetector detector = new SinglelineDetector(options);
        detector.processLines(Arrays.asList(getLines()));
    }

    /**
     * Set the format of the regular expression to match.
     * @param format the format of the regular expression to match.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Set the message to report for a match.
     * @param message the message to report for a match.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Set the minimum number of matches required per file.
     * @param minimum the minimum number of matches required per file.
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * Set the maximum number of matches required per file.
     * @param maximum the maximum number of matches required per file.
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    /**
     * Set whether to ignore case when matching.
     * @param ignoreCase whether to ignore case when matching.
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * Set whether to ignore comments when matching.
     * @param ignore whether to ignore comments when matching.
     */
    public void setIgnoreComments(boolean ignore) {
        ignoreComments = ignore;
    }
}
