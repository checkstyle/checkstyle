////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks the style of array type definitions.
 * Some like Java-style: {@code public static void main(String[] args)}
 * and some like C-style: {@code public static void main(String args[])}.
 *
 * <p>By default the Check enforces Java style.</p>
 *
 * <p>This check strictly enforces only Java style for method return types
 * regardless of the value for 'javaStyle'. For example, {@code byte[] getData()}.
 * This is because C doesn't compile methods with array declarations on the name.</p>
 */
@StatelessCheck
public class ArrayTypeStyleCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "array.type.style";

    /** Controls whether to use Java or C style. */
    private boolean javaStyle = true;

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
        return new int[] {TokenTypes.ARRAY_DECLARATOR};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST typeAST = ast.getParent();
        if (typeAST.getType() == TokenTypes.TYPE) {
            final DetailAST variableAST = typeAST.getNextSibling();
            if (variableAST != null) {
                final boolean isMethod = typeAST.getParent().getType() == TokenTypes.METHOD_DEF;
                final boolean isJavaStyle = variableAST.getLineNo() > ast.getLineNo()
                    || variableAST.getColumnNo() - ast.getColumnNo() > -1;

                // force all methods to be Java style (see note in top Javadoc)
                final boolean isMethodViolation = isMethod && !isJavaStyle;
                final boolean isVariableViolation = !isMethod && isJavaStyle != javaStyle;

                if (isMethodViolation || isVariableViolation) {
                    log(ast, MSG_KEY);
                }
            }
        }
    }

    /**
     * Controls whether to check for Java or C style.
     * @param javaStyle true if Java style should be used.
     */
    public void setJavaStyle(boolean javaStyle) {
        this.javaStyle = javaStyle;
    }

}
