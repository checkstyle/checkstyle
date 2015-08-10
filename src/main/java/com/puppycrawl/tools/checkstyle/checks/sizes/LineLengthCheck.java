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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Checks for long lines.
 *
 * <p>
 * Rationale: Long lines are hard to read in printouts or if developers
 * have limited screen space for the source code, e.g. if the IDE displays
 * additional information like project tree, class hierarchy, etc.
 * </p>
 *
 * <p>
 * Note: Support for the special handling of imports in CheckStyle Version 2
 * has been dropped as it is a special case of regexp: The user can set
 * the ignorePattern to "^import" and achieve the same effect.
 * </p>
 * <p>
 * The default maximum allowable line length is 80 characters. To change the
 * maximum, set property max.
 * </p>
 * <p>
 * To ignore lines in the check, set property ignorePattern to a regular
 * expression for the lines to ignore.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="LineLength"/&gt;
 * </pre>
 * <p> An example of how to configure the check to accept lines up to 120
 * characters long is:
 *</p>
 * <pre>
 * &lt;module name="LineLength"&gt;
 *    &lt;property name="max" value="120"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p> An example of how to configure the check to ignore lines that begin with
 * &quot; * &quot;, followed by just one word, such as within a Javadoc comment,
 * is:
 * </p>
 * <pre>
 * &lt;module name="LineLength"&gt;
 *    &lt;property name="ignorePattern" value="^ *\* *[^ ]+$"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Lars Kühne
 */
public class LineLengthCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLineLen";

    /** default maximum number of columns in a line */
    private static final int DEFAULT_MAX_COLUMNS = 80;

    /** the maximum number of columns in a line */
    private int max = DEFAULT_MAX_COLUMNS;

    /** the regexp when long lines are ignored */
    private Pattern ignorePattern;

    /**
     * Creates a new {@code LineLengthCheck} instance.
     */
    public LineLengthCheck() {
        setIgnorePattern("^$");
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[0];
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        final String[] lines = getLines();
        for (int i = 0; i < lines.length; i++) {

            final String line = lines[i];
            final int realLength = Utils.lengthExpandedTabs(
                line, line.length(), getTabWidth());

            if (realLength > max
                && !ignorePattern.matcher(line).find()) {
                log(i + 1, MSG_KEY, max, realLength);
            }
        }
    }

    /**
     * @param length the maximum length of a line
     */
    public void setMax(int length) {
        max = length;
    }

    /**
     * Set the ignore pattern.
     * @param format a {@code String} value
     */
    public final void setIgnorePattern(String format) {
        ignorePattern = Utils.createPattern(format);
    }
}
