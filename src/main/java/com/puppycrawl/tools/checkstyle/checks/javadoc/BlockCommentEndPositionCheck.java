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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that the Javadoc block comment end <code class="language-java">*&#47;</code>
 * position is either alone on its own line or on the same line as
 * the block comment begin code <code class="language-java">&#47;**</code>.
 * </div>
 *
 * <p>
 * It is possible to enforce two different strategies:
 * </p>
 * <ul>
 * <li>
 * {@code alone} - The block comment end must be alone on its own line.
 * This allows only multi-line Javadoc comments:
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * &#47;**
 *  * Multiple lines of Javadoc text are written here,
 *  * wrapped normally...
 *  *&#47;
 * public void method();
 * </code></pre></div>
 * </li>
 * <li>
 * {@code alone_or_singleline} - The block comment end must be either
 * alone on its own line or on the same line as the block comment begin.
 * This allows multi-line Javadoc as well as single-line Javadoc comments:
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * // Block comment end is alone on its own line
 * &#47;**
 *  * Multiple lines of Javadoc text are written here,
 *  * wrapped normally...
 *  *&#47;
 * public void method();
 *
 * // Single-line Javadoc
 * &#47;** Javadoc summary. *&#47;
 * public void method1();
 * </code></pre></div>
 * </li>
 * </ul>
 *
 * @since 13.3.0
 */
@StatelessCheck
public class BlockCommentEndPositionCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_BLOCK_COMMENT_END = "block.comment.end";

    /**
     * Specify the strategy policy for the block comment end position.
     */
    private BlockCommentEndPositionOption strategy =
            BlockCommentEndPositionOption.ALONE_OR_SINGLELINE;

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    /**
     * Setter to specify the policy on strategy of the block comment end position.
     *
     * @param value string to decode strategy from
     * @throws IllegalArgumentException if unable to decode
     * @since 13.3.0
     */
    public void setStrategy(String value) {
        strategy = BlockCommentEndPositionOption.valueOf(value.trim().toUpperCase(Locale.ENGLISH));
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST lastChild = ast.getLastChild();
        if ((strategy == BlockCommentEndPositionOption.ALONE
                || !isSingleLine(ast, lastChild))
                && !isAlone(lastChild)) {
            log(lastChild, MSG_BLOCK_COMMENT_END, "BLOCK_COMMENT_END");
        }
    }

    /**
     * Checks whether the block comment end token is located alone on its own line.
     *
     * @param ast the block comment end token
     * @return true if block comment end is alone
     */
    private boolean isAlone(DetailAST ast) {
        final String lineText = getLine(ast.getLineNo() - 1).trim();
        return "*/".equals(lineText);
    }

    /**
     * Checks whether the block comment end token is located on the same line
     * as the block comment begin token.
     *
     * @param ast the block comment begin token
     * @param lastChild the block comment end token
     * @return true if block comment end is on same line as block comment begin
     */
    private static boolean isSingleLine(DetailAST ast, DetailAST lastChild) {
        return lastChild.getLineNo() == ast.getLineNo();
    }
}
