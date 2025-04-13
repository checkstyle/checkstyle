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
 * Handler for method definitions.
 *
 */
public class MethodDefHandler extends BlockParentHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public MethodDefHandler(IndentationCheck indentCheck,
                            DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, getHandlerName(ast), ast, parent);
    }

    @Override
    protected DetailAST getTopLevelAst() {
        // we check this stuff ourselves below
        return null;
    }

    @Override
    protected void checkModifiers() {
        final DetailAST modifier = getMainAst().findFirstToken(TokenTypes.MODIFIERS);
        if (isOnStartOfLine(modifier)
            && !getIndent().isAcceptable(expandedTabsColumnNo(modifier))) {
            logError(modifier, "modifier", expandedTabsColumnNo(modifier));
        }
    }

    /**
     * Check the indentation level of the throws clause.
     */
    private void checkThrows() {
        final DetailAST throwsAst = getMainAst().findFirstToken(TokenTypes.LITERAL_THROWS);

        if (throwsAst != null) {
            checkWrappingIndentation(throwsAst, throwsAst.getNextSibling(), getIndentCheck()
                    .getThrowsIndent(), getLineStart(getMethodDefLineStart(getMainAst())),
                !isOnStartOfLine(throwsAst));
        }
    }

    /**
     * Gets the start line of the method, excluding any annotations. This is required because the
     * current {@link TokenTypes#METHOD_DEF} may not always be the start as seen in
     * <a href="https://github.com/checkstyle/checkstyle/issues/3145">#3145</a>.
     *
     * @param mainAst
     *            The method definition ast.
     * @return The start column position of the method.
     */
    private static int getMethodDefLineStart(DetailAST mainAst) {
        // get first type position
        int lineStart = mainAst.findFirstToken(TokenTypes.IDENT).getLineNo();

        // check if there is a type before the indent
        final DetailAST typeNode = mainAst.findFirstToken(TokenTypes.TYPE);
        if (typeNode != null) {
            lineStart = getFirstLine(typeNode);
        }

        // check if there is a modifier before the type
        for (DetailAST node = mainAst.findFirstToken(TokenTypes.MODIFIERS).getFirstChild();
             node != null;
             node = node.getNextSibling()) {
            // skip annotations as we check them else where as outside the method
            if (node.getType() == TokenTypes.ANNOTATION) {
                continue;
            }

            if (node.getLineNo() < lineStart) {
                lineStart = node.getLineNo();
            }
        }

        return lineStart;
    }

    @Override
    public void checkIndentation() {
        checkModifiers();
        checkThrows();

        if (getMethodDefParamRightParen(getMainAst()) != null) {
            checkWrappingIndentation(getMainAst(), getMethodDefParamRightParen(getMainAst()));
        }
        // abstract method def -- no body
        if (getLeftCurly() != null) {
            super.checkIndentation();
        }
    }

    /**
     * Returns right parenthesis of method definition parameter list.
     *
     * @param methodDefAst
     *          method definition ast node(TokenTypes.LITERAL_IF)
     * @return right parenthesis of method definition parameter list.
     */
    private static DetailAST getMethodDefParamRightParen(DetailAST methodDefAst) {
        return methodDefAst.findFirstToken(TokenTypes.RPAREN);
    }

    /**
     * Creates a handler name for this class according to ast type.
     *
     * @param ast the abstract syntax tree.
     * @return handler name for this class.
     */
    private static String getHandlerName(DetailAST ast) {
        final String name;

        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
                name = "ctor def";
                break;
            case TokenTypes.ANNOTATION_FIELD_DEF:
                name = "annotation field def";
                break;
            case TokenTypes.COMPACT_CTOR_DEF:
                name = "compact ctor def";
                break;
            default:
                name = "method def";
        }

        return name;
    }

}
