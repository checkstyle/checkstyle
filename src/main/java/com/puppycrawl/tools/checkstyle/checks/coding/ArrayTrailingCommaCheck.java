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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that array initialization contains a trailing comma.
 * </div>
 *
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * int[] a = new int[]
 * {
 *   1,
 *   2,
 *   3,
 * };
 * </code></pre></div>
 *
 * <p>
 * By default, the check demands a comma at the end if neither left nor right curly braces
 * are on the same line as the last element of the array.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * return new int[] { 0 };
 * return new int[] { 0
 *   };
 * return new int[] {
 *   0 };
 * </code></pre></div>
 *
 * <p>
 * Rationale: Putting this comma in makes it easier to change the
 * order of the elements or add new elements on the end. Main benefit of a trailing
 * comma is that when you add new entry to an array, no surrounding lines are changed.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * {
 *   100000000000000000000,
 *   200000000000000000000, // OK
 * }
 *
 * {
 *   100000000000000000000,
 *   200000000000000000000,
 *   300000000000000000000,  // Just this line added, no other changes
 * }
 * </code></pre></div>
 *
 * <p>
 * If closing brace is on the same line as trailing comma, this benefit is gone
 * (as the check does not demand a certain location of curly braces the following
 * two cases will not produce a violation):
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * {100000000000000000000,
 *  200000000000000000000,} // Trailing comma not needed, line needs to be modified anyway
 *
 * {100000000000000000000,
 *  200000000000000000000, // Modified line
 *  300000000000000000000,} // Added line
 * </code></pre></div>
 *
 * <p>
 * If opening brace is on the same line as trailing comma there's also (more arguable) problem:
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * {100000000000000000000, // Line cannot be just duplicated to slightly modify entry
 * }
 *
 * {100000000000000000000,
 *  100000000000000000001, // More work needed to duplicate
 * }
 * </code></pre></div>
 *
 * @since 3.2
 */
@StatelessCheck
public class ArrayTrailingCommaCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "array.trailing.comma";

    /**
     * Control whether to always check for a trailing comma, even when an array is inline.
     */
    private boolean alwaysDemandTrailingComma;

    /**
     * Setter to control whether to always check for a trailing comma, even when an array is inline.
     *
     * @param alwaysDemandTrailingComma whether to always check for a trailing comma.
     * @since 8.33
     */
    public void setAlwaysDemandTrailingComma(boolean alwaysDemandTrailingComma) {
        this.alwaysDemandTrailingComma = alwaysDemandTrailingComma;
    }

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
        return new int[] {TokenTypes.ARRAY_INIT};
    }

    @Override
    public void visitToken(DetailAST arrayInit) {
        final DetailAST rcurly = arrayInit.findFirstToken(TokenTypes.RCURLY);
        final DetailAST previousSibling = rcurly.getPreviousSibling();

        if (arrayInit.getChildCount() != 1
                && (alwaysDemandTrailingComma
                    || !TokenUtil.areOnSameLine(rcurly, previousSibling)
                        && !TokenUtil.areOnSameLine(arrayInit, previousSibling))
                && previousSibling.getType() != TokenTypes.COMMA) {
            log(previousSibling, MSG_KEY);
        }
    }

}
