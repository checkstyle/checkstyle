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

package com.puppycrawl.tools.checkstyle.checks.coding;


import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks correct format of
 * <a href="https://docs.oracle.com/en/java/javase/17/text-blocks/index.html">Java Text Blocks</a>
 * as specified in
 * <a href="https://google.github.io/styleguide/javaguide.html#s4.8.9-text-blocks">
 * Google Java Style Guide</a>.
 * </div>
 *
 * <p>
 * This Check has two functions: first is to make sure that both opening and closing quotes
 * ({@code ***}) text block are on new lines(no other item should precede the quotes) and
 * second is to ensure opening and closing quotes are vertically aligned.
 * Note: Closing quotes can be followed by further code on the same line.
 * </p>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code textblock.format.close}
 * </li>
 *
 * <li>
 * {@code textblock.format.open}
 * </li>
 *
 * <li>
 * {@code textblock.indentation}
 * </li>
 * </ul>
 *
 * @since 11.0.0
 */
@StatelessCheck
public class TextBlockGoogleStyleFormattingCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_OPEN_QUOTES_ERROR = "textblock.format.open";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_CLOSE_QUOTES_ERROR = "textblock.format.close";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_INDENTATION_ERROR = "textblock.indentation";

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.TEXT_BLOCK_LITERAL_BEGIN,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!areOpeningQuotesOnCorrectPosition(ast)) {
            log(ast, MSG_OPEN_QUOTES_ERROR);
        }

        final DetailAST closingQuotes = getClosingQuotes(ast);
        if (!areClosingQuotesOnCorrectPosition(getClosingQuotes(ast))) {
            log(closingQuotes, MSG_CLOSE_QUOTES_ERROR);
        }

        if (!isVerticallyAligned(ast, closingQuotes)) {
            log(closingQuotes, MSG_INDENTATION_ERROR);
        }
    }

    /**
     * Checks if opening and closing quotes are vertically aligned.
     *
     * @param openQuotes the ast to check.
     * @param closeQuotes the ast to check.
     * @return true if both quotes have same indentation else false.
     */
    private static boolean isVerticallyAligned(DetailAST openQuotes, DetailAST closeQuotes) {
        return openQuotes.getColumnNo() == closeQuotes.getColumnNo();
    }

    /**
     * Gets the {@code TEXT_BLOCK_LITERAL_END} of a {@code TEXT_BLOCK_LITERAL_BEGIN}.
     *
     * @param ast the ast to check
     * @return DetailAST {@code TEXT_BLOCK_LITERAL_END}
     */
    private static DetailAST getClosingQuotes(DetailAST ast) {
        return ast.getFirstChild().getNextSibling();
    }

    /**
     * Determines if the Opening quotes of text block are not preceded by any code.
     *
     * @param openingQuotes opening quotes
     * @return true if the opening quotes are on the new line.
     */
    private static boolean areOpeningQuotesOnCorrectPosition(DetailAST openingQuotes) {
        DetailAST parent = openingQuotes;
        while (!TokenUtil.isOfType(parent.getType(), TokenTypes.LITERAL_RETURN,
            TokenTypes.ASSIGN, TokenTypes.LITERAL_RETURN, TokenTypes.METHOD_CALL,
            TokenTypes.PLUS)) {

            parent = parent.getParent();

            if (parent.getType() == TokenTypes.PLUS
                    && plusIsBetweenTwoTextBlocks(openingQuotes, parent)) {
                parent = parent.getParent();
            }
        }

        boolean result = !TokenUtil.areOnSameLine(openingQuotes, parent);
        if (parent.getType() == TokenTypes.METHOD_CALL) {
            result = checkMethodCall(openingQuotes, parent);
        }
        else if (parent.getType() == TokenTypes.PLUS) {
            final DetailAST grandParent = parent.getParent();
            if (grandParent.getType() == TokenTypes.PLUS) {
                final DetailAST assign = grandParent.getParent().getParent();
                result = !TokenUtil.areOnSameLine(openingQuotes, assign);
            }
            else if (grandParent.getParent().getType() == TokenTypes.LITERAL_RETURN) {
                result = !TokenUtil.areOnSameLine(openingQuotes, grandParent.getParent());
            }
        }
        return result;
    }

    /**
     * Determines if {@code +} is present between two text blocks.
     *
     * @param ast opening quotes
     * @param plus the plus Ast
     * @return true if {@code +} is present between two text blocks.
     */
    private static boolean plusIsBetweenTwoTextBlocks(DetailAST ast, DetailAST plus) {
        return plus.getFirstChild() == ast
                && plus.getFirstChild().getNextSibling().getType()
                == TokenTypes.TEXT_BLOCK_LITERAL_BEGIN;
    }

    /**
     * Checks the Method call expression.
     *
     * @param openingQuotes the quotes
     * @param methodCall the methodCall
     * @return true
     */
    private static boolean checkMethodCall(DetailAST openingQuotes, DetailAST methodCall) {
        final DetailAST methodCallChild = methodCall.getFirstChild();
        boolean result = !TokenUtil.areOnSameLine(openingQuotes, methodCallChild);

        if (methodCallChild.getType() != TokenTypes.DOT
                || methodCallChild.getFirstChild() == openingQuotes) {

            final DetailAST expressionList = methodCall.findFirstToken(TokenTypes.ELIST);
            final DetailAST node = expressionList.getFirstChild().getFirstChild();

            if (methodCall.getParent().getType() == TokenTypes.PLUS) {
                result = !TokenUtil.areOnSameLine(openingQuotes, methodCall.getParent());
            }

            else if (node != openingQuotes
                    && openingQuotes.getParent().getType() == TokenTypes.EXPR) {
                final DetailAST comma = openingQuotes.getParent().getPreviousSibling();
                result = !TokenUtil.areOnSameLine(openingQuotes, comma);
            }

            else if (methodCall.getParent().getParent().getType() == TokenTypes.LITERAL_RETURN) {
                result = !TokenUtil.areOnSameLine(openingQuotes,
                        methodCall.getParent().getParent());
            }
        }
        return result;
    }

    /**
     * Determines if the Closing quotes of text block are not preceded by any code.
     *
     * @param closingQuotes closing quotes
     * @return true if the closing quotes are on the new line.
     */
    private static boolean areClosingQuotesOnCorrectPosition(DetailAST closingQuotes) {
        final DetailAST content = closingQuotes.getPreviousSibling();
        final String text = content.getText();
        int index = text.length() - 1;
        while (text.charAt(index) == ' ') {
            index--;
        }
        return Character.isWhitespace(text.charAt(index));
    }
}
