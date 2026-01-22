///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks that a specified pattern matches a single-line in Java files.
 * </div>
 *
 * <p>
 * This class is variation on
 * <a href="https://checkstyle.org/checks/regexp/regexpsingleline.html">
 * RegexpSingleline</a>
 * for detecting single-lines that match a supplied regular expression in Java files.
 * It supports suppressing matches in Java comments.
 * </p>
 *
 * @since 5.0
 */
@StatelessCheck
public class RegexpSinglelineJavaCheck extends AbstractCheck {

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
    /** Control whether to ignore text in comments when searching. */
    private boolean ignoreComments;

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
    @Override
    @SuppressWarnings("deprecation")
    public void beginTree(DetailAST rootAST) {
        MatchSuppressor suppressor = null;
        if (ignoreComments) {
            suppressor = new CommentSuppressor(getFileContents());
        }

        final DetectorOptions options = DetectorOptions.newBuilder()
            .reporter(this)
            .suppressor(suppressor)
            .format(format)
            .message(message)
            .minimum(minimum)
            .maximum(maximum)
            .ignoreCase(ignoreCase)
            .build();
        final SinglelineDetector detector = new SinglelineDetector(options);
        detector.processLines(getFileContents().getText());
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
     * @since 6.0
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
     * Setter to control whether to ignore text in comments when searching.
     *
     * @param ignore whether to ignore text in comments when searching.
     * @since 5.0
     */
    public void setIgnoreComments(boolean ignore) {
        ignoreComments = ignore;
    }

}
