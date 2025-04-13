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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks that constant names conform to a specified pattern.
 * A <em>constant</em> is a <strong>static</strong> and <strong>final</strong>
 * field or an interface/annotation field, except
 * <strong>serialVersionUID</strong> and <strong>serialPersistentFields
 * </strong>.
 * </div>
 *
 * <ul>
 * <li>
 * Property {@code applyToPackage} - Control if check should apply to package-private members.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToPrivate} - Control if check should apply to private members.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToProtected} - Control if check should apply to protected members.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToPublic} - Control if check should apply to public members.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code format} - Sets the pattern to match valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"}.
 * </li>
 * </ul>
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
 * {@code name.invalidPattern}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
public class ConstantNameCheck
    extends AbstractAccessControlNameCheck {

    /** Creates a new {@code ConstantNameCheck} instance. */
    public ConstantNameCheck() {
        super("^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$");
    }

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
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    protected final boolean mustCheckName(DetailAST ast) {
        boolean returnValue = false;

        final DetailAST modifiersAST =
            ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isStaticFinal =
            modifiersAST.findFirstToken(TokenTypes.LITERAL_STATIC) != null
                && modifiersAST.findFirstToken(TokenTypes.FINAL) != null
            || ScopeUtil.isInAnnotationBlock(ast)
            || ScopeUtil.isInInterfaceBlock(ast);
        if (isStaticFinal && shouldCheckInScope(modifiersAST)
                        && !ScopeUtil.isInCodeBlock(ast)) {
            // Handle the serialVersionUID and serialPersistentFields constants
            // which are used for Serialization. Cannot enforce rules on it. :-)
            final DetailAST nameAST = ast.findFirstToken(TokenTypes.IDENT);
            if (!"serialVersionUID".equals(nameAST.getText())
                && !"serialPersistentFields".equals(nameAST.getText())) {
                returnValue = true;
            }
        }

        return returnValue;
    }

}
