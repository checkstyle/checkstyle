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

package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.Objects;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Restricts throws statements to a specified count.
 * Methods with "Override" or "java.lang.Override" annotation are skipped
 * from validation as current class cannot change signature of these methods.
 * </div>
 *
 * <p>
 * Rationale:
 * Exceptions form part of a method's interface. Declaring
 * a method to throw too many differently rooted
 * exceptions makes exception handling onerous and leads
 * to poor programming practices such as writing code like
 * {@code catch(Exception ex)}. 4 is the empirical value which is based
 * on reports that we had for the ThrowsCountCheck over big projects
 * such as OpenJDK. This check also forces developers to put exceptions
 * into a hierarchy such that in the simplest
 * case, only one type of exception need be checked for by
 * a caller but any subclasses can be caught
 * specifically if necessary. For more information on rules
 * for the exceptions and their issues, see Effective Java:
 * Programming Language Guide Second Edition
 * by Joshua Bloch pages 264-273.
 * </p>
 *
 * <p>
 * <b>ignorePrivateMethods</b> - allows to skip private methods as they do
 * not cause problems for other classes.
 * </p>
 * <ul>
 * <li>
 * Property {@code ignorePrivateMethods} - Allow private methods to be ignored.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code max} - Specify maximum allowed number of throws statements.
 * Type is {@code int}.
 * Default value is {@code 4}.
 * </li>
 * </ul>
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
 * {@code throws.count}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@StatelessCheck
public final class ThrowsCountCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "throws.count";

    /** Default value of max property. */
    private static final int DEFAULT_MAX = 4;

    /** Allow private methods to be ignored. */
    private boolean ignorePrivateMethods = true;

    /** Specify maximum allowed number of throws statements. */
    private int max;

    /** Creates new instance of the check. */
    public ThrowsCountCheck() {
        max = DEFAULT_MAX;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.LITERAL_THROWS,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    /**
     * Setter to allow private methods to be ignored.
     *
     * @param ignorePrivateMethods whether private methods must be ignored.
     * @since 6.7
     */
    public void setIgnorePrivateMethods(boolean ignorePrivateMethods) {
        this.ignorePrivateMethods = ignorePrivateMethods;
    }

    /**
     * Setter to specify maximum allowed number of throws statements.
     *
     * @param max maximum allowed throws statements.
     * @since 3.2
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
     *
     * @param ast throws for check.
     */
    private void visitLiteralThrows(DetailAST ast) {
        if ((!ignorePrivateMethods || !isInPrivateMethod(ast))
                && !isOverriding(ast)) {
            // Account for all the commas!
            final int count = (ast.getChildCount() + 1) / 2;
            if (count > max) {
                log(ast, MSG_KEY, count, max);
            }
        }
    }

    /**
     * Check if a method has annotation @Override.
     *
     * @param ast throws, which is being checked.
     * @return true, if a method has annotation @Override.
     */
    private static boolean isOverriding(DetailAST ast) {
        final DetailAST modifiers = ast.getParent().findFirstToken(TokenTypes.MODIFIERS);
        boolean isOverriding = false;
        DetailAST child = modifiers.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.ANNOTATION
                    && "Override".equals(getAnnotationName(child))) {
                isOverriding = true;
                break;
            }
            child = child.getNextSibling();
        }
        return isOverriding;
    }

    /**
     * Gets name of an annotation.
     *
     * @param annotation to get name of.
     * @return name of an annotation.
     */
    private static String getAnnotationName(DetailAST annotation) {
        final DetailAST dotAst = annotation.findFirstToken(TokenTypes.DOT);
        final DetailAST parent = Objects.requireNonNullElse(dotAst, annotation);
        return parent.findFirstToken(TokenTypes.IDENT).getText();
    }

    /**
     * Checks if method, which throws an exception is private.
     *
     * @param ast throws, which is being checked.
     * @return true, if method, which throws an exception is private.
     */
    private static boolean isInPrivateMethod(DetailAST ast) {
        final DetailAST methodModifiers = ast.getParent().findFirstToken(TokenTypes.MODIFIERS);
        return methodModifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;
    }

}
