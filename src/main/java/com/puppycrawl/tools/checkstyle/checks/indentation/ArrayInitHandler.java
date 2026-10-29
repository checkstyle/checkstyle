///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Handler for array initialization blocks.
 *
 */
public class ArrayInitHandler extends BlockParentHandler {

    /**
     * Indicates that no array element starts on the opening brace's line.
     */
    private static final int NOT_EXIST = -1;

    /**
     * Construct an instance of this handler with the given indentation context,
     * abstract syntax tree, and parent handler.
     *
     * @param context        the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public ArrayInitHandler(IndentationContext context,
        DetailAST ast, AbstractExpressionHandler parent) {
        super(context, "array initialization", ast, parent);
    }

    @Override
    protected IndentLevel getIndentImpl() {
        final DetailAST parentAST = getMainAst().getParent();
        final int type = parentAST.getType();
        final IndentLevel indentLevel;
        if (type == TokenTypes.LITERAL_NEW || type == TokenTypes.ASSIGN) {
            // note: assumes new or assignment is line to align with
            indentLevel = new IndentLevel(getLineStart(parentAST));
        }
        else {
            // at this point getParent() is instance of BlockParentHandler
            indentLevel = ((BlockParentHandler) getParent()).getChildrenExpectedIndent();
        }
        return indentLevel;
    }

    @Override
    protected DetailAST getTopLevelAst() {
        return null;
    }

    @Override
    protected DetailAST getLeftCurly() {
        return getMainAst();
    }

    @Override
    protected IndentLevel curlyIndent() {
        int offset = 0;

        final DetailAST lcurly = getLeftCurly();

        if (isOnStartOfLine(lcurly)
            && lcurly.getParent().getType() != TokenTypes.ARRAY_INIT) {
            offset = getBraceAdjustment();
        }

        final IndentLevel level = new IndentLevel(getIndent(), offset);
        return IndentLevel.addAcceptable(level, level.getLastIndentLevel()
                + getLineWrappingIndentation());
    }

    @Override
    protected DetailAST getRightCurly() {
        return getMainAst().findFirstToken(TokenTypes.RCURLY);
    }

    @Override
    protected boolean canChildrenBeNested() {
        return true;
    }

    @Override
    protected DetailAST getListChild() {
        return getMainAst();
    }

    @Override
    protected IndentLevel getChildrenExpectedIndent() {
        IndentLevel expectedIndent =
            new IndentLevel(getIndent(), getContext().getArrayInitIndent(),
                    getContext().getLineWrappingIndentation());

        final int lcurlyPos = expandedTabsColumnNo(getLeftCurly());
        final int firstChildPos = getArrayFirstElementIndent();

        if (firstChildPos != NOT_EXIST) {
            expectedIndent = IndentLevel.addAcceptable(expectedIndent, firstChildPos, lcurlyPos
                    + getLineWrappingIndentation());
        }
        return expectedIndent;
    }

    /**
     * A shortcut for the corresponding property.
     *
     * @return value of lineWrappingIndentation property from the indentation context
     */
    private int getLineWrappingIndentation() {
        return getContext().getLineWrappingIndentation();
    }

}
