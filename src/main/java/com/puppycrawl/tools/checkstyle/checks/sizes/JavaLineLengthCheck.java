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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for long lines but ignores lines whose tokens are specified.
 * </div>
 *
 * <p>
 * Rationale: Long lines are hard to read in printouts or if developers
 * have limited screen space for the source code, e.g. if the IDE displays
 * additional information like project tree, class hierarchy, etc.
 * </p>
 * <ul>
 * <li>
 * Notes:
 * The calculation of the length of a line takes into account the number of
 * expanded spaces for a tab character ({@code '\t'}). The default number of spaces is {@code 8}.
 * To specify a different number of spaces, the user can set
 * <a href="https://checkstyle.org/config.html#Checker">{@code Checker}</a>
 * property {@code tabWidth} which applies to all Checks, including {@code LineLength};
 * or can set property {@code tabWidth} for {@code LineLength} alone.
 * </li>
 * <li>
 * By default, package and import statements (lines matching pattern {@code ^(package|import) .*})
 * are not verified by this check.
 * </li>
 * <li>
 * Trailing comments are taken into consideration while calculating the line length.
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * import java.util.regex.Pattern; // The length of this comment will be taken into consideration
 * </code></pre></div>
 * In the example above the length of the import statement is just 31 characters but total length
 * will be 94 characters.
 * </li>
 * </ul>
 *
 * @since 13.3.0
 */
@FileStatefulCheck
public class JavaLineLengthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLineLen";

    /** Default maximum number of lines. */
    private static final int DEFAULT_MAX_COLUMNS = 100;

    /** Specify the maximum number of lines allowed. */
    private int max = DEFAULT_MAX_COLUMNS;

    /**Specify which tokens to be ignored. */
    private static final int[] DEFAULT_TOKENS = {
            TokenTypes.TEXT_BLOCK_LITERAL_BEGIN,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT
    };

    /** Used to store line numbers of default token types */
    private Set<Integer> ignoredLines;

    /**
     * Setter to specify the maximum line length allowed.
     *
     * @param length the maximum length of a line
     * @since 13.3.0
     */
    public void setMax(int length) {
        this.max = length;
    }

    @Override
    public int[] getDefaultTokens() {
        return DEFAULT_TOKENS.clone();
    }

    @Override
    public int[] getAcceptableTokens() {
        return TokenUtil.getAllTokenIds();
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        ignoredLines = new HashSet<>();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int start = ast.getLineNo();
        final DetailAST lastChild = ast.getLastChild();
        final int end = lastChild != null ? lastChild.getLineNo() : start;

        for (int i = start; i <= end; i++) {
            ignoredLines.add(i);
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        final String[] lines = getLines();

        for (int i = 0; i < lines.length; i++) {
            int lineNo = i + 1;

            if (ignoredLines.contains(lineNo)) {
                continue;
            }

            final String line = lines[i];
            final int lineLength = CommonUtil.lengthExpandedTabs(
                    line, line.codePointCount(0, line.length()), getTabWidth());

            if (lineLength > max) {
                log(lineNo, MSG_KEY, max, lineLength);
            }
        }
    }
}
