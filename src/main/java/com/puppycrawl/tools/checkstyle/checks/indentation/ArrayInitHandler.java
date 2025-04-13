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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Handler for array initialization blocks.
 *
 */
public class ArrayInitHandler extends BlockParentHandler {

    /**
     * Constant to define that the required character does not exist at any position.
     */
    private static final int NOT_EXIST = -1;

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public ArrayInitHandler(IndentationCheck indentCheck,
                            DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "array initialization", ast, parent);
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
            new IndentLevel(getIndent(), getIndentCheck().getArrayInitIndent(),
                getIndentCheck().getLineWrappingIndentation());

        final int firstLine = getFirstLine(getListChild());
        final int lcurlyPos = expandedTabsColumnNo(getLeftCurly());
        final int firstChildPos =
            getNextFirstNonBlankOnLineAfter(firstLine, lcurlyPos);

        if (firstChildPos != NOT_EXIST) {
            expectedIndent = IndentLevel.addAcceptable(expectedIndent, firstChildPos, lcurlyPos
                + getLineWrappingIndentation());
        }
        return expectedIndent;
    }

    /**
     * Returns column number of first non-blank char after
     * specified column on specified line or {@code NOT_EXIST} if
     * such char doesn't exist.
     *
     * @param lineNo   number of line on which we search
     * @param columnNo number of column after which we search
     *
     * @return column number of first non-blank char after
     *         specified column on specified line or {@code NOT_EXIST} if
     *         such char doesn't exist.
     */
    private int getNextFirstNonBlankOnLineAfter(int lineNo, int columnNo) {
        int realColumnNo = columnNo + 1;
        final String line = getIndentCheck().getLines()[lineNo - 1];
        final int lineLength = line.length();
        while (realColumnNo < lineLength
            && Character.isWhitespace(line.charAt(realColumnNo))) {
            realColumnNo++;
        }

        if (realColumnNo == lineLength) {
            realColumnNo = NOT_EXIST;
        }
        return realColumnNo;
    }

    /**
     * A shortcut for {@code IndentationCheck} property.
     *
     * @return value of lineWrappingIndentation property
     *         of {@code IndentationCheck}
     */
    private int getLineWrappingIndentation() {
        return getIndentCheck().getLineWrappingIndentation();
    }

}
