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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractFormatCheck;

/**
 * Abstract class for checking that names conform to a specified format.
 *
 * @author Rick Giles
 */
public abstract class AbstractNameCheck
    extends AbstractFormatCheck {
    /**
     * Message key for invalid pattern error.
     */
    public static final String MSG_INVALID_PATTERN = "name.invalidPattern";

    /**
     * Creates a new {@code AbstractNameCheck} instance.
     * @param format format to check with
     */
    protected AbstractNameCheck(String format) {
        super(format);
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (mustCheckName(ast)) {
            final DetailAST nameAST = ast.findFirstToken(TokenTypes.IDENT);
            if (!getRegexp().matcher(nameAST.getText()).find()) {
                log(nameAST.getLineNo(),
                    nameAST.getColumnNo(),
                    MSG_INVALID_PATTERN,
                    nameAST.getText(),
                    getFormat());
            }
        }
    }

    /**
     * Decides whether the name of an AST should be checked against
     * the format regexp.
     * @param ast the AST to check.
     * @return true if the IDENT subnode of ast should be checked against
     *     the format regexp.
     */
    protected abstract boolean mustCheckName(DetailAST ast);
}
