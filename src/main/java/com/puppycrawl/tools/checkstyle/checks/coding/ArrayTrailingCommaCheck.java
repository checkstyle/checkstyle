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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks if array initialization contains optional trailing comma.
 * </p>
 *
 * <p>
 * Rationale: Putting this comma in make is easier to change the
 * order of the elements or add new elements on the end.
 * </p>
 *
 * <pre>
 * Properties:
 * </pre>
 * <table summary="Properties" border="1">
 * <tr><th>name</th><th>Description</th><th>type</th><th>default value</th></tr>
 * <tr><td>ignoreInlineArrays</td><td>The flag controls trailing commas in inline arrays.
 * If set to true, then you can ignore the lack of a trailing comma,
 * otherwise an error should be raised.
 * </td><td>Boolean</td><td>true</td>
 * </tr>
 * </table>
 *
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="ArrayTrailingComma"&gt;
 *    &lt;property name=&quot;ignoreInlineArrays&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author o_sukhodolsky
 */
public class ArrayTrailingCommaCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "array.trailing.comma";

    /**
     * The flag controls trailing commas in inline arrays.
     * If set to true, then you can ignore the lack of a trailing comma, otherwise an error
     * should be raised.
     */
    private boolean ignoreInlineArrays = true;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.ARRAY_INIT};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST arrayInit) {
        final DetailAST rcurly = arrayInit.findFirstToken(TokenTypes.RCURLY);

        // if curlies are on the same line
        // or array is empty then check nothing
        if ((!ignoreInlineArrays
            || arrayInit.getLineNo() != rcurly.getLineNo())
            && arrayInit.getChildCount() != 1) {
            final DetailAST prev = rcurly.getPreviousSibling();
            if (prev.getType() != TokenTypes.COMMA) {
                log(rcurly.getLineNo(), MSG_KEY);
            }
        }
    }

    /**
     * Set the ignoreInlineArrays to enforce.
     *
     * @param ignoreInlineArraysStringValue string to decode ignoreInlineArrays from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setIgnoreInlineArrays(boolean ignoreInlineArraysStringValue) {
        ignoreInlineArrays = ignoreInlineArraysStringValue;
    }
}
