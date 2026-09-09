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
 * starts a new line is aligned with the first parameter and each line holds exactly one
 * parameter.
 * </li>
 * </ul>
 *
 * <p>
 * Declarations whose parameters all fit on a single line are ignored.
 * </p>
 *
 * @since 14.1.0
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
        final List<DetailAST> parameters =
                getParameters(ast.findFirstToken(TokenTypes.PARAMETERS));
        final List<DetailAST> wrappedStarts = getWrappedStarts(parameters);
        final int eightSpacesColumn = expandedColumnNo(ast) + EIGHT_SPACES;

        if (!isAlignedOn(wrappedStarts, eightSpacesColumn)) {
            final DetailAST firstParameter = CheckUtil.getFirstNode(parameters.getFirst());
            if (isAlignedOn(wrappedStarts, expandedColumnNo(firstParameter))) {
                if (hasMultipleParametersOnLine(parameters, wrappedStarts)) {
                    log(firstParameter, MSG_KEY);
                }
            }
            else {
                log(firstParameter, MSG_WRAP);
            }
        }
    }

    /**
     * Collects the parameters of the given parameter list.
     *
     * @param parameters the {@code PARAMETERS} node of a method declaration
     * @return every parameter, in declaration order
     */
    private static List<DetailAST> getParameters(DetailAST parameters) {
        final List<DetailAST> parameterList = new ArrayList<>();
        TokenUtil.forEachChild(parameters, TokenTypes.PARAMETER_DEF, parameterList::add);
        return parameterList;
    }

    /**
     * Collects the parameters that start a new line, that is, every parameter whose first token
     * is the first parameter token on its line. The comma that separates a parameter from the
     * parameter before it always sits on the line the previous parameter ends on, so a parameter
     * starts a new line exactly when it does not share a line with that comma.
     *
     * @param parameters every parameter of a method declaration
     * @return the first token of every parameter that starts a new line
     */
    private static List<DetailAST> getWrappedStarts(Iterable<DetailAST> parameters) {
        final List<DetailAST> wrappedStarts = new ArrayList<>();
        for (DetailAST parameter : parameters) {
            final DetailAST comma = parameter.getPreviousSibling();
            final DetailAST start = CheckUtil.getFirstNode(parameter);
            if (comma != null && comma.getLineNo() != start.getLineNo()) {
                wrappedStarts.add(start);
            }
        }
        return wrappedStarts;
    }

    /**
     * Checks whether every parameter that starts a new line begins in the given column. A
     * declaration whose parameters all sit on one line has no parameter that starts a new line,
     * so it is aligned on every column and is never reported.
     *
     * @param wrappedStarts the parameters that start a new line
     * @param column the column the parameters are expected to begin in
     * @return {@code true} if every parameter that starts a new line begins in that column
     */
    private boolean isAlignedOn(Collection<DetailAST> wrappedStarts, int column) {
        return wrappedStarts.stream()
                .allMatch(parameter -> expandedColumnNo(parameter) == column);
    }

    /**
     * Returns the column the given token starts in, counting the tabs that precede it on its
     * line as the configured tab width. Alignment is what the reader sees, so a declaration
     * indented with tabs has to be measured the same way an editor renders it.
     *
     * @param ast the token to locate
     * @return the column of the token, with preceding tabs expanded
     */
    private int expandedColumnNo(DetailAST ast) {
        return CommonUtil.lengthExpandedTabs(
                getLine(ast.getLineNo() - 1), ast.getColumnNo(), getTabWidth());
    }

    /**
     * Checks whether any parameter is declared on the same line as the parameter before it. Every
     * parameter but the first one either starts a new line or shares a line with the parameter
     * before it.
     *
     * @param parameters every parameter of a method declaration
     * @param wrappedStarts the parameters that start a new line
     * @return {@code true} if a line holds more than one parameter
     */
    private static boolean hasMultipleParametersOnLine(Collection<DetailAST> parameters,
                                                       Collection<DetailAST> wrappedStarts) {
        return wrappedStarts.size() < parameters.size() - 1;
    }

}
