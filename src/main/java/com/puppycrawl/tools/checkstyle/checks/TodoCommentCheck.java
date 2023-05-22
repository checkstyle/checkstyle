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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for {@code TODO:} comments. Actually it is a generic
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/regex/Pattern.html">
 * pattern</a> matcher on Java comments. To check for other patterns
 * in Java comments, set the {@code format} property.
 * </p>
 * <p>
 * Using {@code TODO:} comments is a great way to keep track of tasks that need to be done.
 * Having them reported by Checkstyle makes it very hard to forget about them.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specify pattern to match comments against.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "TODO:"}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="TodoComment"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * i++; // TODO: do differently in future   // violation
 * i++; // todo: do differently in future   // OK
 * </pre>
 * <p>
 * To configure the check for comments that contain {@code TODO} and {@code FIXME}:
 * </p>
 * <pre>
 * &lt;module name="TodoComment"&gt;
 *   &lt;property name="format" value="(TODO)|(FIXME)"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * i++;   // TODO: do differently in future   // violation
 * i++;   // todo: do differently in future   // OK
 * i=i/x; // FIXME: handle x = 0 case         // violation
 * i=i/x; // FIX :  handle x = 0 case         // OK
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code todo.match}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class TodoCommentCheck
        extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "todo.match";

    /**
     * Specify pattern to match comments against.
     */
    private Pattern format = Pattern.compile("TODO:");

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    /**
     * Setter to specify pattern to match comments against.
     *
     * @param pattern
     *        pattern of 'todo' comment.
     */
    public void setFormat(Pattern pattern) {
        format = pattern;
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
            if (format.matcher(line).find()) {
                log(ast, MSG_KEY, format.pattern());
            }
        }
    }

}
