///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that all constructors are grouped together.
 * If there is any code between the constructors
 * then this check will give an error.
 * The check identifies and flags any constructors that are not grouped together.
 * The error message will specify the line number from where the constructors are separated.
 * Comments between constructors are allowed.
 * </p>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code constructors.declaration.grouping}
 * </li>
 * </ul>
 *
 * @since 10.17.0
 */

@FileStatefulCheck
public class ConstructorsDeclarationGroupingCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "constructors.declaration.grouping";

    /**
     * Specifies the violation data.
     */
    private final Map<DetailAST, ViolationData> violationDataMap = new HashMap<>();

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
            TokenTypes.CTOR_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAst) {
        violationDataMap.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST currObjBlock = ast.getParent();
        ViolationData astData = violationDataMap.get(currObjBlock);

        if (astData != null) {
            final DetailAST previousSibling = ast.getPreviousSibling();
            final int siblingType = previousSibling.getType();
            final boolean isCtor = siblingType == TokenTypes.CTOR_DEF;
            final boolean isCompactCtor = siblingType == TokenTypes.COMPACT_CTOR_DEF;

            if (!isCtor && !isCompactCtor && !astData.isViolation()) {
                final DetailAST firstNonCtorSibling = astData.getPreviousCtor().getNextSibling();
                astData = new ViolationData(
                                        astData.getPreviousCtor(),
                                        firstNonCtorSibling.getLineNo(),
                                        true);
            }

            if (astData.isViolation()) {
                log(ast, MSG_KEY, astData.getLineNumber());
            }

            astData = new ViolationData(
                                    ast,
                                    astData.getLineNumber(),
                                    astData.isViolation());
        }
        else {
            astData = new ViolationData(ast, ast.getLineNo(), false);
        }

        violationDataMap.put(currObjBlock, astData);

    }

    /**
     * Data class to store violation data.
     * It contains information about the constructor, line number and violation status.
     */
    private static final class ViolationData {

        /**
         * Specifies the previous constructor for the current block.
         */
        private final DetailAST previousCtor;

        /**
         * Specifies the line number of the first non-constructor sibling node
         * following the last constructor for the current block.
         */
        private final int lineNumber;

        /**
         * Specifies the violation status for the current block.
         */
        private final boolean violation;

        /**
         * Creates a new instance of {@code ViolationData}.
         *
         * @param ast the last constructor before the separation.
         * @param lineNo the line number of the last constructor before the separation.
         * @param violationStatus the violation status.
         */
        private ViolationData(DetailAST ast, int lineNo, boolean violationStatus) {
            this.previousCtor = ast;
            this.lineNumber = lineNo;
            this.violation = violationStatus;
        }

        /**
         * Gets the previous constructor for the current block.
         *
         * @return the last constructor before the separation.
         */
        public DetailAST getPreviousCtor() {
            return previousCtor;
        }

        /**
         * Gets the line number of the first non-constructor sibling node
         * following the last constructor for the current block.
         *
         * @return the line number of the first non-constructor sibling node.
         */
        public int getLineNumber() {
            return lineNumber;
        }

        /**
         * Gets the violation status for the current block.
         *
         * @return the violation status.
         */
        public boolean isViolation() {
            return violation;
        }
    }
}
