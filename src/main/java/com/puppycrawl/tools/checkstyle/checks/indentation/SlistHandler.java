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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Handler for a list of statements.
 *
 * @author jrichard
 */
public class SlistHandler extends BlockParentHandler {
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public SlistHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "block", ast, parent);
    }

    @Override
    public IndentLevel suggestedChildLevel(AbstractExpressionHandler child) {
        // this is:
        //  switch (var) {
        //     case 3: {
        //        break;
        //     }
        //  }
        //  ... the case SLIST is followed by a user-created SLIST and
        //  preceded by a switch

        // if our parent is a block handler we want to be transparent
        if (getParent() instanceof BlockParentHandler
                && !(getParent() instanceof SlistHandler)
            || getParent() instanceof CaseHandler
                && child instanceof SlistHandler) {
            return getParent().suggestedChildLevel(child);
        }
        return super.suggestedChildLevel(child);
    }

    @Override
    protected DetailAST getNonlistChild() {
        // blocks always have either block children or they are transparent
        // and aren't checking children at all.  In the later case, the
        // superclass will want to check single children, so when it
        // does tell it we have none.
        return null;
    }

    @Override
    protected DetailAST getListChild() {
        return getMainAst();
    }

    @Override
    protected DetailAST getLCurly() {
        return getMainAst();
    }

    @Override
    protected DetailAST getRCurly() {
        return getMainAst().findFirstToken(TokenTypes.RCURLY);
    }

    @Override
    protected DetailAST getToplevelAST() {
        return null;
    }

    /**
     * Determine if the expression we are handling has a block parent.
     *
     * @return true if it does, false otherwise
     */
    private boolean hasBlockParent() {
        final int parentType = getMainAst().getParent().getType();
        return parentType == TokenTypes.LITERAL_IF
            || parentType == TokenTypes.LITERAL_FOR
            || parentType == TokenTypes.LITERAL_WHILE
            || parentType == TokenTypes.LITERAL_DO
            || parentType == TokenTypes.LITERAL_ELSE
            || parentType == TokenTypes.LITERAL_TRY
            || parentType == TokenTypes.LITERAL_CATCH
            || parentType == TokenTypes.LITERAL_FINALLY
            || parentType == TokenTypes.CTOR_DEF
            || parentType == TokenTypes.METHOD_DEF
            || parentType == TokenTypes.STATIC_INIT
            || parentType == TokenTypes.LITERAL_SYNCHRONIZED;
    }

    @Override
    public void checkIndentation() {
        // only need to check this if parent is not
        // an if, else, while, do, ctor, method
        if (hasBlockParent() || isSameLineCaseGroup()) {
            return;
        }
        super.checkIndentation();
    }

    /**
     * Checks if SLIST node is placed at the same line as CASE_GROUP node.
     * @return true, if SLIST node is places at the same line as CASE_GROUP node.
     */
    private boolean isSameLineCaseGroup() {
        final DetailAST parentNode = getMainAst().getParent();
        return parentNode.getType() == TokenTypes.CASE_GROUP
            && getMainAst().getLineNo() == parentNode.getLineNo();
    }
}
