///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks that instance variable names conform to a specified pattern.
 * </div>
 *
 * @since 3.0
 */
public class MemberNameCheck
    extends AbstractAccessControlNameCheck {

    /** Creates a new {@code MemberNameCheck} instance. */
    public MemberNameCheck() {
        super("^[a-z][a-zA-Z0-9]*$");
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

    /**
     * Setter to control if check should apply to package-private members.
     *
     * @param applyTo new value of the property.
     * @propertySince 3.4
     */
    @Override
    public final void setApplyToPackage(boolean applyTo) {
        super.setApplyToPackage(applyTo);
    }

    /**
     * Setter to control if check should apply to private members.
     *
     * @param applyTo new value of the property.
     * @propertySince 3.4
     */
    @Override
    public final void setApplyToPrivate(boolean applyTo) {
        super.setApplyToPrivate(applyTo);
    }

    /**
     * Setter to control if check should apply to protected members.
     *
     * @param applyTo new value of the property.
     * @propertySince 3.4
     */
    @Override
    public final void setApplyToProtected(boolean applyTo) {
        super.setApplyToProtected(applyTo);
    }

    /**
     * Setter to control if check should apply to public members.
     *
     * @param applyTo new value of the property.
     * @propertySince 3.4
     */
    @Override
    public final void setApplyToPublic(boolean applyTo) {
        super.setApplyToPublic(applyTo);
    }

    @Override
    protected final boolean mustCheckName(DetailAST ast) {
        final DetailAST modifiersAST =
            ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isStatic = modifiersAST.findFirstToken(TokenTypes.LITERAL_STATIC) != null;

        return !isStatic && !ScopeUtil.isInInterfaceOrAnnotationBlock(ast)
            && !ScopeUtil.isLocalVariableDef(ast)
                && shouldCheckInScope(modifiersAST);
    }

}
