///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
 * <p>
 * Checks that a specified pattern matches a single-line in Java files.
 * </p>
 * <p>
 * This class is variation on
 * <a href="https://checkstyle.org/config_regexp.html#RegexpSingleline">RegexpSingleline</a>
 * for detecting single-lines that match a supplied regular expression in Java files.
 * It supports suppressing matches in Java comments.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specify the format of the regular expression to match.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "$."}.
 * </li>
 * <li>
 * Property {@code message} - Specify the message which is used to notify about
 * violations, if empty then default (hard-coded) message is used.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code ignoreCase} - Control whether to ignore case when searching.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code minimum} - Specify the minimum number of matches required in each file.
 * Type is {@code int}.
 * Default value is {@code 0}.
 * </li>
 * <li>
 * Property {@code maximum} - Specify the maximum number of matches required in each file.
 * Type is {@code int}.
 * Default value is {@code 0}.
 * </li>
 * <li>
 * Property {@code ignoreComments} - Control whether to ignore text in comments when searching.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 *   To configure the default check:
 * </p>
 * <pre>
 * &lt;module name=&quot;RegexpSinglelineJava&quot;/&gt;
 * </pre>
 * <p>
 *   This configuration does not match to anything,
 *   so we do not provide any code example for it
 *   as no violation will ever be reported.
 * </p>
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
 * <p>Example:</p>
 * <pre>
 * System.out.println(""); // violation, instruction matches illegal pattern
 * System.out.
 *   println(""); // OK
 * &#47;* System.out.println *&#47; // OK, comments are ignored
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
 * <p>Example:</p>
 * <pre>
 * int debug = 0; // violation, variable name matches illegal pattern
 * public class Debug { // violation, class name matches illegal pattern
 * &#47;* this is for de
 *   bug only; *&#47; // OK
 * </pre>
 * <p>
 * To configure the check to find occurrences of
 * &quot;\.read(.*)|\.write(.*)&quot;
 * and display &quot;IO found&quot; for each violation.
 * </p>
 * <pre>
 * &lt;module name=&quot;RegexpSinglelineJava&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;\.read(.*)|\.write(.*)&quot;/&gt;
 *   &lt;property name=&quot;message&quot; value=&quot;IO found&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * FileReader in = new FileReader("path/to/input");
 * int ch = in.read(); // violation
 * while(ch != -1) {
 *   System.out.print((char)ch);
 *   ch = in.read(); // violation
 * }
 *
 * FileWriter out = new FileWriter("path/to/output");
 * out.write("something"); // violation
 * </pre>
 * <p>
 * To configure the check to find occurrences of
 * &quot;\.log(.*)&quot;. We want to allow a maximum of 2 occurrences.
 * </p>
 * <pre>
 * &lt;module name=&quot;RegexpSinglelineJava&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;\.log(.*)&quot;/&gt;
 *   &lt;property name=&quot;maximum&quot; value=&quot;2&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class Foo{
 *   public void bar(){
 *     Logger.log("first"); // OK, first occurrence is allowed
 *     Logger.log("second"); // OK, second occurrence is allowed
 *     Logger.log("third"); // violation
 *     System.out.println("fourth");
 *     Logger.log("fifth"); // violation
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to find all occurrences of
 * &quot;public&quot;. We want to ignore comments,
 * display &quot;public member found&quot; for each violation
 * and say if less than 2 occurrences.
 * </p>
 * <pre>
 * &lt;module name=&quot;RegexpSinglelineJava&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;public&quot;/&gt;
 *   &lt;property name=&quot;minimum&quot; value=&quot;2&quot;/&gt;
 *   &lt;property name=&quot;message&quot; value=&quot;public member found&quot;/&gt;
 *   &lt;property name=&quot;ignoreComments&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class Foo{ // violation, file contains less than 2 occurrences of "public"
 *   private int a;
 *   &#47;* public comment *&#47; // OK, comment is ignored
 *   private void bar1() {}
 *   public void bar2() {} // violation
 * }
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class Foo{
 *   private int a;
 *  &#47;* public comment *&#47; // OK, comment is ignored
 *   public void bar1() {} // violation
 *   public void bar2() {} // violation
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code regexp.exceeded}
 * </li>
 * <li>
 * {@code regexp.minimum}
 * </li>
 * </ul>
 *
 * @since 6.0
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
    @SuppressWarnings("deprecation")
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
