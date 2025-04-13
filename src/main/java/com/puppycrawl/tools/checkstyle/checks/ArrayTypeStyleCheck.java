////
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

package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks the style of array type definitions.
 * Some like Java style: {@code public static void main(String[] args)}
 * and some like C style: {@code public static void main(String args[])}.
 * </div>
 *
 * <p>
 * By default, the Check enforces Java style.
 * </p>
 *
 * <p>
 * This check strictly enforces only Java style for method return types regardless
 * of the value for 'javaStyle'. For example, {@code byte[] getData()}.
 * This is because C doesn't compile methods with array declarations on the name.
 * </p>
 * <ul>
 * <li>
 * Property {@code javaStyle} - Control whether to enforce Java style (true) or C style (false).
 * Type is {@code boolean}.
 * Default value is {@code true}.
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
 * {@code array.type.style}
 * </li>
 * </ul>
 *
 * @since 3.1
 */
@StatelessCheck
public class ArrayTypeStyleCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "array.type.style";

    /** Control whether to enforce Java style (true) or C style (false). */
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
        final DetailAST identAst = typeAST.getNextSibling();
        // If identAst is null, we have a 'LITERAL_NEW' expression, i.e. 'new int[2][2]'
        if (identAst != null) {
            final boolean isMethod = typeAST.getParent().getType() == TokenTypes.METHOD_DEF;
            final boolean isJavaStyle = identAst.getLineNo() > ast.getLineNo()
                || identAst.getColumnNo() - ast.getColumnNo() > -1;

            // force all methods to be Java style (see note in top Javadoc)
            final boolean isMethodViolation = isMethod && !isJavaStyle;
            final boolean isVariableViolation = !isMethod
                && isJavaStyle != javaStyle;

            if (isMethodViolation || isVariableViolation) {
                log(ast, MSG_KEY);
            }
        }
    }

    /**
     * Setter to control whether to enforce Java style (true) or C style (false).
     *
     * @param javaStyle true if Java style should be used.
     * @since 3.1
     */
    public void setJavaStyle(boolean javaStyle) {
        this.javaStyle = javaStyle;
    }

}
