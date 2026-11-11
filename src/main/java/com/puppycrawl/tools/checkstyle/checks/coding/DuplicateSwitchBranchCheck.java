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
import java.util.List;
import java.util.Objects;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks for duplicate implementations in {@code switch} statement or {@code switch} expression branches.
 * </div>
 *
 * @since 10.22.0
 */
@StatelessCheck
public class DuplicateSwitchBranchCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties".
     */
    public static final String MSG_KEY = "duplicate.switch.branch";

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
        return new int[] {
            TokenTypes.LITERAL_SWITCH,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final List<List<DetailAST>> branchExecutableNodes = new ArrayList<>();
        final List<DetailAST> branchReportNodes = new ArrayList<>();

        for (DetailAST child = ast.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.CASE_GROUP) {
                final List<DetailAST> executableNodes = getCaseGroupExecutableNodes(child);
                if (!executableNodes.isEmpty()) {
                    checkAndAddBranch(executableNodes, child.getFirstChild(), branchExecutableNodes, branchReportNodes);
                }
            }
            else if (child.getType() == TokenTypes.SWITCH_RULE) {
                final List<DetailAST> executableNodes = getSwitchRuleExecutableNodes(child);
                if (!executableNodes.isEmpty()) {
                    checkAndAddBranch(executableNodes, child.getFirstChild(), branchExecutableNodes, branchReportNodes);
                }
            }
        }
    }

    /**
     * Checks if the current branch nodes duplicate an earlier branch. If so, logs a violation;
     * otherwise adds it to the tracked branches list.
     *
     * @param executableNodes current branch executable nodes
     * @param reportNode node to log violation on
     * @param branchExecutableNodes list of previously processed branch executable node lists
     * @param branchReportNodes list of previous report nodes
     */
    private void checkAndAddBranch(List<DetailAST> executableNodes,
                                   DetailAST reportNode,
                                   List<List<DetailAST>> branchExecutableNodes,
                                   List<DetailAST> branchReportNodes) {
        boolean isDuplicate = false;
        int duplicateLineNumber = 0;

        for (int i = 0; i < branchExecutableNodes.size(); i++) {
            final List<DetailAST> prevExecutableNodes = branchExecutableNodes.get(i);
            if (areNodeListsEqual(executableNodes, prevExecutableNodes)) {
                isDuplicate = true;
                duplicateLineNumber = branchReportNodes.get(i).getLineNo();
                break;
            }
        }

        if (isDuplicate) {
            log(reportNode, MSG_KEY, duplicateLineNumber);
        }
        else {
            branchExecutableNodes.add(executableNodes);
            branchReportNodes.add(reportNode);
        }
    }

    /**
     * Extracts non-label executable nodes from a CASE_GROUP.
     *
     * @param caseGroup case group AST node
     * @return list of executable AST nodes
     */
    private static List<DetailAST> getCaseGroupExecutableNodes(DetailAST caseGroup) {
        final List<DetailAST> executableNodes = new ArrayList<>();
        for (DetailAST child = caseGroup.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getType() != TokenTypes.LITERAL_CASE
                    && child.getType() != TokenTypes.LITERAL_DEFAULT) {
                executableNodes.add(child);
            }
        }
        return executableNodes;
    }

    /**
     * Extracts body executable nodes from a SWITCH_RULE.
     *
     * @param switchRule switch rule AST node
     * @return list of executable AST nodes
     */
    private static List<DetailAST> getSwitchRuleExecutableNodes(DetailAST switchRule) {
        final List<DetailAST> executableNodes = new ArrayList<>();
        for (DetailAST child = switchRule.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getType() != TokenTypes.LITERAL_CASE
                    && child.getType() != TokenTypes.LITERAL_DEFAULT
                    && child.getType() != TokenTypes.LAMBDA) {
                executableNodes.add(child);
            }
        }
        return executableNodes;
    }

    /**
     * Compares two lists of AST nodes structurally.
     *
     * @param list1 first list of AST nodes
     * @param list2 second list of AST nodes
     * @return true if lists are structurally equal
     */
    private static boolean areNodeListsEqual(List<DetailAST> list1, List<DetailAST> list2) {
        boolean result = list1.size() == list2.size();
        if (result) {
            for (int i = 0; i < list1.size(); i++) {
                if (!isAstEqual(list1.get(i), list2.get(i))) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Recursively compares two DetailAST trees structurally.
     *
     * @param node1 first AST node
     * @param node2 second AST node
     * @return true if subtrees are structurally identical
     */
    private static boolean isAstEqual(DetailAST node1, DetailAST node2) {
        if (node1 == node2) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        if (node1.getType() != node2.getType()) {
            return false;
        }
        if (!Objects.equals(node1.getText(), node2.getText())) {
            return false;
        }

        return isAstEqual(node1.getFirstChild(), node2.getFirstChild())
                && isAstEqual(node1.getNextSibling(), node2.getNextSibling());
    }
}
