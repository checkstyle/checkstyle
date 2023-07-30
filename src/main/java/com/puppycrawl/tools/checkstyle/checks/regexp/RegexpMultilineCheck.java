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

import java.io.File;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <p>
 * Checks that a specified pattern matches across multiple lines in any file type.
 * </p>
 * <p>
 * Rationale: This check can be used to when the regular expression can be span multiple lines.
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
 * Property {@code matchAcrossLines} - Control whether to match expressions
 * across multiple lines.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code fileExtensions} - Specify the file type extension of files to process.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 * <p>
 * To run the check with its default configuration (no matches will be):
 * </p>
 * <pre>
 * &lt;module name=&quot;RegexpMultiline&quot;/&gt;
 * </pre>
 * <p>Example: </p>
 * <pre>
 * void method() {
 *   int i = 5; // OK
 *   System.out.println(i); // OK
 * }
 * </pre>
 * <p>
 * To configure the check to find calls to print to the console:
 * </p>
 * <pre>
 * &lt;module name="RegexpMultiline"&gt;
 *   &lt;property name="format" value="System\.(out)|(err)\.print(ln)?\("/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *   System.out.print("Example");   // violation
 *   System.err.println("Example"); // violation
 *   System.out.print
 *     ("Example");                 // violation
 *   System.err.println
 *     ("Example");          // OK
 *   System
 *   .out.print("Example");  // OK
 *   System
 *   .err.println("Example");       // violation
 *   System.
 *   out.print("Example");   // OK
 *   System.
 *   err.println("Example");        // violation
 * }
 * </pre>
 * <p>
 * To configure the check to match text that spans multiple lines,
 * like normal code in a Java file:
 * </p>
 * <pre>
 * &lt;module name="RegexpMultiline"&gt;
 *   &lt;property name="matchAcrossLines" value="true"/&gt;
 *   &lt;property name="format" value="System\.out.*?print\("/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *   System.out.print("Example");  // violation
 *   System.err.println("Example");
 *   System.out.print              // violation
 *     ("Example");
 *   System.err.println
 *     ("Example");
 *   System
 *   .out.print("Example");
 *   System
 *   .err.println("Example");
 *   System.
 *   out.print("Example");
 *   System.
 *   err.println("Example");
 * }
 * </pre>
 * <p>
 * Note: Beware of the greedy regular expression used in the above example.
 * {@code .*} will match as much as possible and not produce multiple violations
 * in the file if multiple groups of lines could match the expression. To prevent
 * an expression being too greedy, avoid overusing matching all text or allow it
 * to be optional, like {@code .*?}. Changing the example expression to not be
 * greedy will allow multiple violations in the example to be found in the same file.
 * </p>
 * <p>
 * To configure the check to match a maximum of three test strings:
 * </p>
 * <pre>
 * &lt;module name=&quot;RegexpMultiline&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;Test #[0-9]+:[A-Za-z ]+&quot;/&gt;
 *   &lt;property name=&quot;ignoreCase&quot; value=&quot;true&quot;/&gt;
 *   &lt;property name=&quot;maximum&quot; value=&quot;3&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *   System.out.println("Test #1: this is a test string"); // OK
 *   System.out.println("TeSt #2: This is a test string"); // OK
 *   System.out.println("TEST #3: This is a test string"); // OK
 *   int i = 5;
 *   System.out.println("Value of i: " + i);
 *   System.out.println("Test #4: This is a test string"); // violation
 *   System.out.println("TEst #5: This is a test string"); // violation
 * }
 * </pre>
 * <p>
 * To configure the check to match a minimum of two test strings:
 * </p>
 * <pre>
 * &lt;module name=&quot;RegexpMultiline&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;Test #[0-9]+:[A-Za-z ]+&quot;/&gt;
 *   &lt;property name=&quot;minimum&quot; value=&quot;2&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *   System.out.println("Test #1: this is a test string"); // violation
 *   System.out.println("TEST #2: This is a test string"); // OK, "ignoreCase" is false by default
 *   int i = 5;
 *   System.out.println("Value of i: " + i);
 *   System.out.println("Test #3: This is a test string"); // violation
 *   System.out.println("Test #4: This is a test string"); // violation
 * }
 * </pre>
 * <p>
 * To configure the check to restrict an empty file:
 * </p>
 * <pre>
 * &lt;module name=&quot;RegexpMultiline&quot;&gt;
 *     &lt;property name=&quot;format&quot; value=&quot;^\s*$&quot; /&gt;
 *     &lt;property name=&quot;matchAcrossLines&quot; value=&quot;true&quot; /&gt;
 *     &lt;property name=&quot;message&quot; value=&quot;Empty file is not allowed&quot; /&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of violation from the above config:
 * </p>
 * <pre>
 * /var/tmp$ cat -n Test.java
 * 1
 * 2
 * 3
 * 4
 * </pre>
 * <p>Result:</p>
 * <pre>
 * /var/tmp/Test.java // violation, a file must not be empty.
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code regexp.StackOverflowError}
 * </li>
 * <li>
 * {@code regexp.empty}
 * </li>
 * <li>
 * {@code regexp.exceeded}
 * </li>
 * <li>
 * {@code regexp.minimum}
 * </li>
 * </ul>
 *
 * @since 5.0
 */
@StatelessCheck
public class RegexpMultilineCheck extends AbstractFileSetCheck {

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
    /** Control whether to match expressions across multiple lines. */
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
     * Retrieves the compile-flags for the regular expression being built based
     * on {@code matchAcrossLines}.
     *
     * @return The compile-flags.
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
     * Setter to control whether to match expressions across multiple lines.
     *
     * @param matchAcrossLines whether to match expressions across multiple lines.
     */
    public void setMatchAcrossLines(boolean matchAcrossLines) {
        this.matchAcrossLines = matchAcrossLines;
    }

}
