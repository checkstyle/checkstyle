package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

@StatelessCheck
public class AfterLeftCurlyCheck extends AbstractCheck {
    /**
     * A key is pointing to the warning message empty.line.separator in "messages.properties"
     * file.
     */
    public static final String MSG_SHOULD_BE_FOLLOWED_BY_EMPTY_LINE = "after.left.curly.empty.line";

    /**
     * A key is pointing to the warning message empty.line.separator in "messages.properties"
     * file.
     */
    public static final String MSG_SHOULD_NOT_BE_FOLLOWED_BY_EMPTY_LINE = "after.left.curly.no.empty.line";

    /** Allow no empty line after Left Curly. */
    private boolean blankLineAfterLeftCurly = true;

    /**
     * Setter to allow no empty line between fields.
     *
     * @param blankLine
     *        User's value.
     */
    public final void setBlankLine(boolean blankLine) {
        blankLineAfterLeftCurly = blankLine;
    }


    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[]{TokenTypes.LCURLY};
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        boolean isFollowedByEmptyLine = isFollowedByEmptyLine(ast);
        final Object[] message = {ast.getText()};
        if (!isFollowedByEmptyLine && blankLineAfterLeftCurly) {
            log(ast, MSG_SHOULD_BE_FOLLOWED_BY_EMPTY_LINE, message);
        } else if (isFollowedByEmptyLine && !blankLineAfterLeftCurly) {
            log(ast, MSG_SHOULD_NOT_BE_FOLLOWED_BY_EMPTY_LINE, message);
        }
    }

    /**
     * Checks whether token is followed by an empty line.
     *
     * @param targetAST Ast token.
     * @return true if ast token is followed by an empty line.
     */
    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    private boolean isFollowedByEmptyLine(DetailAST targetAST) {
        return getFileContents().lineIsBlank(targetAST.getLineNo());
    }
}
