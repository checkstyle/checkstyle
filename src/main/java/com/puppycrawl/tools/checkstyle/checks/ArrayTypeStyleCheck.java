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

package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks the style of array type definitions.
 * Some like Java-style: {@code public static void main(String[] args)}
 * and some like C-style: public static void main(String args[])
 *
 * <p>By default the Check enforces Java style.
 * @author lkuehne
 */
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
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.ARRAY_DECLARATOR};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST typeAST = ast.getParent();
        if (typeAST.getType() == TokenTypes.TYPE
                // Do not check method's return type.
                // We have no alternatives here.
                && typeAST.getParent().getType() != TokenTypes.METHOD_DEF) {
            final DetailAST variableAST = typeAST.getNextSibling();
            if (variableAST != null) {
                final boolean isJavaStyle =
                    variableAST.getLineNo() > ast.getLineNo()
                    || variableAST.getColumnNo() > ast.getColumnNo();

                if (isJavaStyle != javaStyle) {
                    log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY);
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
