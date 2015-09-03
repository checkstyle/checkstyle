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

package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Implements Bloch, Effective Java, Item 17 -
 * Use Interfaces only to define types.
 *
 * <p>
 * An interface should describe a <em>type</em>, it is therefore
 * inappropriate to define an interface that does not contain any methods
 * but only constants.
 * </p>
 *
 * <p>
 * The check can be configured to also disallow marker interfaces like
 * {@code java.io.Serializable}, that do not contain methods or
 * constants at all.
 * </p>
 *
 * @author lkuehne
 */
public final class InterfaceIsTypeCheck
        extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "interface.type";

    /** Flag to control whether marker interfaces are allowed. */
    private boolean allowMarkerInterfaces = true;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.INTERFACE_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.INTERFACE_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST objBlock =
                ast.findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST methodDef =
                objBlock.findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST variableDef =
                objBlock.findFirstToken(TokenTypes.VARIABLE_DEF);
        final boolean methodRequired =
                !allowMarkerInterfaces || variableDef != null;

        if (methodDef == null && methodRequired) {
            log(ast.getLineNo(), MSG_KEY);
        }

    }

    /**
     * Controls whether marker interfaces like Serializable are allowed.
     * @param flag whether to allow marker interfaces or not
     */
    public void setAllowMarkerInterfaces(boolean flag) {
        allowMarkerInterfaces = flag;
    }
}
