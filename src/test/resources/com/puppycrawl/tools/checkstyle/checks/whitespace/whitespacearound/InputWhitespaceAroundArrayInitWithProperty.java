package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

/* Config:
 * allowWhitespaceAroundArrayInit = true
 * tokens = RCURLY, ARRAY_INIT
 */

public class InputWhitespaceAroundArrayInitWithProperty {

    public void arrayInitTest() {
        final int[] colors = {1,2,3}; // missing whitespace after
                                    // '{' and missing whitespace before '}'
        final int[] colors1 = new int[]{4,5,6}; // missing whitespace before
                                                // and after '{' and missing whitespace before '}'
        final int[] colors2 = new int[]{ // violation, missing whitespace before '{'
                                0, 1, 2, 3
                              };
        final int[][] colors3 = new int[][]{ // missing whitespace before '{'
                                    {0,1,2,3}, // missing whitespace after '{', '}' and before '}'
                                    {4,5,6,7} // missing whitespace after '{' and before '}'
                                };
        final int[][] colors4 = {{1,2,3},{4,5,6}}; // violation, whitespace missing
                                                    // at various spaces, mentioned in test
        final int[][] colors5 = { { 1,2,3 } , { 4,5,6 } }; // ok
        final int[][] colors6 = {{1,2,3} ,{4,5,6 } }; // violation, whitespace missing
                                                    // at various spaces, mentioned in test
        int[][][] colors7 = {
                {
                  {1, -2, 3}, // violation, missing whitespace before '}' and after '{', '}'
                  {2, 3, 4} // violation, missing whitespace after '{' and before '}'
                }, // violation, missing whitespace after '}'
                {
                  {-4, -5, 6, 9}, // violation, missing whitespace before '}' and after '{', '}'
                  {1}, // violation, missing whitespace before '}' and after '{', '}'
                  {2, 3} // violation, missing whitespace after '{' and before '}'
                }
        };

    }
}
