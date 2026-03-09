package org.checkstyle.suppressionxpathfilter.coding.noarraytrailingcomma;

public class InputXpathNoArrayTrailingCommaThree {

    void method() {
        print(new int[][] {{1, 2}, {3, 3}, {5, 6},}); //warn
    }

    void print(int[][] arr) { }
}
