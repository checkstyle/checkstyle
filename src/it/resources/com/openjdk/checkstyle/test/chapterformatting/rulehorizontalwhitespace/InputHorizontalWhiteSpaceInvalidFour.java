package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

// violation first line 'Header mismatch*'

import java.io.IOException;

/**
 * Dummy input.
 */
public class InputHorizontalWhiteSpaceInvalidFour {

    int x;

    /**
     * Dummy input.
     */
    public InputHorizontalWhiteSpaceInvalidFour(int n) {
    }

    /**
     * Dummy input.
     */
    public void fun() {

        boolean a = true;
        if ( a ) {
        // 2 violations above:
        // ''(' is followed by whitespace'
        // '')' is preceded with whitespace'
        }

        try {
            throw new IOException();
        } catch ( IOException e) { // violation 'is followed by whitespace'
        } catch (Exception e ) {}  // violation 'is preceded with whitespace'

        for ( int i = 0; i < x; i++ ) {
        // 2 violations above:
        // ''(' is followed by whitespace'
        // '')' is preceded with whitespace'
        }
    }

    /**
     * Dummy input.
     */
    public void fun2() {
        switch ( x) { // violation 'is followed by whitespace'
            case 2: {
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * Dummy input.
     */
    class Bar extends InputHorizontalWhiteSpaceInvalidFour {

        /**
         * Dummy input.
         */
        Bar() {
            super(1 ); // violation '')' is preceded with whitespace'
        }

        /**
         * Dummy input.
         */
        Bar(int k) {
            super( k );
            // 2 violations above:
            // ''(' is followed by whitespace'
            // '')' is preceded with whitespace'
            for ( int i = 0; i < k; i++) { // violation ''(' is followed by whitespace'
            }
        }
    }
}
