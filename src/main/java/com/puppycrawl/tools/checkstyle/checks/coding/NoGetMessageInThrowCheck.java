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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that exception messages in throw statements within
 * catch blocks do not include the caught exception's getMessage() call,
 * as the exception cause already contains this information.
 * </div>
 *
 * <p>
 * Rationale: When rethrowing exceptions with a cause, including the caught
 * exception's message in the new exception message is redundant. The message
 * is already accessible through the exception cause chain.
 * </p>
 *
 * <p>
 * Example of violations:
 * </p>
 * <div class="wrapper"><pre>
 * catch (IOException ex) {
 *     throw new CustomException("Error: " + ex.getMessage(), ex); // violation
 * }
 * </pre></div>
 *
 * <p>
 * Correct usage:
 * </p>
 * <div class="wrapper"><pre>
 * catch (IOException ex) {
 *     throw new CustomException("Error processing file", ex); // OK
 * }
 * </pre></div>
 *
 * @since 13.1.0
 */
@StatelessCheck
public class NoGetMessageInThrowCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "no.getmessage.in.throw";

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
        if (containsGetMessageCall(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks if the AST subtree contains a getMessage() call on the specified exception.
     *
     * @param ast the AST node to check
     * @return true if getMessage() is called on the exception
     */
    private static boolean containsGetMessageCall(DetailAST ast) {
        boolean found = false;
        DetailAST currentNode = ast.getFirstChild();

        while (currentNode != null && !found) {
            found = isGetMessageOnException(currentNode);
            if (!found) {
                found = containsGetMessageCall(currentNode);
            }
            currentNode = currentNode.getNextSibling();
        }

        return found;
    }

    /**
     * Checks if a method call is getMessage() on the specified exception variable.
     *
     * @param methodCall the method call AST node
     * @return true if this is a getMessage() call on the exception
     */
    private static boolean isGetMessageOnException(DetailAST methodCall) {
        final DetailAST dot = methodCall.findFirstToken(TokenTypes.DOT);
        boolean result = false;
        if (dot != null) {
            final DetailAST methodIdent = dot.getLastChild();
            result = "getMessage".equals(methodIdent.getText());
        }
        return result;
    }
}
