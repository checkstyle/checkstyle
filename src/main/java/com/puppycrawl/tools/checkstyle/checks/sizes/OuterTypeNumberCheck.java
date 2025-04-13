////
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
///

package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks for the number of types declared at the <i>outer</i> (or <i>root</i>) level in a file.
 * </div>
 *
 * <p>
 * Rationale: It is considered good practice to only define one outer type per file.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum number of outer types allowed.
 * Type is {@code int}.
 * Default value is {@code 1}.
 * </li>
 * </ul>
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
 * {@code maxOuterTypes}
 * </li>
 * </ul>
 *
 * @since 5.0
 */
@FileStatefulCheck
public class OuterTypeNumberCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxOuterTypes";

    /** Specify the maximum number of outer types allowed. */
    private int max = 1;
    /** Tracks the current depth in types. */
    private int currentDepth;
    /** Tracks the number of outer types found. */
    private int outerNum;

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
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST ast) {
        currentDepth = 0;
        outerNum = 0;
    }

    @Override
    public void finishTree(DetailAST ast) {
        if (max < outerNum) {
            log(ast, MSG_KEY, outerNum, max);
        }
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (currentDepth == 0) {
            outerNum++;
        }
        currentDepth++;
    }

    @Override
    public void leaveToken(DetailAST ast) {
        currentDepth--;
    }

    /**
     * Setter to specify the maximum number of outer types allowed.
     *
     * @param max the new number.
     * @since 5.0
     */
    public void setMax(int max) {
        this.max = max;
    }

}
