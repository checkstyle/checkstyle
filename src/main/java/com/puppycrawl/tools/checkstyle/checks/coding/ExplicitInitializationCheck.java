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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

/**
 * <p>
 * Checks if any class or object member explicitly initialized
 * to default for its type value (<code>null</code> for object
 * references, zero for numeric types and <code>char</code>
 * and <code>false</code> for <code>boolean</code>.
 * </p>
 * <p>
 * Rationale: each instance variable gets
 * initialized twice, to the same value.  Java
 * initializes each instance variable to its default
 * value (0 or null) before performing any
 * initialization specified in the code.  So in this case,
 * x gets initialized to 0 twice, and bar gets initialized
 * to null twice.  So there is a minor inefficiency.  This style of
 * coding is a hold-over from C/C++ style coding,
 * and it shows that the developer isn't really confident that
 * Java really initializes instance variables to default
 * values.
 * </p>
 *
 * @author o_sukhodolsky
 */
public class ExplicitInitializationCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "explicit.init";

    @Override
    public final int[] getDefaultTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    public final int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public final int[] getAcceptableTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isSkipCase(ast)) {
            return;
        }

        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        final DetailAST assign = ast.findFirstToken(TokenTypes.ASSIGN);
        final DetailAST exprStart =
            assign.getFirstChild().getFirstChild();
        final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
        if (isObjectType(type)
            && exprStart.getType() == TokenTypes.LITERAL_NULL) {
            log(ident, MSG_KEY, ident.getText(), "null");
        }

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
     * examin Char literal for initializing to default value
     * @param exprStart exprssion
     * @return true is literal is initialized by zero symbol
     */
    private boolean isZeroChar(DetailAST exprStart) {
        return isZero(exprStart)
            || exprStart.getType() == TokenTypes.CHAR_LITERAL
            && "'\\0'".equals(exprStart.getText());
    }

    /**
     * chekc for cases that should be skipped: no assignment, local variable, final variables
     * @param ast Variable def AST
     * @return true is that is a case that need to be skipped.
     */
    private boolean isSkipCase(DetailAST ast) {
        // do not check local variables and
        // fields declared in interface/annotations
        if (ScopeUtils.isLocalVariableDef(ast)
            || ScopeUtils.inInterfaceOrAnnotationBlock(ast)) {
            return true;
        }

        final DetailAST assign = ast.findFirstToken(TokenTypes.ASSIGN);
        if (assign == null) {
            // no assign - no check
            return true;
        }

        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        return modifiers != null && modifiers.branchContains(TokenTypes.FINAL);
    }

    /**
     * Determines if a giiven type is an object type.
     * @param type type to check.
     * @return true if it is an object type.
     */
    private boolean isObjectType(DetailAST type) {
        final int objectType = type.getFirstChild().getType();
        return objectType == TokenTypes.IDENT || objectType == TokenTypes.DOT
                || objectType == TokenTypes.ARRAY_DECLARATOR;
    }

    /**
     * Determine if a given type is a numeric type.
     * @param type code of the type for check.
     * @return true if it's a numeric type.
     * @see TokenTypes
     */
    private boolean isNumericType(int type) {
        return type == TokenTypes.LITERAL_BYTE
                || type == TokenTypes.LITERAL_SHORT
                || type == TokenTypes.LITERAL_INT
                || type == TokenTypes.LITERAL_FLOAT
                || type == TokenTypes.LITERAL_LONG
                || type == TokenTypes.LITERAL_DOUBLE;
    }

    /**
     * @param expr node to check.
     * @return true if given node contains numeric constant for zero.
     */
    private boolean isZero(DetailAST expr) {
        final int type = expr.getType();
        switch (type) {
            case TokenTypes.NUM_FLOAT:
            case TokenTypes.NUM_DOUBLE:
            case TokenTypes.NUM_INT:
            case TokenTypes.NUM_LONG:
                final String text = expr.getText();
                return 0 == CheckUtils.parseFloat(text, type);
            default:
                return false;
        }
    }
}
