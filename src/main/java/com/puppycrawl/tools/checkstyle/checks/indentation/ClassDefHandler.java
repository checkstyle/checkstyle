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
        final int firstModifierLine = getFirstNonAnnotationModifierLine(firstModifier);
        final boolean hasNonAnnotationModifier = hasNonAnnotationModifier(firstModifier);
        boolean visitedFirstModifierLine = false;
        for (DetailAST modifier = firstModifier;
             modifier != null;
             modifier = modifier.getNextSibling()) {
            if (hasNonAnnotationModifier
                    && modifier.getLineNo() == firstModifierLine) {
                visitedFirstModifierLine = true;
            }
            if (shouldLogModifierOnFirstLine(hasNonAnnotationModifier, modifier,
                    firstModifierLine)
                    || shouldLogAnnotationBeforeFirstLine(visitedFirstModifierLine,
                            modifier)) {
                logError(modifier, MODIFIER, expandedTabsColumnNo(modifier));
            }
        }
    }

    /**
     * Determines whether a modifier should be logged on the first modifier line.
     *
     * @param hasNonAnnotationModifier whether a non-annotation modifier exists
     * @param modifier the current modifier token
     * @param firstModifierLine line number of first non-annotation modifier
     * @return {@code true} if the modifier should be logged
     */
    private boolean shouldLogModifierOnFirstLine(boolean hasNonAnnotationModifier,
            DetailAST modifier, int firstModifierLine) {
        return hasNonAnnotationModifier
                && modifier.getLineNo() == firstModifierLine
                && isOnStartOfLine(modifier)
                && !getIndent().isAcceptable(expandedTabsColumnNo(modifier));
    }

    /**
     * Determines whether a modifier before the first modifier line should be logged.
     *
     * @param visitedFirstModifierLine whether the first modifier line was reached
     * @param modifier the current modifier token
     * @return {@code true} if the modifier should be logged
     */
    private boolean shouldLogAnnotationBeforeFirstLine(boolean visitedFirstModifierLine,
            DetailAST modifier) {
        return !visitedFirstModifierLine
                && isOnStartOfLine(modifier)
                && !getIndent().isAcceptable(expandedTabsColumnNo(modifier));
    }

    /**
     * Determines whether modifiers list contains a non-annotation modifier.
     *
     * @param firstModifier the first modifier in the modifiers list
     * @return {@code true} if a non-annotation modifier is present
     */
    private static boolean hasNonAnnotationModifier(DetailAST firstModifier) {
        boolean result = false;
        for (DetailAST modifier = firstModifier;
             modifier != null;
             modifier = modifier.getNextSibling()) {
            if (modifier.getType() != TokenTypes.ANNOTATION) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Finds the line number of the first non-annotation modifier.
     *
     * @param firstModifier the first modifier in the modifiers list
     * @return the line number for the first non-annotation modifier
     */
    private static int getFirstNonAnnotationModifierLine(DetailAST firstModifier) {
        int firstNonAnnotationLine = firstModifier.getLineNo();
        for (DetailAST modifier = firstModifier;
             modifier != null;
             modifier = modifier.getNextSibling()) {
            if (modifier.getType() != TokenTypes.ANNOTATION) {
                firstNonAnnotationLine = modifier.getLineNo();
                break;
            }
        }
        return firstNonAnnotationLine;
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
