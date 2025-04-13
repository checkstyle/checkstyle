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
 * Handler for labels.
 *
 */
public class LabelHandler extends AbstractExpressionHandler {

    /**
     * The types of expressions that are children of a label.
     */
    private static final int[] LABEL_CHILDREN = {
        TokenTypes.IDENT,
    };

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param expr          the abstract syntax tree
     * @param parent        the parent handler
     */
    public LabelHandler(IndentationCheck indentCheck,
                        DetailAST expr, AbstractExpressionHandler parent) {
        super(indentCheck, "label", expr, parent);
    }

    @Override
    protected IndentLevel getIndentImpl() {
        final IndentLevel level = new IndentLevel(super.getIndentImpl(), -getBasicOffset());
        return IndentLevel.addAcceptable(level, super.getIndentImpl());
    }

    /**
     * Check the indentation of the label.
     */
    private void checkLabel() {
        checkChildren(getMainAst(), LABEL_CHILDREN, getIndent(), true, false);
    }

    @Override
    public void checkIndentation() {
        checkLabel();
        // need to check children (like 'block' parents do)
        final DetailAST parent = getMainAst().getFirstChild().getNextSibling();

        final IndentLevel expected =
            new IndentLevel(getIndent(), getBasicOffset());

        checkExpressionSubtree(parent, expected, true, false);
    }

}
