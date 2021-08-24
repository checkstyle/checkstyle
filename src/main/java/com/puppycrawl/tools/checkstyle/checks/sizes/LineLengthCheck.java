////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.io.File;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks for long lines.
 * </p>
 * <p>
 * Rationale: Long lines are hard to read in printouts or if developers
 * have limited screen space for the source code, e.g. if the IDE displays
 * additional information like project tree, class hierarchy, etc.
 * </p>
 * <ul>
 * <li>
 * The calculation of the length of a line takes into account the number of
 * expanded spaces for a tab character ({@code '\t'}). The default number of spaces is {@code 8}.
 * To specify a different number of spaces, the user can set
 * <a href="https://checkstyle.org/config.html#TreeWalker">{@code TreeWalker}</a>
 * property {@code tabWidth} which applies to all Checks, including {@code LineLength};
 * or can set property {@code tabWidth} for {@code LineLength} alone.
 * </li>
 * </ul>
 * <ul>
 * <li>
 * Property {@code fileExtensions} - Specify file extensions that are accepted.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code ignorePattern} - Specify pattern for lines to ignore.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
 * </li>
 * <li>
 * Property {@code max} - Specify the maximum line length allowed.
 * Type is {@code int}.
 * Default value is {@code 80}.
 * </li>
 * <li>
 * Property {@code validateImport} - Validate checking of import statements.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code validatePackage} - Validate checking of package statements.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the check to accept lines up to 80 characters long:
 * </p>
 * <pre>
 * &lt;module name="LineLength"/&gt;
 * </pre>
 * <p>
 * To configure the check to accept lines up to 120 characters long:
 * </p>
 * <pre>
 * &lt;module name="LineLength"&gt;
 *   &lt;property name="max" value="120"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to ignore lines that begin with {@code " * "} code,
 * followed by just one word, such as within a Javadoc comment:
 * </p>
 * <pre>
 * &lt;module name="LineLength"&gt;
 *   &lt;property name="ignorePattern" value="^ *\* *[^ ]+$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>To configure the check to only validate java files and ignore other extensions:
 * </p>
 * <pre>
 * &lt;module name="LineLength"&gt;
 *   &lt;property name="fileExtensions" value="java"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>To configure the check to only validate xml and property files and ignore other extensions:
 * </p>
 * <pre>
 * &lt;module name="LineLength"&gt;
 *   &lt;property name="fileExtensions" value="xml, properties"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to validate the use of check on import statements.
 * </p>
 * <pre>
 * &lt;module name="LineLength"&gt;
 *   &lt;property name="validateImport" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * import static com.puppycrawl.tools.checkstyle.grammar.InputFullOfSinglelineComments; // Violation
 * import static com.puppycrawl.tools.checkstyle.grammar
 *           .comments.InputFullOfSinglelineComments.main; // ok
 * import java.io.File; // ok
 * </pre>
 * <p>
 * To configure the check to validate the use of check on package statements.
 * </p>
 * <pre>
 * &lt;module name="LineLength"&gt;
 *   &lt;property name="validatePackage" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * package org.hibernate.test.annotations.collectionElement.embeddable.withCustomDef; // Violation
 * package com.puppycrawl.tools.checkstyle.checks
 *             .sizes.linelength; // ok
 * package com.puppycrawl.tools.checkstyle.checks.sizes; // ok
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code maxLineLen}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class LineLengthCheck extends AbstractFileSetCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLineLen";

    /** Default maximum number of columns in a line. */
    private static final int DEFAULT_MAX_COLUMNS = 80;

    /** Pattern matching import and import static statements. */
    private static final Pattern IMPORT_PATTERN = Pattern.compile("^import .*");

    /** Pattern matching package statements. */
    private static final Pattern PACKAGE_PATTERN = Pattern.compile("^package .*");

    /** Specify the maximum line length allowed. */
    private int max = DEFAULT_MAX_COLUMNS;

    /** Specify pattern for lines to ignore. */
    private Pattern ignorePattern = Pattern.compile("^$");

    /** Validate checking of import statements. */
    private boolean validateImport;

    /** Validate checking of package statements. */
    private boolean validatePackage;

    @Override
    protected void processFiltered(File file, FileText fileText) {
        for (int i = 0; i < fileText.size(); i++) {
            final String line = fileText.get(i);
            final int realLength = CommonUtil.lengthExpandedTabs(
                line, line.codePointCount(0, line.length()), getTabWidth());

            if (realLength > max && !ignorePattern.matcher(line).find()) {

                final boolean matchPackage = PACKAGE_PATTERN.matcher(line).find();
                final boolean matchImport = IMPORT_PATTERN.matcher(line).find();
                final boolean validateForPackage = validatePackage && matchPackage;
                final boolean validateForImport = validateImport && matchImport;

                if (validateForImport || validateForPackage
                        || !matchImport && !matchPackage) {
                    log(i + 1, MSG_KEY, max, realLength);
                }
            }
        }
    }

    /**
     * Setter to specify the maximum line length allowed.
     *
     * @param length the maximum length of a line
     */
    public void setMax(int length) {
        max = length;
    }

    /**
     * Setter to specify pattern for lines to ignore.
     *
     * @param pattern a pattern.
     */
    public final void setIgnorePattern(Pattern pattern) {
        ignorePattern = pattern;
    }

    /**
     * Setter to validate checking of package statements.
     *
     * @param validate {@code true} to validate package statements.
     */
    public void setValidatePackage(boolean validate) {
        validatePackage = validate;
    }

    /**
     * Setter to validate checking of import statements.
     *
     * @param validate {@code true} to validate import statements.
     */
    public void setValidateImport(boolean validate) {
        validateImport = validate;
    }

}
