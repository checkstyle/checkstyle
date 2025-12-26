/*
UnnecessaryParentheses
tokens = EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LITERAL_NEW, LOR


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesFieldMethodAccess {

    private static int foo;
    private static Main INSTANCE;

    private static class Main {
        private String name;
        private int size;

        private boolean check() {
            return false;
        }
    }

    public static void main(String[] args) {
        foo = args[0].length();
        foo = (args[0]).length();
        // violation above 'Unnecessary parentheses around expression.'
        foo = String.valueOf(foo).length();
        foo = (String.valueOf(foo)).length();
        // violation above 'Unnecessary parentheses around expression.'
        foo = INSTANCE.name.length();
        foo = (INSTANCE.name).length();
        // violation above 'Unnecessary parentheses around expression.'
        foo = new StringBuilder().length();
        foo = (new StringBuilder()).length();
        // violation above 'Unnecessary parentheses around expression.'
        boolean b = INSTANCE.check() ? (INSTANCE.check()) : true;
        // violation above 'Unnecessary parentheses around expression.'
        foo = (INSTANCE.check()) ? 1 : 2;
        foo = ((String) INSTANCE.name).length();
        System.out.println(foo);
    }

    public static void plus() {
        int[] a = {1};
        int b;
        Main m = new Main();
        b = a[0] + 1;
        b = (a[0]) + 1;
        // violation above 'Unnecessary parentheses around expression.'
        b = m.size + 1;
        b = (m.size) + 1;
        // violation above 'Unnecessary parentheses around expression.'
        String s;
        s = m.check() + "";
        s = (m.check()) + "";
        // violation above 'Unnecessary parentheses around expression.'
        s = new Main() + "";
        s = (new Main()) + "";
        // violation above 'Unnecessary parentheses around expression.'
        s = ("" + "").trim();
        // TODO detect extra brackets in (("" + "")).trim();
    }

    public void checks() {
        Main m = new Main();
        if (!m.check()) { return; }
        if (!(m.check())) { return; }
        // violation above 'Unnecessary parentheses around expression.'

        String b1 = ((String) m.toString()).toString();
        String b2 = ((String) (m.toString())).toString();
        // violation above 'Unnecessary parentheses around expression.'
        Class c = m.getClass();
        if (c == null || !Main.class.isAssignableFrom(c)) { return; }
        if (c == null || !(Main.class.isAssignableFrom(c))) { return; }
        // violation above 'Unnecessary parentheses around expression.'
        java.util.List<String> noLinks = java.util.List.of("s");
        String[] newFiles = (String[]) noLinks.toArray(new String[noLinks.size()]);
        String[] moreFiles = (String[]) (noLinks.toArray(new String[noLinks.size()]));
        // violation above 'Unnecessary parentheses around expression.'
    }

}
