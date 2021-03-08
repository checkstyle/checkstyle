package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

@StatelessCheck
public class WhitespaceAroundArrayInitializers extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_PRECEDED = "ws.notPreceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_FOLLOWED = "ws.notFollowed";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.RCURLY,
            TokenTypes.ARRAY_INIT,
            TokenTypes.ANNOTATION_ARRAY_INIT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.RCURLY,
            TokenTypes.ARRAY_INIT,
            TokenTypes.ANNOTATION_ARRAY_INIT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast){
        final int currentType = ast.getType();
        final String line = getLine(ast.getLineNo()-1);
        final int before = ast.getColumnNo() - 1;
        final int after = ast.getColumnNo() + ast.getText().length();
        if (before >= 0) {
                final char prevChar = line.charAt(before);
                if (!Character.isWhitespace(prevChar)) {
                    log(ast, MSG_WS_NOT_PRECEDED, ast.getText());
                }
            }

            if (after < line.length()) {
                final char nextChar = line.charAt(after);
                if (!Character.isWhitespace(nextChar)) {
                    log(ast, MSG_WS_NOT_FOLLOWED, ast.getText());
                }
            }
    }
}
