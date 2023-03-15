///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import java.util.Objects;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Check that the {@code default} is after all the cases in a {@code switch} statement.
 * </p>
 * <p>
 * Rationale: Java allows {@code default} anywhere within the
 * {@code switch} statement. But it is more readable if it comes after the last {@code case}.
 * </p>
 * <ul>
 * <li>
 * Property {@code skipIfLastAndSharedWithCase} - Control whether to allow {@code default}
 * along with {@code case} if they are not last.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;DefaultComesLast&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * switch (i) {
 *   case 1:
 *     break;
 *   case 2:
 *     break;
 *   default: // OK
 *     break;
 * }
 *
 * switch (i) {
 *   case 1:
 *     break;
 *   case 2:
 *     break; // OK, no default
 * }
 *
 * switch (i) {
 *   case 1:
 *     break;
 *   default: // violation, 'default' before 'case'
 *     break;
 *   case 2:
 *     break;
 * }
 *
 * switch (i) {
 *   case 1:
 *   default: // violation, 'default' before 'case'
 *     break;
 *   case 2:
 *     break;
 * }
 * </pre>
 * <p>To configure the check to allow default label to be not last if it is shared with case:
 * </p>
 * <pre>
 * &lt;module name=&quot;DefaultComesLast&quot;&gt;
 *   &lt;property name=&quot;skipIfLastAndSharedWithCase&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * switch (i) {
 *   case 1:
 *     break;
 *   case 2:
 *   default: // OK
 *     break;
 *   case 3:
 *     break;
 * }
 *
 * switch (i) {
 *   case 1:
 *     break;
 *   default: // violation
 *   case 2:
 *     break;
 * }
 *
 * // Switch rules are not subject to fall through, so this is still a violation:
 * switch (i) {
 *   case 1 -&gt; x = 9;
 *   default -&gt; x = 10; // violation
 *   case 2 -&gt; x = 32;
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
 * {@code default.comes.last}
 * </li>
 * <li>
 * {@code default.comes.last.in.casegroup}
 * </li>
 * </ul>
 *
 * @since 3.4
 */
@StatelessCheck
public class DefaultComesLastCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "default.comes.last";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE =
            "default.comes.last.in.casegroup";

    /** Control whether to allow {@code default} along with {@code case} if they are not last. */
    private boolean skipIfLastAndSharedWithCase;

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.LITERAL_DEFAULT,
        };
    }

    /**
     * Setter to control whether to allow {@code default} along with
     * {@code case} if they are not last.
     *
     * @param newValue whether to ignore checking.
     */
    public void setSkipIfLastAndSharedWithCase(boolean newValue) {
        skipIfLastAndSharedWithCase = newValue;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST defaultGroupAST = ast.getParent();

        // Switch rules are not subject to fall through.
        final boolean isSwitchRule = defaultGroupAST.getType() == TokenTypes.SWITCH_RULE;

        if (skipIfLastAndSharedWithCase && !isSwitchRule) {
            if (Objects.nonNull(findNextSibling(ast, TokenTypes.LITERAL_CASE))) {
                log(ast, MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE);
            }
            else if (ast.getPreviousSibling() == null
                && Objects.nonNull(findNextSibling(defaultGroupAST,
                                                   TokenTypes.CASE_GROUP))) {
                log(ast, MSG_KEY);
            }
        }
        else if (Objects.nonNull(findNextSibling(defaultGroupAST,
                                            TokenTypes.CASE_GROUP))
                    || Objects.nonNull(findNextSibling(defaultGroupAST,
                                            TokenTypes.SWITCH_RULE))) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Return token type only if passed tokenType in argument is found or returns -1.
     *
     * @param ast root node.
     * @param tokenType tokentype to be processed.
     * @return token if desired token is found or else null.
     */
    private static DetailAST findNextSibling(DetailAST ast, int tokenType) {
        DetailAST token = null;
        DetailAST node = ast.getNextSibling();
        while (node != null) {
            if (node.getType() == tokenType) {
                token = node;
                break;
            }
            node = node.getNextSibling();
        }
        return token;
    }

}
