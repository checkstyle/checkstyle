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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

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
public class MethodLengthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLen.method";

    /** Default maximum number of lines. */
    private static final int DEFAULT_MAX_LINES = 150;

    /** Whether to ignore empty lines and single line comments. */
    private boolean countEmpty = true;

    /** The maximum number of lines. */
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
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST openingBrace = ast.findFirstToken(TokenTypes.SLIST);
        if (openingBrace != null) {
            final DetailAST closingBrace =
                openingBrace.findFirstToken(TokenTypes.RCURLY);
            final int length = getLengthOfBlock(openingBrace, closingBrace);
            if (length > max) {
                log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY,
                        length, max);
            }
        }
    }

    /**
     * Returns length of code only without comments and blank lines.
     * @param openingBrace block opening brace
     * @param closingBrace block closing brace
     * @return number of lines with code for current block
     */
    private int getLengthOfBlock(DetailAST openingBrace, DetailAST closingBrace) {
        int length = closingBrace.getLineNo() - openingBrace.getLineNo() + 1;

        if (!countEmpty) {
            final FileContents contents = getFileContents();
            final int lastLine = closingBrace.getLineNo();
            for (int i = openingBrace.getLineNo() - 1; i < lastLine; i++) {
                if (contents.lineIsBlank(i) || contents.lineIsComment(i)) {
                    length--;
                }
            }
        }
        return length;
    }

    /**
     * @param length the maximum length of a method.
     */
    public void setMax(int length) {
        max = length;
    }

    /**
     * @param countEmpty whether to count empty and single line comments
     *     of the form //.
     */
    public void setCountEmpty(boolean countEmpty) {
        this.countEmpty = countEmpty;
    }
}
