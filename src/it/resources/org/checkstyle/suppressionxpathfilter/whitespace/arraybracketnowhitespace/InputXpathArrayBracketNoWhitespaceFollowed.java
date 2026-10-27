/*
ArrayBracketNoWhitespace


*/

package org.checkstyle.suppressionxpathfilter.whitespace.arraybracketnowhitespace;

public class InputXpathArrayBracketNoWhitespaceFollowed {
    void bad() {
        int[] offsets = new int[ 10]; // violation "'[' is followed by whitespace."
    }
    void good() {
        int[] offsets = new int[10];
    }
}
