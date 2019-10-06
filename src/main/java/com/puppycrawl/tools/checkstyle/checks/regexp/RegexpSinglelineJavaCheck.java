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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * A check for detecting single lines that match a supplied
 * regular expression in Java files.
 * </p>
 * <p>
 * This class is variation on
 * <a href="https://checkstyle.org/config_regexp.html#RegexpSingleline">RegexpSingleline</a>
 * for detecting single lines that match a supplied regular expression in Java files.
 * It supports suppressing matches in Java comments.
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
 * Property {@code ignoreComments} - Control whether to ignore text in comments when searching.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the check for calls to {@code System.out.println}, except in comments:
 * </p>
 * <pre>
 * &lt;module name="RegexpSinglelineJava"&gt;
 *   &lt;!-- . matches any character, so we need to
 *        escape it and use \. to match dots. --&gt;
 *   &lt;property name="format" value="System\.out\.println"/&gt;
 *   &lt;property name="ignoreComments" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to find case-insensitive occurrences of "debug":
 * </p>
 * <pre>
 * &lt;module name="RegexpSinglelineJava"&gt;
 *   &lt;property name="format" value="debug"/&gt;
 *   &lt;property name="ignoreCase" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @since 6.0
 */
@StatelessCheck
public class RegexpSinglelineJavaCheck extends AbstractCheck {

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

    @Override
    public void beginTree(DetailAST rootAST) {
        MatchSuppressor suppressor = null;
        if (ignoreComments) {
            suppressor = new CommentSuppressor(getFileContents());
        }

        final DetectorOptions options = DetectorOptions.newBuilder()
            .reporter(this)
            .compileFlags(0)
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
     * Setter to control whether to ignore text in comments when searching.
     *
     * @param ignore whether to ignore text in comments when searching.
     */
    public void setIgnoreComments(boolean ignore) {
        ignoreComments = ignore;
    }

}
