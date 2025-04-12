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

    int[] array = new int[] {1, 2, 3};

  int[] arrayb = new int[] {1, 2, 3};
      int[] arrayc = new int[] {1, 2, 3};

  int[] array2 = new int[] {
      1, 2, 3
      };

      int[] array2b = new int[] {
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


    int[] array6 = new int[] { 1, 2,
                    3,
    4,};



    int[] array7 = new int[] {
      1, 2,
        3
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
} //indent:0 exp:0
