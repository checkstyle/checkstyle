package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

/*
 * Config:
 * max = 7
 * countEmpty = false
 */
public class InputMethodLengthComments {
    static class DetailClass {
        public DetailClass find(int type) {
            return null;
        }
    }
    static class Tokens {
        public static int ZERO = 0;
        public static int ONE = 1;
    }

    public void visitToken(DetailClass ast) {

        final DetailClass openingBrace = ast.find(Tokens.ZERO);

        if (openingBrace != null) {
            final DetailClass closingBrace =
                    openingBrace.find(Tokens.ONE);
        }

    }

    public DetailClass visit(DetailClass ast) { // violation
        final DetailClass openingBrace = ast.find(Tokens.ZERO);
        DetailClass closingBrace = null;

        if (openingBrace != null) {
            closingBrace = openingBrace.find(Tokens.ONE);
        }
        return closingBrace;
    }
}
