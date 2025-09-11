/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LOR


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

import java.io.BufferedReader;
import java.io.IOException;

public class InputUnnecessaryParenthesesIdentifier {
    public static boolean is(Class<?> clazz) {
        return true
            && (test(clazz)); // violation 'Unnecessary parentheses around identifier 'test'.'
    }

    public static boolean isOk(Class<?> clazz) {
        return true
            && test(clazz);
    }

    public int square(int a, int b){
    int square = (a * b); // violation 'Unnecessary parentheses around assignment right-hand side'
    return (square);      // violation 'Unnecessary parentheses around identifier 'square''
    }

    public int square1(int a, int b){
    int square = a * b;
    return square;
    }

    public static boolean test(Class<?> clazz) {
        return true;
    }

    public static boolean newTest(Class<?> clazz) {
        return true
            && test((clazz)); // violation 'Unnecessary parentheses around identifier 'clazz''
    }

    public static boolean newTest1(Class<?> clazz) {
        return true
            && test(clazz);
    }

    public static boolean newTest2(Class<?> clazz) {
        return true
            && ((test( // violation 'Unnecessary parentheses around identifier 'test''
                    (clazz)))); // violation 'Unnecessary parentheses around identifier 'clazz''
    }

    public String getMarkerNumber() {
        String markerNumber = "someText";
        return markerNumber;
    }

    public boolean m3() {
        return true;
    }

    public void testIF() {
        int a = 9;
        int b = 10;
        if (a > b) {
            System.out.println("a is greater then b");
        }
        else if ((b < a) && ( // violation 'Unnecessary parentheses around expression'
                (8>9) || // violation 'Unnecessary parentheses around expression'
                        (!m3()))) { // violation 'Unnecessary parentheses around expression'
             System.out.println();
        }
    }

    final double get1(int i) { return (double)(get(i)); }
    // violation above 'Unnecessary parentheses around identifier 'get''

    private Object get(int i) {
        return null;
    }

    public String isComment(String str) {
        return "str";
    }

    void method() throws IOException {
        String line = null;
        System.out.println(" line: null="
                              + false // (line != null)
                              + " empty="
                              + (line.equals(""))
            // violation above 'Unnecessary parentheses around expression'
                              + " comment="
                              + (isComment(line)));
        // violation above 'Unnecessary parentheses around identifier 'isComment''
        BufferedReader reader = null;
        line = reader.readLine();
    }
}
