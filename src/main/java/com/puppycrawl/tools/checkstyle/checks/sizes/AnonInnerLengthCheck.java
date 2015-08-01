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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for long anonymous inner classes.
 * </p>
 * <p>
 * Rationale: If an anonymous inner class becomes very long
 * it is hard to understand and to see the flow of the method
 * where the class is defined. Therefore long anonymous inner
 * classes should usually be refactored into a named inner class.
 * See also Bloch, Effective Java, p. 93.
 * </p>
 * <p>
 * The default maximum anonymous inner class length is 20 lines.
 * To change the maximum number of lines, set property max.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="AnonInnerLength"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it accepts anonymous
 * inner classes with up to 60 lines is:
 * </p>
 * <pre>
 * &lt;module name="AnonInnerLength"&gt;
 *    &lt;property name="max" value="60"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Rob Worth
 */
public class AnonInnerLengthCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLen.anonInner";

    /** default maximum number of lines */
    private static final int DEFAULT_MAX = 20;

    /** maximum number of lines */
    private int max = DEFAULT_MAX;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.LITERAL_NEW};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.LITERAL_NEW};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST openingBrace = ast.findFirstToken(TokenTypes.OBJBLOCK);
        if (openingBrace != null) {
            final DetailAST closingBrace =
                openingBrace.findFirstToken(TokenTypes.RCURLY);
            final int length =
                closingBrace.getLineNo() - openingBrace.getLineNo() + 1;
            if (length > max) {
                log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY,
                        length, max);
            }
        }
    }

    /**
     * @param length the maximum length of an anonymous inner class.
     */
    public void setMax(int length) {
        max = length;
    }
}
