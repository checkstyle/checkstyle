/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

public class InputArrayBracketNoWhitespaceMisc {

    void testVar(int... nums) {
        int b = nums [0]; // violation ''\[' is preceded with whitespace.'
        int[] arr3 = new int [] {1,2,3}; // violation ''\[' is preceded with whitespace.'
    }

    void testInstanceof(Object o) {
        if (o instanceof int []) {} // violation ''\[' is preceded with whitespace.'
    }

    void testMultilineAncestor() {
        int
a[][];
    }
}
