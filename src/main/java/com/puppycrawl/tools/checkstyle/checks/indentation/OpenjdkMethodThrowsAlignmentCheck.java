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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks that the {@code throws} clause of a wrapped method or constructor declaration
 * is properly aligned according to the
 * <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html#toc-wrapping-method-declarations">
 * OpenJDK Java Style Guide</a>.
 * </div>
 *
 * <p>
 * This check only applies when the method or constructor declaration is
 * <em>wrapped</em>, that is, the parameter list spans more than one line.
 * Single-line declarations are not in scope.
 * </p>
 *
 * <p>
 * The two rules enforced are:
 * </p>
 * <ol>
 * <li>The {@code throws} keyword must start on a <em>new line</em>, it must not share
 * the line with the closing {@code )} of the parameter list.</li>
 * <li>The {@code throws} keyword must <em>stand out</em> from the parameter list by
 * being indented 8 columns relative to <em>either</em>:
 * <ul>
 * <li>the column of the method/constructor declaration (i.e. the indentation of the
 * line containing the declaration keyword)</li>
 * <li>the indentation (first non-whitespace column) of the source line immediately
 * above the line where {@code throws} appears</li>
 * </ul>
 * </li>
 * </ol>
 *
 * @since 14.2.0
 */
@StatelessCheck
public class OpenjdkMethodThrowsAlignmentCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_NOT_ON_NEW_LINE = "openjdk.throws.new.line";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_WRONG_INDENTATION = "openjdk.throws.indentation";

    /**
     * The Indentation the throws clause needs relative to either baseline
     * (the method declaration column or the previous-line indentation).
     */
    private static final int LINE_WRAPPING_INDENTATION = 8;

    /**
     * Creates a new {@code OpenjdkMethodThrowsAlignmentCheck} instance.
     */
    public OpenjdkMethodThrowsAlignmentCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST throwsAst = ast.findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAst != null) {
            final int lparenLineNo =
                ast.findFirstToken(TokenTypes.LPAREN).getLineNo();
            final int rparenLineNo =
                ast.findFirstToken(TokenTypes.RPAREN).getLineNo();

            if (lparenLineNo != rparenLineNo) {
                final int throwsLineNo = throwsAst.getLineNo();

                if (throwsLineNo == rparenLineNo) {
                    log(throwsAst, MSG_KEY_NOT_ON_NEW_LINE);
                }
                else {
                    final int throwsCol = throwsAst.getColumnNo();
                    final int declCol = ast.getColumnNo();
                    final int prevLineIndent = getIndentOfLine(throwsLineNo - 1);
                    final boolean indentedFromDecl =
                            throwsCol - declCol == LINE_WRAPPING_INDENTATION;
                    final boolean indentedFromPrev =
                            throwsCol - prevLineIndent == LINE_WRAPPING_INDENTATION;

                    if (throwsCol == prevLineIndent
                            || !indentedFromDecl && !indentedFromPrev) {
                        log(throwsAst, MSG_KEY_WRONG_INDENTATION);
                    }
                }
            }
        }
    }

    /**
     * Returns the indentation (column of the first non-whitespace character)
     * of the given 1-indexed source line number.
     *
     * @param lineNo 1-indexed line number of the source line to inspect.
     * @return 0-indexed column of the first non-whitespace character on that line,
     *         or the full line length if the line is blank.
     */
    private int getIndentOfLine(int lineNo) {
        final String line = getLines()[lineNo - 1];
        return CommonUtil.indexOfNonWhitespace(line);
    }

}
