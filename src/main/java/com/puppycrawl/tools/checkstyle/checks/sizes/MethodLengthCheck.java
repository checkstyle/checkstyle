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

import org.apache.commons.lang3.ArrayUtils;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for long methods.
 * </p>
 * <p>
 * Rationale: If a method becomes very long it is hard to understand.
 * Therefore long methods should usually be refactored into several
 * individual methods that focus on a specific task.
 * </p>
 *<p>
 * The default maximum method length is 150 lines. To change the maximum
 * number of lines, set property max.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="MethodLength"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it accepts methods with at
 * most 60 lines is:
 * </p>
 * <pre>
 * &lt;module name="MethodLength"&gt;
 *    &lt;property name="max" value="60"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Lars Kühne
 */
public class MethodLengthCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLen.method";

    /** default maximum number of lines */
    private static final int DEFAULT_MAX_LINES = 150;

    /** whether to ignore empty lines and single line comments */
    private boolean countEmpty = true;

    /** the maximum number of lines */
    private int max = DEFAULT_MAX_LINES;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST openingBrace = ast.findFirstToken(TokenTypes.SLIST);
        if (openingBrace != null) {
            final DetailAST closingBrace =
                openingBrace.findFirstToken(TokenTypes.RCURLY);
            int length =
                closingBrace.getLineNo() - openingBrace.getLineNo() + 1;

            if (!countEmpty) {
                final FileContents contents = getFileContents();
                final int lastLine = closingBrace.getLineNo();
                for (int i = openingBrace.getLineNo() - 1; i < lastLine; i++) {
                    if (contents.lineIsBlank(i) || contents.lineIsComment(i)) {
                        length--;
                    }
                }
            }
            if (length > max) {
                log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY,
                        length, max);
            }
        }
    }

    /**
     * @param length the maximum length of a method.
     */
    public void setMax(int length) {
        max = length;
    }

    /**
     * @param countEmpty whether to count empty and single line comments
     * of the form //.
     */
    public void setCountEmpty(boolean countEmpty) {
        this.countEmpty = countEmpty;
    }
}
