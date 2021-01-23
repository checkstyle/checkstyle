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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that switch statement has a {@code default} clause.
 * </p>
 * <p>
 * Rationale: It's usually a good idea to introduce a
 * default case in every switch statement. Even if
 * the developer is sure that all currently possible
 * cases are covered, this should be expressed in the
 * default branch, e.g. by using an assertion. This way
 * the code is protected against later changes, e.g.
 * introduction of new types in an enumeration type. Note that
 * the compiler requires switch expressions to be exhaustive,
 * so this check does not enforce default branches on
 * such expressions.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;MissingSwitchDefault&quot;/&gt;
 * </pre>
 * <p>Example of violation:</p>
 * <pre>
 * switch (i) {    // violation
 *  case 1:
 *    break;
 *  case 2:
 *    break;
 * }
 * </pre>
 * <p>Example of correct code:</p>
 * <pre>
 * switch (i) {
 *  case 1:
 *    break;
 *  case 2:
 *    break;
 *  default: // OK
 *    break;
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
 * {@code missing.switch.default}
 * </li>
 * </ul>
 *
 * @since 3.1
 */
@StatelessCheck
public class MissingSwitchDefaultCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "missing.switch.default";

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
        return new int[] {TokenTypes.LITERAL_SWITCH};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST firstSwitchMemberAst = findFirstSwitchMember(ast);

        if (!containsDefaultSwitch(firstSwitchMemberAst) && !isSwitchExpression(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks if the case group or its sibling contain the 'default' switch.
     *
     * @param caseGroupAst first case group to check.
     * @return true if 'default' switch found.
     */
    private static boolean containsDefaultSwitch(DetailAST caseGroupAst) {
        DetailAST nextAst = caseGroupAst;
        boolean found = false;

        while (nextAst != null) {
            if (nextAst.findFirstToken(TokenTypes.LITERAL_DEFAULT) != null) {
                found = true;
                break;
            }

            nextAst = nextAst.getNextSibling();
        }

        return found;
    }

    /**
     * Returns first CASE_GROUP or SWITCH_RULE ast.
     *
     * @param parent the switch statement we are checking
     * @return ast of first switch member.
     */
    private static DetailAST findFirstSwitchMember(DetailAST parent) {
        DetailAST switchMember = parent.findFirstToken(TokenTypes.CASE_GROUP);
        if (switchMember == null) {
            switchMember = parent.findFirstToken(TokenTypes.SWITCH_RULE);
        }
        return switchMember;
    }

    /**
     * Checks if this LITERAL_SWITCH token is part of a switch expression.
     *
     * @param ast the switch statement we are checking
     * @return true if part of a switch expression
     */
    private static boolean isSwitchExpression(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.EXPR;
    }

}
