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
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

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
 * @since 10.26.1
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
        final DetailAST closingQuotes = getClosingQuotes(ast);
        if (!isStartOfLine(ast)) {
            log(ast, MSG_OPEN_QUOTES_ERROR);
        }
        if (!isStartOfLine(closingQuotes)) {
            log(closingQuotes, MSG_CLOSE_QUOTES_ERROR);
        }
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
     * Determines if the given expression is at the start of a line.
     *
     * @param ast the ast to check
     * @return true if it is, false otherwise
     */
    private boolean isStartOfLine(DetailAST ast) {
        final int tabWidth = getTabWidth();
        final String line = getLine(ast.getLineNo() - 1);

        int index = 0;
        while (Character.isWhitespace(line.charAt(index))) {
            index++;
        }
        return CommonUtil.lengthExpandedTabs(line, index, tabWidth)
                == CommonUtil.lengthExpandedTabs(line, ast.getColumnNo(), tabWidth);
    }
}
