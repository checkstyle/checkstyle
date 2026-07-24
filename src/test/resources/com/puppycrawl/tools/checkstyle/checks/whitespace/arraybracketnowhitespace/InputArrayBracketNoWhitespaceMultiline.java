/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

public class InputArrayBracketNoWhitespaceMultiline {

    void testMultiline() {
        int[] arr = new int
[5];
        int z = arr[1
];
        int a = arr[
        1];
        int b = arr[0]
        ;
        // No warning below: ] is the last token on its line; the next real
        // token (;) is on the next line, so nextToken is null and no ws check fires.
        int x[]  // ok - trailing spaces before a line comment, no warning
        ;
        int[] arr3 = new int
 [5]; // violation ''\[' is preceded with whitespace.'
    }
}
