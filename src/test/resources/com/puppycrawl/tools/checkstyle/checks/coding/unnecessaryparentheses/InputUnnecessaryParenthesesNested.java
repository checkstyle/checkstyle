/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LOR, TYPECAST


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesNested {

    int a;
    int b;
    int c;
    int d;
    boolean p;
    boolean q;
    boolean r;
    boolean s;

    void method1(int n) {
        if (((n = n % 2)) == 0) { // violation 'Unnecessary parentheses around expression'
            System.out.println("yes");
        }
        while (((n = n + 1)) < 10) { // violation 'Unnecessary parentheses around expression'
            System.out.println(n);
        }
        int x = ((1 + 2)) * 3; // violation 'Unnecessary parentheses around expression'
        int y = 5 * ((a + b)); // violation 'Unnecessary parentheses around expression'
        int z = ((a - b)) - c; // violation 'Unnecessary parentheses around expression'
        int w = a - ((c / d)); // violation 'Unnecessary parentheses around expression'
        boolean e = a == ((b % 2)); // violation 'Unnecessary parentheses around expression'
        int m = ((calc())) + 1; // violation 'Unnecessary parentheses around identifier 'calc''
        int sh = ((a << 2)) + 1; // violation 'Unnecessary parentheses around expression'
        int t = 2 * (((a + b))); // violation 'Unnecessary parentheses around expression'
        int deep = 1 + (a + ((b * c))); // violation 'Unnecessary parentheses around expression'
        foo(((a + b))); // violation 'Unnecessary parentheses around expression'
        for (int i = 0; i < ((6+6)); i++) { // violation 'Unnecessary parentheses around expression'
            System.out.println(i);
        }
    }

    int method2() {
        return ((a - b)); // violation 'Unnecessary parentheses around return value'
    }

    void method3() {
        int parens = (a + b) * (c - d);
        boolean bitwise = p || ((r ^ s)) || q;
        boolean precedence = p && ((q || r));
        int lit = ((2)) * 3; // violation 'Unnecessary parentheses around literal '2''
        boolean cmp = ((a)) == ((b));
        // 2 violations above:
        //  'Unnecessary parentheses around identifier 'a''
        //  'Unnecessary parentheses around identifier 'b''
    }

    void method4() {
        String str = "a" + ((1 + 2)); // violation 'Unnecessary parentheses around expression'
        int neg = -((a + b)); // violation 'Unnecessary parentheses around expression'
        Object obj = "x";
        boolean inst = obj != null && ((obj instanceof String));
        // violation above 'Unnecessary parentheses around expression'
        int[] arr = {1, 2};
        int arrVal = c + ((arr[0])); // violation 'Unnecessary parentheses around expression'
        int tern = a > 0 ? ((b + c)) : d;
        int md = c + ((a % b)); // violation 'Unnecessary parentheses around expression'
        int bs = c + ((a >>> b)); // violation 'Unnecessary parentheses around expression'
        int slc = c + ((a << b)); // violation 'Unnecessary parentheses around expression'
        int src = c + ((a >> b)); // violation 'Unnecessary parentheses around expression'
        int bd = c + ((a & b));
    }

    int calc() {
        return a + b;
    }

    void foo(int value) {
        System.out.println(value);
    }
}
