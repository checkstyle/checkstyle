///
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that all constructors are grouped together.
 * If there is any non-constructor code separating constructors,
 * this check identifies and logs a violation for those ungrouped constructors.
 * The violation message will specify the line number of the last grouped constructor.
 * Comments between constructors are allowed.
 * </div>
 *
 * <p>
 * Rationale: Grouping constructors together in a class improves code readability
 * and maintainability. It allows developers to easily understand
 * the different ways an object can be instantiated
 * and the tasks performed by each constructor.
 * </p>
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
 * {@code constructors.declaration.grouping}
 * </li>
 * </ul>
 *
 * @since 10.17.0
 */

@StatelessCheck
public class ConstructorsDeclarationGroupingCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "constructors.declaration.grouping";

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
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        // list of all child ASTs
        final List<DetailAST> children = getChildList(ast);

        // find first constructor
        final DetailAST firstConstructor = children.stream()
                .filter(ConstructorsDeclarationGroupingCheck::isConstructor)
                .findFirst()
                .orElse(null);

        if (firstConstructor != null) {

            // get all children AST after the first constructor
            final List<DetailAST> childrenAfterFirstConstructor =
                    children.subList(children.indexOf(firstConstructor), children.size());

            // find the first index of non-constructor AST after the first constructor, if present
            final Optional<Integer> indexOfFirstNonConstructor = childrenAfterFirstConstructor
                    .stream()
                    .filter(currAst -> !isConstructor(currAst))
                    .findFirst()
                    .map(children::indexOf);

            // list of all children after first non-constructor AST
            final List<DetailAST> childrenAfterFirstNonConstructor = indexOfFirstNonConstructor
                    .map(index -> children.subList(index, children.size()))
                    .orElseGet(ArrayList::new);

            // create a list of all constructors that are not grouped to log
            final List<DetailAST> constructorsToLog = childrenAfterFirstNonConstructor.stream()
                    .filter(ConstructorsDeclarationGroupingCheck::isConstructor)
                    .collect(Collectors.toUnmodifiableList());

            // find the last grouped constructor
            final DetailAST lastGroupedConstructor = childrenAfterFirstConstructor.stream()
                    .takeWhile(ConstructorsDeclarationGroupingCheck::isConstructor)
                    .reduce((first, second) -> second)
                    .orElse(firstConstructor);

            // log all constructors that are not grouped
            constructorsToLog
                    .forEach(ctor -> log(ctor, MSG_KEY, lastGroupedConstructor.getLineNo()));
        }
    }

    /**
     * Get a list of all children of the given AST.
     *
     * @param ast the AST to get children of
     * @return a list of all children of the given AST
     */
    private static List<DetailAST> getChildList(DetailAST ast) {
        final List<DetailAST> children = new ArrayList<>();
        DetailAST child = ast.findFirstToken(TokenTypes.OBJBLOCK).getFirstChild();
        while (child != null) {
            children.add(child);
            child = child.getNextSibling();
        }
        return children;
    }

    /**
     * Check if the given AST is a constructor.
     *
     * @param ast the AST to check
     * @return true if the given AST is a constructor, false otherwise
     */
    private static boolean isConstructor(DetailAST ast) {
        return ast.getType() == TokenTypes.CTOR_DEF
                || ast.getType() == TokenTypes.COMPACT_CTOR_DEF;
    }
}
