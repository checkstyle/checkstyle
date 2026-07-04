package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

/**
 * Dummy Test class.
 */
public class InputHorizontalWhiteSpaceInvalidThree {

    long a = 0;

    // violation below ''typecast' is not followed by whitespace.'
    int b = (int)a;

    /**
     * Dummy Test method.
     */
    public void test(int a,int b) { // violation '',' is not followed by whitespace.'
    }

    /**
     * Dummy Test method.
     */
    public void method(int a,int b,int c) { // 2 violations
        // '',' is not followed by whitespace.'
        // '',' is not followed by whitespace.'

        for (int i = 0;i <= 9;i++) { // 2 violations
        // '';' is not followed by whitespace.'
        // '';' is not followed by whitespace.'
        }
    }

    /**
     * Dummy Test inner class.
     */
    class Inner {
        Inner(int a,int b) {} // violation 'not followed by whitespace'

        Inner(int a,int b,int c) {} // 2 violations
        // '',' is not followed by whitespace.'
        // '',' is not followed by whitespace.'
    }
}
