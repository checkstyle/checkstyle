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

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <p>
 * Checks that a specified pattern matches a single-line in any file type.
 * </p>
 * <p>
 * Rationale: This check can be used to prototype checks and to find common bad
 * practice such as calling {@code ex.printStacktrace()},
 * {@code System.out.println()}, {@code System.exit()}, etc.
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
 * Property {@code fileExtensions} - Specify the file type extension of files to process.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 * <p>
 *   To configure the default check:
 * </p>
 * <pre>
 * &lt;module name="RegexpSingleline" /&gt;
 * </pre>
 * <p>
 *   This configuration does not match to anything,
 *   so we do not provide any code example for it
 *   as no violation will ever be reported.
 * </p>
 * <p>
 * To configure the check to find occurrences of 'System.exit('
 * with some <i>slack</i> of allowing only one occurrence per file:
 * </p>
 * <pre>
 * &lt;module name="RegexpSingleline"&gt;
 *   &lt;property name="format" value="System.exit\("/&gt;
 *   &lt;!-- next line not required as 0 is the default --&gt;
 *   &lt;property name="minimum" value="0"/&gt;
 *   &lt;property name="maximum" value="1"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class MyClass {
 *      void myFunction() {
 *          try {
 *             doSomething();
 *          } catch (Exception e) {
 *             System.exit(1); // OK, as only there is only one occurrence.
 *          }
 *      }
 *      void doSomething(){};
 * }
 * </pre>
 * <pre>
 * class MyClass {
 *     void myFunction() {
 *         try {
 *             doSomething();
 *             System.exit(0);
 *         } catch (Exception e) {
 *             System.exit(1); // Violation, as there are more than one occurrence.
 *         }
 *     }
 *     void doSomething(){};
 * }
 * </pre>
 * <p>
 * An example of how to configure the check to make sure a copyright statement
 * is included in the file:
 * </p>
 * <pre>
 * &lt;module name="RegexpSingleline"&gt;
 *   &lt;property name="format" value="This file is copyrighted"/&gt;
 *   &lt;property name="minimum" value="1"/&gt;
 *   &lt;!--  Need to specify a maximum, so 10 times is more than enough. --&gt;
 *   &lt;property name="maximum" value="10"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * &#47;**
 * * This file is copyrighted under CC. // Ok, as the file contains a copyright statement.
 * *&#47;
 * class MyClass {
 *
 * }
 * </pre>
 * <pre>
 * &#47;** // violation, as the file doesn't contain a copyright statement.
 * * MyClass as a configuration example.
 * *&#47;
 * class MyClass {
 *
 * }
 * </pre>
 * <p>
 *  An example of how to configure the check to make sure sql files contains the term 'license'.
 * </p>
 * <pre>
 * &lt;module name="RegexpSingleline"&gt;
 *     &lt;property name="format" value="license"/&gt;
 *     &lt;property name="minimum" value="1"/&gt;
 *     &lt;property name="maximum" value="9999"/&gt;
 *     &lt;property name="ignoreCase" value="true"/&gt;
 *     &lt;!--  Configure a message to be shown on violation of the Check. --&gt;
 *     &lt;property name="message"
 *           value="File must contain at least one occurrence of 'license' term"/&gt;
*      &lt;!--  Perform the Check only on files with java extension. --&gt;
 *     &lt;property name="fileExtensions" value="sql"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * &#47;*
 * AP 2.0 License. // Ok, Check ignores the case of the term.
 * *&#47;
 * CREATE DATABASE MyDB;
 * </pre>
 * <pre>
 * &#47;* // violation, file doesn't contain the term.
 * Example sql file.
 * *&#47;
 * CREATE DATABASE MyDB;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
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
 * @since 5.0
 */
@StatelessCheck
public class RegexpSinglelineCheck extends AbstractFileSetCheck {

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

    /** The detector to use. */
    private SinglelineDetector detector;

    @Override
    public void beginProcessing(String charset) {
        final DetectorOptions options = DetectorOptions.newBuilder()
            .reporter(this)
            .compileFlags(0)
            .format(format)
            .message(message)
            .minimum(minimum)
            .maximum(maximum)
            .ignoreCase(ignoreCase)
            .build();
        detector = new SinglelineDetector(options);
    }

    @Override
    protected void processFiltered(File file, FileText fileText) {
        detector.processLines(fileText);
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

}
