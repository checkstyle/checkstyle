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
 * Handler for annotation array initialization blocks.
 *
 */
public class AnnotationArrayInitHandler extends BlockParentHandler {

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
    public AnnotationArrayInitHandler(IndentationContext context,
                            DetailAST ast, AbstractExpressionHandler parent) {
        super(context, "annotation array initialization", ast, parent);
    }

    @Override
    protected IndentLevel getIndentImpl() {
        final DetailAST parentAST = getMainAst().getParent();
        return new IndentLevel(getLineStart(parentAST));
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
        if (isOnStartOfLine(lcurly)) {
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
            new IndentLevel(getIndent(), getArrayInitIndentation(), getLineWrappingIndentation());

        final DetailAST leftCurly = getLeftCurly();
        final int lcurlyPos = expandedTabsColumnNo(leftCurly);
        final int firstChildPos = getArrayFirstElementIndent();

        // the code is written with old style where curlies are given their own line,
        // the code block should be aligned to lcurly pos + lineWrappingIndent
        if (firstChildPos == NOT_EXIST && lcurlyPos == getLineStart(leftCurly)) {
            expectedIndent = IndentLevel.addAcceptable(expectedIndent,
                        lcurlyPos + getLineWrappingIndentation());
        }

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

    /**
     * A shortcut for the corresponding property.
     *
     * @return value of arrayInitIndent property from the indentation context
     */
    private int getArrayInitIndentation() {
        return getContext().getArrayInitIndent();
    }

}
