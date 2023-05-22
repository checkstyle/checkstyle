///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
 * <p>
 * Checks if unnecessary semicolon is used in last resource declaration.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowWhenNoBraceAfterSemicolon} -
 * Allow unnecessary semicolon if closing paren is not on the same line.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;UnnecessarySemicolonInTryWithResources&quot;/&gt;
 * </pre>
 * <p>
 * Example of violations
 * </p>
 * <pre>
 * class A {
 *     void method() throws IOException {
 *         try(Reader r1 = new PipedReader();){} // violation
 *         try(Reader r4 = new PipedReader();Reader r5 = new PipedReader()
 *         ;){} // violation
 *         try(Reader r6 = new PipedReader();
 *             Reader r7
 *                    = new PipedReader();
 *         ){}
 *     }
 * }
 * </pre>
 * <p>
 * To configure the check to detect unnecessary semicolon
 * if closing paren is not on same line
 * </p>
 * <pre>
 * &lt;module name="UnnecessarySemicolonInTryWithResources"&gt;
 *   &lt;property name="allowWhenNoBraceAfterSemicolon" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of exclusion
 * </p>
 * <pre>
 * class A {
 *     void method() throws IOException {
 *         try(Reader r1 = new PipedReader();){} // violation
 *         try(Reader r6 = new PipedReader();
 *             Reader r7 = new PipedReader(); // violation
 *         ){}
 *     }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code unnecessary.semicolon}
 * </li>
 * </ul>
 *
 * @since 8.22
 */
@StatelessCheck
public final class UnnecessarySemicolonInTryWithResourcesCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SEMI = "unnecessary.semicolon";

    /** Allow unnecessary semicolon if closing paren is not on the same line. */
    private boolean allowWhenNoBraceAfterSemicolon = true;

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
            TokenTypes.RESOURCE_SPECIFICATION,
        };
    }

    /**
     * Setter to allow unnecessary semicolon if closing paren is not on the same line.
     *
     * @param allowWhenNoBraceAfterSemicolon a value to set.
     */
    public void setAllowWhenNoBraceAfterSemicolon(boolean allowWhenNoBraceAfterSemicolon) {
        this.allowWhenNoBraceAfterSemicolon = allowWhenNoBraceAfterSemicolon;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST closingParen = ast.getLastChild();
        final DetailAST tokenBeforeCloseParen = closingParen.getPreviousSibling();
        if (tokenBeforeCloseParen.getType() == TokenTypes.SEMI
            && (!allowWhenNoBraceAfterSemicolon
                || TokenUtil.areOnSameLine(closingParen, tokenBeforeCloseParen))) {
            log(tokenBeforeCloseParen, MSG_SEMI);
        }
    }

}
