///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for switch statements.
 *
 */
public class SwitchHandler extends BlockParentHandler {

    /**
     * Token types that, when appearing as a parent or grandparent of the
     * current switch expression, indicate that the expression is likely
     * line-wrapped and should be indented.
     */
    private static final int[] LINE_WRAPPING_INDENT_TRIGGERS = {
        TokenTypes.ASSIGN,
        TokenTypes.SWITCH_RULE,
        TokenTypes.LAMBDA,
    };

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public SwitchHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "switch", ast, parent);
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
        // all children should be taken care of by case handler (plus
        // there is no parent of just the cases, if checking is needed
        // here in the future, an additional way beyond checkChildren()
        // will have to be devised to get children)
        return null;
    }

    @Override
    protected DetailAST getNonListChild() {
        return null;
    }

    /**
     * Check the indentation of the switch expression.
     */
    private void checkSwitchExpr() {
        checkExpressionSubtree(
            getMainAst().findFirstToken(TokenTypes.LPAREN).getNextSibling(),
            getIndent(),
            false,
            false);
    }

    @Override
    protected IndentLevel getIndentImpl() {
        IndentLevel indentLevel = super.getIndentImpl();
        // if switch is starting the line
        if (isOnStartOfLine(getMainAst())) {
            final DetailAST parent = getMainAst().getParent();
            final DetailAST grandParent = parent.getParent();

            if (shouldIndentDueToWrapping(parent, grandParent)) {
                indentLevel = new IndentLevel(indentLevel,
                    getIndentCheck().getLineWrappingIndentation());
            }
        }
        return indentLevel;
    }

    /**
     * Determines if indentation is needed due to line wrapping caused by intermediate nodes
     * between the current AST node and its enclosing handler node.
     *
     * @param directParent The immediate parent node of the current AST node
     * @param grandParent The grandparent node of the current AST node
     * @return true if either the direct parent or grandparent requires additional indentation,
     *         but only when they are not the enclosing handler node itself
     */
    private boolean shouldIndentDueToWrapping(DetailAST directParent, DetailAST grandParent) {
        // The enclosing handler node that already determines base indentation
        // (e.g., method declaration containing our current node)
        final DetailAST enclosingHandlerNode = getParent().getMainAst();
        final var isDirectParentTheHandler = directParent.equals(enclosingHandlerNode);

        final var shouldIndentForDirectParent = !isDirectParentTheHandler
            && isWrappingTrigger(directParent);

        // Check if grandparent requires extra indentation (when
        // neither it nor direct parent is the handler)
        final var shouldIndentForGrandParent = !isDirectParentTheHandler
            && !grandParent.equals(enclosingHandlerNode)
            && isWrappingTrigger(grandParent);

        return shouldIndentForDirectParent || shouldIndentForGrandParent;
    }

    /**
     * Checks if the given AST node represents a construct that typically causes line wrapping
     * and therefore requires additional indentation level.
     *
     * @param astNode The AST node to check
     * @return true if the node type matches one of the line-wrapping triggers
     *         (e.g., assignments, switch rules, lambdas)
     */
    private static boolean isWrappingTrigger(DetailAST astNode) {
        return TokenUtil.isOfType(astNode, LINE_WRAPPING_INDENT_TRIGGERS);
    }

    @Override
    public void checkIndentation() {
        checkSwitchExpr();
        super.checkIndentation();
    }

}
