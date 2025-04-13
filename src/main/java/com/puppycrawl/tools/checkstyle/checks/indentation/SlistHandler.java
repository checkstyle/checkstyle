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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.BitSet;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for a list of statements.
 *
 */
public class SlistHandler extends BlockParentHandler {

    /**
     * Parent token types.
     */
    private static final BitSet PARENT_TOKEN_TYPES = TokenUtil.asBitSet(
        TokenTypes.CTOR_DEF,
        TokenTypes.METHOD_DEF,
        TokenTypes.STATIC_INIT,
        TokenTypes.LITERAL_SYNCHRONIZED,
        TokenTypes.LITERAL_IF,
        TokenTypes.LITERAL_WHILE,
        TokenTypes.LITERAL_DO,
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_ELSE,
        TokenTypes.LITERAL_TRY,
        TokenTypes.LITERAL_CATCH,
        TokenTypes.LITERAL_FINALLY,
        TokenTypes.COMPACT_CTOR_DEF
    );

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
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        // this is:
        //  switch (var) {
        //     case 3: {
        //        break;
        //     }
        //  }
        //  ... the case SLIST is followed by a user-created SLIST and
        //  preceded by a switch

        final IndentLevel result;
        // if our parent is a block handler we want to be transparent
        if (getParent() instanceof BlockParentHandler
            && !(getParent() instanceof SlistHandler)
            || child instanceof SlistHandler
            && getParent() instanceof CaseHandler) {
            result = getParent().getSuggestedChildIndent(child);
        }
        else {
            result = super.getSuggestedChildIndent(child);
        }
        return result;
    }

    @Override
    protected DetailAST getListChild() {
        return getMainAst();
    }

    @Override
    protected DetailAST getLeftCurly() {
        return getMainAst();
    }

    @Override
    protected DetailAST getRightCurly() {
        return getMainAst().findFirstToken(TokenTypes.RCURLY);
    }

    @Override
    protected DetailAST getTopLevelAst() {
        return null;
    }

    /**
     * Determine if the expression we are handling has a block parent.
     *
     * @return true if it does, false otherwise
     */
    private boolean hasBlockParent() {
        final int parentType = getMainAst().getParent().getType();
        return PARENT_TOKEN_TYPES.get(parentType);
    }

    @Override
    public void checkIndentation() {
        // only need to check this if parent is not
        // an if, else, while, do, ctor, method
        if (!hasBlockParent() && !isSameLineCaseGroup()) {
            super.checkIndentation();
        }
    }

    /**
     * Checks if SLIST node is placed at the same line as CASE_GROUP node.
     *
     * @return true, if SLIST node is places at the same line as CASE_GROUP node.
     */
    private boolean isSameLineCaseGroup() {
        final DetailAST parentNode = getMainAst().getParent();
        return parentNode.getType() == TokenTypes.CASE_GROUP
            && TokenUtil.areOnSameLine(getMainAst(), parentNode);
    }

}
