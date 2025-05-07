///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Map;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks to avoid outdated API usage.
 * </div>
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
 * {@code outdated.api.usage}
 * </li>
 * </ul>
 *
 * @since 10.24.0
 */
@StatelessCheck
public class AvoidOutdatedUsageCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_OUTDATED_API_USAGE = "outdated.api.usage";

    /**
     * Outdated method and its successor (migration): "foo", ".bar(Bar)".
     */
    private static final Map<String, String> OUTDATED_METHODS = Map.of(
        "toList", ".collect(Collectors.toList())"
    );

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_CALL,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST child = ast.getFirstChild();
        if (child.getFirstChild() == null) {
            logOutdated(child.getText(), child);
        }
        else {
            final DetailAST nextSibling = child.getFirstChild().getNextSibling();
            logOutdated(nextSibling.getText(), nextSibling);
        }
    }

    /**
     * Log outdated.
     *
     * @param methodName the method name to check
     * @param ast        ast to check
     */
    private void logOutdated(String methodName, DetailAST ast) {
        if (isOutdated(methodName)) {
            log(ast,
                MSG_OUTDATED_API_USAGE,
                OUTDATED_METHODS.get(methodName));
        }
    }

    /**
     * Checks if the method name is in our outdated set.
     *
     * @param methodName the method name to check
     * @return true if the method is outdated
     */
    private static boolean isOutdated(String methodName) {
        return OUTDATED_METHODS.containsKey(methodName);
    }
}
