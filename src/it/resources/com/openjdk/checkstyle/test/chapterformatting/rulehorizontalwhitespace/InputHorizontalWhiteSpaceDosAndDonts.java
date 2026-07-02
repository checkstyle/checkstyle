package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

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
        int    someInt; // no vioaltion until SingleSpaceSeparator is enabled
        String myString; // no vioaltion until SingleSpaceSeparator is enabled
        char   aChar; // no vioaltion until SingleSpaceSeparator is enabled
        long   sixtyfourFlags; // no vioaltion until SingleSpaceSeparator is enabled

        if ( isFlagSet( "GO" ) ) { // 4 violations
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
