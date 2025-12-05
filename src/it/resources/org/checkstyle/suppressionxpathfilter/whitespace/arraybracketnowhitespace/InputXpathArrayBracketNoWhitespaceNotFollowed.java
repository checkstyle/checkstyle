package org.checkstyle.suppressionxpathfilter.whitespace.arraybracketnowhitespace;

public class InputXpathArrayBracketNoWhitespaceNotFollowed {
    void bad() {
        int[][] matrix = new int[5][10];
        int total = matrix[0] [1]; //warn
    }
    void good() {
        int[][] matrix = new int[5][10];
        int total = matrix[0][1];
    }
}