package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundArrayInitialization {

    public void arrayInitTest() {
        final int[] colors = {1,2,3}; // missing whitespace after
                                    // '{' and missing whitespace before '}'
        final int[] colors1 = new int[]{4,5,6}; // missing whitespace before
                                                // and after '{' and missing whitespace before '}'
        final int[][] colors2 = {{1,2,3},{4,5,6}}; // missing whitespace
                                                  // at various spaces, mentioned in test
        final int[][] colors3 = { { 1,2,3 } , { 4,5,6 } }; // valid
    }

}
