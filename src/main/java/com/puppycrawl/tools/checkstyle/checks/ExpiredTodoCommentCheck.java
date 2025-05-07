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

package com.puppycrawl.tools.checkstyle.checks;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks for expired {@code TODO} comments. Actually it is a generic
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/regex/Pattern.html">
 * pattern</a> matcher on Java comments. To check for other patterns
 * in Java comments, set the {@code pattern} property.
 * </div>
 *
 * <p>
 *     Live template example from <a href=
 *     "https://www.jetbrains.com/help/idea/creating-and-editing-live-templates.html">
 *     IntelliJ IDEA</a>.
 *     Example:
 *     //TODO $DATE$ $USER$ requires remove ISSUE-1234$END$
 *     env:
 *     date("dd.MM.Y")
 *     user()
 * </p>
 *
 * <p>
 * Using {@code TODO} comments is a great way to keep track of tasks that need to be done.
 * Having them reported by Checkstyle makes it very hard to forget about them.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specify format to match date.
 * Type is {@code java.lang.String}.
 * Default value is {@code "dd.MM.yyyy"}.
 * </li>
 * <li>
 * Property {@code pattern} - Specify pattern to match comments against.
 * Should contain groups {@code date} and {@code comment}
 * Type is {@code java.util.regex.Pattern}.
 * Default value is
 * {@code "(?i)(TODO|FIXME)\s*(?&lt;date&gt;(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4})\s*(?&lt;comment&gt;.*)"}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code todo.expired.match}
 * </li>
 * </ul>
 *
 * @since 10.24.0
 */
@StatelessCheck
public class ExpiredTodoCommentCheck
        extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "todo.expired.match";

    /**
     * Specify pattern to match comments against.
     * Should contain groups {@code date} and {@code comment}
     */
    private Pattern pattern = Pattern.compile(
            "(?i)(TODO|FIXME)"
                    + "\\s*(?<date>(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4})"
                    + "\\s*(?<comment>.*)"
    );

    /**
     * Specify format to match date.
     */
    private String format = "dd.MM.yyyy";

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    /**
     * Setter to specify pattern to match comments against.
     * Should contain groups {@code date} and {@code comment}
     *
     * @param pattern
     *        pattern of 'todo' comment.
     * @since 10.24.0
     */
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Setter to specify format to match date.
     *
     * @param format
     *        format of 'date' group in comment
     * @since 10.24.0
     */
    public void setFormat(String format) {
        this.format = format;
    }

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
        return new int[] {TokenTypes.COMMENT_CONTENT };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String[] lines = ast.getText().split("\n");
        for (String line : lines) {
            final Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                final String date = matcher.group("date");
                if (LocalDate
                        .parse(date, DateTimeFormatter.ofPattern(format, Locale.ROOT))
                        .isBefore(LocalDate.now(ZoneId.systemDefault()))
                ) {
                    log(ast, MSG_KEY, date, matcher.group("comment"));
                }
            }
        }
    }

}
