package org.checkstyle.suppressionxpathfilter.whitespace.arraybracketnowhitespace;

public class InputXpathArrayBracketNoWhitespaceNotFollowed {
    public void calculate() {
        int[][] matrix = new int[5][10];
        int total = matrix[0] [1]; // violation: whitespace after ']'
    }
}