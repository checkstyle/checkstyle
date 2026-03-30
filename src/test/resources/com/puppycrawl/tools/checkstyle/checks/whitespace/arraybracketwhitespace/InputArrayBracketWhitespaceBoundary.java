/*
ArrayBracketWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;

public class InputArrayBracketWhitespaceBoundary {
    void testBoundary1() {
        int
 [] a; // violation ''\[' is preceded with whitespace.'
    }

    void testBoundary2() {
        int[] b = new int[5
];
    }

    void testBoundary3() {
        int[] c = new int[
5];
    }
}
