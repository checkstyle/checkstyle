package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

/**
 * Test class.
 */
public class InputHorizontalWhiteSpaceInvalidOne {
    int y = 0;
    int a = 4;

    /**
     * Dummy input method.
     */
    void example() {
        int b = 0;
        switch(b){ // 2 violations
            // ''{' is not preceded with whitespace.'
            // ''switch' is not followed by whitespace.'
            case 1: {
                System.out.println("hello");
                break;
            }
            default: {
                break;
            }
        }

        Runnable noop = () ->{}; // violation
        // no space after '->'

        char[] vowels = {'a', 'e', 'i', 'o', 'u'};

        for (char item: vowels) {}
        // violation above no space before ':'

        for (int i = 100; i > 10; i--){}

        if(a < b) { // violation ''if' is not followed by whitespace.'
            System.out.println("True");
        }

        int a=4; // 2 violations
        // no space before '=', no space after '='
    }

    /**
     * Dummy Test record.
     */
    record MyRecord3() {
        void method (){ // violation ''{' is not preceded with whitespace'
            final int a = 1;
            int b= 1; // violation ''=' is not preceded with whitespace'
            b=1; // 2 violations
        }
    }
}
