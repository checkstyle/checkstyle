package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

// violation first line 'Header mismatch*'

/** Invalid input class for whitespace. */
public class InputHorizontalWhiteSpaceInvalidTwo {

    void method() {
        outerLoop : // violation '':' is preceded with whitespace'
        for (int i = 0; i < 10; i++) {
            while (true) {
                    break outerLoop;
            }
        }
    }

    /**
     * Dummy test method.
     */
    void example() {

        switch (1) {
            case 1 : { // violation '':' is preceded with whitespace'
                break;
            }
            case 2: {
                break;
            }
            default : { // violation '':' is preceded with whitespace'
                break;
            }
        }

        switch (2) {
            case 2: {
                break;
            }
            case 3, 4 : { // violation '':' is preceded with whitespace'
                break;
            }
            case 5, 6: {
                break;
            }
            default : { // violation '':' is preceded with whitespace'
                break;
            }
        }
    }

    // violation below ''//' must be followed by a whitespace.'
    //this comment cause
    void testMethod(float f1) {
        int n = ( int ) f1;
        // 2 violations above:
        // ''(' is followed by whitespace.'
        // '')' is preceded with whitespace.'

        double d = 1.234567;
        float f2 = (float ) d; // violation '')' is preceded with whitespace.'
        float f3 = (float) d;
        float f4 = ( float) d; // violation ''(' is followed by whitespace.'
    }
}
