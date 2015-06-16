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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Make sure that utility classes (classes that contain only static methods)
 * do not have a public constructor.
 * <p>
 * Rationale: Instantiating utility classes does not make sense.
 * A common mistake is forgetting to hide the default constructor.
 * </p>
 *
 * @author lkuehne
 */
public class HideUtilityClassConstructorCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "hide.utility.class";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.CLASS_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.CLASS_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isAbstract(ast)) {
            // abstract class could not have private constructor
            return;
        }
        final boolean hasStaticModifier = isStatic(ast);

        final Details details = new Details(ast);
        details.invoke();

        final boolean hasDefaultCtor = details.isHasDefaultCtor();
        final boolean hasPublicCtor = details.isHasPublicCtor();
        final boolean hasMethodOrField = details.isHasMethodOrField();
        final boolean hasNonStaticMethodOrField = details.isHasNonStaticMethodOrField();
        final boolean hasNonPrivateStaticMethodOrField =
                details.isHasNonPrivateStaticMethodOrField();

        final boolean hasAccessibleCtor = hasDefaultCtor || hasPublicCtor;

        // figure out if class extends java.lang.object directly
        // keep it simple for now and get a 99% solution
        final boolean extendsJLO = // J.Lo even made it into in our sources :-)
            ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE) == null;

        final boolean isUtilClass = extendsJLO && hasMethodOrField
            && !hasNonStaticMethodOrField && hasNonPrivateStaticMethodOrField;

        if (isUtilClass && hasAccessibleCtor && !hasStaticModifier) {
            log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY);
        }
    }

    /**
     * @param ast class definition for check.
     * @return true if a given class declared as abstract.
     */
    private boolean isAbstract(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.MODIFIERS)
            .branchContains(TokenTypes.ABSTRACT);
    }

    /**
     * @param ast class definition for check.
     * @return true if a given class declared as static.
     */
    private boolean isStatic(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.MODIFIERS)
            .branchContains(TokenTypes.LITERAL_STATIC);
    }

    /**
     * Details of class that are required for validation
     */
    private static class Details {
        /** class ast */
        private DetailAST ast;
        /** result of details gathering */
        private boolean hasMethodOrField;
        /** result of details gathering */
        private boolean hasNonStaticMethodOrField;
        /** result of details gathering */
        private boolean hasNonPrivateStaticMethodOrField;
        /** result of details gathering */
        private boolean hasDefaultCtor;
        /** result of details gathering */
        private boolean hasPublicCtor;

        /** c-tor
         * @param ast class ast
         * */
        public Details(DetailAST ast) {
            this.ast = ast;
        }

        /**
         * getter
         * @return boolean
         */
        public boolean isHasMethodOrField() {
            return hasMethodOrField;
        }

        /**
         * getter
         * @return boolean
         */
        public boolean isHasNonStaticMethodOrField() {
            return hasNonStaticMethodOrField;
        }

        /**
         * getter
         * @return boolean
         */
        public boolean isHasNonPrivateStaticMethodOrField() {
            return hasNonPrivateStaticMethodOrField;
        }

        /**
         * getter
         * @return boolean
         */
        public boolean isHasDefaultCtor() {
            return hasDefaultCtor;
        }

        /**
         * getter
         * @return boolean
         */
        public boolean isHasPublicCtor() {
            return hasPublicCtor;
        }

        /**
         *  main method to gather statistics
         */
        public void invoke() {
            final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
            DetailAST child = objBlock.getFirstChild();
            hasMethodOrField = false;
            hasNonStaticMethodOrField = false;
            hasNonPrivateStaticMethodOrField = false;
            hasDefaultCtor = true;
            hasPublicCtor = false;

            while (child != null) {
                final int type = child.getType();
                if (type == TokenTypes.METHOD_DEF
                        || type == TokenTypes.VARIABLE_DEF) {
                    hasMethodOrField = true;
                    final DetailAST modifiers =
                        child.findFirstToken(TokenTypes.MODIFIERS);
                    final boolean isStatic =
                        modifiers.branchContains(TokenTypes.LITERAL_STATIC);
                    final boolean isPrivate =
                        modifiers.branchContains(TokenTypes.LITERAL_PRIVATE);

                    if (!isStatic) {
                        hasNonStaticMethodOrField = true;
                    }
                    if (isStatic && !isPrivate) {
                        hasNonPrivateStaticMethodOrField = true;
                    }
                }
                if (type == TokenTypes.CTOR_DEF) {
                    hasDefaultCtor = false;
                    final DetailAST modifiers =
                        child.findFirstToken(TokenTypes.MODIFIERS);
                    if (!modifiers.branchContains(TokenTypes.LITERAL_PRIVATE)
                        && !modifiers.branchContains(TokenTypes.LITERAL_PROTECTED)) {
                        // treat package visible as public
                        // for the purpose of this Check
                        hasPublicCtor = true;
                    }

                }
                child = child.getNextSibling();
            }
        }
    }
}
