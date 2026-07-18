package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

// violation first line 'Header mismatch'

import java.util.function.IntUnaryOperator;

/** Test Inputs from style guide. */
public class InputHorizontalWhiteSpaceDosAndDonts {
    boolean isFlagSet(String txt) {
        return true;
    }

    public void styleGuideDo() {
        int someInt;
        String myString;
        char aChar;
        long sixtyfourFlags;

        if (isFlagSet("GO")) {
            System.out.println("hello");
        }
        IntUnaryOperator inc = x -> x + 1;

        init: {
            int tempo = 0;
        }
    }

    public void styleGuideDonts() {
        int    someInt; // violation 'Use a single space to separate non-whitespace characters.'
        String myString;
        char   aChar; // violation 'Use a single space to separate non-whitespace characters.'
        long   sixtyfourFlags;
        // violation above 'Use a single space to separate non-whitespace characters.'

        if ( isFlagSet( "GO" ) ) {
            // 4 violations above:
            // ''(' is followed by whitespace.'
            // ''(' is followed by whitespace.'
            // '')' is preceded with whitespace.'
            // '')' is preceded with whitespace.'
            System.out.println("hello");
        }
        IntUnaryOperator inc = x ->x + 1;
        // violation above ''->' is not followed by whitespace.'

        init : { // violation '':' is preceded with whitespace.'
            int tempo = 0;
        }
    }
}
