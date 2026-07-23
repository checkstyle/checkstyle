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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that vertically listed parameters of a method declaration are
 * declared one per line.
 * </div>
 *
 * <p>
 * The
 * <a href="https://checkstyle.org/styleguides/openjdk-java-style-v6/openjdk-styleguide.html#wrapping-method-declarations">
 * OpenJDK Java Style Guidelines</a> allow a wrapped method declaration to be formatted
 * either by listing the parameters vertically, or by breaking the line and indenting the
 * continuation by eight extra spaces. This check validates the first form only: when the
 * parameters are aligned under the first parameter, every line of the parameter list must
 * hold exactly one parameter.
 * </p>
 *
 * <p>
 * The parameters are considered to be listed vertically when the first parameter is on the
 * same line as the opening parenthesis and every parameter that starts a new line begins at
 * the column of the first parameter. Declarations that are not wrapped, and wrapped
 * declarations that use the eight extra spaces form, are ignored by this check.
 * </p>
 *
 * @since 13.9.0
 */
@StatelessCheck
public class OpenjdkMethodParameterAlignmentCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "method.parameter.alignment";

    /**
     * Creates a new {@code OpenjdkMethodParameterAlignmentCheck} instance.
     */
    public OpenjdkMethodParameterAlignmentCheck() {
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
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final List<DetailAST> parameterStarts =
                getParameterStarts(ast.findFirstToken(TokenTypes.PARAMETERS));

        if (!parameterStarts.isEmpty() && isListedVertically(ast, parameterStarts)) {
            logParametersSharingLine(parameterStarts);
        }
    }

    /**
     * Collects the first token of every parameter of the given parameter list.
     *
     * @param parameters the {@code PARAMETERS} node of a method declaration
     * @return the first token of every parameter, in declaration order
     */
    private static List<DetailAST> getParameterStarts(DetailAST parameters) {
        final List<DetailAST> parameterStarts = new ArrayList<>();
        TokenUtil.forEachChild(parameters, TokenTypes.PARAMETER_DEF,
                parameter -> parameterStarts.add(CheckUtil.getFirstNode(parameter)));
        return parameterStarts;
    }

    /**
     * Checks whether the parameter list is wrapped and its parameters are listed vertically,
     * that is, the first parameter follows the opening parenthesis and every parameter that
     * starts a new line is aligned with the first parameter.
     *
     * @param methodDef the method declaration to examine
     * @param parameterStarts the first token of every parameter
     * @return {@code true} if the parameters are listed vertically
     */
    private static boolean isListedVertically(DetailAST methodDef,
                                              List<DetailAST> parameterStarts) {
        final DetailAST firstParameter = parameterStarts.get(0);
        final int alignmentColumn = firstParameter.getColumnNo();
        boolean aligned = TokenUtil.areOnSameLine(
                methodDef.findFirstToken(TokenTypes.LPAREN), firstParameter);
        boolean wrapped = false;

        for (int index = 1; aligned && index < parameterStarts.size(); index++) {
            final DetailAST parameter = parameterStarts.get(index);
            if (!TokenUtil.areOnSameLine(parameterStarts.get(index - 1), parameter)) {
                wrapped = true;
                aligned = parameter.getColumnNo() == alignmentColumn;
            }
        }

        return aligned && wrapped;
    }

    /**
     * Logs every parameter that is declared on the same line as the parameter before it.
     *
     * @param parameterStarts the first token of every parameter
     */
    private void logParametersSharingLine(List<DetailAST> parameterStarts) {
        for (int index = 1; index < parameterStarts.size(); index++) {
            final DetailAST parameter = parameterStarts.get(index);
            if (TokenUtil.areOnSameLine(parameterStarts.get(index - 1), parameter)) {
                log(parameter, MSG_KEY);
            }
        }
    }

}
