///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.nio.file.Path;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks for long lines.
 * </div>
 *
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
 * <a href="https://checkstyle.org/config.html#Checker">{@code Checker}</a>
 * property {@code tabWidth} which applies to all Checks, including {@code LineLength};
 * or can set property {@code tabWidth} for {@code LineLength} alone.
 * </li>
 * <li>
 * By default, package and import statements (lines matching pattern {@code ^(package|import) .*})
 * are not verified by this check.
 * </li>
 * <li>
 * Trailing comments are taken into consideration while calculating the line length.
 * <pre>
 * import java.util.regex.Pattern; // The length of this comment will be taken into consideration
 * </pre>
 * In the example above the length of the import statement is just 31 characters but total length
 * will be 94 characters.
 * </li>
 * </ul>
 * <ul>
 * <li>
 * Property {@code fileExtensions} - Specify the file extensions of the files to process.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code ignorePattern} - Specify pattern for lines to ignore.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^(package|import) .*"}.
 * </li>
 * <li>
 * Property {@code max} - Specify the maximum line length allowed.
 * Type is {@code int}.
 * Default value is {@code 80}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
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

    /** Specify the maximum line length allowed. */
    private int max = DEFAULT_MAX_COLUMNS;

    /** Specify pattern for lines to ignore. */
    private Pattern ignorePattern = Pattern.compile("^(package|import) .*");

    @Override
    protected void processFiltered(Path file, FileText fileText) {
        for (int i = 0; i < fileText.size(); i++) {
            final String line = fileText.get(i);
            final int realLength = CommonUtil.lengthExpandedTabs(
                line, line.codePointCount(0, line.length()), getTabWidth());

            if (realLength > max && !ignorePattern.matcher(line).find()) {
                log(i + 1, MSG_KEY, max, realLength);
            }
        }
    }

    /**
     * Setter to specify the maximum line length allowed.
     *
     * @param length the maximum length of a line
     * @since 3.0
     */
    public void setMax(int length) {
        max = length;
    }

    /**
     * Setter to specify pattern for lines to ignore.
     *
     * @param pattern a pattern.
     * @since 3.0
     */
    public final void setIgnorePattern(Pattern pattern) {
        ignorePattern = pattern;
    }

}
