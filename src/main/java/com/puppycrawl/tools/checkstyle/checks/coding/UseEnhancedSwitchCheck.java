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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * <div>
 * Ensures that the enhanced switch (using {@code ->} for case labels) is used
 * instead of the traditional switch (using {@code :} for case labels) where possible.
 * </div>
 *
 * <p>
 * Rationale: Java 14 has introduced enhancements for switch statements and expressions
 * that disallow fall-through behavior. The enhanced switch syntax using {@code ->}
 * for case labels typically leads to more concise and readable code, reducing the likelihood
 * of errors associated with fall-through cases.
 * </p>
 *
 * <p>
 * See the <a href="https://docs.oracle.com/javase/specs/jls/se22/html/jls-14.html#jls-14.11.1-200">
 * Java Language Specification</a> for more information about {@code ->} case labels, also known as
 * "switch rules".
 * </p>
 *
 * @since 13.1.0
 */
@StatelessCheck
public class UseEnhancedSwitchCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "use.enhanced.switch";

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
        final List<DetailAST> caseGroups = getCaseGroups(ast);
        if (!caseGroups.isEmpty() && allCaseGroupsTerminate(caseGroups)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Check if all case groups terminate (i.e. do not fall through), except the last one which
     * terminates regardless of its content.
     *
     * @param caseGroups the list of case groups to check
     * @return {@code true} if all case groups terminate, {@code false} otherwise
     */
    private static boolean allCaseGroupsTerminate(List<DetailAST> caseGroups) {
        boolean allCaseGroupsTerminate = true;
        for (int index = 0; index < caseGroups.size() - 1; index++) {
            final DetailAST statementList = caseGroups.get(index).findFirstToken(TokenTypes.SLIST);

            if (!CheckUtil.isTerminated(statementList)) {
                allCaseGroupsTerminate = false;
                break;
            }
        }
        return allCaseGroupsTerminate;
    }

    /**
     * Get all case groups from the switch.
     *
     * @param switchAst the AST node representing a {@code LITERAL_SWITCH}
     * @return all {@code CASE_GROUP} nodes within the switch
     */
    private static List<DetailAST> getCaseGroups(DetailAST switchAst) {
        final List<DetailAST> caseGroups = new ArrayList<>();
        DetailAST ast = switchAst.getFirstChild();
        while (ast != null) {
            if (ast.getType() == TokenTypes.CASE_GROUP) {
                caseGroups.add(ast);
            }
            ast = ast.getNextSibling();
        }
        return Collections.unmodifiableList(caseGroups);
    }

}
