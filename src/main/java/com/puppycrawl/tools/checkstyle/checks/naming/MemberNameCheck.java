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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that instance variable names conform to a format specified
 * by the format property. The format is a
 * {@link java.util.regex.Pattern regular expression}
 * and defaults to
 * <strong>^[a-z][a-zA-Z0-9]*$</strong>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="MemberName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with
 * &quot;m&quot;, followed by an upper case letter, and then letters and
 * digits is:
 * </p>
 * <pre>
 * &lt;module name="MemberName"&gt;
 *    &lt;property name="format" value="^m[A-Z][a-zA-Z0-9]*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 */
public class MemberNameCheck
    extends AbstractAccessControlNameCheck {
    /** Creates a new {@code MemberNameCheck} instance. */
    public MemberNameCheck() {
        super("^[a-z][a-zA-Z0-9]*$");
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    protected final boolean mustCheckName(DetailAST ast) {
        final DetailAST modifiersAST =
            ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isStatic = modifiersAST.branchContains(TokenTypes.LITERAL_STATIC);

        return !isStatic && !ScopeUtils.inInterfaceOrAnnotationBlock(ast)
            && !ScopeUtils.isLocalVariableDef(ast)
                && shouldCheckInScope(modifiersAST);
    }
}
