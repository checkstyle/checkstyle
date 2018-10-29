package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class InputMethodLengthComments {
    public void visitToken(DetailAST ast) {

        final DetailAST openingBrace = ast.findFirstToken(TokenTypes.SLIST);

        if (openingBrace != null) {
            final DetailAST closingBrace =
                    openingBrace.findFirstToken(TokenTypes.RCURLY);
        }

    }

    public DetailAST visit(DetailAST ast) {
        final DetailAST openingBrace = ast.findFirstToken(TokenTypes.SLIST);
        DetailAST closingBrace = null;

        if (openingBrace != null) {
            closingBrace = openingBrace.findFirstToken(TokenTypes.RCURLY);
        }
        return closingBrace;
    }
}
