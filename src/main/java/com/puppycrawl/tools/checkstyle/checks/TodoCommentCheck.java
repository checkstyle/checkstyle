////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * A check for 'TODO:' comments. To check for other patterns in Java comments, set
 * property format.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 *
 * <pre>
 * &lt;module name="TodoComment"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for comments that contain
 * {@code TODO} or {@code FIXME}is:
 * </p>
 *
 * <pre>
 * &lt;module name="TodoComment"&gt;
 *    &lt;property name="format" value="(TODO)|(FIXME)"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Oliver Burn
 * @author Baratali Izmailov
 */
public class TodoCommentCheck
        extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "todo.match";

    /**
     * Format of 'todo' comment.
     */
    private String format = "TODO:";

    /**
     * Regular expression pattern compiled from format.
     */
    private Pattern regexp = Pattern.compile(format);

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    /**
     * Setter for 'todo' comment format.
     * @param format
     *        format of 'todo' comment.
     * @throws org.apache.commons.beanutils.ConversionException
     *         if unable to create Pattern object.
     */
    public void setFormat(String format) {
        this.format = format;
        regexp = CommonUtils.createPattern(format);
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.COMMENT_CONTENT };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String[] lines = ast.getText().split("\n");

        for (int i = 0; i < lines.length; i++) {
            if (regexp.matcher(lines[i]).find()) {
                log(ast.getLineNo() + i, MSG_KEY, format);
            }
        }
    }
}
