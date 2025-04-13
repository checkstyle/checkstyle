///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that sealed classes and interfaces have a permits list.
 * </div>
 *
 * <p>
 *     Rationale: When a permits clause is omitted from a sealed class,
 *     any class within the same compilation unit can extend it. This differs
 *     from other sealed classes where permitted subclasses are explicitly
 *     declared, making them readily visible to the reader. Without a permits
 *     clause, identifying potential subclasses requires searching the entire
 *     compilation unit, which can be challenging, especially in large files
 *     with complex class hierarchies.
 * </p>
 *
 * <p>
 * See the <a href="https://docs.oracle.com/javase/specs/jls/se22/html/jls-13.html#jls-13.4.2">
 * Java Language Specification</a> for more information about sealed classes.
 * </p>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code sealed.should.have.permits}
 * </li>
 * </ul>
 *
 * @since 10.18.0
 */

@StatelessCheck
public class SealedShouldHavePermitsListCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "sealed.should.have.permits";

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
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isSealed = modifiers.findFirstToken(TokenTypes.LITERAL_SEALED) != null;
        final boolean hasPermitsList = ast.findFirstToken(TokenTypes.PERMITS_CLAUSE) != null;

        if (isSealed && !hasPermitsList) {
            log(ast, MSG_KEY);
        }
    }
}
