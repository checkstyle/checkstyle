///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that throw statements within catch blocks do not use
 * the caught exception's getMessage() call when the thrown exception
 * is the same type as the caught exception, as rethrowing the same
 * exception type with its own message is redundant.
 * </div>
 *
 * <p>
 * Rationale: When throwing an exception of the same type as the caught
 * exception and including the caught exception's message via getMessage(),
 * the information is redundant. The original exception should be rethrown
 * directly, or a different exception type should be used.
 * </p>
 *
 * <p>
 * Example of violations:
 * </p>
 * <div class="wrapper"><pre>
 * catch (IOException ex) {
 *     throw new IOException("Error: " + ex.getMessage()); // violation
 * }
 * </pre></div>
 *
 * <p>
 * Correct usage:
 * </p>
 * <div class="wrapper"><pre>
 * catch (IOException ex) {
 *     throw new RuntimeException("Error: " + ex.getMessage(), ex); // OK, different type
 * }
 * catch (IOException ex) {
 *     throw new IOException("Error processing file", ex); // OK, no getMessage()
 * }
 * </pre></div>
 *
 * @since 14.4.0
 */
@StatelessCheck
public class NoGetMessageInThrowCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "no.getmessage.in.throw";

    /**
     * Creates a new {@code NoGetMessageInThrowCheck} instance.
     */
    public NoGetMessageInThrowCheck() {
        // no code by default
    }

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
        return new int[] {TokenTypes.LITERAL_THROW};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String thrownType = getThrownType(ast);
        if (thrownType != null) {
            final Set<String> matchingCatchVars = collectMatchingCatchVars(ast, thrownType);
            if (!matchingCatchVars.isEmpty()
                    && hasGetMessageCallOnVar(ast, matchingCatchVars)) {
                log(ast, MSG_KEY);
            }
        }
    }

    /**
     * Gets the simple type name of the exception being thrown via a new expression.
     *
     * @param throwAst the LITERAL_THROW AST node
     * @return the simple type name, or null if the throw does not use a new expression
     */
    private static String getThrownType(DetailAST throwAst) {
        final DetailAST expr = throwAst.getFirstChild();
        final DetailAST literalNew = expr.findFirstToken(TokenTypes.LITERAL_NEW);
        String result = null;
        if (literalNew != null) {
            // Check for simple name (IDENT)
            final DetailAST ident = literalNew.findFirstToken(TokenTypes.IDENT);
            if (ident != null) {
                result = ident.getText();
            }
            else {
                // Check for qualified name (DOT)
                final DetailAST dot = literalNew.findFirstToken(TokenTypes.DOT);
                // Get the last part of the qualified name (the simple class name)
                result = dot.getLastChild().getText();
            }
        }
        return result;
    }

    /**
     * Walks up from the throw statement collecting variable names of enclosing
     * catch blocks whose caught type matches the thrown type.
     *
     * @param throwAst the LITERAL_THROW AST node
     * @param thrownType the simple type name of the thrown exception
     * @return the set of matching catch parameter names (may be empty)
     */
    private static Set<String> collectMatchingCatchVars(DetailAST throwAst, String thrownType) {
        final Set<String> result = new HashSet<>();
        DetailAST current = throwAst.getParent();
        while (current != null) {
            if (current.getType() == TokenTypes.LITERAL_CATCH) {
                final DetailAST paramDef =
                        current.findFirstToken(TokenTypes.PARAMETER_DEF);
                if (containsTypeName(paramDef, thrownType)) {
                    final DetailAST ident = paramDef.findFirstToken(TokenTypes.IDENT);
                    result.add(ident.getText());
                }
            }
            current = current.getParent();
        }
        return result;
    }

    /**
     * Recursively checks if the AST subtree contains a {@code getMessage()} call
     * on any of the given variable names.
     *
     * @param ast the AST node to search
     * @param varNames the caught-exception variable names to look for
     * @return true if a matching call is found
     */
    private static boolean hasGetMessageCallOnVar(DetailAST ast, Set<String> varNames) {
        boolean found = isGetMessageCallOnVar(ast, varNames);
        for (DetailAST child = ast.getFirstChild();
                child != null && !found; child = child.getNextSibling()) {
            found = hasGetMessageCallOnVar(child, varNames);
        }
        return found;
    }

    /**
     * Checks if a node is a {@code <var>.getMessage()} call where {@code var}
     * is one of the given names.
     *
     * @param node the AST node to check
     * @param varNames the caught-exception variable names to match against
     * @return true if this is a matching call
     */
    private static boolean isGetMessageCallOnVar(DetailAST node, Set<String> varNames) {
        boolean result = false;
        final DetailAST dot = node.findFirstToken(TokenTypes.DOT);
        if (dot != null) {
            final DetailAST methodIdent = dot.getLastChild();
            if ("getMessage".equals(methodIdent.getText())) {
                result = varNames.contains(dot.getFirstChild().getText());
            }
        }
        return result;
    }

    /**
     * Recursively checks if the given AST contains a type matching the specified name.
     * Handles IDENT (simple names), DOT (qualified names), and BOR (multi-catch) nodes.
     *
     * @param ast the AST node to search
     * @param typeName the type name to match
     * @return true if a matching type is found
     */
    private static boolean containsTypeName(DetailAST ast, String typeName) {
        boolean result = typeName.equals(ast.getText());

        for (DetailAST child = ast.getFirstChild();
             child != null && !result; child = child.getNextSibling()) {
            result = containsTypeName(child, typeName);
        }

        return result;
    }

}
