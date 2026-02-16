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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks correct format of
 * <a href="https://docs.oracle.com/en/java/javase/17/text-blocks/index.html">
 * Java Text Blocks</a>
 * as specified in
 * <a href="https://google.github.io/styleguide/javaguide.html#s4.8.9-text-blocks">
 * Google Java Style Guide</a>.
 * </div>
 * This Check performs three validations:
 * <ol>
 *   <li>
 *    It ensures that the opening and closing text-block quotes
 *    ({@code """}) each appear on their own line, with no other
 *    item preceding them.
 *   </li>
 *   <li>
 *    Opening and closing quotes are vertically aligned.
 *   </li>
 *   <li>
 *    Each line in the text-block is indented at least as much as
 *    the opening and closing quotes.
 *   </li>
 * </ol>
 * Note: Closing quotes can be followed by additional code on the
 * same line.
 *
 * @since 12.3.0
 */
@StatelessCheck
public final class TextBlockGoogleStyleFormattingCheck
        extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in
     * "messages.properties" file.
     */
    public static final String MSG_OPEN_QUOTES_ERROR =
            "textblock.format.open";

    /**
     * A key is pointing to the warning message text in
     * "messages.properties" file.
     */
    public static final String MSG_CLOSE_QUOTES_ERROR =
            "textblock.format.close";

    /**
     * A key is pointing to the warning message text in
     * "messages.properties" file.
     */
    public static final String MSG_VERTICALLY_UNALIGNED =
            "textblock.vertically.unaligned";

    /**
     * A key is pointing to the warning message text in
     * "messages.properties" file.
     */
    public static final String MSG_LINE_INDENTATION =
            "textblock.line.indentation";

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
    public void visitToken(final DetailAST ast) {
        final boolean openingQuotesAlone = openingQuotesAreAloneOnTheLine(ast);
        if (!openingQuotesAlone) {
            log(ast, MSG_OPEN_QUOTES_ERROR);
        }

        final DetailAST closingQuotes = getClosingQuotes(ast);
        final boolean closingQuotesAlone =
                closingQuotesAreAloneOnTheLine(closingQuotes);
        if (!closingQuotesAlone) {
            log(closingQuotes, MSG_CLOSE_QUOTES_ERROR);
        }

        final boolean quotesAligned = quotesAreVerticallyAligned(
                ast, closingQuotes);
        if (!quotesAligned) {
            log(closingQuotes, MSG_VERTICALLY_UNALIGNED);
        }

        // Only check content indentation if quotes are properly formatted
        if (openingQuotesAlone && quotesAligned) {
            checkContentIndentation(ast);
        }
    }

    /**
     * Checks if opening and closing quotes are vertically aligned.
     *
     * @param openQuotes the ast to check.
     * @param closeQuotes the ast to check.
     * @return true if both quotes have same indentation else false.
     */
    private static boolean quotesAreVerticallyAligned(
            final DetailAST openQuotes, final DetailAST closeQuotes) {
        return openQuotes.getColumnNo() == closeQuotes.getColumnNo();
    }

    /**
     * Gets the text block literal end of text block literal begin.
     *
     * @param ast the ast to check
     * @return DetailAST {@code TEXT_BLOCK_LITERAL_END}
     */
    private static DetailAST getClosingQuotes(final DetailAST ast) {
        return ast.getFirstChild().getNextSibling();
    }

    /**
     * Determines if the Opening quotes of text block are not preceded
     * by any code.
     *
     * @param openingQuotes opening quotes
     * @return true if the opening quotes are on the new line.
     */
    private static boolean openingQuotesAreAloneOnTheLine(
            final DetailAST openingQuotes) {
        DetailAST parent = openingQuotes;
        boolean quotesAreNotPreceded = true;
        while (quotesAreNotPreceded || parent.getType() == TokenTypes.ELIST
                || parent.getType() == TokenTypes.EXPR) {

            parent = parent.getParent();

            if (parent.getType() == TokenTypes.METHOD_DEF) {
                quotesAreNotPreceded = !quotesArePrecededWithComma(
                        openingQuotes);
            }
            else if (parent.getType() == TokenTypes.QUESTION
                    && openingQuotes.getPreviousSibling() != null) {
                quotesAreNotPreceded = !TokenUtil.areOnSameLine(openingQuotes,
                        openingQuotes.getPreviousSibling());
            }
            else {
                quotesAreNotPreceded = !TokenUtil.areOnSameLine(
                        openingQuotes, parent);
            }

            if (TokenUtil.isOfType(parent.getType(),
                    TokenTypes.LITERAL_RETURN,
                    TokenTypes.VARIABLE_DEF,
                    TokenTypes.METHOD_DEF,
                    TokenTypes.CTOR_DEF,
                    TokenTypes.ENUM_DEF,
                    TokenTypes.CLASS_DEF)) {
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
    private static boolean quotesArePrecededWithComma(
            final DetailAST openingQuotes) {
        final DetailAST expression = openingQuotes.getParent();
        return expression.getPreviousSibling() != null
                && TokenUtil.areOnSameLine(openingQuotes,
                        expression.getPreviousSibling());
    }

    /**
     * Determines if the Closing quotes of text block are not preceded
     * by any code.
     *
     * @param closingQuotes closing quotes
     * @return true if the closing quotes are on the new line.
     */
    private static boolean closingQuotesAreAloneOnTheLine(
            final DetailAST closingQuotes) {
        final DetailAST content = closingQuotes.getPreviousSibling();
        final String text = content.getText();
        int index = text.length() - 1;
        while (index != -1) {
            if (text.charAt(index) != ' ') {
                break;
            }
            --index;
        }
        return index == -1 || Character.isWhitespace(text.charAt(index));
    }

    /**
     * Checks that each line in the text block content is indented
     * at least as much as the opening and closing quotes.
     *
     * @param openingQuotes opening quotes
     */
    private void checkContentIndentation(final DetailAST openingQuotes) {
        final int expectedIndentation = openingQuotes.getColumnNo();
        final DetailAST content = openingQuotes.getFirstChild();
        final String contentText = content.getText();
        final String[] lines = contentText.split("\n", -1);
        final int contentLineNumber = content.getLineNo();

        // Skip the first line (empty line after opening quotes)
        // and the last line (empty line before closing quotes)
        for (int lineIndex = 1; lineIndex != lines.length - 1; ++lineIndex) {
            final String line = lines[lineIndex];
            // Skip completely blank lines
            if (!line.isBlank()) {
                final int actualIndentation = getIndentation(line);
                if (actualIndentation < expectedIndentation) {
                    final DetailAST violationAst = createViolationAst(
                            contentLineNumber + lineIndex, actualIndentation);
                    log(violationAst, MSG_LINE_INDENTATION);
                }
            }
        }
    }

    /**
     * Creates a synthetic AST node to report violations at specific line/column.
     *
     * @param lineNo line number of violation
     * @param columnNo zero-based column number of violation
     * @return synthetic ast with the provided location
     */
    private static DetailAST createViolationAst(int lineNo, int columnNo) {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setLineNo(lineNo);
        ast.setColumnNo(columnNo);
        return ast;
    }

    /**
     * Gets the indentation level (number of leading whitespace
     * characters) of a line.
     *
     * @param line the line to check
     * @return the indentation level
     */
    private static int getIndentation(final String line) {
        int indentation = 0;
        while (indentation < line.length()) {
            if (!Character.isWhitespace(line.charAt(indentation))) {
                break;
            }
            ++indentation;
        }
        return indentation;
    }
}
