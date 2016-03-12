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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>Checks that chosen statements are not line-wrapped.
 * By default this Check restricts wrapping import and package statements,
 * but it's possible to check any statement.
 * </p>
 *
 * <p>Examples of line-wrapped statements (bad case):
 * <pre>{@code package com.puppycrawl.
 *    tools.checkstyle.checks;
 *
 * import com.puppycrawl.tools.
 *    checkstyle.api.AbstractCheck;
 * }</pre>
 *
 * <p>
 * To configure the check to force no line-wrapping
 * in package and import statements (default values):
 * </p>
 * <pre class="body">
 * &lt;module name=&quot;NoLineWrap&quot;/&gt;
 * </pre>
 *
 * <p>
 * To configure the check to force no line-wrapping only
 * in import statements:
 * </p>
 * <pre class="body">
 * &lt;module name=&quot;NoLineWrap&quot;&gt;
 *     &lt;property name="tokens" value="IMPORT"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>Examples of not line-wrapped statements (good case):
 * <pre>{@code import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
 * }</pre>
 *
 * @author maxvetrenko
 */
public class NoLineWrapCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "no.line.wrap";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.PACKAGE_DEF, TokenTypes.IMPORT};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.IMPORT,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getLineNo() != ast.getLastChild().getLineNo()) {
            log(ast.getLineNo(), MSG_KEY, ast.getText());
        }
    }
}
