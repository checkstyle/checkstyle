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

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Implementation of a check that looks for a single line in Java files.
 * Supports ignoring comments for matches.
 * @author Oliver Burn
 */
public class RegexpSinglelineJavaCheck extends Check {
    /** The detection options to use. */
    private final DetectorOptions options = new DetectorOptions(0, this);
    /** The detector to use. */
    private SinglelineDetector detector;
    /** The suppressor to use. */
    private final CommentSuppressor suppressor = new CommentSuppressor();

    @Override
    public int[] getDefaultTokens() {
        return new int[0];
    }

    @Override
    public void init() {
        super.init();
        detector = new SinglelineDetector(options);
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        suppressor.setCurrentContents(getFileContents());
        detector.processLines(Arrays.asList(getLines()));
    }

    /**
     * Set the format of the regular expression to match.
     * @param format the format of the regular expression to match.
     */
    public void setFormat(String format) {
        options.setFormat(format);
    }

    /**
     * Set the message to report for a match.
     * @param message the message to report for a match.
     */
    public void setMessage(String message) {
        options.setMessage(message);
    }

    /**
     * Set the minimum number of matches required per file.
     * @param minimum the minimum number of matches required per file.
     */
    public void setMinimum(int minimum) {
        options.setMinimum(minimum);
    }

    /**
     * Set the maximum number of matches required per file.
     * @param maximum the maximum number of matches required per file.
     */
    public void setMaximum(int maximum) {
        options.setMaximum(maximum);
    }

    /**
     * Set whether to ignore case when matching.
     * @param ignore whether to ignore case when matching.
     */
    public void setIgnoreCase(boolean ignore) {
        options.setIgnoreCase(ignore);
    }

    /**
     * Set whether to ignore comments when matching.
     * @param ignore whether to ignore comments when matching.
     */
    public void setIgnoreComments(boolean ignore) {
        if (ignore) {
            options.setSuppressor(suppressor);
        }
        else {
            options.setSuppressor(NeverSuppress.INSTANCE);
        }
    }
}
