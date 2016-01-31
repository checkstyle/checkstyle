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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Restricts throws statements to a specified count (default = 4).
 * Methods with "Override" or "java.lang.Override" annotation are skipped
 * from validation as current class cannot change signature of these methods.
 * </p>
 * <p>
 * Rationale:
 * Exceptions form part of a methods interface. Declaring
 * a method to throw too many differently rooted
 * exceptions makes exception handling onerous and leads
 * to poor programming practices such as catch
 * (Exception). 4 is the empirical value which is based
 * on reports that we had for the ThrowsCountCheck over big projects
 * such as OpenJDK. This check also forces developers to put exceptions
 * into a hierarchy such that in the simplest
 * case, only one type of exception need be checked for by
 * a caller but allows any sub-classes to be caught
 * specifically if necessary. For more information on rules
 * for the exceptions and their issues, see Effective Java:
 * Programming Language Guide Second Edition
 * by Joshua Bloch pages 264-273.
 * </p>
 * <p>
 * <b>ignorePrivateMethods</b> - allows to skip private methods as they do
 * not cause problems for other classes.
 * </p>
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public final class ThrowsCountCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "throws.count";

    /** Default value of max property. */
    private static final int DEFAULT_MAX = 4;

    /** Whether private methods must be ignored. **/
    private boolean ignorePrivateMethods = true;

    /** Maximum allowed throws statements. */
    private int max;

    /** Creates new instance of the check. */
    public ThrowsCountCheck() {
        max = DEFAULT_MAX;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.LITERAL_THROWS,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.LITERAL_THROWS,
        };
    }

    /**
     * Sets whether private methods must be ignored.
     * @param ignorePrivateMethods whether private methods must be ignored.
     */
    public void setIgnorePrivateMethods(boolean ignorePrivateMethods) {
        this.ignorePrivateMethods = ignorePrivateMethods;
    }

    /**
     * Setter for max property.
     * @param max maximum allowed throws statements.
     */
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.LITERAL_THROWS) {
            visitLiteralThrows(ast);
        }
        else {
            throw new IllegalStateException(ast.toString());
        }
    }

    /**
     * Checks number of throws statements.
     * @param ast throws for check.
     */
    private void visitLiteralThrows(DetailAST ast) {
        if ((!ignorePrivateMethods || !isInPrivateMethod(ast))
                && !isOverriding(ast)) {
            // Account for all the commas!
            final int count = (ast.getChildCount() + 1) / 2;
            if (count > max) {
                log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY,
                    count, max);
            }
        }
    }

    /**
     * Check if a method has annotation @Override.
     * @param ast throws, which is being checked.
     * @return true, if a method has annotation @Override.
     */
    private static boolean isOverriding(DetailAST ast) {
        final DetailAST modifiers = ast.getParent().findFirstToken(TokenTypes.MODIFIERS);
        boolean isOverriding = false;
        if (modifiers.branchContains(TokenTypes.ANNOTATION)) {
            DetailAST child = modifiers.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.ANNOTATION
                        && "Override".equals(getAnnotationName(child))) {
                    isOverriding = true;
                }
                child = child.getNextSibling();
            }
        }
        return isOverriding;
    }

    /**
     * Gets name of an annotation.
     * @param annotation to get name of.
     * @return name of an annotation.
     */
    private static String getAnnotationName(DetailAST annotation) {
        final DetailAST dotAst = annotation.findFirstToken(TokenTypes.DOT);
        final String name;
        if (dotAst == null) {
            name = annotation.findFirstToken(TokenTypes.IDENT).getText();
        }
        else {
            name = dotAst.findFirstToken(TokenTypes.IDENT).getText();
        }
        return name;
    }

    /**
     * Checks if method, which throws an exception is private.
     * @param ast throws, which is being checked.
     * @return true, if method, which throws an exception is private.
     */
    private static boolean isInPrivateMethod(DetailAST ast) {
        final DetailAST methodModifiers = ast.getParent().findFirstToken(TokenTypes.MODIFIERS);
        return methodModifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;
    }
}
