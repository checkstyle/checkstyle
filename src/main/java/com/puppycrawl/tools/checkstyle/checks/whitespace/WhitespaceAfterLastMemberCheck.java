package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
*  This checks enforces whitespace after last member of the class.
 * </p>
 * Examples:
 * This code is valid:
 *
 * <pre>
 * class Employee {
 *     private String name;
*
 * }
 * </pre>
 *
 * This example is valid as well:
 *
 * <pre>
 * interface EmptyInterface {
 * }
 * </pre>
 *
 * But this violates the check:
 *
 * <pre>
 * class Message {
 *     public String msg;
 * }
 * </pre>
 *
 * @author <a href="mailto:piotr.listkiewicz@gmail.com">liscju</a>
 */
public class WhitespaceAfterLastMemberCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message ws.after.last.member in "messages.properties"
     * file.
     */
    public static final String MSG_WS_AFTER_LAST_MEMBER = "ws.after.last.member";

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.RCURLY};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getParent() != null &&
            ast.getParent().getType() == TokenTypes.OBJBLOCK &&
            ast.getParent().getParent().getType() == TokenTypes.CLASS_DEF &&
            ast.getPreviousSibling().getType() != TokenTypes.LCURLY) {
            if (!hasEmptyLineBefore(ast)) {
                DetailAST classDef = ast.getParent().getParent();
                log(classDef.getLineNo(), MSG_WS_AFTER_LAST_MEMBER);
            }
        }
    }

    /**
     * Checks if a token has a empty line before.
     * @param token token.
     * @return true, if token have empty line before.
     */
    private boolean hasEmptyLineBefore(DetailAST token) {
        final int lineNo = token.getLineNo();
        if (lineNo == 1) {
            return false;
        }
        //  [lineNo - 2] is the number of the previous line because the numbering starts from zero.
        final String lineBefore = getLines()[lineNo - 2];
        return lineBefore.trim().isEmpty();
    }
}
