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

/**
 * Handler for inner classes.
 *
 */
public class ObjectBlockHandler extends BlockParentHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public ObjectBlockHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "object def", ast, parent);
    }

    @Override
    protected DetailAST getTopLevelAst() {
        return null;
    }

    @Override
    protected DetailAST getLeftCurly() {
        return getMainAst().findFirstToken(TokenTypes.LCURLY);
    }

    @Override
    protected DetailAST getRightCurly() {
        return getMainAst().findFirstToken(TokenTypes.RCURLY);
    }

    @Override
    protected DetailAST getListChild() {
        return getMainAst();
    }

    @Override
    protected IndentLevel getIndentImpl() {
        final DetailAST parentAST = getMainAst().getParent();
        IndentLevel indent = getParent().getIndent();
        if (parentAST.getType() == TokenTypes.LITERAL_NEW) {
            indent = IndentLevel.addAcceptable(indent, super.getIndentImpl());
        }
        else if (parentAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
            indent = super.getIndentImpl();
        }
        return indent;
    }

    @Override
    public void checkIndentation() {
        // if we have a class or interface as a parent, don't do anything,
        // as this is checked by class def; so
        // only do this if we have a new for a parent (anonymous inner
        // class)
        final DetailAST parentAST = getMainAst().getParent();
        if (parentAST.getType() == TokenTypes.LITERAL_NEW) {
            super.checkIndentation();
        }
    }

    @Override
    protected IndentLevel curlyIndent() {
        final IndentLevel indent = super.curlyIndent();
        return IndentLevel.addAcceptable(indent, indent.getFirstIndentLevel()
                + getLineWrappingIndentation());
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
