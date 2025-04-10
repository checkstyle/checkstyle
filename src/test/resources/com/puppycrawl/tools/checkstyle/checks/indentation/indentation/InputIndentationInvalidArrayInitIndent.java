package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationInvalidArrayInitIndent { //indent:0 exp:0

    int[] array = new int[] {1, 2, 3}; //indent:4 exp:4

  int[] arrayb = new int[] {1, 2, 3}; //indent:2 exp:4 warn
      int[] arrayc = new int[] {1, 2, 3}; //indent:6 exp:4 warn

  int[] array2 = new int[] { //indent:2 exp:4 warn
      1, 2, 3 //indent:6 exp:6
      }; //indent:6 exp:6

      int[] array2b = new int[] { //indent:6 exp:4 warn
        1, 2, 3 //indent:8 exp:10,34,36 warn
    }; //indent:4 exp:6,10 warn

    int[] array3 = new int[] { //indent:4 exp:4
         1, //indent:9 exp:8,31,33 warn
       2, //indent:7 exp:8,31,33 warn
         3 //indent:9 exp:8,31,33 warn
    }; //indent:4 exp:4


    int[] array4 = new int[] //indent:4 exp:4
  { //indent:2 exp:4,8 warn
        1, //indent:8 exp:8
        2, //indent:8 exp:8
        3 //indent:8 exp:8
      }; //indent:6 exp:4,8 warn


    int[] array5 = new int[] //indent:4 exp:4
  {1, 2, 3}; //indent:2 exp:4,8 warn


    int[] array6 = new int[] { 1, 2, //indent:4 exp:4
                    3, //indent:20 exp:8,31,33 warn
    4,}; //indent:4 exp:8,31,33 warn



    int[] array7 = new int[] { //indent:4 exp:4
      1, 2, //indent:6 exp:8,31,33 warn
        3 //indent:8 exp:8
    }; //indent:4 exp:4


  int[] array8 = new int[] { }; //indent:2 exp:4 warn

      int[] array9 = new int[] { //indent:6 exp:4 warn
  }; //indent:2 exp:6,10 warn

    int[][] array10 = new int[][] { //indent:4 exp:4
      new int[] { 1, 2, 3}, //indent:6 exp:8,36,38 warn
        new int[] { 1, 2, 3}, //indent:8 exp:8
    }; //indent:4 exp:4


    int[][] array10b //indent:4 exp:4
        = new int[][] { //indent:8 exp:8
          new int[] { 1, 2, 3}, //indent:10 exp:12,24,26 warn
            new int[] { 1, 2, 3}, //indent:12 exp:12
        }; //indent:8 exp:8
} //indent:0 exp:0
