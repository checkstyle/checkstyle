////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
 * Handler for array initialization blocks.
 *
 */
public class AnnotationArrayInitHandler extends BlockParentHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public AnnotationArrayInitHandler(IndentationCheck indentCheck,
                            DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "annotation array initialization", ast, parent);
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
        final IndentLevel level = new IndentLevel(getIndent(), getBraceAdjustment());
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

        final int firstLine = getFirstLine(Integer.MAX_VALUE, getListChild());
        final int lcurlyPos = expandedTabsColumnNo(getLeftCurly());
        final int firstChildPos =
            getNextFirstNonBlankOnLineAfter(firstLine, lcurlyPos);
        if (firstChildPos >= 0) {
            expectedIndent = addAnnotationArrayIndentationLevels(expectedIndent,
                firstChildPos, lcurlyPos);
        }
        return expectedIndent;
    }

    /**
     * Returns column number of first non-blank char after
     * specified column on specified line or -1 if
     * such char doesn't exist.
     *
     * @param lineNo   number of line on which we search
     * @param columnNo number of column after which we search
     *
     * @return column number of first non-blank char after
     *         specified column on specified line or -1 if
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
            realColumnNo = -1;
        }
        return realColumnNo;
    }

    /**
     * A shortcut for {@code IndentationCheck} property.
     * @return value of lineWrappingIndentation property
     *         of {@code IndentationCheck}
     */
    private int getLineWrappingIndentation() {
        return getIndentCheck().getLineWrappingIndentation();
    }

    /**
     * A shortcut for {@code IndentationCheck} property.
     * @return value of arrayInitIndent property
     *         of {@code IndentationCheck}
     */
    private int getArrayInitIndentation() {
        return getIndentCheck().getArrayInitIndent();
    }

    /**
     * Add indentation levels for {@code ANNOTATION_ARRAY_INIT} handler.
     * @param expectedIndent        initial expected indent levels
     * @param firstChildPosition    first child position
     * @param lcurlyPos             left curly brace position
     * @return                      all the expected indent levels before firstChild
     */
    private IndentLevel addAnnotationArrayIndentationLevels(IndentLevel expectedIndent,
                                                            int firstChildPosition, int lcurlyPos) {
        IndentLevel annotationIndents = expectedIndent;
        if (getLineWrappingIndentation() != 0 && getArrayInitIndentation() != 0) {
            int startIndex = expectedIndent.getLastIndentLevel();
            while (startIndex < firstChildPosition - getLineWrappingIndentation()) {
                annotationIndents = IndentLevel.addAcceptable(annotationIndents,
                    startIndex + getLineWrappingIndentation(),
                    startIndex + getArrayInitIndentation());
                startIndex += Math.max(getLineWrappingIndentation(), getArrayInitIndentation());
            }
        }
        annotationIndents = IndentLevel.addAcceptable(annotationIndents, firstChildPosition,
            lcurlyPos + getLineWrappingIndentation());
        return annotationIndents;
    }
}
