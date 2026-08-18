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
 * Checks the line wrapping and alignment of a throws clause in a wrapped method declaration
 * according to the OpenJDK style guide.
 * </div>
 *
 * <p>
 * The throws clause must start on a new line and be indented eight spaces relative to either the
 * method declaration or the preceding parameter line. It must also stand out from the parameter
 * list, so alignment with the preceding line is not accepted.
 * </p>
 *
 * @since 13.10.0
 */
@StatelessCheck
public class OpenjdkMethodThrowsAlignmentCheck extends AbstractCheck {

    /** A key is pointing to the warning message text in "messages.properties" file. */
    public static final String MSG_LINE_NEW = "openjdk.method.throws.line.new";

    /** A key is pointing to the warning message text in "messages.properties" file. */
    public static final String MSG_INDENTATION = "openjdk.method.throws.indentation";

    /** Required additional indentation in the OpenJDK style guide. */
    private static final int REQUIRED_INDENTATION = 8;

    /** Tab width prescribed by the OpenJDK style guide. */
    private static final int TAB_WIDTH = 8;

    /**
     * Creates a new {@code OpenjdkMethodThrowsAlignmentCheck} instance.
     */
    public OpenjdkMethodThrowsAlignmentCheck() {
        // no code by default
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
        return new int[] {
            TokenTypes.METHOD_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST throwsAst = ast.findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAst != null) {
            final int declarationLine = getMethodDeclarationLine(ast);
            final DetailAST rightParen = ast.findFirstToken(TokenTypes.RPAREN);
            if (declarationLine != rightParen.getLineNo()) {
                if (throwsAst.getLineNo() == rightParen.getLineNo()) {
                    log(throwsAst, MSG_LINE_NEW);
                }
                else {
                    checkIndentation(throwsAst, declarationLine, rightParen.getLineNo());
                }
            }
        }
    }

    /**
     * Checks the alignment of a throws clause on its own line.
     *
     * @param throwsAst throws clause to check
     * @param declarationLine first line of the method declaration
     * @param previousLine last line of the parameter list
     */
    private void checkIndentation(DetailAST throwsAst, int declarationLine, int previousLine) {
        final int actualIndentation = getExpandedColumn(throwsAst);
        final int declarationIndentation = getLineStart(declarationLine);
        final int previousLineIndentation = getLineStart(previousLine);
        final boolean isDeclarationRelative =
                actualIndentation == declarationIndentation + REQUIRED_INDENTATION;
        final boolean isPreviousLineRelative =
                actualIndentation == previousLineIndentation + REQUIRED_INDENTATION;

        if (actualIndentation == previousLineIndentation
                || !isDeclarationRelative && !isPreviousLineRelative) {
            log(throwsAst, MSG_INDENTATION);
        }
    }

    /**
     * Gets the first line of a method declaration, excluding annotations.
     *
     * @param methodDef method definition
     * @return first declaration line
     */
    private static int getMethodDeclarationLine(DetailAST methodDef) {
        int result = methodDef.findFirstToken(TokenTypes.IDENT).getLineNo();
        final DetailAST type = methodDef.findFirstToken(TokenTypes.TYPE);
        result = Math.min(result, type.getLineNo());
        for (DetailAST modifier = methodDef.findFirstToken(TokenTypes.MODIFIERS).getFirstChild();
                modifier != null; modifier = modifier.getNextSibling()) {
            if (modifier.getType() != TokenTypes.ANNOTATION) {
                result = Math.min(result, modifier.getLineNo());
            }
        }
        return result;
    }

    /**
     * Gets the indentation of a line with tabs expanded.
     *
     * @param lineNo one-based line number
     * @return expanded indentation
     */
    private int getLineStart(int lineNo) {
        final String line = getLine(lineNo - 1);
        final int index = line.length() - line.stripLeading().length();
        return CommonUtil.lengthExpandedTabs(line, index, TAB_WIDTH);
    }

    /**
     * Gets a token's column with tabs expanded.
     *
     * @param ast token to locate
     * @return expanded column
     */
    private int getExpandedColumn(DetailAST ast) {
        final String line = getLine(ast.getLineNo() - 1);
        return CommonUtil.lengthExpandedTabs(line, ast.getColumnNo(), TAB_WIDTH);
    }

}
