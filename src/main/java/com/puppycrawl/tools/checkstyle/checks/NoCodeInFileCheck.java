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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks whether file contains code. Files which are considered to have no code:
 * </p>
 * <ul>
 * <li>
 * File with no text
 * </li>
 * <li>
 * File with single-line comment(s)
 * </li>
 * <li>
 * File with a multi line comment(s).
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="NoCodeInFile"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <p>
 * Content of the files:
 * </p>
 * <pre>
 * // single-line comment // violation
 * </pre>
 * <pre>
 * /* // violation
 *  block comment
 * *&#47;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code nocode.in.file}
 * </li>
 * </ul>
 *
 * @since 8.33
 */
@StatelessCheck
public class NoCodeInFileCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_NO_CODE = "nocode.in.file";

    /** Line number used to log violation when no AST nodes are present in file. */
    private static final int DEFAULT_LINE_NUMBER = 1;

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

    @Override
    public void finishTree(DetailAST ast) {
        if (ast == null) {
            log(DEFAULT_LINE_NUMBER, MSG_KEY_NO_CODE);
        }
    }
}
