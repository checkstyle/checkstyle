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
import java.util.SequencedCollection;
import java.util.stream.IntStream;

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
 * A declaration whose parameter list fits on a single line is ignored. A wrapped declaration
 * in which no parameter starts a new line, because it is wrapped inside a parameter type
 * rather than between two parameters, is expected to be listed vertically, so a line that
 * holds more than one parameter is reported.
 * </p>
 *
 * @since 14.4.0
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
            TokenTypes.CTOR_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final List<DetailAST> parameters =
                getParameters(ast.findFirstToken(TokenTypes.PARAMETERS));

        if (!parameters.isEmpty() && spansMoreThanOneLine(parameters)) {
            final List<Integer> lineStartColumns = getLineStartColumns(parameters);
            final boolean wrappedByEightSpaces = !lineStartColumns.isEmpty()
                    && isAlignedOn(lineStartColumns, expandedColumnNo(ast) + EIGHT_SPACES);

            if (!wrappedByEightSpaces) {
                final boolean listedVertically = isAlignedOn(lineStartColumns,
                        expandedColumnNo(CheckUtil.getFirstNode(parameters.getFirst())));
                final DetailAST name = ast.findFirstToken(TokenTypes.IDENT);

                if (listedVertically && hasMultipleParametersOnLine(parameters)) {
                    log(name, MSG_KEY);
                }
                else if (!listedVertically) {
                    log(name, MSG_WRAP);
                }
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
     * Checks whether the parameters of a declaration are spread over more than one line. A
     * declaration can be wrapped without any parameter starting a new line, when it is wrapped
     * inside a parameter type rather than between two parameters. Parameters are listed in
     * source order, so a parameter that starts a new line always leaves the last parameter on a
     * later line than the first one.
     *
     * @param parameters every parameter of a method declaration
     * @return {@code true} if the parameters do not all start on the same line
     */
    private static boolean spansMoreThanOneLine(SequencedCollection<DetailAST> parameters) {
        return parameters.getFirst().getLineNo() != parameters.getLast().getLineNo();
    }

    /**
     * Collects the column the line of every parameter that starts a new line begins in. The
     * first parameter of a declaration is the column the other parameters are compared
     * against, so it is never collected.
     *
     * @param parameters every parameter of a method declaration
     * @return the column every parameter that starts a new line is expected to be aligned in
     */
    private List<Integer> getLineStartColumns(List<DetailAST> parameters) {
        final List<Integer> columns = new ArrayList<>();
        for (int index = 1; index < parameters.size(); index++) {
            if (!sharesLineWithPreviousParameter(parameters, index)) {
                columns.add(expandedColumnNo(getLineStart(parameters.get(index))));
            }
        }
        return columns;
    }

    /**
     * Checks whether any parameter is declared on the line the parameter before it ends on. The
     * comma that separates two parameters is not taken into account, as a parameter list can be
     * wrapped before that comma instead of after it.
     *
     * @param parameters every parameter of a method declaration
     * @return {@code true} if a line holds more than one parameter
     */
    private static boolean hasMultipleParametersOnLine(List<DetailAST> parameters) {
        return IntStream.range(1, parameters.size())
                .anyMatch(index -> sharesLineWithPreviousParameter(parameters, index));
    }

    /**
     * Checks whether the parameter at the given position begins on the line the parameter
     * before it ends on. A parameter can end on a later line than it begins on, when it is
     * wrapped inside its own type or annotations.
     *
     * @param parameters every parameter of a method declaration
     * @param index the position of the parameter to locate, never the first one
     * @return {@code true} if the parameter shares a line with the parameter before it
     */
    private static boolean sharesLineWithPreviousParameter(List<DetailAST> parameters,
                                                           int index) {
        final DetailAST previousParameter = parameters.get(index - 1);
        final DetailAST parameterStart = CheckUtil.getFirstNode(parameters.get(index));
        return getLastLineNo(previousParameter) == parameterStart.getLineNo();
    }

    /**
     * Returns the number of the last line the given parameter is declared on.
     *
     * @param ast the parameter to measure
     * @return the greatest line number of the tokens of the parameter
     */
    private static int getLastLineNo(DetailAST ast) {
        int lastLineNo = ast.getLineNo();
        for (DetailAST child = ast.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            lastLineNo = Math.max(lastLineNo, getLastLineNo(child));
        }
        return lastLineNo;
    }

    /**
     * Returns the token the line of a parameter that starts a new line begins with. A parameter
     * list can be wrapped before the comma that separates two parameters instead of after it,
     * which leaves that comma as the first token of the line, so such a line begins at the
     * comma rather than at the parameter. The comma that separates a parameter from the
     * parameter before it is the sibling preceding it, so a parameter that does not start a new
     * line is never passed here.
     *
     * @param parameter the parameter that starts a new line
     * @return the comma preceding the parameter when both are on one line, the first token of
     *         the parameter otherwise
     */
    private static DetailAST getLineStart(DetailAST parameter) {
        final DetailAST firstNode = CheckUtil.getFirstNode(parameter);
        final DetailAST comma = parameter.getPreviousSibling();
        DetailAST result = firstNode;

        if (comma.getLineNo() == firstNode.getLineNo()) {
            result = comma;
        }
        return result;
    }

    /**
     * Checks whether every parameter that starts a new line begins in the given column. A
     * declaration whose parameters all start on one line has no parameter that starts a new
     * line, so it is aligned on every column and is never reported.
     *
     * @param lineStartColumns the column of every parameter that starts a new line
     * @param column the column the parameters are expected to begin in
     * @return {@code true} if every parameter that starts a new line begins in that column
     */
    private static boolean isAlignedOn(Collection<Integer> lineStartColumns, int column) {
        return lineStartColumns.stream()
                .allMatch(lineStartColumn -> lineStartColumn == column);
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

}
