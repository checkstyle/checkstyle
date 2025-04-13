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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for else blocks.
 *
 */
public class ElseHandler extends BlockParentHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public ElseHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "else", ast, parent);
    }

    @Override
    protected void checkTopLevelToken() {
        // check if else is nested with rcurly of if:
        //
        //  } else ...

        final DetailAST ifAST = getMainAst().getParent();
        final DetailAST slist = ifAST.findFirstToken(TokenTypes.SLIST);
        if (slist == null) {
            super.checkTopLevelToken();
        }
        else {
            final DetailAST lcurly = slist.getLastChild();
            // indentation checked as part of LITERAL IF check
            if (!TokenUtil.areOnSameLine(lcurly, getMainAst())) {
                super.checkTopLevelToken();
            }
        }
    }

    @Override
    protected DetailAST getNonListChild() {
        return getMainAst().getFirstChild();
    }

}
