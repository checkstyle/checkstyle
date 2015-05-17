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

package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>Checks that long constants are defined with an upper ell.
 * That is <span class="code">'L'</span> and not
 * <span class="code">'l'</span>. This is in accordance to the Java Language
 * Specification, <a href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.10.1">
 * Section 3.10.1</a>.
 * </p>
 * <p>
 * Rationale: The letter <span class="code">l</span> looks a lot
 * like the number <span class="code">1</span>.
 * </p>
 *
 * Examples
 * <p class="body">
 * To configure the check:
 *
 * </p>
 * <pre class="body">
 * &lt;module name=&quot;UpperEll&quot;/&gt;
 * </pre>
 *
 * @author Oliver Burn
 */
public class UpperEllCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "upperEll";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.NUM_LONG};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.NUM_LONG};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (Utils.endsWithChar(ast.getText(), 'l')) {
            log(ast.getLineNo(),
                ast.getColumnNo() + ast.getText().length() - 1,
                MSG_KEY);
        }
    }
}
