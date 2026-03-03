package org.checkstyle.suppressionxpathfilter.whitespace.arraybracketnowhitespace;

public class InputXpathArrayBracketNoWhitespaceNotFollowed {
    void bad() {
        int[] arr = {1, 2, 3};
        int total = arr[0]+ 5; //warn
    }
    void good() {
        int[] arr = {1, 2, 3};
        int total = arr[0] + 5;
    }
}
