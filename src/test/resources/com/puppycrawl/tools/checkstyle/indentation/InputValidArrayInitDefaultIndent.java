/*
 * InputValidArrayInitIndent.java
 *
 * Created on December 9, 2002, 9:57 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

public class InputValidArrayInitIndent {

    private static char[] sHexChars = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    int[] array = new int[] {1, 2, 3};

    int[] array2 = new int[] {
        1, 2, 3
    };

    int[] array3 = new int[] {
        1,
        2,
        3
    };

    int[] array4 = new int[]
    {
        1,
        2,
        3
    };

    int[] array5 = new int[]
    {1, 2, 3};

    // check nesting on first line
    int[] array6 = new int[] { 1, 2,
        3
    };

    int[] array6a = new int[] { 1, 2,
                                3, 4};

    // nesting
    int[] array7 = new int[] {
        1, 2,
        3
    };

    String[][] mStuff = new String[][] {
        { "key", "value" }
    };

    String[][] mStuff1 = new String[][]
    {
        { "key", "value" }
    };

    int[] array8 = new int[] { };

    int[] array9 = new int[] {
    };

    int[][] array10 = new int[][] {
        new int[] { 1, 2, 3},
        new int[] { 1, 2, 3},
    };

    int[][] array10b
        = new int[][] {
            new int[] { 1, 2, 3},
            new int[] { 1, 2, 3},
        };

    private void func1(int[] arg) {

        char[] sHexChars2 = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        char[] sHexChars3 = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };

        char[] sHexChars4 =
        {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };

    }

    /** Creates a new instance of InputValidArrayInitIndent */
    public InputValidArrayInitIndent() {

        func1(new int[] {1, 2});
        func1(new int[] {});
        func1(new int[] {
            1, 2, 3
        });
    }

}
