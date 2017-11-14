////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks if array initialization contains optional trailing comma.
 * </p>
 * <p>
 * Rationale: Putting this comma in make is easier to change the
 * order of the elements or add new elements on the end. Main benefit of a trailing
 * comma is that when you add new entry to an array, no surrounding lines are changed.
 * </p>
 * <p>
 * The check demands a comma at the end if neither left nor right curly braces
 * are on the same line as the last element of the array.
 * </p>
 * <pre>
 * return new int[] { 0 };
 * return new int[] { 0
 *     };
 * return new int[] {
 *     0 };
 * </pre>
 * <pre>
 * {
 *     100000000000000000000,
 *     200000000000000000000, // OK
 * }
 *
 * {
 *     100000000000000000000,
 *     200000000000000000000,
 *     300000000000000000000,  // Just this line added, no other changes
 * }
 * </pre>
 * <p>
 * If closing brace is on the same line as training comma, this benefit is gone
 * (as the Check does not demand a certain location of curly braces the following
 * two cases will not produce a violation):
 * </p>
 * <pre>
 * {100000000000000000000,
 *     200000000000000000000,} // Trailing comma not needed, line needs to be modified anyway
 *
 * {100000000000000000000,
 *     200000000000000000000, // Modified line
 *     300000000000000000000,} // Added line
 * </pre>
 * <p>
 * If opening brace is on the same line as training comma there's also (more arguable) problem:
 * </p>
 * <pre>
 * {100000000000000000000, // Line cannot be just duplicated to slightly modify entry
 * }
 *
 * {100000000000000000000,
 *     100000000000000000001, // More work needed to duplicate
 * }
 * </pre>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="ArrayTrailingComma"/&gt;
 * </pre>
 * @author o_sukhodolsky
 */
@StatelessCheck
public class ArrayTrailingCommaCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "array.trailing.comma";

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
        return new int[] {TokenTypes.ARRAY_INIT};
    }

    @Override
    public void visitToken(DetailAST arrayInit) {
        final DetailAST rcurly = arrayInit.findFirstToken(TokenTypes.RCURLY);
        final DetailAST previousSibling = rcurly.getPreviousSibling();

        if (arrayInit.getLineNo() != rcurly.getLineNo()
                && arrayInit.getChildCount() != 1
                && rcurly.getLineNo() != previousSibling.getLineNo()
                && arrayInit.getLineNo() != previousSibling.getLineNo()
                && previousSibling.getType() != TokenTypes.COMMA) {
            log(rcurly.getLineNo(), MSG_KEY);
        }
    }
}
