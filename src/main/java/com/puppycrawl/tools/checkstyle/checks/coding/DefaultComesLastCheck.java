////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import java.util.Objects;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Check that the {@code default} is after all the {@code case}s
 * in a {@code switch} statement.
 * </p>
 * <p>
 * Rationale: Java allows {@code default} anywhere within the
 * {@code switch} statement. But if it comes after the last
 * {@code case} then it is more readable.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="DefaultComesLast"/&gt;
 * </pre>
 * @author o_sukhodolsky
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

    /** Whether to process skipIfLastAndSharedWithCaseInSwitch() invocations. */
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
     * Whether to allow default keyword not in last but surrounded with case.
     * @param newValue whether to ignore checking.
     */
    public void setSkipIfLastAndSharedWithCase(boolean newValue) {
        skipIfLastAndSharedWithCase = newValue;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST defaultGroupAST = ast.getParent();
        //default keywords used in annotations too - not what we're
        //interested in
        if (defaultGroupAST.getType() != TokenTypes.ANNOTATION_FIELD_DEF
                && defaultGroupAST.getType() != TokenTypes.MODIFIERS) {

            if (skipIfLastAndSharedWithCase) {
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
                                                     TokenTypes.CASE_GROUP))) {
                log(ast, MSG_KEY);
            }
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
