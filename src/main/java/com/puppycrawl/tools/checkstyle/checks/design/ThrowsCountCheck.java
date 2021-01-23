////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
 * <p>
 * Restricts throws statements to a specified count.
 * Methods with "Override" or "java.lang.Override" annotation are skipped
 * from validation as current class cannot change signature of these methods.
 * </p>
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
 * <p>
 * <b>ignorePrivateMethods</b> - allows to skip private methods as they do
 * not cause problems for other classes.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify maximum allowed number of throws statements.
 * Type is {@code int}.
 * Default value is {@code 4}.
 * </li>
 * <li>
 * Property {@code ignorePrivateMethods} - Allow private methods to be ignored.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure check:
 * </p>
 * <pre>
 * &lt;module name="ThrowsCount"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *     public void myFunction() throws CloneNotSupportedException,
 *                             ArrayIndexOutOfBoundsException,
 *                             StringIndexOutOfBoundsException,
 *                             IllegalStateException,
 *                             NullPointerException { // violation, max allowed is 4
 *         // body
 *     }
 *
 *     public void myFunc() throws ArithmeticException,
 *             NumberFormatException { // ok
 *         // body
 *     }
 *
 *     private void privateFunc() throws CloneNotSupportedException,
 *                             ClassNotFoundException,
 *                             IllegalAccessException,
 *                             ArithmeticException,
 *                             ClassCastException { // ok, private methods are ignored
 *         // body
 *     }
 *
 * }
 * </pre>
 * <p>
 * To configure the check so that it doesn't allow more than two throws per method:
 * </p>
 * <pre>
 * &lt;module name="ThrowsCount"&gt;
 *   &lt;property name=&quot;max&quot; value=&quot;2&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *     public void myFunction() throws IllegalStateException,
 *                                 ArrayIndexOutOfBoundsException,
 *                                 NullPointerException { // violation, max allowed is 2
 *         // body
 *     }
 *
 *     public void myFunc() throws ArithmeticException,
 *                                 NumberFormatException { // ok
 *         // body
 *     }
 *
 *     private void privateFunc() throws CloneNotSupportedException,
 *                                 ClassNotFoundException,
 *                                 IllegalAccessException,
 *                                 ArithmeticException,
 *                                 ClassCastException { // ok, private methods are ignored
 *         // body
 *     }
 *
 * }
 * </pre>
 * <p>
 * To configure the check so that it doesn't skip private methods:
 * </p>
 * <pre>
 * &lt;module name="ThrowsCount"&gt;
 *   &lt;property name=&quot;ignorePrivateMethods&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *     public void myFunction() throws CloneNotSupportedException,
 *                                 ArrayIndexOutOfBoundsException,
 *                                 StringIndexOutOfBoundsException,
 *                                 IllegalStateException,
 *                                 NullPointerException { // violation, max allowed is 4
 *         // body
 *     }
 *
 *     public void myFunc() throws ArithmeticException,
 *                                 NumberFormatException { // ok
 *         // body
 *     }
 *
 *     private void privateFunc() throws CloneNotSupportedException,
 *                                 ClassNotFoundException,
 *                                 IllegalAccessException,
 *                                 ArithmeticException,
 *                                 ClassCastException { // violation, max allowed is 4
 *         // body
 *     }
 *
 *     private void func() throws IllegalStateException,
 *                                 NullPointerException { // ok
 *         // body
 *     }
 *
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
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
     */
    public void setIgnorePrivateMethods(boolean ignorePrivateMethods) {
        this.ignorePrivateMethods = ignorePrivateMethods;
    }

    /**
     * Setter to specify maximum allowed number of throws statements.
     *
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
     *
     * @param ast throws, which is being checked.
     * @return true, if method, which throws an exception is private.
     */
    private static boolean isInPrivateMethod(DetailAST ast) {
        final DetailAST methodModifiers = ast.getParent().findFirstToken(TokenTypes.MODIFIERS);
        return methodModifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;
    }

}
