/*
NoWhitespaceAfter
allowLineBreaks = false
tokens = LITERAL_CATCH,LITERAL_WHILE,LITERAL_FOR,LITERAL_IF,DO_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterTwo {
    void test() {
        try {
        } catch (Exception e) { // violation ''catch' is followed by whitespace.'

        }
        try {
        } catch(Exception e) {
        }
    }

    void test2() {
        do {
        } while(false);
        do {
        } while (false); // violation ''while' is followed by whitespace.'

        do {
        } while // violation ''while' is followed by whitespace.'
        (false);
    }

    void test3() {
        for (int i = 0; i < 10; i++) { // violation ''for' is followed by whitespace.'

        }
        for(int i = 0; i < 10; i++) {
        }
    }

    void test4(boolean condition) {
        if(condition) {
        }
        if (condition) { // violation ''if' is followed by whitespace.'

        }
        if // violation ''if' is followed by whitespace.'
        (condition) {
        }
    }

    boolean condition = false;
    void test5() {

        while (condition) { // violation ''while' is followed by whitespace.'

        }

        while(condition) {
        }
    }
}
