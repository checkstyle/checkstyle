/*
NoArrayTrailingComma


*/

package org.checkstyle.suppressionxpathfilter.coding.noarraytrailingcomma;

public class InputXpathNoArrayTrailingCommaThree {

    void method() {
        print(new int[][] {{1, 2}, {3, 3}, {5, 6},});
        // violation above 'Array should not contain trailing comma.'
    }

    void print(int[][] arr) { }
}
