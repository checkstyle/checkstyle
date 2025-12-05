package org.checkstyle.suppressionxpathfilter.whitespace.arraybracketnowhitespace;

public class InputXpathArrayBracketNoWhitespaceFollowed {
    void bad() {
        int[][] offsets = new int[ 5][10]; //warn
    }
    void good() {
        int[][] offsets = new int[5][10];
    }
}