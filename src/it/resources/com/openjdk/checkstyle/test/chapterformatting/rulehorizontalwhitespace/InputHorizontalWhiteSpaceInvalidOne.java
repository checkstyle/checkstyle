package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

// violation first line 'Header mismatch'

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
        switch(b){
            // 2 violations above:
            // ''switch' is not followed by whitespace.'
            // ''{' is not preceded with whitespace.'
            case 1: {
                System.out.println("hello");
                break;
            }
            default: {
                break;
            }
        }

        Runnable noop = () ->{};
        // violation above ''->' is not followed by whitespace.'

        char[] vowels = {'a', 'e', 'i', 'o', 'u'};

        for (char item: vowels) {}
        // violation above '':' is not preceded with whitespace.'

        for (int i = 100; i > 10; i--){}

        if(a < b) { // violation ''if' is not followed by whitespace.'
            System.out.println("True");
        }

        int s=4;
        // 2 violations above:
        // ''=' is not followed by whitespace.'
        // ''=' is not preceded with whitespace.'
    }

    /**
     * Dummy Test record.
     */
    record MyRecord3() {
        void method (){ // violation ''{' is not preceded with whitespace'
            final int a = 1;
            int b= 1;  // violation ''=' is not preceded with whitespace'
            b=1;
            // 2 violations above:
            // ''=' is not followed by whitespace.'
            // ''=' is not preceded with whitespace.'
        }
    }
}
