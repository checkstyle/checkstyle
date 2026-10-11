package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

// violation first line 'Header mismatch'

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
    public void test(int x,int y) { // violation '',' is not followed by whitespace.'
    }

    /**
     * Dummy Test method.
     */
    public void method(int x,int y,int z) {
        // 2 violations above:
        // '',' is not followed by whitespace.'
        // '',' is not followed by whitespace.'

        for (int i = 0;i <= 9;i++) {
        // 2 violations above:
        // '';' is not followed by whitespace.'
        // '';' is not followed by whitespace.'
        }
    }

    /**
     * Dummy Test inner class.
     */
    class Inner {
        Inner(int e,int f) {} // violation 'not followed by whitespace'

        Inner(int e,int f,int g) {}
        // 2 violations above:
        // '',' is not followed by whitespace.'
        // '',' is not followed by whitespace.'
    }
}
