////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * Checks find classes that are designed for inheritance.
 *
 * <p>
 * Nothing wrong could be with founded classes
 * this Check make sence only for library project (not a application projects)
 * who care about ideal OOP design to make sure clas work in all cases even misusage.
 * Even in library projects this Check most likely find classes that are not required to check.
 * User need to use suppressions extensively to got a benefit from this Check and avoid
 * false positives.
 * </p>
 *
 * <p>
 * ATTENTION: Only user can deside whether class is designed for extension or not.
 * Check just show all possible. If smth inappropriate is found please use supporession.
 * </p>
 *
 * <p>
 * More specifically, it enforces a programming style
 * where superclasses provide empty "hooks" that can be
 * implemented by subclasses.
 * </p>
 *
 * <p>The exact rule is that non-private, non-static methods in
 * non-final classes (or classes that do not
 * only have private constructors) must either be
 * <ul>
 * <li>abstract or</li>
 * <li>final or</li>
 * <li>have an empty implementation</li>
 * </ul>
 *
 *
 * <p>
 * This protects superclasses against being broken by
 * subclasses. The downside is that subclasses are limited
 * in their flexibility, in particular they cannot prevent
 * execution of code in the superclass, but that also
 * means that subclasses can't forget to call their super
 * method.
 * </p>
 *
 * @author lkuehne
 */
public class DesignForExtensionCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "design.forExtension";

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        // nothing to do for Interfaces
        if (!ScopeUtils.isInInterfaceOrAnnotationBlock(ast)
                && !isPrivateOrFinalOrAbstract(ast)
                && ScopeUtils.getSurroundingScope(ast).isIn(Scope.PROTECTED)) {

            // method is ok if it is implementation can verified to be empty
            // Note: native methods don't have impl in java code, so
            // implementation can be null even if method not abstract
            final DetailAST implementation = ast.findFirstToken(TokenTypes.SLIST);
            final boolean nonEmptyImplementation = implementation == null
                    || implementation.getFirstChild().getType() != TokenTypes.RCURLY;

            final DetailAST classDef = findContainingClass(ast);
            final DetailAST classMods = classDef.findFirstToken(TokenTypes.MODIFIERS);
            // check if the containing class can be subclassed
            final boolean classCanBeSubclassed = classDef.getType() != TokenTypes.ENUM_DEF
                    && !classMods.branchContains(TokenTypes.FINAL);

            if (nonEmptyImplementation && classCanBeSubclassed
                    && hasDefaultOrExplicitNonPrivateCtor(classDef)) {

                final String name = ast.findFirstToken(TokenTypes.IDENT).getText();
                log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY, name);
            }
        }
    }

    /**
     * Check for modifiers.
     * @param ast modifier ast
     * @return tru in modifier is in checked ones
     */
    private static boolean isPrivateOrFinalOrAbstract(DetailAST ast) {
        // method is ok if it is private or abstract or final
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        return modifiers.branchContains(TokenTypes.LITERAL_PRIVATE)
                || modifiers.branchContains(TokenTypes.ABSTRACT)
                || modifiers.branchContains(TokenTypes.FINAL)
                || modifiers.branchContains(TokenTypes.LITERAL_STATIC);
    }

    /**
     * Has Default Or Explicit Non Private Ctor.
     * @param classDef class ast
     * @return true if Check should make a violation
     */
    private static boolean hasDefaultOrExplicitNonPrivateCtor(DetailAST classDef) {
        // check if subclassing is prevented by having only private ctors
        final DetailAST objBlock = classDef.findFirstToken(TokenTypes.OBJBLOCK);

        boolean hasDefaultConstructor = true;
        boolean hasExplicitNonPrivateCtor = false;

        DetailAST candidate = objBlock.getFirstChild();

        while (candidate != null) {
            if (candidate.getType() == TokenTypes.CTOR_DEF) {
                hasDefaultConstructor = false;

                final DetailAST ctorMods =
                        candidate.findFirstToken(TokenTypes.MODIFIERS);
                if (!ctorMods.branchContains(TokenTypes.LITERAL_PRIVATE)) {
                    hasExplicitNonPrivateCtor = true;
                    break;
                }
            }
            candidate = candidate.getNextSibling();
        }

        return hasDefaultConstructor || hasExplicitNonPrivateCtor;
    }

    /**
     * Searches the tree towards the root until it finds a CLASS_DEF node.
     * @param ast the start node for searching
     * @return the CLASS_DEF node.
     */
    private static DetailAST findContainingClass(DetailAST ast) {
        DetailAST searchAST = ast;
        while (searchAST.getType() != TokenTypes.CLASS_DEF
               && searchAST.getType() != TokenTypes.ENUM_DEF) {
            searchAST = searchAST.getParent();
        }
        return searchAST;
    }
}
