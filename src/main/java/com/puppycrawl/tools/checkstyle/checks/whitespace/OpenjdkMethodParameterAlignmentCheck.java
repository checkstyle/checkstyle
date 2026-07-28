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
import java.util.Collection;
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
 * Checks that the parameters of a wrapped method declaration follow one of the two forms
 * allowed by the OpenJDK style guidelines.
 * </div>
 *
 * <p>
 * The
 * <a href="https://checkstyle.org/styleguides/openjdk-java-style-v6/openjdk-styleguide.html#wrapping-method-declarations">
 * OpenJDK Java Style Guidelines</a> allow a wrapped method declaration to be formatted
 * either by listing the parameters vertically, one per line, or by breaking the line and
 * indenting the continuation by eight extra spaces. This check validates a wrapped
 * declaration against those two forms:
 * </p>
 * <ul>
 * <li>
 * when every parameter that starts a new line is indented eight spaces past the declaration,
 * the declaration uses the eight extra spaces form and any number of parameters may share a
 * line;
 * </li>
 * <li>
 * otherwise the parameters are expected to be listed vertically, that is, every parameter that
 * starts a new line is aligned in the same column and each line holds exactly one parameter.
 * </li>
 * </ul>
 *
 * <p>
 * Declarations whose parameters all fit on a single line are ignored.
 * </p>
 *
 * @since 13.10.0
 */
@StatelessCheck
public class OpenjdkMethodParameterAlignmentCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "method.parameter.alignment";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WRAP = "method.parameter.wrap";

    /** The number of extra spaces used to indent a wrapped continuation line. */
    private static final int EIGHT_SPACES = 8;

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
        final List<DetailAST> wrappedStarts = getWrappedStarts(parameterStarts);

        if (!wrappedStarts.isEmpty()) {
            final int eightSpacesColumn = ast.getColumnNo() + EIGHT_SPACES;
            if (!isEightSpaceIndented(wrappedStarts, eightSpacesColumn)) {
                if (isAlignedVertically(wrappedStarts)) {
                    if (hasMultipleParametersOnLine(parameterStarts)) {
                        log(parameterStarts.getFirst(), MSG_KEY);
                    }
                }
                else {
                    log(parameterStarts.getFirst(), MSG_WRAP);
                }
            }
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
     * Collects the parameters that start a new line, that is, every parameter that begins on a
     * different line than the parameter before it.
     *
     * @param parameterStarts the first token of every parameter
     * @return the parameters that start a new line
     */
    private static List<DetailAST> getWrappedStarts(List<DetailAST> parameterStarts) {
        final List<DetailAST> wrappedStarts = new ArrayList<>();
        for (int index = 1; index < parameterStarts.size(); index++) {
            final DetailAST parameter = parameterStarts.get(index);
            if (parameter.getLineNo() != parameterStarts.get(index - 1).getLineNo()) {
                wrappedStarts.add(parameter);
            }
        }
        return wrappedStarts;
    }

    /**
     * Checks whether every parameter that starts a new line is indented eight spaces past the
     * declaration.
     *
     * @param wrappedStarts the parameters that start a new line
     * @param eightSpacesColumn the column that is eight spaces past the declaration
     * @return {@code true} if the declaration uses the eight extra spaces form
     */
    private static boolean isEightSpaceIndented(Collection<DetailAST> wrappedStarts,
                                                int eightSpacesColumn) {
        return wrappedStarts.stream()
                .allMatch(parameter -> parameter.getColumnNo() == eightSpacesColumn);
    }

    /**
     * Checks whether every parameter that starts a new line begins in the same column.
     *
     * @param wrappedStarts the parameters that start a new line
     * @return {@code true} if the parameters are aligned vertically
     */
    private static boolean isAlignedVertically(List<DetailAST> wrappedStarts) {
        final int alignmentColumn = wrappedStarts.getFirst().getColumnNo();
        return wrappedStarts.stream()
                .allMatch(parameter -> parameter.getColumnNo() == alignmentColumn);
    }

    /**
     * Checks whether any parameter is declared on the same line as the parameter before it.
     *
     * @param parameterStarts the first token of every parameter
     * @return {@code true} if a line holds more than one parameter
     */
    private static boolean hasMultipleParametersOnLine(List<DetailAST> parameterStarts) {
        boolean multiple = false;
        for (int index = 1; !multiple && index < parameterStarts.size(); index++) {
            multiple = parameterStarts.get(index).getLineNo()
                    == parameterStarts.get(index - 1).getLineNo();
        }
        return multiple;
    }

}
