package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 8                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationValidArrayInitIndent { //indent:0 exp:0

    private static char[] sHexChars = { //indent:4 exp:4
            '0', '1', '2', '3', '4', '5', '6', '7', //indent:12 exp:12
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' }; //indent:12 exp:12

    int[] array = new int[] {1, 2, 3}; //indent:4 exp:4

    int[] array2 = new int[] { //indent:4 exp:4
            1, 2, 3 //indent:12 exp:12
    }; //indent:4 exp:4

    int[] array3 = new int[] { //indent:4 exp:4
            1, //indent:12 exp:12
            2, //indent:12 exp:12
            3 //indent:12 exp:12
    }; //indent:4 exp:4

    int[] array4 = new int[] //indent:4 exp:4
    { //indent:4 exp:4
            1, //indent:12 exp:12
            2, //indent:12 exp:12
            3 //indent:12 exp:12
    }; //indent:4 exp:4

    int[] array5 = new int[] //indent:4 exp:4
    {1, 2, 3}; //indent:4 exp:4

    // check nesting on first line //indent:4 exp:4
    int[] array6 = new int[] { 1, 2, //indent:4 exp:4
            3 //indent:12 exp:12
    }; //indent:4 exp:4

    int[] array6a = new int[] { 1, 2, //indent:4 exp:4
                                3, 4}; //indent:32 exp:32

    // nesting //indent:4 exp:4
    int[] array7 = new int[] { //indent:4 exp:4
            1, 2, //indent:12 exp:12
            3 //indent:12 exp:12
    }; //indent:4 exp:4

    String[][] mStuff = new String[][] { //indent:4 exp:4
            { "key", "value" } //indent:12 exp:12
    }; //indent:4 exp:4

    String[][] mStuff1 = new String[][] //indent:4 exp:4
    { //indent:4 exp:4
            { "key", "value" } //indent:12 exp:12
    }; //indent:4 exp:4

    int[] array8 = new int[] { }; //indent:4 exp:4

    int[] array9 = new int[] { //indent:4 exp:4
    }; //indent:4 exp:4

    int[][] array10 = new int[][] { //indent:4 exp:4
            new int[] { 1, 2, 3}, //indent:12 exp:12
            new int[] { 1, 2, 3}, //indent:12 exp:12
    }; //indent:4 exp:4

    int[][] array10b //indent:4 exp:4
        = new int[][] { //indent:8 exp:8
                new int[] { 1, 2, 3}, //indent:16 exp:16
                new int[] { 1, 2, 3}, //indent:16 exp:16
        }; //indent:8 exp:8

    private void func1(int[] arg) { //indent:4 exp:4

        char[] sHexChars2 = { //indent:8 exp:8
                '0', '1', '2', '3', '4', '5', '6', '7', //indent:16 exp:16
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' }; //indent:16 exp:16

        char[] sHexChars3 = { //indent:8 exp:8
                '0', '1', '2', '3', '4', '5', '6', '7', //indent:16 exp:16
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' //indent:16 exp:16
        }; //indent:8 exp:8

        char[] sHexChars4 = //indent:8 exp:8
        { //indent:8 exp:8
                '0', '1', '2', '3', '4', '5', '6', '7', //indent:16 exp:16
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' //indent:16 exp:16
        }; //indent:8 exp:8

    } //indent:4 exp:4

    /** Creates a new instance of InputIndentationValidArrayInitIndent */ //indent:4 exp:4
    public InputIndentationValidArrayInitIndent() { //indent:4 exp:4

        func1(new int[] {1, 2}); //indent:8 exp:8
        func1(new int[] {}); //indent:8 exp:8
        func1(new int[] { //indent:8 exp:8
                1, 2, 3 //indent:16 exp:16
        }); //indent:8 exp:8
        for (String veryLongVariableName: new String[] //indent:8 exp:8
        {"this is text", "this is text"}) { //indent:8 exp:8
            if (hashCode() == 0) break; //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4

} //indent:0 exp:0
