////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks that classes are designed for inheritance.
 *
 * <p>
 * More specifically, it enforces a programming style
 * where superclasses provide empty "hooks" that can be
 * implemented by subclasses.
 * </p>
 *
 * <p>
 * The exact rule is that nonprivate, nonstatic methods in
 * nonfinal classes (or classes that do not
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
public class DesignForExtensionCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "design.forExtension";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        // nothing to do for Interfaces
        if (ScopeUtils.inInterfaceOrAnnotationBlock(ast)) {
            return;
        }
        if (isPrivateOrFinalOrAbstract(ast)) {
            return;
        }

        // method is ok if containing class is not visible in API and
        // cannot be extended by 3rd parties (bug #884035)
        if (!ScopeUtils.getSurroundingScope(ast).isIn(Scope.PROTECTED)) {
            return;
        }

        // method is ok if it is implementation can verified to be empty
        // Note: native methods don't have impl in java code, so
        // implementation can be null even if method not abstract
        final DetailAST implementation = ast.findFirstToken(TokenTypes.SLIST);
        if (implementation != null
            && implementation.getFirstChild().getType() == TokenTypes.RCURLY) {
            return;
        }

        // check if the containing class can be subclassed
        final DetailAST classDef = findContainingClass(ast);
        final DetailAST classMods =
            classDef.findFirstToken(TokenTypes.MODIFIERS);
        if (classDef.getType() == TokenTypes.ENUM_DEF
            || classMods.branchContains(TokenTypes.FINAL)) {
            return;
        }

        if (hasDefaultOrExplNonPrivateCtor(classDef)) {
            final String name = ast.findFirstToken(TokenTypes.IDENT).getText();
            log(ast.getLineNo(), ast.getColumnNo(),
                MSG_KEY, name);
        }
    }

    /**
     * check for modifiers
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
     * has Default Or Expl Non Private Ctor
     * @param classDef class ast
     * @return true if Check should make a violation
     */
    private static boolean hasDefaultOrExplNonPrivateCtor(DetailAST classDef) {
        // check if subclassing is prevented by having only private ctors
        final DetailAST objBlock = classDef.findFirstToken(TokenTypes.OBJBLOCK);

        boolean hasDefaultConstructor = true;
        boolean hasExplNonPrivateCtor = false;

        DetailAST candidate = objBlock.getFirstChild();

        while (candidate != null) {
            if (candidate.getType() == TokenTypes.CTOR_DEF) {
                hasDefaultConstructor = false;

                final DetailAST ctorMods =
                        candidate.findFirstToken(TokenTypes.MODIFIERS);
                if (!ctorMods.branchContains(TokenTypes.LITERAL_PRIVATE)) {
                    hasExplNonPrivateCtor = true;
                    break;
                }
            }
            candidate = candidate.getNextSibling();
        }

        return hasDefaultConstructor || hasExplNonPrivateCtor;
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
