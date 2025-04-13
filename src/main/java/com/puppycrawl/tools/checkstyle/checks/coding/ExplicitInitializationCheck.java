////
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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks if any class or object member is explicitly initialized
 * to default for its type value ({@code null} for object
 * references, zero for numeric types and {@code char}
 * and {@code false} for {@code boolean}.
 * </div>
 *
 * <p>
 * Rationale: Each instance variable gets
 * initialized twice, to the same value. Java
 * initializes each instance variable to its default
 * value ({@code 0} or {@code null}) before performing any
 * initialization specified in the code.
 * So there is a minor inefficiency.
 * </p>
 * <ul>
 * <li>
 * Property {@code onlyObjectReferences} - Control whether only explicit
 * initializations made to null for objects should be checked.
 * Type is {@code boolean}.
 * Default value is {@code false}.
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
 * {@code explicit.init}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@StatelessCheck
public class ExplicitInitializationCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "explicit.init";

    /**
     * Control whether only explicit initializations made to null for objects should be checked.
     **/
    private boolean onlyObjectReferences;

    @Override
    public final int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    public final int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    /**
     * Setter to control whether only explicit initializations made to null
     * for objects should be checked.
     *
     * @param onlyObjectReferences whether only explicit initialization made to null
     *                             should be checked
     * @since 7.8
     */
    public void setOnlyObjectReferences(boolean onlyObjectReferences) {
        this.onlyObjectReferences = onlyObjectReferences;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!isSkipCase(ast)) {
            final DetailAST assign = ast.findFirstToken(TokenTypes.ASSIGN);
            final DetailAST exprStart =
                assign.getFirstChild().getFirstChild();
            if (exprStart.getType() == TokenTypes.LITERAL_NULL) {
                final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
                log(ident, MSG_KEY, ident.getText(), "null");
            }
            if (!onlyObjectReferences) {
                validateNonObjects(ast);
            }
        }
    }

    /**
     * Checks for explicit initializations made to 'false', '0' and '\0'.
     *
     * @param ast token being checked for explicit initializations
     */
    private void validateNonObjects(DetailAST ast) {
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        final DetailAST assign = ast.findFirstToken(TokenTypes.ASSIGN);
        final DetailAST exprStart =
            assign.getFirstChild().getFirstChild();
        final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
        final int primitiveType = type.getFirstChild().getType();
        if (primitiveType == TokenTypes.LITERAL_BOOLEAN
            && exprStart.getType() == TokenTypes.LITERAL_FALSE) {
            log(ident, MSG_KEY, ident.getText(), "false");
        }
        if (isNumericType(primitiveType) && isZero(exprStart)) {
            log(ident, MSG_KEY, ident.getText(), "0");
        }
        if (primitiveType == TokenTypes.LITERAL_CHAR
            && isZeroChar(exprStart)) {
            log(ident, MSG_KEY, ident.getText(), "\\0");
        }
    }

    /**
     * Examine char literal for initializing to default value.
     *
     * @param exprStart expression
     * @return true is literal is initialized by zero symbol
     */
    private static boolean isZeroChar(DetailAST exprStart) {
        return isZero(exprStart)
            || "'\\0'".equals(exprStart.getText());
    }

    /**
     * Checks for cases that should be skipped: no assignment, local variable, final variables.
     *
     * @param ast Variable def AST
     * @return true is that is a case that need to be skipped.
     */
    private static boolean isSkipCase(DetailAST ast) {
        boolean skipCase = true;

        // do not check local variables and
        // fields declared in interface/annotations
        if (!ScopeUtil.isLocalVariableDef(ast)
            && !ScopeUtil.isInInterfaceOrAnnotationBlock(ast)) {
            final DetailAST assign = ast.findFirstToken(TokenTypes.ASSIGN);

            if (assign != null) {
                final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
                skipCase = modifiers.findFirstToken(TokenTypes.FINAL) != null;
            }
        }
        return skipCase;
    }

    /**
     * Determine if a given type is a numeric type.
     *
     * @param type code of the type for check.
     * @return true if it's a numeric type.
     * @see TokenTypes
     */
    private static boolean isNumericType(int type) {
        return type == TokenTypes.LITERAL_BYTE
            || type == TokenTypes.LITERAL_SHORT
            || type == TokenTypes.LITERAL_INT
            || type == TokenTypes.LITERAL_FLOAT
            || type == TokenTypes.LITERAL_LONG
            || type == TokenTypes.LITERAL_DOUBLE;
    }

    /**
     * Checks if given node contains numeric constant for zero.
     *
     * @param expr node to check.
     * @return true if given node contains numeric constant for zero.
     */
    private static boolean isZero(DetailAST expr) {
        final int type = expr.getType();
        final boolean isZero;
        switch (type) {
            case TokenTypes.NUM_FLOAT:
            case TokenTypes.NUM_DOUBLE:
            case TokenTypes.NUM_INT:
            case TokenTypes.NUM_LONG:
                final String text = expr.getText();
                isZero = Double.compare(CheckUtil.parseDouble(text, type), 0.0) == 0;
                break;
            default:
                isZero = false;
        }
        return isZero;
    }

}
