////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Make sure that utility classes (classes that contain only static methods)
 * do not have a public constructor.
 * <p>
 * Rationale: Instantiating utility classes does not make sense.
 * A common mistake is forgetting to hide the default constructor.
 * </p>
 *
 */
@StatelessCheck
public class HideUtilityClassConstructorCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "hide.utility.class";

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
        return new int[] {TokenTypes.CLASS_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        // abstract class could not have private constructor
        if (!isAbstract(ast)) {
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
            final boolean extendsJlo =
                ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE) == null;

            final boolean isUtilClass = extendsJlo && hasMethodOrField
                && !hasNonStaticMethodOrField && hasNonPrivateStaticMethodOrField;

            if (isUtilClass && hasAccessibleCtor && !hasStaticModifier) {
                log(ast, MSG_KEY);
            }
        }
    }

    /**
     * Returns true if given class is abstract or false.
     * @param ast class definition for check.
     * @return true if a given class declared as abstract.
     */
    private static boolean isAbstract(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.MODIFIERS)
            .findFirstToken(TokenTypes.ABSTRACT) != null;
    }

    /**
     * Returns true if given class is static or false.
     * @param ast class definition for check.
     * @return true if a given class declared as static.
     */
    private static boolean isStatic(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.MODIFIERS)
            .findFirstToken(TokenTypes.LITERAL_STATIC) != null;
    }

    /**
     * Details of class that are required for validation.
     */
    private static class Details {

        /** Class ast. */
        private final DetailAST ast;
        /** Result of details gathering. */
        private boolean hasMethodOrField;
        /** Result of details gathering. */
        private boolean hasNonStaticMethodOrField;
        /** Result of details gathering. */
        private boolean hasNonPrivateStaticMethodOrField;
        /** Result of details gathering. */
        private boolean hasDefaultCtor;
        /** Result of details gathering. */
        private boolean hasPublicCtor;

        /**
         * C-tor.
         * @param ast class ast
         * */
        Details(DetailAST ast) {
            this.ast = ast;
        }

        /**
         * Getter.
         * @return boolean
         */
        public boolean isHasMethodOrField() {
            return hasMethodOrField;
        }

        /**
         * Getter.
         * @return boolean
         */
        public boolean isHasNonStaticMethodOrField() {
            return hasNonStaticMethodOrField;
        }

        /**
         * Getter.
         * @return boolean
         */
        public boolean isHasNonPrivateStaticMethodOrField() {
            return hasNonPrivateStaticMethodOrField;
        }

        /**
         * Getter.
         * @return boolean
         */
        public boolean isHasDefaultCtor() {
            return hasDefaultCtor;
        }

        /**
         * Getter.
         * @return boolean
         */
        public boolean isHasPublicCtor() {
            return hasPublicCtor;
        }

        /**
         * Main method to gather statistics.
         */
        public void invoke() {
            final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
            hasMethodOrField = false;
            hasNonStaticMethodOrField = false;
            hasNonPrivateStaticMethodOrField = false;
            hasDefaultCtor = true;
            hasPublicCtor = false;
            DetailAST child = objBlock.getFirstChild();

            while (child != null) {
                final int type = child.getType();
                if (type == TokenTypes.METHOD_DEF
                        || type == TokenTypes.VARIABLE_DEF) {
                    hasMethodOrField = true;
                    final DetailAST modifiers =
                        child.findFirstToken(TokenTypes.MODIFIERS);
                    final boolean isStatic =
                        modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null;
                    final boolean isPrivate =
                        modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;

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
                    if (modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) == null
                        && modifiers.findFirstToken(TokenTypes.LITERAL_PROTECTED) == null) {
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
