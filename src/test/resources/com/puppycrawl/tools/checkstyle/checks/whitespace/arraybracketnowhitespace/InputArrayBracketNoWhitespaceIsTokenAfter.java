/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

public class InputArrayBracketNoWhitespaceIsTokenAfter {
    void testImmediatelyAdjacentTokens(int[] arr) {
        int a = arr[0]+1; // violation ''\]' is not followed by whitespace.'
        int b = arr[0]-1; // violation ''\]' is not followed by whitespace.'
        int c = arr[0]*2; // violation ''\]' is not followed by whitespace.'
    }

    void testEarlierLineHighColumnTokens(
            int[] someVeryLongNamedArray) {
        int result = someVeryLongNamedArray[0]+1; // violation ''\]' is not followed by whitespace.'
    }

    void testTokenOnNextLine(int[] arr) {
        int x = arr[0]
            + 1;
        int y = arr[0]
            ;
    }
}
