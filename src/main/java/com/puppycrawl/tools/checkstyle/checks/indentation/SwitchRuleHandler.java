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
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

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
        // only need to check if the case statement is not on the same line as
        // the parent switch statement
        if (!isSameLineAsSwitch(getMainAst().getParent())) {
            checkChildren(getMainAst(), SWITCH_RULE_CHILDREN, getIndent(),
                    true, false);
        }
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        final IndentLevel result;

        if (child instanceof SlistHandler || isSameLineAsSwitch(child.getMainAst())) {
            // switchRule with block body (enclosed in {})
            result = getIndent();
        }
        else {
            // Single-expression switchRule (no {} block):
            // assume line wrapping and add additional indentation
            // for the statement in the next line.
            result = new IndentLevel(getIndent(), getIndentCheck().getLineWrappingIndentation());
        }

        return result;
    }

    @Override
    public void checkIndentation() {
        checkCase();
    }

    /**
     * Checks if the current SWITCH_RULE node is placed on the same line
     * as the given SWITCH_LITERAL node.
     *
     * @param node the SWITCH_LITERAL node to compare with
     * @return true if the current SWITCH_RULE node is on the same line
     *     as the given SWITCH_LITERAL node
     */
    private boolean isSameLineAsSwitch(DetailAST node) {
        return node.getType() == TokenTypes.LITERAL_SWITCH
            && TokenUtil.areOnSameLine(getMainAst(), node);
    }

}
