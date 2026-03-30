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
 * Handler for class definitions.
 *
 */
public class ClassDefHandler extends BlockParentHandler {

    /** Token for modifier. */
    private static final String MODIFIER = "modifier";

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public ClassDefHandler(IndentationCheck indentCheck,
                           DetailAST ast,
                           AbstractExpressionHandler parent) {
        super(indentCheck, getHandlerName(ast), ast, parent);
    }

    @Override
    protected DetailAST getLeftCurly() {
        return getMainAst().findFirstToken(TokenTypes.OBJBLOCK)
            .findFirstToken(TokenTypes.LCURLY);
    }

    @Override
    protected DetailAST getRightCurly() {
        return getMainAst().findFirstToken(TokenTypes.OBJBLOCK)
            .findFirstToken(TokenTypes.RCURLY);
    }

    @Override
    protected DetailAST getTopLevelAst() {
        return null;
        // note: ident checked by hand in check indentation;
    }

    @Override
    protected DetailAST getListChild() {
        return getMainAst().findFirstToken(TokenTypes.OBJBLOCK);
    }

    @Override
    public void checkIndentation() {
        final DetailAST modifiers = getMainAst().findFirstToken(TokenTypes.MODIFIERS);
        if (modifiers.hasChildren()) {
            checkModifiers();
        }
        else {
            if (getMainAst().getType() != TokenTypes.ANNOTATION_DEF) {
                final DetailAST ident = getMainAst().findFirstToken(TokenTypes.IDENT);
                DetailAST tokenToCheck = getMainAst();
                if (ident.getLineNo() == getMainAst().getLineNo()) {
                    tokenToCheck = ident;
                }
                final int lineStart = getLineStart(tokenToCheck);
                if (!getIndent().isAcceptable(lineStart)) {
                    logError(tokenToCheck, "ident", lineStart);
                }
            }
        }
        if (getMainAst().getType() == TokenTypes.ANNOTATION_DEF) {
            final DetailAST atAst = getMainAst().findFirstToken(TokenTypes.AT);
            if (isOnStartOfLine(atAst)) {
                checkWrappingIndentation(getMainAst(), getListChild(), 0,
                        getIndent().getFirstIndentLevel(), false);
            }
        }
        else {
            checkWrappingIndentation(getMainAst(), getListChild());
        }
        super.checkIndentation();
    }

    @Override
    protected int[] getCheckedChildren() {
        return new int[] {
            TokenTypes.EXPR,
            TokenTypes.OBJBLOCK,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_THROW,
            TokenTypes.LITERAL_CONTINUE,
        };
    }

    /**
     * Checks modifiers for class/annotation definitions.
     */
    private void checkModifiers() {
        if (getMainAst().getType() == TokenTypes.ANNOTATION_DEF) {
            checkAnnotationDefModifiers();
        }
        else {
            checkClassDefModifiers();
        }
    }

    /**
     * Checks modifiers for annotation definitions, skipping modifiers that are
     * not at the start of the line.
     */
    private void checkAnnotationDefModifiers() {
        final DetailAST modifiers = getMainAst().findFirstToken(TokenTypes.MODIFIERS);
        for (DetailAST modifier = modifiers.getFirstChild();
             modifier != null;
             modifier = modifier.getNextSibling()) {
            if (isOnStartOfLine(modifier)
                    && !getIndent().isAcceptable(expandedTabsColumnNo(modifier))) {
                logError(modifier, MODIFIER, expandedTabsColumnNo(modifier));
            }
        }
    }

    /**
     * Checks modifiers for class definitions, skipping wrapped modifiers
     * that appear on lines after the first modifier line.
     */
    private void checkClassDefModifiers() {
        final DetailAST modifiers = getMainAst().findFirstToken(TokenTypes.MODIFIERS);
        final DetailAST firstModifier = modifiers.getFirstChild();
        final int firstModifierLine = firstModifier.getLineNo();
        final DetailAST firstWrappedModifier = findFirstWrappedModifier(firstModifier);
        final boolean hasWrappedAnnotationBeforeModifier =
                hasWrappedAnnotationBeforeModifier(firstWrappedModifier);
        for (DetailAST modifier = firstModifier;
             modifier != null;
             modifier = modifier.getNextSibling()) {
            if (isOnStartOfLine(modifier)) {
                final int columnNo = expandedTabsColumnNo(modifier);
                if (!getIndent().isAcceptable(columnNo)) {
                    if (modifier.getLineNo() == firstModifierLine) {
                        logError(modifier, MODIFIER, columnNo);
                    }
                    else if (hasWrappedAnnotationBeforeModifier) {
                        getIndentCheck().indentationLog(modifier, IndentationCheck.MSG_ERROR,
                                getModifierTokenText(modifier), columnNo, getIndent());
                    }
                }
            }
        }
    }

    /**
     * Finds the first modifier that is on a different line than the first modifier.
     *
     * @param firstModifier the first modifier in the modifiers list
     * @return the first wrapped modifier, or null if none
     */
    private static DetailAST findFirstWrappedModifier(DetailAST firstModifier) {
        final int firstModifierLine = firstModifier.getLineNo();
        DetailAST nextModifier = firstModifier.getNextSibling();
        while (nextModifier != null && nextModifier.getLineNo() == firstModifierLine) {
            nextModifier = nextModifier.getNextSibling();
        }
        return nextModifier;
    }

    /**
     * Checks if there is a wrapped annotation followed by a non-annotation
     * modifier on a later line.
     *
     * @param firstWrappedModifier the first modifier on a wrapped line
     * @return true if the pattern is detected
     */
    private static boolean hasWrappedAnnotationBeforeModifier(DetailAST firstWrappedModifier) {
        boolean result = false;
        if (firstWrappedModifier != null
                && firstWrappedModifier.getType() == TokenTypes.ANNOTATION) {
            final int firstWrappedLine = firstWrappedModifier.getLineNo();
            for (DetailAST modifier = firstWrappedModifier.getNextSibling();
                    modifier != null;
                    modifier = modifier.getNextSibling()) {
                if (modifier.getLineNo() > firstWrappedLine
                        && modifier.getType() != TokenTypes.ANNOTATION) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Gets the token text to log for a modifier.
     *
     * @param modifier the modifier node
     * @return the token text to log
     */
    private static String getModifierTokenText(DetailAST modifier) {
        final String tokenText;
        if (modifier.getType() == TokenTypes.ANNOTATION) {
            tokenText = "@";
        }
        else {
            tokenText = modifier.getText();
        }
        return tokenText;
    }

    /**
     * Creates a handler name for this class according to ast type.
     *
     * @param ast the abstract syntax tree.
     * @return handler name for this class.
     */
    private static String getHandlerName(DetailAST ast) {
        final int tokenType = ast.getType();

        return switch (tokenType) {
            case TokenTypes.CLASS_DEF -> "class def";
            case TokenTypes.ENUM_DEF -> "enum def";
            case TokenTypes.ANNOTATION_DEF -> "annotation def";
            case TokenTypes.RECORD_DEF -> "record def";
            default -> "interface def";
        };
    }

}
