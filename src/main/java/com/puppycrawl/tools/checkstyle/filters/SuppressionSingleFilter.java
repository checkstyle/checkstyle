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

package com.puppycrawl.tools.checkstyle.filters;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.Filter;

/**
 * <p>
 * Filter {@code SuppressionSingleFilter} suppresses audit events for Checks violations in the
 * specified file, class, checks, message, module id, lines, and columns.
 * </p>
 * <p>
 * Rationale: To allow users use suppressions configured in the same config with other modules.
 * SuppressionFilter and SuppressionXpathFilter are require separate file.
 * </p>
 * <p>
 * Advice: If checkstyle configuration is used for several projects, single suppressions on common
 * files/folders is better to put in checkstyle configuration as common rule. All suppression that
 * are for specific file names is better to keep in project specific config file.
 * </p>
 * <p>
 * Attention: This filter only supports single suppression, and will need multiple instances if
 * users wants to suppress multiple violations.
 * </p>
 * <p>
 * SuppressionSingleFilter can suppress Checks that have Treewalker or
 * Checker as parent module.
 * </p>
 * <ul>
 * <li>
 * Property {@code files} - Define the RegExp for matching against the file name associated with
 * an audit event.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code checks} - Define the RegExp for matching against the name of the check
 * associated with an audit event.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code message} - Define the RegExp for matching against the message of the check
 * associated with an audit event.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code id} - Specify a string matched against the ID of the check associated with
 * an audit event.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code lines} - Specify a comma-separated list of values, where each value is an
 * integer or a range of integers denoted by integer-integer.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code columns} - Specify a comma-separated list of values, where each value is an
 * integer or a range of integers denoted by integer-integer.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * </ul>
 * <p>
 * The following suppressions directs a {@code SuppressionSingleFilter} to reject
 * {@code JavadocStyleCheck} violations for lines 82 and 108 to 122 of file
 * {@code AbstractComplexityCheck.java}, and
 * {@code MagicNumberCheck} violations for line 221 of file
 * {@code JavadocStyleCheck.java}, and {@code 'Missing a Javadoc comment'} violations for all lines
 * and files:
 * </p>
 * <pre>
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="checks" value="JavadocStyleCheck"/&gt;
 *   &lt;property name="files" value="AbstractComplexityCheck.java"/&gt;
 *   &lt;property name="lines" value="82,108-122"/&gt;
 * &lt;/module&gt;
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="checks" value="MagicNumberCheck"/&gt;
 *   &lt;property name="files" value="JavadocStyleCheck.java"/&gt;
 *   &lt;property name="lines" value="221"/&gt;
 * &lt;/module&gt;
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="message" value="Missing a Javadoc comment"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Suppress check by <a href="https://checkstyle.org/config.html#Id">module id</a> when config
 * have two instances on the same check:
 * </p>
 * <pre>
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="id" value="stringEqual"/&gt;
 *   &lt;property name="files" value="SomeTestCode.java"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Suppress all checks for hidden files and folders:
 * </p>
 * <pre>
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="files" value="[/\\]\..+"/&gt;
 *   &lt;property name="checks" value=".*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Suppress all checks for Maven-generated code:
 * </p>
 * <pre>
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="files" value="[/\\]target[/\\]"/&gt;
 *   &lt;property name="checks" value=".*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Suppress all checks for archives, classes and other binary files:
 * </p>
 * <pre>
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="files" value=".+\.(?:jar|zip|war|class|tar|bin)$"/&gt;
 *   &lt;property name="checks" value=".*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Suppress all checks for image files:
 * </p>
 * <pre>
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="files" value=".+\.(?:png|gif|jpg|jpeg)$"/&gt;
 *   &lt;property name="checks" value=".*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Suppress all checks for non-java files:
 * </p>
 * <pre>
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="files"
 *     value=".+\.(?:txt|xml|csv|sh|thrift|html|sql|eot|ttf|woff|css|png)$"/&gt;
 *   &lt;property name="checks" value=".*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Suppress all checks in generated sources:
 * </p>
 * <pre>
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="files" value="com[\\/]mycompany[\\/]app[\\/]gen[\\/]"/&gt;
 *   &lt;property name="checks" value=".*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Suppress FileLength check on integration tests in certain folder:
 * </p>
 * <pre>
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="files" value="com[\\/]mycompany[\\/]app[\\/].*IT.java"/&gt;
 *   &lt;property name="checks" value="FileLength"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Suppress naming violations on variable named 'log' in all files:
 * </p>
 * <pre>
 * &lt;module name="SuppressionSingleFilter"&gt;
 *   &lt;property name="message" value="Name 'log' must match pattern"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 8.23
 */
public class SuppressionSingleFilter extends AutomaticBean implements Filter {

    /**
     * SuppressFilterElement instance.
     */
    private SuppressFilterElement filter;
    /**
     * Define the RegExp for matching against the file name associated with an audit event.
     */
    private Pattern files;
    /**
     * Define the RegExp for matching against the name of the check associated with an audit event.
     */
    private Pattern checks;
    /**
     * Define the RegExp for matching against the message of the check associated with an audit
     * event.
     */
    private Pattern message;
    /**
     * Specify a string matched against the ID of the check associated with an audit event.
     */
    private String id;
    /**
     * Specify a comma-separated list of values, where each value is an integer or a range of
     * integers denoted by integer-integer.
     */
    private String lines;
    /**
     * Specify a comma-separated list of values, where each value is an integer or a range of
     * integers denoted by integer-integer.
     */
    private String columns;

    /**
     * Setter to define the RegExp for matching against the file name associated with an audit
     * event.
     *
     * @param files regular expression for filtered file names
     */
    public void setFiles(Pattern files) {
        this.files = files;
    }

    /**
     * Setter to define the RegExp for matching against the name of the check associated with an
     * audit event.
     *
     * @param checks the name of the check
     */
    public void setChecks(String checks) {
        this.checks = Pattern.compile(checks);
    }

    /**
     * Setter to define the RegExp for matching against the message of the check associated with
     * an audit event.
     *
     * @param message the message of the check
     */
    public void setMessage(Pattern message) {
        this.message = message;
    }

    /**
     * Setter to specify a string matched against the ID of the check associated with an audit
     * event.
     *
     * @param id the ID of the check
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter to specify a comma-separated list of values, where each value is an integer or a
     * range of integers denoted by integer-integer.
     *
     * @param lines the lines of the check
     */
    public void setLines(String lines) {
        this.lines = lines;
    }

    /**
     * Setter to specify a comma-separated list of values, where each value is an integer or a
     * range of integers denoted by integer-integer.
     *
     * @param columns the columns of the check
     */
    public void setColumns(String columns) {
        this.columns = columns;
    }

    @Override
    protected void finishLocalSetup() {
        filter = new SuppressFilterElement(files, checks, message, id, lines, columns);
    }

    @Override
    public boolean accept(AuditEvent event) {
        return filter.accept(event);
    }

}
