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

import java.util.Objects;

/**
 * Handler for module definitions.
 *
 */
public class ModuleDefHandler extends BlockParentHandler {

    /**
     * String used to indicate an identifier.
     */
    private static final String IDENT = "ident";

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public ModuleDefHandler(IndentationCheck indentCheck,
                           DetailAST ast,
                           AbstractExpressionHandler parent) {
        super(indentCheck, "module def", ast, parent);
    }

    @Override
    protected DetailAST getLeftCurly() {
        return Objects.requireNonNull(getListChild().findFirstToken(TokenTypes.LCURLY));
    }

    @Override
    protected DetailAST getRightCurly() {
        return Objects.requireNonNull(getListChild().findFirstToken(TokenTypes.RCURLY));
    }

    @Override
    protected DetailAST getTopLevelAst() {
        return getMainAst();
        // note: ident checked by hand in check indentation;
    }

    @Override
    protected DetailAST getListChild() {
        return Objects.requireNonNull(getMainAst().findFirstToken(TokenTypes.DIRECTIVE_BLOCK));
    }

    @Override
    public void checkIndentation() {
        final DetailAST annotations = getMainAst().findFirstToken(TokenTypes.ANNOTATIONS);
        if (annotations != null && annotations.hasChildren()) {
            checkAnnotations();
        }
        else {
            checkIdent();
        }
        checkWrappingIndentation(getMainAst(), getListChild());
        super.checkIndentation();
    }

    /**
     * Checks indentation of identifier token.
     */
    private void checkIdent() {
        final DetailAST ident = getMainAst().findFirstToken(TokenTypes.LITERAL_MODULE);
        if (ident != null) {
            final int lineStart = getLineStart(ident);
            if (!getIndent().isAcceptable(lineStart)) {
                logError(ident, IDENT, lineStart);
            }
        }
    }

    /**
     * Checks annotations for module definitions, skipping wrapped annotations
     * that appear on lines after the first annotation line.
     */
    private void checkAnnotations() {
        final DetailAST annotations = getMainAst().findFirstToken(TokenTypes.ANNOTATIONS);

        if (annotations != null) {
            for (DetailAST annotation = annotations.getFirstChild();
                 annotation != null;
                 annotation = annotation.getNextSibling()) {
                if (isOnStartOfLine(annotation)
                        && !getIndent().isAcceptable(expandedTabsColumnNo(annotation))) {
                    logError(annotation, "annotation", expandedTabsColumnNo(annotation));
                }
            }
        }

        final DetailAST ident = getMainAst().findFirstToken(TokenTypes.LITERAL_MODULE);
        if (ident != null && isOnStartOfLine(ident) && !getIndent().isAcceptable(expandedTabsColumnNo(ident))) {
            logError(ident, IDENT, expandedTabsColumnNo(ident));
        }
    }

    @Override
    protected int[] getCheckedChildren() {
        return new int[] {};
    }

}
