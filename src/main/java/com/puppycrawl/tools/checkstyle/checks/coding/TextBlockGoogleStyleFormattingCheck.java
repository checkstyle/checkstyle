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
 * This Check performs two validations:
 * <ol>
 *   <li>
 *    It ensures that the opening and closing text-block quotes ({@code """}) each appear on their
 *    own line, with no other item preceding them.
 *   </li>
 *   <li>
 *    Opening and closing quotes are vertically aligned.
 *   </li>
 * </ol>
 * Note: Closing quotes can be followed by additional code on the same line.
 *
 * @since 12.3.0
 */
@StatelessCheck
public class TextBlockGoogleStyleFormattingCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_OPEN_QUOTES_ERROR = "textblock.format.open";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_CLOSE_QUOTES_ERROR = "textblock.format.close";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_VERTICALLY_UNALIGNED = "textblock.vertically.unaligned";

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
        if (!openingQuotesAreAloneOnTheLine(ast)) {
            log(ast, MSG_OPEN_QUOTES_ERROR);
        }

        final DetailAST closingQuotes = getClosingQuotes(ast);
        if (!closingQuotesAreAloneOnTheLine(closingQuotes)) {
            log(closingQuotes, MSG_CLOSE_QUOTES_ERROR);
        }

        if (!quotesAreVerticallyAligned(ast, closingQuotes)) {
            log(closingQuotes, MSG_VERTICALLY_UNALIGNED);
        }
    }

    /**
     * Checks if opening and closing quotes are vertically aligned.
     *
     * @param openQuotes the ast to check.
     * @param closeQuotes the ast to check.
     * @return true if both quotes have same indentation else false.
     */
    private static boolean quotesAreVerticallyAligned(DetailAST openQuotes, DetailAST closeQuotes) {
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
    private static boolean openingQuotesAreAloneOnTheLine(DetailAST openingQuotes) {
        DetailAST parent = openingQuotes;
        boolean quotesAreNotPreceded = true;
        while (quotesAreNotPreceded || parent.getType() == TokenTypes.ELIST
                || parent.getType() == TokenTypes.EXPR) {

            parent = parent.getParent();

            if (parent.getType() == TokenTypes.METHOD_DEF) {
                quotesAreNotPreceded = !quotesArePrecededWithComma(openingQuotes);
            }
            else if (parent.getType() == TokenTypes.QUESTION
                    && openingQuotes.getPreviousSibling() != null) {
                quotesAreNotPreceded = !TokenUtil.areOnSameLine(openingQuotes,
                        openingQuotes.getPreviousSibling());
            }
            else {
                quotesAreNotPreceded = !TokenUtil.areOnSameLine(openingQuotes, parent);
            }

            if (TokenUtil.isOfType(parent.getType(),
                    TokenTypes.LITERAL_RETURN,
                    TokenTypes.VARIABLE_DEF,
                    TokenTypes.METHOD_DEF,
                    TokenTypes.CTOR_DEF,
                    TokenTypes.ENUM_DEF)) {
                break;
            }
        }
        return quotesAreNotPreceded;
    }

    /**
     * Determines if opening quotes are preceded by {@code ,}.
     *
     * @param openingQuotes the quotes
     * @return true if {@code ,} is present before opening quotes.
     */
    private static boolean quotesArePrecededWithComma(DetailAST openingQuotes) {
        final DetailAST expression = openingQuotes.getParent();
        return expression.getType() == TokenTypes.EXPR
                && expression.getPreviousSibling() != null
                && TokenUtil.areOnSameLine(openingQuotes, expression.getPreviousSibling());
    }

    /**
     * Determines if the Closing quotes of text block are not preceded by any code.
     *
     * @param closingQuotes closing quotes
     * @return true if the closing quotes are on the new line.
     */
    private static boolean closingQuotesAreAloneOnTheLine(DetailAST closingQuotes) {
        final DetailAST content = closingQuotes.getPreviousSibling();
        final String text = content.getText();
        int index = text.length() - 1;
        while (text.charAt(index) == ' ') {
            index--;
        }
        return Character.isWhitespace(text.charAt(index));
    }
}
