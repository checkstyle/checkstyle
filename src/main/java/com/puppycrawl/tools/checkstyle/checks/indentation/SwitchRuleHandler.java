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
 * Handler for switch rules.
 */
public class SwitchRuleHandler extends AbstractExpressionHandler {

    /**
     * The child elements of a switch rule.
     */
    private static final int[] SWITCH_RULE_CHILDREN = {
        TokenTypes.LITERAL_CASE,
        TokenTypes.LITERAL_DEFAULT,
    };

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck the indentation check
     * @param expr        the abstract syntax tree
     * @param parent      the parent handler
     */
    public SwitchRuleHandler(IndentationCheck indentCheck,
                       DetailAST expr, AbstractExpressionHandler parent) {
        super(indentCheck, "case", expr, parent);
    }

    @Override
    protected IndentLevel getIndentImpl() {
        return new IndentLevel(getParent().getIndent(),
            getIndentCheck().getCaseIndent());
    }

    /**
     * Check the indentation of the case statement.
     */
    private void checkCase() {
        checkChildren(getMainAst(), SWITCH_RULE_CHILDREN, getIndent(),
            true, false);
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        return getIndent();
    }

    @Override
    public void checkIndentation() {
        checkCase();
    }

}
